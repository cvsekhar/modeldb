import * as React from 'react';

import { IDatasetBlobDiff } from 'core/shared/models/Versioning/Blob/DatasetBlob';
import matchBy from 'core/shared/utils/matchBy';
import matchType from 'core/shared/utils/matchType';
import { BlobDataBox } from 'core/shared/view/domain/Versioning/Blob/BlobBox/BlobBox';

import PathComponentsDiff from './PathComponentsDiff/PathComponentsDiff';
import S3ComponentsDiff from './S3ComponentsDiff/S3ComponentsDiff';

const DatasetDiffView = ({ diff }: { diff: IDatasetBlobDiff }) => {
  const title = matchType(
    {
      s3: () => 'S3 Dataset',
      path: () => 'Path Dataset',
    },
    diff.type
  );

  return (
    <BlobDataBox title={title}>
      {matchBy(diff, 'type')({
        path: pathDiff => (
          <PathComponentsDiff diff={pathDiff.data.components} />
        ),
        s3: s3Diff => <S3ComponentsDiff diff={s3Diff.data.components} />,
      })}
    </BlobDataBox>
  );
};

export default DatasetDiffView;
