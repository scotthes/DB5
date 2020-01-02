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
        Global.frameSetup(frame);
    }

    private void goNewReport(){
        dispose();
        newReport frame = new newReport(currentPatient);
        Global.frameSetup(frame);
    }

    public static void main(String[] args) {
        Patient blankPatient = new Patient(" "," ",null,0, null," "," "," ");
        patientHome frame = new patientHome(blankPatient);
        Global.frameSetup(frame);
    }
}
