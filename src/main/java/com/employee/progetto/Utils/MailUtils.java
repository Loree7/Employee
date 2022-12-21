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
                1041784284663-04gmu02l1jju8aa4c4brlco2j2220cp9.apps.googleusercontent.com
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("employee.teamddll", "Progetto22");
                }
            });
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("employee.teamddll@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(sub);
            message.setText(msg);
            Transport.send(message);
        }catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
