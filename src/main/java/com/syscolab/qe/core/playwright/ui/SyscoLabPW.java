package com.syscolab.qe.core.playwright.ui;

import com.microsoft.playwright.Download;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.MouseButton;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.syscolab.qe.core.common.LoggerUtil;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Kasun Herath
 */
public class SyscoLabPW extends SyscoLabPWFrame{

    Page page;
    Locator locator;
    protected int width;
    protected int height;
    private File file;
    public SyscoLabPW() {

    }

    public SyscoLabPW(Page page) {
        super(page);
        this.page = page;
        this.page.setDefaultTimeout(120000);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) screenSize.getWidth();
        height = (int) screenSize.getHeight();
        this.page.setViewportSize(width, height);
    }

    /**
     * Create a file of JPEG format
     * @param filePath
     * @param fileName
     * @throws IOException
     */
    private void createFile(String filePath,String fileName) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmm");
        String formattedDateTime = now.format(formatter);
        System.out.println("Time is "+formattedDateTime);
        file = new File(filePath + fileName+ formattedDateTime+".jpeg");
        file.createNewFile();
    }

    /**==================================== Locators ========================== **/

    /**
     * Locate element by its text
     * @param text - Accepts String
     * @return     - Return an instance of SyscoLabPW
     */
    public SyscoLabPW findByLocator(String text) {
        locator = page.locator(text);
        return this;
    }

    /**
     * Locate element by its ID - Playwright sets id as "data-testid" ; this has been overridden to id
     * @param text - Accepts String
     * @return     - Return an instance of SyscoLabPW
     */
    public SyscoLabPW findById(String text) {
        locator = page.getByTestId(text);
        return this;
    }


    /**
     * Locate element by Text ; type button and submit are matched by their value
     * @param text - Accepts String
     * @return     - Return an instance of SyscoLabPW
     */
    public SyscoLabPW findByText(String text) {
        locator = page.getByText(text);
        return this;
    }

    /**
     * Locate Image by its alt text
     * @param text - Accepts String
     * @return     - Return an instance of SyscoLabPW
     */
    public SyscoLabPW findByAltText(String text) {
        locator = page.getByAltText(text);
        return this;
    }

    /**
     * Locate Image by its placeholder value
     * @param text - Accepts String
     * @return     - Return an instance of SyscoLabPW
     */
    public SyscoLabPW findByPlaceHolder(String text) {
        locator = page.getByPlaceholder(text);
        return this;
    }

    /**
     * Click on a drop-down by 'Option' Value ; by simply passing the Drop Down Locator + Value
     * @param locator       - Accepts SyscoLabPW type locator
     * @param valueToSelect - Accepts String
     */
    public void clickDropDownNth(SyscoLabPW locator, String valueToSelect) {
        this.locator.click();
        this.locator.selectOption(valueToSelect);
    }

    /**
     * Click on a drop-down by index value ; by simply passing the Drop-Down Locator + Index Eg:- //Select - option
    * @param locator - Accepts the Drop-Down  element locator
     * @param index  - Index of the value
     */
    public void clickDropDownNth(SyscoLabPW locator, int index) {
        this.locator.click();
        this.locator.selectOption(new SelectOption().setIndex(index));
    }

    /**
     * Click on an element by index when multiple matching values are found
     * @param locator - Accepts the Multi-Element  element locator
     * @param index   - Index of the value
     */

    public void clickMultiElemNth(SyscoLabPW locator, int index) {
        this.locator.nth(index).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        this.locator.nth(index).click();
    }

    /**
     * SendKeys on an element by index when multiple matching values are found
     * @param locator - Accepts SyscoLabPW type locator
     * @param index   - Accepts index of the element
     * @param value   - Accepts String
     */
    public void sendKeysMultiElemNth(SyscoLabPW locator, int index, String value) {
        this.locator.nth(index).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        this.locator.nth(index).fill(value);
    }

    /**
     * Locating an elements by its index value when more than 1 match found
     * @param locator - Accepts SyscoLabPW type
     * @param index   - Index of the element to be located
     * @return        - Returns an instance of SyscoLabPW
     */
    public SyscoLabPW findMultiElemNth(SyscoLabPW locator,int index){
        this.locator = this.locator.nth(index);
        return this;
    }
    /**
     * Find the first match in a list of values Eg:- //Ul - li
     * @param locator - Accepts SyscoLabPW type
     */
    public void clickFirst(SyscoLabPW locator) {
        this.locator.first().click();
    }

    /**
     * Find the last match in a list of values
     * @param locator - Accepts SyscoLabPW type
     */
    public void clickLast(SyscoLabPW locator) {
        this.locator.last().click();
    }

    /**
     * Find the first match in a list of values
     * @param locator - Accepts String type
     */
    public void clickFirst(String locator) {
        page.locator(locator).first().click();
    }

    /**
     * Find the last match in a list of values
     * @param locator - Accepts String type
     */
    public void clickLast(String locator) {
        page.locator(locator).last().click();
    }
