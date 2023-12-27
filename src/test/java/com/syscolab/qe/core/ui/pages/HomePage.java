package com.syscolab.qe.core.ui.pages;

import org.openqa.selenium.By;

import static com.syscolab.qe.core.ui.pages.LoginPage.syscoLabUI;

public class HomePage {
    private By txtHome = By.xpath("//div[text()='Home']");
    private By dashbaord = By.xpath("//*[@id='root']/div[1]/span");
    private By lnkQuoting = By.xpath("//li[@id='quote-manager']//div//span[1]//div//div//span");
    private By lnkQuotes = By.xpath("//li[@id='quotes']");
    private By lnkPriceSetting = By.xpath("//li[@id='price-builder']//div//span[1]//div//div//span");
    private By lnkLivePriceGrids = By.xpath("//li[@id='live-price-grids']");
    private By lnkAnalytics = By.xpath("(//li[@id='price-analyzer']//div//span//div//div//span)[1]");
    private By lnkDataManager = By.xpath("//li[@id='pa-data-manager']//span//div//div//span");
    private By lnkDataSources = By.xpath("(//li[@id='pa-datasource-manager']//span//a)[1]");
    private By lnkagreementspromotions = By.xpath("//li[@id='contracts']");
    private By lnkAgreements = By.xpath("//li[@id='promotion-manager']");

    public boolean isHomeTextAvailable() {
        syscoLabUI.waitTillElementLoaded(txtHome);
        if (syscoLabUI.isDisplayed(txtHome)) {
            return true;
        }
        return false;
    }

    public void clickOnToggleButton() {
        syscoLabUI.click(dashbaord);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickOnQuotingLink() {
        syscoLabUI.waitTillElementLoaded(lnkQuoting);
        syscoLabUI.click(lnkQuoting);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickOnQuotesLink() {
        syscoLabUI.waitTillElementLoaded(lnkQuotes);
        syscoLabUI.click(lnkQuotes);
        syscoLabUI.sleepInMilliSeconds(2000);
    }

    public void clickOnPriceSettingLink() {
        syscoLabUI.waitTillElementLoaded(lnkPriceSetting);
        syscoLabUI.click(lnkPriceSetting);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickOnLivePriceGridsLink() {
        syscoLabUI.waitTillElementLoaded(lnkLivePriceGrids);
        syscoLabUI.click(lnkLivePriceGrids);
        syscoLabUI.sleepInMilliSeconds(2000);
    }

    public void clickOnAnalyticsLink() {
        syscoLabUI.waitTillElementLoaded(lnkAnalytics);
        syscoLabUI.click(lnkAnalytics);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickOnDataManagerLink() {
        syscoLabUI.waitTillElementLoaded(lnkDataManager);
        syscoLabUI.click(lnkDataManager);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickOnDataSourcesLink() {
        syscoLabUI.waitTillElementLoaded(lnkDataSources);
        syscoLabUI.click(lnkDataSources);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickOnAgreementsLink() {
        syscoLabUI.waitTillElementLoaded(lnkAgreements);
        syscoLabUI.click(lnkAgreements);
        syscoLabUI.sleepInMilliSeconds(1000);
    }

    public void clickagreementspromotions() {
        syscoLabUI.waitTillElementLoaded(lnkagreementspromotions);
        syscoLabUI.click(lnkagreementspromotions);
        syscoLabUI.sleepInMilliSeconds(2000);
    }
}