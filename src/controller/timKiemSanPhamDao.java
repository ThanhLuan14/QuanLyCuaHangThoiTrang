/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.sanPhamDao;
import POJO.sanPhamPojo;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class timKiemSanPhamDao {
    public static timKiemSanPhamDao getInstance() {
        return new timKiemSanPhamDao();
    }
    public sanPhamPojo searchId(String text) {
        sanPhamPojo result = new sanPhamPojo();
        ArrayList<sanPhamPojo> armt = sanPhamDao.getInstance().layDSSP1();
        for (var mt : armt) {
            if (mt.getMaSanPham().toLowerCase().contains(text.toLowerCase())) {
                return mt;
            }
        }
        return null;
    }
}
