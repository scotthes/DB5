package com.quad;

import com.quad.ClientData.GP;
import com.quad.ClientData.Patient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;

//Define initialOptions class as an extension of JFrame
public class adminOptions extends JFrame {
    //IntelliJ automatically instantiates the below
    private JButton newCentre;
    private JPanel optionsPanel;
    private JButton newPat;
    private JButton newGP;
    private JLabel TitleLabel;
    private JButton logOutButton;
    private JButton exPat;
    private JButton existingGPButton;

    adminOptions() {
        setContentPane(optionsPanel);
        newCentre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                enterNewCentre();
            }
        });
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        newPat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                newPatient();
            }
        });
        exPat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                exPatient();
            }
        });
        newGP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                enterNewGP();
            }
        });
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                logout();
            }
        });
        existingGPButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                exGP();
            }
        });
    }

    private void logout(){
        dispose();
        mainForm frame = new mainForm();
        Global.frameSetup(frame, this);
    }

    private void enterNewCentre(){
        dispose();
        newCentre frame = new newCentre();
        Global.frameSetup(frame, this);
    }

    private void newPatient(){
        dispose();
        Patient nullPatient = new Patient(" "," ",null,0," "," ","1915 January 01");
        editPatient frame = new editPatient(nullPatient);
        Global.frameSetup(frame, this);
    }

    private void exPatient(){
        dispose();
        patientSearch frame = new patientSearch(1);
        Global.frameSetup(frame, this);
    }

    private void enterNewGP(){
        dispose();
        GP nullGP = new GP(" ", " ", null, 0, " ", " ", " ");
        editGP frame = new editGP(nullGP);
        Global.frameSetup(frame, this);
    }

    private void exGP(){
        dispose();
        patientSearch frame = new patientSearch(2);
        Global.frameSetup(frame, this);
    }

    public static void main(String[] args) {
        adminOptions frame = new adminOptions();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
