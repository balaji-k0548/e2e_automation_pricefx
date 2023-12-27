package com.syscolab.qe.core.ui.common;

/**
 * Created by balaji
 */
public class UIConstants {
    public static final String GM_WAIT_LIST_URL = System.getProperty("gm.wait.list.url", "http://ec2-54-81-155-221.compute-1.amazonaws.com/waitlist/login");
    public static final String GOOGLE_URL = System.getProperty("google.url", "https://www.google.com/");
    public static final String GM_WAIT_LIST_USERNAME = System.getProperty("gm.wait.list.username", "mohammed.yoosuf@trycake.com");
    public static final String GM_WAIT_LIST_PASSWORD = System.getProperty("gm.wait.list.password", "111111");

    public static final String PRICE_FX_APP_URL = System.getProperty("app.url", "https://syscoca-qa.pricefx.com/app/#/login");
    public static final String SHOP_APP_URL = System.getProperty("app.url", "https://web.qa.cx-shop-nonprod.sysco-go.com/app");

}