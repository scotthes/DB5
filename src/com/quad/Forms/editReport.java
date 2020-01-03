package com.quad.Forms;

import com.quad.ClientData.*;
import com.quad.Global;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class editReport extends JFrame{
    private JTextArea caseNotesInput;
    private JCheckBox yesCheckBox;
    private JCheckBox noCheckBox;
    private JButton OKButton;
    private JPanel newReportPanel;
    private JTextField medInput;
    private JLabel titleLabel;
    private JTextField durationInput;
    private JTextField conditionText;
    private JTextField textField1;
    private JButton prevComButton;
    private JTextField textField2;
    private JButton browseButton;
    private JLabel image;
    private boolean isChronic;
    private InputStream chosenImage;

    editReport(CaseReport caseR) {
        caseNotesInput.setLineWrap(true); //Allow textfield input to wrap
        if (caseR.getCaseID() == 0){
            prevComButton.setVisible(false); //Do not display the previous comments button if it is a new report
        }
        else{
            caseR.loadNotes();
            caseR.loadMedications();
            fillExisting(caseR);
        }
        titleLabel.setText(String.format("New Case Report For %s" , Global.ActivePatient.getName()));
        setContentPane(newReportPanel);
        getRootPane().setDefaultButton(OKButton);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(isConditionChronic()){
                    saveInfo();
                    goBack();
                }
            }
        });

        prevComButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //BUTTON FOR HENRY... NOT SURE HOW TO DO THIS REALLY
            }
        });

        chosenImage = Global.ActivePatient.getPicture();
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                        imageUpload();
            }
        });
    }

    private void saveInfo(){
        CaseReport newCR = new CaseReport("", Global.ActiveGP.getID(), Global.ActivePatient.getID(), 0);
        if (isChronic != newCR.getChronic()){
            newCR.setChronic(isChronic);
        }
        newCR.save();

        LocalDate now = LocalDate.now();
        String noteText = caseNotesInput.getText();
        Note note = new Note(now, noteText, newCR.getCaseID());
        note.save();

        String medName = medInput.getText();
        int duration = Integer.parseInt(durationInput.getText());
        Medication med = new Medication(medName, now, duration, "", newCR.getCaseID());
        med.save();
    }

    private boolean isConditionChronic(){
        if(yesCheckBox.isSelected() && !noCheckBox.isSelected()){
            isChronic = true;
            return true;
        }
        else if (noCheckBox.isSelected() && !yesCheckBox.isSelected()){
            isChronic = false;
            return true;
        }
        else{
            JOptionPane.showMessageDialog(null,"Please Select One Checkbox for Chronic or Not!");
            return false;
        }
    }

    private void imageUpload(){
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        try {
            chosenImage = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String filename = f.getAbsolutePath();
        textField2.setText(filename);
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

    private void fillExisting(CaseReport caseR){
        conditionText.setText(caseR.getCondition());
        yesCheckBox.setSelected(caseR.getChronic());
        noCheckBox.setSelected(!caseR.getChronic());
        medInput.setText(caseR.getMed(0).getName());
        durationInput.setText(String.valueOf(caseR.getMed(0).getDuration()));
        fillComments(caseR.getNote(0));
    }
    private void fillComments(Note note){
        caseNotesInput.setText(note.getText());
    }

    private void goBack(){
        patientHome frame = new patientHome();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,400);
        frame.setLocation(this.getLocation());
        frame.setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        Global.ActivePatient = new Patient(" "," ",null,0," "," "," ");
        CaseReport nullCaseR = new CaseReport("", 0, 0, 0);
        editReport frame2 = new editReport(nullCaseR);
        frame2.pack();
        frame2.setSize(1200,600);
        frame2.setLocationRelativeTo(null);
        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
