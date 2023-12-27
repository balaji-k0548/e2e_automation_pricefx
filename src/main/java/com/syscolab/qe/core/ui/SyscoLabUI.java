package com.syscolab.qe.core.ui;

import com.syscolab.qe.core.reporting.SyscoLabListener;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.ElementOption;
import org.openqa.selenium.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.awt.*;
import java.awt.event.InputEvent;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * This is the class where all the granular level web driver used methods are written
 * Created by yoosufm on 6/7/17.
 */
public class SyscoLabUI {
    public static RemoteWebDriver driver = null;
    public static final int TIME_OUT = 15;
    static protected SyscoLabListener syscoLabListener;
    String parentWindow;
    static final String SCRIPT_1 = "var event = document.createEvent(\"HTMLEvents\");event.initEvent(\"click\", true, true);var target = document.evaluate('";
    static final String SCRIPT_2 = "document.evaluate('";
    static final String SCRIPT_3 = "', document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.dispatchEvent(new Event('touchstart'))";

    /**
     * This will set the time-out
     * @param timeoutInSeconds time in seconds
     */
    public void setTimeOut(long timeoutInSeconds) {
        driver.manage().timeouts().implicitlyWait(timeoutInSeconds, TimeUnit.SECONDS);
    }

    /**
     * Return the Remote Web Driver
     * @return driver
     */
    public RemoteWebDriver getDriver() {
        if (driver != null) {
            return driver;
        }
        return null;
    }

    /**
     * This will maximize the browser window
     */
    public void maximizeWindow() {
        driver.manage().window().maximize();
    }

    /**
     * This will enlarge the browser window to a fullscreen Window
     */
    public void fullscreenWindow() {
        driver.manage().window().fullscreen();
    }

    /**
     * This will return the Web element in the DOM
     * @param by locating mechanism and the selector
     * @return Web element
     */
    public WebElement findElement(By by) {
        return driver.findElement(by);
    }

    /**
     * This will return the list a set of Web elements in the DOM
     * @param parentElement Web element of the parent
     * @param by            locating mechanism and the selector
     * @return list of web elements
     */
    public List<WebElement> findElements(WebElement parentElement, By by) {
        setTimeOut(0);
        List<WebElement> elements = parentElement.findElements(by);
        setTimeOut(TIME_OUT);
        return elements;
    }

    /**
     * This will return the  Web element in the DOM after waiting for the TIME_OUT
     * @param parentElement Web element of the parent
     * @param by locating mechanism and the selector
     * @return web element
     */
    public WebElement findElement(WebElement parentElement, By by) {
        try {
            setTimeOut(0);
            WebElement element = parentElement.findElement(by);
            setTimeOut(TIME_OUT);
            return element;
        } catch (NoSuchElementException e) {
            setTimeOut(TIME_OUT);
            return null;
        }
    }

    /**
     * This will return the  Web element in the DOM after waiting for the TIME_OUT
     * @param parentElement parent Web element locator/selector
     * @param by locating mechanism and the selector
     * @return web element
     */
    public WebElement findElement(By parentElement, By by) {
        try {
            setTimeOut(0);
            WebElement element = findElement(parentElement).findElement(by);
            setTimeOut(TIME_OUT);
            return element;
        } catch (NoSuchElementException e) {
            setTimeOut(TIME_OUT);
            return null;
        }
    }

    /**
     * This will return the list a set of Web elements in the DOM
     * @param by locating mechanism and the selector
     * @return list of web elements
     */
    public List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    /**
     * This will return if the Web element is visible only in the DOM
     * @param by locating mechanism and the selector
     * @return Web element
     */
    public WebElement findVisibleElement(By by) {
        List<WebElement> elements = findElements(by);
        for (WebElement element : elements) {
            if (isDisplayed(element))
                return element;
        }
        return null;
    }

    /**
     * This will return a list of web elements if the Web elements are visible only in the DOM
     * @param by locating mechanism and the selector
     * @return a list of web elements
     */
    public List<WebElement> findVisibleElements(By by) {
        List<WebElement> elements = findElements(by);
        List<WebElement> visibleElements = new ArrayList();
        for (WebElement element : elements) {
            if (isDisplayed(element))
                visibleElements.add(element);
        }
        return visibleElements;
    }

