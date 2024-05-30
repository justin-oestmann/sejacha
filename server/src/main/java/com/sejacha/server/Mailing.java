package com.sejacha.server;

import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;

/**
 * Utility class for sending emails.
 */
public class Mailing {

    /**
     * Constructs a new Mailing object.
     */
    public Mailing() {
    }

    /**
     * Sends an email with the specified details.
     *
     * @param to      the recipient email address
     * @param subject the subject of the email
     * @param body    the body of the email
     * @throws MessagingException if an error occurs while sending the email
     */
    public static void sendEmail(String to, String subject, String body) throws MessagingException {
        // Set up the SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", Config.getConfig("email.smtp.host"));
        properties.put("mail.smtp.port", Config.getConfig("email.smtp.port"));
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Create a session with an authenticator
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(Config.getConfig("email.smtp.user"),
                        Config.getConfig("email.smtp.password"));
            }
        });

        // Create the email message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(Config.getConfig("email.smtp.user")));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);

        // Send the email
        Transport.send(message);
    }
}
