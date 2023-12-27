package com.syscolab.qe.core.ui;

import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * This is the util class for SyscoLab Capabilities
 * @author  yoosufm on 6/7/17.
 */
public class SyscoLabCapabilityUtil {

    /**
     * This will return the desired capabilities related to the OS and browser of the PC
     * @param os Operating System
     * @param browser browser type
     * @return desired capabilities
     */
    public static DesiredCapabilities getPCCapabilities(String os, String browser){
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        if(!os.isEmpty())
            desiredCapabilities.setCapability(CapabilityType.PLATFORM, os);

        if(!browser.isEmpty())
            desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME, browser);

        return desiredCapabilities;

    }

    /**
     * This will return the desired capabilities related to android
     * @param id Android Device ID
     * @return desired capabilities
     */
    public static DesiredCapabilities getAndroidCapabilities(String id){
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, id);
        capabilities.setCapability(CapabilityType.BROWSER_NAME, MobileBrowserType.CHROME);
        capabilities.setCapability(CapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 200);
        capabilities.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
        capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
        capabilities.setCapability(MobileCapabilityType.UDID, id);

        return capabilities;

    }

    /**
     * This will return the desired capabilities related to iOS
     * @return desired capabilities
     */
    public static DesiredCapabilities getIOSCapabilities(){
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(CapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 200);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
        capabilities.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
        capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        capabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);

        return capabilities;
    }
}