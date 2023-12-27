package com.syscolab.qe.core.ui.tests;

import com.google.gson.JsonArray;
import com.syscolab.qe.core.common.Constants;
import com.syscolab.qe.core.reporting.SyscoLabListener;
import com.syscolab.qe.core.reporting.SyscoLabQCenter;
import com.syscolab.qe.core.reporting.SyscoLabReporting;
import com.syscolab.qe.core.ui.SyscoLabUI;
import com.syscolab.qe.core.ui.common.SyscoLabBrowserTypes;
import com.syscolab.qe.core.ui.web.SyscoLabWUI;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;

import static com.syscolab.qe.core.common.Constants.*;

/**
 * Created by yoosufm on 6/7/17.
 */
public class UITestBase extends SyscoLabListener {

    static protected SyscoLabUI syscoLabUI;
    private SyscoLabQCenter syscoLabQCenter;
    protected SyscoLabListener testListeners;
    static JsonArray elements;

    //**DELETE START*//*
    /*@BeforeSuite
    public void setItems(){
        System.setProperty("qmetry.open.api.key", QMETRY_OPEN_API_KEY);
        System.setProperty("qmetry.project.code", QMETRY_TC_PREFIX);
        System.setProperty("qmetry.tc.version", QMETRY_TC_VERSION);
        System.setProperty("qmetry.test.id", "MTMT-TC-34");
        System.setProperty("qmetry.test.cycle.id", "MTMT-TR-60");
        System.setProperty("qmetry.api.key", Constants.QMETRY_API_KEY);
    }*/

    /**
     * DELETE END
     */
    @BeforeTest
    /**
     * This initializes the variables
     */
    public void init() {

        syscoLabQCenter = new SyscoLabQCenter();
        syscoLabUI = new SyscoLabWUI("",SyscoLabBrowserTypes.CHROME);
        elements = new JsonArray();
    }

    @AfterClass
    /**
     * This does the Clean-up activity
     * @param iTestContext Test Context
     * @exception Exception
     */
    public void cleanUpBaseClass(ITestContext iTestContext) {
        try {

            syscoLabQCenter.setProjectName("QCenter Test");
            syscoLabQCenter.setEnvironment("QE");
            syscoLabQCenter.setRelease("test_build_new");
            syscoLabQCenter.setModule(iTestContext.getAttribute("feature").toString());
            syscoLabQCenter.setFeature(iTestContext.getAttribute("feature").toString());
            syscoLabQCenter.setClassName(iTestContext.getClass().getName());


            SyscoLabReporting.generateJsonFile(testListeners.getResults(), syscoLabQCenter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
