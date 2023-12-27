package com.syscolab.qe.core.swms.util;

import com.google.common.collect.ImmutableList;
import com.sysco.javaprofile.functions.JavaDriver;
import com.syscolab.qe.core.swms.common.Attributes;
import com.syscolab.qe.core.swms.common.OracleFormsConstants;
import com.syscolab.qe.core.ui.SyscoLabUI;
import com.syscolab.qe.core.ui.web.SyscoLabWUI;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * This is the Base Page class for Oracle Forms
 * @author Mohammed Rifad
 */
public class OracleFormsBasePage {
    protected static JavaDriver driver;

    public int indexDelta = 4;
    protected static SyscoLabUI syscoLabUI;

    private static final String GET_NAME = "getName";
    private static final String MS_WITH = "ms with ";
    private static final String MS_INTERVAL = "ms interval";

    /**
     * This will return a java driver based on the passed driver title
     * @param driverTitle title of the driver
     * @return JavaDriver
     */
    protected JavaDriver loadSWMSLoginPage(String driverTitle) {
        OracleDriverSetUpUtil driverSetUpUtil = new OracleDriverSetUpUtil();
        try {
            driver = driverSetUpUtil.launchJavaDriver(driverTitle);
            driver.switchTo().window(driverTitle);
//            sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
        return driver;

    }

    /**
     * This will return a java driver based on the passed driver title and jnlp file path
     * @param driverTitle title of the driver
     * @param jnlpFilePath jnlp file path
     * @return JavaDriver
     */
    protected JavaDriver loadSWMSLoginPage(String driverTitle, String jnlpFilePath ) {
        OracleDriverSetUpUtil driverSetUpUtil = new OracleDriverSetUpUtil();
        try {
            driver = driverSetUpUtil.launchJavaDriver(driverTitle,jnlpFilePath);
            driver.switchTo().window(driverTitle);
//            sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
        return driver;

    }

    /**
     * This will return the web element passed by the locator
     * @param by locating mechanism and the selector
     * @return web element
     */
    protected WebElement findElement(By by) {
        return driver.findElement(by);

    }

    /**
     * This will return a list of web elements passed by the locator
     * @param by locating mechanism and the selector
     * @return list of web elements
     */
    protected List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    /**
     * This will click the element using the locator
     * @param by locating mechanism and the selector of the element to be clicked
     */
    protected void click(By by) {
        driver.findElement(by).click();
    }

    /**
     * This will click the element using the passed element index
     * @param elementIndex index of the element
     */
    protected void click(int elementIndex) {
        driver.findElements(By.cssSelector("*")).get(elementIndex + indexDelta).click();
    }

    /**
     * This will click okay lable
     */
    protected void clickOK() {
        driver.findElement( By.cssSelector( "[label='OK']" )).click();
    }

    /**
     * This will type in an element
     * @param by locating mechanism and the selector of the element to be typed into
     * @param value the string
     */
    protected void sendKeys(By by, String value) {
        driver.findElement(by).sendKeys(value);
    }

    /**
     * This will enter pressable keys which aren't texts
     * @param by locating mechanism and the selector of the element to which the key should be pressed
     * @param key Pressable keys
     */
    protected void sendKeys(By by, Keys key) {
        driver.findElement(by).sendKeys(key);
    }

    /**
     * This will type in an element after waiting
     * @param by locating mechanism and the selector of the element to be typed into
     * @param value Pressable keys
     */
    protected void sendKeysWithWait(By by, String value) {
        waitForElementManual(by, OracleFormsConstants.SWMS_MENU_LOADING_TIME_IN_SECONDS);
        driver.findElement(by).sendKeys(value);
    }

    /**
     * This will type in an element after waiting for the passed time
     * @param by locating mechanism and the selector of the element to which the key should be pressed
     * @param value Pressable keys
     * @param loadintTime waiting time
     */
    protected void sendKeysWithWait(By by, String value,int loadintTime) {
        waitForElementManual(by, loadintTime);
        driver.findElement(by).sendKeys(value);
    }

    /**
     * This will type in the element using the passed element index
     * @param elementIndex index of the element
     * @param key Pressable keys
     */
    protected void sendKeys(int elementIndex, Keys key) {
        driver.findElements(By.cssSelector("*")).get(elementIndex + indexDelta).sendKeys(key);
    }

    /**
     * This will type in the element using the passed element index
     * @param elementIndex index of the element
     * @param value text
     */
    protected void sendKeys(int elementIndex, String value) {
        driver.findElements(By.cssSelector("*")).get(elementIndex + indexDelta).sendKeys(value);
    }

    /**
     * This will type in the element using the passed element index after waiting
     * @param elementIndex index of the element
     * @param key Pressable keys
     */
    protected void sendKeysWithWait(int elementIndex, Keys key) {
        waitForElementManual(elementIndex, OracleFormsConstants.SWMS_MENU_LOADING_TIME_IN_SECONDS);
        driver.findElements(By.cssSelector("*")).get(elementIndex + indexDelta).sendKeys(key);
    }

    /**
     * This will type in the element using the passed element index after waiting for the passed time
     * @param elementIndex index of the element
     * @param key Pressable keys
     * @param loadingTime waiting time
     */
    protected void sendKeysWithWait(int elementIndex, Keys key,int loadingTime) {
        waitForElementManual(elementIndex, loadingTime);
        driver.findElements(By.cssSelector("*")).get(elementIndex + indexDelta).sendKeys(key);
    }


    /**
     * This will type in the element using the passed element index after waiting
     * @param elementIndex index of the element
     * @param value text
     */
    protected void sendKeysWithWait(int elementIndex, String value) {
        waitForElementManual(elementIndex, OracleFormsConstants.SWMS_MENU_LOADING_TIME_IN_SECONDS);
        driver.findElements(By.cssSelector("*")).get(elementIndex + indexDelta).sendKeys(value);
    }

    /**
     * This will type in the element using the passed element index after waiting for the passed time
     * @param elementIndex index of the element
     * @param value text
     * @param loadingTime waiting time
     */
    protected void sendKeysWithWait(int elementIndex, String value,int loadingTime) {
        waitForElementManual(elementIndex, loadingTime);
        driver.findElements(By.cssSelector("*")).get(elementIndex + indexDelta).sendKeys(value);
    }


    /**
     * Return true if the element is displayed
     * @param elementIndex web element index
     * @return true or false
     */
    protected boolean isDisplayed(int elementIndex) {
        return driver.findElements(By.cssSelector("*")).get(elementIndex + indexDelta).isDisplayed();
    }

    /**
     * Return true if the element is displayed
     * @param by locating mechanism and the selector of the element
     * @return true or false
     */
    protected boolean isDisplayed(By by) {
        return !driver.findElements(by).isEmpty();
    }

    /**
     * This will return the text of the element
     * @param by locating mechanism and the selector of the element
     * @return text
     */
    protected String getText(By by) {
        return driver.findElement(by).getText();

    }

    /**
     * This will return the text of the element
     * @param elementIndex index of the element
     * @return text
     */
    protected String getText(int elementIndex) {
        return driver.findElements(By.cssSelector("*")).get(elementIndex + indexDelta).getText();

    }

    /**
     * This will return the text of the element using the attribute
     * @param elementIndex index of the element
     * @param attribute name of the attribute
     * @return text
     */
    protected String getText(int elementIndex, String attribute) {
        return driver.findElements(By.cssSelector("*")).get(elementIndex + indexDelta).getAttribute(attribute);
    }

    /**
     * This will return the text of the message
     * @param elementIndex index of the element
     * @return text of the message
     */
    protected String getMessageText(int elementIndex) {
        return driver.findElements(By.cssSelector("*")).get(elementIndex + indexDelta).getAttribute("_items.getText").split("\\[")[1].split(",")[0];

    }

    /**
     * This will return the text by element index and index delta
     * @param elementIndex index of the element
     * @param indexDelta index delta /offset
     * @return text
     */
    protected String getText(int elementIndex,int indexDelta) {
        return driver.findElements(By.cssSelector("*")).get(elementIndex + indexDelta).getText();

    }

    /**
     * This will return the text by element index and index delta of an attribute
     * @param elementIndex index of the element
     * @param attribute attribute
     * @param indexDelta index delta/offset
     * @return text
     */
    protected String getText(int elementIndex, String attribute,int indexDelta) {
        return driver.findElements(By.cssSelector("*")).get(elementIndex + indexDelta).getAttribute(attribute);
    }

    /**
     * This will return the Message Text
     * @param elementIndex element index
     * @param indexDelta index delta/ offset
     * @return text
     */
    protected String getMessageText(int elementIndex,int indexDelta) {
        return driver.findElements(By.cssSelector("*")).get(elementIndex + indexDelta).getAttribute("_items.getText").split("\\[")[1].split(",")[0];

    }

    /**
     * This will find locators
     * @param locatorName locator name
     */
    public void findLocators(String locatorName) {
        int size = driver.findElements(By.cssSelector("*")).size();
        for (int i = 0; i < size; i++) {
            if (driver.findElements(By.cssSelector("*")).get(i).getAttribute(GET_NAME) != null) {
                if (driver.findElements(By.cssSelector("*")).get(i).getAttribute(GET_NAME).contains(locatorName)) {

                    String textField = driver.findElements(By.cssSelector("*")).get(i).getAttribute(GET_NAME);
                    System.out.println("Location of the Locator::" + textField + "......index ....:" + i + "\n");
                }
            }
        }
    }

    /**
     * This will get Text values by X attribute
     * @param columnValue column index
     * @return Texts
     */
    public ArrayList<String> getTextsByXValue(int columnValue) {
        ArrayList<String> arrayList = new ArrayList<>();
        int size = driver.findElements(By.cssSelector("*")).size();
        for (int i = 0; i < size; i++) {
            if (driver.findElements(By.cssSelector("*")).get(i).getAttribute("X") != null) {
                if (driver.findElements(By.cssSelector("*")).get(i).getAttribute("X").equals(String.valueOf(columnValue))) {

                    arrayList.add(driver.findElements(By.cssSelector("*")).get(i).getAttribute(GET_NAME));
//                    System.out.println("Location of the Locator::" + textField + "......index ....:" + i + "\n");
                }
            }
        }
        return arrayList;
    }

    /**
     * This will return the text count by X attribute
     * @param columnValue column index
     * @return text count
     */
    public int getElementCountByXValue(int columnValue) {
        ArrayList<Object> arrayList = new ArrayList<>();
        int size = driver.findElements(By.cssSelector("*")).size();
        for (int i = 0; i < size; i++) {
            if (driver.findElements(By.cssSelector("*")).get(i).getAttribute("X") != null) {
                System.out.println(driver.findElements(By.cssSelector("*")).get(i).getAttribute("X"));
                if (Integer.parseInt(driver.findElements(By.cssSelector("*")).get(i).getAttribute("X"))==(columnValue)) {
                    arrayList.add(driver.findElements(By.cssSelector("*")).get(i));
//                    System.out.println("Location of the Locator::" + textField + "......index ....:" + i + "\n");
                }
            }
        }
        return arrayList.size();
    }

    /**
     * This will wait for the attribute of the passed element by element index
     * @param elementIndex element index
     * @param millis waiting time in ms
     * @param attribute attribute
     * @return element
     */
    protected String waitForAttribute(int elementIndex, int millis, String attribute){
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofMillis(millis))
                .pollingEvery(Duration.ofNanos(200)).ignoring(IndexOutOfBoundsException.class);

        return wait.until(new Function<WebDriver, String>() {
            @Override
            public String apply(WebDriver driver) {
                String value = driver.findElements(By.cssSelector("*")).get(elementIndex + indexDelta).getAttribute(attribute);
                if (value!= null && value.trim().equals("")){
                    throw new IndexOutOfBoundsException();
                }else{
                    return value;
                }
            }
        });
    }

    /**
     * This will return the element after waiting
     * @param by locating mechanism and the selector
     * @param millis waiting time ms
     * @return element
     */
    protected String waitForAttribute(By by, int millis){               //***********
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofMillis(millis))
                .pollingEvery(Duration.ofNanos(200)).ignoring(IndexOutOfBoundsException.class);

        return wait.until(new Function<WebDriver, String>() {
            @Override
            public String apply(WebDriver driver) {
                String value = driver.findElement(by).getText();
                if (value.trim().equals("")){
                    throw new IndexOutOfBoundsException();
                }else{
                    return value;
                }
            }
        });
    }

