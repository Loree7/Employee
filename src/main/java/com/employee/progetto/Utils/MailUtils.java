package com.employee.progetto.Utils;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import com.mailboxvalidator.*;

public class MailUtils {
    public static void inviaMail(String testo,String oggetto,String email){
        String username = "employee.teamddll@gmail.com";
        String password = "fljkngulfpnqcklp";
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("employee.teamddll@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(oggetto);
            message.setText(testo);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static boolean verificaMail(String email) {
        try {
            SingleValidation mbv = new com.mailboxvalidator.SingleValidation("1NGUYDB5CPFEK2QH97W6");
            MBVResult rec = mbv.ValidateEmail(email);
            if (rec.getIsVerified().equals("True")) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
