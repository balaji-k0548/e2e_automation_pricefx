package com.syscolab.qe.core.ui.functions;

import com.syscolab.qe.core.ui.pages.QuotesPage;

import static com.syscolab.qe.core.ui.pages.LoginPage.syscoLabUI;

public class Quotes {
    private static QuotesPage quotesPage = new QuotesPage();

    /**
     * This method selects the Fee Code Maintenance Option on Quotes Page.
     *
     * @param
     * @author Balaji.K
     */
    public static void chooseFeeCodeMaintenanceOption() {
        quotesPage.clickOnNewQuote();
        quotesPage.clickOnFeeCodeMaintenance();
        System.out.println("Successfully Chosen Fee Code Maintenance Option.");
    }

    /**
     * This method updates Fee Code Maintenance details.
     *
     * @param
     * @author Balaji.K
     */
    public static void
    updateFeeCodeMaintenanceQuote(String strEffectiveDate, String strExpiryDate, String strCustomerType, String strFeeCodeType, String strDescription, String strApplyTo, String strExcludeControlledPayout, String strCalculateEachAsFullCase, String strFeeCode) throws Exception {
        try {
            quotesPage.clickAndSendEffectiveDate(strEffectiveDate);
            quotesPage.clickAndSendExpiryDate(strExpiryDate);
            quotesPage.clickAndChooseCustomerType(strCustomerType);
            quotesPage.clickAndChooseFeeCodeType(strFeeCodeType);
            quotesPage.clickAndSendFeeCodeText(strFeeCode);
            syscoLabUI.sleepInMilliSeconds(2000);
            quotesPage.clickAndChooseApplyTo(strApplyTo);
            quotesPage.clickAndChooseExcludeControlledPayout(strExcludeControlledPayout);
            quotesPage.clickAndChooseCalculateEachAsFullCase(strCalculateEachAsFullCase);
            quotesPage.clickAndSendFeeCodeDescription(strDescription);
            System.out.println("Successfully updated FeeCode Maintenance Quote.");
        } catch (Exception e) {
            throw new Exception("Unable to Update FeeCode Maintenance Quote");
        }
    }

    /**
     * This method updates Customer Row in Fee Code Maintenance Quote.
     *
     * @param
     * @author Balaji.K
     */
    public static void updateCustomerRowInFeeCodeMaintenanceQuote(int rowEntry, String CustOpsite, String CustUserType, String CustValue, String CustIncludeOrExclude, String CustApplyToStop) throws Exception {
        try {
            for (int row = 1; row <= rowEntry; row++) {
                quotesPage.addNewCustomerRow();
                quotesPage.clickAndSendCustomerOpsite(CustOpsite);
                quotesPage.clickAndSendCustomerUserType(CustUserType);
                quotesPage.clickAndSendCustomerValue(CustValue);
                quotesPage.clickAndSendCustomerIncludeOrExclude(CustIncludeOrExclude);
                syscoLabUI.sleepInMilliSeconds(1000);
                quotesPage.clickAndSendCustomerApplyToStop(CustApplyToStop);
                System.out.println("Successfully updated Customer Row In a FeeCode Maintenance Quote.");
            }
        } catch (Exception e) {
            throw new Exception("Unable to update Customer Row In a FeeCode Maintenance Quote.");
        }
    }

    /**
     * This method is used to click and choose Product Input Item Fee Code Maintenance Quote.
     *
     * @param
     * @author Balaji.K
     */
    public static void chooseProductInput_Item() {
        quotesPage.clickAndChooseProductInput_Item();
    }

