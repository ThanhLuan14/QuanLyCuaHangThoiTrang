/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.chiTietHoaDonPojo;
import POJO.hoaDonPojo;
import POJO.sanPhamPojo;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import java.awt.Desktop;
import java.awt.FileDialog;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


/**
 *
 * @author Admin
 */
public class WritePDF {
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/YYYY HH:mm");
    Document document = new Document();
    FileOutputStream file;
    JFrame jf = new JFrame();
    FileDialog fd = new FileDialog(jf, "Xuất pdf", FileDialog.SAVE);
    Font fontData;
    Font fontTitle;
    Font fontHeader;

    public WritePDF() {
        try {
            fontData = new Font(BaseFont.createFont("Roboto/Roboto-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 11, Font.NORMAL);
            fontTitle = new Font(BaseFont.createFont("Roboto/Roboto-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 25, Font.NORMAL);
            fontHeader = new Font(BaseFont.createFont("Roboto/Roboto-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 11, Font.NORMAL);
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(WritePDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void chooseURL(String url) {
        try {
            document.close();
            document = new Document();
            file = new FileOutputStream(url);
            PdfWriter writer = PdfWriter.getInstance(document, file);
            document.open();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Khong tim thay duong dan file " + url);
        } catch (DocumentException ex) {
            JOptionPane.showMessageDialog(null, "Khong goi duoc document !");
        }
    }

    public void setTitle(String title) {
        try {
            Paragraph pdfTitle = new Paragraph(new Phrase(title, fontTitle));
            pdfTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(pdfTitle);
            document.add(Chunk.NEWLINE);
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }
    }

    private String getFile(String name) {
        fd.pack();
        fd.setSize(800, 600);
        fd.validate();
        Rectangle rect = jf.getContentPane().getBounds();
        double width = fd.getBounds().getWidth();
        double height = fd.getBounds().getHeight();
        double x = rect.getCenterX() - (width / 2);
        double y = rect.getCenterY() - (height / 2);
        Point leftCorner = new Point();
        leftCorner.setLocation(x, y);
        fd.setLocation(leftCorner);
        fd.setFile(name + ".pdf");
        fd.setVisible(true);
        String url = fd.getDirectory() + fd.getFile();
        if (url.equals("null")) {
            return null;
        }
        return url;
    }
    public void writePhieuXuat(String mapn) {
        String url = "";
        try {
            fd.setTitle("In phiếu xuất");
            fd.setLocationRelativeTo(null);
            url = getFile(mapn);
            if (url == null) {
                return;
            }
            file = new FileOutputStream(url);
            document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, file);
            document.open();
            Paragraph para3 = new Paragraph();
            para3.setFont(fontData);
            para3.add("\nCông ty TNHH Thời Trang và Mỹ Phẩm Châu Âu.");
            para3.add("\nĐịa chỉ: Lầu 12, Toà Nhà Sonatus, 15 Lê Thánh Tôn, phường Bến Nghé, Quận 1, TP. HCM.");
            para3.add("\nĐiện thoại: + 84 (28) 3825 6523.");
            para3.setIndentationLeft(40);
            document.add(para3);
            document.add(Chunk.NEWLINE);

            setTitle("THÔNG TIN PHIẾU XUẤT");

            hoaDonPojo hd = hoaDonDao.getInstance().selectById(mapn);
            

            Chunk glue = new Chunk(new VerticalPositionMark());// Khoang trong giua hang
            Paragraph para1 = new Paragraph();
            para1.setFont(fontData);
            para1.add("Mã phiếu: " + hd.getMaHD());
            para1.add("\nThời gian tạo: " + formatDate.format(hd.getNgayLap()));
            para1.setIndentationLeft(40);
            Paragraph para2 = new Paragraph();
            para2.setPaddingTop(30);
            para2.setFont(fontData);
            para2.add(String.valueOf("Người tạo: " + nhanVienDao.getInstance().layDSNV3(hd.getMaNhanVien()).getHoTen()));
            String tenKH = hd.getMaKH();            
            if(!tenKH.equals("Khách vãng lai")){
                para2.add(String.valueOf("\nKhách hàng: " + khachHangDao.getInstance().layDSKH3(hd.getMaKH()).getTenKH()+ "  -  " + hd.getMaKH()));

            }else{
                para2.add(String.valueOf("\nKhách hàng: Vãng lai" ));

            }
//            para2.add(String.valueOf("\nKhách hàng: " + khachHangDao.getInstance().layDSKH3(hd.getMaKH()).getTenKH()+ "  -  " + hd.getMaKH()));
            para2.setIndentationLeft(40);
            document.add(para1);
            document.add(para2);
            document.add(Chunk.NEWLINE);//add hang trong de tao khoang cach

            //Tao table cho cac chi tiet cua hoa don
            PdfPTable pdfTable = new PdfPTable(5);
            pdfTable.setWidths(new float[]{15f, 30f, 15f, 5f, 15f});
            PdfPCell cell;

            //Set headers cho table chi tiet
            pdfTable.addCell(new PdfPCell(new Phrase("Mã sản phẩm", fontHeader))).setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTable.addCell(new PdfPCell(new Phrase("Tên sản phẩm", fontHeader))).setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTable.addCell(new PdfPCell(new Phrase("Đơn giá", fontHeader))).setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTable.addCell(new PdfPCell(new Phrase("SL", fontHeader))).setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTable.addCell(new PdfPCell(new Phrase("Tổng tiền", fontHeader))).setHorizontalAlignment(Element.ALIGN_CENTER);

            for (int i = 0; i < 5; i++) {
                cell = new PdfPCell(new Phrase(""));
                pdfTable.addCell(cell);
            }

            //Truyen thong tin tung chi tiet vao table
            for (chiTietHoaDonPojo cthd : chiTietHoaDonDao.getInstance().layDSCTHD(mapn)) {
                sanPhamPojo mt = sanPhamDao.getInstance().layDSSP1(cthd.getMaSanPham());
                pdfTable.addCell(new PdfPCell(new Phrase(mt.getMaSanPham(), fontData))).setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfTable.addCell(new PdfPCell(new Phrase(mt.getTenSanPham(), fontData))).setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfTable.addCell(new PdfPCell(new Phrase(formatter.format(mt.getGiaBan()) + " VNĐ", fontData))).setHorizontalAlignment(Element.ALIGN_RIGHT);
                pdfTable.addCell(new PdfPCell(new Phrase(String.valueOf(cthd.getSoLuong()), fontData))).setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfTable.addCell(new PdfPCell(new Phrase(formatter.format(cthd.getSoLuong() * mt.getGiaBan()) + " VNĐ", fontData))).setHorizontalAlignment(Element.ALIGN_RIGHT);
            }

            document.add(pdfTable);
            document.add(Chunk.NEWLINE);

            Paragraph paraTongThanhToan = new Paragraph(new Phrase("Tổng thanh toán: " + formatter.format(hd.getTongTien()) + " VNĐ", fontData));
            paraTongThanhToan.setIndentationLeft(322);
            document.add(paraTongThanhToan);
            document.close();
            JOptionPane.showMessageDialog(null, "Ghi file thành công " + url);
            openFile(url);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi ghi file " + url);
        }
    }
     private void openFile(String file) {
        try {
            File path = new File(file);
            Desktop.getDesktop().open(path);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
