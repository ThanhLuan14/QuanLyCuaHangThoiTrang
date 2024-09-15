/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.util.Properties;
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

/**
 *
 * @author Admin
 */
public class SendMail {
    public static void send(String[] args) {
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
            message.setText("Kính gửi Ban giám đốc "
                               +"Doanh thu và lợi nhuận"
                               + "/n"
                               +"Từ ngày:" 
                               +"Đến ngày:"
                               +"Tổng doanh thu:"
                               +"Tổng lợi nhuận:");   
            
            mul.addBodyPart(mineText);
            mul.addBodyPart(minePdf);
            
            message.setContent(mul);
            Transport.send(message);
            System.out.println("succes");
           
                    
        } catch (Exception e) {
        }
      
    }
}
