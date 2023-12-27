package com.syscolab.qe.core.data.tdm.common;

import com.syscolab.qe.core.api.restassured.RestUtil;
import com.syscolab.qe.core.common.Constants;

/**
 * @author Kasun Herath
 * This is the class to set the API Host
 */
public class TDMPaths {

    public static final String FILE_UPLOAD_ENDPOINT = "file-upload";
    public static final String FILE_UPLOAD_TRACKING_ENDPOINT = "file-upload-tracking/ID";
    public static final String TDM_VALIDATION_ENDPOINT = "validate-file-upload";
    public static final String TDM_GET_ENDPOINT = "test-data-entries";

    public static void setPaths(){
        RestUtil.API_HOST = Constants.TDM_API_HOST;
        RestUtil.PORT = 3000;
        RestUtil.BASE_PATH = "";
    }

    public static void setPaths(int port){
        RestUtil.API_HOST = Constants.TDM_API_HOST;
        RestUtil.PORT = port;
        RestUtil.BASE_PATH = "";
    }
}