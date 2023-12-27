package com.syscolab.qe.core.ids.functions;

import com.syscolab.qe.core.ids.Pages.CommonPage;
import com.syscolab.qe.core.ids.Pages.WarehousePage;

import static com.syscolab.qe.core.ids.Pages.PageBase.pressF3;
import static framework.AS400.waitForScreenwithText;

public class WarehouseFunction {

    private static WarehousePage warehousePage = new WarehousePage();
    private static CommonPage commonPage = new CommonPage();

    public static void chooseInvoiceSelectionAndPrintOption() throws Exception {
        commonPage.enterOptionOrCommand("3");
    }

    public static void chooseOrderStatusInquiryOption() throws Exception {
        commonPage.enterOptionOrCommand("6");
    }

    public static void isNavigateToABCWarehouse() throws Exception {
        if (waitForScreenwithText("ABC Warehouse", 5)) {
            System.out.println("Successfully Navigated to ABC Warehouse screen");
        } else {
            throw new Exception("Cursor isn't Navigated to ABC Warehouse screen");
        }
    }

    public static String orderStatus(String strDate, String strMonth, String strYear, String strRouteno) throws Exception {
        Thread.sleep(1000);
        CommonFunction.chooseABCShippingOption();
        isNavigateToABCWarehouse();
        chooseOrderStatusInquiryOption();
        warehousePage.displayOrdersOnOrderStatusInquiry(strYear + "/" + strMonth + "/" + strDate, strRouteno);
        String orderNo = warehousePage.fetchOrderNumberFromOrderStatusEnquiryPage(strYear + "/" + strMonth + "/" + strDate);
        pressF3();
        pressF3();
        return orderNo;
    }

    public static void invoiceSubmission(String strDate, String strMonth, String strYear, String strRouteno) throws Exception {
        chooseInvoiceSelectionAndPrintOption();
        warehousePage.invoiceSelection(strYear + "/" + strMonth + "/" + strDate, strRouteno);
        if (waitForScreenwithText("Invoice selection submitted for processing", 5)) {
            System.out.println("Successfully Invoice submitted for processing");
        } else {
            throw new Exception("Invoice not submitted for processing");
        }
    }

}