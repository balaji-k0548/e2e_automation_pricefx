package com.syscolab.qe.core.ui.tests;

import com.syscolab.qe.core.excelUtils.ReadExcel;
import com.syscolab.qe.core.ids.Pages.PageBase;
import com.syscolab.qe.core.ids.common.Constants;
import com.syscolab.qe.core.ids.functions.*;
import com.syscolab.qe.core.reporting.SyscoLabListener;
import com.syscolab.qe.core.ui.common.ActionUtils;
import com.syscolab.qe.core.ui.common.UIConstants;
import com.syscolab.qe.core.ui.functions.CompanyParameters;
import com.syscolab.qe.core.ui.functions.Home;
import com.syscolab.qe.core.ui.functions.Login;
import com.syscolab.qe.core.ui.functions.Quotes;
import com.syscolab.qe.core.util.fileutil.DataProperty;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.io.FileNotFoundException;

import static com.syscolab.qe.core.ids.common.Constants.*;
import static com.syscolab.qe.core.ids.functions.FunctionBase.disconnectBox;
import static com.syscolab.qe.core.ids.functions.FunctionBase.pressEnter;
import static com.syscolab.qe.core.ids.functions.Login.connectAndLoginToIDSBox;

@Listeners(SyscoLabListener.class)
public class FeeCodeMaintenanceTest extends UITestBase {

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
     * This method achieve Login functionality of PriceFX application.
     *
     * @param
     * @author Balaji.K
     */
    public static void priceFX_Login() throws Exception {
        try {

            String syscoUserId = DataProperty.getProperty("syscoUserId");
            String syscoPassword = DataProperty.getProperty("syscoPassword");
            String username = DataProperty.getProperty("username");
            String partition = DataProperty.getProperty("partition");
            String password = DataProperty.getProperty("password");

            Login.loadLoginScreen(UIConstants.PRICE_FX_APP_URL);
            if (!Login.loginPage.getLogoStatus()) {
                Login.SyscoSingleSignOn(syscoUserId, syscoPassword);
            }
            Login.loginToPriceFX(username, partition, password);
            Home.verifyHomeText();
        } catch (Exception e) {
            throw new Exception("Unable to Login" + e.getMessage());
        }
    }

    /**
     * This testCase is implemented to create a session on IDS BOX.
     *
     * @author Balaji.K
     */
    public static void idsSetUp() throws Exception {
        try {
            connectAndLoginToIDSBox(Constants.IDS_BOX.toLowerCase());
        } catch (Exception e) {
            throw new Exception("Unable to Login" + e.getMessage());
        }
    }

    /**
     * This testCase is implemented to disconnect a session on IDS BOX.
     *
     * @author Balaji.K
     */
    public static void idsSetDown() throws Exception {
        disconnectBox();
    }

