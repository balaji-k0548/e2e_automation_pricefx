package com.syscolab.qe.core.ui.desktop;

import com.syscolab.qe.core.reporting.SyscoLabListener;
import com.syscolab.qe.core.ui.SyscoLabDriver;
import com.syscolab.qe.core.ui.SyscoLabUI;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * This is the class for SyscoLab Desktop UI
 * @author  yoosufm on 6/7/17.
 */
public class SyscoLabDUI extends SyscoLabUI {

    /**
     * This is the constructor to initialize a new driver for Windows desktop and to initialize the syscolab Listener
     * @param desiredCapabilities properties for the browsers
     */
    public SyscoLabDUI(DesiredCapabilities desiredCapabilities){
        driver = new SyscoLabDriver().getWinAppDriver(desiredCapabilities);
        syscoLabListener = new SyscoLabListener(this);
    }
}