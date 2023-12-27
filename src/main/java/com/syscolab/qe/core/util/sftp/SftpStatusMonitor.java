package com.syscolab.qe.core.util.sftp;

import com.jcraft.jsch.SftpProgressMonitor;
import com.syscolab.qe.core.common.LoggerUtil;

public class SftpStatusMonitor implements SftpProgressMonitor {

    private String process;

    public SftpStatusMonitor(String process) {
        this.process = process;
    }

    @Override
    public void init(int i, String s, String s1, long l) {
        LoggerUtil.logINFO(process + ": " + s + " -> " + s1 + " size: " + l + "kb");
    }

    @Override
    public boolean count(long l) {
        return true;
    }

    @Override
    public void end() {
        LoggerUtil.logINFO("FINISHED!");
    }
}
