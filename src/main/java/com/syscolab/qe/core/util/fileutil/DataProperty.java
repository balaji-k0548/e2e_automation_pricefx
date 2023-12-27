package com.syscolab.qe.core.util.fileutil;

import com.syscolab.qe.core.common.LoggerUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DataProperty {


    public DataProperty() {
    }

    private static Properties configProperties = null;
    private static Properties dataProperties = null;

    private static void loadConfigProperties() throws FileNotFoundException {
        configProperties = new Properties();
        load(DataProperty.class.getClassLoader().getResourceAsStream("config.properties"), configProperties);
    }

    private static void loadDataProperties() {
        dataProperties = new Properties();
        load(DataProperty.class.getClassLoader().getResourceAsStream("swms_qe_data.properties"), dataProperties);
    }

    private static void load(InputStream inputStream, Properties properties) {
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            LoggerUtil.logERROR(e.getMessage(), e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException ignore) {
            }
        }
    }

    public static String getProperty(String key) throws FileNotFoundException {
        if (configProperties == null) {
            loadConfigProperties();
        }
        return configProperties.getProperty(key);
    }

    public static String getDataProperty(String key) {
        if (dataProperties == null) {
            loadDataProperties();
        }
        return dataProperties.getProperty(key);
    }

}
