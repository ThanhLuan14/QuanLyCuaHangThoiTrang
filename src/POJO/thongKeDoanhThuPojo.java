/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
public class thongKeDoanhThuPojo {
    String maHD,Code,tenSanPham,tenKichThuoc,tenMauSac;
    int soLuong;
    double donGia,giaNhap;

    public Timestamp getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Timestamp ngayLap) {
        this.ngayLap = ngayLap;
    }
    private Timestamp ngayLap;

    public thongKeDoanhThuPojo(String maHD, String Code, String tenSanPham, String tenKichThuoc, String tenMauSac, int soLuong, double donGia, double giaNhap, Timestamp ngayLap) {
        this.maHD = maHD;
        this.Code = Code;
        this.tenSanPham = tenSanPham;
        this.tenKichThuoc = tenKichThuoc;
        this.tenMauSac = tenMauSac;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.giaNhap = giaNhap;
        this.ngayLap = ngayLap;
    }

    public thongKeDoanhThuPojo() {
    }

    public thongKeDoanhThuPojo(String maHD, String Code, String tenSanPham, String tenKichThuoc, String tenMauSac, int soLuong, double donGia, double giaNhap) {
        this.maHD = maHD;
        this.Code = Code;
        this.tenSanPham = tenSanPham;
        this.tenKichThuoc = tenKichThuoc;
        this.tenMauSac = tenMauSac;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.giaNhap = giaNhap;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getTenKichThuoc() {
        return tenKichThuoc;
    }

    public void setTenKichThuoc(String tenKichThuoc) {
        this.tenKichThuoc = tenKichThuoc;
    }

    public String getTenMauSac() {
        return tenMauSac;
    }

    public void setTenMauSac(String tenMauSac) {
        this.tenMauSac = tenMauSac;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }
}
