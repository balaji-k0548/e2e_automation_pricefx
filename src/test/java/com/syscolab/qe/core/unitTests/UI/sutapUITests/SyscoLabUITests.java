package com.syscolab.qe.core.unitTests.UI.sutapUITests;

import com.syscolab.qe.core.ui.SyscoLabUI;
import com.syscolab.qe.core.ui.common.SyscoLabBrowserTypes;
import com.syscolab.qe.core.ui.web.SyscoLabWUI;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.awt.*;

import static com.syscolab.qe.core.unitTests.common.UnitTestConstants.*;

/**
 * @author Kasun Herath
 */
public class SyscoLabUITests {
    //This is the test class for SyscoLabUI class
    //This class will test the methods in SyscoLabUI class
    //A SyscoLabUI type object is created to interact with the methods in SyscoLabUI class
    //SyscoLabUI is created with the browser type as the parameter
    //SyscoLabUI syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME); is an example of creating a SyscoLabUI object

    ChromeOptions chromeOptions;
    SyscoLabUI syscoLabUI;

    By googleBtn = By.xpath("(//input[@name='btnI'])[2]");
    By demoQaLeftPanXpath = By.xpath("//div[@class='accordion']");
    By demoQaMenuList = By.xpath("//span[@class='text']");
    By menuItemButton = By.xpath("//li[@id='item-4']");
    By googleSearchText = By.id("APjFqb");
    By googleSearchBtn = By.xpath("//div[@jsname='VlcLAe']//input[@name='btnK']");
    By googleSearchBtn3 = By.xpath("//span[@class='z1asCe MZy1Rb']");
    By checkBox = By.xpath("//span[@class='rct-checkbox']");
    By headerText = By.xpath("(//div[@class='header-text'])[1]");
    By textCount = By.xpath("//span[@class='text']");
    By headerText2 = By.xpath("(//span[@class='group-header'])[1]");
    By menuItem = By.xpath("(//ul[@class='menu-list']/li)[1]");
    By headerText3 = By.xpath("//div[@class='main-header']");
    By enableAfter = By.xpath("//button[@id='enableAfter']");
    By menuItem2 = By.xpath("//select[@id='oldSelectMenu']");
    By expandAllBtn = By.xpath("//button[@title='Expand all']");
    By visibleBtn = By.xpath("//button[@id='visibleAfter']");
    By btnSave = By.xpath("//button[@id='save']");
    By imgDissapear = By.xpath("//img[@src='at_spinner.gif']");
    By genderRadio = By.xpath("//input[@id='gender-radio-1']");
    By twitterLogo = By.xpath("//img[@alt='Twitter logo']");
    By pageHeader = By.xpath("//div[@id='app']/header/a");
    By footPanel = By.xpath("//div[@id='footnav']");
    By imgBtn=By.xpath("//img[@id='enterimg']");
    By leftPaneMenu = By.xpath("//a[@class='navbar__tutorial-menu']");
    By leftPaneMenuItem = By.xpath("(//div[@class='first-generation']/ul/li)[2]");
    By menuSubItem = By.xpath("(//a[@title='Selenium in Java'])[2]");
    By switchTabBtn = By.xpath("//button[@id='tabButton']");
    By pageHeader2 = By.xpath("//h1[@id='sampleHeading']");
    By switchWindowBtn = By.xpath("//button[@id='windowButton']");
    By alertBtn = By.xpath("//button[@id='alertButton']");
    By alertConfirmBtn = By.xpath("//button[@id='confirmButton']");
    By alertConfirmText = By.xpath("//span[@id='confirmResult']");
    By draggable = By.xpath("//div[@id='draggable']");


    @BeforeTest
    public void setRunMode() {
        chromeOptions = new ChromeOptions();
        chromeOptions.addArguments(System.getProperty("SeleniumRunMode"));
    }

