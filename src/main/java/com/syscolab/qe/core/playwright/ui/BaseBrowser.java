package com.syscolab.qe.core.playwright.ui;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.options.Cookie;
import com.syscolab.qe.core.playwright.common.PlayWrightConstants;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kasun Herath
 */

public class BaseBrowser extends SyscoLabPW {
    Browser BROWSER1;
    Browser BROWSER2;
    BrowserContext context1;
    BrowserContext context2;
    ThreadLocalContextMap contexts_browser1 = new ThreadLocalContextMap();
    ThreadLocalContextMap contexts_browser2 = new ThreadLocalContextMap();
    protected static File file;
    List<Cookie> cookies;


    public BaseBrowser() {
    }

    BaseBrowser(Page page) {
        super(page);
    }

    /**
     * This method use retrievePage() to create a page with First Browser
     *
     * @param contextKey Accepts unique key for new context , duplicate keys for same context
     * @return - Return a page through retrievePage()
     */
    public BaseBrowser getPage(String contextKey) {
        if (BROWSER1 == null)
            BROWSER1 = new PlayWrightFactory(PlayWrightConstants.SYSTEM_BROWSER1).browser;

        return retrievePage(BROWSER1, contexts_browser1,  contextKey);
    }

    /**
     * This method use retrievePage() to create a page with Second Browser
     *
     * @param contextKey Accepts unique key for new context , duplicate keys for same context
     * @return - Return a page through retrievePage()
     */
    public BaseBrowser getPageFromExtraBrowser(String contextKey) {
        if (BROWSER2 == null)
            BROWSER2 = new PlayWrightFactory(PlayWrightConstants.SYSTEM_BROWSER2).browser;

        return retrievePage(BROWSER2, contexts_browser2, contextKey);
    }

    /**
     * This method handles creating page per context , including reference to Store Page for Screenshot capturing
     * ITest-Context is Used to get results of entire tests
     *
     * @param browser    - Retrieved from PlayWrightConstants
     * @param contextMap - Thread Safe Map
     * @param contextKey - Accepts String
     * @return - Returns a new page
     */
    public BaseBrowser retrievePage(Browser browser, ThreadLocalContextMap contextMap, String contextKey) {
        Page pageReference;
        BrowserContext browserContext;
        if (contextMap.containsKey(contextKey)) {
            browserContext = contextMap.get(contextKey);
        } else {
            contextMap.put(contextKey, browser.newContext(new Browser.NewContextOptions().setIgnoreHTTPSErrors(true)));
            browserContext = contextMap.get(contextKey);
        }
        pageReference = browserContext.newPage();
        if (PlayWrightConstants.ABORT_MEDIA == true)
            pageReference.route("**/*.{png,jpg,jpeg,svg}", route -> route.abort());
        ITestContext iTestContext = Reporter.getCurrentTestResult().getTestContext();
        iTestContext.setAttribute("SyscoLabPW_Reference", pageReference);
        return new BaseBrowser(pageReference);
    }

    public BrowserContext getContextsBrowser1(String Key) {
        return contexts_browser1.get(Key);
    }

    public BrowserContext getContextsBrowser2(String Key) {
        return contexts_browser2.get(Key);
    }


    /**
     * Stores session info / Cookies for a given context
     *
     * @param fileCreationPath - Session file creation path
     * @param FileName         - File Name
     * @param context          - Which context
     * @throws IOException - Throws IOException
     */
    public void storeSessionInfo(String fileCreationPath, String FileName, BrowserContext context) throws IOException {
        file = new File(System.getProperty("user.dir") +"/"+ fileCreationPath + FileName + ".json");
        //context.storageState(new BrowserContext.StorageStateOptions().setPath(Path.of(file.toString())));
        context.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get(file.toString()))); //Java 8
    }

    /**
     * Injecting Cookies Dynamically to the BrowserContext , stored sessions may not work if expired , regenerate new ones
     *
     * @param fileCreationPath - Session file creation path
     * @param FileName         - File Name
     * @param context          - Which context
     * @return - Returns cookies for current browser session
     * @throws IOException - Throws IOException
     */
    public List sessionReUse(String fileCreationPath, String FileName, BrowserContext context) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File(System.getProperty("user.dir") +"/"+ fileCreationPath + FileName + ".json");
        JsonNode root = mapper.readValue(jsonFile, JsonNode.class);

        JsonNode cookiesArray = root.get("cookies");
        /*List<Cookie>*/
        cookies = new ArrayList<>();
        for (JsonNode cookieNode : cookiesArray) {
            String name = cookieNode.get("name").asText();
            String value = cookieNode.get("value").asText();
            String domain = cookieNode.get("domain").asText();
            String path = cookieNode.get("path").asText();
            long expires = cookieNode.get("expires").asLong();

            Cookie cookie = new Cookie(name, value)
                    .setDomain(domain)
                    .setPath(path)
                    .setExpires(expires);

            cookies.add(cookie);
        }
        context.addCookies(cookies);
        return cookies;
    }

    public void deleteSession(String path, String fileName) {
        file = new File(path + fileName + ".json");
        file.delete();
    }

    /**
     * Points to the page which is taken for screenshot capturing for QCenter Reporting
     *
     * @param page - Accepts SyscoLabPW type
     */
    public void changeReportingPageContext(SyscoLabPW page) {
        ITestResult itr = Reporter.getCurrentTestResult();
        itr.setAttribute("SyscoLabPW_Reference", page);
    }

    /**
     * Common Method for Trace Start
     *
     * @param contextKey - Accepts String of the Context Key
     * @param contextMap - Accepts thread-local map for the Browser1 and Browser2 contexts
     */
    void traceStart(String contextKey, ThreadLocalContextMap contextMap) {
        contextMap.get(contextKey).tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true));
    }

    void traceStop(String contextKey, ThreadLocalContextMap contextMap) {
        contextMap.get(contextKey).tracing().stop(new Tracing.StopOptions().setPath(Paths.get("traceResults.zip")));
    }

    /**
     * Starts Tracing for the Given Context in Browser1
     *
     * @param contextKey
     */
    public void startTracing(String contextKey) {
        traceStart(contextKey, contexts_browser1);
    }

    /**
     * Stops Tracing for the given context in Browser1 .zip file stored to Project Directory
     * TO see results run mvn command from CMD  -> mvn exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="show-trace fileName.zip"
     *
     * @param contextKey
     */
    public void stopTracing(String contextKey) {
        traceStop(contextKey, contexts_browser1);
    }

    /**
     * Starts Tracing for the Given Context in Browser2
     *
     * @param contextKey
     */
    public void startTracingExtraBrowser(String contextKey) {
        traceStart(contextKey, contexts_browser2);
    }

    /**
     * Stops Tracing for the given context in Browser2 .zip file stored to Project Directory
     * TO see results run mvn command from CMD  -> mvn exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="show-trace fileName.zip"
     *
     * @param contextKey
     */
    public void stopTracingExtraBrowser(String contextKey) {
        traceStop(contextKey, contexts_browser2);
    }
}
