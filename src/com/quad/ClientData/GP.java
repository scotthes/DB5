package com.quad.ClientData;

import com.quad.DataAccess;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;

public class GP extends Person {
    String PagerNum;
    String UserName;
    int PasswordHash;
    public GP(String NameIn,
              String EmailIn,
              MedCentre MedCIn,
              int IDIn,
              InputStream PicIn,
              String PagerNumIn,
              String UsernameIn,
              String PasswordIn) {
        super(NameIn, EmailIn, MedCIn, IDIn, PicIn);
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

    public void save(){
        if (this.ID == 0){
            try {
                DataAccess.saveGP(this);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int searchCount() {
        return 0;
    }

    @Override
    public ArrayList<Person> search(int pageNo) {
        return null;
    }

    @Override
    public void refreshPic() {
        try {
            Picture = DataAccess.refreshGPPic(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}