package com.syscolab.qe.core.reporting;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static com.syscolab.qe.core.common.Constants.QLYTICS_DAILY_WEEKLY_BUILD;
import static com.syscolab.qe.core.common.Constants.QLYTICS_QMETRY_RELEASE;

/**
 * This is the class for QCenter related methods
 * @author  yoosufm on 6/7/17.
 */
public class SyscoLabQCenter {

    private String projectName;
    private String branchName;
    private String environment;
    private String module;
    private String feature;
    private String className;
    private String releaseInternal;
    private String testLayer = "";

    /**
     * This will return the class name
     * @return class name
     */
    public String getClassName() {
        return className;
    }

    /**
     * This will set the class name
     * @param className class name
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * This will return the qCenter project name
     * @return qCenter project name
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * This will set the qCenter project name
     * @param projectName qCenter project name
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * This will get the repository branch name of the runtime
     * @return repository branch name
     */
    public String getBranchName()  {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec( "git branch --show-current");
            process.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            return reader.readLine();
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
            return "";

        }
    }

    /**
     * This will set the repository branch name of the run time
     * @param branchName repository branch name
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    /**
     * This will return the qCenter environment
     * @return qCenter environment
     */
    public String getEnvironment() {
        return environment;
    }

    /**
     * This will set the qCenter environment
     * @param environment qCenter environment
     */
    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    /**
     * This will return the qCenter release name
     * @return qCenter release name
     */
    public String getRelease() {
        if(QLYTICS_QMETRY_RELEASE ||(QLYTICS_DAILY_WEEKLY_BUILD.equalsIgnoreCase("weekly"))||(QLYTICS_DAILY_WEEKLY_BUILD.equalsIgnoreCase("daily") )){
            this.releaseInternal = System.getProperty("release.internal.name");
        }
        return releaseInternal;
    }

    /**
     * This will set the qCenter release name
     * @param release qCenter release name
     */
    public void setRelease(String release) {
        this.releaseInternal = release;
    }

    /**
     * This will return the qCenter module name
     * @return qCenter module name
     */
    public String getModule() {
        return module;
    }

    /**
     * This will set the qCenter module name
     * @param module qCenter module name
     */
    public void setModule(String module) {
        this.module = module;
    }

    /**
     * This will return the qCenter feature name
     * @return feature name
     */
    public String getFeature() {
        return feature;
    }

    /**
     * This will set the qCenter feature name
     * @param feature feature name
     */
    public void setFeature(String feature) {
        this.feature = feature;
    }

   /**
     * This will return the testLayer name
     * @return testLayer name
     */
    public String getTestLayer() {
        return testLayer;
    }

    /**
     * This will set the testLayer name
     * @param testLayer name
     */
    public void setTestLayer(String testLayer) {
        this.testLayer = testLayer;
    }
}
