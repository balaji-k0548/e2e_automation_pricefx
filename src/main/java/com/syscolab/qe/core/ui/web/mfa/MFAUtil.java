package com.syscolab.qe.core.ui.web.mfa;

import com.syscolab.qe.core.common.LoggerUtil;
import com.syscolab.qe.core.ui.SyscoLabUI;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.openqa.selenium.Cookie;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Base64;


public class MFAUtil {

    static Workbook wb;
    static WritableWorkbook writeWB;
    private static String fileLocation;
    private static final String ALGORITHM = "AES";
    private static byte[] KEY_BYTES;
    private static final String cookie1 = "ESTSAUTHLIGHT";
    private static final String cookie2 = "ESTSAUTHPERSISTENT";
    private static final String cookie3 = "ESTSAUTH";
    private static final String cookie4 = "SSOCOOKIEPULLED";


    public static String getFileLocation() {
        return fileLocation;
    }

    public static void setFileLocation(String newFileLocation) {
        fileLocation = newFileLocation;
    }

    public static String encrypt(String input) {
        try {
            Key key = generateKey();
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting data", e);
        }
    }

    public static String decrypt(String encrypted) {
        try {
            Key key = generateKey();
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedBytes = Base64.getDecoder().decode(encrypted);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting data", e);
        }
    }

    private static Key generateKey() {
        return new SecretKeySpec(KEY_BYTES, ALGORITHM);
    }

    public static void getWorkbook() throws BiffException, IOException {
        File file = new File(getFileLocation());
        if (!file.exists()) {
            try {
                // Create a new workbook
                WritableWorkbook workbook = Workbook.createWorkbook(file);
                // Create a new sheet
                WritableSheet sheet = workbook.createSheet("Sheet1", 0);
                // Add values to the first column
                String[] values = {cookie1,cookie2, cookie3, cookie4};
                for (int i = 0; i < values.length; i++) {
                    Label label = new Label(0, i, values[i]);
                    sheet.addCell(label);
                }
                // Write the workbook
                workbook.write();
                workbook.close();
                LoggerUtil.logINFO("File created successfully!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        wb = Workbook.getWorkbook(file);
    }

    public static void getSessionCookieFromExcel(String secret_key) throws BiffException, IOException {
        KEY_BYTES = secret_key.getBytes(StandardCharsets.UTF_8);
        String[][] sessionData = extractSessionCookie();
        for (String[] sessionDatum : sessionData) {
            for (int j = 0; j < 1; j++) {
                String key = sessionDatum[0];
                String value;
                try {
                    value = decrypt(sessionDatum[1]);
                } catch (Exception e) {
                    value = "";
                }
                Cookie cookie = new Cookie.Builder(key, value).build();
                SyscoLabUI.driver.manage().addCookie(cookie);
            }
        }
        LoggerUtil.logINFO(">>> Session restored to current browser");
    }

    public static String[][] extractSessionCookie() throws BiffException, IOException {
        MFAUtil.getWorkbook();
        Sheet sheet = wb.getSheet(0);
        int columnsCount = sheet.getColumns();
        int rowsCount = sheet.getRows();
        String[][] extractedSession = new String[rowsCount][columnsCount];
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                Cell cell = sheet.getCell(j, i);
                extractedSession[i][j] = cell.getContents();
            }
        }
        return extractedSession;
    }

    public static String getCookieValue(String cookieName) {
        Cookie cookie = SyscoLabUI.driver.manage().getCookieNamed(cookieName);
        return cookie.getValue();
    }

    public static void storeSessionData() {

        ArrayList<String> encryptedCookies = new ArrayList<>();

        encryptedCookies.add(getCookieValue(cookie1));
        encryptedCookies.add(getCookieValue(cookie2));
        encryptedCookies.add(getCookieValue(cookie3));
        encryptedCookies.add(getCookieValue(cookie4));

        writeSessionCookies(encryptedCookies);
    }

    public static void writeSessionCookies(ArrayList<String> sessionData) {

        try {
            getWorkbook();
            writeWB = Workbook.createWorkbook(new File(fileLocation), wb);
            WritableSheet excelSheet = writeWB.getSheet(0);
            Label label;

            // Encrypt and write the first cookie value
            String encryptedCookie1 = encrypt(sessionData.get(0));
            label = new Label(1, 0, new String(encryptedCookie1));
            excelSheet.addCell(label);

            // Encrypt and write the second cookie value
            String encryptedCookie2 = encrypt(sessionData.get(1));
            label = new Label(1, 1, new String(encryptedCookie2));
            excelSheet.addCell(label);

            // Encrypt and write the third cookie value
            String encryptedCookie3 = encrypt(sessionData.get(2));
            label = new Label(1, 2, new String(encryptedCookie3));
            excelSheet.addCell(label);

            // Encrypt and write the fourth cookie value
            String encryptedCookie4 = encrypt(sessionData.get(3));
            label = new Label(1, 3, new String(encryptedCookie4));
            excelSheet.addCell(label);

        } catch (IOException | WriteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (writeWB != null) {
                    writeWB.write();
                    writeWB.close();
                }
            } catch (IOException | WriteException e) {
                e.printStackTrace();
            }
        }
    }
}