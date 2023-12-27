package com.syscolab.qe.core.unitTests.UI.sutapUITests;

import com.syscolab.qe.core.ui.SyscoLabCapabilityUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Kasun Herath
 */
public class SyscoLabCapabilityUtilTests {
    //This is the test class for SyscoLabCapabilityUtil
    //This class returns the desired capabilities related to the OS and browser of the PC
    //That returned capabilities are used in the SyscoLabWUI class


    @Test
    public void testGetPCCapabilities(){
        Assert.assertEquals(SyscoLabCapabilityUtil.getPCCapabilities("Windows 10", "Chrome").getPlatformName().toString(), "Windows 10");
        Assert.assertEquals(SyscoLabCapabilityUtil.getPCCapabilities("Windows 10", "Chrome").getBrowserName().toString(), "Chrome");
    }

    //test getAndroidCapabilities method
    @Test
    public void testGetAndroidCapabilities(){
        Assert.assertEquals(SyscoLabCapabilityUtil.getAndroidCapabilities("123456789").getCapability("deviceName").toString(),"123456789");
        Assert.assertEquals(SyscoLabCapabilityUtil.getAndroidCapabilities("123456789").getBrowserName().toString(), "Chrome");
        Assert.assertEquals(SyscoLabCapabilityUtil.getAndroidCapabilities("123456789").getPlatformName().toString(), "ANDROID");
        Assert.assertEquals(SyscoLabCapabilityUtil.getAndroidCapabilities("123456789").getCapability("newCommandTimeout").toString(),"200");
        Assert.assertEquals(SyscoLabCapabilityUtil.getAndroidCapabilities("123456789").getCapability("udid").toString(),"123456789");
    }

    //test getIOSCapabilities method
    @Test
    public void testGetIOSCapabilities(){
        Assert.assertEquals(SyscoLabCapabilityUtil.getIOSCapabilities().getPlatformName().toString(), "IOS");
        Assert.assertEquals(SyscoLabCapabilityUtil.getIOSCapabilities().getCapability("newCommandTimeout").toString(), "200");
        Assert.assertEquals(SyscoLabCapabilityUtil.getIOSCapabilities().getCapability("automationName").toString(), "XCuiTest");
        Assert.assertEquals(SyscoLabCapabilityUtil.getIOSCapabilities().getCapability("noReset").toString(), "true");
        Assert.assertEquals(SyscoLabCapabilityUtil.getIOSCapabilities().getCapability("fullReset").toString(), "false");
        Assert.assertEquals(SyscoLabCapabilityUtil.getIOSCapabilities().getCapability("noReset").toString(), "true");
    }


}
