// THIS FILE IS AUTO-GENERATED. DO NOT EDIT
package ai.verta.swagger._public.modeldb.versioning.model

import scala.util.Try

import net.liftweb.json._

import ai.verta.swagger._public.modeldb.versioning.model.ArtifactTypeEnumArtifactType._
import ai.verta.swagger._public.modeldb.versioning.model.DiffStatusEnumDiffStatus._
import ai.verta.swagger._public.modeldb.versioning.model.OperatorEnumOperator._
import ai.verta.swagger._public.modeldb.versioning.model.ProtobufNullValue._
import ai.verta.swagger._public.modeldb.versioning.model.RepositoryVisibilityEnumRepositoryVisibility._
import ai.verta.swagger._public.modeldb.versioning.model.TernaryEnumTernary._
import ai.verta.swagger._public.modeldb.versioning.model.ValueTypeEnumValueType._
import ai.verta.swagger._public.modeldb.versioning.model.VersioningBlobType._
import ai.verta.swagger._public.modeldb.versioning.model.WorkspaceTypeEnumWorkspaceType._
import ai.verta.swagger.client.objects._

case class VersioningCommitMultipartVersionedBlobArtifact (
  commit_sha: Option[String] = None,
  location: Option[List[String]] = None,
  path_dataset_component_blob_path: Option[String] = None,
  repository_id: Option[VersioningRepositoryIdentification] = None
) extends BaseSwagger {
  def toJson(): JValue = VersioningCommitMultipartVersionedBlobArtifact.toJson(this)
}

object VersioningCommitMultipartVersionedBlobArtifact {
  def toJson(obj: VersioningCommitMultipartVersionedBlobArtifact): JObject = {
    new JObject(
      List[Option[JField]](
        obj.commit_sha.map(x => JField("commit_sha", JString(x))),
        obj.location.map(x => JField("location", ((x: List[String]) => JArray(x.map(JString)))(x))),
        obj.path_dataset_component_blob_path.map(x => JField("path_dataset_component_blob_path", JString(x))),
        obj.repository_id.map(x => JField("repository_id", ((x: VersioningRepositoryIdentification) => VersioningRepositoryIdentification.toJson(x))(x)))
      ).flatMap(x => x match {
        case Some(y) => List(y)
        case None => Nil
      })
    )
  }

  def fromJson(value: JValue): VersioningCommitMultipartVersionedBlobArtifact =
    value match {
      case JObject(fields) => {
        val fieldsMap = fields.map(f => (f.name, f.value)).toMap
        VersioningCommitMultipartVersionedBlobArtifact(
          // TODO: handle required
          commit_sha = fieldsMap.get("commit_sha").map(JsonConverter.fromJsonString),
          location = fieldsMap.get("location").map((x: JValue) => x match {case JArray(elements) => elements.map(JsonConverter.fromJsonString); case _ => throw new IllegalArgumentException(s"unknown type ${x.getClass.toString}")}),
          path_dataset_component_blob_path = fieldsMap.get("path_dataset_component_blob_path").map(JsonConverter.fromJsonString),
          repository_id = fieldsMap.get("repository_id").map(VersioningRepositoryIdentification.fromJson)
        )
      }
      case _ => throw new IllegalArgumentException(s"unknown type ${value.getClass.toString}")
    }
}
