/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import DAO.Database;
import DAO.dataAdmin;
import DAO.kichThuocDao;
import DAO.loaiSanPhamDao;
import DAO.mauSacDao;
import DAO.nhaCungCapDao;
import DAO.nhanVienDao;
import DAO.phieuNhapDao;
import DAO.sanPhamDao;
import POJO.kichThuocPojo;
import POJO.loaiSanPhamPojo;
import POJO.mauSacPojo;
import POJO.nhaCungCapPojo;
import POJO.nhanVienPojo;
import POJO.phieuNhapPojo;
import POJO.sanPhamPojo;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import table.TableCustom;
import textfield.SearchOptinEvent;
import textfield.SearchOption;

/**
 *
 * @author AnhDoan
 */
public class frmNhanVienKho extends javax.swing.JFrame {


    /**
     * Creates new form frmAdmin
     */
    DecimalFormat formatter = new DecimalFormat("###,###,###");

    public frmNhanVienKho() {
        initComponents();
        init();
        loadQLSP(0, "", "Tất cả", "Tất cả", "Tất cả");
        loadM();
        loadLSP();
        loadKT();
        loadTextSearch();
        loadTextSearch1();
        loadTextSearch2();
        loadDateVN();
        loadDSPN(0, "", "null", "null");
        loadQLNCC(0, "");
        loadQLLSP();
        dataAdmin.Connect();
        tableCustom2.apply(jScrollPane1, TableCustom.TableType.DEFAULT);
        tableCustom1.apply(jScrollPane2, TableCustom.TableType.DEFAULT);
        tableCustom3.apply(jScrollPane3, TableCustom.TableType.DEFAULT);
        tableCustom4.apply(jScrollPane4, TableCustom.TableType.DEFAULT);
        tbSanPham.setDefaultEditor(Object.class, null);
        tbPN.setDefaultEditor(Object.class, null);
        tblNCC.setDefaultEditor(Object.class, null);
        tblLSP.setDefaultEditor(Object.class, null);
        lbName.setText(tenNV());
        lbThongBao.setText("");
        panelRound2.setEnabled(false);
        
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
            JOptionPane.showMessageDialog(rootPane, "Bạn cần thay đổi mật khẩu cho lần đầu đăng nhập !");
            resetFlags();
            flag6 = 1;
            enableColor();
            panelRound6.setBackground(new Color(176, 210, 235, 255));
            closeFrom();
            from6.setVisible(true);
            checkLanDau = 1;
        }
    }
    
    public void loadQLLSP() {
        try {
            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(new String[]{"Mã loại sản phẩm", "Tên loại sản phẩm"});
            loaiSanPhamDao lspDAO = new loaiSanPhamDao();
            ArrayList<loaiSanPhamPojo> dsLSP = lspDAO.layDSLSP();
            for (loaiSanPhamPojo lsp : dsLSP) {
                String maLSP = lsp.getMaLoaiSanPham();
                String tenLSP = lsp.getTenLoaiSanPham();
                model.addRow(new Object[]{maLSP, tenLSP});
            }
            tblLSP.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void loadQLNCC(int checkSQL, String text) {
        try {
            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(new String[]{"Mã nhà cung cấp", "Tên nhà cung cấp", "Số điện thoại", "Email", "Địa chỉ"});
            nhaCungCapDao nccDAO = new nhaCungCapDao();
            ArrayList<nhaCungCapPojo> dsNCC = nccDAO.layDSNCC1(checkSQL, text);
            for (nhaCungCapPojo ncc : dsNCC) {
                String tenNCC = ncc.getTenncc();
                String maNCC = ncc.getMancc();
                String diaChi = ncc.getDiachi();
                String email = ncc.getEmail();
                String sdt = ncc.getSdt();
                model.addRow(new Object[]{maNCC, tenNCC, sdt, email, diaChi});
            }
            tblNCC.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        titleBar1.initJFram(this);
    }
    
    private String tenNV(){
        String name ="";
        ArrayList<nhanVienPojo> dsnv = new nhanVienDao().layDSNV2(Database.user.toUpperCase());
        for(nhanVienPojo nv : dsnv){
            name = nv.getHoTen();
        }
        
        return name;
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

    public void loadDateVN() {
        dateTu.setLocale(new Locale("vi", "VN"));
        dateDen.setLocale(new Locale("vi", "VN"));
    }

    public void loadTextSearch() {
        txtSearch.addEventOptionSelected(new SearchOptinEvent() {
            @Override
            public void optionSelected(SearchOption option, int index) {
                txtSearch.setHint("Nhập " + option.getName() + "...");
            }
        });
        txtSearch.addOption(new SearchOption("tên sản phẩm", new ImageIcon(getClass().getResource("/icons/product.png"))));
        txtSearch.addOption(new SearchOption("mã sản phẩm", new ImageIcon(getClass().getResource("/icons/code.png"))));
    }
    
    public void loadTextSearch2() {
        txtSearch1.addEventOptionSelected(new SearchOptinEvent() {
            @Override
            public void optionSelected(SearchOption option, int index) {
                txtSearch1.setHint("Nhập " + option.getName() + "...");
            }
        });
        txtSearch1.addOption(new SearchOption("mã nhà cung cấp", new ImageIcon(getClass().getResource("/icons/code.png"))));
        txtSearch1.addOption(new SearchOption("tên nhà cung cấp", new ImageIcon(getClass().getResource("/icons/user.png"))));
        txtSearch1.addOption(new SearchOption("số điện thoại", new ImageIcon(getClass().getResource("/icons/tel.png"))));
        txtSearch1.addOption(new SearchOption("email", new ImageIcon(getClass().getResource("/icons/email.png"))));
    }

    public void loadTextSearch1() {
        txtSearch2.addEventOptionSelected(new SearchOptinEvent() {
            @Override
            public void optionSelected(SearchOption option, int index) {
                txtSearch2.setHint("Nhập " + option.getName() + "...");
            }
        });
        txtSearch2.addOption(new SearchOption("tên nhân viên lập", new ImageIcon(getClass().getResource("/icons/user.png"))));
        txtSearch2.addOption(new SearchOption("tên nhà cung cấp", new ImageIcon(getClass().getResource("/icons/gr.png"))));
        txtSearch2.addOption(new SearchOption("mã phiếu", new ImageIcon(getClass().getResource("/icons/code.png"))));
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
            model.setColumnIdentifiers(new String[]{"Mã sản phẩm", "Mã sản phẩm", "Tên sản phẩm", "Giá bán", "Số lượng tồn", "Màu Sắc", "Kích thước"});
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
                model.addRow(new Object[]{maSP, code, tenSP, formatter.format(giaBan) + " VNĐ", soLuongTon, mauSac, kichThuoc});
            }

            tbSanPham.setModel(model);
            tbSanPham.getColumnModel().getColumn(0).setMinWidth(0);
            tbSanPham.getColumnModel().getColumn(0).setMaxWidth(0);
            tbSanPham.getColumnModel().getColumn(0).setWidth(0);
            tbSanPham.getColumnModel().getColumn(0).setPreferredWidth(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadQLSP1() {
        try {
            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(new String[]{"Mã sản phẩm", "Mã sản phẩm", "Tên sản phẩm", "Giá bán", "Số lượng tồn", "Màu Sắc", "Kích thước"});
            sanPhamDao spDAO = new sanPhamDao();
            ArrayList<sanPhamPojo> dsSP = spDAO.layDSSP1();
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
                model.addRow(new Object[]{maSP, code, tenSP, formatter.format(giaBan) + " VNĐ", soLuongTon, mauSac, kichThuoc});
            }

            tbSanPham.setModel(model);
            tbSanPham.getColumnModel().getColumn(0).setMinWidth(0);
            tbSanPham.getColumnModel().getColumn(0).setMaxWidth(0);
            tbSanPham.getColumnModel().getColumn(0).setWidth(0);
            tbSanPham.getColumnModel().getColumn(0).setPreferredWidth(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadDSPN(int check, String text, String dateFrom, String dateTo) {
        try {
            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(new String[]{"Mã phiếu nhập", "Nhà cung cấp", "Người lập", "Thời gian lập"});
            phieuNhapDao pnDAO = new phieuNhapDao();
            ArrayList<phieuNhapPojo> dsPN = pnDAO.layDSPN1(check, text, dateFrom, dateTo);
            for (phieuNhapPojo pn : dsPN) {
                String maPN = pn.getMaPhieu();
                String tenNCC = pn.getTenNCC();
                String tenNV = pn.getTenNV();
                String ngayLap = pn.getNgayNhap();
                model.addRow(new Object[]{maPN, tenNCC, tenNV, ngayLap});
            }

            tbPN.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
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

        componentResizer1 = new javaswingdev.swing.titlebar.ComponentResizer();
        tableCustom1 = new table.TableCustom();
        tableCustom2 = new table.TableCustom();
        tableCustom3 = new table.TableCustom();
        tableCustom4 = new table.TableCustom();
        backround = new javax.swing.JPanel();
        panel = new javax.swing.JPanel();
        thongtin = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        lbName = new javax.swing.JLabel();
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
        panelRound7 = new boder_panel.PanelRound();
        jLabel10 = new javax.swing.JLabel();
        panelRound6 = new boder_panel.PanelRound();
        jLabel32 = new javax.swing.JLabel();
        titleBar1 = new javaswingdev.swing.titlebar.TitleBar();
        jPanel3 = new javax.swing.JPanel();
        from1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        panelRound8 = new boder_panel.PanelRound();
        panelRound11 = new boder_panel.PanelRound();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        panelRound12 = new boder_panel.PanelRound();
        panelRound13 = new boder_panel.PanelRound();
        jLabel24 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        panelRound14 = new boder_panel.PanelRound();
        panelRound20 = new boder_panel.PanelRound();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        from4 = new javax.swing.JPanel();
        panelRound17 = new boder_panel.PanelRound();
        txtSearch1 = new textfield.TextFieldSearchOption();
        panelRound21 = new boder_panel.PanelRound();
        jLabel16 = new javax.swing.JLabel();
        panelRound22 = new boder_panel.PanelRound();
        jLabel11 = new javax.swing.JLabel();
        panelRound23 = new boder_panel.PanelRound();
        jLabel26 = new javax.swing.JLabel();
        panelRound24 = new boder_panel.PanelRound();
        jLabel27 = new javax.swing.JLabel();
        tableScrollButton3 = new table.TableScrollButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblNCC = new javax.swing.JTable();
        from5 = new javax.swing.JPanel();
        panelRound25 = new boder_panel.PanelRound();
        panelRound26 = new boder_panel.PanelRound();
        jLabel31 = new javax.swing.JLabel();
        panelRound27 = new boder_panel.PanelRound();
        jLabel9 = new javax.swing.JLabel();
        panelRound28 = new boder_panel.PanelRound();
        jLabel12 = new javax.swing.JLabel();
        panelRound29 = new boder_panel.PanelRound();
        jLabel13 = new javax.swing.JLabel();
        tableScrollButton4 = new table.TableScrollButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblLSP = new javax.swing.JTable();
        from2 = new javax.swing.JPanel();
        panelRound9 = new boder_panel.PanelRound();
        txtSearch = new textfield.TextFieldSearchOption();
        panelRound15 = new boder_panel.PanelRound();
        jLabel14 = new javax.swing.JLabel();
        btnNhap = new boder_panel.PanelRound();
        jLabel2 = new javax.swing.JLabel();
        panelRound18 = new boder_panel.PanelRound();
        jLabel1 = new javax.swing.JLabel();
        panelRound19 = new boder_panel.PanelRound();
        jLabel34 = new javax.swing.JLabel();
        panelRound30 = new boder_panel.PanelRound();
        jLabel25 = new javax.swing.JLabel();
        tableScrollButton1 = new table.TableScrollButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbSanPham = new javax.swing.JTable();
        cboLoai = new combobox.Combobox();
        cboMau = new combobox.Combobox();
        cboKT = new combobox.Combobox();
        from3 = new javax.swing.JPanel();
        panelRound10 = new boder_panel.PanelRound();
        txtSearch2 = new textfield.TextFieldSearchOption();
        panelRound16 = new boder_panel.PanelRound();
        jLabel18 = new javax.swing.JLabel();
        dateTu = new com.toedter.calendar.JDateChooser();
        jLabel19 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        dateDen = new com.toedter.calendar.JDateChooser();
        tableScrollButton2 = new table.TableScrollButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPN = new javax.swing.JTable();
        from6 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        txtMkcu = new textfield.PasswordField();
        txtMkmoi = new textfield.PasswordField();
        txtnhapLaiMK = new textfield.PasswordField();
        button1 = new sample.message.Button();
        lbThongBao = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        panel.setBackground(new java.awt.Color(255, 255, 255));

        thongtin.setBackground(new java.awt.Color(255, 255, 255));
        thongtin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/boy.png"))); // NOI18N

        lbName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbName.setText("Bùi Thanh Luân");

        jLabel17.setForeground(new java.awt.Color(153, 153, 153));
        jLabel17.setText("Nhân viên kho");

        javax.swing.GroupLayout thongtinLayout = new javax.swing.GroupLayout(thongtin);
        thongtin.setLayout(thongtinLayout);
        thongtinLayout.setHorizontalGroup(
            thongtinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thongtinLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(thongtinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbName, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        thongtinLayout.setVerticalGroup(
            thongtinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thongtinLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(thongtinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(thongtinLayout.createSequentialGroup()
                        .addComponent(lbName)
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
                .addGap(0, 35, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/receipt.png"))); // NOI18N
        jLabel6.setText("  Phiếu nhập");

        javax.swing.GroupLayout panelRound3Layout = new javax.swing.GroupLayout(panelRound3);
        panelRound3.setLayout(panelRound3Layout);
        panelRound3Layout.setHorizontalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/supplier1.png"))); // NOI18N
        jLabel7.setText("  Nhà cung cấp");

        javax.swing.GroupLayout panelRound4Layout = new javax.swing.GroupLayout(panelRound4);
        panelRound4.setLayout(panelRound4Layout);
        panelRound4Layout.setHorizontalGroup(
            panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        jLabel8.setText(" Quản lý loại sản phẩm");

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

        jLabel32.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reset-password.png"))); // NOI18N
        jLabel32.setText(" Đổi mật khẩu");

        javax.swing.GroupLayout panelRound6Layout = new javax.swing.GroupLayout(panelRound6);
        panelRound6.setLayout(panelRound6Layout);
        panelRound6Layout.setHorizontalGroup(
            panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelRound6Layout.setVerticalGroup(
            panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
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
            .addComponent(panelRound7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelRound6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        from1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(51, 102, 255));
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/JAVA.png"))); // NOI18N
        jLabel20.setText("HỆ THỐNG QUẢN LÝ CỬA HÀNG THỜI TRANG");
        jLabel20.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel20.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jLabel21.setFont(new java.awt.Font("Segoe UI", 2, 16)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(51, 102, 255));
        jLabel21.setText("Dù chỉ một lần trong đời, hãy thử điều gì đó. Nỗ lực vì điều gì đó. Hãy thử thay đổi, không gì tồi tệ có thể xảy ra đâu");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(229, 229, 229))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(115, 115, 115))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel21)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(238, 250, 254));

        panelRound8.setBackground(new java.awt.Color(255, 255, 255));
        panelRound8.setRoundBottomLeft(100);
        panelRound8.setRoundBottomRight(100);
        panelRound8.setRoundTopLeft(100);
        panelRound8.setRoundTopRight(100);

        panelRound11.setBackground(new java.awt.Color(238, 250, 254));
        panelRound11.setRoundBottomLeft(100);
        panelRound11.setRoundBottomRight(100);
        panelRound11.setRoundTopLeft(100);
        panelRound11.setRoundTopRight(100);

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/target.png"))); // NOI18N

        javax.swing.GroupLayout panelRound11Layout = new javax.swing.GroupLayout(panelRound11);
        panelRound11.setLayout(panelRound11Layout);
        panelRound11Layout.setHorizontalGroup(
            panelRound11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound11Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(59, Short.MAX_VALUE))
        );
        panelRound11Layout.setVerticalGroup(
            panelRound11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound11Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel22)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel23.setText("TÍNH CHÍNH XÁC");

        javax.swing.GroupLayout panelRound8Layout = new javax.swing.GroupLayout(panelRound8);
        panelRound8.setLayout(panelRound8Layout);
        panelRound8Layout.setHorizontalGroup(
            panelRound8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound8Layout.createSequentialGroup()
                .addGroup(panelRound8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound8Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(panelRound11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelRound8Layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(jLabel23)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        panelRound8Layout.setVerticalGroup(
            panelRound8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound8Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(panelRound11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel23)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelRound12.setBackground(new java.awt.Color(255, 255, 255));
        panelRound12.setRoundBottomLeft(100);
        panelRound12.setRoundBottomRight(100);
        panelRound12.setRoundTopLeft(100);
        panelRound12.setRoundTopRight(100);

        panelRound13.setBackground(new java.awt.Color(238, 250, 254));
        panelRound13.setRoundBottomLeft(100);
        panelRound13.setRoundBottomRight(100);
        panelRound13.setRoundTopLeft(100);
        panelRound13.setRoundTopRight(100);

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/security.png"))); // NOI18N

        javax.swing.GroupLayout panelRound13Layout = new javax.swing.GroupLayout(panelRound13);
        panelRound13.setLayout(panelRound13Layout);
        panelRound13Layout.setHorizontalGroup(
            panelRound13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound13Layout.createSequentialGroup()
                .addContainerGap(69, Short.MAX_VALUE)
                .addComponent(jLabel24)
                .addGap(62, 62, 62))
        );
        panelRound13Layout.setVerticalGroup(
            panelRound13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound13Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel24)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel28.setText("TÍNH BẢO MẬT");

        javax.swing.GroupLayout panelRound12Layout = new javax.swing.GroupLayout(panelRound12);
        panelRound12.setLayout(panelRound12Layout);
        panelRound12Layout.setHorizontalGroup(
            panelRound12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound12Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(panelRound13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel28)
                .addGap(94, 94, 94))
        );
        panelRound12Layout.setVerticalGroup(
            panelRound12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound12Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(panelRound13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel28)
                .addContainerGap(147, Short.MAX_VALUE))
        );

        panelRound14.setBackground(new java.awt.Color(255, 255, 255));
        panelRound14.setRoundBottomLeft(100);
        panelRound14.setRoundBottomRight(100);
        panelRound14.setRoundTopLeft(100);
        panelRound14.setRoundTopRight(100);

        panelRound20.setBackground(new java.awt.Color(238, 250, 254));
        panelRound20.setRoundBottomLeft(100);
        panelRound20.setRoundBottomRight(100);
        panelRound20.setRoundTopLeft(100);
        panelRound20.setRoundTopRight(100);

        jLabel29.setBackground(new java.awt.Color(238, 250, 254));
        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/efficiency.png"))); // NOI18N

        javax.swing.GroupLayout panelRound20Layout = new javax.swing.GroupLayout(panelRound20);
        panelRound20.setLayout(panelRound20Layout);
        panelRound20Layout.setHorizontalGroup(
            panelRound20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound20Layout.createSequentialGroup()
                .addContainerGap(65, Short.MAX_VALUE)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
        panelRound20Layout.setVerticalGroup(
            panelRound20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound20Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jLabel29)
                .addGap(14, 14, 14))
        );

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel30.setText("TÍNH HIỆU QUẢ");

        javax.swing.GroupLayout panelRound14Layout = new javax.swing.GroupLayout(panelRound14);
        panelRound14.setLayout(panelRound14Layout);
        panelRound14Layout.setHorizontalGroup(
            panelRound14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound14Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(panelRound20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel30)
                .addGap(91, 91, 91))
        );
        panelRound14Layout.setVerticalGroup(
            panelRound14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound14Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(panelRound20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(panelRound12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(panelRound14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelRound8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRound12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRound14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(33, Short.MAX_VALUE))
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

        from4.setBackground(new java.awt.Color(240, 247, 250));

        panelRound17.setBackground(new java.awt.Color(255, 255, 255));
        panelRound17.setRoundBottomLeft(10);
        panelRound17.setRoundBottomRight(10);
        panelRound17.setRoundTopLeft(10);
        panelRound17.setRoundTopRight(10);

        txtSearch1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearch1KeyReleased(evt);
            }
        });

        panelRound21.setBackground(new java.awt.Color(211, 213, 214));
        panelRound21.setRoundBottomLeft(10);
        panelRound21.setRoundBottomRight(10);
        panelRound21.setRoundTopLeft(10);
        panelRound21.setRoundTopRight(10);
        panelRound21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelRound21MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelRound21MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelRound21MouseExited(evt);
            }
        });

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/refresh (1).png"))); // NOI18N
        jLabel16.setText("Làm mới");

        javax.swing.GroupLayout panelRound21Layout = new javax.swing.GroupLayout(panelRound21);
        panelRound21.setLayout(panelRound21Layout);
        panelRound21Layout.setHorizontalGroup(
            panelRound21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound21Layout.createSequentialGroup()
                .addGap(0, 16, Short.MAX_VALUE)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelRound21Layout.setVerticalGroup(
            panelRound21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelRound22.setBackground(new java.awt.Color(211, 213, 214));
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

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 102, 102));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add20.png"))); // NOI18N
        jLabel11.setText("Thêm");

        javax.swing.GroupLayout panelRound22Layout = new javax.swing.GroupLayout(panelRound22);
        panelRound22.setLayout(panelRound22Layout);
        panelRound22Layout.setHorizontalGroup(
            panelRound22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        panelRound22Layout.setVerticalGroup(
            panelRound22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelRound23.setBackground(new java.awt.Color(211, 213, 214));
        panelRound23.setRoundBottomLeft(10);
        panelRound23.setRoundBottomRight(10);
        panelRound23.setRoundTopLeft(10);
        panelRound23.setRoundTopRight(10);
        panelRound23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelRound23MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelRound23MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelRound23MouseExited(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(102, 102, 102));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/pencil20.png"))); // NOI18N
        jLabel26.setText("Sửa");

        javax.swing.GroupLayout panelRound23Layout = new javax.swing.GroupLayout(panelRound23);
        panelRound23.setLayout(panelRound23Layout);
        panelRound23Layout.setHorizontalGroup(
            panelRound23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        panelRound23Layout.setVerticalGroup(
            panelRound23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelRound24.setBackground(new java.awt.Color(211, 213, 214));
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

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(102, 102, 102));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/trash20.png"))); // NOI18N
        jLabel27.setText("Xoá");

        javax.swing.GroupLayout panelRound24Layout = new javax.swing.GroupLayout(panelRound24);
        panelRound24.setLayout(panelRound24Layout);
        panelRound24Layout.setHorizontalGroup(
            panelRound24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        panelRound24Layout.setVerticalGroup(
            panelRound24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelRound17Layout = new javax.swing.GroupLayout(panelRound17);
        panelRound17.setLayout(panelRound17Layout);
        panelRound17Layout.setHorizontalGroup(
            panelRound17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound17Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(panelRound22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(panelRound23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(panelRound24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 171, Short.MAX_VALUE)
                .addComponent(txtSearch1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(panelRound21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        panelRound17Layout.setVerticalGroup(
            panelRound17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound17Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelRound17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSearch1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRound21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRound22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRound23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRound24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        tblNCC.setModel(new javax.swing.table.DefaultTableModel(
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
        tblNCC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblNCCKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(tblNCC);

        tableScrollButton3.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout from4Layout = new javax.swing.GroupLayout(from4);
        from4.setLayout(from4Layout);
        from4Layout.setHorizontalGroup(
            from4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(from4Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(from4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelRound17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tableScrollButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(87, Short.MAX_VALUE))
        );
        from4Layout.setVerticalGroup(
            from4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(from4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(panelRound17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(tableScrollButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jPanel3.add(from4, "card5");

        from5.setBackground(new java.awt.Color(240, 247, 250));

        panelRound25.setBackground(new java.awt.Color(255, 255, 255));
        panelRound25.setRoundBottomLeft(10);
        panelRound25.setRoundBottomRight(10);
        panelRound25.setRoundTopLeft(10);
        panelRound25.setRoundTopRight(10);

        panelRound26.setBackground(new java.awt.Color(211, 213, 214));
        panelRound26.setRoundBottomLeft(10);
        panelRound26.setRoundBottomRight(10);
        panelRound26.setRoundTopLeft(10);
        panelRound26.setRoundTopRight(10);
        panelRound26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelRound26MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelRound26MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelRound26MouseExited(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(102, 102, 102));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/refresh (1).png"))); // NOI18N
        jLabel31.setText("Làm mới");

        javax.swing.GroupLayout panelRound26Layout = new javax.swing.GroupLayout(panelRound26);
        panelRound26.setLayout(panelRound26Layout);
        panelRound26Layout.setHorizontalGroup(
            panelRound26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
        );
        panelRound26Layout.setVerticalGroup(
            panelRound26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelRound27.setBackground(new java.awt.Color(211, 213, 214));
        panelRound27.setRoundBottomLeft(10);
        panelRound27.setRoundBottomRight(10);
        panelRound27.setRoundTopLeft(10);
        panelRound27.setRoundTopRight(10);
        panelRound27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelRound27MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelRound27MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelRound27MouseExited(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add20.png"))); // NOI18N
        jLabel9.setText("Thêm");

        javax.swing.GroupLayout panelRound27Layout = new javax.swing.GroupLayout(panelRound27);
        panelRound27.setLayout(panelRound27Layout);
        panelRound27Layout.setHorizontalGroup(
            panelRound27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        panelRound27Layout.setVerticalGroup(
            panelRound27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelRound28.setBackground(new java.awt.Color(211, 213, 214));
        panelRound28.setRoundBottomLeft(10);
        panelRound28.setRoundBottomRight(10);
        panelRound28.setRoundTopLeft(10);
        panelRound28.setRoundTopRight(10);
        panelRound28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelRound28MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelRound28MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelRound28MouseExited(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(102, 102, 102));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/pencil20.png"))); // NOI18N
        jLabel12.setText("Sửa");

        javax.swing.GroupLayout panelRound28Layout = new javax.swing.GroupLayout(panelRound28);
        panelRound28.setLayout(panelRound28Layout);
        panelRound28Layout.setHorizontalGroup(
            panelRound28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        panelRound28Layout.setVerticalGroup(
            panelRound28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelRound29.setBackground(new java.awt.Color(211, 213, 214));
        panelRound29.setRoundBottomLeft(10);
        panelRound29.setRoundBottomRight(10);
        panelRound29.setRoundTopLeft(10);
        panelRound29.setRoundTopRight(10);
        panelRound29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelRound29MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelRound29MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelRound29MouseExited(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(102, 102, 102));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/trash20.png"))); // NOI18N
        jLabel13.setText("Xoá");

        javax.swing.GroupLayout panelRound29Layout = new javax.swing.GroupLayout(panelRound29);
        panelRound29.setLayout(panelRound29Layout);
        panelRound29Layout.setHorizontalGroup(
            panelRound29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        panelRound29Layout.setVerticalGroup(
            panelRound29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelRound25Layout = new javax.swing.GroupLayout(panelRound25);
        panelRound25.setLayout(panelRound25Layout);
        panelRound25Layout.setHorizontalGroup(
            panelRound25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound25Layout.createSequentialGroup()
                .addGap(114, 114, 114)
                .addComponent(panelRound27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(127, 127, 127)
                .addComponent(panelRound28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(109, 109, 109)
                .addComponent(panelRound29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 99, Short.MAX_VALUE)
                .addComponent(panelRound26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
        );
        panelRound25Layout.setVerticalGroup(
            panelRound25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound25Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelRound25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelRound29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRound28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRound27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRound26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(25, 25, 25))
        );

        tblLSP.setModel(new javax.swing.table.DefaultTableModel(
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
        tblLSP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblLSPKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(tblLSP);

        tableScrollButton4.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout from5Layout = new javax.swing.GroupLayout(from5);
        from5.setLayout(from5Layout);
        from5Layout.setHorizontalGroup(
            from5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(from5Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(from5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelRound25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tableScrollButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(87, Short.MAX_VALUE))
        );
        from5Layout.setVerticalGroup(
            from5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(from5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(panelRound25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(tableScrollButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jPanel3.add(from5, "card6");

        from2.setBackground(new java.awt.Color(240, 247, 250));

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

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/refresh (1).png"))); // NOI18N
        jLabel14.setText("Làm mới");

        javax.swing.GroupLayout panelRound15Layout = new javax.swing.GroupLayout(panelRound15);
        panelRound15.setLayout(panelRound15Layout);
        panelRound15Layout.setHorizontalGroup(
            panelRound15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound15Layout.createSequentialGroup()
                .addGap(0, 16, Short.MAX_VALUE)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelRound15Layout.setVerticalGroup(
            panelRound15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnNhap.setBackground(new java.awt.Color(211, 213, 214));
        btnNhap.setRoundBottomLeft(10);
        btnNhap.setRoundBottomRight(10);
        btnNhap.setRoundTopLeft(10);
        btnNhap.setRoundTopRight(10);
        btnNhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNhapMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnNhapMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnNhapMouseExited(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add20.png"))); // NOI18N
        jLabel2.setText("Nhập");

        javax.swing.GroupLayout btnNhapLayout = new javax.swing.GroupLayout(btnNhap);
        btnNhap.setLayout(btnNhapLayout);
        btnNhapLayout.setHorizontalGroup(
            btnNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        btnNhapLayout.setVerticalGroup(
            btnNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/trash20.png"))); // NOI18N
        jLabel1.setText("Xoá");

        javax.swing.GroupLayout panelRound18Layout = new javax.swing.GroupLayout(panelRound18);
        panelRound18.setLayout(panelRound18Layout);
        panelRound18Layout.setHorizontalGroup(
            panelRound18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        panelRound18Layout.setVerticalGroup(
            panelRound18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        jLabel34.setForeground(new java.awt.Color(102, 102, 102));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-info-20.png"))); // NOI18N
        jLabel34.setText("Hình sản phẩm");

        javax.swing.GroupLayout panelRound19Layout = new javax.swing.GroupLayout(panelRound19);
        panelRound19.setLayout(panelRound19Layout);
        panelRound19Layout.setHorizontalGroup(
            panelRound19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel34, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelRound19Layout.setVerticalGroup(
            panelRound19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelRound30.setBackground(new java.awt.Color(211, 213, 214));
        panelRound30.setRoundBottomLeft(10);
        panelRound30.setRoundBottomRight(10);
        panelRound30.setRoundTopLeft(10);
        panelRound30.setRoundTopRight(10);
        panelRound30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelRound30MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelRound30MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelRound30MouseExited(evt);
            }
        });

        jLabel25.setForeground(new java.awt.Color(102, 102, 102));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/pencil20.png"))); // NOI18N
        jLabel25.setText("Sửa");

        javax.swing.GroupLayout panelRound30Layout = new javax.swing.GroupLayout(panelRound30);
        panelRound30.setLayout(panelRound30Layout);
        panelRound30Layout.setHorizontalGroup(
            panelRound30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        panelRound30Layout.setVerticalGroup(
            panelRound30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelRound9Layout = new javax.swing.GroupLayout(panelRound9);
        panelRound9.setLayout(panelRound9Layout);
        panelRound9Layout.setHorizontalGroup(
            panelRound9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound9Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(btnNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(panelRound18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(panelRound30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(panelRound19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
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
                    .addComponent(panelRound30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelRound9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(panelRound19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelRound15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelRound18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(25, Short.MAX_VALUE))
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
                .addGap(70, 70, 70)
                .addGroup(from2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(from2Layout.createSequentialGroup()
                        .addComponent(cboLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(cboMau, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(cboKT, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(from2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(panelRound9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tableScrollButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        from2Layout.setVerticalGroup(
            from2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(from2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(panelRound9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(from2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboMau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboLoai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboKT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tableScrollButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.add(from2, "card3");

        from3.setBackground(new java.awt.Color(240, 247, 250));

        panelRound10.setBackground(new java.awt.Color(255, 255, 255));
        panelRound10.setRoundBottomLeft(10);
        panelRound10.setRoundBottomRight(10);
        panelRound10.setRoundTopLeft(10);
        panelRound10.setRoundTopRight(10);

        txtSearch2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearch2KeyReleased(evt);
            }
        });

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

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/refresh (1).png"))); // NOI18N
        jLabel18.setText("Làm mới");

        javax.swing.GroupLayout panelRound16Layout = new javax.swing.GroupLayout(panelRound16);
        panelRound16.setLayout(panelRound16Layout);
        panelRound16Layout.setHorizontalGroup(
            panelRound16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound16Layout.createSequentialGroup()
                .addGap(0, 16, Short.MAX_VALUE)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelRound16Layout.setVerticalGroup(
            panelRound16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        dateTu.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateTuPropertyChange(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel19.setText("Từ");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Đến");

        dateDen.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateDenPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout panelRound10Layout = new javax.swing.GroupLayout(panelRound10);
        panelRound10.setLayout(panelRound10Layout);
        panelRound10Layout.setHorizontalGroup(
            panelRound10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound10Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateTu, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateDen, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                .addGap(34, 34, 34)
                .addComponent(txtSearch2, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(panelRound16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        panelRound10Layout.setVerticalGroup(
            panelRound10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound10Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelRound10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dateTu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSearch2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRound16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateDen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        tbPN.setModel(new javax.swing.table.DefaultTableModel(
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
        tbPN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbPNMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tbPN);

        tableScrollButton2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout from3Layout = new javax.swing.GroupLayout(from3);
        from3.setLayout(from3Layout);
        from3Layout.setHorizontalGroup(
            from3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(from3Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(from3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelRound10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tableScrollButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(74, Short.MAX_VALUE))
        );
        from3Layout.setVerticalGroup(
            from3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(from3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(panelRound10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tableScrollButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.add(from3, "card3");

        from6.setBackground(new java.awt.Color(240, 247, 250));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 25)); // NOI18N
        jLabel33.setText("Đổi mật khẩu");

        txtMkcu.setLabelText("Mật khẩu cũ");
        txtMkcu.setShowAndHide(true);

        txtMkmoi.setLabelText("Mật khẩu mới");
        txtMkmoi.setShowAndHide(true);

        txtnhapLaiMK.setLabelText("Nhập lại mật khẩu");
        txtnhapLaiMK.setShowAndHide(true);

        button1.setBackground(new java.awt.Color(0, 102, 102));
        button1.setForeground(new java.awt.Color(255, 255, 255));
        button1.setText("Đổi mật khẩu");
        button1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button1MouseClicked(evt);
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
                        .addComponent(jLabel33)
                        .addGap(341, 341, 341))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtMkmoi, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMkcu, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtnhapLaiMK, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbThongBao, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(202, 202, 202))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel33)
                .addGap(42, 42, 42)
                .addComponent(txtMkcu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(txtMkmoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(txtnhapLaiMK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addContainerGap(64, Short.MAX_VALUE))
        );
        from6Layout.setVerticalGroup(
            from6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(from6Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel3.add(from6, "card7");

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

    private void panelRound7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound7MouseExited

        panelRound7.setBackground(Color.white);
    }//GEN-LAST:event_panelRound7MouseExited

    public void enableColor() {
        panelRound1.setBackground(Color.white);
        panelRound2.setBackground(Color.white);
        panelRound3.setBackground(Color.white);
        panelRound4.setBackground(Color.white);
        panelRound5.setBackground(Color.white);
        panelRound7.setBackground(Color.white);
        panelRound6.setBackground(Color.white);
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
            JOptionPane.showMessageDialog(rootPane, "Bạn cần thay đổi mật khẩu cho lần đầu đăng nhập !");
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
            JOptionPane.showMessageDialog(rootPane, "Bạn cần thay đổi mật khẩu cho lần đầu đăng nhập !");
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
            JOptionPane.showMessageDialog(rootPane, "Bạn cần thay đổi mật khẩu cho lần đầu đăng nhập !");
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
            JOptionPane.showMessageDialog(rootPane, "Bạn cần thay đổi mật khẩu cho lần đầu đăng nhập !");
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
            JOptionPane.showMessageDialog(rootPane, "Bạn cần thay đổi mật khẩu cho lần đầu đăng nhập !");
        }
    }//GEN-LAST:event_panelRound5MouseClicked

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

    private void btnNhapMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNhapMouseEntered
        btnNhap.setBackground(new Color(1, 166, 90, 255));
    }//GEN-LAST:event_btnNhapMouseEntered

    private void btnNhapMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNhapMouseExited
        btnNhap.setBackground(new Color(211, 213, 214, 255));

    }//GEN-LAST:event_btnNhapMouseExited

    private void panelRound18MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound18MouseEntered
        panelRound18.setBackground(new Color(221, 76, 57, 255));
    }//GEN-LAST:event_panelRound18MouseEntered

    private void panelRound18MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound18MouseExited
        panelRound18.setBackground(new Color(211, 213, 214, 255));
    }//GEN-LAST:event_panelRound18MouseExited

    private void panelRound19MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound19MouseEntered
        panelRound19.setBackground(new Color(0, 192, 239, 255));
    }//GEN-LAST:event_panelRound19MouseEntered

    private void panelRound19MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound19MouseExited
        panelRound19.setBackground(new Color(211, 213, 214, 255));
    }//GEN-LAST:event_panelRound19MouseExited

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

    private void btnNhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNhapMouseClicked
        frmNhapHang nh = new frmNhapHang();
        nh.setVisible(true);
        nh.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_btnNhapMouseClicked

    private void panelRound18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound18MouseClicked
        int n = JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc muốn xoá sản phẩm?", "Thông báo", JOptionPane.YES_NO_OPTION);
        try {
            if (n == JOptionPane.YES_OPTION) {
                try {
                    int row = tbSanPham.getSelectedRow();
                    String maSP = (String) tbSanPham.getValueAt(row, 0);
                    String sql = "Update qldata.SanPham Set Trangthai = 0 where MaSanPham = '" + maSP + "'";
                    if (Database.excuteUpdate(sql)) {
                        JOptionPane.showMessageDialog(this, "Xoá sản phẩm thành công");
                        loadQLSP1();
                        Database.excuteUpdate("commit");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Chưa chọn sản phẩm cần xoá !");
                }
            }
        } catch (Exception e) {
        }

    }//GEN-LAST:event_panelRound18MouseClicked

    public static String maSP = "";

    private void panelRound19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound19MouseClicked
        try {
            int flag = 0;
        int rowSP = tbSanPham.getSelectedRow();
        maSP = (String) tbSanPham.getValueAt(rowSP, 0);
        ResultSet rs = null;
        InputStream is = null;
        String maSP = frmNhanVienKho.maSP;
        String sql = "SELECT HINHANHSP FROM qldata.SANPHAM WHERE MASANPHAM ='" + maSP + "'";
        try {
            rs = Database.excuteQuery(sql);
            if (rs.next()) {
                is = rs.getBinaryStream("HINHANHSP");
                BufferedImage img = ImageIO.read(is);
            }

        } catch (Exception e) {
            flag = 1;
            JOptionPane.showMessageDialog(rootPane, "Sản phẩm chưa có hình ảnh");

        }
        if (flag != 1) {
            frmHinhSanPham hinh = new frmHinhSanPham();
            hinh.setVisible(true);
            hinh.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Chưa chọn sản phẩm cần xem hình");
        }

    }//GEN-LAST:event_panelRound19MouseClicked

    private void txtSearch2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearch2KeyReleased
        String dateFrom = chuyenDoi(dateTu.getDate());
        String dateTo = chuyenDoi(dateDen.getDate());
        if (txtSearch2.isSelected()) {
            int option = txtSearch2.getSelectedIndex();
            if (option == 0) {
                loadDSPN(1, txtSearch2.getText(), dateFrom, dateTo);
            } else if (option == 1) {
                loadDSPN(2, txtSearch2.getText(), dateFrom, dateTo);
            } else {
                loadDSPN(3, txtSearch2.getText(), dateFrom, dateTo);
            }
        }


    }//GEN-LAST:event_txtSearch2KeyReleased

    private void panelRound16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound16MouseClicked
        dateTu.setDate(null);
        dateDen.setDate(null);
        txtSearch2.setText("");
        loadDSPN(0, "", "null", "null");
    }//GEN-LAST:event_panelRound16MouseClicked

    private void panelRound16MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound16MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelRound16MouseEntered

    private void panelRound16MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound16MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_panelRound16MouseExited

    private void dateTuPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateTuPropertyChange
        String dateFrom = chuyenDoi(dateTu.getDate());
        String dateTo = chuyenDoi(dateDen.getDate());
        if (txtSearch2.isSelected()) {
            int option = txtSearch2.getSelectedIndex();
            if (option == 0) {
                loadDSPN(1, txtSearch2.getText(), dateFrom, dateTo);
            } else if (option == 1) {
                loadDSPN(2, txtSearch2.getText(), dateFrom, dateTo);
            } else {
                loadDSPN(3, txtSearch2.getText(), dateFrom, dateTo);
            }
        }
    }//GEN-LAST:event_dateTuPropertyChange

    private void dateDenPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateDenPropertyChange
        String dateFrom = chuyenDoi(dateTu.getDate());
        String dateTo = chuyenDoi(dateDen.getDate());
        if (txtSearch2.isSelected()) {
            int option = txtSearch2.getSelectedIndex();
            if (option == 0) {
                loadDSPN(1, txtSearch2.getText(), dateFrom, dateTo);
            } else if (option == 1) {
                loadDSPN(2, txtSearch2.getText(), dateFrom, dateTo);
            } else {
                loadDSPN(3, txtSearch2.getText(), dateFrom, dateTo);
            }
        }
    }//GEN-LAST:event_dateDenPropertyChange
    public static String maPN = "";
    public static String nhaCungCap = "";
    public static String nhanVienLap = "";
    public static String ngayLap = "";
    private void tbPNMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPNMousePressed
        int rowSP = tbPN.getSelectedRow();
        maPN = (String) tbPN.getValueAt(rowSP, 0);
        nhaCungCap = (String) tbPN.getValueAt(rowSP, 1);
        nhanVienLap = (String) tbPN.getValueAt(rowSP, 2);
        ngayLap = (String) tbPN.getValueAt(rowSP, 3);
        if (evt.getClickCount() == 2) {
            frmChiTietPN pn = new frmChiTietPN();
            pn.setVisible(true);
            pn.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
    }//GEN-LAST:event_tbPNMousePressed

    private void txtSearch1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearch1KeyReleased
        if (txtSearch1.isSelected()) {
            int option = txtSearch1.getSelectedIndex();
            if (option == 0) {
                loadQLNCC(1, txtSearch1.getText());
            } else if (option == 1) {
                loadQLNCC(2, txtSearch1.getText());
            } else if (option == 2) {
                loadQLNCC(3, txtSearch1.getText());
            } else {
                loadQLNCC(4, txtSearch1.getText());
            }
        }
    }//GEN-LAST:event_txtSearch1KeyReleased

    private void panelRound21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound21MouseClicked
        loadQLNCC(0, "");
    }//GEN-LAST:event_panelRound21MouseClicked

    private void panelRound21MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound21MouseEntered
        panelRound15.setBackground(new Color(204, 204, 204));
    }//GEN-LAST:event_panelRound21MouseEntered

    private void panelRound21MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound21MouseExited

        panelRound15.setBackground(new Color(211, 213, 214, 255));
    }//GEN-LAST:event_panelRound21MouseExited

    private void panelRound22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound22MouseClicked
        frmThemNCC ncc = new frmThemNCC();
        ncc.setVisible(true);
        ncc.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_panelRound22MouseClicked

    private void panelRound22MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound22MouseEntered
        panelRound22.setBackground(new Color(1, 166, 90, 255));
    }//GEN-LAST:event_panelRound22MouseEntered

    private void panelRound22MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound22MouseExited
        panelRound22.setBackground(new Color(211, 213, 214, 255));
    }//GEN-LAST:event_panelRound22MouseExited
    static String maNCC = "";
    private void panelRound23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound23MouseClicked

        try {
            int rowNCC = tblNCC.getSelectedRow();
            maNCC = (String) tblNCC.getValueAt(rowNCC, 0);
            frmSuaNCC suaNCC = new frmSuaNCC();
            suaNCC.setVisible(true);
            suaNCC.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Chưa chọn nhà cung cấp cần sửa");
        }
    }//GEN-LAST:event_panelRound23MouseClicked

    private void panelRound23MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound23MouseEntered
        panelRound23.setBackground(new Color(243, 156, 17, 255));
    }//GEN-LAST:event_panelRound23MouseEntered

    private void panelRound23MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound23MouseExited
        panelRound23.setBackground(new Color(211, 213, 214, 255));
    }//GEN-LAST:event_panelRound23MouseExited

    private void panelRound24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound24MouseClicked

        int check = JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc muốn xoá nhà cung cấp này !", "Thông báo", JOptionPane.YES_NO_OPTION);
        if (check == JOptionPane.YES_OPTION) {
            try {
                int selectedRow = tblNCC.getSelectedRow();
                String maNCC = (String) tblNCC.getValueAt(selectedRow, 0);
                String query = "update qldata.nhacungcap set trangthai = 0 where MANHACUNGCAP = '" + maNCC + "'";
                if (Database.excuteUpdate(query)) {
                    JOptionPane.showMessageDialog(rootPane, "Xoá nhà cung cấp thành công !");
                    Database.excuteUpdate("commit");
                    loadQLNCC(0, "");
                } else {
                    if (Database.baoLoi.contains("insufficient privileges")) {
                        JOptionPane.showMessageDialog(rootPane, "Bạn không có quyền xoá bảng này !");
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Xoá nhà cung cấp thất bại !");
                    }
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Chưa chọn nhà cung cấp cần xoá !");
            }
            
        }
    }//GEN-LAST:event_panelRound24MouseClicked

    private void panelRound24MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound24MouseEntered
        panelRound24.setBackground(new Color(221, 76, 57, 255));
    }//GEN-LAST:event_panelRound24MouseEntered

    private void panelRound24MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound24MouseExited
        panelRound24.setBackground(new Color(211, 213, 214, 255));
    }//GEN-LAST:event_panelRound24MouseExited

    private void tblNCCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblNCCKeyPressed

    }//GEN-LAST:event_tblNCCKeyPressed

    private void panelRound26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound26MouseClicked
       loadQLLSP();
    }//GEN-LAST:event_panelRound26MouseClicked

    private void panelRound26MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound26MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelRound26MouseEntered

    private void panelRound26MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound26MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_panelRound26MouseExited

    private void panelRound27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound27MouseClicked
       frmThemLSP lsp = new frmThemLSP();
       lsp.setVisible(true);
       lsp.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_panelRound27MouseClicked

    private void panelRound27MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound27MouseEntered
        panelRound27.setBackground(new Color(1, 166, 90, 255));
    }//GEN-LAST:event_panelRound27MouseEntered

    private void panelRound27MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound27MouseExited
        panelRound27.setBackground(new Color(211, 213, 214, 255));
    }//GEN-LAST:event_panelRound27MouseExited
    static String maLSP = "";
    private void panelRound28MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound28MouseClicked
        try {
            int row = tblLSP.getSelectedRow();
            maLSP = tblLSP.getValueAt(row, 0).toString();
            frmSuaLoaiSP lsp = new frmSuaLoaiSP();
            lsp.setVisible(true);
            lsp.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Chưa chọn loại sản phẩm cần sửa");
        }
    }//GEN-LAST:event_panelRound28MouseClicked

    private void panelRound28MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound28MouseEntered
        panelRound28.setBackground(new Color(243, 156, 17, 255));
    }//GEN-LAST:event_panelRound28MouseEntered

    private void panelRound28MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound28MouseExited
        panelRound28.setBackground(new Color(211, 213, 214, 255));
    }//GEN-LAST:event_panelRound28MouseExited

    private void panelRound29MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound29MouseClicked
        
        int check = JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc muốn xoá loại sản phẩm này !", "Thông báo", JOptionPane.YES_NO_OPTION);
        if (check == JOptionPane.YES_OPTION) {
            try {
                int selectedRow = tblLSP.getSelectedRow();
                String maLSP = (String) tblLSP.getValueAt(selectedRow, 0);
                String query = "update qldata.loaisanpham set trangthai = 0 where maloaisanpham = '" + maLSP + "'";
                if (Database.excuteUpdate(query)) {
                    JOptionPane.showMessageDialog(rootPane, "Xoá loại sản phẩm thành công !");
                    Database.excuteUpdate("commit");
                    loadQLLSP();
                } else {
                    if (Database.baoLoi.contains("insufficient privileges")) {
                        JOptionPane.showMessageDialog(rootPane, "Bạn không có quyền xoá bảng này !");
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Xoá loại sản phẩm thất bại !");
                    }
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Chưa chọn loại sản phẩm cần xoá !");
            }
        }
    }//GEN-LAST:event_panelRound29MouseClicked

    private void panelRound29MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound29MouseEntered
        panelRound29.setBackground(new Color(221, 76, 57, 255));
    }//GEN-LAST:event_panelRound29MouseEntered

    private void panelRound29MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound29MouseExited
        panelRound29.setBackground(new Color(211, 213, 214, 255));
    }//GEN-LAST:event_panelRound29MouseExited

    private void tblLSPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblLSPKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblLSPKeyPressed

    private void panelRound6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound6MouseClicked
        resetFlags();
        flag6 = 1;
        enableColor();
        panelRound6.setBackground(new Color(176, 210, 235, 255));
        closeFrom();
        from6.setVisible(true);
    }//GEN-LAST:event_panelRound6MouseClicked

    private void panelRound6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound6MouseExited
        if (flag6 == 0)
            panelRound6.setBackground(Color.white);
    }//GEN-LAST:event_panelRound6MouseExited

    private void panelRound6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound6MouseEntered
        if (flag6 == 0)
            panelRound6.setBackground(new Color(211, 213, 214, 255));
    }//GEN-LAST:event_panelRound6MouseEntered

    private void button1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button1MouseClicked
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
        
    }//GEN-LAST:event_button1MouseClicked
    
    
    static String maSP1 = "";
    private void panelRound30MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound30MouseClicked
        try {
            int row = tbSanPham.getSelectedRow();
            maSP1 = tbSanPham.getValueAt(row, 0).toString();
            frmSuaSP sp = new frmSuaSP();
            sp.setVisible(true);
            sp.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Chưa chọn sản phẩm cần sửa");
        }
    }//GEN-LAST:event_panelRound30MouseClicked

    private void panelRound30MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound30MouseEntered
        panelRound30.setBackground(new Color(243, 156, 17, 255));
    }//GEN-LAST:event_panelRound30MouseEntered

    private void panelRound30MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound30MouseExited
        panelRound30.setBackground(new Color(211, 213, 214, 255));
    }//GEN-LAST:event_panelRound30MouseExited

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
            java.util.logging.Logger.getLogger(frmNhanVienKho.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmNhanVienKho.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmNhanVienKho.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmNhanVienKho.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmNhanVienKho().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backround;
    private boder_panel.PanelRound btnNhap;
    private sample.message.Button button1;
    private combobox.Combobox cboKT;
    private combobox.Combobox cboLoai;
    private combobox.Combobox cboMau;
    private javaswingdev.swing.titlebar.ComponentResizer componentResizer1;
    private com.toedter.calendar.JDateChooser dateDen;
    private com.toedter.calendar.JDateChooser dateTu;
    private javax.swing.JPanel from1;
    private javax.swing.JPanel from2;
    private javax.swing.JPanel from3;
    private javax.swing.JPanel from4;
    private javax.swing.JPanel from5;
    private javax.swing.JPanel from6;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lbName;
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
    private boder_panel.PanelRound panelRound19;
    private boder_panel.PanelRound panelRound2;
    private boder_panel.PanelRound panelRound20;
    private boder_panel.PanelRound panelRound21;
    private boder_panel.PanelRound panelRound22;
    private boder_panel.PanelRound panelRound23;
    private boder_panel.PanelRound panelRound24;
    private boder_panel.PanelRound panelRound25;
    private boder_panel.PanelRound panelRound26;
    private boder_panel.PanelRound panelRound27;
    private boder_panel.PanelRound panelRound28;
    private boder_panel.PanelRound panelRound29;
    private boder_panel.PanelRound panelRound3;
    private boder_panel.PanelRound panelRound30;
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
    private table.TableScrollButton tableScrollButton1;
    private table.TableScrollButton tableScrollButton2;
    private table.TableScrollButton tableScrollButton3;
    private table.TableScrollButton tableScrollButton4;
    private javax.swing.JTable tbPN;
    private javax.swing.JTable tbSanPham;
    private javax.swing.JTable tblLSP;
    private javax.swing.JTable tblNCC;
    private javax.swing.JPanel thongtin;
    private javaswingdev.swing.titlebar.TitleBar titleBar1;
    private textfield.PasswordField txtMkcu;
    private textfield.PasswordField txtMkmoi;
    private textfield.TextFieldSearchOption txtSearch;
    private textfield.TextFieldSearchOption txtSearch1;
    private textfield.TextFieldSearchOption txtSearch2;
    private textfield.PasswordField txtnhapLaiMK;
    // End of variables declaration//GEN-END:variables
}
