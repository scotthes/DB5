package com.quad;

import com.quad.ClientData.MedCentre;
import com.quad.ClientData.Patient;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
    private JButton button1;
    private JTextField textField1;
    private JLabel image;

    editPatient(Patient currentPatient) {
        comboBoxSetup();
        fillExistingDetails(currentPatient);
        setContentPane(newPatientPanel);
        getRootPane().setDefaultButton(OKButton);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        int id = currentPatient.getID();

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                saveInfo(id);
                goBack();
            }
        });

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser chooser = new JFileChooser();
                chooser.showOpenDialog(null);
                File f = chooser.getSelectedFile();
                String filename = f.getAbsolutePath();
                textField1.setText(filename);
                try {
                    ImageIcon ii=new ImageIcon(scaleImage(ImageIO.read(new File(f.getAbsolutePath()))));//get the image from file chooser and scale it to match JLabel size
                    image.setIcon(ii); //SHOW THE IMAGE

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                BufferedImage theImage = null; //HENRY LOOK INTO SAVING THIS TO SQL
                try {
                    theImage = ImageIO.read(f);
                    ImageIO.write(theImage, "jpg", new File("C://Users/scott/Desktop/image.png")); //THIS SAVES IT LOCALLY SOMEWHERE IDK
                } catch (IOException e) {
                    System.out.println("Exception occured :" + e.getMessage());
                }
                System.out.println("Images were written succesfully.");
            }
        });

    }

    private static BufferedImage scaleImage(BufferedImage img) throws Exception {
        BufferedImage bi;
        bi = new BufferedImage(60, 100, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(img, 0, 0, 120, 120, null);
        g2d.dispose();
        return bi;
    }

    private void saveInfo(int id) {
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
            Patient pNew= new Patient(name, email, medC, id, phoneNum, address, bDay);
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
