package com.syscolab.qe.core.ids.common;

public class Constants {
    //IDS Box & Credentials
    public static final String IDS_BOX = System.getProperty("ids.box", "as179dev");
    public static final String OPCO_NUMBER = System.getProperty("opco.number", "28");
    public static final String IDS_ENTER_CUSTOMER_ORDER = System.getProperty("ids.enter.customer.order", "OE01");
    public static final String IDS_PRINT_PICK_TICKET = System.getProperty("ids.print.pick.ticket", "OE02");
    public static final String IDS_ORDER_ENTRY_INQUIRY = System.getProperty("ids.order.entry.inquiry", "OE08");
    public static final String IDS_SQL_OPEN_CMD = System.getProperty("ids.sql.open.cmd", "strsql");
    public static final String IDS_DIRECTORY_OPEN_CMD = System.getProperty("ids.directory.open.cmd", "wrklnk '/pricefx/*'");
    public static final String IDS_CALL_PGM_FEECODE = System.getProperty("ids.call.pgm.feecode", "Call pgm(pfxob02cl)");
    public static final String IDS_FEECODE_HEADER_OUTBOUND = System.getProperty("ids.feecode.header.outbound", "select * from PFXFHOPF");
    public static final String IDS_FEECODE_CUSTOMER_OUTBOUND = System.getProperty("ids.feecode.customer.outbound", "select * from PFXFCOPF");
    public static final String IDS_FEECODE_PRODUCT_OUTBOUND = System.getProperty("ids.feecode.product.outbound", "select * from PFXFPRDPF");
    public static final String IDS_OPEN_WRKUSRJOB_CMD = System.getProperty("ids.sql.open.cmd", "wrkusrjob");
    public static final String IDS_CALL_PGM_LIVEPRICEGRID = System.getProperty("ids.call.pgm.livepricegrid", "Call pgm(pfxob01cl)");
    public static final String IDS_PRICELIST_DETAIL_OUTBOUND = System.getProperty("ids.pricelist.detail.outbound", "select * from PFXPLDPF");
    public static final String IDS_PRICELIST_HEADER_OUTBOUND = System.getProperty("ids.pricelist.header.outbound", "select * from PFXPLHPF");
    public static final String IDS_CALL_PGM_FUELSURCHARGE = System.getProperty("ids.call.pgm.fuelsurcharge", "Call pgm(pfxob08cl)");
}
