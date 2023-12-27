package com.syscolab.qe.core.ui.pages;

import com.syscolab.qe.core.ui.common.ActionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.util.HashMap;
import java.util.Map;

import static com.syscolab.qe.core.ui.pages.LoginPage.syscoLabUI;

public class ShopPage {
    private By lnkSelectCustomer = By.xpath("//div[text()='Select Customer']");
    private By txtSelectSite = By.xpath("//input[@value='Select a Site']");
    private By txtEnterSite = By.xpath("//div[@data-id='opco-selector-search']//div//input");
    private By txtSearchCustomer = By.xpath("//input[@data-id='txt_account_search_customer']");
    private By txtSelectFirstCustomer = By.xpath("(//div[@data-id='globalCustomerSelectFlyout-customerList-label-customerInfoCard'])[1]");
    private By txtSearchProductCatalog = By.xpath("//input[@placeholder='Search Product Catalog']");
    private By txtOrderQuantity = By.xpath("//input[@data-id='txt_products_cs_order_quantity']");
    private By btnCartIcon = By.xpath("//button[@data-id='icon_cart']");
    private By btnIncreaseQuantity = By.xpath("//button[contains(@data-id, 'add_case_to_cart_increase_button')]");
    private By btnEditOrderName = By.xpath("//button[@id='icon_order_name_edit']");
    private By txtOrderSummaryName = By.xpath("//h3[@id='order-name']");
    private By txtEstTotalCost = By.xpath("//div[@data-id='order-total-cost']");
    private By btnCheckOut = By.xpath("//button[@data-id='proceed-to-checkout-button']");
    private By btnSubmitOrder = By.xpath("//button[@data-id='submit-order-button']");
    private By txtThankyouMsg = By.xpath("//*[contains(text(),'Thank You')]");
    private By btnViewOrders = By.xpath("//button[@data-id='confirm-stage-orders-page-nav-button']");
    private By lnkOpenOrder = By.xpath("//div[@data-id='order-list-category-open']");

