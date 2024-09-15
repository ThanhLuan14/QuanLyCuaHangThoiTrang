/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.quyenDTRolePojo;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author AnhDoan
 */
public class quyenDTRoleDao {
    public ArrayList<quyenDTRolePojo> layDSQDTRole(String role){
            ArrayList<quyenDTRolePojo>dsQuyenDT = new ArrayList<>();
            String query = "SELECT * FROM ROLE_TAB_PRIVS WHERE ROLE = '"+role+"' order by table_name desc";
            ResultSet rs = Database.excuteQuery(query);
            try {
                 while (rs.next()) {
                    quyenDTRolePojo quyenDoiTuong = new quyenDTRolePojo();
                    quyenDoiTuong.setOwner(rs.getString("OWNER"));
                    quyenDoiTuong.setTable_name(rs.getString("TABLE_NAME"));
                    quyenDoiTuong.setPrivilege(rs.getString("PRIVILEGE"));
                    dsQuyenDT.add(quyenDoiTuong);
                }
        } catch (Exception e) {
        }
        return dsQuyenDT;
    }
    
    public ArrayList<quyenDTRolePojo> layDSQDTRoles(String role){
            ArrayList<quyenDTRolePojo>dsQuyenDT = new ArrayList<>();
            String query = "SELECT * FROM SYS.V_RolePrivilegeDT WHERE ROLE = '"+role+"'";
            ResultSet rs = Database.excuteQuery(query);
            try {
                 while (rs.next()) {
                    quyenDTRolePojo quyenDoiTuong = new quyenDTRolePojo();
                    quyenDoiTuong.setOwner(rs.getString("OWNER"));
                    quyenDoiTuong.setTable_name(rs.getString("TABLE_NAME"));
                    quyenDoiTuong.setPrivilege(rs.getString("PRIVILEGE"));
                    dsQuyenDT.add(quyenDoiTuong);
                }
        } catch (Exception e) {
        }
        return dsQuyenDT;
    }
}