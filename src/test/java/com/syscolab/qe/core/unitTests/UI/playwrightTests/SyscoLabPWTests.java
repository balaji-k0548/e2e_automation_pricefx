package com.syscolab.qe.core.unitTests.UI.playwrightTests;

import com.syscolab.qe.core.playwright.ui.BaseBrowser;
import com.syscolab.qe.core.playwright.ui.SyscoLabPW;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static com.syscolab.qe.core.unitTests.common.UnitTestConstants.*;

/**
 * @author Kasun Herath
 */
public class SyscoLabPWTests extends BaseBrowser {
    //This is the test class used to write unit tests for SyscoLabPW class
    //A page which is the reference object gets created by SyscoLabPW page = getPage("Default"); which comes from BaseBrowser class
    //SyscoLabPW class has methods to interact with the page
    //SyscoLabPW class has methods to find elements in the page

    SyscoLabPW page;
    String dropDownElemId = "Skills";
    String dropDownElemXpath = "(//select[@id='Skills']/option)[4]";
    String chkBoxId = "checkbox1";
    String chkBoxElemXpath1 = "//input[@type='checkbox']";
    String chkBoxElemXpath2 = "//input[@id='checkbox1']";
    String chkBoxElemXpath3 = "//input[@id='checkbox3']";
    String txtNameXpath = "//div[@class='col-md-9 col-sm-12']/input";
    String txtNameIdXpath = "//p[@id='name']";
    String btnSubmit = "//button[@id='submit']";
    String tabTitle = "//ul[@class='nav navbar-nav']/li/a";
    String tabTitleIndex = "(//ul[@class='nav navbar-nav']/li/a)[1]";
    String btnId = "rightClickBtn";
    String btnXpath = "//p[@id='rightClickMessage']";
    String menuTutorial = "//a[@class='navbar__tutorial-menu']";
    String hoverItem1 = "(//div[@class='first-generation']/ul/li)[2]";
    String hoverSubListItem1 = "(//a[@title='Selenium in Java'])[2]";
    String tabFlyer = "(//img[@alt='Flyer'])[1]";
    String footerNewsRoom = "(//li[@class='d-none d-md-flex'])[5]";
    String googleSearch = "//textarea[@id='APjFqb']";
    String btnSearch = "(//input[@name='btnK'])[1]";
    String btnSearch2 = "(//input[@name='btnK'])[2]";
    String menuSwitchTo = "(//a[@href='SwitchTo.html'])[1]";
    String menuSwitchToSubItem = "((//a[@href='SwitchTo.html'])[1]/../ul/li)[1]";
    String btnDelayActivate = "//button[@id='enableAfter']";
    String txtResultClick = "//div[@id='result']";
    String uploadFileXpath = "//input[@id='uploadFile']";
    String uploadFileId = "uploadedFilePath";
    String btnDownloadXpath = "//a[@id='downloadButton']";
    String btnDownloadId = "downloadButton";
    String btnConfirmId = "confirmButton";
    String txtConfirmResId = "confirmResult";
    String imgPageTop = "//a[@href='https://demoqa.com']";

    //test findByAltText method
    @Test
    public void testFindByAltText() {
        page = getPage("Default");
        page.goTo(AUTOMATIONTESTINGWEBTABLE_URL);
        Assert.assertTrue(page.elemVisibility(page.findByAltText("image not displaying")), "Image not displaying");
    }


    @Test
    //With information provided create a test method to test clickDropDownNth method by creating a page and clicking on the 3rd drop down option
    public void testClickDropDownNth() {
        page = getPage("Default");
        page.goTo(AUTOMATIONTESTING_URL);
        page.clickDropDownNth(page.findById(dropDownElemId), 3);
        Assert.assertEquals(page.getText(page.findByLocator(dropDownElemXpath)), "Analytics", "Drop down value is not Analytics");
    }

    @Test
    //similar to above test clickDropDownNth method by creating a page and clicking on the 3rd drop down option passing a String parameter
    public void testClickDropDownNthString() {
        page = getPage("Default");
        page.goTo(AUTOMATIONTESTING_URL);
        page.clickDropDownNth(page.findById(dropDownElemId), "Analytics");
        Assert.assertEquals(page.getText(page.findByLocator(dropDownElemXpath)), "Analytics", "Drop down value is not Analytics");
    }

