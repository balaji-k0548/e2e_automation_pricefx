package com.syscolab.qe.core.reporting;

import com.syscolab.qe.core.common.LoggerUtil;
import io.restassured.response.Response;
import com.syscolab.qe.core.api.restassured.RestUtil;
import com.syscolab.qe.core.api.util.Headers;
import com.syscolab.qe.core.api.util.RequestMethods;

import com.syscolab.qe.core.common.Constants;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * This is the helper class for qMetry
 * @author  maheshganegoda on 12/26/17.
 */
public class QmetryHelper {
    private static Logger LOGGER = Logger.getLogger(QmetryHelper.class);

    private static final Map<String,ArrayList<String>> QMETRY_TEST_STEPS = new HashMap<String, ArrayList<String>>();

    private static final Map<String,String> QMETRY_TEST_DES = new HashMap<String, String>();

    private static String TEST_METHOD_NAME = "default";
    private static boolean TEST_WITH_STEP = false;

    public static String DELIMINATOR = " ";

    private static final String QTMCLOUD_QMETRY_URL = "https://qtmcloud.qmetry.com/";
    private static final String UTF_8 = "UTF-8";

    /**
     * This will return the qmetry test steps
     * @return qmetry test steps
     */
    public static Map<String, ArrayList<String>> getQmetryTestSteps() {
        return QMETRY_TEST_STEPS;
    }

    /**
     * This will return the qmetry test description
     * @return qmetry test description
     */
    public static Map<String, String> getQmetryTestDes() {
        return QMETRY_TEST_DES;
    }

    /**
     * This will set the qmetry test with test steps
     * @param name name
     */
    public static void qmetryTest(String name) {
        String des ;
        TEST_METHOD_NAME = name;
        if(TEST_METHOD_NAME.contains(QmetryHelper.DELIMINATOR)){
            des= TEST_METHOD_NAME.split(QmetryHelper.DELIMINATOR)[0].trim();
            TEST_METHOD_NAME = TEST_METHOD_NAME.split(QmetryHelper.DELIMINATOR)[1].trim();
            QMETRY_TEST_DES.put(TEST_METHOD_NAME,des);
        }

        if(QMETRY_TEST_STEPS.get(name)==null){
            ArrayList<String> stepForMethod = new ArrayList<String>();
            QMETRY_TEST_STEPS.put(TEST_METHOD_NAME,stepForMethod);
        }
    }

    /**
     * This will set the qmetry test
     * @param withStep with step
     * @param name name
     */
    public static void qmetryTest(boolean withStep,String name) {
        TEST_WITH_STEP =withStep;
        qmetryTest(name);
    }

    /**
     * This will set teh qmetry test with des and name
     * @param withStep with step
     * @param des description
     * @param name name
     */
    public static void qmetryTest(boolean withStep,String des,String name) {
        TEST_WITH_STEP =withStep;
        qmetryTest(des+DELIMINATOR+name);
    }

