package com.syscolab.qe.core.ids.functions;

import com.syscolab.qe.core.ids.Pages.CommonPage;
import framework.KeyMnemonic;

import static com.syscolab.qe.core.ids.Pages.PageBase.pressF3;
import static com.syscolab.qe.core.ids.common.Constants.IDS_SQL_OPEN_CMD;
import static framework.AS400.sendKeys;
import static framework.AS400.waitForScreenwithText;

public class CommonFunction {

    private static CommonPage commonPage = new CommonPage();

    public static void navigateToQuickAccess() throws Exception {
        commonPage.openQuickAccess();
    }

    public static void exitPage() throws Exception {
        commonPage.exitPage();
    }

    public static void refreshPage() throws Exception {
        sendKeys(KeyMnemonic.PF5, 10);
        sendKeys(KeyMnemonic.PF5, 20);

    }

    public static void navigateToDateVerificationWarning() throws Exception {
        if (waitForScreenwithText("Date Verification Warning", 5)) {
            System.out.println("Successfully Navigated to Date Verification Warning screen");
            pressF3();
        } else if (waitForScreenwithText("Display Program Messages", 5)) {
            System.out.println("Successfully Navigated to Display Program Messages screen");
            FunctionBase.pressEnter();
            pressF3();
            FunctionBase.pressEnter();
        } else {
            throw new Exception("Either Cursor isn't Navigated to Date Verification Warning screen or Display Program Messages screen");
        }
    }

    public static void navigateToSyscoCanadaITEnvironment(String OPCONo) throws Exception {
        if (waitForScreenwithText("SYSCO CANADA IT ENVIRONMENTS", 5)) {
            System.out.println("Successfully Navigated to Sysco Canada IT Environments screen");
            commonPage.enterOptionOrCommand(OPCONo);
        } else {
            throw new Exception("Cursor isn't Navigated to Sysco Canada IT Environments screen");
        }
    }

    public static void chooseOptionOrEnterCommand(String strOptionOrCmd) throws Exception {
        commonPage.enterOptionOrCommand(strOptionOrCmd);
    }

    public static void chooseABCShippingOption() throws Exception {
        commonPage.enterOptionOrCommand("3");
    }

    public static void openSQLStatementScreen() throws Exception {
        commonPage.openSQLScreen(IDS_SQL_OPEN_CMD);
    }

}