    @Test
    //similar to above test clickMultiElemNth method by creating a page and clicking on the 3rd element when multiple matches are found
    public void testClickMultiElemNth() {
        page = getPage("Default");
        page.goTo(AUTOMATIONTESTING_URL);
        page.clickMultiElemNth(page.findByLocator(chkBoxElemXpath1), 0);
        Assert.assertTrue(page.isChecked(page.findByLocator(chkBoxElemXpath2)), "Checkbox is not checked");
    }

    //test sendKeysMultiElemNth method
    @Test
    public void testSendKeysMultiElemNth() {
        page = getPage("Default");
        page.goTo(DEMOQA_URL1);
        page.sendKeysMultiElemNth(page.findByLocator(txtNameXpath), 0, "Unit Testing");
        page.click(btnSubmit);
        Assert.assertEquals(page.getText(page.findByLocator(txtNameIdXpath)), "Name:Unit Testing", "Name is not Unit Testing");
    }

    //test findMultiElemNth method
    @Test
    public void testFindMultiElemNth() {
        page = getPage("Default");
        page.goTo(AUTOMATIONTESTING_URL);
        Assert.assertEquals(page.getText(page.findMultiElemNth(page.findByLocator(tabTitle), 0)), "Home", "Title is not Home");
    }


    //test clickFirst method
    @Test
    public void testClickFirst() {
        page = getPage("Default");
        page.goTo(AUTOMATIONTESTING_URL);
        page.clickFirst(page.findByLocator(chkBoxElemXpath1));
        Assert.assertTrue(page.isChecked(page.findByLocator(chkBoxElemXpath2)), "Checkbox is not checked");
    }

    //test clickLast method
    @Test
    public void testClickLast() {
        page = getPage("Default");
        page.goTo(AUTOMATIONTESTING_URL);
        page.clickLast(page.findByLocator(chkBoxElemXpath1));
        Assert.assertTrue(page.isChecked(page.findByLocator(chkBoxElemXpath3)), "Checkbox is not checked");
    }

    //test clickFirst method
    @Test
    public void testClickFirstStringLocator() {
        page = getPage("Default");
        page.goTo(AUTOMATIONTESTING_URL);
        page.clickFirst(chkBoxElemXpath1);
        Assert.assertTrue(page.isChecked(page.findByLocator(chkBoxElemXpath2)), "Checkbox is not checked");
    }

    //test clickLast method
    @Test
    public void testClickLastStringLocator() {
        page = getPage("Default");
        page.goTo(AUTOMATIONTESTING_URL);
        page.clickLast(chkBoxElemXpath1);
        Assert.assertTrue(page.isChecked(page.findByLocator(chkBoxElemXpath3)), "Checkbox is not checked");
    }

    //test click method
    @Test
    public void testClick() {
        page = getPage("Default");
        page.goTo(AUTOMATIONTESTING_URL);
        page.click(page.findByLocator(chkBoxElemXpath2));
        Assert.assertTrue(page.isChecked(page.findByLocator(chkBoxElemXpath2)), "Checkbox is not checked");
    }



    //test click method with String locator
    @Test
    public void testClickStringLocator() {
        page = getPage("Default");
        page.goTo(AUTOMATIONTESTING_URL);
        page.click(chkBoxElemXpath2);
        Assert.assertTrue(page.isChecked(page.findByLocator(chkBoxElemXpath2)), "Checkbox is not checked");
    }

    //test clickById method
    @Test
    public void testClickById() {
        page = getPage("Default");
        page.goTo(AUTOMATIONTESTING_URL);
        page.clickById(chkBoxId);
        Assert.assertTrue(page.isChecked(page.findByLocator(chkBoxElemXpath2)), "Checkbox is not checked");
    }

    //test doubleClick method
    @Test
    public void testDoubleClick() {
        page = getPage("Default");
        page.goTo(AUTOMATIONTESTING_URL);
        page.doubleClick(page.findByLocator(chkBoxElemXpath2));
        Assert.assertFalse(page.isChecked(page.findByLocator(chkBoxElemXpath2)), "Checkbox is checked");
    }

