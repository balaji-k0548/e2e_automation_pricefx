package com.syscolab.qe.core.util.aws;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.*;
import com.syscolab.qe.core.common.LoggerUtil;
import jodd.util.StringPool;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The following Util Class contains the methods for AWS SSM related functions
 * @author Sahan Jayawardena
 */

public class AWSSecretUtils {
    AWSSecretUtils() {
    }

    private static AWSSimpleSystemsManagement ssmClient;

    /**
     * The following method generates the AWS SSM Client Connection
     * when the connection is NOT NULL
     */
    public static void createAWSSSMConnection() {
        if (ssmClient == null) {
            LoggerUtil.logINFO("Creating AWS SSM Connection");
            ssmClient = AWSSimpleSystemsManagementClientBuilder.defaultClient();
        }
    }

    /**
     * The following method generates Linked Hash Map with (ssmPath, ssmPathValue) based on the AWS-SSM Parameter Path
     * which is provided.
     * @param ssmParameterKeyOrPath - SSM Parameter Base Path
     * @return - LinkedHashMap(String, String) will be returned with the values, else Empty LinkedHashMap(String, String)
     */
    public static Map<String, String> getAWSSSMParamaterValuesmap(String ssmParameterKeyOrPath) {
        GetParametersByPathRequest parameterRequest;
        GetParametersByPathResult parameterResult;
        Map<String, String> ssmParamaterMap = new LinkedHashMap<>();
        try {
            createAWSSSMConnection();

            LoggerUtil.logINFO("Sending the AWS SSM Parameter Request for SSM Path : " + ssmParameterKeyOrPath);
            parameterRequest = new GetParametersByPathRequest()
                    .withPath(ssmParameterKeyOrPath)
                    .withRecursive(true)
                    .withWithDecryption(true);

            parameterResult = ssmClient.getParametersByPath(parameterRequest);
            List<Parameter> paramaterList = parameterResult.getParameters();
            if (!paramaterList.isEmpty()) {
                for (Parameter parameter : paramaterList) {
                    ssmParamaterMap.put(parameter.getName(), parameter.getValue());
                }
            } else {
                LoggerUtil.logINFO("No Results found for SSM Path : " + ssmParameterKeyOrPath);
            }
        } catch (Exception ex) {
            LoggerUtil.logERROR(ex.getMessage(), ex);
        }
        return ssmParamaterMap;
    }

    /**
     * The following method stores the SSM Parameters along with the Parameter Values in the AWS Parameter Store
     * which is provided.
     * @param parameterName  - SSM Parameter Base Path
     * @param parameterValue - SSM Parameter Value
     * @param parameterType  - SSM Parameter Type (String, StringList,SecureString)
     * @param description    - Optional Description for SSM Parameter
     */
    public static void setAWSSSMParameter(String parameterName, String parameterValue, ParameterType parameterType, String description) {
        PutParameterRequest parameterRequest;
        PutParameterResult parameterRequestResult;
        try {
            createAWSSSMConnection();
            LoggerUtil.logINFO("Storing AWS SSM Parameter with Key : " + parameterName + ", Parameter Type : " + parameterType.toString());
            parameterRequest = new PutParameterRequest()
                    .withName(parameterName)
                    .withValue(parameterValue)
                    .withType(parameterType)
                    .withDescription(description == null ? StringPool.EMPTY : description)
                    .withOverwrite(true);

            parameterRequestResult = ssmClient.putParameter(parameterRequest);
            if (parameterRequestResult.getVersion() != null || !parameterRequestResult.getVersion().toString().isEmpty()) {
                LoggerUtil.logINFO("AWS SSM Parameter created Successfully with the name : " + parameterName);
            } else {
                LoggerUtil.logINFO("AWS SSM Parameter creation Failed with the name of : " + parameterName);
            }
        } catch (Exception ex) {
            LoggerUtil.logERROR(ex.getMessage(), ex);
        }
    }

    /**
     * The following method removes the SSMParameter from AWS Parameter Store only if exists
     * @param parameterName - SSM Parameter Base Path
     */
    public static void removeAWSSSMParameter(String parameterName) {
        DeleteParameterRequest deleteParameterRequest = null;
        try {
            createAWSSSMConnection();
            LoggerUtil.logINFO("Deleting AWS SSM Parameter with Key : " + parameterName);
            if(!getAWSSSMParamaterValuesmap(parameterName).isEmpty()) {
                deleteParameterRequest = new DeleteParameterRequest().withName(parameterName);
                ssmClient.deleteParameter(deleteParameterRequest);
            }
            else {
                LoggerUtil.logERROR("SSM Parameter : " + parameterName + " not found in AWS Parameter Store which cannot be Deleted", null);
            }
        } catch (Exception ex) {
            LoggerUtil.logERROR("Failed to remove AWS SSM Parameter : " + parameterName, ex);
        }
    }
}