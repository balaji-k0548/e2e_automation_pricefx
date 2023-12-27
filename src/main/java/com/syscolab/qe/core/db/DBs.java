package com.syscolab.qe.core.db;

/**
 * This is the enum for Databases
 * @author Sandeep Perera
 */
public enum DBs {

    ORACLE("oracle"),
    MY_SQL("MySql"),
    SQL_SERVER("sqlserver"),
    HSQLDB("hsqldb"),
    DERBY("derby"),
    POSTGRES("postgres"),
    DB2("DB2");

    private String value;

    private DBs(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}
