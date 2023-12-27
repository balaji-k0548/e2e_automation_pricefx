package com.syscolab.qe.core.unitTests.UI.playwrightTests;

import com.syscolab.qe.core.playwright.ui.BaseBrowser;
import com.syscolab.qe.core.playwright.ui.SyscoLabPW;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static com.syscolab.qe.core.unitTests.common.UnitTestConstants.*;

/**
 * @author Kasun Herath
 */
public class SyscoLabPWFrameTests extends BaseBrowser {
    //This test class is used to write tests for the SyscoLabPWFrame class
    //SyscoLabPWFrame class is used to handle the iFrame related actions
    //SyscoLabPWFrame class is extending SyscoLabPW class and handling the iFrame related actions

    //with the information above create a test method to test switchToFrameByName method

    SyscoLabPW page;
    String frameTitleXpath = "//body/h1";
    String loginBtnText = "//a[@id='w3loginbtn']";
    String menuDropDXpath = "(//a[@title='Menu'])[1]";
    String tutorialElemXpath = "//div[@class='w3-container']/a";
    String tutorialTitleText = "(//h2/b)[1]";
    String frameURL = "https://www.hyrtutorials.com/p/html-dropdown-elements-practice.html";
    String frameCourseText = "(//label[@class='inp'])[1]";
    String frameSearchBtn = "//a[@title='Search W3Schools']";
    String frameSearchText = "//input[@name='search']";
    String frameSearchBtn2 = "//td[@class='gsc-search-button']/button";
    String frameSearchResults = "//div[@class='gsc-result-info']";
    String childFrameElem = "(//div[@id='btn_container_subtopnav']/a)[1]";
    String childFrameLabel = "//label[@for='radio_darkpage']";
    String childFrameDropD = "//select[@id='selectnav2']";
    String childFrameTitleDropD = "//h1[@class='post-title entry-title']";
    String childFrameCheckBoxID = "//input[@id='radio_darkpage']";

    @Test
    public void testSwitchToFrameByName() {
        page = getPage("Default");
        page.goTo(IFRAME_URL);
        page.switchToFrameByName("iframeResult");
        Assert.assertEquals(page.frameGetText(frameTitleXpath), "The iframe element");
    }

    //test switchToFrameByURL method
    @Test
    public void testSwitchToFrameByURL() {
        page = getPage("Default");
        page.goTo(IFRAME_URL2);
        page.switchToFrameByURL(frameURL);
        Assert.assertEquals(page.frameGetText(frameCourseText), "Course Name:");
    }


    //similar to above test switchToFrameByIndex method
    @Test
    public void testSwitchToChildFrame() {
        page = getPage("Default");
        page.goTo(IFRAME_URL);
        page.switchToChildFrame("iframeResult", 0);
        Assert.assertEquals(page.frameGetText(loginBtnText), "Log in");
    }

    //test frameMouseHover method
    @Test
    public void testFrameMouseHover() {
        page = getPage("Default");
        page.goTo(IFRAME_URL);
        page.switchToChildFrame("iframeResult", 0);
        page.frameMouseHover(childFrameElem);
        Assert.assertTrue(page.frameIsDisplayed(childFrameLabel));

    }

    //test frameSendKeys method
    @Test
    public void testFrameSendKeys() {
        page = getPage("Default");
        page.goTo(IFRAME_URL);
        page.switchToChildFrame("iframeResult", 0);
        page.frameClick(frameSearchBtn);
        page.frameSendKeys(frameSearchText, "UniTTests");
        page.frameClick(frameSearchBtn2);
        Assert.assertTrue(page.frameGetText(frameSearchResults).contains("About"));
    }

    //test frameClickDropDownNth method
    @Test
    public void testFrameClickDropDownNth() {
        page = getPage("Default");
        page.goTo(IFRAME_URL2);
        page.switchToFrameByURL(IFRAME_URL3);
        page.frameClickDropDownNth(childFrameDropD, 2);
        Assert.assertTrue(page.frameGetText(childFrameTitleDropD).contains("HTML Dropdowns"));
    }

    //test frameClickDropDownNth method
    @Test
    public void testClickMultiElemNth() {
        page = getPage("Default");
        page.goTo(IFRAME_URL);
        page.switchToChildFrame("iframeResult", 0);
        page.frameClick(menuDropDXpath);
        page.frameClickMultiElemNth(tutorialElemXpath, 0);
        Assert.assertEquals(page.frameGetText(tutorialTitleText), "Tutorials and References");
    }

    //test frameCheckBoxClick method
    @Test
    public void testFrameCheckBoxClick() {
        page = getPage("Default");
        page.goTo(IFRAME_URL);
        page.switchToChildFrame("iframeResult", 0);
        page.frameMouseHover(childFrameElem);
        page.frameCheckBoxClick(childFrameCheckBoxID);
        Assert.assertTrue(page.frameIsChecked(childFrameCheckBoxID));

    }


    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {
        page.close();
    }
}
