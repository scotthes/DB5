package com.quad.ClientData;

import com.quad.DataAccess;

public class MedCentre {
    private String Name;
    private String Address;
    private int ID;
    public MedCentre(String NameIn,
                     String AddressIn){
        Name=NameIn;
        Address=AddressIn;
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

    public void saveMC(){
        String sqlStr = "";
        if (this.ID == 0) {
            sqlStr = "INSERT INTO public.medicalcentre (mcname, mcadd)"+
                    "values ('"+this.getName()+"', '"+this.getAddress()+"');";
        }
        else {
            sqlStr = "";
        }
        DataAccess.save(sqlStr);
    }
}
