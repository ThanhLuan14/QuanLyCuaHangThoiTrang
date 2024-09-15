/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import DAO.Database;
import DAO.kichThuocDao;
import DAO.loaiSanPhamDao;
import DAO.mauSacDao;
import DAO.nhaCungCapDao;
import DAO.nhanVienDao;
import DAO.phieuNhapDao;
import DAO.sanPhamDao;
import POJO.cboItem;
import POJO.kichThuocPojo;
import POJO.loaiSanPhamPojo;
import POJO.mauSacPojo;
import POJO.nhaCungCapPojo;
import POJO.nhanVienPojo;
import POJO.phieuNhapPojo;
import POJO.sanPhamPojo;
import java.awt.Color;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import table.TableCustom;
import textfield.SearchOptinEvent;
import textfield.SearchOption;

/**
 *
 * @author AnhDoan
 */
public class frmNhapHang extends javax.swing.JFrame {

    /**
     * Creates new form frmThemNV
     */
    DecimalFormat formatter = new DecimalFormat("###,###,###");

    public frmNhapHang() {
        initComponents();
        loadQLSP(0, "", "Tất cả", "Tất cả", "Tất cả");
        loadTextSearch();
        tableCustom1.apply(jScrollPane2, TableCustom.TableType.DEFAULT);
        tbSanPham.setDefaultEditor(Object.class, null);
        tableCustom2.apply(jScrollPane1, TableCustom.TableType.DEFAULT);
        loadCTHD();
        
        txtMaPhieu.setText(id_phieunhap());
        loadNCC();
        txtNV.setText(tenNV());
    }

    private String tenNV(){
        String name ="";
        ArrayList<nhanVienPojo> dsnv = new nhanVienDao().layDSNV2(Database.user.toUpperCase());
        for(nhanVienPojo nv : dsnv){
            name = new cboItem(nv.getMaNhanVien(), nv.getHoTen()).toString();
        }
        
        return name;
    }

    void loadNCC() {
        ArrayList<nhaCungCapPojo> listNCC = new nhaCungCapDao().layDSNCC();
        for (nhaCungCapPojo ncc : listNCC) {
            cboNCC.addItem(new cboItem(ncc.getMancc(), ncc.getTenncc()).toString());
        }
    }

    public String layMa(String textCbo) {
        String ma;
        String[] mang = textCbo.split(" - ");
        ma = mang[0];

        return ma;
    }

    public String id_phieunhap() {
        int id = 1;
        ArrayList<phieuNhapPojo> listPN = new phieuNhapDao().layDSPN();
        Set<String> maPSet = new HashSet<String>();
        for (phieuNhapPojo pn : listPN) {
            String maP = pn.getMaPhieu();
            maPSet.add(maP);
        }
        while (maPSet.contains(String.format("PN%03d", id)) && id <= 999) {
            id++;
        }

        return String.format("PN%03d", id);
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

    public void loadQLSP(int checkSQL, String text, String loaiSp, String ms, String kt) {
        try {
            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(new String[]{"Mã sản phẩm", "Mã sản phẩm", "Tên sản phẩm", "Số lượng tồn", "Màu Sắc", "Kích thước", "Giá bán"});
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
                model.addRow(new Object[]{maSP, code, tenSP, soLuongTon, mauSac, kichThuoc, formatter.format(giaBan) + " VNĐ"});
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
    DefaultTableModel modelCTHD = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 3 || column == 6;
        }

    };

    public void loadCTHD() {
        modelCTHD.setColumnIdentifiers(new String[]{"Mã sản phẩm", "Mã sản phẩm", "Tên sản phẩm", "Số lượng nhập", "Màu Sắc", "Kích thước", "Giá nhập"});
        modelCTHD.addTableModelListener(e -> tableChanged(e));
        tbChiTiet.setModel(modelCTHD);
        tbChiTiet.getColumnModel().getColumn(0).setMinWidth(0);
        tbChiTiet.getColumnModel().getColumn(0).setMaxWidth(0);
        tbChiTiet.getColumnModel().getColumn(0).setWidth(0);
        tbChiTiet.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    private void tableChanged(TableModelEvent e) {
        int rowCount = modelCTHD.getRowCount();
        int tong = 0;
        for (int i = 0; i < rowCount; i++) {
            String soLuongNhap = modelCTHD.getValueAt(i, 3).toString();
            String giaNhap = modelCTHD.getValueAt(i, 6).toString();
            String giaNhapClean = giaNhap.replaceAll("[^\\d]", "");
            int soLuongNhap1 = Integer.parseInt(soLuongNhap);
            int giaNhap1 = Integer.parseInt(giaNhapClean);
            tong += soLuongNhap1 * giaNhap1;
        }
        txtTong.setText(formatter.format(tong) + " VNĐ");
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
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        from2 = new javax.swing.JPanel();
        panelRound9 = new boder_panel.PanelRound();
        txtSearch = new textfield.TextFieldSearchOption();
        panelRound15 = new boder_panel.PanelRound();
        jLabel2 = new javax.swing.JLabel();
        btnThem = new boder_panel.PanelRound();
        jLabel3 = new javax.swing.JLabel();
        tableScrollButton1 = new table.TableScrollButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbSanPham = new javax.swing.JTable();
        tableScrollButton2 = new table.TableScrollButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbChiTiet = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        txtTong = new javax.swing.JLabel();
        panelRound1 = new boder_panel.PanelRound();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtMaPhieu = new textfield_suggestion.TextFieldSuggestion();
        txtNV = new textfield_suggestion.TextFieldSuggestion();
        cboNCC = new combo_suggestion.ComboBoxSuggestion();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        panelRound2 = new boder_panel.PanelRound();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(22, 122, 197));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("NHẬP HÀNG");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1294, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addContainerGap(25, Short.MAX_VALUE))
        );

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

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/refresh (1).png"))); // NOI18N
        jLabel2.setText("Làm mới");

