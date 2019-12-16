package com.quad.ClientData;

import com.quad.DataAccess;

public class GP extends Person {
    String PagerNum;
    String UserName;
    int PasswordHash;
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

    public void saveGP(){
        String sqlStr = "";
        if (this.ID == 0) {
            sqlStr = "INSERT INTO public.gp (fullname, emailadd, medicalcentreid, pagernum, username, pswrd)" +
                    "values ('" + this.getName() + "', '" + this.getEmail() + "', '" + this.getMedC().getID() + "', '" + this.getPagerNum() + "', '" + this.getUserName() + "', '" + this.getPassword() + "');";
        }
        else {
            sqlStr = "";
        }
        DataAccess.save(sqlStr);
    }
}