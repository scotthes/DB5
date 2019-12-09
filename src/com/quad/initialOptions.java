package com.quad;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Define initialOptions class as an extension of JFrame
public class initialOptions extends JFrame {
    //IntelliJ automatically instantiates the below
    private JButton newCentre;
    private JPanel optionsPanel;
    private JButton newPat;
    private JButton newGP;
    private JLabel TitleLabel;

    initialOptions() {
        setContentPane(optionsPanel);
        newCentre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                enterNewCentre();
            }
        });
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        newPat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                editPatient();
            }
        });
        newGP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                enterNewGP();
            }
        });
    }

    private void enterNewCentre(){
        dispose();
        newCentre frame = new newCentre();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void editPatient(){
        dispose();
        editPatient frame = new editPatient();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void enterNewGP(){
        dispose();
        newGP frame = new newGP();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        initialOptions frame2 = new initialOptions();
        frame2.pack();
        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
