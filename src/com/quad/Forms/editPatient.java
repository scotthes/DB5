package com.quad.Forms;

import com.quad.AutoCompletion;
import com.quad.ClientData.MedCentre;
import com.quad.ClientData.Patient;
import com.quad.Global;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;

public class editPatient extends JFrame {
    private JButton OKButton;
    private JTextField nameField;
    private JTextField emailField;
    private JComboBox<String> medCentreBox;
    private JTextField phoneField;
    private JTextField addressField;
    private JComboBox dayBox;
    private JComboBox monthBox;
    private JComboBox yearBox;
    private JButton button1;
    private JTextField textField1;
    private JLabel image;
    private JButton logoutButton;
    private JButton cancelAndReturnHomeButton;
    private JButton viewPreviousEditsButton;
    private JButton enlargeImageButton;
    private JPanel patientEditPanel;
    private InputStream chosenImage;

    editPatient(Patient currentPatient) {
        viewPreviousEditsButton.setVisible(false);
        enlargeImageButton.setVisible(false);
        comboBoxSetup();
        fillExistingDetails(currentPatient);
        setContentPane(patientEditPanel);
        getRootPane().setDefaultButton(OKButton);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        if (currentPatient.getTStamp() != null){
            nameField.setEditable(false);
            emailField.setEditable(false);
            phoneField.setEditable(false);
            addressField.setEditable(false);
            textField1.setEditable(false);
            dayBox.setEditable(false);
            monthBox.setEditable(false);
            yearBox.setEditable(false);
            medCentreBox.setEditable(false);
            button1.setVisible(false);
        }

        chosenImage = InputStream.nullInputStream();
        try {
            image.setIcon(new ImageIcon(Global.scaleImage(ImageIO.read(currentPatient.getPicture()))));
            enlargeImageButton.setVisible(true); //if there is an image, allow enlarge
        } catch (Exception e) {
            e.printStackTrace();
        }

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (currentPatient.getTStamp() == null){
                    saveInfo(currentPatient.getID());
                } // will not save info when viewing legacy patient info via the view previous edits button
                Return();
            }
        });

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                imageUpload();
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
        viewPreviousEditsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Patient pNew =  currentPatient.getPrev();
                refresh(pNew);
            }
        });
        enlargeImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                refresh(currentPatient);
                JFrame viewImage = new JFrame();
                ImageIcon icon = null;
                try {
                    icon = new ImageIcon(ImageIO.read(currentPatient.getPicture()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                JLabel label = new JLabel(icon);

                //label.setMaximumSize(size);
                viewImage.setLocation(getLocation());
                viewImage.add(label);
                viewImage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                viewImage.pack();
                Dimension size = new Dimension(700,400);
                viewImage.setMaximumSize(size);
                viewImage.setResizable(false);
                viewImage.setVisible(true);
            }
        });
    }

    private void refresh(Patient p){
        editPatient frame = new editPatient(p);
        Global.frameSetup(frame, this);
        dispose();
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

    private void saveInfo(int id) {
        String name = nameField.getText(); //Name
        String email = emailField.getText(); //Email
        String phoneNum = phoneField.getText(); //Phone Num
        String address = addressField.getText(); //Address
        String day = (String) dayBox.getSelectedItem(); //Day OB
        String month = (String) monthBox.getSelectedItem(); //Month OB
        String year = (String) yearBox.getSelectedItem(); //Year OB
        String bDay = year + " " + month + " " + day;
        MedCentre medC = new MedCentre((String) medCentreBox.getSelectedItem(), "", 0); //Med Centre
        medC = medC.search();
        if (medC.getID() == 0) {
            JOptionPane.showMessageDialog(null, "Sorry, there is no medical centre with that name!");//ERROR NO MED CENTRE FOUND W/E
        } else {
            Patient pNew = new Patient(name, email, medC, id, phoneNum, address, bDay);
            pNew.save(chosenImage);
            //only saves the patient if a valid medical centre was entered
        }
    }

    private void comboBoxSetup() {
        AutoCompletion.enable(medCentreBox); //Enable searchable combobox
        for (int i = 0; i < Global.MCList.size(); i++) {
            medCentreBox.addItem(Global.MCList.get(i).getName());
        }
    }

    private void fillExistingDetails(Patient currentPatient) {
        if (!currentPatient.getName().equals(" ")) {
            nameField.setText(currentPatient.getName());
            viewPreviousEditsButton.setVisible(true);
        }
        if (!currentPatient.getEmail().equals(" ")) {
            emailField.setText(currentPatient.getEmail());
        }
        if (!currentPatient.getAddress().equals(" ")) {
            addressField.setText(currentPatient.getAddress());
        }
        if (!currentPatient.getPhoneNum().equals(" ")) {
            phoneField.setText(currentPatient.getPhoneNum());
        }
        if (currentPatient.getMedC() != null) {
            medCentreBox.setSelectedItem(currentPatient.getMedC().getName());
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d");
        dayBox.setSelectedItem(dtf.format(currentPatient.getDOBDate()));
        dtf = DateTimeFormatter.ofPattern("MMMM");
        monthBox.setSelectedItem(dtf.format(currentPatient.getDOBDate()));
        dtf = DateTimeFormatter.ofPattern("yyyy");
        yearBox.setSelectedItem(dtf.format(currentPatient.getDOBDate()));
    }

    private void imageUpload() {
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        try {
            chosenImage = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String filename = f.getAbsolutePath();
        textField1.setText(filename);
        //BufferedImage theImage = null; //HENRY LOOK INTO SAVING THIS TO SQL
        try {
            image.setIcon(new ImageIcon(Global.scaleImage(ImageIO.read(chosenImage))));
        } catch (Exception e) {
            e.getMessage();
        }
        try {
            chosenImage = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Images were written succesfully.");
    }

    public static void main(String[] args) {
        editPatient frame = new editPatient(new Patient(" ", " ", null, 0, " ", " ", " "));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}