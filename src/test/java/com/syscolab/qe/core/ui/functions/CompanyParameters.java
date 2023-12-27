package com.syscolab.qe.core.ui.functions;

import com.syscolab.qe.core.ui.pages.CompanyParametersPage;
import org.testng.Assert;

import static com.syscolab.qe.core.ui.pages.LoginPage.syscoLabUI;

public class CompanyParameters {

    private static CompanyParametersPage companyParametersPage = new CompanyParametersPage();

    /**
     * This method Verifies Quote is getting submitted from PriceFx portal.
     *
     * @param strLabelText
     * @param strFeeCodeTypeText
     * @author Balaji.K
     */
    public static void verifyQuoteSubmittedFromPriceFX(String strLabelText, String strFeeCodeTypeText) throws Exception {
        try {
            syscoLabUI.sleepInMilliSeconds(5000);
            companyParametersPage.clickOnCompanyParametersIcon();
            companyParametersPage.sendLabelText(strLabelText);
            companyParametersPage.selectFirstRadioButton();
            companyParametersPage.sendFeeCodeText(strFeeCodeTypeText);
            String mstrFeeCodeType = companyParametersPage.getFirstRowTextFromFeeCodeType();
            Assert.assertEquals(mstrFeeCodeType, strFeeCodeTypeText, "Fee Code Suggestion is not available, Hence Quote didn't reach Destination");
        } catch (Exception e) {
            throw new Exception("Verification Of Quote Submitted From PriceFX Unsuccessful" + e.getMessage());
        }
    }

}