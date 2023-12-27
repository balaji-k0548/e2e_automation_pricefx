package com.syscolab.qe.core.ids.functions;

import com.syscolab.qe.core.ids.Pages.LoginPage;
import com.syscolab.qe.core.util.fileutil.DataProperty;
import framework.KeyMnemonic;

import java.util.Arrays;
import java.util.List;

import static com.syscolab.qe.core.ids.Pages.LoginPage.typePassword;
import static com.syscolab.qe.core.ids.Pages.PageBase.sendKey;

public class Login extends FunctionBase {

    public static LoginPage loginPage = new LoginPage();

    public static void connectAndLoginToIDSBox(String key) throws Exception {
        List<String> loginData = Arrays.asList(DataProperty.getProperty(key).split(","));
        connectIDSBox(loginData.get(0), loginData.get(1));
        loginToOPCO(loginData.get(2), loginData.get(3));
    }

    public static void connectIDSBox(String strIPAddress, String strHostName) {
        connectBox(strIPAddress, strHostName);
    }

    public static void loginToOPCO(String userName, String password) throws Exception {
        LoginPage.typeUserName(userName);
        sendKey(KeyMnemonic.TAB);
        typePassword(password);
        sendKey(KeyMnemonic.ENTER);
        sendKey(KeyMnemonic.ENTER);
    }

    public static void navigateToDailyAgendaPage() {
        pressEnter();
        pressEnter();
    }

}