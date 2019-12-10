package com.quad.ClientData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Patient extends Person {
    private int PhoneNum;
    private String Address;
    private String DateOfBirth;
    private ArrayList<CaseReport> CaseReports;
    public Patient(String NameIn,
                   String EmailIn,
                   MedCentre MedCIn,
                   int IDIn,
                   String PhoneNumIn,
                   String AddressIn,
                   String DateOBIn) {
        super(NameIn, EmailIn, MedCIn, IDIn);
        PhoneNum = Integer.parseInt(PhoneNumIn);
        Address = AddressIn;
        // date is converted into format that is more easily parsed into SQL Date type
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMMMM dd");
        try {
            date = sdf.parse(DateOBIn);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf.applyPattern("yyyy-MM-dd");
        DateOfBirth = sdf.format(date);
    }

    public int getPhoneNum() {
        return PhoneNum;
    }

    public void setPhoneNum(int phoneNum) {
        PhoneNum = phoneNum;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void addCase(String condition,
                        int gpID){
        CaseReport caseReport = new CaseReport(condition, gpID, this.ID);
        CaseReports.add(caseReport);
    }
    public CaseReport getCase(int index){
        return CaseReports.get(index);
    }

    public int getCaseRSize() {
        return CaseReports.size();
    }
}
