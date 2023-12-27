package com.syscolab.qe.core.tdm;

import com.jayway.jsonpath.JsonPath;
import com.syscolab.qe.core.api.jackson.JacksonUtil;
import com.syscolab.qe.core.api.restassured.RestUtil;
import com.syscolab.qe.core.api.util.Headers;
import com.syscolab.qe.core.api.util.RequestMethods;
import com.syscolab.qe.core.common.LoggerUtil;
import com.syscolab.qe.core.data.tdm.TDMReservationValidationData;
import com.syscolab.qe.core.data.tdm.common.TDMPaths;
import io.restassured.response.Response;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

public class TDMUtil {

    public static String trackingId = "";

    public static String reserveDataSet(String token, TDMReservationValidationData tdmReservationValidationData) throws InterruptedException {
        LinkedHashMap<String, Object> responseList = new LinkedHashMap<>();
        TDMPaths.setPaths();
        Response response = RestUtil.send(Headers.getAuthTokenHeader(token), JacksonUtil.getAsString(tdmReservationValidationData), TDMPaths.FILE_UPLOAD_ENDPOINT, RequestMethods.POST.name());
        responseList.put("trackingID", JsonPath.read(response.asString(), "$.trackingId"));
        trackingId = responseList.get("trackingID").toString();
        waitUntilReservationIsCompleted(token);
        return response.asString();
    }

    public static String waitUntilReservationIsCompleted(String token) throws InterruptedException {
        long timeDifference = 0;
        String responseMessage = "";
        Response responseOfTracking = null;
        long timestampNow = new Timestamp(System.currentTimeMillis()).getTime();
        long timestamp2MinutesLater = timestampNow + 120000;
        do {
            responseOfTracking = RestUtil.send(Headers.getAuthTokenHeader(token), null, TDMPaths.FILE_UPLOAD_TRACKING_ENDPOINT.replace("ID", trackingId), RequestMethods.GET.name());
            responseMessage = JsonPath.read(responseOfTracking.asString(), "$.message");
            timeDifference = new Timestamp(System.currentTimeMillis()).getTime() - timestampNow;
            if (responseMessage.equals("Your file is still uploading!"))
                TimeUnit.SECONDS.sleep(10);
        }while (responseMessage.equals("Your file is still uploading!") && new Timestamp(System.currentTimeMillis()).getTime() <= timestamp2MinutesLater);
        LoggerUtil.logINFO("Time taken for Response in seconds " + timeDifference/1000);
        return responseOfTracking.asString();
    }

    public static String validateDataSet(String token, TDMReservationValidationData tdmReservationValidationData){
        TDMPaths.setPaths(3001);
        Response response = RestUtil.send(Headers.getAuthTokenHeader(token), JacksonUtil.getAsString(tdmReservationValidationData), TDMPaths.TDM_VALIDATION_ENDPOINT, RequestMethods.POST.name());
        LoggerUtil.logINFO("Validation Status \n" + response.asString());
        return response.asString();
    }

    //* keeping the main method for debugging purposes in future*/
    public static void main(String[] args) throws InterruptedException {
        String token = "";
        String fileName = "/Users/sandeepp/Downloads/Omni Release V1.0_AS242B_Customers.xlsx";
        String applicationName = "SUS OP";
        String environmentName = "AS242K";
        String releaseName = "Jay_Rel_2";
        String[] checkingColumn = {"Customer Type","Customer ID"};
        TDMReservationValidationData tdmReservationValidationData = new TDMReservationValidationData(fileName, applicationName, environmentName, releaseName, checkingColumn);
        reserveDataSet(token, tdmReservationValidationData);
        validateDataSet(token, tdmReservationValidationData);
    }
}