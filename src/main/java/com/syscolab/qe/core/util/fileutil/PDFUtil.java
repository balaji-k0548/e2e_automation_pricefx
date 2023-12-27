package com.syscolab.qe.core.util.fileutil;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * This is the util class for Read PDF file
 *
 * @author Sanduni Jayawardena
 */

public class PDFUtil {


    private PDFUtil() {
    }

    /**
     * This will get the pdf file path location and return full pdf content as string
     *
     * @param filePath pdf file path from the repository root
     * @return PDFContent
     */
    public static String getPdfContent(String filePath) {
        String content = "";
        File pdfFile = new File(filePath);
        PDDocument pdf;
        try {
            pdf = PDDocument.load(pdfFile);
            PDFTextStripper stripper = new PDFTextStripper();
            content = stripper.getText(pdf);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;

    }

    /**
     * This will get the pdf file path location , value to be checked in pdf and return the availability true/false
     *
     * @param filePath pdf file path from the repository root
     * @param key      value to be validate
     * @return availability state
     */
    public static boolean getAvailabilityOfKey(String filePath, String key) {
        String pdfText = getPdfContent(filePath);
        boolean state = false;
        if (pdfText != null) {
            state = pdfText.contains(key);
        }
        return state;
    }

    /**
     * This will get the pdf file path location and return the lines of pdf as array of strings
     *
     * @param filePath pdf file path from the repository root
     * @return lines in an array
     */
    public static String[] pdfContentsByLines(String filePath) {
        return getPdfContent(filePath).split(System.getProperty("line.separator"));
    }

    /**
     * This will get the pdf file path location, PDF file  two lines which need to validate order and return the boolean state
     * purpose of this feature is to check whether given two lines are in the correct order.
     * for each success path it will return true else it will return false
     *
     * @param filePath   pdf file path from the repository root
     * @param firstLine  line 1 to be compared in PDF
     * @param secondLine line 2 to be compared with first given line in PDF
     * @return the line order comparison boolean
     */
    public static boolean validateLineOrder(String filePath, String firstLine, String secondLine) {
        String[] lines = pdfContentsByLines(filePath);

        int firstLineIndex = 0;
        int secondLineIndex = 0;

        if (Arrays.asList(lines).contains(firstLine) && (Arrays.asList(lines).contains(secondLine))) {
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].contains(firstLine)) {
                    firstLineIndex = i;
                } else if (lines[i].contains(secondLine)) {
                    secondLineIndex = i;
                }

            }
            return secondLineIndex > firstLineIndex;
        } else {
            return false;
        }

    }

}
