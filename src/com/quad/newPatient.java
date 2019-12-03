package com.quad;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class newPatient extends JFrame {
    private JButton OKButton;
    private JPanel patientPanel;
    private JButton existingPatientButton;
    private JButton OKButton1;

    newPatient(){
        setContentPane(patientPanel);
        getRootPane().setDefaultButton(OKButton);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        OKButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                saveInfo();
                goBack();
            }
        });
    }

    private void saveInfo(){
        //KAY SAVE INFO
    }

    private void goBack(){
        dispose();
        initialOptions frame = new initialOptions();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        newPatient frame4 = new newPatient();
        frame4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame4.pack();
        frame4.setVisible(true);
    }

}

