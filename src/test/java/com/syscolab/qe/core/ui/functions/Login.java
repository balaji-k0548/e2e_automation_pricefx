package com.syscolab.qe.core.ui.functions;

import com.syscolab.qe.core.ui.pages.LoginPage;

public class Login {

    public static LoginPage loginPage = new LoginPage();

    /**
     * This method helps to launch the PriceFX URL
     *
     * @param url
     * @author Balaji.K
     */
    public static void loadLoginScreen(String url) {
        loginPage.loadLoginPage(url);
    }

    /**
     * This method helps to enter credentials on SYSCO Single SIgn On page
     *
     * @param userId,
     * @param password
     * @author Balaji.K
     */
    public static void SyscoSingleSignOn(String userId, String password) {
        loginPage.enterSyscoUserId(userId);
        loginPage.clickOnNext();
        loginPage.enterSyscoPassword(password);
        loginPage.clickOnSyscoSigin();
        loginPage.clickOnYes();
        System.out.println("Sysco Single SignOn Completed Successfully.");
    }

    /**
     * This method helps to enter Email Id on SYSCO Single SIgn On page
     *
     * @param emailId,
     * @author Balaji.K
     */
    public static void SyscoSingleSignOn_EmailId(String emailId) {
        loginPage.enterSyscoSignInEmailId(emailId);
        loginPage.clickOnSyscoSignInNext();
        System.out.println("Sysco Single SignOn EmailId Submitted Successfully.");
    }

    /**
     * This method helps to Log into Price FX application.
     *
     * @param username,
     * @param partition
     * @param password
     * @author Balaji.K
     */
    public static void loginToPriceFX(String username, String partition, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPartition(partition);
        loginPage.enterPassword(password);
        loginPage.clickOnLogin();
        System.out.println("Logged-in To PriceFX Portal Successfully.");
    }

    /**
     * This method helps to Log into Shop application.
     *
     * @param email
     * @author Balaji.K
     */
    public static void loginToShop(String email) {
        loginPage.enterShopEmail(email);
        loginPage.clickOnShopNext();
        System.out.println("Entered Shop Mail Id Successfully.");
    }

    public static void quiteDriver() {
        loginPage.quitDriver();
    }

}