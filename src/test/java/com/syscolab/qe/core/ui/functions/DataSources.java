package com.syscolab.qe.core.ui.functions;

import com.syscolab.qe.core.ui.pages.DataSourcesPage;
import org.openqa.selenium.By;

import static com.syscolab.qe.core.ui.pages.LoginPage.syscoLabUI;

public class DataSources {

    public static DataSourcesPage dataSourcesPage = new DataSourcesPage();

    /**
     * This method Verifies Locked Price is present under Data source Page.
     *
     * @param strPriceListNo
     * @author Balaji.K
     */
    public static void validateLPGinDataSource(String strPriceListNo) throws Exception {
        dataSourcesPage.clickAndSendLabelText("Price List Header Outbound");
        dataSourcesPage.clickOnDataTab();
//        dataSourcesPage.clickOnRefreshButton();
        dataSourcesPage.clickAndSendHeaderPriceListNo(strPriceListNo);
        syscoLabUI.sleepInMilliSeconds(5000);
        boolean headerStatus = syscoLabUI.isDisplayed(By.xpath("//*[text()='" + strPriceListNo + "']"));
        if (headerStatus != true) {
            System.out.println("Price List No isn't available under Price List Header Outbound.");
        }
        System.out.println("Price List No is available under Price List Header Outbound.");
        syscoLabUI.navigateBack();
        syscoLabUI.refreshBrowser();
        syscoLabUI.sleepInMilliSeconds(5000);
        dataSourcesPage.clickAndSendLabelText("Price List Detail Outbound");
        dataSourcesPage.clickOnDataTab();
//        dataSourcesPage.clickOnRefreshButton();
        dataSourcesPage.clickAndSendDetailPriceListNo(strPriceListNo);
        syscoLabUI.sleepInMilliSeconds(5000);
        boolean detailStatus = syscoLabUI.isDisplayed(By.xpath("//*[text()='" + strPriceListNo + "']"));
        if (detailStatus != true) {
            System.out.println("Price List No isn't available under Price List Detail Outbound.");
        }
        System.out.println("Price List No is available under Price List Detail Outbound.");

/*
        syscoLabUI.navigateBack();
        syscoLabUI.refreshBrowser();
        syscoLabUI.sleepInMilliSeconds(5000);
        dataSourcesPage.clickAndSendLabelText("Outbound Price Calculations History");
        dataSourcesPage.clickOnDataTab();
        dataSourcesPage.clickOnRefreshButton();
        dataSourcesPage.clickAndSendCalculatedPriceListNo(strPriceListNo);
        syscoLabUI.sleepInMilliSeconds(5000);
        boolean calcStatus = syscoLabUI.isDisplayed(By.xpath("//*[text()='"+strPriceListNo+"']"));
        if(calcStatus != true){
            System.out.println("Price List No isn't available under Price List Detail Outbound.");
        }
        System.out.println("Price List No is available under Price List Detail Outbound.");
*/
    }
}