package com.syscolab.qe.core.tdm;

import com.syscolab.qe.core.data.tdm.TDMReservationValidationData;
import com.syscolab.qe.core.data.tdm.TDMReservationValidationFileData;
import org.testng.annotations.Test;

public class TDMTest {

    String token = "";
    String fileName = "/Users/sandeepp/Downloads/Customers4 (1).xlsx";
    String applicationName = "Test_SUTAP";
    String environmentName = "Test_SUTAP";
    String releaseName = "Test_SUTAP";
    String[] checkingColumn = {"Name"};
    TDMReservationValidationData tdmReservationValidationData = new TDMReservationValidationData(fileName, applicationName, environmentName, releaseName, checkingColumn);


    @Test
    public void testTDM() throws InterruptedException {
        /*following tests are considered passed if the entire method is executed. Because TDM return methods don't have a proper
        return value. However, methods are implemented to return a value in case if TDM returns a valuable data in future
        */
        TDMUtil.reserveDataSet(token, tdmReservationValidationData);
        TDMUtil.validateDataSet(token, tdmReservationValidationData);
    }
}
