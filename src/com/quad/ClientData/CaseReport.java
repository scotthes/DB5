package com.quad.ClientData;

import com.quad.DataAccess;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CaseReport {
    private String Condition;
    private ArrayList<Note> Notes;
    private ArrayList<Medication> Medications;
    private int GPID;
    private int PatientID;
    private boolean IsChronic;
    private int CaseID;
    private LocalDate lastModified;
    public CaseReport(String condition,
                      int gpID,
                      int patientID,
                      int caseID){
        Condition = condition;
        GPID = gpID;
        PatientID = patientID;
        CaseID = caseID;
        IsChronic = false; // defaults to not chronic, can be changed with button in UI
        Medications = new ArrayList<>();
        Notes = new ArrayList<>();
    }

    public int getCaseID() {
        return CaseID;
    }

    public String getCondition() {
        return Condition;
    }

    public int getGPID() {
        return GPID;
    }

    public int getPatientID() {
        return PatientID;
    }

    public boolean getChronic(){
        return IsChronic;
    }

    public void setChronic(boolean chronic) {
        IsChronic = chronic;
    }

    public void setLastModified(LocalDate date){
        lastModified = date;
    }

    public String getLastModifiedString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dtf.format(lastModified);
    }

    public void loadNotes(){
        Notes = DataAccess.loadNotes(this.getCaseID());
        Note nullNote = new Note(LocalDateTime.now(), "", this.getCaseID());
        Notes.add(0, nullNote);
    }

    public Note getNote(int index){
        return Notes.get(index);
    }

    public int getNotesSize(){
        return Notes.size();
    }

    public void loadMedications(){
        Medications = DataAccess.loadMedications(this.getCaseID());
    }

    public Medication getMed(int index){
        return Medications.get(index);
    }

    public int getMedSize() {
        return Medications.size();
    }

    public String getPatientName(){
        return DataAccess.getPatientName(this.getPatientID());
    }

    public void save(){
        if (this.getCaseID() == 0){
            CaseID = DataAccess.saveCaseReport(this);
        }
        else {
            DataAccess.updateCaseReport(this);
        }
    }

    public void saveImage(InputStream image){
        DataAccess.saveCasePic(image, this.getCaseID());
    }

    public InputStream loadImage() {
        return DataAccess.loadCasePic(this.getCaseID());
    }
}
