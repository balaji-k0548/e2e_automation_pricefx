package com.syscolab.qe.core.ids.Pages;

import framework.KeyMnemonic;

public class OrderEntryInquiryPage extends PageBase {

    public static int[] customerNumberField = {3, 45};
    public static int[] orderNumberField = {4, 59};

    public void sendCustomerNoInSalesInquirySystem(String strCustomerNumber) throws Exception {
        typeText(strCustomerNumber, customerNumberField);
        sendKey(KeyMnemonic.ENTER);
    }

    public void selectAppropriateInvoiceHeader(String strDeliveryDate) throws Exception {
        int[] ddRowCol = CommonPage.quickSearch(strDeliveryDate, true);
        int[] DeliveryDatePos = findTextPosLastOccurance(strDeliveryDate);
        sendText("H", DeliveryDatePos[0], 2);
        sendKey(KeyMnemonic.ENTER);
        verifyTextInScreen("Open Orders - Order Header");
        sendKey(KeyMnemonic.ENTER);
    }

    public static String getOrderNumberFromInvoiceScreen() throws Exception {
        return getText(orderNumberField, 7);
    }
}