package com.syscolab.qe.core.ui.functions;

import com.syscolab.qe.core.ui.pages.LoginPageSample;


/**
 * Created by yoosufm on 6/7/17.
 */
public class LoginSample {

    private static LoginPageSample loginPageSample = new LoginPageSample();

    /**
     * This loads the Home Page
     */
    public static void loadHomePage() {
        loginPageSample.loadLoginPage();
    }

    public static void quit(){
        loginPageSample.quit();
    }
}
