package com.syscolab.qe.core.playwright.reporting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.microsoft.playwright.Page;
import com.syscolab.qe.core.common.LoggerUtil;
import com.syscolab.qe.core.playwright.common.PlayWrightConstants;
import com.syscolab.qe.core.playwright.ui.BaseBrowser;
import com.syscolab.qe.core.playwright.ui.SyscoLabPW;
import com.syscolab.qe.core.reporting.SyscoLabReporting;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.IOException;


/**
 * @author Kasun Herath
 */
public class SyscoLabPWListener extends BaseBrowser implements ITestListener {
    protected static JsonArray elements = new JsonArray();
    private static final String SUCCESS_WHILE_TAKING_SCREENSHOT_UPON_FAILURE = "SUCCESS: while taking screenshot upon failure...";
    private static final String ERROR_WHILE_TAKING_SCREENSHOT_UPON_FAILURE = "ERROR: while taking screenshot upon failure...";
    private static final String ERROR_WHILE_REPORTING = "ERROR: while reporting results through element/elements...";
    JsonObject element = null;
    byte[] bufferImage = null;

    /**
     * Common method to handle QCenter Reporting mechanism
     * @param iTestResult - An instance of iTestResult
     */
    public void qCenterReport(ITestResult iTestResult) {
        try {
            SyscoLabPW page = (SyscoLabPW) iTestResult.getAttribute(PlayWrightConstants.SYSCO_LAB_PW_REFERENCE);
            bufferImage = page.screenShot();
            LoggerUtil.logINFO(SUCCESS_WHILE_TAKING_SCREENSHOT_UPON_FAILURE);
        } catch (NullPointerException | ClassCastException n) {
            Page page = (Page) iTestResult.getTestContext().getAttribute(PlayWrightConstants.SYSCO_LAB_PW_REFERENCE);
            bufferImage = page.screenshot();
            LoggerUtil.logINFO(SUCCESS_WHILE_TAKING_SCREENSHOT_UPON_FAILURE);
        } catch (Exception e) {
            LoggerUtil.logERROR(ERROR_WHILE_TAKING_SCREENSHOT_UPON_FAILURE, e.getCause());
        }

        try {
            element = SyscoLabReporting.getElement(iTestResult, bufferImage);
            elements.add(element);
        } catch (IOException e) {
            LoggerUtil.logERROR(ERROR_WHILE_REPORTING, e.getCause());
        }
    }

    /**
     * Marks test case as Passed on successful Assertions
     * @param iTestResult - An instance of iTestResult
     */
    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        try {
            element = SyscoLabReporting.getElement(iTestResult, null);
        } catch (IOException e) {
            LoggerUtil.logERROR(ERROR_WHILE_TAKING_SCREENSHOT_UPON_FAILURE, e.getCause());
        }
        if (element != null)
            elements.add(element);
    }

    /**
     * Takes Screenshot on failure
     * Logic 1 - If the User has used getPage(); only once , then by default that page is set to be taken for screenshots , and if they call getPage(); again that page is overridden
     * Logic 2 - If the User wishes to work with multiple pages , can call reportingPage(); and pass required page as parameter
     * @param iTestResult <code>ITestResult</code> containing information about the run test
     */
    @Override
    public void onTestFailure(ITestResult iTestResult) {
        qCenterReport(iTestResult);
    }

    /**
     * Takes Screenshot on skip tests
     * Logic 1 - If the User has used getPage(); only once , then by default that page is set to be taken for screenshots , and if they call getPage(); again that page is overridden
     * Logic 2 - If the User wishes to work with multiple pages , can call reportingPage(); and pass required page as parameter
     * @param iTestResult <code>ITestResult</code> containing information about the run test
     */
    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        qCenterReport(iTestResult);
    }

    /**
     * Returns results to be published to reporting
     * @return - A json array object
     */
    public static JsonArray getResults() {
        return elements;
    }
}
