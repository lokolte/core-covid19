package com.core.covid19.services;

import org.springframework.stereotype.Service;

import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@Service
public class EmailSender {

    public void send(String correoDestinatario, String asunto, String mensaje) {

        final String host = "";
        final String port = "";
        final String username = "";
        final String password = "";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", port);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", port);
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoDestinatario));
            message.setSubject(asunto);
            message.setContent(mensaje, "text/html; charset=utf-8");

            Transport.send(message);

            System.err.println("Correo enviado a : " + correoDestinatario);

        } catch (MessagingException e) {
            System.err.println("Error al enviar correo a " + correoDestinatario + " : " + e.getMessage());
        }
    }
}
