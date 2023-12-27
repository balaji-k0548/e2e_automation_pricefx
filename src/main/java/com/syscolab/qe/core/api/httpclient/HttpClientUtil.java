package com.syscolab.qe.core.api.httpclient;

import com.syscolab.qe.core.common.LoggerUtil;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This is the util class for Http Client.
 * @author Sandeep Perera
 */
public class HttpClientUtil {
    private HttpClientUtil() {}
    public static String url = "";


    /**
     * This will send the headers, parameters to the given endpoint depending on the request type provided and return the response
     * @param headers request headers
     * @param parameters set of parameters
     * @param endpoint end point
     * @param httpRequestType http request type
     * @return the response
     */
    public static String send(Map<String, String> headers, Map<String, String> parameters, String endpoint, String httpRequestType) {

        HttpClient client = HttpClientBuilder.create().build();
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        HttpResponse httpResponse = null;
        StringBuilder result = null;

        LoggerUtil.logINFO("Request URL ========= \n" + url + endpoint);
        LoggerUtil.logINFO("Request Headers ========= \n" + headers);
        try {
            switch (httpRequestType) {
                case "POST":
                    HttpPost httpPost = new HttpPost(url + endpoint);
                    for (Map.Entry<String, String> headerEntry : headers.entrySet()) {
                        httpPost.addHeader(headerEntry.getKey(), headerEntry.getValue());
                    }

                    for (Map.Entry<String, String> parametersEntry : parameters.entrySet()) {
                        urlParameters.add(new BasicNameValuePair(parametersEntry.getKey(), parametersEntry.getValue()));
                    }
                    httpPost.setEntity(new UrlEncodedFormEntity(urlParameters));
                    httpResponse = client.execute(httpPost);

                    break;

                default:
                    HttpGet httpGet = new HttpGet(url + endpoint);
                    for (Map.Entry<String, String> headerEntry : headers.entrySet()) {
                        httpGet.addHeader(headerEntry.getKey(), headerEntry.getValue());
                    }
                    httpResponse = client.execute(httpGet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (httpResponse == null) {
            return null;
        }
        LoggerUtil.logINFO("Response Code ========= \n" + httpResponse.getStatusLine().getStatusCode());

        try {

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(httpResponse.getEntity().getContent()));

           result = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            LoggerUtil.logINFO("Response  ========= \n " + result.toString());


        } catch (IOException e) {
            e.printStackTrace();
        }
        if (result == null) {
            return null;
        }
        return result.toString();

    }

    /**
     * This will return the http response status code
     * @param httpResponse http response
     * @return response status code
     */
    public static String getResponseStatusCode(HttpResponse httpResponse){
        return String.valueOf(httpResponse.getStatusLine().getStatusCode());
    }

    /**
     * This will return the response value for the passed http response
     * @param httpResponse http response
     * @return response value
     */
    public static String getResponseValue(HttpResponse httpResponse) {
        StringBuilder result = null;
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            result = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            LoggerUtil.logINFO("Response  ========= \n " + result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (result == null) {
            return null;
        }
        return result.toString();
    }
}

