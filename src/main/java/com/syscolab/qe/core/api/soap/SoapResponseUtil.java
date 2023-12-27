package com.syscolab.qe.core.api.soap;

import org.apache.http.HttpResponse;

/**
 * This is the util class for Soap Response
 * @author Mohammed Rifad
 */
public class SoapResponseUtil {

    public int getStatus(HttpResponse response) {
        return response.getStatusLine().getStatusCode();
    }

}
