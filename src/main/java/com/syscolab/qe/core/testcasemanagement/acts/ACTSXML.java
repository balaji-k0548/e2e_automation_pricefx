package com.syscolab.qe.core.testcasemanagement.acts;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This is the class for automated combinatorial testing for software
 */
public class ACTSXML {

    public String completeXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "SYSTEMXML \n" +
            "PARAMETERS \n" +
            "  <OutputParameters />\n" +
            "  <Relations />\n" +
            "  <Constraints />\n" +
            "</System>";
    public String systemXML = "<System name=\"SYSTEMNAME\">";
    public String parametersXML = "<Parameters>PARAMETER </Parameters>";
    public String parameterXML = "<Parameter id=\"PARAMETER_ID\" name=\"PARAMETER_NAME\" type=\"1\">" + "VALUES" + "<basechoices />" + "</Parameter>";
    public String valuesXML = "<values> VALUE </values>";
    public String valueXML = "<value>VALUENAME</value>";

    /**
     * This will return the complete xml file content
     * @return complete xml
     */
    public String getXMLFileContent(){
        parametersXML = parametersXML.replace("PARAMETER", parameterXML);
        completeXML = completeXML.replace("SYSTEMXML", systemXML).replace("PARAMETERS", parametersXML);
        System.out.println(completeXML);
        return completeXML;
    }

    /**
     * This will write the content to a xml file
     * @param fileName xml file name
     * @param content content to be written
     */
    public void writeToXML(String fileName, String content){
        try {
            Path path = Paths.get("xmlFiles");
            if (!Files.exists(path)) {
                try {
                    Files.createDirectories(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            FileOutputStream fileOutputStream = new FileOutputStream("xmlFiles/" + fileName + ".xml");
            try {
                fileOutputStream.write(content.getBytes());
            }finally {
                fileOutputStream.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This will generate the possible combinations using Automated Combinatorial Testing for Software
     * @param fileName name of the file
     * @param numberOfParameters number of the parameters
     */
    public void generateACTSCombination(String fileName, int numberOfParameters){
        try {
            Path path = Paths.get("actsCombinations");
            if (!Files.exists(path)) {
                try {
                    Files.createDirectories(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Process p = Runtime.getRuntime().exec("java -Ddoi=" + numberOfParameters + " -Doutput=excel -jar src/main/resources/acts_cmd_2.92.jar cmd xmlFiles/" + fileName+ ".xml actsCombinations/" + fileName + ".xls");
            p.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
