package com.quad.ClientData;

import com.quad.DataAccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class GP extends Person {
    private String PagerNum;
    private String UserName;
    private int PasswordHash;
    public GP(String NameIn,
              String EmailIn,
              MedCentre MedCIn,
              int IDIn,
              String PagerNumIn,
              String UsernameIn,
              String PasswordIn) {
        super(NameIn, EmailIn, MedCIn, IDIn);
        PagerNum = (PagerNumIn);
        UserName = UsernameIn;
        // does not store password in plaintext for security reasons
        // stores hash instead
        PasswordHash = PasswordIn.hashCode();
    }

    public String getPagerNum() {
        return PagerNum;
    }

    public String getUserName() {
        return UserName;
    }

    public String getPassword(){
        String str = String.valueOf(PasswordHash);
        return str;
    }

    public void save(InputStream pic){
        if (this.getID() == 0){
            DataAccess.saveGP(this, pic);
        }
        else{
            DataAccess.updateGP(this, pic);
        }
    }

    @Override
    public int searchCount() {
        return DataAccess.searchGPCount(this);
    }

    @Override
    public ArrayList<Person> search(int pageNo) {
        return DataAccess.searchGP(this, pageNo);
    }


    @Override
    public InputStream getPicture() {
        if (this.getID()==0){
            try {
                return new FileInputStream(new File("src/com/quad/ClientData/blank-profile-picture.png"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            return DataAccess.getGPPic(this);
        }
        return null;
    }
    public ArrayList<CaseReport> loadCaseReports(){
        return DataAccess.searchCaseReport(0, this.getID());
    }

    public GP getPrev(){
        return DataAccess.getPrevGP(this);
    }
}