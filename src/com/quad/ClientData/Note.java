package com.quad.ClientData;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Note {
    private java.util.Date Date;
    private String Text;
    private int CaseID;
    public Note(Date date,
                String text,
                int caseID){
        Date = date;
        Text = text;
        CaseID = caseID;
    }

    public Date getDate(){
        return Date;
    }

    public String getDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(Date);
    }
    public int getCaseID() {
        return CaseID;
    }
    public String getText() {
        return Text;
    }

}
