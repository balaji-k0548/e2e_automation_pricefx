package com.syscolab.qe.core.util.qcenter;

import io.restassured.response.Response;
import com.syscolab.qe.core.api.jackson.JacksonUtil;
import com.syscolab.qe.core.api.restassured.RestUtil;
import com.syscolab.qe.core.api.util.Headers;
import com.syscolab.qe.core.api.util.RequestMethods;
import com.syscolab.qe.core.common.LoggerUtil;
import com.syscolab.qe.core.common.SyscoLabCoreConstants;
import com.syscolab.qe.core.data.BuildData;
import com.syscolab.qe.core.reporting.QmetryHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Objects;

import static com.syscolab.qe.core.common.Constants.*;

/**
 * QCenterUtil class acts as the util class to generate builds in QCenter QLytics Dashboard
 * @author tqe-platform team
 * @see com.syscolab.qe.core.common.Constants
 */
public class QCenterUtil {

    public static String projectName = System.getProperty("test.project");
    public static String environmentName = System.getProperty("test.env");
    public static String buildName = System.getProperty("test.release");
    private static String PATH = System.getProperty("user.dir") + "/Ill-FormattedTestCaseTitles.txt";
    private static final String BUILDS = "builds";
    private static final String U001B = "\u001B[31m";
    private static final String RELEASE_INTERNAL_NAME = "release.internal.name";

    /**
     * This will generate a custom build in qCenter with the passed buildName
     * @param projectName     qCenter project name
     * @param environmentName qCenter environment name
     * @param buildName       custom build name
     */
    public static void generateBuildOld(String projectName, String environmentName, String buildName) {
        RestUtil.BASE_PATH = "";
        RestUtil.API_HOST = SyscoLabCoreConstants.SYSCO_QCENTER_API_HOST;
        RestUtil.PORT = SyscoLabCoreConstants.SYSCO_QCENTER_API_PORT;
        BuildData buildData = new BuildData();
        try {
            buildData.project_id = Integer.parseInt(getProjectID(projectName));
            buildData.environment_id = getEnvID(buildData.project_id, environmentName);
            buildData.cake_release = buildName;
            RestUtil.send(Headers.getAuthorizationHeader(SyscoLabCoreConstants.SYSCO_QCENTER_TOKEN), JacksonUtil.getAsString(buildData), BUILDS, RequestMethods.POST.toString(), null);
        } catch (JSONException e) {
            LoggerUtil.logINFO(U001B + "ERROR CREATING BUILD");
        }
    }


    /**
     * This will Generate a Build in QCenter depending on the option Daily, Weekly, Qmetry Release build or a custom build
     * @param projectName     qCenter project name
     * @param environmentName qCenter environment name
     * @param buildName       custom build name
     * @throws JSONException throwing JSONException
     */

