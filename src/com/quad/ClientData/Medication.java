package com.quad.ClientData;

import com.quad.DataAccess;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Medication {
    private String Name;
    private LocalDate StartDate;
    private int Duration;
    private String UsageNotes;
    private int CaseID;
    public Medication(String name,
                      LocalDate startDate,
                      int duration,
                      String usageNotes,
                      int caseID){
        Name = name;
        StartDate = startDate;
        Duration = duration;
        UsageNotes = usageNotes;
        CaseID = caseID;
    }

    public String getName() {
        return Name;
    }

    public LocalDate getStartDate(){
        return StartDate;
    }

    public int getDuration() {
        return Duration;
    }

    public String getUsageNotes() {
        return UsageNotes;
    }

    public int getCaseID(){
        return CaseID;
    }

    public boolean equals(Medication test){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy MMMM d");
        return (dtf.format(this.getStartDate()).equals(dtf.format(test.getStartDate()))) &&
                (this.getDuration() == test.getDuration()) &&
                (this.getName().equals(test.getName())) &&
                (this.getUsageNotes().equals(test.getUsageNotes()));
    }

    public void save() {
        DataAccess.saveMedication(this);
    }
}
