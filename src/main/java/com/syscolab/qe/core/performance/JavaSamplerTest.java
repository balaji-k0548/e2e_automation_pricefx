package com.syscolab.qe.core.performance;


import com.syscolab.qe.core.common.LoggerUtil;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.java.control.gui.JavaTestSamplerGui;
import org.apache.jmeter.protocol.java.sampler.JavaSampler;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This is the class used for test JMeter API
 * @author Mohammed Rifad
 */
public class JavaSamplerTest {

    private static final String USER_DIR = "user.dir";

    /**
     * This is the test class used for testing JMeter API
     * @throws IOException throwing IOException
     */
    @Test
    public void testJmeterTest() throws IOException {
        File jmeterHome = new File(System.getProperty(USER_DIR)+"/src/main/java/com/syscolab/qe/core/performance/");
        String slash = System.getProperty("file.separator");

        if (jmeterHome.exists()) {
            File jmeterProperties = new File(jmeterHome+"/jmeter.properties");
            if (jmeterProperties.exists()) {
                //JMeter Engine
                StandardJMeterEngine jmeter = new StandardJMeterEngine();

                //JMeter initialization (properties, log levels, locale, etc)
                JMeterUtils.setJMeterHome(System.getProperty(USER_DIR)+"/src/main/java/com/syscolab/qe/core/performance");
                JMeterUtils.loadJMeterProperties(jmeterProperties.getPath());
                JMeterUtils.initLogging();// you can comment this line out to see extra log messages of i.e. DEBUG level
                JMeterUtils.initLocale();

                // JMeter Test Plan, basically JOrphan HashTree
                HashTree testPlanTree = new HashTree();

                // First HTTP Sampler - open uttesh.com

                // Java Request

                JavaSampler examplecomSampler = new JavaSampler();
                examplecomSampler.setName("Java Request");
                examplecomSampler.setClassname("com.syscolab.qe.core.performance.AddNumberTest");
                Arguments arguments = new Arguments();
                arguments.addArgument("var1", "10");
                arguments.addArgument("var2", "10");
                examplecomSampler.setArguments(arguments);
                examplecomSampler.setProperty(TestElement.TEST_CLASS, JavaSampler.class.getName());
                examplecomSampler.setProperty(TestElement.GUI_CLASS, JavaTestSamplerGui.class.getName());



                // Loop Controller
                LoopController loopController = new LoopController();
                loopController.setLoops(2);
                loopController.setFirst(true);
                loopController.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
                loopController.setProperty(TestElement.GUI_CLASS, LoopControlPanel.class.getName());
                loopController.initialize();

                // Thread Group
                ThreadGroup threadGroup = new ThreadGroup();
                threadGroup.setName("Sample Thread Group");
                threadGroup.setNumThreads(5);
                threadGroup.setRampUp(1);
                threadGroup.setSamplerController(loopController);
                threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
                threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());

                // Test Plan
                TestPlan testPlan = new TestPlan("Create JMeter Script From Java Code");

                testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
                testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
                testPlan.setUserDefinedVariables((Arguments) new ArgumentsPanel().createTestElement());

                // Construct Test Plan from previously initialized elements
                testPlanTree.add(testPlan);

                HashTree threadGroupHashTree = testPlanTree.add(testPlan, threadGroup);
                threadGroupHashTree.add(examplecomSampler);

                // save generated test plan to JMeter's .jmx file format
                SaveService.saveTree(testPlanTree, new FileOutputStream(System.getProperty(USER_DIR) + "/report/jmeter_api_sample.jmx"));

                //add Summarizer output to get test progress in stdout like:
                // summary =      2 in   1.3s =    1.5/s Avg:   631 Min:   290 Max:   973 Err:     0 (0.00%)
                Summariser summer = null;
                String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");
                if (summariserName.length() > 0) {
                    summer = new Summariser(summariserName);
                }


                // Store execution results into a .jtl file, we can save file as csv also
                String reportFile = System.getProperty(USER_DIR)+"/report/report.jtl";
                String csvFile = System.getProperty(USER_DIR)+"/report/report.csv";
                ResultCollector logger = new ResultCollector(summer);
                logger.setFilename(reportFile);
                ResultCollector csvlogger = new ResultCollector(summer);
                csvlogger.setFilename(csvFile);
                testPlanTree.add(testPlanTree.getArray()[0], logger);
                testPlanTree.add(testPlanTree.getArray()[0], csvlogger);

                // Run Test Plan
                jmeter.configure(testPlanTree);
                jmeter.run();

                LoggerUtil.logINFO("Test completed. See " + jmeterHome + slash + "report.jtl file for results");
                LoggerUtil.logINFO("JMeter .jmx script is available at " + jmeterHome + slash + "jmeter_api_sample.jmx");

            }
        }

        LoggerUtil.logINFO("jmeterHome property is not set or pointing to incorrect location");

    }

    /**
     * This is the test class used for testing JMeter API with call
     * @throws IOException throwing IOException,
     * @throws InterruptedException throwing InterruptedException
     */
    @Test
    public void testJmeterTestWithCall() throws IOException, InterruptedException {
        StandardJMeterEngine jMeterEngine = JmeterHelper.startJmeter(System.getProperty(USER_DIR)+"/src/main/java/com/syscolab/qe/core/performance/");
        JavaSampler sampler = JmeterHelper.createJavaSampler("com.syscolab.qe.core.performance.AddNumberTest");
        LoopController loopController = JmeterHelper.createLoopController(2);
        ThreadGroup threadGroup = JmeterHelper.createThreadGroup(loopController, 1000, 1);
        HashTree testPlan = JmeterHelper.createTestPlan(threadGroup,sampler,"Java Test");
        JmeterHelper.saveResult(testPlan,System.getProperty(USER_DIR),System.getProperty(USER_DIR),System.getProperty(USER_DIR));
        JmeterHelper.startDistributeJMeterRunner("10.1.10.48", testPlan);
        Thread.sleep(20000);

    }
}
