package com.syscolab.qe.core.ui.tests;

import com.syscolab.qe.core.api.common.APIConstants;
import com.syscolab.qe.core.api.requests.API;
import com.syscolab.qe.core.excelUtils.ReadExcel;
import com.syscolab.qe.core.ids.Pages.PageBase;
import com.syscolab.qe.core.ids.functions.*;
import com.syscolab.qe.core.ui.common.ActionUtils;
import com.syscolab.qe.core.ui.common.UIConstants;
import com.syscolab.qe.core.ui.functions.*;
import com.syscolab.qe.core.ui.functions.Login;
import com.syscolab.qe.core.util.dates.DateUtils;
import com.syscolab.qe.core.util.fileutil.DataProperty;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;

import static com.syscolab.qe.core.ids.common.Constants.*;
import static com.syscolab.qe.core.ids.functions.FunctionBase.pressEnter;
import static framework.AS400.sendKeys;

public class LivePriceGridTest extends UITestBase {

    public static String filePath;
    public static String sheetName;
    public static String effectiveDate;
    public static String expiryDate;

    static {
        try {
            filePath = DataProperty.getProperty("filePath");
            sheetName = "LivePriceGrid";
            effectiveDate = DataProperty.getProperty("effectiveDate");
            expiryDate = DataProperty.getProperty("expiryDate");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeClass
    public void init(ITestContext iTestContext) {
        iTestContext.setAttribute("feature", "Login - ValidLogin");
    }

    /**
     * This method achieve Login functionality of SYSCO Shop application.
     *
     * @param
     * @author Balaji.K
     */
    public static void shopLogin() throws Exception {
        try {

            String shopEmail = DataProperty.getProperty("shopEmail");
            String shopUserId = DataProperty.getProperty("shopUserId");
            String shopPassword = DataProperty.getProperty("shopPassword");

            Login.loadLoginScreen(UIConstants.SHOP_APP_URL);
            Thread.sleep(2000);
            Login.loginToShop(shopEmail);
            Login.SyscoSingleSignOn_EmailId(shopEmail);
            Login.SyscoSingleSignOn( shopUserId, shopPassword);

        } catch (Exception e) {
            throw new Exception("Unable to Login" + e.getMessage());
        }
    }

    /**
     * This testCase is implemented to Set Live Price Grid and Verify Prices are reflected on IDS & Shop.
     *
     * @author Balaji.K
     */
    @Test
    public void priceFX_LivePriceGrid() throws Exception {
        try {

            String flag = ReadExcel.getDataFromDataSheet(filePath, "ExecutionFlag", sheetName);
            String customerNo = ReadExcel.getDataFromDataSheet(filePath, "CustomerNo", sheetName);
            String itemNo = ReadExcel.getDataFromDataSheet(filePath, "ItemNo", sheetName);
            String quantity = ReadExcel.getDataFromDataSheet(filePath, "Quantity", sheetName);
            String routeNo = ReadExcel.getDataFromDataSheet(filePath, "RouteNo", sheetName);
            String shippingDDMMYYYY = ReadExcel.getDataFromDataSheet(filePath, "ShippingDate", sheetName);
            String defineLabel = ReadExcel.getDataFromDataSheet(filePath, "DefineLabel", sheetName);
            String resultPrice = ReadExcel.getDataFromDataSheet(filePath, "ResultPrice", sheetName);
            String autoApprove = ReadExcel.getDataFromDataSheet(filePath, "AutoApprove", sheetName);
            String customer = ReadExcel.getDataFromDataSheet(filePath, "Opcocustomer", sheetName);
            String customerType = ReadExcel.getDataFromDataSheet(filePath, "CustomerType", sheetName);
            String calculationFrequency = ReadExcel.getDataFromDataSheet(filePath, "CalculationFrequency", sheetName);
            String calculationDate = ReadExcel.getDataFromDataSheet(filePath, "CalculationDate", sheetName);
            String productID = ReadExcel.getDataFromDataSheet(filePath, "ProductID", sheetName);
            String manualOverrideValue = ReadExcel.getDataFromDataSheet(filePath, "ManualOverrideValue", sheetName);
            String splitType = ReadExcel.getDataFromDataSheet(filePath, "SplitType", sheetName);
            String shopSiteId = ReadExcel.getDataFromDataSheet(filePath, "ShopSiteId", sheetName);
//remove below statement
            String feeCode = ActionUtils.randomStringGenerate(3);

            String[] extractDDMMYYYY = shippingDDMMYYYY.split("/");
            String shippingDate = extractDDMMYYYY[0];
            String shippingMonth = extractDDMMYYYY[1];
            String shippingYear = extractDDMMYYYY[2];

            if(flag.equals("Y")) {

//                FeeCodeMaintenanceTest.idsSetUp();
//                CommonFunction.navigateToDateVerificationWarning();
//                CommonFunction.navigateToSyscoCanadaITEnvironment(OPCO_NUMBER);
//                CommonFunction.navigateToDateVerificationWarning();
//                ObjectLinksFunction.moveFilesToArchiveFolder_LivePriceGrid();
//                System.out.println("Clearing Old Files from Directory in IDS.");
//                FeeCodeMaintenanceTest.idsSetDown();

                FeeCodeMaintenanceTest.priceFX_Login();
                Home.navigateToLivePriceGridsPage();
                LivePriceGrids.createLockedPricing(defineLabel, resultPrice, autoApprove);
                String priceListNo = LivePriceGrids.updateHeaderDetails(customer, customerType, effectiveDate, expiryDate, calculationFrequency, calculationDate);
                System.out.println("Price List Id : " + priceListNo);
                LivePriceGrids.selectCalculateButton();
                LivePriceGrids.AddProducts(productID, manualOverrideValue);
                LivePriceGrids.selectCalculateButton();
                System.out.println("Live Price Grid is placed from PriceFx Portal.");

                Home.navigateToDataSourcePage();
                DataSources.validateLPGinDataSource(priceListNo);
                System.out.println("Live Price Grid is available under Data Sources page.");

                Thread.sleep(1200000);

//                 when to check api ? we need to wait for some time or check files in Ids alos check in Api     20231121
                String executionDate = DateUtils.getCurrentDate();
                String[] exeDate = executionDate.split("/");
                API.orderPriceAPI(productID, quantity, customer.substring(0, 3), customerNo, exeDate[2]+exeDate[0]+exeDate[1], splitType, APIConstants.ORDER_PRICE_URI, priceListNo);

//                String priceListNo="804";
                FeeCodeMaintenanceTest.idsSetUp();

                CommonFunction.navigateToDateVerificationWarning();
                CommonFunction.navigateToSyscoCanadaITEnvironment(OPCO_NUMBER);
                CommonFunction.navigateToDateVerificationWarning();

                ObjectLinksFunction.openDirectoryScreen();
                sendKeys("[pgdown]");
                ObjectLinksFunction.openObjectInDirectory("PRICELIST");
                if (ObjectLinksFunction.priceListFileStatus() == false) {
                    Thread.sleep(300000);
//                    600000 => total 20 min
                    CommonFunction.refreshPage();
                }
                CommonFunction.refreshPage();

                ObjectLinksFunction.isNavigateToWorkWithObjects();
//                extracted 139 from cutsopsite

//                Failed @ below method  257|801|7803408|19142|National|20231210|20231211|90.0000|90.0000||||170203804700
                ObjectLinksFunction.verifyPriceListDetails( customer.substring(0,3), priceListNo, productID, customerNo, customerType, effectiveDate, expiryDate, manualOverrideValue, manualOverrideValue);
                ObjectLinksFunction.verifyPriceListHeaderDetails( customer.substring(0,3), customerNo, customerType, priceListNo, effectiveDate, expiryDate, calculationFrequency, defineLabel);
                PageBase.pressF3();
                Thread.sleep(3000);

                ObjectLinksFunction.moveFilesToArchiveFolder_LivePriceGrid();
                Thread.sleep(3000);
                CommonFunction.refreshPage();
                ObjectLinksFunction.priceListFileStatus();
                PageBase.pressF3();
                CommonFunction.openSQLStatementScreen();
                SQLScreenFunction.updateDestinationTableUsingSQL(IDS_PRICELIST_DETAIL_OUTBOUND, priceListNo, "Price List No");
                SQLScreenFunction.updateDestinationTableUsingSQL(IDS_PRICELIST_HEADER_OUTBOUND, priceListNo, "Price List No");
                PageBase.pressF3();
                pressEnter();

//                CommonFunction.chooseOptionOrEnterCommand(IDS_ENTER_CUSTOMER_ORDER);
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

                System.out.println("Live Price Grid Completed.");
                FeeCodeMaintenanceTest.idsSetDown();

                shopLogin();
                Shop.addCustomerToShop(shopSiteId, customerNo);
                String orderSummaryName = Shop.placeOrder(productID,quantity);
                String orderNumber = Shop.fetchOrderDetails(orderSummaryName);
                System.out.println("Order placed from Shop : "+orderNumber);

//                 Add invoicing via IDS
            } else {
                System.out.println("Kindly Check the parameters in Test data Sheet.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @AfterClass
    public void close() {
        Login.quiteDriver();
    }
}