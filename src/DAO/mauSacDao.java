/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.mauSacPojo;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class mauSacDao {

    public ArrayList<mauSacPojo> layDSMS() {

        ArrayList<mauSacPojo> dsMauSac = new ArrayList<>();
        String query = "select * from qldata.mausac";
        ResultSet rs = Database.excuteQuery(query);
        try {
            while (rs.next()) {
                mauSacPojo mauSac = new mauSacPojo();
                mauSac.setMaMauSac(rs.getString("MAMAUSAC"));
                mauSac.setTenMauSac(rs.getString("TENMAUSAC"));
                dsMauSac.add(mauSac);
            }
        } catch (Exception e) {
        }

        return dsMauSac;
    }
}
