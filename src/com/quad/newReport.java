package com.quad;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class newReport extends JFrame{
    private JTextField textField1;
    private JTextArea textArea1;
    private JCheckBox yesCheckBox;
    private JCheckBox noCheckBox;
    private JButton OKButton;
    private JPanel newReportPanel;

    private newReport() {
        setContentPane(newReportPanel);
        getRootPane().setDefaultButton(OKButton);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
    }

    public static void main(String[] args) {
        newReport frame2 = new newReport();
        frame2.pack();
        frame2.setSize(700,400);
        frame2.setLocationRelativeTo(null);
        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
