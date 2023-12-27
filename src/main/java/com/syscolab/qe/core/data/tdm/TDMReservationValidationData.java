package com.syscolab.qe.core.data.tdm;

import com.syscolab.qe.core.api.request.RequestBase;
import com.syscolab.qe.core.excelUtils.ReadExcel;

public class TDMReservationValidationData extends RequestBase{
    public String fileName;
    public String fileType;
    public String releaseName;
    public String environmentName;
    public String applicationName;
    public String[] checkingColumn;
    public String data;

    public TDMReservationValidationData(String filePath, String applicationName, String environmentName, String releaseName, String[] checkingColumn){
        this.fileName = filePath.substring(filePath.lastIndexOf('/') + 1, filePath.lastIndexOf('.'));
        this.fileType = ReadExcel.getFileIndex(filePath);
        this.releaseName = releaseName;
        this.environmentName = environmentName;
        this.applicationName = applicationName;
        this.checkingColumn = checkingColumn;
        this.data = ReadExcel.getExcelSheetInBase64(filePath);
    }
}
