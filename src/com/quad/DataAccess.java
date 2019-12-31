package com.quad;
import com.quad.ClientData.*;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.xml.transform.Result;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class DataAccess {
    public static Connection connect(){
        String url = "jdbc:postgresql://ec2-54-195-252-243.eu-west-1.compute.amazonaws.com:5432/dbelilspt7ge3m"; // change to web url when server set up
        String user = "fjmisibystymlz";
        String password = "ebbd5d6575060e77fbf9aeb335c66bf4441cac094218713a75585511a6f7aae5"; // change to whatever postgres password is
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /*===================================================================================================================================*/
    /*==============================                            PATIENT QUERIES                            ==============================*/
    /*===================================================================================================================================*/
    public static void savePatient(@NotNull Patient input) throws SQLException {
        Connection conn = DataAccess.connect();
        String query = "INSERT INTO patients "+
                "(fullname, patientadd, emailadd, medicalcentre, phonenumber, dob, picture)" +
                "values (?, ?, ?, ?, ?, ?, ?);";
        byte[] targetArray = new byte[0];
        try {
            targetArray = new byte[input.getPicture().available()];
            input.getPicture().read(targetArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        java.sql.Date dateOB = Date.valueOf(input.getDOBDate());
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1,input.getName());
        ps.setString(2,input.getAddress());
        ps.setString(3,input.getEmail());
        ps.setInt(4,input.getMedC().getID());
        ps.setString(5,input.getPhoneNum());
        ps.setDate(6, dateOB);
        ps.setBytes(7, targetArray);
        ps.executeUpdate();
    }

    public static void updatePatient(@NotNull Patient input) throws SQLException {
        Connection conn = DataAccess.connect();
        String query = "Update patients SET " +
                "fullname = ?,"+
                "patientadd = ?,"+
                "emailadd = ?,"+
                "medicalcentre = ?,"+
                "phonenumber = ?,"+
                "dob= ?, " +
                "picture = ? " +
                "WHERE id = ?;";
        byte[] targetArray = new byte[0];
        /*
        try {
            input.setPicture(new FileInputStream(new File("src/com/quad/ClientData/blank-profile-picture.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

         */
        try {
            targetArray = new byte[input.getPicture().available()];
            input.getPicture().read(targetArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            System.out.println( "Data Access "+ input.getPicture().available());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Date dateOB = Date.valueOf(input.getDOBDate());
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1,input.getName());
        ps.setString(2,input.getAddress());
        ps.setString(3,input.getEmail());
        ps.setInt(4,input.getMedC().getID());
        ps.setString(5,input.getPhoneNum());
        ps.setDate(6, dateOB);
        ps.setBytes(7, targetArray);
        //ps.setBinaryStream(7, input.getPicture());
        ps.setInt(8,input.getID());
        ps.executeUpdate();
    }

    public static ResultSet searchPatient(@NotNull Patient input, int pageNo) throws SQLException {
        Connection conn = DataAccess.connect();
        String query = "SELECT * from " +
                "(SELECT *, ROW_NUMBER() OVER (ORDER by 1) as RowNumber from patients " +
                "INNER JOIN medicalcentre " +
                "ON patients.medicalcentre = medicalcentre.id " +
                "WHERE (fullname LIKE ? OR ? IS NULL)" +
                "AND (patientadd LIKE ? OR ? IS NULL)" +
                "AND (emailadd LIKE ? OR ? IS NULL)" +
                "AND (phonenumber LIKE ? OR ? IS NULL)" +
                "AND (?::date IS NULL or dob = ?)) foo " +                 //this is not an error, regardless of what intellij says!
                "WHERE RowNumber > ? ORDER BY 1 LIMIT 10 ;";
        java.sql.Date dateOB;
        if (input.getDOBString().equals("1915-01-01")) {
            dateOB = null;
        }
        else {
            dateOB = Date.valueOf(input.getDOBDate());
        }
        //why are there two  different types of date? ahhhhhhhhhh
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1,"%"+input.getName()+"%");
        ps.setString(2,input.getName());
        ps.setString(3,"%"+input.getAddress()+"%");
        ps.setString(4,input.getAddress());
        ps.setString(5,"%"+input.getEmail()+"%");
        ps.setString(6,input.getEmail());
        ps.setString(7,"%"+input.getPhoneNum()+"%");
        ps.setString(8,input.getPhoneNum());
        ps.setDate(9, dateOB);
        ps.setDate(10, dateOB);
        ps.setInt(11,10*pageNo);

        return ps.executeQuery();
    }

    public static ResultSet searchPatientCount(@NotNull Patient input) throws SQLException {
        Connection conn = DataAccess.connect();
        String query = "SELECT COUNT(id) from patients " +
                "WHERE (fullname LIKE ? OR ? IS NULL)" +
                "AND (patientadd LIKE ? OR ? IS NULL)" +
                "AND (emailadd LIKE ? OR ? IS NULL)" +
                "AND (phonenumber LIKE ? OR ? IS NULL)"+
                "AND (?::date IS NULL or dob = ?)";
        java.sql.Date dateOB;
        if (input.getDOBString().equals("1915-01-01")) {
            dateOB = null;
        }
        else {
            dateOB = Date.valueOf(input.getDOBDate());
        }
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1,"%"+input.getName()+"%");
        ps.setString(2,input.getName());
        ps.setString(3,"%"+input.getAddress()+"%");
        ps.setString(4,input.getAddress());
        ps.setString(5,"%"+input.getEmail()+"%");
        ps.setString(6,input.getEmail());
        ps.setString(7,"%"+input.getPhoneNum()+"%");
        ps.setString(8,input.getPhoneNum());
        ps.setDate(9, dateOB);
        ps.setDate(10, dateOB);

        return ps.executeQuery();
    }

    public static InputStream refreshPatientPic(Patient input) throws SQLException {
        Connection conn = DataAccess.connect();
        String query = "Select picture from patients where id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, input.getID());
        ResultSet rs = ps.executeQuery();
        boolean k = rs.next();
        return new ByteArrayInputStream(rs.getBytes("picture"));
    }
    /*===================================================================================================================================*/
    /*==============================                              GP QUERIES                               ==============================*/
    /*===================================================================================================================================*/
    public static void saveGP(@NotNull GP input) throws SQLException {
        Connection conn = DataAccess.connect();
        String query = "INSERT INTO gp " +
                "(fullname, pagernum, emailadd, medicalcentreid, username, pswrd, picture) " +
                "VALUES (?,?,?,?,?,?,?);";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1,input.getName());
        ps.setString(2,input.getPagerNum());
        ps.setString(3,input.getEmail());
        ps.setInt(4,input.getMedC().getID());
        ps.setString(5,input.getUserName());
        ps.setString(6,input.getPassword());
        ps.setBinaryStream(7, input.getPicture());
        ps.executeUpdate();
    }

    public static void updateGP(@NotNull GP input) throws SQLException {
        Connection conn = DataAccess.connect();
        String query = "Update gp SET " +
                "fullname = ?,"+
                "emailadd = ?,"+
                "pagernum = ?,"+
                "medicalcentreid = ? " +
                "WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1,input.getName());
        ps.setString(2,input.getEmail());
        ps.setString(3,input.getPagerNum());
        ps.setInt(4,input.getMedC().getID());
        ps.setInt(5,input.getID());
        ps.executeUpdate();
    }

    public static ResultSet searchGP(@NotNull GP input, int pageNo) throws SQLException {
        Connection conn = DataAccess.connect();
        String query =  "SELECT id, fullname, pagernum, username, emailadd, medicalcentre, mcname, mcadd, picture from " +
                "(SELECT *, ROW_NUMBER() OVER (ORDER by 1) as RowNumber from gp " +
                "INNER JOIN medicalcentre " +
                "ON gp.medicalcentreid = medicalcentre.id " +
                "WHERE (fullname LIKE ? OR ? IS NULL)" +
                "AND (pagernum LIKE ? OR ? IS NULL)" +
                "AND (emailadd LIKE ? OR ? IS NULL)" +
                "AND (username LIKE ? OR ? IS NULL)) foo " +
                "WHERE RowNumber > ? ORDER BY 1 LIMIT 5 ;";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1,"%"+input.getName()+"%");
        ps.setString(2,input.getName());
        ps.setString(3,"%"+input.getPagerNum()+"%");
        ps.setString(4,input.getPagerNum());
        ps.setString(5,"%"+input.getEmail()+"%");
        ps.setString(6,input.getEmail());
        ps.setString(7,"%"+input.getUserName()+"%");
        ps.setString(8,input.getUserName());
        ps.setInt(9,10*pageNo);
        return ps.executeQuery();
    }

    public static boolean GPLogin(String Username, @NotNull String Password) throws SQLException {
        Connection conn = DataAccess.connect();
        String query = "SELECT gp.id, fullname, pagernum, username, emailadd, medicalcentreid, mcname, mcadd, picture from gp " +
                "INNER JOIN medicalcentre " +
                "ON gp.medicalcentreid = medicalcentre.id " +
                "WHERE username = ? AND pswrd = ?;";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, Username);
        ps.setString(2, String.valueOf(Password.hashCode()));
        ResultSet rs = ps.executeQuery();
        boolean validLogin = rs.next();
        if (validLogin){
            int idIn = rs.getInt(1);
            String nameIn = rs.getString(2);
            String pagNumIn = rs.getString(3);
            String usernameIn = rs.getString(4);
            String emailIn = rs.getString(5);
            int mcid = rs.getInt(6);
            String mcName = rs.getString(7);
            String mcAdd = rs.getString(8);
            MedCentre MedCIn = new MedCentre(mcName, mcAdd, mcid);
            InputStream picIn = new ByteArrayInputStream(rs.getBytes(9));
            GP gpActive = new GP(nameIn, emailIn, MedCIn, idIn, picIn, pagNumIn, usernameIn, "n/a");
            Global.ActiveGP = gpActive;
        }
        return validLogin;
    }

    public static InputStream refreshGPPic(GP input) throws SQLException {
        Connection conn = DataAccess.connect();
        String query = "Select picture from gp where id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, input.getID());
        ResultSet rs = ps.executeQuery();
        boolean k = rs.next();
        return new ByteArrayInputStream(rs.getBytes("picture"));
    }
    /*===================================================================================================================================*/
    /*==============================                        MEDICAL CENTRE QUERIES                         ==============================*/
    /*===================================================================================================================================*/
    public static void saveMedC(@NotNull MedCentre input) throws SQLException {
        Connection conn = DataAccess.connect();
        String query = "INSERT INTO medicalcentre " +
                "(mcname, mcadd) " +
                "VALUES (?,?);";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1,input.getName());
        ps.setString(2,input.getAddress());
        ps.executeUpdate();
        DataAccess.loadMedCs();
    }
    public static ResultSet searchMedC(@NotNull MedCentre input, int pageNo) throws SQLException {
        Connection conn = DataAccess.connect();
        String query =  "SELECT * from " +
                "(SELECT *, ROW_NUMBER() OVER (ORDER by 1) as RowNumber from medicalcentre " +
                "WHERE (mcname LIKE ?)) foo " +
                "WHERE RowNumber > ? ORDER BY 1 LIMIT 10 ;";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1,input.getName());
        ps.setInt(2,10*pageNo);
        return ps.executeQuery();
    }
    public static void loadMedCs() throws SQLException {
        Connection conn = DataAccess.connect();
        String query = "SELECT * FROM medicalcentre";
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        ArrayList<MedCentre> results = new ArrayList<>();
        while(rs.next()){
            int idIn = rs.getInt(1);
            String nameIn = rs.getString(2);
            String addIn = rs.getString(3);
            MedCentre mc = new MedCentre(nameIn, addIn, idIn);
            results.add(mc);
        }
        Global.MCList=results;
    }
    /*===================================================================================================================================*/
    /*==============================                          CASE REPORT QUERIES                          ==============================*/
    /*===================================================================================================================================*/
    public static int saveCaseReport(@NotNull CaseReport input) throws SQLException {
        Connection conn = DataAccess.connect();
        String query = "INSERT INTO casereports " +
                "(patientid, gpid, condition, chronic) " +
                "VALUES (?,?,?,?) " +
                "RETURNING id;"; //Also not an error
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1,input.getPatientID());
        ps.setInt(2,input.getGPID());
        ps.setString(3,input.getCondition());
        ps.setBoolean(4,input.getChronic());
        ResultSet rs = ps.executeQuery();
        boolean k = rs.next();
        return rs.getInt(1);
    }

    public static ResultSet searchCaseReport(int patientID, int GPID, int pageNo) throws SQLException {
        Connection conn = DataAccess.connect();
        String query =  "SELECT patientid, condition, chronic from " +
                "(SELECT *, ROW_NUMBER() OVER (ORDER by 1) as RowNumber from casereports " +
                "WHERE (patientid = ?)" +
                "OR (gpid = ?)) moo " +
                "WHERE RowNumber > ? ORDER BY 1 LIMIT 10 ;";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1,patientID);
        ps.setInt(2,GPID);
        ps.setInt(3,10*pageNo);
        return ps.executeQuery();
    }
    /*===================================================================================================================================*/
    /*==============================                             NOTE QUERIES                              ==============================*/
    /*===================================================================================================================================*/
    public static void saveNote(@NotNull Note input) throws SQLException {
        Connection conn = DataAccess.connect();
        String query = "INSERT INTO notes" +
                "(notedate, note, casereportid)" +
                "VALUES (?,?,?);";
        PreparedStatement ps = conn.prepareStatement(query);
        Date date = Date.valueOf(input.getDate().toLocalDate());
        ps.setDate(1,date);
        ps.setString(2,input.getText());
        ps.setInt(3,input.getCaseID());
        ps.executeUpdate();
    }
    public static ResultSet searchNoteAll(int caseID, int pageNo) throws SQLException {
        Connection conn = DataAccess.connect();
        String query =  "SELECT notedate, note from " +
                "(SELECT *, ROW_NUMBER() OVER (ORDER by 1) as RowNumber from notes " +
                "WHERE (casereportid = ?)) roo " +
                "WHERE RowNumber > ? ORDER BY notedate LIMIT 10 ;";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, caseID);
        ps.setInt(2,10*pageNo);
        return ps.executeQuery();
    }
    public static ResultSet searchNote(int caseID) throws SQLException {
        Connection conn = DataAccess.connect();
        String query =  "SELECT notedate, note from notes " +
                "WHERE (casereportid = ?)" +
                "ORDER BY notedate DESC LIMIT 5 ;";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, caseID);
        return ps.executeQuery();
    }

    /*===================================================================================================================================*/
    /*==============================                          MEDICATION QUERIES                           ==============================*/
    /*===================================================================================================================================*/
    public static void saveMedication(@NotNull Medication input) throws SQLException {
        Connection conn = DataAccess.connect();
        String query = "INSERT INTO medication" +
                "(medname, startdate, duration, usagenotes, casereportid)" +
                "VALUES (?,?,?,?,?);";
        PreparedStatement ps = conn.prepareStatement(query);
        Date startDate = Date.valueOf(input.getStartDate().toLocalDate());
        ps.setString(1,input.getName());
        ps.setDate(2,startDate);
        ps.setInt(3,input.getDuration());
        ps.setString(4,input.getUsageNotes());
        ps.setInt(5,input.getCaseID());
        ps.executeUpdate();
    }
    public static ResultSet searchMedAll(int caseID, int pageNo) throws SQLException {
        Connection conn = DataAccess.connect();
        String query =  "SELECT medname, startdate, duration, usuagenotes from medication" +
                "(SELECT *, ROW_NUMBER() OVER (ORDER by 1) as RowNumber from medication " +
                "WHERE (casereportid = ?)) eoo " +
                "WHERE RowNumber > ? ORDER BY startdate DESC LIMIT 10;";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, caseID);
        ps.setInt(2,10*pageNo);
        return ps.executeQuery();
    }
    public static ResultSet searchMed(int caseID) throws SQLException {
        Connection conn = DataAccess.connect();
        String query =  "SELECT name, startdate, duration, usuagenotes FROM medication " +
                "WHERE (casereportid = ?) " +
                "ORDER BY startdate DESC LIMIT 5 ;";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, caseID);
        return ps.executeQuery();
    }

}