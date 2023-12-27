package com.syscolab.qe.core.performance;

import com.blazemeter.jmeter.threads.concurrency.ConcurrencyThreadGroup;
import com.blazemeter.jmeter.threads.concurrency.ConcurrencyThreadGroupGui;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.TransactionController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.control.gui.TransactionControllerGui;
import org.apache.jmeter.engine.DistributedRunner;
import org.apache.jmeter.engine.JMeterEngine;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.java.control.gui.JavaTestSamplerGui;
import org.apache.jmeter.protocol.java.sampler.JavaSampler;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;
import org.apache.jmeter.timers.ConstantThroughputTimer;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jmeter.visualizers.backend.BackendListener;
import org.apache.jmeter.visualizers.backend.BackendListenerGui;
import org.apache.jorphan.collections.HashTree;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * This is the helper class for JMeter related integration
 * @author Mohammed Rifad
 */
public class JmeterHelper {

    private JmeterHelper(){}

    /**
     * This will start the Jmeter engine in the predefined path
     * @return jmeter engine instance
     */
    public static StandardJMeterEngine startJmeter(){
        File jmeterHome = new File(System.getProperty("user.dir")+"/src/main/java/com/syscolab/qe/core/performance/");
        File jmeterProperties = new File(jmeterHome+"/jmeter.properties");
        StandardJMeterEngine jmeter = new StandardJMeterEngine();

        //JMeter initialization (properties, log levels, locale, etc)
        JMeterUtils.setJMeterHome(System.getProperty("user.dir")+"/src/main/java/com/syscolab/qe/core/performance");
        JMeterUtils.loadJMeterProperties(jmeterProperties.getPath());
        JMeterUtils.initLogging();// you can comment this line out to see extra log messages of i.e. DEBUG level
        JMeterUtils.initLocale();
        return jmeter;
    }

    /**
     * This will start the Jmeter engine in the passed path
     * @param jmeterHomePath path of the Jmeter home
     * @return jmeter engine instance
     */
    public static StandardJMeterEngine startJmeter(String jmeterHomePath){
        File jmeterHome = new File(jmeterHomePath);
        File jmeterProperties = new File(jmeterHome+"/jmeter.properties");
        StandardJMeterEngine jmeter = new StandardJMeterEngine();

        //JMeter initialization (properties, log levels, locale, etc)
        JMeterUtils.setJMeterHome(jmeterHomePath);
        JMeterUtils.loadJMeterProperties(jmeterProperties.getPath());
        JMeterUtils.initLogging();// you can comment this line out to see extra log messages of i.e. DEBUG level
        JMeterUtils.initLocale();
        return jmeter;

    }

    /**
     * This will set and return the grafana backend listener
     * @param applicationName qcenter project name
     * @param testTitle title of the test
     * @return grafana backend listener
     */
    public static BackendListener setGrafanaListener(String applicationName, String testTitle) {
        BackendListener backendListener = new BackendListener();
        backendListener.setName("Backend Listner");
        backendListener.setClassname("org.apache.jmeter.visualizers.backend.influxdb.InfluxdbBackendListenerClient");
        Arguments arguments = new Arguments();
        arguments.addArgument("influxdbMetricsSender", "org.apache.jmeter.visualizers.backend.influxdb.HttpMetricsSender", "=");
        arguments.addArgument("influxdbUrl", "http://10.133.13.16:8086/write?db=jmeter", "=");
        arguments.addArgument("application", applicationName, "=");
        arguments.addArgument("measurement", "jmeter", "=");
        arguments.addArgument("summaryOnly", "false", "=");
        arguments.addArgument("samplersRegex", ".*", "=");
        arguments.addArgument("testTitle", testTitle, "=");
        arguments.addArgument("eventTags", ".*", "=");
        arguments.addArgument("influxdbUrlNew", "http://10.133.13.16:8086/write?db=jmeter", "=");
        backendListener.setArguments(arguments);
        backendListener.setProperty(TestElement.TEST_CLASS, backendListener.getClassname());
        backendListener.setProperty(TestElement.GUI_CLASS, BackendListenerGui.class.getName());
        return backendListener;

    }

