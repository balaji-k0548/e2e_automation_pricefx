package com.syscolab.qe.core.playwright.ui;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import com.syscolab.qe.core.playwright.common.PlayWrightConstants;
import java.util.Properties;

/**
 * @author Kasun Herath
 */
public class PlayWrightFactory {
    protected static Properties prop;
    Browser browser;
    protected ThreadLocal<Playwright> playwright = new ThreadLocal<>(); //For Thread-Local Parallel Execution
    private BrowserContext context;



    public PlayWrightFactory() {
        //empty
    }

    /**
     * Initialized required browser , Up-To 2 browsers are supported simultaneously
     * @param browserName - Accepts browser name through PlayWrightConstants
     */
    public PlayWrightFactory(String browserName) {
        playwright.set(Playwright.create());
        playwright.get().selectors().setTestIdAttribute("id"); //changed getByTestId() to use "id"
        switch (browserName.toLowerCase()) {
            case "chrome":
                browser = playwright.get().chromium().launch(new BrowserType.LaunchOptions().setChannel(PlayWrightConstants.CHROME).setHeadless(PlayWrightConstants.HEADLESS_MODE));
                break;
            case "webkit":
                browser = playwright.get().webkit().launch(new BrowserType.LaunchOptions().setChannel(PlayWrightConstants.WEBKIT).setHeadless(PlayWrightConstants.HEADLESS_MODE));
                break;
            case "safari":
                browser = playwright.get().webkit().launch(new BrowserType.LaunchOptions().setChannel(PlayWrightConstants.SAFARI).setHeadless(PlayWrightConstants.HEADLESS_MODE));
                break;
            case "chromium":
                browser = playwright.get().chromium().launch(new BrowserType.LaunchOptions().setChannel(PlayWrightConstants.CHROMIUM).setHeadless(PlayWrightConstants.HEADLESS_MODE));
                break;
            case "firefox":
                browser = playwright.get().firefox().launch(new BrowserType.LaunchOptions().setChannel(PlayWrightConstants.FIREFOX).setHeadless(PlayWrightConstants.HEADLESS_MODE));
                break;
            case "edge":
                browser = playwright.get().chromium().launch(new BrowserType.LaunchOptions().setChannel(PlayWrightConstants.MSEDGE).setHeadless(PlayWrightConstants.HEADLESS_MODE));
                break;
        }
    }
}
