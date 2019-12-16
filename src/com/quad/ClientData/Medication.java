package com.quad.ClientData;

public class Medication {
    private String Name;
    private String StartDate;
    private String Duration;
    private String UsageNotes;
    public Medication(String name,
                      String startDate,
                      String duration,
                      String usageNotes){
        Name = name;
        StartDate = startDate;
        Duration = duration;
        UsageNotes = usageNotes;
    }

    public String getName() {
        return Name;
    }

    public String getStartDate() {
        return StartDate;
    }

    public String getDuration() {
        return Duration;
    }

    public String getUsageNotes() {
        return UsageNotes;
    }
}
