package com.quad.ClientData;

public abstract class Person {
    String Name;
    String Email;
    int ID;
    MedCentre MedC;
    public Person(String NameIn,
                  String EmailIn,
                  MedCentre MedCIn,
                  int IDIn){
        Name = NameIn;
        Email = EmailIn;
        if (MedCIn != null) {
            MedC = MedCIn;
        }
        ID = IDIn;
    }

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
}
