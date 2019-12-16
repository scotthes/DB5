package com.quad.ClientData;

public class Note {
    private String Date;
    private String Time;
    private String Text;
    public Note(String date,
                String time,
                String text){
        Date = date;
        Time = time;
        Text = text;
    }

    public String getDate() {
        return Date;
    }
    public String getTime() {
        return Time;
    }
    public String getText() {
        return Text;
    }

}
