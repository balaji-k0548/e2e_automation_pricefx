package com.syscolab.qe.core.util.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.transfer.*;
import com.syscolab.qe.core.common.LoggerUtil;
import com.syscolab.qe.core.util.fileutil.FileUtil;
import jodd.util.StringPool;
import jodd.util.ThreadUtil;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

/**
 * The following Util Class contains the methods for AWS S3 related functions
 *
 * @author Sahan Jayawardena
 */

public class AWSS3Util {
    AWSS3Util() {
    }

    private static AmazonS3 s3Client = null;

    /**
     * The following method generates the AWS S3 Client Connection
     * when the connection is NOT NULL
     */
    public static void createAWSS3Connection() {
        if (s3Client == null) {
            LoggerUtil.logINFO("Creating AWS S3 Connection");
            s3Client = AmazonS3ClientBuilder.defaultClient();
        }
    }

    /**
     * The following method uploads a File into the given S3 Bucket
     * @param s3BucketName - S3 Bucket Name where the File Objects needs to be uploaded into
     * @param fileObjectKey - S3 File Object Key for the uploading File Name
     * @param resourceNameWithPath - Resource Name with Path for the Source
     */
    public static void uploadFileIntoAWSS3Bucket(String s3BucketName, String fileObjectKey, String resourceNameWithPath) {
        TransferManager s3FileTransferManager;
        try {
            String fileName = new File(resourceNameWithPath).getName();
            LoggerUtil.logINFO("Uploading File : " + fileName + " into AWS S3 Bucket : " + s3BucketName);

            s3FileTransferManager = TransferManagerBuilder.standard().withS3Client(s3Client).build();
            Upload fileUploader = s3FileTransferManager.upload(s3BucketName, fileObjectKey, new File(resourceNameWithPath));
            fileUploader.waitForCompletion();
            LoggerUtil.logINFO("File Uploading Completed for File Name : " + fileName + " into S3 Path : " + fileObjectKey + ", S3 Bucket Name : " + s3BucketName + StringPool.NEWLINE);
        } catch (Exception ex) {
            LoggerUtil.logERROR(ex.getMessage(), ex);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * The following method uploads all the Files inside a Directory
     * @param s3BucketName - S3 Bucket Name where the File Objects needs to be uploaded into
     * @param s3DestinationFolder - Destination Folder Name where the Files need to be uploaded
     *                              This can be keep as NULL or Empty when you do not need to mention any Destination Folder
     * @param inputFolderNameWithPath - Input Folder Name where your files contain
     */
    public static void uploadFilesInAFolderIntoS3Bucket(String s3BucketName, String s3DestinationFolder, String inputFolderNameWithPath) {
        try {
            if (FileUtil.isResourcePathIsADirectory(inputFolderNameWithPath)) {
                createAWSS3Connection();
                s3DestinationFolder = s3DestinationFolder == null ? StringPool.EMPTY : s3DestinationFolder;

                File resourcePath = new File(inputFolderNameWithPath);
                LoggerUtil.logINFO("Uploading Files inside location : " + resourcePath.getPath() + " into AWS S3 Bucket : " + s3BucketName);
                for (File file : Objects.requireNonNull(resourcePath.listFiles())) {
                    if (FileUtil.isResourcePathIsAFile(file.toPath().toString())) {
                        uploadFileIntoAWSS3Bucket(s3BucketName, s3DestinationFolder + file.getName(), file.getAbsolutePath());
                    }
                }
            } else {
                LoggerUtil.logINFO("Specified Resource Path : " + inputFolderNameWithPath + " is not a Directory");
            }
        } catch (Exception ex) {
            LoggerUtil.logERROR(ex.getMessage(), ex);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * The following method uploads a single file into S3 Bucket
     * @param s3BucketName - S3 Bucket Name where the File Objects needs to be uploaded into
     * @param s3DestinationFolder - Destination Folder Name where the Files need to be uploaded
     *                              This can be keep as NULL or Empty when you do not need to mention any Destination Folder
     * @param fileNameWithPath - Input File Name with Path
     */
    public static void uploadASingleFileIntoS3Bucket(String s3BucketName, String s3DestinationFolder, String fileNameWithPath) {
        try {
            if (FileUtil.isResourcePathIsAFile(fileNameWithPath)) {
                createAWSS3Connection();

                File file = new File(fileNameWithPath);
                uploadFileIntoAWSS3Bucket(s3BucketName, s3DestinationFolder + file.getName(), file.getAbsolutePath());
            } else {
                LoggerUtil.logINFO("Specified Resource Path : " + fileNameWithPath + " is not a File");
            }
        } catch (Exception ex) {
            LoggerUtil.logERROR(ex.getMessage(), ex);
        }
    }

    /**
     * The following method checks whether a specific file object exists in the S3 Bucket
     * @param s3BucketName - S3 Bucket Name where the File is verified for existence.
     * @param fileObjectKey - File Object Key to check whether the file exists in S3 or not
     * @return - Returns a BOOLEAN value. True - File Exists, False - File does not exist
     */
    public static boolean isFileExistsInS3Bucket(String s3BucketName, String fileObjectKey) {
        boolean isFileExistsInS3 = false;
        try {
            createAWSS3Connection();

            LoggerUtil.logINFO("Verifying File Exists Status for File Object Key : " + fileObjectKey + ", S3BucketName : " + s3BucketName);
            isFileExistsInS3 = s3Client.doesObjectExist(s3BucketName, fileObjectKey);
        } catch (Exception ex) {
            LoggerUtil.logERROR(ex.getMessage(), ex);
        }

        LoggerUtil.logINFO("File Exists Status : " + String.valueOf(isFileExistsInS3).toUpperCase() + " for File Object Key : " + fileObjectKey + StringPool.NEWLINE);
        return isFileExistsInS3;
    }

    /**
     * The following method downloads a File from S3 Bucket only if exists.
     * @param s3BucketName - S3 Bucket Name where the File is located
     * @param fileObjectKey - File Object Key for the file to be downloaded.
     * @param destinationLocation - Location Name where the file will be downloaded.
     */
    public static void downloadFileFromAWSS3Bucket(String s3BucketName, String fileObjectKey, String destinationLocation) {
        try {
            createAWSS3Connection();
            GetObjectRequest s3ObjectRequest = new GetObjectRequest(s3BucketName, fileObjectKey);
            S3Object s3Object;

            if (isFileExistsInS3Bucket(s3BucketName, fileObjectKey)) {
                LoggerUtil.logINFO("Downloading File Object Key : " + fileObjectKey + " into location : " + destinationLocation + " into S3 Bucket : " + s3BucketName);
                do {
                    s3Object = s3Client.getObject(s3ObjectRequest);
                    ThreadUtil.sleep(1000);
                }
                while (s3Object.getObjectContent() == null);

                Files.copy(s3Object.getObjectContent(), Paths.get(destinationLocation + new File(s3Object.getKey()).getName()), StandardCopyOption.REPLACE_EXISTING);
                LoggerUtil.logINFO("File Downloading Completed for File Object Key : " + fileObjectKey + " from S3Bucket : " + s3BucketName + " into Location : " + destinationLocation + StringPool.NEWLINE);
            } else {
                LoggerUtil.logINFO("File Not Found to be Downloaded with the File Object Key : " + fileObjectKey + ", S3 Bucket : " + s3BucketName);
            }
        } catch (Exception ex) {
            LoggerUtil.logERROR(ex.getMessage(), ex);
            Thread.currentThread().interrupt();
        }
    }
}