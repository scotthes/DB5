package com.quad;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class reportHome extends JFrame {
    private JComboBox comboBox1;
    private JButton goButton;
    private JButton newCaseReportButton;
    private JButton backButton;
    private JPanel reportsPanel;

    reportHome(){
        setContentPane(reportsPanel);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                back();
            }
        });
        newCaseReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                newReport();
            }
        });
        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                reportExample();
            }
        });
    }

    private void back(){
        dispose();
        patientSearch frame = new patientSearch();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void newReport(){
        dispose();

        newReport frame = new newReport();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void reportExample(){
        dispose();
        reportExample frame = new reportExample();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        reportHome frame2 = new reportHome();
        frame2.pack();
        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
