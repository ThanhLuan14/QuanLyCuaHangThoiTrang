/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import static DAO.Database.conn;
import POJO.nhanVienPojo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author AnhDoan
 */
public class nhanVienDao {
    
    public static nhanVienDao getInstance() {
        return new nhanVienDao();
    }

    public ArrayList<nhanVienPojo> layDSNV(int checkSQL, String text) {

        ArrayList<nhanVienPojo> dsNhanVien = new ArrayList<>();
        var query = new StringBuilder("select * from qldata.nhanvien nv join qldata.chucvu cv on nv.machucvu = cv.machucvu where trangthai = 1");
        if (checkSQL == 1) {
            query.append(" AND LOWER(hoten) LIKE '%").append(text.toLowerCase()).append("%'");
        } else if (checkSQL == 2) {
            query.append(" AND LOWER(sodienthoai) LIKE '%").append(text.toLowerCase()).append("%'");
        } else if (checkSQL == 3) {
            query.append(" AND LOWER(email) LIKE '%").append(text.toLowerCase()).append("%'");
        } else {
            query.append(" AND LOWER(diachi) LIKE '%").append(text.toLowerCase()).append("%'");
        }

        ResultSet rs = Database.excuteQuery(query.toString());
        try {
            while (rs.next()) {

                nhanVienPojo nhanVien = new nhanVienPojo();
                nhanVien.setMaNhanVien(rs.getString("MANHANVIEN"));
                nhanVien.setHoTen(rs.getString("HOTEN"));
                nhanVien.setGioiTinh(rs.getString("GIOITINH"));
                nhanVien.setNgaySinh(rs.getDate("NGAYSINH").toString());
                nhanVien.setDiaChi(rs.getString("DIACHI"));
                nhanVien.setEmail(rs.getString("EMAIL"));
                nhanVien.setChucVu(rs.getString("TENCHUCVU"));
                nhanVien.setSoDienThoai(rs.getString("SODIENTHOAI"));
                dsNhanVien.add(nhanVien);
            }
        } catch (Exception e) {
        }

        return dsNhanVien;
    }
    
    public ArrayList<nhanVienPojo> layDSNV1() {

        ArrayList<nhanVienPojo> dsNhanVien = new ArrayList<>();
        String query ="select * from qldata.nhanvien where trangthai = 1";
        ResultSet rs = Database.excuteQuery(query);
        try {
            while (rs.next()) {

                nhanVienPojo nhanVien = new nhanVienPojo();
                nhanVien.setMaNhanVien(rs.getString("MANHANVIEN"));
                nhanVien.setHoTen(rs.getString("HOTEN"));
                nhanVien.setGioiTinh(rs.getString("GIOITINH"));
                nhanVien.setNgaySinh(rs.getDate("NGAYSINH").toString());
                nhanVien.setDiaChi(rs.getString("DIACHI"));
                nhanVien.setEmail(rs.getString("EMAIL"));
                nhanVien.setChucVu(rs.getString("MACHUCVU"));
                nhanVien.setSoDienThoai(rs.getString("SODIENTHOAI"));
                dsNhanVien.add(nhanVien);
            }
        } catch (Exception e) {
        }

        return dsNhanVien;
    }
    public ArrayList<nhanVienPojo> layMANV() {

        ArrayList<nhanVienPojo> dsNhanVien = new ArrayList<>();
        String query ="select * from qldata.nhanvien";
        ResultSet rs = Database.excuteQuery(query);
        try {
            while (rs.next()) {

                nhanVienPojo nhanVien = new nhanVienPojo();
                nhanVien.setMaNhanVien(rs.getString("MANHANVIEN"));
                dsNhanVien.add(nhanVien);
            }
        } catch (Exception e) {
        }

        return dsNhanVien;
    }
    
    
    public ArrayList<nhanVienPojo> layDSNV2(String manv) {
        ArrayList<nhanVienPojo> dsNhanVien = new ArrayList<>();
        String query ="select * from qldata.nhanvien where trangthai = 1 and manhanvien = '"+manv+"'";
        ResultSet rs = Database.excuteQuery(query);
        try {
            while (rs.next()) {

                nhanVienPojo nhanVien = new nhanVienPojo();
                nhanVien.setMaNhanVien(rs.getString("MANHANVIEN"));
                nhanVien.setHoTen(rs.getString("HOTEN"));
                nhanVien.setGioiTinh(rs.getString("GIOITINH"));
                nhanVien.setNgaySinh(rs.getDate("NGAYSINH").toString());
                nhanVien.setDiaChi(rs.getString("DIACHI"));
                nhanVien.setEmail(rs.getString("EMAIL"));
                nhanVien.setChucVu(rs.getString("MACHUCVU"));
                nhanVien.setSoDienThoai(rs.getString("SODIENTHOAI"));
                dsNhanVien.add(nhanVien);
            }
        } catch (Exception e) {
        }

        return dsNhanVien;
    }
     public nhanVienPojo layDSNV3( String maNv) {

        nhanVienPojo dsNhanVien = null;
        
        try {
            String query ="select * from qldata.nhanvien where trangthai = 1 and manhanvien =?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, maNv);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {

                nhanVienPojo nhanVien = new nhanVienPojo();
                String manv=rs.getString("MANHANVIEN");
                String hoten =rs.getString("HOTEN");
                String gioitinh= rs.getString("GIOITINH");
                String ngaysinh =rs.getDate("NGAYSINH").toString();
                String diaChi =rs.getString("DIACHI");
                String gmail=rs.getString("EMAIL");
                String chucVu=rs.getString("MACHUCVU");
                String sdt =rs.getString("SODIENTHOAI");
                dsNhanVien = new nhanVienPojo(manv, hoten, ngaysinh, gioitinh, sdt, gmail, diaChi, chucVu);
            }
        } catch (Exception e) {
        }

        return dsNhanVien;
    }
    
}
