package com.quad;

import com.quad.ClientData.Patient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class patientSearch extends JFrame {

    private JPanel optionsGPPanel;
    private JButton OKButton;
    private JTextField textField1;
    private JTextField textField3;
    private JButton logOutButton;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private ArrayList<String> namesResults= new ArrayList<>();

    patientSearch(int type){
        setContentPane(optionsGPPanel);
        getRootPane().setDefaultButton(OKButton);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                search(type);
            }
        });
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                homePage();
            }
        });
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

    private void search(int type){
        String name = textField1.getText();
        String address = textField3.getText();
        String day = (String) comboBox2.getSelectedItem(); //Day OB
        String month = (String) comboBox3.getSelectedItem(); //Month OB
        String year = (String) comboBox1.getSelectedItem(); //Year OB
        String bDay = year+" "+month+" "+day;
        Patient pSearch = new Patient(name, null, null, 0, null, address, bDay);
        int resultCount = pSearch.searchCount();
        ArrayList<Patient> results = pSearch.searchPatient(0);

        System.out.println(resultCount);
        for (Patient result : results) {
            namesResults.add(result.getName() + " ----- " + result.getEmail());
            System.out.println(result.getID() + result.getName() + result.getPhoneNum() + result.getEmail() + result.getAddress() +
                    result.getDOBString() + result.getMedC().getID() + result.getMedC().getName() + result.getMedC().getAddress());
        }

        if(resultCount == 0){
            JOptionPane.showMessageDialog(null, "Sorry, no patient with those details can be found!");
        }
        else {
            dispose();
            patientSearchResults frame = new patientSearchResults(results,resultCount, namesResults, type);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setSize(700, 400);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }

    public static void main(String[] args) {
        int blankInt = 2;
        patientSearch frame2 = new patientSearch(blankInt);
        frame2.pack();
        frame2.setSize(700,400);
        frame2.setLocationRelativeTo(null);
        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
