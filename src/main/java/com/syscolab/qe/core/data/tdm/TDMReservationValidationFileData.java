package com.syscolab.qe.core.data.tdm;

import com.syscolab.qe.core.api.request.RequestBase;

public class TDMReservationValidationFileData extends RequestBase{
    public int releaseId;
    public int queryId;
    public String sheetName;
    public String columnNameOrJSONPath;
    public String sheetPath;

    public TDMReservationValidationFileData(int releaseId, int queryId, String sheetName, String columnNameOrJSONPath, String sheetPath){
        this.releaseId = releaseId;
        this.queryId = queryId;
        this.sheetName = sheetName;
        this.columnNameOrJSONPath = columnNameOrJSONPath;
        this.sheetPath = sheetPath;
    }
}
