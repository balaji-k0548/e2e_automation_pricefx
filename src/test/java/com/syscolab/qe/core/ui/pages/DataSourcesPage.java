package com.syscolab.qe.core.ui.pages;

import com.syscolab.qe.core.ui.common.ActionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import static com.syscolab.qe.core.ui.pages.LoginPage.syscoLabUI;

public class DataSourcesPage {
    private By lnkDataSources = By.xpath("//div[@data-test='quick-filter-Label-input']//span[1]//input");
    private By txtData = By.xpath("//*[text()='Data']");
    private By txtHeaderPLNo = By.xpath("//div[@data-test='quick-filter-PriceList-Num-input']//span//input");
    private By txtDetailPLNo = By.xpath("//div[@data-test='quick-filter-Price-List-Num--input']//span//input");
    private By txtCalculatedPLNo = By.xpath("//div[@data-test='quick-filter-Price-List#-or-Name-input']");
    private By btnRefresh = By.xpath("//*[text()='Data']");
//    private By txtPriceListNumAfterSearch = By.xpath("(//div[@data-column-name='PriceListNum'])[2]");

    public void clickAndSendLabelText(String strLabelTxt) {
        syscoLabUI.waitTillElementLoaded(lnkDataSources, 1000);
        syscoLabUI.click(lnkDataSources);
        syscoLabUI.sendKeys(lnkDataSources, strLabelTxt);
        syscoLabUI.sendKeys(lnkDataSources, Keys.ENTER);
        syscoLabUI.sleepInMilliSeconds(1000);
        syscoLabUI.click(By.xpath("//a[@title='" + strLabelTxt + "']"));
    }

    public void clickOnDataTab() {
        syscoLabUI.waitTillElementLoaded(txtData);
        syscoLabUI.click(txtData);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickOnRefreshButton() {
        syscoLabUI.waitTillElementLoaded(btnRefresh);
        syscoLabUI.click(btnRefresh);
        syscoLabUI.sleepInMilliSeconds(5000);
    }

    public void clickAndSendHeaderPriceListNo(String strPriceListNum) {
        syscoLabUI.waitTillElementLoaded(txtHeaderPLNo, 1000);
        syscoLabUI.click(txtHeaderPLNo);
        syscoLabUI.sendKeys(txtHeaderPLNo, strPriceListNum);
        syscoLabUI.sendKeys(txtHeaderPLNo, Keys.ENTER);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickAndSendDetailPriceListNo(String strPriceListNum) {
        syscoLabUI.waitTillElementLoaded(txtDetailPLNo, 1000);
        syscoLabUI.click(txtDetailPLNo);
        syscoLabUI.sendKeys(txtDetailPLNo, strPriceListNum);
        syscoLabUI.sendKeys(txtDetailPLNo, Keys.ENTER);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickAndSendCalculatedPriceListNo(String strPriceListNum) throws Exception {
        syscoLabUI.waitTillElementLoaded(txtCalculatedPLNo, 1000);
        WebElement customerTypeElement = ActionUtils.castToWebElements(txtCalculatedPLNo);
        ActionUtils.singleClickUsingActions(customerTypeElement, strPriceListNum);
        ActionUtils.pressEnterKey();
        syscoLabUI.sleepInMilliSeconds(1000);
    }

}