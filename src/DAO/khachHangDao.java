/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import static DAO.Database.conn;
import POJO.khachHangPojo;
import POJO.nhanVienPojo;
import POJO.phieuNhapPojo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class khachHangDao implements DAOInterface<khachHangPojo>{
     public static khachHangDao getInstance() {
        return new khachHangDao();
    }
    public ArrayList<khachHangPojo> layDSKH() {
        ArrayList<khachHangPojo> listKH = new ArrayList<khachHangPojo>();
        try {

            String sql = "select * from qldata.khachhang where trangthai = 1";

            ResultSet rs = Database.excuteQuery(sql);

            while (rs.next()) {
                khachHangPojo pn = new khachHangPojo();
                pn.setMaKH(rs.getString("MAKHACHHANG"));
                pn.setTenKH(rs.getString("HOTEN"));
                pn.setNgaySinh(rs.getString("NGAYSINH"));
                pn.setGioiTinh(rs.getString("GIOITINH"));
                pn.setSoDt(rs.getString("SODIENTHOAI"));
                pn.setEmail(rs.getString("EMAIL"));
                pn.setDiaChi(rs.getString("DIACHI"));
                listKH.add(pn);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listKH;
    }

    public ArrayList<khachHangPojo> layDSKH1(String m) {
        ArrayList<khachHangPojo> listKH = new ArrayList<khachHangPojo>();
        String sql = "select * from qldata.khachhang where makhachhang ='" + m + "' and trangthai =1";
        ResultSet rs = Database.excuteQuery(sql);
        try {
            while (rs.next()) {
                khachHangPojo pn = new khachHangPojo();
                pn.setMaKH(rs.getString("MAKHACHHANG"));
                pn.setTenKH(rs.getString("HOTEN"));
                pn.setNgaySinh(rs.getString("NGAYSINH"));
                pn.setGioiTinh(rs.getString("GIOITINH"));
                pn.setSoDt(rs.getString("SODIENTHOAI"));
                pn.setEmail(rs.getString("EMAIL"));
                pn.setDiaChi(rs.getString("DIACHI"));
                listKH.add(pn);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listKH;
    }
     public ArrayList<khachHangPojo> layDSKH2(String m) {
        ArrayList<khachHangPojo> listKH = new ArrayList<khachHangPojo>();
        try {

            String sql = "select * from qldata.khachhang where sodienthoai  LIKE '%"+m+"%' and trangthai = 1";

            ResultSet rs = Database.excuteQuery(sql);

            while (rs.next()) {
                khachHangPojo pn = new khachHangPojo();
                pn.setMaKH(rs.getString("MAKHACHHANG"));
                pn.setTenKH(rs.getString("HOTEN"));
                pn.setNgaySinh(rs.getString("NGAYSINH"));
                pn.setGioiTinh(rs.getString("GIOITINH"));
                pn.setSoDt(rs.getString("SODIENTHOAI"));
                pn.setEmail(rs.getString("EMAIL"));
                pn.setDiaChi(rs.getString("DIACHI"));
                listKH.add(pn);
            }

        } catch (Exception e) {
//            e.printStackTrace();
        }
        return listKH;
    }
      public khachHangPojo layDSKH3(String m) {
        khachHangPojo kh = null;
        try {

            String sql = "select * from qldata.khachhang where MAKHACHHANG = ? and trangthai =1";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, m);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                khachHangPojo pn = new khachHangPojo();
                String MaKH =rs.getString("MAKHACHHANG");
                String TenKH= rs.getString("HOTEN");
                String Sdt =rs.getString("SODIENTHOAI");
                
                kh = new khachHangPojo(MaKH, TenKH, Sdt);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return kh;
    }
    public ArrayList<khachHangPojo> layDSKH4(int checkSQL, String text) {

        ArrayList<khachHangPojo> listKH = new ArrayList<>();
        var query = new StringBuilder("select * from qldata.khachhang where trangthai =1");
        if (checkSQL == 1) {
            query.append(" AND LOWER(hoten) LIKE '%").append(text.toLowerCase()).append("%'");
        } else if (checkSQL == 2) {
            query.append(" AND LOWER(sodienthoai) LIKE '%").append(text.toLowerCase()).append("%'");
        }

        ResultSet rs = Database.excuteQuery(query.toString());
        try {
            while (rs.next()) {
                khachHangPojo pn = new khachHangPojo();
                pn.setMaKH(rs.getString("MAKHACHHANG"));
                pn.setTenKH(rs.getString("HOTEN"));
                pn.setNgaySinh(rs.getString("NGAYSINH"));
                pn.setGioiTinh(rs.getString("GIOITINH"));
                pn.setSoDt(rs.getString("SODIENTHOAI"));
                pn.setEmail(rs.getString("EMAIL"));
                pn.setDiaChi(rs.getString("DIACHI"));
                listKH.add(pn);
            }
        } catch (Exception e) {
        }

        return listKH;
    }

    @Override
    public khachHangPojo layDSSP1(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<khachHangPojo> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int insert(khachHangPojo t) {
        int ketQua = 0;
        try {
            String sql = "INSERT INTO qldata.KhachHang (MaKhachHang, HoTen, NgaySinh, GioiTinh, SoDienThoai, Email, DiaChi, TrangThai) VALUES (?,?,NULL,NULL,?,NULL,NULL,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, t.getMaKH());
            pst.setString(2, t.getTenKH());
            pst.setString(3, t.getSoDt());
            pst.setString(4, t.getTrangThai());
           
            ketQua = pst.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }
}
