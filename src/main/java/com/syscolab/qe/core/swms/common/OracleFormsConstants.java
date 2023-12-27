package com.syscolab.qe.core.swms.common;

/**
 * This class will contain oracle forms related constants
 * @author Mohammed Rifad
 */
public class OracleFormsConstants {
    private OracleFormsConstants(){}
    public static final String APPLICATION_UNDER_TEST = System.getProperty("application.under.test", "frmservlet_rs238wl.jnlp");
    public static final int SWMS_LOADING_TIME = Integer.parseInt(System.getProperty("loading.time", "10000"));
    public static final boolean SWMS_JAVA_EXCEPTION = Boolean.parseBoolean(System.getProperty("security.exception", "true"));
    public static String JOB_ID_RETRIEVAL_URL = "http://lx239wl:9002/reports/rwservlet/showjobs";
    public static final int SWMS_MENU_LOADING_TIME_IN_SECONDS = 8;

}