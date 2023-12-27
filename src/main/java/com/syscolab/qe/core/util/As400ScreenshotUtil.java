package com.syscolab.qe.core.util;

import framework.AS400;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

/**
 * This class will contain methods to capture a screenshot on SUS
 * using sysco as400 artifact
 */
public class As400ScreenshotUtil {

    public static final JPanel main = AS400.main;

    /**
     * Default constructor of the class
     */
    public As400ScreenshotUtil() {
        // Empty constructor
    }

    /**
     * This will write the image into a byte array
     * @return img byte array
     */
    public byte[] captureAS400ScreenShot() {
        try {
            String strFormat = "png";
            BufferedImage screenFullImage = createImage(main);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(screenFullImage, strFormat, bos);
            return bos.toByteArray();
        } catch (Exception var6) {
            var6.printStackTrace();
            return new byte[0];
        }

    }

    /**
     * This will create and return the buffered image from the passed JPanel
     * @param panel jPanel
     * @return buffered image
     */
    private BufferedImage createImage(JPanel panel) {
        int w = panel.getWidth();
        int h = panel.getHeight();
        BufferedImage bi = new BufferedImage(w, h, 1);
        Graphics2D g = bi.createGraphics();
        panel.paint(g);
        g.dispose();
        return bi;
    }
}
