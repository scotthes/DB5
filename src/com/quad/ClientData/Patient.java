package com.quad.ClientData;

import com.quad.DataAccess;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;

public class Patient extends Person {
    private String PhoneNum;
    private String Address;
    private LocalDate DateOfBirth;
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
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy MMMM dd");
        DateTimeFormatter dtf = new DateTimeFormatterBuilder().parseCaseInsensitive().parseLenient().append(f).toFormatter();
        DateOfBirth = LocalDate.parse(DateOBIn, dtf);
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

    public LocalDate getDOBDate(){
        return DateOfBirth;
    }

    public String getDOBString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dtf.format(DateOfBirth);
    }

    public void addCase(String condition,
                        int gpID){
        CaseReport caseReport = new CaseReport(condition, gpID, this.getID());
        CaseReports.add(caseReport);
    }
    public CaseReport getCase(int index){
        return CaseReports.get(index);
    }

    public int getCaseRSize() {
        return CaseReports.size();
    }

    public void save(InputStream pic){
        if (this.getID() == 0){
            try {
                DataAccess.savePatient(this, pic);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                DataAccess.updatePatient(this, pic);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<Person> search(int pageNo){
        ResultSet rs = null;
        try {
            rs = DataAccess.searchPatient(this, pageNo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<Person> results = new ArrayList<>();
        try {
            while (rs.next()) {
                String nameIn = rs.getString("fullname");
                String emailIn = rs.getString("emailadd");
                int idIn = rs.getInt(1);
                String phoneIn = rs.getString("phonenumber");
                String addressIn = rs.getString("patientadd");
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy MMMMM dd");
                String DOBin = dtf.format(rs.getDate("dob").toLocalDate());
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
                return DataAccess.getPatientPic(this);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
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
