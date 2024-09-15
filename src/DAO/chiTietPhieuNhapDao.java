/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.chiTietPhieuNhapPojo;
import POJO.khachHangPojo;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author AnhDoan
 */
public class chiTietPhieuNhapDao {
    public ArrayList<chiTietPhieuNhapPojo> layDSCTPN(String maPN) {
        ArrayList<chiTietPhieuNhapPojo> listCTPN = new ArrayList<chiTietPhieuNhapPojo>();
        try {

            String sql = "select * from qldata.chitietphieunhap ct join qldata.sanpham sp on ct.masanpham = sp.masanpham join qldata.nhomsanpham nsp on nsp.manhomsanpham = sp.manhomsanpham join qldata.kichthuoc kt on kt.makichthuoc = sp.makichthuoc join qldata.mausac ms on ms.mamausac = sp.mamausac where maphieunhap = '"+maPN+"'";

            ResultSet rs = Database.excuteQuery(sql);

            while (rs.next()) {
                chiTietPhieuNhapPojo pn = new chiTietPhieuNhapPojo();
                pn.setMaSP(rs.getString("MASANPHAM"));
                pn.setMa(rs.getString("MAPHIEUNHAP"));
                pn.setKichThuoc(rs.getString("TENKICHTHUOC"));
                pn.setMauSac(rs.getString("TENMAUSAC"));
                pn.setTensp(rs.getString("TENSANPHAM"));
                pn.setSoluong(rs.getString("SOLUONG"));
                pn.setGianhap(rs.getString("GIANHAP"));
                listCTPN.add(pn);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCTPN;
    }
}
