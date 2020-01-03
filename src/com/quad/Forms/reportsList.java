package com.quad.Forms;

import com.quad.ClientData.CaseReport;
import com.quad.ClientData.Patient;
import com.quad.Global;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class reportsList extends JFrame {

    private JPanel reportsListPanel;
    private JLabel titleLabel;
    private JButton button1;
    private JButton backButton;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private ArrayList<JButton> resultsButtons = new ArrayList<>();
    private ArrayList<CaseReport> reports = new ArrayList<>();
    private ArrayList<String> reportsTitles = new ArrayList<>();

    reportsList(){
        reports = Global.ActivePatient.loadCaseReports(0);
        for (CaseReport report : reports) {
            String title = report.getCondition() + "  | Last Modified: " + report.getLastModifiedString();
            reportsTitles.add(title); //will want to also add date
        }

        setButtons();
        setContentPane(reportsListPanel);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                goBack();
            }
        });
        for(int i = 0; i < reports.size(); i++){
            int finalI = i;
            resultsButtons.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    goEditReport(reports.get(finalI));
                }
            });
        }
    }

    private void setButtons(){
        titleLabel.setText(String.format("New Case Report For %s" , Global.ActivePatient.getName()));
        resultsButtons.add(button1);
        resultsButtons.add(button2);
        resultsButtons.add(button3);
        resultsButtons.add(button4);
        resultsButtons.add(button5);
        for(int i = 0; i < reports.size(); i++){
            resultsButtons.get(i).setText(reportsTitles.get(i));
        }
        for(int i = reports.size(); i < 5; i++){
            resultsButtons.get(i).setText("");
        }
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
    private void goBack(){
        patientHome frame = new patientHome();
        Global.frameSetup(frame,this);
        dispose();
    }

    public static void main(){
        Global.ActivePatient = new Patient(" "," ",null,0, " "," "," ");
        reportsList frame = new reportsList();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
