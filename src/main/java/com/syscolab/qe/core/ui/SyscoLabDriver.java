package com.syscolab.qe.core.ui;

import com.syscolab.qe.core.ui.common.SyscoLabBrowserTypes;
import com.syscolab.qe.core.ui.common.SyscoLabCoreConstants;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.windows.WindowsDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import io.github.bonigarcia.wdm.managers.SafariDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.EnumMap;
import java.util.Map;

/**
 * This class will handle the Remote Web Driver related methods
 * @author  yoosufm on 6/7/17.
 */
public class SyscoLabDriver {

    protected static Map<DriverManagerType, WebDriverManager> instanceMap = new EnumMap(DriverManagerType.class);

    public static final String HUB_URL = "hub.url";

    /**
     * This will return the Remote Web Driver with the passed capabilities to the driver
     * @param capabilities  properties for the browsers
     * @return Remote Web Driver
     */
    public  RemoteWebDriver getPCDriver(Capabilities capabilities){
        RemoteWebDriver driver = null;

        if (capabilities != null){
            if (capabilities.getCapability(HUB_URL).equals("") || capabilities.getCapability(HUB_URL)==null){
                driver = new ChromeDriver(new ChromeOptions().merge(capabilities));
            }else {
                try {
                    driver = new RemoteWebDriver(new URL(capabilities.getCapability(HUB_URL).toString()), capabilities);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }else {
            driver = new ChromeDriver();
        }
        return driver;
    }

    /**
     * This will return the Remote Web Driver with the passed capabilities and Chrome options to the driver as well
     * @param capabilities  properties of browsers to perform cross browser testing of web applications
     * @param chromeOptions methods for setting ChromeDriver-specific capabilities
     * @return Remote Web Driver
     */
    public  RemoteWebDriver getPCDriver(DesiredCapabilities capabilities, ChromeOptions chromeOptions){
        RemoteWebDriver driver = null;

        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

        if (capabilities.getCapability(HUB_URL).equals("") || capabilities.getCapability(HUB_URL)==(null)){
            driver = new ChromeDriver(new ChromeOptions().merge(capabilities));
        }else {
            try {
                driver = new RemoteWebDriver(new URL(capabilities.getCapability(HUB_URL).toString()), capabilities);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        return driver;
    }

    /**
     *This will return the Remote Web Driver with the passed capabilities related to the passed browser type
     * @param capabilities  properties for the browsers
     * @param browserType browser type
     * @return Remote Web Driver
     */
    public  RemoteWebDriver getPCDriver(Capabilities capabilities, String browserType){
        RemoteWebDriver driver = null;

        if (capabilities != null){
            if (!capabilities.getCapability(HUB_URL).equals("") || !(capabilities.getCapability(HUB_URL) ==null)) {
                try {
                    driver = new RemoteWebDriver(new URL(SyscoLabCoreConstants.HUB_URL), capabilities);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } else {
                driver = getDriver(browserType, driver);
            }
        }else {
            driver = getDriver(browserType, driver);
        }

        return driver;
    }

    /**
     * This will return the Remote web driver of the passed browser type
     * @param browserType browser type
     * @return RemoteWebDriver
     */
    public RemoteWebDriver getPCDriver(String browserType) {
        RemoteWebDriver driver = null;
        driver = getDriver(browserType, driver);
        return driver;
    }

    /**
     *This will return the Remote Web Driver with the passed capabilities related to the passed browser type
     * @param capabilities  properties for the browsers
     * @param browserType browser type
     * @return Remote Web Driver
     */
    public RemoteWebDriver getPCDriver(String browserType, Capabilities capabilities) {
        RemoteWebDriver driver = null;
        driver = getDriver(browserType, driver, capabilities);
        return driver;
    }

    /**
     *This will return the Remote Web Driver with the passed capabilities related to the passed browser type
     * @param browserType browser type
     * @param browserOptions  properties for the browsers
     * @return Remote Web Driver
     */
    public RemoteWebDriver getPCDriver(String browserType, Object browserOptions) {
        RemoteWebDriver driver = null;
        driver = this.getDriver(browserType, driver, browserOptions);
        return driver;
    }

    /**
     * This will return the remote web driver with the passed browser type and version
     * @param browserType browser type
     * @param version version of browser
     * @return remote web driver
     */
    public RemoteWebDriver getPCDriver(String browserType, String version) {
        RemoteWebDriver driver = null;
        driver = getDriver(browserType, driver, version);
        return driver;
    }

    /**
     * This will return the driver by the passed browser type
     * @param browserType type of the browser
     * @param driver driver instance
     * @return remote web driver
     */
    public RemoteWebDriver getDriver(String browserType, RemoteWebDriver driver) {
        if (browserType.equalsIgnoreCase(SyscoLabBrowserTypes.IE)) {
            WebDriverManager.iedriver().setup();
            driver = new InternetExplorerDriver();
        } else if (browserType.equalsIgnoreCase(SyscoLabBrowserTypes.FIREFOX)) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browserType.equalsIgnoreCase(SyscoLabBrowserTypes.CHROME)) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browserType.equalsIgnoreCase(SyscoLabBrowserTypes.EDGE)) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else if (browserType.equalsIgnoreCase(SyscoLabBrowserTypes.SAFARI)) {
            this.safariDriver().setup();
            driver = new SafariDriver();
        }
        return driver;
    }

    /**
     * This will return the remote web driver by the passed browser type and with the capabilities passed
     * @param browserType type of the browser
     * @param driver driver instance
     * @param capabilities properties for the browsers
     * @return RemoteWebDriver
     */
    public RemoteWebDriver getDriver(String browserType, RemoteWebDriver driver, Capabilities capabilities) {
        if (browserType.equalsIgnoreCase(SyscoLabBrowserTypes.IE)) {
            WebDriverManager.iedriver().setup();
            InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions(capabilities);
            driver = new InternetExplorerDriver(internetExplorerOptions);
        } else if (browserType.equalsIgnoreCase(SyscoLabBrowserTypes.FIREFOX)) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions firefoxOptions = new FirefoxOptions(capabilities);
            driver = new FirefoxDriver(firefoxOptions);
        } else if (browserType.equalsIgnoreCase(SyscoLabBrowserTypes.CHROME)) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions chromeOptions = new ChromeOptions().merge(capabilities);
            driver = new ChromeDriver(chromeOptions);
        } else if (browserType.equalsIgnoreCase(SyscoLabBrowserTypes.EDGE)) {
            WebDriverManager.edgedriver().setup();
            EdgeOptions edgeOptions = new EdgeOptions().merge(capabilities);
            driver = new EdgeDriver(edgeOptions);
        } else if (browserType.equalsIgnoreCase(SyscoLabBrowserTypes.SAFARI)) {
            this.safariDriver().setup();
            SafariOptions safariOptions = new SafariOptions(capabilities);
            driver = new SafariDriver(safariOptions);
        }

        return driver;
    }

    /**
     * This will return the remote web driver by the passed browser type and with the capabilities passed
     * @param browserType type of the browser
     * @param driver driver instance
     * @param options properties for the browsers
     * @return RemoteWebDriver
     */
    public RemoteWebDriver getDriver(String browserType, RemoteWebDriver driver, Object options) {
        if (browserType.equalsIgnoreCase(SyscoLabBrowserTypes.IE)) {
            WebDriverManager.iedriver().setup();
            driver = new InternetExplorerDriver((InternetExplorerOptions) options);
        } else if (browserType.equalsIgnoreCase(SyscoLabBrowserTypes.FIREFOX)) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver((FirefoxOptions) options);
        } else if (browserType.equalsIgnoreCase(SyscoLabBrowserTypes.CHROME)) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver((ChromeOptions) options);
        } else if (browserType.equalsIgnoreCase(SyscoLabBrowserTypes.EDGE)) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver((EdgeOptions) options);
        } else if (browserType.equalsIgnoreCase(SyscoLabBrowserTypes.SAFARI)) {
            this.safariDriver().setup();
            driver = new SafariDriver((SafariOptions) options);
        }
        return driver;
    }

    /**
     * This will return the remote web driver for the passed type of the browser and version of the browser
     * @param browserType type of the browser
     * @param driver driver instance
     * @param version version of the browser
     * @return remote web driver
     */
    public RemoteWebDriver getDriver(String browserType, RemoteWebDriver driver, String version) {
        if (browserType.equalsIgnoreCase(SyscoLabBrowserTypes.IE)) {
            WebDriverManager.iedriver().browserVersion(version).setup();
            driver = new InternetExplorerDriver();
        } else if (browserType.equalsIgnoreCase(SyscoLabBrowserTypes.FIREFOX)) {
            WebDriverManager.firefoxdriver().browserVersion(version).setup();
            driver = new FirefoxDriver();
        } else if (browserType.equalsIgnoreCase(SyscoLabBrowserTypes.CHROME)) {
            WebDriverManager.chromedriver().browserVersion(version).setup();
            driver = new ChromeDriver();
        } else if (browserType.equalsIgnoreCase(SyscoLabBrowserTypes.EDGE)) {
            WebDriverManager.edgedriver().browserVersion(version).setup();
            driver = new EdgeDriver();
        } else if (browserType.equalsIgnoreCase(SyscoLabBrowserTypes.SAFARI)) {
            this.safariDriver().browserVersion(version).setup();
            driver = new SafariDriver();
        }

        return driver;
    }

    /**
     * This will return the appium driver for android with the passed capabilities
     * @param desiredCapabilities properties for the browsers
     * @return appium driver
     */
    public  AppiumDriver getAndroidDriver(Capabilities desiredCapabilities){
        AppiumDriver driver = null;

        try {
            driver = new AndroidDriver(new URL(SyscoLabCoreConstants.APPIUM_HUB_URL), desiredCapabilities );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return driver;

    }

    /**
     * This will return the appium driver for iOS with the passed capabilities
     * @param desiredCapabilities properties for the browsers
     * @return appium driver
     */
    public  AppiumDriver getIOSDriver(Capabilities desiredCapabilities){
        AppiumDriver driver = null;

        try {
            driver = new IOSDriver(new URL(SyscoLabCoreConstants.APPIUM_HUB_URL), desiredCapabilities );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return driver;

    }

    /**
     * This will return the windows driver with the passed desired capabilities
     * @param desiredCapabilities properties of browsers to perform cross browser testing of web applications
     * @return WindowsDriver
     */
    public WindowsDriver getWinAppDriver(DesiredCapabilities desiredCapabilities){
        WindowsDriver driver = null;

        try {
            driver = new WindowsDriver(new URL(desiredCapabilities.getCapability("winappdriver.url").toString()), desiredCapabilities );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return driver;

    }

    /**
     * This will return the Safari driver if it doesn't already exist
     * @return safari driver
     */
    public static synchronized WebDriverManager safariDriver() {
        instanceMap.putIfAbsent(DriverManagerType.SAFARI, new SafariDriverManager());
        return (WebDriverManager)instanceMap.get(DriverManagerType.SAFARI);
    }
}