    /**
     * This will wait for the attribute of the passed element by web element
     * @param element element
     * @param millis waiting time in ms
     * @param attribute attribute
     * @return element
     */
    protected String waitForAttribute(WebElement element, int millis, String attribute){
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofMillis(millis))
                .pollingEvery(Duration.ofNanos(200)).ignoring(IndexOutOfBoundsException.class);

        return wait.until(new Function<WebDriver, String>() {
            @Override
            public String apply(WebDriver driver) {
                String value = element.getAttribute(attribute);
                if(value!= null && value.trim().equals("")){
                    throw new IndexOutOfBoundsException();
                }else{
                    return value;
                }
            }
        });
    }

    /**
     * This will wait for the element by locator and time
     * @param by locating mechanism and the selector
     * @param seconds seconds in ms
     */
    protected void waitForElement(By by,int seconds){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        //wait.until(ExpectedConditions.elementToBeClickable(by),10);
    }

    /**
     * This will wait for the element by element index and time
     * @param elementIndex element index
     * @param seconds seconds in ms
     */
    protected void waitForElement(int elementIndex,int seconds){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        //  wait.until(ExpectedConditions.elementToBeSelected(driver.findElements(By.cssSelector("*")).get(elementIndex + indexDelta)));
    }

    /**
     * This will wait element to be visible
     * @param elementIndex element index
     * @param seconds time in ms
     */
    protected void waitForElementToBeVisible(int elementIndex,int seconds){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
//        wait.until(ExpectedConditions.visibilityOf(driver.findElements(By.cssSelector("*")).get(elementIndex + indexDelta)));
    }

    /**
     * This will wait for expected status bar message to load and return the element
     * @param elementIndex element index
     * @param millis waiting time in ms
     * @param expectedMessages exepected message
     * @return element
     */
    protected String waitForExpectedStatusBarMessages(int elementIndex,int millis,String... expectedMessages){
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofMillis(millis))
                .pollingEvery(Duration.ofNanos(200)).ignoreAll(ImmutableList.of(IndexOutOfBoundsException.class,NullPointerException.class));

        return wait.until(new Function<WebDriver, String>() {
            @Override
            public String apply(WebDriver driver) {
                try {
                    String value = driver.findElements(By.cssSelector("*")).get(elementIndex + indexDelta).getAttribute(Attributes.GET_TEXT_1).split(",")[0].substring(1);
                    for (String message: expectedMessages){
                        if(message.equalsIgnoreCase(value.trim())){
                            return value;
                        }
                    }
                }
                catch(NullPointerException e){

                }
                throw new IndexOutOfBoundsException();
            }
        });
    }

    /**
     * This will wait until the passed wait time for element index passed element to load
     * @param elementIndex element index
     * @param totalWaitTime wait time in ms
     */
    protected void waitForElementManual(int elementIndex,int totalWaitTime){
        int milliSeconds=0;
        int interval = 50;
        while(milliSeconds<=totalWaitTime){
            try {
                driver.findElements(By.cssSelector("*")).get(elementIndex + indexDelta);
                return;
            }
            catch(IndexOutOfBoundsException e) {
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                }
                milliSeconds += interval;
            }
        }
        throw new TimeoutException("Waited for element with index : "+elementIndex+" for " + totalWaitTime + MS_WITH+interval+MS_INTERVAL);
    }

    /**
     * This will wait until the passed wait time for the element passed by locator to load
     * @param by locating mechanism and the selector
     * @param totalWaitTime wait time in ms
     */
    protected void waitForElementManual(By by,int totalWaitTime){
        int milliSeconds=0;
        int interval = 50; //interval in ms
        while(milliSeconds<=totalWaitTime){
            try {
                driver.findElements(by);
                return;
            }
            catch(java.util.NoSuchElementException e) {
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                }
                milliSeconds += interval;
            }
        }
        throw new TimeoutException("Waited for element for " + totalWaitTime + MS_WITH+interval+MS_INTERVAL);
    }

    /**
     * This will wait the given time to attribute of the element passed by the element index
     * @param elementIndex element index
     * @param totalWaitTime total wait time in ms
     * @param attribute attribute
     */
    protected void waitForAttributeManual(int elementIndex,int totalWaitTime,String attribute){
        int milliSeconds=0;
        int interval = 50; //interval in ms
        while (milliSeconds <= totalWaitTime) {
            try {
                String value = driver.findElements(By.cssSelector("*")).get(elementIndex + indexDelta).getAttribute(attribute);
                if (!value.equals("")) {
                    return;
                }
            } catch (IndexOutOfBoundsException e) {
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                }
                milliSeconds += interval;
            }
        }
        throw new TimeoutException("Waited for attribute : "+attribute+" of element with index : "+elementIndex+" for " + totalWaitTime + MS_WITH+interval+MS_INTERVAL);
    }

    /**
     * This will wait the given time to attribute of the element passed by the locator
     * @param by locating mechanism and the selector
     * @param totalWaitTime total wait time in ms
     * @param attribute attribute
     */
    protected void waitForAttributeManual(By by,int totalWaitTime,String attribute){
        int milliSeconds=0;
        int interval = 50; //interval in ms
        while (milliSeconds <= totalWaitTime) {
            try {
                String value = driver.findElement(by).getAttribute(attribute);
                if (!value.equals("")) {
                    return;
                }
            } catch (IndexOutOfBoundsException| java.util.NoSuchElementException e) {
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                }
                milliSeconds += interval;
            }
        }
        throw new TimeoutException("Waited for attribute : "+attribute+" of element for " + totalWaitTime + MS_WITH+interval+MS_INTERVAL);
    }


    /**
     * Search for the given text in all the elements in the page
     * @param text text
     * @param millis waiting time ms
     * @return text
     */
    public String waitForTextToBeDisplayed(String text, int millis){
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofMillis(millis))
                .pollingEvery(Duration.ofNanos(2000));
        return wait.until(new Function<WebDriver, String>() {
            @Override
            public String apply(WebDriver webDriver) {
                List<WebElement> elements = driver.findElements(By.cssSelector("*"));
                for(WebElement e : elements){
                    if(e.getText()!=null && e.getText().contains(text)){
                        return e.getText();
                    }
                }
                return null;
            }
        });
    }

    /**
     * Search for the given text in all the elements in the page using java driver instance passed
     * @param javaDriver  java driver instance
     * @param text text
     * @param millis waiting time ms
     * @return text
     */
    public String waitForTextToBeDisplayed(JavaDriver javaDriver, String text, int millis){
        driver = javaDriver;
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofMillis(millis))
                .pollingEvery(Duration.ofNanos(200));
        return wait.until(new Function<WebDriver, String>() {
            @Override
            public String apply(WebDriver webDriver) {
                List<WebElement> elements = driver.findElements(By.cssSelector("*"));
                for(WebElement e : elements){
                    if(e.getText()!=null && e.getText().contains(text)){
                        return e.getText();
                    }
                }
                return null;
            }
        });
    }

    /**
     * This will return true if the header title is displayed
     * @param headerName name of the header
     * @param millis waiting time in ms
     * @return true or false
     */
    public boolean isHeaderTitleDisplayed(String headerName, int millis){
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofMillis(millis))
                .pollingEvery(Duration.ofNanos(200));

        List<WebElement> elements = driver.findElements(By.cssSelector("*"));
        for(WebElement e : elements) {
            if (e.getText()!=null && e.getText().equals(headerName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Search for the given text in the element passed by element index
     * @param elementIndex index of the element
     * @param text text
     * @param millis waiting time ms
     * @return text
     */
    public String waitForTextToBeDisplayed(int elementIndex, String text,int millis){
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofMillis(millis))
                .pollingEvery(Duration.ofNanos(200)).ignoring(org.openqa.selenium.NoSuchElementException.class);
        return wait.until(new Function<WebDriver, String>() {
            @Override
            public String apply(WebDriver webDriver) {
                WebElement element = driver.findElements(By.cssSelector("*")).get(elementIndex+indexDelta);
                if(element.getText()!=null && element.getText().contains(text)){
                    return element.getText();
                }
                throw new org.openqa.selenium.NoSuchElementException("");
            }
        });
    }

    /**
     * Search for the given text in the element passed by element index using java driver instance passed
     * @param javaDriver  java driver instance
     * @param elementIndex index of the element
     * @param text text
     * @param millis waiting time ms
     * @return text
     */
    public String waitForTextToBeDisplayed(JavaDriver javaDriver, int elementIndex, String text,int millis){
        driver = javaDriver;
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofMillis(millis))
                .pollingEvery(Duration.ofNanos(200)).ignoring(org.openqa.selenium.NoSuchElementException.class);
        return wait.until(new Function<WebDriver, String>() {
            @Override
            public String apply(WebDriver webDriver) {
                WebElement element = driver.findElements(By.cssSelector("*")).get(elementIndex+indexDelta);
                if(element.getText()!=null && element.getText().contains(text)){
                    return element.getText();
                }
                throw new org.openqa.selenium.NoSuchElementException("");
            }
        });
    }

    /**
     * This will suspend execution of the current thread for a specified period
     * @param milliSeconds time for the sleep in ms
     */
    public void sleep(int milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * This will return the status code of the printed report in the job id retrieval url
     * @return status code
     * @throws IOException throwing IOException
     */
    public int getStatusCodeOfPrintedReport() throws IOException {
        int code = 0;
        syscoLabUI = new SyscoLabWUI("chrome");
        syscoLabUI.maximizeWindow();
        syscoLabUI.navigateTo(OracleFormsConstants.JOB_ID_RETRIEVAL_URL);
        List<WebElement> rowCells = syscoLabUI.findElements(By.className("OraTableCellTextBand"));
        WebElement firstCell = rowCells.get(0).findElement(By.xpath("span"));
        String jobId = syscoLabUI.getText(firstCell);
        String stringUrl = "http://lx239wl:9002/reports/rwservlet/getjobid"+jobId+"?server=rep_wls_reports_ol6-forms_asinst_1";
        URL url = new URL(stringUrl);
        syscoLabUI.navigateTo(stringUrl);
        String title = syscoLabUI.getTitle();

        if(title.equals("")){
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
            code = connect.getResponseCode();
        }
        syscoLabUI.quit();
        return code;
    }

    /**
     * This will return the status code of the printed report in the passed url
     * @param url url
     * @return status code
     * @throws IOException throwing IOException
     */
    public int getStatusCodeOfPrintedReport(String url) throws IOException {
        int code = 0;
        syscoLabUI = new SyscoLabWUI("chrome");
        syscoLabUI.maximizeWindow();
        syscoLabUI.navigateTo(url);
        List<WebElement> rowCells = syscoLabUI.findElements(By.className("OraTableCellTextBand"));
        WebElement firstCell = rowCells.get(0).findElement(By.xpath("span"));
        String jobId = syscoLabUI.getText(firstCell);
        String stringUrl = "http://lx239wl:9002/reports/rwservlet/getjobid"+jobId+"?server=rep_wls_reports_ol6-forms_asinst_1";
        URL naviagationUrl = new URL(stringUrl);
        syscoLabUI.navigateTo(stringUrl);
        String title = syscoLabUI.getTitle();

        if(title.equals("")){
            HttpURLConnection connect = (HttpURLConnection) naviagationUrl.openConnection();
            code = connect.getResponseCode();
        }
        syscoLabUI.quit();
        return code;
    }
}
