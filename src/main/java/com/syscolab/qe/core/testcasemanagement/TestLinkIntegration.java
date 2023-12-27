package com.syscolab.qe.core.testcasemanagement;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.constants.*;
import br.eti.kinoshita.testlinkjavaapi.model.*;
import com.google.gson.JsonObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * This is the class for TestLink integration
 * @author yoosufm on 6/29/17.
 */
public class TestLinkIntegration {

    static URL URL;
    Integer testCaseId;
    Integer testCaseExternalId;
    Integer testPlanId;
    ExecutionStatus status;
    Integer buildId;
    String notes;
    Boolean guess;
    String bugId;
    Integer platformId;
    String platformName;
    Map<String, String> customFields;
    Boolean overwrite;
    public static TestLinkAPI testLinkAPI;
    static final String DESCRIPTION = "description";
    static String testLinkUrl = System.getProperty("testlink.url", "http://quality.leapset.com/testlink/lib/api/xmlrpc/v1/xmlrpc.php");
    static String apiKey = System.getProperty("testlink.api.key", "618837c38a9209d14c7caafba5e01cd5");
    String project = System.getProperty("testlink.project", "CAKE API Demo");
    String testPlan = System.getProperty("testlink.test.plan", "Regression ");
    String buildName = System.getProperty("testlink.build.name", "");
    String projectPrefix = System.getProperty("testlink.prefix", "CAD");
    protected Logger logger = Logger.getLogger("logger");

    /**
     * This is the constructor
     */
    public TestLinkIntegration() {
        try {
            URL = new URL(testLinkUrl);
            testLinkAPI = new TestLinkAPI(URL, apiKey);

        } catch (MalformedURLException e) {
           // LoggerUtil.log(e);
        }
    }

    /**
     * This will update the Passed test cases in TestLink
     * @param testCaseID test case ID
     * @param status execution status
     * @param note notes Additional description of test plan
     */
    public void updatePassedTestCase(String testCaseID, ExecutionStatus status, String note) {

        String[] testCaseIds = testCaseID.split(",");
        for (String id : testCaseIds) {
            testCaseID = id.trim();
            try {
                if (testCaseID.contains(":")) {
                    testCaseID = testCaseID.split(":")[0].trim();
                }
                if (!testCaseID.contains(projectPrefix)) {
                    testCaseID = projectPrefix + "-" + testCaseID;
                }

                TestCase tc = testLinkAPI.getTestCaseByExternalId(testCaseID, 1);
                testCaseId = tc.getId();
                testCaseExternalId = Integer.valueOf(tc.getFullExternalId().split("-")[1]);
                testPlanId = testLinkAPI.getTestPlanByName(testPlan, project).getId();
                this.status = status;
                Build build = testLinkAPI.getLatestBuildForTestPlan(testPlanId);
                buildId = build.getId();
                buildName = build.getName();
                notes = note;
                guess = false;
                bugId = "";
                Platform platform = testLinkAPI.getTestPlanPlatforms(testPlanId)[0];
                platformId = platform.getId();
                platformName = platform.getName();
                overwrite = true;

                testLinkAPI.reportTCResult(testCaseId, testCaseExternalId, testPlanId, status, buildId, buildName, notes, guess, bugId, platformId, platformName, customFields, overwrite);
            } catch (Exception e) {
                //LoggerUtil.log(e);
            }
        }
    }

    /**
     * This will create the suite
     * @param testCase test case json object
     */
    public static void createSuite(JsonObject testCase){
        try {
            URL = new URL(testLinkUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String suiteName = testCase.get("name").getAsString();
        boolean isDuplicateSuite = false;
        TestSuite testSuiteToBeCreated = null;
        testLinkAPI = new TestLinkAPI(URL, apiKey);
        TestSuite [] rootLevelTestSuites = testLinkAPI.getFirstLevelTestSuitesForTestProject(62104);
        TestSuite [] childTestSuites = testLinkAPI.getTestSuitesForTestSuite(rootLevelTestSuites[0].getId());
        for(TestSuite testSuite : childTestSuites){
            testSuiteToBeCreated = testSuite;
            if(testSuite.getName().equalsIgnoreCase(suiteName)){
                isDuplicateSuite = true;

                break;
            }
        }

        if (!isDuplicateSuite){
                testSuiteToBeCreated = testLinkAPI.createTestSuite(62104,suiteName, suiteName, rootLevelTestSuites[0].getId(), 1, true, ActionOnDuplicate.BLOCK);
        }

        TestCaseStep testCaseStep = new TestCaseStep();

        testCaseStep.setId(89793445);
        testCaseStep.setExecutionType(ExecutionType.AUTOMATED);
        testCaseStep.setActive(true);
        testCaseStep.setNumber(1);
        testCaseStep.setTestCaseVersionId(4);

        System.out.println(testCase.get("request").getAsJsonObject());
        String request = testCase.get("request").getAsJsonObject().toString().replace(" ", "&nbsp;").replace(",", ",<br>");
        String response = testCase.get("expectedResponse").getAsJsonObject().toString().replace(" ", "&nbsp;");



        testCaseStep.setActions("Send following request : <pre><code>" + request + "</code></pre>" );
        testCaseStep.setExpectedResults("Send following response should received : <div> <code class=\"prettyprint\">" + response + "</code></div>");

        List<TestCaseStep> testCaseStepList = new ArrayList<>();
        testCaseStepList.add(testCaseStep);
        testLinkAPI.createTestCase(testCase.get(DESCRIPTION).getAsString(),testSuiteToBeCreated.getId(),62104, "yoosuf", testCase.get(DESCRIPTION).getAsString(), testCaseStepList, "pre condition",
                TestCaseStatus.FINAL, TestImportance.HIGH, ExecutionType.AUTOMATED, 1, 213213, true, ActionOnDuplicate.CREATE_NEW_VERSION);
        System.out.println(testSuiteToBeCreated.getId());
    }

    /**
     * This will convert Json To Test Link
     * @param testCaseJson test case json object
     * @return test case
     */
    public static TestCase convertJsonToTestLink(JsonObject testCaseJson){
        TestCase testCase = new TestCase();
        testCase.setName(testCaseJson.get(DESCRIPTION).getAsString());
        return testCase;
    }

}
