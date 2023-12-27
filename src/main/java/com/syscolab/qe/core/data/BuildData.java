package com.syscolab.qe.core.data;

import com.syscolab.qe.core.api.request.RequestBase;

/**
 * This is the class for Build Data extending from the request base
 * @author Sandeep Perera
 */
public class BuildData extends RequestBase {
    public int project_id;
    public String environment_id;
    public String cake_release;
}
