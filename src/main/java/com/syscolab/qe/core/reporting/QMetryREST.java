package com.syscolab.qe.core.reporting;

import com.syscolab.qe.core.data.QMetryNewTestCycleData;
import io.restassured.response.Response;
import com.syscolab.qe.core.api.jackson.JacksonUtil;
import com.syscolab.qe.core.api.restassured.RestUtil;
import com.syscolab.qe.core.api.util.Headers;
import com.syscolab.qe.core.api.util.RequestMethods;
import com.syscolab.qe.core.common.LoggerUtil;
import com.syscolab.qe.core.data.QMetryImportAutomationResultsData;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.ITestContext;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.syscolab.qe.core.common.Constants.QLYTICS_QMETRY_RELEASE;
import static com.syscolab.qe.core.common.Constants.UPDATE_DASHBOARD;

/**
 * This is the class for qMetry REST
 * @author Sandeep Perera
 */
public class QMetryREST {

    private static String urlToUploadFile = null;
    private static String testngResultsFilePath = System.getProperty("user.dir", ".") + "/qmetry.xml";

    private static final String QTMCLOUD_QMETRY_URL = "https://qtmcloud.qmetry.com/";
    private static final String QMETRY_PROJECT_KEY = "qmetry.project.key";
    private static final String QMETRY_OPEN_API_KEY = "qmetry.open.api.key";
    private static final String SUMMARY = "summary";
    private static final String PLEASE_CHECK_THE_CORRECT_QMETRY_ID_TEST_CASE_VERSION_AND_OPEN_API_KEY_PASSED_IN_CONSTANTS = "Please check whether the correct QMetry ID, QMetry Test Case version and QMetry Open API Key is passed in Constants";
    private static final String QMETRY_CUSTOM_FIELDS_LIST = "qmetry.custom.fields.list";

    /**
     * This will create test result xml, create upload file url and upload TestNG results file
     * @param iTestContext iTestContext
     * @throws Exception throwing Exception
     */
    public static void updateQMetry(ITestContext iTestContext) throws Exception {
        QMetryResultsAdapterREST.createTestResultXML(iTestContext);
        getUploadFileURL();
        uploadTestNGResultsFile();
    }

    /**
     * This will get the import automation results related Upload file URL
     * @throws Exception throwing Exception
     */
    public static void getUploadFileURL() throws Exception {
        RestUtil.API_HOST = QTMCLOUD_QMETRY_URL;
        JSONObject body = new JSONObject();
        body = new JSONObject(System.getProperty("qmetry.automation.data.body"));
        System.out.println("BODY: "+body);
        RestUtil.BASE_PATH = "rest/api/automation/importresult";
        try{
            Response response = RestUtil.send(Headers.getHeaderWithApiKey(System.getProperty("qmetry.api.key")), body.toString(), "", RequestMethods.POST.toString());
            urlToUploadFile = RestUtil.getValue(response, "url");
            if(urlToUploadFile.isEmpty()){
                throw new RuntimeException("Please check whether the correct Automation API key is passed in Constants");
            }
        }catch (Exception e){
            throw new RuntimeException("Please check whether the correct Automation API key is passed in Constants");
        }

    }

    /**
     * This will create a new Test Cycle and return the Test Cycle ID
     * @return Test Cycle ID
     * @throws Exception throwing Exception
     */
    public static String createNewTestCycle() throws Exception {
        RestUtil.API_HOST = QTMCLOUD_QMETRY_URL;
        QMetryNewTestCycleData qMetryNewTestCycleData = new QMetryNewTestCycleData();
        qMetryNewTestCycleData.projectId = System.getProperty(QMETRY_PROJECT_KEY);
        RestUtil.BASE_PATH = "rest/api/latest/testcycles";
        try{
            Response response = RestUtil.send(Headers.getHeaderWithApiKey(System.getProperty(QMETRY_OPEN_API_KEY)), JacksonUtil.getAsString(qMetryNewTestCycleData), "", RequestMethods.POST.toString());
            return RestUtil.getValue(response, "key");
        }catch (Exception e){
            throw new RuntimeException("Please check whether the correct Open API key is passed in Constants");
        }

    }