    /**
     * This will set up and return the qPerf backend listener
     * @param projectBuild build name
     * @param projectBuildVersion build version
     * @return qperf backend listener
     */
    public static BackendListener setQperfListener(String projectBuild, String projectBuildVersion) {
        BackendListener qperfBackEndListener = new BackendListener();
        qperfBackEndListener.setName("Backend Listener QPerf");
        qperfBackEndListener.setClassname("com.syscolab.qperf.core.reporting.SyscoLabReporting");
        Arguments perfArguments = new Arguments();
        perfArguments.addArgument("qperf_api", "http://syscoqcenter.sysco.com:3010", "=");
        perfArguments.addArgument("project_build", projectBuild, "=");
        perfArguments.addArgument("project_build_version", projectBuildVersion, "=");
        qperfBackEndListener.setArguments(perfArguments);
        qperfBackEndListener.setProperty(TestElement.TEST_CLASS, qperfBackEndListener.getClassname());
        qperfBackEndListener.setProperty(TestElement.GUI_CLASS, BackendListenerGui.class.getName());
        return qperfBackEndListener;

    }

    /**
     * This will start the distributed JMeter Runner
     * @param host host
     * @param testPlanTree test plan hash tree
     */
    public static void startDistributeJMeterRunner(String host, HashTree testPlanTree) {

        List<JMeterEngine> engines = new LinkedList<>();
        Properties remoteProps = new Properties();
        //set properties you want to send to remote clients here

        DistributedRunner distributedRunner = new DistributedRunner(remoteProps);


        List<String> hosts = new LinkedList<>();
        //add your JMeter slaves here

        hosts.add(host);

        distributedRunner.setStdout(System.out);
        distributedRunner.setStdErr(System.err);
        distributedRunner.init(hosts, testPlanTree);
        engines.addAll(distributedRunner.getEngines());
        distributedRunner.start();
    }

    /**
     * This will create a Java Sampler related to the passed class name
     * @param className class name
     * @return java sampler
     */
    public static JavaSampler createJavaSampler(String className) {
        JavaSampler examplecomSampler = new JavaSampler();
        examplecomSampler.setName("Java Request");
        examplecomSampler.setClassname(className);
        examplecomSampler.setProperty(TestElement.TEST_CLASS, JavaSampler.class.getName());
        examplecomSampler.setProperty(TestElement.GUI_CLASS, JavaTestSamplerGui.class.getName());

        return examplecomSampler;
    }

    /**
     * This will create a Java Sampler related to the passed class name and request name
     * @param className class name
     * @param requestName name of the request
     * @return java sampler
     */
    public static JavaSampler createJavaSampler(String className, String requestName) {
        JavaSampler examplecomSampler = new JavaSampler();
        examplecomSampler.setName(requestName);
        examplecomSampler.setClassname(className);
        examplecomSampler.setProperty(TestElement.TEST_CLASS, JavaSampler.class.getName());
        examplecomSampler.setProperty(TestElement.GUI_CLASS, JavaTestSamplerGui.class.getName());

        return examplecomSampler;
    }

    /**
     * This will set up and return a constant throughput timer
     * @param throughput throughput value
     * @param calcMode  calculation mode
     * @return constant throughput timer
     */
    public static ConstantThroughputTimer ConstantThroughputTimer(int throughput, int calcMode) {
        ConstantThroughputTimer timer = new ConstantThroughputTimer();
        timer.setProperty("throughput", throughput);
        timer.setProperty("calcMode", calcMode);
        timer.setCalcMode(calcMode);
        timer.setThroughput(throughput);
        timer.setEnabled(true);
        timer.setProperty(TestElement.TEST_CLASS, ConstantThroughputTimer.class.getName());
        timer.setProperty(TestElement.GUI_CLASS, TestBeanGUI.class.getName());
        return timer;

    }

