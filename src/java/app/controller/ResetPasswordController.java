package app.controller;

import app.dal.ResetTokensDAO;
import app.dal.UserDAO;
import app.model.User;
import app.utils.BasicFieldExtractor;
import java.io.IOException;
import java.sql.SQLException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ResetPasswordController extends HttpServlet {

    private UserDAO userDao;
    private ResetTokensDAO resetTokensDao;

    @Override
    public void init() {
        userDao = new UserDAO();
        resetTokensDao = new ResetTokensDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.getRequestDispatcher("/ResetPassword.jsp").forward(request, response);
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
            request.setAttribute("message", "Internal Server Error");
        }
    }

    private void handleChangePassword(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, ServletException, IOException {
        request.getRequestDispatcher("/ResetPassword.jsp").forward(request, response);
    }

    private void handleSendEmail(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, ServletException, IOException {
        String email = request.getParameter("email");

        User user = userDao.getByEmail(email);

        if (user != null) {
            String token = resetTokensDao.createForUserId(user.getId());
            request.setAttribute("status", "sent");
            request.setAttribute("token", token);
            request.setAttribute("message", "Email sent");
        } else {
            request.setAttribute("message", "User does not exist");
        }
        
        request.getRequestDispatcher("/ResetPassword.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "CounterController Servlet";
    }
}
