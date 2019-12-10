package com.quad.ClientData;

public class MedCentre {
    private String Name;
    private String Address;
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
}
