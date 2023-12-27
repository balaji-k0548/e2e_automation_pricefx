package com.syscolab.qe.core.ids.Pages;

import com.syscolab.qe.core.ids.data.OrderData;
import framework.KeyMnemonic;
import java.util.ArrayList;
import java.util.List;

public class CommonPage extends PageBase {

    public static int[] loggedInText = {3, 18};
    public static int[] invoiceQueue = {11, 31};
    public static int[] displayFile = {11, 3};
    public static int[] firstInvoiceQueue = {10, 31};
    public static int[] firstDisplayFile = {10, 3};
    public static int[] selectOption = {19, 17};
    public static int[] commandLine = {20, 2};

    public String getLoggedInText() {
        return getText(loggedInText, 6);
    }

    public String getInvoiceQueueName() {
        if ((getText(firstInvoiceQueue, 6).equals(OrderData.invoiceQueue))) {
            return getText(firstInvoiceQueue, 6);
        } else {
            return getText(invoiceQueue, 6);
        }
    }

    public List<String> getInvoiceQueueName(String queueName) {
        List<String> lstInvoiceQueueDetails = new ArrayList<>();
        if ((getText(firstInvoiceQueue, 6).equals(queueName))) {
            lstInvoiceQueueDetails.add(getText(firstInvoiceQueue, 6));
            lstInvoiceQueueDetails.add(String.valueOf(firstInvoiceQueue[0]));
        } else {
            lstInvoiceQueueDetails.add(getText(invoiceQueue, 6));
            lstInvoiceQueueDetails.add(String.valueOf(invoiceQueue[0]));
        }
        return lstInvoiceQueueDetails;
    }

    public void displayFileData(String displayFileOption) throws Exception {
        if ((getText(firstInvoiceQueue, 6).equals(OrderData.invoiceQueue))) {
            typeText(displayFileOption, firstDisplayFile);
            sendKey(KeyMnemonic.ENTER);
        } else {
            typeText(displayFileOption, displayFile);
            sendKey(KeyMnemonic.ENTER);
        }
    }

    public void displayFileData(String displayFileOption, String queueNameLine) throws Exception {
        displayFile[0] = Integer.parseInt(queueNameLine);
        typeText(displayFileOption, displayFile);
        sendKey(KeyMnemonic.ENTER);
    }

    public void openQuickAccess() throws Exception {
        sendKey(KeyMnemonic.PF10);
    }

    public void exitPage() throws Exception {
        sendKey(KeyMnemonic.PF3);
        sendKey(KeyMnemonic.PF12);
    }

    public static void enterOptionOrCommand(String strCmdOrOPCONo) throws Exception {
        typeText(strCmdOrOPCONo, selectOption);
        sendKey(KeyMnemonic.ENTER);
    }

    public void openSQLScreen(String strCommand) throws Exception {
        typeText(strCommand, commandLine);
        sendKey(KeyMnemonic.ENTER);
    }

    public void enterCmdInCommandLine(String strCommand) throws Exception {
        typeText(strCommand, commandLine);
        sendKey(KeyMnemonic.ENTER);
    }

}