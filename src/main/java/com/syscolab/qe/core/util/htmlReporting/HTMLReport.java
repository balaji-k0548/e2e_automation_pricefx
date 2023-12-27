package com.syscolab.qe.core.util.htmlReporting;

import io.restassured.response.Response;
import com.syscolab.qe.core.api.restassured.RestUtil;
import com.syscolab.qe.core.util.email.EmailUtil;
import com.syscolab.qe.core.util.qcenter.QCenterUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;
import javax.mail.MessagingException;
import java.io.*;

/**
 * This is the class for HTML Reporting
 * @author Sandeep Perera
 *
 * @see HTMLUtil
 */

public class HTMLReport {

    private static final String FAILED = "failed";
    private static final String MODULE = "MODULE";
    private static final String MODULE_SIMPLE = "module";
    private static final String FEATURE = "FEATURE";
    private static final String TESTCASE = "TESTCASE";
    private static final String STR_STATUS = "STATUS";
    private static final String BGCOLOUR = "BGCOLOUR";
    private static final String BACKGROUNDCOLORTR = "BACKGROUNDCOLORTR";
    private static final String TEST_CASE = "TestCase_";
    private static final String BGCOLORTR = "BGCOLORTR";

    public static String featureName;
    public static String testCaseName;
    public static String status;
    public static String moduleName;
    public static String errorMessage;

    static String projectName;
    static String environmentName;
    static String buildName;
    static String totalTestCases;
    static String passedCount;
    static String failedCount;
    static String uncategorizedFail;
    static String defectFail;
    static String environmentFail;
    static String dataFail;
    static String scriptFail;
    static String embedding;
    static String buildID;

    /**
     * This will generate an HTML report of the Executed Automation results and send an email.
     * Please ensure that the following System Properties have been configured correctly
     * Project: System.getProperty("test.project")
     * Env: System.getProperty("test.env")
     * Release: System.getProperty("test.release")
     * Email Recipients (Comma-separated list of recipient email IDs): System.getProperty("email.recipient")
     */
    public static void generateReport() throws JSONException {
        sendBuildHTMLReport(System.getProperty("test.project"),System.getProperty("test.env"),System.getProperty("test.release"),System.getProperty("email.recipient"));
    }

    public static void main(String args[]) throws JSONException {
        sendBuildHTMLReport("E2E_PRICING_TRANSFORMATION","QA","E2E_PRICING_TRANSFORMATION-DNB-08-04-2021", "sandeep.perera@sysco.com");
    }

