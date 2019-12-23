package com.quad;

import com.quad.ClientData.Patient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class patientSearchResults extends JFrame{
    private JPanel searchResultsPanel;
    private JButton searchAgainButton;
    private JLabel titleLabel;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton button8;
    private JButton button9;
    private JButton button10;
    private JButton nextPageButton;
    private ArrayList<JButton> resultsButtons = new ArrayList<>();


    patientSearchResults(ArrayList results, int resultsCount, ArrayList namesResults, int user) {
        setButtons(namesResults, resultsCount);
        setContentPane(searchResultsPanel);
        getRootPane().setDefaultButton(searchAgainButton);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        searchAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                goBack(user);
            }
        });

        for(int i = 0; i < results.size(); i++){
            int finalI = i;
            resultsButtons.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    if (user == 0){
                        JOptionPane.showMessageDialog(null, "You are a GP");
                    }
                    else if (user == 1){
                        Patient currentPatient = (Patient) results.get(finalI);
                        goEditPatient(currentPatient);
                    }
                }
            });
        }
    }

    private void goBack(int user){
        dispose();
        patientSearch frame = new patientSearch(user);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void goEditPatient(Patient currentPatient){
        dispose();
        editPatient frame = new editPatient(currentPatient);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void setButtons(ArrayList results, int resultsCount){
        titleLabel.setText(String.format("%d Results Were Found", resultsCount));
        searchAgainButton.setText("Search Again");
        nextPageButton.setText("Next Page");
        resultsButtons.add(button1);
        resultsButtons.add(button2);
        resultsButtons.add(button3);
        resultsButtons.add(button4);
        resultsButtons.add(button5);
        resultsButtons.add(button6);
        resultsButtons.add(button7);
        resultsButtons.add(button8);
        resultsButtons.add(button9);
        resultsButtons.add(button10);
        for(int i = 0; i < resultsCount; i++){
            resultsButtons.get(i).setText((String) results.get(i));
        }
    }
    public static void main(String[] args) {
        ArrayList blankArray = new ArrayList();
        int blankInt = 2;
        int blankUser = 2;
        patientSearchResults frame2 = new patientSearchResults(blankArray, blankInt, blankArray, blankUser);
        frame2.pack();
        frame2.setSize(700,400);
        frame2.setLocationRelativeTo(null);
        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
