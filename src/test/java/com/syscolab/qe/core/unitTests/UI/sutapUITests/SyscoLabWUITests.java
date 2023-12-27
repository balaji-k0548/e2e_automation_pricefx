package com.syscolab.qe.core.unitTests.UI.sutapUITests;

import com.syscolab.qe.core.ui.SyscoLabUI;
import com.syscolab.qe.core.ui.common.SyscoLabBrowserTypes;
import com.syscolab.qe.core.ui.web.SyscoLabWUI;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * @author Kasun Herath
 */
public class SyscoLabWUITests {
    //This is the test class for SyscoLabWUI class
    //SyscoLabWUI() is the constructor for SyscoLabWUI class which accepts capabilities,chromeoptions and browsertype as parameters

    SyscoLabUI syscoLabUI;

    @Test
    public void testSyscoLabWUIWithBrowserType() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME);
        Assert.assertEquals(syscoLabUI.driver.getCapabilities().getBrowserName(), "chrome");
    }

    @Test
    public void testSyscoLabWUIWithVersion() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, "115.0");
        Assert.assertEquals(syscoLabUI.driver.getCapabilities().getBrowserName(), "chrome");
    }

    @Test
    public void testSyscoLabWUIWithObject() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setCapability("javascriptEnabled", true);
        Object options = chromeOptions;
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, options);
        Assert.assertEquals(syscoLabUI.driver.getCapabilities().getBrowserName(), "chrome");
    }


    @AfterMethod
    public void tearDown() {
        syscoLabUI.quit();
    }
}
