package com.quad;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainForm extends JFrame {
    public JPanel panel1;
    private JTextField name;
    private JLabel UserNameLabel;
    private JLabel PasswordLabel;
    private JPasswordField pw;
    private JLabel TitleLabel;
    private JButton logInButton;

    private mainForm() {
        setContentPane(panel1);
        getRootPane().setDefaultButton(logInButton);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(!name.getText().equals("Admin")){
                    JOptionPane.showMessageDialog(null, "Username Not Recognised");
                }
                else if(!pwCorrect()){
                    JOptionPane.showMessageDialog(null, "Incorrect Password");
                }
                else {
                    grantAccess();
                }
            }
        });
    }

    public static void main(String[] args){
        mainForm frame = new mainForm();
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private boolean pwCorrect(){
        String rightPw = "pass";
        String accPw = String.valueOf(pw.getPassword());
        return accPw.equals(rightPw);
    }

    private void grantAccess(){
        dispose();
        initialOptions frame = new initialOptions();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
