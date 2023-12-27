package com.syscolab.qe.core.ui.tests;

import com.syscolab.qe.core.excelUtils.ReadExcel;
import com.syscolab.qe.core.ids.Pages.PageBase;
import com.syscolab.qe.core.ids.functions.*;
import com.syscolab.qe.core.reporting.SyscoLabListener;
import com.syscolab.qe.core.ui.common.ActionUtils;
import com.syscolab.qe.core.ui.functions.CompanyParameters;
import com.syscolab.qe.core.ui.functions.Home;
import com.syscolab.qe.core.ui.functions.Quotes;
import com.syscolab.qe.core.util.fileutil.DataProperty;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;

import static com.syscolab.qe.core.ids.common.Constants.*;
import static com.syscolab.qe.core.ids.functions.FunctionBase.pressEnter;
import static com.syscolab.qe.core.ui.tests.FeeCodeMaintenanceTest.*;

@Listeners(SyscoLabListener.class)
public class FuelSurchargeTest extends UITestBase {

    public static String filePath;
    public static String sheetName;
    public static String effectiveDate;
    public static String expiryDate;

    static {
        try {
            filePath = DataProperty.getProperty("filePath");
            sheetName = "FeeCodeMaintenance";
            effectiveDate = DataProperty.getProperty("effectiveDate");
            expiryDate = DataProperty.getProperty("expiryDate");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeClass
    /**
     * This sets the attributes
     */
    public void loginToGum(ITestContext iTestContext) {
        iTestContext.setAttribute("feature", "test_module_new - test_feature_new");
    }

    /**
     * This testCase is implemented to create New Quote for Fuel Surcharge and Verify fee is updated on IDS.
     *
     * @author Balaji.K
     */
    @Test
    public void priceFX_FeeCodeMaintenance() throws Exception {
        try {
            String flag = "Y";
//                    ReadExcel.getDataFromDataSheet(filePath, "ExecutionFlag", sheetName);
            String customerType = DataProperty.getProperty("CustomerType");
            String fscCodeDescription = DataProperty.getProperty("FSCCodeDescription");
            String autoApprove = DataProperty.getProperty("AutoApprove");
            String customers = DataProperty.getProperty("Customers");
            String fuelType = DataProperty.getProperty("FuelType");
            String fuelTypeLocation = DataProperty.getProperty("FuelTypeLocation");
            String uom = DataProperty.getProperty("UOM");
            String minFuelRate = DataProperty.getProperty("MinFuelRate");
            String maxFuelRate = DataProperty.getProperty("MaxFuelRate");
            String fuelRateIncrement = DataProperty.getProperty("FuelRateIncrement");
            String minFSCRate = DataProperty.getProperty("MinFSCRate");
            String maxFSCRate = DataProperty.getProperty("MaxFSCRate");
            String fscIncrement = DataProperty.getProperty("FSCIncrement");
            String defaultFuelRate = DataProperty.getProperty("DefaultFuelRate");
            String currency = DataProperty.getProperty("Currency");
            String frequency = DataProperty.getProperty("Frequency");
            String calculationDay = DataProperty.getProperty("CalculationDay");
            String calculationMethod = DataProperty.getProperty("CalculationMethod");


            String label ="Fuel Surcharge Outbound";
//                    ReadExcel.getDataFromDataSheet(filePath, "Label", sheetName);
            String fscCode = ActionUtils.randomStringGenerate(3);

            if (flag.equals("Y")) {

                idsSetUp();
                CommonFunction.navigateToDateVerificationWarning();
                CommonFunction.navigateToSyscoCanadaITEnvironment(OPCO_NUMBER);
                CommonFunction.navigateToDateVerificationWarning();
                ObjectLinksFunction.moveFilesToArchiveFolder_FuelSurcharge();
                System.out.println("Clearing Old Files from Directory in IDS.");
                idsSetDown();

                priceFX_Login();
                Home.navigateToQuotesPage();
                Quotes.chooseFuelSurchargeMaintenanceOption();
                syscoLabUI.sleepInMilliSeconds(5000);
                Quotes.updateFuelSurchargeQuote(effectiveDate, expiryDate, customerType, fscCode, fscCodeDescription, autoApprove,
                        customers, fuelType, fuelTypeLocation, uom, minFuelRate, maxFuelRate, fuelRateIncrement, minFSCRate, maxFSCRate,
                        fscIncrement, currency, frequency, calculationDay, calculationMethod);
                syscoLabUI.sleepInMilliSeconds(5000);
                Quotes.chooseSubmitButton();
                CompanyParameters.verifyQuoteSubmittedFromPriceFX(label, fscCode);
                System.out.println("Quote placed from PriceFx Portal.");

//                verify below codes to be handled based on file generation
                Thread.sleep(360000);
                idsSetUp();

                CommonFunction.navigateToDateVerificationWarning();
                CommonFunction.navigateToSyscoCanadaITEnvironment(OPCO_NUMBER);
                CommonFunction.navigateToDateVerificationWarning();

                ObjectLinksFunction.openDirectoryScreen();
                ObjectLinksFunction.openObjectInDirectory("FUELSURCHARGE");
                if (ObjectLinksFunction.fuelSurchargeFileStatus(customers) == false) {
                    Thread.sleep(240000);
                    CommonFunction.refreshPage();
                }
                CommonFunction.refreshPage();
                ObjectLinksFunction.isNavigateToWorkWithObjects();
                ObjectLinksFunction.verifyFuelSurchargeDetails(customers, fscCode, effectiveDate, fscCodeDescription, defaultFuelRate, uom);
                PageBase.pressF3();
                Thread.sleep(3000);

                /* move files to archive??
                ObjectLinksFunction.moveFilesToArchiveFolder_FuelSurcharge();
                Thread.sleep(3000);
                CommonFunction.refreshPage();
                ObjectLinksFunction.fuelSurchargeFileStatus(customers);
                PageBase.pressF3();*/

                /* cross check do we need to monitor sql screen
                CommonFunction.openSQLStatementScreen();
                SQLScreenFunction.updateDestinationTableUsingSQL(IDS_FEECODE_PRODUCT_OUTBOUND, feeCode, "Fee Code");
                PageBase.pressF3();
                pressEnter();*/

//                Place order from IDS

                /*CommonFunction.navigateToSyscoCanadaITEnvironment(IDS_ENTER_CUSTOMER_ORDER);
                Thread.sleep(2000);
                CustomerOrderFunction.navigateToEnterCustomerOrders();
                String memoCodeAppliedOnOrder = CustomerOrderFunction.addAnItemAndAcceptOrder(customerNo, shippingDate, shippingMonth, shippingYear, routeNo, itemNo, quantity);
                System.out.println("Memo Code Applied During Order : " + memoCodeAppliedOnOrder);
                CommonFunction.chooseOptionOrEnterCommand(IDS_PRINT_PICK_TICKET);
                Thread.sleep(2000);
                PrintPickTicketOptsFunction.navigateToPrintPickTicketOpts();
                PrintPickTicketOptsFunction.pickTickets(shippingDate, shippingMonth, shippingYear, routeNo);
                CommonFunction.navigateToSyscoCanadaITEnvironment(OPCO_NUMBER);
                CommonFunction.navigateToDateVerificationWarning();
                String orderNumberBeforeInvoicing = WarehouseFunction.orderStatus(shippingDate, shippingMonth, shippingYear, routeNo);
                System.out.println("Order Number Before Invoicing : " + orderNumberBeforeInvoicing);
                CommonFunction.openSQLStatementScreen();
                SQLScreenFunction.updateOrderUsingSQL("update shajcpp set ajambt='4' where trim(aja2dn)=(" + orderNumberBeforeInvoicing + ")");
                WarehouseFunction.invoiceSubmission(shippingDate, shippingMonth, shippingYear, routeNo);
                CommonFunction.chooseOptionOrEnterCommand(IDS_ORDER_ENTRY_INQUIRY);
                Thread.sleep(2000);
                String orderNumberAfterInvocing = OrderEntryInquiryFunction.openInvoiceHeader(customerNo, shippingDate, shippingMonth, shippingYear, "20");
                System.out.println("Order Number After Invoicing : " + orderNumberAfterInvocing);*/

            }
        } catch (Exception e) {
        System.out.println(e.getMessage());
        throw e;
    }
    }

}
