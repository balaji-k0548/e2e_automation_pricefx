package com.syscolab.qe.core.api.tests.admin;

import com.syscolab.qe.core.api.common.APIConstants;
import com.syscolab.qe.core.api.restassured.RestUtil;
import com.syscolab.qe.core.reporting.SyscoLabListener;
import com.syscolab.qe.core.reporting.SyscoLabQCenter;
import com.syscolab.qe.core.reporting.SyscoLabReporting;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 * Created by yoosufm on 6/13/17.
 */
public class APITestBase {
    SyscoLabListener testListeners;
    SyscoLabQCenter syscoLabQCenter;

    @BeforeClass
    /**
     * This initializes the variables
     */
    public void init() {
        RestUtil.API_HOST = APIConstants.API_HOST;
        RestUtil.PORT = APIConstants.PORT;
        RestUtil.BASE_PATH = APIConstants.BASE_PATH;
        testListeners = new SyscoLabListener();
        syscoLabQCenter = new SyscoLabQCenter();
    }

    @AfterClass
    /**
     * This does the Clean-up activity
     * @param iTestContext Test Context
     * @exception Exception
     */
    public void cleanUpBaseClass(ITestContext iTestContext) {
        try {

            syscoLabQCenter.setProjectName("Sysco qCenter");
            syscoLabQCenter.setEnvironment("qe");
            syscoLabQCenter.setRelease("sqc_v_2");
            syscoLabQCenter.setModule(iTestContext.getAttribute("feature").toString());
            syscoLabQCenter.setFeature(iTestContext.getAttribute("feature").toString());
            syscoLabQCenter.setClassName(iTestContext.getClass().getName());

            SyscoLabReporting.generateJsonFile(testListeners.getResults(), syscoLabQCenter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