    /**
     * This will create a concurrency thread group and return the concurrency thread group
     * @param name name of the thread group
     * @param targetConcurrency target level of concurrency
     * @param rampUpTime ramp up time
     * @param rampUpStep ramp up steps
     * @param timeUnit time unit
     * @param holdTargetTime hold target time
     * @return concurrency thread group
     */
    public static ConcurrencyThreadGroup createConcurrencyThreadGroup(String name, String targetConcurrency, String rampUpTime, String rampUpStep, String timeUnit, String holdTargetTime) {

        ConcurrencyThreadGroup concurrencyThreadGroup = new ConcurrencyThreadGroup();
        concurrencyThreadGroup.setName(name);
        concurrencyThreadGroup.setTargetLevel(targetConcurrency);
        concurrencyThreadGroup.setRampUp(rampUpTime);
        concurrencyThreadGroup.setSteps(rampUpStep);
        concurrencyThreadGroup.setUnit(timeUnit);
        concurrencyThreadGroup.setHold(holdTargetTime);
        concurrencyThreadGroup.setEnabled(true);
        concurrencyThreadGroup.setProperty(TestElement.TEST_CLASS, ConcurrencyThreadGroup.class.getName());
        concurrencyThreadGroup.setProperty(TestElement.GUI_CLASS, ConcurrencyThreadGroupGui.class.getName());
        return concurrencyThreadGroup;

    }

    /**
     * This will create and return the loop controller
     * @param loops loops count
     * @return loop controller
     */
    public static LoopController createLoopController(int loops) {
        LoopController loopController = new LoopController();
        loopController.setLoops(loops);
        loopController.setFirst(true);
        loopController.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
        loopController.setProperty(TestElement.GUI_CLASS, LoopControlPanel.class.getName());
        loopController.initialize();
        return loopController;
    }

    /**
     * This will create and return the thread group related to loop controller, no of threads and ramp up
     * @param loopController loop controller instance
     * @param noOfThreads no of threads
     * @param rampUp ramp up
     * @return thread group
     */
    public static ThreadGroup createThreadGroup(LoopController loopController,int noOfThreads,int rampUp){
        ThreadGroup threadGroup = new ThreadGroup();
        threadGroup.setName("Sample Thread Group");
        threadGroup.setNumThreads(noOfThreads);
        threadGroup.setRampUp(rampUp);
        threadGroup.setSamplerController(loopController);
        threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
        threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());

