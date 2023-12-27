package com.syscolab.qe.core.data;

import com.syscolab.qe.core.api.request.RequestBase;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * This is the class for qmetry import automation results related data extending from request base
 * @author Sandeep Perera
 */
public class QMetryImportAutomationResultsData extends RequestBase {

    public JSONObject body;
    public String format = "testng";
    public String testCycleToReuse = System.getProperty("qmetry.test.cycle.id", "");
    public String description;
    public JSONObject fields;
    public JSONObject testCase;
    public ArrayList<String> labels;
    public String priority;
    public String fixVersionId;
    public String assignee;
    public String reporter;
    public String status;
    public ArrayList<String> components;
    public String sprintId;
    public JSONArray customFields;


}

