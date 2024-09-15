/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.chucVuPojo;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author AnhDoan
 */
public class chucVuDao {
    public ArrayList<chucVuPojo> layDSCV() {

        ArrayList<chucVuPojo> dsCV = new ArrayList<>();
        String query = "select * from qldata.chucvu";
        ResultSet rs = Database.excuteQuery(query);
        try {
            while (rs.next()) {
                chucVuPojo cv = new chucVuPojo();
                cv.setMaCV(rs.getString("MACHUCVU"));
                cv.setTenCV(rs.getString("TENCHUCVU"));
                dsCV.add(cv);
            }
        } catch (Exception e) {
        }

        return dsCV;
    }
}
