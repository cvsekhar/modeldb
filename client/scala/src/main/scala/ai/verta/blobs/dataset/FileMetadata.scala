package ai.verta.blobs.dataset

/** Represent a file's metadata stored in Dataset Blob
 *  @param lastModified last time file was modified
 *  @param md5 MD5 hash of the file
 *  @param path path of the file
 *  @param size size of the file
 *  @param versionId: (optional) versionId of the file. Only exists for S3 files
 */
class FileMetadata(
  val lastModified: BigInt,
  val md5: String,
  val path: String,
  val size: BigInt,
  val versionId: Option[String] = None
) {
  override def equals(other: Any) = other match {
    case other: FileMetadata => lastModified == other.lastModified &&
      md5 == other.md5 && path == other.path && size == other.size &&
      ((versionId.isEmpty && other.versionId.isEmpty) ||
      (versionId.isDefined && other.versionId.isDefined && versionId.get == other.versionId.get))
    case _ => false
  }
}
