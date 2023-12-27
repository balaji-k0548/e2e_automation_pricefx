package com.syscolab.qe.core.reporting;

import com.syscolab.qe.core.common.LoggerUtil;
import com.syscolab.qe.core.data.QMetryImportAutomationResultsData;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

/**
 * This will handle QMetry results for REST
 * @author Sandeep Perera
 */
public class QMetryResultsAdapterREST extends TestListenerAdapter {

    static Logger LOGGER = Logger.getLogger(QMetryResultsAdapterREST.class);
    private static int PASSED_COUNT =0;
    private static int FAILED_COUNT =0;
    private static int SKIPPED_COUNT =0;
    private static int UPDATE_PASSED_COUNT =0;
    private static int UPDATE_FAILED_COUNT =0;
    private static int UPDATE_SKIPPED_COUNT =0;
    private static int UPDATE_TOTAL_COUNT =0;
    private static String PATH =System.getProperty("user.dir") + "/qmetry.xml";
    private static boolean UPDATE = false;

    private static final String APACHE_ORG_XML_FEATURES_DISALLOW_DOCTYPE_DECL_URL = "http://apache.org/xml/features/disallow-doctype-decl";
    private static final String XML_ORG_SAX_FEATURES_EXTERNAL_GENERAL_ENTITIES_URL = "http://xml.org/sax/features/external-general-entities";
    private static final String XML_ORG_SAX_FEATURES_EXTERNAL_PARAMETER_ENTITIES_URL = "http://xml.org/sax/features/external-parameter-entities";

    private static final String SKIPPED = "skipped";
    private static final String FAILED = "failed";
    private static final String TOTAL = "total";
    private static final String PASSED = "passed";
    private static final String DURATION_MS = "duration-ms";
    private static final String STARTED_AT = "started-at";
    private static final String FINISHED_AT = "finished-at";
    private static final String CLASS = "class";
    private static final String TEST_METHOD = "test-method";
    private static final String STATUS = "status";

    private static Iterator<ITestResult> PASS_TEST_CASES;
    private static Iterator<ITestResult> FAILED_TEST_CASES;

    static Document doc;

    private static Iterator<ITestResult> SKIPPED_TEST_CASES;

    /**
     * This will delete configuration results
     * @param resultConfig results
     */
    private static void deleteConfigResults(Iterator<ITestResult> resultConfig){

        while (resultConfig.hasNext()) {

            ITestResult passTestCase = resultConfig.next();
            ITestNGMethod method = passTestCase.getMethod();


            if (method.isAfterMethodConfiguration()
                    ||method.isAfterTestConfiguration()
                    ||method.isAfterClassConfiguration()
                    ||method.isAfterSuiteConfiguration()
                    ||method.isBeforeMethodConfiguration()
                    ||method.isBeforeClassConfiguration()
                    ||method.isBeforeSuiteConfiguration()
                    ||method.isBeforeTestConfiguration()
                    ||method.isBeforeGroupsConfiguration()
                    ||method.isAfterGroupsConfiguration()){
                LOGGER.debug("Test Results remove :"+method.getMethodName());
                resultConfig.remove();
            }
        }
    }

