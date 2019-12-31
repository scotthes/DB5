package com.quad;

import com.quad.ClientData.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class newReport extends JFrame{
    private JTextArea caseNotesInput;
    private JCheckBox yesCheckBox;
    private JCheckBox noCheckBox;
    private JButton OKButton;
    private JPanel newReportPanel;
    private JTextField medInput;
    private JLabel titleLabel;
    private JTextField durationInput;
    private  Patient p;
    private GP gp;
    private boolean isChronic;

    newReport(Patient currentPatient) {
        p = currentPatient;
        gp = Global.ActiveGP;
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
    }

    private void goBack(){
        dispose();
        patientHome frame = new patientHome(p);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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

    public static void main(String[] args) {
        Patient blankPatient = new Patient(" "," ",null,0, null," "," "," ");
        newReport frame2 = new newReport(blankPatient);
        frame2.pack();
        frame2.setSize(700,400);
        frame2.setLocationRelativeTo(null);
        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