    /**
     * This will search for the project code, and it will set property the value
     * @throws JSONException throwing JSONException
     */
    public static void getProjectCode() throws JSONException {
        RestUtil.API_HOST = QTMCLOUD_QMETRY_URL;
        RestUtil.BASE_PATH = "rest/api/latest/projects";
        Response response = RestUtil.send(Headers.getHeaderWithApiKey(System.getProperty(QMETRY_OPEN_API_KEY)), "", "", RequestMethods.GET.toString(), null);
        JSONObject jsonObjectQmetryResults = new JSONObject(response.asString());
        try {
            for (int i = 0; i < jsonObjectQmetryResults.getJSONArray("data").length(); i++) {
                JSONObject jsonObjectProject = jsonObjectQmetryResults.getJSONArray("data").getJSONObject(i);
                if (jsonObjectProject.get("key").toString().trim().equals(System.getProperty("qmetry.project.code"))) {
                    LoggerUtil.logINFO("ID" + jsonObjectProject.get("id").toString());
                    System.setProperty(QMETRY_PROJECT_KEY,jsonObjectProject.get("id").toString());
                }

            }
            if(System.getProperty(QMETRY_PROJECT_KEY) == null){
                throw new RuntimeException("Please check whether the correct Project Code is passed in Constants");
            }
        } catch (Exception e) {
            throw new RuntimeException("Please check whether the correct Project Code and QMetry Open API key is passed in Constants");
        }
    }

    /**
     * This will set Property Project code and set property custom fields
     * @throws Exception throwing Exception
     */
    public static void setProjectCodeAndSetCustomFieldsList() throws Exception {
        getProjectCode();
        getTestCaseCustomFieldsByProject();
    }

    /**
     * This will get the test case QMetry Summary from the passed qMetry ID in system variable
     * @return qMetry Summary of QMetry ID
     * @throws JSONException throwing JSONException
     */
    public static String getQMetrySummaryOfQMetryID() throws JSONException {
        RestUtil.API_HOST = QTMCLOUD_QMETRY_URL;
        RestUtil.BASE_PATH = "rest/api/latest/testcases/";
        Response response = RestUtil.send(Headers.getHeaderWithApiKey(System.getProperty(QMETRY_OPEN_API_KEY)), "", System.getProperty("qmetry.test.id") + "/versions/" + System.getProperty("qmetry.tc.version"), RequestMethods.GET.toString(), null);
        JSONObject jsonObjectQmetryResults = new JSONObject(response.asString());
        try {
            LoggerUtil.logINFO((jsonObjectQmetryResults.getJSONObject("data").getString(SUMMARY)) + " : QMetry Summary");
            if (!(jsonObjectQmetryResults.getJSONObject("data").getString(SUMMARY).isEmpty())) {
                return (jsonObjectQmetryResults.getJSONObject("data").getString(SUMMARY));
            } else {
                throw new RuntimeException(PLEASE_CHECK_THE_CORRECT_QMETRY_ID_TEST_CASE_VERSION_AND_OPEN_API_KEY_PASSED_IN_CONSTANTS);
            }
        } catch (Exception e) {
            throw new RuntimeException(PLEASE_CHECK_THE_CORRECT_QMETRY_ID_TEST_CASE_VERSION_AND_OPEN_API_KEY_PASSED_IN_CONSTANTS);
        }
    }

