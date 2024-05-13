package app.controller;

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

    @Override
    public void init() {
        userDao = new UserDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.getRequestDispatcher("/ResetPassword.jsp").forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String email = request.getParameter("email");

        try {
            if (userDao.exists(email)) {
                request.setAttribute("message", "Email sent");
            } else {
                request.setAttribute("message", "User does not exist");
            }
        } catch (SQLException e) {
            request.setAttribute("message", "Internal Server Error");
        }
        
        request.getRequestDispatcher("/ResetPassword.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "CounterController Servlet";
    }
}