        javax.swing.GroupLayout panelRound15Layout = new javax.swing.GroupLayout(panelRound15);
        panelRound15.setLayout(panelRound15Layout);
        panelRound15Layout.setHorizontalGroup(
            panelRound15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
        );
        panelRound15Layout.setVerticalGroup(
            panelRound15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnThem.setBackground(new java.awt.Color(211, 213, 214));
        btnThem.setRoundBottomLeft(10);
        btnThem.setRoundBottomRight(10);
        btnThem.setRoundTopLeft(10);
        btnThem.setRoundTopRight(10);
        btnThem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnThemMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnThemMouseExited(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add20.png"))); // NOI18N
        jLabel3.setText("Thêm mới");

        javax.swing.GroupLayout btnThemLayout = new javax.swing.GroupLayout(btnThem);
        btnThem.setLayout(btnThemLayout);
        btnThemLayout.setHorizontalGroup(
            btnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
        );
        btnThemLayout.setVerticalGroup(
            btnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelRound9Layout = new javax.swing.GroupLayout(panelRound9);
        panelRound9.setLayout(panelRound9Layout);
        panelRound9Layout.setHorizontalGroup(
            panelRound9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound9Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(panelRound15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        panelRound9Layout.setVerticalGroup(
            panelRound9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound9Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelRound9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRound15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        tbSanPham.setGridColor(new java.awt.Color(204, 204, 204));
        tbSanPham.setSelectionBackground(new java.awt.Color(204, 204, 204));
        tbSanPham.setSelectionForeground(new java.awt.Color(255, 204, 204));
        tbSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbSanPhamMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tbSanPham);

        tableScrollButton1.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        tbChiTiet.setModel(new javax.swing.table.DefaultTableModel(
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
        tbChiTiet.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbChiTietPropertyChange(evt);
            }
        });
        tbChiTiet.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                tbChiTietVetoableChange(evt);
            }
        });
        jScrollPane1.setViewportView(tbChiTiet);

        tableScrollButton2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel4.setText("Tổng tiền:");

        txtTong.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        txtTong.setForeground(new java.awt.Color(255, 51, 51));
        txtTong.setText("NONE");

        panelRound1.setBackground(new java.awt.Color(125, 160, 128));
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

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-add-product-20.png"))); // NOI18N
        jLabel8.setText("Nhập hàng");

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Mã phiếu nhập");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Mã nhà cung cấp");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Người tạo phiếu");

        txtMaPhieu.setForeground(new java.awt.Color(102, 102, 102));
        txtMaPhieu.setEnabled(false);

