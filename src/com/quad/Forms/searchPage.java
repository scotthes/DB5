package com.quad.Forms;

import com.quad.ClientData.GP;
import com.quad.ClientData.Patient;
import com.quad.Global;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class searchPage extends JFrame {

    private JPanel optionsGPPanel;
    private JButton OKButton;
    private JTextField textField1;
    private JTextField textField3;
    private JButton logOutButton;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JLabel titleLabel;
    private JButton goBackButton;

    searchPage(int type){
        if (type == 2){
            titleLabel.setText("GP Search");
        }
        else if (type == 0){
            goBackButton.setVisible(false); //A searching GP does not need a go back button, only a logout
        }
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
                logout();
            }
        });
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
                if (type == 0){
                    logout();
                }
                else if (type == 1 | type == 2){
                    adminHome();
                }
            }
        });
    }

    private void logout() {
        dispose();
        mainForm frame = new mainForm();
        Global.frameSetup(frame, this);
    }

    private void adminHome(){
        adminOptions frame = new adminOptions();
        Global.frameSetup(frame,this);
    }

    private void search(int type){
        String name = textField1.getText();
        String address = textField3.getText();
        String day = (String) comboBox2.getSelectedItem(); //Day OB
        String month = (String) comboBox3.getSelectedItem(); //Month OB
        String year = (String) comboBox1.getSelectedItem(); //Year OB
        String bDay = year+" "+month+" "+day;

        if (type == 2){
            GP gSearch = new GP(name, null, null, 0,null, name,""); //can search by name or username
            int resultCount = gSearch.searchCount();
            if(resultCount == 0){
                JOptionPane.showMessageDialog(null, "Sorry, no GP with those details can be found!");
            }
            else {
                searchResults frame = new searchResults(gSearch, type, 0);
                Global.frameSetup(frame, this);
                this.dispose();
            }
        }
        else{
            Patient pSearch = new Patient(name, null, null, 0,null , address, bDay);
            int resultCount = pSearch.searchCount();
            if(resultCount == 0){
                JOptionPane.showMessageDialog(null, "Sorry, no patient with those details can be found!");
            }
            else {
                searchResults frame = new searchResults(pSearch, type, 0);
                Global.frameSetup(frame, this);
                this.dispose();
            }
        }

    }

    public static void main(String[] args) {
        int blankInt = 3;
        searchPage frame = new searchPage(blankInt);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}