    /**
     * This will Get the Test Case Details by Version Of Qmetry and set property the body of the qMetry Automation Result to be passed in the upload File endpoint
     * and set property qmetry summary
     * @param qMetryImportAutomationResultsData qMetry Import Automation results related data class object
     * @throws Exception throwing Exception
     */
    public static void getTestCaseDetailsByVersionOfQMetry(QMetryImportAutomationResultsData qMetryImportAutomationResultsData) throws Exception {
        RestUtil.API_HOST = QTMCLOUD_QMETRY_URL;
        RestUtil.BASE_PATH = "rest/api/latest/testcases/";
        Response response = RestUtil.send(Headers.getHeaderWithApiKey(System.getProperty(QMETRY_OPEN_API_KEY)), "", System.getProperty("qmetry.test.id") + "/versions/" + System.getProperty("qmetry.tc.version") + "/?fields=description,sprint,components,estimatedTime,fixVersions,labels,reporter," + System.getProperty(QMETRY_CUSTOM_FIELDS_LIST), RequestMethods.GET.toString(), null);
        JSONObject jsonObjectQmetryResults = new JSONObject(response.asString());
        try {
            qMetryImportAutomationResultsData.testCase = new JSONObject();
            qMetryImportAutomationResultsData.fields = new JSONObject();
            qMetryImportAutomationResultsData.body = new JSONObject();
            //set property - summary
            setSummary(jsonObjectQmetryResults);
            //set format
            qMetryImportAutomationResultsData.format = "testng";
            qMetryImportAutomationResultsData.body.put("format", qMetryImportAutomationResultsData.format);
            //set testCycleToReUse
            getTestCycleToReUse(qMetryImportAutomationResultsData);
            //Get Labels into a String Array
            getLabelsToAStringArray(jsonObjectQmetryResults, qMetryImportAutomationResultsData);
            //Get Description
            getDescription(jsonObjectQmetryResults, qMetryImportAutomationResultsData);
            //Get Priority
            getPriority(jsonObjectQmetryResults, qMetryImportAutomationResultsData);
            //Get Fix Version Id
            getFixVersionID(jsonObjectQmetryResults, qMetryImportAutomationResultsData);
            //Get assignee
            getAssignee(jsonObjectQmetryResults, qMetryImportAutomationResultsData);
            //Get reporter
            getReporter(jsonObjectQmetryResults, qMetryImportAutomationResultsData);
            //Get status
            getStatus(jsonObjectQmetryResults, qMetryImportAutomationResultsData);
            //Get Components into a String Array
            getComponentsToAStringArray(jsonObjectQmetryResults, qMetryImportAutomationResultsData);
            //Get Sprint Id
            getSprintID(jsonObjectQmetryResults, qMetryImportAutomationResultsData);
            //Get Custom Field
            if (!System.getProperty(QMETRY_CUSTOM_FIELDS_LIST).isEmpty()) {
                getCustomFieldsToAStringArray(jsonObjectQmetryResults, qMetryImportAutomationResultsData);
            }
            qMetryImportAutomationResultsData.fields.put("testCase", qMetryImportAutomationResultsData.testCase);
            qMetryImportAutomationResultsData.body.put("fields", qMetryImportAutomationResultsData.fields);
            System.setProperty("qmetry.automation.data.body", String.valueOf(qMetryImportAutomationResultsData.body));
        }catch (Exception e){
            throw new RuntimeException("\"Please check whether the correct QMetry ID, QMetry Test Case version and QMetry Open API Key is passed in Constants\"");
        }

    }

    /**
     * This will Get Test Cycle to reuse
     * @param qMetryImportAutomationResultsData qMetry Import Automation results related data class object
     * @throws JSONException throwing JSONException
     */
    public static void getTestCycleToReUse(QMetryImportAutomationResultsData qMetryImportAutomationResultsData) throws JSONException {
        String cycleToReuse = "testCycleToReuse";
        try {
            if (QLYTICS_QMETRY_RELEASE && UPDATE_DASHBOARD) {
                qMetryImportAutomationResultsData.testCycleToReuse = System.getProperty("qmetry.test.internal.key");
                qMetryImportAutomationResultsData.body.put(cycleToReuse, qMetryImportAutomationResultsData.testCycleToReuse);
            }
            qMetryImportAutomationResultsData.testCycleToReuse = System.getProperty("qmetry.test.cycle.id");
            qMetryImportAutomationResultsData.body.put(cycleToReuse, qMetryImportAutomationResultsData.testCycleToReuse);
        } catch (Exception e) {
            qMetryImportAutomationResultsData.testCycleToReuse = "";
            qMetryImportAutomationResultsData.body.put(cycleToReuse, qMetryImportAutomationResultsData.testCycleToReuse);
        }
    }

    /**
     * This will Get Description for Qmetry test case
     * @param jsonObjectQmetryResults results object
     * @param qMetryImportAutomationResultsData qMetry Import Automation results related data class object
     * @throws JSONException throwing JSONException
     */
    public static void getDescription(JSONObject jsonObjectQmetryResults, QMetryImportAutomationResultsData qMetryImportAutomationResultsData) throws JSONException {
        String des = "description";
        try {
            qMetryImportAutomationResultsData.description = jsonObjectQmetryResults.getJSONObject("data").getString(des);
            qMetryImportAutomationResultsData.testCase.put(des, qMetryImportAutomationResultsData.description);
        } catch (Exception e) {
            qMetryImportAutomationResultsData.description = "";
            qMetryImportAutomationResultsData.testCase.put(des, qMetryImportAutomationResultsData.description);
        }
    }

