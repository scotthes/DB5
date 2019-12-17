package com.quad;

import com.quad.ClientData.GP;
import com.quad.ClientData.MedCentre;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class newGP extends JFrame{
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JPanel GPPanel;
    private JButton OKButton;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;

    newGP(){
        setContentPane(GPPanel);
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
        String name = textField1.getText(); //Name
        String email = textField2.getText(); //Email
        String pagerNum = textField3.getText(); //Pager Number
        MedCentre medC = new MedCentre(textField4.getText(), "123 Fakestreet"); //Med Centre
        String id = textField5.getText(); //ID
        String Password = textField6.getText(); //Password
        GP newGP = new GP(name, email, medC, 0, pagerNum, id, Password);
        newGP.saveGP();
    }

    private void goBack(){
        dispose();
        initialOptions frame = new initialOptions();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        newGP frame5 = new newGP();
        frame5.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame5.pack();
        frame5.setSize(700,400);
        frame5.setLocationRelativeTo(null);
        frame5.setVisible(true);
    }
}
