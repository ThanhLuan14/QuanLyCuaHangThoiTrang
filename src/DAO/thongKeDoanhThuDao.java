/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import static DAO.Database.conn;
import POJO.thongKeDoanhThuPojo;
import POJO.thongKeSanPhamPojo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class thongKeDoanhThuDao {
    public static thongKeDoanhThuDao getInstance() {
        return new thongKeDoanhThuDao();
    }
    public ArrayList<thongKeDoanhThuPojo> getThongKe() {
        ArrayList<thongKeDoanhThuPojo> ketQua = new ArrayList<thongKeDoanhThuPojo>();
        try {
            
            String sql = "select t2.mahoadon,concat(t3.manhomsanpham,t3.mathuoctinh) as code,t3.tensanpham,t3.tenkichthuoc,t3.tenmausac,t2.dongia,t2.soluong,t1.gianhap,t2.ngaylap\n" +
                        "from (select DISTINCT masanpham , gianhap from qldata.chitietphieunhap ctpn\n" +
                        "join qldata.PhieuNhap pn on pn.maphieunhap = ctpn.maphieunhap) t1\n" +
                        "join(\n" +
                        "select cthd.mahoadon,cthd.masanpham,cthd.soluong,cthd.dongia,hd.ngaylap from qldata.ChiTietHoaDon cthd\n" +
                        "join qldata.HoaDon hd on hd.MaHoaDon = cthd.MaHoaDon) t2\n" +
                        "on t1.masanpham = t2.masanpham\n" +
                        "join (select tensanpham, masanpham,mathuoctinh,nsp.manhomsanpham,kt.tenkichthuoc,ms.tenmausac\n" +
                        "from qldata.sanpham sp\n" +
                        "join qldata.nhomsanpham nsp on nsp.manhomsanpham = sp.manhomsanpham\n" +
                        "join qldata.kichthuoc kt on kt.makichthuoc = sp.makichthuoc\n" +
                        "join qldata.mausac ms  on ms.mamausac =sp.mamausac) t3\n" +
                        "on t1.masanpham  = t3.Masanpham\n"+
                        "order by ngaylap desc";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maHoaDon = rs.getString("MAHOADON");
                String code = rs.getString("CODE");
                String tenSanPham = rs.getString("TENSANPHAM");
                String tenKichThuoc = rs.getString("TENKICHTHUOC");
                String tenMausac = rs.getString("TENMAUSAC");
                double donGia = rs.getDouble("DONGIA");
                int soLuong = rs.getInt("SOLUONG");
                double giaNhap = rs.getDouble("GIANHAP");
                Timestamp ngayLap = rs.getTimestamp("NGAYLAP");
                thongKeDoanhThuPojo p = new thongKeDoanhThuPojo(maHoaDon, code, tenSanPham, tenKichThuoc,tenMausac,soLuong,donGia,giaNhap,ngayLap);
                ketQua.add(p);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }
     public ArrayList<thongKeDoanhThuPojo> getThongKe(String timeStart, String timeEnd) {
        System.out.println(timeStart);
        System.out.println(timeEnd);

        ArrayList<thongKeDoanhThuPojo> ketQua = new ArrayList<thongKeDoanhThuPojo>();
        try {
            
            String sql = "select t2.mahoadon,concat(t3.manhomsanpham,t3.mathuoctinh) as code,t3.tensanpham,t3.tenkichthuoc,t3.tenmausac,t2.dongia,t2.soluong,t1.gianhap,t2.ngaylap\n" +
                        "from (select DISTINCT masanpham , gianhap from qldata.chitietphieunhap ctpn\n" +
                        "join qldata.PhieuNhap pn on pn.maphieunhap = ctpn.maphieunhap\n" +
                        "where pn.ngaynhap <= to_date(?,'DD-MM-YYYY')) t1\n" +
                        "join(\n" +
                        "select cthd.mahoadon,cthd.masanpham,cthd.soluong,cthd.dongia,hd.ngaylap from qldata.ChiTietHoaDon cthd\n" +
                        "join qldata.HoaDon hd on hd.MaHoaDon = cthd.MaHoaDon\n" +
                        "where hd.ngaylap between to_date(?,'DD-MM-YYYY') and to_date(?,'DD-MM-YYYY')) t2\n" +
                        "on t1.masanpham = t2.masanpham\n" +
                        "join (select tensanpham, masanpham,mathuoctinh,nsp.manhomsanpham,kt.tenkichthuoc,ms.tenmausac\n" +
                        "from qldata.sanpham sp\n" +
                        "join qldata.nhomsanpham nsp on nsp.manhomsanpham = sp.manhomsanpham\n" +
                        "join qldata.kichthuoc kt on kt.makichthuoc = sp.makichthuoc\n" +
                        "join qldata.mausac ms  on ms.mamausac =sp.mamausac) t3\n" +
                        "on t1.masanpham  = t3.Masanpham";
            PreparedStatement pst = conn.prepareStatement(sql);
//            pst.setString(1, timeStart);
            pst.setString(1, timeEnd);
            pst.setString(2, timeStart);
            pst.setString(3, timeEnd);
            

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
               String maHoaDon = rs.getString("MAHOADON");
                String code = rs.getString("CODE");
                String tenSanPham = rs.getString("TENSANPHAM");
                String tenKichThuoc = rs.getString("TENKICHTHUOC");
                String tenMausac = rs.getString("TENMAUSAC");
                double donGia = rs.getDouble("DONGIA");
                int soLuong = rs.getInt("SOLUONG");
                double giaNhap = rs.getDouble("GIANHAP");
                Timestamp ngayLap = rs.getTimestamp("NGAYLAP");
                thongKeDoanhThuPojo p = new thongKeDoanhThuPojo(maHoaDon, code, tenSanPham, tenKichThuoc,tenMausac,soLuong,donGia,giaNhap,ngayLap);
                ketQua.add(p);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }
}
