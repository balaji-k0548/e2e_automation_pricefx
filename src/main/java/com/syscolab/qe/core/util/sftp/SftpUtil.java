package com.syscolab.qe.core.util.sftp;

import com.jcraft.jsch.*;
import com.syscolab.qe.core.common.LoggerUtil;

import java.util.*;

public class SftpUtil {

    private static final String SFTP = "sftp";

    /**
     * creates the sftp connection.
     * @param sftpUserName is for to pass the user name of the sftp
     * @param sftpPassword is for to pass the password of the sftp
     * @param sftpHost is for to pass the host of the sftp
     * @return the connection to access the sftp
     */
    public static ChannelSftp initSftpConnection(String sftpUserName, String sftpPassword, String sftpHost) {
        ChannelSftp channelSftp = null;

        try {
            LoggerUtil.logINFO("Initializing the SFTP connection......");
            JSch jsch = new JSch();

            Session jschSession = jsch.getSession(sftpUserName, sftpHost);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            jschSession.setConfig(config);
            jschSession.setPassword(sftpPassword);
            jschSession.connect();
            channelSftp = (ChannelSftp) jschSession.openChannel(SFTP);
            LoggerUtil.logINFO("Initialized the SFTP connection!");
        } catch (JSchException ex) {
            LoggerUtil.logERROR("Failed to initialize the SFTP connection!", ex);
        }

        return channelSftp;
    }

    /**
     * uploads the resource(s) to the relevant sftp location
     * @param sftpConnection is for to pass the sftp connection
     *                      <br> N.B: use SftpUtil.initSftpConnection() to create a sftp connection
     * @param resourcePath is for to pass the path of the resource that needs to be uploaded to the sftp location
     * @param destinationDirectory is for to pass the path of the destination directory of the sftp location that the resource should get uploaded
     */
    public static void uploadResourceToSftpLocation(ChannelSftp sftpConnection, String resourcePath, String destinationDirectory) {
        try {
            LoggerUtil.logINFO("Uploading the files to SFTP Location!");
            sftpConnection.connect();
            sftpConnection.put(resourcePath, destinationDirectory, new SftpStatusMonitor("UPLOADING"));
            sftpConnection.exit();
            LoggerUtil.logINFO("Uploaded the files to SFTP Location Successfully!");
            sftpConnection.exit();
        } catch (Exception ex) {
            LoggerUtil.logERROR("Failed to upload the files to SFTP Location!", ex);
        }
    }

    /**
     * deletes all the resources from a sftp location
     * @param sftpConnection is for to pass the sftp connection
     *                       <br> N.B: use SftpUtil.initSftpConnection() to create a sftp connection
     * @param sftpDirectory is for to pass the path of the sftp directory that needs to be deleted all the resources within the directory
     */
    public static void deleteResourcesFromSftpLocation(ChannelSftp sftpConnection, String sftpDirectory) {
        try {
            LoggerUtil.logINFO("Deleting the files from the SFTP Location!");
            sftpConnection.connect();
            Collection<ChannelSftp.LsEntry> fileAndFolderList = sftpConnection.ls(sftpDirectory);
            for (ChannelSftp.LsEntry item : fileAndFolderList) {
                if (!item.getAttrs().isDir()) {
                    sftpConnection.rm(sftpDirectory + "/" + item.getFilename());
                }
            }
            sftpConnection.exit();
            LoggerUtil.logINFO("Deleted the files from the SFTP Location Successfully!");
        } catch (Exception ex) {
            LoggerUtil.logERROR("Failed to delete the files from SFTP Location!", ex);
        }
    }

    /**
     * deletes a specific resource from a sftp location
     * @param sftpConnection is for to pass the sftp connection
     *                       <br> N.B: use SftpUtil.initSftpConnection() to create a sftp connection
     * @param resourceToBeDeleted is for to pass the path of the resource that needs to be deleted from the sftp directory
     */
    public static void deleteResourceFromSftpLocation(ChannelSftp sftpConnection, String resourceToBeDeleted) {
        try {
            LoggerUtil.logINFO("Deleting the files from the SFTP Location!");
            sftpConnection.connect();
            sftpConnection.rm(resourceToBeDeleted);
            sftpConnection.exit();
            LoggerUtil.logINFO("Deleted the files from the SFTP Location Successfully!");
        } catch (Exception ex) {
            LoggerUtil.logERROR("Failed to delete the files from SFTP Location!", ex);
        }
    }

    /**
     * downloads a resource from a sftp location
     * @param sftpConnection is for to pass the sftp connection
     *                       <br> N.B: use SftpUtil.initSftpConnection() to create a sftp connection
     * @param downloadDirectory is for to pass the directory that the resource should be downloaded
     * @param resourceToBeDownloaded is for to pass the path of the resource that needs to be downloaded from the sftp directory
     */
    public static void downloadResourcesFromSftpLocation(ChannelSftp sftpConnection, String downloadDirectory, String resourceToBeDownloaded) {
        try {
            LoggerUtil.logINFO("Downloading the files from SFTP Location!");
            sftpConnection.connect();
            sftpConnection.get(resourceToBeDownloaded, downloadDirectory, new SftpStatusMonitor("DOWNLOADING"));
            sftpConnection.exit();
        } catch (Exception ex) {
            LoggerUtil.logERROR("Failed to download the files from SFTP Location!", ex);
        }
    }
}
