package com.quad.ClientData;

import com.quad.DataAccess;

import java.sql.SQLException;
import java.time.LocalDate;

public class Note {
    private LocalDate Date;
    private String Text;
    private int CaseID;
    public Note(LocalDate date,
                String text,
                int caseID){
        Date = date;
        Text = text;
        CaseID = caseID;
    }

    public LocalDate getDate(){
        return Date;
    }


    public int getCaseID() {
        return CaseID;
    }
    public String getText() {
        return Text;
    }
    public void save(){
        try {
            DataAccess.saveNote(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
