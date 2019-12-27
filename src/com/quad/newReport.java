package com.quad;

import com.quad.ClientData.Patient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class newReport extends JFrame{
    private JTextField textField1;
    private JTextArea DATEANDTIMESHOULDTextArea;
    private JCheckBox yesCheckBox;
    private JCheckBox noCheckBox;
    private JButton OKButton;
    private JPanel newReportPanel;
    private JTextField textField2;
    private  Patient p;

    newReport(Patient currentPatient) {
        p = currentPatient;
        setContentPane(newReportPanel);
        getRootPane().setDefaultButton(OKButton);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                goBack();
            }
        });
    }

    private void goBack(){
        dispose();
        patientHome frame = new patientHome(p);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Patient blankPatient = new Patient(" "," ",null,0," "," "," ");
        newReport frame2 = new newReport(blankPatient);
        frame2.pack();
        frame2.setSize(700,400);
        frame2.setLocationRelativeTo(null);
        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
