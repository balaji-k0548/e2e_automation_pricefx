package com.syscolab.qe.core.ui.pages;

import com.syscolab.qe.core.ui.SyscoLabUI;
import com.syscolab.qe.core.ui.common.SyscoLabBrowserTypes;
import com.syscolab.qe.core.ui.common.UIConstants;
import com.syscolab.qe.core.ui.web.SyscoLabWUI;
import org.openqa.selenium.By;

/**
 * Created by yoosufm on 6/7/17.
 */
public class LoginPageSample {

    public static SyscoLabUI syscoLabUI;
    By txtUsername = By.id("email");
    By txtPassword = By.id("password");
    By btnLogin = By.id("submit-button");

    /**
     * This will load the page
     */
    public void loadLoginPage(){
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME);
        syscoLabUI.maximizeWindow();
        syscoLabUI.navigateTo(UIConstants.GOOGLE_URL);
    }

    /**
     * This will enter the username in the respective input field
     * @param userName Username
     */
    public void enterUserName(String userName) {
        syscoLabUI.sendKeys(txtUsername, userName);
    }

    /**
     * This will enter the password in the respective input field
     * @param password Password
     */
    public void enterPassword(String password) {
        syscoLabUI.sendKeys(txtPassword, password);
    }

    /**
     * This will click the login button
     */
    public void clickLogin() {
        syscoLabUI.click(btnLogin);
    }

    /**
     * This will capture the screenshot of the web page
     * @return screenshot
     */
    public byte[] getScreenshot() {
        return syscoLabUI.getScreenShot();
    }

    /**
     * This will quit the current driver and exit the browser
     */
    public void quit(){
        syscoLabUI.quit();
    }
}
