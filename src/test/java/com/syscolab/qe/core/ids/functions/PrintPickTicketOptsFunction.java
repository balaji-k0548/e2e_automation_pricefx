package com.syscolab.qe.core.ids.functions;

import com.syscolab.qe.core.ids.Pages.CommonPage;
import com.syscolab.qe.core.ids.Pages.PrintPickTicketOptsPage;

import static com.syscolab.qe.core.ids.Pages.PageBase.pressF11;
import static com.syscolab.qe.core.ids.Pages.PageBase.pressF3;
import static framework.AS400.verifyTextInScreen;
import static framework.AS400.waitForScreenwithText;

public class PrintPickTicketOptsFunction {

    private static PrintPickTicketOptsPage printPickTicketOptsPage = new PrintPickTicketOptsPage();
    private static CommonPage commonPage = new CommonPage();

    public static void navigateToPrintPickTicketOpts() throws Exception {
        if (waitForScreenwithText("Print Pick Ticket Opts", 5)) {
            System.out.println("Successfully Navigated to Print Pick Ticket Opts screen");
        } else {
            throw new Exception("Cursor isn't Navigated to Print Pick Ticket Opts screen");
        }
    }

    public static void chooseSelectAndPrintPickTicketsOption() throws Exception {
        commonPage.enterOptionOrCommand("1");
    }

    public static void navigateToSelectRoutesForPickTickets() throws Exception {
        if (waitForScreenwithText("Select Routes For Pick Tickets", 5)) {
            System.out.println("Successfully Navigated to Select Routes For Pick Tickets screen");
        } else {
            throw new Exception("Cursor isn't Navigated to Select Routes For Pick Tickets screen");
        }
    }

    public static void pickTickets(String strDate, String strMonth, String strYear, String strRouteno) throws Exception {
        Thread.sleep(2000);
        chooseSelectAndPrintPickTicketsOption();
        printPickTicketOptsPage.sendShipDate(strDate);
        printPickTicketOptsPage.sendShipMonth(strMonth);
        printPickTicketOptsPage.sendShipYear(strYear);
        FunctionBase.pressEnter();
        navigateToSelectRoutesForPickTickets();
//        check for pagination, if needed use Quick access  in page class also add verification for user pick up
        printPickTicketOptsPage.pickTicketsBasedOnDeliveryDateAndRouteNo(strMonth + "/" + strDate + "/" + strYear, strRouteno);
        verifyTextInScreen("ISMYA");
        pressF3();
        pressF11();
        pressF3();
        verifyTextInScreen("Request submitted for processing. Work with your submitted jobs for status.");
        System.out.println("Successfully Request submitted for processing.");
        pressF3();
    }

}