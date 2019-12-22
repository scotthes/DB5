package com.quad.ClientData;

import java.util.ArrayList;
import java.util.Date;

public class CaseReport {
    private String Condition;
    private ArrayList<Note> Notes;
    private ArrayList<Medication> Medications;
    private int GPID;
    private int PatientID;
    private boolean IsChronic;
    private int CaseID;

    public CaseReport(String condition,
                      int gpID,
                      int patientID){
        Condition = condition;
        GPID = gpID;
        PatientID = patientID;
        IsChronic = false; // defaults to not chronic, can be changed with button in UI
    }

    public int getCaseID() {
        return CaseID;
    }

    public String getCondition() {
        return Condition;
    }

    public void setCondition(String condition) {
        Condition = condition;
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

    public void addNote(Date date,
                        String time,
                        String text){
        Note note = new Note(date, time, this.getCaseID());
        Notes.add(note);
    }
    public Note getNote(int index){
        return Notes.get(index);
    }

    public int getNotesSize(){
        return Notes.size();
    }

    public void addMed(String name,
                             Date startDate,
                             int duration,
                             String usageNotes){
        Medication med = new Medication(name, startDate, duration, usageNotes, this.getCaseID());
        Medications.add(med);
    }
    public Medication getMed(int index){
        return Medications.get(index);
    }

    public int getMedSize() {
        return Medications.size();
    }


}
