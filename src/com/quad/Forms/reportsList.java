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
    private Patient currentPatient;
    private ArrayList<JButton> resultsButtons = new ArrayList<>();
    private ArrayList<CaseReport> reports = new ArrayList<>();
    private ArrayList<String> reportsTitles = new ArrayList<>();

    reportsList(Patient p){
        currentPatient = p;
        System.out.println(p.getCaseRSize());
        for (int i = 0; i < p.getCaseRSize(); i ++){
            reports.add(p.getCase(i));
        }
        for (int i = 0; i < p.getCaseRSize(); i ++){
            String title = reports.get(i).getCondition();
            //String date = reports.get(i).getDate();
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
    }

    private void setButtons(){
        titleLabel.setText(String.format("New Case Report For %s" , currentPatient.getName()));
        resultsButtons.add(button1);
        resultsButtons.add(button2);
        resultsButtons.add(button3);
        resultsButtons.add(button4);
        resultsButtons.add(button5);
        for(int i = 0; i < reports.size(); i++){
            resultsButtons.get(i).setText(reportsTitles.get(i));
        }
        for(int i = reports.size(); i < 5 - reports.size(); i++){
            resultsButtons.get(i).setText("");
        }
    }

    private void goBack(){
        dispose();
        patientHome frame = new patientHome(currentPatient);
        Global.frameSetup(frame,this);
    }

    public static void main(){
        Patient blankPatient = new Patient(" "," ",null,0, " "," "," ");
        reportsList frame = new reportsList(blankPatient);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
