package com.quad;

import com.quad.ClientData.GP;
import com.quad.ClientData.MedCentre;
import com.quad.ClientData.Patient;

import javax.swing.*;
import java.util.ArrayList;

public class Global {
    public static Patient ActivePatient;
    public static GP ActiveGP;
    public static ArrayList<MedCentre> MCList;

    public static void frameSetup(JFrame frame, JFrame oldFrame){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(oldFrame.getWidth(),oldFrame.getHeight());
        frame.setLocation(oldFrame.getLocation());
        frame.setVisible(true);
    }
}
