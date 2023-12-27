package com.syscolab.qe.core.ui.pages;

import com.syscolab.qe.core.ui.common.ActionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import static com.syscolab.qe.core.ui.pages.LoginPage.syscoLabUI;

public class QuotesPage {
    private By btnNewQuote = By.xpath("//button[@data-test='priceshop-newquotename-button']");
    private By btnFeeCodeMaintenance = By.xpath("//a[text()='Fee Code Maintenance']");
    private By txtEffectiveDate = By.xpath("//input[@data-test='targetdate-input']");
    private By txtExpiryDate = By.xpath("//input[@data-test='expirydate-input']");
    private By txtCustomertype = By.xpath("//div[@name='CustomerType']//div//span//input");
    private By txtFeeCodetype = By.xpath("//div[@name='FeeCodeAdmin']//div//span//input");
    private By txtFeeCode = By.xpath("//textarea[@name='FeeCode']");
    private By txtFeeCodeDescription = By.xpath("//textarea[@name='FeeCodeDescription']");
    private By txtApplyTo = By.xpath("//div[@name='ApplyTo']//div//span//input");
    private By txtExcludeControlledPayout = By.xpath("//div[@name='ExcludeControlledPayout']//div//span//input");
    private By txtCalculateEachAsFullCase = By.xpath("//div[@name='CalculateEachAsFullCase']//div//span//input");
    private By btnAddCustomerRow = By.xpath("(//div[@class='ucButton ucButton--size-small']//button)[1]");
    private By txtCustomerOpsite = By.xpath("(//div[@data-test-column='Opsite*'])[1]");
    private By txtCustomerUserType = By.xpath("//div[@data-test-column='C/G/CH/MAG/NVG*']");
    private By txtCustomerValue = By.xpath("//div[@data-test-column='Value*']");
    private By txtCustomerIncludeOrExclude = By.xpath("//div[@data-test-column='Include-(Y)-or-Exclude-(N)']");
    private By txtCustomerApplyToStop = By.xpath("//div[@data-test-column='Apply-to-stop-Customer']");
    private By txtProductInput = By.xpath("//div[@name='ProductInput']");
    private By txtProductInputItem = By.xpath("//div[@data-test='productinput-dropdown']//div[2]//div//div//div//div//div[contains(text(),'Item')]");
    private By btnAddProductRow = By.xpath("(//div[@class='ucButton ucButton--size-small']//button)[2]");
    private By txtRowCount = By.xpath("(//*[text()='1 row'])[2]");
    private By txtProductOpsite = By.xpath("(//div[@data-test-column='Opsite*'])[2]");
    private By txtProductAttribute = By.xpath("(//div[@data-test-column='Prod-Attr*'])[1]");
    private By txtProductAttributeValue = By.xpath("(//div[@data-test-column='Prod-Attr-Value*'])[1]");
    private By txtProductAttributeUOM = By.xpath("//div[@data-test-column='Prod-Attr-UOM']");
    private By txtProductRateType = By.xpath("//div[@data-test-column='Rate-Type']//div");
    private By txtProductRateUOM = By.xpath("//div[@data-test-column='Rate-UOM']");
    private By txtProductRateAmount = By.xpath("//div[@data-test-column='Rate-Amount']");
    private By btnSubmit = By.xpath("//button[@data-test='common-submit-button']");
    private By btnYesSubmitConfirmation = By.xpath("//button[@data-test='common-yes-button']");
    private By btnFuelSurchargeMaintenance = By.xpath("//a[text()='Fuel Surcharge']");
    private By btnFSCCode = By.xpath("//textarea[@name='FSCCode']");
    private By txtFSCCode = By.xpath("//textarea[@name='FSCCodeDescription']");
    private By txtAutoApprove = By.xpath("//div[@name='AutoApprove']//div//span//input");
    private By txtCustomer = By.xpath("//div[@name='Customers']//div");
    private By txtFuelType = By.xpath("//div[@name='FuelType']//div//input");
    private By txtFuelTypeLocation = By.xpath("//div[@name='FuelTypeLocation']//div//input");
    private By txtUOM = By.xpath("//div[@name='UOM']//div//input");
    private By txtMinFuelRate = By.xpath("//input[@name='MinFuelRate']");
    private By txtMaxFuelRate = By.xpath("//input[@name='MaxFuelRate']");
    private By txtFuelRateIncrement = By.xpath("//input[@name='FuelRateIncrement']");
    private By txtMinFSCRate = By.xpath("//input[@name='MinFSCRate']");
    private By txtMaxFSCRate = By.xpath("//input[@name='MaxFSCRate']");
    private By txtFSCIncrement = By.xpath("//input[@name='FSCIncrement']");
    private By txtDefaultFuelRate = By.xpath("//input[@name='DefaultFuelRate']");
    private By txtCurrency = By.xpath("//div[@name='Currency']//div//input");
    private By txtFrequency = By.xpath("//div[@name='Frequency']//div//input");
    private By txtCalculationDay = By.xpath("//div[@name='CalculationDay']//div//input");
    private By txtCalculationMethod = By.xpath("//div[@name='CalculationMethod']//div//input");

