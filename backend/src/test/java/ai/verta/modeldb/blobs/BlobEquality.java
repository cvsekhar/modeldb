package ai.verta.modeldb.blobs;

import static org.junit.Assert.*;
import static org.junit.Assume.*;

import ai.verta.modeldb.versioning.autogenerated._public.modeldb.versioning.model.*;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

@RunWith(JUnitQuickcheck.class)
public class BlobEquality {
  @Property
  public void equalityAutogenBlobDiff(AutogenBlobDiff b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenBlob(AutogenBlob b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenCodeBlob(AutogenCodeBlob b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenCodeDiff(AutogenCodeDiff b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenCommandLineEnvironmentDiff(AutogenCommandLineEnvironmentDiff b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenConfigBlob(AutogenConfigBlob b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenConfigDiff(AutogenConfigDiff b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenContinuousHyperparameterSetConfigBlob(
      AutogenContinuousHyperparameterSetConfigBlob b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenDatasetBlob(AutogenDatasetBlob b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenDatasetDiff(AutogenDatasetDiff b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenDiscreteHyperparameterSetConfigBlob(
      AutogenDiscreteHyperparameterSetConfigBlob b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenDockerEnvironmentBlob(AutogenDockerEnvironmentBlob b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenDockerEnvironmentDiff(AutogenDockerEnvironmentDiff b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenEnvironmentBlob(AutogenEnvironmentBlob b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenEnvironmentDiff(AutogenEnvironmentDiff b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenEnvironmentVariablesBlob(AutogenEnvironmentVariablesBlob b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenEnvironmentVariablesDiff(AutogenEnvironmentVariablesDiff b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenGitCodeBlob(AutogenGitCodeBlob b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenGitCodeDiff(AutogenGitCodeDiff b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenHyperparameterConfigBlob(AutogenHyperparameterConfigBlob b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenHyperparameterConfigDiff(AutogenHyperparameterConfigDiff b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenHyperparameterSetConfigBlob(AutogenHyperparameterSetConfigBlob b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenHyperparameterSetConfigDiff(AutogenHyperparameterSetConfigDiff b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenHyperparameterValuesConfigBlob(
      AutogenHyperparameterValuesConfigBlob b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenNotebookCodeBlob(AutogenNotebookCodeBlob b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenNotebookCodeDiff(AutogenNotebookCodeDiff b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenPathDatasetBlob(AutogenPathDatasetBlob b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenPathDatasetComponentBlob(AutogenPathDatasetComponentBlob b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenPathDatasetComponentDiff(AutogenPathDatasetComponentDiff b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenPathDatasetDiff(AutogenPathDatasetDiff b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenPythonEnvironmentBlob(AutogenPythonEnvironmentBlob b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenPythonEnvironmentDiff(AutogenPythonEnvironmentDiff b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenPythonRequirementEnvironmentBlob(
      AutogenPythonRequirementEnvironmentBlob b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenPythonRequirementEnvironmentDiff(
      AutogenPythonRequirementEnvironmentDiff b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenS3DatasetBlob(AutogenS3DatasetBlob b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenS3DatasetComponentBlob(AutogenS3DatasetComponentBlob b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenS3DatasetComponentDiff(AutogenS3DatasetComponentDiff b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenS3DatasetDiff(AutogenS3DatasetDiff b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenVersionEnvironmentBlob(AutogenVersionEnvironmentBlob b) {
    assertTrue(b.equals(b));
  }

  @Property
  public void equalityAutogenVersionEnvironmentDiff(AutogenVersionEnvironmentDiff b) {
    assertTrue(b.equals(b));
  }
}