        return  threadGroup;
    }

    /**
     * This will create and return the thread group related to loop controller, no of threads,ramp up and duration
     * @param loopController loop controller instance
     * @param noOfThreads no of threads
     * @param rampUp ramp up
     * @param duration duration
     * @return thread group
     */
    public static ThreadGroup createThreadGroup(LoopController loopController, int noOfThreads, int rampUp, int duration) {
        ThreadGroup threadGroup = new ThreadGroup();
        threadGroup.setName("Sample Thread Group");
        threadGroup.setNumThreads(noOfThreads);
        threadGroup.setRampUp(rampUp);
        threadGroup.setDuration(duration);
        threadGroup.setSamplerController(loopController);
        threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
        threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());

        return threadGroup;
    }

    /**
     * This will create and return the transaction controller
     * @param name transaction name
     * @return transaction controller
     */
    public static TransactionController createTransactionController(String name) {
        TransactionController transactionController = new TransactionController();
        transactionController.setGenerateParentSample(true);
        transactionController.isEnabled();
        transactionController.setName(name);
        transactionController.setProperty(TestElement.TEST_CLASS, TransactionController.class.getName());
        transactionController.setProperty(TestElement.GUI_CLASS, TransactionControllerGui.class.getName());
        return transactionController;
    }

    /**
     * This will create and return the test plan
     * @param threadGroup thread group instance
     * @param sampler java sampler instance
     * @param planName test plan name
     * @return test plan
     */
    public static HashTree  createTestPlan(ThreadGroup threadGroup, JavaSampler sampler , String planName){
        HashTree testPlanTree = new HashTree();

        TestPlan testPlan = new TestPlan(planName);

        testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
        testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
        testPlan.setUserDefinedVariables((Arguments) new ArgumentsPanel().createTestElement());

        // Construct Test Plan from previously initialized elements
        testPlanTree.add(testPlan);

        HashTree threadGroupHashTree = testPlanTree.add(testPlan, threadGroup);
        threadGroupHashTree.add(sampler);

        return  testPlanTree;
    }

    /**
     * This will create a test plan with the passed name
     * @param name test plan name
     * @return test plan
     */
    public static TestPlan createTestPlan(String name) {
        TestPlan testPlan = new TestPlan(name);
        testPlan.setEnabled(true);
        testPlan.setSerialized(false);
        testPlan.setFunctionalMode(false);
        testPlan.setTearDownOnShutdown(true);
        testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
        testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
        testPlan.setUserDefinedVariables((Arguments) new ArgumentsPanel().createTestElement());
        return testPlan;
    }


    /**
     * This will create a test plan with listeners
     * @param threadGroup thread group instance
     * @param sampler java sampler
     * @param planName name of the test plan
     * @param grafanaListener grafana backend listener
     * @param qperfListener qperf backend listener
     * @return HashTree
     */
    public static HashTree createTestPlanWithListeners(ThreadGroup threadGroup, JavaSampler sampler, String planName, BackendListener grafanaListener, BackendListener qperfListener) {
        HashTree testPlanTree = new HashTree();

        TestPlan testPlan = new TestPlan(planName);
        testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
        testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
        testPlan.setUserDefinedVariables((Arguments) new ArgumentsPanel().createTestElement());

        // Construct Test Plan from previously initialized elements
        testPlanTree.add(testPlan);

        HashTree threadGroupHashTree = testPlanTree.add(testPlan, threadGroup);
        threadGroupHashTree.add(grafanaListener);
        threadGroupHashTree.add(qperfListener);
        threadGroupHashTree.add(sampler);

        return testPlanTree;
    }

    /**
     * This will save results to the test plan
     * @param testPlanTree test plan hash tree
     * @param jmeterHome jmeter home
     * @param jtlPath jtl path
     * @param csvPath csv path
     * @throws IOException throwing IOException
     */
    public  static void saveResult(HashTree testPlanTree,String jmeterHome,String jtlPath , String csvPath) throws IOException {
        SaveService.saveTree(testPlanTree, new FileOutputStream(jmeterHome+"/report/jmeter_api_sample.jmx"));

        //add Summarizer output to get test progress in stdout like:
        // summary =      2 in   1.3s =    1.5/s Avg:   631 Min:   290 Max:   973 Err:     0 (0.00%)
        Summariser summer = null;
        String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");
        if (summariserName.length() > 0) {
            summer = new Summariser(summariserName);
        }


        // Store execution results into a .jtl file, we can save file as csv also
        String reportFile = jtlPath+"/report/report.jtl";
        String csvFile = csvPath+"/report/report.csv";
        ResultCollector logger = new ResultCollector(summer);
        logger.setFilename(reportFile);
        ResultCollector csvlogger = new ResultCollector(summer);
        csvlogger.setFilename(csvFile);
        testPlanTree.add(testPlanTree.getArray()[0], logger);
        testPlanTree.add(testPlanTree.getArray()[0], csvlogger);
    }

    /**
     * This will run the the tests in the test plan
     * @param jMeterEngine jmeter engine instance
     * @param testPlanTree test plan tree
     */
    public static void runTest(StandardJMeterEngine jMeterEngine,HashTree testPlanTree){
        jMeterEngine.configure(testPlanTree);
        jMeterEngine.run();
    }

}
