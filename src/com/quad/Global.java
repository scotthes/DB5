package com.quad;

import com.quad.ClientData.GP;
import com.quad.ClientData.MedCentre;
import com.quad.ClientData.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Global {
    public static Patient ActivePatient;
    public static GP ActiveGP;
    public static ArrayList<MedCentre> MCList;

    public static void frameSetup(JFrame frame, JFrame oldFrame){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(oldFrame.getWidth(),oldFrame.getHeight());
        frame.setResizable(false);
        frame.setLocation(oldFrame.getLocation());
        frame.setVisible(true);
    }

    public static BufferedImage scaleImage(BufferedImage img) throws Exception {
        BufferedImage bi;
        bi = new BufferedImage(40, 50, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(img, 0, 0, 40, 50, null);
        g2d.dispose();
        return bi;
    }
}
