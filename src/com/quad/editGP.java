package com.quad;

import com.quad.ClientData.GP;
import com.quad.ClientData.MedCentre;

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
    private InputStream theImage;
    editGP(){
        setContentPane(GPPanel);
        getRootPane().setDefaultButton(OKButton);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setupComboBox();
        theImage = null;
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                saveInfo();
                dispose();
                adminOptions frame = new adminOptions();
                Global.frameSetup(frame);
            }
        });

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser chooser = new JFileChooser();
                chooser.showOpenDialog(null);
                File f = chooser.getSelectedFile();
                try {
                    theImage = new FileInputStream(f);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                String filename = f.getAbsolutePath();
                photoAddress.setText(filename);
                //BufferedImage theImage = null; //HENRY LOOK INTO SAVING THIS TO SQL
                try {
                    image.setIcon(new ImageIcon(scaleImage(ImageIO.read(theImage))));
                } catch (Exception e) {
                    e.getMessage();
                }
                try {
                    theImage = new FileInputStream(f);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                System.out.println("Images were written succesfully.");
            }
        });


        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
                mainForm frame = new mainForm();
                Global.frameSetup(frame);
            }
        });
        cancelAndReturnHomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
                adminOptions frame = new adminOptions();
                Global.frameSetup(frame);
            }
        });
    }

    private void saveInfo(){
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
            GP nGP = new GP(name, email, medC, 0, theImage, pagerNum, username, Password);
            nGP.save();
            //only saves the gp if valid medical centre entered
        }

    }

    private static BufferedImage scaleImage(BufferedImage img) throws Exception {
        BufferedImage bi;
        bi = new BufferedImage(120, 120, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(img, 0, 0, 120, 120, null);
        g2d.dispose();
        return bi;
    }

    private void setupComboBox(){
        AutoCompletion.enable(medCentreBox); //Enable searchable combobox
        for (int i = 0; i < Global.MCList.size(); i++){
            medCentreBox.addItem(Global.MCList.get(i).getName());
        }
    }

    public static void main(String[] args) {
        editGP frame = new editGP();
        Global.frameSetup(frame);
    }
}
