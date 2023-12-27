package com.syscolab.qe.core.unitTests.UI.playwrightTests;

import com.syscolab.qe.core.playwright.ui.BaseBrowser;
import com.syscolab.qe.core.playwright.ui.SyscoLabPW;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import java.io.IOException;
import static com.syscolab.qe.core.unitTests.common.UnitTestConstants.*;

/**
 * @author Kasun Herath
 */
public class BaseBrowserTests extends BaseBrowser {
    //This test class is used to write tests for BaseBrowser class
    //BaseBrowser class is used to create a page with a browser and a context given as a String parameter to getPage() method
    //getPage() method is used to create a Browser page with SYSTEM_BROWSER1 stored at PlayWrightConstants.SYSTEM_BROWSER1
    //getPageFromExtraBrowser() method is used to create a Browser page with SYSTEM_BROWSER2 stored at PlayWrightConstants.SYSTEM_BROWSER2
    //both getPage() and getPageFromExtraBrowser() methods use retrievePage() method to create a page
    //pages that gets created are of SyscoLabPW type
    //BaseBrowser class also had methods named storeSessionInfo() and retrieveSessionInfo() which are not used in the project to store and reuse cookie info of a page
    //BaseBrowser class also has methods named traceStart() and traceStop() which used to generate a trace file of .zip format
    //Depending on above context create a test method to test each method in BaseBrowser class
    //Example:Page is created by SyscoLabPW page = getPage("Default");

    SyscoLabPW page;

    String inputUsrName = "Username";
    String inputPassword = "Password";
    String btnSubmit = "//button[@type='submit']";

    @Test
    public void testGetPage() {
        page = getPage("Default");
        page.goTo(SYSCO_URL);
        Assert.assertEquals(page.getTitle(), "Sysco | Wholesale Restaurant Food Distributor", "Page title is not correct");
    }

    @Test
    //test getPageFromExtraBrowser method
    public void testGetPageFromExtraBrowser() {
        page = getPageFromExtraBrowser("Default");
        page.goTo(SYSCO_URL);
        Assert.assertEquals(page.getTitle(), "Sysco | Wholesale Restaurant Food Distributor", "Page title is not correct");
    }

/*
    @Test
    //test storeSessionInfo method for a different url
    public void testStoreSessionInfoBrowser1() throws IOException {
        page = getPage("Default");
        page.goTo(ORANGEHRM_URL);
        page.sendKeys(page.findByPlaceHolder(inputUsrName), "Admin");
        page.sendKeys(page.findByPlaceHolder(inputPassword), "admin123");
        page.click(page.findById(btnSubmit));
        storeSessionInfo("src/test/resources/loginInfo/", "orangeHRM", getContextsBrowser1("Default"));
        Assert.assertEquals(page.getTitle(), "OrangeHRM", "Page title is not correct");
    }
 */


    @Test
    //test sessionReUse() method
    public void testSessionReUseBrowser1() throws IOException {
        page = getPage("Default");
        page.goTo(ORANGEHRM_URL);
        page.sendKeys(page.findByPlaceHolder(inputUsrName), "Admin");
        page.sendKeys(page.findByPlaceHolder(inputPassword), "admin123");
        page.click(page.findByLocator(btnSubmit));
        storeSessionInfo("src/test/resources/loginInfo/", "orangeHRM", getContextsBrowser1("Default"));
        page.close();
        page = getPage("Default");
        sessionReUse("src/test/resources/loginInfo/", "orangeHRM", getContextsBrowser1("Default"));
        page.goTo(ORANGEHRM_URL);
        Assert.assertEquals(page.getTitle(), "OrangeHRM", "Page title is not correct");
    }


/*
    //test storeSessionInfo method for Browser2
    @Test
    public void testStoreSessionInfoBrowser2() throws IOException {
        page = getPageFromExtraBrowser("Default");
        page.goTo(ORANGEHRM_URL);
        page.sendKeys(page.findByPlaceHolder(inputUsrName), "Admin");
        page.sendKeys(page.findByPlaceHolder(inputPassword), "admin123");
        page.click(page.findByLocator("//button[@type='submit']"));
        storeSessionInfo("src/test/resources/loginInfo/", "orangeHRM", getContextsBrowser2("Default"));
        Assert.assertEquals(page.getTitle(), "OrangeHRM", "Page title is not correct");
    }

 */


/*
    //test sessionReUse() method for Browser2
    @Test
    public void testSessionReUseBrowser2() throws IOException {
        page = getPageFromExtraBrowser("Default");
        page.goTo(ORANGEHRM_URL);
        page.sendKeys(page.findByPlaceHolder(inputUsrName), "Admin");
        page.sendKeys(page.findByPlaceHolder(inputPassword), "admin123");
        page.click(page.findByLocator("//button[@type='submit']"));
        storeSessionInfo("src/test/resources/loginInfo/", "orangeHRM", getContextsBrowser2("Default"));
        page.close();
        page = getPageFromExtraBrowser("Default");
        sessionReUse("src/test/resources/loginInfo/", "orangeHRM", getContextsBrowser2("Default"));
        page.goTo(ORANGEHRM_URL);
        Assert.assertEquals(page.getTitle(), "OrangeHRM", "Page title is not correct");
    }
 */


    //test deleteSessionInfo() method which deletes the file created by storeSessionInfo() method
    @Test
    public void testDeleteSessionInfo() throws IOException {
        page = getPageFromExtraBrowser("Default");
        page.goTo(ORANGEHRM_URL);
        page.sendKeys(page.findByPlaceHolder(inputUsrName), "Admin");
        page.sendKeys(page.findByPlaceHolder(inputPassword), "admin123");
        page.click(page.findByLocator("//button[@type='submit']"));
        storeSessionInfo("src/test/resources/loginInfo/", "orangeHRM", getContextsBrowser2("Default"));
        deleteSession("src/test/resources/loginInfo/", "orangeHRM");
        //check orangeHRM file is deleted using java.io.File class
        Assert.assertFalse(new java.io.File("src/test/resources/loginInfo/orangeHRM").exists(), "File is not deleted");
    }

    //test Tracing() methods for Browser1
    @Test
    public void testTracingBrowser1() {
        page = getPage("NewContext");
        startTracing("NewContext");
        page.goTo(ORANGEHRM_URL);
        page.sendKeys(page.findByPlaceHolder(inputUsrName), "Admin");
        page.sendKeys(page.findByPlaceHolder(inputPassword), "admin123");
        page.click(page.findByLocator(btnSubmit));
        page.waitFor(5000);
        stopTracing("NewContext");
        //check trace file is created using java.io.File class
        Assert.assertTrue(new java.io.File("traceResults.zip").exists(), "Trace file is not created");
    }

    //test Tracing() methods for Browser2
    @Test
    public void testTracingBrowser2() {
        page = getPageFromExtraBrowser("NewContext");
        startTracingExtraBrowser("NewContext");
        page.goTo(ORANGEHRM_URL);
        page.sendKeys(page.findByPlaceHolder(inputUsrName), "Admin");
        page.sendKeys(page.findByPlaceHolder(inputPassword), "admin123");
        page.click(page.findByLocator(btnSubmit));
        page.waitFor(5000);
        stopTracingExtraBrowser("NewContext");
        //check trace file is created using java.io.File class
        Assert.assertTrue(new java.io.File("traceResults.zip").exists(), "Trace file is not created");
    }

    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {
        page.close();
    }
}
