package com.quad;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class newPatient extends JFrame {
    private JPanel newPatientPanel;
    private JButton OKButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JComboBox comboBox1;
    private JTextField textField4;
    private JTextField textField5;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JComboBox comboBox4;

    newPatient() {
        setContentPane(newPatientPanel);
        getRootPane().setDefaultButton(OKButton);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                saveInfo();
                goBack();
            }
        });
    }

    private void saveInfo(){
        textField1.getText(); //Name
        textField2.getText(); //Email
        textField3.getText(); //ID
        textField4.getText(); //Phone Num
        textField5.getText(); //Address
        comboBox1.getAccessibleContext(); //Med Centre
        comboBox2.getAccessibleContext(); //Day OB
        comboBox3.getAccessibleContext(); //Month OB
        comboBox4.getAccessibleContext(); //Year OB
        //HENRY SAVE INFO
    }

    private void goBack(){
        dispose();
        editPatient frame = new editPatient();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        newPatient frame5 = new newPatient();
        frame5.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame5.pack();
        frame5.setVisible(true);
    }

}
