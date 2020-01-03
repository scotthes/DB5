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
    private  Patient p;
    private GP gp;
    private boolean isChronic;
    private InputStream chosenImage;

    editReport(Patient currentPatient, int type) {
        caseNotesInput.setLineWrap(true); //Allow textfield input to wrap
        p = currentPatient;
        gp = Global.ActiveGP;
        if (type == 0){
            prevComButton.setVisible(false); //Do not display the previous comments button if it is a new report
        }
        titleLabel.setText(String.format("New Case Report For %s" , p.getName()));
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

        chosenImage = currentPatient.getPicture();
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                        imageUpload();
            }
        });
    }

    private void saveInfo(){
        System.out.println(gp.getName());// THE current GP = gp
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));  //just save 'now' i think
        System.out.println(p.getName());// THE current Patient = p
        String caseNotes = caseNotesInput.getText(); //returns case notes as string
        System.out.println(caseNotes);
        System.out.println(isChronic);//Boolean 'isChronic'

        CaseReport newCR = new CaseReport("", gp.getID(), p.getID());
        if (isChronic != newCR.getChronic()){
            newCR.setChronic(isChronic);
        }
        newCR.save();

        String medName = medInput.getText();
        int duration = Integer.parseInt(durationInput.getText());
        Medication med = new Medication(medName, now, duration, "", newCR.getCaseID());
        med.save();

        String noteText = caseNotesInput.getText();
        Note note = new Note(now, noteText, newCR.getCaseID());
        note.save();
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

    private void goBack(){
        dispose();
        patientHome frame = new patientHome(p);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,400);
        frame.setLocation(this.getLocation());
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Patient blankPatient = new Patient(" "," ",null,0," "," "," ");
        editReport frame2 = new editReport(blankPatient,0);
        frame2.pack();
        frame2.setSize(1200,600);
        frame2.setLocationRelativeTo(null);
        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
