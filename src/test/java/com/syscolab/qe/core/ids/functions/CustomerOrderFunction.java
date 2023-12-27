package com.syscolab.qe.core.ids.functions;

import com.syscolab.qe.core.ids.Pages.CommonPage;
import com.syscolab.qe.core.ids.Pages.CustomerOrderPage;
import com.syscolab.qe.core.ui.common.ActionUtils;

import static com.syscolab.qe.core.ids.Pages.PageBase.pressF10;
import static com.syscolab.qe.core.ids.Pages.PageBase.pressF3;
import static framework.AS400.waitForScreenwithText;


public class CustomerOrderFunction {

    private static CustomerOrderPage customerOrderPage = new CustomerOrderPage();
    private static CommonPage commonPage = new CommonPage();

    public static void navigateToEnterCustomerOrders() throws Exception {
        if (waitForScreenwithText("Enter Customer Orders", 5)) {
            System.out.println("Successfully Navigated to Enter Customer Orders screen");
        } else {
            throw new Exception("Cursor isn't Navigated to Date Verification Warning screen");
        }
    }

    public static void chooseEnterCustomerOrdersOption() throws Exception {
        commonPage.enterOptionOrCommand("1");
    }

    public static String addAnItemAndAcceptOrder(String strCustNo, String strDate, String strMonth, String strYear, String strRouteno, String strItemNo, String strQuantity) throws Exception {
        chooseEnterCustomerOrdersOption();
        if (!waitForScreenwithText("Pick Ticket Order Entry", 5)) {
            throw new Exception("Cursor isn't Navigated to Pick Ticket Order Entry screen");
        }
        System.out.println("Successfully Navigated to Pick Ticket Order Entry screen");
        customerOrderPage.enterCustomerNo(strCustNo);
        String memoCode = ActionUtils.randomStringGenerate(3);
        customerOrderPage.enterMemoCode(memoCode);
        customerOrderPage.enterShipDate(strMonth + strDate + strYear);
        FunctionBase.pressEnter();
        customerOrderPage.enterRouteNumber(strRouteno);
        FunctionBase.pressEnter();
        customerOrderPage.enterItemNumber(strItemNo);
        customerOrderPage.enterQuantity(strQuantity);
        FunctionBase.pressEnter();
        Thread.sleep(10000);
        FunctionBase.pressEnter();
        pressF10();
        pressF10();
        if (!waitForScreenwithText("Pick Ticket Order Entry", 5)) {
            throw new Exception("Cursor isn't added an Item and Accepted an order");
        }
        System.out.println("Successfully added an Item and Accepted an order");
        pressF3();
        pressF3();
        return memoCode;
    }

}