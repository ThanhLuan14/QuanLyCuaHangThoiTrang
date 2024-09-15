/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

import java.util.Objects;

/**
 *
 * @author Admin
 */
public class chiTietHoaDonPojo {

    public chiTietHoaDonPojo(String maHD, String maSanPham, int soLuong, double donGia) {
        this.maHD = maHD;
        this.maSanPham = maSanPham;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
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
    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final chiTietHoaDonPojo other = (chiTietHoaDonPojo) obj;
        if (this.soLuong != other.soLuong) {
            return false;
        }
        if (Double.doubleToLongBits(this.donGia) != Double.doubleToLongBits(other.donGia)) {
            return false;
        }
        if (!Objects.equals(this.maHD, other.maHD)) {
            return false;
        }
        return Objects.equals(this.maSanPham, other.maSanPham);
    }

    public chiTietHoaDonPojo() {
    }
    private String maHD;
    private String maSanPham;
    private int soLuong;
    private double donGia;

    public chiTietHoaDonPojo(String maHD, String code, String tenSanPham, int soLuong, double donGia) {
        this.maHD = maHD;
        this.code = code;
        this.tenSanPham = tenSanPham;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }
    private String code;
    private String tenSanPham;
}
