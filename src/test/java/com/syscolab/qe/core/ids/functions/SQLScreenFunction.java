package com.syscolab.qe.core.ids.functions;

import com.syscolab.qe.core.ids.Pages.CommonPage;
import com.syscolab.qe.core.ids.Pages.PageBase;
import com.syscolab.qe.core.ids.Pages.SQLPage;
import com.syscolab.qe.core.ids.common.Constants;
import framework.KeyMnemonic;
import org.testng.Assert;

import static com.syscolab.qe.core.ids.Pages.PageBase.*;
import static com.syscolab.qe.core.ids.functions.FunctionBase.pressEnter;
import static com.syscolabs.functions.TN3270FunctionBase.pressF10;

public class SQLScreenFunction {

    private static SQLPage sqlPage = new SQLPage();

    public static void typeSQLQuery(String query) throws Exception {
        sqlPage.typeSQLQuery(query);
    }

    public static void getQueryData(String query) throws Exception {
        sqlPage.typeSQLQuery(query);
        sendKey(KeyMnemonic.ENTER);
    }

    public static void getLongQueryData(String query) throws Exception {
        sendKey(KeyMnemonic.PF6, 10);
        sqlPage.typeLongSQLQuery(query);
        sendKey(KeyMnemonic.ENTER);
    }

    public static boolean isNavigateToSQLScreen() throws Exception {
        return sqlPage.getScreenTitle().equals("Enter SQL Statement");
    }

    public static void updateOrderUsingSQL(String query) throws Exception {
        isNavigateToSQLScreen();
        typeSQLQuery(query);
        pressEnter();
        if (waitForScreenwithText("1 rows updated in SHAJCPP", 5)) {
            System.out.println("Successfully Order is updated using SQL.");
        } else {
            throw new Exception("Order isn't updated using SQL.");
        }
        pressF3();
        pressEnter();
    }

    public static void updateDestinationTableUsingSQL(String query, String strFeeCode, String strDescription) throws Exception {
        isNavigateToSQLScreen();
        typeSQLQuery(query);
        Thread.sleep(2000);
        pressEnter();
        Thread.sleep(5000);
        if (waitForScreenwithText("Display Data", 3)) {
            System.out.println("Successfully Navigated to Display Data Page.");
            sqlPage.typePositionText("B");
            pressEnter();
            if (!waitForScreenwithText(strFeeCode, 3)) {
                throw new Exception(strDescription +" isn't present under Display Data and files not moved to Destination Table.");
            }
            System.out.println(strDescription +" is present under Display Data and files moved to Destination Table.");
        } else {
            throw new Exception("Unable to Navigate to Display Data Page.");
        }
        pressF3();
    }

    public static boolean validateFeeCodeUsingSQL(String query, String strFeeCode, String strFeeAmount) throws Exception {
        boolean flag;
        isNavigateToSQLScreen();
        typeSQLQuery(query);
        pressEnter();
        if (waitForScreenwithText("Display Data", 5)) {
            System.out.println("Successfully Navigated to Display Data screen.");
            sqlPage.typePositionText("B");
            pressEnter();
            if (waitForScreenwithText(strFeeCode, 5)) {
                flag = true;
                int[] positionOfCode = findTextPos(strFeeCode);
                String feeAmount = getLabelText(positionOfCode[0], 79, 8);
                System.out.println("Fee Code Amount from IDS : " + feeAmount);
                if(feeAmount.contains(strFeeAmount+".00")){
                    System.out.println("Fee Code is updated on IDS.");
                }else{
                    System.out.println("Fee Code isn't updated on IDS.");
                }
                Assert.assertEquals(feeAmount, strFeeAmount + ".00", "Fee Code isn't updated on IDS.");
            } else {
                System.out.println("Fee Code isn't Displayed under Data screen. Hence trigger Invoice via Work With User Jobs.");return
                flag = false;
            }
            pressF3();
            pressF3();
            pressEnter();
            return flag;
        } else {
            throw new Exception("Unable to Navigate to Display Data screen.");
        }
    }

    public static void workWithUserJobs() throws Exception {
        sendText(Constants.IDS_OPEN_WRKUSRJOB_CMD, 20, 2);
        pressEnter();
        if (waitForScreenwithText("Work with User Jobs", 5)) {
            System.out.println("Successfully Navigated to Work with User Jobs Page.");
            int[] statusRowCol = CommonPage.quickSearch("JOBQ", true);
            int[] jobPos = {statusRowCol[0], 7};
            String jobText = PageBase.getText(jobPos, 6);
            if (jobText.equals("WHSINV")) {
                sendText("5", statusRowCol[0], 2);
                pressEnter();
                sendText("40", 21, 7);
                pressEnter();
                pressF10();
                setCursorPostion(17, 37);
                sendKey(KeyMnemonic.ERASE_FIELD);
                sendText("qinter", 17, 37);
                pressEnter();
                pressF3();
                refresh();
                pressF3();
            }

        } else {
            throw new Exception("Unable to Navigate to Work with User Jobs Page.");
        }
    }

}