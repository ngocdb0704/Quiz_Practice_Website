/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package app.controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import app.entity.Users;
import java.util.Vector;
import app.dal.DAOUsers;

/**
 *
 * @author quatn
 */
public class UserProfile extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String uId = request.getParameter("uId");
        System.out.println(uId);
        if (uId == null || uId.length() < 1) {
            try (PrintWriter out = response.getWriter()) {
                out.print("Provide a uId parameter to display a user");
            }
        }
        else {
            DAOUsers dao = new DAOUsers();
            String service = request.getParameter("service");
            System.out.println("AAAA");
            if (service != null && service.equals("update")) {
                System.out.println("BBBB");
                System.out.println(dao.updateUser(Integer.parseInt(uId), request.getParameter("fullName"), request.getParameter("gender"), request.getParameter("mobile")));
            }
             // TODO: Replace with get by user id
            Users fetched = dao.getUserById(Integer.parseInt(request.getParameter("uId")));
            System.out.println(fetched);
            request.setAttribute("UserObj", fetched);
            /*
            fetched.getEmail();
            fetched.getFullName();
            fetched.getGender();
            fetched.getMobile();
            */
            request.getRequestDispatcher("UserProfile.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