    /**
     * This will Set Summary - setProperty for the QMetry Test case
     * @param jsonObjectQmetryResults results object
     */
    public static void setSummary(JSONObject jsonObjectQmetryResults) {
        try {
            LoggerUtil.logINFO((jsonObjectQmetryResults.getJSONObject("data").getString(SUMMARY)) + " : QMetry Summary");
            if (!(jsonObjectQmetryResults.getJSONObject("data").getString(SUMMARY).isEmpty())) {
                System.setProperty("qmetry.test.name",jsonObjectQmetryResults.getJSONObject("data").getString(SUMMARY));
            } else {
                throw new RuntimeException(PLEASE_CHECK_THE_CORRECT_QMETRY_ID_TEST_CASE_VERSION_AND_OPEN_API_KEY_PASSED_IN_CONSTANTS);
            }
        } catch (Exception e) {
            throw new RuntimeException(PLEASE_CHECK_THE_CORRECT_QMETRY_ID_TEST_CASE_VERSION_AND_OPEN_API_KEY_PASSED_IN_CONSTANTS);
        }
    }

    /**
     * This will Get Priority of the QMetry Test case
     * @param jsonObjectQmetryResults results object
     * @param qMetryImportAutomationResultsData qMetry Import Automation results related data class object
     * @throws JSONException throwing JSONException
     */
    public static void getPriority(JSONObject jsonObjectQmetryResults, QMetryImportAutomationResultsData qMetryImportAutomationResultsData) throws JSONException {
        String priorityTxt = "priority";
        try {
            qMetryImportAutomationResultsData.priority = jsonObjectQmetryResults.getJSONObject("data").getJSONObject("priority").getString("name");
            qMetryImportAutomationResultsData.testCase.put(priorityTxt, qMetryImportAutomationResultsData.priority);
        } catch (Exception e) {
            qMetryImportAutomationResultsData.priority = "";
            qMetryImportAutomationResultsData.testCase.put(priorityTxt, qMetryImportAutomationResultsData.priority);
        }
    }

    /**
     * This will Get FIx Version ID for the QMetry Test case
     * @param jsonObjectQmetryResults results object
     * @param qMetryImportAutomationResultsData qMetry Import Automation results related data class object
     * @throws JSONException throwing JSONException
     */
    public static void getFixVersionID(JSONObject jsonObjectQmetryResults, QMetryImportAutomationResultsData qMetryImportAutomationResultsData) throws JSONException {
        try {
            qMetryImportAutomationResultsData.fixVersionId = (jsonObjectQmetryResults.getJSONObject("data").getJSONArray("fixVersions").getJSONObject(0).getString("id"));
            qMetryImportAutomationResultsData.testCase.put("fixVersionId", qMetryImportAutomationResultsData.fixVersionId);
        } catch (Exception e) {
            qMetryImportAutomationResultsData.fixVersionId = "";
            qMetryImportAutomationResultsData.testCase.put("fixVersionId", qMetryImportAutomationResultsData.fixVersionId);
        }
    }

    /**
     * This will Get Assignee for the QMetry Test case
     * @param jsonObjectQmetryResults results object
     * @param qMetryImportAutomationResultsData qMetry Import Automation results related data class object
     * @throws JSONException throwing JSONException
     */
    public static void getAssignee(JSONObject jsonObjectQmetryResults, QMetryImportAutomationResultsData qMetryImportAutomationResultsData) throws JSONException {
        String assigneeTxt = "assignee";
        try {
            qMetryImportAutomationResultsData.assignee = jsonObjectQmetryResults.getJSONObject("data").getString(assigneeTxt);
            qMetryImportAutomationResultsData.testCase.put(assigneeTxt, qMetryImportAutomationResultsData.assignee);
        } catch (Exception e) {
            qMetryImportAutomationResultsData.assignee = "";
            qMetryImportAutomationResultsData.testCase.put(assigneeTxt, qMetryImportAutomationResultsData.assignee);
        }
    }

