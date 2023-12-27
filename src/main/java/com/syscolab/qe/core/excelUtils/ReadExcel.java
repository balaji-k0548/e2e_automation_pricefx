package com.syscolab.qe.core.excelUtils;

import com.syscolab.qe.core.util.json.JsonUtil;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * This is the util class for Read Excel
 *
 * @author Sandeep Perera
 */
public class ReadExcel {

    private ReadExcel() {
    }

    /**
     * This will return the data object from the Excel sheet
     *
     * @param key           - column Data(key)
     * @param uniqueField   - field name
     * @param excelFilePath - location
     * @param sheetID       - ID of the sheet
     * @param aClass        - mapping class
     * @return data object
     */
    public static Object getDataFromExcelSheet(String key, Field uniqueField, String excelFilePath, int sheetID, Class aClass) {

        Object object = null;
        try {
            //get excel sheet
            Sheet sheet = readExcelSheet(excelFilePath, sheetID);
            if (sheet != null) {
                object = aClass.newInstance();
                Field[] fields = object.getClass().getDeclaredFields();
                int colCount = sheet.getColumns();
                int rowCount = sheet.getRows();
                int dataRow = -1;
                int uniqueFieldColId = -1;
                ArrayList<String> headerNameList = new ArrayList<String>(colCount);

                //select matching column
                for (int i = 0; i < colCount; i++) {
                    String colHeader = sheet.getCell(i, 0).getContents();
                    headerNameList.add(colHeader);
                    if (colHeader.toLowerCase().equals(uniqueField.getName().toLowerCase())) {
                        uniqueFieldColId = i;
                    }
                }

                //select matching row
                for (int i = 0; i < rowCount; i++) {
                    String cellData = sheet.getCell(uniqueFieldColId, i).getContents();
                    if (cellData.equals(key)) {
                        dataRow = i;
                        break;
                    }
                }

                //get row data and add data to a relevant object
                addRowDataToObject(dataRow, fields, headerNameList, object, sheet);
            }
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static String getFileIndex(String fileName) {
        String extension = "";
        int index = fileName.lastIndexOf('.');
        if (index > 0) {
            extension = fileName.substring(index + 1);
        }
        return extension;
    }

    /**
     * This will return the data object from the Excel sheet
     *
     * @param filePath   - location
     * @param sheetName  - name of the sheet
     * @param columnNameOrJSONPath - unique column name or JSONPath
     * @return string
     *
     */
    public static String getDataFromDataSheet(String filePath, String columnNameOrJSONPath, String sheetName) {
        String columnValues;
        String fileExtension = getFileIndex(filePath);
        switch (fileExtension){
            case "json":
                columnValues = (JsonUtil.readJSON(filePath, columnNameOrJSONPath)).toString();
                break;
            case "csv":
                columnValues = String.valueOf(readCSVSheet(filePath, columnNameOrJSONPath));
                break;
            default:
                columnValues = readExcelSheet(filePath, columnNameOrJSONPath, sheetName);
                break;
        }
        return columnValues;
    }

    /**
     * Read Excel file when unique data is given
     *
     * @param key           - column Data(key)
     * @param excelFilePath - location
     * @param sheetID       - ID of the sheet
     * @param aClass        - mapping class
     * @return data object
     */
    public static Object getDataFromExcelSheet(String key, String excelFilePath, int sheetID, Class aClass) {
        Object object = null;
        try {
            //get excel sheet
            Sheet sheet = readExcelSheet(excelFilePath, sheetID);
            Objects.requireNonNull(sheet);
            object = aClass.newInstance();
            Field[] fields = object.getClass().getDeclaredFields();
            int colCount = sheet.getColumns();
            int rowCount = sheet.getRows();
            int dataRow = -1;
            ArrayList<String> headerNameList = new ArrayList<String>(colCount);

            //select matching row
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < colCount; j++) {
                    if (i == 0) {
                        String colHeader = sheet.getCell(j, i).getContents();
                        headerNameList.add(colHeader);
                    } else {
                        String cellData = sheet.getCell(j, i).getContents();
                        if (cellData.equals(key)) {
                            dataRow = i;
                            break;
                        }
                    }
                }
            }

            //get row data and add data to a relevant object
            addRowDataToObject(dataRow, fields, headerNameList, object, sheet);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * Read Excel file when unique data is given
     *
     * @param key           - column Data(key)
     * @param excelFilePath - location
     * @param sheetID       - ID of the sheet
     * @param aClass        - mapping class
     * @return data object
     */
    public static Object getDataFromExcelSheet(String key, String excelFilePath, String sheetID, Class aClass) {
        Object object = null;
        try {
            //get excel sheet
            Sheet sheet = readExcelSheet(excelFilePath, sheetID);
            Objects.requireNonNull(sheet);
            object = aClass.newInstance();
            Field[] fields = object.getClass().getDeclaredFields();
            int colCount = sheet.getColumns();
            int rowCount = sheet.getRows();
            int dataRow = -1;
            ArrayList<String> headerNameList = new ArrayList<String>(colCount);

            //select matching row
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < colCount; j++) {
                    if (i == 0) {
                        String colHeader = sheet.getCell(j, i).getContents();
                        headerNameList.add(colHeader);
                    } else {
                        String cellData = sheet.getCell(j, i).getContents();
                        if (cellData.equals(key)) {
                            dataRow = i;
                            break;
                        }
                    }
                }
            }

            //get row data and add data to a relevant object
            addRowDataToObject(dataRow, fields, headerNameList, object, sheet);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return object;
    }


    /**
     * This will map sheet data to given object
     *
     * @param dataRow        data row
     * @param fields         fields array
     * @param headerNameList list of header names
     * @param object         object to be set
     * @param sheet          excel sheet
     */
    public static void addRowDataToObject(int dataRow, Field[] fields, List<String> headerNameList, Object object, Sheet sheet) {
        for (int j = 0; j < fields.length; ++j) {
            if (headerNameList.get(j).equalsIgnoreCase(fields[j].getName())) {
                String fieldName = fields[j].getName();
                String fieldValue = sheet.getCell(j, dataRow).getContents();

                try {
                    object.getClass().getField(fieldName).set(object, fieldValue);
                } catch (IllegalAccessException | NoSuchFieldException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }


    /**
     * This will read the Excel file when location and id are given
     *
     * @param excelPathName path name of the Excel
     * @param sheetID       id of the Excel sheet
     * @return excel sheet
     */
    public static Sheet readExcelSheet(String excelPathName, int sheetID) {
        //read Excel file
        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(new File(excelPathName));
        } catch (IOException | BiffException e) {
            e.printStackTrace();
        }
        if (workbook == null) {
            return null;
        }
        return workbook.getSheet(sheetID);
    }

    /**
     * This will read the Excel file when location and name are given
     *
     * @param excelPathName path name of the Excel
     * @param sheetName     name of the Excel sheet
     * @return excel sheet
     */
    public static Sheet readExcelSheet(String excelPathName, String sheetName) {
        //read Excel file
        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(new File(excelPathName));
        } catch (IOException | BiffException e) {
            e.printStackTrace();
        }
        if (workbook == null) {
            return null;
        }
        return workbook.getSheet(sheetName);
    }

    /**
     * This will read the Excel file when location and name are given
     *
     * @param filePath path name of the Excel
     * @param sheetName     name of the Excel sheet
     * @param columnName     name of the column in sheet
     * @return columnValues
     */
    public static String readExcelSheet(String filePath, String columnName, String sheetName) {
        String columnValues = null;
        Sheet sheet = readExcelSheet(filePath, sheetName);
        if (sheet != null) {
            int colCount = sheet.getColumns();
            int rowCount = sheet.getRows();
            int uniqueFieldColId = -1;
            //select matching column
            for (int i = 0; i < colCount; i++) {
                String colHeader = sheet.getCell(i, 0).getContents();
                if (colHeader.toLowerCase().equals(columnName.toLowerCase())) {
                    uniqueFieldColId = i;
                    for (int j = 0; j < rowCount; j++) {
                        String cellValue = sheet.getCell(uniqueFieldColId, j).getContents();
                        if (cellValue != null && !cellValue.equals("") && !cellValue.equalsIgnoreCase(columnName)) {
                            columnValues=(sheet.getCell(uniqueFieldColId, j).getContents());
                        }
                    }
                    break;
                }
            }
        }
        return columnValues;
    }

    /**
     * This will read the Excel file when location
     * @param filePath path name of the Excel
     * @return content
     */
    public static String readExcelSheet(String filePath) {
        StringBuilder value = new StringBuilder();
        Sheet sheet = readExcelSheet(filePath, 0);
        if (sheet != null) {
            int colCount = sheet.getColumns();
            int rowCount = sheet.getRows();
            int uniqueFieldColId = -1;
            for (int i = 0; i < colCount; i++) {
                uniqueFieldColId = i;
                for (int j = 0; j < rowCount; j++) {
                    String cellValue = sheet.getCell(uniqueFieldColId, j).getContents();
                    if (cellValue != null && !cellValue.equals("")) {
                        value.append(sheet.getCell(uniqueFieldColId, j).getContents());
                    }
                }
            }
        }
        return value.toString();
    }

    /**
     * This will read the Excel file when location
     * @param filePath path name of the Excel
     * @return base64 content
     */
    public static String getExcelSheetInBase64(String filePath) {
        File file = new File(filePath);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        byte[] bytes = new byte[(int) file.length()];
        try {
            fis.read(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Base64.encodeBase64String(bytes);
    }

    /**
     * This will read the Excel file when location and name are given
     *
     * @param csvPathName path name of the CSV File
     * @param columnName path name of the column
     * @return csvValues sheet
     */
    public static ArrayList<String> readCSVSheet(String csvPathName, String columnName) {
        ArrayList<String> csvValues = new ArrayList<>();
        try (
                BufferedReader br = new BufferedReader(new FileReader(csvPathName));
                CSVParser parser = CSVFormat.DEFAULT.withDelimiter(',').withHeader().parse(br);
        ) {
            for (CSVRecord record : parser) {
                csvValues.add(record.get(columnName).replace(",",""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return csvValues;
    }

//    //keeping this main method for debugging purpose in future
//    public static void main(String[] args) {
//        String filePath = "/Users/administrator/Downloads/sample4.json";//sheet is not attached in the project
//        String jsonPath = "$.people[:3].firstName";
//        readJSON(filePath, jsonPath);
//    }
}