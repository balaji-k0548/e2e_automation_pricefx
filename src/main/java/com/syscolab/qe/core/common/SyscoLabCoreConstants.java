package com.syscolab.qe.core.common;

import org.json.JSONException;
import org.json.JSONObject;

import static com.syscolab.qe.core.util.email.EmailUtil.getCommonProperty;

/**
 * This is the class for core constants
 * @author  yoosufm on 6/13/17.
 */
public class SyscoLabCoreConstants {
    private SyscoLabCoreConstants(){}

    public static final String SYSCO_QCENTER_API_HOST = System.getProperty("qcenter.url", "https://qcenter.tqe-nonprod.us-east-1.aws.sysco.net");//https://qcenter.tqe-nonprod.us-east-1.aws.sysco.net:3000
    public static final int SYSCO_QCENTER_API_PORT = Integer.valueOf(System.getProperty("qcenter.port", "3000"));
    public static final String SYSCO_QCENTER_TEST_API_HOST = System.getProperty("qcenter.url", "https://qcentertest.sysco.net:3000");//https://syscoqcenter.sysco.com:3000
    public static final String SYSCO_QCENTER_TOKEN = System.getProperty("qcenter.token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NDQsImlhdCI6MTUyNzc0MzA4OX0.rghdxIkqo9CBpqyDiPSSmSLT5GSkiM39TZ4KcnKU4aA");
    public static final String SYSCO_QCENTER_TEST_TOKEN = System.getProperty("qcenter.token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6Mzg5LCJpYXQiOjE1OTc4MTgwMzR9.vGatLCzXSH5U0AaWXXm74nOE5DD3fZ9X_dUk6qtrxHI");
    public static final String SYSCO_QCENTER_API_BASE_PATH = "";
    public static final String SYSCO_QCENTER_EMAIL_USERNAME = "QCenter@corp.sysco.com";
    public static final String SYSCO_QCENTER_EMAIL_PW = getCommonProperty("qe.core.email.pw");
    public static boolean RUN_LOCALLY = Boolean.parseBoolean(System.getProperty("run.locally","false"));
    public static final String QMETRY_PROJECT_CODE = System.getProperty("qmetry.project.key");
    public static final String QMETRY_CUSTOM_FIELD_LIST = System.getProperty("qmetry.custom.fields.list");


}
