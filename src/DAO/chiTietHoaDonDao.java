/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.chiTietHoaDonPojo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import static DAO.Database.conn;

/**
 *
 * @author Admin
 */
public class chiTietHoaDonDao implements DAOInterface<chiTietHoaDonPojo> {
    
     public static chiTietHoaDonDao getInstance() {
        return new chiTietHoaDonDao();
    }
    public ArrayList<chiTietHoaDonPojo> layDSCTHD(String maHD){
         ArrayList<chiTietHoaDonPojo> ketQua = new ArrayList<chiTietHoaDonPojo>();
        try {
            
            String sql = "SELECT * FROM qldata.ChiTietHoaDOn WHERE MaHoaDon='"+maHD+"'";
 
            ResultSet rs = Database.excuteQuery(sql);

            while (rs.next()) {
                String maPhieu = rs.getString("MAHOADON");
                String maMay = rs.getString("MASANPHAM");
                int soLuong = rs.getInt("SOLUONG");
                double donGia = rs.getDouble("DONGIA");
                chiTietHoaDonPojo ctp = new chiTietHoaDonPojo(maPhieu, maMay, soLuong, donGia);
                ketQua.add(ctp);
            }
            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }
   public ArrayList<chiTietHoaDonPojo> layDSCTHD1(String maHD){
         ArrayList<chiTietHoaDonPojo> ketQua = new ArrayList<chiTietHoaDonPojo>();
        try {
            
            String sql = "select cthd.MAHOADON,concat(nsp.manhomsanpham,sp.mathuoctinh)AS Code,nsp.TENSANPHAM,cthd.SOLUONG,cthd.DONGIA\n" 
                        + "from qldata.sanpham sp join qldata.nhomsanpham nsp on nsp.manhomsanpham = sp.manhomsanpham \n" 
                        + "join qldata.loaisanpham lsp on lsp.maloaisanpham = nsp.maloaisanpham \n" 
                        + "join qldata.mausac ms on sp.mamausac = ms.mamausac \n" 
                        + "join qldata.kichthuoc kt on kt.makichthuoc = sp.makichthuoc\n" 
                        + "join qldata.chitiethoadon cthd on cthd.masanpham = sp.masanpham\n" 
                        + "where sp.trangthai = 1 and mahoadon = '"+maHD+"'";
 
            ResultSet rs = Database.excuteQuery(sql);

            while (rs.next()) {
                String maPhieu = rs.getString("MAHOADON");
                String maMay = rs.getString("CODE");
                String tenSP = rs.getString("TENSANPHAM");
                int soLuong = rs.getInt("SOLUONG");
                double donGia = rs.getDouble("DONGIA");
                chiTietHoaDonPojo ctp = new chiTietHoaDonPojo(maPhieu, maMay, tenSP, soLuong, donGia);
                ketQua.add(ctp);
            }
            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }
     @Override
    public int insert(chiTietHoaDonPojo t) {
        int ketQua = 0;
        try {
            String sql = "INSERT INTO qldata.ChiTietHoaDon (MAHOADON, MASANPHAM, SOLUONG, DONGIA) VALUES(?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, t.getMaHD());
            pst.setString(2, t.getMaSanPham());
            pst.setInt(3, t.getSoLuong());
            pst.setDouble(4, t.getDonGia());
            ketQua = pst.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public chiTietHoaDonPojo layDSSP1(String t) {
          chiTietHoaDonPojo ketQua = null;
        try {
            
            String sql = "SELECT * FROM qldata.ChiTietHoaDOn WHERE MaHoaDon=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maPhieu = rs.getString("MAHOADON");
                String maMay = rs.getString("MASANPHAM");
                int soLuong = rs.getInt("SOLUONG");
                double donGia = rs.getDouble("DONGIA");
                ketQua= new chiTietHoaDonPojo(maPhieu, maMay, soLuong, donGia);

            }
            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public ArrayList<chiTietHoaDonPojo> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
   
}
