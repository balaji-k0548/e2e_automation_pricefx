package com.syscolab.qe.core.sap.utils;
import autoitx4java.AutoItX;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.syscolab.qe.core.common.LoggerUtil;

/**
 * This is the util class for SAP UI
 * @author Mohammed Rifad
 */
public class SapUi {
    public ActiveXComponent SAPROTWr;
    public ActiveXComponent GUIApp;
    public ActiveXComponent Connection;
    public ActiveXComponent Obj2;
    public ActiveXComponent sapFrontEnd;
    public ActiveXComponent Session;
    Dispatch ROTEntry;
    Variant Value;
    Variant ScriptEngine;
    AutoItX x;
    String cnt = "0";
    static final String SAP_LOGON_750 = "SAP Logon 750";


    /**
     * Parameterised constructor for SapUi class
     * @param sapSession session of the SAP
     */
    public SapUi(ActiveXComponent sapSession) {
        this.Session = sapSession;
    }

    /**
     * Default constructor of the class
     */
    public SapUi() {
    }

    /**
     * This will open login pad
     * @param applicationPath application path
     * @throws InterruptedException throwing InterruptedException
     */
    public void openLoginPad(String applicationPath) throws InterruptedException {
        this.x = new AutoItX();
        this.x.run(applicationPath);
        this.x.controlClick(SAP_LOGON_750, "", "1091", "left", 1, 21, 24);
        Thread.sleep(2000L);
        this.x.controlSend(SAP_LOGON_750, "", "1091", "C11");
        this.x.controlSend(SAP_LOGON_750, "", "", "{ENTER}");
    }

    /**
     * This will set SAP session
     * @return SAP session
     */
    public ActiveXComponent setSapSession() {
        ComThread.InitSTA();
        this.SAPROTWr = new ActiveXComponent("SapROTWr.SapROTWrapper");
        try {
            this.ROTEntry = this.SAPROTWr.invoke("GetROTEntry", "SAPGUI").toDispatch();
            this.ScriptEngine = Dispatch.call(this.ROTEntry, "GetScriptingEngine");
            this.GUIApp = new ActiveXComponent(this.ScriptEngine.toDispatch());
            this.Connection = new ActiveXComponent(this.GUIApp.invoke("Children", 0).toDispatch());
            this.Session = new ActiveXComponent(this.Connection.invoke("Children", 0).toDispatch());
        } catch (Exception var2) {
            LoggerUtil.logINFO(var2.getMessage().toString());
        }
        return this.Session;
    }

    /**
     * This will return the SAP session
     * @return SAP session
     */
    public ActiveXComponent getSapSession() {
        return this.Session;
    }

    /**
     * This will print the element id and find element in the UI
     * @param elementId element id
     * @return element
     */
    public ActiveXComponent findElement(String elementId) {
        LoggerUtil.logINFO("*********----****" + elementId);
        return new ActiveXComponent(this.Session.invoke("FindById", elementId).toDispatch());
    }

    /**
     * This will find element in the UI
     * @param elementId element id
     * @return element
     */
    public Dispatch findElementWithDispatch(String elementId) {
        return this.Session.invoke("findById", elementId).toDispatch();
    }

    /**
     * This will enter text to the element
     * @param elementId element id
     * @param value text value
     */
    public void enterText(String elementId, String value) {
        this.findElement(elementId).setProperty("Text", value);
    }

    /**
     * This will select/check the checkbox
     * @param elementID element id
     */
    public void selectFromCheckBox(String elementID) {
        this.findElement(elementID).setProperty("selected", -1);
    }

    /**
     * This will unselect/uncheck the checkbox
     * @param elementID element id
     */
    public void uncheckFromCheckBox(String elementID) {
        this.findElement(elementID).setProperty("selected", 0);
    }

    /**
     * This will press the key enter
     */
    public void pressEnter() {
        this.findElement("wnd[0]").invoke("sendVKey", 0);
    }

    /**
     * This will click the element
     * @param elementId element id
     */
    public void click(String elementId) {
        this.findElement(elementId).invoke("press");
    }

    /**
     * This will click close
     * @param elementId element id
     */
    public void clickClose(String elementId) {
        this.findElement(elementId).invoke("close");
    }

    /**
     * This will return value from row column
     * @param table table
     * @param row row number
     * @param column column
     * @return value of the passed row and column
     */
    public String getValueFromRowColumn(String table, int row, String column) {
        Dispatch itemToDispatch = this.findElementWithDispatch(table);
        return Dispatch.call(itemToDispatch, "GetCellValue", row, column).toString();
    }

    /**
     * This will set focus on the element
     * @param elementId element id
     */
    public void setFocus(String elementId) {
        this.findElement(elementId).invoke("setFocus");
    }

    /**
     * This will set property
     * @param elementId element id
     * @param property property
     * @param value value
     */
    public void setProperty(String elementId, String property, int value) {
        this.findElement(elementId).setProperty(property, value);
    }

    /**
     * This will invoke a command on the element
     * @param elementId element id
     * @param command command
     * @param value value
     */
    public void invokeCommand(String elementId, String command, int value) {
        this.findElement(elementId).invoke(command, value);
    }

    /**
     * This will return the text of the element
     * @param elementId element id
     * @return text of the element
     */
    public String getText(String elementId) {
        return this.findElement(elementId).getProperty("text").toString();
    }
}