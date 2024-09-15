/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.chiTietHoaDonPojo;
import POJO.phieuNhapPojo;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author AnhDoan
 */
public class phieuNhapDao {
    public ArrayList<phieuNhapPojo> layDSPN(){
         ArrayList<phieuNhapPojo> listPN = new ArrayList<phieuNhapPojo>();
        try {
            
            String sql = "SELECT * FROM qldata.PhieuNhap";
 
            ResultSet rs = Database.excuteQuery(sql);

            while (rs.next()) {
                phieuNhapPojo pn = new phieuNhapPojo();
                pn.setMaPhieu(rs.getString("MAPHIEUNHAP"));
                pn.setMaNCC(rs.getString("MANHACUNGCAP"));
                pn.setMaNV(rs.getString("MANHANVIEN"));
                pn.setNgayNhap(rs.getString("NGAYNHAP")); 
                listPN.add(pn);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listPN;
    }
    
    public ArrayList<phieuNhapPojo> layDSPN1(int check, String text,String dateFrom, String dateTo){
         ArrayList<phieuNhapPojo> listPN = new ArrayList<phieuNhapPojo>();
        try {
            
            var query = new StringBuilder("SELECT * FROM qldata.PHIEUNHAP PN JOIN qldata.NHACUNGCAP NCC ON PN.MANHACUNGCAP = NCC.MANHACUNGCAP JOIN qldata.NHANVIEN NV ON PN.MANHANVIEN = NV.MANHANVIEN where 1 = 1 ");
            sqlSearch(check, query, text);
            sqlDate(query, dateFrom,dateTo);
            query.append(" order by NGAYNHAP desc");
            ResultSet rs = Database.excuteQuery(query.toString());
            
            while (rs.next()) {
                phieuNhapPojo pn = new phieuNhapPojo();
                pn.setMaPhieu(rs.getString("MAPHIEUNHAP"));
                pn.setTenNCC(rs.getString("TENNHACUNGCAP"));
                pn.setTenNV(rs.getString("HOTEN"));
                pn.setNgayNhap(rs.getString("NGAYNHAP")); 
                listPN.add(pn);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listPN;
    }
    
    
    public void sqlSearch(int check, StringBuilder query, String text) {
        if (check == 1 && (text.equals("") == false)) {
            query.append(" AND LOWER(hoten) LIKE '%").append(text.toLowerCase()).append("%'");
        }
        if (check == 2 && (text.equals("") == false)) {

            query.append(" AND LOWER(tennhacungcap) LIKE '%").append(text.toLowerCase()).append("%'");
        }
        if (check == 3 && (text.equals("") == false)) {

            query.append(" AND LOWER(maphieunhap) LIKE '%").append(text.toLowerCase()).append("%'");
        }
    }
    
    public void sqlDate(StringBuilder query, String dateFrom, String dateTo) {
        try {
            if (dateFrom.equals("null") && (dateTo.equals("null") == false)) {
                query.append(" AND ngaynhap <= '").append(dateTo).append("'");
            }
            else if (dateTo.equals("null")&& (dateFrom.equals("null") == false)) {
                query.append(" AND ngaynhap >= '").append(dateFrom).append("'");
            }
            else if(dateTo.equals("null")&& dateFrom.equals("null")){
                return;
            }
            else{
                query.append(" AND (ngaynhap BETWEEN '").append(dateFrom).append("'").append(" and '").append(dateTo).append("')");
            }
        } catch (Exception e) {
        }
    }
}
