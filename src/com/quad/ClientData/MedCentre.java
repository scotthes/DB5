package com.quad.ClientData;

import com.quad.DataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MedCentre {
    private String Name;
    private String Address;
    private int ID;
    public MedCentre(String NameIn,
                     String AddressIn,
                     int IDin){
        Name=NameIn;
        Address=AddressIn;
        ID=IDin;
    }
    public String getName(){
        return Name;
    }
    public String getAddress(){
        return Address;
    }
    public int getID(){
        return ID;
    }

    public void save(){
        if (this.ID == 0){
            DataAccess.saveMedC(this);
        }
    }

    public MedCentre search(){
        return DataAccess.searchMedC(this);
    }
}
