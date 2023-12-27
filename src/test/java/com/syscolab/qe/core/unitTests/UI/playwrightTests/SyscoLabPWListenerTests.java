package com.syscolab.qe.core.unitTests.UI.playwrightTests;

import com.syscolab.qe.core.playwright.reporting.SyscoLabPWListener;
import com.syscolab.qe.core.playwright.ui.BaseBrowser;
import com.syscolab.qe.core.playwright.ui.SyscoLabPW;
import com.syscolab.qe.core.reporting.SyscoLabQCenter;
import com.syscolab.qe.core.reporting.SyscoLabReporting;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.*;
import static com.syscolab.qe.core.unitTests.common.UnitTestConstants.SYSCO_URL;

/**
 * @author Kasun Herath
 */
@Listeners(SyscoLabPWListener.class)
public class SyscoLabPWListenerTests extends BaseBrowser {

    //This test class is used to write tests for the SyscoLabPWListener class
    //SyscoLabPWListener class is extending ITestListener class and handling the test execution events such as onTestStart, onTestSuccess, onTestFailure, onTestSkipped, onTestFailedButWithinSuccessPercentage
    //SyscoLabPWListener class is used to generate a report of the test execution
    //SyscoLabListener class is also used to feed data into a in-house built reporting tool


    SyscoLabPW page;
    SyscoLabQCenter syscoLabQCenterSUTAP;
    SyscoLabPWListener syscoLabPWListener;


    //below method is to initialize the QCenter object needed to generate the report
    @BeforeTest
    public void init(ITestContext iTestContext) {
        syscoLabQCenterSUTAP = new SyscoLabQCenter();
        iTestContext.setAttribute("feature", "Playwright - PWSS");
    }

    //Below Method will publish results to the QCenter with the below build configurations
    @AfterClass
    public void cleanUp(ITestContext iTestContext) {
        syscoLabQCenterSUTAP.setProjectName("TQE_Demos");
        syscoLabQCenterSUTAP.setEnvironment("QE");
        syscoLabQCenterSUTAP.setRelease("PlaywrightSS");
        syscoLabQCenterSUTAP.setModule(iTestContext.getAttribute("feature").toString());
        syscoLabQCenterSUTAP.setFeature(iTestContext.getAttribute("feature").toString());
        syscoLabQCenterSUTAP.setClassName(iTestContext.getClass().getName());
        SyscoLabReporting.generateJsonFile(syscoLabPWListener.getResults(), syscoLabQCenterSUTAP);
    }

    //test onTestStart method
    @Test(description = "Testing onTestStart method")
    public void testGetPage() {
        page = getPage("Default");
        page.goTo(SYSCO_URL);
        Assert.assertEquals(page.getTitle(), "Sysco | Wholesale Restaurant Food Distributor", "Page title is not correct");
    }

    //similar to above test onTestFailure method
//    @Test(description = "Testing onTestFailure method")
//    public void testOnTestFailure() {
//        page = getPage("Default");
//        page.goTo(SYSCO_URL);
//        Assert.assertEquals(page.getTitle(), "Sysco | Wholesale Restaurant Food Distributor Invalid Title", "Page title is not correct");
//    }

    //similar to above test onTestSkipped method
    @Test(description = "Testing onTestSkipped method")
    public void testOnTestSkipped() {
        page = getPage("Default");
        page.goTo(SYSCO_URL);
        throw new SkipException("Skipping this exception");
    }

    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {
        page.close();
    }

//    @AfterTest(alwaysRun = true)
//    public void assertAll() {
//        softAssert.assertAll();
//    }
}