    /**
     * This will handle results assigning them to pass test cases, failed test cases and skipped test cases
     * @param context iTextContext instance
     */
    public static void createTestResultXML(ITestContext context) {

        String testResultRemoveAsDuplicateFailMethod = "Test Results remove as duplicate fail method :";

        Iterator<ITestResult> passConfigurations =context.getPassedTests().getAllResults().iterator();
        deleteConfigResults(passConfigurations);
        Iterator<ITestResult> failConfigurations =context.getFailedTests().getAllResults().iterator();
        deleteConfigResults(failConfigurations);
        Iterator<ITestResult> skipConfigurations =context.getSkippedTests().getAllResults().iterator();
        deleteConfigResults(skipConfigurations);


        PASS_TEST_CASES =context.getPassedTests().getAllResults().iterator();
        while (PASS_TEST_CASES.hasNext()) {

            ITestResult passTestCase = PASS_TEST_CASES.next();
            ITestNGMethod method = passTestCase.getMethod();


            if (context.getPassedTests().getResults(method).size()>1) {
                LOGGER.debug("Test Results remove as duplicate methods :"+method.getMethodName());
                PASS_TEST_CASES.remove();
            }
            if (context.getFailedTests().getResults(method).size()>0) {
                LOGGER.debug(testResultRemoveAsDuplicateFailMethod+method.getMethodName());
                PASS_TEST_CASES.remove();
            }
            if (context.getSkippedTests().getResults(method).size()>0) {
                LOGGER.debug("Test Results remove as duplicate skip method :"+method.getMethodName());
                PASS_TEST_CASES.remove();
            }

        }
        PASSED_COUNT =context.getPassedTests().getAllResults().size();

        FAILED_TEST_CASES = context.getFailedTests().getAllResults().iterator();
        while (FAILED_TEST_CASES.hasNext()) {
            ITestResult failedTestCase = FAILED_TEST_CASES.next();
            ITestNGMethod method = failedTestCase.getMethod();
            LOGGER.debug(testResultRemoveAsDuplicateFailMethod+method.getMethodName());
            if (context.getFailedTests().getResults(method).size()>1) {
                LOGGER.debug(testResultRemoveAsDuplicateFailMethod+method.getMethodName()+ " "+context.getFailedTests().getResults(method).size());
                FAILED_TEST_CASES.remove();
            }
        }

        FAILED_COUNT =context.getFailedTests().getAllResults().size();

        SKIPPED_TEST_CASES = context.getSkippedTests().getAllResults().iterator();
        while (SKIPPED_TEST_CASES.hasNext()) {
            ITestResult skippedTestCase = SKIPPED_TEST_CASES.next();
            ITestNGMethod method = skippedTestCase.getMethod();
            if (context.getSkippedTests().getResults(method).size()>1) {
                LOGGER.debug("Test Results remove as duplicate skip method :"+method.getMethodName());
                SKIPPED_TEST_CASES.remove();
            }
        }

        SKIPPED_COUNT =context.getSkippedTests().getAllResults().size();

        UPDATE = Boolean.parseBoolean(System.getProperty("qmetry.xml.update", "false"));
        createResultsXml(context);

    }