    public void clickOnSelectCustomerLink() {
        syscoLabUI.waitTillElementLoaded(lnkSelectCustomer);
        syscoLabUI.click(lnkSelectCustomer);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickOnSelectSite() {
        syscoLabUI.waitTillElementLoaded(txtSelectSite);
        syscoLabUI.click(txtSelectSite);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void enterSiteDetail(String strSiteId) {
        syscoLabUI.waitTillElementLoaded(txtEnterSite);
        syscoLabUI.sendKeys(txtEnterSite, strSiteId);
        syscoLabUI.sendKeys(txtEnterSite, Keys.ENTER);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void searchForCustomer(String strCustomer) {
        syscoLabUI.waitTillElementLoaded(txtSearchCustomer);
        syscoLabUI.sendKeys(txtSearchCustomer, strCustomer);
        syscoLabUI.sendKeys(txtSearchCustomer, Keys.ENTER);
        syscoLabUI.sleepInMilliSeconds(2000);
    }

    public void clickOnFirstCustomerSuggestion() {
        syscoLabUI.waitTillElementLoaded(txtSelectFirstCustomer);
        syscoLabUI.click(txtSelectFirstCustomer);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void searchProductCatalog(String strProduct) {
        syscoLabUI.waitTillElementLoaded(txtSearchProductCatalog);
        syscoLabUI.sendKeys(txtSearchProductCatalog, strProduct);
        syscoLabUI.sendKeys(txtSearchProductCatalog, Keys.ENTER);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void enterOrderQuantity(String strQty) {
        syscoLabUI.waitTillElementLoaded(txtOrderQuantity);
        syscoLabUI.sendKeys(txtOrderQuantity, strQty);
        syscoLabUI.sendKeys(txtOrderQuantity, Keys.ENTER);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickOnCartIcon() {
        syscoLabUI.waitTillElementLoaded(btnCartIcon);
        syscoLabUI.click(btnCartIcon);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public Boolean addToCartButtonStatus() {
        if (!syscoLabUI.isDisplayed(btnIncreaseQuantity)) {
            return false;
        }
        return true;
    }

    public void clickOnToIncreaseQty() {
        syscoLabUI.waitTillElementLoaded(btnIncreaseQuantity);
        syscoLabUI.click(btnIncreaseQuantity);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickOnEditOrderSummary(String strOrderSummary) throws Exception {
        syscoLabUI.waitTillElementLoaded(btnEditOrderName);
        syscoLabUI.click(btnEditOrderName);
        syscoLabUI.sendKeys(btnEditOrderName, Keys.BACK_SPACE);
//        syscoLabUI.clearWithBackSpace(By.xpath("(//div[@class='order-name-editor-cart']//div)[2]"),21);
//        ActionUtils.pressBackspaceKey(21);
        syscoLabUI.sendKeys(btnEditOrderName, strOrderSummary);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public String getOrderSummaryName() throws Exception {
        syscoLabUI.waitTillElementLoaded(txtOrderSummaryName);
        return syscoLabUI.getText(txtOrderSummaryName);
    }

    public String getEstimatedTotal() {
        syscoLabUI.waitTillElementLoaded(txtEstTotalCost);
        syscoLabUI.sleepInMilliSeconds(2000);
        String totalCost = syscoLabUI.findElement(txtEstTotalCost).getText();
        return totalCost;
    }

    public void clickOnContinueToCheckOut() {
        syscoLabUI.waitTillElementLoaded(btnCheckOut);
        syscoLabUI.click(btnCheckOut);
        syscoLabUI.sleepInMilliSeconds(2000);
    }

    public void clickOnSubmitOrder() {
        syscoLabUI.waitTillElementLoaded(btnSubmitOrder);
        syscoLabUI.click(btnSubmitOrder);
        syscoLabUI.sleepInMilliSeconds(2000);
    }

    public Boolean orderStatus() {
        syscoLabUI.sleepInMilliSeconds(1000);
        if (!syscoLabUI.isDisplayed(txtThankyouMsg)) {
            return false;
        }
        return true;
    }

    public void clickOnViewOrders() {
        syscoLabUI.waitTillElementLoaded(btnViewOrders);
        syscoLabUI.click(btnViewOrders);
        syscoLabUI.sleepInMilliSeconds(2000);
    }

    public void clickOnOpenOrder() {
        syscoLabUI.waitTillElementLoaded(lnkOpenOrder);
        syscoLabUI.click(lnkOpenOrder);
        syscoLabUI.sleepInMilliSeconds(2000);
    }

    public Map<String, String> getOrderStatusAndNumber(String summaryText) {
        Map<String, String> orderDetails = new HashMap<>();
        syscoLabUI.sleepInMilliSeconds(2000);
        if (syscoLabUI.isDisplayed(By.xpath("//*[text()='" + summaryText + " ']"))) {
            String mstrOrderStatus = syscoLabUI.getText(By.xpath("(//*[text()='" + summaryText + " ']//..//..//..//..//..//..//div[2]//span//div)[3]"));
            orderDetails.put("OrderStatus", mstrOrderStatus);
            if (mstrOrderStatus.equals("Submitted")) {
                String mstrOrderNumber = syscoLabUI.getText(By.xpath("(//*[text()='" + summaryText + " ']//..//..//..//..//..//..//div[4]//span//label)[1]"));
                orderDetails.put("OrderNumber", mstrOrderNumber);
            } else {
                System.out.println("Order Status : " + mstrOrderStatus + " Hence Order Number is not generated.");
            }
            String mstrDeliveryDate = syscoLabUI.getText(By.xpath("(//*[text()='" + summaryText + " ']//..//..//..//..//..//..//div[5]//span//label)[1]"));
            String mstrTotal = syscoLabUI.getText(By.xpath("(//*[text()='" + summaryText + " ']//..//..//..//..//..//..//div[7]//span//label)[1]"));
            orderDetails.put("DeliveryDate", mstrDeliveryDate);
            orderDetails.put("Total", mstrTotal);
        }
        return orderDetails;
    }
}