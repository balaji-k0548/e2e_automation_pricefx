package com.syscolab.qe.core.ids.functions;

import com.syscolab.qe.core.ids.Pages.CommonPage;
import framework.KeyMnemonic;

import static com.syscolab.qe.core.ids.Pages.PageBase.pressF3;
import static com.syscolab.qe.core.ids.Pages.PageBase.sendKey;
import static com.syscolab.qe.core.ids.common.Constants.*;
import static framework.AS400.*;

public class ObjectLinksFunction {

    private static CommonPage commonPage = new CommonPage();

    public static void openDirectoryScreen() throws Exception {
        commonPage.enterCmdInCommandLine(IDS_DIRECTORY_OPEN_CMD);
    }

    public static void isNavigateToWorkWithObjects() throws Exception {
        CommonFunction.refreshPage();
        if (waitForScreenwithText("Work with Object Links", 3)) {
            System.out.println("Successfully Navigated to Work with Object Links screen");
        } else {
            throw new Exception("Cursor isn't Navigated to Work with Object Links screen");
        }
    }

    public static void openObjectInDirectory(String strObject) throws Exception {
        int[] objPos = findTextPos(strObject);
        sendText("5", objPos[0], 3);
        sendKey(KeyMnemonic.ENTER);
    }

    public static boolean feeCodeFileStatus() throws Exception {
        boolean status = true;
        if (verifyTextInScreen("FeeCodeCustomer") && verifyTextInScreen("FeeCodeHeader") && verifyTextInScreen("FeeCodeProduct")) {
            System.out.println("Fee Code Files are available under Archive Folder.");
            return status;
        } else {
            System.out.println("Fee Code Files aren't available under Archive Folder.");
            return status = false;
        }
    }

    public static boolean fuelSurchargeFileStatus(String strOpsiteNumber) throws Exception {
        boolean status = true;
        if (verifyTextInScreen("FuelSurcharge_"+strOpsiteNumber.substring(0,2))) {
            System.out.println("Fee Code Files are available under Archive Folder.");
            return status;
        } else {
            System.out.println("Fee Code Files aren't available under Archive Folder.");
            return status = false;
        }
    }

    public static void verifyFeeCodeCustomerDetails(String strFeeCode, String strCustOpsite, String strCustUserType, String strCustValue, String strCustIncludeOrExclude, String strCustApplyToStop) throws Exception {
        if (waitForScreenwithText("FeeCodeCustomer", 10)) {
            System.out.println("Fee Code Customer file is Generated under the Directory.");
            openObjectInDirectory("FeeCodeCustomer");
            String feeCodeCustomerData = getLabelText(8, 1, 80);
            System.out.println(feeCodeCustomerData);
//            Need to add validation - strCustApplyToStop
            if (!feeCodeCustomerData.contains(strFeeCode + "|" + strCustOpsite + "|" + strCustUserType + "|" + strCustValue + "|" + strCustIncludeOrExclude + "|")) {
                throw new Exception("Fee Code Customer details aren't Matching");
            }
            System.out.println("Fee Code Customer details are Matching");
            pressF3();
        } else {
            throw new Exception("Fee Code Customer file isn't Generated under the Directory.");
        }
    }

    public static void verifyFeeCodeHeaderDetails(String strFeeCodeType, String strFeeCode, String strDescription, String strEffectiveDate, String strExpiryDate, String strApplyTo, String strExcludeControlledPayout, String strCalculateEachAsFullCase, String strCustomerType) throws Exception {
        if (waitForScreenwithText("FeeCodeHeader", 10)) {
            System.out.println("Fee Code Header file is Generated under the Directory.");
            openObjectInDirectory("FeeCodeHeader");
            String feeCodeHeaderData = getLabelText(8, 1, 80);
            System.out.println(feeCodeHeaderData);
            String effDateParts[] = strEffectiveDate.split("/");
            String expDateParts[] = strExpiryDate.split("/");
            //            check
            if (!feeCodeHeaderData.contains(strFeeCodeType.substring(0, 4) + "C" + "|" + strFeeCode + "|" + strDescription + "|" + effDateParts[2] + effDateParts[1] + effDateParts[0] + "|" + expDateParts[2] + expDateParts[1] + expDateParts[0] + "|" + strApplyTo + "|" + strExcludeControlledPayout + "|" + strCalculateEachAsFullCase + "|" + strCustomerType)) {
                throw new Exception("Fee Code Header details aren't Matching");
            }
            System.out.println("Fee Code Header details are Matching");
            pressF3();
        } else {
            throw new Exception("Fee Code Header file isn't Generated under the Directory.");
        }
    }

    public static void verifyFeeCodeProductDetails(String strFeeCode, String strPrdOpsite, String strProdAttribute, String strPrdAttributeValue, String strProdRateType, String strProdRateUOM, String strProdRateAmount, String strProdAttributeUOM) throws Exception {
        if (waitForScreenwithText("FeeCodeProduct", 10)) {
            System.out.println("Fee Code Product file is Generated under the Directory.");
            openObjectInDirectory("FeeCodeProduct");
            String feeCodeProductData = getLabelText(8, 1, 80);
            System.out.println(feeCodeProductData);
            if ((!feeCodeProductData.contains(strFeeCode + "|" + strPrdOpsite + "|" + strProdAttribute + "|" + strPrdAttributeValue)) && (!feeCodeProductData.contains(strProdRateType + "|" + strProdRateUOM + "|" + strProdRateAmount + ".00")) && (!feeCodeProductData.contains(strProdAttributeUOM))) {
                throw new Exception("Fee Code Product details aren't Matching");
            }
            System.out.println("Fee Code Product details are Matching");
            pressF3();
        } else {
            throw new Exception("Fee Code Product file isn't Generated under the Directory.");
        }
    }

