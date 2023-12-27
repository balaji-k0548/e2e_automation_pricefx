package com.syscolab.qe.core.playwright.common;

/**
 * @author Kasun Herath
 */
public class PlayWrightConstants {
    public static final String CHROME = "chrome";
    public static final String FIREFOX = "firefox";
    public static final String WEBKIT = "webkit";
    public static final String SAFARI = "safari";
    public static final String CHROMIUM = "chromium";
    public static final String MSEDGE = "msedge";
    public static final String ENTER_KEY = "Enter";
    public static Boolean HEADLESS_MODE;
    public static String SYSTEM_BROWSER1;
    public static String SYSTEM_BROWSER2;

    public static Boolean ABORT_MEDIA;
    public static final String SYSCO_LAB_PW_REFERENCE="SyscoLabPW_Reference";

    /**
     * Checks for user defined Browser1,Browser2 and HeadLess Mode , assigns default if not set
     */
    static {
        HEADLESS_MODE = System.getProperty("HeadlessMode")==null? false : System.getProperty("HeadlessMode").equalsIgnoreCase("true") ? true : false;
        SYSTEM_BROWSER1 = System.getProperty("Browser1")==null?PlayWrightConstants.CHROME:System.getProperty("Browser1").isEmpty()?PlayWrightConstants.CHROME:System.getProperty("Browser1");
        SYSTEM_BROWSER2 = System.getProperty("Browser2")==null?PlayWrightConstants.FIREFOX:System.getProperty("Browser2").isEmpty()?PlayWrightConstants.FIREFOX:System.getProperty("Browser2");
        ABORT_MEDIA = System.getProperty("AbortMedia")==null? false : System.getProperty("AbortMedia").equalsIgnoreCase("true") ? true : false;
    }
}
