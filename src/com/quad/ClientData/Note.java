package com.quad.ClientData;

import com.quad.DataAccess;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Note {
    private LocalDateTime Date;
    private String Text;
    private int CaseID;
    public Note(LocalDateTime date,
                String text,
                int caseID){
        Date = date;
        Text = text;
        CaseID = caseID;
    }

    public LocalDateTime getDate(){
        return Date;
    }

    public String getDateString(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-d | HH:mm:ss");
        return dtf.format(Date);
    }

    public void setText(String newText) {
        Text = newText;
    }

    public int getCaseID() {
        return CaseID;
    }
    public String getText() {
        return Text;
    }
    public void save(){
        DataAccess.saveNote(this);
    }
}
