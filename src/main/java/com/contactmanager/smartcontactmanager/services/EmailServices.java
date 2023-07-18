package com.contactmanager.smartcontactmanager.services;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailServices {
    public boolean sendEmail(String message, String subject, String to) {
        boolean f = false;
        String from = "abc@gmail.com";
        String host = "smtp.gmail.com";

        //get the system properties
        Properties properties = System.getProperties();
        System.out.println("PROPERTIES : " + properties);

        //SET PROPERTIES
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");


        //step1:To get the Session object
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("abc@gmail.com", "");
            }
        });
        session.setDebug(true);

        //step2:Compose the Message
        MimeMessage m = new MimeMessage(session);
        try {
            m.setFrom(from);
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            m.setSubject(subject);
//            m.setText(message);
            m.setContent(message,"text/html");
            //step3:send the message using the transport class
            Transport.send(m);
            f = true;
            System.out.println("Send Success....");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return f;
    }
}
