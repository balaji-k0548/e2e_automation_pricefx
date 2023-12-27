package com.syscolab.qe.core.api.requests;

import com.syscolab.qe.core.api.restassured.RestUtil;
import com.syscolab.qe.core.api.util.RequestMethods;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yoosufm on 6/13/17.
 */

public class API {

    public static String requestBody_LPG = "{\n" +
            "    \"siteId\": \"siteNo\",\n" +
            "    \"sellerId\": \"CABL\",\n" +
            "    \"accountId\": \"customerNo\",\n" +
            "    \"priceRequestDate\": \"timestamp\",\n" +
            "    \"products\": [\n" +
            "        {\n" +
            "            \"supc\": \"productNo\",\n" +
            "            \"splitFlag\": \"splitType\",\n" +
            "            \"quantity\": qty\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    public static void orderPriceAPI(String strProductNo, String strQuantity, String strSiteId, String strAccountId, String strYYYYMMDD, String strSplitType, String strBaseURI, String strPriceListNo) throws FileNotFoundException {

        Map<String, String> headers = new HashMap<>();
        headers.put("client-id", "IDS-INVOICING-NONPROD");
        headers.put("content-type", "application/json");
        headers.put("syy-correlation-id", "a545188c-f95d-42ee-9356-cd25b29ad652");
        headers.put("accept","application/json");
        headers.put("seller-id", "CABL");

        requestBody_LPG = requestBody_LPG.replaceAll("siteNo",strSiteId);
        requestBody_LPG = requestBody_LPG.replaceAll("customerNo",strAccountId);
        requestBody_LPG = requestBody_LPG.replaceAll("timestamp",strYYYYMMDD);
        requestBody_LPG = requestBody_LPG.replaceAll("productNo", strProductNo);
        requestBody_LPG = requestBody_LPG.replaceAll("splitType", strSplitType);
        requestBody_LPG = requestBody_LPG.replaceAll("qty", strQuantity);

        System.out.println(requestBody_LPG);

        Response response = RestUtil.send(headers, requestBody_LPG,strBaseURI, String.valueOf(RequestMethods.POST));

        System.out.println("The response code for Order Price API is "+ response.getStatusCode());
        String responseBody = response.getBody().asString();
        JsonPath jsonPathPO = new JsonPath(responseBody);
        String costUsed = jsonPathPO.getString("products.costUsed");
        String priceListUsed = jsonPathPO.getString("products.priceListUsed");
        System.out.println("The result from Order Price API for Cost Used is : " + costUsed);
        System.out.println("The result from Order Price API for Price List Used is : " + priceListUsed);
        System.out.println("Response Body is =>  " + responseBody);
//Add validation for cost used also
        if(priceListUsed.contains(strPriceListNo)){
            System.out.println("Price List used is Same.");
        } else {
            System.out.println("Price List used is Different.");
            throw new RuntimeException("Price list is not Synced kindly check files are generated or not.");
        }
    }
}