    /**
     * This will set the qmetry test with method name from the current thread
     * @param withStep with step
     */
    public static void qmetryTest(boolean withStep) {
        TEST_WITH_STEP =withStep;
        qmetryTest(Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    /**
     * This will add the step to the qmetry test steps
     * @param step step
     */
    public static void qmetryStep(String step) {
        if(TEST_WITH_STEP) {
            try {
                QMETRY_TEST_STEPS.get(TEST_METHOD_NAME).add(step);
            } catch (Exception e) {
                LOGGER.debug("Test not added for Qmetry results :");
            }
        }
    }

    /**
     * This will add the method name as the step to steps from the current thread
     */
    public static void qmetryStep(){
        qmetryStep(Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    /**
     * This will return qmetry issue case ID
     * @param testcaseID test case ID
     * @return qmetry issue case id
     */
    public static String getQMetryIssueCaseID(String testcaseID){
        RestUtil.API_HOST = "https://syscobt.atlassian.net";
        RestUtil.BASE_PATH = "";
        Response response = RestUtil.send(Headers.getBasicHeader(Constants.JIRA_USERNAME, Constants.JIRA_TOKEN),null, "rest/api/2/search?jql=key=" + testcaseID,  RequestMethods.GET.toString());
        LoggerUtil.logINFO("Get QMetry issue case id "+ response.asString());
        return RestUtil.getValueInArray(response.asString(), "issues", 0, "id");
    }

    /**
     * This will return qmetry test run details
     * @param testcaseID test case id
     * @return test run details
     */
    public static Response getQMetryTestRunDetails(String testcaseID){
        RestUtil.API_HOST = QTMCLOUD_QMETRY_URL;
        RestUtil.BASE_PATH = "rest/api/latest/testcycles/";
        Response response = RestUtil.send(Headers.getHeaderWithApiKey(System.getProperty("qmetry.open.api.key")),null,   testcaseID+"?fields=summary",  RequestMethods.GET.toString());
        return response;
    }

    /**
     * This will return qmetry test name
     * @param testcaseID test case id
     * @return qmetry test name
     */
    public static String getQMetryTestName(String testcaseID) {
        RestUtil.BASE_PATH = "";
        RestUtil.API_HOST = "https://syscobt.atlassian.net";
        Response response = RestUtil.send(Headers.getBasicHeader(Constants.JIRA_USERNAME, Constants.JIRA_TOKEN),null, "rest/api/2/search?jql=key=" + testcaseID,  RequestMethods.GET.toString());
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(RestUtil.getValue(response.asString(), "issues"));
            return RestUtil.getValueRecursively(jsonArray.get(0).toString(), "fields->summary");
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * This will get qmetry test run id
     * @param testCaseID test case id
     * @return qmetry test run id
     */
    public static String getQMetryTestRunID(String testCaseID){
        RestUtil.BASE_PATH = "";
        String testIssueID = getQMetryIssueCaseID(testCaseID);
        RestUtil.API_HOST = "https://qtmcloud.qmetry.com/actionstep/";
        Response response = RestUtil.send (Headers.getHeaderQMetry1("cGVyZXJhLnNhbmRlZXBAY29ycC5zeXNjby5jb206MXFhejJ3c3hA", "JSESSIONID=8F902B55B97423A577CF711224ED6B0B", "https://qtmcloud.qmetry.com/actionstep/view/action-step-automation-attributes.do?issueId=322185&projectId=13848&xdm_e=https%3A%2F%2Fsyscobt.atlassian.net&xdm_c=channel-com.infostretch.QmetryTestManager__action-step-automation-panel&cp=&xdm_deprecated_addon_key_do_not_use=com.infostretch.QmetryTestManager&lic=active&cv=1.507.0&jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1YTk3MTg4ZGJiNjU2MTI5MmYyNTdmYzEiLCJxc2giOiI3ZTE5OTllNjE1NmQ2ODlmMjE2YTM3YjFhOWQ4MTdkZjI3MjY2YWQ1ZjQwZmEzOGE1NzVjMjhkM2I5Zjg4N2MyIiwiaXNzIjoiNzdjNjNkMzUtYzU5Ni0zZWIwLWEwMjMtNzAyNDNiYmM0YzgyIiwiY29udGV4dCI6e30sImV4cCI6MTU2ODEyNTQ1NCwiaWF0IjoxNTY4MTI0NTU0fQ.MCVsjw8yY0l1mQJqEEubodHpO0chmmqUdg16gLd3ig4"),null, "getExecutedActionStepHistory.do?location=Test%20Case_Test%20Runs_pageload&actionStepIssueId=" + testIssueID + "&limit=10&startIndex=0&_=1549014841889",  RequestMethods.GET.toString());
        LoggerUtil.logINFO("Get QMetry Test Run ID "+response.asString());
        return RestUtil.getValueInArray(RestUtil.getValue(response.asString(), "result"), "executedActionSteps", 0, "testRunIssueKey");
    }

    /**
     * This will set Automation Attributes
     * @param testCaseID test case id
     * @param testMethodName test method name
     * @param testClassName test class name
     */
    public static void setAutomationAttributes(String testCaseID, String testMethodName, String testClassName){
        RestUtil.BASE_PATH = "";
        String testIssueID = getQMetryIssueCaseID(testCaseID);
        RestUtil.API_HOST = "https://qtmcloud.qmetry.com/automation/";
        RestUtil.send (Headers.getHeaderQMetry(Constants.JIRA_USERNAME, Constants.JIRA_TOKEN, "https://qtmcloud.qmetry.com/actionstep/view/action-step-automation-attributes.do?issueId=322185&projectId=13848&xdm_e=https%3A%2F%2Fsyscobt.atlassian.net&xdm_c=channel-com.infostretch.QmetryTestManager__action-step-automation-panel&cp=&xdm_deprecated_addon_key_do_not_use=com.infostretch.QmetryTestManager&lic=active&cv=1.507.0&jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1YTk3MTg4ZGJiNjU2MTI5MmYyNTdmYzEiLCJxc2giOiI3ZTE5OTllNjE1NmQ2ODlmMjE2YTM3YjFhOWQ4MTdkZjI3MjY2YWQ1ZjQwZmEzOGE1NzVjMjhkM2I5Zjg4N2MyIiwiaXNzIjoiNzdjNjNkMzUtYzU5Ni0zZWIwLWEwMjMtNzAyNDNiYmM0YzgyIiwiY29udGV4dCI6e30sImV4cCI6MTU2ODEyNTQ1NCwiaWF0IjoxNTY4MTI0NTU0fQ.MCVsjw8yY0l1mQJqEEubodHpO0chmmqUdg16gLd3ig4"),null, "editAutomationAttributes.do?location=Test%20Case_Automation%20attributes_click_Automation_Attr_update&issueId=" + testIssueID + "&automationId=" + testMethodName + "&featureUri=" + testClassName + "&_=1549356388362",  RequestMethods.GET.toString());
    }

    /**
     * This will get qmetry steps of the test case
     * @param issueID issue id
     * @param testcaseID test case id
     * @throws JSONException throwing JSONException
     * @throws FileNotFoundException throwing FileNotFoundException
     * @throws UnsupportedEncodingException throwing UnsupportedEncodingException
     */
    public static void getQMetrySteps(String issueID, String testcaseID) throws JSONException, FileNotFoundException, UnsupportedEncodingException {
        RestUtil.API_HOST = QTMCLOUD_QMETRY_URL;
        RestUtil.BASE_PATH = "";
        String format = "%100s %20s";
        Response response = RestUtil.send (Headers.getHeaderQMetry("cWNlbnRlckBjb3JwLnN5c2NvLmNvbTpXZWxjb21lOA", "JSESSIONID=F0B74D8C96CEC5819D23415917FE10FF", "https://qtmcloud.qmetry.com/actionstep/view/action-step.do?issueId=197017&projectId=13609&user_id=Perera.Sandeep&user_key=perera.sandeep&tz=America%2FChicago&loc=en-US&xdm_e=https%3A%2F%2Fsyscobt.atlassian.net&xdm_c=channel-com.infostretch.QmetryTestManager__action-step-panel&cp=&xdm_deprecated_addon_key_do_not_use=com.infostretch.QmetryTestManager&lic=active&cv=1.269.0&jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZXJlcmEuc2FuZGVlcCIsInFzaCI6IjFjMmE3ZjRkMzk2MzUzMDQwMjllNjlkZGEyMTIzMzU5MDUwYWVkMzg5N2IwZmZjMGI0YzYxNDgzZGY5M2IxYzQiLCJpc3MiOiI3N2M2M2QzNS1jNTk2LTNlYjAtYTAyMy03MDI0M2JiYzRjODIiLCJjb250ZXh0Ijp7InVzZXIiOnsiYWNjb3VudElkIjoiNWE5NzE4OGRiYjY1NjEyOTJmMjU3ZmMxIiwiZGlzcGxheU5hbWUiOiJTYW5kZWVwIFBlcmVyYSIsInVzZXJLZXkiOiJwZXJlcmEuc2FuZGVlcCIsInVzZXJuYW1lIjoiUGVyZXJhLlNhbmRlZXAifX0sImV4cCI6MTU0ODA1NjkwNiwiaWF0IjoxNTQ4MDU2MDA2fQ.L_ITPW9ExKNSfnPRrPh8WrThujcEKLrdU0kl_GqhB7Q"),null, "stepunit?location=Test%20Case_stepunits_pageload&actionStepIssueId=" + issueID,  RequestMethods.GET.toString());
        String testSteps = RestUtil.getValueRecursively(response.asString(), "result->stepUnitList->stepUnits");

        try(PrintWriter writer = new PrintWriter(testcaseID + ".txt", UTF_8)){
            writer.println((String.format(format, "Test Case Step", "Test Data")));
            LoggerUtil.logINFO((String.format(format, "Test Case Step", "Test Data")));
            int i = 0;

            org.json.JSONArray responseBody = new org.json.JSONArray(testSteps);
            while (i < responseBody.length()){
                writer.println((String.format(format, RestUtil.getValue(responseBody.getString(i), "stepDetails"), RestUtil.getValue(responseBody.getString(i), "testData"))));
                LoggerUtil.logINFO(String.format(format, RestUtil.getValue(responseBody.getString(i), "stepDetails"), RestUtil.getValue(responseBody.getString(i), "testData")));
                i++;
            }
        }
    }

    /**
     * This will get qmetry story/scenarios steps
     * @param issueID issue ID
     * @param testcaseID test case id
     * @throws JSONException throwing JSONException
     * @throws FileNotFoundException throwing FileNotFoundException
     * @throws UnsupportedEncodingException throwing UnsupportedEncodingException
     */
    public static void getQMetryStorySteps(String issueID, String testcaseID) throws JSONException, FileNotFoundException, UnsupportedEncodingException {
        RestUtil.API_HOST = QTMCLOUD_QMETRY_URL;
        RestUtil.BASE_PATH = "";
        Response response = RestUtil.send (Headers.getHeaderQMetry("cWNlbnRlckBjb3JwLnN5c2NvLmNvbTpXZWxjb21lOA", "JSESSIONID=F0B74D8C96CEC5819D23415917FE10FF", "https://qtmcloud.qmetry.com/actionstep/view/action-step.do?issueId=197017&projectId=13609&user_id=Perera.Sandeep&user_key=perera.sandeep&tz=America%2FChicago&loc=en-US&xdm_e=https%3A%2F%2Fsyscobt.atlassian.net&xdm_c=channel-com.infostretch.QmetryTestManager__action-step-panel&cp=&xdm_deprecated_addon_key_do_not_use=com.infostretch.QmetryTestManager&lic=active&cv=1.269.0&jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZXJlcmEuc2FuZGVlcCIsInFzaCI6IjFjMmE3ZjRkMzk2MzUzMDQwMjllNjlkZGEyMTIzMzU5MDUwYWVkMzg5N2IwZmZjMGI0YzYxNDgzZGY5M2IxYzQiLCJpc3MiOiI3N2M2M2QzNS1jNTk2LTNlYjAtYTAyMy03MDI0M2JiYzRjODIiLCJjb250ZXh0Ijp7InVzZXIiOnsiYWNjb3VudElkIjoiNWE5NzE4OGRiYjY1NjEyOTJmMjU3ZmMxIiwiZGlzcGxheU5hbWUiOiJTYW5kZWVwIFBlcmVyYSIsInVzZXJLZXkiOiJwZXJlcmEuc2FuZGVlcCIsInVzZXJuYW1lIjoiUGVyZXJhLlNhbmRlZXAifX0sImV4cCI6MTU0ODA1NjkwNiwiaWF0IjoxNTQ4MDU2MDA2fQ.L_ITPW9ExKNSfnPRrPh8WrThujcEKLrdU0kl_GqhB7Q"),null, "testscenario?location=Story_Test Case_pageload&storyIssueId=" + issueID + "&offset=0&_=1573040072050",  RequestMethods.GET.toString());
        String testScenariosArray = RestUtil.getValueRecursively(response.asString(), "result->testScenarios");
        LoggerUtil.logINFO("======================================================================");

        try(PrintWriter writer = new PrintWriter(testcaseID + ".txt", UTF_8)) {
            int i = 0;

            org.json.JSONArray responseBody = new org.json.JSONArray(testScenariosArray);
            while (i < responseBody.length()){
                writer.println(String.format("%100s %20s %20s", RestUtil.getValue(responseBody.getString(i), "issueType") , RestUtil.getValue(responseBody.getString(i), "issueDetails"), RestUtil.getValue(responseBody.getString(i), "issueKey")));
                LoggerUtil.logINFO(String.format("%100s %20s %20s", RestUtil.getValue(responseBody.getString(i), "issueType"),  RestUtil.getValue(responseBody.getString(i), "issueDetails"), RestUtil.getValue(responseBody.getString(i), "issueKey")));
                i++;
            }
        }

    }

    /**
     * This will write steps and data to file
     * @param testCaseID test case id
     * @param testCaseStepsData test case steps data
     * @throws FileNotFoundException throwing FileNotFoundException
     * @throws UnsupportedEncodingException throwing UnsupportedEncodingException
     */
    public static void writeStepsAndDataToFile(String testCaseID, String testCaseStepsData) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(testCaseID + ".txt", UTF_8);
        writer.println(testCaseStepsData);
        writer.close();
    }

    /**
     * This will get the qmetry test steps
     * @param testcaseID test case id
     */
    public static void getTestSteps(String testcaseID) {
        String issueID = getQMetryIssueCaseID(testcaseID);
        try {
            getQMetrySteps(issueID, testcaseID);
        } catch (JSONException | FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * This will get the qmetry story steps
     * @param testcaseID test case id
     */
    public static void getTestStory(String testcaseID) {
        String issueID = getQMetryIssueCaseID(testcaseID);
        try {
            getQMetryStorySteps(issueID, testcaseID);
        } catch (JSONException | FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is the main method
     * @param args stores the incoming arguments
     */
    public static void main(String args[]) {
        getQMetryTestName("FLS-2053");
    }

}
