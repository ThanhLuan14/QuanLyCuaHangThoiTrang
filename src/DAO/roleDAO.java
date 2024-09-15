/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.roleNamePojo;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author AnhDoan
 */
public class roleDAO {
    public ArrayList<roleNamePojo> loadRoles(){
            ArrayList<roleNamePojo>role = new ArrayList<>();
            String query = "select ROLE from DBA_ROLES where oracle_maintained != 'Y'";
            ResultSet rs = Database.excuteQuery(query);
            try {
                 while (rs.next()) {
                    roleNamePojo rl = new roleNamePojo();
                    rl.setRoleName(rs.getString("ROLE"));
                    role.add(rl);
                }
        } catch (Exception e) {
        }
            return role;
        }
}
