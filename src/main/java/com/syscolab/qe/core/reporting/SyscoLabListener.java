package com.syscolab.qe.core.reporting;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.syscolab.qe.core.api.restassured.RestUtil;
import com.syscolab.qe.core.util.As400ScreenshotUtil;
import com.syscolab.qe.core.common.LoggerUtil;
import com.syscolab.qe.core.ui.SyscoLabUI;
import framework.AS400;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is SyscoLab Listener class
 * @author  yoosufm on 6/7/17.
 */
public class SyscoLabListener implements ITestListener{

    // These are scoped as protected so that anyone can extend this and create a custom listener
    protected static int testCount = 0;
    protected static ITestNGMethod[] iTestNGMethods;
    protected static JsonArray elements = new JsonArray();
    protected static JsonArray skippedTests = new JsonArray();
    public static  byte[] screenShotData;
    protected static SyscoLabUI syscoLabUI;
    protected static WebDriver driver;

    private static final String SUCCESS_WHILE_TAKING_SCREENSHOT_UPON_FAILURE = "SUCCESS: while taking screenshot upon failure...";
    private static final String ERROR_WHILE_TAKING_SCREENSHOT_UPON_FAILURE = "ERROR: while taking screenshot upon failure...";


    /**
     * This is the default constructor
     */
    public SyscoLabListener() {
        testCount = 0;
        elements = new JsonArray();
        skippedTests = new JsonArray();
    }

    /**
     * This is the constructor with the driver assigned
     * @param syscoLabUI driver
     */
    public SyscoLabListener(SyscoLabUI syscoLabUI) {
        SyscoLabListener.syscoLabUI = syscoLabUI;
        testCount = 0;
        elements = new JsonArray();
        skippedTests = new JsonArray();
    }

    /**
     * This will set the driver to the listener
     * @param driver syscolab driver
     */
    public static void setListenerDriver(WebDriver driver) {
        if (syscoLabUI == null) {
            syscoLabUI = new SyscoLabUI();
        }
        if (syscoLabUI.getDriver() == null) {
            syscoLabUI.driver = (RemoteWebDriver) driver;
        }

    }

    /**
     * This will be overridden On test start event
     * @param iTestResult iTestResult instance
     */
    @Override
    public void onTestStart(ITestResult iTestResult) {
        //onTestStart
    }

