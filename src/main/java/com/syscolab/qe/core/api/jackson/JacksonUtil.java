package com.syscolab.qe.core.api.jackson;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.syscolab.qe.core.api.request.RequestBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syscolab.qe.core.api.response.ResponseBase;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is the util class for Jackson - JSON processor for Java
 * Jackson will serialize Java objects into JSON and deserialize JSON string into Java objects.
 * @author Sandeep Perera
 */
public class JacksonUtil {
    /**
     * Default constructor for JacksonUtil class
     */
    private JacksonUtil() {}

    /**
     * This will pass the request base and return the json formatted string without ignoring null fields
     * @param requestBase request body
     * @return json formatted string
     */
    public static String getAsString(RequestBase requestBase){
        return getAsString(requestBase,false);
    }

    /**
     * This method will take the request body and if ignoreNull is false will write the body in JSON formatted string and return the json string
     * @param body request body
     * @param ignoreNull flag to ignore null fields while serializing
     * @return json formatted string
     */
    public static final String getAsString(RequestBase body, boolean ignoreNull){
        ObjectMapper mapper = new ObjectMapper();
        if(ignoreNull)
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String jsonInString ="";
        try {
            jsonInString = mapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            jsonInString = Arrays.toString(e.getStackTrace());

        }
        return jsonInString;
    }

    /**
     * This will pass the request base and return the json formatted string ignoring null fields
     * @param requestBase request body
     * @return json formatted string
     */
    public static String getAsArrayString(RequestBase... requestBase){
        return getAsArrayString(true,requestBase);
    }

    /**
     * This method will take the request body and if ignoreNull is false will write the body in JSON formatted string and return the json string
     * @param requestBase request body
     * @param ignoreNull flag to ignore null fields while serializing
     * @return json formatted string
     */
    public static String getAsArrayString( boolean ignoreNull, RequestBase... requestBase){
        ObjectMapper mapper = new ObjectMapper();
        if(ignoreNull)
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String jsonInString ="";
        try {
            jsonInString = mapper.writeValueAsString(requestBase);
        } catch (JsonProcessingException e) {
            jsonInString = Arrays.toString(e.getStackTrace());

        }
        return jsonInString;
    }

    /**
     *This will parse or deserialize JSON content into a Java object and return the java object related to Request
     * @param jsonString json String
     * @param ignoreNull  flag to ignore null fields while serializing
     * @return deserialized java object
     * @throws IOException throwing IOException
     */
    public static RequestBase getRequestAsObject(String jsonString,boolean ignoreNull) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        if(ignoreNull)
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return  mapper.readValue(jsonString, RequestBase.class);
    }

    /**
     *This will parse or deserialize JSON content into a Java object and return the java object related Response
     * @param jsonString json String
     * @param ignoreNull  flag to ignore null fields while serializing
     * @return deserialized java object
     * @throws IOException throwing IOException
     */
    public static ResponseBase getResponseAsObject(String jsonString, boolean ignoreNull) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        if(ignoreNull)
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return  mapper.readValue(jsonString, ResponseBase.class);
    }

    /**
     *This will parse or deserialize JSON content into a Java object and return the java object related Response by the cls parameter
     * @param jsonString json String
     * @param ignoreNull  flag to ignore null fields while serializing
     * @param cls value type of the class
     * @return deserialized java object
     * @throws IOException throwing IOException
     */
    public static ResponseBase getResponseAsObject(String jsonString, boolean ignoreNull, Class<?> cls) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        if(ignoreNull) {
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }

        return (ResponseBase)mapper.readValue(jsonString, cls);
    }


}
