package com.syscolab.qe.core.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.syscolab.qe.core.ui.pages.LoginPage.syscoLabUI;

public class CompanyParametersPage {
    private By companyParametersIcon = By.xpath("//li[@title='main_pricingParametersTitle']//span//span//a");
    private By txtLabelField = By.xpath("(//div[@class='ant-dropdown-trigger ucQuickFilter']//span//input)[2]");
    private By btnRadio = By.xpath("(//label[@class='ant-radio-wrapper']//span//span)[2]");
    private By txtFeeCode = By.xpath("(//div[@class='ant-dropdown-trigger ucQuickFilter']//span//input)[10]");
    private By txtFeeCodeTypeFirstRow = By.xpath("(//div[@class='ucTableCellContent ucTableCellContent--higherPriority']//div//div//span)[8]");

    public void clickOnCompanyParametersIcon() {
        syscoLabUI.waitTillElementLoaded(companyParametersIcon);
        syscoLabUI.click(companyParametersIcon);
    }

    public void sendLabelText(String strLabelText) {
        syscoLabUI.sleepInMilliSeconds(1000);
        syscoLabUI.waitTillElementLoaded(txtLabelField);
        syscoLabUI.sendKeys(txtLabelField, strLabelText);
        syscoLabUI.sendKeys(txtLabelField, Keys.ENTER);
    }

    public void selectFirstRadioButton() {
        syscoLabUI.waitTillElementLoaded(btnRadio);
        syscoLabUI.clickWithJavascript(btnRadio);
    }

    public void sendFeeCodeText(String strFeeCodeTypeText) {
        syscoLabUI.sleepInMilliSeconds(1000);
        syscoLabUI.waitTillElementLoaded(txtFeeCode);
        syscoLabUI.sendKeys(txtFeeCode, strFeeCodeTypeText);
        syscoLabUI.sendKeys(txtFeeCode, Keys.ENTER);
    }

    public String getFirstRowTextFromFeeCodeType() {
        syscoLabUI.waitTillElementLoaded(txtFeeCodeTypeFirstRow);
        String feeCodeType = syscoLabUI.getText(txtFeeCodeTypeFirstRow);
        return feeCodeType;
    }

}
