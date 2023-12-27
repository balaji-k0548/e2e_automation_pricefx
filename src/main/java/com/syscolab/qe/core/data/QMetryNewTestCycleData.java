package com.syscolab.qe.core.data;

import com.syscolab.qe.core.api.request.RequestBase;

/**
 * This is the class for qmetry test cycle data extending from request base
 * @author Deepika Gnanathilake
 */
public class QMetryNewTestCycleData  extends RequestBase  {

    public String summary = "Automated Test Cycle Created from SUTAP";
    public String description = "Automated Test Cycle Created from SUTAP";
    public String projectId = "";

}