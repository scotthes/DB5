package com.quad.Forms;

import com.quad.ClientData.GP;
import com.quad.ClientData.Patient;
import com.quad.DataAccess;
import com.quad.Global;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        Global.ActiveGP = new GP(" ", " ", null, 0, " ", " ", " ");
        Global.ActivePatient = new Patient(" "," ",null,0," "," ","1915 January 1");
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (name.getText().equals("Admin") && String.valueOf(pw.getPassword()).equals("pass")) {
                    grantAdminAccess();
                }
                else if (DataAccess.GPLogin(name.getText(), String.valueOf(pw.getPassword()))) {
                    grantGPAccess();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Sorry, those details are not recognised!");
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
        searchPage frame = new searchPage(0);
        Global.frameSetup(frame, this);
    }

    public static void main(String[] args){
        DataAccess.loadMedCs();
        mainForm frame = new mainForm();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
