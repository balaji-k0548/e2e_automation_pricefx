package com.syscolab.qe.core.ids.Pages;

public class CustomerOrderPage extends PageBase {

    public static int[] customerNumber = {6, 29};
    public static int[] memoCodeField = {7, 24};
    public static int[] shipDateField = {7, 28};
    public static int[] routeNumberField = {19, 25};
    public static int[] itemNumberField = {18, 11};
    public static int[] quantityField = {18, 24};


    public void enterCustomerNo(String strCustomerNo) throws Exception {
        typeText(strCustomerNo, customerNumber);
    }

    public void enterMemoCode(String strMemoCode) throws Exception {
        typeText(strMemoCode, memoCodeField);
    }

    public void enterShipDate(String strShipDate) throws Exception {
        typeText(strShipDate, shipDateField);
    }

    public void enterRouteNumber(String strRouteNo) throws Exception {
        typeText(strRouteNo, routeNumberField);
    }

    public void enterItemNumber(String strItemNo) throws Exception {
        typeText(strItemNo, itemNumberField);
    }

    public void enterQuantity(String strQuantity) throws Exception {
        typeText(strQuantity, quantityField);
    }

}