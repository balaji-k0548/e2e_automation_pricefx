package com.syscolab.qe.core.db;

import com.syscolab.qe.core.common.LoggerUtil;
import dbfit.api.DBEnvironment;
import dbfit.api.DbEnvironmentFactory;
import dbfit.fixture.StoreQuery;
import dbfit.util.*;

import java.sql.SQLException;

/**
 * This is the DB Fit Util class - test driven database development
 * @author Sandeep Perera
 */
public class DBFitUtil {

    /**
     * Default constructor for DBFitUtil class
     */
    private DBFitUtil() {}

    /**
     * This will set options
     * @param option option
     * @param value value
     */
    public static void setOption(String option, String value) {
        Options.setOption(option, value);
    }

    /**
     * This will initiate the DB environment
     * @param db DB instance
     * @param dataSource data source
     * @param username username
     * @param password password
     * @param database database name
     * @return db environment
     */
    public static DBEnvironment initDBEnvironment(DBs db, String dataSource, String username, String password, String database) {
        DBEnvironment dbEnvironment = DbEnvironmentFactory.newEnvironmentInstance(db.value());
        try {
            dbEnvironment.connect(dataSource, username, password, database);
        } catch (SQLException e) {
            LoggerUtil.logERROR(e.getMessage(), e);
        }
        return dbEnvironment;
    }

    /**
     * This will return the data table
     * @param dbEnvironment db environment instance
     * @param query query to execute
     * @param symbolName symbol name
     * @return data table
     */
    public static DataTable getDataTable(DBEnvironment dbEnvironment, String query, String symbolName) {
        StoreQuery postgresOutput = new StoreQuery(dbEnvironment, query, symbolName);
        postgresOutput.doTable(null);
        return SymbolUtil.getDataTable(symbolName);
    }

    /**
     * This will return the data row formatted to a string
     * @param dataRow data row instance
     * @param rowStructure row structure
     * @return data row formatted to a string
     */
    private static String getFormattedRowString(DataRow dataRow, RowStructure rowStructure) {

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < rowStructure.size(); i++) {
            if (i == 0)
                stringBuilder.append("{");

            stringBuilder.append(rowStructure.getColumnName(i)).append(": ").append(dataRow.get(rowStructure.getColumnName(i)));

            if (i == (rowStructure.size() -1))
                stringBuilder.append("}");
            else
                stringBuilder.append(", ");
        }

        return stringBuilder.toString();
    }

    /**
     * This method will do a cell-to-cell comparison of the given 2 Data Tables.
     * This will not stop upon detecting the first mismatching row,
     * But rather traverse through all cells and log mismatching rows (if any)
     *
     * @param dataTable1 Data Table 1
     * @param dataTable2 Data Table 2
     * @param columnsToCompare String Array of columns to compare
     * @return true iff tables match after a cell-to-cell comparison. false otherwise, and the mismatching rows will be logged.
     */
    public static boolean compareDataTables(DataTable dataTable1, DataTable dataTable2, String[] columnsToCompare) {

        if (dataTable1.getRows().size() != dataTable2.getRows().size()) {
            LoggerUtil.logERROR("Table Row Count mismatch. Table1 has " + dataTable1.getRows().size() + " Rows & Table2 has " + dataTable2.getRows().size() + " Rows", null);
            return false;
        }

        boolean[] keyColumns = new boolean[columnsToCompare.length];
        for (int i = 0; i < keyColumns.length; i++) {
            keyColumns[i] = true;
        }

        RowStructure rowStructure = new RowStructure(columnsToCompare, keyColumns);
        MatchingMaskBuilder mmb = new MatchingMaskBuilder(rowStructure);

        boolean rowsMatched = true;
        for (int i = 0; i < dataTable1.getRows().size(); i++) {
            if (!dataTable1.getRows().get(i).matches(mmb.buildMatchingMask(dataTable2.getRows().get(i)))) {
                LoggerUtil.logINFO("Table1.Row" + i + ": " + getFormattedRowString(dataTable1.getRows().get(i), rowStructure) + " != Table2.Row" + i + ": " + getFormattedRowString(dataTable2.getRows().get(i), rowStructure));
                rowsMatched = false;
            }
        }

        return rowsMatched;
    }

    /**
     * This method will do a cell-to-cell comparison of the given 2 Data Tables.
     * This will stop upon detecting the first mismatching row, log it and return false
     *
     * @param dataTable1 Data Table 1
     * @param dataTable2 Data Table 2
     * @param columnsToCompare String Array of columns to compare
     * @return true iff tables match after a cell-to-cell comparison. false otherwise.
     */
    public static boolean compareDataTablesForEquality(DataTable dataTable1, DataTable dataTable2, String[] columnsToCompare) {

        if (dataTable1.getRows().size() != dataTable2.getRows().size()) {
            LoggerUtil.logERROR("Table Row Count mismatch. Table1 has " + dataTable1.getRows().size() + " Rows & Table2 has " + dataTable2.getRows().size() + " Rows", null);
            return false;
        }

        boolean[] keyColumns = new boolean[columnsToCompare.length];
        for (int i = 0; i < keyColumns.length; i++) {
            keyColumns[i] = true;
        }

        RowStructure rowStructure = new RowStructure(columnsToCompare, keyColumns);
        MatchingMaskBuilder mmb = new MatchingMaskBuilder(rowStructure);

        for (int i = 0; i < dataTable1.getRows().size(); i++) {
            if (!dataTable1.getRows().get(i).matches(mmb.buildMatchingMask(dataTable2.getRows().get(i)))) {
                LoggerUtil.logERROR("Table1.Row" + i + ": " + getFormattedRowString(dataTable1.getRows().get(i), rowStructure) + " != Table2.Row" + i + ": " + getFormattedRowString(dataTable2.getRows().get(i), rowStructure), null);
                return false;
            }
        }

        return true;
    }

    /**
     * This will close the connection of DB environment
     * @param dbEnvironments db environment instance
     */
    public static void closeDBEnvironments(DBEnvironment... dbEnvironments) {
        for (DBEnvironment dbEnvironment: dbEnvironments) {
            try {
                dbEnvironment.closeConnection();
            } catch (SQLException e) {
                LoggerUtil.logWARNING(e.getMessage(), e);
            }
        }
    }
}
