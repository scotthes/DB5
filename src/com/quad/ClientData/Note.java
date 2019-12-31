package com.quad.ClientData;

import com.quad.DataAccess;

import java.sql.SQLException;
import java.time.LocalDateTime;

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
