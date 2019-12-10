package com.quad.ClientData;

public class GP extends Person {
    int PagerNum;
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
        PagerNum = Integer.parseInt(PagerNumIn);
        UserName = UsernameIn;
        // does not store password in plaintext for security reasons
        // stores hash instead
        PasswordHash = PasswordIn.hashCode();
    }

    public int getPagerNum() {
        return PagerNum;
    }

    public void setPagerNum(int pagerNum) {
        PagerNum = pagerNum;
    }

    public String getUserName() {
        return UserName;
    }
}
