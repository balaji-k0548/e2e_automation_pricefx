package com.syscolab.qe.core.reporting;

import com.google.common.base.CaseFormat;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.syscolab.qe.core.common.LoggerUtil;
import io.restassured.response.Response;
import com.syscolab.qe.core.api.restassured.RestUtil;
import com.syscolab.qe.core.api.util.Headers;
import com.syscolab.qe.core.api.util.RequestMethods;
import com.syscolab.qe.core.common.SyscoLabCoreConstants;
import com.thoughtworks.xstream.core.util.Base64Encoder;
import org.testng.ITestResult;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is the class for reporting related methods
 * @author  yoosufm on 6/7/17.
 */
public class SyscoLabReporting {

    private static String PATH =System.getProperty("user.dir") + "/Ill-FormattedTestCaseTitles.txt";
    private static final String STATUS = "status";
    private static final String KEYWORD = "keyword";

    /**
     * This will create a file named Ill-FormattedTestCaseTitles.txt to include
     * ill-formatted test titles in the file
     */
    public static void createIllFormattedTestTitlesFile(){
        try {
            File myObj = new File(PATH);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * This will resize the screenshot image
     * @param screenShotData screenshot data
     * @return resized screenshot data
     */
    public static byte[] resizeImage( byte[] screenShotData) {
        int height=0;
        int width=0;
        ByteArrayInputStream in = new ByteArrayInputStream(screenShotData);
        try {
            BufferedImage img = ImageIO.read(in);
            height = (int) (img.getHeight()*0.5);
            width  = (int) (img.getWidth()*0.5);

            if(height == 0) {
                height = (width * img.getHeight())/ img.getWidth();
            }
            if(width == 0) {
                width = (height * img.getWidth())/ img.getHeight();
            }

            Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage imageBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            imageBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0,0,0), null);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            ImageIO.write(imageBuff, "jpg", buffer);

            return buffer.toByteArray();
        } catch (IOException e) {
        }
        return null;
    }

    /**
     * This will return Json object for the reporting
     * @param iTestResult ITestResult instance
     * @param screenShotData screenshot data
     * @return element Json object
     * @throws IOException throwing IOException
     */
    public static JsonObject getElement(ITestResult iTestResult, byte[] screenShotData) throws IOException {
        boolean isScreenshotAvailable = !(screenShotData == null || screenShotData.length == 0);
        JsonObject element = new JsonObject();
        JsonArray steps = new JsonArray();
        JsonObject step = new JsonObject();
        JsonObject result = new JsonObject();
        String testTitle = "";
        String qmetryID = null;

            createIllFormattedTestTitlesFile();
            Writer output = new BufferedWriter(new FileWriter(PATH, true));
            try {
                qmetryID = generateTestCaseTitle(iTestResult,qmetryID);

                if(qmetryID==null){

                    if (iTestResult.getMethod().getDescription() == null) {
                        testTitle = iTestResult.getName();
                        ((BufferedWriter) output).newLine();
                        output.append(testTitle);
                    }
                    else {
                        testTitle = iTestResult.getMethod().getDescription();
                        ((BufferedWriter) output).newLine();
                        output.append(testTitle);
                    }
                }
                else {
                    String descriptionWithoutQMetryID = iTestResult.getMethod().getDescription().replace(qmetryID,"");
                    String descriptionFinal = descriptionWithoutQMetryID.replaceAll("([|]|[_])","");
                    descriptionFinal= descriptionFinal.replaceFirst("([:])","");

                    if(descriptionFinal==null || descriptionFinal.isEmpty()){
                        testTitle = qmetryID+" - "+iTestResult.getName();
                        ((BufferedWriter) output).newLine();
                        output.append(testTitle);
                    }
                    else {
                        String descriptionTag = iTestResult.getMethod().getDescription();
                        Pattern qmetryIdPatternNew = Pattern.compile("^(([A-Z]|[0-9])*+([-]|[_])+TC+([-]|[_])+\\b([0-9])*+(\\s[-]\\s)+([A-Z]|[a-z]|[0-9]|\\W).*$)");
                        Matcher matcherNew = qmetryIdPatternNew.matcher(descriptionTag);
                        Pattern qmetryIdPattern = Pattern.compile("^(([A-Z]|[0-9])*+([-]|[_])+\\\\b([0-9])*+(\\\\s[-]\\\\s)+([A-Z]|[a-z]|[0-9]|\\\\W).*$)");
                        Matcher matcher = qmetryIdPattern.matcher(descriptionTag);
                        if (matcherNew.find()){
                            testTitle= matcherNew.group(0);
                        }
                        else if(matcher.find()){
                            testTitle= matcher.group(0);
                        }
                        else {
                            descriptionFinal= descriptionFinal.replaceFirst("([:]|[|]|[_]|[-])","");
                            testTitle = qmetryID+ " - " + descriptionFinal;
                            ((BufferedWriter) output).newLine();
                            output.append(testTitle);
                        }
                    }
                }
            }finally {
                output.close();
            }


        element.addProperty("name", testTitle);
        element.addProperty("id",  iTestResult.getMethod().getDescription());
        element.addProperty("type", "scenario");
        element.addProperty(KEYWORD, "Test case");
        result.addProperty("duration", (iTestResult.getEndMillis() - iTestResult.getStartMillis()) * 1000 * 1000);
        if (iTestResult.getStatus() == 1) {
            result.addProperty(STATUS, "passed");
        }
        else if(iTestResult.getStatus() == 2){
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            iTestResult.getThrowable().printStackTrace(pw);
            result.addProperty("error_message", sw.toString());
            result.addProperty(STATUS, "failed");
            if(isScreenshotAvailable) {
                JsonArray embeddings = new JsonArray();
                JsonObject embed = new JsonObject();
                Base64Encoder encoder = new Base64Encoder();
                String screenShot = encoder.encode(resizeImage(screenShotData));
                String screenShotString_Base64 = screenShot.replace(System.lineSeparator(),"").replaceAll("\\s", "");
                embed.addProperty("data", screenShotString_Base64 );
                embed.addProperty("mime_type", "image/png");
                embeddings.add(embed);
                step.add("embeddings", embeddings);
            }
        }
        else if(iTestResult.getStatus() == 3){
            result.addProperty("error_message", "Skipped");
            result.addProperty(STATUS, "failed");
            if(isScreenshotAvailable) {
                JsonArray embeddings = new JsonArray();
                JsonObject embed = new JsonObject();
                Base64Encoder encoder = new Base64Encoder();

                embed.addProperty("data", encoder.encode(screenShotData));
                embed.addProperty("mime_type", "image/png");
                embeddings.add(embed);
                step.add("embeddings", embeddings);
            }
        }
        step.addProperty("name", "             ");
        step.addProperty(KEYWORD, "Duration");
        step.add("result", result);
        steps.add(step);
        element.add("steps", steps);
        return element;
    }

