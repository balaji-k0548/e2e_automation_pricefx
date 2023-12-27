package com.syscolab.qe.core.reporting;


import com.syscolab.qe.core.api.restassured.RestUtil;
import org.apache.log4j.Logger;
import org.testng.*;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;


/**
 * This is the class to qMetry Results
 * @author  maheshganegoda on 8/9/17.
 */
public class QMetryResultsAdapter extends TestListenerAdapter{

    Logger LOGGER = Logger.getLogger(this.getClass());
    private int passedCount =0;
    private int failedCount =0;
    private int skippedCount =0;
    private int updatePassedCount =0;
    private int updateFailedCount =0;
    private int updateSkippedCount =0;
    private int updateTotalCount =0;
    private static String PATH =System.getProperty("user.dir") + "/qmetry.xml";
    private boolean update = false;

    private static final String TEST_RESULTS_REMOVE_AS_DUPLICATE_FAIL_METHOD = "Test Results remove as duplicate fail method :";

    private static final String FEATURES_DISALLOW_DOC_TYPE_URL = "http://apache.org/xml/features/disallow-doctype-decl";
    private static final String FEATURES_EXTERNAL_GENERAL_ENTITIES_URL = "http://xml.org/sax/features/external-general-entities";
    private static final String FEATURES_EXTERNAL_PARAMETER_ENTITIES_URL ="http://xml.org/sax/features/external-parameter-entities";

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
    private static final String DATE_PATTERN_YYYY_MM_DD = "yyyy-MM-dd,HH:mm:ss";

    private Iterator<ITestResult> passTestCases;
    private Iterator<ITestResult> failedTestCases;

    Document doc;

    private Iterator<ITestResult> skippedTestCases;

