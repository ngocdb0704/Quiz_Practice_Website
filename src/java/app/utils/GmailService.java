package app.utils;

import jakarta.mail.Authenticator;
import java.util.Objects;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.servlet.ServletContext;
import java.util.Properties;

public class GmailService {

    private Session session;
    private InternetAddress from;

    public GmailService(ServletContext ctx) {
        Config cfg = new Config(ctx);
        Properties prop = cfg.getConfig();

        try {
            from = new InternetAddress(prop.getProperty("mail.account"));
        } catch (AddressException e) {
            e.printStackTrace();
        }

        this.session = Session.getInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        prop.getProperty("mail.account"),
                        prop.getProperty("mail.password")
                );
            }
        });
    }

    public void sendMailTo(String subject, String message, String[] emails)
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

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(from);

            for (InternetAddress addr : addresses) {
                msg.addRecipient(Message.RecipientType.TO, addr);
            }

            msg.setSubject(subject);
            msg.setText(message);

            Transport.send(msg);

            System.out.println("Sent message successfully!");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public void sendMailWithContent(String subject, String emailBody, String[] emails)
            throws Exception {
        Objects.requireNonNull(subject, "subject cannot be null");
        Objects.requireNonNull(emailBody, "email body cannot be null");
        Objects.requireNonNull(emails, "emails cannot be null");
        if (emails.length == 0) {
            throw new IllegalArgumentException("emails cannot be empty");
        }
        InternetAddress[] addresses = new InternetAddress[emails.length];
        for (int i = 0; i < emails.length; i++) {
            addresses[i] = new InternetAddress(emails[i]);
            addresses[i].validate();
        }
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(from);
            for (InternetAddress addr : addresses) {
                msg.addRecipient(Message.RecipientType.TO, addr);
            }
            msg.setSubject(subject);
            msg.setContent(emailBody, "text/html");
            Transport.send(msg);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
