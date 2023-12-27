package com.syscolab.qe.core.util.fileutil;

import com.syscolab.qe.core.common.LoggerUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileUtil {

    /**
     * Default constructor of the class
     */
    private FileUtil() {
    }

    /**
     * This is method for reading from a file
     * @param filePath File path
     * @return data
     */
    public static String readFromFile(String filePath) {
        StringBuilder data = new StringBuilder();
        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data.append(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            LoggerUtil.logINFO("An error occurred.");
            e.printStackTrace();
        }
        return data.toString();
    }

    public static boolean isResourcePathIsAFile(String resourcePath) {
        try {
            return new File(resourcePath).isFile();
        }
        catch (Exception ex) {
            LoggerUtil.logERROR(ex.getMessage(), ex);
            return false;
        }
    }

    public static boolean isResourcePathIsADirectory(String resourcePath) {
        try {
            return new File(resourcePath).isDirectory();
        }
        catch (Exception ex) {
            LoggerUtil.logERROR(ex.getMessage(), ex);
            return false;
        }
    }
}
