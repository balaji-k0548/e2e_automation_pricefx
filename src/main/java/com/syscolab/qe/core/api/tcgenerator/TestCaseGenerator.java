package com.syscolab.qe.core.api.tcgenerator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.syscolab.qe.core.common.LoggerUtil;
import com.syscolab.qe.core.testcasemanagement.TestLinkIntegration;

import java.io.*;
import java.util.*;

/**
 * This is the class for test case generation
 * @author  yoosufm on 6/23/17.
 */
public class TestCaseGenerator {

    /**
     * This is the main method to create the test suite
     * @param args stores the incoming arguments
     * @throws IOException throwing IOException
     */
    public static void main(String [] args) throws IOException {
        String apiDocPath = "/Users/yoosufm/wp/qe_core/src/main/resources/api_doc.json";
        LoggerUtil.logINFO(convertFileContentToString(apiDocPath));

        JsonObject apiDoc = getAPIDocAsJson(apiDocPath);
        JsonArray endPoints = getEndpoints(apiDoc);
        JsonObject endPoint = endPoints.get(0).getAsJsonObject();

        JsonArray errorCodes = endPoint.getAsJsonObject().getAsJsonArray("error_codes");
        for(JsonElement errorCode: errorCodes){
            String description = "description";
            String res = "responses";
            String error = "error";
            JsonObject endPointTemp = getEndpoints( getAPIDocAsJson(apiDocPath)).get(0).getAsJsonObject();
            String errorCodeStr = errorCode.getAsJsonObject().get("error_code").getAsString();
            String errorDescription = errorCode.getAsJsonObject().get(description).getAsString() ;

            endPointTemp.addProperty(description,  endPoints.get(0).getAsJsonObject().get(description).getAsString()
                    + " error code : " + errorCodeStr);
            endPointTemp.get(res).getAsJsonArray().get(1).getAsJsonObject().get(error).getAsJsonObject().addProperty("code", errorCodeStr);
            endPointTemp.get(res).getAsJsonArray().get(1).getAsJsonObject().get(error).getAsJsonObject().addProperty("message", errorDescription);
            endPointTemp.add("expectedResponse", endPointTemp.get(res).getAsJsonArray().get(1).getAsJsonObject().get(error).getAsJsonObject());

            TestLinkIntegration.createSuite(endPointTemp);
        }
    }

    /**
     * This will create a test case and write in to an existing file or a new file
     * @throws IOException throwing IOException
     */
    public static void createTestCase() throws IOException {
        String content = "This is the content to write into create file";
        String path="D:\\a\\hi.txt";
        File file = new File(path);

        // If file doesn't exist, then create it
        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        try{
            // Write in file
            bw.write(content);
        }finally {
            // Close connection
            bw.close();
        }
    }

    /**
     * This will create a new suite with the given path
     * @param suitePath suite path
     */
    public static void createSuites(String suitePath){
        (new File( suitePath)).mkdirs();
    }

    /**
     * This will return the endpoints
     * @param apiDoc api doc json object
     * @return array of endpoints
     */
    public static JsonArray getEndpoints(JsonObject apiDoc){
        return  apiDoc.getAsJsonArray("endpoints");
    }


    /**
     * This will return the api doc as a json object
     * @param apiDocPath api document path
     * @return api doc json object
     */
    public static JsonObject getAPIDocAsJson(String apiDocPath){
        JsonParser jsonParser = new JsonParser();

        return jsonParser.parse(convertFileContentToString(apiDocPath)).getAsJsonObject();
    }

    /**
     * This will convert the file content in to a text and return the text
     * @param apiDocPath api document path
     * @return converted text
     */
    public static String convertFileContentToString(String apiDocPath){
        Scanner scanner = null;
        try {
            scanner = new Scanner( new File(apiDocPath) );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (scanner == null) {
            return null;
        }
        String text = scanner.useDelimiter("\\A").next();
        scanner.close();
        return text;
    }

}
