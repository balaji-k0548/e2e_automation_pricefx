package com.syscolab.qe.core.reporting.common;

import com.syscolab.qe.core.reporting.SyscoLabListener;
import com.syscolab.qe.core.reporting.SyscoLabQCenter;
import com.syscolab.qe.core.reporting.SyscoLabReporting;
import com.syscolab.qe.core.ui.functions.LoginSample;
import org.testng.ITestContext;
import org.testng.annotations.*;

@Listeners(SyscoLabListener.class)
public class TestBase {
    protected SyscoLabQCenter syscoLabQCenter;
    protected SyscoLabListener testListeners;
    @BeforeTest
    public void init() {
        syscoLabQCenter = new SyscoLabQCenter();
    }

    @AfterClass
    public void cleanUpBaseClass(ITestContext iTestContext) {
        try {
            syscoLabQCenter.setProjectName("TQE_Demos");
            syscoLabQCenter.setEnvironment("DEV");
            syscoLabQCenter.setRelease("SUTAP-Screenshot-issue");
            syscoLabQCenter.setModule(iTestContext.getAttribute("feature").toString());
            syscoLabQCenter.setFeature(iTestContext.getAttribute("feature").toString());
            syscoLabQCenter.setClassName(iTestContext.getClass().getName());
            SyscoLabReporting.generateJsonFile(testListeners.getResults(), syscoLabQCenter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterTest
    public void clean(){
        LoginSample.quit();
    }
}