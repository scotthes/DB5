package com.quad.ClientData;

import com.quad.DataAccess;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
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

    public void setPagerNum(String pagerNum) {
        PagerNum = pagerNum;
    }

    public String getUserName() {
        return UserName;
    }

    public String getPassword(){
        String str = String.valueOf(PasswordHash);
        return str;
    }

    public void save(InputStream pic){
        try {
            if (this.getID() == 0){
            DataAccess.saveGP(this, pic);
            }
            else{
                DataAccess.updateGP(this, pic);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int searchCount() {
        int count = 0;
        try {
            ResultSet rs = DataAccess.searchGPCount(this);
            Boolean k = rs.next();
            count = rs.getInt("count");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public ArrayList<Person> search(int pageNo) {
        ResultSet rs = null;
        try {
            rs = DataAccess.searchGP(this, pageNo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<Person> results = new ArrayList<>();
        try {
            while (rs.next()) {
                String nameIn = rs.getString("fullname");
                String emailIn = rs.getString("emailadd");
                int idIn = rs.getInt(1);
                String pagerIn = rs.getString("pagernum");
                String usernameIn = rs.getString("username");
                int mcid = rs.getInt(6);
                String mcName = rs.getString("mcname");
                String mcAdd = rs.getString("mcadd");
                MedCentre MedCIn = new MedCentre(mcName, mcAdd, mcid);
                GP gpRes = new GP(nameIn, emailIn, MedCIn, idIn, pagerIn, usernameIn, "");
                results.add(gpRes);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
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
            try {
                return DataAccess.getGPPic(this);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}