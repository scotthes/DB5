package com.quad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainForm extends JFrame {
    private JPanel panel1;
    private JLabel iconLabel;
    private JTextField name;
    private JPasswordField pw;
    private JButton logInButton;


    mainForm() {
        //ImageIcon iconNHS = new ImageIcon("nhs.png");
        //iconLabel.setIcon(iconNHS);
        //iconLabel.setText(null);
        setContentPane(panel1);
        getRootPane().setDefaultButton(logInButton);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (name.getText().equals("Admin") && pwCorrect()) {
                    grantAdminAccess();
                }
                else if (name.getText().equals("JS2019") && pwCorrect()) {
                    grantGPAccess();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Sorry, those details are not recognised!");
                }
            }
        });
    }

    public static void main(String[] args){
        System.out.println("THIS VERSION WAS USED IN PRESENTATION");
        mainForm frame = new mainForm();
        frame.pack();
        frame.setSize(700,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private boolean pwCorrect(){
        String rightPw = "pass";
        String accPw = String.valueOf(pw.getPassword());
        return accPw.equals(rightPw);
    }

    private void grantAdminAccess(){
        dispose();
        initialOptions frame = new initialOptions();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void grantGPAccess(){
        dispose();
        optionsGP frame = new optionsGP();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
