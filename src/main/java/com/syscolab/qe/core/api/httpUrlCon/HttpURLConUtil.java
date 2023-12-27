package com.syscolab.qe.core.api.httpUrlCon;

import com.syscolab.qe.core.common.LoggerUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;


/**
 * This is the util class for the methods related to HTTP URL connection
 * @author Mohammed Rifad
 */
public class HttpURLConUtil {
    /**
     * This method will create a HTTPURLConnection and will write the response to a file
     * @param requestType request type
     * @param url url
     * @param headers request headers
     * @param inputFile input file
     * @param responseFileLocation response file location
     * @return response
     */

    public String send(String requestType,String url,Map<String, String> headers,String inputFile,String responseFileLocation){
        URL obj = null;
        HttpURLConnection con = null;
        try {
            obj = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (obj == null) {
            return null;
        }
        try {
             con = (HttpURLConnection) obj.openConnection();
             if(requestType.equals("POST")) {
                 con.setRequestMethod("POST");
             }
             else {
                 con.setRequestMethod("GET");
             }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (con == null){
            return null;
        }
        for (Map.Entry<String, String> headerEntry : headers.entrySet()) {
            con.addRequestProperty(headerEntry.getKey(), headerEntry.getValue());
        }

        byte[] content = new byte[0];
        try {
            content = Files.readAllBytes(Paths.get(inputFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        con.setDoOutput(true);
        DataOutputStream wr = null;
        try {
            wr = new DataOutputStream(con.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (wr == null) {
            return null;
        }
        try {
            wr.write(content);
            wr.flush();
            wr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            PrintWriter pw = new PrintWriter(responseFileLocation);
            pw.write(response.toString());
            pw.close();
            pw.flush();
            LoggerUtil.logINFO("Response  ========= \n " + response.toString());

            return  response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This will send request body and return the response written to a file
     * @param requestType request type
     * @param url url
     * @param headers request headers
     * @param requestBody request body
     * @param responseFilePathLocation file path of the response file
     * @return response
     */
    public String sendRequestBody(String requestType,String url,Map<String, String> headers,String requestBody,String responseFilePathLocation){
        URL obj = null;
        HttpURLConnection con = null;
        try {
            obj = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (obj == null){
            return null;
        }
        try {
            con = (HttpURLConnection) obj.openConnection();
            if(requestType.equals("POST")) {
                con.setRequestMethod("POST");
            }
            else {
                con.setRequestMethod("GET");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (con == null) {
            return null;
        }
        for (Map.Entry<String, String> headerEntry : headers.entrySet()) {
            con.addRequestProperty(headerEntry.getKey(), headerEntry.getValue());
        }

        con.setDoOutput(true);
        DataOutputStream wr = null;
        try {
            wr = new DataOutputStream(con.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (wr == null) {
            return null;
        }
        try {
            wr.write(requestBody.getBytes());
            wr.flush();
            wr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            PrintWriter pw = new PrintWriter(responseFilePathLocation);
            pw.write(response.toString());
            pw.close();
            pw.flush();
            LoggerUtil.logINFO("Response  ========= \n " + response.toString());

            return  response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
