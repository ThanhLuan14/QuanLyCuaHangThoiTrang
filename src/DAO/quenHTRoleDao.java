/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.quyenHTRolePojo;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author AnhDoan
 */
public class quenHTRoleDao {
    public ArrayList<quyenHTRolePojo> layDSQHTRole(String role){
            ArrayList<quyenHTRolePojo>dsQuyenHT = new ArrayList<>();
            String query = "SELECT * FROM ROLE_SYS_PRIVS WHERE ROLE = '"+role+"'";
            ResultSet rs = Database.excuteQuery(query);
            try {
                 while (rs.next()) {
                    quyenHTRolePojo quyenHT = new quyenHTRolePojo();
                    quyenHT.setPrivilege(rs.getString("PRIVILEGE"));
                    dsQuyenHT.add(quyenHT);
                }
        } catch (Exception e) {
        }
        return dsQuyenHT;
    }
    
    public ArrayList<quyenHTRolePojo> layDSQHTRoles(String role){
            ArrayList<quyenHTRolePojo>dsQuyenHT = new ArrayList<>();
            String query = "SELECT * FROM SYS.V_RolePrivilegeHT WHERE ROLE = '"+role+"'";
            ResultSet rs = Database.excuteQuery(query);
            try {
                 while (rs.next()) {
                    quyenHTRolePojo quyenHT = new quyenHTRolePojo();
                    quyenHT.setPrivilege(rs.getString("PRIVILEGE"));
                    dsQuyenHT.add(quyenHT);
                }
        } catch (Exception e) {
        }
        return dsQuyenHT;
    }
}