    /**
     * This testCase is implemented to create New Quote for Fee Code Maintenance and Verify fee is updated on IDS.
     *
     * @author Balaji.K
     */
    @Test
    public void priceFX_FeeCodeMaintenance() throws Exception {
        try {

            String flag = ReadExcel.getDataFromDataSheet(filePath, "ExecutionFlag", sheetName);
            String customerNo = ReadExcel.getDataFromDataSheet(filePath, "CustomerNo", sheetName);
            String itemNo = ReadExcel.getDataFromDataSheet(filePath, "ItemNo", sheetName);
            String quantity = ReadExcel.getDataFromDataSheet(filePath, "Quantity", sheetName);
            String routeNo = ReadExcel.getDataFromDataSheet(filePath, "RouteNo", sheetName);
            String shippingDDMMYYYY = ReadExcel.getDataFromDataSheet(filePath, "ShippingDate", sheetName);
            String customerType = ReadExcel.getDataFromDataSheet(filePath, "CustomerType", sheetName);
            String feeCodeType = ReadExcel.getDataFromDataSheet(filePath, "FeeCodeType", sheetName);
            String feeCodeDescription = ReadExcel.getDataFromDataSheet(filePath, "FeeCodeDescription", sheetName);
            String applyTo = ReadExcel.getDataFromDataSheet(filePath, "ApplyTo", sheetName);
            String excludeControlledPayout = ReadExcel.getDataFromDataSheet(filePath, "ExcludeControlledPayout", sheetName);
            String calculateEachAsFullCase = ReadExcel.getDataFromDataSheet(filePath, "CalculateEachAsFullCase", sheetName);
            String rowEntry = ReadExcel.getDataFromDataSheet(filePath, "RowEntry", sheetName);
            String custOpsite = ReadExcel.getDataFromDataSheet(filePath, "CustOpsite", sheetName);
            String custUserType = ReadExcel.getDataFromDataSheet(filePath, "CustUserType", sheetName);
            String custValue = ReadExcel.getDataFromDataSheet(filePath, "CustValue", sheetName);
            String custIncludeOrExclude = ReadExcel.getDataFromDataSheet(filePath, "CustIncludeOrExclude", sheetName);
            String custApplyToStop = ReadExcel.getDataFromDataSheet(filePath, "CustApplyToStop", sheetName);
            String prdOpsite = ReadExcel.getDataFromDataSheet(filePath, "PrdOpsite", sheetName);
            String prodAttribute = ReadExcel.getDataFromDataSheet(filePath, "ProdAttribute", sheetName);
            String prdAttributeValue = ReadExcel.getDataFromDataSheet(filePath, "PrdAttributeValue", sheetName);
            String prodAttributeUOM = ReadExcel.getDataFromDataSheet(filePath, "ProdAttributeUOM", sheetName);
            String prodRateType = ReadExcel.getDataFromDataSheet(filePath, "ProdRateType", sheetName);
            String prodRateUOM = ReadExcel.getDataFromDataSheet(filePath, "ProdRateUOM", sheetName);
            String prodRateAmount = ReadExcel.getDataFromDataSheet(filePath, "ProdRateAmount", sheetName);
            String label = ReadExcel.getDataFromDataSheet(filePath, "Label", sheetName);

            String[] extractDDMMYYYY = shippingDDMMYYYY.split("/");
            String shippingDate = extractDDMMYYYY[0];
            String shippingMonth = extractDDMMYYYY[1];
            String shippingYear = extractDDMMYYYY[2];

            String feeCode = ActionUtils.randomStringGenerate(3);

            if (flag.equals("Y")) {

                idsSetUp();
                CommonFunction.navigateToDateVerificationWarning();
                CommonFunction.navigateToSyscoCanadaITEnvironment(OPCO_NUMBER);
                CommonFunction.navigateToDateVerificationWarning();
                ObjectLinksFunction.moveFilesToArchiveFolder_FeeCode();
                System.out.println("Clearing Old Files from Directory in IDS.");
                idsSetDown();

                priceFX_Login();
                Home.navigateToQuotesPage();
                Quotes.chooseFeeCodeMaintenanceOption();
                syscoLabUI.sleepInMilliSeconds(10000);
                Quotes.updateFeeCodeMaintenanceQuote(effectiveDate, expiryDate, customerType, feeCodeType, feeCodeDescription, applyTo, excludeControlledPayout, calculateEachAsFullCase, feeCode);
                Quotes.updateCustomerRowInFeeCodeMaintenanceQuote(Integer.parseInt(rowEntry), custOpsite, custUserType, custValue, custIncludeOrExclude, custApplyToStop);
                Quotes.chooseProductInput_Item();
                syscoLabUI.sleepInMilliSeconds(5000);
                Quotes.updateProductItemRowInFeeCodeMaintenanceQuote(Integer.parseInt(rowEntry), prdOpsite, prodAttribute, prdAttributeValue, prodAttributeUOM, prodRateType, prodRateUOM, prodRateAmount);
                Quotes.chooseSubmitButton();
                CompanyParameters.verifyQuoteSubmittedFromPriceFX(label, feeCode);
                System.out.println("Quote placed from PriceFx Portal.");

                Thread.sleep(360000);
                idsSetUp();

                CommonFunction.navigateToDateVerificationWarning();
                CommonFunction.navigateToSyscoCanadaITEnvironment(OPCO_NUMBER);
                CommonFunction.navigateToDateVerificationWarning();

                ObjectLinksFunction.openDirectoryScreen();
                ObjectLinksFunction.openObjectInDirectory("FEECODE");
                if (ObjectLinksFunction.feeCodeFileStatus() == false) {
                    Thread.sleep(240000);
                    CommonFunction.refreshPage();
                }
                CommonFunction.refreshPage();
                ObjectLinksFunction.isNavigateToWorkWithObjects();
                ObjectLinksFunction.verifyFeeCodeCustomerDetails(feeCode, custOpsite, custUserType, custValue, custIncludeOrExclude, custApplyToStop);
                ObjectLinksFunction.verifyFeeCodeHeaderDetails(feeCodeType, feeCode, feeCodeDescription, effectiveDate, expiryDate, applyTo, excludeControlledPayout, calculateEachAsFullCase, customerType);
                ObjectLinksFunction.verifyFeeCodeProductDetails(feeCode, prdOpsite, prodAttribute, prdAttributeValue, prodRateType, prodRateUOM, prodRateAmount, prodAttributeUOM);
                PageBase.pressF3();
                Thread.sleep(3000);
                ObjectLinksFunction.moveFilesToArchiveFolder_FeeCode();
                Thread.sleep(3000);
                CommonFunction.refreshPage();
                ObjectLinksFunction.feeCodeFileStatus();
                PageBase.pressF3();
                CommonFunction.openSQLStatementScreen();
                SQLScreenFunction.updateDestinationTableUsingSQL(IDS_FEECODE_CUSTOMER_OUTBOUND, feeCode, "Fee Code");
                SQLScreenFunction.updateDestinationTableUsingSQL(IDS_FEECODE_HEADER_OUTBOUND, feeCode, "Fee Code");
                SQLScreenFunction.updateDestinationTableUsingSQL(IDS_FEECODE_PRODUCT_OUTBOUND, feeCode, "Fee Code");
                PageBase.pressF3();
                pressEnter();

//            CommonFunction.chooseOptionOrEnterCommand(IDS_ENTER_CUSTOMER_ORDER);
                CommonFunction.navigateToSyscoCanadaITEnvironment(IDS_ENTER_CUSTOMER_ORDER);
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
                System.out.println("Order Number After Invoicing : " + orderNumberAfterInvocing);

                CommonFunction.openSQLStatementScreen();
                boolean status = SQLScreenFunction.validateFeeCodeUsingSQL("select * from uzordxl0 where uzornr = " + orderNumberAfterInvocing + "", feeCode, prodRateAmount);
                if (status = false) {
                    SQLScreenFunction.workWithUserJobs();
                    boolean statusAfterUserJobs = SQLScreenFunction.validateFeeCodeUsingSQL("select * from uzordxl0 where uzornr = " + orderNumberAfterInvocing + "", feeCode, prodRateAmount);
                    if (statusAfterUserJobs == false) {
                        System.out.println("Fee Code Status Not visible after triggering via Work with User Jobs.");
                        throw new Exception("Unable to Navigate to Work with User Jobs Page.");
                    }
                }
                System.out.println("Fee Code Maintenance Completed.");
                idsSetDown();
            } else {
                System.out.println("Kindly Check the parameters in Test data Sheet.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @AfterMethod
    public void tearDown() {
        syscoLabUI.quit();
    }
}