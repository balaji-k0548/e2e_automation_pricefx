 package com.syscolab.qe.core.swms.util;


import com.sysco.javaprofile.functions.Constants;
import com.sysco.javaprofile.functions.JavaDriver;
import com.sysco.javaprofile.functions.JavaProfile;
import com.syscolab.qe.core.swms.common.OracleFormsConstants;

import java.io.File;

/**
 * This is the class to set up the oracle driver
 * @author  Rifad on 3/8/18.
 */
public class OracleDriverSetUpUtil {
    private JavaDriver driver;

    /**
     * This is the default constructor of the class
     */
    public OracleDriverSetUpUtil() {
        // Empty constructor
    }

    /**
     * This will launch the java driver in the passed window
     * @param window window
     * @return java driver
     * @throws InterruptedException throwing InterruptedException
     */

    public JavaDriver launchJavaDriver(String window) throws InterruptedException {
        System.setProperty(Constants.PROP_PROJECT_FRAMEWORK, Constants.FRAMEWORK_SWING);
        JavaProfile profile = new JavaProfile(JavaProfile.LaunchMode.JAVA_WEBSTART);
        File f = findFile();
        if (f == null) {
            return null;
        }
        profile.setJNLPPath(f.getAbsolutePath());
        profile.setStartWindowTitle(window);
        System.err.println("Launching: " + profile.getCommandLine());
        Thread.sleep(OracleFormsConstants.SWMS_LOADING_TIME);
        driver = new JavaDriver(profile);

        return driver;
    }

    /**
     * This will launch the java driver related to the passed window and jnlpFilePath
     * @param window window
     * @param jnlpFilePath jnlp file path
     * @return java driver
     * @throws InterruptedException throwing InterruptedException
     */
    public JavaDriver launchJavaDriver(String window, String jnlpFilePath) throws InterruptedException {
        System.setProperty(Constants.PROP_PROJECT_FRAMEWORK, Constants.FRAMEWORK_SWING);
        JavaProfile profile = new JavaProfile(JavaProfile.LaunchMode.JAVA_WEBSTART);
        profile.setJNLPPath(jnlpFilePath);
        profile.setStartWindowTitle(window);
        System.err.println("Launching: " + profile.getCommandLine());
        Thread.sleep(OracleFormsConstants.SWMS_LOADING_TIME);
        driver = new JavaDriver(profile);

        return driver;
    }


    /**
     * This will find and return the file application under test
     * @return file
     */
    private static File findFile() {
        File f = new File(System.getProperty(OracleFormsConstants.APPLICATION_UNDER_TEST, System.getProperty("user.dir") + "/src/main/resources/applications/" + OracleFormsConstants.APPLICATION_UNDER_TEST));
        if (f.exists()) {
            return f;
        }

        return null;
    }

    /**
     * This will find and return the file passed by the file name
     * @param fileName name of the file
     * @return file
     */
    private static File findFile(String fileName) {
        File f = new File(System.getProperty(fileName, System.getProperty("user.dir") + "/src/main/resources/applications/" + fileName));
        if (f.exists()) {
            return f;
        }
        return null;
    }

}
