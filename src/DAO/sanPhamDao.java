/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.sanPhamPojo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import static DAO.Database.conn;
/**
 *
 * @author Admin
 */
public class sanPhamDao implements DAOInterface<sanPhamPojo>{
    public static sanPhamDao getInstance() {
        return new sanPhamDao();
    }
    public ArrayList<sanPhamPojo> layDSSP(int checkSQL, String text, String loaisp, String mausac, String kichthuoc) {
        ArrayList<sanPhamPojo> dsSanPham = new ArrayList<>();
        var query = new StringBuilder("select sp.masanpham,concat(nsp.manhomsanpham,sp.mathuoctinh)AS Code,nsp.tensanpham,sp.giaban,sp.soluongton,lsp.tenloaisanpham,ms.tenmausac,kt.tenkichthuoc from qldata.sanpham sp join qldata.nhomsanpham nsp on nsp.manhomsanpham = sp.manhomsanpham join qldata.loaisanpham lsp on lsp.maloaisanpham = nsp.maloaisanpham join qldata.mausac ms on sp.mamausac = ms.mamausac join qldata.kichthuoc kt on kt.makichthuoc = sp.makichthuoc where sp.trangthai = 1");
        sqlSearch(checkSQL, query, text);
        sqlCbo(query, loaisp, mausac, kichthuoc);
        ResultSet rs = Database.excuteQuery(query.toString());
        try {
            while (rs.next()) {
                sanPhamPojo sanPham = new sanPhamPojo();
                sanPham.setMaSanPham(rs.getString("MASANPHAM"));
                sanPham.setTenSanPham(rs.getString("TENSANPHAM"));
                sanPham.setGiaBan(rs.getDouble("GIABAN"));
                sanPham.setSoLuongTon(rs.getInt("SOLUONGTON"));
                sanPham.setTenLoaiSanPham(rs.getString("TENLOAISANPHAM"));
                sanPham.setTenMauSac(rs.getString("TENMAUSAC"));
                sanPham.setTenKichThuoc(rs.getString("TENKICHTHUOC"));
                sanPham.setCode(rs.getString("CODE"));
                dsSanPham.add(sanPham);
            }
        } catch (Exception e) {
        }

        return dsSanPham;
    }

    public void sqlSearch(int check, StringBuilder query, String text) {
        if (check == 1 && (text.equals("") == false)) {
            query.append(" AND LOWER(tensanpham) LIKE '%").append(text.toLowerCase()).append("%'");
        }
        if (check == 2 && (text.equals("") == false)) {

            query.append(" AND LOWER(concat(nsp.manhomsanpham,sp.mathuoctinh)) LIKE '%").append(text.toLowerCase()).append("%'");
        }
    }

    public void sqlCbo(StringBuilder query, String loaisp, String mausac, String kichthuoc) {
        try {
            if (loaisp.equals("Tất cả") == false) {
                query.append(" AND TENLOAISANPHAM = '").append(loaisp).append("'");
            }
            if (mausac.equals("Tất cả") == false) {
                query.append(" AND TENMAUSAC = '").append(mausac).append("'");
            }
            if ((kichthuoc.equals("Tất cả") == false)) {
                query.append(" AND TENKICHTHUOC = '").append(kichthuoc).append("'");
            }
        } catch (Exception e) {
        }
    }
    
    public ArrayList<sanPhamPojo> layMNhomSP() {
        ArrayList<sanPhamPojo> dsSanPham = new ArrayList<>();
        String query ="select * from qldata.nhomsanpham";
        ResultSet rs = Database.excuteQuery(query);
        try {
            while (rs.next()) {
                sanPhamPojo sanPham = new sanPhamPojo();
                sanPham.setMaNhomSP(rs.getString("MANHOMSANPHAM"));
                sanPham.setTenNhomSanPham(rs.getString("TENSANPHAM"));
                dsSanPham.add(sanPham);
            }
        } catch (Exception e) {
        }

        return dsSanPham;
    }
    
