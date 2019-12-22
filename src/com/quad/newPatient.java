package com.quad;

import com.quad.ClientData.MedCentre;
import com.quad.ClientData.Patient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class newPatient extends JFrame {
    private JPanel newPatientPanel;
    private JButton OKButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JComboBox comboBox1;
    private JTextField textField4;
    private JTextField textField5;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JComboBox comboBox4;

    newPatient() {
        setContentPane(newPatientPanel);
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

    private void saveInfo() {
        String name =textField1.getText(); //Name
        String email = textField2.getText(); //Email
        textField3.getText(); //ID
        String phoneNum = textField4.getText(); //Phone Num
        String address = textField5.getText(); //Address

        String day = (String) comboBox2.getSelectedItem(); //Day OB
        String month = (String) comboBox3.getSelectedItem(); //Month OB
        String year = (String) comboBox4.getSelectedItem(); //Year OB
        String bDay = year+" "+month+" "+day;
        MedCentre medC = new MedCentre((String) comboBox1.getSelectedItem(), "", 0); //Med Centre
        medC = medC.search();
        if (medC.getID()==0){
            //ERROR NO MED CENTRE FOUND W/E
        }
        else{
            Patient pNew= new Patient(name, email, medC, 0, phoneNum, address, bDay);
            pNew.save();
            //only saves the patient if a valid medical centre was entered
        }

    }

    private void goBack(){
        dispose();
        editPatient frame = new editPatient();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        newPatient frame5 = new newPatient();
        frame5.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame5.pack();
        frame5.setSize(700,400);
        frame5.setLocationRelativeTo(null);
        frame5.setVisible(true);
    }

}
