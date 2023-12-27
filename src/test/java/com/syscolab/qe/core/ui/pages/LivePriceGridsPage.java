package com.syscolab.qe.core.ui.pages;

import com.syscolab.qe.core.ui.common.ActionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import static com.syscolab.qe.core.ui.pages.LoginPage.syscoLabUI;

public class LivePriceGridsPage {
    private By btnNewPriceGrid = By.xpath("//button[@data-test='pricegrid-newpricegridname-button']");
    private By btnLockedPricing = By.xpath("//a[text()='Locked Pricing']");
    private By txtLabel = By.xpath("//input[@data-test='price-grid-label-input']");
    private By btnContToProd = By.xpath("//span[text()='Continue to Products']");
    private By btnSkip = By.xpath("//button[@data-test='common-skip-button']");
    private By chkBoxAllowDistributedCalc = By.xpath("//input[@name='shotgunModeEnabled']//..//span");
    private By txtResultPrice = By.xpath("//div[@data-test='result-price-select']//div/span//input");
    private By txtFinalPriceFCOption = By.xpath("//div[@data-test='result-price-dropdown']//div//div//div//div//div[text()='Future Price FC']");
    private By txtAutoApprove = By.xpath("//div[@data-test='autoapproveelementname-select']//div/span[1]");
    private By txtAutoApprovalOption = By.xpath("//div[@data-test='autoapproveelementname-dropdown']//div//div//div//div//div[text()='Auto Approval']");
    private By chkBoxOutputElement = By.xpath("(//div[@class='ucTableHeader']//div/label//span//span)[2]");
    private By btnSave = By.xpath("//button[@data-test='common-save-button']//span");
    private By txtPriceListID = By.xpath("(//div[@class='ucTableCell ucTableCell']//div//div//span//a)[1]");
    private By txtCustomer = By.xpath("(//div[@data-test='customergroup-select']//div)[1]");
    private By txtCustomerType = By.xpath("//div[@data-test='customertype-select']//div//span//input");
    private By txtEffectiveDate = By.xpath("//input[@data-test='effectivedate-input']");
    private By txtExpiryDate = By.xpath("//input[@data-test='expirydate-input']");
    private By txtCalculationFrequency = By.xpath("//div[@name='CalculationFrequency']//div//span//input");
    private By txtCalculationDate = By.xpath("//input[@data-test='calculationdate-input']");
    private By btnSaveAndRecalculate = By.xpath("//button[@data-test='headerlogic-save-and-recalculate-button']");
    private By btnCalculate = By.xpath("//button[@data-test='common-calculate-button']");
    private By btnAddProducts = By.xpath("//button[@data-test='calculatedobject-addproducts-button']");
    private By txtProductId = By.xpath("(//div[@data-test='quick-filter-Product-ID-input'])[2]");
    private By chkBoxProductId = By.xpath("(//div[@data-column-name='_selection'])[3]");
    private By btnSelect = By.xpath("//button[@data-test='common-selectall-button']");
    private By txtReady = By.xpath("//span[text()='Ready']");
    private By txtManualOverride = By.xpath("//div[@data-test-column='Manual-Override']//div//div");
    private By btnSubmitArrow = By.xpath("(//button[@data-test-parent='LPG-detail-table-table-row'])[1]");