        txtNV.setEnabled(false);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Danh mục sản phẩm");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Phiếu nhập");

        panelRound2.setBackground(new java.awt.Color(255, 102, 102));
        panelRound2.setRoundBottomLeft(10);
        panelRound2.setRoundBottomRight(10);
        panelRound2.setRoundTopLeft(10);
        panelRound2.setRoundTopRight(10);
        panelRound2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelRound2MouseClicked(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-delete-20 (1).png"))); // NOI18N
        jLabel11.setText("Xoá");

        javax.swing.GroupLayout panelRound2Layout = new javax.swing.GroupLayout(panelRound2);
        panelRound2.setLayout(panelRound2Layout);
        panelRound2Layout.setHorizontalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        panelRound2Layout.setVerticalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout from2Layout = new javax.swing.GroupLayout(from2);
        from2.setLayout(from2Layout);
        from2Layout.setHorizontalGroup(
            from2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(from2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(from2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelRound9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tableScrollButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(from2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(from2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(from2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(from2Layout.createSequentialGroup()
                                .addGroup(from2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(from2Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtTong, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(panelRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(23, 23, 23)
                                        .addComponent(panelRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(tableScrollButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 617, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 11, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(from2Layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addGroup(from2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(43, 43, 43)
                        .addGroup(from2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaPhieu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboNCC, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        from2Layout.setVerticalGroup(
            from2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(from2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(from2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(from2Layout.createSequentialGroup()
                        .addGroup(from2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtMaPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(from2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cboNCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(from2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(from2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(panelRound9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(38, 38, 38)
                .addGroup(from2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(from2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(from2Layout.createSequentialGroup()
                        .addComponent(tableScrollButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addGroup(from2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(from2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(txtTong))
                            .addComponent(panelRound1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelRound2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(tableScrollButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(from2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(from2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased

        if (txtSearch.isSelected()) {
            int option = txtSearch.getSelectedIndex();
            if (option == 0) {
                loadQLSP(1, txtSearch.getText(), "Tất cả", "Tất cả", "Tất cả");

            } else {
                loadQLSP(2, txtSearch.getText(), "Tất cả", "Tất cả", "Tất cả");
            }
        }
    }//GEN-LAST:event_txtSearchKeyReleased

    private void panelRound15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound15MouseClicked
        txtSearch.setText("");
        loadQLSP(0, "", "Tất cả", "Tất cả", "Tất cả");
    }//GEN-LAST:event_panelRound15MouseClicked

    private void panelRound15MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound15MouseEntered
        panelRound15.setBackground(new Color(204, 204, 204));
    }//GEN-LAST:event_panelRound15MouseEntered

    private void panelRound15MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound15MouseExited

        panelRound15.setBackground(new Color(211, 213, 214, 255));
    }//GEN-LAST:event_panelRound15MouseExited

    private void btnThemMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseEntered
        btnThem.setBackground(new Color(1, 166, 90, 255));
    }//GEN-LAST:event_btnThemMouseEntered

    private void btnThemMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseExited
        btnThem.setBackground(new Color(211, 213, 214, 255));

    }//GEN-LAST:event_btnThemMouseExited

    public boolean checkSoNguyen(String text) {
        try {
            int a = Integer.parseInt(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void tbSanPhamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSanPhamMousePressed
        int selectedRow = tbSanPham.getSelectedRow();
        int rowCTHD = tbChiTiet.getRowCount();
        String ma = (String) tbSanPham.getValueAt(selectedRow, 0);
        String code = (String) tbSanPham.getValueAt(selectedRow, 1);
        String ten = (String) tbSanPham.getValueAt(selectedRow, 2);
        String mau = (String) tbSanPham.getValueAt(selectedRow, 4);
        String kt = (String) tbSanPham.getValueAt(selectedRow, 5);

        if (evt.getClickCount() == 2) {
            String sln = JOptionPane.showInputDialog(this, "Số lượng hàng cần nhập", "Thêm số lượng hàng", QUESTION_MESSAGE);

            sanPhamDao sp = new sanPhamDao();
            int gia = (int) ((int)sp.layDSSP1(ma).getGiaBan() * 0.5);
            for (int i = 0; i < rowCTHD; i++) {
                String maCTHD = (String) tbChiTiet.getValueAt(i, 0);
                if (maCTHD.equals(ma)) {
                    JOptionPane.showMessageDialog(rootPane, "Bạn đã thêm sản phẩm này rồi !", "Thông báo", JOptionPane.OK_OPTION);
                    return;
                }
            }
            try {
                if (checkSoNguyen(sln) && Integer.parseInt(sln) > 0) {
                    modelCTHD.addRow(new Object[]{ma, code, ten, sln, mau, kt, formatter.format(gia) + " VNĐ"});
                    tbChiTiet.setModel(modelCTHD);
                } else if (sln == null) {
                    return;
                } else if (Integer.parseInt(sln) <= 0) {
                    JOptionPane.showMessageDialog(rootPane, "Bạn cần nhập số lượng > 0 !", "Thông báo", JOptionPane.OK_OPTION);
                }
            
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(rootPane, "Bạn cần nhập số nguyên !", "Thông báo", JOptionPane.OK_OPTION);
            }

        }
    }//GEN-LAST:event_tbSanPhamMousePressed

    private void panelRound1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound1MouseClicked
        int rowCTHD = tbChiTiet.getRowCount();
        try {
            if (rowCTHD == 0) {
                JOptionPane.showMessageDialog(rootPane, "Chưa thêm sản phẩm cần nhập !", "Thông báo", HEIGHT);
            } else {

                String ma = txtMaPhieu.getText();
                String maNCC = layMa(cboNCC.getSelectedItem().toString());
                String maNV = layMa(txtNV.getText());
                String query1 = "INSERT INTO qldata.PhieuNhap (MaPhieuNhap, MaNhaCungCap, MaNhanVien, NgayNhap) VALUES('" + ma + "', '" + maNCC + "', '"+maNV+"', sysdate)";
                Database.excuteQuery(query1);
                Database.excuteQuery("commit");
                for (int i = 0; i < rowCTHD; i++) {
                    String maSP = (String) tbChiTiet.getValueAt(i, 0);
                    String sln = (String) tbChiTiet.getValueAt(i, 3);
                    String gn = (String) tbChiTiet.getValueAt(i, 6);
                    String giaNhapClean = gn.replaceAll("[^\\d]", "");
                    String query2 = "INSERT INTO qldata.ChiTietPhieuNhap (MaPhieuNhap, MaSanPham, SoLuong, GiaNhap) VALUES('" + ma + "', '" + maSP + "', " + sln + ", " + giaNhapClean + ")";
                    Database.excuteUpdate(query2);
                    Database.excuteQuery("commit");
                }
                txtMaPhieu.setText(id_phieunhap());
                modelCTHD.setRowCount(0);
                loadQLSP(0, "", "Tất cả", "Tất cả", "Tất cả");
                JOptionPane.showMessageDialog(rootPane, "Nhập hàng thành công");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }//GEN-LAST:event_panelRound1MouseClicked

    private void tbChiTietPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbChiTietPropertyChange
        int rowCount = modelCTHD.getRowCount();
        int tong = 0;
        for (int i = 0; i < rowCount; i++) {
            String soLuongNhap = modelCTHD.getValueAt(i, 3).toString();
            String giaNhap = modelCTHD.getValueAt(i, 6).toString();
            String giaNhapClean = giaNhap.replaceAll("[^\\d]", "");
            int soLuongNhap1 = Integer.parseInt(soLuongNhap);
            int giaNhap1 = Integer.parseInt(giaNhapClean);
            tong += soLuongNhap1 * giaNhap1;
        }
        txtTong.setText(formatter.format(tong) + " VNĐ");
    }//GEN-LAST:event_tbChiTietPropertyChange

    private void tbChiTietVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_tbChiTietVetoableChange

    }//GEN-LAST:event_tbChiTietVetoableChange

    private void panelRound1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound1MouseEntered
        panelRound1.setBackground(new Color(69, 128, 82, 255));
    }//GEN-LAST:event_panelRound1MouseEntered

    private void panelRound1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound1MouseExited
        panelRound1.setBackground(new Color(125, 160, 128));
    }//GEN-LAST:event_panelRound1MouseExited

    private void btnThemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseClicked
        this.dispose();
        frmthemSpMoi sp = new frmthemSpMoi();
        sp.setVisible(true);
        sp.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_btnThemMouseClicked

    private void panelRound2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound2MouseClicked
         int row = tbChiTiet.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xoá !");
        } else {
            modelCTHD.removeRow(row);
        }
    }//GEN-LAST:event_panelRound2MouseClicked

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
            java.util.logging.Logger.getLogger(frmNhapHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmNhapHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmNhapHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmNhapHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmNhapHang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private boder_panel.PanelRound btnThem;
    private combo_suggestion.ComboBoxSuggestion cboNCC;
    private javax.swing.JPanel from2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private boder_panel.PanelRound panelRound1;
    private boder_panel.PanelRound panelRound15;
    private boder_panel.PanelRound panelRound2;
    private boder_panel.PanelRound panelRound9;
    private table.TableCustom tableCustom1;
    private table.TableCustom tableCustom2;
    private table.TableCustom tableCustom3;
    private table.TableCustom tableCustom4;
    private table.TableScrollButton tableScrollButton1;
    private table.TableScrollButton tableScrollButton2;
    private javax.swing.JTable tbChiTiet;
    private javax.swing.JTable tbSanPham;
    private textfield_suggestion.TextFieldSuggestion txtMaPhieu;
    private textfield_suggestion.TextFieldSuggestion txtNV;
    private textfield.TextFieldSearchOption txtSearch;
    private javax.swing.JLabel txtTong;
    // End of variables declaration//GEN-END:variables
}
