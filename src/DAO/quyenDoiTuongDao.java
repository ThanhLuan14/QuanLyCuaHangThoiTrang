/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.xemQuyenDoiTuongPojo;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author AnhDoan
 */
public class quyenDoiTuongDao {
        public ArrayList<xemQuyenDoiTuongPojo> layDSQDT(String userName){
            ArrayList<xemQuyenDoiTuongPojo>dsQuyenDT = new ArrayList<>();
            String query = "SELECT TABLE_NAME, PRIVILEGE, GRANTOR FROM DBA_TAB_PRIVS WHERE GRANTEE='"+userName+"' order by table_name desc";
            ResultSet rs = Database.excuteQuery(query);
            try {
                 while (rs.next()) {
                    xemQuyenDoiTuongPojo quyenDoiTuong = new xemQuyenDoiTuongPojo();
                    quyenDoiTuong.setTableName(rs.getString("TABLE_NAME"));
                    quyenDoiTuong.setQuyen(rs.getString("PRIVILEGE"));
                    quyenDoiTuong.setNguoiCap(rs.getString("GRANTOR"));
                    dsQuyenDT.add(quyenDoiTuong);
                }
        } catch (Exception e) {
        }
        
            return dsQuyenDT;
        }
        
        public ArrayList<xemQuyenDoiTuongPojo> layDSQDTUS(String userName){
            ArrayList<xemQuyenDoiTuongPojo>dsQuyenDT = new ArrayList<>();
            String query = "SELECT TABLE_NAME, PRIVILEGE, GRANTOR FROM SYS.V_QDT WHERE GRANTEE='"+userName+"'";
            ResultSet rs = Database.excuteQuery(query);
            try {
                 while (rs.next()) {
                    xemQuyenDoiTuongPojo quyenDoiTuong = new xemQuyenDoiTuongPojo();
                    quyenDoiTuong.setTableName(rs.getString("TABLE_NAME"));
                    quyenDoiTuong.setQuyen(rs.getString("PRIVILEGE"));
                    quyenDoiTuong.setNguoiCap(rs.getString("GRANTOR"));
                    dsQuyenDT.add(quyenDoiTuong);
                }
        } catch (Exception e) {
        }
        
            return dsQuyenDT;
        }
}
