package com.syscolab.qe.core.ui.common;

import com.syscolab.qe.core.ui.SyscoLabUI;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.SecureRandom;

import static com.syscolab.qe.core.ui.pages.LoginPage.syscoLabUI;


public class ActionUtils {

    public static RemoteWebDriver driver = null;

    /**
     * This method is used to cast locators To WebElements
     *
     * @param locatorElement
     * @author Balaji.K
     */
    public static WebElement castToWebElements(By locatorElement) {
        WebElement webElement = syscoLabUI.findElement(locatorElement);
        return webElement;
    }

    /**
     * This method is used to perform double click on a field using Actions
     *
     * @param webElement
     * @param strText
     * @author Balaji.K
     */
    public static void doubleClickUsingActions(WebElement webElement, String strText) {
        Actions act = new Actions(SyscoLabUI.driver);
        act.keyDown(Keys.CONTROL).doubleClick(webElement).keyUp(Keys.CONTROL).sendKeys(webElement, strText).perform();
    }

    /**
     * This method is used to perform double click on a field using Actions
     *
     * @param webElement
     * @param strText
     * @author Balaji.K
     */
    public static void singleClickUsingActions(WebElement webElement, String strText) {
        Actions act = new Actions(SyscoLabUI.driver);
        act.keyDown(Keys.CONTROL).click(webElement).keyUp(Keys.CONTROL).sendKeys(webElement, strText).perform();
    }

    /**
     * This method is used to perform double click on a field using Actions
     *
     * @param webElement
     * @author Balaji.K
     */
    public static void singleClickUsingActions(WebElement webElement) {
        Actions act = new Actions(SyscoLabUI.driver);
        act.keyDown(Keys.CONTROL).click(webElement).perform();
    }

    /**
     * This method is used to perform double click on a field using Actions
     *
     * @param webElement1
     * @param webElement2
     * @author Balaji.K
     */
    public static void selectByAction(WebElement webElement1, WebElement webElement2) {
        Actions act = new Actions(SyscoLabUI.driver);
        act.moveToElement(webElement1).perform();
        webElement2.click();
    }

    /**
     * This method is used to press Enter key using Robot class.
     *
     * @author Balaji.K
     */
    public static void pressEnterKey() throws Exception {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    /**
     * This method is used to generate random alpha value.
     *
     * @param intNumberOfCharacters
     * @author Balaji.K
     */
    public static String randomStringGenerate(int intNumberOfCharacters) {

        String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < intNumberOfCharacters; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

}