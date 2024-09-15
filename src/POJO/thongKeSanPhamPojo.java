/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author Admin
 */
public class thongKeSanPhamPojo {

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
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

    public int getSlnhap() {
        return slnhap;
    }

    public void setSlnhap(int slnhap) {
        this.slnhap = slnhap;
    }

    public int getSlxuat() {
        return slxuat;
    }

    public void setSlxuat(int slxuat) {
        this.slxuat = slxuat;
    }

    public thongKeSanPhamPojo(String maSanPham, String Code, String tenSanPham, String tenKichThuoc, String tenMauSac, int slnhap, int slhientai) {
        this.maSanPham = maSanPham;
        this.Code = Code;
        this.tenSanPham = tenSanPham;
        this.tenKichThuoc = tenKichThuoc;
        this.tenMauSac = tenMauSac;
        this.slnhap = slnhap;
        this.slhientai = slhientai;
    }

   

    public thongKeSanPhamPojo(String maSanPham, String Code, String tenSanPham, String tenKichThuoc, String tenMauSac, int slnhap, int slxuat, int slhientai) {
        this.maSanPham = maSanPham;
        this.Code = Code;
        this.tenSanPham = tenSanPham;
        this.tenKichThuoc = tenKichThuoc;
        this.tenMauSac = tenMauSac;
        this.slnhap = slnhap;
        this.slxuat = slxuat;
        this.slhientai = slhientai;
    }

    
   

    

    public thongKeSanPhamPojo() {
    }

    public thongKeSanPhamPojo(String Code, String tenSanPham, int slhientai) {
        this.Code = Code;
        this.tenSanPham = tenSanPham;
        this.slhientai = slhientai;
    }
    String maSanPham,Code,tenSanPham,tenKichThuoc,tenMauSac;
    int slnhap,slxuat;

    public int getSlhientai() {
        return slhientai;
    }

    public void setSlhientai(int slhientai) {
        this.slhientai = slhientai;
    }
    int slhientai;
}
