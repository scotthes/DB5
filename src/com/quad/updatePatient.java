package com.quad;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class updatePatient extends JFrame {
    private JTextField textField1;
    private JButton OKButton;
    private JPanel updatePatientPanel;

    updatePatient(){
        setContentPane(updatePatientPanel);
        getRootPane().setDefaultButton(OKButton);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                search();
            }
        });
    }

    private void search(){
        dispose();
        patientSearch frame = new patientSearch();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        optionsGP frame2 = new optionsGP();
        frame2.pack();
        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