    /**
     * This will Get Reporter for the QMetry Test case
     * @param jsonObjectQmetryResults results object
     * @param qMetryImportAutomationResultsData qMetry Import Automation results related data class object
     * @throws JSONException throwing JSONException
     */
    public static void getReporter(JSONObject jsonObjectQmetryResults, QMetryImportAutomationResultsData qMetryImportAutomationResultsData) throws JSONException {
        String reporterTxt = "reporter";
        try {
            qMetryImportAutomationResultsData.reporter = jsonObjectQmetryResults.getJSONObject("data").getString(reporterTxt);
            qMetryImportAutomationResultsData.testCase.put(reporterTxt, qMetryImportAutomationResultsData.reporter);
        } catch (Exception e) {
            qMetryImportAutomationResultsData.reporter = "";
            qMetryImportAutomationResultsData.testCase.put(reporterTxt, qMetryImportAutomationResultsData.reporter);
        }
    }

    /**
     * This will Get Status for the QMetry Test case
     * @param jsonObjectQmetryResults results object
     * @param qMetryImportAutomationResultsData qMetry Import Automation results related data class object
     * @throws JSONException throwing JSONException
     */
    public static void getStatus(JSONObject jsonObjectQmetryResults, QMetryImportAutomationResultsData qMetryImportAutomationResultsData) throws JSONException {
        String statusTxt = "status";
        try {
            qMetryImportAutomationResultsData.status = jsonObjectQmetryResults.getJSONObject("data").getJSONObject(statusTxt).getString("name");
            qMetryImportAutomationResultsData.testCase.put(statusTxt, qMetryImportAutomationResultsData.status);
        } catch (Exception e) {
            qMetryImportAutomationResultsData.status = "";
            qMetryImportAutomationResultsData.testCase.put(statusTxt, qMetryImportAutomationResultsData.status);
        }
    }

    /**
     * This will Get Sprint ID for the QMetry Test case
     * @param jsonObjectQmetryResults results object
     * @param qMetryImportAutomationResultsData qMetry Import Automation results related data class object
     * @throws JSONException throwing JSONException
     */
    public static void getSprintID(JSONObject jsonObjectQmetryResults, QMetryImportAutomationResultsData qMetryImportAutomationResultsData) throws JSONException {
        try {
            qMetryImportAutomationResultsData.sprintId = (jsonObjectQmetryResults.getJSONObject("data").getJSONObject("sprint").getString("id"));
            qMetryImportAutomationResultsData.testCase.put("sprintId", qMetryImportAutomationResultsData.sprintId);
        } catch (Exception e) {
            qMetryImportAutomationResultsData.sprintId = "";
            qMetryImportAutomationResultsData.testCase.put("sprintId", qMetryImportAutomationResultsData.sprintId);
        }
    }

    /**
     * This will Get Labels into a String Array
     * @param qmetryResultsObject results object
     * @param qMetryImportAutomationResultsData qMetry Import Automation results related data class object
     * @throws Exception throwing Exception
     */
    public static void getLabelsToAStringArray(JSONObject qmetryResultsObject, QMetryImportAutomationResultsData qMetryImportAutomationResultsData) throws Exception {
        String labelsTxt = "labels";
        JSONArray labels = qmetryResultsObject.getJSONObject("data").getJSONArray(labelsTxt);
        qMetryImportAutomationResultsData.labels = new ArrayList<String>();
        try {
            for (int i = 0; i < labels.length(); i++) {
                String labelName = labels.getJSONObject(i).getString("name");
                qMetryImportAutomationResultsData.labels.add(labelName);
            }
            System.out.println("LABELS: " + qMetryImportAutomationResultsData.labels);
            qMetryImportAutomationResultsData.testCase.put(labelsTxt, qMetryImportAutomationResultsData.labels);
        } catch (Exception e) {
            qMetryImportAutomationResultsData.testCase.put(labelsTxt, qMetryImportAutomationResultsData.labels);
        }
    }

    /**
     * This will Get Components into a String Array
     * @param qmetryResultsObject results object
     * @param qMetryImportAutomationResultsData qMetry Import Automation results related data class object
     * @throws Exception throwing Exception
     */
    public static void getComponentsToAStringArray(JSONObject qmetryResultsObject, QMetryImportAutomationResultsData qMetryImportAutomationResultsData) throws Exception {
        String componentsTxt = "components";
        JSONArray component = qmetryResultsObject.getJSONObject("data").getJSONArray(componentsTxt);
        qMetryImportAutomationResultsData.components = new ArrayList<String>();
        try {
            for (int i = 0; i < component.length(); i++) {
                String componentName = component.getJSONObject(i).getString("name");
                qMetryImportAutomationResultsData.components.add(componentName);
            }
            System.out.println("COMPONENTS: " + qMetryImportAutomationResultsData.components);
            qMetryImportAutomationResultsData.testCase.put(componentsTxt, qMetryImportAutomationResultsData.components);
        } catch (Exception e) {
            qMetryImportAutomationResultsData.testCase.put(componentsTxt, qMetryImportAutomationResultsData.components);
        }
    }

