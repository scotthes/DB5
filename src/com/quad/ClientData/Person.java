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
        MedC = MedCIn;
        // if an id exists already (aka importing person from database), then
        // sets the id to the imported one, otherwise if no id exists (aka
        // adding a new person) then creates a new one
        /*
        if (IDIn == 0){
            ID = EmailIn.hashCode();
            // uses email to generate unique ID as email addresses are unique
        }
        else {
            ID = IDIn;
        }*/
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

    public long getID() {
        return ID;
    }
}