    /**
     * This will create the results xml according to the passed, failed and skipped test cases
     * @param context ITestContext instance
     * @exception Exception
     */
    private static void createResultsXml (ITestContext context){
        Element suite = null;
        try {
            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();

            dbFactory.setFeature(APACHE_ORG_XML_FEATURES_DISALLOW_DOCTYPE_DECL_URL,true);
            dbFactory.setFeature(XML_ORG_SAX_FEATURES_EXTERNAL_GENERAL_ENTITIES_URL,false);
            dbFactory.setFeature(XML_ORG_SAX_FEATURES_EXTERNAL_PARAMETER_ENTITIES_URL,false);

            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.newDocument();
            // root element
            Element rootElement = doc.createElement("testng-results");
            doc.appendChild(rootElement);

            Attr skipped = doc.createAttribute(SKIPPED);
            Attr failed = doc.createAttribute(FAILED);
            Attr total = doc.createAttribute(TOTAL);
            Attr passed = doc.createAttribute(PASSED);
            skipped.setValue(Integer.toString(SKIPPED_COUNT));
            failed.setValue(Integer.toString(FAILED_COUNT));
            passed.setValue(Integer.toString(PASSED_COUNT));
            int tot = SKIPPED_COUNT + PASSED_COUNT + FAILED_COUNT;
            total.setValue(Integer.toString(tot));

            rootElement.setAttributeNode(skipped);
            rootElement.setAttributeNode(failed);
            rootElement.setAttributeNode(total);
            rootElement.setAttributeNode(passed);

            // supercars element
            suite = doc.createElement("suite");

            Attr suiteName = doc.createAttribute("name");
            Attr suiteDuration = doc.createAttribute(DURATION_MS);
            Attr suiteStarted = doc.createAttribute(STARTED_AT);
            Attr suiteFinished = doc.createAttribute(FINISHED_AT);

            suiteName.setValue("QmetrySuite");
            suiteDuration.setValue("75137");
            suiteStarted.setValue("2017-12-19T07:26:14Z");
            suiteFinished.setValue("2017-12-19T07:27:29Z");

            suite.setAttributeNode(suiteName);
            suite.setAttributeNode(suiteDuration);
            suite.setAttributeNode(suiteStarted);
            suite.setAttributeNode(suiteFinished);

            rootElement.appendChild(suite);

            PASS_TEST_CASES =context.getPassedTests().getAllResults().iterator();

            while (PASS_TEST_CASES.hasNext()) {

                ITestResult passTestCase = PASS_TEST_CASES.next();
                getPassedFailedSkippedDetails("PASS",passTestCase,suite);

            }

            FAILED_TEST_CASES =context.getFailedTests().getAllResults().iterator();

            while (FAILED_TEST_CASES.hasNext()) {
                ITestResult failTestCase = FAILED_TEST_CASES.next();
                getPassedFailedSkippedDetails("FAIL",failTestCase,suite);

            }

            SKIPPED_TEST_CASES =context.getSkippedTests().getAllResults().iterator();
            while (SKIPPED_TEST_CASES.hasNext()) {
                ITestResult skipTestCase = SKIPPED_TEST_CASES.next();
                getPassedFailedSkippedDetails("SKIP",skipTestCase,suite);
            }

            writeTheContentToAnXMLFileAndUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This will create the test class node and append the test node to the suite
     * @param status pass/fail/skip status
     * @param testCase testCase type
     * @param suite suite element
     * @throws Exception
     */
    private static void getPassedFailedSkippedDetails(String status, ITestResult testCase, Element suite) throws Exception {
        String className = testCase.getTestClass().getName();
        long startTime =testCase.getStartMillis();
        long endTime =testCase.getEndMillis();
        long duration = endTime -startTime;
        QMetryImportAutomationResultsData qMetryImportAutomationResultsData = new QMetryImportAutomationResultsData();

        Date date = new Date(startTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");

        String startT = dateFormat.format(date);
        String[] sp=startT.split(",");
        startT=sp[0]+"T"+sp[1];
        startT=startT+"Z";

        date = new Date(startTime);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
        String endT = dateFormat.format(date);

        String[] ep=endT.split(",");
        endT=ep[0]+"T"+ep[1];
        endT=endT+"Z";

        String durationT=Long.toString(duration);

        QMetryREST.getTestCaseDetailsByVersionOfQMetry(qMetryImportAutomationResultsData);
        String testName = System.getProperty("qmetry.test.name");
        String testNameFromMethodDescription = testCase.getMethod().getDescription().replace(" ","");
        String lastExecutedQmetryID = System.getProperty("qmetry.test.id").trim();
        int index = lastExecutedQmetryID.lastIndexOf("-");
        String numbersOfQmetryID = lastExecutedQmetryID.substring(index,lastExecutedQmetryID.length());
        LoggerUtil.logINFO("RESULT"+numbersOfQmetryID);
        if(testNameFromMethodDescription.contains(numbersOfQmetryID)){
            suite.appendChild(createTestNode(testName,className,startT,endT,durationT,status));
        }
    }

    /**
     * This will write the content to xml file and update
     * @throws TransformerException
     */
    private static void writeTheContentToAnXMLFileAndUpdate() throws TransformerException {
        // write the content into xml file
        DOMSource source = new DOMSource(doc);

        //UPDATE
        StreamResult result = new StreamResult(new File(PATH).getAbsolutePath());
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");

        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);
    }


    /**
     * Create and return test node
     * @param name name
     * @param className class name
     * @param startTime started at ms
     * @param endTime finished at ms
     * @param duration duration ms
     * @return test node
     */
    private static Element createTestNode(String name,String className,String startTime,String endTime,String duration,String status){
        Element test = doc.createElement("test");


        Attr testName = doc.createAttribute("name");
        Attr durationAtr= doc.createAttribute(DURATION_MS);
        Attr startTimeAtr= doc.createAttribute(STARTED_AT);
        Attr endTimeAtr= doc.createAttribute(FINISHED_AT);

        testName.setValue(name);
        test.setAttributeNode(testName);

        durationAtr.setValue(duration);
        test.setAttributeNode(durationAtr);

        startTimeAtr.setValue(startTime);
        test.setAttributeNode(startTimeAtr);

        endTimeAtr.setValue(endTime);
        test.setAttributeNode(endTimeAtr);

        Element testClass = doc.createElement(CLASS);
        Attr testClassName = doc.createAttribute("name");

        testClassName.setValue(className);

        testClass.setAttributeNode(testClassName);

        test.appendChild(testClass);

        testClass.appendChild(createTestMethodNode(System.getProperty("qmetry.test.id"),startTime,endTime,duration,status));
        return test;
    }


    /**
     * This will create test step node
     * @param name name
     * @param startTime started at ms
     * @param endTime finished at ms
     * @param duration duration ms
     * @param status status
     * @return test step
     */
    private static Element createTestMethodNode(String name,String startTime,String endTime,String duration,String status){
        Element testMethod = doc.createElement(TEST_METHOD);

        Attr testName = doc.createAttribute("name");
        Attr testStatus = doc.createAttribute(STATUS);
        Attr durationAtr= doc.createAttribute(DURATION_MS);
        Attr startTimeAtr= doc.createAttribute(STARTED_AT);
        Attr endTimeAtr= doc.createAttribute(FINISHED_AT);

        testName.setValue(name);
        testMethod.setAttributeNode(testName);

        testStatus.setValue(status);
        testMethod.setAttributeNode(testStatus);

        durationAtr.setValue(duration);
        testMethod.setAttributeNode(durationAtr);

        startTimeAtr.setValue(startTime);
        testMethod.setAttributeNode(startTimeAtr);

        endTimeAtr.setValue(endTime);
        testMethod.setAttributeNode(endTimeAtr);

        return testMethod;
    }


    /**
     * This will assign and return a custom name of the qmetry method name
     * @param methodName qmetry custom method name
     * @return qmetry custom method name
     */
    private static String customName(String methodName){
        return QmetryHelper.getQmetryTestDes().get(methodName)+ QmetryHelper.DELIMINATOR+methodName;
    }


    /**
     * This will return the node list to update
     * @return node list
     * @exception ParserConfigurationException
     * @exception SAXException
     * @exception IOException
     */
    private static NodeList getTestNodeListForUpdate (){
        NodeList testList = null;
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory
                    .newInstance();

            docFactory.setFeature(APACHE_ORG_XML_FEATURES_DISALLOW_DOCTYPE_DECL_URL,true);
            docFactory.setFeature(XML_ORG_SAX_FEATURES_EXTERNAL_GENERAL_ENTITIES_URL,false);
            docFactory.setFeature(XML_ORG_SAX_FEATURES_EXTERNAL_PARAMETER_ENTITIES_URL,false);


            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.parse(PATH);

            testList = doc.getElementsByTagName("test");


        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return testList;
    }


    /**
     * This will return the TestNG node list to update
     * @return TestNG node list
     * @exception ParserConfigurationException
     * @exception SAXException
     * @exception IOException
     */
    private static NodeList getTestNGResultsNodeListForUpdate (){
        NodeList testNG = null;
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory
                    .newInstance();

            docFactory.setFeature(APACHE_ORG_XML_FEATURES_DISALLOW_DOCTYPE_DECL_URL,true);
            docFactory.setFeature(XML_ORG_SAX_FEATURES_EXTERNAL_GENERAL_ENTITIES_URL,false);
            docFactory.setFeature(XML_ORG_SAX_FEATURES_EXTERNAL_PARAMETER_ENTITIES_URL,false);

            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.parse(PATH);

            testNG = doc.getElementsByTagName("testng-results");


        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return testNG;
    }

    /**
     * This will push changes to the file path
     * @exception TransformerException
     */
    private static void pushChanges(){
        try {
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(PATH));
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");

            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.transform(source, result);
        }catch (TransformerException e){

        }
    }

    /**
     * This will update the results xml
     * @param testList node list of tests,
     * @param elementIn xml element
     */
    private static void updateResultsXml (NodeList testList,Element elementIn){

        boolean isElementAvailable= false;
        String currentStatus;
        String newStatus;

        for(int i=0;i<testList.getLength();i++){


            Element elementTest = (Element)testList.item(i);

            if( elementTest.getAttribute("name").equals(elementIn.getAttribute("name"))){
                Element elementClass =(Element)(elementTest.getElementsByTagName(CLASS).item(0));
                Element elementInClass =(Element)(elementIn.getElementsByTagName(CLASS).item(0));

                boolean isClassCorrect=elementClass.getAttribute("name").equals(elementInClass.getAttribute("name"));
                if(isClassCorrect){
                    elementTest.getParentNode().replaceChild(elementIn,elementTest);
                    isElementAvailable=true;
                    NodeList stepsOld= elementClass.getElementsByTagName(TEST_METHOD);
                    NodeList stepsNew= elementInClass.getElementsByTagName(TEST_METHOD);
                    currentStatus= getStatusOfTest(stepsOld);
                    newStatus=getStatusOfTest(stepsNew);
                    if(!currentStatus.equalsIgnoreCase(newStatus)){
                        if(currentStatus.equalsIgnoreCase("PASS")){
                            if(newStatus.equalsIgnoreCase("FAIL")){
                                UPDATE_FAILED_COUNT+=1;
                            }
                            if(newStatus.equalsIgnoreCase("SKIP")){
                                UPDATE_SKIPPED_COUNT+=1;
                            }
                            UPDATE_PASSED_COUNT-=1;
                        }
                        if(currentStatus.equalsIgnoreCase("FAIL")){
                            if(newStatus.equalsIgnoreCase("PASS")){
                                UPDATE_PASSED_COUNT+=1;
                            }
                            if(newStatus.equalsIgnoreCase("SKIP")){
                                UPDATE_SKIPPED_COUNT+=1;
                            }
                            UPDATE_FAILED_COUNT-=1;
                        }
                        if(currentStatus.equalsIgnoreCase("SKIP")){
                            if(newStatus.equalsIgnoreCase("FAIL")){
                                UPDATE_FAILED_COUNT+=1;
                            }
                            if(newStatus.equalsIgnoreCase("PASS")){
                                UPDATE_PASSED_COUNT+=1;
                            }
                            UPDATE_SKIPPED_COUNT-=1;
                        }
                    }
                    break;
                }

            }

        }
        if(!isElementAvailable){
            Element elementTest = (Element)testList.item(0);
            elementTest.getParentNode().appendChild(elementIn);
            Element elementInClass =(Element)(elementIn.getElementsByTagName(CLASS).item(0));
            NodeList stepsNew= elementInClass.getElementsByTagName(TEST_METHOD);
            newStatus=getStatusOfTest(stepsNew);

            if(newStatus.equalsIgnoreCase("FAIL")){
                UPDATE_FAILED_COUNT+=1;
            }
            if(newStatus.equalsIgnoreCase("PASS")){
                UPDATE_PASSED_COUNT+=1;
            }
            if(newStatus.equalsIgnoreCase("SKIP")){
                UPDATE_SKIPPED_COUNT+=1;
            }

            UPDATE_TOTAL_COUNT +=1;
        }
    }


    /**
     * This will get status of the test passed,failed or skipped
     * @param steps node list of steps
     * @return status
     */
    private static String getStatusOfTest(NodeList steps){
        String status = "PASS";
        for(int j=0;j<steps.getLength();j++){
            Element stepTest = (Element)steps.item(j);
            if(!stepTest.getAttribute(STATUS).equalsIgnoreCase("PASS")){
                status = stepTest.getAttribute(STATUS);
            }
        }
        return status;
    }


    /**
     * This will update test results count according to failed,passed,skipped and total
     * @param testList test node list
     */
    private static void updateResultsCount (NodeList testList){


        Element elementTest = (Element)testList.item(0);
        String fail =elementTest.getAttribute(FAILED);
        String pass =elementTest.getAttribute(PASSED);
        String skip =elementTest.getAttribute(SKIPPED);
        String total =elementTest.getAttribute(TOTAL);

        Integer intFail = new Integer(fail);
        Integer intPass = new Integer(pass);
        Integer intSkip = new Integer(skip);
        Integer intTotal = new Integer(total);
        UPDATE_FAILED_COUNT = UPDATE_FAILED_COUNT+intFail;
        UPDATE_PASSED_COUNT = UPDATE_PASSED_COUNT+intPass;
        UPDATE_SKIPPED_COUNT = UPDATE_SKIPPED_COUNT+intSkip;
        UPDATE_TOTAL_COUNT = UPDATE_TOTAL_COUNT+intTotal;

        elementTest.removeAttribute(FAILED);
        elementTest.removeAttribute(PASSED);
        elementTest.removeAttribute(TOTAL);
        elementTest.removeAttribute(SKIPPED);

        elementTest.setAttribute(FAILED,String.valueOf(UPDATE_FAILED_COUNT));
        elementTest.setAttribute(PASSED,String.valueOf(UPDATE_PASSED_COUNT));
        elementTest.setAttribute(TOTAL,String.valueOf(UPDATE_TOTAL_COUNT));
        elementTest.setAttribute(SKIPPED,String.valueOf(UPDATE_SKIPPED_COUNT));

    }
}
