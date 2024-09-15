/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.hoaDonPojo;
import POJO.mauSacPojo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import static DAO.Database.conn;
import POJO.khachHangPojo;
import POJO.phieuNhapPojo;


public class hoaDonDao {
    public static hoaDonDao getInstance() {
        return new hoaDonDao();
    }
      public ArrayList<hoaDonPojo> layDSHD() {
          
        ArrayList<hoaDonPojo> dsHoaDon = new ArrayList<>();
        String query = "SELECT * FROM qldata.HOADON ORDER BY ngaylap DESC";
        ResultSet rs = Database.excuteQuery(query);
        try {
            while (rs.next()) {
                String maPhieu = rs.getString("MAHOADON");
                String khachHang = rs.getString("MAKHACHHANG");
                String maNhanVien = rs.getString("MANHANVIEN");
                Timestamp thoiGianTao = rs.getTimestamp("NGAYLAP");
                double tongTien = rs.getDouble("TONGTIEN");
                hoaDonPojo p = new hoaDonPojo(maPhieu,khachHang,maNhanVien, thoiGianTao, chiTietHoaDonDao.getInstance().layDSCTHD(maPhieu), tongTien);
                dsHoaDon.add(p);
            }
        } catch (Exception e) {
        }

        return dsHoaDon;
    }
    public int insert(hoaDonPojo t) {
        int ketQua = 0;
        try {

                String sql = "INSERT INTO qldata.hoadon (MAHOADON, MAKHACHHANG, MANHANVIEN, NGAYLAP,TONGTIEN) VALUES (?,?,?,?,?)";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, t.getMaHD());
                pst.setString(2, t.getMaKH());
                pst.setString(3, t.getMaNhanVien());
                pst.setTimestamp(4, t.getNgayLap());
                pst.setDouble(5, t.getTongTien());
                ketQua = pst.executeUpdate();
  
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }
    public int insertHDVangLai(hoaDonPojo t) {
        int ketQua = 0;
        try {

                String sql = "INSERT INTO qldata.hoadon (MAHOADON, MAKHACHHANG, MANHANVIEN, NGAYLAP,TONGTIEN) VALUES (?,NULL,?,?,?)";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, t.getMaHD());             
                pst.setString(2, t.getMaNhanVien());
                pst.setTimestamp(3, t.getNgayLap());
                pst.setDouble(4, t.getTongTien());
                ketQua = pst.executeUpdate();
  
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }

    public ArrayList<hoaDonPojo> loadDSHDDATE(int check, String text,String dateFrom, String dateTo){
         ArrayList<hoaDonPojo> listHD = new ArrayList<hoaDonPojo>();
        try {
            
            var query = new StringBuilder("SELECT * FROM qldata.HOADON where tongtien >0 order by ngaylap desc" );
            sqlSearch(check, query, text);
            sqlDate(query, dateFrom,dateTo);
            ResultSet rs = Database.excuteQuery(query.toString());

            while (rs.next()) {
                hoaDonPojo hd = new hoaDonPojo();
                hd.setMaHD(rs.getString("MAHOADON"));
                hd.setMaKH(rs.getString("MAKHACHHANG"));
                hd.setMaNhanVien(rs.getString("MANHANVIEN"));
                hd.setNgayLap(rs.getTimestamp("NGAYLAP")); 
                hd.setTongTien(rs.getDouble("TONGTIEN"));
                listHD.add(hd);
            }
            
        } catch (Exception e) {
           
        }
        return listHD;
    }
         public void sqlSearch(int check, StringBuilder query, String text) {
        if (check == 1 && (text.equals("") == false)) {
            query.append(" AND LOWER(mahoadon) LIKE '%").append(text.toLowerCase()).append("%'");
        }
        if (check == 2 && (text.equals("") == false)) {

            query.append(" AND LOWER(makhachhang) LIKE '%").append(text.toLowerCase()).append("%'");
        }
        if (check == 3 && (text.equals("") == false)) {

            query.append(" AND LOWER(manhanvien) LIKE '%").append(text.toLowerCase()).append("%'");
        }
    }
    
    public void sqlDate(StringBuilder query, String dateFrom, String dateTo) {
        try {
            if (dateFrom.equals("null") && (dateTo.equals("null") == false)) {
                query.append(" AND ngaylap <= to_date('").append(dateTo).append("','DD-MM-YYYY')");
            }
            else if (dateTo.equals("null")&& (dateFrom.equals("null") == false)) {
                query.append(" AND ngaylap >=  to_date('").append(dateFrom).append("','DD-MM-YYYY')");
            }
            else if(dateTo.equals("null")&& dateFrom.equals("null")){
                return;
            }
            else{
                query.append(" AND (ngaylap BETWEEN to_date('").append(dateFrom).append("','DD-MM-YYYY')").append(" and to_date('").append(dateTo).append("','DD-MM-YYYY'))");
            }
        } catch (Exception e) {
            System.out.println("load date1");
            System.out.println(dateFrom);
            System.out.println(dateTo);
        }
    }
     public ArrayList<hoaDonPojo> layDSHD2(String m) {
        ArrayList<hoaDonPojo> listKH = new ArrayList<hoaDonPojo>();
        try {

            String sql = "select * from qldata.hoadon where mahoadon = '"+m+"'";
            ResultSet rs = Database.excuteQuery(sql);
            while (rs.next()) {
                hoaDonPojo pn = new hoaDonPojo();
                pn.setMaHD(rs.getString("MAHOADON"));
                pn.setMaKH(rs.getString("MAKHACHHANG"));
                pn.setMaNhanVien(rs.getString("MANHANVIEN"));
                pn.setNgayLap(rs.getTimestamp("NGAYLAP"));
                pn.setTongTien(rs.getDouble("TONGTIEN"));
                listKH.add(pn);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listKH;
    }
     public hoaDonPojo selectById(String t) {
        hoaDonPojo ketQua = null;
        try {
            
            String sql = "SELECT * FROM qldata.hoadon WHERE mahoadon=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maPhieu = rs.getString("MAHOADON");
                String khachHang = rs.getString("MAKHACHHANG");
                String kh;
                if(khachHang != null){
                    kh = rs.getString("MAKHACHHANG");
                }else{
                    kh = "Khách vãng lai";
                }
                String maNhanVien = rs.getString("MANHANVIEN");
                Timestamp thoiGianTao = rs.getTimestamp("NGAYLAP");
                double tongTien = rs.getDouble("TONGTIEN");
                ketQua = new hoaDonPojo(maPhieu,kh,maNhanVien, thoiGianTao, chiTietHoaDonDao.getInstance().layDSCTHD(maPhieu), tongTien);
                
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }
}
