package com.syscolab.qe.core.ui.functions;

import static com.syscolab.qe.core.ui.pages.LoginPage.syscoLabUI;

import com.syscolab.qe.core.ui.pages.ShopPage;
import org.openqa.selenium.By;
import org.testng.Assert;

import java.util.Map;

public class Shop {
    private static ShopPage shopPage = new ShopPage();

    /**
     * This method adds the customer details on Shop portal.
     *
     * @param strCustomer
     * @param strSiteId
     * @author Balaji.K
     */
    public static void addCustomerToShop(String strSiteId, String strCustomer) {
        shopPage.clickOnSelectCustomerLink();
        shopPage.clickOnSelectSite();
        shopPage.enterSiteDetail(strSiteId);
        shopPage.searchForCustomer(strCustomer);
        shopPage.clickOnFirstCustomerSuggestion();
        System.out.println("Successfully Added Customer to Shop.");
    }

    /**
     * This method creates an Order on Shop portal.
     *
     * @param strProduct
     * @param strQty
     * @author Balaji.K
     */
    public static String placeOrder(String strProduct, String strQty) throws Exception {
        syscoLabUI.sleepInMilliSeconds(2000);
        shopPage.searchProductCatalog(strProduct);
        if (shopPage.addToCartButtonStatus()) {
            shopPage.clickOnToIncreaseQty();
        } else {
            shopPage.enterOrderQuantity(strQty);
        }
        System.out.println("Successfully Added Product to Cart.");
        syscoLabUI.sleepInMilliSeconds(2000);
        shopPage.clickOnCartIcon();
        System.out.println("Successfully Clicked on Cart Icon.");
//        shopPage.clickOnEditOrderSummary(strOderSummary);
        syscoLabUI.sleepInMilliSeconds(2000);
        String orderSummaryName = shopPage.getOrderSummaryName();
        System.out.println("Successfully updated Order Summary.");
        String strEstimatedTotal = shopPage.getEstimatedTotal();
        shopPage.clickOnContinueToCheckOut();
        shopPage.clickOnSubmitOrder();
        System.out.println("Successfully Clicked on Submit Order");
        syscoLabUI.sleepInMilliSeconds(5000);
        if (!shopPage.orderStatus()) {
            throw new RuntimeException("Thank you message isn't displayed on screen.");
        }
        System.out.println("Thank you message is displayed on screen.");
        shopPage.clickOnViewOrders();
        return orderSummaryName;
    }

    /**
     * This method gets Order status and Number on Shop portal.
     *
     * @param strOderSummary
     * @author Balaji.K
     */
    public static String fetchOrderDetails(String strOderSummary) {
        syscoLabUI.sleepInMilliSeconds(3000);
        if (!syscoLabUI.isDisplayed(By.xpath("//*[text()='" + strOderSummary + " ']"))) {
            shopPage.clickOnOpenOrder();
            syscoLabUI.sleepInMilliSeconds(3000);
        }
        Map<String, String> orderDetails = shopPage.getOrderStatusAndNumber(strOderSummary);
        Assert.assertEquals(orderDetails.get("OrderStatus"), "Submitted", "Order Status is not yet Submitted.");
        return orderDetails.get("OrderNumber");
    }
}