    public void clickOnNewPriceGrid() {
        syscoLabUI.waitTillElementLoaded(btnNewPriceGrid);
        syscoLabUI.click(btnNewPriceGrid);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickOnLockedPricing() {
        syscoLabUI.waitTillElementLoaded(btnLockedPricing);
        syscoLabUI.click(btnLockedPricing);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickAndSendLabelText(String strDefineLabel) {
        syscoLabUI.waitTillElementLoaded(txtLabel);
        syscoLabUI.click(txtLabel);
        syscoLabUI.sendKeys(txtLabel, strDefineLabel);
    }

    public void clickOnContButton() {
        syscoLabUI.waitTillElementLoaded(btnContToProd);
        syscoLabUI.click(btnContToProd);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickOnSkipButton() {
        syscoLabUI.waitTillElementLoaded(btnSkip);
        syscoLabUI.click(btnSkip);
        syscoLabUI.sleepInMilliSeconds(3000);
    }

    public void clickOnDistributedCalcCheckBox() {
        syscoLabUI.waitTillElementLoaded(chkBoxAllowDistributedCalc);
        syscoLabUI.clickWithJavascript(chkBoxAllowDistributedCalc);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickAndSendResultPrice(String strResultPrice) {
        syscoLabUI.sleepInMilliSeconds(5000);
        WebElement resultPriceElement = ActionUtils.castToWebElements(txtResultPrice);
        ActionUtils.singleClickUsingActions(resultPriceElement, strResultPrice);
        syscoLabUI.sleepInMilliSeconds(1000);
        WebElement FinalOption = ActionUtils.castToWebElements(txtFinalPriceFCOption);
        FinalOption.click();
    }

    public void clickAndSendAutoApprove(String strAutoApprove) {
        syscoLabUI.sleepInMilliSeconds(5000);
        WebElement resultPriceElement = ActionUtils.castToWebElements(txtAutoApprove);
        ActionUtils.singleClickUsingActions(resultPriceElement, strAutoApprove);
        syscoLabUI.sleepInMilliSeconds(1000);
        WebElement approvalOption = ActionUtils.castToWebElements(txtAutoApprovalOption);
        approvalOption.click();
    }

    public void clickOnOutputEleCheckBox() {
        syscoLabUI.waitTillElementLoaded(chkBoxOutputElement);
        syscoLabUI.clickWithJavascript(chkBoxOutputElement);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickOnSaveButton() {
        syscoLabUI.waitTillElementLoaded(btnSave);
        syscoLabUI.click(btnSave);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public String getFirstPriceListId() {
        syscoLabUI.waitTillElementLoaded(txtPriceListID);
        syscoLabUI.sleepInMilliSeconds(1000);
        String priceListId = syscoLabUI.getText(txtPriceListID);
        return priceListId;
    }

    public void clickOnFirstPriceListId() {
        syscoLabUI.waitTillElementLoaded(txtPriceListID);
        syscoLabUI.click(txtPriceListID);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickAndSendCustomerText(String strCustomer) throws Exception {
        syscoLabUI.sleepInMilliSeconds(2000);
        syscoLabUI.waitTillElementLoaded(txtCustomer);
        syscoLabUI.clickWithJavascript(txtCustomer);
        WebElement customerElement = ActionUtils.castToWebElements(txtCustomer);
        ActionUtils.singleClickUsingActions(customerElement, strCustomer);
        syscoLabUI.sleepInMilliSeconds(3000);
        ActionUtils.pressEnterKey();
    }

    public void clickAndSendCustomerTypeText(String strCustomerType) throws Exception {
        syscoLabUI.waitTillElementLoaded(txtCustomerType);
        WebElement customerTypeElement = ActionUtils.castToWebElements(txtCustomerType);
        ActionUtils.singleClickUsingActions(customerTypeElement, strCustomerType);
        syscoLabUI.sleepInMilliSeconds(2000);
        ActionUtils.pressEnterKey();
    }

    public void clickAndSendEffectiveDateText(String strEffectiveDate) {
        syscoLabUI.waitTillElementLoaded(txtEffectiveDate);
        syscoLabUI.click(txtEffectiveDate);
        syscoLabUI.sendKeys(txtEffectiveDate, strEffectiveDate);
        syscoLabUI.sleepInMilliSeconds(1000);
        syscoLabUI.sendKeys(txtEffectiveDate, Keys.ENTER);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickAndSendExpiryDateText(String strExpiryDate) {
        syscoLabUI.waitTillElementLoaded(txtExpiryDate);
        syscoLabUI.click(txtExpiryDate);
        syscoLabUI.sendKeys(txtExpiryDate, strExpiryDate);
        syscoLabUI.sleepInMilliSeconds(1000);
        syscoLabUI.sendKeys(txtExpiryDate, Keys.ENTER);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickAndSendCalculationFrequencyText(String strCalculationFrequency) {
        syscoLabUI.waitTillElementLoaded(txtCalculationFrequency);
        syscoLabUI.sendKeys(txtCalculationFrequency, strCalculationFrequency);
        syscoLabUI.sendKeys(txtCalculationFrequency, Keys.ENTER);
    }

    public void clickAndSendCalculationDateText(String strCalculationDate) {
        syscoLabUI.waitTillElementLoaded(txtCalculationDate);
        syscoLabUI.click(txtCalculationDate);
        syscoLabUI.sendKeys(txtCalculationDate, strCalculationDate);
    }

    public void clickOnSaveAndRecalculate() {
        syscoLabUI.waitTillElementLoaded(btnSaveAndRecalculate);
        syscoLabUI.click(btnSaveAndRecalculate);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickOnCalculateButton() {
        syscoLabUI.waitTillElementLoaded(btnCalculate);
        syscoLabUI.click(btnCalculate);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void verifyReadyMessage() {
        syscoLabUI.waitTillElementLoaded(txtReady, 10000);
    }

    public void clickOnAddProductsButton() {
        syscoLabUI.waitTillElementLoaded(btnAddProducts);
        syscoLabUI.click(btnAddProducts);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickAndSendProductIdText(String strProductId) throws Exception {
        syscoLabUI.waitTillElementLoaded(txtProductId);
        WebElement productIdElement = ActionUtils.castToWebElements(txtProductId);
        ActionUtils.singleClickUsingActions(productIdElement, strProductId);
        syscoLabUI.sleepInMilliSeconds(2000);
        ActionUtils.pressEnterKey();
    }

    public void clickOnProductCheckBox() {
        syscoLabUI.waitTillElementLoaded(chkBoxProductId, 3000);
        WebElement chkBoxElement = ActionUtils.castToWebElements(chkBoxProductId);
        ActionUtils.singleClickUsingActions(chkBoxElement);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickOnSelectButton() {
        syscoLabUI.waitTillElementLoaded(btnSelect);
        WebElement selectElement = ActionUtils.castToWebElements(btnSelect);
        ActionUtils.singleClickUsingActions(selectElement);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickAndSendManualOverrideValue(String strManualOverrideValue) throws Exception {
        syscoLabUI.waitTillElementLoaded(txtManualOverride);
        WebElement element = ActionUtils.castToWebElements(txtManualOverride);
        ActionUtils.doubleClickUsingActions(element, strManualOverrideValue);
        syscoLabUI.sleepInMilliSeconds(2000);
        ActionUtils.pressEnterKey();
        syscoLabUI.sleepInMilliSeconds(2000);
    }

    public void clickOnArrowSubmitButton() {
        syscoLabUI.waitTillElementLoaded(btnSubmitArrow, 1000);
        syscoLabUI.click(btnSubmitArrow);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

}