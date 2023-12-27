package com.syscolab.qe.core.unitTests.UI.sutapUITests;

import com.syscolab.qe.core.ui.SyscoLabDriver;
import com.syscolab.qe.core.ui.common.SyscoLabBrowserTypes;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * @author Kasun Herath
 */
public class SyscoLabDriverTests {
    //This is the test class for the SyscoLabDriver class
    //SyscoLabDriver class is used to return the driver instance
    //Using the created driver instance, we can perform actions on the browser

    SyscoLabDriver driver;
    RemoteWebDriver driverInstance;

    @Test
    public void testGetPCDriver() {
        driver = new SyscoLabDriver();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("hub.url", "");
        capabilities.setCapability("browserName", "chrome");
        driverInstance = driver.getPCDriver(capabilities);
        Assert.assertEquals(driverInstance != null, true);
    }

    //test getPCDriver method
    @Test
    public void testGetPCDriverWithBrowserType() {
        driver = new SyscoLabDriver();
        driverInstance = driver.getPCDriver("chrome");
        Assert.assertEquals(driverInstance.getCapabilities().getBrowserName(), "chrome");
    }

    //test getPCDriver method with DesiredCapabilities and ChromeOptions
    @Test
    public void testGetPCDriverWithChromeOptions() {
        driver = new SyscoLabDriver();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("hub.url", "");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setCapability("browserName", "chrome");
        driverInstance = driver.getPCDriver(capabilities, chromeOptions);
        Assert.assertEquals(driverInstance != null, true);
    }

    //test getPCDriver method with browserType and browserOptions as an object
    @Test
    public void testGetPCDriverWithBrowserTypeAndBrowserOptions() {
        driver = new SyscoLabDriver();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        Object browserOptions = chromeOptions;
        driverInstance = driver.getPCDriver("chrome", browserOptions);
        Assert.assertEquals(driverInstance != null, true);
    }

    //test getDriver switch case with browserType as chrome
    @Test
    public void testGetDriverWithChrome() {
        driver = new SyscoLabDriver();
        RemoteWebDriver remoteWebDriver = null;
        driverInstance = driver.getDriver(SyscoLabBrowserTypes.CHROME, remoteWebDriver);
        Assert.assertEquals(driverInstance.getCapabilities().getBrowserName(), "chrome");
    }


    //test getDriver switch case with browserType as chrome and capabilities
    @Test
    public void testGetDriverWithChromeAndCapabilities() {
        driver = new SyscoLabDriver();
        RemoteWebDriver remoteWebDriver = null;
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("javascriptEnabled", "true");
        driverInstance = driver.getDriver(SyscoLabBrowserTypes.CHROME, remoteWebDriver, capabilities);
        Assert.assertEquals(driverInstance.getCapabilities().getBrowserName(), "chrome");
    }


    //test getDriver switch case with browserType as chrome and Options as an object
    @Test
    public void testGetDriverWithChromeAndObject() {
        driver = new SyscoLabDriver();
        RemoteWebDriver remoteWebDriver = null;
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setCapability("javascriptEnabled", "true");
        Object options = chromeOptions;
        driverInstance = driver.getDriver(SyscoLabBrowserTypes.CHROME, remoteWebDriver, options);
        Assert.assertEquals(driverInstance.getCapabilities().getBrowserName(), "chrome");
    }


    //test getDriver switch case with browserType as chrome ,remoteWebdriver and version
    @Test
    public void testGetDriverWithChromeAndVersion() {
        driver = new SyscoLabDriver();
        RemoteWebDriver remoteWebDriver = null;
        String version = "116.0.0";
        driverInstance = driver.getDriver(SyscoLabBrowserTypes.CHROME, remoteWebDriver, version);
        Assert.assertEquals(driverInstance.getCapabilities().getBrowserName(), "chrome");
    }





    @AfterMethod
    public void tearDown() {
        driverInstance.quit();
    }

}
