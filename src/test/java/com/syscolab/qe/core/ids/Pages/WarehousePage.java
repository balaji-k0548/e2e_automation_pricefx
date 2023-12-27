package com.syscolab.qe.core.ids.Pages;

import framework.KeyMnemonic;

public class WarehousePage extends PageBase {


    public void displayOrdersOnOrderStatusInquiry(String strDeliveryDate, String strRouteNo) throws Exception {
        int[] deliveryRowCol = quickSearch(strDeliveryDate, true);
        int[] routePos = {deliveryRowCol[0], 13};
        String routeText = PageBase.getText(routePos, 4);
        if (routeText.equals(strRouteNo)) {
            sendText("5", deliveryRowCol[0], 2);
            sendKey(KeyMnemonic.ENTER);
        } else {
            int[] DeliveryDatePos = findTextPosLastOccurance(strDeliveryDate);
            int[] routeLastOccurancePos = {DeliveryDatePos[0], 13};
            String routeLastOccuranceText = PageBase.getText(routeLastOccurancePos, 4);
            if (routeLastOccuranceText.equals(strRouteNo)) {
                sendText("5", DeliveryDatePos[0], 2);
                sendKey(KeyMnemonic.ENTER);
//            } else if (DeliveryDatePos[0] > 18) {
//                sendKeys("[pgdown]");
//                int[] DeliveryDatePosNxtPg = findTextPosLastOccurance(strDeliveryDate);
//                int[] routeLastOccurancePosNxtPg = {DeliveryDatePosNxtPg[0], 13};
//                String routeLastOccuranceTextNxtPg = PageBase.getText(routeLastOccurancePosNxtPg, 4);
//                if (routeLastOccuranceTextNxtPg.equals(strRouteNo)) {
//                    sendText("5", DeliveryDatePosNxtPg[0], 2);
//                    sendKey(KeyMnemonic.ENTER);
//                } else {
//                    throw new Exception("Delivery date and route no Position is different on Next Page Also.");
//                }
//            } else if (DeliveryDatePos[0] - deliveryRowCol[0] > 0) {
//                for (int i = 0; i <= DeliveryDatePos[0] - deliveryRowCol[0]; i++) {
//                    int[] routeLastOccurancePosNxtPg = {i, 13};
//                    String routeNo = PageBase.getText(routeLastOccurancePosNxtPg, 4);
//                    if (routeNo.equals(strRouteNo)) {
//                        sendText("5", i, 2);
//                        sendKey(KeyMnemonic.ENTER);
//                        break;
//                    }
//                }
            } else {
                throw new Exception("Delivery date and route no Position is different on last Occurance.");
            }
        }
    }

    public String fetchOrderNumberFromOrderStatusEnquiryPage(String strDeliveryDate) throws Exception {
        int[] dateRowCol = findTextPosLastOccurance(strDeliveryDate);
        int[] orderPos = {dateRowCol[0], 10};
        String orderNumber = PageBase.getText(orderPos, 7);
        return orderNumber;
    }

    public void invoiceSelection(String strDeliveryDate, String strRouteNo) throws Exception {
        int[] deliveryRowCol = quickSearch(strDeliveryDate, true);
        int[] routePos = {deliveryRowCol[0], 33};
        String routeText = PageBase.getText(routePos, 4);
        if (routeText.equals(strRouteNo)) {
            sendText("1", deliveryRowCol[0], 2);
            sendKey(KeyMnemonic.ENTER);
            sendKey(KeyMnemonic.PF8);
//            SAVAL01Q is chosen by default
            sendText("1");
            sendKey(KeyMnemonic.ENTER);
            sendKey(KeyMnemonic.PF3);
        } else {
            int[] datePos = findTextPosLastOccurance(strDeliveryDate);
            int[] routeLastOccurancePos = {datePos[0], 33};
            String routeLastOccuranceText = PageBase.getText(routeLastOccurancePos, 4);
            if (routeLastOccuranceText.equals(strRouteNo)) {
                sendText("1", datePos[0], 2);
                sendKey(KeyMnemonic.ENTER);
                sendKey(KeyMnemonic.PF8);
                sendText("1");
                sendKey(KeyMnemonic.ENTER);
                sendKey(KeyMnemonic.PF3);
            } else {
                throw new Exception("Delivery date and route no Position is different on last Occurance.");
            }
        }
    }

}