    public static String generateBuild(String projectName, String environmentName, String buildName) throws JSONException {

        File file = new File(PATH);
        try {
            Files.deleteIfExists(file.toPath()); //surround it in try catch block
        } catch (Exception e) {
            e.printStackTrace();
        }

        //check dashboard update true
        if (UPDATE_DASHBOARD) {
            //check if QLYTICS_SPRINT_RELEASE and QLYTICS_DAILY_WEEKLY_BUILD fields Not Empty and not Null
            if (QLYTICS_DAILY_WEEKLY_BUILD.isEmpty() || QLYTICS_DAILY_WEEKLY_BUILD == null) {
                LoggerUtil.logINFO(U001B + "PLEASE PROVIDE NECESSARY VALUES FOR QLYTICS_SPRINT_RELEASE AND QLYTICS_DAILY_WEEKLY_BUILD FIELDS ");
                throw new RuntimeException("Provide necessary values for QLYTICS_SPRINT_RELEASE and QLYTICS_DAILY_WEEKLY_BUILD fields");
            } else {
                BuildData buildData = new BuildData();
                LocalDateTime now = LocalDateTime.now();
                //Check if project name is Empty or unavailable
                if (projectName.isEmpty()) {
                    throw new RuntimeException("Project Name is Empty. Please add the Project Name");
                } else if (Objects.requireNonNull(getProjectDetails(projectName)).getString("name").isEmpty()) {
                    throw new RuntimeException("Project Name doesn't exist. Please add an existing Project Name");
                } else {
                    JSONObject jsonObject = getProjectDetails(projectName);
                    try {
                        buildData.project_id = Objects.requireNonNull(jsonObject).getInt("id");
                    } catch (RuntimeException e) {
                        LoggerUtil.logINFO(e.getMessage());
                    }
                    //Check if environment name is Empty or unavailable
                    if (environmentName.isEmpty()) {
                        throw new RuntimeException("Environment Name is Empty. Please add the Environment Name");
                    } else if (getEnvID(buildData.project_id, environmentName).isEmpty()) {
                        throw new RuntimeException("Environment Name doesn't exist. Please add an existing Environment Name");
                    } else {
                        try {
                            buildData.environment_id = getEnvID(buildData.project_id, environmentName);
                        } catch (RuntimeException e) {
                            LoggerUtil.logINFO(e.getMessage());
                        }
                        if (QLYTICS_QMETRY_RELEASE) {
                            createqMetryReleaseBuild(buildName, buildData);
                        } else if (QLYTICS_DAILY_WEEKLY_BUILD.equalsIgnoreCase("weekly")) {
                            if (jsonObject != null) {
                                buildData.cake_release = createWeeklyBuild(jsonObject.getString("code"), now);
                                System.setProperty(RELEASE_INTERNAL_NAME, buildData.cake_release);
                            }
                        } else if (QLYTICS_DAILY_WEEKLY_BUILD.equalsIgnoreCase("daily")) {
                            if (jsonObject != null) {
                                buildData.cake_release = createDailyBuild(jsonObject.getString("code"), now);
                                System.setProperty(RELEASE_INTERNAL_NAME, buildData.cake_release);
                            }
                        } else if (QLYTICS_DAILY_WEEKLY_BUILD.equalsIgnoreCase("bvt")) {
                            if (jsonObject != null) {
                                buildData.cake_release = createBVTBuild(jsonObject.getString("code"), now);
                                System.setProperty(RELEASE_INTERNAL_NAME, buildData.cake_release);
                            }
                        } else {
                            buildData.cake_release = buildName;
                            System.setProperty(RELEASE_INTERNAL_NAME, buildData.cake_release);
                        }
                        RestUtil.BASE_PATH = "";
                        RestUtil.API_HOST = SyscoLabCoreConstants.SYSCO_QCENTER_API_HOST;
                        RestUtil.PORT = SyscoLabCoreConstants.SYSCO_QCENTER_API_PORT;
                        RestUtil.send(Headers.getAuthorizationHeader(SyscoLabCoreConstants.SYSCO_QCENTER_TOKEN), JacksonUtil.getAsString(buildData), BUILDS, RequestMethods.POST.toString(), null);
                    }
                }
            }
        } else {
            LoggerUtil.logINFO(U001B + "A Custom build won't be created");
        }
        return System.getProperty(RELEASE_INTERNAL_NAME);
    }

    /**
     * Return the BVT Build Name in the format of "projectCode-BVT-MM-dd-yyyy hh:mm a"
     * @param projectCode qCenter Project Code
     * @param now         current date-time from the system clock in the default time-zone
     * @return BVT build name
     */
    private static String createBVTBuild(String projectCode, LocalDateTime now) {
        DateTimeFormatter dtfDate = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        return projectCode + "-BVT-" + dtfDate.format(now);
    }

