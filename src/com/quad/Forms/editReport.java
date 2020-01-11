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
    private JComboBox<String> yearBox;
    private JComboBox<String> durationBox;
    private JButton newMedButton;
    private JButton enlargeImageButton;
    private JComboBox<String> crMeds;
    private boolean isChronic;
    private InputStream chosenImage;
    private int commentsIterator;
    private CaseReport caseR;

    editReport(CaseReport cR) {
        comboBoxSetup();
        caseR = cR;
        caseNotesInput.setLineWrap(true); //Allow textfield input to wrap
        enlargeImageButton.setVisible(false); //Don't allow user to see enlarge image button by default


        if (caseR.getCaseID() == 0){
            prevComButton.setVisible(false); //Do not display the previous comments button if it is a new report
            titleLabel.setText(String.format("New Case Report For %s" , Global.ActivePatient.getName()));
        }
        else if(Global.ActivePatient.getID() == 0){
            setupForAdmin();
            titleLabel.setText(String.format("Case Report For %s" , caseR.getPatientName()));
        }
        else{
            caseR.loadNotes();
            caseR.loadMedications();
            fillExisting();
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
                    caseNotesInput.setEditable(false);  // Comments box is uneditable when viewing previous comments
                    pack();                             // making uneditable messes up sizing, so page size is refreshed
                    setSize(1200, 600);
                }
                commentsIterator++;
                if (commentsIterator == caseR.getNotesSize()){
                    commentsIterator = 0;
                    caseNotesInput.setEditable(true);
                    pack();
                    setSize(1200, 600);
                }
                fillComments(caseR.getNote(commentsIterator));
            }
        });

        chosenImage = InputStream.nullInputStream();

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
        newMedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                saveCurrentMed();
                medInput.setText("");
                durationBox.setSelectedItem(0);
                dayBox.setSelectedItem(0);
                monthBox.setSelectedItem(0);
                yearBox.setSelectedItem(0);
                refresh();
            }
        });
        enlargeImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFrame viewImage = new JFrame();
                ImageIcon icon = null;
                try {
                    icon = new ImageIcon((ImageIO.read(caseR.loadImage())));
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
        crMeds.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fillMed(caseR.getMed(crMeds.getSelectedIndex()));
            }
        });
    }

    private void saveInfo(CaseReport caseR){
        CaseReport CR = new CaseReport(conditionText.getText(), Global.ActiveGP.getID(), Global.ActivePatient.getID(), caseR.getCaseID());
        if (isChronic != CR.getChronic()){
            CR.setChronic(isChronic);
        }
        CR.save();
        CR.saveImage(chosenImage);

        LocalDateTime now = LocalDateTime.now();
        if (commentsIterator != 0) {
            caseR.getNote(0).save();
        }  // if view previous comments and press save, saves the stored new edited comment
        else {
            String noteText = caseNotesInput.getText();
            Note note = new Note(now, noteText, CR.getCaseID());
            note.save();
        }  // otherwise just saves the content of the comment box (aka the non-stored new comment)

        //saveCurrentMed();  DON't NEED TO SAVE TWICE
    }

    private void saveCurrentMed(){
        String day = (String) dayBox.getSelectedItem(); //Day OB
        String month = (String) monthBox.getSelectedItem(); //Month OB
        String year = (String) yearBox.getSelectedItem(); //Year OB
        String start = year+" "+month+" "+day;  //gets start date as a string
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy MMMM d");
        LocalDate startDate = LocalDate.parse(start, dtf); //converts date string to LocalDate
        String medName = medInput.getText();
        int duration = Integer.parseInt((String) Objects.requireNonNull(durationBox.getSelectedItem()));
        Medication med = new Medication(medName,startDate, duration, "", caseR.getCaseID());
        System.out.println(caseR.getMedSize());
        if(caseR.getMedSize()!=0){
            if(!med.equals(caseR.getMed(0))){
                med.save();
            }
        } //if there are existing medications for this case report, only saves the entered medication if it has been edited
        else{
            med.save();
        } //if there are no existing medications for this case report, then saves the entered medication
    }

    private void setupForAdmin(){
        caseR.loadNotes();
        caseR.loadMedications();
        fillExisting();
        conditionText.setEditable(false);
        durationBox.setEditable(false);
        medInput.setEditable(false);
        caseNotesInput.setEditable(false);
        browseButton.setVisible(false);
        newMedButton.setVisible(false);
        // Admin cant edit fields or set picture
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

    private void fillExisting(){
        conditionText.setText(caseR.getCondition());
        yesCheckBox.setSelected(caseR.getChronic());
        noCheckBox.setSelected(!caseR.getChronic());

        if (caseR.getMedSize()!=0){
            for(int i = 0; i < caseR.getMedSize(); i++){
                Medication med = caseR.getMed(i);
                crMeds.addItem(med.getName()) ;
            }
            fillMed(caseR.getMed(0));
        }
        try {
            image.setIcon(new ImageIcon(Global.scaleImage(ImageIO.read(caseR.loadImage()))));
            enlargeImageButton.setVisible(true); //if there is a photo, allow user to enlarge
        } catch (Exception e) {
            e.printStackTrace();
        }
        fillComments(caseR.getNote(0));
    }

    private void fillMed(Medication Med){
        medInput.setText(Med.getName());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d");
        dayBox.setSelectedItem(dtf.format(Med.getStartDate()));
        dtf = DateTimeFormatter.ofPattern("MMMM");
        monthBox.setSelectedItem(dtf.format(Med.getStartDate()));
        dtf = DateTimeFormatter.ofPattern("yyyy");
        yearBox.setSelectedItem(dtf.format(Med.getStartDate()));
        durationBox.setSelectedItem(String.valueOf(Med.getDuration()));
    }
    private void fillComments(Note note){
        caseNotesInput.setText(note.getText());
    }

    private void comboBoxSetup(){
        AutoCompletion.enable(durationBox); //Enable searchable combobox
        for(int i = 1; i < 1000; i ++){
            durationBox.addItem(String.valueOf(i));
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

    private void refresh(){
        dispose();
        editReport frame = new editReport(caseR);
        Global.frameSetup(frame, this);
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
