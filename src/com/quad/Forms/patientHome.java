package com.quad.Forms;

import com.quad.AutoCompletion;
import com.quad.ClientData.CaseReport;
import com.quad.ClientData.Patient;
import com.quad.Global;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class patientHome extends JFrame {

    private JPanel patientHomePanel;
    private JLabel nameLabel;
    private JButton goBackButton;
    private JButton newCaseReportButton;
    private JComboBox<String> existingReportsBox;
    private JButton goButton;
    private ArrayList<CaseReport> reports;

    patientHome(){
        setContentPane(patientHomePanel);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        if (Global.ActivePatient.getID()!=0){
            nameLabel.setText(Global.ActivePatient.getName() + "'s Case Reports");
            reports = Global.ActivePatient.loadCaseReports();
        }
        else {
            nameLabel.setText(Global.ActiveGP.getName()+ "'s Case Reports");
            reports = Global.ActiveGP.loadCaseReports();
            newCaseReportButton.setVisible(false);
        }


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
                CaseReport newCase = new CaseReport("", Global.ActiveGP.getID(), Global.ActivePatient.getID(), 0);
                goEditReport(newCase);
            }
        });

        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                goEditReport(reports.get(existingReportsBox.getSelectedIndex()));
            }
        });
    }

    private void goBack(){
        int type = 0;
        if (Global.ActivePatient.getID()==0){
            type = 2;
        }
        patientSearch frame = new patientSearch(type);
        Global.frameSetup(frame, this);
        dispose();
    }

    private void goEditReport(CaseReport caseR){
        editReport frame = new editReport(caseR);
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
        for (CaseReport report : reports) {
            if (Global.ActivePatient.getID()!=0) {
                existingReportsBox.addItem(report.getLastModifiedString() + " - " + report.getCondition());
            }
            else {
                existingReportsBox.addItem(report.getLastModifiedString() + " - " + report.getPatientName() + " - " + report.getCondition());
            }
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
