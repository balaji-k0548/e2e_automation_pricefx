package com.syscolab.qe.core.testcasemanagement.acts;

import com.syscolab.qe.core.common.LoggerUtil;
import io.restassured.response.Response;
import com.syscolab.qe.core.api.restassured.RestUtil;
import com.syscolab.qe.core.api.util.RequestMethods;
import com.syscolab.qe.core.common.Constants;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * This is the Swagger related class
 * @author Sandeep Perera
 */
public class Swagger {

    static JSONObject jsonObjectSwagger, jsonObjectPaths, jsonObjectEndPoint;
    static JSONArray jsonArrayProperties;
    static final String PROPERTIES = "properties";
    static ExcelReader excelReader = new ExcelReader();

    /**
     * This is the main method
     * @param args - stores the incoming arguments
     * @throws InvalidFormatException - Throws InvalidFormatException
     */
    public static void main(String args[]) throws InvalidFormatException {
        getSwaggerAttributes();
    }

    /**
     * This will get swagger attributes
     * @throws InvalidFormatException - Throws InvalidFormatException
     */
    public static void getSwaggerAttributes() throws InvalidFormatException {
        try {
            RestUtil.API_HOST = Constants.SWAGGER_APP_URL;
            RestUtil.BASE_PATH = "";
            Response response = RestUtil.send(null, "", "api-docs.json", RequestMethods.GET.toString());
            LoggerUtil.logINFO(response.body().asString());
            jsonObjectSwagger = new JSONObject(response.getBody().asString());
            jsonObjectPaths = new JSONObject(RestUtil.getValue(jsonObjectSwagger.toString(), "definitions"));

            JSONArray jsonArrayEndPoints = jsonObjectPaths.names();

            LoggerUtil.logINFO("\n \n \n \n \n ");

            for (int i = 0 ; i < jsonArrayEndPoints.length() ; i++) {
                jsonObjectEndPoint = new JSONObject(RestUtil.getValue(jsonObjectPaths.toString(), jsonArrayEndPoints.get(i).toString()));
                try {
                    jsonArrayProperties = jsonObjectEndPoint.getJSONObject(PROPERTIES).names();
                }catch (Exception e){
                    jsonObjectEndPoint = jsonObjectEndPoint.getJSONObject("items");
                    jsonArrayProperties = jsonObjectEndPoint.getJSONObject(PROPERTIES).names();
                }
                ACTSXML xmlContentObject = new ACTSXML();

                String xmlSystemName = "";
                String xmlParameter = "";

                xmlSystemName = jsonArrayEndPoints.get(i).toString();
                xmlContentObject.systemXML = xmlContentObject.systemXML.replace("SYSTEMNAME", xmlSystemName);

                for (int j = 0 ; j < jsonArrayProperties.length() ; j++){
                    String xmlValues = "";
                    String xmlValue = "";

                    String parameterName = jsonArrayProperties.get(j).toString();
                    JSONObject jsonObject = new JSONObject(RestUtil.getValue(RestUtil.getValue(jsonObjectEndPoint.toString(), PROPERTIES), jsonArrayProperties.get(j).toString()));
                    String parameterType = RestUtil.getValue(jsonObject.toString(), "type");
                    jsonArrayProperties = jsonObjectEndPoint.getJSONObject(PROPERTIES).names();

                    xmlParameter = xmlParameter + xmlContentObject.parameterXML.replace("PARAMETER_ID", String.valueOf(j)).replace("PARAMETER_NAME", parameterName);
                    xmlValue = xmlValue + xmlContentObject.valueXML.replace("VALUENAME", "INVALID");
                    xmlValue = xmlValue + xmlContentObject.valueXML.replace("VALUENAME", "VALID");
                    xmlValues = xmlContentObject.valuesXML.replace("VALUE", xmlValue);
                    xmlParameter = xmlParameter.replace("VALUES", xmlValues);
                }

                xmlContentObject.parameterXML = xmlParameter;
                String content = xmlContentObject.getXMLFileContent();
                xmlContentObject.writeToXML(xmlSystemName, content);
                xmlContentObject.generateACTSCombination(xmlSystemName, jsonArrayProperties.length());
                try {
                    ExcelReader.retrieveTestCaseData(xmlSystemName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }catch (JSONException e){
            LoggerUtil.logINFO("Exception occurred reading JSON " + e);
        }

    }
}
