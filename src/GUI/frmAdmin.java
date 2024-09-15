/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import DAO.Database;
import DAO.TableDao;
import DAO.nhanVienDao;
import DAO.roleDAO;
import DAO.thongKeDoanhThuDao;
import DAO.thongKeSanPhamDao;
import POJO.TablePojo;
import POJO.cboItem;
import POJO.nhanVienPojo;
import POJO.roleNamePojo;
import POJO.thongKeDoanhThuPojo;
import POJO.thongKeSanPhamPojo;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import table.TableCustom;
import textfield.SearchOptinEvent;
import textfield.SearchOption;

/**
 *
 * @author AnhDoan
 */
public class frmAdmin extends javax.swing.JFrame {
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/YYYY HH:mm");
    /**
     * Creates new form frmAdmin
     */
    public frmAdmin() {
        initComponents();
        loadQLNV(0, "");
        loadTextSearch();
        tableCustom1.apply(jScrollPane1, TableCustom.TableType.DEFAULT);
        init();
        loadList();
        loadTableName();
        loadUser();
        loadListNQ();
        loadRolesName();
        loadDateVN();
        loadTextSearch1();
        loadTextSearch2();
        tableCustom2.apply(jScrollPane8,TableCustom.TableType.DEFAULT);
        loadDataToTableThongKeProduct(thongKeSanPhamDao.getInstance().getThongKe());
        tableCustom3.apply(jScrollPane9,TableCustom.TableType.DEFAULT);
        loadDataToTableThongKeDoanhThu(thongKeDoanhThuDao.getInstance().getThongKe());
        tbNhanVien.setDefaultEditor(Object.class, null);
        tblFrmThongKeDoanhThu.setDefaultEditor(Object.class, null);
        tblThongKeSanPhamfrmAdmin.setDefaultEditor(Object.class, null);
        tableCustom4.apply(jScrollPane10,TableCustom.TableType.DEFAULT);
        loadDataToTableThongKeProductSlTon(thongKeSanPhamDao.getInstance().getThongKeSlTon());
        tbl_sanphamtonAdmin.setDefaultEditor(Object.class, null);
    }
    
    DefaultListModel modelNQTG;
    DefaultListModel modelNQ1;
    DefaultListModel modelNQ2;
    
    public void loadListNQ(){
        modelNQTG = new DefaultListModel();
        modelNQ1 = new DefaultListModel();
        modelNQ1.addElement("SELECT");
        modelNQ1.addElement("INSERT");
        modelNQ1.addElement("UPDATE");
        modelNQ1.addElement("DELETE");
        listDoiTuong1.setModel(modelNQ1);
        modelNQ2 = new DefaultListModel();
        modelNQ2.addElement("CREATE SESSION");
        modelNQ2.addElement("CREATE TABLE");
        modelNQ2.addElement("CREATE PROCEDURE");
        modelNQ2.addElement("CREATE TRIGGER");
        listHeThong1.setModel(modelNQ2);
    }
    public void loadRolesName(){
        cboRole.removeAllItems();
        cboRole1.removeAllItems();
        roleDAO role = new roleDAO();
        ArrayList<roleNamePojo> dsRole = role.loadRoles();
            for (roleNamePojo rl : dsRole) {
                cboRole.addItem(rl.getRoleName());
                cboRole1.addItem(rl.getRoleName());
            } 
    }
    
    
    DefaultListModel modelTG;
    DefaultListModel model1;
    DefaultListModel model2;
    public void loadList(){
        modelTG = new DefaultListModel();
        model1 = new DefaultListModel();
        model1.addElement("SELECT");
        model1.addElement("INSERT");
        model1.addElement("UPDATE");
        model1.addElement("DELETE");
        listDoiTuong.setModel(model1);
        model2 = new DefaultListModel();
        model2.addElement("CREATE SESSION");
        model2.addElement("CREATE TABLE");
        model2.addElement("CREATE PROCEDURE");
        model2.addElement("CREATE TRIGGER");
        listHeThong.setModel(model2);
    }

    private void init() {
        titleBar1.initJFram(this);
    }
    
    public void loadUser(){
        cboNV.removeAllItems();
        nhanVienDao userDao = new nhanVienDao();
        ArrayList<nhanVienPojo> dsNV = userDao.layDSNV1();
            for (nhanVienPojo user : dsNV) {
                cboNV.addItem(new cboItem(user.getMaNhanVien(), user.getHoTen()));
            } 
    }
    public void loadTableName(){
        cboTB.removeAllItems();
        TableDao tableDao = new TableDao();
        ArrayList<TablePojo> dsTable = tableDao.layDSTable();
            for (TablePojo tableName : dsTable) {
                cboTB.addItem(tableName.getTableName());
                cboTB1.addItem(tableName.getTableName());
            } 
    }

    public void loadTextSearch() {
        txtSearch.addEventOptionSelected(new SearchOptinEvent() {
            @Override
            public void optionSelected(SearchOption option, int index) {
                txtSearch.setHint("Nhập " + option.getName() + "...");
            }
        });
        txtSearch.addOption(new SearchOption("tên", new ImageIcon(getClass().getResource("/icons/user.png"))));
        txtSearch.addOption(new SearchOption("số điện thoại", new ImageIcon(getClass().getResource("/icons/tel.png"))));
        txtSearch.addOption(new SearchOption("email", new ImageIcon(getClass().getResource("/icons/email.png"))));
        txtSearch.addOption(new SearchOption("địa chỉ", new ImageIcon(getClass().getResource("/icons/address.png"))));
    }

