/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package app.controller;

/**
 *
 * @author OwO
 */
import app.dal.DAOUser;
import java.io.IOException;
import java.util.Random;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Properties;

public class SendVerificationCodeUserRegister extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        if (email != null && !email.isEmpty()) {
            // Check if the email already exists in the database
            if (isEmailRegistered(email)) {
                response.setStatus(HttpServletResponse.SC_CONFLICT); // 409 Conflict
                response.getWriter().write("Email already existed!");
            } else {
                String verificationCode = generateVerificationCode();
                boolean emailSent = sendEmail(email, verificationCode);
                if (emailSent) {
                    HttpSession session = request.getSession();
                    session.setAttribute("verificationCode", verificationCode);
                    session.setAttribute("verificationCodeTimestamp", System.currentTimeMillis());
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private boolean isEmailRegistered(String email) {
        DAOUser daoUser = new DAOUser();
        return daoUser.isEmailRegistered(email);
    }

    private String generateVerificationCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }

    private boolean sendEmail(String to, String code) {
        final String from = "kittiepieuwu@gmail.com";
        final String host = "smtp.gmail.com";
        final String username = "anlthe182228@fpt.edu.vn";
        final String password = "qzwn fxtw iare yofc";

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Your Verification Code");
            message.setText("Your verification code is: " + code);

            Transport.send(message);
            return true;
        } catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        String toEmail = "kittiepieowo@gmail.com";
        String text = "Thank you for using JavaMail";
        boolean testSend = new SendVerificationCodeUserRegister().sendEmail(toEmail, new SendVerificationCodeUserRegister().generateVerificationCode());
        System.out.println(testSend);
    }
}
