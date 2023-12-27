package com.syscolab.qe.core.ids.functions;

import com.syscolab.qe.core.ids.Pages.CommonPage;
import com.syscolab.qe.core.ids.Pages.OrderEntryInquiryPage;

import static com.syscolab.qe.core.ids.Pages.PageBase.pressF3;
import static framework.AS400.waitForScreenwithText;

public class OrderEntryInquiryFunction {

    private static CommonPage commonPage = new CommonPage();
    private static OrderEntryInquiryPage orderEntryInquiryPage = new OrderEntryInquiryPage();


    public static void isNavigateToOrderEntryInquiry() throws Exception {
        if (waitForScreenwithText("Order Entry Inquiry", 5)) {
            System.out.println("Successfully Navigated to Order Entry Inquiry screen");
        } else {
            throw new Exception("Cursor isn't Navigated to Order Entry Inquiry screen");
        }
    }

    public static void isNavigateToInternalSalesInquirySystem() throws Exception {
        if (waitForScreenwithText("Internal Sales Inquiry System", 5)) {
            System.out.println("Successfully Navigated to Internal Sales Inquiry System screen");
        } else {
            throw new Exception("Cursor isn't Navigated to Internal Sales Inquiry System screen");
        }
    }

    public static String openInvoiceHeader(String strCustNo, String strDate, String strMonth, String strYear, String strfeeCode) throws Exception {
        isNavigateToOrderEntryInquiry();
        CommonPage.enterOptionOrCommand("17");
        isNavigateToInternalSalesInquirySystem();
        orderEntryInquiryPage.sendCustomerNoInSalesInquirySystem(strCustNo);
        orderEntryInquiryPage.selectAppropriateInvoiceHeader(strMonth + "/" + strDate + "/" + strYear);
        String orderNumber = orderEntryInquiryPage.getOrderNumberFromInvoiceScreen();
        pressF3();
        pressF3();
        pressF3();
        return orderNumber;
    }

}