    //test leftClick method
    @Test
    public void testLeftClick() {
        page = getPage("Default");
        page.goTo(AUTOMATIONTESTING_URL);
        page.leftClick(page.findByLocator(chkBoxElemXpath2));
        Assert.assertTrue(page.isChecked(page.findByLocator(chkBoxElemXpath2)), "Checkbox is not checked");
    }

    //test rightClick method
    @Test
    public void testRightClick() {
        page = getPage("Default");
        page.goTo(DEMOQA_URL2);
        page.rightClick(page.findById(btnId));
        page.waitFor(2000);
        Assert.assertEquals(page.getText(page.findByLocator(btnXpath)), "You have done a right click", "Button text is not You have done a right click");
    }


    //test middleClick method
    @Test
    public void testMiddleClick() {
        page = getPage("Default");
        page.goTo(AUTOMATIONTESTING_URL);
        page.middleClick(page.findByLocator(tabTitleIndex));
        page.waitFor(6000);
        page.switchToTab(1);
        //assert page title
        Assert.assertEquals(page.getTitle(), "Index", "Title is not Index");
    }

    //test forceClick method
    @Test
    public void testForceClick() {
        page = getPage("Default");
        page.goTo(AUTOMATIONTESTING_URL);
        page.forceClick(page.findByLocator(chkBoxElemXpath2));
        Assert.assertTrue(page.isChecked(page.findByLocator(chkBoxElemXpath2)), "Checkbox is not checked");
    }


    //test checkBoxClick method
    @Test
    public void testCheckBoxClick() {
        page = getPage("Default");
        page.goTo(AUTOMATIONTESTING_URL);
        page.checkBoxClick(page.findByLocator(chkBoxElemXpath2));
        Assert.assertTrue(page.isChecked(page.findByLocator(chkBoxElemXpath2)), "Checkbox is not checked");
    }

    //test mouseHover method
    @Test
    public void testMouseHover() {
        page = getPage("Default");
        page.goTo(TOOLQA_URL);
        page.click(page.findByLocator(menuTutorial));
        page.mouseHover(page.findByLocator(hoverItem1));
        page.waitFor(10000);
        Assert.assertEquals(page.getText(page.findByLocator(hoverSubListItem1)).contains("Java"), true, "Text does not contain Java");
    }


    //test goBack method
    @Test
    public void testGoBack() {
        page = getPage("Default");
        page.goTo(AUTOMATIONTESTING_URL);
        page.click(tabTitleIndex);
        page.goBack();
        Assert.assertEquals(page.getTitle(), "Register", "Title is not Register");
    }


    //test goForward method
    @Test
    public void testGoForward() {
        page = getPage("Default");
        page.goTo(AUTOMATIONTESTING_URL);
        page.click(tabTitleIndex);
        page.goBack();
        page.waitFor(2000);
        page.goForward();
        Assert.assertEquals(page.getTitle(), "Index", "Title is not Index");
    }

    //test switchTabOnClick method
    @Test
    public void testSwitchTabOnClick() {
        page = getPage("Default");
        page.goTo(DRUGMART_URL);
        //page.switchTabOnClick(page.findByLocator(tabFlyer));
        page.switchTabOnClick(page.findByLocator(tabFlyer));
        Assert.assertEquals(page.getTitle().contains("Flyer"), true, "Title does not contain Flyer");
    }


    @Test
    public void testSwitchToDefault() {
        page = getPage("Default");
        page.goTo(AUTOMATIONTESTING_URL);
        page.middleClick(page.findByLocator(tabTitleIndex));
        page.waitFor(6000);
        page.switchToTab(1);
        page.switchToDefault();
        //assert page title
        Assert.assertEquals(page.getTitle(), "Register", "Title is not Register");
    }

    /*
    //test refresh method
    @Test
    public void testRefresh() {
        page = getPage("Default");
        page.goTo(AUTOMATIONTESTING_URL);
        page.refresh();
        page.waitFor(20000);
        Assert.assertEquals(page.getTitle(), "Register");
    }
     */

