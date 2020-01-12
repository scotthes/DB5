package com.quad.Forms;

import com.quad.ClientData.GP;
import com.quad.ClientData.Patient;
import com.quad.ClientData.Person;
import com.quad.Global;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class searchResults extends JFrame{
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
    private JButton backButton;
    private ArrayList<JButton> resultsButtons = new ArrayList<>();


    searchResults(Person pSearch, int user, int pageNo) {
        setContentPane(searchResultsPanel);
        int resultCount = pSearch.searchCount(); //gets total number of matches from database
        System.out.println(resultCount);
        ArrayList<Person> results = pSearch.search(pageNo); //only retrieves 10 at a time (max that can fit on page) to speed up search of there are many results
        ArrayList<String> namesResults = new ArrayList<>();

        for (Person result : results) {
            namesResults.add(result.getName() + " ----- " + result.getEmail());
        } //gets names to add to buttons

        setButtons(namesResults, resultCount);
        //setContentPane(searchResultsPanel);
        getRootPane().setDefaultButton(searchAgainButton);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        searchAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                goBack(user);
            }
        });

        nextPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (resultCount/10 > pageNo) {
                    int nextPage = pageNo + 1;
                    changePage(nextPage, pSearch, user);
                }
                else{
                    JOptionPane.showMessageDialog(null, "No more results!");
                }
            }
        });
        for(int i = 0; i < results.size(); i++){
            int finalI = i;
            resultsButtons.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    if (user == 0){
                        Global.ActivePatient = (Patient) results.get(finalI);
                        goPatientHome();
                    }
                    else if (user == 1){
                        Patient currentPatient = (Patient) results.get(finalI);
                        goEditPatient(currentPatient);
                    }
                    else if (user == 2){
                        GP currentGP = (GP) results.get(finalI);
                        goEditGP(currentGP);
                    }
                }
            });
        }
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (pageNo == 0){
                    goBack(user);
                }
                else{
                    int nextPage=pageNo-1;
                    changePage(nextPage, pSearch, user);
                }
            }
        });
    }

    private void changePage(int nextPage, Person pSearch, int user) {
        searchResults frame = new searchResults(pSearch, user, nextPage);
        Global.frameSetup(frame, this);
        dispose();
    }

    private void goBack(int user){
        searchPage frame = new searchPage(user);
        Global.frameSetup(frame, this);
        dispose();
    }

    private void goEditPatient(Patient currentPatient){
        editPatient frame = new editPatient(currentPatient);
        Global.frameSetup(frame, this);
        dispose();
    }

    private void goPatientHome(){
        patientHome frame = new patientHome();
        Global.frameSetup(frame, this);
        dispose();
    }

    private void goEditGP(GP currentGP){
        editGP frame = new editGP(currentGP);
        Global.frameSetup(frame, this);
        dispose();
    }


    private void setButtons(ArrayList results, int resultsCount){
        titleLabel.setText(String.format("%d Result(s) Found", resultsCount));
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
        for(int i = 0; i < results.size(); i++){
            resultsButtons.get(i).setText((String) results.get(i));
        }
        for(int i = results.size(); i < 10; i++){
            resultsButtons.get(i).setVisible(false);
        }
    }
    public static void main(String[] args) {
        Patient blankPatient = new Patient(" "," ",null,0," "," ","1915 January 01");
        int blankUser = 3;
        searchResults frame = new searchResults(blankPatient, blankUser, 0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
