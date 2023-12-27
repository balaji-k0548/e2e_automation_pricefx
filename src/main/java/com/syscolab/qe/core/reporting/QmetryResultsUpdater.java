package com.syscolab.qe.core.reporting;

import com.syscolab.qe.core.api.restassured.RestUtil;
import com.syscolab.qe.core.common.LoggerUtil;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This is the class to update qMetry Results
 * @author  maheshganegoda on 8/13/17.
 */
public class QmetryResultsUpdater {

    private static final String DEFAULT_KEY = "defaultKey";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json; charset=UTF-8";
    private static final String FORMAT = "\"format\":";
    private static final String TESTNG_XML = "\"testng/xml\",";
    private static final String API_KEY = "\"apiKey\":";
    private static final String TEST_RUN_NAME = "\"testRunName\":";
    private static final String STR_PLATFORM = "\"platform\":";
    private static final String STR_LABELS = "\"labels\":";
    private static final String VERSION = "\"versions\":";
    private static final String STR_COMPONENTS ="\"components\":";
    private static final String STR_SPRINT = "\"sprint\":";
    private static final String TEST_RUN_KEY = "\"testRunKey\":";
    private static final String TEST_ASSET_HIERARCHY = "\"testAssetHierarchy\":";
    private static final String STR_COMMENT = "\"comment\":";
    private static final String RESPONSE = "====================RESPONSE==============================";
    private static final String IS_SUCCESS = "isSuccess";
    private static final String TEST_RESULTS_UPDATE_SUSPENDED_TEST_RUN_CREATION_NOT_SUCCESS = "Test results update suspended, Test run creation not success";
    private static final String EXCEPTION_IN_CREATING_TEST_RUN = "Exception in creating test run";
    private static final String SYSTEM_VARIABLE_QMETRY_KEY_IS_NOT_SET = "System variable \"qmetry.key\" is not set;";
    private static final String QMETRY_ADAPTER_EXCEPTION_QMETRY_API_KEY_IS_NOT_SET = "Qmetry Adapter Exception, Qmetry API key is not set";

    private static String urlToUploadFile = null;
    private static String testngResultsFilePath =System.getProperty("user.dir",".")+"/qmetry.xml";

    private static String encoding = "UTF-8";
    private static String testAssertHierarchy = "TestCase-TestStep";
    private static String qmetryUrl = System.getProperty("qmetry.url","https://importresults.qmetry.com/prod/importresults-qtm4j");

    private String qmetryApiKey = System.getProperty("qmetry.key",DEFAULT_KEY);
    private static String testRunName = System.getProperty("qmetry.testRunName", "testRunName");
    private static String platform = System.getProperty("qmetry.platform", "platform");
    private static String labels = System.getProperty("qmetry.labels", "labels");
    private static String versions = System.getProperty("qmetry.versions", "versions");
    private static String components = System.getProperty("qmetry.components", "components");
    private static String sprint = System.getProperty("qmetry.sprint", "sprint");
    private static String testRunKey = System.getProperty("qmetry.testRunKey", "testRunKey");
    private static String comment = System.getProperty("qmetry.comment", "comment");

    /**
     * This is the main method
     * @param args stores the incoming arguments
     */
    public static void main(String [] args){

        QmetryResultsUpdater qmetryResultsUpdater = new QmetryResultsUpdater();
        qmetryResultsUpdater.testRunName = args.length>0?args[0]: qmetryResultsUpdater.testRunName;
        qmetryResultsUpdater.qmetryApiKey = args.length>0&&args.length>1?args[1]: qmetryResultsUpdater.qmetryApiKey;
        SyscoLabListener syscoLabListener = new SyscoLabListener();
    }