    /**
     * This will clear the web element
     * @param element the element to be cleared
     */
    public void clear(WebElement element) {
        element.clear();
    }

    /**
     * This will clear the element by the selector
     * @param by locating mechanism and the selector
     */
    public void clear(By by) {
        clear(waitTillElementLoaded(by));
    }

    /**
     * This will clear the element using the backspace as per the count mentioned
     * @param by locating mechanism and the selector of the element to be cleared
     * @param count how many backspace instances should be pressed
     */
    public void clearWithBackSpace(By by, int count) {
        while (count > 0) {
            findElement(by).sendKeys(Keys.BACK_SPACE);
            count--;
        }
    }

    /**
     * This will clear the element using the backspace as per the count mentioned
     * @param element web element to be cleared
     * @param count   how many backspace instances should be pressed
     */
    public void clearWithBackSpace(WebElement element, int count) {
        while (count > 0) {
            element.sendKeys(Keys.BACK_SPACE);
            count--;
        }
    }

    /**
     * This will  clear the element and type in an element
     * @param by   locating mechanism and the selector of the element to be typed into
     * @param text the string
     */
    public void sendKeys(By by, String text) {
        clear(by);
        findElement(by).sendKeys(text);
    }

    /**
     * This will  clear the element and type in an element
     * @param element the web element to be typed into
     * @param text    the string
     */
    public void sendKeys(WebElement element, String text) {
        clear(element);
        element.sendKeys(text);
    }

    /**
     * This will enter pressable keys which aren't texts
     * @param by   locating mechanism and the selector of the element to which the key should be pressed
     * @param keys Pressable keys
     */
    public void sendKeys(By by, Keys keys) {
        findElement(by).sendKeys(keys);
    }

    /**
     * This will enter pressable keys which aren't texts to an element
     * @param element the web element into which the key should be pressed
     * @param keys    Pressable keys
     */
    public void sendKeys(WebElement element, Keys keys) {
        element.sendKeys(keys);
    }

    /**
     * This will type in an element
     * @param element the web element to be typed into
     * @param text    the string
     */
    public void sendKeysWithoutClear(WebElement element, String text) {
        element.sendKeys(text);
    }

    /**
     * This will type in an element
     * @param by   locating mechanism and the selector of the element to be typed into
     * @param text the string
     */
    public void sendKeysWithoutClear(By by, String text) {
        findElement(by).sendKeys(text);
    }

    /**
     * This will type using javascript executor using the locator
     * @param by   locating mechanism and the selector of the element to be typed into
     * @param text the string
     */
    public void sendKeysInScript(By by, String text) {
        WebElement element = driver.findElement(by);
        JavascriptExecutor myExecutor = ((JavascriptExecutor) driver);
        myExecutor.executeScript("arguments[0].value='" + text + "';", element);
    }

    /**
     * This will type using javascript executor using the locator
     * @param element the web element to be typed into
     * @param text    the string
     */
    public void sendKeysInScript(WebElement element, String text) {
        JavascriptExecutor myExecutor = ((JavascriptExecutor) driver);
        myExecutor.executeScript("arguments[0].value='" + text + "';", element);
    }

    /**
     * This will click the element using the locator
     * @param by locating mechanism and the selector of the element to be clicked
     */
    public void click(By by) {
        findElement(by).click();
    }

    /**
     * This will click the passed web element
     * @param element web element to be clicked
     */
    public void click(WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(TIME_OUT)).until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    /**
     * This will click the child element of the passed parent element
     * @param parentElement parent element locator
     * @param by locating mechanism and the selector of the element to be clicked
     */
    public void click(WebElement parentElement, By by) {
        WebElement webElement = parentElement.findElement(by);
        click(webElement);
    }

    /**
     * This will click the element of the passed locator
     * @param by locating mechanism and the selector of the element to be clicked
     */
    public void clickWithJavascript(By by) {
        clickWithJavascript(findElement(by));
    }