    public void clickOnNewQuote() {
        syscoLabUI.waitTillElementLoaded(btnNewQuote);
        syscoLabUI.sleepInMilliSeconds(1000);
        syscoLabUI.click(btnNewQuote);
    }

    public void clickOnFeeCodeMaintenance() {
        syscoLabUI.waitTillElementLoaded(btnFeeCodeMaintenance);
        syscoLabUI.click(btnFeeCodeMaintenance);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickAndSendEffectiveDate(String strEffectiveDate) {
        syscoLabUI.waitTillElementLoaded(txtEffectiveDate);
        syscoLabUI.click(txtEffectiveDate);
        syscoLabUI.clearWithBackSpace(txtEffectiveDate, 10);
        syscoLabUI.sendKeys(txtEffectiveDate, strEffectiveDate);
        syscoLabUI.sendKeys(txtEffectiveDate, Keys.ENTER);
    }

    public void clickAndSendExpiryDate(String strExpiryDate) {
        syscoLabUI.waitTillElementLoaded(txtExpiryDate);
        syscoLabUI.click(txtExpiryDate);
        syscoLabUI.clearWithBackSpace(txtExpiryDate, 10);
        syscoLabUI.sendKeys(txtExpiryDate, strExpiryDate);
        syscoLabUI.sendKeys(txtExpiryDate, Keys.ENTER);
    }

    public void clickAndChooseCustomerType(String strCustomerType) {
        syscoLabUI.waitTillElementLoaded(txtCustomertype);
        syscoLabUI.sendKeys(txtCustomertype, strCustomerType);
        syscoLabUI.sendKeys(txtCustomertype, Keys.ENTER);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickAndChooseFeeCodeType(String strFeeCodeType) {
        syscoLabUI.waitTillElementLoaded(txtFeeCodetype);
        syscoLabUI.sendKeys(txtFeeCodetype, strFeeCodeType);
        syscoLabUI.sendKeys(txtFeeCodetype, Keys.ENTER);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickAndSendFeeCodeText(String strFeeCode) {
        syscoLabUI.waitTillElementLoaded(txtFeeCode);
        syscoLabUI.click(txtFeeCode);
        syscoLabUI.sendKeys(txtFeeCode, strFeeCode);
    }

    public void clickAndSendFeeCodeDescription(String strFeeCodeDescription) {
        syscoLabUI.waitTillElementLoaded(txtFeeCodeDescription);
        syscoLabUI.click(txtFeeCodeDescription);
        syscoLabUI.sendKeys(txtFeeCodeDescription, strFeeCodeDescription);
    }

    public void clickAndChooseApplyTo(String strApplyTo) {
        syscoLabUI.waitTillElementLoaded(txtApplyTo);
        syscoLabUI.sendKeys(txtApplyTo, strApplyTo);
        syscoLabUI.sendKeys(txtApplyTo, Keys.ENTER);
    }

    public void clickAndChooseExcludeControlledPayout(String strExcludeControlledPayout) {
        syscoLabUI.waitTillElementLoaded(txtExcludeControlledPayout);
        syscoLabUI.sendKeys(txtExcludeControlledPayout, strExcludeControlledPayout);
        syscoLabUI.sendKeys(txtExcludeControlledPayout, Keys.ENTER);
    }

    public void clickAndChooseCalculateEachAsFullCase(String strCalculateEachAsFullCase) {
        syscoLabUI.waitTillElementLoaded(txtCalculateEachAsFullCase);
        syscoLabUI.sendKeys(txtCalculateEachAsFullCase, strCalculateEachAsFullCase);
        syscoLabUI.sendKeys(txtCalculateEachAsFullCase, Keys.ENTER);
    }

    public void addNewCustomerRow() {
        syscoLabUI.waitTillElementLoaded(btnAddCustomerRow);
        syscoLabUI.click(btnAddCustomerRow);
    }

    public void clickAndSendCustomerOpsite(String strOpsite) throws Exception {
        syscoLabUI.waitTillElementLoaded(txtCustomerOpsite);
        WebElement element = ActionUtils.castToWebElements(txtCustomerOpsite);
        ActionUtils.doubleClickUsingActions(element, strOpsite);
        syscoLabUI.sleepInMilliSeconds(2000);
        ActionUtils.pressEnterKey();
        syscoLabUI.sleepInMilliSeconds(2000);
    }

    public void clickAndSendCustomerUserType(String strUser) throws Exception {
        syscoLabUI.waitTillElementLoaded(txtCustomerUserType);
        WebElement element = ActionUtils.castToWebElements(txtCustomerUserType);
        ActionUtils.doubleClickUsingActions(element, strUser);
        syscoLabUI.sleepInMilliSeconds(2000);
        ActionUtils.pressEnterKey();
        syscoLabUI.sleepInMilliSeconds(2000);
    }

    public void clickAndSendCustomerValue(String strValue) throws Exception {
        syscoLabUI.waitTillElementLoaded(txtCustomerValue);
        WebElement element = ActionUtils.castToWebElements(txtCustomerValue);
        ActionUtils.doubleClickUsingActions(element, strValue);
        syscoLabUI.sleepInMilliSeconds(2000);
        ActionUtils.pressEnterKey();
        syscoLabUI.sleepInMilliSeconds(2000);
    }

    public void clickAndSendCustomerIncludeOrExclude(String strIncludeOrExclude) throws Exception {
        syscoLabUI.waitTillElementLoaded(txtCustomerIncludeOrExclude);
        WebElement element = ActionUtils.castToWebElements(txtCustomerIncludeOrExclude);
        ActionUtils.doubleClickUsingActions(element, strIncludeOrExclude);
        syscoLabUI.sleepInMilliSeconds(2000);
        ActionUtils.pressEnterKey();
        syscoLabUI.sleepInMilliSeconds(2000);
    }

    public void clickAndSendCustomerApplyToStop(String strApplyToStop) throws Exception {
        syscoLabUI.waitTillElementLoaded(txtCustomerApplyToStop);
        WebElement element = ActionUtils.castToWebElements(txtCustomerApplyToStop);
        ActionUtils.doubleClickUsingActions(element, strApplyToStop);
        syscoLabUI.sleepInMilliSeconds(2000);
        ActionUtils.pressEnterKey();
        syscoLabUI.sleepInMilliSeconds(2000);
    }

    /*  alternative try out
        public void clickAndSendCustomerApplyToStop(String strApplyToStop) throws Exception {
            syscoLabUI.waitTillElementLoaded(txtCustomerApplyToStop);
            WebElement element = ActionUtils.castToWebElements(txtCustomerApplyToStop);
            //Passing below line couple of time to avoid new creation of rows
            ActionUtils.doubleCLickUsingActions(element, strApplyToStop);
    //        syscoLabUI.clickWithJavascript(element);
    //        syscoLabUI.sendKeysInScript(element,strApplyToStop);
            syscoLabUI.sleepInMilliSeconds(1000);
            ActionUtils.doubleCLickUsingActions(element, strApplyToStop);
            ActionUtils.pressEnterKey();
            syscoLabUI.sleepInMilliSeconds(1000);
        }
    */
    public void clickAndChooseProductInput_Item() {
        syscoLabUI.waitTillElementLoaded(txtProductInput);
        syscoLabUI.click(txtProductInput);
        syscoLabUI.click(txtProductInputItem);
    }

    public void addNewProductRow() {
        syscoLabUI.waitTillElementLoaded(btnAddProductRow);
        syscoLabUI.click(btnAddProductRow);
        if (!(syscoLabUI.isDisplayed(txtRowCount))) {
            syscoLabUI.click(btnAddProductRow);
        }
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickAndSendProductOpsite(String strProductOpsite) throws Exception {
        syscoLabUI.waitTillElementLoaded(txtProductOpsite);
        WebElement element = ActionUtils.castToWebElements(txtProductOpsite);
        ActionUtils.doubleClickUsingActions(element, strProductOpsite);
        syscoLabUI.sleepInMilliSeconds(2000);
        ActionUtils.pressEnterKey();
        syscoLabUI.sleepInMilliSeconds(2000);
    }

    public void clickAndSendProductAttribute(String strProductAttribute) throws Exception {
        syscoLabUI.waitTillElementLoaded(txtProductAttribute);
        WebElement element = ActionUtils.castToWebElements(txtProductAttribute);
        ActionUtils.doubleClickUsingActions(element, strProductAttribute);
        syscoLabUI.sleepInMilliSeconds(2000);
        ActionUtils.pressEnterKey();
        syscoLabUI.sleepInMilliSeconds(2000);
    }

    public void clickAndSendProductAttributeValue(String strProductAttributeValue) throws Exception {
        syscoLabUI.waitTillElementLoaded(txtProductAttributeValue);
        WebElement element = ActionUtils.castToWebElements(txtProductAttributeValue);
        ActionUtils.doubleClickUsingActions(element, strProductAttributeValue);
        syscoLabUI.sleepInMilliSeconds(2000);
        ActionUtils.pressEnterKey();
        syscoLabUI.sleepInMilliSeconds(2000);
    }

    public void clickAndSendProductAttributeUOM(String strProductAttributeUOM) throws Exception {
        syscoLabUI.waitTillElementLoaded(txtProductAttributeUOM);
        WebElement element = ActionUtils.castToWebElements(txtProductAttributeUOM);
        ActionUtils.doubleClickUsingActions(element, strProductAttributeUOM);
        syscoLabUI.sleepInMilliSeconds(2000);
        ActionUtils.pressEnterKey();
        syscoLabUI.sleepInMilliSeconds(2000);
    }

    public void clickAndSendProductRateType(String strProductRateType) throws Exception {
        syscoLabUI.waitTillElementLoaded(txtProductRateType);
        WebElement element = ActionUtils.castToWebElements(txtProductRateType);
        ActionUtils.doubleClickUsingActions(element, strProductRateType);
        syscoLabUI.sleepInMilliSeconds(2000);
        ActionUtils.doubleClickUsingActions(element, strProductRateType);
        ActionUtils.pressEnterKey();
        syscoLabUI.sleepInMilliSeconds(2000);
    }

    public void clickAndSendProductRateUOM(String strProductRateUOM) throws Exception {
        syscoLabUI.waitTillElementLoaded(txtProductRateUOM);
        WebElement element = ActionUtils.castToWebElements(txtProductRateUOM);
        ActionUtils.doubleClickUsingActions(element, strProductRateUOM);
        syscoLabUI.sleepInMilliSeconds(2000);
        ActionUtils.pressEnterKey();
        syscoLabUI.sleepInMilliSeconds(2000);
    }

    public void clickAndSendProductRateAmount(String strProductRateUOM) throws Exception {
        syscoLabUI.waitTillElementLoaded(txtProductRateAmount);
        WebElement element = ActionUtils.castToWebElements(txtProductRateAmount);
        ActionUtils.doubleClickUsingActions(element, strProductRateUOM);
        syscoLabUI.sleepInMilliSeconds(2000);
        ActionUtils.pressEnterKey();
        syscoLabUI.sleepInMilliSeconds(2000);
    }

    public void clickOnSubmit() {
        syscoLabUI.waitTillElementLoaded(btnSubmit);
        syscoLabUI.click(btnSubmit);
    }

    public void clickOnYesBtnConfirmation() {
        syscoLabUI.waitTillElementLoaded(btnYesSubmitConfirmation);
        syscoLabUI.click(btnYesSubmitConfirmation);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickOnFuelSurchangeMaintenance() {
        syscoLabUI.waitTillElementLoaded(btnFuelSurchargeMaintenance);
        syscoLabUI.click(btnFuelSurchargeMaintenance);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickAndChooseFSCcodeType(String strFSCCodeType) {
        syscoLabUI.waitTillElementLoaded(btnFSCCode);
        syscoLabUI.sendKeys(btnFSCCode, strFSCCodeType);
        syscoLabUI.sendKeys(btnFSCCode, Keys.ENTER);
        syscoLabUI.sleepInMilliSeconds(2000);
    }

    public void clickAndSendFSCCodeDecription(String strFSCCode) {
        syscoLabUI.waitTillElementLoaded(txtFSCCode);
        syscoLabUI.click(txtFSCCode);
        syscoLabUI.sendKeys(txtFSCCode, strFSCCode);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickAndChooseAutoApprove(String strAutoApprove) {
        syscoLabUI.waitTillElementLoaded(txtAutoApprove);
        syscoLabUI.sendKeys(txtAutoApprove, strAutoApprove);
        syscoLabUI.sendKeys(txtAutoApprove, Keys.ENTER);
        syscoLabUI.sleepInMilliSeconds(2000);
    }

    public void clickAndSendCustomer(String strCustomer) throws Exception {
        syscoLabUI.waitTillElementLoaded(txtCustomer);
//        syscoLabUI.sendKeys(txtCustomer, strCustomer);
//        syscoLabUI.sendKeys(txtCustomer, Keys.ENTER);
        WebElement element = ActionUtils.castToWebElements(txtCustomer);
        ActionUtils.singleClickUsingActions(element, "("+strCustomer+")");
        syscoLabUI.sleepInMilliSeconds(3000);
        ActionUtils.pressEnterKey();
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickAndSendFuelType(String strFuelType) {
        syscoLabUI.waitTillElementLoaded(txtFuelType);
        syscoLabUI.sendKeys(txtFuelType, strFuelType);
        syscoLabUI.sendKeys(txtFuelType, Keys.ENTER);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickAndSendFuelTypeLocation(String strFuelTypelocation) {
        syscoLabUI.waitTillElementLoaded(txtFuelTypeLocation);
        syscoLabUI.sendKeys(txtFuelTypeLocation, strFuelTypelocation);
        syscoLabUI.sendKeys(txtFuelTypeLocation, Keys.ENTER);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickAndSendUOM(String strUOM) {
        syscoLabUI.waitTillElementLoaded(txtUOM);
        syscoLabUI.sendKeys(txtUOM, strUOM);
        syscoLabUI.sendKeys(txtUOM, Keys.ENTER);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickAndSendMinFuelRate(String strMinFuelRate) {
        syscoLabUI.waitTillElementLoaded(txtMinFuelRate);
        syscoLabUI.sendKeys(txtMinFuelRate, strMinFuelRate);
        syscoLabUI.sendKeys(txtMinFuelRate, Keys.ENTER);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickAndSendMaxFuelRate(String strMaxFuelRate) {
        syscoLabUI.waitTillElementLoaded(txtMaxFuelRate);
        syscoLabUI.sendKeys(txtMaxFuelRate, strMaxFuelRate);
        syscoLabUI.sendKeys(txtMaxFuelRate, Keys.ENTER);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickAndSendFuelRateIncrement(String strFuelRateIncrement) {
        syscoLabUI.waitTillElementLoaded(txtFuelRateIncrement);
        syscoLabUI.sendKeys(txtFuelRateIncrement, strFuelRateIncrement);
        syscoLabUI.sendKeys(txtFuelRateIncrement, Keys.ENTER);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickAndSendMinFSCRate(String strtxtMinFSCRate) {
        syscoLabUI.waitTillElementLoaded(txtMinFSCRate);
        syscoLabUI.sendKeys(txtMinFSCRate, strtxtMinFSCRate);
        syscoLabUI.sendKeys(txtMinFSCRate, Keys.ENTER);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickAndSendMaxFSCRate(String strtxtMaxFSCRate) {
        syscoLabUI.waitTillElementLoaded(txtMaxFSCRate);
        syscoLabUI.sendKeys(txtMaxFSCRate, strtxtMaxFSCRate);
        syscoLabUI.sendKeys(txtMaxFSCRate, Keys.ENTER);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickAndSendFSCIncrement(String strFSCIncrement) {
        syscoLabUI.waitTillElementLoaded(txtFSCIncrement);
        syscoLabUI.sendKeys(txtFSCIncrement, strFSCIncrement);
        syscoLabUI.sendKeys(txtFSCIncrement, Keys.ENTER);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickAndSendCurrency(String strCurrency) {
        syscoLabUI.waitTillElementLoaded(txtCurrency);
        syscoLabUI.sendKeys(txtCurrency, strCurrency);
        syscoLabUI.sendKeys(txtCurrency, Keys.ENTER);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickAndSendFrequency(String strFrequency) {
        syscoLabUI.waitTillElementLoaded(txtFrequency);
        syscoLabUI.sendKeys(txtFrequency, strFrequency);
        syscoLabUI.sendKeys(txtFrequency, Keys.ENTER);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickAndSendCalculationDay(String strCalculationDay) {
        syscoLabUI.waitTillElementLoaded(txtCalculationDay);
        syscoLabUI.sendKeys(txtCalculationDay, strCalculationDay);
        syscoLabUI.sendKeys(txtCalculationDay, Keys.ENTER);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickAndSendCalculationMethod(String strCalculationMethod) {
        syscoLabUI.waitTillElementLoaded(txtCalculationMethod);
        syscoLabUI.sendKeys(txtCalculationMethod, strCalculationMethod);
        syscoLabUI.sendKeys(txtCalculationMethod, Keys.ENTER);
        syscoLabUI.sleepInMilliSeconds(1000);
    }
}