    //test scrollBottom method
    @Test
    public void testScrollBottom() {
        page = getPage("Default");
        page.goTo(SYSCO_URL);
        page.scrollBottom();
        page.waitFor(5000);
        Assert.assertEquals(page.getText(page.findByLocator(footerNewsRoom)), "News Room", "Text is not News Room");
    }

    //test sendKeys method
    @Test
    public void testSendKeys() {
        page = getPage("Default");
        page.goTo(GOOGLE_URL);
        page.sendKeys(googleSearch, "Unit Testing");
        page.click(btnSearch);
        Assert.assertEquals(page.getText(page.findByLocator(googleSearch)), "Unit Testing", "Text is not Unit Testing");
    }

    //test typeInto method
    @Test
    public void testTypeInto() {
        page = getPage("Default");
        page.goTo(GOOGLE_URL);
        page.typeInto(page.findByLocator(googleSearch), "Unit Testing");
        page.click(btnSearch);
        Assert.assertEquals(page.getText(page.findByLocator(googleSearch)), "Unit Testing", "Text is not Unit Testing");
    }


    //test pressKeyBoardKey method
    @Test
    public void testPressKeyBoardKey() {
        page = getPage("Default");
        page.goTo(GOOGLE_URL);
        page.sendKeys(googleSearch, "Unit Testing");
        page.pressKeyBoardKey(page.findByLocator(googleSearch), "Control+A");
        page.pressKeyBoardKey("Backspace");
        page.sendKeys(googleSearch, "Selenium");
        page.pressKeyBoardKey("Enter");
        page.waitFor(5000);
        Assert.assertEquals(page.getText(page.findByLocator(googleSearch)), "Selenium", "Text is not Selenium");
    }

    //test pressKeyBoardKey method
    @Test
    public void testPressKeyBoardKey2() {
        page = getPage("Default");
        page.goTo(GOOGLE_URL);
        page.sendKeys(googleSearch, "Unit Testing");
        page.pressKeyBoardKey("Enter");
        page.waitFor(5000);
        Assert.assertEquals(page.getText(page.findByLocator(googleSearch)), "Unit Testing", "Text is not Unit Testing");
    }

    //test elemVisibility method
    @Test
    public void testElemVisibility() {
        page = getPage("Default");
        page.goTo(AUTOMATIONTESTINGWEBTABLE_URL);
        page.mouseHover(page.findByLocator(menuSwitchTo));
        Assert.assertEquals(page.elemVisibility(page.findByLocator(menuSwitchToSubItem)), true, "Element is not visible");
    }

    //test isChecked method
    @Test
    public void testIsChecked() {
        page = getPage("Default");
        page.goTo(AUTOMATIONTESTING_URL);
        page.click(chkBoxElemXpath2);
        Assert.assertEquals(page.isChecked(page.findByLocator(chkBoxElemXpath1), 0), true, "Checkbox is not checked");
    }

    //test isEnabled method
    @Test
    public void testIsEnabled() {
        page = getPage("Default");
        page.goTo(DEMOQA_URL3);
        page.waitFor(5500);
        Assert.assertEquals(page.isEnabled(page.findByLocator(btnDelayActivate)), true, "Button is not enabled");
    }

    //test executeJS method
    @Test
    public void testExecuteJS() {
        page = getPage("Default");
        page.goTo(DEMOQA_URL4);
        page.executeJS("document.getElementById('tree-node-home').click();");
        Assert.assertTrue(page.getText(page.findByLocator(txtResultClick)).contains("You have selected :"), "Text does not contain You have selected :");
    }

    @Test
    public void testSendKeysJS() {
        page = getPage("Default");
        page.goTo(GOOGLE_URL);
        page.sendKeysJS(googleSearch, "Unit Testing");
        page.click(btnSearch2);
        Assert.assertEquals(page.getText(page.findByLocator(googleSearch)), "Unit Testing", "Text is not Unit Testing");
    }

    @Test
    public void testClickJS() {
        page = getPage("Default");
        page.goTo(AUTOMATIONTESTING_URL);
        page.clickJS(chkBoxElemXpath2);
        Assert.assertTrue(page.isChecked(page.findByLocator(chkBoxElemXpath2)), "Checkbox is not checked");
    }

