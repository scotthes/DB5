package com.quad;

import com.quad.ClientData.MedCentre;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class newCentre extends JFrame{
    private JPanel centrePanel;
    private JTextField textField1;
    private JTextField textField2;
    private JButton OKButton;
    private JButton logoutButton;
    private JButton cancelAndReturnHomeButton;

    newCentre(){
        setContentPane(centrePanel);
        getRootPane().setDefaultButton(OKButton);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                saveInfo();
                Return();
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                logout();
            }
        });
        cancelAndReturnHomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Return();
            }
        });
    }

    private void logout() {
        dispose();
        mainForm frame = new mainForm();
        Global.frameSetup(frame, this);
    }

    private void Return() {
        dispose();
        adminOptions frame = new adminOptions();
        Global.frameSetup(frame, this);
    }

    private void saveInfo(){
            String name = textField2.getText();
            String address = textField1.getText();
            MedCentre newMed = new MedCentre(name, address, 0);
            newMed.save();
    }

    public static void main(String[] args) {
        newCentre frame = new newCentre();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
