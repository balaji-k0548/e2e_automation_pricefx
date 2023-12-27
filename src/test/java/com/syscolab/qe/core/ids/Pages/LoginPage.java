package com.syscolab.qe.core.ids.Pages;

import com.syscolab.qe.core.ui.SyscoLabUI;
import framework.KeyMnemonic;

import static com.syscolab.qe.core.ids.Pages.PageBase.sendKey;
import static com.syscolab.qe.core.ids.Pages.PageBase.typeText;

public class LoginPage {
    protected static SyscoLabUI syscoLabUi;
    public static int[] userName = {06, 53};
    public static int[] password = {07, 53};

    public static void typeUserName(String txtUserName) throws Exception {
        typeText(txtUserName, userName);
    }

    public static void typePassword(String txtPassword) throws Exception {
        typeText(txtPassword, password);
    }

    public static void logout() throws Exception {
        sendKey(KeyMnemonic.PF5);
    }

    /*
    public static void loadLoginPage(Capabilities capabilities, String url) {
        syscoLabUi = new SyscoLabWUI(capabilities);
        syscoLabUi.navigateTo(url);
        syscoLabUi.driver.manage().window().maximize();
    }
    */

    public void quitDriver() {
        if (syscoLabUi != null) {
            syscoLabUi.quit();
        }
    }

}