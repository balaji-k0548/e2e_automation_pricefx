package com.syscolab.qe.core.ui.functions;

import com.syscolab.qe.core.ui.pages.HomePage;

public class Home {
    private static HomePage homePage = new HomePage();

    /**
     * This verifies Home text in priceFX page.
     *
     * @param
     * @author Balaji.K
     */
    public static boolean verifyHomeText() {
        return homePage.isHomeTextAvailable();
    }

    /**
     * This method navigates To Quotes Page
     *
     * @param
     * @author Balaji.K
     */
    public static void navigateToQuotesPage() {
        homePage.clickOnToggleButton();
        homePage.clickOnQuotingLink();
        homePage.clickOnQuotesLink();
        System.out.println("Successfully Navigated To Quote Page.");
    }

    /**
     * This method navigates To Live Price Grid Page
     *
     * @param
     * @author Balaji.K
     */
    public static void navigateToLivePriceGridsPage() {
        homePage.clickOnToggleButton();
        homePage.clickOnPriceSettingLink();
        homePage.clickOnLivePriceGridsLink();
        System.out.println("Successfully Navigated To Live Price Grids Page.");
    }

    /**
     * This method navigates To Data Source Page
     *
     * @param
     * @author Balaji.K
     */
    public static void navigateToDataSourcePage() {
        homePage.clickOnToggleButton();
        homePage.clickOnAnalyticsLink();
        homePage.clickOnDataManagerLink();
        homePage.clickOnDataSourcesLink();
        System.out.println("Successfully Navigated To Data Source Page.");
    }
}