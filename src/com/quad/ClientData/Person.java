package com.quad.ClientData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public abstract class Person {
    private String Name;
    private String Email;
    private int ID;
    private MedCentre MedC;
    public Person(String NameIn,
                  String EmailIn,
                  MedCentre MedCIn,
                  int IDIn){
        Name = NameIn;
        Email = EmailIn;
        MedC = MedCIn;
        ID = IDIn;
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

    public abstract InputStream getPicture();

}
