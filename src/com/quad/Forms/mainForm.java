package com.quad.Forms;

import com.quad.DataAccess;
import com.quad.Global;

import javax.swing.*;
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

    private void grantAdminAccess(){
        dispose();
        adminOptions frame = new adminOptions();
        Global.frameSetup(frame, this);
    }

    private void grantGPAccess(){
        dispose();
        patientSearch frame = new patientSearch(0);
        Global.frameSetup(frame, this);
    }

    public static void main(String[] args){
        try {
            DataAccess.loadMedCs();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mainForm frame = new mainForm();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