    public ArrayList<sanPhamPojo> layDSSP1() {
        ArrayList<sanPhamPojo> dsSanPham = new ArrayList<>();
        String query ="select sp.masanpham,concat(nsp.manhomsanpham,sp.mathuoctinh)AS Code,nsp.tensanpham,sp.giaban,sp.soluongton,lsp.tenloaisanpham,ms.tenmausac,kt.tenkichthuoc from qldata.sanpham sp join qldata.nhomsanpham nsp on nsp.manhomsanpham = sp.manhomsanpham join qldata.loaisanpham lsp on lsp.maloaisanpham = nsp.maloaisanpham join qldata.mausac ms on sp.mamausac = ms.mamausac join qldata.kichthuoc kt on kt.makichthuoc = sp.makichthuoc where sp.trangthai = 1";
        ResultSet rs = Database.excuteQuery(query);
        try {
            while (rs.next()) {
                sanPhamPojo sanPham = new sanPhamPojo();
                sanPham.setMaSanPham(rs.getString("MASANPHAM"));
                sanPham.setTenSanPham(rs.getString("TENSANPHAM"));
                sanPham.setGiaBan(rs.getDouble("GIABAN"));
                sanPham.setSoLuongTon(rs.getInt("SOLUONGTON"));
                sanPham.setTenLoaiSanPham(rs.getString("TENLOAISANPHAM"));
                sanPham.setTenMauSac(rs.getString("TENMAUSAC"));
                sanPham.setTenKichThuoc(rs.getString("TENKICHTHUOC"));
                sanPham.setCode(rs.getString("CODE"));
                dsSanPham.add(sanPham);
            }
        } catch (Exception e) {
        }

        return dsSanPham;
    }
 
    public int updateSoLuong(String maSP, int soluong) {
        int ketQua = 0;
        try {
            String sql = "UPDATE qldata.sanpham SET soluongton= ? WHERE masanpham= ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, soluong);
            pst.setString(2, maSP);
            ketQua = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("loi update soluong");
        }
        return ketQua;
    }

    @Override
    public ArrayList<sanPhamPojo> selectAll() {
        ArrayList<sanPhamPojo> ds = new ArrayList<sanPhamPojo>();
        try{
        String query ="select sp.masanpham,concat(nsp.manhomsanpham,sp.mathuoctinh)AS Code,nsp.tensanpham,sp.giaban,sp.soluongton,lsp.tenloaisanpham,ms.tenmausac,kt.tenkichthuoc from qldata.sanpham sp join qldata.nhomsanpham nsp on nsp.manhomsanpham = sp.manhomsanpham join qldata.loaisanpham lsp on lsp.maloaisanpham = nsp.maloaisanpham join qldata.mausac ms on sp.mamausac = ms.mamausac join qldata.kichthuoc kt on kt.makichthuoc = sp.makichthuoc  where sp.masanpham=?";
        PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maSP = rs.getString("MASANPHAM");
                String tenSP = rs.getString("TENSANPHAM");
                double giban = rs.getDouble("GIABAN");
                int soLuong = rs.getInt("SOLUONGTON");
                String tenLoaiSp = rs.getString("TENLOAISANPHAM");
                String tenMauSac = rs.getString("TENMAUSAC");
                String kichThuoc = rs.getString("TENKICHTHUOC");

                sanPhamPojo mt = new sanPhamPojo(maSP, tenSP, giban, soLuong, tenLoaiSp, tenMauSac, kichThuoc);
                ds.add(mt);
            }
        } catch (Exception e) {
        }
        return ds;
    }

    @Override
    public sanPhamPojo layDSSP1(String t) {
        sanPhamPojo ds = null;
        try{
            String query ="select sp.masanpham,concat(nsp.manhomsanpham,sp.mathuoctinh)AS Code,nsp.tensanpham,sp.giaban,sp.soluongton,lsp.tenloaisanpham,ms.tenmausac,kt.tenkichthuoc from qldata.sanpham sp join qldata.nhomsanpham nsp on nsp.manhomsanpham = sp.manhomsanpham join qldata.loaisanpham lsp on lsp.maloaisanpham = nsp.maloaisanpham join qldata.mausac ms on sp.mamausac = ms.mamausac join qldata.kichthuoc kt on kt.makichthuoc = sp.makichthuoc where sp.masanpham=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maSP = rs.getString("Code");
                String tenSP = rs.getString("TENSANPHAM");
                double giban = rs.getDouble("GIABAN");
                int soLuong = rs.getInt("SOLUONGTON");
                String tenLoaiSp = rs.getString("TENLOAISANPHAM");
                String tenMauSac = rs.getString("TENMAUSAC");
                String kichThuoc = rs.getString("TENKICHTHUOC");
                ds = new sanPhamPojo(maSP, tenSP, giban, soLuong, tenLoaiSp, tenMauSac, kichThuoc);
            }
        } catch (Exception e) {
            System.out.println("loi lay dssp1");
        }
        return ds;
    }

    @Override
    public int insert(sanPhamPojo t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

 
