package com.syscolab.qe.core.ui.pages;

import com.syscolab.qe.core.ui.SyscoLabUI;
import com.syscolab.qe.core.ui.common.ActionUtils;
import com.syscolab.qe.core.ui.common.SyscoLabBrowserTypes;
import com.syscolab.qe.core.ui.web.SyscoLabWUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginPage {

    public static SyscoLabUI syscoLabUI;

    private By txtSyscoSignInEmailId = By.xpath("//input[@type='email']");
    private By btnSyscoSignInNext = By.xpath("//input[@type='submit']");
    private By txtSyscoUserId = By.xpath("//input[@id='userInput']");
    private By btnSyscoNext = By.xpath("//button[@id='userNameFormSubmit']");
    private By txtSyscoPassword = By.xpath("//input[@id='password']");
    private By btnSyscoSignin = By.xpath("//button[@id='signIn']");
    private By btnYes = By.xpath("//input[@id='idSIButton9']");
    private By txtUsername = By.xpath("//input[@data-test='username-input']");
    private By txtPartition = By.xpath("//input[@data-test='partition-input']");
    private By txtPassword = By.xpath("//input[@data-test='password-input']");
    private By btnLogin = By.xpath("//button[@data-test='login-loginbutton-button']");
    private By imgPriceFX = By.xpath("//img[contains(@src,'/logo/svg/pricefx_logo_black.svg')]");
    private By txtShopEmail = By.xpath("//input[@name='email']");
    private By btnShopNext = By.xpath("//button[@data-id='btn_next']");

    public static void loadLoginPage(String url) {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME);
        syscoLabUI.maximizeWindow();
        syscoLabUI.navigateTo(url);
    }

    /**
     * This will quit the current driver and exit the browser
     */
    public void quitDriver() {
        if (syscoLabUI != null) {
            syscoLabUI.quit();
        }
    }

    public void enterSyscoSignInEmailId(String strSyscoEmailId) {
        syscoLabUI.waitTillElementLoaded(txtSyscoSignInEmailId);
        WebElement email = ActionUtils.castToWebElements(txtSyscoSignInEmailId);
        ActionUtils.singleClickUsingActions(email, strSyscoEmailId);
    }

    public void clickOnSyscoSignInNext() {
        syscoLabUI.waitTillElementLoaded(btnSyscoSignInNext);
        syscoLabUI.click(btnSyscoSignInNext);
    }

    public void enterSyscoUserId(String strSyscoUserId) {
        syscoLabUI.waitTillElementLoaded(txtSyscoUserId);
        syscoLabUI.sendKeys(txtSyscoUserId, strSyscoUserId);
    }

    public void clickOnNext() {
        syscoLabUI.waitTillElementLoaded(btnSyscoNext);
        syscoLabUI.click(btnSyscoNext);
    }

    public void enterSyscoPassword(String strSyscoPassword) {
        syscoLabUI.waitTillElementLoaded(txtSyscoPassword);
        syscoLabUI.sendKeys(txtSyscoPassword, strSyscoPassword);
    }

    public void clickOnSyscoSigin() {
        syscoLabUI.waitTillElementLoaded(btnSyscoSignin);
        syscoLabUI.click(btnSyscoSignin);
    }

    public void clickOnYes() {
        syscoLabUI.waitTillElementLoaded(btnYes);
        syscoLabUI.click(btnYes);
    }

    public void enterUsername(String strUserName) {
        syscoLabUI.waitTillElementLoaded(txtUsername);
        syscoLabUI.sendKeys(txtUsername, strUserName);
    }

    public void enterPartition(String strPartition) {
        syscoLabUI.waitTillElementLoaded(txtPartition);
        syscoLabUI.sendKeys(txtPartition, strPartition);
    }

    public void enterPassword(String strPassword) {
        syscoLabUI.waitTillElementLoaded(txtPassword);
        syscoLabUI.sendKeys(txtPassword, strPassword);
    }

    public void clickOnLogin() {
        syscoLabUI.waitTillElementLoaded(btnLogin);
        syscoLabUI.click(btnLogin);
    }

    public void enterShopEmail(String strEmailId) {
        syscoLabUI.waitTillElementLoaded(txtShopEmail);
        WebElement emailField = ActionUtils.castToWebElements(txtShopEmail);
        ActionUtils.singleClickUsingActions(emailField, strEmailId);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickOnShopNext() {
        syscoLabUI.waitTillElementLoaded(btnShopNext);
        WebElement nextBtn = ActionUtils.castToWebElements(btnShopNext);
        ActionUtils.singleClickUsingActions(nextBtn);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public boolean getLogoStatus() {
        syscoLabUI.sleepInMilliSeconds(2000);
        return (syscoLabUI.isDisplayed(imgPriceFX));
    }

    /**
     * This will capture the screenshot of the web page
     *
     * @return screenshot
     */
    public byte[] getScreenshot() {
        return syscoLabUI.getScreenShot();
    }

}