    /**
     * This will call  generate json file method
     * @param elements json array of elements
     * @param syscoQCenter SyscoLab QCenter instance
     */
    public static void generateJsonFile(JsonArray elements, SyscoLabQCenter syscoQCenter) {
        generateJsonFile(elements, syscoQCenter, -1);
    }

    /**
     * This will generate the json file for qCenter
     * @param elements json array of elements
     * @param syscoQCenter SyscoLabQCenter instance
     * @param durationInMillis duration in ms
     */
    public static void generateJsonFile(JsonArray elements, SyscoLabQCenter syscoQCenter, long durationInMillis) {
        JsonObject jsonReport = new JsonObject();

        jsonReport.add("elements", elements);
        jsonReport.addProperty("name", CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, syscoQCenter.getFeature()));
        jsonReport.addProperty("id", UUID.randomUUID().toString().split("-")[0]);
        jsonReport.addProperty(KEYWORD, "Suite");
        jsonReport.addProperty("description", syscoQCenter.getFeature());
        if (durationInMillis >= 0)
            jsonReport.addProperty("duration", String.valueOf((durationInMillis + 5000) * 1000 * 1000)); // Adding extra 5 seconds to compensate the setting up time, etc.

        jsonReport.addProperty("testClassName", syscoQCenter.getClassName());
        jsonReport.addProperty("node", System.getProperty("jenkins.node", "stag_cl2122"));
        jsonReport.addProperty("env", syscoQCenter.getEnvironment());
        jsonReport.addProperty("release", syscoQCenter.getRelease());
        jsonReport.addProperty("project", syscoQCenter.getProjectName());
        jsonReport.addProperty("branchName", syscoQCenter.getBranchName());
        jsonReport.addProperty("uri", syscoQCenter.getFeature().replace("-", "").replace("  ", " ").replace(" ", "_"));
        jsonReport.addProperty("testLayer", syscoQCenter.getTestLayer());

        try(FileWriter file = new FileWriter("./target/" + syscoQCenter.getFeature().replace("-", "").replace("  ", " ").replace(" ", "_") + ".json");) {

            file.write("[" + jsonReport.toString() + "]");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            RestUtil.API_HOST = SyscoLabCoreConstants.SYSCO_QCENTER_API_HOST;
            RestUtil.BASE_PATH = SyscoLabCoreConstants.SYSCO_QCENTER_API_BASE_PATH;
            RestUtil.PORT = SyscoLabCoreConstants.SYSCO_QCENTER_API_PORT;
            LoggerUtil.logINFO("\n\nREQUEST_URL\n" + RestUtil.API_HOST + RestUtil.BASE_PATH + ":" + RestUtil.PORT + "\n*********\n\n");
            Response response = RestUtil.send(Headers.getHeader(), jsonReport.toString(), "Automations", RequestMethods.POST.toString());
            LoggerUtil.logINFO(response.asString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This will identify and format the test case title to the standard format
     * @param iTestResult test result
     * @param qmetryID qMetry ID
     * @return standard format
     */
    public static String generateTestCaseTitle(ITestResult iTestResult,String qmetryID){
        if(iTestResult.getMethod().getDescription() == null){
            return qmetryID;
        }else {
            String descriptionTag = iTestResult.getMethod().getDescription();
            Pattern qmetryIdPatternNew = Pattern.compile("\\S(([A-Z]|[0-9])*+([-]|[_])+TC+([-]|[_])+\\b([0-9])*)");
            Matcher matcherNew = qmetryIdPatternNew.matcher(descriptionTag);
            Pattern qmetryIdPattern = Pattern.compile("\\S(([A-Z]|[0-9])*+([-]|[_])+\\b([0-9])*)");
            Matcher matcher = qmetryIdPattern.matcher(descriptionTag);
            if (matcherNew.find())
            {
                return matcherNew.group(0);
            }
            else if(matcher.find()){
                return matcher.group(0);
            }else {
                return null;
            }

        }
    }
}
