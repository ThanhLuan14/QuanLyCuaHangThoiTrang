/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.nhaCungCapPojo;
import POJO.nhanVienPojo;
import POJO.phieuNhapPojo;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author AnhDoan
 */
public class nhaCungCapDao {

    public ArrayList<nhaCungCapPojo> layDSNCC() {
        ArrayList<nhaCungCapPojo> listNCC = new ArrayList<nhaCungCapPojo>();
        try {

            String sql = "SELECT * FROM qldata.nhacungcap where trangthai = 1";

            ResultSet rs = Database.excuteQuery(sql);

            while (rs.next()) {
                nhaCungCapPojo ncc = new nhaCungCapPojo();
                ncc.setMancc(rs.getString("MANHACUNGCAP"));
                ncc.setTenncc(rs.getString("TENNHACUNGCAP"));
                ncc.setSdt(rs.getString("SODIENTHOAI"));
                ncc.setEmail(rs.getString("EMAIL"));
                ncc.setDiachi(rs.getString("DIACHI"));
                listNCC.add(ncc);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listNCC;
    }
    
    public ArrayList<nhaCungCapPojo> layMaNCC() {
        ArrayList<nhaCungCapPojo> listNCC = new ArrayList<nhaCungCapPojo>();
        try {

            String sql = "SELECT * FROM qldata.nhacungcap";

            ResultSet rs = Database.excuteQuery(sql);

            while (rs.next()) {
                nhaCungCapPojo ncc = new nhaCungCapPojo();
                ncc.setMancc(rs.getString("MANHACUNGCAP"));
                listNCC.add(ncc);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listNCC;
    }
    
    public ArrayList<nhaCungCapPojo> layDSNCC1(int checkSQL, String text) {

        ArrayList<nhaCungCapPojo> listNCC = new ArrayList<nhaCungCapPojo>();
        var query = new StringBuilder("select * from qldata.nhacungcap where 1 = 1 and trangthai = 1");
        if (checkSQL == 1) {
            query.append(" AND LOWER(MANHACUNGCAP) LIKE '%").append(text.toLowerCase()).append("%'");
        } else if (checkSQL == 2) {
            query.append(" AND LOWER(TENNHACUNGCAP) LIKE '%").append(text.toLowerCase()).append("%'");
        } else if (checkSQL == 3) {
            query.append(" AND LOWER(SODIENTHOAI) LIKE '%").append(text.toLowerCase()).append("%'");
        } else {
            query.append(" AND LOWER(EMAIL) LIKE '%").append(text.toLowerCase()).append("%'");
        }

        ResultSet rs = Database.excuteQuery(query.toString());
        try {
            while (rs.next()) {
                nhaCungCapPojo ncc = new nhaCungCapPojo();
                ncc.setMancc(rs.getString("MANHACUNGCAP"));
                ncc.setTenncc(rs.getString("TENNHACUNGCAP"));
                ncc.setSdt(rs.getString("SODIENTHOAI"));
                ncc.setEmail(rs.getString("EMAIL"));
                ncc.setDiachi(rs.getString("DIACHI"));
                listNCC.add(ncc);
            }
        } catch (Exception e) {
        }

        return listNCC;
    }
    
    public ArrayList<nhaCungCapPojo> layDSNCC2(String mancc) {
        ArrayList<nhaCungCapPojo> listNCC = new ArrayList<nhaCungCapPojo>();
        try {

            String sql = "SELECT * FROM qldata.nhacungcap where MANHACUNGCAP = '"+mancc+"'";

            ResultSet rs = Database.excuteQuery(sql);

            while (rs.next()) {
                nhaCungCapPojo ncc = new nhaCungCapPojo();
                ncc.setMancc(rs.getString("MANHACUNGCAP"));
                ncc.setTenncc(rs.getString("TENNHACUNGCAP"));
                ncc.setSdt(rs.getString("SODIENTHOAI"));
                ncc.setEmail(rs.getString("EMAIL"));
                ncc.setDiachi(rs.getString("DIACHI"));
                listNCC.add(ncc);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listNCC;
    }
}
