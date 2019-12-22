package com.quad.ClientData;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Medication {
    private String Name;
    private Date StartDate;
    private int Duration;
    private String UsageNotes;
    private int CaseID;
    public Medication(String name,
                      Date startDate,
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

    public Date getStartDate(){
        return StartDate;
    }

    public String getStartDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(StartDate);
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
}
