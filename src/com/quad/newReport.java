package com.quad;

import com.quad.ClientData.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
    private JTextField conditionText;
    private JTextField textField1;
    private JButton seePreviousCaseReportsButton;
    private JTextField textField2;
    private JButton browseButton;
    private JLabel image;
    private  Patient p;
    private GP gp;
    private boolean isChronic;
    private InputStream chosenImage;

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

        seePreviousCaseReportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });

        chosenImage = currentPatient.getPicture();
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
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
                        //BufferedImage theImage = null; //HENRY LOOK INTO SAVING THIS TO SQL
                        try {
                            image.setIcon(new ImageIcon(scaleImage(ImageIO.read(chosenImage))));
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
        });
    }

    private static BufferedImage scaleImage(BufferedImage img) throws Exception {
        BufferedImage bi;
        bi = new BufferedImage(60, 100, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(img, 0, 0, 60, 100, null);
        g2d.dispose();
        return bi;
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
        frame2.setSize(1650,1040);
        frame2.setLocationRelativeTo(null);
        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
