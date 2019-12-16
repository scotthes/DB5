package com.quad.ClientData;

import com.quad.DataAccess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Patient extends Person {
    private String PhoneNum;
    private String Address;
    private Date DateOfBirth;
    private ArrayList<CaseReport> CaseReports;
    public Patient(String NameIn,
                   String EmailIn,
                   MedCentre MedCIn,
                   int IDIn,
                   String PhoneNumIn,
                   String AddressIn,
                   String DateOBIn) {
        super(NameIn, EmailIn, MedCIn, IDIn);
        PhoneNum = PhoneNumIn;
        Address = AddressIn;
        // date is converted into format that is more easily parsed into SQL Date type
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMMMM dd");
        try {
            date = sdf.parse(DateOBIn);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateOfBirth = date;
    }

    public String getPhoneNum() {
        return PhoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        PhoneNum = phoneNum;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDateOfBirth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(DateOfBirth);
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

    public void savePatient(){
        String sqlStr = "";
        if (this.ID == 0){
            sqlStr = "INSERT INTO public.patients (fullname, emailadd, medicalcentre, phonenumber, patientadd, dob) " +
                    "values ('"+ this.getName() +"', '"+ this.getEmail() +"', '"+ this.getMedC().getID() +"','"+ this.getPhoneNum() +"','"+ this.getAddress() +"','"+ this.getDateOfBirth() +"');";
        }
        else {
            sqlStr = "";
        }
        DataAccess.save(sqlStr);
    }
}