    /**
     * This will Get Custom Fields into a Json Array
     * @param qmetryResultsObject results object
     * @param qMetryImportAutomationResultsData qMetry Import Automation results related data class object
     * @throws Exception throwing Exception
     */
    public static void getCustomFieldsToAStringArray(JSONObject qmetryResultsObject, QMetryImportAutomationResultsData qMetryImportAutomationResultsData) throws Exception {
        String customFieldsTxt = "customFields";
        String valueTxt = "value";
        JSONObject customFields = qmetryResultsObject.getJSONObject("data").getJSONObject(customFieldsTxt);
        qMetryImportAutomationResultsData.customFields = new JSONArray();
        try {
            JSONObject customFieldsObject = null;
            JSONArray customFieldsValueListArray = new JSONArray();
            Iterator<String> keys = customFields.keys();
            while (keys.hasNext()) {
                List<String> tempCustomFieldValue = new ArrayList<>();
                String keyValue = (String) keys.next();
                customFieldsObject = customFields.getJSONObject(keyValue);
                //getting string values with keys - name and value
                String name = customFieldsObject.getString("name");
                customFieldsValueListArray = customFieldsObject.getJSONArray(valueTxt);
                for (int i = 0; i < customFieldsValueListArray.length(); i++) {
                    String customFieldValue = "";
                    customFieldValue = customFieldsValueListArray.getJSONObject(i).getString(valueTxt);
                    tempCustomFieldValue.add(customFieldValue);
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", name);
                jsonObject.put(valueTxt, String.join(",", tempCustomFieldValue));
                qMetryImportAutomationResultsData.customFields.put(jsonObject);
                qMetryImportAutomationResultsData.testCase.put(customFieldsTxt, qMetryImportAutomationResultsData.customFields);
            }
        } catch (Exception e) {
            qMetryImportAutomationResultsData.testCase.put(customFieldsTxt, qMetryImportAutomationResultsData.customFields);
        }
    }

    /**
     * This will set Property the Custom Fields of Test cases by Project
     * @throws Exception throwing Exception
     */
    public static void getTestCaseCustomFieldsByProject() throws Exception {
        RestUtil.API_HOST = QTMCLOUD_QMETRY_URL;
        RestUtil.BASE_PATH = "rest/api/latest/projects/";

        Response response = RestUtil.send(Headers.getHeaderWithApiKey(System.getProperty(QMETRY_OPEN_API_KEY)), "", System.getProperty(QMETRY_PROJECT_KEY) + "/testcase-custom-fields/", RequestMethods.GET.toString(), null);
        JSONArray jsonObjectQmetryResults = new JSONArray(response.asString());
        ArrayList<String> customFieldsListArray = new ArrayList<String>();
        String customFields = "";
        try {
            for (int i = 0; i < jsonObjectQmetryResults.length(); i++) {
                customFields = jsonObjectQmetryResults.getJSONObject(i).getString("id");
                customFieldsListArray.add(customFields);
            }
            System.setProperty(QMETRY_CUSTOM_FIELDS_LIST,String.join(",", customFieldsListArray)) ;
        } catch (Exception e) {
            System.setProperty(QMETRY_CUSTOM_FIELDS_LIST,customFields);
        }
    }

    /**
     * This will upload TestNG Results file
     */
    public static void uploadTestNGResultsFile() {
        String testResults = testngResultsFilePath;
        try(FileInputStream file = new FileInputStream(testResults)) {
            URL url = new URL(urlToUploadFile);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "multipart/form-data");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            OutputStream os = connection.getOutputStream();
            IOUtils.copy(file, os);
            InputStream fis = connection.getInputStream();
            StringWriter writer = new StringWriter();
            IOUtils.copy(fis, writer, "UTF-8");
            if (connection.getResponseCode() == 200) {
                LoggerUtil.logINFO("Qmetry Results Upload Success");
            } else {
                LoggerUtil.logINFO("Qmetry Results Upload Failed");
            }
        } catch (Exception ex) {
            LoggerUtil.logERROR("Test Run file upload exception", ex.getCause());
        }
    }
}
