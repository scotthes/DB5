package com.quad.ClientData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public abstract class Person {
    String Name;
    String Email;
    int ID;
    MedCentre MedC;
    InputStream Picture;
    public Person(String NameIn,
                  String EmailIn,
                  MedCentre MedCIn,
                  int IDIn,
                  InputStream PicIn){
        Name = NameIn;
        Email = EmailIn;
        MedC = MedCIn;
        ID = IDIn;
        try {
            if (PicIn.available() == 0){
                Picture = new FileInputStream(new File("src/com/quad/ClientData/blank-profile-picture.png"));
            }
            else{
                Picture = PicIn;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public abstract int searchCount();

    public abstract ArrayList<Person> search(int pageNo);

    public abstract void refreshPic();

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

    public InputStream getPicture(){
        return Picture;
    }

    public void setPicture(InputStream newPic){
        Picture = newPic;
    }

}