    public static void moveFilesToArchiveFolder_FeeCode() throws Exception {
        commonPage.enterCmdInCommandLine(IDS_CALL_PGM_FEECODE);
    }

    public static void moveFilesToArchiveFolder_FuelSurcharge() throws Exception {
        commonPage.enterCmdInCommandLine(IDS_CALL_PGM_FUELSURCHARGE);
    }

    public static void moveFilesToArchiveFolder_LivePriceGrid() throws Exception {
        commonPage.enterCmdInCommandLine(IDS_CALL_PGM_LIVEPRICEGRID);
    }

    public static boolean priceListFileStatus() throws Exception {
        boolean status = true;
        if (verifyTextInScreen("PriceListDetail") && verifyTextInScreen("PriceListHeader")) {
            System.out.println("Price List Files are available under Archive Folder.");
            return status;
        } else {
            System.out.println("Price List Files aren't available under Archive Folder.");
            return status = false;
        }
    }

    public static void verifyPriceListDetails( String strCustOpsite, String strpricelistNo, String strItemNo, String strCustomerNo, String strCustAccountType, String strEffectiveDate, String strExpiryDate, String strCurrentPrice, String strFuturePrice) throws Exception {
        if (waitForScreenwithText("PriceListDetail", 10)) {
            System.out.println("Price List Details file is Generated under the Directory.");
            openObjectInDirectory("PriceListDetail");
            String feeCodeCustomerData = getLabelText(8, 1, 80);
            System.out.println(feeCodeCustomerData);
            String effDateParts[] = strEffectiveDate.split("/");
            String expDateParts[] = strExpiryDate.split("/");
//            check on decimal places for prices
            if (!feeCodeCustomerData.contains( strCustOpsite + "|" + strpricelistNo + "|" + strItemNo + "|" + strCustomerNo + "|" + strCustAccountType + "|" + effDateParts[2] + effDateParts[1] + effDateParts[0] + "|" + expDateParts[2] + expDateParts[1] + expDateParts[0] + "|" + strCurrentPrice+.0000 + "|" + strFuturePrice+.0000)) {
                throw new Exception("Price List details aren't Matching");
            }
            System.out.println("Price List details are Matching");
            pressF3();
        } else {
            throw new Exception("Price List Details file isn't Generated under the Directory.");
        }
    }

    public static void verifyPriceListHeaderDetails( String strCustOpsite, String strCustomerNo, String strCustAccountType, String strPriceListNo, String strEffectiveDate, String strExpiryDate, String calculationFrequency, String strDefineLabel) throws Exception {
        if (waitForScreenwithText("PriceListHeader", 10)) {
            System.out.println("Price List Header file is Generated under the Directory.");
            openObjectInDirectory("PriceListHeader");
            String feeCodeHeaderData = getLabelText(8, 1, 80);
            System.out.println(feeCodeHeaderData);
            String effDateParts[] = strEffectiveDate.split("/");
            String expDateParts[] = strExpiryDate.split("/");
            //            check H mark- HardStopFlag|
            if (!feeCodeHeaderData.contains( strCustOpsite + "|" + strCustomerNo + "|" + strCustAccountType + "|" + strPriceListNo + "|" + effDateParts[2] + effDateParts[1] + effDateParts[0] + "|" + expDateParts[2] + expDateParts[1] + expDateParts[0] + "|H|" + calculationFrequency + "||" + strDefineLabel)) {
                throw new Exception("Price List Header details aren't Matching");
            }
            System.out.println("Price List Header details are Matching");
            pressF3();
        } else {
            throw new Exception("Price List Header file isn't Generated under the Directory.");
        }
    }

    public static void verifyFuelSurchargeDetails(String strCustomers, String strFSCCode, String strEffectiveDate, String strFSCCodeDescription, String strDefaultFuelRate, String strUOM) throws Exception {
        if (waitForScreenwithText("FuelSurcharge_"+strCustomers.substring(0,2), 10)) {
            System.out.println("Fuel Surcharge file is Generated under the Directory.");
            openObjectInDirectory("FuelSurcharge_"+strCustomers.substring(0,2));
            String fuelSurchargeData = getLabelText(8, 1, 80);
            System.out.println(fuelSurchargeData);
            String effDateParts[] = strEffectiveDate.split("/");
            //            check
            if (!fuelSurchargeData.contains(strCustomers.substring(0,2) + "|" + strCustomers.substring(4) + "|" + strFSCCode + "|" + effDateParts[2] + effDateParts[1] + effDateParts[0] + "|" + strFSCCodeDescription + "|" + strDefaultFuelRate + "|" + strUOM)) {
                throw new Exception("Fuel Surcharge details aren't Matching");
            }
            System.out.println("Fuel Surcharge details are Matching");
            pressF3();
        } else {
            throw new Exception("Fuel Surcharge file isn't Generated under the Directory.");
        }
    }
}