    /**
     * This will click the element of the passed element
     * @param element element to be clicked
     */
    public void clickWithJavascript(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    /**
     * This will double-click the passed element
     * @param element element to be double-clicked
     */
    public void doubleClick(WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(TIME_OUT)).until(ExpectedConditions.elementToBeClickable(element)).click();
        new WebDriverWait(driver, Duration.ofSeconds(TIME_OUT)).until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    /**
     * This will click on the element passed by the locator if only the element is visible in DOM
     * @param by locating mechanism and the selector of the element to be clicked
     */
    public void clickOnVisibleElement(By by) {
        List<WebElement> elements = findElements(by);
        for (WebElement element : elements) {
            if (isDisplayed(element)) {
                click(element);
            }
        }
    }

    /**
     * This will click the element passed by the locator in the Mobile touch
     * @param by locating mechanism and the selector of the element
     */
    public void clickInMobileTouchStart(By by) {
        String element = by.toString().split(":")[1].trim();
        driver.executeScript(SCRIPT_2 + element + SCRIPT_3);
    }

    /**
     * This will click the element passed by the element in the Mobile touch start event
     * @param element web element
     * @param by      locating mechanism and the selector of the element
     */
    public void clickInMobileTouchStart(WebElement element, By by) {
        String strElement = element.toString().split("->")[1].split(":")[1].trim();
        strElement = element.toString().split("->")[1].split(":")[1].trim().substring(0, strElement.length() - 1);
        String strBy = by.toString().split(":")[1].trim().substring(1);
        driver.executeScript(SCRIPT_2 + strElement + strBy + SCRIPT_3);
    }

    /**
     * This will click the element passed by the locator in the mobile touch end event
     * @param by locating mechanism and the selector of the element
     */
    public void clickInMobileTouchEnd(By by) {
        String strBy = by.toString().split(":")[1].trim();
        driver.executeScript(SCRIPT_1 + strBy + "', document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;target.dispatchEvent(event);var event = document.createEvent(\"HTMLEvents\");event.initEvent(\"touchend\", true, true);target.dispatchEvent(event)");
    }

    /**
     * This will click the element in the mobile touch HTML event
     * @param by locating mechanism and the selector of the element
     */
    public void clickInMobileHTMLEvent(By by) {
        String element = by.toString().split(":")[1].trim();
        driver.executeScript(SCRIPT_1 + element + "', document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;target.dispatchEvent(event);var event = document.createEvent(\"HTMLEvents\");event.initEvent(\"touchstart\", true, true);target.dispatchEvent(event)");
    }

    /**
     * This will click the element in the mobile touch HTML event
     * @param element element locator
     * @param by locating mechanism and the selector of the element
     */
    public void clickInMobileHTMLEvent(WebElement element, By by) {
        String strElement = element.toString().split("->")[1].split(":")[1].trim();
        strElement = element.toString().split("->")[1].split(":")[1].trim().substring(0, strElement.length() - 1);
        String strBy = by.toString().split(":")[1].trim().substring(1);
        driver.executeScript(SCRIPT_1 + strElement + strBy + "', document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;target.dispatchEvent(event);var event = document.createEvent(\"HTMLEvents\");event.initEvent(\"touchstart\", true, true);target.dispatchEvent(event)");
    }

    /**
     * This will click in mobile screen
     * @param element web element
     */
    public void clickInMobile(String element) {
        driver.executeScript(SCRIPT_2 + element + SCRIPT_3);
    }

    /**
     * This will return the title in the browser
     * @return title
     */
    public String getTitle() {
        return driver.getTitle();
    }

    /**
     * This will return the text of the element
     * @param by locating mechanism and the selector of the element
     * @return text
     */
    public String getText(By by) {
        WebElement element = findElement(by);
        return element.getText();
    }

    /**
     * This will return the text of the passed element
     * @param element web element
     * @return text
     */
    public String getText(WebElement element) {
        return element.getText();
    }

    /**
     * This will return the content of all elements including and elements
     *
     * @param by locating mechanism and the selector of the element
     * @return text
     */
    public String getInvisibleText(By by) {
        return findElement(by).getAttribute("textContent");
    }

    /**
     * Return the first selected option related text of a select options
     *
     * @param by locating mechanism and the selector of the element
     * @return text
     */
    public String getFirstSelectedOptionText(By by) {
        Select select = new Select(findElement(by));
        WebElement option = select.getFirstSelectedOption();
        return option.getText();
    }

    /**
     * Return the first selected option related text of a select options
     *
     * @param element Web Element
     * @return text
     */
    public String getFirstSelectedOptionText(WebElement element) {
        Select select = new Select(element);
        WebElement option = select.getFirstSelectedOption();
        return option.getText();
    }

    /**
     * Return true if the element is displayed
     *
     * @param element web element
     * @return true or false
     */
    public boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Return true if the element passed by the locator is displayed
     *
     * @param by locating mechanism and the selector of the element
     * @return true or false
     */
    public boolean isDisplayed(By by) {
        try {
            return findElement(by).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Return true if the element is enabled
     *
     * @param by locating mechanism and the selector of the element
     * @return true or false
     */
    public boolean isEnabled(By by) {
        return waitTillElementLoaded(by).isEnabled();
    }

    /**
     * Return true if the element is clickable
     *
     * @param by locating mechanism and the selector of the element
     * @return true or false
     */
    public boolean isClickable(By by) {
        return findElement(by).isEnabled();
    }

    /**
     * Return true or false if the element passed by the locator is clickable
     *
     * @param element web element
     * @return true or false
     */
    public boolean isClickable(WebElement element) {
        return element.isEnabled();
    }

    /**
     * This will navigate to the passed url
     *
     * @param url navigation url
     */
    public void navigateTo(String url) {
        driver.navigate().to(url);
    }

    /**
     * This will navigate back
     */
    public void navigateBack() {
        driver.navigate().back();
    }

    /**
     * This will refresh the browser
     */
    public void refreshBrowser() {
        driver.navigate().refresh();
    }

    /**
     * This will select from a select
     *
     * @param by     locating mechanism and the selector of the element
     * @param option option to be selected
     */
    public void select(By by, String option) {
        new Select(findElement(by)).selectByVisibleText(option);
    }

    /**
     * This will wait the web driver until the element passed by the locator is loaded
     *
     * @param by      locating mechanism and the selector of the element
     * @param timeout time to be waited in seconds
     * @return web driver
     */
    public WebElement waitTillElementLoaded(By by, long timeout) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeout)).until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    /**
     * This will wait till element passed by locator loaded until the TIME_OUT time
     *
     * @param by locating mechanism and the selector of the element
     * @return web driver
     */
    public WebElement waitTillElementLoaded(By by) {
        return waitTillElementLoaded(by, TIME_OUT);
    }

    /**
     * This will wait till the element loaded until the TIME_OUT time
     *
     * @param element web element
     * @return web driver
     */
    public WebElement waitTillElementLoaded(WebElement element) {
        return new WebDriverWait(driver, Duration.ofSeconds(TIME_OUT)).until(ExpectedConditions.visibilityOf(element));

    }

    /**
     * This will wait until the CSS Attribute value to be equal to the expected value of the passed element
     *
     * @param element       web element
     * @param attributeName name of the attribute
     * @param expectedValue expected value
     * @param timeout       timeout in seconds
     */
    public void waitTillCSSAttributeValueToBePresent(WebElement element, String attributeName, String expectedValue, long timeout) {
        long counter = 0;
        long timeoutNew = timeout * 1000;
        while (counter <= timeoutNew) {
            try {
                if (getCssValue(element, attributeName).equals(expectedValue))
                    return;
            } catch (NullPointerException e) {
            } finally {
                sleepInMilliSeconds(50);
                counter += 50;
            }
        }
        throw new TimeoutException("Timeout while waiting for element attribute to be present: [Attribute: " + attributeName + ", Expected Value: " + expectedValue + "]");
    }

    /**
     * This will return true if the element passed by locator is selected
     *
     * @param by locating mechanism and the selector of the element
     * @return true or false
     */
    public boolean isSelected(By by) {
        return findElement(by).isSelected();
    }

    /**
     * This will return true if the element  is selected
     *
     * @param element web element
     * @return true or false
     */
    public boolean isSelected(WebElement element) {
        return waitTillElementLoaded(element).isSelected();
    }

    /**
     * This will wait till the element passed by the locator disappears until given TIME_OUT
     *
     * @param by locating mechanism and the selector of the element
     * @return driver
     */
    public boolean waitTillElementDisappear(By by) {
        setTimeOut(0);
        Boolean result = new WebDriverWait(driver, Duration.ofSeconds(TIME_OUT)).until(ExpectedConditions.invisibilityOfElementLocated(by));
        setTimeOut(TIME_OUT);
        return result;
    }

    /**
     * This will wait until the element passed by the locator until the given timeout
     *
     * @param by      locating mechanism and the selector of the element
     * @param timeout time out in seconds
     * @return driver
     */
    public boolean waitTillElementDisappeared(By by, long timeout) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeout)).until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    /**
     * This will return the value of an attribute of the element passed by the locator
     *
     * @param by            locating mechanism and the selector of the element
     * @param attributeName name of the attribute
     * @return the value of the attribute
     */
    public String getAttribute(By by, String attributeName) {
        return findElement(by).getAttribute(attributeName);
    }

    /**
     * This will return the value of an attribute of the element
     *
     * @param element       web element
     * @param attributeName name of the attribute
     * @return the value of the attribute
     */
    public String getAttribute(WebElement element, String attributeName) {
        return element.getAttribute(attributeName);
    }

    /**
     * This will return the css value of the attribute of the passed element by locator
     *
     * @param by            locating mechanism and the selector of the element
     * @param attributeName name of the attribute
     * @return css value
     */
    public String getCssValue(By by, String attributeName) {
        return findElement(by).getCssValue(attributeName);
    }

    /**
     * This will return the css value of the attribute of the passed element
     *
     * @param element       web element
     * @param attributeName name of the attribute
     * @return css value
     */
    public String getCssValue(WebElement element, String attributeName) {
        return element.getCssValue(attributeName);
    }

    /**
     * This will return the value of the value attribute of the passed locator
     *
     * @param by locating mechanism and the selector of the element
     * @return the value
     */
    public String getValue(By by) {
        return findElement(by).getAttribute("value");
    }

    /**
     * This will return the value of the value attribute of the element
     *
     * @param element web element
     * @return the value
     */
    public String getValue(WebElement element) {
        return element.getAttribute("value");
    }

    /**
     * This will select the option passed by the dropdown locator
     *
     * @param by     locating mechanism and the selector of the element
     * @param option option to be selected
     */
    public void selectFromDropDown(By by, String option) {
        new Select(findElement(by)).selectByVisibleText(option);
    }

    /**
     * This will select the option passed by the dropdown element
     *
     * @param element web element
     * @param option  option to be selected
     */
    public void selectFromDropDown(WebElement element, String option) {
        new Select(element).selectByVisibleText(option);
    }

    /**
     * This will switch to the given iFrame by the locator
     *
     * @param iFrame locator of the iFrame to be switched
     */
    public void switchToFrame(By iFrame) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIME_OUT));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iFrame));
    }

    /**
     * This will switch to the given iFrame element
     *
     * @param iFrame web element of the iFrame to be switched
     */
    public void switchToFrame(WebElement iFrame) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIME_OUT));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iFrame));
    }

    /**
     * This will switch to the default frame
     */
    public void switchToDefaultFrame() {
        driver.switchTo().defaultContent();
    }

    /**
     * This will scroll up to the top
     */
    public void scrollUp() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollTo(0, 0)");
    }

    /**
     * This will scroll down to the bottom
     */
    public void scrollBottom() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    /**
     * This will scroll to the right
     */
    public void scrollRight() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(1000,0)", "");
    }

    /**
     * This will scroll down to the given height
     *
     * @param height the height to be scrolled
     */
    public void scrollDown(double height) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0," + height + ")");

    }

    /**
     * This will scroll right of the element passed by the locator
     *
     * @param by locating mechanism and the selector of the element
     */
    public void scrollToRight(By by) {
        WebElement element = driver.findElement(by);
        Actions actions = new Actions(driver);
        actions.moveByOffset(0, 0);
        actions.moveToElement(element);
        actions.perform();
    }

    /**
     * This will scroll right of the web element
     *
     * @param element web element
     */
    public void scrollToRight(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveByOffset(0, 0);
        actions.moveToElement(element);
        actions.perform();
    }

    /**
     * Scroll in to a given element
     *
     * @param element web element
     */
    public void scrollToElement(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
    }

    /**
     * Scroll in to a given element by the locator
     *
     * @param by locating mechanism and the selector of the element
     */
    public void scrollToElement(By by) {
        Actions actions = new Actions(driver);
        actions.moveToElement(findElement(by));
        actions.perform();
    }

    /**
     * This will move the mouse to a given element by the locator using javascript executor
     *
     * @param by locating mechanism and the selector of the element
     */
    public void move(By by) {
        WebElement element = findElement(by);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * This will move the mouse to a given element using the javascript executor
     *
     * @param element web element
     */
    public void move(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * This will move the mouse with regard to the given screen coordinates
     *
     * @param sourceX screen coordinate of the source X axis
     * @param sourceY screen coordinate of the source Y axis
     * @param destX   screen coordinate of the destination X axis
     * @param destY   screen coordinate of the destination Y axis
     * @throws AWTException throwing AWTException
     */
    public void batchMove(int sourceX, int sourceY, int destX, int destY) throws AWTException {
        Robot r = new Robot();
        r.mouseMove(sourceX, sourceY);
        r.mousePress(InputEvent.BUTTON1_MASK);
        sleepInMilliSeconds(500);
        r.mouseMove(destX, destY);
        r.delay(2000);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);

    }

    /**
     * This will drag and drop an element to the destination of the given coordinates
     *
     * @param byFrom locating mechanism and the selector of the FROM element
     * @param byTo   locating mechanism and the selector of the TO element
     * @param destX  destination X axis coordinates
     * @param destY  destination Y axis coordinates
     */
    public void batchMove(WebElement byFrom, WebElement byTo, int destX, int destY) {
        WebElement locatorFrom = byFrom;
        WebElement locatorTo = byTo;
        int x = destX;
        int y = destY;
        Actions act = new Actions(driver);
        sleepInMilliSeconds(500);
        act.clickAndHold(locatorFrom).dragAndDropBy(locatorTo, x, y).release().build().perform();
    }

    /**
     * This will move to the element and click the element
     *
     * @param element web element
     */
    public void moveToAndClick(WebElement element) {
        new Actions(driver).moveToElement(element).click().perform();
    }

    /**
     * This will move to the given element by the locator and click the element
     *
     * @param by locating mechanism and the selector of the element
     */
    public void moveToAndClick(By by) {
        new Actions(driver).moveToElement(findElement(by)).click().perform();
    }

    /**
     * This will hover the mouse over the web element
     *
     * @param webElement web element
     */
    public void mouseHover(WebElement webElement) {
        new Actions(driver).moveToElement(webElement).build().perform();
    }

    /**
     * This will hover the mouse over the web element passed by the locator
     *
     * @param by locating mechanism and the selector of the element
     */
    public void mouseHover(By by) {
        new Actions(driver).moveToElement(findElement(by)).build().perform();
    }

    /**
     * This will move the mouse pointer to the given x-axis 50 and y-axis 50 coordinates
     */
    public void movePointerTo() {
        try {
            Robot robot = new Robot();
            robot.mouseMove(50, 50);
        } catch (NumberFormatException | AWTException var3) {
        }

    }

    /**
     * This will move the mouse pointer to the passed x,y screen coordinates
     *
     * @param x screen coordinates of the x-axis to move the mouse pointer to
     * @param y screen coordinates of the y-axis to move the mouse pointer to
     */
    public void movePointerTo(int x, int y) {
        try {
            Robot robot = new Robot();
            robot.mouseMove(x, y);
        } catch (NumberFormatException | AWTException var3) {
        }

    }

    /**
     * This will drag and drop the element from the source to destination
     *
     * @param sourceX source screen coordinates of the x-axis
     * @param sourceY source screen coordinates of the y-axis
     * @param destX   destination screen coordinates of the x-axis
     * @param destY   destination screen coordinates of the y-axis
     * @throws AWTException throwing AWTException
     */
    public void dragAndDrop(int sourceX, int sourceY, int destX, int destY) throws AWTException {

        Robot r = new Robot();
        sleepInMilliSeconds(4000);
        r.mouseMove(sourceX, sourceY);
        r.mousePress(InputEvent.BUTTON1_MASK);
        sleepInMilliSeconds(2000);
        r.mouseMove(destX, destY);
        r.delay(3000);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);

    }

    /**
     * This will drag and drop the element by the given gap of coordinates
     *
     * @param byFrom locating mechanism and the selector of the FROM element
     * @param byTo   locating mechanism and the selector of the TO element
     * @param xGap   x-axis gap of coordinates
     * @param yGap   y-axis gap of coordinates
     */
    public void dragAndDrop(By byFrom, WebElement byTo, int xGap, int yGap) {
        WebElement locatorFrom = findElement(byFrom);
        WebElement locatorTo = byTo;
        int x = locatorTo.getLocation().getX();
        int y = -locatorTo.getLocation().getY();
        Actions act = new Actions(driver);

        if (yGap != 0) {
            y = y - yGap;
        }
        if (xGap != 0) {
            x = x + xGap;
        }
        sleepInMilliSeconds(500);
        act.clickAndHold(locatorFrom).moveByOffset(x, y).release().build().perform();
    }

    /**
     * Drag and drop and element from to element to
     *
     * @param byFrom locating mechanism and the selector of the FROM element
     * @param byTo   locating mechanism and the selector of the TO element
     */
    public void dragAndDrop(By byFrom, By byTo) {
        (new Actions(driver)).dragAndDrop(findElement(byFrom), findElement(byTo)).perform();
    }

    /**
     * This will click and hold the element and Shifts the mouse from its current position by the given offset
     *
     * @param byFrom locating mechanism and the selector of the FROM element
     * @param byTo   locating mechanism and the selector of the To element
     * @param gap    gap between the coordinates
     */
    public void holdElement(By byFrom, WebElement byTo, int gap) {
        WebElement locatorFrom = findElement(byFrom);
        WebElement locatorTo = byTo;

        int x = locatorTo.getLocation().getX();
        int y = -locatorTo.getLocation().getY();

        if (gap != 0) {
            y = y - gap;
        }
        Actions act = new Actions(driver);
        act.clickAndHold(locatorFrom).moveByOffset(x, y).build().perform();
    }

    /**
     * This will release the element from the hold
     */
    public void releaseElement() {
        Actions act = new Actions(driver);
        act.release().build().perform();
    }

    /**
     * This will switch to a tab in the browser
     *
     * @param tabIndex tab index of the browser to be switched
     */
    public void switchToTab(int tabIndex) {
        ArrayList<String> tabs_windows = new ArrayList(this.driver.getWindowHandles());
        this.driver.switchTo().window((String) tabs_windows.get(tabIndex));
    }

    /**
     * This will switch to the default tab
     */
    public void switchToDefaultTab() {
        ArrayList<String> tabs_windows = new ArrayList(this.driver.getWindowHandles());
        this.driver.switchTo().window((String) tabs_windows.get(0));
    }

    /**
     * This will switch to the default/parent window
     */
    public void switchToDefaultWindow() {
        driver.switchTo().window(parentWindow);
    }

    /**
     * This will switch to the newest window by checking how many windows available
     */
    public void switchToWindow() {
        parentWindow = driver.getWindowHandle();

        //move to the newest window by checking how many windows available
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
    }

    /**
     * This will return true if the alert is visible and switch to the alert
     *
     * @return true or false
     */
    public boolean isAlertDisplayed() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    /**
     * This will switch to the alert and return the text of the alert
     *
     * @return text of the alert
     */
    public String getAlertText() {
        Alert simpleAlert = driver.switchTo().alert();
        return simpleAlert.getText();
    }

    /**
     * This will switch to the alert and click ok on the alert
     */
    public void clickOkInWindowsAlert() {
        Alert simpleAlert = driver.switchTo().alert();
        simpleAlert.accept();
    }

    /**
     * This will wait until the TIME_OUT and the alert is not present
     */
    public void waitTillAlertBoxAppears() {
        new WebDriverWait(driver, Duration.ofSeconds(TIME_OUT)).until(ExpectedConditions.alertIsPresent());
    }

    /**
     * This will dismiss the alert
     */
    public void dismissAlert() {
        driver.switchTo().alert().dismiss();
    }

    /**
     * This will return relative position of an element where it is rendered on the web page.
     * This position is calculated relative to the top-left corner of the web page of which the (x, y) coordinates are assumed to be (0, 0)
     *
     * @param element web element
     * @return relative position of an element
     */
    public Point getLocation(WebElement element) {
        return element.getLocation();
    }

    /**
     * This will return relative position of an element where it is rendered on the web page.
     * This position is calculated relative to the top-left corner of the web page of which the (x, y) coordinates are assumed to be (0, 0)
     *
     * @param by locating mechanism and the selector of the element
     * @return relative position of an element
     */
    public Point getLocation(By by) {
        return findElement(by).getLocation();
    }

    /**
     * Returns the X coordinate of this Point2D in double precision.
     *
     * @param p point, the new location for this point
     * @return X coordinate of this Point2D
     */
    public int getX(Point p) {
        return p.getX();
    }

    /**
     * Returns the Y coordinate of this Point2D in double precision.
     *
     * @param p point, the new location for this point
     * @return Y coordinate of this Point2D
     */
    public int getY(Point p) {
        return p.getY();
    }

    /**
     * This will suspend execution of the current thread for a specified period
     *
     * @param noOfSeconds time for the sleep in seconds
     */
    public void sleep(int noOfSeconds) {
        try {
            Thread.sleep(1000 * noOfSeconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

        }
    }

    /**
     * This will suspend execution of the current thread for a specified period
     *
     * @param noOfMilliSeconds time for the sleep in MilliSeconds
     */
    public void sleepInMilliSeconds(int noOfMilliSeconds) {
        try {
            Thread.sleep(noOfMilliSeconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * This will return the current url
     *
     * @return current url
     */
    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }

    /**
     * This will Obtain the screenshot as raw bytes.
     *
     * @return the screenshot in raw bytes
     */
    public byte[] getScreenShot() {
        if (!(driver == null))
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        ;
        return null;
    }

    /**
     * This will Obtain the screenshot in BASE64 format.
     *
     * @return the screenshot
     */
    public byte[] getScreenShotBase64() {

        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64).getBytes();
    }

    /**
     * This will quit the current driver and exit the browser, end the session, tabs, pop-ups etc
     */
    public void quit() {
        if(driver!=null){
            driver.quit();
        }
    }

    /**
     * This will close the driver.Better use this when you are dealing with multiple tabs or windows
     */
    public void closeDriver() {
        driver.close();
    }

    /**
     * This will tap an element in iOS by element ID
     *
     * @param elementID element id
     */
    public void tapElement(String elementID) {
        this.tap(this.getIosDriver().findElement(MobileBy.accessibilityId(elementID)));
    }

    /**
     * This will tap a web element in iOS
     *
     * @param element web element
     */
    public void tap(WebElement element) {
//        new TouchAction((getIosDriver())).tap(element).perform();
        new TouchAction((getIosDriver())).tap(TapOptions.tapOptions().withElement(ElementOption.element(element))).perform();
    }

    /**
     * This will tap a web element by locator in iOS
     *
     * @param by locating mechanism and the selector of the element
     */
    public void tap(By by) {
//        new TouchAction((getIosDriver())).tap(findElement(by)).perform();
        new TouchAction((getIosDriver())).tap(TapOptions.tapOptions().withElement(ElementOption.element(findElement(by)))).perform();

    }

    /**
     * This will return the iOS driver
     *
     * @return iOS driver
     */
    protected IOSDriver getIosDriver() {
        return (IOSDriver) driver;
    }

    /**
     * This will switch to the web view in iOS
     */
    public void switchToWebView() {
        Set<String> set = getIosDriver().getContextHandles();
        for (String s : set) {
            if (s.contains("WEBVIEW")) {
                getIosDriver().context(s);
            }
        }
    }

    /**
     * This will switch to Native app in iOS
     */
    public void switchToNativeApp() {
        getIosDriver().context("NATIVE_APP");
    }

    /**
     * This will hide the keyboard in iOS
     */
    public void hideKeyBoard() {
        getIosDriver().hideKeyboard();
    }
}