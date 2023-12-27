package com.syscolab.qe.core.ui.web;

import com.syscolab.qe.core.reporting.SyscoLabListener;
import com.syscolab.qe.core.ui.SyscoLabDriver;
import com.syscolab.qe.core.ui.SyscoLabUI;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * This is the class for SyscoLab Web UI
 * @author yoosufm on 6/7/17.
 */
public class SyscoLabWUI extends SyscoLabUI {

    /**
     * This is the constructor to initialize a new driver and a new SyscoLab Listener with the capabilities passed
     * @param capabilities properties for the browsers
     */
    public SyscoLabWUI(Capabilities capabilities) {
        driver = new SyscoLabDriver().getPCDriver(capabilities);
        syscoLabListener = new SyscoLabListener(this);

    }

    /**
     * This is the constructor to initialize a new driver and a syscoLab Listener with the capabilities and browser type
     * @param capabilities properties for the browsers
     * @param browserType  type of browser
     */
    public SyscoLabWUI(Capabilities capabilities, String browserType) {
        driver = new SyscoLabDriver().getPCDriver(capabilities, browserType);
        syscoLabListener = new SyscoLabListener(this);
    }

    /**
     * This is the constructor to initialize a new driver and a new syscoLab Listener with the passed capabilities and
     * @param capabilities  properties for the browsers
     * @param chromeOptions methods for setting ChromeDriver-specific capabilities
     */
    public SyscoLabWUI(DesiredCapabilities capabilities, ChromeOptions chromeOptions) {
        driver = new SyscoLabDriver().getPCDriver(capabilities, chromeOptions);
        syscoLabListener = new SyscoLabListener(this);
    }

    /**
     * This is the constructor to initialize a new driver and a new syscoLabListener with the passed browser Type
     * @param browserType type of the browser
     */
    public SyscoLabWUI(String browserType) {
        driver = new SyscoLabDriver().getPCDriver(browserType);
        syscoLabListener = new SyscoLabListener(this);
    }

    /**
     * This is the constructor to initialize a new driver and a new syscoLab Listener with the passed browser type and version
     * @param browserType type of browser
     * @param version     version of the browser
     */
    public SyscoLabWUI(String browserType, String version) {
        driver = new SyscoLabDriver().getPCDriver(browserType, version);
        syscoLabListener = new SyscoLabListener(this);
    }

    /**
     * This is the constructor to initialize a new driver and a new syscoLab Listener with browser type and capabilities
     * @param browserType  type of the browser
     * @param capabilities properties for the browsers
     */
    public SyscoLabWUI(String browserType, Capabilities capabilities) {
        driver = new SyscoLabDriver().getPCDriver(browserType, capabilities);
        syscoLabListener = new SyscoLabListener(this);
    }

    /**
     * This is the constructor to initialize a new driver and a new syscoLab Listener with browser type and options
     * @param browserType  type of the browser
     * @param browserOptions properties for the browsers
     */
    public SyscoLabWUI(String browserType, Object browserOptions) {
        driver = new SyscoLabDriver().getPCDriver(browserType, browserOptions);
        syscoLabListener = new SyscoLabListener(this);
    }
}