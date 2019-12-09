package com.quad;

        import javax.swing.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;

public class editPatient extends JFrame {
    private JButton newPatBut;
    private JButton exPatBut;
    private JButton OKButton;
    private JPanel patientPanel;

    editPatient(){
        setContentPane(patientPanel);
        getRootPane().setDefaultButton(OKButton);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                saveInfo();
                goBack();
            }
        });

        newPatBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                newPatient();
            }
        });
        exPatBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(null, "Sorry, that feature is coming soon!");
            }
        });
    }

    private void saveInfo(){
        //KAY SAVE INFO
    }

    private void newPatient(){
        dispose();
        newPatient frame = new newPatient();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void goBack(){
        dispose();
        initialOptions frame = new initialOptions();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        editPatient frame4 = new editPatient();
        frame4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame4.pack();
        frame4.setVisible(true);
    }

}

