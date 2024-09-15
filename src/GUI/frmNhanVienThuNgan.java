/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import DAO.Database;
import DAO.chiTietHoaDonDao;
import DAO.dataAdmin;
import DAO.hoaDonDao;
import DAO.khachHangDao;
import DAO.kichThuocDao;
import DAO.loaiSanPhamDao;
import DAO.mauSacDao;
import DAO.nhaCungCapDao;
import DAO.nhanVienDao;
import DAO.phieuNhapDao;
import DAO.sanPhamDao;
import static GUI.frmAdmin.maNV;
import static GUI.frmNhanVienKho.maPN;
import POJO.cboItem;
import POJO.chiTietHoaDonPojo;
import POJO.hoaDonPojo;
import POJO.khachHangPojo;
import POJO.kichThuocPojo;
import POJO.loaiSanPhamPojo;
import POJO.mauSacPojo;
import POJO.nhaCungCapPojo;
import POJO.nhanVienPojo;
import POJO.phieuNhapPojo;
import POJO.sanPhamPojo;
import controller.timKiemSanPhamDao;
import java.awt.Color;
import static java.awt.SystemColor.text;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import javax.swing.SwingUtilities;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

import javax.swing.table.DefaultTableModel;
import table.TableCustom;
import textfield.SearchOptinEvent;
import textfield.SearchOption;

/**
 *
 * @author AnhDoan
 */
public class frmNhanVienThuNgan extends javax.swing.JFrame {
    
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    private ArrayList<sanPhamPojo> allProduct;
    private ArrayList<khachHangPojo> allKhachHang;
    private String MaPhieu;
    private String MaKhachHang;
    private ArrayList<chiTietHoaDonPojo> CTPhieu;
    
