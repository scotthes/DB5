package com.quad;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class newGP extends JFrame{
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JPanel GPPanel;
    private JButton OKButton;

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
        textField1.getText(); //Name
        textField2.getText(); //Email
        textField3.getText(); //Pager Number
        //HENRY SAVE INFO
    }

    private void goBack(){
        dispose();
        initialOptions frame = new initialOptions();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        newGP frame5 = new newGP();
        frame5.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame5.pack();
        frame5.setVisible(true);
    }
}