    /**
     * This method updates Product Item Row in Fee Code Maintenance Quote.
     *
     * @param PrdAttributeValue
     * @param PrdOpsite
     * @param ProdAttribute
     * @param ProdAttributeUOM
     * @param ProdRateAmount
     * @param ProdRateType
     * @param ProdRateUOM
     * @param rowEntry
     * @author Balaji.K
     */
    public static void updateProductItemRowInFeeCodeMaintenanceQuote(int rowEntry, String PrdOpsite, String ProdAttribute, String PrdAttributeValue, String ProdAttributeUOM, String ProdRateType, String ProdRateUOM, String ProdRateAmount) throws Exception {
        try {
            for (int row = 1; row <= rowEntry; row++) {
                quotesPage.addNewProductRow();
                quotesPage.clickAndSendProductOpsite(PrdOpsite);
                quotesPage.clickAndSendProductAttribute(ProdAttribute);
                quotesPage.clickAndSendProductAttributeValue(PrdAttributeValue);
                quotesPage.clickAndSendProductAttributeUOM(ProdAttributeUOM);
                quotesPage.clickAndSendProductRateType(ProdRateType);
                quotesPage.clickAndSendProductRateAmount(ProdRateAmount);
                quotesPage.clickAndSendProductRateUOM(ProdRateUOM);
                quotesPage.clickAndSendProductRateAmount(ProdRateAmount);
                System.out.println("Successfully updated Product Item Row In a FeeCode Maintenance Quote.");
            }
        } catch (Exception e) {
            throw new Exception("Unable to update Product Item Row In a FeeCode Maintenance Quote." + e.getMessage());
        }
    }

    /**
     * This method clicks on submit button in Fee Code Maintenance Quote.
     *
     * @param
     * @author Balaji.K
     */
    public static void chooseSubmitButton() {
        quotesPage.clickOnSubmit();
        System.out.println("Submit button is clicked.");
        quotesPage.clickOnYesBtnConfirmation();
        syscoLabUI.sleepInMilliSeconds(2000);
    }

    /**
     * This method selects the Fuel Surcharge Maintenance Option on Quotes Page.
     *
     * @param
     * @author Balaji.K
     */
    public static void chooseFuelSurchargeMaintenanceOption() {
        quotesPage.clickOnNewQuote();
        quotesPage.clickOnFuelSurchangeMaintenance();
        System.out.println("Successfully Chosen Fuel Surcharge Option.");
    }

    /**
     * This method updates Fuel Surcharge Maintenance details.
     *
     * @param
     * @author Balaji.K
     */
    public static void
    updateFuelSurchargeQuote(String strEffectiveDate, String strExpiryDate, String strCustomerType, String strFSCCode,
                             String strFSCDescription, String strAutoApprove, String strCustomer, String strFuelType,
                             String strFueltypeLocation, String strUOM, String strMinFuelRate, String strMaxFuelRate,
                             String strFuelRateIncrement, String strMinFSCRate, String strMaxFSCRate, String strFSCIncrement,
                             String strCurrency, String strFrequency, String strCalculationDay, String strCalculationMethod) throws Exception {
        try {
            quotesPage.clickAndSendEffectiveDate(strEffectiveDate);
            quotesPage.clickAndSendExpiryDate(strExpiryDate);
            quotesPage.clickAndChooseCustomerType(strCustomerType);
            quotesPage.clickAndChooseFSCcodeType(strFSCCode);
            quotesPage.clickAndChooseAutoApprove(strAutoApprove);
            quotesPage.clickAndSendFSCCodeDecription(strFSCDescription);
            quotesPage.clickAndSendCustomer(strCustomer);
            quotesPage.clickAndSendFuelType(strFuelType);
            quotesPage.clickAndSendFuelTypeLocation(strFueltypeLocation);
            quotesPage.clickAndSendUOM(strUOM);
            quotesPage.clickAndSendMinFuelRate(strMinFuelRate);
            quotesPage.clickAndSendMaxFuelRate(strMaxFuelRate);
            quotesPage.clickAndSendFuelRateIncrement(strFuelRateIncrement);
            quotesPage.clickAndSendMinFSCRate(strMinFSCRate);
            quotesPage.clickAndSendMaxFSCRate(strMaxFSCRate);
            quotesPage.clickAndSendFSCIncrement(strFSCIncrement);
            quotesPage.clickAndSendFSCIncrement(strFSCIncrement);
            quotesPage.clickAndSendCurrency(strCurrency);
            quotesPage.clickAndSendFrequency(strFrequency);
            quotesPage.clickAndSendCalculationDay(strCalculationDay);
            quotesPage.clickAndSendCalculationMethod(strCalculationMethod);

            System.out.println("Successfully updated FeeCode Maintenance Quote.");
        } catch (Exception e) {
            throw new Exception("Unable to Update FeeCode Maintenance Quote");
        }
    }
}