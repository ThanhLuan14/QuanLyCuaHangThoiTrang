/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.RoleUserPojo;
import POJO.roleNamePojo;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author AnhDoan
 */
public class RoleUserDao {
    public ArrayList<RoleUserPojo> loadRolesUser(String userName){
            ArrayList<RoleUserPojo>role = new ArrayList<>();
            String query = "SELECT * FROM DBA_ROLE_PRIVS WHERE GRANTEE = '"+userName+"'";
            ResultSet rs = Database.excuteQuery(query);
            try {
                 while (rs.next()) {
                    RoleUserPojo rl = new RoleUserPojo();
                    rl.setRoleName(rs.getString("GRANTED_ROLE"));
                    role.add(rl);
                }
        } catch (Exception e) {
        }
            return role;
        }
    
    public ArrayList<RoleUserPojo> loadRoleUser(String userName){
            ArrayList<RoleUserPojo>role = new ArrayList<>();
            String query = "SELECT * FROM SYS.V_NHOMQUYEN WHERE GRANTEE = '"+userName+"'";
            ResultSet rs = Database.excuteQuery(query);
            try {
                 while (rs.next()) {
                    RoleUserPojo rl = new RoleUserPojo();
                    rl.setRoleName(rs.getString("GRANTED_ROLE"));
                    role.add(rl);
                }
        } catch (Exception e) {
        }
            return role;
        }
}
