package app.utils;

import jakarta.mail.Authenticator;
import java.util.Objects;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import java.util.Properties;

public class GmailService {
    private static Properties prop;
    private static Session session;

    static {
        prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.mailtrap.io");
        prop.put("mail.smtp.port", "25");
        prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");

        session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }
    
    public static void sendMailTo(String subject, String message, String[] emails)
    throws Exception {
        Objects.requireNonNull(subject, "subject cannot be null");
        Objects.requireNonNull(message, "message cannot be null");
        Objects.requireNonNull(emails, "emails cannot be null");

        if (emails.length == 0) {
            throw new IllegalArgumentException("emails cannot be empty");
        }

        InternetAddress[] addresses = new InternetAddress[emails.length];

        for (int i = 0; i < emails.length; i++) {
            addresses[i] = new InternetAddress(emails[i]);
            addresses[i].validate();
        }

        Session session = Session.g

        MimeMessage emailMessage = new MimeMessage();
        emailMessage.
        emailMessage.setSubject(subject);
        emailMessage
    }
}
