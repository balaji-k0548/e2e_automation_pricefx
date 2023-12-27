package com.syscolab.qe.core.testcasemanagement.acts;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This is the class for Excel Reader
 * @author Sandeep Perera
 */
public class ExcelReader {
    public static final String SAMPLE_XLSX_FILE_PATH = "./actsCombinations/builds-get.xls";
    public static final String SAMPLE_XLSX_FILE_PATH_STRUCTURE = "./src/main/Test_Case_Structure.xlsx";

    static DataFormatter dataFormatter = new DataFormatter();

    /**
     * This is the main method
     * @param args stores the incoming arguments
     * @throws IOException throwing IOException
     * @throws InvalidFormatException throwing InvalidFormatException
     */
    public static void main(String args[]) throws IOException, InvalidFormatException {
        retrieveTestCaseData("automations-post");
    }

    /**
     * This will retrieve test case data
     * @param testDataFileName test data file name
     * @throws IOException throwing IOException
     * @throws InvalidFormatException throwing InvalidFormatException
     */
    public static void retrieveTestCaseData(String testDataFileName) throws IOException, InvalidFormatException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        try(Workbook workbookReadFrom = WorkbookFactory.create(new File("actsCombinations/" + testDataFileName + ".xls"))){
            try(Workbook workbookStructure = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH_STRUCTURE))){
                Sheet sheetCopyFrom = workbookReadFrom.getSheetAt(0);
                Sheet sheetStructure = workbookStructure.getSheetAt(0);

                ArrayList<String> columnHeadersList = new ArrayList<>();
                List<HashMap> globalTestCaseDataList = new ArrayList<>();

                //getting the headers
                setGlobalTestDataList(sheetCopyFrom, columnHeadersList, globalTestCaseDataList);

                setCellValuesInGlobalTestCaseDataList(sheetStructure, globalTestCaseDataList);

                Path path = Paths.get("testCases");
                if (!Files.exists(path)) {
                    try {
                        Files.createDirectories(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                workbookStructure.write(new FileOutputStream("testCases/" + testDataFileName + ".xlsx"));
            }

        }

    }
    /**
     * This will set test details
     * @param sheetCopyFrom sheet to copy details from
     * @param columnHeadersList sheet column header array
     * @param globalTestCaseDataList test case data list
     */
    private static void setGlobalTestDataList(Sheet sheetCopyFrom, ArrayList<String> columnHeadersList, List<HashMap> globalTestCaseDataList) {
        for (Row row: sheetCopyFrom) {
            if (row.getRowNum() == 0){
                for(Cell cell: row) {
                    String cellValue = dataFormatter.formatCellValue(cell);
                    if (cell.getColumnIndex() != 0){
                        columnHeadersList.add(cellValue);
                    }
                }
            }

            HashMap<String, String> testCaseData = new HashMap<>();

            //values
            getCellValuesInARow(columnHeadersList, row, testCaseData);

            globalTestCaseDataList.add(testCaseData);
        }
    }
    /**
     * This will get the cell values in a row
     * @param columnHeadersList sheet column header array
     * @param row Row
     * @param testCaseData test case data
     */
    private static void getCellValuesInARow(ArrayList<String> columnHeadersList, Row row, HashMap<String, String> testCaseData) {
        for(Cell cell: row) {
            if(row.getRowNum() != 0){
                String cellValue = dataFormatter.formatCellValue(cell);
                if (cell.getColumnIndex() != 0){
                    testCaseData.put(columnHeadersList.get(cell.getColumnIndex() - 1), cellValue);
                }
            }
        }
    }
    /**
     * This will set cell values in Test case data list
     * @param sheetStructure sheet structure
     * @param globalTestCaseDataList test case data list
     */
    private static void setCellValuesInGlobalTestCaseDataList(Sheet sheetStructure, List<HashMap> globalTestCaseDataList) {
        for (int i = 0; i < globalTestCaseDataList.size() ; i++){
            Row row = sheetStructure.getRow(i + 1);
            if (row == null) {
                row = sheetStructure.createRow(i + 1);
            }
            Cell cell = row.getCell(11);
            if (cell == null) {
                cell = row.createCell(11);
            }

            cell.setCellValue(globalTestCaseDataList.get(i).toString());
        }
    }

}
