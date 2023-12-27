package com.syscolab.qe.core.common;

/**
 * This is the class for Constants
 * @author Sandeep Perera, Deepika Gnanathilake
 */
public class Constants {
 private Constants(){}
    public static final String JIRA_USERNAME = System.getProperty("jira.username");
    public static final String JIRA_TOKEN = System.getProperty("jira.token");
    public static final boolean RUN_WINDOWS = Boolean.parseBoolean(System.getProperty("run.windows", "false"));

    public static final String SWAGGER_APP_URL = System.getProperty("app.url", "https://qcenter.cake.net:3000");

    public static final boolean UPDATE_DASHBOARD = Boolean.parseBoolean(System.getProperty("update.dashboard"));
    public static final boolean UPDATE_QMETRY = Boolean.parseBoolean(System.getProperty("update.qmetry"));

    public static final boolean QLYTICS_QMETRY_RELEASE = Boolean.parseBoolean(System.getProperty("qmetry.release"));
    public static String QLYTICS_DAILY_WEEKLY_BUILD = System.getProperty("daily.weekly.build","none");

    public static final String QMETRY_API_KEY_QE_TEST_SL = System.getProperty("api.key", "API KEY");
    public static final boolean IS_TEST_CYCLE_PROVIDED = Boolean.parseBoolean(System.getProperty("is.test.cycle.provided"));
    public static final String QMETRY_TEST_CYCLE_ID = System.getProperty("test.cycle.id", "");

    public static final int LOGGER_DEFAULT_LEVEL = 3;

   public static final String TDM_API_HOST = System.getProperty("tdm.host","http://internal-tdm-prd-internal-alb-990994257.us-east-1.elb.amazonaws.com");
}