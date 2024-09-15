/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.kichThuocPojo;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class kichThuocDao {

    public ArrayList<kichThuocPojo> layDSKT() {

        ArrayList<kichThuocPojo> dsKichThuoc = new ArrayList<>();
        String query = "select * from qldata.kichthuoc";
        ResultSet rs = Database.excuteQuery(query);
        try {
            while (rs.next()) {
                kichThuocPojo kichThuoc = new kichThuocPojo();
                kichThuoc.setMaKichThuoc(rs.getString("MAKICHTHUOC"));
                kichThuoc.setTenKichThuoc(rs.getString("TENKICHTHUOC"));
                dsKichThuoc.add(kichThuoc);
            }
        } catch (Exception e) {
        }

        return dsKichThuoc;
    }
}
