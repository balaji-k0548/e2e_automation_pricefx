package com.syscolab.qe.core.common;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the util class for Logger
 * @author  kasunalwis on 1/10/18.
 */
public class LoggerUtil {
    private LoggerUtil(){}
    private static Logger logger = Logger.getAnonymousLogger();
    public static int logLevel = Constants.LOGGER_DEFAULT_LEVEL;

    //Switch
    // 0 -      [OFF]       - Turn off all the logging
    // 1 -      [SEVERE]    - indicates a Serious Failure
    // 2 -      [WARNING]   - indicating a potential problem.
    //default   [INFO]      - indicating informational messages
    //change logLevel accordingly

    /**
     * This will set the log level
     */
    public static void setLogLevel(){

        switch (logLevel){
            case 0:
                logger.setLevel(Level.OFF);
                break;
            case 1:
                break;
            case 2 :
                logger.setLevel(Level.WARNING);
                break;
            case 3 :
                logger.setLevel(Level.INFO);
                break;
            default:
                logger.setLevel(Level.INFO);
        }

    }

    /**
     * This will set the logs for information
     * @param logMessage info message to be displayed
     */
    public static void logINFO(String logMessage) {
        setLogLevel();
        logger.log(Level.INFO, logMessage);
    }

    /**
     * This will set the logs for Error
     * @param logMessage error message to be displayed
     * @param throwable throwing a throwable
     */
    public static void logERROR(String logMessage, Throwable throwable) {
        setLogLevel();
        if (throwable != null)
            logger.log(Level.SEVERE, logMessage, throwable);
        else
            logger.log(Level.SEVERE, logMessage);
    }

    /**
     * This will set the logs for warning
     * @param logMessage warning message
     * @param throwable throwing a throwable
     */
    public static void logWARNING(String logMessage, Throwable throwable) {
        setLogLevel();
        if (throwable != null)
            logger.log(Level.WARNING, logMessage, throwable);
        else
            logger.log(Level.WARNING, logMessage);
    }
}
