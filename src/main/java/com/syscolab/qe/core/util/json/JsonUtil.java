package com.syscolab.qe.core.util.json;

import com.google.gson.JsonElement;
import com.jayway.jsonpath.JsonPath;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This is the util class for JSON
 * @author Sandeep Perera
 */

public class JsonUtil {
    /**
     * Default constructor of the class
     */
    private JsonUtil() {
    }

    /**
     * This will check for the key and add the value to a list
     * @param key         key of the json object or json array
     * @param jsonElement Json element
     * @param list        list object
     */
    private static void check(String key, JsonElement jsonElement, List<String> list) {

        if (jsonElement.isJsonArray()) {
            for (JsonElement jsonElement1 : jsonElement.getAsJsonArray()) {
                check(key, jsonElement1, list);
            }
        } else {
            if (jsonElement.isJsonObject()) {
                Set<Map.Entry<String, JsonElement>> entrySet = jsonElement
                        .getAsJsonObject().entrySet();
                checkAllEntrySet(key, list, entrySet);
            } else {
                if (jsonElement.toString().equals(key)) {
                    list.add(jsonElement.toString());
                }
            }
        }
    }

    /**
     * This will check for all entry set key and add the value to a list
     * @param key      key
     * @param list     list object
     * @param entrySet entry set
     */
    private static void checkAllEntrySet(String key, List<String> list, Set<Map.Entry<String, JsonElement>> entrySet) {
        for (Map.Entry<String, JsonElement> entry : entrySet) {
            String key1 = entry.getKey();
            if (key1.equals(key)) {
                list.add(entry.getValue().toString());
            }
            check(key, entry.getValue(), list);
        }
    }

    /**
     * This will read the Excel file when location and name are given
     *
     * @param jsonFilePathName path name of the JSON File
     * @param jsonPath JSONpath name of the attribute
     * @return csvValues sheet
     */
    public static List<String> readJSON(String jsonFilePathName, String jsonPath) {
        List<String> jsonValues;
        String data;
        try {
            data = new String(Files.readAllBytes(Paths.get(jsonFilePathName)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        data = JsonPath.read(data, jsonPath).toString();
        jsonValues = Arrays.asList(data.replaceAll("\\[|\\][-+^\"\"]*", "").split(","));
        return jsonValues;
    }
}
