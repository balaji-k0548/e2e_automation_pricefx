package com.syscolab.qe.core.data;

import com.syscolab.qe.core.api.request.RequestBase;

/**
 * This is the class for Email util data extending from request base
 * @author Sandeep Perera
 */
public class EmailUtilData extends RequestBase {

    public EmailUtilAuthData auth = new EmailUtilAuthData();
    public String from;
    public String to;
    public String bcc = "sandeep.perera@sysco.com";
    public String subject;
    public String html;
}
