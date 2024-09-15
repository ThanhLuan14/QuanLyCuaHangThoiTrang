/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AnhDoan
 */
public class Database {

//    private static String url = "jdbc:oracle:thin:@localhost:1521:";
//    private static String url = "jdbc:oracle:thin:@//192.168.1.13:1521/";
    private static String url = "jdbc:oracle:thin:@//192.168.23.1:1521/";
    public static String database = "QLTT";
    public static String user = "";
    public static String password = "";
    public static Connection conn;

    public static String baoLoiConnect = "";

    public static boolean Connect() {
        baoLoiConnect = "";
        try {
            if (user.equals("SYS") || user.equals("sys")) {
                user += " as sysdba";
            }
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(url + database, user, password);
            return true;
        } catch (Exception e) {
            baoLoiConnect = e.getMessage();
            System.out.println(baoLoiConnect);
            return false;
        }
        
    }

    public static String loiLoadTable = "";

    public static ResultSet excuteQuery(String sql) {
        loiLoadTable = "";
        ResultSet rs = null;
        try {
            Statement state = conn.createStatement();
            rs = state.executeQuery(sql);
        } catch (Exception e) {
            loiLoadTable = e.getMessage();
            System.out.println(loiLoadTable);
        }
        return rs;
    }

    public static String baoLoi = "";

    public static boolean excuteUpdate(String sql) {
        baoLoi = "";
        try {
            Statement state = conn.createStatement();
            state.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            baoLoi = e.getMessage();
            System.out.println(baoLoi);
            
            return false;
        }
        
    }
}
