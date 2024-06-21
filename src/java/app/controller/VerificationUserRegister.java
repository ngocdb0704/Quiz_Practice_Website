/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package app.controller;

import app.dal.DAOUser;
import app.entity.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.regex.Pattern;

/**
 *
 * @author OwO
 */
public class VerificationUserRegister extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOUser daoUser = new DAOUser();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        int genderID = Integer.parseInt(request.getParameter("gender"));
        String mobile = request.getParameter("mobile");
        String verificationCode = request.getParameter("verificationCode");

        HttpSession session = request.getSession();
        String sessionVerificationCode = (String) session.getAttribute("verificationCode");
        Long sessionVerificationCodeTimestamp = (Long) session.getAttribute("verificationCodeTimestamp");

        if (sessionVerificationCode != null && sessionVerificationCodeTimestamp != null) {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - sessionVerificationCodeTimestamp;

            if (elapsedTime <= 60000) { // 60 seconds = 60000 milliseconds
                if (verificationCode.equals(sessionVerificationCode)) {
                    // Validate and register user
                    String passwordRegex = "^(?=.*\\d)(?=.*[a-zA-Z]).{8,31}$";
                    String fullNameRegex = "^(?=.*[a-zA-Z])[a-zA-Z ]+$";
                    String mobileRegex = "^0\\d{9}$";

                    if (Pattern.matches(fullNameRegex, fullName)
                            && Pattern.matches(passwordRegex, password)
                            && Pattern.matches(mobileRegex, mobile)) {
                        // Registration logic (save user to database)
                        User user = new User(1, email, password, 1, fullName, genderID, mobile, true);
                        daoUser.addUser(user);

                        response.getWriter().write("success");
                        session.removeAttribute("verificationCode");
                        session.removeAttribute("verificationCodeTimestamp");
                    } else {
                        // Handle invalid input formats
                        StringBuilder errorMessage = new StringBuilder();
                        if (!Pattern.matches(fullNameRegex, fullName)) {
                            errorMessage.append("Your name should contain alphabet characters (and space between them if needed) only.\n");
                        }
                        if (!Pattern.matches(passwordRegex, password)) {
                            errorMessage.append("Password must contain at least one letter, one number, and be between 8 and 31 characters long.\n");
                        }
                        if (!Pattern.matches(mobileRegex, mobile)) {
                            errorMessage.append("Your mobile number should contain 10 digits, with '0' at the start.\n");
                        }
                        response.getWriter().write(errorMessage.toString());
                    }
                } else {
                    response.getWriter().write("Invalid verification code");
                }
            } else {
                response.getWriter().write("Expired code");
            }
        } else {
            response.getWriter().write("Invalid verification code");
        }
    }
}
