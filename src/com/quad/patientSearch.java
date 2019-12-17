package com.quad;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class patientSearch extends JFrame{
    private JPanel searchResultsPanel;
    private JButton johnSmithSW114HJ29Button;
    private JButton johnSmithSW72AZ13Button;
    private JButton searchAgainButton;

    patientSearch() {
        setContentPane(searchResultsPanel);
        getRootPane().setDefaultButton(searchAgainButton);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        searchAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                goBack();
            }
        });
        johnSmithSW114HJ29Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                reportHome();
            }
        });
    }

    private void goBack(){
        dispose();
        optionsGP frame = new optionsGP();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void reportHome(){
        dispose();
        reportHome frame = new reportHome();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        patientSearch frame2 = new patientSearch();
        frame2.pack();
        frame2.setSize(700,400);
        frame2.setLocationRelativeTo(null);
        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
