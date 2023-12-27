package com.syscolab.qe.core.ids.Pages;

public class SQLPage extends PageBase {

    public static int[] sqlField = {17, 7};
    public static int[] screenTitle = {1, 31};
    public static int[] positionToLine = {3, 32};

    public void typeSQLQuery(String query) throws Exception {
        typeText(query, sqlField);
    }

    public void typeLongSQLQuery(String query) throws Exception {
        sqlField[0] = 14;
        typeText(query, sqlField);
        sqlField[0] = 16;
    }

    public String getScreenTitle() throws Exception {
        return getText(screenTitle, 19);
    }

    public void typePositionText(String position) throws Exception {
        typeText(position, positionToLine);
    }

}