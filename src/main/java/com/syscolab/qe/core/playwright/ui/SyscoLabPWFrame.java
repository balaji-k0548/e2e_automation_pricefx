package com.syscolab.qe.core.playwright.ui;

import com.microsoft.playwright.Frame;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;

/**
 * @author Kasun Herath
 */
public class SyscoLabPWFrame {
    protected Page page;
    protected Frame frame;

    SyscoLabPWFrame() {
        //empty
    }

    /**
     * Initialize the Page retrieved from SyscoLabPW class
     * @param page
     */
    SyscoLabPWFrame(Page page) {
        this.page = page;
    }

    /**
     * Switch to iFrame by Frame Name
     * @param frameName ; name of the frame on DOM
     */
    public void switchToFrameByName(String frameName) {
        frame = page.frame(frameName);
    }

    /**
     * Switch to iFrame by Frame URL
     * @param frameURL ; src of the frame on DOM
     */
    public void switchToFrameByURL(String frameURL) {
        frame = page.frameByUrl(frameURL);
    }

    /**
     * Switch to Child Frame
     * @param parentFrameName - Accepts String of Main Frame
     * @param Index - Accepts Integer to point which Child Frame by Index value
     */
    public void switchToChildFrame(String parentFrameName,int Index){
        frame= page.frame(parentFrameName).childFrames().get(Index);
    }

    /**
     * Mouse Hover over provided locator inside an iFrame
     * @param locator - Accepts String ; xpath of the required locator
     */
    public void frameMouseHover(String locator) {
        this.frame.locator(locator).hover();
    }

    /**
     * Send Keys to provided locator inside an iFrame
     * @param locator - Accepts String ; xpath of the required locator
     * @param value - Accepts String ; value to be sent
     */
    public void frameSendKeys(String locator, String value) {
        this.frame.locator(locator).fill(value);
    }

    /**
     * Click on provided locator inside an iFrame
     * @param locator - Accepts String ; xpath of the required locator
     */
    public void frameClick(String locator) {
        this.frame.locator(locator).click();
    }

    /**
     * Select value from a drop-down for provided Select Class Locator with value to select identified by index inside an iFrame
     * @param locator - Accepts String ; xpath of the required locator
     * @param index - Accepts Integer ; value of drop down identified by index position
     */
    public void frameClickDropDownNth(String locator, int index) {
        this.frame.locator(locator).click();
        this.frame.locator(locator).selectOption(new SelectOption().setIndex(index));
    }

    /**
     * Click on an element by index when multiple matching values are found inside and iFrame
     * @param locator - Accepts String ; xpath of the required locator
     * @param index - Accepts Integer ; index of match
     */
    public void frameClickMultiElemNth(String locator, int index) {
        this.frame.locator(locator).nth(index).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        this.frame.locator(locator).nth(index).click();
    }

    /**
     * Checks on a checkbox for provided locator inside an iFrame
     * @param locator - Accepts String ; xpath of the required locator
     */
    public void frameCheckBoxClick(String locator) {
        this.frame.locator(locator).check();
    }

    /**
     * Returns text for provided locator inside an iFrame
     * @param locator - Accepts String ; xpath of the required locator
     * @return text content of the specified locator
     */
    public String frameGetText(String locator) {
        return frame.locator(locator).textContent();
    }

    /**
     * Returns Boolean for provided locator if visible
     * @param locator - Accepts String ; xpath of the required locator
     */
    public Boolean frameIsDisplayed(String locator) {
        return frame.locator(locator).isVisible();
    }

    /**
     * Returns Boolean for provided locator if checked
     * @param locator - Accepts String ; xpath of the required locator
     */
    public Boolean frameIsChecked(String locator) {
        return frame.locator(locator).isChecked();
    }
}
