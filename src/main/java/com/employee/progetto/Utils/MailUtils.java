package com.example.progetto.Utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class MailUtils {
    public static void inviaMail(String msg,String sub,String email){
        try {
            Properties properties = new Properties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            Session session = Session.getInstance(properties, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("Davide Bonura", "DeHxqvm7Py");
                }
            });
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("davidebonura53@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(sub);
            message.setText(msg);
            Transport.send(message);
        }catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
