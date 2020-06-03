package ai.verta.modeldb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import ai.verta.common.KeyValue;
import ai.verta.common.ValueTypeEnum.ValueType;
import ai.verta.modeldb.ArtifactTypeEnum.ArtifactType;
import ai.verta.modeldb.CommitArtifactPart.Response;
import ai.verta.modeldb.ExperimentRunServiceGrpc.ExperimentRunServiceBlockingStub;
import ai.verta.modeldb.ExperimentServiceGrpc.ExperimentServiceBlockingStub;
import ai.verta.modeldb.ProjectServiceGrpc.ProjectServiceBlockingStub;
import ai.verta.modeldb.authservice.AuthService;
import ai.verta.modeldb.authservice.AuthServiceUtils;
import ai.verta.modeldb.authservice.PublicAuthServiceUtils;
import ai.verta.modeldb.authservice.PublicRoleServiceUtils;
import ai.verta.modeldb.authservice.RoleService;
import ai.verta.modeldb.authservice.RoleServiceUtils;
import ai.verta.modeldb.utils.ModelDBUtils;
import com.google.protobuf.ListValue;
import com.google.protobuf.Value;
import io.grpc.ManagedChannel;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExperimentRunTest2 {

  private static final Logger LOGGER = LogManager.getLogger(ExperimentRunTest2.class);
  /**
   * This rule manages automatic graceful shutdown for the registered servers and channels at the
   * end of test.
   */
  @Rule public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

  private ManagedChannel channel = null;
  private ManagedChannel client2Channel = null;
  private static String serverName = InProcessServerBuilder.generateName();
  private static InProcessServerBuilder serverBuilder =
      InProcessServerBuilder.forName(serverName).directExecutor();
  private static InProcessChannelBuilder client1ChannelBuilder =
      InProcessChannelBuilder.forName(serverName).directExecutor();
  private static InProcessChannelBuilder client2ChannelBuilder =
      InProcessChannelBuilder.forName(serverName).directExecutor();
  private static AuthClientInterceptor authClientInterceptor;
  private static App app;

  @SuppressWarnings("unchecked")
  @BeforeClass
  public static void setServerAndService() throws Exception {

    Map<String, Object> propertiesMap =
        ModelDBUtils.readYamlProperties(System.getenv(ModelDBConstants.VERTA_MODELDB_CONFIG));
    Map<String, Object> testPropMap = (Map<String, Object>) propertiesMap.get("test");
    Map<String, Object> databasePropMap = (Map<String, Object>) testPropMap.get("test-database");

    app = App.getInstance();
    AuthService authService = new PublicAuthServiceUtils();
    RoleService roleService = new PublicRoleServiceUtils(authService);

    Map<String, Object> authServicePropMap =
        (Map<String, Object>) propertiesMap.get(ModelDBConstants.AUTH_SERVICE);
    if (authServicePropMap != null) {
      String authServiceHost = (String) authServicePropMap.get(ModelDBConstants.HOST);
      Integer authServicePort = (Integer) authServicePropMap.get(ModelDBConstants.PORT);
      app.setAuthServerHost(authServiceHost);
      app.setAuthServerPort(authServicePort);

      authService = new AuthServiceUtils();
      roleService = new RoleServiceUtils(authService);
    }

    App.initializeServicesBaseOnDataBase(
        serverBuilder, databasePropMap, propertiesMap, authService, roleService);
    serverBuilder.intercept(new ModelDBAuthInterceptor());

    Map<String, Object> testUerPropMap = (Map<String, Object>) testPropMap.get("testUsers");
    if (testUerPropMap != null && testUerPropMap.size() > 0) {
      authClientInterceptor = new AuthClientInterceptor(testPropMap);
      client1ChannelBuilder.intercept(authClientInterceptor.getClient1AuthInterceptor());
      client2ChannelBuilder.intercept(authClientInterceptor.getClient2AuthInterceptor());
    }
  }

  @AfterClass
  public static void removeServerAndService() {
    App.initiateShutdown(0);
  }

  @After
  public void clientClose() {
    if (!channel.isShutdown()) {
      channel.shutdownNow();
    }
    if (!client2Channel.isShutdown()) {
      client2Channel.shutdownNow();
    }
  }

  @Before
  public void initializeChannel() throws IOException {
    grpcCleanup.register(serverBuilder.build().start());
    channel = grpcCleanup.register(client1ChannelBuilder.maxInboundMessageSize(1024).build());
    client2Channel =
        grpcCleanup.register(client2ChannelBuilder.maxInboundMessageSize(1024).build());
  }

  private void checkEqualsAssert(StatusRuntimeException e) {
    Status status = Status.fromThrowable(e);
    LOGGER.warn("Error Code : " + status.getCode() + " Description : " + status.getDescription());
    if (app.getAuthServerHost() != null && app.getAuthServerPort() != null) {
      assertEquals(Status.PERMISSION_DENIED.getCode(), status.getCode());
    } else {
      assertEquals(Status.NOT_FOUND.getCode(), status.getCode());
    }
  }

  public CreateExperimentRun getCreateExperimentRunRequestSimple(
      String projectId, String experimentId, String experimentRunName) {
    return CreateExperimentRun.newBuilder()
        .setProjectId(projectId)
        .setExperimentId(experimentId)
        .setName(experimentRunName)
        .build();
  }

  public CreateExperimentRun getCreateExperimentRunRequest(
      String projectId, String experimentId, String experimentRunName) {

    List<String> tags = new ArrayList<>();
    tags.add("Tag_x");
    tags.add("Tag_y");

    int rangeMax = 20;
    int rangeMin = 1;
    Random randomNum = new Random();

    List<KeyValue> attributeList = new ArrayList<>();
    Value intValue = Value.newBuilder().setNumberValue(1.1).build();
    attributeList.add(
        KeyValue.newBuilder()
            .setKey("attribute_" + Calendar.getInstance().getTimeInMillis())
            .setValue(intValue)
            .setValueType(ValueType.NUMBER)
            .build());
    Value stringValue =
        Value.newBuilder()
            .setStringValue("attributes_value_" + Calendar.getInstance().getTimeInMillis())
            .build();
    attributeList.add(
        KeyValue.newBuilder()
            .setKey("attribute_" + Calendar.getInstance().getTimeInMillis())
            .setValue(stringValue)
            .setValueType(ValueType.STRING)
            .build());

    double randomValue = rangeMin + (rangeMax - rangeMin) * randomNum.nextDouble();
    List<KeyValue> hyperparameters = new ArrayList<>();
    intValue = Value.newBuilder().setNumberValue(randomValue).build();
    hyperparameters.add(
        KeyValue.newBuilder()
            .setKey("tuning_" + Calendar.getInstance().getTimeInMillis())
            .setValue(intValue)
            .setValueType(ValueType.NUMBER)
            .build());
    stringValue =
        Value.newBuilder()
            .setStringValue("hyperparameters_value_" + Calendar.getInstance().getTimeInMillis())
            .build();
    hyperparameters.add(
        KeyValue.newBuilder()
            .setKey("hyperparameters_" + Calendar.getInstance().getTimeInMillis())
            .setValue(stringValue)
            .setValueType(ValueType.STRING)
            .build());

    List<Artifact> artifactList = new ArrayList<>();
    artifactList.add(
        Artifact.newBuilder()
            .setKey("Google developer Artifact")
            .setPath(
                "test/https%3A%2F%2Flh3.googleusercontent.com%2FFyZA5SbKPJA7Y3XCeb9-uGwow8pugxj77Z1xvs8vFS6EI3FABZDCDtA9ScqzHKjhU8av_Ck95ET-P_rPJCbC2v_OswCN8A%3Ds688&imgrefurl=https%3A%2F%2Fdevelopers.google.com%2F&docid=1MVaWrOPIjYeJM&tbnid=I7xZkRN5m6_z-M%3A&vet=10ahUKEwjr1OiS0ufeAhWNbX0KHXpFAmQQMwhyKAMwAw..i&w=688&h=387&bih=657&biw=1366&q=google&ved=0ahUKEwjr1OiS0ufeAhWNbX0KHXpFAmQQMwhyKAMwAw&iact=mrc&uact=8")
            .setArtifactType(ArtifactType.BLOB)
            .build());
    artifactList.add(
        Artifact.newBuilder()
            .setKey("Google Pay Artifact")
            .setPath(
                "test/https%3A%2F%2Fpay.google.com%2Fabout%2Fstatic%2Fimages%2Fsocial%2Fknowledge_graph_logo.png&imgrefurl=https%3A%2F%2Fpay.google.com%2Fabout%2F&docid=zmoE9BrSKYr4xM&tbnid=eCL1Y6f9xrPtDM%3A&vet=10ahUKEwjr1OiS0ufeAhWNbX0KHXpFAmQQMwhwKAIwAg..i&w=1200&h=630&bih=657&biw=1366&q=google&ved=0ahUKEwjr1OiS0ufeAhWNbX0KHXpFAmQQMwhwKAIwAg&iact=mrc&uact=8")
            .setArtifactType(ArtifactType.IMAGE)
            .build());

    List<Artifact> datasets = new ArrayList<>();
    datasets.add(
        Artifact.newBuilder()
            .setKey("Google developer datasets")
            .setPath("This is data artifact type in Google developer datasets")
            .setArtifactType(ArtifactType.MODEL)
            .build());
    datasets.add(
        Artifact.newBuilder()
            .setKey("Google Pay datasets")
            .setPath("This is data artifact type in Google Pay datasets")
            .setArtifactType(ArtifactType.DATA)
            .build());

    List<KeyValue> metrics = new ArrayList<>();
    randomValue = rangeMin + (rangeMax - rangeMin) * randomNum.nextDouble();
    intValue = Value.newBuilder().setNumberValue(randomValue).build();
    metrics.add(
        KeyValue.newBuilder()
            .setKey("accuracy_" + Calendar.getInstance().getTimeInMillis())
            .setValue(intValue)
            .setValueType(ValueType.NUMBER)
            .build());
    randomValue = rangeMin + (rangeMax - rangeMin) * randomNum.nextDouble();
    intValue = Value.newBuilder().setNumberValue(randomValue).build();
    metrics.add(
        KeyValue.newBuilder()
            .setKey("loss_" + Calendar.getInstance().getTimeInMillis())
            .setValue(intValue)
            .setValueType(ValueType.NUMBER)
            .build());
    randomValue = rangeMin + (rangeMax - rangeMin) * randomNum.nextDouble();
    Value listValue =
        Value.newBuilder()
            .setListValue(
                ListValue.newBuilder()
                    .addValues(intValue)
                    .addValues(Value.newBuilder().setNumberValue(randomValue).build()))
            .build();
    metrics.add(
        KeyValue.newBuilder()
            .setKey("profit_" + Calendar.getInstance().getTimeInMillis())
            .setValue(listValue)
            .setValueType(ValueType.LIST)
            .build());

    List<Observation> observations = new ArrayList<>();
    observations.add(
        Observation.newBuilder()
            .setArtifact(
                Artifact.newBuilder()
                    .setKey("Google developer Observation artifact")
                    .setPath("This is data artifact type in Google developer Observation artifact")
                    .setArtifactType(ArtifactType.DATA)
                    .build())
            .setTimestamp(Calendar.getInstance().getTimeInMillis())
            .build());
    stringValue =
        Value.newBuilder()
            .setStringValue("Observation_value_" + Calendar.getInstance().getTimeInMillis())
            .build();
    observations.add(
        Observation.newBuilder()
            .setAttribute(
                KeyValue.newBuilder()
                    .setKey("Observation Key " + Calendar.getInstance().getTimeInMillis())
                    .setValue(stringValue)
                    .setValueType(ValueType.STRING))
            .setTimestamp(Calendar.getInstance().getTimeInMillis())
            .build());

    List<Feature> features = new ArrayList<>();
    features.add(Feature.newBuilder().setName("ExperimentRun Test case feature 1").build());
    features.add(Feature.newBuilder().setName("ExperimentRun Test case feature 2").build());

    return CreateExperimentRun.newBuilder()
        .setProjectId(projectId)
        .setExperimentId(experimentId)
        .setName(experimentRunName)
        .setDescription("this is a ExperimentRun description")
        .setDateCreated(Calendar.getInstance().getTimeInMillis())
        .setDateUpdated(Calendar.getInstance().getTimeInMillis())
        .setStartTime(Calendar.getInstance().getTime().getTime())
        .setEndTime(Calendar.getInstance().getTime().getTime())
        .setCodeVersion("1.0")
        .addAllTags(tags)
        .addAllAttributes(attributeList)
        .addAllHyperparameters(hyperparameters)
        .addAllArtifacts(artifactList)
        .addAllDatasets(datasets)
        .addAllMetrics(metrics)
        .addAllObservations(observations)
        .addAllFeatures(features)
        .build();
  }

  @Test
  public void x_getURLForArtifact() throws IOException {
    LOGGER.info("Get Url for Artifact test start................................");
    ProjectTest projectTest = new ProjectTest();
    ExperimentTest experimentTest = new ExperimentTest();

    ProjectServiceBlockingStub projectServiceStub = ProjectServiceGrpc.newBlockingStub(channel);
    ExperimentServiceBlockingStub experimentServiceStub =
        ExperimentServiceGrpc.newBlockingStub(channel);
    ExperimentRunServiceBlockingStub experimentRunServiceStub =
        ExperimentRunServiceGrpc.newBlockingStub(channel);

    // Create project
    CreateProject createProjectRequest =
        projectTest.getCreateProjectRequest("experimentRun_project_ypcd1");
    CreateProject.Response createProjectResponse =
        projectServiceStub.createProject(createProjectRequest);
    Project project = createProjectResponse.getProject();
    LOGGER.info("Project created successfully");
    try {
      // Create two experiment of above project
      CreateExperiment createExperimentRequest =
          experimentTest.getCreateExperimentRequest(project.getId(), "Experiment_zys");
      CreateExperiment.Response createExperimentResponse =
          experimentServiceStub.createExperiment(createExperimentRequest);
      Experiment experiment = createExperimentResponse.getExperiment();
      LOGGER.info("Experiment created successfully");

      CreateExperimentRun createExperimentRunRequest =
          getCreateExperimentRunRequest(project.getId(), experiment.getId(), "ExperimentRun_zys");
      CreateExperimentRun.Response createExperimentRunResponse =
          experimentRunServiceStub.createExperimentRun(createExperimentRunRequest);
      ExperimentRun experimentRun = createExperimentRunResponse.getExperimentRun();
      Artifact artifact = experimentRun.getArtifacts(0);

      GetUrlForArtifact getUrlForArtifact =
          GetUrlForArtifact.newBuilder()
              .setId(experimentRun.getId())
              .setKey(artifact.getKey())
              .setMethod("put")
              .setPartNumber(1)
              .build();
      GetUrlForArtifact.Response getUrlForArtifactResponse =
          experimentRunServiceStub.getUrlForArtifact(getUrlForArtifact);
      String url = getUrlForArtifactResponse.getUrl();

      getUrlForArtifact =
          GetUrlForArtifact.newBuilder()
              .setId(experimentRun.getId())
              .setKey(artifact.getKey())
              .setMethod("put")
              .setPartNumber(2)
              .build();
      getUrlForArtifactResponse = experimentRunServiceStub.getUrlForArtifact(getUrlForArtifact);
      String url2 = getUrlForArtifactResponse.getUrl();

      // Create the connection and use it to upload the new object using the pre-signed URL.
      HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
      connection.setDoOutput(true);
      connection.setRequestMethod("PUT");
      OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
      for (int i = 0; i < 130000; ++i)
        out.write("This text uploaded as an object via presigned URL.");
      out.close();

      // Check the HTTP response code. To complete the upload and make the object available,
      // you must interact with the connection object in some way.
      connection.getResponseCode();
      String etag1 = connection.getHeaderField("ETag");
      connection = (HttpURLConnection) new URL(url2).openConnection();
      connection.setDoOutput(true);
      connection.setRequestMethod("PUT");
      out = new OutputStreamWriter(connection.getOutputStream());
      for (int i = 0; i < 120000; ++i)
        out.write("This text uploaded as an object via presigned URL2.");
      out.close();

      // Check the HTTP response code. To complete the upload and make the object available,
      // you must interact with the connection object in some way.
      connection.getResponseCode();
      String etag2 = connection.getHeaderField("ETag");

      Response p1 =
          experimentRunServiceStub.commitArtifactPart(
              CommitArtifactPart.newBuilder()
                  .setId(experimentRun.getId())
                  .setKey(artifact.getKey())
                  .setArtifactPart(
                      ArtifactPart.newBuilder()
                          .setEtag(etag1.replaceAll("\"", ""))
                          .setPartNumber(1))
                  .build());
      Response p2 =
          experimentRunServiceStub.commitArtifactPart(
              CommitArtifactPart.newBuilder()
                  .setId(experimentRun.getId())
                  .setKey(artifact.getKey())
                  .setArtifactPart(
                      ArtifactPart.newBuilder()
                          .setEtag(etag2.replaceAll("\"", ""))
                          .setPartNumber(2))
                  .build());
      GetCommittedArtifactParts.Response committedArtifactParts =
          experimentRunServiceStub.getCommittedArtifactParts(
              GetCommittedArtifactParts.newBuilder()
                  .setId(experimentRun.getId())
                  .setKey(artifact.getKey())
                  .build());
      CommitMultipartArtifact.Response commitMultipartArtifact =
          experimentRunServiceStub.commitMultipartArtifact(
              CommitMultipartArtifact.newBuilder()
                  .setId(experimentRun.getId())
                  .setKey(artifact.getKey())
                  .build());
      GetCommittedArtifactParts.Response committedArtifactParts1 =
          experimentRunServiceStub.getCommittedArtifactParts(
              GetCommittedArtifactParts.newBuilder()
                  .setId(experimentRun.getId())
                  .setKey(artifact.getKey())
                  .build());
      GetCommittedArtifactParts.Response committedArtifactParts2 =
          experimentRunServiceStub.getCommittedArtifactParts(
              GetCommittedArtifactParts.newBuilder()
                  .setId(experimentRun.getId())
                  .setKey(artifact.getKey())
                  .build());
    } finally {
      DeleteProject deleteProject = DeleteProject.newBuilder().setId(project.getId()).build();
      DeleteProject.Response deleteProjectResponse =
          projectServiceStub.deleteProject(deleteProject);
      LOGGER.info(deleteProjectResponse.toString());
      assertTrue(deleteProjectResponse.getStatus());

      LOGGER.info("Get Url for Artifact test stop................................");
    }
  }
}
