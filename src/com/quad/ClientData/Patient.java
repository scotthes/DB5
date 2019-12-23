package com.quad.ClientData;

import com.quad.DataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    public Date getDOBDate(){
        return DateOfBirth;
    }

    public String getDOBString() {
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

    public void save(){
        if (this.ID == 0){
            try {
                DataAccess.savePatient(this);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                DataAccess.updatePatient(this);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Patient> searchPatient(int pageNo){
        ResultSet rs = null;
        try {
            rs = DataAccess.searchPatient(this, pageNo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<Patient> results = new ArrayList<>();
        try {
            while (rs.next()) {
                String nameIn = rs.getString("fullname");
                String emailIn = rs.getString("emailadd");
                int idIn = rs.getInt(1);
                String phoneIn = rs.getString("phonenumber");
                String addressIn = rs.getString("patientadd");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMMMM dd");
                String DOBin = sdf.format(rs.getDate("dob"));
                int mcid = rs.getInt(8);
                String mcName = rs.getString("mcname");
                String mcAdd = rs.getString("mcadd");
                MedCentre MedCIn = new MedCentre(mcName, mcAdd, mcid);
                Patient pRes = new Patient(nameIn, emailIn, MedCIn, idIn, phoneIn, addressIn, DOBin);
                results.add(pRes);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
    public int searchCount() {
        int count = 0;
        try {
            ResultSet rs = DataAccess.searchPatientCount(this);
            Boolean k = rs.next();
            count = rs.getInt("count");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

}
