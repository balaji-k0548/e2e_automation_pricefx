package com.syscolab.qe.core.ids.Pages;

import framework.KeyMnemonic;

public class PrintPickTicketOptsPage extends PageBase {

    public static int[] printPickTicketOptsScreenTitle = {3, 31};
    public static int[] sendShipDate = {14, 42};
    public static int[] sendShipMonth = {14, 39};
    public static int[] sendShipYear = {14, 45};
    public static int[] selectRoutesForPickTicketsTitle = {1, 26};


    public void sendShipDate(String strShipDate) throws Exception {
        typeText(strShipDate, sendShipDate);
    }

    public void sendShipMonth(String strShipMonth) throws Exception {
        typeText(strShipMonth, sendShipMonth);
    }

    public void sendShipYear(String strShipYear) throws Exception {
        typeText(strShipYear, sendShipYear);
    }

    public void pickTicketsBasedOnDeliveryDateAndRouteNo(String strDeliveryDate, String strRouteNo) throws Exception {
        int[] deliveryRowCol = findTextPos(strDeliveryDate);
        int[] routeRowCol = findTextPos(strRouteNo);
        if (deliveryRowCol[0] == routeRowCol[0]) {
            sendText("1", deliveryRowCol[0], 3);
            sendKey(KeyMnemonic.ENTER);
        }
    }
}