    /**
     * This will generate an HTML report of the Executed Automation results and send and email
     * @param project Project Name
     * @param environment Env Name
     * @param build Build Name
     * @param recipientList Comma-separated list of recipient email IDs
     * @throws JSONException throwing JSONException
     */
    public static void sendBuildHTMLReport(String project, String environment, String build, String recipientList) throws JSONException {

        projectName = project;
        environmentName = environment;
        buildName = build;

        if(projectName == null || environmentName == null || buildName == null || recipientList==null){
            throw new RuntimeException("Null values encountered in project configuration"+"\n>> projectName: "+projectName+"\n>> environmentName: "+environmentName+"\n>> buildName: "+buildName+"\n>> recipientList: "+recipientList+"\nExiting..");
        }
        generateHTMLReport(projectName, environmentName, buildName);
        String filename = projectName + "_" + environmentName + "_" + buildName + ".html";
        // The HTML body of the email.
        String bodyHTML = "<html lang=\"en\"> <head> " +
                "<style> /* Style the body */ body { font-family: Arial; margin: 0; } " +
                "/* Header/Logo Title */ .header { padding: 60px; text-align: center; background: #008CD2; color: white; font-size: 30px; } " +
                "</style> </head> " +
                "<body> <div class=\"header\"> " +
                "<h1>"+ projectName +"</h1> " +
                "<h2>"+ environmentName+"</h2> " +
                "<h2>"+ buildName+"</h2> " +
                "<p>Please refer the attached report</p> </div> </body> </html>";

        try {
            EmailUtil.sendViaSESWithAttachment("sandeep.perera@sysco.com",recipientList,"Test Results of : "  + projectName + " - " + environmentName.toUpperCase() + " - " +  buildName,bodyHTML ,filename);
        } catch (MessagingException e) {

            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This will generate an HTML report of the Executed Automation results and send and email
     * @param sender sender's email
     * @param project Project Name
     * @param environment Env Name
     * @param build Build Name
     * @param recipientList Comma-separated list of recipient email IDs
     * @throws JSONException throwing JSONException
     */
    public static void sendBuildHTMLReport(String project, String environment, String build, String sender, String recipientList) throws JSONException {

        projectName = project;
        environmentName = environment;
        buildName = build;

        if(projectName == null || environmentName == null || buildName == null || recipientList==null){
            throw new RuntimeException("Null values encountered in project configuration"+"\n>> projectName: "+projectName+"\n>> environmentName: "+environmentName+"\n>> buildName: "+buildName+"\n>> recipientList: "+recipientList+"\nExiting..");
        }
        generateHTMLReport(projectName, environmentName, buildName);
        String filename = projectName + "_" + environmentName + "_" + buildName + ".html";
        // The HTML body of the email.
        String bodyHTML = "<html lang=\"en\"> <head> " +
                "<style> /* Style the body */ body { font-family: Arial; margin: 0; } " +
                "/* Header/Logo Title */ .header { padding: 60px; text-align: center; background: #008CD2; color: white; font-size: 30px; } " +
                "</style> </head> " +
                "<body> <div class=\"header\"> " +
                "<h1>"+ projectName +"</h1> " +
                "<h2>"+ environmentName+"</h2> " +
                "<h2>"+ buildName+"</h2> " +
                "<p>Please refer the attached report</p> </div> </body> </html>";

        try {
            EmailUtil.sendViaSESWithAttachment(sender,recipientList,"Test Results of : "  + projectName + " - " + environmentName.toUpperCase() + " - " +  buildName,bodyHTML ,filename);
        } catch (MessagingException e) {

            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void generateHTMLReport(String project, String environment, String build) throws JSONException {

        projectName = project;
        environmentName = environment;
        buildName = build;

        if(projectName == null || environmentName == null || buildName == null){
            throw new RuntimeException("Null values encountered in project configuration"+"\n>> projectName: "+projectName+"\n>> environmentName: "+environmentName+"\n>> buildName: "+buildName+"\n>> Exiting..");
        }

        Response projectList = QCenterUtil.getQCenterProjects(project, environment, build);
        JSONArray jsonArrayProjects = new JSONArray(projectList.asString());

        Response buildsList = QCenterUtil.getQCenterBuilds();
        JSONArray jsonArrayBuilds = new JSONArray(buildsList.asString());

        for(int i=0; i<jsonArrayBuilds.length(); i++){
            JSONObject buildObject = jsonArrayBuilds.getJSONObject(i);
            if(buildObject.getString("cake_release").equalsIgnoreCase(buildName)
                    && buildObject.getString("project_name").equalsIgnoreCase(projectName)
                    && buildObject.getString("environment_name").equalsIgnoreCase(environmentName)) {
                buildID = buildObject.get("id").toString();
                break;
            }
        }

        if(buildID == null){
            throw new RuntimeException("Build \""+buildName+"\" Not Found in QCenter under project \""+projectName+"\" and Env \""+environmentName+"\"\nExiting..");
        }

        Response buildDetails = QCenterUtil.getBuildDetails(buildID);
        JSONArray buildDetailsArray = new JSONArray(buildDetails.getBody().asString());

        for(int i=0; i<buildDetailsArray.length(); i++){
            JSONObject buildDetailsObject = buildDetailsArray.getJSONObject(i);
            uncategorizedFail = buildDetailsObject.get("uncategorizeFail").toString();
            scriptFail = buildDetailsObject.get("scriptFail").toString();
            environmentFail = buildDetailsObject.get("envFail").toString();
            defectFail = buildDetailsObject.get("defectFail").toString();
            dataFail = buildDetailsObject.get("dataFail").toString();
        }

        int failedInLoopCount = 0;
        int passedInBegin = (findCount(jsonArrayProjects, FAILED) + 1);

        for(int i=0; i<jsonArrayProjects.length(); i++){
            String trBlockForTestCaseAttachment = HTMLUtil.trBlockForTestCaseAttachment;
            JSONObject jsonObjectProject = jsonArrayProjects.getJSONObject(i);
            moduleName = RestUtil.getValue(jsonObjectProject.toString(), MODULE_SIMPLE);
            featureName = RestUtil.getValue(jsonObjectProject.toString(), "feature");
            testCaseName = RestUtil.getValue(jsonObjectProject.toString(), "test_case");
            status = RestUtil.getValue(jsonObjectProject.toString(), "status").toUpperCase();
            errorMessage = RestUtil.getValue(jsonObjectProject.toString(), "error_message");


            if(status.equalsIgnoreCase(FAILED)){
                if (errorMessage.equalsIgnoreCase("skipped")) {
                    HTMLUtil.failedTRBlock = HTMLUtil.failedTRBlock + HTMLUtil.trBlockForTestCase.replace(MODULE, moduleName).replace(FEATURE, featureName).replace(TESTCASE, testCaseName).replace(STR_STATUS, status + " - (SKIPPED)").replace(BGCOLOUR, "#e6c000");
                }
                else{
                    HTMLUtil.failedTRBlock = HTMLUtil.failedTRBlock + HTMLUtil.trBlockForTestCase.replace(MODULE, moduleName).replace(FEATURE, featureName).replace(TESTCASE, testCaseName).replace(STR_STATUS, status).replace(BGCOLOUR, "#E78154");
                }

                if((i+1) % 2 == 0){
                    HTMLUtil.failedTRBlock = HTMLUtil.failedTRBlock.replace(BACKGROUNDCOLORTR, "");
                }
                else{
                    HTMLUtil.failedTRBlock = HTMLUtil.failedTRBlock.replace(BACKGROUNDCOLORTR, "#ddd");
                }

            }
            else{
                HTMLUtil.passedTRBlock = HTMLUtil.passedTRBlock + HTMLUtil.trBlockForTestCase.replace(MODULE, moduleName).replace(FEATURE, featureName).replace(TESTCASE, testCaseName).replace(STR_STATUS, status).replace(BGCOLOUR, "54E763");
                if((i+1) % 2 == 0){
                    HTMLUtil.passedTRBlock = HTMLUtil.passedTRBlock.replace(BACKGROUNDCOLORTR, "");
                }
                else{
                    HTMLUtil.passedTRBlock = HTMLUtil.passedTRBlock.replace(BACKGROUNDCOLORTR, "#ddd");
                }
            }

            if(status.equalsIgnoreCase(FAILED)){
                embedding = RestUtil.getValue(jsonObjectProject.toString(), "embeddings");
                failedInLoopCount += 1;

                if(errorMessage.equalsIgnoreCase("skipped")){
                    HTMLUtil.failedTestCaseTRBBlockAttachment = HTMLUtil.failedTestCaseTRBBlockAttachment + trBlockForTestCaseAttachment.replace(MODULE, moduleName).replace(FEATURE, featureName).replace(TESTCASE, testCaseName  + HTMLUtil.tdDataToggleAddIconAttachment.replace("DATATARGETNAME", TEST_CASE + i)).replace(STR_STATUS, status + " - (SKIPPED)").replace(BGCOLOUR, "#e6c000");
                }
                else{
                    HTMLUtil.failedTestCaseTRBBlockAttachment = HTMLUtil.failedTestCaseTRBBlockAttachment + trBlockForTestCaseAttachment.replace(MODULE, moduleName).replace(FEATURE, featureName).replace(TESTCASE, testCaseName  + HTMLUtil.tdDataToggleAddIconAttachment.replace("DATATARGETNAME", TEST_CASE + i)).replace(STR_STATUS, status).replace(BGCOLOUR, "E78154");
                }

                if(embedding.isEmpty()){
                    HTMLUtil.failedTestCaseTRBBlockAttachment = HTMLUtil.failedTestCaseTRBBlockAttachment + HTMLUtil.trDataToggleAddIconRowAttachment.replace("TARGETID", TEST_CASE + i).replace("ERRORCODE", errorMessage).replace("FAILURESNAPSHOT", "");
                }
                else{
                    HTMLUtil.failedTestCaseTRBBlockAttachment = HTMLUtil.failedTestCaseTRBBlockAttachment + HTMLUtil.trDataToggleAddIconRowAttachment.replace("TARGETID", TEST_CASE + i).replace("ERRORCODE", errorMessage).replace("FAILURESNAPSHOT", HTMLUtil.snapshotBlock.replace("SNAPSHOTID", embedding));
                }

                if(failedInLoopCount % 2 == 0){
                    HTMLUtil.failedTestCaseTRBBlockAttachment = HTMLUtil.failedTestCaseTRBBlockAttachment.replace(BGCOLORTR, "bgcolor=\"f2f2f2\"");
                }
                else{
                    HTMLUtil.failedTestCaseTRBBlockAttachment = HTMLUtil.failedTestCaseTRBBlockAttachment.replace(BGCOLORTR, "");
                }
            }
            else{
                passedInBegin += 1;
                HTMLUtil.passedTestCaseTRBBlockAttachment = HTMLUtil.passedTestCaseTRBBlockAttachment + trBlockForTestCaseAttachment.replace(MODULE, moduleName).replace(FEATURE, featureName).replace(TESTCASE, testCaseName).replace(STR_STATUS, status).replace(BGCOLOUR, "54E763");

                if(passedInBegin % 2 == 0){
                    HTMLUtil.passedTestCaseTRBBlockAttachment = HTMLUtil.passedTestCaseTRBBlockAttachment.replace(BGCOLORTR, "bgcolor=\"f2f2f2\"");
                }
                else{
                    HTMLUtil.passedTestCaseTRBBlockAttachment = HTMLUtil.passedTestCaseTRBBlockAttachment.replace(BGCOLORTR, "");
                }
            }
        }

        totalTestCases = String.valueOf(jsonArrayProjects.length());
        passedCount = String.valueOf(findCount(jsonArrayProjects, "passed"));
        failedCount = String.valueOf(findCount(jsonArrayProjects, FAILED));

        HTMLUtil.finalProjectsTRBlock = HTMLUtil.finalProjectsTRBlock + HTMLUtil.trBlockForProject.replace("PROJECTNAME", projectName).replace("ENVIRONMENTNAME", environmentName).replace("BUILDNAME", buildName);
        HTMLUtil.finalCountTRBlock = HTMLUtil.finalCountTRBlock + HTMLUtil.trBlockForCount.replace("TOTALTESTCASES", totalTestCases).replace("PASSEDCOUNT", passedCount).replace("FAILEDCOUNT", failedCount);

        HTMLUtil.finalTestCaseTRBlock = HTMLUtil.finalTestCaseTRBlock + HTMLUtil.failedTRBlock + HTMLUtil.passedTRBlock;
        HTMLUtil.finalTestCaseTRBlockAttachment = HTMLUtil.finalTestCaseTRBlockAttachment + HTMLUtil.failedTestCaseTRBBlockAttachment + HTMLUtil.passedTestCaseTRBBlockAttachment;

        setHTMLReport();
//        EmailUtil.send("Test Results of : "  + projectName + " - " + environmentName.toUpperCase() + " - " +  buildName, recipientList, projectName + "_" + environmentName + "_" + buildName);
        String filename = projectName + "_" + environmentName + "_" + buildName + ".html";
        // The HTML body of the email.
        String bodyHTML = "<html lang=\"en\"> <head> " +
                "<style> /* Style the body */ body { font-family: Arial; margin: 0; } " +
                "/* Header/Logo Title */ .header { padding: 60px; text-align: center; background: #008CD2; color: white; font-size: 30px; } " +
                "</style> </head> " +
                "<body> <div class=\"header\"> " +
                "<h1>"+ projectName +"</h1> " +
                "<h2>"+ environmentName+"</h2> " +
                "<h2>"+ buildName+"</h2> " +
                "<p>Please refer the attached report</p> </div> </body> </html>";
    }

    /**
     * This will return the count of the test cases in the passed json array depending on the status
     * @param jsonArray Array of test cases for a specific project, environment and build
     * @param status status of 'final', 'passd' or 'failed'
     * @return count of the test cases
     * @throws JSONException throwing JSONException
     */
    public static int findCount(JSONArray jsonArray, String status) throws JSONException {
        int cnt = 0;
        for(int i=0; i<jsonArray.length(); i++){
            JSONObject jsonObjectProject = jsonArray.getJSONObject(i);
            String testStatus = RestUtil.getValue(jsonObjectProject.toString(), "status").toUpperCase();
            if(testStatus.equalsIgnoreCase(status)){
                cnt += 1;
            }
        }
        return cnt;
    }

    /**
     * This will set the HTML report with the details passed
     */
    public static void setHTMLReport() {

        HTMLUtil.cssBarChartBlock = HTMLUtil.cssBarChartBlock.replace("DONUTPASSED", String.valueOf(Math.round(Double.valueOf(passedCount) / Double.valueOf(totalTestCases) * 100.00)));
        HTMLUtil.cssBarChartBlock = HTMLUtil.cssBarChartBlock.replace("DONUTUNCATEGORIZED", String.valueOf(Math.round(Double.valueOf(uncategorizedFail) / Double.valueOf(totalTestCases) * 100.00)));
        HTMLUtil.cssBarChartBlock = HTMLUtil.cssBarChartBlock.replace("DONUTDEFECT", String.valueOf(Math.round(Double.valueOf(defectFail) / Double.valueOf(totalTestCases) * 100.00)));
        HTMLUtil.cssBarChartBlock = HTMLUtil.cssBarChartBlock.replace("DONUTENVIRONMENT", String.valueOf(Math.round(Double.valueOf(environmentFail) / Double.valueOf(totalTestCases) * 100.00)));
        HTMLUtil.cssBarChartBlock = HTMLUtil.cssBarChartBlock.replace("DONUTDATA", String.valueOf(Math.round(Double.valueOf(dataFail) / Double.valueOf(totalTestCases) * 100.00))) ;
        HTMLUtil.cssBarChartBlock = HTMLUtil.cssBarChartBlock.replace("DONUTSCRIPT", String.valueOf(Math.round(Double.valueOf(scriptFail) / Double.valueOf(totalTestCases) * 100.00)));

        HTMLUtil.tableBlockForProject = HTMLUtil.tableBlockForProject.replace("PROJECTSTRBLOCK",  HTMLUtil.finalProjectsTRBlock);
        HTMLUtil.tableBlockForCount = HTMLUtil.tableBlockForCount.replace("COUNTTRBLOCK",  HTMLUtil.finalCountTRBlock);

        HTMLUtil.tableBlockForTestCase = HTMLUtil.tableBlockForTestCase.replace("TESTCASETRBLOCK",  HTMLUtil.finalTestCaseTRBlock).replace("PASSEDCOUNT", passedCount).replace("PASSEDPERC", String.valueOf(Math.round(Double.valueOf(passedCount) / Double.valueOf(totalTestCases) * 100.00)));
        HTMLUtil.tableBlockForTestCase = HTMLUtil.tableBlockForTestCase.replace("UNCATEGORIZEDCOUNT", uncategorizedFail).replace("UNCATEGORIZEDPERC", String.valueOf(Math.round(Double.valueOf(uncategorizedFail) / Double.valueOf(totalTestCases) * 100.00)));
        HTMLUtil.tableBlockForTestCase = HTMLUtil.tableBlockForTestCase.replace("DEFECTCOUNT", defectFail).replace("DEFECTPERC", String.valueOf(Math.round(Double.valueOf(defectFail) / Double.valueOf(totalTestCases) * 100.0)));
        HTMLUtil.tableBlockForTestCase = HTMLUtil.tableBlockForTestCase.replace("ENVIRONMENTCOUNT", environmentFail).replace("ENVIRONMENTPERC", String.valueOf(Math.round(Double.valueOf(environmentFail) / Double.valueOf(totalTestCases) * 100.00)));
        HTMLUtil.tableBlockForTestCase = HTMLUtil.tableBlockForTestCase.replace("DATACOUNT", dataFail).replace("DATAPERC", String.valueOf(Math.round(Double.valueOf(dataFail) / Double.valueOf(totalTestCases) * 100.00)));
        HTMLUtil.tableBlockForTestCase = HTMLUtil.tableBlockForTestCase.replace("SCRIPTCOUNT", scriptFail).replace("SCRIPTPERC", String.valueOf(Math.round(Double.valueOf(scriptFail) / Double.valueOf(totalTestCases) * 100.00)));


        //Attachment TR Block
        HTMLUtil.tableBlockForTestCaseAttachment = HTMLUtil.controllerAttachment.replace("PROJECTNAMECHART", projectName.toUpperCase() + " - " + environmentName.toUpperCase() + " - " + buildName.toUpperCase()).replace("TOTALTESTSCHART", totalTestCases).replace("TOTALTESTSFAILED", failedCount) + " " + HTMLUtil.tableBlockForTestCaseAttachment.replace("TESTCASETRBLOCK",  HTMLUtil.finalTestCaseTRBlockAttachment);

        HTMLUtil.donutScriptBlock = HTMLUtil.donutScriptBlock.replace("DONUTPASSED", "Passed").replace("PassedCOUNT", passedCount);
        HTMLUtil.donutScriptBlock = HTMLUtil.donutScriptBlock.replace("DONUTUNCATEGORIZED", "Uncategorized").replace("UncategorizedCOUNT", uncategorizedFail);
        HTMLUtil.donutScriptBlock = HTMLUtil.donutScriptBlock.replace("DONUTDEFECT", "Defect").replace("DefectCOUNT", defectFail);
        HTMLUtil.donutScriptBlock = HTMLUtil.donutScriptBlock.replace("DONUTENVIRONMENT", "Environment").replace("EnvironmentCOUNT", environmentFail);
        HTMLUtil.donutScriptBlock = HTMLUtil.donutScriptBlock.replace("DONUTDATA", "Data").replace("DataCOUNT", dataFail);
        HTMLUtil.donutScriptBlock = HTMLUtil.donutScriptBlock.replace("DONUTSCRIPT", "Script").replace("ScriptCOUNT", scriptFail);

        String a = HTMLUtil.content.replace("PROJECTSUMMARYTABLE", HTMLUtil.tableBlockForProject).replace("TESTCASECOUNTSUMMARYTABLE", HTMLUtil.tableBlockForCount).replace("TESTCASEDETAILSUMMARYTABLE", HTMLUtil.tableBlockForTestCase).replace("BARCHARTCSSBLOCK", HTMLUtil.cssBarChartBlock);
        String b = HTMLUtil.contentAttachment.replace("PROJECTSUMMARYTABLE", HTMLUtil.tableBlockForProject).replace("TESTCASECOUNTSUMMARYTABLE", HTMLUtil.tableBlockForCount).replace("TESTCASEDETAILSUMMARYTABLE", HTMLUtil.tableBlockForTestCaseAttachment).replace("JAVASCRIPTCODE", HTMLUtil.blockForJavaScript.replace("DONUTSCRIPTBLOCK", HTMLUtil.donutScriptBlock));

        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter("Report.html"));
            try{
                writer.write(a);
            }finally {
                writer.close();
            }

            writer = new BufferedWriter(new FileWriter(projectName + "_" + environmentName + "_" + buildName + ".html"));
            try{
                writer.write(b);
            }finally {
                writer.close();
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is the main method
     * @param args stores the incoming arguments
     */
//    public static void main(String args[]) throws JSONException {
//        generateReport();
//    }

    /**
     * This is the test method to test the generateReport method
     * @throws JSONException throwing JSONException
     */
    @Test
    public static void test() throws JSONException {
        generateReport();
    }
}
