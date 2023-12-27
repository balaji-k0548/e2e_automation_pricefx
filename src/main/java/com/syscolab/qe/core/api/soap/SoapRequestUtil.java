package com.syscolab.qe.core.api.soap;

import com.syscolab.qe.core.common.LoggerUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

/**
 * This is the util class for Soap Requests
 * @author Mohammed Rifad
 */
public class SoapRequestUtil {
    static Logger logger = Logger.getLogger(SoapRequestUtil.class);
    public String responseString = null;

    /**
     * This will write the response to a file and return the soap response for POST method
     * @param url url
     * @param requestFilePath file path of the request
     * @param responseFilePath file path of the response
     * @return response
     * @throws IOException throwing IOException
     * @throws TransformerException throwing TransformerException
     * @throws ParserConfigurationException throwing ParserConfigurationException
     * @throws SAXException throwing SAXException
     */
    public HttpResponse getSoapResponseByPostMethod(String url, String requestFilePath, String responseFilePath)
            throws IOException, TransformerException, ParserConfigurationException, SAXException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-type", "text/xml;charset=UTF-8");
        post.setEntity(new StringEntity(requestFilePath));
        HttpResponse response = client.execute(post);
        writeResponseToFile(responseFilePath, response);
        return response;
    }

    /**
     * This will return the soap response for POST method
     * @param url url
     * @param requestFilePath file path of the request
     * @return response
     * @throws IOException throwing IOException
     */
    public HttpResponse getSoapResponse(String url, String requestFilePath) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-type", "text/xml;charset=UTF-8");
        post.setEntity(new StringEntity(requestFilePath));
        HttpResponse response = client.execute(post);
        return response;
    }

    /**
     * This will convert xml response to a json object and return the json object
     * @param response response
     * @return json object
     * @throws JSONException throwing JSONException
     */
    public JSONObject convertXMLtoJSON(String response) throws JSONException {
        //converting xml to json object
        JSONObject obj = XML.toJSONObject(response);
        return obj;
    }

    /**
     * This will write the response to a file
     * @param filePath file path for the response file
     * @param response response
     * @throws IOException throwing IOException
     * @throws TransformerException throwing TransformerException
     * @throws SAXException throwing SAXException
     * @throws ParserConfigurationException throwing ParserConfigurationException
     */
    public void writeResponseToFile(String filePath, HttpResponse response) throws IOException, TransformerException, SAXException, ParserConfigurationException {

        filePath = System.getProperty("user.dir") + "/src/main/resources/" + filePath;
        logger.info("......file path....." + filePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        PrintWriter pw = new PrintWriter(filePath);
        pw.write(sb.toString());
        pw.close();
        pw.flush();
        responseString = sb.toString();

        LoggerUtil.logINFO("...............result\n............" + sb.toString());
        logger.info("......Result can be found at .....:\n" + filePath);

        //Formatting the result and pushing to response file path
        getXMLPrettyPrintText(filePath, filePath);
    }

    /**
     * This will return the response as a string
     * @param response response as a HttpResponse object
     * @return response as a string
     * @throws IOException throwing IOException
     */
    public String getResponseAsString(HttpResponse response) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        StringBuilder stringBuilder= new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }

        return stringBuilder.toString();

    }

    /**
     * This will write the xml pretty print text into the output file
     * @param inputFilePath path of the input file
     * @param outputFilePath path of the output file
     * @throws ParserConfigurationException throwing ParserConfigurationException
     * @throws SAXException throwing ParserConfigurationException
     * @throws IOException throwing ParserConfigurationException
     * @throws TransformerException throwing ParserConfigurationException
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


}

