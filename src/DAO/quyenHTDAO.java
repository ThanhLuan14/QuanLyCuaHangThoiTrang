/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.xemQuyenHeThongPojo;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author AnhDoan
 */
public class quyenHTDAO {
    public ArrayList<xemQuyenHeThongPojo> layDSQHT(String userName){
            ArrayList<xemQuyenHeThongPojo>dsQuyenHT = new ArrayList<>();
            String query = "SELECT PRIVILEGE FROM DBA_SYS_PRIVS WHERE grantee = '"+userName+"'";
            ResultSet rs = Database.excuteQuery(query);
            try {
                 while (rs.next()) {
                    xemQuyenHeThongPojo quyenHT = new xemQuyenHeThongPojo();
                    quyenHT.setQuyenHT(rs.getString("PRIVILEGE"));
                    dsQuyenHT.add(quyenHT);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
            return dsQuyenHT;
        }
    
    public ArrayList<xemQuyenHeThongPojo> layDSQHTUS(String userName){
            ArrayList<xemQuyenHeThongPojo>dsQuyenHT = new ArrayList<>();
            String query = "SELECT PRIVILEGE FROM SYS.V_QHT WHERE grantee = '"+userName+"'";
            ResultSet rs = Database.excuteQuery(query);
            try {
                 while (rs.next()) {
                    xemQuyenHeThongPojo quyenHT = new xemQuyenHeThongPojo();
                    quyenHT.setQuyenHT(rs.getString("PRIVILEGE"));
                    dsQuyenHT.add(quyenHT);
                }
        } catch (Exception e) {
        }
            return dsQuyenHT;
        }
}
