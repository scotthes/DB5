package com.quad;

import com.quad.ClientData.*;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DataAccess {
    public static Connection connect(){
        String url = "jdbc:postgresql://ec2-54-195-252-243.eu-west-1.compute.amazonaws.com:5432/dbelilspt7ge3m";
        String user = "fjmisibystymlz";
        String password = "ebbd5d6575060e77fbf9aeb335c66bf4441cac094218713a75585511a6f7aae5";
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
    public static void savePatient(@NotNull Patient input, InputStream pic){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DataAccess.connect();
            String query = "INSERT INTO patients " +
                    "(fullname, patientadd, emailadd, medicalcentre, phonenumber, dob, picture)" +
                    "values (?, ?, ?, ?, ?, ?, ?);";
            byte[] targetArray = new byte[0];
            try {
                targetArray = new byte[pic.available()];
                pic.read(targetArray);
            } catch (IOException e) {
                e.printStackTrace();
            }
            java.sql.Date dateOB = Date.valueOf(input.getDOBDate());
            ps = conn.prepareStatement(query);
            ps.setString(1, input.getName());
            ps.setString(2, input.getAddress());
            ps.setString(3, input.getEmail());
            ps.setInt(4, input.getMedC().getID());
            ps.setString(5, input.getPhoneNum());
            ps.setDate(6, dateOB);
            ps.setBytes(7, targetArray);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public static void updatePatient(@NotNull Patient input, InputStream pic) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DataAccess.connect();
            String query = "Update patients SET " +
                    "fullname = ?," +
                    "patientadd = ?," +
                    "emailadd = ?," +
                    "medicalcentre = ?," +
                    "phonenumber = ?," +
                    "dob= ?, " +
                    "picture = COALESCE(?, picture) " +
                    "WHERE id = ?;";
            byte[] targetArray = new byte[0];
            try {
                if (pic.available() == 0) {
                    targetArray = null;
                } else {
                    targetArray = new byte[pic.available()];
                    pic.read(targetArray);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Date dateOB = Date.valueOf(input.getDOBDate());
            ps = conn.prepareStatement(query);
            ps.setString(1, input.getName());
            ps.setString(2, input.getAddress());
            ps.setString(3, input.getEmail());
            ps.setInt(4, input.getMedC().getID());
            ps.setString(5, input.getPhoneNum());
            ps.setDate(6, dateOB);
            ps.setBytes(7, targetArray);
            ps.setInt(8, input.getID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public static ArrayList<Person> searchPatient(@NotNull Patient input, int pageNo) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Person> results = new ArrayList<>();
        try {
            conn = DataAccess.connect();
            String query = "SELECT * from " +
                    "(SELECT patients.id, fullname, phonenumber, emailadd, medicalcentre, patientadd, dob, mcname, mcadd, ROW_NUMBER() OVER (ORDER by patients.id) as RowNumber from patients " +
                    "INNER JOIN medicalcentre " +
                    "ON patients.medicalcentre = medicalcentre.id " +
                    "WHERE (fullname LIKE ? OR ? IS NULL)" +
                    "AND (patientadd LIKE ? OR ? IS NULL)" +
                    "AND (emailadd LIKE ? OR ? IS NULL)" +
                    "AND (phonenumber LIKE ? OR ? IS NULL)" +
                    "AND (?::date IS NULL or dob = ?)) foo " +                 //this is not an error, regardless of what intellij says!
                    "WHERE RowNumber > ? ORDER BY id LIMIT 10 ;";
            java.sql.Date dateOB;
            if (input.getDOBString().equals("1915-01-01")) {
                dateOB = null;
            } else {
                dateOB = Date.valueOf(input.getDOBDate());
            }
            ps = conn.prepareStatement(query);
            ps.setString(1, "%" + input.getName() + "%");
            ps.setString(2, input.getName());
            ps.setString(3, "%" + input.getAddress() + "%");
            ps.setString(4, input.getAddress());
            ps.setString(5, "%" + input.getEmail() + "%");
            ps.setString(6, input.getEmail());
            ps.setString(7, "%" + input.getPhoneNum() + "%");
            ps.setString(8, input.getPhoneNum());
            ps.setDate(9, dateOB);
            ps.setDate(10, dateOB);
            ps.setInt(11, 10 * pageNo); //only retrieves one page of results at a time
            rs = ps.executeQuery();

            while (rs.next()) {
                String nameIn = rs.getString("fullname");
                String emailIn = rs.getString("emailadd");
                int idIn = rs.getInt("id");
                String phoneIn = rs.getString("phonenumber");
                String addressIn = rs.getString("patientadd");
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy MMMMM dd");
                String DOBin = dtf.format(rs.getDate("dob").toLocalDate());
                int mcid = rs.getInt("medicalcentre");
                String mcName = rs.getString("mcname");
                String mcAdd = rs.getString("mcadd");
                MedCentre MedCIn = new MedCentre(mcName, mcAdd, mcid);
                Patient pRes = new Patient(nameIn, emailIn, MedCIn, idIn, phoneIn, addressIn, DOBin);
                results.add(pRes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
        return results;
    }

    public static int searchPatientCount(@NotNull Patient input) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        try {
            conn = DataAccess.connect();
            String query = "SELECT COUNT(id) from patients " +
                    "WHERE (fullname LIKE ? OR ? IS NULL)" +
                    "AND (patientadd LIKE ? OR ? IS NULL)" +
                    "AND (emailadd LIKE ? OR ? IS NULL)" +
                    "AND (phonenumber LIKE ? OR ? IS NULL)" +
                    "AND (?::date IS NULL or dob = ?)";
            java.sql.Date dateOB;
            if (input.getDOBString().equals("1915-01-01")) {
                dateOB = null;
            } else {
                dateOB = Date.valueOf(input.getDOBDate());
            }
            ps = conn.prepareStatement(query);
            ps.setString(1, "%" + input.getName() + "%");
            ps.setString(2, input.getName());
            ps.setString(3, "%" + input.getAddress() + "%");
            ps.setString(4, input.getAddress());
            ps.setString(5, "%" + input.getEmail() + "%");
            ps.setString(6, input.getEmail());
            ps.setString(7, "%" + input.getPhoneNum() + "%");
            ps.setString(8, input.getPhoneNum());
            ps.setDate(9, dateOB);
            ps.setDate(10, dateOB);
            rs = ps.executeQuery();
            Boolean k = rs.next();
            count = rs.getInt("count");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }

        return count;
    }

    public static InputStream getPatientPic(Patient input) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        InputStream pic = InputStream.nullInputStream();
        try {
            conn = DataAccess.connect();
            String query = "Select picture from patients where id = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, input.getID());
            rs = ps.executeQuery();
            boolean k = rs.next();
            pic = new ByteArrayInputStream(rs.getBytes("picture"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
        return pic;
    }

    public static String getPatientName(int PatID){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String name = "";
        try{
            conn = DataAccess.connect();
            String query = "Select fullname from patients where id = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, PatID);
            rs = ps.executeQuery();
            boolean k = rs.next();
            name = rs.getString("fullname");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
        return name;

    }
    /*===================================================================================================================================*/
    /*==============================                              GP QUERIES                               ==============================*/
    /*===================================================================================================================================*/
    public static void saveGP(@NotNull GP input, InputStream pic) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DataAccess.connect();
            String query = "INSERT INTO gp " +
                    "(fullname, pagernum, emailadd, medicalcentreid, username, pswrd, picture) " +
                    "VALUES (?,?,?,?,?,?,?);";
            byte[] targetArray = new byte[0];
            try {
                targetArray = new byte[pic.available()];
                pic.read(targetArray);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ps = conn.prepareStatement(query);
            ps.setString(1,input.getName());
            ps.setString(2,input.getPagerNum());
            ps.setString(3,input.getEmail());
            ps.setInt(4,input.getMedC().getID());
            ps.setString(5,input.getUserName());
            ps.setString(6,input.getPassword());
            ps.setBytes(7, targetArray);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public static void updateGP(@NotNull GP input, InputStream pic) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DataAccess.connect();
            String query = "Update gp SET " +
                    "fullname = ?," +
                    "emailadd = ?," +
                    "pagernum = ?," +
                    "medicalcentreid = ?, " +
                    "picture = COALESCE(?, picture) " +
                    "WHERE id = ?";
            byte[] targetArray = new byte[0];
            try {
                if (pic.available() == 0) {
                    targetArray = null;
                } else {
                    targetArray = new byte[pic.available()];
                    pic.read(targetArray);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            ps = conn.prepareStatement(query);
            ps.setString(1, input.getName());
            ps.setString(2, input.getEmail());
            ps.setString(3, input.getPagerNum());
            ps.setInt(4, input.getMedC().getID());
            ps.setBytes(5, targetArray);
            ps.setInt(6, input.getID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public static ArrayList<Person> searchGP(@NotNull GP input, int pageNo) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Person> results = new ArrayList<>();
        try {
            conn = DataAccess.connect();
            String query = "SELECT * from " +
                    "(SELECT gp.id, fullname, pagernum, username, emailadd, medicalcentreid, mcname, mcadd, ROW_NUMBER() OVER (ORDER by gp.id) as RowNumber from gp " +
                    "INNER JOIN medicalcentre " +
                    "ON gp.medicalcentreid = medicalcentre.id " +
                    "WHERE (fullname LIKE ? OR ? IS NULL)" +
                    "AND (pagernum LIKE ? OR ? IS NULL)" +
                    "AND (emailadd LIKE ? OR ? IS NULL)" +
                    "AND (username LIKE ? OR ? IS NULL)) foo " +
                    "WHERE RowNumber > ? ORDER BY id LIMIT 5 ;";
            ps = conn.prepareStatement(query);
            ps.setString(1, "%" + input.getName() + "%");
            ps.setString(2, input.getName());
            ps.setString(3, "%" + input.getPagerNum() + "%");
            ps.setString(4, input.getPagerNum());
            ps.setString(5, "%" + input.getEmail() + "%");
            ps.setString(6, input.getEmail());
            ps.setString(7, "%" + input.getUserName() + "%");
            ps.setString(8, input.getUserName());
            ps.setInt(9, 10 * pageNo);
            rs = ps.executeQuery();
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
        return results;
    }

    public static int searchGPCount(@NotNull GP input) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        try {
            conn = DataAccess.connect();
            String query = "SELECT COUNT(id) from gp " +
                    "WHERE (fullname LIKE ? OR ? IS NULL)" +
                    "AND (pagernum LIKE ? OR ? IS NULL)" +
                    "AND (emailadd LIKE ? OR ? IS NULL)" +
                    "AND (username LIKE ? OR ? IS NULL)";

            ps = conn.prepareStatement(query);
            ps.setString(1, "%" + input.getName() + "%");
            ps.setString(2, input.getName());
            ps.setString(3, "%" + input.getPagerNum() + "%");
            ps.setString(4, input.getPagerNum());
            ps.setString(5, "%" + input.getEmail() + "%");
            ps.setString(6, input.getEmail());
            ps.setString(7, "%" + input.getUserName() + "%");
            ps.setString(8, input.getUserName());
            rs = ps.executeQuery();
            Boolean k = rs.next();
            count = rs.getInt("count");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
        return count;
    }

    public static boolean GPLogin(String Username, @NotNull String Password) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean validLogin = false;
        try {
            conn = DataAccess.connect();
            String query = "SELECT gp.id, fullname, pagernum, username, emailadd, medicalcentreid, mcname, mcadd from gp " +
                    "INNER JOIN medicalcentre " +
                    "ON gp.medicalcentreid = medicalcentre.id " +
                    "WHERE username = ? AND pswrd = ?;";
            ps = conn.prepareStatement(query);
            ps.setString(1, Username);
            ps.setString(2, String.valueOf(Password.hashCode()));
            rs = ps.executeQuery();
            validLogin = rs.next();
            if (validLogin) {
                int idIn = rs.getInt(1);
                String nameIn = rs.getString(2);
                String pagNumIn = rs.getString(3);
                String usernameIn = rs.getString(4);
                String emailIn = rs.getString(5);
                int mcid = rs.getInt(6);
                String mcName = rs.getString(7);
                String mcAdd = rs.getString(8);
                MedCentre MedCIn = new MedCentre(mcName, mcAdd, mcid);
                GP gpActive = new GP(nameIn, emailIn, MedCIn, idIn, pagNumIn, usernameIn, "n/a");
                Global.ActiveGP = gpActive;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
        return validLogin;
    }

    public static InputStream getGPPic(GP input) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        InputStream pic = InputStream.nullInputStream();
        try {
            conn = DataAccess.connect();
            String query = "Select picture from GP where id = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, input.getID());
            rs = ps.executeQuery();
            boolean k = rs.next();
            pic = new ByteArrayInputStream(rs.getBytes("picture"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
        return pic;
    }
    /*===================================================================================================================================*/
    /*==============================                        MEDICAL CENTRE QUERIES                         ==============================*/
    /*===================================================================================================================================*/
    public static void saveMedC(@NotNull MedCentre input) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DataAccess.connect();
            String query = "INSERT INTO medicalcentre " +
                    "(mcname, mcadd) " +
                    "VALUES (?,?);";
            ps = conn.prepareStatement(query);
            ps.setString(1, input.getName());
            ps.setString(2, input.getAddress());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
        DataAccess.loadMedCs();
    }

    public static MedCentre searchMedC(@NotNull MedCentre input) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        MedCentre mc = null;
        try {
            conn = DataAccess.connect();
            String query = "SELECT *, ROW_NUMBER() OVER (ORDER by 1) as RowNumber from medicalcentre " +
                    "WHERE (mcname LIKE ?) ORDER BY id LIMIT 3;";
            ps = conn.prepareStatement(query);
            ps.setString(1, input.getName());
            rs = ps.executeQuery();
            if (rs.next()){
                String name = rs.getString("mcname");
                String address = rs.getString("mcadd");
                int id = rs.getInt("id");
                mc = new MedCentre(name, address, id);
            }
            else {
                mc = new MedCentre("", "", 0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                ps.close();
            } catch (Exception e) { /* ignored */ }
            try {
                conn.close();
            } catch (Exception e) { /* ignored */ }
        }
        return mc;
    }

    public static void loadMedCs() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DataAccess.connect();
            String query = "SELECT * FROM medicalcentre";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<MedCentre> results = new ArrayList<>();
            while (rs.next()) {
                int idIn = rs.getInt(1);
                String nameIn = rs.getString(2);
                String addIn = rs.getString(3);
                MedCentre mc = new MedCentre(nameIn, addIn, idIn);
                results.add(mc);
            }
            Global.MCList = results;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
    }
    /*===================================================================================================================================*/
    /*==============================                          CASE REPORT QUERIES                          ==============================*/
    /*===================================================================================================================================*/
    public static int saveCaseReport(@NotNull CaseReport input) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int returnID = 0;

        try {
            conn = DataAccess.connect();
            String query = "INSERT INTO casereports " +
                    "(patientid, gpid, condition, chronic) " +
                    "VALUES (?,?,?,?) " +
                    "RETURNING id;"; //Also not an error
            ps = conn.prepareStatement(query);
            ps.setInt(1, input.getPatientID());
            ps.setInt(2, input.getGPID());
            ps.setString(3, input.getCondition());
            ps.setBoolean(4, input.getChronic());
            rs = ps.executeQuery();
            boolean k = rs.next();
            returnID = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
        return returnID;
    }

    public static void updateCaseReport(@NotNull CaseReport input) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DataAccess.connect();
            String query = "UPDATE casereports SET " +
                    "patientid = ?, " +
                    "gpid = ?, " +
                    "condition = ?, " +
                    "chronic = ? " +
                    "where  id = ?;"; //Also not an error
            ps = conn.prepareStatement(query);
            ps.setInt(1, input.getPatientID());
            ps.setInt(2, input.getGPID());
            ps.setString(3, input.getCondition());
            ps.setBoolean(4, input.getChronic());
            ps.setInt(5, input.getCaseID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public static ArrayList<CaseReport> searchCaseReport(int patientID, int GPID) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<CaseReport> results = new ArrayList<>();

        try {
            conn = DataAccess.connect();
            String query = "SELECT * from " +
                    "(SELECT DISTINCT on (casereports.id) " + //also not an error
                    "casereports.id, patientid, gpid, condition, chronic, notedate, ROW_NUMBER() OVER (ORDER by notedate desc) as RowNumber from casereports " +
                    "inner join notes on casereports.id=notes.casereportid " +
                    "WHERE (patientid = ?) " +
                    "OR (gpid = ?)) foo " +
                    "ORDER BY notedate desc;";
            ps = conn.prepareStatement(query);
            ps.setInt(1, patientID);
            ps.setInt(2, GPID);
            rs = ps.executeQuery();
            while (rs.next()) {
                String condition = rs.getString("condition");
                int patID = rs.getInt("patientid");
                int id = rs.getInt("id");
                int gpid = rs.getInt("gpid");
                Boolean isChronic = rs.getBoolean("chronic");
                LocalDate modified = rs.getDate("notedate").toLocalDate();
                CaseReport CRNew = new CaseReport(condition, gpid, patID, id);
                CRNew.setChronic(isChronic);
                CRNew.setLastModified(modified);
                results.add(CRNew);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
        return results;

    }
    /*===================================================================================================================================*/
    /*==============================                             NOTE QUERIES                              ==============================*/
    /*===================================================================================================================================*/
    public static void saveNote(@NotNull Note input) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DataAccess.connect();
            String query = "INSERT INTO notes" +
                    "(notedate, note, casereportid)" +
                    "VALUES (?,?,?);";
            ps = conn.prepareStatement(query);
            Timestamp tStamp = Timestamp.valueOf(input.getDate()); //saves date AND time
            ps.setTimestamp(1, tStamp);
            ps.setString(2, input.getText());
            ps.setInt(3, input.getCaseID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public static ArrayList<Note> loadNotes(int caseID) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Note> results = new ArrayList<>();
        try {
            conn = DataAccess.connect();
            String query = "SELECT notedate, note from notes " +
                    "WHERE (casereportid = ?)" +
                    "ORDER BY id DESC;";
            ps = conn.prepareStatement(query);
            ps.setInt(1, caseID);
            rs = ps.executeQuery();
            while (rs.next()) {
                LocalDateTime date = rs.getTimestamp("notedate").toLocalDateTime();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-d | HH:mm:ss");
                String noteText = "( " + dtf.format(date) + " ): " + rs.getString("note");
                Note newNote = new Note(date, noteText, caseID);
                results.add(newNote);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
        return results;
    }

    /*===================================================================================================================================*/
    /*==============================                          MEDICATION QUERIES                           ==============================*/
    /*===================================================================================================================================*/
    public static void saveMedication(@NotNull Medication input) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DataAccess.connect();
            String query = "INSERT INTO medication" +
                    "(medname, startdate, duration, usagenotes, casereportid)" +
                    "VALUES (?,?,?,?,?);";
            ps = conn.prepareStatement(query);
            Date startDate = Date.valueOf(input.getStartDate());
            ps.setString(1, input.getName());
            ps.setDate(2, startDate);
            ps.setInt(3, input.getDuration());
            ps.setString(4, input.getUsageNotes());
            ps.setInt(5, input.getCaseID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
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
    public static ArrayList<Medication> loadMedications(int caseID) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Medication> results = new ArrayList<>();
        try {
            conn = DataAccess.connect();
            String query = "SELECT * FROM medication " +
                    "WHERE (casereportid = ?) " +
                    "ORDER BY id DESC;";
            ps = conn.prepareStatement(query);
            ps.setInt(1, caseID);
            rs = ps.executeQuery();
            while (rs.next()) {
                LocalDate startDate = rs.getDate("startdate").toLocalDate();
                int duration = rs.getInt("duration");
                String name = rs.getString("medname");
                String usage = rs.getString("usagenotes");
                Medication newMed = new Medication(name, startDate, duration, usage, caseID);
                results.add(newMed);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                ps.close();
            } catch (Exception e) { /* ignored */ }
            try {
                conn.close();
            } catch (Exception e) { /* ignored */ }
        }
        return results;
    }
}