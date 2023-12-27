package com.syscolab.qe.core.api.restassured;

import com.sun.mail.imap.IMAPFolder;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import com.syscolab.qe.core.api.util.Headers;
import com.syscolab.qe.core.api.util.RequestMethods;
import com.syscolab.qe.core.common.LoggerUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;


/**
 * This is the util class for Rest
 * @author yoosufm on 3/23/17.HE
 */
public class RestUtil {

    public static String API_HOST = "http://pricing-api-stg.gpp-np.us-east-1.aws.sysco.net/";
    public static String BASE_PATH = "/";
    public static int PORT = 0;
    public static final String END_LINE_LOGGER = "\n*********\n\n";
    public static final String SUCCESS = "success";

    /**
     *
     * This will send the headers, request parameters and request body to the given uri depending on the request type provided and return the response
     * @param headers request headers
     * @param bodyString request body
     * @param uri endpoint url to append to base uri and base path
     * @param requestMethod http request type/method
     * @param queryParameters query parameters
     * @return response
     */
    public static Response send(Map<String, String> headers, String bodyString, String uri, String requestMethod, Map<String, String> queryParameters) {
        RestAssured.baseURI = API_HOST;
        RestAssured.basePath = BASE_PATH;
        if (PORT == 0) {
            RestAssured.port = -1;
        }else {
            RestAssured.port = PORT;
        }

        LoggerUtil.logINFO("\n\nHEADERS\n" + headers + END_LINE_LOGGER);
        LoggerUtil.logINFO("\n\nREQUEST_URL\n" + RestAssured.baseURI+ RestAssured.basePath + ":" + RestAssured.port + "/" + uri + END_LINE_LOGGER);

        RequestSpecification requestSpecification = getRequestSpec(headers, bodyString);

        LoggerUtil.logINFO("\n\nREQUEST_BODY\n" + bodyString + END_LINE_LOGGER);
        RestAssured.useRelaxedHTTPSValidation();
        requestSpecification = RestAssured.given().spec(requestSpecification);
        String theUri = setQueryParameters(uri, queryParameters);

        Response response = execute(requestMethod, requestSpecification, theUri);

        if (response == null) {
            return null;
        }

        LoggerUtil.logINFO("\n\nRESPONSE\n" + response.getBody().asString() + END_LINE_LOGGER);
        LoggerUtil.logINFO("\n\nRESPONSE_STATUS_CODE\n" + response.getStatusCode() + END_LINE_LOGGER);

        return response;
    }

    /**
     * This will return the response of the request made without query parameters
     * @param headers request headers
     * @param bodyString request body
     * @param uri endpoint url to append to base uri and base path
     * @param requestMethod http request type/method
     * @return the response
     */
    public static Response send(Map<String, String> headers, String bodyString, String uri, String requestMethod) {
        return send(headers, bodyString, uri, requestMethod, null);
    }