    public void loadQLNV(int checkSQL, String text) {
        try {
            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(new String[]{"Mã nhân viên", "Tên nhân viên", "Giới tính", "Ngày sinh", "Địa chỉ", "Email", "Số điện thoại", "Chức vụ"});
            nhanVienDao nvDAO = new nhanVienDao();
            ArrayList<nhanVienPojo> dsNV = nvDAO.layDSNV(checkSQL, text);
            for (nhanVienPojo nv : dsNV) {
                String tenNV = nv.getHoTen();
                String maNV = nv.getMaNhanVien();
                String gioiTinh = nv.getGioiTinh();
                String ngayVL = nv.getNgaySinh();
                String diaChi = nv.getDiaChi();
                String luong = nv.getEmail();
                String sdt = nv.getSoDienThoai();
                String chucVu = nv.getChucVu();
                model.addRow(new Object[]{maNV, tenNV, gioiTinh, ngayVL, diaChi, luong, sdt, chucVu});
            }
            tbNhanVien.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String layMa(String textCbo) {
        String ma;
        String[] mang = textCbo.split(" - ");
        ma = mang[0];

        return ma;
    }
     public void loadTextSearch1() {
        textOptionfrmAdmin.addEventOptionSelected(new SearchOptinEvent() {
            @Override
            public void optionSelected(SearchOption option, int index) {
                textOptionfrmAdmin.setHint("Nhập " + option.getName() + "...");
            }
        });
        textOptionfrmAdmin.addOption(new SearchOption("tên sản phẩm..", new ImageIcon(getClass().getResource("/icons/product.png"))));
        textOptionfrmAdmin.addOption(new SearchOption("mã sản phẩm..", new ImageIcon(getClass().getResource("/icons/code.png"))));
    }
      public void loadTextSearch2() {
        txtoptionSLton.addEventOptionSelected(new SearchOptinEvent() {
            @Override
            public void optionSelected(SearchOption option, int index) {
                txtoptionSLton.setHint("Nhập " + option.getName() + "...");
            }
        });
        txtoptionSLton.addOption(new SearchOption("tên sản phẩm..", new ImageIcon(getClass().getResource("/icons/product.png"))));
        txtoptionSLton.addOption(new SearchOption("mã sản phẩm..", new ImageIcon(getClass().getResource("/icons/code.png"))));
    }
   
      String chuyenDoi(Date x) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(x);

            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);

                return (day + "-" + month + "-" + year);
        } catch (Exception e) {
            return "null";
        }
    }
    public void loadDateVN(){
        dateTufrmAdmin.setLocale(new Locale("vi", "VN"));
        dateDenfrmAdmin.setLocale(new Locale("vi", "VN"));
        dateDenFrmTKdoanhThu.setLocale(new Locale("vi", "VN"));
        dateTuFrmTKdoanhThu.setLocale(new Locale("vi", "VN"));
    }
      private void loadDataToTableThongKeProduct(ArrayList<thongKeSanPhamPojo> thongKe) {
        try {
            DefaultTableModel tblModelAcc = (DefaultTableModel) tblThongKeSanPhamfrmAdmin.getModel();
            tblModelAcc.setColumnIdentifiers(new String[]{"Mã sản phẩm","Tên sản phẩm","Số lượng nhập","Số lượng xuất","Số lượng hiện tại"});
            tblModelAcc.setRowCount(0);
            for (int i = 0; i < thongKe.size(); i++) {
                tblModelAcc.addRow(new Object[]{
                    thongKe.get(i).getCode(), thongKe.get(i).getTenSanPham(),thongKe.get(i).getSlnhap(),thongKe.get(i).getSlxuat(),
                    thongKe.get(i).getSlhientai()
                });
            }
            tblThongKeSanPhamfrmAdmin.getColumnModel().getColumn(0).setPreferredWidth(30);
            tblThongKeSanPhamfrmAdmin.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblThongKeSanPhamfrmAdmin.getColumnModel().getColumn(2).setPreferredWidth(30);
            tblThongKeSanPhamfrmAdmin.getColumnModel().getColumn(3).setPreferredWidth(30);
            tblThongKeSanPhamfrmAdmin.getColumnModel().getColumn(4).setPreferredWidth(30);
        } catch (Exception e) {
        }
    }
    private void loadDataToTableThongKeProductSlTon(ArrayList<thongKeSanPhamPojo> thongKe) {
        try {
            DefaultTableModel tblModelAcc = (DefaultTableModel) tbl_sanphamtonAdmin.getModel();
            tblModelAcc.setColumnIdentifiers(new String[]{"Mã sản phẩm","Tên sản phẩm","Màu sắc","Kích thước","Số lượng tồn"});
            tblModelAcc.setRowCount(0);
            for (int i = 0; i < thongKe.size(); i++) {
                tblModelAcc.addRow(new Object[]{
                    thongKe.get(i).getCode(), thongKe.get(i).getTenSanPham(),
                    thongKe.get(i).getTenMauSac(),
                    thongKe.get(i).getTenKichThuoc(),
                    thongKe.get(i).getSlhientai()
                });
            }
            tbl_sanphamtonAdmin.getColumnModel().getColumn(0).setPreferredWidth(30);
            tbl_sanphamtonAdmin.getColumnModel().getColumn(1).setPreferredWidth(150);
            tbl_sanphamtonAdmin.getColumnModel().getColumn(2).setPreferredWidth(30);
            tbl_sanphamtonAdmin.getColumnModel().getColumn(3).setPreferredWidth(30);
            tbl_sanphamtonAdmin.getColumnModel().getColumn(4).setPreferredWidth(30);
           
        } catch (Exception e) {
        }
    }
    public void filterThongKeSanPham() throws ParseException {
        ArrayList<thongKeSanPhamPojo> thongKe = new ArrayList<>();
        if (dateTufrmAdmin.getDate() != null || dateDenfrmAdmin.getDate() != null) {
            String dateFrom,dateTo;
            Date to = new Date();
            if (dateTufrmAdmin.getDate() != null && dateDenfrmAdmin.getDate() == null) {
                dateFrom = chuyenDoi(dateTufrmAdmin.getDate());
                dateTo = chuyenDoi(to);
            } else if (dateDenfrmAdmin.getDate() != null && dateTufrmAdmin.getDate() == null) {
                dateFrom = "01/08/2024";
                dateTo = chuyenDoi(dateDenfrmAdmin.getDate());
            } else {
                dateFrom = chuyenDoi(dateTufrmAdmin.getDate());
                dateTo = chuyenDoi(dateDenfrmAdmin.getDate());
            }
            thongKe = thongKeSanPhamDao.getInstance().getThongKe(dateFrom, dateTo);

        } else {
            thongKe = thongKeSanPhamDao.getInstance().getThongKe();
        }
        if (!textOptionfrmAdmin.getText().equals("")) {
            thongKe = searchTenSanPhamThongKe(thongKe, textOptionfrmAdmin.getText());
        }
        loadDataToTableThongKeProduct(thongKe);
    }
    public void filterSlTon() throws ParseException{
        ArrayList<thongKeSanPhamPojo> thongKe = new ArrayList<>();
       
        thongKe = thongKeSanPhamDao.getInstance().getThongKeSlTon();
        if (!txtoptionSLton.getText().equals("")){
            thongKe = searchTenSanPhamThongKe1(thongKe,txtoptionSLton.getText());
        }
        loadDataToTableThongKeProductSlTon(thongKe);
    }
    private ArrayList<thongKeSanPhamPojo> searchTenSanPhamThongKe(ArrayList<thongKeSanPhamPojo> arr, String name) {
        ArrayList<thongKeSanPhamPojo> result = new ArrayList<>();
        for (thongKeSanPhamPojo i : arr) {
            if (i.getCode().toLowerCase().contains(name.toLowerCase()) || i.getTenSanPham().toLowerCase().contains(name.toLowerCase())) {
                result.add(i);
            }
        }
        return result;
    }
    private ArrayList<thongKeSanPhamPojo> searchTenSanPhamThongKe1(ArrayList<thongKeSanPhamPojo> arr, String name) {
        ArrayList<thongKeSanPhamPojo> result = new ArrayList<>();
        for (thongKeSanPhamPojo i : arr) {
            if (i.getCode().toLowerCase().contains(name.toLowerCase()) || i.getTenSanPham().toLowerCase().contains(name.toLowerCase())) {
                result.add(i);
            }
        }
        return result;
    }
    private void loadDataToTableThongKeDoanhThu(ArrayList<thongKeDoanhThuPojo> thongKe) {
        try {
            DefaultTableModel tblModelAcc = (DefaultTableModel) tblFrmThongKeDoanhThu.getModel();
            tblModelAcc.setColumnIdentifiers(new String[]{"Mã hoá đơn","Mã sản phẩm","Tên sản phẩm","Kích thước","Đơn giá","Số lượng","Đơn giá","Giá nhập","Doanh thu","Lợi nhuận","Ngày lập"});
            tblModelAcc.setRowCount(0);
            double tongDoanhThu = 0;
            double tongLoiNhuan = 0;
            for (int i = 0; i < thongKe.size(); i++) {
                tblModelAcc.addRow(new Object[]{
                    thongKe.get(i).getMaHD(),thongKe.get(i).getCode(), thongKe.get(i).getTenSanPham(),thongKe.get(i).getTenKichThuoc(),
                    thongKe.get(i).getTenMauSac(),thongKe.get(i).getSoLuong(),formatter.format(thongKe.get(i).getDonGia())+" VNĐ",
                    formatter.format(thongKe.get(i).getGiaNhap())+ " VNĐ",
                    formatter.format(thongKe.get(i).getSoLuong()*thongKe.get(i).getDonGia()) + " VNĐ",
                    formatter.format(thongKe.get(i).getSoLuong()*thongKe.get(i).getDonGia() - thongKe.get(i).getSoLuong() * thongKe.get(i).getGiaNhap())+" VNĐ",
                    formatDate.format(thongKe.get(i).getNgayLap()) 
                }                    
                );
                tongDoanhThu = (thongKe.get(i).getDonGia()*thongKe.get(i).getSoLuong()) + tongDoanhThu;
                tongLoiNhuan = ((thongKe.get(i).getDonGia()*thongKe.get(i).getSoLuong()) - (thongKe.get(i).getGiaNhap()*thongKe.get(i).getSoLuong()))+ tongLoiNhuan;
                
            }
            tblFrmThongKeDoanhThu.getColumnModel().getColumn(1).setPreferredWidth(50);
            lblTongDoanhThu.setText(formatter.format(tongDoanhThu)+" VNĐ" );
            lblTongLoiNhuan.setText(formatter.format(tongLoiNhuan)+ " VNĐ");
            tblFrmThongKeDoanhThu.getColumnModel().getColumn(10).setPreferredWidth(150);
        } catch (Exception e) {
        }
    }
     public void filterThongKeDoanhThu() throws ParseException {
        ArrayList<thongKeDoanhThuPojo> thongKe = new ArrayList<>();
        if (dateTuFrmTKdoanhThu.getDate() != null || dateDenFrmTKdoanhThu.getDate() != null) {
            String dateFrom,dateTo;
            Date to = new Date();
            if (dateTuFrmTKdoanhThu.getDate() != null && dateDenFrmTKdoanhThu.getDate() == null) {
                dateFrom = chuyenDoi(dateTuFrmTKdoanhThu.getDate());
                dateTo = chuyenDoi(to);
            } else if (dateDenFrmTKdoanhThu.getDate() != null && dateTuFrmTKdoanhThu.getDate() == null) {
                dateFrom = "01/08/2024";
                dateTo = chuyenDoi(dateDenFrmTKdoanhThu.getDate());
            } else {
                dateFrom = chuyenDoi(dateTuFrmTKdoanhThu.getDate());
                dateTo = chuyenDoi(dateDenFrmTKdoanhThu.getDate());
            }
            thongKe = thongKeDoanhThuDao.getInstance().getThongKe(dateFrom, dateTo);

        } else {
            thongKe = thongKeDoanhThuDao.getInstance().getThongKe();
        }
        if (!textfrmTKdoanhThu.getText().equals("")) {
            thongKe = searchTenSanPhamThongKeDoanhThu(thongKe, textfrmTKdoanhThu.getText());
        }
        loadDataToTableThongKeDoanhThu(thongKe);
    }
     private ArrayList<thongKeDoanhThuPojo> searchTenSanPhamThongKeDoanhThu(ArrayList<thongKeDoanhThuPojo> arr, String name) {
        ArrayList<thongKeDoanhThuPojo> result = new ArrayList<>();
        for (thongKeDoanhThuPojo i : arr) {
            if (i.getCode().toLowerCase().contains(name.toLowerCase()) || i.getTenSanPham().toLowerCase().contains(name.toLowerCase())) {
                result.add(i);
            }
        }
        return result;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        componentResizer1 = new javaswingdev.swing.titlebar.ComponentResizer();
        tableCustom1 = new table.TableCustom();
        searchOption1 = new textfield.SearchOption();
        searchOption2 = new textfield.SearchOption();
        tableCustom2 = new table.TableCustom();
        tableCustom3 = new table.TableCustom();
        buttonGroup1 = new javax.swing.ButtonGroup();
        tableCustom4 = new table.TableCustom();
        backround = new javax.swing.JPanel();
        panel = new javax.swing.JPanel();
        thongtin = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        menu = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        panelRound1 = new boder_panel.PanelRound();
        jLabel4 = new javax.swing.JLabel();
        panelRound2 = new boder_panel.PanelRound();
        jLabel5 = new javax.swing.JLabel();
        panelRound4 = new boder_panel.PanelRound();
        jLabel7 = new javax.swing.JLabel();
        panelRound5 = new boder_panel.PanelRound();
        jLabel8 = new javax.swing.JLabel();
        panelRound6 = new boder_panel.PanelRound();
        jLabel9 = new javax.swing.JLabel();
        panelRound7 = new boder_panel.PanelRound();
        jLabel10 = new javax.swing.JLabel();
        titleBar1 = new javaswingdev.swing.titlebar.TitleBar();
        jPanel3 = new javax.swing.JPanel();
        trangchu = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        panelRound8 = new boder_panel.PanelRound();
        panelRound10 = new boder_panel.PanelRound();
        jLabel18 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        panelRound11 = new boder_panel.PanelRound();
        panelRound13 = new boder_panel.PanelRound();
        jLabel19 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        panelRound12 = new boder_panel.PanelRound();
        panelRound14 = new boder_panel.PanelRound();
        jLabel20 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        qlnv = new javax.swing.JPanel();
        panelRound9 = new boder_panel.PanelRound();
        txtSearch = new textfield.TextFieldSearchOption();
        panelRound15 = new boder_panel.PanelRound();
        jLabel2 = new javax.swing.JLabel();
        panelRound16 = new boder_panel.PanelRound();
        jLabel28 = new javax.swing.JLabel();
        panelRound17 = new boder_panel.PanelRound();
        jLabel25 = new javax.swing.JLabel();
        panelRound18 = new boder_panel.PanelRound();
        jLabel26 = new javax.swing.JLabel();
        panelRound19 = new boder_panel.PanelRound();
        jLabel27 = new javax.swing.JLabel();
        tableScrollButton1 = new table.TableScrollButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbNhanVien = new javax.swing.JTable();
        capquyen = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        cboNV = new combobox.Combobox();
        jScrollPane2 = new javax.swing.JScrollPane();
        listDoiTuong = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        listCapQuyen = new javax.swing.JList<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        listHeThong = new javax.swing.JList<>();
        cboTB = new combobox.Combobox();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        button2 = new sample.message.Button();
        button3 = new sample.message.Button();
        button4 = new sample.message.Button();
        nhomquyen = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        button7 = new sample.message.Button();
        button8 = new sample.message.Button();
        button9 = new sample.message.Button();
        button10 = new sample.message.Button();
        jPanel8 = new javax.swing.JPanel();
        cboRole = new combobox.Combobox();
        jScrollPane5 = new javax.swing.JScrollPane();
        listDoiTuong1 = new javax.swing.JList<>();
        jScrollPane6 = new javax.swing.JScrollPane();
        listCapQuyen1 = new javax.swing.JList<>();
        jScrollPane7 = new javax.swing.JScrollPane();
        listHeThong1 = new javax.swing.JList<>();
        cboTB1 = new combobox.Combobox();
        jLabel24 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        txtTenNhom = new textfield.TextField();
        button5 = new sample.message.Button();
        jPanel10 = new javax.swing.JPanel();
        cboRole1 = new combobox.Combobox();
        button6 = new sample.message.Button();
        thongke = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel12 = new javax.swing.JPanel();
        tableScrollButton2 = new table.TableScrollButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblThongKeSanPhamfrmAdmin = new javax.swing.JTable();
        panelRound28 = new boder_panel.PanelRound();
        dateDenfrmAdmin = new com.toedter.calendar.JDateChooser();
        dateTufrmAdmin = new com.toedter.calendar.JDateChooser();
        button1 = new sample.message.Button();
        jLabel13 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        panelRound29 = new boder_panel.PanelRound();
        textOptionfrmAdmin = new textfield.TextFieldSearchOption();
        jPanel13 = new javax.swing.JPanel();
        panelRound3 = new boder_panel.PanelRound();
        textfrmTKdoanhThu = new textfield.TextFieldSearchOption();
        tableScrollButton3 = new table.TableScrollButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblFrmThongKeDoanhThu = new javax.swing.JTable();
        panelRound20 = new boder_panel.PanelRound();
        jLabel12 = new javax.swing.JLabel();
        lblTongDoanhThu = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        lblTongLoiNhuan = new javax.swing.JLabel();
        button12 = new sample.message.Button();
        panelRound21 = new boder_panel.PanelRound();
        dateTuFrmTKdoanhThu = new com.toedter.calendar.JDateChooser();
        dateDenFrmTKdoanhThu = new com.toedter.calendar.JDateChooser();
        button11 = new sample.message.Button();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        panelRound22 = new boder_panel.PanelRound();
        txtoptionSLton = new textfield.TextFieldSearchOption();
        button14 = new sample.message.Button();
        ckb_soluongton = new checkbox.JCheckBoxCustom();
        tableScrollButton4 = new table.TableScrollButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        tbl_sanphamtonAdmin = new javax.swing.JTable();
        button13 = new sample.message.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        panel.setBackground(new java.awt.Color(255, 255, 255));

        thongtin.setBackground(new java.awt.Color(255, 255, 255));
        thongtin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/profile.png"))); // NOI18N

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText("Đoàn Quốc Anh");

        jLabel17.setForeground(new java.awt.Color(153, 153, 153));
        jLabel17.setText("Admin");

        javax.swing.GroupLayout thongtinLayout = new javax.swing.GroupLayout(thongtin);
        thongtin.setLayout(thongtinLayout);
        thongtinLayout.setHorizontalGroup(
            thongtinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thongtinLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(thongtinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        thongtinLayout.setVerticalGroup(
            thongtinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thongtinLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(thongtinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(thongtinLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel17)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        menu.setBackground(new java.awt.Color(255, 255, 255));
        menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        panelRound1.setBackground(new java.awt.Color(255, 255, 255));
        panelRound1.setForeground(new java.awt.Color(255, 255, 255));
        panelRound1.setToolTipText("");
        panelRound1.setRoundBottomLeft(10);
        panelRound1.setRoundBottomRight(10);
        panelRound1.setRoundTopLeft(10);
        panelRound1.setRoundTopRight(10);
        panelRound1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelRound1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelRound1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelRound1MouseExited(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/home (1).png"))); // NOI18N
        jLabel4.setText("  Trang chủ");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        panelRound2.setBackground(new java.awt.Color(255, 255, 255));
        panelRound2.setRoundBottomLeft(10);
        panelRound2.setRoundBottomRight(10);
        panelRound2.setRoundTopLeft(10);
        panelRound2.setRoundTopRight(10);
        panelRound2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelRound2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelRound2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelRound2MouseExited(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/users.png"))); // NOI18N
        jLabel5.setText("  Quản lý nhân viên");

        javax.swing.GroupLayout panelRound2Layout = new javax.swing.GroupLayout(panelRound2);
        panelRound2.setLayout(panelRound2Layout);
        panelRound2Layout.setHorizontalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelRound2Layout.setVerticalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        panelRound4.setBackground(new java.awt.Color(255, 255, 255));
        panelRound4.setRoundBottomLeft(10);
        panelRound4.setRoundBottomRight(10);
        panelRound4.setRoundTopLeft(10);
        panelRound4.setRoundTopRight(10);
        panelRound4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelRound4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelRound4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelRound4MouseExited(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/benefit.png"))); // NOI18N
        jLabel7.setText("  Cấp quyền");

        javax.swing.GroupLayout panelRound4Layout = new javax.swing.GroupLayout(panelRound4);
        panelRound4.setLayout(panelRound4Layout);
        panelRound4Layout.setHorizontalGroup(
            panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound4Layout.createSequentialGroup()
                .addGap(0, 32, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelRound4Layout.setVerticalGroup(
            panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        panelRound5.setBackground(new java.awt.Color(255, 255, 255));
        panelRound5.setRoundBottomLeft(10);
        panelRound5.setRoundBottomRight(10);
        panelRound5.setRoundTopLeft(10);
        panelRound5.setRoundTopRight(10);
        panelRound5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelRound5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelRound5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelRound5MouseExited(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/groups.png"))); // NOI18N
        jLabel8.setText("  Cấp nhóm quyền");

        javax.swing.GroupLayout panelRound5Layout = new javax.swing.GroupLayout(panelRound5);
        panelRound5.setLayout(panelRound5Layout);
        panelRound5Layout.setHorizontalGroup(
            panelRound5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelRound5Layout.setVerticalGroup(
            panelRound5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        panelRound6.setBackground(new java.awt.Color(255, 255, 255));
        panelRound6.setRoundBottomLeft(10);
        panelRound6.setRoundBottomRight(10);
        panelRound6.setRoundTopLeft(10);
        panelRound6.setRoundTopRight(10);
        panelRound6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelRound6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelRound6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelRound6MouseExited(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bar-chart.png"))); // NOI18N
        jLabel9.setText("  Thống kê");

        javax.swing.GroupLayout panelRound6Layout = new javax.swing.GroupLayout(panelRound6);
        panelRound6.setLayout(panelRound6Layout);
        panelRound6Layout.setHorizontalGroup(
            panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelRound6Layout.setVerticalGroup(
            panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        panelRound7.setBackground(new java.awt.Color(255, 255, 255));
        panelRound7.setRoundBottomLeft(10);
        panelRound7.setRoundBottomRight(10);
        panelRound7.setRoundTopLeft(10);
        panelRound7.setRoundTopRight(10);
        panelRound7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelRound7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelRound7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelRound7MouseExited(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/turn-off.png"))); // NOI18N
        jLabel10.setText("Đăng xuất");

        javax.swing.GroupLayout panelRound7Layout = new javax.swing.GroupLayout(panelRound7);
        panelRound7.setLayout(panelRound7Layout);
        panelRound7Layout.setHorizontalGroup(
            panelRound7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelRound7Layout.setVerticalGroup(
            panelRound7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelRound1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelRound4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelRound5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelRound6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelRound7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(panelRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(panelRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(panelRound4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(panelRound5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(panelRound6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelRound7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout menuLayout = new javax.swing.GroupLayout(menu);
        menu.setLayout(menuLayout);
        menuLayout.setHorizontalGroup(
            menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );
        menuLayout.setVerticalGroup(
            menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        titleBar1.setBackground(new java.awt.Color(255, 255, 255));
        titleBar1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(thongtin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(titleBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addComponent(titleBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(thongtin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.CardLayout());

        trangchu.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 102, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/JAVA.png"))); // NOI18N
        jLabel1.setText("HỆ THỐNG QUẢN LÝ CỬA HÀNG THỜI TRANG");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 2, 16)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 102, 255));
        jLabel14.setText("Dù chỉ một lần trong đời, hãy thử điều gì đó. Nỗ lực vì điều gì đó. Hãy thử thay đổi, không gì tồi tệ có thể xảy ra đâu");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(229, 229, 229))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(115, 115, 115))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(238, 250, 254));

        panelRound8.setBackground(new java.awt.Color(255, 255, 255));
        panelRound8.setRoundBottomLeft(100);
        panelRound8.setRoundBottomRight(100);
        panelRound8.setRoundTopLeft(100);
        panelRound8.setRoundTopRight(100);

        panelRound10.setBackground(new java.awt.Color(238, 250, 254));
        panelRound10.setRoundBottomLeft(100);
        panelRound10.setRoundBottomRight(100);
        panelRound10.setRoundTopLeft(100);
        panelRound10.setRoundTopRight(100);

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/target.png"))); // NOI18N

        javax.swing.GroupLayout panelRound10Layout = new javax.swing.GroupLayout(panelRound10);
        panelRound10.setLayout(panelRound10Layout);
        panelRound10Layout.setHorizontalGroup(
            panelRound10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound10Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(59, Short.MAX_VALUE))
        );
        panelRound10Layout.setVerticalGroup(
            panelRound10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound10Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel18)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel21.setText("TÍNH CHÍNH XÁC");

        javax.swing.GroupLayout panelRound8Layout = new javax.swing.GroupLayout(panelRound8);
        panelRound8.setLayout(panelRound8Layout);
        panelRound8Layout.setHorizontalGroup(
            panelRound8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound8Layout.createSequentialGroup()
                .addGroup(panelRound8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound8Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(panelRound10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelRound8Layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(jLabel21)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        panelRound8Layout.setVerticalGroup(
            panelRound8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound8Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(panelRound10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel21)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelRound11.setBackground(new java.awt.Color(255, 255, 255));
        panelRound11.setRoundBottomLeft(100);
        panelRound11.setRoundBottomRight(100);
        panelRound11.setRoundTopLeft(100);
        panelRound11.setRoundTopRight(100);

        panelRound13.setBackground(new java.awt.Color(238, 250, 254));
        panelRound13.setRoundBottomLeft(100);
        panelRound13.setRoundBottomRight(100);
        panelRound13.setRoundTopLeft(100);
        panelRound13.setRoundTopRight(100);

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/security.png"))); // NOI18N

        javax.swing.GroupLayout panelRound13Layout = new javax.swing.GroupLayout(panelRound13);
        panelRound13.setLayout(panelRound13Layout);
        panelRound13Layout.setHorizontalGroup(
            panelRound13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound13Layout.createSequentialGroup()
                .addContainerGap(69, Short.MAX_VALUE)
                .addComponent(jLabel19)
                .addGap(62, 62, 62))
        );
        panelRound13Layout.setVerticalGroup(
            panelRound13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound13Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel19)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel22.setText("TÍNH BẢO MẬT");

        javax.swing.GroupLayout panelRound11Layout = new javax.swing.GroupLayout(panelRound11);
        panelRound11.setLayout(panelRound11Layout);
        panelRound11Layout.setHorizontalGroup(
            panelRound11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound11Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(panelRound13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel22)
                .addGap(94, 94, 94))
        );
        panelRound11Layout.setVerticalGroup(
            panelRound11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound11Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(panelRound13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel22)
                .addContainerGap(147, Short.MAX_VALUE))
        );

        panelRound12.setBackground(new java.awt.Color(255, 255, 255));
        panelRound12.setRoundBottomLeft(100);
        panelRound12.setRoundBottomRight(100);
        panelRound12.setRoundTopLeft(100);
        panelRound12.setRoundTopRight(100);

        panelRound14.setBackground(new java.awt.Color(238, 250, 254));
        panelRound14.setRoundBottomLeft(100);
        panelRound14.setRoundBottomRight(100);
        panelRound14.setRoundTopLeft(100);
        panelRound14.setRoundTopRight(100);

        jLabel20.setBackground(new java.awt.Color(238, 250, 254));
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/efficiency.png"))); // NOI18N

        javax.swing.GroupLayout panelRound14Layout = new javax.swing.GroupLayout(panelRound14);
        panelRound14.setLayout(panelRound14Layout);
        panelRound14Layout.setHorizontalGroup(
            panelRound14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound14Layout.createSequentialGroup()
                .addContainerGap(65, Short.MAX_VALUE)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
        panelRound14Layout.setVerticalGroup(
            panelRound14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound14Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addGap(14, 14, 14))
        );

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel23.setText("TÍNH HIỆU QUẢ");

        javax.swing.GroupLayout panelRound12Layout = new javax.swing.GroupLayout(panelRound12);
        panelRound12.setLayout(panelRound12Layout);
        panelRound12Layout.setHorizontalGroup(
            panelRound12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound12Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(panelRound14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel23)
                .addGap(91, 91, 91))
        );
        panelRound12Layout.setVerticalGroup(
            panelRound12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound12Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(panelRound14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(panelRound8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addComponent(panelRound11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(panelRound12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelRound8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRound11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRound12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(140, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout trangchuLayout = new javax.swing.GroupLayout(trangchu);
        trangchu.setLayout(trangchuLayout);
        trangchuLayout.setHorizontalGroup(
            trangchuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        trangchuLayout.setVerticalGroup(
            trangchuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(trangchuLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(trangchu, "card2");

        qlnv.setBackground(new java.awt.Color(240, 247, 250));

        panelRound9.setBackground(new java.awt.Color(255, 255, 255));
        panelRound9.setRoundBottomLeft(10);
        panelRound9.setRoundBottomRight(10);
        panelRound9.setRoundTopLeft(10);
        panelRound9.setRoundTopRight(10);

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        panelRound15.setBackground(new java.awt.Color(211, 213, 214));
        panelRound15.setRoundBottomLeft(10);
        panelRound15.setRoundBottomRight(10);
        panelRound15.setRoundTopLeft(10);
        panelRound15.setRoundTopRight(10);
        panelRound15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelRound15MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelRound15MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelRound15MouseExited(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/refresh (1).png"))); // NOI18N
        jLabel2.setText("Làm mới");

        javax.swing.GroupLayout panelRound15Layout = new javax.swing.GroupLayout(panelRound15);
        panelRound15.setLayout(panelRound15Layout);
        panelRound15Layout.setHorizontalGroup(
            panelRound15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound15Layout.createSequentialGroup()
                .addGap(0, 16, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelRound15Layout.setVerticalGroup(
            panelRound15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelRound16.setBackground(new java.awt.Color(211, 213, 214));
        panelRound16.setRoundBottomLeft(10);
        panelRound16.setRoundBottomRight(10);
        panelRound16.setRoundTopLeft(10);
        panelRound16.setRoundTopRight(10);
        panelRound16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelRound16MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelRound16MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelRound16MouseExited(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(102, 102, 102));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add20.png"))); // NOI18N
        jLabel28.setText("Thêm");

        javax.swing.GroupLayout panelRound16Layout = new javax.swing.GroupLayout(panelRound16);
        panelRound16.setLayout(panelRound16Layout);
        panelRound16Layout.setHorizontalGroup(
            panelRound16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        panelRound16Layout.setVerticalGroup(
            panelRound16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelRound17.setBackground(new java.awt.Color(211, 213, 214));
        panelRound17.setRoundBottomLeft(10);
        panelRound17.setRoundBottomRight(10);
        panelRound17.setRoundTopLeft(10);
        panelRound17.setRoundTopRight(10);
        panelRound17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelRound17MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelRound17MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelRound17MouseExited(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(102, 102, 102));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/pencil20.png"))); // NOI18N
        jLabel25.setText("Sửa");

        javax.swing.GroupLayout panelRound17Layout = new javax.swing.GroupLayout(panelRound17);
        panelRound17.setLayout(panelRound17Layout);
        panelRound17Layout.setHorizontalGroup(
            panelRound17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        panelRound17Layout.setVerticalGroup(
            panelRound17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelRound18.setBackground(new java.awt.Color(211, 213, 214));
        panelRound18.setRoundBottomLeft(10);
        panelRound18.setRoundBottomRight(10);
        panelRound18.setRoundTopLeft(10);
        panelRound18.setRoundTopRight(10);
        panelRound18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelRound18MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelRound18MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelRound18MouseExited(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(102, 102, 102));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/trash20.png"))); // NOI18N
        jLabel26.setText("Xoá");

        javax.swing.GroupLayout panelRound18Layout = new javax.swing.GroupLayout(panelRound18);
        panelRound18.setLayout(panelRound18Layout);
        panelRound18Layout.setHorizontalGroup(
            panelRound18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        panelRound18Layout.setVerticalGroup(
            panelRound18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelRound19.setBackground(new java.awt.Color(211, 213, 214));
        panelRound19.setRoundBottomLeft(10);
        panelRound19.setRoundBottomRight(10);
        panelRound19.setRoundTopLeft(10);
        panelRound19.setRoundTopRight(10);
        panelRound19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelRound19MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelRound19MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelRound19MouseExited(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(102, 102, 102));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-info-20.png"))); // NOI18N
        jLabel27.setText("Hình nhân viên");

        javax.swing.GroupLayout panelRound19Layout = new javax.swing.GroupLayout(panelRound19);
        panelRound19.setLayout(panelRound19Layout);
        panelRound19Layout.setHorizontalGroup(
            panelRound19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelRound19Layout.setVerticalGroup(
            panelRound19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelRound9Layout = new javax.swing.GroupLayout(panelRound9);
        panelRound9.setLayout(panelRound9Layout);
        panelRound9Layout.setHorizontalGroup(
            panelRound9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound9Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(panelRound16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(panelRound17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(panelRound18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(panelRound19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(panelRound15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        panelRound9Layout.setVerticalGroup(
            panelRound9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound9Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelRound9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelRound19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRound15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRound16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRound17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRound18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        tbNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbNhanVien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbNhanVienKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tbNhanVien);

        tableScrollButton1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout qlnvLayout = new javax.swing.GroupLayout(qlnv);
        qlnv.setLayout(qlnvLayout);
        qlnvLayout.setHorizontalGroup(
            qlnvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(qlnvLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(qlnvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelRound9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tableScrollButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(151, Short.MAX_VALUE))
        );
        qlnvLayout.setVerticalGroup(
            qlnvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(qlnvLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(panelRound9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(tableScrollButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(138, Short.MAX_VALUE))
        );

        jPanel3.add(qlnv, "card3");

        capquyen.setBackground(new java.awt.Color(240, 247, 250));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Chọn quyền"));

        cboNV.setLabeText("Nhân viên");

        jScrollPane2.setViewportView(listDoiTuong);

        jScrollPane3.setViewportView(listCapQuyen);

        jScrollPane4.setViewportView(listHeThong);

        cboTB.setLabeText("Bảng");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Quyền đối tượng");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Quyền đã chọn");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Quyền hệ thống");

        jButton1.setText(">");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jButton2.setText("<");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText(">>");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        jButton4.setText("<<");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Làm mới");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(100, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(cboTB, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(97, 97, 97)
                        .addComponent(cboNV, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(168, 168, 168))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(125, 125, 125)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(65, 65, 65))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jButton5)
                        .addGap(444, 444, 444))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboTB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6)
                    .addComponent(jLabel11))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2)
                            .addComponent(jScrollPane3)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addComponent(jButton1)
                        .addGap(48, 48, 48)
                        .addComponent(jButton3))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(jButton2)
                        .addGap(52, 52, 52)
                        .addComponent(jButton4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5)
                .addGap(11, 11, 11))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Chức năng"));

        button2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/checked.png"))); // NOI18N
        button2.setText("Cấp quyền");
        button2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        button2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button2MouseClicked(evt);
            }
        });
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });

        button3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/permission.png"))); // NOI18N
        button3.setText("Xem quyền");
        button3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        button3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button3MouseClicked(evt);
            }
        });

        button4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/unchecked.png"))); // NOI18N
        button4.setText("Thu quyền");
        button4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        button4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(193, 193, 193)
                .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addComponent(button4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(156, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(button2, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(button3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(button4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout capquyenLayout = new javax.swing.GroupLayout(capquyen);
        capquyen.setLayout(capquyenLayout);
        capquyenLayout.setHorizontalGroup(
            capquyenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(capquyenLayout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(capquyenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(75, Short.MAX_VALUE))
        );
        capquyenLayout.setVerticalGroup(
            capquyenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(capquyenLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(85, Short.MAX_VALUE))
        );

        jPanel3.add(capquyen, "card5");

        nhomquyen.setBackground(new java.awt.Color(240, 247, 250));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Chức năng"));

        button7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/checked.png"))); // NOI18N
        button7.setText("Cấp quyền");
        button7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        button7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button7MouseClicked(evt);
            }
        });

        button8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/unchecked.png"))); // NOI18N
        button8.setText("Thu quyền");
        button8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        button8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button8MouseClicked(evt);
            }
        });

        button9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/permission.png"))); // NOI18N
        button9.setText("Xem quyền");
        button9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        button9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button9MouseClicked(evt);
            }
        });

        button10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-group-30.png"))); // NOI18N
        button10.setText("Cấp role");
        button10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        button10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button10MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(button9, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(109, 109, 109)
                .addComponent(button7, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(button8, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(109, 109, 109)
                .addComponent(button10, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(button10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Chọn quyền cho nhóm"));

        cboRole.setLabeText("Nhóm");

        jScrollPane5.setViewportView(listDoiTuong1);

        jScrollPane6.setViewportView(listCapQuyen1);

        jScrollPane7.setViewportView(listHeThong1);

        cboTB1.setLabeText("Bảng");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("Quyền đối tượng");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Quyền đã chọn");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("Quyền hệ thống");

        jButton6.setText(">");
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton6MouseClicked(evt);
            }
        });

        jButton7.setText("<");
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton7MouseClicked(evt);
            }
        });
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText(">>");
        jButton8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton8MouseClicked(evt);
            }
        });

        jButton9.setText("<<");
        jButton9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton9MouseClicked(evt);
            }
        });
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setText("Làm mới");
        jButton10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton10MouseClicked(evt);
            }
        });
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(125, 125, 125)
                                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(65, 65, 65))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(jButton10)
                        .addGap(444, 444, 444))))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(237, 237, 237)
                .addComponent(cboTB1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92)
                .addComponent(cboRole, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboTB1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(jLabel29)
                            .addComponent(jLabel24))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(jButton6)
                        .addGap(32, 32, 32)
                        .addComponent(jButton8)
                        .addGap(50, 50, 50))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(jButton7)
                        .addGap(29, 29, 29)
                        .addComponent(jButton9)
                        .addGap(54, 54, 54)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton10)
                .addGap(11, 11, 11))
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Tạo nhóm quyền"));

        txtTenNhom.setLabelText("Tên nhóm");

        button5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add20.png"))); // NOI18N
        button5.setText("Tạo");
        button5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        button5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button5MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(txtTenNhom, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(button5, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTenNhom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Xoá nhóm quyền"));

        cboRole1.setLabeText("Nhóm");
        cboRole1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboRole1ActionPerformed(evt);
            }
        });

        button6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/trash20.png"))); // NOI18N
        button6.setText("Xoá");
        button6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        button6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button6MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cboRole1, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(button6, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboRole1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button6, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout nhomquyenLayout = new javax.swing.GroupLayout(nhomquyen);
        nhomquyen.setLayout(nhomquyenLayout);
        nhomquyenLayout.setHorizontalGroup(
            nhomquyenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nhomquyenLayout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(nhomquyenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(nhomquyenLayout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(68, Short.MAX_VALUE))
        );
        nhomquyenLayout.setVerticalGroup(
            nhomquyenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nhomquyenLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(nhomquyenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(nhomquyen, "card6");

        thongke.setBackground(new java.awt.Color(255, 255, 255));
        thongke.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1100, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 110, Short.MAX_VALUE)
        );

        thongke.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 110));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        tblThongKeSanPhamfrmAdmin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane8.setViewportView(tblThongKeSanPhamfrmAdmin);

        tableScrollButton2.add(jScrollPane8, java.awt.BorderLayout.CENTER);

        panelRound28.setBackground(new java.awt.Color(255, 255, 255));
        panelRound28.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Lọc theo ngày"));

        dateDenfrmAdmin.setBackground(new java.awt.Color(255, 255, 255));
        dateDenfrmAdmin.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateDenfrmAdminPropertyChange(evt);
            }
        });

        dateTufrmAdmin.setBackground(new java.awt.Color(255, 255, 255));
        dateTufrmAdmin.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateTufrmAdminPropertyChange(evt);
            }
        });

        button1.setBackground(new java.awt.Color(204, 204, 204));
        button1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/refresh (1).png"))); // NOI18N
        button1.setText("Làm mới");
        button1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button1MouseClicked(evt);
            }
        });
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("TỪ:");

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel32.setText("ĐẾN:");

        javax.swing.GroupLayout panelRound28Layout = new javax.swing.GroupLayout(panelRound28);
        panelRound28.setLayout(panelRound28Layout);
        panelRound28Layout.setHorizontalGroup(
            panelRound28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound28Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(dateTufrmAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jLabel32)
                .addGap(18, 18, 18)
                .addComponent(dateDenfrmAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 97, Short.MAX_VALUE)
                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        panelRound28Layout.setVerticalGroup(
            panelRound28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound28Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelRound28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelRound28Layout.createSequentialGroup()
                        .addGroup(panelRound28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel32))
                        .addGap(6, 6, 6)))
                .addGap(16, 16, 16))
            .addGroup(panelRound28Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRound28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateTufrmAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateDenfrmAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        panelRound29.setBackground(new java.awt.Color(255, 255, 255));
        panelRound29.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Tìm kiếm"));

        textOptionfrmAdmin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textOptionfrmAdminKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout panelRound29Layout = new javax.swing.GroupLayout(panelRound29);
        panelRound29.setLayout(panelRound29Layout);
        panelRound29Layout.setHorizontalGroup(
            panelRound29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound29Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(textOptionfrmAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );
        panelRound29Layout.setVerticalGroup(
            panelRound29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound29Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(textOptionfrmAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(tableScrollButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 1068, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(panelRound29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelRound28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(106, 106, 106))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelRound29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRound28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(tableScrollButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Thống kê sản phẩm", jPanel12);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        panelRound3.setBackground(new java.awt.Color(255, 255, 255));
        panelRound3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Tìm kiếm"));

        textfrmTKdoanhThu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textfrmTKdoanhThuActionPerformed(evt);
            }
        });
        textfrmTKdoanhThu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textfrmTKdoanhThuKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout panelRound3Layout = new javax.swing.GroupLayout(panelRound3);
        panelRound3.setLayout(panelRound3Layout);
        panelRound3Layout.setHorizontalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound3Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(textfrmTKdoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(59, Short.MAX_VALUE))
        );
        panelRound3Layout.setVerticalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(textfrmTKdoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        tblFrmThongKeDoanhThu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane9.setViewportView(tblFrmThongKeDoanhThu);

        tableScrollButton3.add(jScrollPane9, java.awt.BorderLayout.CENTER);

        panelRound20.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Tổng doanh thu:");

        lblTongDoanhThu.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTongDoanhThu.setForeground(new java.awt.Color(153, 153, 0));
        lblTongDoanhThu.setText("doanhThuVND");

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel31.setText("Tổng lợi nhuận:");

        lblTongLoiNhuan.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTongLoiNhuan.setForeground(new java.awt.Color(0, 102, 0));
        lblTongLoiNhuan.setText("loiNhuanVND");

        button12.setBackground(new java.awt.Color(0, 102, 0));
        button12.setForeground(new java.awt.Color(255, 255, 255));
        button12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-send-mail-22.png"))); // NOI18N
        button12.setText("Gửi thống kê");
        button12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRound20Layout = new javax.swing.GroupLayout(panelRound20);
        panelRound20.setLayout(panelRound20Layout);
        panelRound20Layout.setHorizontalGroup(
            panelRound20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound20Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(lblTongDoanhThu)
                .addGap(129, 129, 129)
                .addComponent(jLabel31)
                .addGap(18, 18, 18)
                .addComponent(lblTongLoiNhuan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 199, Short.MAX_VALUE)
                .addComponent(button12, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(96, 96, 96))
        );
        panelRound20Layout.setVerticalGroup(
            panelRound20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound20Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(panelRound20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lblTongDoanhThu)
                    .addComponent(jLabel31)
                    .addComponent(lblTongLoiNhuan)
                    .addComponent(button12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        panelRound21.setBackground(new java.awt.Color(255, 255, 255));
        panelRound21.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Lọc theo ngày"));

        dateTuFrmTKdoanhThu.setBackground(new java.awt.Color(255, 255, 255));
        dateTuFrmTKdoanhThu.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateTuFrmTKdoanhThuPropertyChange(evt);
            }
        });

        dateDenFrmTKdoanhThu.setBackground(new java.awt.Color(255, 255, 255));
        dateDenFrmTKdoanhThu.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateDenFrmTKdoanhThuPropertyChange(evt);
            }
        });

        button11.setBackground(new java.awt.Color(204, 204, 204));
        button11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/refresh (1).png"))); // NOI18N
        button11.setText("Làm mới");
        button11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button11ActionPerformed(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel33.setText("TỪ:");
        jLabel33.setToolTipText("");

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel34.setText("ĐẾN:");

        javax.swing.GroupLayout panelRound21Layout = new javax.swing.GroupLayout(panelRound21);
        panelRound21.setLayout(panelRound21Layout);
        panelRound21Layout.setHorizontalGroup(
            panelRound21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound21Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dateTuFrmTKdoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dateDenFrmTKdoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(button11, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        panelRound21Layout.setVerticalGroup(
            panelRound21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound21Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panelRound21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34)
                    .addComponent(jLabel33)
                    .addGroup(panelRound21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(dateDenFrmTKdoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(dateTuFrmTKdoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(button11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelRound20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel13Layout.createSequentialGroup()
                            .addComponent(panelRound3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(panelRound21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(tableScrollButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1064, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelRound3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelRound21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tableScrollButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelRound20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Thống kê doanh thu", jPanel13);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        panelRound22.setBackground(new java.awt.Color(255, 255, 255));
        panelRound22.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Tìm kiếm"));

        txtoptionSLton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtoptionSLtonKeyReleased(evt);
            }
        });

        button14.setBackground(new java.awt.Color(204, 204, 204));
        button14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/refresh (1).png"))); // NOI18N
        button14.setText("Làm mới");
        button14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button14ActionPerformed(evt);
            }
        });

        ckb_soluongton.setText("Sản phẩm sắp hết");
        ckb_soluongton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ckb_soluongtonMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ckb_soluongtonMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ckb_soluongtonMouseReleased(evt);
            }
        });
        ckb_soluongton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckb_soluongtonActionPerformed(evt);
            }
        });
        ckb_soluongton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ckb_soluongtonKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout panelRound22Layout = new javax.swing.GroupLayout(panelRound22);
        panelRound22.setLayout(panelRound22Layout);
        panelRound22Layout.setHorizontalGroup(
            panelRound22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound22Layout.createSequentialGroup()
                .addGap(305, 305, 305)
                .addComponent(txtoptionSLton, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(ckb_soluongton, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                .addComponent(button14, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58))
        );
        panelRound22Layout.setVerticalGroup(
            panelRound22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRound22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtoptionSLton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ckb_soluongton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelRound22Layout.createSequentialGroup()
                        .addComponent(button14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        tbl_sanphamtonAdmin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane10.setViewportView(tbl_sanphamtonAdmin);

        tableScrollButton4.add(jScrollPane10, java.awt.BorderLayout.CENTER);

        button13.setBackground(new java.awt.Color(0, 102, 0));
        button13.setForeground(new java.awt.Color(255, 255, 255));
        button13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-send-mail-22.png"))); // NOI18N
        button13.setText("Gửi danh sách ");
        button13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button13ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tableScrollButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRound22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(455, 455, 455)
                .addComponent(button13, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(panelRound22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tableScrollButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(button13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        jTabbedPane1.addTab("Sản phẩm tồn", jPanel14);

        thongke.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 1100, 650));

        jPanel3.add(thongke, "card7");

        javax.swing.GroupLayout backroundLayout = new javax.swing.GroupLayout(backround);
        backround.setLayout(backroundLayout);
        backroundLayout.setHorizontalGroup(
            backroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backroundLayout.createSequentialGroup()
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        backroundLayout.setVerticalGroup(
            backroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backround, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backround, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void panelRound1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound1MouseEntered
        if (flag1 == 0)
            panelRound1.setBackground(new Color(211, 213, 214, 255));
    }//GEN-LAST:event_panelRound1MouseEntered

    private void panelRound1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound1MouseExited
        if (flag1 == 0)
            panelRound1.setBackground(Color.white);
    }//GEN-LAST:event_panelRound1MouseExited

    private void panelRound2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound2MouseEntered
        if (flag2 == 0)
            panelRound2.setBackground(new Color(211, 213, 214, 255));
    }//GEN-LAST:event_panelRound2MouseEntered

    private void panelRound4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound4MouseEntered
        if (flag4 == 0)
            panelRound4.setBackground(new Color(211, 213, 214, 255));
    }//GEN-LAST:event_panelRound4MouseEntered

    private void panelRound5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound5MouseEntered
        if (flag5 == 0)
            panelRound5.setBackground(new Color(211, 213, 214, 255));
    }//GEN-LAST:event_panelRound5MouseEntered

    private void panelRound6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound6MouseEntered
        if (flag6 == 0)
            panelRound6.setBackground(new Color(211, 213, 214, 255));
    }//GEN-LAST:event_panelRound6MouseEntered

    private void panelRound7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound7MouseEntered

        panelRound7.setBackground(new Color(211, 213, 214, 255));
    }//GEN-LAST:event_panelRound7MouseEntered

    private void panelRound2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound2MouseExited
        if (flag2 == 0)
            panelRound2.setBackground(Color.white);
    }//GEN-LAST:event_panelRound2MouseExited

    private void panelRound4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound4MouseExited
        if (flag4 == 0)
            panelRound4.setBackground(Color.white);
    }//GEN-LAST:event_panelRound4MouseExited

    private void panelRound5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound5MouseExited
        if (flag5 == 0)
            panelRound5.setBackground(Color.white);
    }//GEN-LAST:event_panelRound5MouseExited

    private void panelRound6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound6MouseExited
        if (flag6 == 0)
            panelRound6.setBackground(Color.white);
    }//GEN-LAST:event_panelRound6MouseExited

    private void panelRound7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound7MouseExited

        panelRound7.setBackground(Color.white);
    }//GEN-LAST:event_panelRound7MouseExited

    public void enableColor() {
        panelRound1.setBackground(Color.white);
        panelRound2.setBackground(Color.white);
        panelRound4.setBackground(Color.white);
        panelRound5.setBackground(Color.white);
        panelRound6.setBackground(Color.white);
        panelRound7.setBackground(Color.white);
    }

    int flag1 = 0, flag2 = 0, flag3 = 0, flag4 = 0, flag5 = 0, flag6 = 0;

    private void resetFlags() {
        flag1 = 0;
        flag2 = 0;
        flag3 = 0;
        flag4 = 0;
        flag5 = 0;
        flag6 = 0;
    }
    private void panelRound1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound1MouseClicked
        resetFlags();
        flag1 = 1;
        enableColor();
        panelRound1.setBackground(new Color(176, 210, 235, 255));
        closeFrom();
        trangchu.setVisible(true);
    }//GEN-LAST:event_panelRound1MouseClicked

    private void panelRound2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound2MouseClicked
        resetFlags();
        flag2 = 1;
        enableColor();
        panelRound2.setBackground(new Color(176, 210, 235, 255));
        closeFrom();
        qlnv.setVisible(true);
    }//GEN-LAST:event_panelRound2MouseClicked

    private void panelRound4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound4MouseClicked
        resetFlags();
        flag4 = 1;
        enableColor();
        panelRound4.setBackground(new Color(176, 210, 235, 255));
        closeFrom();
        capquyen.setVisible(true);
    }//GEN-LAST:event_panelRound4MouseClicked

    private void panelRound5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound5MouseClicked
        resetFlags();
        flag5 = 1;
        enableColor();
        panelRound5.setBackground(new Color(176, 210, 235, 255));
        closeFrom();
        nhomquyen.setVisible(true);
    }//GEN-LAST:event_panelRound5MouseClicked

    private void panelRound6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound6MouseClicked
        resetFlags();
        flag6 = 1;
        enableColor();
        panelRound6.setBackground(new Color(176, 210, 235, 255));
        closeFrom();
        thongke.setVisible(true);
        
    }//GEN-LAST:event_panelRound6MouseClicked

    private void panelRound7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound7MouseClicked
     int n = JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc muốn đăng xuất?", "Thông báo", JOptionPane.YES_NO_OPTION);
        try {
            if(n == JOptionPane.YES_OPTION){
//                Database.conn.close();
                this.dispose();
                new frmDangNhap().setVisible(true);
        }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_panelRound7MouseClicked

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        if (txtSearch.isSelected()) {
            int option = txtSearch.getSelectedIndex();
            if (option == 0) {
                loadQLNV(1, txtSearch.getText());
            } else if (option == 1) {
                loadQLNV(2, txtSearch.getText());
            } else if (option == 2) {
                loadQLNV(3, txtSearch.getText());
            } else {
                loadQLNV(4, txtSearch.getText());
            }
        }
    }//GEN-LAST:event_txtSearchKeyReleased

    private void panelRound15MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound15MouseEntered
        panelRound15.setBackground(new Color(204, 204, 204));
    }//GEN-LAST:event_panelRound15MouseEntered

    private void panelRound15MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound15MouseExited

        panelRound15.setBackground(new Color(211, 213, 214, 255));
    }//GEN-LAST:event_panelRound15MouseExited

    private void panelRound15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound15MouseClicked
        loadQLNV(0, "");
    }//GEN-LAST:event_panelRound15MouseClicked

    private void panelRound16MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound16MouseExited
        panelRound16.setBackground(new Color(211, 213, 214, 255));
    }//GEN-LAST:event_panelRound16MouseExited

    private void panelRound17MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound17MouseExited
        panelRound17.setBackground(new Color(211, 213, 214, 255));
    }//GEN-LAST:event_panelRound17MouseExited

    private void panelRound18MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound18MouseExited
        panelRound18.setBackground(new Color(211, 213, 214, 255));
    }//GEN-LAST:event_panelRound18MouseExited

    private void panelRound19MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound19MouseExited
        panelRound19.setBackground(new Color(211, 213, 214, 255));
    }//GEN-LAST:event_panelRound19MouseExited

    private void panelRound16MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound16MouseEntered
        panelRound16.setBackground(new Color(1, 166, 90, 255));
    }//GEN-LAST:event_panelRound16MouseEntered

    private void panelRound17MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound17MouseEntered
        panelRound17.setBackground(new Color(243, 156, 17, 255));
    }//GEN-LAST:event_panelRound17MouseEntered

    private void panelRound18MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound18MouseEntered
        panelRound18.setBackground(new Color(221, 76, 57, 255));
    }//GEN-LAST:event_panelRound18MouseEntered

    private void panelRound19MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound19MouseEntered
        panelRound19.setBackground(new Color(0, 192, 239, 255));
    }//GEN-LAST:event_panelRound19MouseEntered

    private void panelRound18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound18MouseClicked
