/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

import java.io.InputStream;
import java.util.Objects;

/**
 *
 * @author Admin
 */
public class sanPhamPojo {
    
    String tenLoaiSanPham,tenMauSac,tenKichThuoc;
    String maSanPham,tenSanPham;
    double giaBan;
    int soLuongTon;
    InputStream hinhAnhSP;
    String maNhomSP, tenNhomSanPham;
    String code;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getMaNhomSP() {
        return maNhomSP;
    }

    public void setMaNhomSP(String maNhomSP) {
        this.maNhomSP = maNhomSP;
    }

    public String getTenNhomSanPham() {
        return tenNhomSanPham;
    }

    public void setTenNhomSanPham(String tenNhomSanPham) {
        this.tenNhomSanPham = tenNhomSanPham;
    }
    
    public sanPhamPojo(String maSanPham, String tenSanPham, double giaBan, int soLuongTon, String tenLoaiSanPham, String tenMauSac, String tenKichThuoc) {
        this.tenLoaiSanPham = tenLoaiSanPham;
        this.tenMauSac = tenMauSac;
        this.tenKichThuoc = tenKichThuoc;
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.giaBan = giaBan;
        this.soLuongTon = soLuongTon;
      
    }

    public String getTenLoaiSanPham() {
        return tenLoaiSanPham;
    }

    public void setTenLoaiSanPham(String tenLoaiSanPham) {
        this.tenLoaiSanPham = tenLoaiSanPham;
    }

    public String getTenMauSac() {
        return tenMauSac;
    }

    public void setTenMauSac(String tenMauSac) {
        this.tenMauSac = tenMauSac;
    }

    public String getTenKichThuoc() {
        return tenKichThuoc;
    }

    public void setTenKichThuoc(String tenKichThuoc) {
        this.tenKichThuoc = tenKichThuoc;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public InputStream getHinhAnhSP() {
        return hinhAnhSP;
    }

    public void setHinhAnhSP(InputStream hinhAnhSP) {
        this.hinhAnhSP = hinhAnhSP;
    }

    public sanPhamPojo() {
    }
    
    @Override
    public int hashCode() {
        int hash = 3;

        hash = 37 * hash + this.soLuongTon;
   
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
        final sanPhamPojo other = (sanPhamPojo) obj;
        if (this.soLuongTon != other.soLuongTon) {
            return false;
        }
        if (!Objects.equals(this.tenLoaiSanPham, other.tenLoaiSanPham)) {
            return false;
        }
        if (!Objects.equals(this.tenMauSac, other.tenMauSac)) {
            return false;
        }
        if (!Objects.equals(this.tenKichThuoc, other.tenKichThuoc)) {
            return false;
        }
        if (!Objects.equals(this.maSanPham, other.maSanPham)) {
            return false;
        }
        if (!Objects.equals(this.tenSanPham, other.tenSanPham)) {
            return false;
        }
        if (!Objects.equals(this.giaBan, other.giaBan)) {
            return false;
        }
        return Objects.equals(this.hinhAnhSP, other.hinhAnhSP);
    }
    
    
}
