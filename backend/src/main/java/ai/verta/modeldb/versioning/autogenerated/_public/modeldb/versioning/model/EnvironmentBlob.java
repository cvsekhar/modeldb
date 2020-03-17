// THIS FILE IS AUTO-GENERATED. DO NOT EDIT
package ai.verta.modeldb.versioning.autogenerated._public.modeldb.versioning.model;

import ai.verta.modeldb.ModelDBException;
import ai.verta.modeldb.versioning.*;
import ai.verta.modeldb.versioning.blob.diff.*;
import ai.verta.modeldb.versioning.blob.diff.Function3;
import ai.verta.modeldb.versioning.blob.visitors.Visitor;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EnvironmentBlob implements ProtoType {
  public Optional<PythonEnvironmentBlob> Python;
  public Optional<DockerEnvironmentBlob> Docker;
  public Optional<List<EnvironmentVariablesBlob>> EnvironmentVariables;
  public Optional<List<String>> CommandLine;

  public EnvironmentBlob() {
    this.Python = Optional.empty();
    this.Docker = Optional.empty();
    this.EnvironmentVariables = Optional.empty();
    this.CommandLine = Optional.empty();
  }

  public Boolean isEmpty() {
    if (this.Python.isPresent()) {
      return false;
    }
    if (this.Docker.isPresent()) {
      return false;
    }
    if (this.EnvironmentVariables.isPresent()) {
      return false;
    }
    if (this.CommandLine.isPresent()) {
      return false;
    }
    return true;
  }

  // TODO: not consider order on lists
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    if (!(o instanceof EnvironmentBlob)) return false;
    EnvironmentBlob other = (EnvironmentBlob) o;

    {
      Function3<PythonEnvironmentBlob, PythonEnvironmentBlob, Boolean> f = (x, y) -> x.equals(y);
      if (this.Python.isPresent() || other.Python.isPresent()) {
        if (!this.Python.isPresent()) return false;
        if (other.Python.isPresent()) return false;
        if (!f.apply(this.Python.get(), other.Python.get())) return false;
      }
    }
    {
      Function3<DockerEnvironmentBlob, DockerEnvironmentBlob, Boolean> f = (x, y) -> x.equals(y);
      if (this.Docker.isPresent() || other.Docker.isPresent()) {
        if (!this.Docker.isPresent()) return false;
        if (other.Docker.isPresent()) return false;
        if (!f.apply(this.Docker.get(), other.Docker.get())) return false;
      }
    }
    {
      Function3<List<EnvironmentVariablesBlob>, List<EnvironmentVariablesBlob>, Boolean> f =
          (x2, y2) ->
              IntStream.range(0, Math.min(x2.size(), y2.size()))
                  .mapToObj(
                      i -> {
                        Function3<EnvironmentVariablesBlob, EnvironmentVariablesBlob, Boolean> f2 =
                            (x, y) -> x.equals(y);
                        return f2.apply(x2.get(i), y2.get(i));
                      })
                  .filter(x -> x != null)
                  .collect(Collectors.toList())
                  .isEmpty();
      if (this.EnvironmentVariables.isPresent() || other.EnvironmentVariables.isPresent()) {
        if (!this.EnvironmentVariables.isPresent()) return false;
        if (other.EnvironmentVariables.isPresent()) return false;
        if (!f.apply(this.EnvironmentVariables.get(), other.EnvironmentVariables.get()))
          return false;
      }
    }
    {
      Function3<List<String>, List<String>, Boolean> f =
          (x2, y2) ->
              IntStream.range(0, Math.min(x2.size(), y2.size()))
                  .mapToObj(
                      i -> {
                        Function3<String, String, Boolean> f2 = (x, y) -> x.equals(y);
                        return f2.apply(x2.get(i), y2.get(i));
                      })
                  .filter(x -> x != null)
                  .collect(Collectors.toList())
                  .isEmpty();
      if (this.CommandLine.isPresent() || other.CommandLine.isPresent()) {
        if (!this.CommandLine.isPresent()) return false;
        if (other.CommandLine.isPresent()) return false;
        if (!f.apply(this.CommandLine.get(), other.CommandLine.get())) return false;
      }
    }
    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.Python, this.Docker, this.EnvironmentVariables, this.CommandLine);
  }

  public EnvironmentBlob setPython(Optional<PythonEnvironmentBlob> value) {
    this.Python = value;
    return this;
  }

  public EnvironmentBlob setPython(PythonEnvironmentBlob value) {
    if (value == null) this.Python = Optional.empty();
    else this.Python = Optional.of(value);
    return this;
  }

  public EnvironmentBlob setDocker(Optional<DockerEnvironmentBlob> value) {
    this.Docker = value;
    return this;
  }

  public EnvironmentBlob setDocker(DockerEnvironmentBlob value) {
    if (value == null) this.Docker = Optional.empty();
    else this.Docker = Optional.of(value);
    return this;
  }

  public EnvironmentBlob setEnvironmentVariables(Optional<List<EnvironmentVariablesBlob>> value) {
    this.EnvironmentVariables = value;
    return this;
  }

  public EnvironmentBlob setEnvironmentVariables(List<EnvironmentVariablesBlob> value) {
    if (value == null) this.EnvironmentVariables = Optional.empty();
    else this.EnvironmentVariables = Optional.of(value);
    return this;
  }

  public EnvironmentBlob setCommandLine(Optional<List<String>> value) {
    this.CommandLine = value;
    return this;
  }

  public EnvironmentBlob setCommandLine(List<String> value) {
    if (value == null) this.CommandLine = Optional.empty();
    else this.CommandLine = Optional.of(value);
    return this;
  }

  public static EnvironmentBlob fromProto(ai.verta.modeldb.versioning.EnvironmentBlob blob) {
    if (blob == null) {
      return null;
    }

    EnvironmentBlob obj = new EnvironmentBlob();
    {
      Function<ai.verta.modeldb.versioning.EnvironmentBlob, PythonEnvironmentBlob> f =
          x -> PythonEnvironmentBlob.fromProto(blob.getPython());
      obj.Python = Utils.removeEmpty(f.apply(blob));
    }
    {
      Function<ai.verta.modeldb.versioning.EnvironmentBlob, DockerEnvironmentBlob> f =
          x -> DockerEnvironmentBlob.fromProto(blob.getDocker());
      obj.Docker = Utils.removeEmpty(f.apply(blob));
    }
    {
      Function<ai.verta.modeldb.versioning.EnvironmentBlob, List<EnvironmentVariablesBlob>> f =
          x ->
              blob.getEnvironmentVariablesList().stream()
                  .map(EnvironmentVariablesBlob::fromProto)
                  .collect(Collectors.toList());
      obj.EnvironmentVariables = Utils.removeEmpty(f.apply(blob));
    }
    {
      Function<ai.verta.modeldb.versioning.EnvironmentBlob, List<String>> f =
          x -> blob.getCommandLineList();
      obj.CommandLine = Utils.removeEmpty(f.apply(blob));
    }
    return obj;
  }

  public ai.verta.modeldb.versioning.EnvironmentBlob.Builder toProto() {
    ai.verta.modeldb.versioning.EnvironmentBlob.Builder builder =
        ai.verta.modeldb.versioning.EnvironmentBlob.newBuilder();
    this.Python.ifPresent(x -> builder.setPython(x.toProto()));
    this.Docker.ifPresent(x -> builder.setDocker(x.toProto()));
    this.EnvironmentVariables.ifPresent(
        x ->
            builder.addAllEnvironmentVariables(
                x.stream().map(y -> y.toProto().build()).collect(Collectors.toList())));
    this.CommandLine.ifPresent(x -> builder.addAllCommandLine(x));
    return builder;
  }

  public void preVisitShallow(Visitor visitor) throws ModelDBException {
    visitor.preVisitEnvironmentBlob(this);
  }

  public void preVisitDeep(Visitor visitor) throws ModelDBException {
    this.preVisitShallow(visitor);
    if (this.Python.isPresent()) visitor.preVisitDeepPythonEnvironmentBlob(this.Python.get());
    if (this.Docker.isPresent()) visitor.preVisitDeepDockerEnvironmentBlob(this.Docker.get());
    if (this.EnvironmentVariables.isPresent())
      visitor.preVisitDeepListOfEnvironmentVariablesBlob(this.EnvironmentVariables.get());
    if (this.CommandLine.isPresent()) visitor.preVisitDeepListOfString(this.CommandLine.get());
  }

  public EnvironmentBlob postVisitShallow(Visitor visitor) throws ModelDBException {
    return visitor.postVisitEnvironmentBlob(this);
  }

  public EnvironmentBlob postVisitDeep(Visitor visitor) throws ModelDBException {
    if (this.Python.isPresent())
      this.setPython(visitor.postVisitDeepPythonEnvironmentBlob(this.Python.get()));
    if (this.Docker.isPresent())
      this.setDocker(visitor.postVisitDeepDockerEnvironmentBlob(this.Docker.get()));
    if (this.EnvironmentVariables.isPresent())
      this.setEnvironmentVariables(
          visitor.postVisitDeepListOfEnvironmentVariablesBlob(this.EnvironmentVariables.get()));
    if (this.CommandLine.isPresent())
      this.setCommandLine(visitor.postVisitDeepListOfString(this.CommandLine.get()));
    return this.postVisitShallow(visitor);
  }
}
