# -*- coding: utf-8 -*-

from __future__ import print_function

import hashlib
import os

from .._protos.public.modeldb.versioning import Dataset_pb2 as _DatasetService

from ..external import six

from .._internal_utils import _artifact_utils
from .._internal_utils import _utils

from . import _dataset


class Path(_dataset._Dataset):
    """
    Captures metadata about files.

    .. note::

        If relative paths are passed in, they will *not* be converted to absolute paths.

    Parameters
    ----------
    paths : list of str
        List of filepaths or directory paths.
    enable_mdb_versioning : bool, default False
        Whether to upload the data itself to ModelDB to enable managed data versioning.

    Examples
    --------
    .. code-block:: python

        from verta.dataset import Path
        dataset1 = Path([
            "../datasets/census-train.csv",
            "../datasets/census-test.csv",
        ])
        dataset2 = Path([
            "../datasets",
        ])

    """
    def __init__(self, paths, enable_mdb_versioning=False):
        if isinstance(paths, six.string_types):
            paths = [paths]
        paths = map(os.path.expanduser, paths)

        super(Path, self).__init__(enable_mdb_versioning=enable_mdb_versioning)

        paths_to_metadata = dict()  # prevent duplicate objects
        for path in paths:
            paths_to_metadata.update({
                file_metadata.path: file_metadata
                for file_metadata
                in self._get_path_metadata(path)
            })

        metadata = six.viewvalues(paths_to_metadata)
        self._component_blobs.extend(metadata)

    def __repr__(self):
        lines = ["Path Version"]
        components = sorted(
            self._component_blobs,
            key=lambda component_msg: component_msg.path,
        )
        for component in components:
            lines.extend(self._path_component_to_repr_lines(component))

        return "\n    ".join(lines)

    @property
    def _component_blobs(self):
        return self._msg.path.components

    @classmethod
    def _get_path_metadata(cls, path):
        if os.path.isdir(path):
            for root, _, filenames in os.walk(path):
                for filename in filenames:
                    filepath = os.path.join(root, filename)
                    yield cls._get_file_metadata(filepath)
        else:
            yield cls._get_file_metadata(path)

    @classmethod
    def _get_file_metadata(cls, filepath):
        msg = _DatasetService.PathDatasetComponentBlob()
        msg.path = filepath
        msg.size = os.stat(filepath).st_size
        msg.last_modified_at_source = _utils.timestamp_to_ms(os.stat(filepath).st_mtime)
        msg.md5 = cls._hash_file(filepath)

        return msg

    @staticmethod
    def _hash_file(filepath):
        """
        Returns the MD5 hash of `filename`.

        Notes
        -----
        Loop recommended by https://stackoverflow.com/questions/3431825 and
        https://stackoverflow.com/questions/1131220.

        """
        file_hash = hashlib.md5()
        with open(filepath, 'rb') as f:
            for chunk in iter(lambda: f.read(8192), b''):
                file_hash.update(chunk)
        return file_hash.hexdigest()

    def _prepare_components_to_upload(self):
        """
        Tracks files for upload to ModelDB.

        This method does nothing if ModelDB-managed versioning was not enabled.

        """
        if not self._mdb_versioned:
            return

        for component_blob in self._path_component_blobs:
            component_path = component_blob.path

            # TODO: when stripping base path is implemented, reconstruct original path here
            filepath = os.path.abspath(component_path)

            # track which file this component corresponds to
            self._components_to_upload[component_path] = filepath

    def _clean_up_uploaded_components(self):
        """
        This method does nothing becaese this dataset's components should not be deleted.

        """
        return
