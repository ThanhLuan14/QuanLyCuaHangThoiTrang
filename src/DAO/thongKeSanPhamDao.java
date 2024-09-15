/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.hoaDonPojo;
import POJO.thongKeSanPhamPojo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import static DAO.Database.conn;


/**
 *
 * @author Admin
 */
public class thongKeSanPhamDao {
    
    public static thongKeSanPhamDao getInstance() {
        return new thongKeSanPhamDao();
    }
   
    public ArrayList<thongKeSanPhamPojo> getThongKe(String timeStart, String timeEnd) {
        System.out.println(timeStart);
        System.out.println(timeEnd);

        ArrayList<thongKeSanPhamPojo> ketQua = new ArrayList<thongKeSanPhamPojo>();
        try {
            
            String sql = "select t1.masanpham,concat(t3.manhomsanpham,t3.mathuoctinh) as code,t3.tensanpham,t3.tenkichthuoc,t3.tenmausac,slnhap,slxuat,t3.soluongton \n" +
                        "from (select masanpham,sum(soluong) as slnhap from qldata.ChiTietPhieuNhap ctpn\n" +
                        "join qldata.PhieuNhap pn on pn.maphieunhap = ctpn.maphieunhap\n" +
                        "where pn.ngaynhap between to_date(?,'DD-MM-YYYY') and to_date(?,'DD-MM-YYYY')\n" +
                        "group by masanpham) t1\n" +
                        "join(\n" +
                        "select masanpham,sum(soluong) as slxuat from qldata.ChiTietHoaDon cthd\n" +
                        "join qldata.HoaDon hd on hd.MaHoaDon = cthd.MaHoaDon\n" +
                        "where hd.ngaylap between to_date(?,'DD-MM-YYYY') and to_date(?,'DD-MM-YYYY')\n" +
                        "group by masanpham) t2\n" +
                        "on t1.masanpham = t2.masanpham\n" +
                        "join (select tensanpham,soluongton, masanpham,mathuoctinh,nsp.manhomsanpham,kt.tenkichthuoc,ms.tenmausac\n" +
                        "from qldata.sanpham sp\n" +
                        "join qldata.nhomsanpham nsp on nsp.manhomsanpham = sp.manhomsanpham\n" +
                        "join qldata.kichthuoc kt on kt.makichthuoc = sp.makichthuoc\n" +
                        "join qldata.mausac ms  on ms.mamausac =sp.mamausac) t3\n" +
                        "on t1.masanpham  = t3.Masanpham\n"+
                        "order by slxuat DESC";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, timeStart);
            pst.setString(2, timeEnd);
            pst.setString(3, timeStart);
            pst.setString(4, timeEnd);
            

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maSanPham = rs.getString("MASANPHAM");
                String code = rs.getString("CODE");
                String tenSanPham = rs.getString("TENSANPHAM");
                String tenKichThuoc = rs.getString("TENKICHTHUOC");
                String tenMausac = rs.getString("TENMAUSAC");
                int slNhap = rs.getInt("SLNHAP");
                int slXuat = rs.getInt("SLXUAT");
                int slhientai = rs.getInt("SOLUONGTON");
                thongKeSanPhamPojo p = new thongKeSanPhamPojo(maSanPham, code, tenSanPham, tenKichThuoc,tenMausac,slNhap,slXuat,slhientai);
                ketQua.add(p);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }
     public ArrayList<thongKeSanPhamPojo> getThongKe() {
        ArrayList<thongKeSanPhamPojo> ketQua = new ArrayList<thongKeSanPhamPojo>();
        try {
            
            String sql = "select t1.masanpham,concat(t3.manhomsanpham,t3.mathuoctinh) as code,t3.tensanpham,t3.tenkichthuoc,t3.tenmausac,slnhap,slxuat,t3.soluongton \n" +
                            "from (select masanpham,sum(soluong) as slnhap from qldata.ChiTietPhieuNhap ctpn\n" +
                            "join qldata.PhieuNhap pn on pn.maphieunhap = ctpn.maphieunhap\n" +
                            "group by masanpham) t1\n" +
                            "join(\n" +
                            "select masanpham,sum(soluong) as slxuat from qldata.ChiTietHoaDon cthd\n" +
                            "join qldata.HoaDon hd on hd.MaHoaDon = cthd.MaHoaDon\n" +
                            "group by masanpham) t2\n" +
                            "on t1.masanpham = t2.masanpham\n" +
                            "join (select tensanpham,soluongton, masanpham,mathuoctinh,nsp.manhomsanpham,kt.tenkichthuoc,ms.tenmausac\n" +
                            "from qldata.sanpham sp\n" +
                            "join qldata.nhomsanpham nsp on nsp.manhomsanpham = sp.manhomsanpham\n" +
                            "join qldata.kichthuoc kt on kt.makichthuoc = sp.makichthuoc\n" +
                            "join qldata.mausac ms  on ms.mamausac =sp.mamausac) t3\n" +
                            "on t1.masanpham  = t3.Masanpham\n" +
                            "order by slxuat DESC";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maSanPham = rs.getString("MASANPHAM");
                String code = rs.getString("CODE");
                String tenSanPham = rs.getString("TENSANPHAM");
                String tenKichThuoc = rs.getString("TENKICHTHUOC");
                String tenMausac = rs.getString("TENMAUSAC");
                int slNhap = rs.getInt("SLNHAP");
                int slXuat = rs.getInt("SLXUAT");
                int slhientai = rs.getInt("SOLUONGTON");
                thongKeSanPhamPojo p = new thongKeSanPhamPojo(maSanPham, code, tenSanPham, tenKichThuoc,tenMausac,slNhap,slXuat,slhientai);
                ketQua.add(p);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }
      public ArrayList<thongKeSanPhamPojo> getThongKeSlTon() {
        ArrayList<thongKeSanPhamPojo> ketQua = new ArrayList<thongKeSanPhamPojo>();
        try {
            
            String sql = "select t1.masanpham,concat(t3.manhomsanpham,t3.mathuoctinh) as code,t3.tensanpham,t3.tenkichthuoc,t3.tenmausac,slnhap,t3.soluongton \n" +
                            "from (select masanpham,sum(soluong) as slnhap from qldata.ChiTietPhieuNhap ctpn\n" +
                            "join qldata.PhieuNhap pn on pn.maphieunhap = ctpn.maphieunhap\n" +
                            "group by masanpham) t1\n" +                       
                            "join (select tensanpham,soluongton, masanpham,mathuoctinh,nsp.manhomsanpham,kt.tenkichthuoc,ms.tenmausac\n" +
                            "from qldata.sanpham sp\n" +
                            "join qldata.nhomsanpham nsp on nsp.manhomsanpham = sp.manhomsanpham\n" +
                            "join qldata.kichthuoc kt on kt.makichthuoc = sp.makichthuoc\n" +
                            "join qldata.mausac ms  on ms.mamausac =sp.mamausac) t3\n" +
                            "on t1.masanpham  = t3.Masanpham\n" +
                            "order by soluongton ASC";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maSanPham = rs.getString("MASANPHAM");
                String code = rs.getString("CODE");
                String tenSanPham = rs.getString("TENSANPHAM");
                String tenKichThuoc = rs.getString("TENKICHTHUOC");
                String tenMausac = rs.getString("TENMAUSAC");
                int slNhap = rs.getInt("SLNHAP");
            
                int slhientai = rs.getInt("SOLUONGTON");
                thongKeSanPhamPojo p = new thongKeSanPhamPojo(maSanPham, code, tenSanPham, tenKichThuoc, tenMausac, slNhap, slhientai);
                ketQua.add(p);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }
     public ArrayList<thongKeSanPhamPojo> getThongKeSlTon1() {
        ArrayList<thongKeSanPhamPojo> ketQua = new ArrayList<thongKeSanPhamPojo>();
        try {
            
            String sql = "select t1.masanpham,concat(t3.manhomsanpham,t3.mathuoctinh) as code,t3.tensanpham,t3.tenkichthuoc,t3.tenmausac,slnhap,t3.soluongton \n" +
                            "from (select masanpham,sum(soluong) as slnhap from qldata.ChiTietPhieuNhap ctpn\n" +
                            "join qldata.PhieuNhap pn on pn.maphieunhap = ctpn.maphieunhap\n" +
                            "group by masanpham) t1\n" +
                           
                            "join (select tensanpham,soluongton, masanpham,mathuoctinh,nsp.manhomsanpham,kt.tenkichthuoc,ms.tenmausac\n" +
                            "from qldata.sanpham sp\n" +
                            "join qldata.nhomsanpham nsp on nsp.manhomsanpham = sp.manhomsanpham\n" +
                            "join qldata.kichthuoc kt on kt.makichthuoc = sp.makichthuoc\n" +
                            "join qldata.mausac ms  on ms.mamausac =sp.mamausac) t3\n" +
                            "on t1.masanpham  = t3.Masanpham\n" +
                            "where soluongton < 10";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maSanPham = rs.getString("MASANPHAM");
                String code = rs.getString("CODE");
                String tenSanPham = rs.getString("TENSANPHAM");
                String tenKichThuoc = rs.getString("TENKICHTHUOC");
                String tenMausac = rs.getString("TENMAUSAC");
                int slNhap = rs.getInt("SLNHAP");
                
                int slhientai = rs.getInt("SOLUONGTON");
                thongKeSanPhamPojo p = new thongKeSanPhamPojo(maSanPham, code, tenSanPham, tenKichThuoc,tenMausac, slNhap, slhientai);
                ketQua.add(p);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }
  
}
