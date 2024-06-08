/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package app.controller;

import java.io.IOException;
import java.util.Properties;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author OwO
 */
public class VerificationUserRegister extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String service = request.getParameter("service");
        String email = request.getParameter("email");

        if (action != null && action.equals("register")) {
            if (service != null && service.equals("sendCode")) {
                // Handle the request to send the verification code
                String verificationCode = generateVerificationCode();
                sendVerificationCodeByEmail(email, verificationCode);
                request.setAttribute("message", "Verification code sent to " + email);
                request.getRequestDispatcher("UserRegisterThroughMail.jsp").forward(request, response);
            } else {
                // Handle the user registration request
                String password = request.getParameter("password");
                String verificationCode = request.getParameter("verificationCode");
                // Your logic to handle the user registration goes here
                // If the verification code is invalid, you can set an error attribute
                request.setAttribute("error", "error_invalid_code");
                request.getRequestDispatcher("UserRegisterThroughMail.jsp").forward(request, response);
            }
        } else {
            // Handle other requests or display the initial registration page
            request.getRequestDispatcher("UserRegisterThroughMail.jsp").forward(request, response);
        }
    }

    private String generateVerificationCode() {
        // Generate a random 6-digit verification code
        return String.format("%06d", (int) (Math.random() * 1000000));
    }

    private void sendVerificationCodeByEmail(String email, String verificationCode) {
        // Configure the email properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Create the email session
        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("your_email@example.com", "your_password");
            }
        });

        try {
            // Create the email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("your_email@example.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Verification Code");
            message.setText("Your verification code is: " + verificationCode);

            // Send the email
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
