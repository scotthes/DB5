package com.quad;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataAccess {
    public static Connection connect(){
        String url = "jdbc:postgresql://localhost:5432/postgres"; // change to web url when server set up
        String user = "postgres";
        String password = "easyentry"; // change to whatever postgres password is
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    public static void save(String sqlStr){
        Connection conn = DataAccess.connect();
        Statement s = null;
        try {
            s = conn.createStatement();
            s.execute(sqlStr);
            s.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
