/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package app.controller;

import app.entity.User;
import app.dal.DAOUser;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Vector;

/**
 *
 * @author OwO
 */
public class LoginControllerTempOfAnForLoginView extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String service = request.getParameter("service");
        DAOUser daoUser = new DAOUser();
        if (service.equals("login")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            session.setAttribute("userEmail", username);
            boolean flag = validateUser(username, password);
            if (flag) {
                String message = "Hello " + username + ". You logged in successfully";
                session.setAttribute("successMessage", message);

                response.sendRedirect("index.jsp");
            } else {
                response.sendRedirect("LoginInterface.jsp");
            }
        }

        if (service.equals("changepass")) {
            String username = (String) session.getAttribute("userEmail");
            if (username == null || username.length() < 1) {
                
                response.sendRedirect("LoginInterface.jsp");
            } else {
                String prePassword = request.getParameter("prePass");
                String newPassword = request.getParameter("newPass");
                String confirmPassword = request.getParameter("confirmPass");
                boolean flag = validateUser(username, prePassword);
                if (flag) {
                    if (!(newPassword.equals(prePassword) || newPassword.isEmpty())) {
                        if (confirmPassword.equals(newPassword)) {
                            daoUser.updatePassByUser(username, confirmPassword);
                            session.setAttribute("successMessage", "Change password successfully!");
                            response.sendRedirect("index.jsp");
                        } else {
                            session.setAttribute("changePassMessage", "The confirmation password is not identical with the new password. Try again!");
                            response.sendRedirect("ChangePassAn.jsp");
                        }
                    } else {
                        session.setAttribute("changePassMessage", "New password should be different from previous one and should not be empty. Try again!");
                        response.sendRedirect("ChangePassAn.jsp");
                    }
                } else {
                    session.setAttribute("changePassMessage", "Wrong username or password. Try again!");
                    response.sendRedirect("ChangePassAn.jsp");
                }
            }
        }

        if (service.equals("logout")) {
            session.invalidate();
            response.sendRedirect("index.jsp");
        }

    }

    public boolean validateUser(String username, String password) {
        boolean flag = false;
        DAOUser dao = new DAOUser();
        Vector<User> vec = dao.getAll("select * from [User]");

        for (User user : vec) {
            if (user.getEmail().equals(username) && user.getPassword().equals(password)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    @Override
    public String getServletInfo() {
        return "LoginControllerTempOfAnForLoginView Servlet";
    }

}
