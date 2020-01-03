package com.quad.ClientData;

import com.quad.DataAccess;

import java.sql.SQLException;
import java.time.LocalDate;

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

    public void save() {
        try {
            DataAccess.saveMedication(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
