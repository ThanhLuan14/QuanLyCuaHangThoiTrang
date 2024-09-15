/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author AnhDoan
 */
public class cboItem {

    public String getMa() {
        return ma;
    }

    public String getTen() {
        return ten;
    }

    public cboItem(String ma, String ten) {
        this.ma = ma;
        this.ten = ten;
    }

    @Override
    public String toString() {
        return (ma + " - " + ten);
    }
    private String ma,ten;
}
