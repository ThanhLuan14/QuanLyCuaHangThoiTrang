/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.loaiSanPhamPojo;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class loaiSanPhamDao {

    public ArrayList<loaiSanPhamPojo> layDSLSP() {

        ArrayList<loaiSanPhamPojo> dsloaiSanPham = new ArrayList<>();
        String query = "select * from qldata.loaisanpham where trangthai = 1";
        ResultSet rs = Database.excuteQuery(query);
        try {
            while (rs.next()) {
                loaiSanPhamPojo loaiSanPham = new loaiSanPhamPojo();
                loaiSanPham.setMaLoaiSanPham(rs.getString("MALOAISANPHAM"));
                loaiSanPham.setTenLoaiSanPham(rs.getString("TENLOAISANPHAM"));
                dsloaiSanPham.add(loaiSanPham);
            }
        } catch (Exception e) {
        }

        return dsloaiSanPham;
    }
    
    public ArrayList<loaiSanPhamPojo> layMaLSP() {

        ArrayList<loaiSanPhamPojo> dsloaiSanPham = new ArrayList<>();
        String query = "select * from qldata.loaisanpham";
        ResultSet rs = Database.excuteQuery(query);
        try {
            while (rs.next()) {
                loaiSanPhamPojo loaiSanPham = new loaiSanPhamPojo();
                loaiSanPham.setMaLoaiSanPham(rs.getString("MALOAISANPHAM"));
                dsloaiSanPham.add(loaiSanPham);
            }
        } catch (Exception e) {
        }

        return dsloaiSanPham;
    }
}