    //test fileUpload method
    @Test
    public void testFileUpload1() {
        page = getPage("Default");
        page.goTo(DEMOQA_URL5);
        page.fileUpload(uploadFileXpath, System.getProperty("user.dir") + "/src/test/resources/sampleText.txt");
        Assert.assertTrue(page.getText(page.findById(uploadFileId)).contains("sampleText"), "Text does not contain sampleText");
    }

    //test fileUpload method
    @Test
    public void testFileUpload2() {
        page = getPage("Default");
        page.goTo(DEMOQA_URL5);
        page.fileUpload(page.findByLocator(uploadFileXpath), System.getProperty("user.dir") + "/src/test/resources/sampleText.txt");
        Assert.assertTrue(page.getText(page.findById(uploadFileId)).contains("sampleText"), "Text does not contain sampleText");
    }


    //similar to above test fileDownload method
    @Test
    public void testFileDownload1() {
        page = getPage("Default");
        page.goTo(DEMOQA_URL5);
        page.fileDownload(btnDownloadXpath);
    }


    //similar to above test fileDownload method
    @Test
    public void testFileDownload2() {
        page = getPage("Default");
        page.goTo(DEMOQA_URL5);
        page.fileDownload(page.findById(btnDownloadId));
    }

    //test clickWithAlertAccept method
    @Test
    public void testClickWithAlertAccept() {
        page = getPage("Default");
        page.goTo(DEMOQA_URL6);
        page.clickWithAlertAccept(page.findById(btnConfirmId));
        Assert.assertEquals(page.getText(page.findById(txtConfirmResId)), "You selected Ok", "Text is not You selected Ok");
    }

    //test alertDismiss method
    @Test
    public void testAlertDismiss() {
        page = getPage("Default");
        page.goTo(DEMOQA_URL6);
        page.click(page.findById(btnConfirmId));
        page.alertDismiss();
        Assert.assertEquals(page.getText(page.findById(txtConfirmResId)), "You selected Cancel", "Text is not You selected Cancel");
    }

    //test screenShotPage method
    @Test
    public void testScreenShotPage() throws IOException {
        page = getPage("Default");
        page.goTo(DEMOQA_URL6);
        new File(System.getProperty("user.dir") + "/src/test/resources/screenShots/").mkdirs();
        page.screenShotPage(System.getProperty("user.dir") + "/" + "target");
        Assert.assertEquals(new File(System.getProperty("user.dir") + "/src/test/resources/screenShots/").listFiles().length, 1, "Screenshot is not taken");
    }

    //test screenShot method
    @Test
    public void testScreenShot() throws IOException {
        page = getPage("Default");
        page.goTo(DEMOQA_URL6);
        byte[] screenShotByte = page.screenShot();
        //assert that the byte array is not null
        Assert.assertNotNull(screenShotByte, "Screenshot is not taken");
    }

    //test screenShotElement method
    @Test
    public void testScreenShotElement() throws IOException {
        page = getPage("Default");
        page.goTo(DEMOQA_URL6);
        new File(System.getProperty("user.dir") + "/src/test/resources/screenShots/").mkdirs();
        page.screenShotElement(page.findByLocator(imgPageTop), System.getProperty("user.dir") + "/src/test/resources/screenShots");
        //Assert if the file is created in the directory by checking the size of the directory
        Assert.assertEquals(new File(System.getProperty("user.dir") + "/src/test/resources/screenShots/").listFiles().length, 1, "Screenshot is not taken");
    }

    //test abortMedia method
    @Test
    public void testAbortMedia() {
        page = getPage("Default");
        page.abortMedia();
        page.goTo(DEMOQA_URL1);
        page.resetMediaAbort();
        page.refresh();
        page.waitFor(15000);
        //check no images are loaded on this webpage
        //Assert.assertEquals(page.findByLocator("//img"), 0);
    }


    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {
        page.close();
    }

    @AfterTest(alwaysRun = true)
    public void deleteScreenShotDir() throws IOException {
        FileUtils.deleteDirectory(new File(System.getProperty("user.dir") + "/src/test/resources/screenShots"));
    }


}
