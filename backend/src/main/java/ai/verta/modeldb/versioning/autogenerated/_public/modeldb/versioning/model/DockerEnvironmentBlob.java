// THIS FILE IS AUTO-GENERATED. DO NOT EDIT
package ai.verta.modeldb.versioning.autogenerated._public.modeldb.versioning.model;

import ai.verta.modeldb.ModelDBException;
import ai.verta.modeldb.versioning.*;
import ai.verta.modeldb.versioning.blob.diff.*;
import ai.verta.modeldb.versioning.blob.diff.Function3;
import ai.verta.modeldb.versioning.blob.visitors.Visitor;
import java.util.*;
import java.util.function.Function;

public class DockerEnvironmentBlob implements ProtoType {
  public Optional<String> Repository;
  public Optional<String> Tag;
  public Optional<String> Sha;

  public DockerEnvironmentBlob() {
    this.Repository = Optional.empty();
    this.Tag = Optional.empty();
    this.Sha = Optional.empty();
  }

  public Boolean isEmpty() {
    if (this.Repository.isPresent()) {
      return false;
    }
    if (this.Tag.isPresent()) {
      return false;
    }
    if (this.Sha.isPresent()) {
      return false;
    }
    return true;
  }

  // TODO: not consider order on lists
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    if (!(o instanceof DockerEnvironmentBlob)) return false;
    DockerEnvironmentBlob other = (DockerEnvironmentBlob) o;

    {
      Function3<String, String, Boolean> f = (x, y) -> x.equals(y);
      if (this.Repository.isPresent() || other.Repository.isPresent()) {
        if (!this.Repository.isPresent()) return false;
        if (other.Repository.isPresent()) return false;
        if (!f.apply(this.Repository.get(), other.Repository.get())) return false;
      }
    }
    {
      Function3<String, String, Boolean> f = (x, y) -> x.equals(y);
      if (this.Tag.isPresent() || other.Tag.isPresent()) {
        if (!this.Tag.isPresent()) return false;
        if (other.Tag.isPresent()) return false;
        if (!f.apply(this.Tag.get(), other.Tag.get())) return false;
      }
    }
    {
      Function3<String, String, Boolean> f = (x, y) -> x.equals(y);
      if (this.Sha.isPresent() || other.Sha.isPresent()) {
        if (!this.Sha.isPresent()) return false;
        if (other.Sha.isPresent()) return false;
        if (!f.apply(this.Sha.get(), other.Sha.get())) return false;
      }
    }
    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.Repository, this.Tag, this.Sha);
  }

  public DockerEnvironmentBlob setRepository(Optional<String> value) {
    this.Repository = value;
    return this;
  }

  public DockerEnvironmentBlob setRepository(String value) {
    if (value == null) this.Repository = Optional.empty();
    else this.Repository = Optional.of(value);
    return this;
  }

  public DockerEnvironmentBlob setTag(Optional<String> value) {
    this.Tag = value;
    return this;
  }

  public DockerEnvironmentBlob setTag(String value) {
    if (value == null) this.Tag = Optional.empty();
    else this.Tag = Optional.of(value);
    return this;
  }

  public DockerEnvironmentBlob setSha(Optional<String> value) {
    this.Sha = value;
    return this;
  }

  public DockerEnvironmentBlob setSha(String value) {
    if (value == null) this.Sha = Optional.empty();
    else this.Sha = Optional.of(value);
    return this;
  }

  public static DockerEnvironmentBlob fromProto(
      ai.verta.modeldb.versioning.DockerEnvironmentBlob blob) {
    if (blob == null) {
      return null;
    }

    DockerEnvironmentBlob obj = new DockerEnvironmentBlob();
    {
      Function<ai.verta.modeldb.versioning.DockerEnvironmentBlob, String> f =
          x -> (blob.getRepository());
      obj.Repository = Utils.removeEmpty(f.apply(blob));
    }
    {
      Function<ai.verta.modeldb.versioning.DockerEnvironmentBlob, String> f = x -> (blob.getTag());
      obj.Tag = Utils.removeEmpty(f.apply(blob));
    }
    {
      Function<ai.verta.modeldb.versioning.DockerEnvironmentBlob, String> f = x -> (blob.getSha());
      obj.Sha = Utils.removeEmpty(f.apply(blob));
    }
    return obj;
  }

  public ai.verta.modeldb.versioning.DockerEnvironmentBlob.Builder toProto() {
    ai.verta.modeldb.versioning.DockerEnvironmentBlob.Builder builder =
        ai.verta.modeldb.versioning.DockerEnvironmentBlob.newBuilder();
    this.Repository.ifPresent(x -> builder.setRepository(x));
    this.Tag.ifPresent(x -> builder.setTag(x));
    this.Sha.ifPresent(x -> builder.setSha(x));
    return builder;
  }

  public void preVisitShallow(Visitor visitor) throws ModelDBException {
    visitor.preVisitDockerEnvironmentBlob(this);
  }

  public void preVisitDeep(Visitor visitor) throws ModelDBException {
    this.preVisitShallow(visitor);
    if (this.Repository.isPresent()) visitor.preVisitDeepString(this.Repository.get());
    if (this.Tag.isPresent()) visitor.preVisitDeepString(this.Tag.get());
    if (this.Sha.isPresent()) visitor.preVisitDeepString(this.Sha.get());
  }

  public DockerEnvironmentBlob postVisitShallow(Visitor visitor) throws ModelDBException {
    return visitor.postVisitDockerEnvironmentBlob(this);
  }

  public DockerEnvironmentBlob postVisitDeep(Visitor visitor) throws ModelDBException {
    if (this.Repository.isPresent())
      this.setRepository(visitor.postVisitDeepString(this.Repository.get()));
    if (this.Tag.isPresent()) this.setTag(visitor.postVisitDeepString(this.Tag.get()));
    if (this.Sha.isPresent()) this.setSha(visitor.postVisitDeepString(this.Sha.get()));
    return this.postVisitShallow(visitor);
  }
}