/**============================================ Mouse Clicks ============================ **/

    /**
     * Clicks on the element
     * @param locator - Accepts SyscoLabPW type locators
     */
    public void click(SyscoLabPW locator) {
        this.locator.click(new Locator.ClickOptions().setTimeout(120000));
    }

    /**
     * Click on the element
     * @param locator - Accepts String type locators ; xpath
     */
    public void click(String locator) {
        page.click(locator, new Page.ClickOptions().setTimeout(120000));
    }

    /**
     * Clicks on the element by id
     * @param id  - Accepts id of the element
     */
    public void clickById(String id) {
        findById(id);
        locator.click();
    }

    /**
     * Performs double click on the element
     * @param locator - Accepts SyscoLabPW type locators
     */
    public void doubleClick(SyscoLabPW locator) {
        this.locator.dblclick();
    }

    /**
     * Performs left-click on the element
     * @param locator - Accepts SyscoLabPW type locators
     */
    public void leftClick(SyscoLabPW locator) {
        this.locator.click(new Locator.ClickOptions().setButton(MouseButton.LEFT));
    }

    /**
     * Performs right-click on the element
     * @param locator - Accepts SyscoLabPW type locators
     */
    public void rightClick(SyscoLabPW locator) {
        this.locator.click(new Locator.ClickOptions().setButton(MouseButton.RIGHT));
    }

    /**
     * Performs middle-click on the element
     * @param locator - Accepts SyscoLabPW type locators
     */

    public void middleClick(SyscoLabPW locator) {
        this.locator.click(new Locator.ClickOptions().setButton(MouseButton.MIDDLE));
    }

    /**
     * Performs force-click on the element
     * @param locator - Accepts SyscoLabPW type locators
     */
    public void forceClick(SyscoLabPW locator) {
        this.locator.click(new Locator.ClickOptions().setForce(true));
    }

    /**
     * Checks checkbox element
     * @param locator - Accepts SyscoLabPW type locators
     */
    public void checkBoxClick(SyscoLabPW locator) {
        this.locator.check();
    }

    /**
     * Performs mouse-hovering over the element
     * @param locator - Accepts SyscoLabPW type locators
     */
    public void mouseHover(SyscoLabPW locator) {
        this.locator.hover();
    }


    /** ============================ Interactions =============================**/

    /**
     * Navigate to provided url
     * @param URL - Accepts url as String
     */
    public void goTo(String URL) {
        page.bringToFront();
        page.navigate(URL);
    }

    /**
     * Closes the current page
     */
    public void close() {
        page.close();
    }

    /**
     * Navigate one page back from the current page
     */
    public void goBack() {
        page.goBack();
    }

    /**
     * Navigate one page forward from the current page
     */
    public void goForward() {
        page.goForward();
    }

    /**
     * Click on an element which opens in new tab
     * @param locator - Accepts SyscoLabPW type locator
     */
    public void switchTabOnClick(SyscoLabPW locator) {
        this.page = page.waitForPopup(() -> {
            this.locator.click();
            System.out.println();
        });
    }

    /**
     * Switches between tabs/pages , used when tabs/pages are automatically opened due to an element click
     * @param Index  - Accepts index of the element
     */
    public void switchToTab(int Index) {
        this.page = page.context().pages().get(Index);
    }

    /**
     * Switches to the default or the parent tab
     */
    public void switchToDefault() {
        this.page = page.context().pages().get(0);
    }

    /**
     * Performs page refresh and waits until page load completed and network becomes idle
     */
    public void refresh() {
        page.bringToFront();
        page.reload(new Page.ReloadOptions().setTimeout(65000));
        this.page.waitForLoadState(LoadState.LOAD);
        this.page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    /**
     * Scroll to bottom of the current page
     */
    public void scrollBottom() {
        page.evaluate("window.scrollTo(0, document.body.scrollHeight)");
    }

    /**
     * Input values to provided Xpath
     * @param xPath - Accepts xPath as String
     * @param value - Accepts value as String
     */
    public void sendKeys(String xPath, String value) {
        page.bringToFront();
        page.fill(xPath, value);
    }

    /**
     * Input values to provided locator element
     * @param locator - Accepts SyscoLabPW type locator
     * @param value   - Accepts value as String
     */
    public void sendKeys(SyscoLabPW locator, String value) {
        page.bringToFront();
        this.locator.fill(value);
    }

    /**
     * Type values provided into the locator element (mimics user typing)
     * @param locator - Accepts SyscoLabPW type
     * @param value   - Accepts value as String
     */
    public void typeInto(SyscoLabPW locator, String value) {
        this.locator.type(value);
    }

    /**
     * Press KeyBoard Key on a given locator element
     * @param locator - Accepts SyscoLabPW type
     * @param Key     - Accepts String in the format of Camel Case Eg- "Enter" , "Shift+A"
     */
    public void pressKeyBoardKey(SyscoLabPW locator, String Key) {
        this.locator.press(Key);
    }

    /**
     * Press KeyBoard Key
     * @param Key - Accepts String in the format of Camel Case Eg- "Enter" , "Shift+A"
     */
    public void pressKeyBoardKey(String Key) {
        page.bringToFront();
        page.keyboard().press(Key);
    }

    /**
     * Returns text of the provided locator element
     * @param locator - Accepts SyscoLabPW type
     * @return        - Return a String
     */
    public String getText(SyscoLabPW locator) {
        return this.locator.textContent();
    }

    /**
     * Returns page title of the current page
     * @return - Returns page title as String
     */
    public String getTitle()  {
        page.waitForLoadState(LoadState.LOAD, new Page.WaitForLoadStateOptions().setTimeout(70000));
        return page.title();
    }

    /**
     * Checks if element is visible
     * @param locator - Accepts SyscoLabPW type
     * @return        - Rerturns a boolean value
     */
    public boolean elemVisibility(SyscoLabPW locator) {
        return this.locator.isVisible();
    }

    /**
     * Checks if element is checked
     * @param locator - Accepts SyscoLabPW type
     * @return        - Rerturns a boolean value
     */
    public boolean isChecked(SyscoLabPW locator) {
        return this.locator.isChecked();
    }

    /**
     * Checks if element is checked when multiple matching elements present identified by index
     * @param locator - Accepts SyscoLabPW type
     * @param index   - Accepts index of the element
     * @return        - Rerturns a boolean value
     */
    public boolean isChecked(SyscoLabPW locator, int index) {
        return this.locator.nth(index).isChecked();
    }

    /**
     * Checks if element is enabled
     * @param locator - Accepts SyscoLabPW type
     * @return        - Rerturns a boolean value
     */
    public boolean isEnabled(SyscoLabPW locator) {
        return this.locator.isEnabled();
    }

    /**
     * Execute any JS script in general
     * @param jsScript - Accepts a java script as String
     */
    public void executeJS(String jsScript) {
        page.evaluate(jsScript);
    }

    /**
     * Perform Send Keys using JS , used when needed to perform forcefully
     * @param xpath - Accepts xpath as String
     * @param value - Accepts value as String
     */
    public void sendKeysJS(String xpath, String value) {
        page.evaluate("document.evaluate(\"" + xpath + "\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.value = \"" + value + "\";");
    }

    /**
     * Perform Click using JS ,used when needed to click forcefully
     * @param xpath - Accepts xpath as String
     */
    public void clickJS(String xpath) {
        page.evaluate("document.evaluate(\"" + xpath + "\",document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.click();");
    }

    /**
     * Upload a File
     * @param fileUploadLocator  - Accepts the String locator of upload button
     * @param uploadFileLocation - Accepts String path of the file to be uploaded
     */
    public void fileUpload(String fileUploadLocator, String uploadFileLocation) {
        page.locator(fileUploadLocator).setInputFiles(Paths.get(uploadFileLocation));
    }

    /**
     * Upload a File
     * @param fileUploadLocator  - Accepts the SyscoLabPW locator of upload button
     * @param uploadFileLocation - Accepts String path of the file to be uploaded
     */
    public void fileUpload(SyscoLabPW fileUploadLocator, String uploadFileLocation) {
        this.locator.setInputFiles(Paths.get(uploadFileLocation));
    }

    /**
     * Download File to Default location
     * @param locator - Accepts String locator of download button
     * Note - Has an issue in changing download location and the file format been downloaded.
     */
    public void fileDownload(String locator) {
        Download download = page.waitForDownload(new Page.WaitForDownloadOptions().setTimeout(50000), () -> {
            page.locator(locator).click();
        });
        // download.saveAs(Paths.get("src/main/resources"));
        LoggerUtil.logINFO("File Downloaded to "+download.path());
    }

    /**
     * Download File to Default location
     * @param locator - Accepts SyscoLabPW locator of download button
     * Note -  Has an issue in changing download location and the file format been downloaded.
     */
    public void fileDownload(SyscoLabPW locator) {
        Download download = page.waitForDownload(new Page.WaitForDownloadOptions().setTimeout(50000), () -> {
            this.locator.click();
        });
        // download.saveAs(Paths.get("src/main/resources"));
        LoggerUtil.logINFO("File Downloaded to "+download.path());
    }

    /**
     * Accepts the Browser Alert
     * @param locator  - Accepts SyscoLabPW type locator
     */
    public void clickWithAlertAccept(SyscoLabPW locator){
        page.onDialog(dialog -> dialog.accept());
        this.locator.click();
    }

    /**
     * Dismisses the Browser Alert
     */
    public void alertDismiss(){
        page.onDialog(dialog -> {
            dialog.dismiss();
        });
    }

    /**
     * Screenshots the whole page and saves to User defined location
     * @param pathToSaveImage - Accepts a String path
     * @throws IOException    - Throws IOException
     */
    public void screenShotPage(String pathToSaveImage) throws IOException {
        createFile(pathToSaveImage,"/PageScreenShot_");
//        page.screenshot(new Page.ScreenshotOptions().setPath(Path.of(file.toString())).setFullPage(true));
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(file.toString())).setFullPage(true)); //Java 8 method
    }

    /**
     * Captures the active page screenshot and returns a byte
     *
     * @return - byte array
     */
    public byte[] screenShot() {
        return page.screenshot();
    }

    /**
     * Screenshots an element and saves to User defined locations
     * @param locator         - Accepts SyscoLabPW type locator
     * @param pathToSaveImage - Accepts a String path
     * @throws IOException    - Throws IOException
     */
    public void screenShotElement(SyscoLabPW locator,String pathToSaveImage) throws IOException {
        createFile(pathToSaveImage,"/ElemScreenShot_");
        //this.locator.screenshot(new Locator.ScreenshotOptions().setPath(Path.of(file.toString())));
        this.locator.screenshot(new Locator.ScreenshotOptions().setPath(Paths.get(file.toString()))); //Java 8 method
    }
    /** Waits for the given time
     * @param Time - MilliSeconds
     */
    public void waitFor(double Time) {
        page.waitForTimeout(Time);
    }

    /**
     * Start Debugging mode through Playwright Code Inspector
     */
    public void debugFromHere() {
        page.pause();
    }

    /**
     * Stops loading Media formats specified for the page
     */
    public void abortMedia() {
        page.route("**/*.{png,jpg,jpeg,svg}", route -> route.abort());
    }

    /**
     * Reset Media Abort with formats specified for the page
     */
    public void resetMediaAbort() {
        page.unroute("**/*.{png,jpg,jpeg,svg}");
    }
}
