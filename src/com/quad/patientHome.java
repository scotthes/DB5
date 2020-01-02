package com.quad;

import com.quad.ClientData.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class patientHome extends JFrame {

    private JPanel patientHomePanel;
    private JLabel nameLabel;
    private JButton goBackButton;
    private JButton viewAndEditExistingButton;
    private JButton newCaseReportButton;
    private Patient currentPatient;

    patientHome(Patient p){
        currentPatient = p;
        setContentPane(patientHomePanel);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        nameLabel.setText(p.getName());
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                goBack();
            }
        });
        viewAndEditExistingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(null, "Sorry, that feature don't exist!");
            }
        });
        newCaseReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                goNewReport();
            }
        });
    }

    private void goBack(){
        dispose();
        patientSearch frame = new patientSearch(0);
        Global.frameSetup(frame, this);
    }

    private void goNewReport(){
        dispose();
        newReport frame = new newReport(currentPatient);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1650,1040);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Patient blankPatient = new Patient(" "," ",null,0, " "," "," ");
        patientHome frame = new patientHome(blankPatient);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
