package com.quad.ClientData;

import java.util.ArrayList;

public class CaseReport {
    private String Condition;
    private ArrayList<Note> Notes;
    private ArrayList<Medication> Medications;
    private int GPID;
    private int PatientID;
    private boolean IsChronic;

    public CaseReport(String condition,
                      int gpID,
                      int patientID){
        Condition = condition;
        GPID = gpID;
        PatientID = patientID;
        IsChronic = false; // defaults to not chronic, can be changed with button in UI
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

    public void addNote(String date,
                        String time,
                        String text){
        Note note = new Note(date, time, text);
        Notes.add(note);
    }
    public Note getNote(int index){
        return Notes.get(index);
    }

    public int getNotesSize(){
        return Notes.size();
    }

    public void addMed(String name,
                             String startDate,
                             String duration,
                             String usageNotes){
        Medication med = new Medication(name, startDate, duration, usageNotes);
        Medications.add(med);
    }
    public Medication getMed(int index){
        return Medications.get(index);
    }

    public int getMedSize() {
        return Medications.size();
    }


}
