package com.quad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class mainForm extends JFrame {
    private JPanel panel1;
    private JLabel iconLabel;
    private JTextField name;
    private JPasswordField pw;
    private JButton logInButton;


    mainForm() {
        setContentPane(panel1);
        getRootPane().setDefaultButton(logInButton);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (name.getText().equals("Admin") && String.valueOf(pw.getPassword()).equals("pass")) {
                    grantAdminAccess();
                }
                else {
                    try {
                        if (DataAccess.GPLogin(name.getText(), String.valueOf(pw.getPassword()))) {
                            grantGPAccess();
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Sorry, those details are not recognised!");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static void main(String[] args){
        System.out.println("THIS VERSION WAS USED IN PRESENTATION");
        try {
            DataAccess.loadMedCs();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mainForm frame = new mainForm();
        frame.pack();
        frame.setSize(700,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private boolean pwCorrect(){ //not used
        String rightPw = "pass";
        String accPw = String.valueOf(pw.getPassword());
        return accPw.equals(rightPw);
    }

    private void grantAdminAccess(){
        adminOptions frame = new adminOptions();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,400);
        frame.setLocation(this.getLocation());
        //frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        this.dispose();
    }

    private void grantGPAccess(){
        Point p = this.getLocation();
        dispose();
        patientSearch frame = new patientSearch(0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,400);
        frame.setLocation(p);
        //frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
