package com.syscolab.qe.core.api.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;

import java.util.HashMap;

/**
 * This is the util class for Headers
 * @author  yoosufm on 6/13/17.
 */
public class Headers {

    static final String BASIC ="Basic ";
    static final String OAUTH ="OAuth ";
    private Headers(){}

    /**
     * This will put the headers of application/json content type to a hashmap and return the headers
     * @return headers
     */
    public static HashMap<String, String> getHeader(){
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
        headers.put(HttpHeaders.ACCEPT, ContentTypes.APPLICATION_JSON);
        return headers;
    }

    /**
     * This will put the headers of application/json content type along with the passed apiKey to a hashmap and return the headers
     * @param apiKey apiKey of the project
     * @return headers
     */
    public static HashMap<String, String> getHeaderWithApiKey(String apiKey){
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
        headers.put("apiKey", apiKey);
        return headers;
    }

    /**
     * This will put basic authorization header to a hash map and return headers
     * @param token authorization token
     * @return headers
     */
    public static HashMap<String, String> getBasicHeader(String token){
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.AUTHORIZATION, BASIC + token);
        return headers;
    }

    /**
     * This will put authorization and application/json content type to a map and return the headers
     * @param token authorization token
     * @return headers
     */
    public static HashMap<String, String> getAuthorizationHeader(String token){
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.AUTHORIZATION, token);
        headers.put(HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
        return headers;
    }

    /**
     * This will put authorization and application/json content type to a map and return the headers
     * @param authToken authorization token
     * @return headers
     */
    public static HashMap<String, String> getAuthTokenHeader(String authToken){
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("authToken", authToken);
        headers.put(HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
        headers.put(HttpHeaders.ACCEPT, ContentTypes.APPLICATION_JSON);
        headers.put("include-count","true");
        return headers;
    }

    /**
     * This will put basic authorization , referer, cookie and content type (application/json) to a map and return headers
     * @param token authorization token
     * @param referer optional header field to identify address of the webpage
     * @param cookie cookie
     * @return headers
     */
    public static HashMap<String, String> getWithTokenRefererCookie(String token, String referer, String cookie){
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.AUTHORIZATION, BASIC + token);
        headers.put(HttpHeaders.REFERER, referer);
        headers.put("COOKIE", cookie);
        headers.put(HttpHeaders.ACCEPT, ContentTypes.APPLICATION_JSON);
        return headers;
    }

    /**
     * This will put OAuth authorization , referer, cookie and content type (Application/json) to a map and return headers
     * @param token authorization token
     * @param referer optional header field to identify address of the web page
     * @param cookie cookie
     * @return headers
     */
    public static HashMap<String, String> getWithTokenRefererCookie1(String token, String referer, String cookie){
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.AUTHORIZATION, OAUTH + token);
        headers.put(HttpHeaders.REFERER, referer);
        headers.put("COOKIE",cookie);
        headers.put(HttpHeaders.ACCEPT, ContentTypes.APPLICATION_JSON);
        return headers;
    }

    /**
     * This will return the basic authentication related header after encoding client id and client secret to Base64
     * @param clientID username
     * @param clientSecret token
     * @return headers
     */
    public static HashMap<String, String> getBasicHeader(String clientID, String clientSecret){
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.AUTHORIZATION, BASIC + getBase64(clientID + ":" + clientSecret));
        return headers;
    }

    /**
     * This will put basic authorization with Base64 encoding  and content type (application form url) to a map and return headers
     * @param clientId username
     * @param clientSecret token
     * @return headers
     */
    public static HashMap<String, String> getHeader(String clientId, String clientSecret){
        HashMap<String, String> headers = new HashMap<String, String>();
        String base64 = getBase64(clientId+":"+clientSecret);
        headers.put(HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_FORM_URL);
        headers.put(HttpHeaders.AUTHORIZATION, BASIC + base64);
        headers.put("OTHER HEADERS", ContentTypes.OTHER_HEADERS);
        return headers;
    }

    /**
     * This will put basic authorization with Base64 , referer and content type (application/json) to a map and return headers for qMetry
     * @param clientId username
     * @param clientSecret token
     * @param referer optional header field to identify address of the webpage
     * @return headers
     */
    public static HashMap<String, String> getHeaderQMetry(String clientId, String clientSecret, String referer) {
        HashMap<String, String> headers = new HashMap<>();
        String base64 = getBase64(clientId+":"+clientSecret);
        headers.put(HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
        headers.put(HttpHeaders.AUTHORIZATION, BASIC + base64);
        headers.put(HttpHeaders.REFERER, referer);
        return headers;
    }

    /**
     * This will put OAuth authorization , referer and jsession ID, content type (application/json) to a map and return headers for qMetry
     * @param token token parameter
     * @param jsessionID cookie for jsession
     * @param referer optional header field to identify address of the webpage
     * @return headers
     */
    public static HashMap<String, String> getHeaderQMetry1(String token, String jsessionID, String referer) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
        headers.put(HttpHeaders.AUTHORIZATION, OAUTH + token);
        headers.put("cookie", jsessionID);
        headers.put(HttpHeaders.REFERER, referer);
        return headers;
    }

    /**
     * This will put basic authorization with Base64 , referer,jsession and content type (application/json) to a map and return headers for qMetry
     * @param clientID username
     * @param clientSecret token
     * @param jsessionID cookie for jsession
     * @param referer optional header field to identify address of the webpage
     * @return headers
     */
    public static HashMap<String, String> getHeaderQMetry(String clientID, String clientSecret, String jsessionID, String referer) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
        headers.put(HttpHeaders.AUTHORIZATION, BASIC + getBase64(clientID + ":" + clientSecret));
        headers.put("cookie", jsessionID);
        headers.put(HttpHeaders.REFERER, referer);
        return headers;
    }

    /**
     * This will put the headers of application form url content type to a hashmap and return the headers
     * @return headers
     */
    public static HashMap<String, String> getHeaderUrlEncoded() {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_FORM_URL);
        return headers;
    }

    /**
     * This will return the basic headers when the encoding is passed
     * @param encoding encoding format
     * @return headers
     */
    public static HashMap<String, String> getBasicHeaders(String encoding){
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
        headers.put(HttpHeaders.AUTHORIZATION, ContentTypes.BASIC+encoding);
        return headers;
    }

    /**
     * This will return the bearer headers when the access token is passed
     * @param accessToken access token
     * @return headers
     */
    public static HashMap<String, String> getBearerHeaders(String accessToken){
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
        headers.put(HttpHeaders.AUTHORIZATION, ContentTypes.BEARER+accessToken);
        return headers;
    }

    /**
     * This will return the header with XML content type
     * @return headers
     */
    public static HashMap<String, String> getXmlHeaders(){
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_XML);
        return headers;
    }

    /**
     * This will encode the string into Base64 format
     * @param originalString original string
     * @return encoded string
     */
    public static String getBase64(String originalString) {
        return Base64.encodeBase64String((originalString).getBytes());
    }
}