    /**
     * This will be overridden in Success event
     * @param iTestResult iTestResult instance
     */
    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        JsonObject element = null;
        try {
            element = SyscoLabReporting.getElement(iTestResult, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (element != null)
            elements.add(element);
    }

    /**
     * This will be overridden in Failed event and take a screenshot of the failure as well
     * Web driver screenshot method and SUS screenshot method are embedded
     * @param iTestResult iTestResult instance
     */
    @Override
    public void onTestFailure(ITestResult iTestResult) {
        byte[] screenShotData = null;
        try {
            if(AS400.sb == null){
                if (syscoLabUI != null) {
                    try {
                        screenShotData = syscoLabUI.getScreenShot();
                        LoggerUtil.logINFO(SUCCESS_WHILE_TAKING_SCREENSHOT_UPON_FAILURE);
                    } catch (Exception e) {
                        LoggerUtil.logERROR(ERROR_WHILE_TAKING_SCREENSHOT_UPON_FAILURE,e.getCause());
                        e.printStackTrace();
                    }
                }

            }
            else if (AS400.sb != null) {
                As400ScreenshotUtil as400ScreenshotUtil = new As400ScreenshotUtil();
                if (syscoLabUI != null) {
                    //this will work if Web Driver up and SUS is also up
                    if (syscoLabUI.getDriver().getSessionId() != null) {
                        try {
                            screenShotData = syscoLabUI.getScreenShot();
                            LoggerUtil.logINFO(SUCCESS_WHILE_TAKING_SCREENSHOT_UPON_FAILURE);
                        } catch (Exception e) {
                            LoggerUtil.logERROR(ERROR_WHILE_TAKING_SCREENSHOT_UPON_FAILURE,e.getCause());
                            e.printStackTrace();
                        }
                    }
                    //this will work if Web Driver is quit and SUS is up
                    else {
                        try {

                            screenShotData = as400ScreenshotUtil.captureAS400ScreenShot();
                            LoggerUtil.logINFO("SUCCESS: while taking screenshot upon failure of SUS...");
                        } catch (Exception var4) {
                            LoggerUtil.logERROR("ERROR: while taking screenshot upon failure of SUS...",var4.getCause());
                            var4.printStackTrace();
                        }
                    }

                } else {
                    try {
                        screenShotData = as400ScreenshotUtil.captureAS400ScreenShot();
                        LoggerUtil.logINFO("SUCCESS: while taking screenshot upon failure of SUS...");
                    } catch (Exception var4) {
                        LoggerUtil.logERROR("ERROR: while taking screenshot upon failure of SUS...",var4.getCause());
                        var4.printStackTrace();
                    }
                }
            } else {
                screenShotData = null;
            }
            JsonObject element = null;
            try {
                element = SyscoLabReporting.getElement(iTestResult, screenShotData);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (element != null)
                elements.add(element);
        } catch (NoClassDefFoundError error) {
            //this will work when SUS is not involved
            if (syscoLabUI != null) {
                try {
                    screenShotData = syscoLabUI.getScreenShot();
                    LoggerUtil.logINFO(SUCCESS_WHILE_TAKING_SCREENSHOT_UPON_FAILURE);
                } catch (Exception b) {
                    LoggerUtil.logERROR(ERROR_WHILE_TAKING_SCREENSHOT_UPON_FAILURE,b.getCause());
                    b.printStackTrace();
                }
            }
            else {
                screenShotData = null;
            }
            JsonObject element = null;
            try {
                element = SyscoLabReporting.getElement(iTestResult, screenShotData);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (element != null)
                elements.add(element);
        }
    }

    /**
     * This will be overridden in Skipped event and take a screenshot of the skipped test case as well
     * @param iTestResult iTestResult instance
     */
    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        byte[] screenShotData = null;
        if (syscoLabUI == null) {
            screenShotData = null;
        } else {
            try {
                screenShotData = syscoLabUI.getScreenShot();
                LoggerUtil.logINFO("SUCCESS: while taking screenshot upon skip...");
            }catch (Exception e){
                LoggerUtil.logINFO("ERROR: while taking screenshot upon skip...");
                e.printStackTrace();
            }
        }
        JsonObject element = null;
        try {
            element = SyscoLabReporting.getElement(iTestResult, screenShotData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (element != null)
            elements.add(element);
    }

    /**
     * This will be overridden in Failed but within success range event
     * @param iTestResult iTestResult instance
     */
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        //onTestFailedButWithinSuccessPercentage
    }

    /**
     * This will be overridden on start event
     * @param iTestContext iTestContext instance
     */
    @Override
    public void onStart(ITestContext iTestContext) {
        //onStart
    }

    /**
     * This will be overridden on finish event
     * @param iTestContext iTestContext instance
     */
    @Override
    public void onFinish(ITestContext iTestContext) {
        //onFinish
    }

    /**
     * This will return a json array of results
     * @return results json array
     */
    public static JsonArray getResults() {
        return elements;
    }


    /**
     * This will replace the test name with user given name
     * @param testName Test Name
     */
    public static void setTestName(String testName) {
        if (testName == null || !testName.equals("")){
            JsonElement jsonElement = elements.get(0);
            ((JsonObject) jsonElement).get("name");
            Gson gson = new Gson();
            Type stringObjectMap = new TypeToken<Map<String, Object>>(){}.getType();
            Map<String,Object> map = gson.fromJson(jsonElement, stringObjectMap);
            map.remove("name");
            map.put("name", testName);
            JsonElement gsonString = gson.toJsonTree(map, stringObjectMap);
            elements.remove(0);
            elements.add(gsonString);
        }
    }

    /**
     * This will return the last executed test case ID
     * @return last executed testcase ID
     */
    /*public static String getLastExecutedTCID() {
        return RestUtil.getValue(SyscoLabListener.getResults().get(SyscoLabListener.getResults().size() - 1).toString(),"id");
    }*/
    public static String getLastExecutedTCID() {
        String qMetryID = "";
        String descriptionTagWithTC = RestUtil.getValue(SyscoLabListener.getResults().get(SyscoLabListener.getResults().size() - 1).toString(),"name").replace(" ","");
        Pattern qmetryIdPatternWithTC = Pattern.compile("\\S(([A-Z]|[0-9])*+([-]|[_])+TC+([-]|[_])+\\b([0-9])*)");
        Matcher matcherWithTC = qmetryIdPatternWithTC.matcher(descriptionTagWithTC);
        String descriptionTagWithoutTC = RestUtil.getValue(SyscoLabListener.getResults().get(SyscoLabListener.getResults().size() - 1).toString(),"name").split("-")[1].trim();
        Pattern qmetryIdPatternWithoutTC = Pattern.compile("^\\d+$");
        Matcher matcherWithoutTC = qmetryIdPatternWithoutTC.matcher(descriptionTagWithoutTC);
        if (matcherWithTC.find())
        {
            LoggerUtil.logINFO("QMETRY ID : "+matcherWithTC.group(0));
            return qMetryID = matcherWithTC.group(0);
        }else if(matcherWithoutTC.find()) {
            LoggerUtil.logINFO("QMETRY ID : "+matcherWithoutTC.group(0));
            qMetryID = matcherWithoutTC.group(0);
            return (System.getProperty("qmetry.project.code"))+"-TC-"+qMetryID;
        }else{
            throw new RuntimeException ("Please Enter Test Description in Correct Format");
        }

    }

    /**
     * This will return the last executed test case name
     * @return last executed testcase name
     */
    public static String getLastExecutedTCName() {
        return RestUtil.getValue(SyscoLabListener.getResults().get(SyscoLabListener.getResults().size() - 1).toString(),"name").split(" ")[0];
    }
}
