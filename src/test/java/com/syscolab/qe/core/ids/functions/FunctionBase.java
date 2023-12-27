package com.syscolab.qe.core.ids.functions;

import com.syscolabs.functions.TN5250FunctionBase;
import framework.KeyMnemonic;

public class FunctionBase extends TN5250FunctionBase {

    public static void connectBox(String strIPAddress, String strHostName) {
        try {
            connectToBox_27x132(strIPAddress, strHostName);
            waitForScreenwithText("Wait for login screeen", 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void pressEnter() {
        try {
            sendKeys(KeyMnemonic.ENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendTextValue(String value, int row, int col) {
        try {
            sendText(value, row, col);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void disconnectBox() {
        try {
            close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getTextFromRow(int row, int col, int len) {
        return getLabelText(row, col, len);
    }

}