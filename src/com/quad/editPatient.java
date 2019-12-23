package com.quad;

import com.quad.ClientData.MedCentre;
import com.quad.ClientData.Patient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class editPatient extends JFrame {
    private JPanel newPatientPanel;
    private JButton OKButton;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField idField;
    private JComboBox<String> medCentreBox;
    private JTextField phoneField;
    private JTextField addressField;
    private JComboBox dayBox;
    private JComboBox monthBox;
    private JComboBox yearBox;

    editPatient(Patient currentPatient) {
        comboBoxSetup();
        fillExistingDetails(currentPatient);
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
        String name = nameField.getText(); //Name
        String email = emailField.getText(); //Email
        idField.getText(); //ID
        String phoneNum = phoneField.getText(); //Phone Num
        String address = addressField.getText(); //Address
        String day = (String) dayBox.getSelectedItem(); //Day OB
        String month = (String) monthBox.getSelectedItem(); //Month OB
        String year = (String) yearBox.getSelectedItem(); //Year OB
        String bDay = year+" "+month+" "+day;
        MedCentre medC = new MedCentre((String) medCentreBox.getSelectedItem(), "", 0); //Med Centre
        medC = medC.search();
        if (medC.getID()==0){
            JOptionPane.showMessageDialog(null, "Sorry, there is no medical centre with that name!");//ERROR NO MED CENTRE FOUND W/E
        }
        else{
            Patient pNew= new Patient(name, email, medC, 0, phoneNum, address, bDay);
            pNew.save();
            //only saves the patient if a valid medical centre was entered
        }

    }

    private void goBack(){
        dispose();
        adminOptions frame = new adminOptions();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void comboBoxSetup(){
        AutoCompletion.enable(medCentreBox); //Enable searchable combobox
        medCentreBox.addItem("Chelsea & Westminster Hospital");
        medCentreBox.addItem("Chelsea Clinic");
        medCentreBox.addItem("Battersea Bridge GP");
        medCentreBox.addItem("Bridge Lane Medical Practice");
    }

    private void fillExistingDetails(Patient currentPatient){
        if(!currentPatient.getName().equals(" ")){
            nameField.setText(currentPatient.getName());
        }
        if(!currentPatient.getEmail().equals(" ")){
            emailField.setText(currentPatient.getEmail());
        }
        if(currentPatient.getID() != 0){
            idField.setText(String.valueOf(currentPatient.getID()));
        }
        if(!currentPatient.getAddress().equals(" ")){
            addressField.setText(currentPatient.getAddress());
        }
        if(!currentPatient.getPhoneNum().equals(" ")){
            phoneField.setText(currentPatient.getPhoneNum());
        }
        //NEED TO ADD MED CENTRE AND D.O.B BUT IT IS HARD

    }

    public static void main(String[] args) {
        editPatient frame = new editPatient(new Patient(" "," ",null,0," "," "," "));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
