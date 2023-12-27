package com.syscolab.qe.core.api.util;

import com.syscolab.qe.core.common.LoggerUtil;
import io.restassured.path.xml.XmlPath;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Map;

/**
 * This is the util class for xml
 * @author Mohammed Rifad
 */
public class XmlUtil {

    /**
     * This will try formatting the xml or catch the exceptions
     * @param inputFilePath file path of the input file
     * @param outputFilePath file path of the output file
     */
    public void formatXml(String inputFilePath,String outputFilePath){
        try {
            getXMLPrettyPrintText(inputFilePath,outputFilePath);
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }

    }

    /**
     * This will format the xml of the given file path and write to the output file
     * @param inputFilePath file path of the input file
     * @param outputFilePath file path of the output file
     * @throws ParserConfigurationException throwing ParserConfigurationException
     * @throws SAXException throwing SAXException
     * @throws IOException throwing IOException
     * @throws TransformerException throwing TransformerException
     */
    public void getXMLPrettyPrintText(String inputFilePath, String outputFilePath)
            throws ParserConfigurationException, SAXException, IOException, TransformerException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl",true);
        factory.setFeature("http://xml.org/sax/features/external-general-entities",false);
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities",false);

        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(inputFilePath));
        DOMSource domSource = new DOMSource(document);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");

        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        transformer.transform(domSource, result);
        PrintWriter pw = new PrintWriter(outputFilePath);
        pw.write(result.getWriter().toString());
        pw.close();
        pw.flush();
    }

    /**
     * This will return the xml value of the passed key
     * @param responseXml response xml
     * @param key key
     * @return value
     */
    public String getXmlValue(XmlPath responseXml, String key){
         return responseXml.get(key).toString();

        }


    /**
     * This will read the xml file and return the xml as a string
     * @param filePath xml file path
     * @return xml as a string
     */
    public String getXmlAsString(String filePath) {
        StringBuilder sb = new StringBuilder();

        try {
            File file = new File(filePath);
            try(BufferedReader br = new BufferedReader(new FileReader(file))){
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    LoggerUtil.logINFO(line);
                    line = br.readLine();
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    /**
     * This will replace value map with the request
     * @param request request
     * @param valuesMap map of value
     * @return replaced data
     */
    public  String replaceData(String request, Map<String, String> valuesMap) {

        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        return sub.replace(request);

    }
}
