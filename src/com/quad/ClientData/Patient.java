package com.quad.ClientData;

import com.quad.DataAccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Patient extends Person {
    private String PhoneNum;
    private String Address;
    private LocalDate DateOfBirth;
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
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy MMMM d");
        DateOfBirth = LocalDate.parse(DateOBIn, dtf);
    }

    public String getPhoneNum() {
        return PhoneNum;
    }

    public String getAddress() {
        return Address;
    }

    public LocalDate getDOBDate(){
        return DateOfBirth;
    }

    public String getDOBString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dtf.format(DateOfBirth);
    }

    public ArrayList<CaseReport> loadCaseReports(){
        return DataAccess.searchCaseReport(this.getID(), 0);
    }

    public void save(InputStream pic){
        if (this.getID() == 0) {
            DataAccess.savePatient(this, pic);
        }
        else {
            DataAccess.updatePatient(this, pic);
        }
    }

    @Override
    public ArrayList<Person> search(int pageNo){
        return DataAccess.searchPatient(this, pageNo);
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
                return DataAccess.getPatientPic(this); //pictures retrieved from DB only when needed to speed up program on slow internets
        }
        return null;
    }

    public Patient getPrev() {
       return DataAccess.getPrevPatient(this);
    }

    @Override
    public int searchCount() {
        return DataAccess.searchPatientCount(this);
    }

}