    /**
     * This will create a test run with passed test run name and api key
     * @param testRunName test run name to create
     * @param apiKey api key of the project
     */
    public static void createTestRun(String testRunName, String apiKey){

        if(!apiKey.equalsIgnoreCase(DEFAULT_KEY)) {
            LoggerUtil.logINFO("Qmetry test run name will be :"+testRunName);
            try {
                URL url = new URL(qmetryUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                StringBuilder jsonBody = new StringBuilder("{");
                jsonBody.append(FORMAT + TESTNG_XML);
                jsonBody.append(API_KEY + "\"" + apiKey + "\",");
                jsonBody.append(TEST_RUN_NAME + "\"" + testRunName + "\",");
                jsonBody.append(STR_PLATFORM + "\"" + "chrome" + "\",");
                jsonBody.append(STR_LABELS + "\"" + "lbl1" + "\",");
                jsonBody.append(VERSION + "\"" + "v1" + "\",");
                jsonBody.append(STR_COMPONENTS + "\"" + "com1,com2" + "\",");
                jsonBody.append(STR_SPRINT + "\"" + "sprint1" + "\",");
                jsonBody.append(TEST_RUN_KEY + "\"" + "PRM-1580" + "\",");
                jsonBody.append(TEST_ASSET_HIERARCHY + "\"" + testAssertHierarchy + "\",");
                jsonBody.append(STR_COMMENT + "\"" + "sample" + "\"");
                jsonBody.append("}");

                System.out.println("====================REQUEST==============================");
                System.out.println(jsonBody);


                OutputStream os = connection.getOutputStream();
                os.write(jsonBody.toString().getBytes(encoding));
                InputStream fis = connection.getInputStream();

                StringWriter response = new StringWriter();
                IOUtils.copy(fis, response, encoding);
                JSONObject myObject = new JSONObject(response.toString());
                LoggerUtil.logINFO(RESPONSE);
                System.out.println(myObject);
                String isSuccess = myObject.get(IS_SUCCESS).toString();

                if (Boolean.valueOf(isSuccess)) {
                    urlToUploadFile = myObject.get("url").toString();
                    uploadTestNGResultsFile();
                } else {
                    LoggerUtil.logINFO(TEST_RESULTS_UPDATE_SUSPENDED_TEST_RUN_CREATION_NOT_SUCCESS);

                }
            } catch (Exception ex) {
                LoggerUtil.logERROR(EXCEPTION_IN_CREATING_TEST_RUN, ex.getCause());
            }
        }else{
            LoggerUtil.logWARNING(SYSTEM_VARIABLE_QMETRY_KEY_IS_NOT_SET,new Throwable(QMETRY_ADAPTER_EXCEPTION_QMETRY_API_KEY_IS_NOT_SET));
        }

    }

    /**
     * This will create a test run with the api key
     * @param apiKey api key of the project
     */
    public  static void createTestRun(String apiKey){

        if(!apiKey.equalsIgnoreCase(DEFAULT_KEY)) {
            LoggerUtil.logINFO("Qmetry test run name will be : " + testRunName);
            try {
                URL url = new URL(qmetryUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                StringBuilder jsonBody = new StringBuilder("{");
                jsonBody.append(FORMAT + TESTNG_XML);
                jsonBody.append(API_KEY + "\"" + apiKey + "\",");
                jsonBody.append(TEST_RUN_NAME + "\"" + testRunName + "\",");
                jsonBody.append(STR_PLATFORM + "\"" + platform + "\",");
                jsonBody.append(STR_LABELS + "\"" + labels + "\",");
                jsonBody.append(VERSION + "\"" + versions + "\",");
                jsonBody.append(STR_COMPONENTS + "\"" + components + "\",");
                jsonBody.append(STR_SPRINT + "\"" + sprint + "\",");
//                jsonBody.append(TEST_RUN_KEY + "\"" + testRunKey + "\",");

                //todo
                jsonBody.append(TEST_RUN_KEY + "\"" + RestUtil.getValue(SyscoLabListener.getResults().get(SyscoLabListener.getResults().size() - 1).toString(),"id") + "\",");
                jsonBody.append(TEST_ASSET_HIERARCHY + "\"" + testAssertHierarchy + "\",");
                jsonBody.append(STR_COMMENT + "\"" + comment + "\"");
                jsonBody.append("}");

                System.out.println("====================REQUEST only api key==============================");
                System.out.println(jsonBody);

                OutputStream os = connection.getOutputStream();
                os.write(jsonBody.toString().getBytes(encoding));
                InputStream fis = connection.getInputStream();

                StringWriter response = new StringWriter();
                IOUtils.copy(fis, response, encoding);
                JSONObject myObject = new JSONObject(response.toString());
                LoggerUtil.logINFO(RESPONSE);
                System.out.println(myObject);
                String isSuccess = myObject.get(IS_SUCCESS).toString();

                if (Boolean.valueOf(isSuccess)) {
                    urlToUploadFile = myObject.get("url").toString();
                    uploadTestNGResultsFile();
                } else {
                    LoggerUtil.logINFO(TEST_RESULTS_UPDATE_SUSPENDED_TEST_RUN_CREATION_NOT_SUCCESS);

                }
            } catch (Exception ex) {
                LoggerUtil.logERROR(EXCEPTION_IN_CREATING_TEST_RUN, ex.getCause());
            }
        }else{
            LoggerUtil.logWARNING(SYSTEM_VARIABLE_QMETRY_KEY_IS_NOT_SET,new Throwable(QMETRY_ADAPTER_EXCEPTION_QMETRY_API_KEY_IS_NOT_SET));
        }

    }

    /**
     * This will create a test run with the api key and test run key
     * @param filePath file path
     * @param apiKey api key of the project
     */
    public static  void createTestRunWithTestRunKey(String filePath, String apiKey){

        if(!apiKey.equalsIgnoreCase(DEFAULT_KEY)) {
            LoggerUtil.logINFO("Qmetry test run name will be : " + testRunName);
            try {
                URL url = new URL(qmetryUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                StringBuilder jsonBody = new StringBuilder("{");
                jsonBody.append(FORMAT + TESTNG_XML);
                jsonBody.append(API_KEY + "\"" + apiKey + "\",");
                jsonBody.append(TEST_RUN_NAME + "\"" + testRunName + "\",");
                jsonBody.append(STR_PLATFORM + "\"" + platform + "\",");
                jsonBody.append(STR_LABELS + "\"" + labels + "\",");
                jsonBody.append(VERSION + "\"" + versions + "\",");
                jsonBody.append(STR_COMPONENTS + "\"" + components + "\",");
                jsonBody.append(STR_SPRINT + "\"" + sprint + "\",");
                jsonBody.append(TEST_RUN_KEY + "\"" + testRunKey + "\",");
                jsonBody.append(TEST_ASSET_HIERARCHY + "\"" + testAssertHierarchy + "\",");
                jsonBody.append(STR_COMMENT + "\"" + comment + "\"");
                jsonBody.append("}");

                System.out.println("====================REQUEST only api key==============================");
                System.out.println(jsonBody);

                OutputStream os = connection.getOutputStream();
                os.write(jsonBody.toString().getBytes(encoding));
                InputStream fis = connection.getInputStream();

                StringWriter response = new StringWriter();
                IOUtils.copy(fis, response, encoding);
                JSONObject myObject = new JSONObject(response.toString());
                LoggerUtil.logINFO(RESPONSE);
                System.out.println(myObject);
                String isSuccess = myObject.get(IS_SUCCESS).toString();

                if (Boolean.valueOf(isSuccess)) {
                    urlToUploadFile = myObject.get("url").toString();
                    uploadTestNGResultsFile();
                } else {
                    LoggerUtil.logINFO(TEST_RESULTS_UPDATE_SUSPENDED_TEST_RUN_CREATION_NOT_SUCCESS);

                }
            } catch (Exception ex) {
                LoggerUtil.logERROR(EXCEPTION_IN_CREATING_TEST_RUN, ex.getCause());
            }
        }else{
            LoggerUtil.logWARNING(SYSTEM_VARIABLE_QMETRY_KEY_IS_NOT_SET,new Throwable(QMETRY_ADAPTER_EXCEPTION_QMETRY_API_KEY_IS_NOT_SET));
        }

    }

    /**
     * This will create a test run with api key
     * @param syscoLabListener Sysco Lab Listener
     * @param apiKey qmetry api key of the project
     */
    public static void createTestRun(SyscoLabListener syscoLabListener, String apiKey){

        if(!apiKey.equalsIgnoreCase(DEFAULT_KEY)) {
            LoggerUtil.logINFO("Qmetry test run name will be :"+ RestUtil.getValue(SyscoLabListener.getResults().toString(),"name"));
            try {
                URL url = new URL(qmetryUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                StringBuilder jsonBody = new StringBuilder("{");
                jsonBody.append(FORMAT + TESTNG_XML);
                jsonBody.append(API_KEY + "\"" + apiKey + "\",");
                jsonBody.append(TEST_RUN_NAME + "\"" + RestUtil.getValue(SyscoLabListener.getResults().toString(),"name") + "\",");
                jsonBody.append(STR_PLATFORM + "\"" + RestUtil.getValue(SyscoLabListener.getResults().toString(),"id") + "\",");
                jsonBody.append(STR_LABELS + "\"" + RestUtil.getValue(SyscoLabListener.getResults().toString(),"id") + "\",");
                jsonBody.append(VERSION + "\"" + RestUtil.getValue(SyscoLabListener.getResults().toString(),"id") + "\",");
                jsonBody.append(STR_COMPONENTS + "\"" + RestUtil.getValue(SyscoLabListener.getResults().toString(),"id") + "\",");
                jsonBody.append(STR_SPRINT + "\"" + RestUtil.getValue(SyscoLabListener.getResults().toString(),"id") + "\",");
                jsonBody.append(TEST_RUN_KEY + "\"" + RestUtil.getValue(SyscoLabListener.getResults().toString(),"id") + "\",");
                jsonBody.append(TEST_ASSET_HIERARCHY + "\"" + testAssertHierarchy + "\",");
                jsonBody.append(STR_COMMENT + "\"" + RestUtil.getValue(SyscoLabListener.getResults().toString(),"keyword") + "\"");
                jsonBody.append("}");

                System.out.println("==================================================");
                System.out.println(jsonBody);

                OutputStream os = connection.getOutputStream();
                os.write(jsonBody.toString().getBytes(encoding));
                InputStream fis = connection.getInputStream();

                StringWriter response = new StringWriter();
                IOUtils.copy(fis, response, encoding);
                JSONObject myObject = new JSONObject(response.toString());
                String isSuccess = myObject.get(IS_SUCCESS).toString();

                if (Boolean.valueOf(isSuccess)) {
                    urlToUploadFile = myObject.get("url").toString();
                    uploadTestNGResultsFile();
                } else {
                    LoggerUtil.logINFO(TEST_RESULTS_UPDATE_SUSPENDED_TEST_RUN_CREATION_NOT_SUCCESS);

                }
            } catch (Exception ex) {
                LoggerUtil.logERROR(EXCEPTION_IN_CREATING_TEST_RUN, ex.getCause());
            }
        }else{
            LoggerUtil.logWARNING(SYSTEM_VARIABLE_QMETRY_KEY_IS_NOT_SET,new Throwable(QMETRY_ADAPTER_EXCEPTION_QMETRY_API_KEY_IS_NOT_SET));
        }

    }

    /**
     * This will call create test run with qmetry api key method
     */
    public void createTestRun(){
//        createTestRun(testRunName, qmetryApiKey);
        createTestRun(qmetryApiKey);
    }


    /**
     * This will upload testNG Results file
     */
    public  static void uploadTestNGResultsFile(){

        String testResults = testngResultsFilePath;

        try(FileInputStream file = new FileInputStream(testResults)) {
            URL url = new URL(urlToUploadFile);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty(CONTENT_TYPE, "multipart/form-data");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            OutputStream os = connection.getOutputStream();
            IOUtils.copy(file, os);

            InputStream fis = connection.getInputStream();
            StringWriter writer = new StringWriter();
            IOUtils.copy(fis, writer, "UTF-8");
            if (connection.getResponseCode() == 200) {
                LoggerUtil.logINFO("Qmetry Results Upload Success");
            }else{
                LoggerUtil.logINFO("Qmetry Results Upload Failed");
            }
        }catch (Exception ex){
            LoggerUtil.logINFO("Test Run file upload exception");
        }
    }

}
