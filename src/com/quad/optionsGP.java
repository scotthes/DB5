package com.quad;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class optionsGP extends JFrame {

    private JPanel optionsGPPanel;
    private JButton OKButton;
    private JTextField textField1;
    private JTextField textField3;
    private JButton logOutButton;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;

    optionsGP(){
        setContentPane(optionsGPPanel);
        getRootPane().setDefaultButton(OKButton);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(!textField1.getText().equals("John Smith") ){
                    JOptionPane.showMessageDialog(null, "Sorry, that name is not recognised!");
                }
                else if (textField3.getText().equals("SW114HJ")){
                    reportHome();
                }
                else {
                    search();
                }
            }
        });
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                homePage();
            }
        });
    }

    private void search(){
        dispose();
        patientSearch frame = new patientSearch();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void reportHome(){
        dispose();
        reportHome frame = new reportHome();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void homePage(){
        dispose();
        mainForm frame = new mainForm();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        optionsGP frame2 = new optionsGP();
        frame2.pack();
        frame2.setSize(700,400);
        frame2.setLocationRelativeTo(null);
        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
