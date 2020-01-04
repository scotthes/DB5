package com.quad.Forms;

import com.quad.AutoCompletion;
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
import java.util.Objects;

public class editReport extends JFrame{
    private JTextArea caseNotesInput;
    private JCheckBox yesCheckBox;
    private JCheckBox noCheckBox;
    private JButton OKButton;
    private JPanel newReportPanel;
    private JTextField medInput;
    private JLabel titleLabel;
    private JTextField conditionText;
    private JButton prevComButton;
    private JTextField imageURL;
    private JButton browseButton;
    private JLabel image;
    private JButton cancelButton;
    private JComboBox dayBox;
    private JComboBox monthBox;
    private JComboBox yearBox;
    private JComboBox<Integer> durationBox;
    private boolean isChronic;
    private InputStream chosenImage;
    private int commentsIterator;
    private CaseReport caseR;

    editReport(CaseReport cR) {
        comboBoxSetup();
        caseR = cR;
        caseNotesInput.setLineWrap(true); //Allow textfield input to wrap

        if (caseR.getCaseID() == 0){
            prevComButton.setVisible(false); //Do not display the previous comments button if it is a new report
            titleLabel.setText(String.format("New Case Report For %s" , Global.ActivePatient.getName()));
        }
        else if(Global.ActivePatient.getID() == 0){
            setupForAdmin();
        }
        else{
            caseR.loadNotes();
            caseR.loadMedications();
            fillExisting(caseR);
            titleLabel.setText(String.format("Case Report For %s" , Global.ActivePatient.getName()));
        }

        setContentPane(newReportPanel);
        getRootPane().setDefaultButton(prevComButton);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(isConditionChronic()){
                    if (Global.ActivePatient.getID()!=0){
                        saveInfo(caseR);
                    } //Does not save edits if no active patient - this is when an admin is viewing case reports
                    goBack();
                }
            }
        });

        commentsIterator = 0;
        prevComButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (commentsIterator == 0){
                    caseR.getNote(0).setText(caseNotesInput.getText());
                    //caseNotesInput.setEditable(false);
                }
                commentsIterator++;
                if (commentsIterator == caseR.getNotesSize()){
                    commentsIterator = 0;
                    //caseNotesInput.setEditable(true);
                }
                fillComments(caseR.getNote(commentsIterator));
            }
        });

        chosenImage = Global.ActivePatient.getPicture();

        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                        imageUpload();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                goBack();
            }
        });
    }

    private void saveInfo(CaseReport caseR){
        CaseReport CR = new CaseReport(conditionText.getText(), Global.ActiveGP.getID(), Global.ActivePatient.getID(), caseR.getCaseID());
        if (isChronic != CR.getChronic()){
            CR.setChronic(isChronic);
        }
        CR.save();

        LocalDateTime now = LocalDateTime.now();
        String noteText = caseNotesInput.getText();
        Note note = new Note(now, noteText, CR.getCaseID());
        note.save();

        String day = (String) dayBox.getSelectedItem(); //Day OB
        String month = (String) monthBox.getSelectedItem(); //Month OB
        String year = (String) yearBox.getSelectedItem(); //Year OB
        String start = year+" "+month+" "+day;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy MMMM d");
        LocalDate startDate = LocalDate.parse(start, dtf);
        String medName = medInput.getText();
        int duration = Integer.parseInt((String) Objects.requireNonNull(durationBox.getSelectedItem()));
        Medication med = new Medication(medName,startDate, duration, "", CR.getCaseID());
        if(caseR.getMedSize()!=0){
            if(!med.equals(caseR.getMed(0))){
                med.save();
            }
        }
        else{
            med.save();
        }
    }

    private void setupForAdmin(){
        caseR.loadNotes();
        caseR.loadMedications();
        fillExisting(caseR);
        titleLabel.setText("Case Report");
        conditionText.setEditable(false);
        durationBox.setEditable(false);
        medInput.setEditable(false);
        caseNotesInput.setEditable(false);
        browseButton.setVisible(false);
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
        imageURL.setText(filename);
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
    }

    private void fillExisting(CaseReport caseR){
        conditionText.setText(caseR.getCondition());
        yesCheckBox.setSelected(caseR.getChronic());
        noCheckBox.setSelected(!caseR.getChronic());

        if (caseR.getMedSize()!=0){
            medInput.setText(caseR.getMed(0).getName());
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d");
            dayBox.setSelectedItem(dtf.format(caseR.getMed(0).getStartDate()));
            dtf = DateTimeFormatter.ofPattern("MMMM");
            monthBox.setSelectedItem(dtf.format(caseR.getMed(0).getStartDate()));
            dtf = DateTimeFormatter.ofPattern("yyyy");
            System.out.println(dtf.format(caseR.getMed(0).getStartDate()));
            yearBox.setSelectedItem(dtf.format(caseR.getMed(0).getStartDate()));
            durationBox.setSelectedItem(String.valueOf(caseR.getMed(0).getDuration()));
        }

        fillComments(caseR.getNote(0));
    }
    private void fillComments(Note note){
        caseNotesInput.setText(note.getText());
    }

    private void comboBoxSetup(){
        AutoCompletion.enable(durationBox); //Enable searchable combobox
        for(int i = 0; i < 1000; i ++){
            durationBox.addItem(i);
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy");
        int currentYear = Integer.parseInt(dtf.format(LocalDateTime.now()));
        for (int i = 2000; i<currentYear+20; i++){
            yearBox.addItem(String.valueOf(i));
        }
        yearBox.setSelectedItem(dtf.format(LocalDate.now()));
        dtf = DateTimeFormatter.ofPattern("MMMM");
        monthBox.setSelectedItem(dtf.format(LocalDate.now()));
        dtf = DateTimeFormatter.ofPattern("d");
        dayBox.setSelectedItem(dtf.format(LocalDate.now()));

    }

    private void goBack(){
        patientHome frame = new patientHome();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,400);
        frame.setResizable(false);
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
        frame2.setResizable(false);
        frame2.setLocationRelativeTo(null);
        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
