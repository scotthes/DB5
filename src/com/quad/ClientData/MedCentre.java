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
            try {
                DataAccess.saveMedC(this);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public MedCentre search(){
        try {
            ResultSet rs = DataAccess.searchMedC(this, 0);
            if (rs.next()){
                String name = rs.getString("mcname");
                String address = rs.getString("mcadd");
                int id = rs.getInt("id");
                MedCentre mc = new MedCentre(name, address, id);
                return mc;
            }
            else {
                MedCentre mc = new MedCentre("", "", 0);
                return mc;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MedCentre mc = new MedCentre("", "", 0);
        return mc;
    }
}
