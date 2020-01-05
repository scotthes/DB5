package com.quad.ClientData;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Person {
    private String Name;
    private String Email;
    private int ID;
    private MedCentre MedC;
    LocalDateTime TStamp;
    public Person(String NameIn,
                  String EmailIn,
                  MedCentre MedCIn,
                  int IDIn){
        Name = NameIn;
        Email = EmailIn;
        MedC = MedCIn;
        ID = IDIn;
        TStamp = null;
    }
    public abstract int searchCount();

    public abstract ArrayList<Person> search(int pageNo);

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public MedCentre getMedC() {
        return MedC;
    }

    public void setMedC(MedCentre medC) {
        MedC = medC;
    }

    public int getID() {
        return ID;
    }

    public void setTStamp(LocalDateTime tIn) {
        TStamp = tIn;
    }

    public LocalDateTime getTStamp() {
        return TStamp;
    }

    public abstract InputStream getPicture();

}
