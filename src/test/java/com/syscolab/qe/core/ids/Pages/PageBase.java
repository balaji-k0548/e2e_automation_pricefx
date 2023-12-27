package com.syscolab.qe.core.ids.Pages;

import com.syscolabs.functions.TN5250FunctionBase;
import framework.KeyMnemonic;


public class PageBase extends TN5250FunctionBase {

    public static void typeText(String text, int[] element) throws Exception {
        sendText(text, element[0], element[1]);
    }

    public static String getText(int[] element, int len) {
        return getLabelText(element[0], element[1], len);
    }

    public static void sendKey(String key) throws Exception {
        sendKeys(key);
    }

    public static void sendKey(String key, int frequency) throws Exception {
        for (int i = 0; i < frequency; i++) {
            sendKey(key);
        }
    }

    public static void refresh() {
        try {
            sendKeys(KeyMnemonic.PF5, 3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cancelScreen() {
        try {
            sendKeys(KeyMnemonic.PF12);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void pressF3() {
        try {
            sendKeys(KeyMnemonic.PF3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void pressF10() {
        try {
            sendKeys(KeyMnemonic.PF10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void pressF11() {
        try {
            sendKeys(KeyMnemonic.PF11);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}