    /**
     * This will execute the request specification related to the passed request method and return the relevant response
     * @param reqMethod request method/type
     * @param requestSpec request specification
     * @param uri endpoint url to append to base uri and base path
     * @return response
     */
    public static Response execute(String reqMethod, RequestSpecification requestSpec, String uri) {
        RequestSpecification requestSpecification = requestSpec;
        requestSpecification = given(requestSpecification).config(RestAssured.config().encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)));

        Response response = null;
        if ("GET".equalsIgnoreCase(reqMethod)) {
            response = requestSpecification.expect().when().get(uri, new Object[0]);
        }
        if ("POST".equalsIgnoreCase(reqMethod)) {
            response = requestSpecification.expect().when().post(uri, new Object[0]);
        }
        if ("PUT".equalsIgnoreCase(reqMethod)) {
            response = requestSpecification.expect().when().put(uri, new Object[0]);
        }
        if ("DELETE".equalsIgnoreCase(reqMethod)) {
            response = requestSpecification.expect().when().delete(uri, new Object[0]);
        }
        if ("PATCH".equalsIgnoreCase(reqMethod)) {
            response = requestSpecification.expect().when().patch(uri, new Object[0]);
        }
        return response;
    }


    /**
     * This will return the request specification
     * @param headers request headers
     * @param body request body
     * @return request specification
     */
    public static RequestSpecification getRequestSpec(Map<String, String> headers, String body) {
        RequestSpecBuilder reqSpecBuilder = new RequestSpecBuilder();
        if (headers != null)
            reqSpecBuilder.addHeaders(headers);
        if (body != null && body.length() > 0) {
            reqSpecBuilder.setBody(body);
        }
        return reqSpecBuilder.build();
    }

    /**
     * This will send the request and return the response as string
     * @param headers request headers
     * @param body request body
     * @param baseUri base uri
     * @param basePath base path
     * @param uri endpoint uri
     * @param requestMethod request method
     * @return response as a string
     */
    public static String send(Map<String, String> headers, String body, String baseUri, String basePath, String uri, String requestMethod) {
        if (PORT != 0) {
            RestAssured.port = PORT;
        }
        RestAssured.baseURI = baseUri;
        RestAssured.basePath = basePath;
        LoggerUtil.logINFO("\n\nHEADERS\n" + headers + END_LINE_LOGGER);
        LoggerUtil.logINFO("\n\nREQUEST_URL\n" + RestAssured.baseURI + RestAssured.basePath + "/" + uri + END_LINE_LOGGER);
        RequestSpecification requestSpecification = getRequestSpec(headers, body);
        requestSpecification = given().spec(requestSpecification);
        LoggerUtil.logINFO("\n\nREQUEST_BODY\n" + body + END_LINE_LOGGER);
        String response = executeAndGetResponseAsString(requestMethod, requestSpecification, uri);
        LoggerUtil.logINFO("\n\nRESPONSE_BODY\n" + response + END_LINE_LOGGER);
        return response;
    }

    /**
     * This will execute and get response as a string depending on the request method
     * @param reqMethod request method
     * @param requestSpecification request specification
     * @param uri endpoint uri
     * @return response as a string
     */
    public static String executeAndGetResponseAsString(String reqMethod, RequestSpecification requestSpecification, String uri) {
        /*
        * will return the response as a string
        * */
        requestSpecification = given(requestSpecification).config(RestAssured.config().encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)));
        Response response = null;
        if (reqMethod.equalsIgnoreCase("GET")) {
            response = requestSpecification.expect().when().get("/" + uri);
        }
        if (reqMethod.equalsIgnoreCase("POST")) {
            response = requestSpecification.expect().when().post("/" + uri);
        }
        if (reqMethod.equalsIgnoreCase("PUT")) {
            response = requestSpecification.expect().when().put("/" + uri);
        }
        if (reqMethod.equalsIgnoreCase("DELETE")) {
            response = requestSpecification.expect().when().delete("/" + uri);
        }
        if (response == null) {
            return null;
        }
        return response.asString();
    }

    /**
     * This will set query parameters and return the new uri with the new query parameters set
     * @param url url
     * @param queryParameters set of query parameters
     * @return url with the new query parameters
     */
    public static String setQueryParameters(String url, Map<String, String> queryParameters) {
        if (queryParameters == null || queryParameters.isEmpty())
            return url;
        String newUrl = url.concat("?");
        for (Map.Entry<String, String> entry : queryParameters.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            newUrl = newUrl.concat(key).concat("=").concat(value).concat("&");
        }
        return newUrl.substring(0, newUrl.length() - 1);
    }

    /**
     * This will return the status code of the response
     * @param response response
     * @return status code
     */
    public static int getResponseCode(Response response){
        return response.getStatusCode();
    }

    /**
     * This will return the status of the response as true if the status is success
     * @param response response as a string
     * @return true or false
     */
    public static boolean getResponseStatus(String response) {
        boolean isSuccess = false;
        try {
            String status = getValue(response, SUCCESS);
            if(status != null && !status.isEmpty())
                 isSuccess = Boolean.parseBoolean(status);
            else{
                String value = getValue(response,"status");
                if (value == null) {
                    return isSuccess;
                }
                isSuccess = value.equals(SUCCESS);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return isSuccess;
    }

    /**
     * This will return the status of the response as true if the status is success
     * @param response response as an object
     * @return true or false
     */
    public static boolean getResponseStatus(Response response) {
        boolean isSuccess = false;
        try {
            String status = getValue(response, SUCCESS);
            if(status != null && !status.isEmpty())
                isSuccess = Boolean.parseBoolean(status);
            else{
                String value = getValue(response,"status");
                if (value == null) {
                    return isSuccess;
                }
                isSuccess = value.equals(SUCCESS);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return isSuccess;
    }

    /**
     * This will return the value of the key passed
     * @param response response as a string
     * @param key key of the json
     * @return value
     */
    public static String getValue(String response, String key) {
        try {
            if (response.charAt(0) != '{' && response.charAt(response.length() - 1) != '}') {
                response = response.replace(response.substring(0, 1), "");
                response = response.replace(response.substring(response.length() - 1, response.length()), "");
            }

            JSONObject responseBody;
            try {
                responseBody = new JSONObject(response);
                return responseBody.getString(key);
            } catch (Exception var8) {
                try {
                    responseBody = new JSONObject(response);
                    return String.valueOf(responseBody.getBoolean(key));
                } catch (Exception var7) {
                    try {
                        responseBody = new JSONObject(response);
                        return String.valueOf(responseBody.getInt(key));
                    } catch (Exception var6) {
                        try {
                            responseBody = new JSONObject(response);
                            return String.valueOf(responseBody.getDouble(key));
                        } catch (Exception var5) {
                            try {
                                responseBody = new JSONObject(response);
                                return String.valueOf(responseBody.getJSONObject(key));
                            } catch (Exception var4) {
                                try {
                                    responseBody = new JSONObject(response);
                                    return String.valueOf(responseBody.getJSONArray(key));
                                } catch (Exception var3) {
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception var9) {
        }

        return null;
    }

    /**
     * This will return the value recursively
     * @param response response as a string
     * @param key key
     * @return value
     */
    public static String getValueRecursively(String response, String key) {
        String keys[] = key.split("->");
        for (int i=0 ; i < keys.length ; i++){
            response = getValue(response, keys[i]);
            if (response == null) {
                return null;
            }
        }
        LoggerUtil.logINFO(response);
        return response;
    }

    /**
     * This will return the value of the key passed
     * @param response response as an object
     * @param key key
     * @return value
     */
    public static String getValue(Response response, String key) {
        return getValue(response.asString(), key);
    }

    /**
     * This will return the value depending on the key and the data array index
     * @param response response as a string
     * @param attribute attribute name in the response
     * @param dataArrayIndex array index
     * @param key key
     * @return value
     */
    public static String getValueInArray(String response, String attribute, int dataArrayIndex, String key){
        String toReturn = "";
        try {
            JSONObject responseBody = new JSONObject(response);
            JSONArray dataArray = (JSONArray)responseBody.get(attribute);
            if(dataArrayIndex < 0)
                dataArrayIndex = dataArray.length() - Math.abs(dataArrayIndex);
            JSONObject dataObject = (JSONObject)dataArray.get(dataArrayIndex);
            toReturn = dataObject.getString(key);
        }catch (Exception e){
            e.getMessage();
        }
        return toReturn;

    }

    /**
     * This will return the value depending on the key and the data array index
     * @param response response as an object
     * @param attribute attribute name in the response
     * @param dataArrayIndex array index
     * @param key key
     * @return value
     */
    public static String getValueInArray(Response response, String attribute, int dataArrayIndex, String key){
        String toReturn = "";
        try {
            JSONObject responseBody = new JSONObject(response);
            JSONArray dataArray = (JSONArray)responseBody.get(attribute);
            if(dataArrayIndex < 0)
                dataArrayIndex = dataArray.length() - Math.abs(dataArrayIndex);
            JSONObject dataObject = (JSONObject)dataArray.get(dataArrayIndex);
            toReturn = dataObject.getString(key);
        }catch (Exception e){
            e.getMessage();
        }
        return toReturn;

    }

    /**
     * This will return the access token key related value for sales force as per the passed authentication details
     * @param uri uri
     * @param clientID client id
     * @param clientSecret client secret
     * @param username username
     * @param pw password
     * @return access token of the sales force
     */
    public static String getSalesForceAccessToken(String uri, String clientID, String clientSecret, String username, String pw) {
        String body = "grant_type=password&client_id=CLIENTID&client_secret=CLIENTSECRET&username=USER_NAME&password=PASS_WORD".replace("CLIENTID", clientID).replace("CLIENTSECRET", clientSecret).replace("USER_NAME", username).replace("PASS_WORD", pw);
        Response response = RestUtil.send(Headers.getHeaderUrlEncoded(), body, uri, RequestMethods.POST.toString());
        if (response!=null){
        return RestUtil.getValue(response, "access_token");
    }
        else{
            return null;
        }
    }

    /**
     * This will return true if the passed attribute is present in the response
     * @param response response as a string
     * @param attribute attribute to be checked
     * @return true or false
     */
    public static boolean isGivenAttributeInResponse(String response, String attribute){
        boolean isAttributeExists = false;
        try{
            org.json.JSONObject responseBody = new org.json.JSONObject(response);
            if(responseBody.has(attribute)){
                isAttributeExists = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return isAttributeExists;

    }

    /**
     * This will return true if the passed attribute is present in the response
     * @param response response as an object
     * @param attribute attribute to be checked
     * @return true or false
     */
    public static boolean isGivenAttributeInResponse(Response response, String attribute){
        boolean isAttributeExists = false;
        try{
            org.json.JSONObject responseBody = new org.json.JSONObject(response);
            if(responseBody.has(attribute)){
                isAttributeExists = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return isAttributeExists;

    }
}