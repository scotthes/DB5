package com.quad;

import com.quad.ClientData.GP;
import com.quad.ClientData.Patient;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
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
                dispose();
                mainForm frame = new mainForm();
                Global.frameSetup(frame);
            }
        });
    }

    private void search(int type){
        String name = textField1.getText();
        String address = textField3.getText();
        String day = (String) comboBox2.getSelectedItem(); //Day OB
        String month = (String) comboBox3.getSelectedItem(); //Month OB
        String year = (String) comboBox1.getSelectedItem(); //Year OB
        String bDay = year+" "+month+" "+day;
        Patient pSearch = new Patient(name, null, null, 0, InputStream.nullInputStream(),null , address, bDay);
        //GP gSearch = new GP(name, null, null, 0, InputStream.nullInputStream(),null , address, bDay);
        int resultCount = pSearch.searchCount();
        if(resultCount == 0){
            JOptionPane.showMessageDialog(null, "Sorry, no patient with those details can be found!");
        }
        else {
            patientSearchResults frame = new patientSearchResults(pSearch, type, 0);
            Global.frameSetup(frame);
            this.dispose(); //WE NEED TO LOOK AT IF THERE ARE NO RESULTS... THERE IS A BUG
        }
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

    public static void main(String[] args) {
        int blankInt = 3;
        patientSearch frame = new patientSearch(blankInt);
        Global.frameSetup(frame);
    }
}