    public frmNhanVienThuNgan() {
        initComponents();
        loadQLSP(0, "", "Tất cả", "Tất cả", "Tất cả");
        loadLSP();
        loadM();
        loadKT();
        loadTextSearch();
        loadLSP1();
        loadM1();
        loadKT1();
        loadTextSearch1();
        loadTextField();
        loadDateVN();
        loadDSHDDATE(0,"","null","null");
        loadTextSearchKH();
        loadQLKH(0,"");
        dataAdmin.Connect();
        allProduct = sanPhamDao.getInstance().layDSSP1();
        allKhachHang = khachHangDao.getInstance().layDSKH();
        tableCustom1.apply(jScrollPane1, TableCustom.TableType.DEFAULT);
        tableCustom2.apply(jScrollPane2, TableCustom.TableType.DEFAULT);
        tableCustom3.apply(jScrollPane3, TableCustom.TableType.DEFAULT);
        tableCustom4.apply(jScrollPane4, TableCustom.TableType.DEFAULT);
        tableCustom5.apply(jScrollPane5, TableCustom.TableType.DEFAULT);
        
        MaKhachHang = createIdKhachHang(khachHangDao.getInstance().layDSKH());
        MaPhieu = createId(hoaDonDao.getInstance().layDSHD());
        txt_maHD.setText(MaPhieu);
        
        CTPhieu = new ArrayList<chiTietHoaDonPojo>();
        init();
        lbNV.setText(tenNV());
        txt_MaNhavien.setText(maNV());
        txt_hotenNhanVien.setText(tenNV());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                dangNhapLanDau();
            }
        });
        
        
    }
    int checkLanDau = 0;
    public void dangNhapLanDau() {
        if (Database.user.toLowerCase().equals(Database.password.toLowerCase())) {
            JOptionPane.showMessageDialog(this, "Bạn cần thay đổi mật khẩu cho lần đầu đăng nhập !");
            resetFlags();
            flag6 = 1;
            enableColor();
            panelRound6.setBackground(new Color(176, 210, 235, 255));
            closeFrom();
            from6.setVisible(true);
            checkLanDau = 1;
        }
    }
    public void loadTextSearchKH() {
        textFielKhachHang.addEventOptionSelected(new SearchOptinEvent() {
            @Override
            public void optionSelected(SearchOption option, int index) {
                textFielKhachHang.setHint("Nhập " + option.getName() + "...");
            }
        });
        textFielKhachHang.addOption(new SearchOption("tên..", new ImageIcon(getClass().getResource("/icons/user.png"))));
        textFielKhachHang.addOption(new SearchOption("số điện thoại..", new ImageIcon(getClass().getResource("/icons/tel.png"))));
        
    }
    public void loadQLKH(int checkSQL, String text) {
        try {
            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(new String[]{"Mã khách hàng", "Tên khách hàng", "Ngày sinh", "Giới tính", "Số điện thoại", "Email", "Địa chỉ"});
            khachHangDao nvDAO = new khachHangDao();
            ArrayList<khachHangPojo> dsNV = nvDAO.layDSKH4(checkSQL, text);
            for (khachHangPojo nv : dsNV) {
                String maKH = nv.getMaKH();
                String hoTen = nv.getTenKH();
                String ngaySinh = nv.getNgaySinh();
                String gioiTinh = nv.getGioiTinh();
                String sdt = nv.getSoDt();
                String email = nv.getEmail();
                String diaChi = nv.getDiaChi();
                if(ngaySinh == null){
                    ngaySinh = "không xác định";
                }
                if(email == null){
                    email = "không xác định";
                }
                if(diaChi == null){
                    diaChi ="không xác định";
                }
                if(gioiTinh == null){
                    gioiTinh ="không xác định";
                }
                
                model.addRow(new Object[]{maKH, hoTen, ngaySinh, gioiTinh, sdt, email, diaChi});
            }
            tblKhachHang.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void init() {
        titleBar1.initJFram(this);
    }
    
 

    private String maNV(){
        String name ="";
        ArrayList<nhanVienPojo> dsnv = new nhanVienDao().layDSNV2(Database.user.toUpperCase());
        for(nhanVienPojo nv : dsnv){
            name = nv.getMaNhanVien();
        }
        
        return name;
    }
    
    private String tenNV(){
        String name ="";
        ArrayList<nhanVienPojo> dsnv = new nhanVienDao().layDSNV2(Database.user.toUpperCase());
        for(nhanVienPojo nv : dsnv){
            name = nv.getHoTen();
        }
        
        return name;
    }
    private String tenNV1(String maNv){
        String name ="";
        ArrayList<nhanVienPojo> dsnv = new nhanVienDao().layDSNV2(maNv);
        for(nhanVienPojo nv : dsnv){
            name = nv.getHoTen();
        }
        
        return name;
    }
    
    public void loadTextSearch() {
        txtSearch.addEventOptionSelected(new SearchOptinEvent() {
            @Override
            public void optionSelected(SearchOption option, int index) {
                txtSearch.setHint("Nhập " + option.getName() + "...");
            }
        });
        txtSearch.addOption(new SearchOption("tên sản phẩm..", new ImageIcon(getClass().getResource("/icons/product.png"))));
        txtSearch.addOption(new SearchOption("mã sản phẩm..", new ImageIcon(getClass().getResource("/icons/code.png"))));
    }
    public void loadTextField() {
        textFieldSearchOption1.addEventOptionSelected(new SearchOptinEvent() {
            @Override
            public void optionSelected(SearchOption option, int index) {
                textFieldSearchOption1.setHint("Nhập " + option.getName() + "...");
            }
        });
        textFieldSearchOption1.addOption(new SearchOption("mã hoá đơn", new ImageIcon(getClass().getResource("/icons/code.png"))));
        textFieldSearchOption1.addOption(new SearchOption("mã khách hàng", new ImageIcon(getClass().getResource("/icons/gr.png"))));
        textFieldSearchOption1.addOption(new SearchOption("mã nhân viên", new ImageIcon(getClass().getResource("/icons/user.png"))));
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
    public void loadDSHDDATE(int check, String text,String dateFrom,String dateTo) {
        try {
            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(new String[]{"Mã hoá đơn", "Khách hàng", "Tên nhân viên", "Thời gian lập","Tổng tiền"});
            hoaDonDao hdDAO = new hoaDonDao();
            ArrayList<hoaDonPojo> dsHD = hdDAO.loadDSHDDATE(check, text, dateFrom, dateTo);
            for (hoaDonPojo pn : dsHD) {
                String maHd = pn.getMaHD();
                String maKh = tenKH(pn.getMaKH());
                if((pn.getMaKH()) == null){
                    maKh ="Khách vãng lai";
                }
                String maNv = pn.getMaNhanVien();
                String tenNv = tenNV1(maNv);
                Timestamp ngayLap = pn.getNgayLap();
                double tongTien = pn.getTongTien();
                model.addRow(new Object[]{maHd,maKh,tenNv,ngayLap,formatter.format(tongTien)+ " VNĐ"});
            }

            tbHoaDon.setModel(model);

        } catch (Exception e) {
         
        }
    }
    private String tenKH(String maKH){
        String khname ="";
        ArrayList<khachHangPojo> dskh = new khachHangDao().layDSKH1(maKH);
        for(khachHangPojo kh : dskh){
            khname = kh.getTenKH();
        }
        
        return khname;
    }
    public void loadTenKH(String sdt){
        khachHangDao khDao = new khachHangDao();
        ArrayList<khachHangPojo> ds = khDao.layDSKH2(sdt);
        for(khachHangPojo kh : ds){
            if(kh.getSoDt().equals(sdt))
            {
                txt_Khachhang.setText(kh.getTenKH());
            
            }
               
        }
        
    }
    public void loadDateVN(){
        dateTU.setLocale(new Locale("vi", "VN"));
        dateDEN.setLocale(new Locale("vi", "VN"));
        
    }
    
    public void loadLSP() {
        loaiSanPhamDao spDAO = new loaiSanPhamDao();
        ArrayList<loaiSanPhamPojo> dsLSP = spDAO.layDSLSP();
        cboLoai.removeAllItems();
        cboLoai.addItem("Tất cả");
        for (loaiSanPhamPojo sp : dsLSP) {
            cboLoai.addItem(sp.getTenLoaiSanPham());
        }
    }

    public void loadM() {
        mauSacDao mauDAO = new mauSacDao();
        ArrayList<mauSacPojo> dsM = mauDAO.layDSMS();
        cboMau.removeAllItems();
        cboMau.addItem("Tất cả");
        for (mauSacPojo m : dsM) {
            cboMau.addItem(m.getTenMauSac());
        }
    }

    public void loadKT() {
        kichThuocDao ktDAO = new kichThuocDao();
        ArrayList<kichThuocPojo> dsKT = ktDAO.layDSKT();
        cboKT.removeAllItems();
        cboKT.addItem("Tất cả");
        for (kichThuocPojo kt : dsKT) {
            cboKT.addItem(kt.getTenKichThuoc());
        }
    }

//    public String layMa(String textCbo) {
//        String ma;
//        if (textCbo.equals("Tất cả")) {
//            return "Tất cả";
//        } else {
//            String[] mang = textCbo.split(" - ");
//            ma = mang[0];
//        }
//        return ma;
//    }
    public void loadQLSP(int checkSQL, String text, String loaiSp, String ms, String kt) {
        try {
            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(new String[]{"Mã sản phẩm","Mã sản phẩm", "Tên sản phẩm", "Giá bán", "Số lượng tồn", "Loại sản phẩm", "Màu Sắc", "Kích thước"});
            sanPhamDao spDAO = new sanPhamDao();
            ArrayList<sanPhamPojo> dsSP = spDAO.layDSSP(checkSQL, text, loaiSp, ms, kt);
            for (sanPhamPojo nv : dsSP) {
                String maSP = nv.getMaSanPham();
                String tenSP = nv.getTenSanPham();
                double giaBan = nv.getGiaBan();
                int soLuongTon = nv.getSoLuongTon();
                String lSanpham = nv.getTenLoaiSanPham();
                String mauSac = nv.getTenMauSac();
                String kichThuoc = nv.getTenKichThuoc();
                String code = nv.getCode();
//                String chucVu = nv.getChucVu();
                model.addRow(new Object[]{maSP, code, tenSP, formatter.format(giaBan)+" VNĐ", soLuongTon, lSanpham, mauSac, kichThuoc});
            }
            tbSanPham.setModel(model);
            tbSanPham.getColumnModel().getColumn(0).setMinWidth(0);
            tbSanPham.getColumnModel().getColumn(0).setMaxWidth(0);
            tbSanPham.getColumnModel().getColumn(0).setWidth(0);
            tbSanPham.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblSanPham.setModel(model);
            tblSanPham.getColumnModel().getColumn(0).setMinWidth(0);
            tblSanPham.getColumnModel().getColumn(0).setMaxWidth(0);
            tblSanPham.getColumnModel().getColumn(0).setWidth(0);
            tblSanPham.getColumnModel().getColumn(0).setPreferredWidth(0);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void loadTextSearch1() {
        txtSearch1.addEventOptionSelected(new SearchOptinEvent() {
            @Override
            public void optionSelected(SearchOption option, int index) {
                txtSearch1.setHint("Nhập " + option.getName() + "...");
            }
        });
        txtSearch1.addOption(new SearchOption("tên sản phẩm..", new ImageIcon(getClass().getResource("/icons/product.png"))));
        txtSearch1.addOption(new SearchOption("mã sản phẩm..", new ImageIcon(getClass().getResource("/icons/code.png"))));
    }
    
    public void loadLSP1() {
        loaiSanPhamDao spDAO = new loaiSanPhamDao();
        ArrayList<loaiSanPhamPojo> dsLSP = spDAO.layDSLSP();
        cboLoai1.removeAllItems();
        cboLoai1.addItem("Tất cả");
        for (loaiSanPhamPojo sp : dsLSP) {
            cboLoai1.addItem(sp.getTenLoaiSanPham());
        }
    }

    public void loadM1() {
        mauSacDao mauDAO = new mauSacDao();
        ArrayList<mauSacPojo> dsM = mauDAO.layDSMS();
        cboMau1.removeAllItems();
        cboMau1.addItem("Tất cả");
        for (mauSacPojo m : dsM) {
            cboMau1.addItem(m.getTenMauSac());
        }
    }

    public void loadKT1() {
        kichThuocDao ktDAO = new kichThuocDao();
        ArrayList<kichThuocPojo> dsKT = ktDAO.layDSKT();
        cboKT1.removeAllItems();
        cboKT1.addItem("Tất cả");
        for (kichThuocPojo kt : dsKT) {
            cboKT1.addItem(kt.getTenKichThuoc());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tableCustom1 = new table.TableCustom();
        tableCustom2 = new table.TableCustom();
        tableCustom3 = new table.TableCustom();
        tableCustom4 = new table.TableCustom();
        tableCustom5 = new table.TableCustom();
        backround = new javax.swing.JPanel();
        panel = new javax.swing.JPanel();
        thongtin = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        lbNV = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        menu = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        panelRound1 = new boder_panel.PanelRound();
        jLabel4 = new javax.swing.JLabel();
        panelRound2 = new boder_panel.PanelRound();
        jLabel5 = new javax.swing.JLabel();
        panelRound3 = new boder_panel.PanelRound();
        jLabel6 = new javax.swing.JLabel();
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
        from1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        panelRound11 = new boder_panel.PanelRound();
        panelRound12 = new boder_panel.PanelRound();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        panelRound13 = new boder_panel.PanelRound();
        panelRound21 = new boder_panel.PanelRound();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        panelRound25 = new boder_panel.PanelRound();
        panelRound26 = new boder_panel.PanelRound();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        from3 = new javax.swing.JPanel();
        tableScrollButton2 = new table.TableScrollButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        panelRound10 = new boder_panel.PanelRound();
        txtSearch1 = new textfield.TextFieldSearchOption();
        panelRound20 = new boder_panel.PanelRound();
        jLabel18 = new javax.swing.JLabel();
        panelRound8 = new boder_panel.PanelRound();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txt_Khachhang = new textfield_suggestion.TextFieldSuggestion();
        txt_MaNhavien = new textfield_suggestion.TextFieldSuggestion();
        txt_maHD = new textfield_suggestion.TextFieldSuggestion();
        txt_sodienthoai = new textfield_suggestion.TextFieldSuggestion();
        jLabel1 = new javax.swing.JLabel();
        tableScrollButton3 = new table.TableScrollButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblNhapHang = new javax.swing.JTable();
        txt_hotenNhanVien = new textfield_suggestion.TextFieldSuggestion();
        PanelTool = new boder_panel.PanelRound();
        jLabel2 = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JLabel();
        panelRound14 = new boder_panel.PanelRound();
        jLabel21 = new javax.swing.JLabel();
        panelRound22 = new boder_panel.PanelRound();
        jLabel20 = new javax.swing.JLabel();
        panelRound24 = new boder_panel.PanelRound();
        jLabel31 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txtSoLuong = new textfield_suggestion.TextFieldSuggestion();
        cboLoai1 = new combobox.Combobox();
        cboMau1 = new combobox.Combobox();
        cboKT1 = new combobox.Combobox();
        panelRound23 = new boder_panel.PanelRound();
        jLabel33 = new javax.swing.JLabel();
        from4 = new javax.swing.JPanel();
        panelRound27 = new boder_panel.PanelRound();
        dateTU = new com.toedter.calendar.JDateChooser();
        dateDEN = new com.toedter.calendar.JDateChooser();
        textFieldSearchOption1 = new textfield.TextFieldSearchOption();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        tableScrollButton4 = new table.TableScrollButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbHoaDon = new javax.swing.JTable();
        from5 = new javax.swing.JPanel();
        panelRound16 = new boder_panel.PanelRound();
        panelRound17 = new boder_panel.PanelRound();
        btn_themKhachHang = new sample.message.Button();
        btn_suaKhachHang = new sample.message.Button();
        button2 = new sample.message.Button();
        panelRound18 = new boder_panel.PanelRound();
        textFielKhachHang = new textfield.TextFieldSearchOption();
        bnt_lamMoiKhachHang = new sample.message.Button();
        tableScrollButton5 = new table.TableScrollButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblKhachHang = new javax.swing.JTable();
        from6 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        txtMkcu = new textfield.PasswordField();
        txtMkmoi = new textfield.PasswordField();
        txtnhapLaiMK = new textfield.PasswordField();
        button3 = new sample.message.Button();
        lbThongBao = new javax.swing.JLabel();
        from7 = new javax.swing.JPanel();
        from2 = new javax.swing.JPanel();
        panelRound9 = new boder_panel.PanelRound();
        txtSearch = new textfield.TextFieldSearchOption();
        panelRound15 = new boder_panel.PanelRound();
        jLabel14 = new javax.swing.JLabel();
        button1 = new sample.message.Button();
        tableScrollButton1 = new table.TableScrollButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbSanPham = new javax.swing.JTable();
        cboLoai = new combobox.Combobox();
        cboMau = new combobox.Combobox();
        cboKT = new combobox.Combobox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        panel.setBackground(new java.awt.Color(255, 255, 255));

        thongtin.setBackground(new java.awt.Color(255, 255, 255));
        thongtin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cashier.png"))); // NOI18N

        lbNV.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbNV.setText("Nguyễn Quang Phúc");

        jLabel17.setForeground(new java.awt.Color(153, 153, 153));
        jLabel17.setText("Nhân viên thu ngân");

        javax.swing.GroupLayout thongtinLayout = new javax.swing.GroupLayout(thongtin);
        thongtin.setLayout(thongtinLayout);
        thongtinLayout.setHorizontalGroup(
            thongtinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thongtinLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(thongtinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(lbNV))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        thongtinLayout.setVerticalGroup(
            thongtinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thongtinLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(thongtinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(thongtinLayout.createSequentialGroup()
                        .addComponent(lbNV)
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
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/box.png"))); // NOI18N
        jLabel5.setText("  Quản lý sản phẩm");

        javax.swing.GroupLayout panelRound2Layout = new javax.swing.GroupLayout(panelRound2);
        panelRound2.setLayout(panelRound2Layout);
        panelRound2Layout.setHorizontalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelRound2Layout.setVerticalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        panelRound3.setBackground(new java.awt.Color(255, 255, 255));
        panelRound3.setRoundBottomLeft(10);
        panelRound3.setRoundBottomRight(10);
        panelRound3.setRoundTopLeft(10);
        panelRound3.setRoundTopRight(10);
        panelRound3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelRound3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelRound3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelRound3MouseExited(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/accounting.png"))); // NOI18N
        jLabel6.setText("  Bán hàng");

        javax.swing.GroupLayout panelRound3Layout = new javax.swing.GroupLayout(panelRound3);
        panelRound3.setLayout(panelRound3Layout);
        panelRound3Layout.setHorizontalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelRound3Layout.setVerticalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
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
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bill.png"))); // NOI18N
        jLabel7.setText("   Hoá đơn");

        javax.swing.GroupLayout panelRound4Layout = new javax.swing.GroupLayout(panelRound4);
        panelRound4.setLayout(panelRound4Layout);
        panelRound4Layout.setHorizontalGroup(
            panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/rating.png"))); // NOI18N
        jLabel8.setText("   Khách hàng");

        javax.swing.GroupLayout panelRound5Layout = new javax.swing.GroupLayout(panelRound5);
        panelRound5.setLayout(panelRound5Layout);
        panelRound5Layout.setHorizontalGroup(
            panelRound5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound5Layout.createSequentialGroup()
                .addGap(0, 32, Short.MAX_VALUE)
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
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reset-password.png"))); // NOI18N
        jLabel9.setText(" Đổi mật khẩu");

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
            .addComponent(panelRound3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(panelRound3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addGroup(menuLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
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

        from1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(51, 102, 255));
        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/JAVA.png"))); // NOI18N
        jLabel29.setText("HỆ THỐNG QUẢN LÝ CỬA HÀNG THỜI TRANG");
        jLabel29.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel29.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jLabel32.setFont(new java.awt.Font("Segoe UI", 2, 16)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(51, 102, 255));
        jLabel32.setText("Dù chỉ một lần trong đời, hãy thử điều gì đó. Nỗ lực vì điều gì đó. Hãy thử thay đổi, không gì tồi tệ có thể xảy ra đâu");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(229, 229, 229))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addGap(115, 115, 115))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel32)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(238, 250, 254));

        panelRound11.setBackground(new java.awt.Color(255, 255, 255));
        panelRound11.setRoundBottomLeft(100);
        panelRound11.setRoundBottomRight(100);
        panelRound11.setRoundTopLeft(100);
        panelRound11.setRoundTopRight(100);

        panelRound12.setBackground(new java.awt.Color(238, 250, 254));
        panelRound12.setRoundBottomLeft(100);
        panelRound12.setRoundBottomRight(100);
        panelRound12.setRoundTopLeft(100);
        panelRound12.setRoundTopRight(100);

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/target.png"))); // NOI18N

        javax.swing.GroupLayout panelRound12Layout = new javax.swing.GroupLayout(panelRound12);
        panelRound12.setLayout(panelRound12Layout);
        panelRound12Layout.setHorizontalGroup(
            panelRound12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound12Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(59, Short.MAX_VALUE))
        );
        panelRound12Layout.setVerticalGroup(
            panelRound12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound12Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel34)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel35.setText("TÍNH CHÍNH XÁC");

        javax.swing.GroupLayout panelRound11Layout = new javax.swing.GroupLayout(panelRound11);
        panelRound11.setLayout(panelRound11Layout);
        panelRound11Layout.setHorizontalGroup(
            panelRound11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound11Layout.createSequentialGroup()
                .addGroup(panelRound11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound11Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(panelRound12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelRound11Layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(jLabel35)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        panelRound11Layout.setVerticalGroup(
            panelRound11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound11Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(panelRound12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel35)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelRound13.setBackground(new java.awt.Color(255, 255, 255));
        panelRound13.setRoundBottomLeft(100);
        panelRound13.setRoundBottomRight(100);
        panelRound13.setRoundTopLeft(100);
        panelRound13.setRoundTopRight(100);

        panelRound21.setBackground(new java.awt.Color(238, 250, 254));
        panelRound21.setRoundBottomLeft(100);
        panelRound21.setRoundBottomRight(100);
        panelRound21.setRoundTopLeft(100);
        panelRound21.setRoundTopRight(100);

        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/security.png"))); // NOI18N

        javax.swing.GroupLayout panelRound21Layout = new javax.swing.GroupLayout(panelRound21);
        panelRound21.setLayout(panelRound21Layout);
        panelRound21Layout.setHorizontalGroup(
            panelRound21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound21Layout.createSequentialGroup()
                .addContainerGap(69, Short.MAX_VALUE)
                .addComponent(jLabel36)
                .addGap(62, 62, 62))
        );
        panelRound21Layout.setVerticalGroup(
            panelRound21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound21Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel36)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel37.setText("TÍNH BẢO MẬT");

        javax.swing.GroupLayout panelRound13Layout = new javax.swing.GroupLayout(panelRound13);
        panelRound13.setLayout(panelRound13Layout);
        panelRound13Layout.setHorizontalGroup(
            panelRound13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound13Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(panelRound21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel37)
                .addGap(94, 94, 94))
        );
        panelRound13Layout.setVerticalGroup(
            panelRound13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound13Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(panelRound21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel37)
                .addContainerGap(147, Short.MAX_VALUE))
        );

        panelRound25.setBackground(new java.awt.Color(255, 255, 255));
        panelRound25.setRoundBottomLeft(100);
        panelRound25.setRoundBottomRight(100);
        panelRound25.setRoundTopLeft(100);
        panelRound25.setRoundTopRight(100);

        panelRound26.setBackground(new java.awt.Color(238, 250, 254));
        panelRound26.setRoundBottomLeft(100);
        panelRound26.setRoundBottomRight(100);
        panelRound26.setRoundTopLeft(100);
        panelRound26.setRoundTopRight(100);

        jLabel38.setBackground(new java.awt.Color(238, 250, 254));
        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/efficiency.png"))); // NOI18N

        javax.swing.GroupLayout panelRound26Layout = new javax.swing.GroupLayout(panelRound26);
        panelRound26.setLayout(panelRound26Layout);
        panelRound26Layout.setHorizontalGroup(
            panelRound26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound26Layout.createSequentialGroup()
                .addContainerGap(65, Short.MAX_VALUE)
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
        panelRound26Layout.setVerticalGroup(
            panelRound26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound26Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jLabel38)
                .addGap(14, 14, 14))
        );

        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel39.setText("TÍNH HIỆU QUẢ");

        javax.swing.GroupLayout panelRound25Layout = new javax.swing.GroupLayout(panelRound25);
        panelRound25.setLayout(panelRound25Layout);
        panelRound25Layout.setHorizontalGroup(
            panelRound25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound25Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(panelRound26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound25Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel39)
                .addGap(91, 91, 91))
        );
        panelRound25Layout.setVerticalGroup(
            panelRound25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound25Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(panelRound26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(panelRound11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addComponent(panelRound13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(panelRound25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelRound11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRound13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRound25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(181, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout from1Layout = new javax.swing.GroupLayout(from1);
        from1.setLayout(from1Layout);
        from1Layout.setHorizontalGroup(
            from1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(from1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(from1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        from1Layout.setVerticalGroup(
            from1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(from1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        jPanel3.add(from1, "card2");

        from3.setBackground(new java.awt.Color(255, 255, 255));
        from3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "BÁN HÀNG"));

        jScrollPane1.setBorder(null);

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblSanPham);

        tableScrollButton2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        panelRound10.setBackground(new java.awt.Color(255, 255, 255));
        panelRound10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Tìm kiếm"));
        panelRound10.setRoundBottomLeft(10);
        panelRound10.setRoundBottomRight(10);
        panelRound10.setRoundTopLeft(10);
        panelRound10.setRoundTopRight(10);

        txtSearch1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearch1KeyReleased(evt);
            }
        });

        panelRound20.setBackground(new java.awt.Color(211, 213, 214));
        panelRound20.setRoundBottomLeft(10);
        panelRound20.setRoundBottomRight(10);
        panelRound20.setRoundTopLeft(10);
        panelRound20.setRoundTopRight(10);
        panelRound20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelRound20MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelRound20MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelRound20MouseExited(evt);
            }
        });

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/refresh (1).png"))); // NOI18N
        jLabel18.setText("Làm mới");

        javax.swing.GroupLayout panelRound20Layout = new javax.swing.GroupLayout(panelRound20);
        panelRound20.setLayout(panelRound20Layout);
        panelRound20Layout.setHorizontalGroup(
            panelRound20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound20Layout.createSequentialGroup()
                .addGap(0, 16, Short.MAX_VALUE)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelRound20Layout.setVerticalGroup(
            panelRound20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelRound10Layout = new javax.swing.GroupLayout(panelRound10);
        panelRound10.setLayout(panelRound10Layout);
        panelRound10Layout.setHorizontalGroup(
            panelRound10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound10Layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(txtSearch1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(panelRound20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        panelRound10Layout.setVerticalGroup(
            panelRound10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRound10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelRound20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearch1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelRound8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel22.setText("Mã hoá đơn");

        jLabel23.setText("Khách hàng");

        jLabel24.setText("Nhân viên");

        txt_MaNhavien.setBackground(new java.awt.Color(242, 242, 242));
        txt_MaNhavien.setEnabled(false);
        txt_MaNhavien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_MaNhavienActionPerformed(evt);
            }
        });

        txt_maHD.setBackground(new java.awt.Color(242, 242, 242));
        txt_maHD.setEnabled(false);
        txt_maHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_maHDActionPerformed(evt);
            }
        });

        txt_sodienthoai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_sodienthoaiKeyReleased(evt);
            }
        });

        jLabel1.setText("Số điện thoại");

        tblNhapHang.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblNhapHang);

        tableScrollButton3.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        txt_hotenNhanVien.setBackground(new java.awt.Color(242, 242, 242));
        txt_hotenNhanVien.setEnabled(false);

        javax.swing.GroupLayout panelRound8Layout = new javax.swing.GroupLayout(panelRound8);
        panelRound8.setLayout(panelRound8Layout);
        panelRound8Layout.setHorizontalGroup(
            panelRound8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRound8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tableScrollButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
                    .addGroup(panelRound8Layout.createSequentialGroup()
                        .addGroup(panelRound8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(jLabel22)
                            .addComponent(jLabel1)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelRound8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelRound8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txt_sodienthoai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_Khachhang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                                .addComponent(txt_maHD, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(panelRound8Layout.createSequentialGroup()
                                .addComponent(txt_MaNhavien, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txt_hotenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        panelRound8Layout.setVerticalGroup(
            panelRound8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound8Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(panelRound8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txt_maHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(panelRound8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_sodienthoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(panelRound8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Khachhang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addGap(18, 18, 18)
                .addGroup(panelRound8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txt_MaNhavien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_hotenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(tableScrollButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        PanelTool.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 51));
        jLabel2.setText("Tổng tiền:");

        txtTongTien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTongTien.setForeground(new java.awt.Color(255, 0, 51));
        txtTongTien.setText("0 VNĐ");

        panelRound14.setBackground(new java.awt.Color(247, 254, 247));
        panelRound14.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelRound14.setRoundBottomLeft(10);
        panelRound14.setRoundBottomRight(10);
        panelRound14.setRoundTopLeft(10);
        panelRound14.setRoundTopRight(10);
        panelRound14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelRound14MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelRound14MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelRound14MouseExited(evt);
            }
        });

        jLabel21.setBackground(new java.awt.Color(255, 255, 255));
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/payment.png"))); // NOI18N
        jLabel21.setText("Thanh toán");
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel21MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelRound14Layout = new javax.swing.GroupLayout(panelRound14);
        panelRound14.setLayout(panelRound14Layout);
        panelRound14Layout.setHorizontalGroup(
            panelRound14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound14Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelRound14Layout.setVerticalGroup(
            panelRound14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound14Layout.createSequentialGroup()
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelRound22.setBackground(new java.awt.Color(254, 241, 241));
        panelRound22.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelRound22.setRoundBottomLeft(10);
        panelRound22.setRoundBottomRight(10);
        panelRound22.setRoundTopLeft(10);
        panelRound22.setRoundTopRight(10);
        panelRound22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelRound22MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelRound22MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelRound22MouseExited(evt);
            }
        });

        jLabel20.setBackground(new java.awt.Color(255, 255, 255));
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/trash20.png"))); // NOI18N
        jLabel20.setText("Xoá sản phẩm");

        javax.swing.GroupLayout panelRound22Layout = new javax.swing.GroupLayout(panelRound22);
        panelRound22.setLayout(panelRound22Layout);
        panelRound22Layout.setHorizontalGroup(
            panelRound22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound22Layout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelRound22Layout.setVerticalGroup(
            panelRound22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelRound24.setBackground(new java.awt.Color(243, 243, 213));
        panelRound24.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelRound24.setRoundBottomLeft(10);
        panelRound24.setRoundBottomRight(10);
        panelRound24.setRoundTopLeft(10);
        panelRound24.setRoundTopRight(10);
        panelRound24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelRound24MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelRound24MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelRound24MouseExited(evt);
            }
        });

        jLabel31.setBackground(new java.awt.Color(255, 255, 255));
        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/pencil20.png"))); // NOI18N
        jLabel31.setText("Sửa số lượng");

        javax.swing.GroupLayout panelRound24Layout = new javax.swing.GroupLayout(panelRound24);
        panelRound24.setLayout(panelRound24Layout);
        panelRound24Layout.setHorizontalGroup(
            panelRound24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound24Layout.createSequentialGroup()
                .addGap(0, 21, Short.MAX_VALUE)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelRound24Layout.setVerticalGroup(
            panelRound24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout PanelToolLayout = new javax.swing.GroupLayout(PanelTool);
        PanelTool.setLayout(PanelToolLayout);
        PanelToolLayout.setHorizontalGroup(
            PanelToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelToolLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(40, 40, 40)
                .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelRound14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelToolLayout.createSequentialGroup()
                .addGap(108, 108, 108)
                .addComponent(panelRound24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81)
                .addComponent(panelRound22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(88, Short.MAX_VALUE))
        );
        PanelToolLayout.setVerticalGroup(
            PanelToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelToolLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelRound24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRound22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(PanelToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelToolLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(PanelToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PanelToolLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(panelRound14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jLabel30.setText("Số lượng");

        cboLoai1.setLabeText("Loại sản phẩm");
        cboLoai1.setLightWeightPopupEnabled(false);
        cboLoai1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboLoai1ItemStateChanged(evt);
            }
        });

        cboMau1.setLabeText("Màu sắc");
        cboMau1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboMau1ItemStateChanged(evt);
            }
        });

        cboKT1.setLabeText("Kích thước");
        cboKT1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboKT1ItemStateChanged(evt);
            }
        });

        panelRound23.setBackground(new java.awt.Color(233, 254, 233));
        panelRound23.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelRound23.setRoundBottomLeft(10);
        panelRound23.setRoundBottomRight(10);
        panelRound23.setRoundTopLeft(10);
        panelRound23.setRoundTopRight(10);
        panelRound23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelRound23MouseClicked(evt);
            }
        });

        jLabel33.setBackground(new java.awt.Color(255, 255, 255));
        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add20.png"))); // NOI18N
        jLabel33.setText("Thêm");

        javax.swing.GroupLayout panelRound23Layout = new javax.swing.GroupLayout(panelRound23);
        panelRound23.setLayout(panelRound23Layout);
        panelRound23Layout.setHorizontalGroup(
            panelRound23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound23Layout.createSequentialGroup()
                .addGap(0, 21, Short.MAX_VALUE)
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelRound23Layout.setVerticalGroup(
            panelRound23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout from3Layout = new javax.swing.GroupLayout(from3);
        from3.setLayout(from3Layout);
        from3Layout.setHorizontalGroup(
            from3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(from3Layout.createSequentialGroup()
                .addGroup(from3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(from3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(panelRound23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(122, 122, 122))
                    .addGroup(from3Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addGroup(from3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(from3Layout.createSequentialGroup()
                                .addComponent(cboLoai1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(cboMau1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(from3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(cboKT1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(panelRound10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(tableScrollButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)))
                .addGroup(from3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelTool, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelRound8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        from3Layout.setVerticalGroup(
            from3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(from3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(from3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(from3Layout.createSequentialGroup()
                        .addComponent(panelRound10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addGroup(from3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboLoai1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboMau1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboKT1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(tableScrollButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addGroup(from3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelRound23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(from3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(from3Layout.createSequentialGroup()
                        .addComponent(panelRound8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(PanelTool, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jPanel3.add(from3, "card4");

        from4.setBackground(new java.awt.Color(238, 250, 254));

        panelRound27.setBackground(new java.awt.Color(255, 255, 255));
        panelRound27.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Chức năng"));

        dateTU.setBackground(new java.awt.Color(255, 255, 255));
        dateTU.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateTUPropertyChange(evt);
            }
        });

        dateDEN.setBackground(new java.awt.Color(255, 255, 255));
        dateDEN.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateDENPropertyChange(evt);
            }
        });

        textFieldSearchOption1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textFieldSearchOption1KeyReleased(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(204, 204, 204));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/refresh (1).png"))); // NOI18N
        jButton1.setText("Làm mới");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("TỪ");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("ĐẾN");

        javax.swing.GroupLayout panelRound27Layout = new javax.swing.GroupLayout(panelRound27);
        panelRound27.setLayout(panelRound27Layout);
        panelRound27Layout.setHorizontalGroup(
            panelRound27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound27Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateTU, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateDEN, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(125, 125, 125)
                .addComponent(textFieldSearchOption1, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(68, 68, 68))
        );
        panelRound27Layout.setVerticalGroup(
            panelRound27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound27Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panelRound27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textFieldSearchOption1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                    .addComponent(dateDEN, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(dateTU, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jScrollPane4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jScrollPane4MousePressed(evt);
            }
        });

        tbHoaDon.setModel(new javax.swing.table.DefaultTableModel(
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
        tbHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbHoaDonMousePressed(evt);
            }
        });
        jScrollPane4.setViewportView(tbHoaDon);

        tableScrollButton4.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout from4Layout = new javax.swing.GroupLayout(from4);
        from4.setLayout(from4Layout);
        from4Layout.setHorizontalGroup(
            from4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(from4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(from4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tableScrollButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRound27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        from4Layout.setVerticalGroup(
            from4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(from4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(panelRound27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(tableScrollButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(129, Short.MAX_VALUE))
        );

        jPanel3.add(from4, "card5");

        from5.setBackground(new java.awt.Color(240, 247, 250));

        panelRound16.setBackground(new java.awt.Color(255, 255, 255));

        panelRound17.setBackground(new java.awt.Color(255, 255, 255));
        panelRound17.setBorder(javax.swing.BorderFactory.createTitledBorder("Chức năng"));

        btn_themKhachHang.setBackground(new java.awt.Color(211, 213, 214));
        btn_themKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add20.png"))); // NOI18N
        btn_themKhachHang.setText("Thêm");
        btn_themKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_themKhachHangMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_themKhachHangMouseExited(evt);
            }
        });
        btn_themKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themKhachHangActionPerformed(evt);
            }
        });

        btn_suaKhachHang.setBackground(new java.awt.Color(211, 213, 214));
        btn_suaKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/pencil20.png"))); // NOI18N
        btn_suaKhachHang.setText("Sửa");
        btn_suaKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_suaKhachHangMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_suaKhachHangMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_suaKhachHangMouseExited(evt);
            }
        });
        btn_suaKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_suaKhachHangActionPerformed(evt);
            }
        });

        button2.setBackground(new java.awt.Color(211, 213, 214));
        button2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/trash20.png"))); // NOI18N
        button2.setText("Xoá");
        button2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button2MouseExited(evt);
            }
        });
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRound17Layout = new javax.swing.GroupLayout(panelRound17);
        panelRound17.setLayout(panelRound17Layout);
        panelRound17Layout.setHorizontalGroup(
            panelRound17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound17Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(btn_themKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(btn_suaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        panelRound17Layout.setVerticalGroup(
            panelRound17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRound17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_themKhachHang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_suaKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        panelRound18.setBackground(new java.awt.Color(255, 255, 255));
        panelRound18.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"));

        textFielKhachHang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textFielKhachHangKeyReleased(evt);
            }
        });

        bnt_lamMoiKhachHang.setBackground(new java.awt.Color(204, 204, 204));
        bnt_lamMoiKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/refresh (1).png"))); // NOI18N
        bnt_lamMoiKhachHang.setText("Làm mới");
        bnt_lamMoiKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bnt_lamMoiKhachHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRound18Layout = new javax.swing.GroupLayout(panelRound18);
        panelRound18.setLayout(panelRound18Layout);
        panelRound18Layout.setHorizontalGroup(
            panelRound18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound18Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(textFielKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                .addComponent(bnt_lamMoiKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
        );
        panelRound18Layout.setVerticalGroup(
            panelRound18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRound18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFielKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bnt_lamMoiKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelRound16Layout = new javax.swing.GroupLayout(panelRound16);
        panelRound16.setLayout(panelRound16Layout);
        panelRound16Layout.setHorizontalGroup(
            panelRound16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound16Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(panelRound17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(panelRound18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        panelRound16Layout.setVerticalGroup(
            panelRound16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound16Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(panelRound16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelRound17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRound18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        tblKhachHang.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(tblKhachHang);

        tableScrollButton5.add(jScrollPane5, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout from5Layout = new javax.swing.GroupLayout(from5);
        from5.setLayout(from5Layout);
        from5Layout.setHorizontalGroup(
            from5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(from5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(from5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tableScrollButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRound16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        from5Layout.setVerticalGroup(
            from5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(from5Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(panelRound16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tableScrollButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.add(from5, "card6");

        from6.setBackground(new java.awt.Color(240, 247, 250));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel40.setFont(new java.awt.Font("Segoe UI", 1, 25)); // NOI18N
        jLabel40.setText("Đổi mật khẩu");

        txtMkcu.setLabelText("Mật khẩu cũ");
        txtMkcu.setShowAndHide(true);

        txtMkmoi.setLabelText("Mật khẩu mới");
        txtMkmoi.setShowAndHide(true);

        txtnhapLaiMK.setLabelText("Nhập lại mật khẩu");
        txtnhapLaiMK.setShowAndHide(true);

        button3.setBackground(new java.awt.Color(0, 102, 102));
        button3.setForeground(new java.awt.Color(255, 255, 255));
        button3.setText("Đổi mật khẩu");
        button3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button3MouseClicked(evt);
            }
        });
        button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button3ActionPerformed(evt);
            }
        });

        lbThongBao.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbThongBao.setForeground(new java.awt.Color(255, 72, 72));
        lbThongBao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbThongBao.setText(".");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(215, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addGap(341, 341, 341))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtMkmoi, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMkcu, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtnhapLaiMK, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbThongBao, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(202, 202, 202))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel40)
                .addGap(42, 42, 42)
                .addComponent(txtMkcu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(txtMkmoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(txtnhapLaiMK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbThongBao)
                .addContainerGap(116, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout from6Layout = new javax.swing.GroupLayout(from6);
        from6.setLayout(from6Layout);
        from6Layout.setHorizontalGroup(
            from6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(from6Layout.createSequentialGroup()
                .addGap(155, 155, 155)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(166, Short.MAX_VALUE))
        );
        from6Layout.setVerticalGroup(
            from6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(from6Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(160, Short.MAX_VALUE))
        );

        jPanel3.add(from6, "card7");

        from7.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout from7Layout = new javax.swing.GroupLayout(from7);
        from7.setLayout(from7Layout);
        from7Layout.setHorizontalGroup(
            from7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1171, Short.MAX_VALUE)
        );
        from7Layout.setVerticalGroup(
            from7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 798, Short.MAX_VALUE)
        );

        jPanel3.add(from7, "card8");

        from2.setBackground(new java.awt.Color(240, 247, 250));

        panelRound9.setBackground(new java.awt.Color(255, 255, 255));
        panelRound9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Chức năng"));
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

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/refresh (1).png"))); // NOI18N
        jLabel14.setText("Làm mới");

        javax.swing.GroupLayout panelRound15Layout = new javax.swing.GroupLayout(panelRound15);
        panelRound15.setLayout(panelRound15Layout);
        panelRound15Layout.setHorizontalGroup(
            panelRound15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound15Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelRound15Layout.setVerticalGroup(
            panelRound15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        button1.setBackground(new java.awt.Color(211, 213, 214));
        button1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-info-20.png"))); // NOI18N
        button1.setText("Hình mẫu");
        button1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button1MouseExited(evt);
            }
        });

        javax.swing.GroupLayout panelRound9Layout = new javax.swing.GroupLayout(panelRound9);
        panelRound9.setLayout(panelRound9Layout);
        panelRound9Layout.setHorizontalGroup(
            panelRound9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound9Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(148, 148, 148)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 208, Short.MAX_VALUE)
                .addComponent(panelRound15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(95, 95, 95))
        );
        panelRound9Layout.setVerticalGroup(
            panelRound9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound9Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(panelRound9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelRound15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelRound9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        tbSanPham.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tbSanPham);

        tableScrollButton1.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        cboLoai.setLabeText("Loại sản phẩm");
        cboLoai.setLightWeightPopupEnabled(false);
        cboLoai.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboLoaiItemStateChanged(evt);
            }
        });

        cboMau.setLabeText("Màu sắc");
        cboMau.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboMauItemStateChanged(evt);
            }
        });

        cboKT.setLabeText("Kích thước");
        cboKT.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboKTItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout from2Layout = new javax.swing.GroupLayout(from2);
        from2.setLayout(from2Layout);
        from2Layout.setHorizontalGroup(
            from2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(from2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(from2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tableScrollButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(from2Layout.createSequentialGroup()
                        .addComponent(cboLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63)
                        .addComponent(cboMau, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)
                        .addComponent(cboKT, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelRound9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        from2Layout.setVerticalGroup(
            from2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(from2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(panelRound9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(from2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboKT, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboMau, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(tableScrollButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 522, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58))
        );

        jPanel3.add(from2, "card3");

        javax.swing.GroupLayout backroundLayout = new javax.swing.GroupLayout(backround);
        backround.setLayout(backroundLayout);
        backroundLayout.setHorizontalGroup(
            backroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backroundLayout.createSequentialGroup()
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void panelRound3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound3MouseEntered
        if (flag3 == 0)
            panelRound3.setBackground(new Color(211, 213, 214, 255));
    }//GEN-LAST:event_panelRound3MouseEntered

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

    private void panelRound3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound3MouseExited
        if (flag3 == 0)
            panelRound3.setBackground(Color.white);
    }//GEN-LAST:event_panelRound3MouseExited

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
        panelRound3.setBackground(Color.white);
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
        if (checkLanDau == 0) {
            resetFlags();
            flag1 = 1;
            enableColor();
            panelRound1.setBackground(new Color(176, 210, 235, 255));
            closeFrom();
            from1.setVisible(true);
        }
        else{
            JOptionPane.showMessageDialog(PanelTool, "Bạn cần thay đổi mật khẩu cho lần đầu đăng nhập !");
        }
    }//GEN-LAST:event_panelRound1MouseClicked

    private void panelRound2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound2MouseClicked
        
        if (checkLanDau == 0) {
            resetFlags();
            flag2 = 1;
            enableColor();
            panelRound2.setBackground(new Color(176, 210, 235, 255));
            closeFrom();
            from2.setVisible(true);
        }
        else{
            JOptionPane.showMessageDialog(PanelTool, "Bạn cần thay đổi mật khẩu cho lần đầu đăng nhập !");
        }
    }//GEN-LAST:event_panelRound2MouseClicked

    private void panelRound3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound3MouseClicked
        
        if (checkLanDau == 0) {
            resetFlags();
            flag3 = 1;
            enableColor();
            panelRound3.setBackground(new Color(176, 210, 235, 255));
            closeFrom();
            from3.setVisible(true);
        }
        else{
            JOptionPane.showMessageDialog(PanelTool, "Bạn cần thay đổi mật khẩu cho lần đầu đăng nhập !");
        }
        
    }//GEN-LAST:event_panelRound3MouseClicked

    private void panelRound4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound4MouseClicked
        
        if (checkLanDau == 0) {
            resetFlags();
            flag4 = 1;
            enableColor();
            panelRound4.setBackground(new Color(176, 210, 235, 255));
            closeFrom();
            from4.setVisible(true);
        }
        else{
            JOptionPane.showMessageDialog(PanelTool, "Bạn cần thay đổi mật khẩu cho lần đầu đăng nhập !");
        }
    }//GEN-LAST:event_panelRound4MouseClicked

    private void panelRound5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound5MouseClicked
        
        if (checkLanDau == 0) {
            resetFlags();
            flag5 = 1;
            enableColor();
            panelRound5.setBackground(new Color(176, 210, 235, 255));
            closeFrom();
            from5.setVisible(true);
        }
        else{
            JOptionPane.showMessageDialog(PanelTool, "Bạn cần thay đổi mật khẩu cho lần đầu đăng nhập !");
        }
    }//GEN-LAST:event_panelRound5MouseClicked

    private void panelRound6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound6MouseClicked
        resetFlags();
        flag6 = 1;
        enableColor();
        panelRound6.setBackground(new Color(176, 210, 235, 255));
        closeFrom();
        from6.setVisible(true);
    }//GEN-LAST:event_panelRound6MouseClicked

    private void panelRound7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound7MouseClicked
        int n = JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc muốn đăng xuất?", "Thông báo", JOptionPane.YES_NO_OPTION);
        try {
            if (n == JOptionPane.YES_OPTION) {
                Database.conn.close();
                this.dispose();
                new frmDangNhap().setVisible(true);
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_panelRound7MouseClicked

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        String loai = (String) cboLoai.getSelectedItem();
        String mau = (String) cboMau.getSelectedItem();
        String kt = (String) cboKT.getSelectedItem();
        if (txtSearch.isSelected()) {
            int option = txtSearch.getSelectedIndex();
            if (option == 0) {
                loadQLSP(1, txtSearch.getText(), loai, mau, kt);
            } else {
                loadQLSP(2, txtSearch.getText(), loai, mau, kt);
            }
        }
    }//GEN-LAST:event_txtSearchKeyReleased

    private void panelRound15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound15MouseClicked
        txtSearch.setText("");
        loadQLSP(0, "", "Tất cả", "Tất cả", "Tất cả");
        loadKT();
        loadLSP();
        loadM();
    }//GEN-LAST:event_panelRound15MouseClicked

    private void panelRound15MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound15MouseEntered
        panelRound15.setBackground(new Color(204, 204, 204));
    }//GEN-LAST:event_panelRound15MouseEntered

    private void panelRound15MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound15MouseExited
        
        panelRound15.setBackground(new Color(211, 213, 214, 255));
    }//GEN-LAST:event_panelRound15MouseExited

    private void cboLoaiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboLoaiItemStateChanged
        String loai = (String) cboLoai.getSelectedItem();
        String mau = (String) cboMau.getSelectedItem();
        String kt = (String) cboKT.getSelectedItem();
        if (txtSearch.isSelected()) {
            int option = txtSearch.getSelectedIndex();
            if (option == 0) {
                loadQLSP(1, txtSearch.getText(), loai, mau, kt);
            } else {
                loadQLSP(2, txtSearch.getText(), loai, mau, kt);
            }
        }
    }//GEN-LAST:event_cboLoaiItemStateChanged

    private void cboMauItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboMauItemStateChanged
        String loai = (String) cboLoai.getSelectedItem();
        String mau = (String) cboMau.getSelectedItem();
        String kt = (String) cboKT.getSelectedItem();
        if (txtSearch.isSelected()) {
            int option = txtSearch.getSelectedIndex();
            if (option == 0) {
                loadQLSP(1, txtSearch.getText(), loai, mau, kt);
            } else {
                loadQLSP(2, txtSearch.getText(), loai, mau, kt);
            }
        }
    }//GEN-LAST:event_cboMauItemStateChanged

    private void cboKTItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboKTItemStateChanged
        String loai = (String) cboLoai.getSelectedItem();
        String mau = (String) cboMau.getSelectedItem();
        String kt = (String) cboKT.getSelectedItem();
        if (txtSearch.isSelected()) {
            int option = txtSearch.getSelectedIndex();
            if (option == 0) {
                loadQLSP(1, txtSearch.getText(), loai, mau, kt);
            } else {
                loadQLSP(2, txtSearch.getText(), loai, mau, kt);
            }
        }
    }//GEN-LAST:event_cboKTItemStateChanged

    private void txtSearch1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearch1KeyReleased
        // TODO add your handling code here:
        String loai = (String) cboLoai1.getSelectedItem();
        String mau = (String) cboMau1.getSelectedItem();
        String kt = (String) cboKT1.getSelectedItem();
        if (txtSearch1.isSelected()) {
            int option = txtSearch1.getSelectedIndex();
            if (option == 0) {
                loadQLSP(1, txtSearch1.getText(), loai, mau, kt);
            } else {
                loadQLSP(2, txtSearch1.getText(), loai, mau, kt);
            }
        }
    }//GEN-LAST:event_txtSearch1KeyReleased

    private void panelRound20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound20MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelRound20MouseClicked

    private void panelRound20MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound20MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelRound20MouseEntered

    private void panelRound20MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound20MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_panelRound20MouseExited

    private void txt_maHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_maHDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_maHDActionPerformed

    private void cboLoai1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboLoai1ItemStateChanged
        // TODO add your handling code here:
        String loai = (String) cboLoai1.getSelectedItem();
        String mau = (String) cboMau1.getSelectedItem();
        String kt = (String) cboKT1.getSelectedItem();
        if (txtSearch1.isSelected()) {
            int option = txtSearch1.getSelectedIndex();
            if (option == 0) {
                loadQLSP(1, txtSearch1.getText(), loai, mau, kt);
            } else {
                loadQLSP(2, txtSearch1.getText(), loai, mau, kt);
            }
        }
    }//GEN-LAST:event_cboLoai1ItemStateChanged

    private void cboMau1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboMau1ItemStateChanged
        // TODO add your handling code here:
        String loai = (String) cboLoai1.getSelectedItem();
        String mau = (String) cboMau1.getSelectedItem();
        String kt = (String) cboKT1.getSelectedItem();
        if (txtSearch1.isSelected()) {
            int option = txtSearch1.getSelectedIndex();
            if (option == 0) {
                loadQLSP(1, txtSearch1.getText(), loai, mau, kt);
            } else {
                loadQLSP(2, txtSearch1.getText(), loai, mau, kt);
            }
        }
    }//GEN-LAST:event_cboMau1ItemStateChanged

    private void cboKT1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboKT1ItemStateChanged
        // TODO add your handling code here:
        String loai = (String) cboLoai1.getSelectedItem();
        String mau = (String) cboMau1.getSelectedItem();
        String kt = (String) cboKT1.getSelectedItem();
        if (txtSearch1.isSelected()) {
            int option = txtSearch1.getSelectedIndex();
            if (option == 0) {
                loadQLSP(1, txtSearch1.getText(), loai, mau, kt);
            } else {
                loadQLSP(2, txtSearch1.getText(), loai, mau, kt);
            }
        }
    }//GEN-LAST:event_cboKT1ItemStateChanged

    private void panelRound23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound23MouseClicked
        int i_row = tblSanPham.getSelectedRow();
        if (i_row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xuất hàng !");
        } else {
            int soluongselect = allProduct.get(i_row).getSoLuongTon();
            if (soluongselect == 0) {
                JOptionPane.showMessageDialog(this, "Sản phẩm đã hết hàng !");
            } else {
                int soluong;
                try {
                    soluong = Integer.parseInt(txtSoLuong.getText().trim());
                    if (soluong > 0) {
                        if (soluongselect < soluong) {
                            JOptionPane.showMessageDialog(this, "Số lượng không đủ !");
                        } else {
                            chiTietHoaDonPojo mtl = findCTPhieu((String) tblSanPham.getValueAt(i_row, 0));
                            if (mtl != null) {
                                if (findSanPham((String) tblSanPham.getValueAt(i_row, 0)).getSoLuongTon()< mtl.getSoLuong() + soluong) {
                                    JOptionPane.showMessageDialog(this, "Số lượng máy không đủ !");
                                } else {
                                    mtl.setSoLuong(mtl.getSoLuong() + soluong);
                                }
                            } else {
                                sanPhamPojo mt = timKiemSanPhamDao.getInstance().searchId((String) tblSanPham.getValueAt(i_row, 0));
                                chiTietHoaDonPojo ctp = new chiTietHoaDonPojo(MaPhieu, mt.getMaSanPham(), soluong, mt.getGiaBan() );
                                CTPhieu.add(ctp);
                            }
                            loadDataToTableNhapHang();
                            txtTongTien.setText(formatter.format(tinhTongTien()) + " VNĐ");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng lớn hơn 0");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng ở dạng số nguyên!");
                }
            }
        }
    }//GEN-LAST:event_panelRound23MouseClicked

    private void panelRound14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound14MouseClicked
        // TODO add your handling code here:
        if (CTPhieu.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn sản phẩm để xuất hàng !", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        } else {
            int check = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xuất hàng ?", "Xác nhận xuất hàng", JOptionPane.YES_NO_OPTION);
            if (check == JOptionPane.YES_OPTION) {
                // Lay thoi gian hien tai
                long now = System.currentTimeMillis();
                Timestamp sqlTimestamp = new Timestamp(now);
                khachHangPojo kh = findSDTKhachHang(txt_sodienthoai.getText());
                if (kh != null) {
                    hoaDonPojo pn = new hoaDonPojo(MaPhieu, kh.getMaKH(), txt_MaNhavien.getText(), sqlTimestamp, CTPhieu, tinhTongTien());
                    try {
                        System.out.println(MaPhieu);
                        hoaDonDao.getInstance().insert(pn);
                        System.out.println("thanh cong");
                        sanPhamDao mtdao = sanPhamDao.getInstance();
                        for (var i : CTPhieu) {
                            chiTietHoaDonDao.getInstance().insert(i);
                            mtdao.updateSoLuong(i.getMaSanPham(), mtdao.layDSSP1(i.getMaSanPham()).getSoLuongTon() - i.getSoLuong());
                            System.out.println("toi day 11");
                        }

                        JOptionPane.showMessageDialog(this, "Xuất hàng thành công !");

                        allProduct = sanPhamDao.getInstance().layDSSP1();
                        loadQLSP(0, "", "Tất cả", "Tất cả", "Tất cả");
                        DefaultTableModel l = (DefaultTableModel) tblNhapHang.getModel();
                        l.setRowCount(0);
                        CTPhieu = new ArrayList<chiTietHoaDonPojo>();
//                        txtSoLuong.setText("1");
                        txtTongTien.setText(0 + " VNĐ");
                        this.MaPhieu = createId(hoaDonDao.getInstance().layDSHD());
                        txt_maHD.setText(this.MaPhieu);
//                        this.MaKhachHang = createIdKhachHang(khachHangDao.getInstance().layDSKH());
                        txtSoLuong.setText("");
                        txt_Khachhang.setText("");
                        txt_sodienthoai.setText("");
                        loadDSHDDATE(0,"","null","null");
                    } catch (Exception e) {
                        JOptionPane.showConfirmDialog(this, "Đã xảy ra lỗi !");
                    }
                } else if (txt_sodienthoai.getText().equals("") && txt_Khachhang.getText().equals("")) {
                    hoaDonPojo pn1 = new hoaDonPojo(MaPhieu, txt_MaNhavien.getText(), sqlTimestamp, CTPhieu, tinhTongTien());
                    try {
                        hoaDonDao.getInstance().insertHDVangLai(pn1);
                        System.out.println("thanh cong");
                        sanPhamDao mtdao = sanPhamDao.getInstance();
                        for (var i : CTPhieu) {
                            chiTietHoaDonDao.getInstance().insert(i);
//                        System.out.println("toi day");
                            mtdao.updateSoLuong(i.getMaSanPham(), mtdao.layDSSP1(i.getMaSanPham()).getSoLuongTon() - i.getSoLuong());
                            System.out.println("toi day 11");
                        }

                        JOptionPane.showMessageDialog(this, "Xuất hàng thành công !");

                        allProduct = sanPhamDao.getInstance().layDSSP1();
                        loadQLSP(0, "", "Tất cả", "Tất cả", "Tất cả");
                        DefaultTableModel l = (DefaultTableModel) tblNhapHang.getModel();
                        l.setRowCount(0);
                        CTPhieu = new ArrayList<chiTietHoaDonPojo>();
//                        txtSoLuong.setText("1");
                        txtTongTien.setText(0 + " VNĐ");
                        this.MaPhieu = createId(hoaDonDao.getInstance().layDSHD());
                        txt_maHD.setText(this.MaPhieu);
//                        this.MaKhachHang = createIdKhachHang(khachHangDao.getInstance().layDSKH());
                        txtSoLuong.setText("");
                        txt_Khachhang.setText("");
                        txt_sodienthoai.setText("");
                        loadDSHDDATE(0,"","null","null");
                    } catch (Exception e) {
                        JOptionPane.showConfirmDialog(this, "Đã xảy ra lỗi !");
                    }
                } else {
                    khachHangPojo kh1 = new khachHangPojo(MaKhachHang, txt_Khachhang.getText(), txt_sodienthoai.getText(),"1");
                    khachHangDao.getInstance().insert(kh1);
                    hoaDonPojo pn1 = new hoaDonPojo(MaPhieu, MaKhachHang, txt_MaNhavien.getText(), sqlTimestamp, CTPhieu, tinhTongTien());
                    try {
                        hoaDonDao.getInstance().insert(pn1);
                        System.out.println("thanh cong");
                        sanPhamDao mtdao = sanPhamDao.getInstance();
                        for (var i : CTPhieu) {
                            chiTietHoaDonDao.getInstance().insert(i);
//                        System.out.println("toi day");
                            mtdao.updateSoLuong(i.getMaSanPham(), mtdao.layDSSP1(i.getMaSanPham()).getSoLuongTon() - i.getSoLuong());
                            System.out.println("toi day 11");
                        }

                        JOptionPane.showMessageDialog(this, "Xuất hàng thành công !");

                        allProduct = sanPhamDao.getInstance().layDSSP1();
                        loadQLSP(0, "", "Tất cả", "Tất cả", "Tất cả");
                        DefaultTableModel l = (DefaultTableModel) tblNhapHang.getModel();
                        l.setRowCount(0);
                        CTPhieu = new ArrayList<chiTietHoaDonPojo>();
//                        txtSoLuong.setText("1");
                        txtTongTien.setText(0 + " VNĐ");
                        this.MaPhieu = createId(hoaDonDao.getInstance().layDSHD());
                        txt_maHD.setText(this.MaPhieu);
                        this.MaKhachHang = createIdKhachHang(khachHangDao.getInstance().layDSKH());
                        txtSoLuong.setText("");
                        txt_Khachhang.setText("");
                        txt_sodienthoai.setText("");
                        loadDSHDDATE(0,"","null","null");
                    } catch (Exception e) {
                        JOptionPane.showConfirmDialog(this, "Đã xảy ra lỗi !");
                    }

                }

            }
        }
    }//GEN-LAST:event_panelRound14MouseClicked

    private void txt_MaNhavienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_MaNhavienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_MaNhavienActionPerformed

    private void panelRound22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound22MouseClicked
        // TODO add your handling code here:
    int i_row = tblNhapHang.getSelectedRow();
        if (i_row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xoá khỏi bảng xuất hàng !");
        } else {
            CTPhieu.remove(i_row);
            loadDataToTableNhapHang();
            txtTongTien.setText(formatter.format(tinhTongTien()) + "đ");
        }
       
    }//GEN-LAST:event_panelRound22MouseClicked

    private void panelRound22MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound22MouseEntered
        // TODO add your handling code here:
        panelRound22.setBackground(new Color(221, 76, 57, 255));
    }//GEN-LAST:event_panelRound22MouseEntered

    private void panelRound22MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound22MouseExited
        // TODO add your handling code here:
        panelRound22.setBackground(new Color(254,241,241));
    }//GEN-LAST:event_panelRound22MouseExited

    private void panelRound24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound24MouseClicked
        // TODO add your handling code here:
        int i_row = tblNhapHang.getSelectedRow();
        if (i_row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xoá sửa số lượng !");
        } else {
            String newSL = JOptionPane.showInputDialog(this, "Nhập số lượng cần thay đổi", "Thay đổi số lượng", QUESTION_MESSAGE);
            if (newSL != null) {
                int soLuong;
                try {
                    soLuong = Integer.parseInt(newSL);
                    if (soLuong > 0) {
                        if (soLuong > findSanPham(CTPhieu.get(i_row).getMaSanPham()).getSoLuongTon()) {
                            JOptionPane.showMessageDialog(this, "Số lượng không đủ !");
                        } else {
                            CTPhieu.get(i_row).setSoLuong(soLuong);
                            loadDataToTableNhapHang();
                            txtTongTien.setText(formatter.format(tinhTongTien()) + "đ");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng lớn hơn 0");

                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng ở dạng số nguyên!");
                }
            }
        }
    }//GEN-LAST:event_panelRound24MouseClicked

    private void panelRound24MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound24MouseEntered
        // TODO add your handling code here:
        panelRound24.setBackground(new Color(243, 156, 17, 255));
    }//GEN-LAST:event_panelRound24MouseEntered

    private void panelRound24MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound24MouseExited
        // TODO add your handling code here:
        panelRound24.setBackground(new Color(243,243,213));
    }//GEN-LAST:event_panelRound24MouseExited

    private void panelRound14MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound14MouseEntered
        // TODO add your handling code here:
        panelRound14.setBackground(new Color(233,254,233));
    }//GEN-LAST:event_panelRound14MouseEntered

    private void panelRound14MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound14MouseExited
        // TODO add your handling code here:
        panelRound14.setBackground(new Color(247,254,247));
    }//GEN-LAST:event_panelRound14MouseExited

    private void textFieldSearchOption1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textFieldSearchOption1KeyReleased
        // TODO add your handling code here:
        String dateFrom = chuyenDoi(dateTU.getDate());
        String dateTo = chuyenDoi(dateDEN.getDate());
        if (textFieldSearchOption1.isSelected()) {
            int option = textFieldSearchOption1.getSelectedIndex();
            if (option == 0) {
                loadDSHDDATE(1, textFieldSearchOption1.getText(),dateFrom,dateTo);
            } else if(option == 1){
                loadDSHDDATE(2, textFieldSearchOption1.getText(),dateFrom,dateTo);
            }
            else{
                loadDSHDDATE(3, textFieldSearchOption1.getText(),dateFrom,dateTo);
            }
        }
    }//GEN-LAST:event_textFieldSearchOption1KeyReleased

    private void dateTUPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateTUPropertyChange
        // TODO add your handling code here:
         String dateFrom = chuyenDoi(dateTU.getDate());
         String dateTo = chuyenDoi(dateDEN.getDate());
        if (textFieldSearchOption1.isSelected()) {
            int option = textFieldSearchOption1.getSelectedIndex();
            if (option == 0) {
                loadDSHDDATE(1, textFieldSearchOption1.getText(),dateFrom,dateTo);
            } else if(option == 1){
                loadDSHDDATE(2, textFieldSearchOption1.getText(),dateFrom,dateTo);
            }
            else{
                loadDSHDDATE(3, textFieldSearchOption1.getText(),dateFrom,dateTo);
            }
        }
    }//GEN-LAST:event_dateTUPropertyChange

    private void dateDENPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateDENPropertyChange
        // TODO add your handling code here:
          String dateFrom = chuyenDoi(dateTU.getDate());
         String dateTo = chuyenDoi(dateDEN.getDate());
        if (textFieldSearchOption1.isSelected()) {
            int option = textFieldSearchOption1.getSelectedIndex();
            if (option == 0) {
                loadDSHDDATE(1, textFieldSearchOption1.getText(),dateFrom,dateTo);
            } else if(option == 1){
                loadDSHDDATE(2, textFieldSearchOption1.getText(),dateFrom,dateTo);
            }
            else{
                loadDSHDDATE(3, textFieldSearchOption1.getText(),dateFrom,dateTo);
            }
        }
    }//GEN-LAST:event_dateDENPropertyChange

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        loadQLKH(0,"");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_sodienthoaiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_sodienthoaiKeyReleased
        // TODO add your handling code here:
        String sdt = txt_sodienthoai.getText();
        loadTenKH(sdt);
    }//GEN-LAST:event_txt_sodienthoaiKeyReleased

    private void jScrollPane4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane4MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane4MousePressed
    public static String maHD = "";
    private void tbHoaDonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbHoaDonMousePressed
        // TODO add your handling code here:
        int rowSP = tbHoaDon.getSelectedRow();
        maHD = (String) tbHoaDon.getValueAt(rowSP, 0);
        if (evt.getClickCount() == 2) {
            frmChiTietHD hd = new frmChiTietHD();
            hd.setVisible(true);
            hd.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
    }//GEN-LAST:event_tbHoaDonMousePressed

    private void button1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button1MouseEntered
        // TODO add your handling code here:
        button1.setBackground(new Color(0, 192, 239, 255));
    }//GEN-LAST:event_button1MouseEntered

    private void button1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button1MouseExited
        // TODO add your handling code here:
        button1.setBackground(new Color(211, 213, 214, 255));
    }//GEN-LAST:event_button1MouseExited
    public static String maSp ="";
    private void button1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button1MouseClicked
        // TODO add your handling code here:
        int flag = 0;
        int rowSP = tbSanPham.getSelectedRow();
        maSp = (String) tbSanPham.getValueAt(rowSP, 0);
        ResultSet rs = null;
        InputStream is = null;
        String maSP = frmNhanVienThuNgan.maSp;
        String sql = "SELECT HINHANHSP FROM qldata.SANPHAM WHERE MASANPHAM ='" + maSP + "'";
        try {
            rs = Database.excuteQuery(sql);
            if (rs.next()) {
                is = rs.getBinaryStream("HINHANHSP");
                BufferedImage img = ImageIO.read(is);
            }

        } catch (Exception e) {
            flag = 1;
            JOptionPane.showMessageDialog(rootPane, "Sản phẩm chưa có hình ảnh ");

        }
        if (flag != 1) {
            frmHinhSanPham hinh = new frmHinhSanPham();
            hinh.setVisible(true);
            hinh.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
    }//GEN-LAST:event_button1MouseClicked

    private void textFielKhachHangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textFielKhachHangKeyReleased
        // TODO add your handling code here:
        if (textFielKhachHang.isSelected()) {
            int option = textFielKhachHang.getSelectedIndex();
            if (option == 0) {
                loadQLKH(1, textFielKhachHang.getText());
            } else{
                loadQLKH(2, textFielKhachHang.getText());
            }  
        }
    }//GEN-LAST:event_textFielKhachHangKeyReleased
    public static String maKh1 = "";
    private void btn_suaKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_suaKhachHangMouseClicked
        // TODO add your handling code here:
        int flag = 0;
        int rowSP = tblKhachHang.getSelectedRow();
        maKh1 = (String) tblKhachHang.getValueAt(rowSP, 0);
        frmSuaKH suaKH = new frmSuaKH();
        suaKH.setVisible(true);
        suaKH.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_btn_suaKhachHangMouseClicked

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button2ActionPerformed

    private void bnt_lamMoiKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bnt_lamMoiKhachHangActionPerformed
        // TODO add your handling code here:
        loadQLKH(0,"");
    }//GEN-LAST:event_bnt_lamMoiKhachHangActionPerformed

    private void btn_themKhachHangMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_themKhachHangMouseEntered
        // TODO add your handling code here:
        btn_themKhachHang.setBackground(new Color(1, 166, 90, 255));
    }//GEN-LAST:event_btn_themKhachHangMouseEntered

    private void btn_suaKhachHangMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_suaKhachHangMouseEntered
        // TODO add your handling code here:
        btn_suaKhachHang.setBackground(new Color(243, 156, 17, 255));
    }//GEN-LAST:event_btn_suaKhachHangMouseEntered

    private void button2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button2MouseEntered
        // TODO add your handling code here:
        button2.setBackground(new Color(221, 76, 57, 255));
    }//GEN-LAST:event_button2MouseEntered

    private void btn_themKhachHangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_themKhachHangMouseExited
        // TODO add your handling code here:
        btn_themKhachHang.setBackground(new Color(211, 213, 214, 255));
    }//GEN-LAST:event_btn_themKhachHangMouseExited

    private void btn_suaKhachHangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_suaKhachHangMouseExited
        // TODO add your handling code here:
        btn_suaKhachHang.setBackground(new Color(211, 213, 214, 255));
    }//GEN-LAST:event_btn_suaKhachHangMouseExited

    private void button2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button2MouseExited
        // TODO add your handling code here:
        button2.setBackground(new Color(211, 213, 214, 255));
    }//GEN-LAST:event_button2MouseExited

    private void btn_themKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themKhachHangActionPerformed
        // TODO add your handling code here:
        frmThemKH themKH = new frmThemKH();
        themKH.setVisible(true);
        themKH.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
    }//GEN-LAST:event_btn_themKhachHangActionPerformed

    private void btn_suaKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suaKhachHangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_suaKhachHangActionPerformed

    private void button3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button3MouseClicked
        String mkcu = txtMkcu.getText();
        String mkmoi = txtMkmoi.getText();
        String nhapLaiMk = txtnhapLaiMK.getText();
        if(mkcu.equals("") || mkmoi.equals("") || nhapLaiMk.equals("")){
            lbThongBao.setText("Chưa điền đầy đủ thông tin");
        }else{
            if(!mkcu.equals(Database.password)){
                lbThongBao.setText("Mật khẩu cũ không đúng");
            }else{
                if(!mkmoi.equals(nhapLaiMk)){
                    lbThongBao.setText("Mật khẩu nhập lại không khớp");
                }
                else{
                    if(mkcu.equals(mkmoi)){
                        lbThongBao.setText("Mật khẩu mới giống mật khẩu cũ");
                    }
                    else{
                        String sql = "alter user " + Database.user + " identified by " + nhapLaiMk + "";
                        if (dataAdmin.excuteUpdate(sql)) {
                            lbThongBao.setText("");
                            txtMkcu.setText("");
                            txtMkmoi.setText("");
                            txtnhapLaiMK.setText("");
                            JOptionPane.showMessageDialog(rootPane, "Đổi mật khẩu thành công");
                            checkLanDau = 0;
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "Đổi mật khẩu thất bại");
                        }
                    }
                }
            }
        }

    }//GEN-LAST:event_button3MouseClicked

    private void button3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button3ActionPerformed

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel21MouseClicked
    
    public void closeFrom() {
        from1.setVisible(false);
        from2.setVisible(false);
        from3.setVisible(false);
        from4.setVisible(false);
        from5.setVisible(false);
        from6.setVisible(false);
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
            java.util.logging.Logger.getLogger(frmNhanVienThuNgan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmNhanVienThuNgan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmNhanVienThuNgan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmNhanVienThuNgan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmNhanVienThuNgan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private boder_panel.PanelRound PanelTool;
    private javax.swing.JPanel backround;
    private sample.message.Button bnt_lamMoiKhachHang;
    private sample.message.Button btn_suaKhachHang;
    private sample.message.Button btn_themKhachHang;
    private sample.message.Button button1;
    private sample.message.Button button2;
    private sample.message.Button button3;
    private combobox.Combobox cboKT;
    private combobox.Combobox cboKT1;
    private combobox.Combobox cboLoai;
    private combobox.Combobox cboLoai1;
    private combobox.Combobox cboMau;
    private combobox.Combobox cboMau1;
    private com.toedter.calendar.JDateChooser dateDEN;
    private com.toedter.calendar.JDateChooser dateTU;
    private javax.swing.JPanel from1;
    private javax.swing.JPanel from2;
    private javax.swing.JPanel from3;
    private javax.swing.JPanel from4;
    private javax.swing.JPanel from5;
    private javax.swing.JPanel from6;
    private javax.swing.JPanel from7;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lbNV;
    private javax.swing.JLabel lbThongBao;
    private javax.swing.JPanel menu;
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
    private boder_panel.PanelRound panelRound2;
    private boder_panel.PanelRound panelRound20;
    private boder_panel.PanelRound panelRound21;
    private boder_panel.PanelRound panelRound22;
    private boder_panel.PanelRound panelRound23;
    private boder_panel.PanelRound panelRound24;
    private boder_panel.PanelRound panelRound25;
    private boder_panel.PanelRound panelRound26;
    private boder_panel.PanelRound panelRound27;
    private boder_panel.PanelRound panelRound3;
    private boder_panel.PanelRound panelRound4;
    private boder_panel.PanelRound panelRound5;
    private boder_panel.PanelRound panelRound6;
    private boder_panel.PanelRound panelRound7;
    private boder_panel.PanelRound panelRound8;
    private boder_panel.PanelRound panelRound9;
    private table.TableCustom tableCustom1;
    private table.TableCustom tableCustom2;
    private table.TableCustom tableCustom3;
    private table.TableCustom tableCustom4;
    private table.TableCustom tableCustom5;
    private table.TableScrollButton tableScrollButton1;
    private table.TableScrollButton tableScrollButton2;
    private table.TableScrollButton tableScrollButton3;
    private table.TableScrollButton tableScrollButton4;
    private table.TableScrollButton tableScrollButton5;
    private javax.swing.JTable tbHoaDon;
    private javax.swing.JTable tbSanPham;
    private javax.swing.JTable tblKhachHang;
    private javax.swing.JTable tblNhapHang;
    private javax.swing.JTable tblSanPham;
    private textfield.TextFieldSearchOption textFielKhachHang;
    private textfield.TextFieldSearchOption textFieldSearchOption1;
    private javax.swing.JPanel thongtin;
    private javaswingdev.swing.titlebar.TitleBar titleBar1;
    private textfield.PasswordField txtMkcu;
    private textfield.PasswordField txtMkmoi;
    private textfield.TextFieldSearchOption txtSearch;
    private textfield.TextFieldSearchOption txtSearch1;
    private textfield_suggestion.TextFieldSuggestion txtSoLuong;
    private javax.swing.JLabel txtTongTien;
    private textfield_suggestion.TextFieldSuggestion txt_Khachhang;
    private textfield_suggestion.TextFieldSuggestion txt_MaNhavien;
    private textfield_suggestion.TextFieldSuggestion txt_hotenNhanVien;
    private textfield_suggestion.TextFieldSuggestion txt_maHD;
    private textfield_suggestion.TextFieldSuggestion txt_sodienthoai;
    private textfield.PasswordField txtnhapLaiMK;
    // End of variables declaration//GEN-END:variables

    private String createId(ArrayList<hoaDonPojo> arr) {
        int id = arr.size() + 1;
        String check = "";
        for (hoaDonPojo hoaDon : arr) {
            if (hoaDon.getMaHD().equals("HD00" + id)) {
                check = hoaDon.getMaHD();
            }
        }

        while (check.length() != 0) {
            String c = check;
            id++;
            for (int i = 0; i < arr.size(); i++) {
                if (arr.get(i).getMaHD().contains(String.format("HD%03d", id)) && id <= 999) {
                    check = arr.get(i).getMaHD();
                }
            }
            if (check.equals(c)) {
                check = "";
            }
        }
        return String.format("HD%03d", id);
    }
    private String createIdKhachHang(ArrayList<khachHangPojo> arr) {
        int id = arr.size() + 1;
        String check = "";
        for (khachHangPojo khachHang : arr) {
            if (khachHang.getMaKH().equals("KH00" + id)) {
                check = khachHang.getMaKH();
            }
        }

        while (check.length() != 0) {
            String c = check;
            id++;
            for (int i = 0; i < arr.size(); i++) {
                if (arr.get(i).getMaKH().contains(String.format("KH%03d", id)) && id <= 999) {
                    check = arr.get(i).getMaKH();
                }
            }
            if (check.equals(c)) {
                check = "";
            }
        }
        return String.format("KH%03d", id);
    }
    public String id_NCC() {
        int id = 1;
        ArrayList<nhaCungCapPojo> listNCC = new nhaCungCapDao().layMaNCC();
        Set<String> maNCCSet = new HashSet<String>();
        for (nhaCungCapPojo ncc : listNCC) {
            String maNCC = ncc.getMancc();
            maNCCSet.add(maNCC);
        }
        while (maNCCSet.contains(String.format("NCC%03d", id)) && id <= 999) {
            id++;
        }
        return String.format("NCC%03d", id);
    }
    public double tinhTongTien() {
        double tt = 0;
        for (var i : CTPhieu) {
            tt += i.getDonGia() * i.getSoLuong();
        }
        return tt;
    }

    public sanPhamPojo findSanPham(String maMay) {
        for (var i : allProduct) {
            if (maMay.equals(i.getMaSanPham())) {
                return i;
            }
        }
        return null;
    }
    public khachHangPojo findSDTKhachHang(String sdt) {
        for (var i : allKhachHang) {
            if (sdt.equals(i.getSoDt())) {
                return i;
            }
        }
        return null;
    }

    public chiTietHoaDonPojo findCTPhieu(String maMay) {
        for (var i : CTPhieu) {
            if (maMay.equals(i.getMaSanPham())) {
                return i;
            }
        }
        return null;
    }

    private void loadDataToTableNhapHang() {
        double sum = 0;
            
        try {
            DefaultTableModel tblNhapHangmd = (DefaultTableModel) tblNhapHang.getModel();
            tblNhapHangmd.setColumnIdentifiers(new String[]{"Số thứ tự","Mã sản phẩm","Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn giá"});
            tblNhapHangmd.setRowCount(0);
            tblNhapHang.getColumnModel().getColumn(1).setMinWidth(1);
            tblNhapHang.getColumnModel().getColumn(1).setMaxWidth(1);
            tblNhapHang.getColumnModel().getColumn(1).setWidth(1);
            tblNhapHang.getColumnModel().getColumn(1).setPreferredWidth(1);
            for (int i = 0; i < CTPhieu.size(); i++) {
                tblNhapHangmd.addRow(new Object[]{
                    i + 1, CTPhieu.get(i).getMaSanPham(),findSanPham(CTPhieu.get(i).getMaSanPham()).getCode() ,findSanPham(CTPhieu.get(i).getMaSanPham()).getTenSanPham(), CTPhieu.get(i).getSoLuong(), formatter.format(CTPhieu.get(i).getDonGia()) + " VNĐ"
                });
                sum += CTPhieu.get(i).getDonGia();
            }
        } catch (Exception e) {
        }
        txtTongTien.setText(formatter.format(sum) + "đ");
    }
}
