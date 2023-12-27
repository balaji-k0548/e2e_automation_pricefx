package com.syscolab.qe.core.reporting.functions;

import com.syscolab.qe.core.reporting.common.TestBase;
import com.syscolab.qe.core.ui.functions.LoginSample;
import org.junit.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ScreenShotTest extends TestBase {

    @BeforeClass
    public void init(ITestContext iTestContext) {
        iTestContext.setAttribute("feature", "SUTAP_Test - SUTAP_Screenshot_issue");
    }

    @Test(priority = 1, description = "Test_01")
    public void failureTest() {
        LoginSample.loadHomePage();
        Assert.fail("Screenshot capturing testing purposes");
    }
}
