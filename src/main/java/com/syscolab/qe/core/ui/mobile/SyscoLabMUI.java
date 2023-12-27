package com.syscolab.qe.core.ui.mobile;

import com.syscolab.qe.core.reporting.SyscoLabListener;
import com.syscolab.qe.core.ui.SyscoLabUI;
import com.syscolab.qe.core.ui.SyscoLabDriver;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * This is the class for SyscoLab Mobile UI
 * @author yoosufm on 6/7/17.
 */
public class SyscoLabMUI extends SyscoLabUI {

    public static final String PLATFORM_NAME = "platformName";

    /**
     * This is the constructor to initialize a new driver depending on the platform name and to initialize the syscolab Listener
     * @param capabilities properties for the browsers
     */
    public SyscoLabMUI(DesiredCapabilities capabilities) {

        if (capabilities.getCapability(PLATFORM_NAME).equals(MobilePlatform.ANDROID)) {
            driver = new SyscoLabDriver().getAndroidDriver(capabilities);
        } else if (capabilities.getCapability(PLATFORM_NAME).equals(MobilePlatform.IOS)) {
            driver = new SyscoLabDriver().getIOSDriver(capabilities);
        }

        syscoLabListener = new SyscoLabListener(this);
    }
}