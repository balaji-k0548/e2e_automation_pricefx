package com.syscolab.qe.core.db.tests;

import com.syscolab.qe.core.db.DBFitUtil;
import com.syscolab.qe.core.db.DBs;
import dbfit.api.DBEnvironment;
import dbfit.util.DataTable;
import dbfit.util.Options;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class SampleDBFitTest {

    private DBEnvironment dbEnvironment1;
    private DBEnvironment dbEnvironment2;

    @Test
    /**
     * This runs the DB queries
     */
    public void test() {

        DBFitUtil.setOption(Options.OPTION_BIND_SYMBOLS, "false");

        dbEnvironment1 = DBFitUtil.initDBEnvironment(DBs.POSTGRES, "postgres_host:port", "username", "password", "database_name");
        dbEnvironment2 = DBFitUtil.initDBEnvironment(DBs.DB2,"db2_host:port", "username", "password", "database_name");

        String postgresQuery = "SELECT opco_nbr AS OPCO, cust_ship_to_nbr AS CUST_NBR, TRIM(sale_oblig_trans_id) AS SALE_OBG_ID, line_nbr AS LINE, TRIM(itm_nbr) AS ITEM, " +
                "splt_cd AS SPLIT, curr_itm_qty AS CURR_QTY, net_prc AS NET_PRC, TRIM(supc_catgy_cd) AS SUPC, TRIM(itm_desc) AS IT_DESCRIPTION, curr_itm_splt_qty AS CURR_SPLT_QTY, " +
                "to_timestamp(sus_creat_dt_tm/1000)::DATE as SUS_CREAT_DT, cast(to_timestamp(sus_creat_dt_tm/1000) as time) as SUS_CREAT_TM, " +
                "cast(to_timestamp(sus_mod_dt_tm/1000) AS date) as SUS_MOD_DT, cast(to_timestamp(sus_mod_dt_tm/1000) as time) as SUS_MOD_TM " +
                "FROM mss.sus_obligation_detail WHERE opco_nbr ='045' AND cust_ship_to_nbr='246868' " +
                "ORDER BY opco_nbr,sale_oblig_trans_id,line_nbr,itm_nbr ASC LIMIT 500";

        String db2Query = "SELECT OPCO_NBR AS OPCO,CUST_SHIP_TO_NBR AS CUST_NBR,SALE_OBLIG_TRANS_ID AS SALE_OBG_ID, LINE_NBR AS LINE, ITM_NBR AS ITEM, SPLT_CD AS SPLIT, CURR_ITM_QTY AS CURR_QTY, NET_PRC AS NET_PRC, SUPC_CATGY_CD AS SUPC, " +
                "  ITM_DESC AS IT_DESCRIPTION, CURR_ITM_SPLT_QTY AS CURR_SPLT_QTY, " +
                "  CASE WHEN SUS_CREAT_DT IS NOT NULL THEN date(SUS_CREAT_DT) " +
                "          WHEN SUS_MOD_DT IS NULL AND SUS_CREAT_DT IS NULL THEN CAST('1970-01-01' AS DATE) " +
                "          ELSE date(SUS_MOD_DT) END " +
                "     as SUS_CREAT_DT,    " +
                "     CASE WHEN SUS_CREAT_DT IS NOT NULL AND SUS_CREAT_TM IS NOT NULL THEN SUS_CREAT_TM " +
                "          ELSE CAST('00:00:00' AS TIME) END " +
                "     as SUS_CREAT_TM, " +
                "     " +
                "      CASE WHEN SUS_MOD_DT IS NOT NULL THEN SUS_MOD_DT " +
                "          WHEN SUS_CREAT_TM IS NULL AND SUS_MOD_DT IS NULL THEN CAST('1970-01-01' AS DATE) " +
                "          ELSE SUS_CREAT_DT END " +
                "     as SUS_MOD_DT,    " +
                "     CASE WHEN SUS_MOD_DT IS NOT NULL AND SUS_MOD_TM IS NOT NULL THEN SUS_MOD_TM " +
                "          ELSE CAST('00:00:00' AS TIME) END " +
                "     as SUS_MOD_TM " +
                " FROM " +
                "  UOMLND.SUS_OBLIGATION_DETAIL WHERE OPCO_NBR='045' AND CUST_SHIP_TO_NBR='246868' ORDER BY OPCO_NBR,SALE_OBLIG_TRANS_ID,LINE_NBR,ITM_NBR ASC fetch first 500 rows only";

        DataTable postgresOutputDT = DBFitUtil.getDataTable(dbEnvironment1, postgresQuery, "postgresOutput");
        DataTable db2OutputDT = DBFitUtil.getDataTable(dbEnvironment2, db2Query, "db2Output");

        Assert.assertTrue(DBFitUtil.compareDataTablesForEquality(postgresOutputDT, db2OutputDT, new String[] {"OPCO", "CUST_NBR", "SALE_OBG_ID"}));
    }

    @AfterClass
    /**
     * This closes the DB Environment connections
     */
    public void runAfterClass() {
        DBFitUtil.closeDBEnvironments(dbEnvironment1, dbEnvironment2);
    }
}
