package com.quad.Forms;

import com.quad.AutoCompletion;
import com.quad.ClientData.GP;
import com.quad.ClientData.MedCentre;
import com.quad.Global;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;

public class editGP extends JFrame{
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JPanel GPPanel;
    private JButton OKButton;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField photoAddress;
    private JButton button1;
    private JLabel image;
    private JComboBox<String> medCentreBox;
    private JButton logoutButton;
    private JButton cancelAndReturnHomeButton;
    private JLabel titleLabel;
    private JButton reportsButton;
    private JButton viewPreiviousEditsButton;
    private JButton enlargeImageButton;
    private InputStream theImage;

    editGP(GP currentGP){
        viewPreiviousEditsButton.setVisible(false);
        reportsButton.setVisible(false);
        enlargeImageButton.setVisible(false);
        setupComboBox();
        fillExistingDetails(currentGP);
        setContentPane(GPPanel);
        getRootPane().setDefaultButton(OKButton);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        if (currentGP.getTStamp() != null){
            textField1.setEditable(false);
            textField2.setEditable(false);
            textField3.setEditable(false);
            textField5.setEditable(false);
            textField6.setEditable(false);
            photoAddress.setEditable(false);
            medCentreBox.setEditable(false);
            button1.setVisible(false);
        }
        theImage = InputStream.nullInputStream();
        try {
            image.setIcon(new ImageIcon(Global.scaleImage(ImageIO.read(currentGP.getPicture()))));
            enlargeImageButton.setVisible(true); // if there is an image, allow user to view
        } catch (Exception e) {
            e.printStackTrace();
        }
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                saveInfo(currentGP.getID());
                Return();
            }
        });

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    imageUpload();
                } catch (Exception e) {
                    //exceptions occur when user cancels due to attempts to read/scale an image that is never selected,
                    // this is not an issue so exceptions are ignored
                }
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
        reportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Global.ActiveGP=currentGP;
                goReports();
            }
        });
        viewPreiviousEditsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                GP gpNew = currentGP.getPrev();
                refresh(gpNew);
            }
        });
        enlargeImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFrame viewImage = new JFrame();
                ImageIcon icon = null;
                try {
                    icon = new ImageIcon(ImageIO.read(currentGP.getPicture()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                JLabel label = new JLabel(icon);
                viewImage.setLocation(getLocation());
                viewImage.add(label);
                viewImage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                viewImage.pack();
                viewImage.setVisible(true);
            }
        });
    }

    private void goReports(){
        patientHome frame = new patientHome();
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

    private void refresh(GP gp) {
        editGP frame = new editGP(gp);
        Global.frameSetup(frame, this);
        dispose();
    }

    private void fillExistingDetails(GP currentGP){
        if(!currentGP.getName().equals(" ")){
            titleLabel.setText(currentGP.getName());
            textField1.setText(currentGP.getName());
            textField6.setText("DO NOT CHANGE");
            reportsButton.setVisible(true);
            viewPreiviousEditsButton.setVisible(true);
        }
        if(!currentGP.getEmail().equals(" ")){
            textField2.setText(currentGP.getEmail());
        }
        if(!currentGP.getPagerNum().equals(" ")){
            textField3.setText(currentGP.getPagerNum());
        }
        if(!currentGP.getUserName().equals(" ")){
            textField5.setText(currentGP.getUserName());
        }
        if(currentGP.getMedC()!=null){
            medCentreBox.setSelectedItem(currentGP.getMedC().getName());
        }

    }

    private void saveInfo(int id){
        String name = textField1.getText(); //Name
        String email = textField2.getText(); //Email
        String pagerNum = textField3.getText(); //Pager Number
        String username = textField5.getText(); //Username
        String Password = textField6.getText(); //Password
        MedCentre medC = new MedCentre((String) medCentreBox.getSelectedItem(), "", 0); //Med Centre
        medC = medC.search();
        if (medC.getID()==0){
            JOptionPane.showMessageDialog(null, "Sorry, no medical centre with that name could be found!");
        }
        else{
            GP nGP = new GP(name, email, medC, id, pagerNum, username, Password);
            nGP.save(theImage);
            //only saves the gp if valid medical centre entered
        }

    }

    private void setupComboBox(){
        AutoCompletion.enable(medCentreBox); //Enable searchable combobox
        for (int i = 0; i < Global.MCList.size(); i++){
            medCentreBox.addItem(Global.MCList.get(i).getName());
        }
    }

    private void imageUpload() throws Exception {
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        theImage = new FileInputStream(f);
        String filename = f.getAbsolutePath();
        photoAddress.setText(filename);
        image.setIcon(new ImageIcon(Global.scaleImage(ImageIO.read(theImage))));
        theImage = new FileInputStream(f);
        System.out.println("Images were written succesfully.");
    }

    public static void main(String[] args) {
        GP nullGP = new GP(" ", " ", null, 0, " ", " ", " ");
        editGP frame = new editGP(nullGP);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
