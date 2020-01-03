package com.quad.Forms;

import com.quad.AutoCompletion;
import com.quad.ClientData.CaseReport;
import com.quad.ClientData.Patient;
import com.quad.Global;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class patientHome extends JFrame {

    private JPanel patientHomePanel;
    private JLabel nameLabel;
    private JButton goBackButton;
    private JButton newCaseReportButton;
    private JComboBox<String> existingReportsBox;
    private JButton goButton;

    patientHome(){
        setContentPane(patientHomePanel);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        nameLabel.setText(Global.ActivePatient.getName() + "'s Case Reports");
        comboBoxSetup();
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                goBack();
            }
        });

        newCaseReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                goEditReport();
            }
        });

        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String reportName = (String)existingReportsBox.getSelectedItem();
                JOptionPane.showMessageDialog(null,reportName);
            }
        });
    }

    private void goBack(){
        patientSearch frame = new patientSearch(0);
        Global.frameSetup(frame, this);
        dispose();
    }

    private void goEditReport(){
        CaseReport newCase = new CaseReport("", Global.ActiveGP.getID(), Global.ActivePatient.getID(), 0);
        editReport frame = new editReport(newCase);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1200,600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        dispose();
    }

    private void comboBoxSetup(){
        AutoCompletion.enable(existingReportsBox); //Enable searchable combobox
        //System.out.println(Global.ActivePatient.loadCaseReports(0).get(1).getCondition());
        for (int i = 0; i < Global.ActivePatient.loadCaseReports(0).size(); i++){
            existingReportsBox.addItem(Global.ActivePatient.loadCaseReports(0).get(i).getLastModifiedString()); //GET CONDITION NOT WORKING
        }
    }

    /*private void goListReports(){   NO LONGER NEEDED. CAN DELETE
        reportsList frame = new reportsList();
        Global.frameSetup(frame, this);
        dispose();
    }*/

    public static void main(String[] args) {
        Global.ActivePatient = new Patient(" "," ",null,0, " "," "," ");
        patientHome frame = new patientHome();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
