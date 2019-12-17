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

    newCentre(){
        setContentPane(centrePanel);
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
            String name = textField2.getText();
            String address = textField1.getText();
            MedCentre newMed = new MedCentre(name, address);
            newMed.saveMC();
            //KAY SAVE THE INFO

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
        newCentre frame3 = new newCentre();
        frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame3.pack();
        frame3.setSize(700,400);
        frame3.setLocationRelativeTo(null);
        frame3.setVisible(true);
    }
}