//        int row = tbNhanVien.getSelectedRow();
//        String maNV = tbNhanVien.getValueAt(row, 0).toString();
        int check = JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc muốn xoá nhân viên này !", "Thông báo", JOptionPane.YES_NO_OPTION);
        if (check == JOptionPane.YES_OPTION) {
            try {
                int selectedRow = tbNhanVien.getSelectedRow();
                String maNV = (String) tbNhanVien.getValueAt(selectedRow, 0);
                String query = "update qldata.nhanvien set trangthai = 0 where MANHANVIEN = '" + maNV + "'";
                String query2 = "drop user " + maNV + "";
                String query1 = "commit";
                if (Database.excuteUpdate(query) && Database.excuteUpdate(query1)&& Database.excuteUpdate(query2)) {
                    JOptionPane.showMessageDialog(rootPane, "Xoá nhân viên thành công thành công !");
                    loadQLNV(0, "");
                } else {
                    if (Database.baoLoi.contains("insufficient privileges")) {
                        JOptionPane.showMessageDialog(rootPane, "Bạn không có quyền xoá bảng này !");
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Xoá nhân viên thất bại thất bại !");
                    }
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Chưa chọn nhân viên cần xoá !");
            }
            loadQLNV(0, "");
        }
    }//GEN-LAST:event_panelRound18MouseClicked

    private void panelRound16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound16MouseClicked
        frmThemNVMoi nv = new frmThemNVMoi();
        nv.setVisible(true);
        nv.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_panelRound16MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        String value = listDoiTuong.getSelectedValue();
        modelTG.addElement(value);
        model1.removeElement(value);
        listCapQuyen.setModel(modelTG); 
        listDoiTuong.setModel(model1);
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        for (int i = 0; i < model1.getSize(); i++) {
            modelTG.addElement(model1.getElementAt(i));
        }
        listCapQuyen.setModel(modelTG);
        model1.removeAllElements();
        listDoiTuong.setModel(model1);
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        modelTG.removeAllElements();
        listCapQuyen.setModel(modelTG);
        loadList();
        loadUser();
        loadTableName();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String value = listHeThong.getSelectedValue();
        modelTG.addElement(value);
        model2.removeElement(value);
        listCapQuyen.setModel(modelTG);
        listHeThong.setModel(model2);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        for (int i = 0; i < model2.getSize(); i++) {
            modelTG.addElement(model2.getElementAt(i));
        }
        listCapQuyen.setModel(modelTG);
        model2.removeAllElements();
        listHeThong.setModel(model2);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseClicked
        String value = listDoiTuong1.getSelectedValue();
        modelNQTG.addElement(value);
        modelNQ1.removeElement(value);
        listCapQuyen1.setModel(modelNQTG); 
        listDoiTuong1.setModel(modelNQ1);
    }//GEN-LAST:event_jButton6MouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton8MouseClicked

        for (int i = 0; i < modelNQ1.getSize(); i++) {
            modelNQTG.addElement(modelNQ1.getElementAt(i));
        }
        listCapQuyen1.setModel(modelNQTG);
        modelNQ1.removeAllElements();
        listDoiTuong1.setModel(modelNQ1);
    }//GEN-LAST:event_jButton8MouseClicked

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed

    private void cboRole1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboRole1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboRole1ActionPerformed

    private void jButton7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MouseClicked

        String value = listHeThong1.getSelectedValue();
        modelNQTG.addElement(value);
        modelNQ2.removeElement(value);
        listCapQuyen1.setModel(modelNQTG);
        listHeThong1.setModel(modelNQ2);
    }//GEN-LAST:event_jButton7MouseClicked

    private void jButton9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton9MouseClicked
                                        
        for (int i = 0; i < modelNQ2.getSize(); i++) {
            modelNQTG.addElement(modelNQ2.getElementAt(i));
        }
        listCapQuyen1.setModel(modelNQTG);
        modelNQ2.removeAllElements();
        listHeThong1.setModel(modelNQ2);
    }//GEN-LAST:event_jButton9MouseClicked

    private void jButton10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton10MouseClicked
        modelNQTG.removeAllElements();
        listCapQuyen1.setModel(modelTG);
        loadListNQ();
        loadTableName();
    }//GEN-LAST:event_jButton10MouseClicked

    private void dateTufrmAdminPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateTufrmAdminPropertyChange
        // TODO add your handling code here:
        try {
            filterThongKeSanPham();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_dateTufrmAdminPropertyChange

    private void dateDenfrmAdminPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateDenfrmAdminPropertyChange
        // TODO add your handling code here:
        try {
            filterThongKeSanPham();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_dateDenfrmAdminPropertyChange

    private void textOptionfrmAdminKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textOptionfrmAdminKeyReleased
        // TODO add your handling code here:
        try {
            filterThongKeSanPham();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_textOptionfrmAdminKeyReleased

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        // TODO add your handling code here:
        loadDataToTableThongKeProduct(thongKeSanPhamDao.getInstance().getThongKe());
    }//GEN-LAST:event_button1ActionPerformed

    private void button1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_button1MouseClicked

    private void button2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button2MouseClicked
        String username = layMa(cboNV.getSelectedItem().toString());
        String tableName = cboTB.getSelectedItem().toString();
        ListModel<String> modelCapQuyen = listCapQuyen.getModel();
        int n = modelCapQuyen.getSize();     
        String thongBao = "";
        String sql = "";
        try {
               for(int i=0;i<n;i++){
                   String quyen = modelCapQuyen.getElementAt(i);
                   if(quyen.equals("CREATE SESSION")|| quyen.equals("CREATE TABLE")|| quyen.equals("CREATE PROCEDURE")|| quyen.equals("CREATE TRIGGER")){
                        sql = "GRANT "+quyen+" TO " + username;
                        if(Database.excuteUpdate(sql)){
                            thongBao += "Cấp quyền " + quyen + " thành công\n";
                                }
                        else{
                            thongBao += "Cấp quyền " + quyen + " thất bại\n";
                        }
                   }
                   else{
                       sql = "GRANT " + quyen + " ON QLDATA." + tableName + " TO " + username;
                       if(Database.excuteUpdate(sql)){
                            thongBao += "Cấp quyền " + quyen + " thành công\n";
                                }
                        else{
                            thongBao += "Cấp quyền " + quyen + " thất bại\n";
                        }
                   }
               }
               JOptionPane.showMessageDialog(rootPane, thongBao);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_button2MouseClicked

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button2ActionPerformed

    private void button3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button3MouseClicked
        frmXemQuyen xemq = new frmXemQuyen();
        xemq.setVisible(true);
        xemq.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_button3MouseClicked

    private void button4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button4MouseClicked
        String username = layMa(cboNV.getSelectedItem().toString());
        String tableName = cboTB.getSelectedItem().toString();
        String thongBao = "";
        String sql = "";
        try {
               for(int i=0;i<modelTG.getSize();i++){
                   String quyen = (String)modelTG.getElementAt(i);
                   if(quyen.equals("CREATE SESSION")|| quyen.equals("CREATE TABLE")|| quyen.equals("CREATE PROCEDURE")|| quyen.equals("CREATE TRIGGER")){
                        sql = "REVOKE "+quyen+" FROM " + username;
                        if(Database.excuteUpdate(sql)){
                            thongBao += "Thu quyền " + quyen + " thành công\n";
                                }
                        else{
                            thongBao += "Thu quyền " + quyen + " thất bại\n";
                        }
                   }
                   else{
                       sql = "REVOKE " + quyen + " ON QLDATA." + tableName + " FROM " + username;
                       if(Database.excuteUpdate(sql)){
                            thongBao += "Thu quyền " + quyen + " thành công\n";
                                }
                        else{
                            thongBao += "Thu quyền " + quyen + " thất bại\n";
                        }
                   }
               }
               JOptionPane.showMessageDialog(rootPane, thongBao);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_button4MouseClicked

    private void button5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button5MouseClicked
       try {
            String roleName = txtTenNhom.getText();
            String query = "CREATE ROLE "+roleName.toUpperCase()+"";
            if(Database.excuteUpdate(query)){
                JOptionPane.showMessageDialog(rootPane, "Tạo role "+roleName+" thành công !" );
                txtTenNhom.setText("");
                loadRolesName();
            }
            else{
               JOptionPane.showMessageDialog(rootPane, "Role đã tồn tại !" );
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_button5MouseClicked

    private void button6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button6MouseClicked
        try {
            String roleName =(String) cboRole1.getSelectedItem();
            String query = "DROP ROLE "+roleName+"";
            if(Database.excuteUpdate(query)){
                JOptionPane.showMessageDialog(rootPane, "Xoá role "+roleName+" thành công !" );
                loadRolesName();
            }
            else{
               JOptionPane.showMessageDialog(rootPane, "Xoá role thất bại !" );
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_button6MouseClicked

    private void button7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button7MouseClicked
        String username = cboRole.getSelectedItem().toString();
        String tableName = cboTB1.getSelectedItem().toString();
        ListModel<String> modelCapQuyen = listCapQuyen1.getModel();
        int n = modelCapQuyen.getSize();
        String thongBao = "";
        String sql = "";
        try {
            for(int i=0;i<n;i++){
                String quyen = modelCapQuyen.getElementAt(i);
                if(quyen.equals("CREATE SESSION")|| quyen.equals("CREATE TABLE")|| quyen.equals("CREATE PROCEDURE")|| quyen.equals("CREATE TRIGGER")){
                    sql = "GRANT "+quyen+" TO " + username;
                    if(Database.excuteUpdate(sql)){
                        thongBao += "Cấp quyền " + quyen + " thành công\n";
                    }
                    else{
                        thongBao += "Cấp quyền " + quyen + " thất bại\n";
                    }
                }
                else{
                    sql = "GRANT " + quyen + " ON QLDATA." + tableName + " TO " + username;
                    if(Database.excuteUpdate(sql)){
                        thongBao += "Cấp quyền " + quyen + " thành công\n";
                    }
                    else{
                        thongBao += "Cấp quyền " + quyen + " thất bại\n";
                    }
                }
            }
            JOptionPane.showMessageDialog(rootPane, thongBao);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_button7MouseClicked

    private void button8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button8MouseClicked
        String username = cboRole.getSelectedItem().toString();
        String tableName = cboTB1.getSelectedItem().toString();
        String thongBao = "";
        String sql = "";
        try {
            for(int i=0;i<modelNQTG.getSize();i++){
                String quyen = (String)modelNQTG.getElementAt(i);
                if(quyen.equals("CREATE SESSION")|| quyen.equals("CREATE TABLE")|| quyen.equals("CREATE PROCEDURE")|| quyen.equals("CREATE TRIGGER")){
                    sql = "REVOKE "+quyen+" FROM " + username;
                    if(Database.excuteUpdate(sql)){
                        thongBao += "Thu quyền " + quyen + " thành công\n";
                    }
                    else{
                        thongBao += "Thu quyền " + quyen + " thất bại\n";
                    }
                }
                else{
                    sql = "REVOKE " + quyen + " ON QLDATA." + tableName + " FROM " + username;
                    if(Database.excuteUpdate(sql)){
                        thongBao += "Thu quyền " + quyen + " thành công\n";
                    }
                    else{
                        thongBao += "Thu quyền " + quyen + " thất bại\n";
                    }
                }
            }
            JOptionPane.showMessageDialog(rootPane, thongBao);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_button8MouseClicked

    private void button9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button9MouseClicked
        frmXemQuyenRole role = new frmXemQuyenRole();
        role.setVisible(true);
        role.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_button9MouseClicked

    private void button10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button10MouseClicked
        CapQuyenUser cq = new CapQuyenUser();
        cq.setVisible(true);
        cq.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_button10MouseClicked
    
    public static String maNV = "";
    private void tbNhanVienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbNhanVienKeyPressed
        
    }//GEN-LAST:event_tbNhanVienKeyPressed

    private void panelRound19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound19MouseClicked
        try {
            int flag = 0;
            int rowSP = tbNhanVien.getSelectedRow();
            maNV = (String) tbNhanVien.getValueAt(rowSP, 0);
            ResultSet rs = null;
            InputStream is = null;
            String maNV = frmAdmin.maNV;
            String sql = "SELECT HINHANHNV FROM qldata.NHANVIEN WHERE MANHANVIEN ='" + maNV + "'";
            try {
                rs = Database.excuteQuery(sql);
                if (rs.next()) {
                    is = rs.getBinaryStream("HINHANHNV");
                    BufferedImage img = ImageIO.read(is);
                }

            } catch (Exception e) {
                flag = 1;
                JOptionPane.showMessageDialog(rootPane, "Nhân viên chưa có hình ảnh ");

            }
            if (flag != 1) {
                frmHinhAnhNV hinh = new frmHinhAnhNV();
                hinh.setVisible(true);
                hinh.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Chưa chọn nhân viền cần xem hình");
        }
    }//GEN-LAST:event_panelRound19MouseClicked
    
    private void panelRound17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound17MouseClicked
        try {
            int flag = 0;
            int rowSP = tbNhanVien.getSelectedRow();
            maNV = (String) tbNhanVien.getValueAt(rowSP, 0);
            frmSuaNV suaNV = new frmSuaNV();
            suaNV.setVisible(true);
            suaNV.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Chưa chọn nhân viên cần sửa");
        }
    }//GEN-LAST:event_panelRound17MouseClicked

    private void textfrmTKdoanhThuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textfrmTKdoanhThuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textfrmTKdoanhThuActionPerformed

    private void button11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button11ActionPerformed
        // TODO add your handling code here:
        loadDataToTableThongKeDoanhThu(thongKeDoanhThuDao.getInstance().getThongKe());
    }//GEN-LAST:event_button11ActionPerformed

    private void dateTuFrmTKdoanhThuPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateTuFrmTKdoanhThuPropertyChange
        // TODO add your handling code here:
        try {
            filterThongKeDoanhThu();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_dateTuFrmTKdoanhThuPropertyChange

    private void dateDenFrmTKdoanhThuPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateDenFrmTKdoanhThuPropertyChange
        // TODO add your handling code here:
        try {
            filterThongKeDoanhThu();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_dateDenFrmTKdoanhThuPropertyChange

    private void textfrmTKdoanhThuKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textfrmTKdoanhThuKeyReleased
        // TODO add your handling code here:
        try {
            filterThongKeDoanhThu();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_textfrmTKdoanhThuKeyReleased

    private void button12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button12ActionPerformed
        // TODO add your handling code here:
        String tongDoanhThu= lblTongDoanhThu.getText();
        String tongLoiNhuan = lblTongLoiNhuan.getText();
        String tu = chuyenDoi(dateTuFrmTKdoanhThu.getDate());
        String den = chuyenDoi(dateDenFrmTKdoanhThu.getDate());
        if(tu.equals("null") || den.equals("null")){
            JOptionPane.showMessageDialog(rootPane, "Vui lòng chọn ngày bắt đầu và kết thúc");
        }else{
        final String from ="buithanhluan070902@gmail.com";
        final String pass ="hjzzsnkexsqmymuc";
        final String to ="bthanhluan0709@gmail.com";
        
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        
      Authenticator auth = new Authenticator() {
           @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, pass);
            }
      };
      
      Session session = Session.getInstance(props, auth);
     
      
          try {
            Multipart mul = new MimeMultipart();
            MimeBodyPart minePdf = new MimeBodyPart();
            minePdf.attachFile("C:/Users/Admin/Desktop/HD002.pdf");
            MimeBodyPart mineText = new MimeBodyPart();
            mineText.setText("Tệp báo cáo doanh thu lợi nhuận");
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Thống kê doanh thu lợi nhuận");
            message.setText("Kính gửi Ban giám đốc \n"
                               +"Doanh thu và lợi nhuận"
                               + "\n"
                               +"Từ ngày:"+ tu 
                               +" - Đến ngày:" + den
                               +"\n"
                               +"Tổng doanh thu:" + tongDoanhThu
                               +"\n"
                               +"Tổng lợi nhuận:"+ tongLoiNhuan);   
            
//            mul.addBodyPart(mineText);
//            mul.addBodyPart(minePdf);
//            
//            message.setContent(mul);
            Transport.send(message);
            JOptionPane.showMessageDialog(rootPane, "Gửi báo cáo thành công");
           
                    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Gửi báo cáo thất bại");
        } 
        }
        
      
       
      
    }//GEN-LAST:event_button12ActionPerformed

    private void txtoptionSLtonKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtoptionSLtonKeyReleased
        // TODO add your handling code here:
        try {
            filterSlTon();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_txtoptionSLtonKeyReleased

    private void button14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button14ActionPerformed
        // TODO add your handling code here:
        loadDataToTableThongKeProductSlTon(thongKeSanPhamDao.getInstance().getThongKeSlTon());
    }//GEN-LAST:event_button14ActionPerformed

    private void button13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button13ActionPerformed
        // TODO add your handling code here:
         String url = "C:/Users/Admin/Desktop/";
       

        String fileName = "dsSanPham.pdf";
        File file = new File(url + fileName);

        int counter = 1;
        while (file.exists()) {
            fileName = "dsSanPham(" + counter + ").pdf";
            file = new File(url + fileName);
            counter++;
        }
        
        
        Document doc = new Document();
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(file));
            doc.open();

            BaseFont bf = BaseFont.createFont("Roboto/Roboto-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(bf, 12);
            Font titleFont = new Font(bf, 18, Font.BOLD); 
           
            PdfPTable headerTable1 = new PdfPTable(1);
            headerTable1.setWidthPercentage(100);
            headerTable1.setSpacingAfter(15);

            PdfPCell cellTT1 = new PdfPCell(new Phrase("Công ty TNHH Thời Trang và Mỹ Phẩm Châu Âu", font));
            cellTT1.setBorder(Rectangle.NO_BORDER);
            PdfPCell cellTT2 = new PdfPCell(new Phrase("Địa chỉ: Lầu 12, Toà Nhà Sonatus, 15 Lê Thánh Tôn, Phường Bến Nghé, Quận 1, TP.HCM", font));
            cellTT2.setBorder(Rectangle.NO_BORDER);
            PdfPCell cellTT3 = new PdfPCell(new Phrase("Điện thoại: +84(28) 3825 6523", font));
            cellTT3.setBorder(Rectangle.NO_BORDER);

            headerTable1.addCell(cellTT1);
            headerTable1.addCell(cellTT2);
            headerTable1.addCell(cellTT3);
            doc.add(headerTable1);
            
            Paragraph title = new Paragraph("DANH SÁCH SẢN PHẨM SẮP HẾT HÀNG", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            doc.add(title);

            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
            headerTable.setSpacingAfter(10);

            headerTable.setWidths(new float[]{1, 1});

            PdfPCell cell1 = new PdfPCell(new Phrase("Mã phiếu nhập: "));
            cell1.setBorder(Rectangle.NO_BORDER);
            PdfPCell cell2 = new PdfPCell(new Phrase("Nhà cung cấp: " ));
            cell2.setBorder(Rectangle.NO_BORDER);

            PdfPCell cell3 = new PdfPCell(new Phrase("Nhân viên lập: " ));
            cell3.setBorder(Rectangle.NO_BORDER);
            PdfPCell cell4 = new PdfPCell(new Phrase("Thời gian lập: "));
            cell4.setBorder(Rectangle.NO_BORDER);

            headerTable.addCell(cell1);
            headerTable.addCell(cell2);
            headerTable.addCell(cell3);
            headerTable.addCell(cell4);

//            doc.add(headerTable);

            PdfPTable tbl = new PdfPTable(6);
            tbl.setWidthPercentage(100);
            tbl.setWidths(new float[]{0.5f, 2f, 1.2f, 1.2f, 1.8f, 1.5f});
            
            PdfPCell header1 = new PdfPCell(new Phrase("STT", font));
            header1.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell header2 = new PdfPCell(new Phrase("Mã sản phẩm", font));
            header2.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell header3 = new PdfPCell(new Phrase("Tên sản phẩm", font));
            header3.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell header4 = new PdfPCell(new Phrase("Màu sắc", font));
            header4.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell header5 = new PdfPCell(new Phrase("Kích thước", font));
            header5.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell header6 = new PdfPCell(new Phrase("Số lượng tồn", font));
            header6.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            tbl.addCell(header1);
            tbl.addCell(header2);
            tbl.addCell(header3);
            tbl.addCell(header4);
            tbl.addCell(header5);
            tbl.addCell(header6);
            

            
            int stt = 1;
            for (int i = 0; i < tbl_sanphamtonAdmin.getRowCount(); i++) {
                String masp = tbl_sanphamtonAdmin.getValueAt(i, 0).toString();
                String tensp = tbl_sanphamtonAdmin.getValueAt(i, 1).toString();
                String ms = tbl_sanphamtonAdmin.getValueAt(i, 2).toString();
                String kt = tbl_sanphamtonAdmin.getValueAt(i, 3).toString();
                String soluongton = tbl_sanphamtonAdmin.getValueAt(i, 4).toString();
               

               
               
                
                PdfPCell cellSTT = new PdfPCell(new Phrase(String.valueOf(stt), font));
                cellSTT.setHorizontalAlignment(Element.ALIGN_CENTER);
                tbl.addCell(cellSTT);
                
                PdfPCell cellTenSP = new PdfPCell(new Phrase(masp, font));
                cellTenSP.setHorizontalAlignment(Element.ALIGN_CENTER);
                tbl.addCell(cellTenSP);
                
                PdfPCell cellMauSac = new PdfPCell(new Phrase(tensp, font));
                cellMauSac.setHorizontalAlignment(Element.ALIGN_CENTER);
                tbl.addCell(cellMauSac);
                
                PdfPCell cellKT= new PdfPCell(new Phrase(ms, font));
                cellKT.setHorizontalAlignment(Element.ALIGN_CENTER);
                tbl.addCell(cellKT);
                
                PdfPCell cellSLN = new PdfPCell(new Phrase(kt, font));
                cellSLN.setHorizontalAlignment(Element.ALIGN_RIGHT);
                tbl.addCell(cellSLN);
                
                PdfPCell cellGiaNhap = new PdfPCell(new Phrase(soluongton, font));
                cellGiaNhap.setHorizontalAlignment(Element.ALIGN_RIGHT);
                tbl.addCell(cellGiaNhap);
                
                
                stt++;
            }

            doc.add(tbl);
            
            PdfPTable bottomTable = new PdfPTable(2);
            bottomTable.setWidthPercentage(100);
            bottomTable.setSpacingBefore(20);

            bottomTable.setWidths(new float[]{1.9f, 1});


            doc.add(bottomTable);
             JOptionPane.showMessageDialog(rootPane, "Xuất phiếu thành công !");
             doc.close();
            final String from ="buithanhluan070902@gmail.com";
        final String pass ="hjzzsnkexsqmymuc";
        final String to ="doanquocanh156@gmail.com";
        
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        
      Authenticator auth = new Authenticator() {
           @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, pass);
            }
      };
      
      Session session = Session.getInstance(props, auth);
     
           
          try {
            Multipart mul = new MimeMultipart();
            MimeBodyPart minePdf = new MimeBodyPart();
//            minePdf.attachFile("C:/Users/Admin/Desktop/"+fileName+"");
            minePdf.attachFile(file);
            MimeBodyPart mineText = new MimeBodyPart();
            mineText.setText("Tệp thống kê sản phẩm tồn");
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Danh sách sản phẩm sắp hết hàng");
            message.setText("Đây là nội dung");   
            
            mul.addBodyPart(mineText);
            mul.addBodyPart(minePdf);
            
            message.setContent(mul);
            Transport.send(message);
            System.out.println("succes");
            JOptionPane.showMessageDialog(rootPane, "Gửi báo cáo thành công");
           
                    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Gửi báo cáo thất bại");
        } 
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
            
           
        
    }//GEN-LAST:event_button13ActionPerformed

    private void ckb_soluongtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckb_soluongtonActionPerformed
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_ckb_soluongtonActionPerformed

    private void ckb_soluongtonKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ckb_soluongtonKeyReleased
        // TODO add your handling code here:
        
    }//GEN-LAST:event_ckb_soluongtonKeyReleased

    private void ckb_soluongtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ckb_soluongtonMouseClicked
        // TODO add your handling code here:
        if(ckb_soluongton.isSelected()){
            loadDataToTableThongKeProductSlTon(thongKeSanPhamDao.getInstance().getThongKeSlTon1());
        }else{
            loadDataToTableThongKeProductSlTon(thongKeSanPhamDao.getInstance().getThongKeSlTon());

        }
        
    }//GEN-LAST:event_ckb_soluongtonMouseClicked

    private void ckb_soluongtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ckb_soluongtonMouseReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_ckb_soluongtonMouseReleased

    private void ckb_soluongtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ckb_soluongtonMousePressed
        // TODO add your handling code here:
//                loadDataToTableThongKeProductSlTon(thongKeSanPhamDao.getInstance().getThongKeSlTon());

    }//GEN-LAST:event_ckb_soluongtonMousePressed

    public void closeFrom() {
        trangchu.setVisible(false);
        qlnv.setVisible(false);
        capquyen.setVisible(false);
        nhomquyen.setVisible(false);
        thongke.setVisible(false);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backround;
    private sample.message.Button button1;
    private sample.message.Button button10;
    private sample.message.Button button11;
    private sample.message.Button button12;
    private sample.message.Button button13;
    private sample.message.Button button14;
    private sample.message.Button button2;
    private sample.message.Button button3;
    private sample.message.Button button4;
    private sample.message.Button button5;
    private sample.message.Button button6;
    private sample.message.Button button7;
    private sample.message.Button button8;
    private sample.message.Button button9;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel capquyen;
    private combobox.Combobox cboNV;
    private combobox.Combobox cboRole;
    private combobox.Combobox cboRole1;
    private combobox.Combobox cboTB;
    private combobox.Combobox cboTB1;
    private checkbox.JCheckBoxCustom ckb_soluongton;
    private javaswingdev.swing.titlebar.ComponentResizer componentResizer1;
    private com.toedter.calendar.JDateChooser dateDenFrmTKdoanhThu;
    private com.toedter.calendar.JDateChooser dateDenfrmAdmin;
    private com.toedter.calendar.JDateChooser dateTuFrmTKdoanhThu;
    private com.toedter.calendar.JDateChooser dateTufrmAdmin;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblTongDoanhThu;
    private javax.swing.JLabel lblTongLoiNhuan;
    private javax.swing.JList<String> listCapQuyen;
    private javax.swing.JList<String> listCapQuyen1;
    private javax.swing.JList<String> listDoiTuong;
    private javax.swing.JList<String> listDoiTuong1;
    private javax.swing.JList<String> listHeThong;
    private javax.swing.JList<String> listHeThong1;
    private javax.swing.JPanel menu;
    private javax.swing.JPanel nhomquyen;
    private javax.swing.JPanel panel;
    private boder_panel.PanelRound panelRound1;
    private boder_panel.PanelRound panelRound10;
    private boder_panel.PanelRound panelRound11;
    private boder_panel.PanelRound panelRound12;
    private boder_panel.PanelRound panelRound13;
    private boder_panel.PanelRound panelRound14;
    private boder_panel.PanelRound panelRound15;
    private boder_panel.PanelRound panelRound16;
    private boder_panel.PanelRound panelRound17;
    private boder_panel.PanelRound panelRound18;
    private boder_panel.PanelRound panelRound19;
    private boder_panel.PanelRound panelRound2;
    private boder_panel.PanelRound panelRound20;
    private boder_panel.PanelRound panelRound21;
    private boder_panel.PanelRound panelRound22;
    private boder_panel.PanelRound panelRound28;
    private boder_panel.PanelRound panelRound29;
    private boder_panel.PanelRound panelRound3;
    private boder_panel.PanelRound panelRound4;
    private boder_panel.PanelRound panelRound5;
    private boder_panel.PanelRound panelRound6;
    private boder_panel.PanelRound panelRound7;
    private boder_panel.PanelRound panelRound8;
    private boder_panel.PanelRound panelRound9;
    private javax.swing.JPanel qlnv;
    private textfield.SearchOption searchOption1;
    private textfield.SearchOption searchOption2;
    private table.TableCustom tableCustom1;
    private table.TableCustom tableCustom2;
    private table.TableCustom tableCustom3;
    private table.TableCustom tableCustom4;
    private table.TableScrollButton tableScrollButton1;
    private table.TableScrollButton tableScrollButton2;
    private table.TableScrollButton tableScrollButton3;
    private table.TableScrollButton tableScrollButton4;
    private javax.swing.JTable tbNhanVien;
    private javax.swing.JTable tblFrmThongKeDoanhThu;
    private javax.swing.JTable tblThongKeSanPhamfrmAdmin;
    private javax.swing.JTable tbl_sanphamtonAdmin;
    private textfield.TextFieldSearchOption textOptionfrmAdmin;
    private textfield.TextFieldSearchOption textfrmTKdoanhThu;
    private javax.swing.JPanel thongke;
    private javax.swing.JPanel thongtin;
    private javaswingdev.swing.titlebar.TitleBar titleBar1;
    private javax.swing.JPanel trangchu;
    private textfield.TextFieldSearchOption txtSearch;
    private textfield.TextField txtTenNhom;
    private textfield.TextFieldSearchOption txtoptionSLton;
    // End of variables declaration//GEN-END:variables
}