    /**
     * Return the Weekly Build Name in the format of "projectCode-Weekly-weekNumber"
     * @param projectCode qCenter Project Code
     * @param now         current date-time from the system clock in the default time-zone
     * @return weekly build name
     */
    private static String createWeeklyBuild(String projectCode, LocalDateTime now) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekNumber = now.get(weekFields.weekOfWeekBasedYear());
        return projectCode + "-Weekly-" + weekNumber;
    }

    /**
     * Return the Weekly Build Name in the format of "projectCode-DNB-MM-dd-yyyy hh:mm a"
     * @param projectCode qCenter Project Code
     * @param now         current date-time from the system clock in the default time-zone
     * @return daily build name
     */

    private static String createDailyBuild(String projectCode, LocalDateTime now) {
        DateTimeFormatter dtfDate = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        //will return date + timestamp
        //DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("hh:mm a");
        //return projectCode + "-DNB-" + dtfDate.format(now) + " " + dtfTime.format(now);
        //will return date only
        return projectCode + "-DNB-" + dtfDate.format(now);
    }

    /**
     * Create QMetry Release build in the format "buildName-qmetrySummary"
     * @param buildName qMetry Test Run ID
     * @param buildData buildData object
     * @throws JSONException
     * @exception  Exception
     */
    private static void createqMetryReleaseBuild(String buildName, BuildData buildData) throws JSONException {
        Response response = (Response) QmetryHelper.getQMetryTestRunDetails(buildName);
        JSONObject jsonObjectQmetryResults = new JSONObject(response.asString());
        String testRunNotFound = "Test Run Not Found. Please create the Test Run in QMetry";
        try {
            JSONObject jsonObjectProject = jsonObjectQmetryResults.getJSONObject("data");
            String summary = jsonObjectProject.get("summary").toString();

            if (!(summary.isEmpty())) {
                buildData.cake_release = buildName + " - " + summary;
                System.setProperty(RELEASE_INTERNAL_NAME, buildData.cake_release);
                System.setProperty("qmetry.test.internal.key", buildName);
            } else {
                LoggerUtil.logINFO(testRunNotFound);
                throw new RuntimeException(testRunNotFound);
            }
        } catch (Exception e) {
            LoggerUtil.logINFO(testRunNotFound);
            throw new RuntimeException(testRunNotFound);
        }

    }

    /**
     * This is the main method
     * @param args stores the incoming arguments
     * @throws JSONException throwing JSONException
     */
    public static void main(String[] args) throws JSONException {
        BuildData buildData = new BuildData();
        buildData.project_id = Integer.parseInt(getProjectID(System.getProperty("test.project")));
        buildData.environment_id = getEnvID(buildData.project_id, System.getProperty("test.env"));
        buildData.cake_release = System.getProperty("test.build");
        RestUtil.send(Headers.getAuthorizationHeader(SyscoLabCoreConstants.SYSCO_QCENTER_TOKEN), JacksonUtil.getAsString(buildData), BUILDS, RequestMethods.POST.toString(), null);
    }

    /**
     * Returns the projectID from the qCenter
     * @param projectName project name of the qCenter
     * @return projectID from qCenter
     * @throws JSONException throwing JSONException
     */
    public static String getProjectID(String projectName) throws JSONException {
        RestUtil.BASE_PATH = "";
        RestUtil.API_HOST = SyscoLabCoreConstants.SYSCO_QCENTER_API_HOST;
        RestUtil.PORT = SyscoLabCoreConstants.SYSCO_QCENTER_API_PORT;
        Response response = RestUtil.send(Headers.getAuthorizationHeader(SyscoLabCoreConstants.SYSCO_QCENTER_TOKEN), "", "projects", "GET");
        JSONArray jsonArrayProjects = new JSONArray(response.asString());
        for (int i = 0; i < jsonArrayProjects.length(); i++) {
            JSONObject jsonObjectProject = jsonArrayProjects.getJSONObject(i);
            if (jsonObjectProject.get("name").toString().trim().equals(projectName)) {
                return jsonObjectProject.get("id").toString();
            }
        }
        LoggerUtil.logINFO(U001B + "Project not Found");
        return "Project Name Not Found";
    }

    /**
     * Return project details relevant to the passed project name
     * @param projectName project name of the QCenter
     * @return project details
     * @throws JSONException throwing JSONException
     */
    public static JSONObject getProjectDetails(String projectName) throws JSONException {
        if (projectName.isEmpty() || projectName == null) {
            throw new RuntimeException("The project name cannot be Empty");
        } else {
            //this method should handle Charitha's new project api, and it will either return a new object or an empty array. Update logic accordingly.
            RestUtil.BASE_PATH = "";
            RestUtil.API_HOST = SyscoLabCoreConstants.SYSCO_QCENTER_API_HOST;
            RestUtil.PORT = SyscoLabCoreConstants.SYSCO_QCENTER_API_PORT;
            Response response = RestUtil.send(Headers.getAuthorizationHeader(SyscoLabCoreConstants.SYSCO_QCENTER_TOKEN), "", "projects/name/" + projectName, "GET");
            if (!response.asString().isEmpty()) {
                JSONObject jsonObject = new JSONObject(response.asString());
                if (jsonObject.getString("name").trim().equalsIgnoreCase(projectName)) {
                    return jsonObject;
                }
            } else {
                LoggerUtil.logINFO(U001B + "Project not Found");
                throw new RuntimeException("Project not Found. Please create the Project");
            }
        }
        return null;
    }

    /**
     * Return Environment ID relevant to the passed project id and environment name
     * @param projectID       project id of qCenter
     * @param environmentName environment name of the QCenter
     * @return environment ID
     * @throws JSONException throwing JSONException
     */

    public static String getEnvID(int projectID, String environmentName) throws JSONException {
        if (environmentName.isEmpty() || environmentName == null) {
            throw new RuntimeException("The environment name cannot be Empty");
        } else {
            RestUtil.BASE_PATH = "";
            RestUtil.API_HOST = SyscoLabCoreConstants.SYSCO_QCENTER_API_HOST;
            RestUtil.PORT = SyscoLabCoreConstants.SYSCO_QCENTER_API_PORT;
            Response response = RestUtil.send(Headers.getAuthorizationHeader(SyscoLabCoreConstants.SYSCO_QCENTER_TOKEN), "", "environments/project/" + projectID, "GET");
            JSONArray jsonArrayEnvs = new JSONArray(response.asString());
            for (int i = 0; i < jsonArrayEnvs.length(); i++) {
                JSONObject jsonObjectProject = jsonArrayEnvs.getJSONObject(i);
                if (environmentName.equalsIgnoreCase(jsonObjectProject.get("name").toString().trim())) {
                    return jsonObjectProject.get("id").toString();
                }
            }
            LoggerUtil.logINFO(U001B + "Environment not Found");
            return "";
        }

    }

    /**
     * This will generate an API call and return all the Test cases related to a project name, environment name and build name
     * @param projectName throwing projectName
     * @param environmentName throwing environmentName
     * @param buildName throwing buildName
     * @return test cases of a particular build
     */
    public static Response getQCenterProjects(String projectName, String environmentName, String buildName) {
        RestUtil.BASE_PATH = "";
        RestUtil.API_HOST = SyscoLabCoreConstants.SYSCO_QCENTER_API_HOST;
        RestUtil.PORT = SyscoLabCoreConstants.SYSCO_QCENTER_API_PORT;
        return RestUtil.send(Headers.getAuthorizationHeader(SyscoLabCoreConstants.SYSCO_QCENTER_TOKEN), "", "TestCases/projectName/" + projectName + "/envName/" + environmentName + "/buildName/" + buildName, RequestMethods.GET.toString(), null);
    }

    /**
     * This will generate an API call and return all the builds
     * @return builds
     */
    public static Response getQCenterBuilds() {
        RestUtil.BASE_PATH = "";
        RestUtil.API_HOST = SyscoLabCoreConstants.SYSCO_QCENTER_API_HOST;
        RestUtil.PORT = SyscoLabCoreConstants.SYSCO_QCENTER_API_PORT;
        return RestUtil.send(Headers.getAuthorizationHeader(SyscoLabCoreConstants.SYSCO_QCENTER_TOKEN), "", BUILDS, "GET");
    }

    /**
     * This will return the build details related to a particular build id
     * @param buildID build id of qCenter
     * @return build details
     */
    public static Response getBuildDetails(String buildID) {
        RestUtil.BASE_PATH = "";
        RestUtil.API_HOST = SyscoLabCoreConstants.SYSCO_QCENTER_API_HOST;
        RestUtil.PORT = SyscoLabCoreConstants.SYSCO_QCENTER_API_PORT;
        return RestUtil.send(Headers.getAuthorizationHeader(SyscoLabCoreConstants.SYSCO_QCENTER_TOKEN), "", "builds/" + buildID, "GET");
    }

//**DELETE START**//*
    /*public static void testingQMETRYCalls() throws Exception {
        QMetryImportAutomationResultsData qMetryImportAutomationResultsData = new QMetryImportAutomationResultsData();
         //QMetryREST.getTestCaseDetailsByVersionOfQMetry(qMetryImportAutomationResultsData);
        //QMetryREST.getTestCaseCustomFieldsByProject();
        QMetryREST.getUploadFileURL();
    }*/
    //**DELETE END**/
}
