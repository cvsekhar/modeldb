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

public class ConfigBlob implements ProtoType {
  public Optional<List<HyperparameterSetConfigBlob>> HyperparameterSet;
  public Optional<List<HyperparameterConfigBlob>> Hyperparameters;

  public ConfigBlob() {
    this.HyperparameterSet = Optional.empty();
    this.Hyperparameters = Optional.empty();
  }

  public Boolean isEmpty() {
    if (this.HyperparameterSet.isPresent()) {
      return false;
    }
    if (this.Hyperparameters.isPresent()) {
      return false;
    }
    return true;
  }

  // TODO: not consider order on lists
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    if (!(o instanceof ConfigBlob)) return false;
    ConfigBlob other = (ConfigBlob) o;

    {
      Function3<List<HyperparameterSetConfigBlob>, List<HyperparameterSetConfigBlob>, Boolean> f =
          (x2, y2) ->
              IntStream.range(0, Math.min(x2.size(), y2.size()))
                  .mapToObj(
                      i -> {
                        Function3<HyperparameterSetConfigBlob, HyperparameterSetConfigBlob, Boolean>
                            f2 = (x, y) -> x.equals(y);
                        return f2.apply(x2.get(i), y2.get(i));
                      })
                  .filter(x -> x != null)
                  .collect(Collectors.toList())
                  .isEmpty();
      if (this.HyperparameterSet.isPresent() || other.HyperparameterSet.isPresent()) {
        if (!this.HyperparameterSet.isPresent()) return false;
        if (other.HyperparameterSet.isPresent()) return false;
        if (!f.apply(this.HyperparameterSet.get(), other.HyperparameterSet.get())) return false;
      }
    }
    {
      Function3<List<HyperparameterConfigBlob>, List<HyperparameterConfigBlob>, Boolean> f =
          (x2, y2) ->
              IntStream.range(0, Math.min(x2.size(), y2.size()))
                  .mapToObj(
                      i -> {
                        Function3<HyperparameterConfigBlob, HyperparameterConfigBlob, Boolean> f2 =
                            (x, y) -> x.equals(y);
                        return f2.apply(x2.get(i), y2.get(i));
                      })
                  .filter(x -> x != null)
                  .collect(Collectors.toList())
                  .isEmpty();
      if (this.Hyperparameters.isPresent() || other.Hyperparameters.isPresent()) {
        if (!this.Hyperparameters.isPresent()) return false;
        if (other.Hyperparameters.isPresent()) return false;
        if (!f.apply(this.Hyperparameters.get(), other.Hyperparameters.get())) return false;
      }
    }
    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.HyperparameterSet, this.Hyperparameters);
  }

  public ConfigBlob setHyperparameterSet(Optional<List<HyperparameterSetConfigBlob>> value) {
    this.HyperparameterSet = value;
    return this;
  }

  public ConfigBlob setHyperparameterSet(List<HyperparameterSetConfigBlob> value) {
    if (value == null) this.HyperparameterSet = Optional.empty();
    else this.HyperparameterSet = Optional.of(value);
    return this;
  }

  public ConfigBlob setHyperparameters(Optional<List<HyperparameterConfigBlob>> value) {
    this.Hyperparameters = value;
    return this;
  }

  public ConfigBlob setHyperparameters(List<HyperparameterConfigBlob> value) {
    if (value == null) this.Hyperparameters = Optional.empty();
    else this.Hyperparameters = Optional.of(value);
    return this;
  }

  public static ConfigBlob fromProto(ai.verta.modeldb.versioning.ConfigBlob blob) {
    if (blob == null) {
      return null;
    }

    ConfigBlob obj = new ConfigBlob();
    {
      Function<ai.verta.modeldb.versioning.ConfigBlob, List<HyperparameterSetConfigBlob>> f =
          x ->
              blob.getHyperparameterSetList().stream()
                  .map(HyperparameterSetConfigBlob::fromProto)
                  .collect(Collectors.toList());
      obj.HyperparameterSet = Utils.removeEmpty(f.apply(blob));
    }
    {
      Function<ai.verta.modeldb.versioning.ConfigBlob, List<HyperparameterConfigBlob>> f =
          x ->
              blob.getHyperparametersList().stream()
                  .map(HyperparameterConfigBlob::fromProto)
                  .collect(Collectors.toList());
      obj.Hyperparameters = Utils.removeEmpty(f.apply(blob));
    }
    return obj;
  }

  public ai.verta.modeldb.versioning.ConfigBlob.Builder toProto() {
    ai.verta.modeldb.versioning.ConfigBlob.Builder builder =
        ai.verta.modeldb.versioning.ConfigBlob.newBuilder();
    this.HyperparameterSet.ifPresent(
        x ->
            builder.addAllHyperparameterSet(
                x.stream().map(y -> y.toProto().build()).collect(Collectors.toList())));
    this.Hyperparameters.ifPresent(
        x ->
            builder.addAllHyperparameters(
                x.stream().map(y -> y.toProto().build()).collect(Collectors.toList())));
    return builder;
  }

  public void preVisitShallow(Visitor visitor) throws ModelDBException {
    visitor.preVisitConfigBlob(this);
  }

  public void preVisitDeep(Visitor visitor) throws ModelDBException {
    this.preVisitShallow(visitor);
    if (this.HyperparameterSet.isPresent())
      visitor.preVisitDeepListOfHyperparameterSetConfigBlob(this.HyperparameterSet.get());
    if (this.Hyperparameters.isPresent())
      visitor.preVisitDeepListOfHyperparameterConfigBlob(this.Hyperparameters.get());
  }

  public ConfigBlob postVisitShallow(Visitor visitor) throws ModelDBException {
    return visitor.postVisitConfigBlob(this);
  }

  public ConfigBlob postVisitDeep(Visitor visitor) throws ModelDBException {
    if (this.HyperparameterSet.isPresent())
      this.setHyperparameterSet(
          visitor.postVisitDeepListOfHyperparameterSetConfigBlob(this.HyperparameterSet.get()));
    if (this.Hyperparameters.isPresent())
      this.setHyperparameters(
          visitor.postVisitDeepListOfHyperparameterConfigBlob(this.Hyperparameters.get()));
    return this.postVisitShallow(visitor);
  }
}
