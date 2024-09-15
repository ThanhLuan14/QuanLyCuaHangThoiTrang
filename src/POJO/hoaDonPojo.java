/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Admin
 */
public class hoaDonPojo {

    public hoaDonPojo(String maHD, String maKH, String maNhanVien, Timestamp ngayLap, ArrayList<chiTietHoaDonPojo> CTPhieu, double tongTien) {
        this.maHD = maHD;
        this.maKH = maKH;
        this.maNhanVien = maNhanVien;
        this.ngayLap = ngayLap;
        this.CTPhieu = CTPhieu;
        this.tongTien = tongTien;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public Timestamp getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Timestamp ngayLap) {
        this.ngayLap = ngayLap;
    }

    public ArrayList<chiTietHoaDonPojo> getCTPhieu() {
        return CTPhieu;
    }

    public void setCTPhieu(ArrayList<chiTietHoaDonPojo> CTPhieu) {
        this.CTPhieu = CTPhieu;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }
    @Override
    public int hashCode() {
        int hash = 7;
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
        final hoaDonPojo other = (hoaDonPojo) obj;
        if (Double.doubleToLongBits(this.tongTien) != Double.doubleToLongBits(other.tongTien)) {
            return false;
        }
        if (!Objects.equals(this.maHD, other.maHD)) {
            return false;
        }
        if (!Objects.equals(this.maNhanVien, other.maNhanVien)) {
            return false;
        }
        if (!Objects.equals(this.ngayLap, other.ngayLap)) {
            return false;
        }
        return Objects.equals(this.CTPhieu, other.CTPhieu);
    }

    public hoaDonPojo() {
    }

    public hoaDonPojo(String maHD, String maKH, String maNhanVien, Timestamp ngayLap, double tongTien) {
        this.maHD = maHD;
        this.maKH = maKH;
        this.maNhanVien = maNhanVien;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
    }

    public hoaDonPojo(String maHD, String maNhanVien, Timestamp ngayLap, ArrayList<chiTietHoaDonPojo> CTPhieu, double tongTien) {
        this.maHD = maHD;
        this.maNhanVien = maNhanVien;
        this.ngayLap = ngayLap;
        this.CTPhieu = CTPhieu;
        this.tongTien = tongTien;
    }
    private String maHD;
    private String maKH;
    private String maNhanVien;
    private Timestamp ngayLap;
    private ArrayList<chiTietHoaDonPojo> CTPhieu;
    private double tongTien;
}
