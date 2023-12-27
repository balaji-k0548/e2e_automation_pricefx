package com.syscolab.qe.core.playwright.ui;

import com.microsoft.playwright.BrowserContext;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Kasun Herath
 * Following class is to Wrap Java HashMap , to be used as thread safe maps
 */
public class ThreadLocalContextMap {
    private ThreadLocal<Map<String, BrowserContext>> threadLocalMap = new ThreadLocal<Map<String, BrowserContext>>() {
        @Override
        protected Map<String, BrowserContext> initialValue() {
            return new LinkedHashMap<>();
        }
    };
    public void put(String key, BrowserContext value) {
        threadLocalMap.get().put(key, value);
    }

    public BrowserContext get(String key) {
        return threadLocalMap.get().get(key);
    }

    public boolean containsKey(String Key){
        return threadLocalMap.get().containsKey(Key);
    }
}
