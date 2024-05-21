package com.sejacha.server;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mailing {

    public Mailing() {
    }

    public void sendEmail(String to, String subject, String body) throws MessagingException {
        // Set up the SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", Config.getConfig("email.smtp.host"));
        properties.put("mail.smtp.port", Config.getConfig("email.smtp.port"));
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Create a session with an authenticator
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Config.getConfig("email.smtp.user"),
                        Config.getConfig("email.smtp.password"));
            }
        };

        Session session = Session.getInstance(properties, auth);

        // Create the email message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(Config.getConfig("email.smtp.user")));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);

        // Send the email
        Transport.send(message);
    }

    public static void main(String[] args) {
        // Example usage

        Mailing emailSender = new Mailing();

        try {
            emailSender.sendEmail("recipient@example.com", "Test Subject", "Test Body");
            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}