    @Test
    public void testNavigateTo() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(GOOGLE_URL);
        Assert.assertEquals(syscoLabUI.getTitle(), "Google");
    }

    //test findElement method
    @Test
    public void testFindElement() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(GOOGLE_URL);
        Assert.assertEquals(syscoLabUI.findElement(googleBtn).getAttribute("value"), "I'm Feeling Lucky");
    }

    //test findElements method
    @Test
    public void testFindElements() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL1);
        WebElement demoQaLeftPan = syscoLabUI.findElement(demoQaLeftPanXpath);
        Assert.assertTrue(syscoLabUI.findElements(demoQaLeftPan, demoQaMenuList).size() > 0);
    }

    //test findElement method similar to above testFindElements method
    @Test
    public void testFindElementWithTimeOut() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL1);
        WebElement demoQaLeftPan = syscoLabUI.findElement(demoQaLeftPanXpath);
        Assert.assertEquals(syscoLabUI.findElement(demoQaLeftPan, menuItemButton).getText(), "Buttons");
    }

    //test findElement method similar to above testFindElements method
    @Test
    public void testFindElementWithBy() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL1);
        By demoQaLeftPan = demoQaLeftPanXpath;
        Assert.assertEquals(syscoLabUI.findElement(demoQaLeftPan, menuItemButton).getText(), "Buttons");
    }

    //test findVisibleElement method
    @Test
    public void testFindVisibleElement() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL1);
        Assert.assertEquals(syscoLabUI.findVisibleElement(headerText).getText(), "Elements");
    }

    //test findVisibleElements method
    @Test
    public void testFindVisibleElements() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL1);
        Assert.assertTrue(syscoLabUI.findVisibleElements(textCount).size() > 0);
    }

    //test sendKeys method
    @Test
    public void testSendKeysBy() throws InterruptedException {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(GOOGLE_URL);
        syscoLabUI.sendKeys(googleSearchText, "Test");
        syscoLabUI.clickWithJavascript(googleSearchBtn);
        Assert.assertEquals(syscoLabUI.findElement(googleSearchText).getText(), "Test");
    }

    //test sendKeys method
    @Test
    public void testSendKeysWebElement() throws InterruptedException {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(GOOGLE_URL);
        WebElement element = syscoLabUI.findElement(googleSearchText);
        syscoLabUI.sendKeys(element, "Test");
        syscoLabUI.clickWithJavascript(googleSearchBtn);
        Assert.assertEquals(syscoLabUI.findElement(googleSearchText).getText(), "Test");
    }

    //test sendKeys method
    @Test
    public void testSendKeysPressableKeyBy() throws InterruptedException {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(GOOGLE_URL);
        syscoLabUI.sendKeys(googleSearchText, "Test");
        syscoLabUI.sendKeys(googleSearchText, Keys.ENTER);
        Assert.assertEquals(syscoLabUI.findElement(googleSearchText).getText(), "Test");
    }

    //test sendKeys method
    @Test
    public void testSendKeysPressableKeyWebElement() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(GOOGLE_URL);
        syscoLabUI.sendKeys(googleSearchText, "Test");
        WebElement element = syscoLabUI.findElement(googleSearchText);
        syscoLabUI.sendKeys(element, Keys.ENTER);
        Assert.assertEquals(syscoLabUI.findElement(googleSearchText).getText(), "Test");
    }

    //test sendKeysWithoutClear method
    @Test
    public void testSendKeysWithoutClearWebElement() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(GOOGLE_URL);
        WebElement element = syscoLabUI.findElement(googleSearchText);
        syscoLabUI.sendKeys(element, "Test");
        syscoLabUI.sendKeysWithoutClear(element, " Appended without clear");
        syscoLabUI.clickWithJavascript(googleSearchBtn);
        Assert.assertEquals(syscoLabUI.findElement(googleSearchText).getText(), "Test Appended without clear");
    }

    //test sendKeysWithoutClear method
    @Test
    public void testSendKeysWithoutClearBy() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(GOOGLE_URL);
        syscoLabUI.sendKeys(googleSearchText, "Test");
        syscoLabUI.sendKeysWithoutClear(googleSearchText, " Appended without clear");
        syscoLabUI.clickWithJavascript(googleSearchBtn);
        Assert.assertEquals(syscoLabUI.findElement(googleSearchText).getText(), "Test Appended without clear");
    }

    //test sendKeysInScript method
    @Test
    public void testSendKeysInScriptBy() throws InterruptedException {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(GOOGLE_URL);
        syscoLabUI.sendKeysInScript(googleSearchText, "Test");

        syscoLabUI.clickWithJavascript(googleSearchBtn);
        Assert.assertEquals(syscoLabUI.findElement(googleSearchText).getText(), "Test");
    }

    //test sendKeysInScript method
    @Test
    public void testSendKeysInScriptWebElement() throws InterruptedException {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(GOOGLE_URL);
        WebElement element = syscoLabUI.findElement(googleSearchText);
        syscoLabUI.sendKeysInScript(element, "Test");
        syscoLabUI.clickWithJavascript(googleSearchBtn);
        Assert.assertEquals(syscoLabUI.findElement(googleSearchText).getText(), "Test");
    }


    //test clear method
    @Test
    public void testClearWebElement() throws InterruptedException {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(GOOGLE_URL);
        syscoLabUI.sendKeys(googleSearchText, "Test");
        syscoLabUI.clickWithJavascript(googleSearchBtn);
        WebElement element = syscoLabUI.findElement(googleSearchText);
        syscoLabUI.clear(element);
        syscoLabUI.sendKeys(googleSearchText, "Cleared");
        syscoLabUI.click(googleSearchBtn3);
        Assert.assertEquals(syscoLabUI.findElement(googleSearchText).getText(), "Cleared");
    }

    //test clear method
    @Test
    public void testClearBy() throws InterruptedException {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(GOOGLE_URL);
        syscoLabUI.sendKeys(googleSearchText, "Test");
        syscoLabUI.clickWithJavascript(googleSearchBtn);
        syscoLabUI.clear(googleSearchText);
        syscoLabUI.sendKeys(googleSearchText, "Cleared");
        syscoLabUI.click(googleSearchBtn3);
        Assert.assertEquals(syscoLabUI.findElement(googleSearchText).getText(), "Cleared");
    }

    //test clearWithBackSpace method
    @Test
    public void testClearWithBackSpaceBy() throws InterruptedException {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(GOOGLE_URL);
        syscoLabUI.sendKeys(googleSearchText, "Test");
        syscoLabUI.clickWithJavascript(googleSearchBtn);
        syscoLabUI.clearWithBackSpace(googleSearchText, 2);
        syscoLabUI.click(googleSearchBtn3);
        Assert.assertEquals(syscoLabUI.findElement(googleSearchText).getText(), "Te");
    }

    //test clearWithBackSpace method
    @Test
    public void testClearWithBackSpaceWebElement() throws InterruptedException {

        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(GOOGLE_URL);
        syscoLabUI.sendKeys(googleSearchText, "Test");
        syscoLabUI.clickWithJavascript(googleSearchBtn);
        WebElement element = syscoLabUI.findElement(googleSearchText);
        syscoLabUI.clearWithBackSpace(element, 2);
        syscoLabUI.click(googleSearchBtn3);
        Assert.assertEquals(syscoLabUI.findElement(googleSearchText).getText(), "Te");
    }

    //test doubleClick method
    @Test
    public void testDoubleClickBy() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL4);
        WebElement element = syscoLabUI.findElement(checkBox);
        syscoLabUI.doubleClick(element);
        Assert.assertTrue(!syscoLabUI.findElement(checkBox).isSelected());
    }

    //test clickOnVisibleElement method
    @Test
    public void testClickOnVisibleElementBy() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL6);
        syscoLabUI.click(headerText2);
        syscoLabUI.clickOnVisibleElement(menuItem);
        Assert.assertEquals(syscoLabUI.findElement(headerText3).getText(), "Text Box");
    }

    //test getText method
    @Test
    public void testGetTextBy() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL1);
        Assert.assertEquals(syscoLabUI.getText(headerText3), "Text Box");
    }

    //test getInvisibleText method
    @Test
    public void testGetInvisibleTextBy() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL1);
        Assert.assertEquals(syscoLabUI.getInvisibleText(headerText3), "Text Box");
    }

    //test getFirstSelectedOptionText method
    @Test
    public void testGetFirstSelectedOptionBy() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL7);
        Assert.assertEquals(syscoLabUI.getFirstSelectedOptionText(menuItem2), "Red");
    }

    //test getFirstSelectedOptionText method
    @Test
    public void testGetFirstSelectedOptionWebElement() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL7);
        WebElement element = syscoLabUI.findElement(menuItem2);
        Assert.assertEquals(syscoLabUI.getFirstSelectedOptionText(element), "Red");
    }

    //test isDisplayed method
    @Test
    public void testIsDisplayedWebElement() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL1);
        WebElement element = syscoLabUI.findElement(headerText3);
        //Assert that the element is displayed
        Assert.assertTrue(syscoLabUI.isDisplayed(element));
    }

    //test isDisplayed method
    @Test
    public void testIsDisplayedBy() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL1);
        //Assert that the element is displayed
        Assert.assertTrue(syscoLabUI.isDisplayed(headerText3));
    }

    //test isEnabled method
    @Test
    public void testIsEnabledWebElement() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL4);
        //Assert that another element is enabled
        Assert.assertTrue(syscoLabUI.isEnabled(expandAllBtn));
    }

    //test isClickable method
    @Test
    public void testIsClickableBy() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL7);
        //Assert that another element is clickable
        Assert.assertTrue(syscoLabUI.isClickable(menuItem2));
    }

    //test isClickable method
    @Test
    public void testIsClickableWebElement() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL7);
        WebElement element = syscoLabUI.findElement(menuItem2);
        //Assert that another element is clickable
        Assert.assertTrue(syscoLabUI.isClickable(element));
    }

    //test navigateBack method
    @Test
    public void testNavigateBack() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(GOOGLE_URL);
        syscoLabUI.navigateTo(DEMOQA_URL1);
        syscoLabUI.navigateBack();
        Assert.assertEquals(syscoLabUI.getCurrentURL(), GOOGLE_URL);
    }

    //test refreshBrowser method
    @Test
    public void testRefreshBrowser() throws InterruptedException {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL3);
        Thread.sleep(5500);
        syscoLabUI.refreshBrowser();
        Assert.assertTrue(!syscoLabUI.findElement(enableAfter).isEnabled());
    }

    //test select method
    @Test
    public void testSelect() throws InterruptedException {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL7);
        syscoLabUI.select(menuItem2, "Blue");
        //assert that blue is selected
        Assert.assertEquals(syscoLabUI.getFirstSelectedOptionText(menuItem2), "Blue");
    }

    //test waitTillElementLoaded method
    @Test
    public void testWaitTillElementLoadedByTimeOut() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL3);
        syscoLabUI.waitTillElementLoaded(visibleBtn, 7);
        //assert that the element is enabled
        Assert.assertTrue(syscoLabUI.isEnabled(visibleBtn));
    }

    //test waitTillElementLoaded method
    @Test
    public void testWaitTillElementLoadedBy() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL3);
        syscoLabUI.waitTillElementLoaded(visibleBtn);
        //assert that the element is enabled
        Assert.assertTrue(syscoLabUI.isEnabled(visibleBtn));
    }

    //test waitTillElementLoaded method
    @Test
    public void testWaitTillElementLoadedWebElement() throws InterruptedException {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL3);
        Thread.sleep(6000);
        WebElement element = syscoLabUI.findElement(visibleBtn);
        syscoLabUI.waitTillElementLoaded(element);
        //assert that the element is enabled
        Assert.assertTrue(syscoLabUI.isEnabled(visibleBtn));
    }

    //test isSelected method for different url
    @Test
    public void testIsSelectedBy() throws InterruptedException {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL7);
        syscoLabUI.select(menuItem2, "Blue");
        //assert that blue is selected
        Assert.assertTrue(syscoLabUI.isSelected(By.xpath("//select[@id='oldSelectMenu']/option[@value='1']")));
    }

    //test isSelected method
    @Test
    public void testIsSelectedWebElement() throws InterruptedException {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL7);
        syscoLabUI.select(menuItem2, "Blue");
        WebElement element = syscoLabUI.findElement(By.xpath("//select[@id='oldSelectMenu']/option[@value='1']"));
        //assert that blue is selected
        Assert.assertTrue(syscoLabUI.isSelected(element));
    }

    //test waitTillElementDisappear method
    @Test
    public void testWaitTillElementDisappearBy() throws InterruptedException {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DYNAMIC_URL);
        syscoLabUI.click(btnSave);
        syscoLabUI.waitTillElementDisappear(imgDissapear);
        //assert that blue is not selected
        Assert.assertTrue(syscoLabUI.waitTillElementDisappear(imgDissapear));
    }

    //test waitTillElementDisappear method
    @Test
    public void testWaitTillElementDisappeared() throws InterruptedException {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DYNAMIC_URL);
        syscoLabUI.click(btnSave);
        syscoLabUI.waitTillElementDisappeared(imgDissapear, 5);
        //assert that blue is not selected
        Assert.assertTrue(syscoLabUI.waitTillElementDisappear(imgDissapear));
    }

    //test getAttribute method
    @Test
    public void testGetAttributeBy() throws InterruptedException {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL7);
        syscoLabUI.select(menuItem2, "Blue");
        //assert that blue is selected
        Assert.assertEquals(syscoLabUI.getAttribute(By.xpath("//select[@id='oldSelectMenu']/option[@value='1']"), "value"), "1");
    }


    //test getAttribute method
    @Test
    public void testGetAttributeWebElement() throws InterruptedException {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL7);
        syscoLabUI.select(menuItem2, "Blue");
        WebElement element = syscoLabUI.findElement(By.xpath("//select[@id='oldSelectMenu']/option[@value='1']"));
        //assert that blue is selected
        Assert.assertEquals(syscoLabUI.getAttribute(element, "value"), "1");
    }

    //test getCssValue method
    @Test
    public void testGetCssValueBy() throws InterruptedException {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL7);
        syscoLabUI.select(menuItem2, "Blue");
        //assert that blue is selected
        Assert.assertEquals(syscoLabUI.getCssValue(By.xpath("//select[@id='oldSelectMenu']/option[@value='1']"), "color"), "rgba(0, 0, 0, 1)");
    }

    // test getCssValue method
    @Test
    public void testGetCssValueWebElement() throws InterruptedException {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL7);
        syscoLabUI.select(menuItem2, "Blue");
        WebElement element = syscoLabUI.findElement(By.xpath("//select[@id='oldSelectMenu']/option[@value='1']"));
        //assert that blue is selected
        Assert.assertEquals(syscoLabUI.getCssValue(element, "color"), "rgba(0, 0, 0, 1)");
    }

    //test getValue method
    @Test
    public void testGetValueBy() throws InterruptedException {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL8);
        Assert.assertEquals(syscoLabUI.getValue(genderRadio), "Male");
    }

    //test getValue method
    @Test
    public void testGetValueWebElement() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL8);
        WebElement element = syscoLabUI.findElement(genderRadio);
        Assert.assertEquals(syscoLabUI.getValue(element), "Male");
    }

    //test selectFromDropDown method
    @Test
    public void testSelectFromDropDownBy() throws InterruptedException {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL7);
        syscoLabUI.selectFromDropDown(menuItem2, "Blue");
        //assert that blue is selected
        Assert.assertTrue(syscoLabUI.isSelected(By.xpath("//select[@id='oldSelectMenu']/option[@value='1']")));
    }

    //test selectFromDropDown method
    @Test
    public void testSelectFromDropDownWebElement() throws InterruptedException {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL7);
        WebElement element = syscoLabUI.findElement(menuItem2);
        syscoLabUI.selectFromDropDown(element, "Blue");
        //assert that blue is selected
        Assert.assertTrue(syscoLabUI.isSelected(By.xpath("//select[@id='oldSelectMenu']/option[@value='1']")));
    }

    //test scrollBottom method
    @Test
    public void testScrollBottom() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DRUGMART_URL);
        syscoLabUI.scrollBottom();
        //assert that bottom of the page is reached
        Assert.assertTrue(syscoLabUI.isDisplayed(twitterLogo));
    }

    //test scrollUp method
    @Test
    public void testScrollUp() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL1);
        syscoLabUI.scrollBottom();
        syscoLabUI.scrollUp();
        Assert.assertTrue(syscoLabUI.isDisplayed(pageHeader));
    }

    //test scrollToElement method
    @Test
    public void testScrollToElementBy() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DRUGMART_URL);
        syscoLabUI.scrollToElement(twitterLogo);
        Assert.assertTrue(syscoLabUI.isDisplayed(twitterLogo));
    }

    //test scrollToElement method
    @Test
    public void testScrollToElementWebElement() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DRUGMART_URL);
        WebElement element = syscoLabUI.findElement(twitterLogo);
        syscoLabUI.scrollToElement(element);
        Assert.assertTrue(syscoLabUI.isDisplayed(twitterLogo));
    }

    //test move method
    @Test
    public void testMoveBy() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DRUGMART_URL);
        syscoLabUI.move(twitterLogo);
        Assert.assertTrue(syscoLabUI.isDisplayed(twitterLogo));
    }

    //test move method
    @Test
    public void testMoveWebElement() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DRUGMART_URL);
        WebElement element = syscoLabUI.findElement(twitterLogo);
        syscoLabUI.move(element);
        Assert.assertTrue(syscoLabUI.isDisplayed(twitterLogo));
    }

    //test batchMove method
    @Test
    public void testBatchMoveBy() throws AWTException {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(BATCH_MOVE_URL);
        syscoLabUI.batchMove(947, 62, 933, 935);
        Assert.assertTrue(syscoLabUI.isDisplayed(footPanel));
    }

    //test moveToAndClick method
    @Test
    public void testMoveToAndClickWebElement()  {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(AUTOMATIONTESTINGSMAIN_URL);
        WebElement element=syscoLabUI.findElement(imgBtn);
        syscoLabUI.moveToAndClick(element);
        Assert.assertEquals(syscoLabUI.getCurrentURL(), "https://demo.automationtesting.in/Register.html", "URL is not correct");
    }


    //test moveToAndClick method
    @Test
    public void testMoveToAndClickBy() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(AUTOMATIONTESTINGSMAIN_URL);
        syscoLabUI.moveToAndClick(syscoLabUI.findElement(imgBtn));
        Assert.assertEquals(syscoLabUI.getCurrentURL(), "https://demo.automationtesting.in/Register.html", "URL is not correct");
    }


    //test mouseHover method
    @Test
    public void testMouseHoverBy() throws InterruptedException {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(TOOLQA_URL);
        syscoLabUI.click(syscoLabUI.findElement(leftPaneMenu));
        syscoLabUI.mouseHover(syscoLabUI.findElement(leftPaneMenuItem));
        Thread.sleep(10000);
        Assert.assertEquals(syscoLabUI.getText(syscoLabUI.findElement(menuSubItem)).contains("Java"), true, "Text does not contain Java");
    }


    //test mouseHover method
    @Test
    public void testMouseHoverWebElement() throws InterruptedException {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(TOOLQA_URL);
        syscoLabUI.click(syscoLabUI.findElement(leftPaneMenu));
        syscoLabUI.mouseHover(leftPaneMenuItem);
        Thread.sleep(10000);
        Assert.assertEquals(syscoLabUI.getText(syscoLabUI.findElement(menuSubItem)), "Selenium in Java", "Text does not contain Java");
    }


    //test switchToTab method
    @Test
    public void testSwitchToTab() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL9);
        syscoLabUI.click(switchTabBtn);
        syscoLabUI.switchToTab(1);
        Assert.assertEquals(syscoLabUI.getText(pageHeader2), "This is a sample page");
    }

    //test switchToDefaultTab method
    @Test
    public void testSwitchToDefaultTab() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL9);
        syscoLabUI.click(switchTabBtn);
        syscoLabUI.switchToTab(1);
        syscoLabUI.switchToDefaultTab();
        Assert.assertEquals(syscoLabUI.getTitle(), "DEMOQA");
    }

    //test switchToDefaultWindow method
    @Test
    public void testSwitchToWindow() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL9);
        syscoLabUI.click(switchWindowBtn);
        syscoLabUI.switchToWindow();
        Assert.assertEquals(syscoLabUI.getText(pageHeader2), "This is a sample page");
    }

    //test switchToDefaultWindow method
    @Test
    public void testSwitchToDefaultWindow() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL9);
        syscoLabUI.click(switchWindowBtn);
        syscoLabUI.switchToWindow();
        syscoLabUI.switchToDefaultWindow();
        Assert.assertEquals(syscoLabUI.getTitle(), "DEMOQA");
    }

    //test isAlertDisplayed method
    @Test
    public void testIsAlertDisplayed() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL6);
        syscoLabUI.click(alertBtn);
        Assert.assertEquals(syscoLabUI.isAlertDisplayed(), true);
    }

    //test getAlertText method
    @Test
    public void testGetAlertText() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL6);
        syscoLabUI.click(alertBtn);
        Assert.assertEquals(syscoLabUI.getAlertText(), "You clicked a button");
    }

    //test clickOkInWindowsAlert method
    @Test
    public void testClickOkInWindowsAlert() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL6);
        syscoLabUI.clickWithJavascript(alertConfirmBtn);
        syscoLabUI.clickOkInWindowsAlert();
        Assert.assertEquals(syscoLabUI.getText(alertConfirmText), "You selected Ok");
    }

    //test dismissAlert method
    @Test
    public void testDismissAlert() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL6);
        syscoLabUI.clickWithJavascript(alertConfirmBtn);
        syscoLabUI.dismissAlert();
        Assert.assertEquals(syscoLabUI.getText(alertConfirmText), "You selected Cancel");
    }

    //test getLocation method
    @Test
    public void testGetLocationWebElement() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL10);
        WebElement element = syscoLabUI.findElement(draggable);
        String locationString = "(" + syscoLabUI.getLocation(element).getX() + "," + syscoLabUI.getLocation(element).getY() + ")";
        Assert.assertEquals(locationString, "(244,433)");
    }

    //test getLocation method
    @Test
    public void testGetLocationBy() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL10);
        String locationString = "(" + syscoLabUI.getLocation(draggable).getX() + "," + syscoLabUI.getLocation(By.xpath("//div[@id='draggable']")).getY() + ")";
        Assert.assertEquals(locationString, "(244,433)");
    }

    //test getX method
    @Test
    public void testGetX() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL10);
        Assert.assertEquals(syscoLabUI.getX(syscoLabUI.getLocation(draggable)), 244);
    }

    //test getY method
    @Test
    public void testGetY() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL10);
        Assert.assertEquals(syscoLabUI.getY(syscoLabUI.getLocation(draggable)), 433);
    }

    //test sleep method with start time and end time in seconds
    @Test
    public void testSleep() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        long startTime = System.currentTimeMillis();
        syscoLabUI.sleep(5);
        long endTime = System.currentTimeMillis();
        Assert.assertEquals(endTime - startTime >= 5000, true);
    }

    //test sleepInMilliSeconds method
    @Test
    public void testSleepInMilliSeconds() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        long startTime = System.currentTimeMillis();
        syscoLabUI.sleepInMilliSeconds(5000);
        long endTime = System.currentTimeMillis();
        Assert.assertEquals(endTime - startTime >= 5000, true);
    }

    //test getCurrentURL method
    @Test
    public void testGetCurrentURL() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL1);
        Assert.assertEquals(syscoLabUI.getCurrentURL(), DEMOQA_URL1);
    }

    //test getScreenShot method
    @Test
    public void testGetScreenShot() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL1);
        byte[] screenshot = syscoLabUI.getScreenShot();
        //assert screenshot is not null
        Assert.assertNotNull(screenshot);
    }

    //test getScreenShotBase64 method
    @Test
    public void testGetScreenShotBase64() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL1);
        byte[] screenshot = syscoLabUI.getScreenShotBase64();
        //assert screenshot is not null
        Assert.assertNotNull(screenshot);
    }

    //test closeDriver method
    @Test
    public void testCloseDriver() {
        syscoLabUI = new SyscoLabWUI(SyscoLabBrowserTypes.CHROME, chromeOptions);
        syscoLabUI.navigateTo(DEMOQA_URL9);
        syscoLabUI.click(switchTabBtn);
        syscoLabUI.switchToTab(1);
        syscoLabUI.closeDriver();
        try {
            syscoLabUI.getCurrentURL();
        } catch (NoSuchWindowException E) {
            Assert.assertTrue(true);
        }
    }

    @AfterMethod
    public void tearDown() {
        syscoLabUI.quit();
    }
}