    /**
     * This will delete configuration results
     * @param resultConfig results
     */
    private void deleteConfigResults(Iterator<ITestResult> resultConfig){

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

//    @Override
//    public void onTestSuccess(ITestResult iTestResult) {
////        onFinish((ITestContext) iTestResult);
//        onFinish(iTestResult.getTestContext());
//    }
//
//    @Override
//    public void onTestFailure(ITestResult iTestResult) {
////        onFinish((ITestContext) iTestResult);
//        onFinish(iTestResult.getTestContext());
//    }
//
//    @Override
//    public void onTestSkipped(ITestResult iTestResult) {
////        onFinish((ITestContext) iTestResult);
//        onFinish(iTestResult.getTestContext());
//    }

//    @Override
//    public void onTestSuccess(ITestResult iTestResult) {
//
//    }

    /**
     * This will handle results on finish event assigning them to pass test cases, failed test cases and skipped test cases
     * @param context iTextContext instance
     */
    @Override
    public void onFinish(ITestContext context) {

        Iterator<ITestResult> passConfigurations =context.getPassedConfigurations().getAllResults().iterator();
        deleteConfigResults(passConfigurations);
        Iterator<ITestResult> failConfigurations =context.getFailedConfigurations().getAllResults().iterator();
        deleteConfigResults(failConfigurations);
        Iterator<ITestResult> skipConfigurations =context.getSkippedConfigurations().getAllResults().iterator();
        deleteConfigResults(skipConfigurations);

        boolean qmetryUpdate = false;

        passTestCases =context.getPassedTests().getAllResults().iterator();
        while (passTestCases.hasNext()) {

            ITestResult passTestCase = passTestCases.next();
            ITestNGMethod method = passTestCase.getMethod();


            if (context.getPassedTests().getResults(method).size()>1) {
                LOGGER.debug("Test Results remove as duplicate methods :"+method.getMethodName());
                passTestCases.remove();
            }
            if (context.getFailedTests().getResults(method).size()>0) {
                LOGGER.debug(TEST_RESULTS_REMOVE_AS_DUPLICATE_FAIL_METHOD+method.getMethodName());
                passTestCases.remove();
            }
            if (context.getSkippedTests().getResults(method).size()>0) {
                LOGGER.debug("Test Results remove as duplicate skip method :"+method.getMethodName());
                passTestCases.remove();
            }

        }
        passedCount =context.getPassedTests().getAllResults().size();

        failedTestCases = context.getFailedTests().getAllResults().iterator();
        while (failedTestCases.hasNext()) {
            ITestResult failedTestCase = failedTestCases.next();
            ITestNGMethod method = failedTestCase.getMethod();
            LOGGER.debug(TEST_RESULTS_REMOVE_AS_DUPLICATE_FAIL_METHOD+method.getMethodName());
            if (context.getFailedTests().getResults(method).size()>1) {
                LOGGER.debug(TEST_RESULTS_REMOVE_AS_DUPLICATE_FAIL_METHOD+method.getMethodName()+ " "+context.getFailedTests().getResults(method).size());
                failedTestCases.remove();
            }
        }

        failedCount =context.getFailedTests().getAllResults().size();

        skippedTestCases = context.getSkippedTests().getAllResults().iterator();
        while (skippedTestCases.hasNext()) {
            ITestResult skippedTestCase = skippedTestCases.next();
            ITestNGMethod method = skippedTestCase.getMethod();
            if (context.getSkippedTests().getResults(method).size()>1) {
                LOGGER.debug("Test Results remove as duplicate skip method :"+method.getMethodName());
                skippedTestCases.remove();
            }
        }

        skippedCount =context.getSkippedTests().getAllResults().size();

        update = Boolean.parseBoolean(System.getProperty("qmetry.xml.update", "false"));
        createResultsXml(context);
        qmetryUpdate = Boolean.parseBoolean(System.getProperty("qmetry.update", "false"));
        if(qmetryUpdate) {
            QmetryResultsUpdater qmetryResultsUpdater = new QmetryResultsUpdater();
            qmetryResultsUpdater.createTestRun();
        }

    }


    /**
     * This will create the results xml according to the passed, failed and skipped test cases
     * @param context ITestContext instance
     * @exception Exception
     */
    private void createResultsXml (ITestContext context){
        Element suite = null;
        try {
            if(!update){
                DocumentBuilderFactory dbFactory =
                        DocumentBuilderFactory.newInstance();

                dbFactory.setFeature(FEATURES_DISALLOW_DOC_TYPE_URL,true);
                dbFactory.setFeature(FEATURES_EXTERNAL_GENERAL_ENTITIES_URL,false);
                dbFactory.setFeature(FEATURES_EXTERNAL_PARAMETER_ENTITIES_URL,false);

                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                doc = dBuilder.newDocument();

                // root element
                Element rootElement = doc.createElement("testng-results");
                doc.appendChild(rootElement);

                Attr skipped = doc.createAttribute(SKIPPED);
                Attr failed = doc.createAttribute(FAILED);
                Attr total = doc.createAttribute(TOTAL);
                Attr passed = doc.createAttribute(PASSED);
                skipped.setValue(Integer.toString(skippedCount));
                failed.setValue(Integer.toString(failedCount));
                passed.setValue(Integer.toString(passedCount));
                int tot = skippedCount + passedCount + failedCount;
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
            }

            passTestCases =context.getPassedTests().getAllResults().iterator();

            while (passTestCases.hasNext()) updatePassTestCasesResultsXml(suite);

            failedTestCases =context.getFailedTests().getAllResults().iterator();

            while (failedTestCases.hasNext()) updateFailedTestCasesResultsXml(suite);

            skippedTestCases =context.getSkippedTests().getAllResults().iterator();

            while (skippedTestCases.hasNext()) updateSkippedTestCasesResultsXml(suite);

            if(update){

                NodeList nodeListCount=getTestNGResultsNodeListForUpdate();
                updateResultsCount(nodeListCount);
                pushChanges();
            }
            else {
                StreamResult result = new StreamResult(new File(PATH));
                DOMSource source = new DOMSource(doc);
                // write the content into xml file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
                transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
                transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");

                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");

                transformer.transform(source, result);


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This will update the results xml with details of skipped test cases
     * @param suite Test Suite element
     */
    private void updateSkippedTestCasesResultsXml(Element suite) {
        ITestResult skipTestCase = skippedTestCases.next();

        String className = skipTestCase.getTestClass().getName();
        long startTime =skipTestCase.getStartMillis();
        long endTime =skipTestCase.getEndMillis();
        long duration = endTime -startTime;


        Date date = new Date(startTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN_YYYY_MM_DD);


        String startT = dateFormat.format(date);
        String[] sp=startT.split(",");
        startT=sp[0]+"T"+sp[1];
        startT=startT+"Z";

        date = new Date(startTime);
        dateFormat = new SimpleDateFormat(DATE_PATTERN_YYYY_MM_DD);
        String endT = dateFormat.format(date);

        String[] ep=endT.split(",");
        endT=ep[0]+"T"+ep[1];
        endT=endT+"Z";


        String durationT=Long.toString(duration);

        String methodName=skipTestCase.getName();
        String description = RestUtil.getValue(SyscoLabListener.getResults().get(SyscoLabListener.getResults().size() - 1).toString(),"id");

        String name;
        if(QmetryHelper.getQmetryTestSteps().get(methodName)!=null) {
            if(QmetryHelper.getQmetryTestDes().get(methodName)!=null){
                name = customName(methodName);
            }else{
                name=methodName;
            }

            if(update){
                NodeList nodeList=getTestNodeListForUpdate();
                updateResultsXml(nodeList,createTestNode(name,className,methodName,startT,endT,durationT,"SKIP", description));
                pushChanges();
            }else {
                suite.appendChild(createTestNode(name, className, methodName, startT, endT, durationT, "SKIP", description));
            }

        }
    }

    /**
     * This will update the results xml with details of failed test cases
     * @param suite Test Suite element
     */
    private void updateFailedTestCasesResultsXml(Element suite) {
        ITestResult failTestCase = failedTestCases.next();
        String className = failTestCase.getTestClass().getName();
        long startTime =failTestCase.getStartMillis();
        long endTime =failTestCase.getEndMillis();
        long duration = endTime -startTime;

        Date date = new Date(startTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN_YYYY_MM_DD);

        String startT = dateFormat.format(date);
        String[] sp=startT.split(",");
        startT=sp[0]+"T"+sp[1];
        startT=startT+"Z";

        date = new Date(startTime);
        dateFormat = new SimpleDateFormat(DATE_PATTERN_YYYY_MM_DD);
        String endT = dateFormat.format(date);

        String[] ep=endT.split(",");
        endT=ep[0]+"T"+ep[1];
        endT=endT+"Z";


        String durationT=Long.toString(duration);

        String methodName=failTestCase.getName();
        String description = RestUtil.getValue(SyscoLabListener.getResults().get(SyscoLabListener.getResults().size() - 1).toString(),"id");

        String name;
        if(QmetryHelper.getQmetryTestSteps().get(methodName)!=null) {
            if(QmetryHelper.getQmetryTestDes().get(methodName)!=null){
                name = customName(methodName);
            }else{
                name=methodName;
            }
            if(update){
                NodeList nodeList=getTestNodeListForUpdate();
                updateResultsXml(nodeList,createTestNode(name,className,methodName,startT,endT,durationT,"FAIL", description ));
                pushChanges();
            }else {
                suite.appendChild(createTestNode(name, className, methodName, startT, endT, durationT, "FAIL", description));
            }

        }
    }

    /**
     * This will update the results xml with details of passed test cases
     * @param suite Test Suite element
     */
    private void updatePassTestCasesResultsXml(Element suite) {
        ITestResult passTestCase = passTestCases.next();
        String className = passTestCase.getTestClass().getName();
        long startTime =passTestCase.getStartMillis();
        long endTime =passTestCase.getEndMillis();
        long duration = endTime -startTime;

        Date date = new Date(startTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN_YYYY_MM_DD);

        String startT = dateFormat.format(date);
        String[] sp=startT.split(",");
        startT=sp[0]+"T"+sp[1];
        startT=startT+"Z";

        date = new Date(startTime);
        dateFormat = new SimpleDateFormat(DATE_PATTERN_YYYY_MM_DD);
        String endT = dateFormat.format(date);

        String[] ep=endT.split(",");
        endT=ep[0]+"T"+ep[1];
        endT=endT+"Z";


        String durationT=Long.toString(duration);

        String methodName=passTestCase.getName();
        String description = RestUtil.getValue(SyscoLabListener.getResults().get(SyscoLabListener.getResults().size() - 1).toString(),"id");

        String name;
        if(QmetryHelper.getQmetryTestSteps().get(methodName)!=null){
            if(QmetryHelper.getQmetryTestDes().get(methodName)!=null){
                name = customName(methodName);
            }else{
                name=methodName;
            }

            if(update){
                NodeList nodeList=getTestNodeListForUpdate();
                updateResultsXml(nodeList,createTestNode(name,className,methodName,startT,endT,durationT,"PASS", description));
                pushChanges();
            }else {
                suite.appendChild(createTestNode(name,className,methodName,startT,endT,durationT,"PASS", description));
            }


        }
    }

    /**
     * Create and return test node
     * @param name name
     * @param className class name
     * @param methodName method name
     * @param startTime started at ms
     * @param endTime finished at ms
     * @param duration duration ms
     * @param status status
     * @param description description
     * @return test node
     */
    private Element createTestNode(String name,String className,String methodName,String startTime,String endTime,String duration,String status, String description){
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

        String defaultStep = "Qmetry Step";
        if(QmetryHelper.getQmetryTestSteps().get(methodName)==null){
            QmetryHelper.qmetryTest(methodName);
        }
        if(QmetryHelper.getQmetryTestSteps().get(methodName).isEmpty()){
            QmetryHelper.getQmetryTestSteps().get(methodName).add(defaultStep);
        }

        ArrayList<String> steps = QmetryHelper.getQmetryTestSteps().get(methodName);
        for(String st:steps){
            if(!status.equalsIgnoreCase("PASS")&&steps.get(steps.size()-1).equalsIgnoreCase(st)){
                testClass.appendChild(createTestStepNode(doc,st,startTime,endTime,duration,status, description));
            }else{
                testClass.appendChild(createTestStepNode(doc,st,startTime,endTime,duration,"PASS", description));
            }

        }
        return test;
    }

    /**
     * This will create test step node
     * @param doc document
     * @param name name
     * @param startTime started at ms
     * @param endTime finished at ms
     * @param duration duration ms
     * @param status status
     * @param description description
     * @return test step
     */
    private Element createTestStepNode(Document doc,String name,String startTime,String endTime,String duration,String status, String description){
        Element testSetup = doc.createElement(TEST_METHOD);


        Attr testName = doc.createAttribute("name");
        Attr testStatus = doc.createAttribute(STATUS);
        Attr durationAtr= doc.createAttribute(DURATION_MS);
        Attr startTimeAtr= doc.createAttribute(STARTED_AT);
        Attr endTimeAtr= doc.createAttribute(FINISHED_AT);
        Attr descriptionAtr= doc.createAttribute("description");

        testName.setValue(name);
        testSetup.setAttributeNode(testName);

        testStatus.setValue(status);
        testSetup.setAttributeNode(testStatus);

        durationAtr.setValue(duration);
        testSetup.setAttributeNode(durationAtr);

        startTimeAtr.setValue(startTime);
        testSetup.setAttributeNode(startTimeAtr);

        endTimeAtr.setValue(endTime);
        testSetup.setAttributeNode(endTimeAtr);

        descriptionAtr.setValue(description);
        testSetup.setAttributeNode(descriptionAtr);

        return testSetup;
    }

    /**
     * This will assign and return a custom name of the qmetry method name
     * @param methodName qmetry custom method name
     * @return qmetry custom method name
     */
    private String customName(String methodName){
        return QmetryHelper.getQmetryTestDes().get(methodName)+ QmetryHelper.DELIMINATOR+methodName;
    }

    /**
     * This will return the node list to update
     * @return node list
     * @exception ParserConfigurationException
     * @exception SAXException
     * @exception IOException
     */
    private NodeList getTestNodeListForUpdate (){
        NodeList testList = null;
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory
                    .newInstance();

            docFactory.setFeature(FEATURES_DISALLOW_DOC_TYPE_URL,true);
            docFactory.setFeature(FEATURES_EXTERNAL_GENERAL_ENTITIES_URL,false);
            docFactory.setFeature(FEATURES_EXTERNAL_PARAMETER_ENTITIES_URL,false);

            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.parse(PATH);

            testList = doc.getElementsByTagName("test");


        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } /*catch (TransformerException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }*/ catch (SAXException e) {
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
    private NodeList getTestNGResultsNodeListForUpdate (){
        NodeList testNG = null;
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory
                    .newInstance();

            docFactory.setFeature(FEATURES_DISALLOW_DOC_TYPE_URL,true);
            docFactory.setFeature(FEATURES_EXTERNAL_GENERAL_ENTITIES_URL,false);
            docFactory.setFeature(FEATURES_EXTERNAL_PARAMETER_ENTITIES_URL,false);

            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.parse(PATH);

            testNG = doc.getElementsByTagName("testng-results");


        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } /*catch (TransformerException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }*/ catch (SAXException e) {
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
    private void pushChanges(){
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
    private void updateResultsXml (NodeList testList,Element elementIn){

        boolean isElementAvailable= false;
        String current_status= "PASS";
        String new_status= "PASS";

        isElementAvailable = updateTestCounts(testList, elementIn, isElementAvailable);

        if(!isElementAvailable){
            Element elementTest = (Element)testList.item(0);
            elementTest.getParentNode().appendChild(elementIn);
            Element elementInClass =(Element)(elementIn.getElementsByTagName(CLASS).item(0));
            NodeList stepsNew= elementInClass.getElementsByTagName(TEST_METHOD);
            new_status=getStatusOfTest(stepsNew);

            if(new_status.toString().equalsIgnoreCase("FAIL")){
                updateFailedCount +=1;
            }
            if(new_status.toString().equalsIgnoreCase("PASS")){
                updatePassedCount +=1;
            }
            if(new_status.toString().equalsIgnoreCase("SKIP")){
                updateSkippedCount +=1;
            }

            updateTotalCount +=1;
        }

    }

    /**
     * This will update test count
     * @param testList Test List
     * @param elementIn Element
     * @param isElementAvailable flag for element available or not
     * @return flag for element available or not
     */
    private boolean updateTestCounts(NodeList testList, Element elementIn, boolean isElementAvailable) {
        String currentStatus;
        String newStatus;
        for(int i = 0; i< testList.getLength(); i++){

            Element elementTest = (Element) testList.item(i);

            if( elementTest.getAttribute("name").equals(elementIn.getAttribute("name"))){
                Element elementClass =(Element)(elementTest.getElementsByTagName(CLASS).item(0));
                Element elementInClass =(Element)(elementIn.getElementsByTagName(CLASS).item(0));

                boolean isClassCorrect=elementClass.getAttribute("name").equals(elementInClass.getAttribute("name"));
                if(isClassCorrect){
                    elementTest.getParentNode().replaceChild(elementIn,elementTest);
                    isElementAvailable =true;
                    NodeList stepsOld= elementClass.getElementsByTagName(TEST_METHOD);
                    NodeList stepsNew= elementInClass.getElementsByTagName(TEST_METHOD);
                    currentStatus= getStatusOfTest(stepsOld);
                    newStatus=getStatusOfTest(stepsNew);
                    checkNewStatusAndUpdateTestCounts(currentStatus, newStatus);
                    break;
                }

            }

        }
        return isElementAvailable;
    }

    /**
     * This will update test execution status and test count
     * @param currentStatus Current Test execution status
     * @param newStatus New Test execution status
     */
    private void checkNewStatusAndUpdateTestCounts(String currentStatus, String newStatus) {
        if(!currentStatus.equalsIgnoreCase(newStatus)){

            if(currentStatus.equalsIgnoreCase("PASS")){
                updateTestCountsIfCurrentStatusPass(newStatus);
            }

            if(currentStatus.equalsIgnoreCase("FAIL")){
                updateTestCountIfCurrentStatusFail(newStatus);
            }

            if(currentStatus.equalsIgnoreCase("SKIP")){
                if(newStatus.equalsIgnoreCase("FAIL")){
                    updateFailedCount +=1;
                }
                if(newStatus.equalsIgnoreCase("PASS")){
                    updatePassedCount +=1;
                }
                updateSkippedCount -=1;
            }
        }
    }

    /**
     * This will update test count if the test Fail from Pass or Skip
     * @param newStatus Test execution status
     */
    private void updateTestCountIfCurrentStatusFail(String newStatus) {
        if(newStatus.equalsIgnoreCase("PASS")){
            updatePassedCount +=1;
        }
        if(newStatus.equalsIgnoreCase("SKIP")){
            updateSkippedCount +=1;
        }
        updateFailedCount -=1;
    }

    /**
     * This will update test count if the test passed from Fail or Skip
     * @param newStatus Test execution status
     */
    private void updateTestCountsIfCurrentStatusPass(String newStatus) {
        if(newStatus.equalsIgnoreCase("FAIL")){
            updateFailedCount +=1;
        }
        if(newStatus.equalsIgnoreCase("SKIP")){
            updateSkippedCount +=1;
        }
        updatePassedCount -=1;
    }

    /**
     * This will get status of the test passed,failed or skipped
     * @param steps node list of steps
     * @return status
     */
    private String getStatusOfTest(NodeList steps){
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
    private void updateResultsCount (NodeList testList){


        Element elementTest = (Element)testList.item(0);
        //  Element elementTestNew = (Element)testList.item(0);
        String fail =elementTest.getAttribute(FAILED).toString();
        String pass =elementTest.getAttribute(PASSED).toString();
        String skip =elementTest.getAttribute(SKIPPED).toString();
        String total =elementTest.getAttribute(TOTAL).toString();

        Integer intFail = new Integer(fail);
        Integer intPass = new Integer(pass);
        Integer intSkip = new Integer(skip);
        Integer intTotal = new Integer(total);
        updateFailedCount = updateFailedCount +intFail;
        updatePassedCount = updatePassedCount +intPass;
        updateSkippedCount = updateSkippedCount +intSkip;
        updateTotalCount = updateTotalCount +intTotal;

        elementTest.removeAttribute(FAILED);
        elementTest.removeAttribute(PASSED);
        elementTest.removeAttribute(TOTAL);
        elementTest.removeAttribute(SKIPPED);

        elementTest.setAttribute(FAILED,String.valueOf(updateFailedCount));
        elementTest.setAttribute(PASSED,String.valueOf(updatePassedCount));
        elementTest.setAttribute(TOTAL,String.valueOf(updateTotalCount));
        elementTest.setAttribute(SKIPPED,String.valueOf(updateSkippedCount));

    }

}
