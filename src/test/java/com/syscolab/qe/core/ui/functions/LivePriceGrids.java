package com.syscolab.qe.core.ui.functions;

import com.syscolab.qe.core.ui.pages.LivePriceGridsPage;

import static com.syscolab.qe.core.ui.pages.LoginPage.syscoLabUI;

public class LivePriceGrids {

    private static LivePriceGridsPage livePriceGridsPage = new LivePriceGridsPage();

    /**
     * This method creates new Locked Pricing Setting on Live Price Grids Page.
     *
     * @param
     * @author Balaji.K
     */
    public static void createLockedPricing(String strDefineLabel, String strResultPrice, String strAutoApprove) {
        livePriceGridsPage.clickOnNewPriceGrid();
        livePriceGridsPage.clickOnLockedPricing();
        livePriceGridsPage.clickAndSendLabelText(strDefineLabel);
        livePriceGridsPage.clickOnContButton();
        livePriceGridsPage.clickOnSkipButton();
        livePriceGridsPage.clickOnDistributedCalcCheckBox();
        livePriceGridsPage.clickAndSendResultPrice(strResultPrice);
        livePriceGridsPage.clickAndSendAutoApprove(strAutoApprove);
        livePriceGridsPage.clickOnOutputEleCheckBox();
        livePriceGridsPage.clickOnSaveButton();
        System.out.println("Successfully created Locked Pricing List.");
    }

    /**
     * This method updates header details of price List on Live Price Grids Page.
     *
     * @param strCustomerType
     * @param strEffectiveDate
     * @param strExpiryDate
     * @param strCalculationDate
     * @param strCalculationFrequency
     * @param strCustomer
     * @author Balaji.K
     */
    public static String
    updateHeaderDetails(String strCustomer, String strCustomerType, String strEffectiveDate, String strExpiryDate, String strCalculationFrequency, String strCalculationDate) throws Exception {
        String priceListId = livePriceGridsPage.getFirstPriceListId();
        livePriceGridsPage.clickOnFirstPriceListId();
        System.out.println("Successfully clicked on First price list ID.");
        syscoLabUI.sleepInMilliSeconds(2000);
        livePriceGridsPage.clickAndSendCustomerText(strCustomer);
        livePriceGridsPage.clickAndSendCustomerTypeText(strCustomerType);
        livePriceGridsPage.clickAndSendEffectiveDateText(strEffectiveDate);
        livePriceGridsPage.clickAndSendExpiryDateText(strExpiryDate);
        livePriceGridsPage.clickAndSendCalculationFrequencyText(strCalculationFrequency);
        livePriceGridsPage.clickAndSendCalculationDateText(strCalculationDate);
        livePriceGridsPage.clickOnSaveAndRecalculate();
        System.out.println("Updated Header Details.");
        return priceListId;
    }

    /**
     * This method Selects Calculate Button
     *
     * @param
     * @author Balaji.K
     */
    public static void selectCalculateButton() {
        livePriceGridsPage.clickOnCalculateButton();
        syscoLabUI.sleepInMilliSeconds(10000);
        System.out.println("Successfully clicked on Calculate button.");
        livePriceGridsPage.verifyReadyMessage();
        System.out.println("Ready Message is Displayed on Screen.");
    }

    /**
     * This method is used to add products
     *
     * @param strProductId
     * @param strManualOverrideValue
     * @author Balaji.K
     */
    public static void AddProducts(String strProductId, String strManualOverrideValue) throws Exception {
        livePriceGridsPage.clickOnAddProductsButton();
        livePriceGridsPage.clickAndSendProductIdText(strProductId);
//        livePriceGridsPage.clickOnProductCheckBox();
        syscoLabUI.sleepInMilliSeconds(3000);
        livePriceGridsPage.clickOnSelectButton();
        livePriceGridsPage.clickAndSendManualOverrideValue(strManualOverrideValue);
        syscoLabUI.sleepInMilliSeconds(2000);
        livePriceGridsPage.clickOnArrowSubmitButton();
        System.out.println("Successfully added Products.");
    }

}