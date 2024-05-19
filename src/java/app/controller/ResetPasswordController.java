package app.controller;

import app.dal.DAOResetTokens;
import app.dal.DAOUser;
import app.entity.ResetRecord;
import app.entity.User;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Objects;

public class ResetPasswordController extends HttpServlet {

    //in case the path changes, just change this line
    private final String RESET_PAGE = "/user/ResetPassword.jsp";

    private DAOUser daoUser;
    private DAOResetTokens daoResetTokens;

    //initialize your DAO here
    @Override
    public void init() {
        daoUser = new DAOUser();
        daoResetTokens = new DAOResetTokens();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String token = request.getParameter("token");
        if (token != null) {
            try {
                ResetRecord record = daoResetTokens.getByToken(token);
                if (!record.isValid()) {
                    request.setAttribute("screen", "expired");
                } else {
                    request.setAttribute("screen", "change_pw");
                    request.setAttribute("user", daoUser.getById(record.getUserId()));
                }
            } catch (Exception e) {
                request.setAttribute("error", "error_invalid_token");
                e.printStackTrace();
            }
        }

        request.getRequestDispatcher(RESET_PAGE).forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "send_email" -> handleSendEmail(request, response);
                case "reset_password" -> handleChangePassword(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleChangePassword(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, ServletException, IOException {
        String token = request.getParameter("token");
        String newPassword = request.getParameter("newpw");
        String confirmNewPassword = request.getParameter("confirmnewpw");
        boolean same = newPassword.equals(confirmNewPassword);

        if (token != null) {
            try {
                ResetRecord record = daoResetTokens.getByToken(token);

                if (!record.isValid()) {
                    request.setAttribute("screen", "expired");
                } else if (same) {
                    response.sendRedirect("../home");
                    daoUser.updatePassword(record.getUserId(), newPassword);
                    daoResetTokens.deleteToken(record.getUserId());
                    return;
                } else {
                    request.setAttribute("user", daoUser.getById(record.getUserId()));
                    request.setAttribute("screen", "change_pw");
                    request.setAttribute("error", "error_pw_not_same");
                }
            } catch (Exception e) {
                request.setAttribute("error", "error_invalid_token");
                e.printStackTrace();
            }
        }

        request.getRequestDispatcher(RESET_PAGE).forward(request, response);
    }

    private void handleSendEmail(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, ServletException, IOException {
        String email = request.getParameter("email");

        User user = daoUser.getByEmail(email);

        if (user != null) {
            String token = daoResetTokens.createForUserId(user.getUserId());
            request.setAttribute("screen", "sent");
            request.setAttribute("token", token);
        } else {
            request.setAttribute("error", "error_not_exist");
        }
        
        request.getRequestDispatcher(RESET_PAGE).forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "ResetPassword Servlet";
    }
}
