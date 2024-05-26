/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package app.controller;

import app.dal.DAORegistration;
import app.dal.DAOSubject;
import app.dal.DAOUser;
import app.entity.Registration;
import app.entity.Subject;
import app.entity.User;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class RegistrationController extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        DAORegistration daoRegistration = new DAORegistration();
        DAOSubject daoSubject = new DAOSubject();
        HttpSession session = request.getSession();
        String service = request.getParameter("service");
        Vector<Registration> registrationVector = null;
        Vector<Subject> subjectVector = daoSubject.getFilterList();
        String page;
        String userEmail = "";
        String inputSearch = request.getParameter("search");
        // check inputSearch's value
        if (inputSearch == null) {
            inputSearch = "";
        }
        int pos;
        String subjectCategory = request.getParameter("subjectCategory");
        //check session's attribute for email
        if (session.getAttribute("userEmail") != null) {
            userEmail = session.getAttribute("userEmail").toString();
        } else {
            //redirect to home page
            page = "/index.jsp";
            dispath(request, response, page);
        }
        // check service's value
        if (service == null) {
            service = "listAll";
        }
        // check service's value
        if (service.equals("listAll")) {
            //check subject's category 
            if (subjectCategory == null) {
                subjectCategory = "0";
            }
            //no filter; no search
            if (subjectCategory.equals("0") && inputSearch.equals("")) {
                registrationVector = daoRegistration.getAll(userEmail);
                subjectVector.add(0, new Subject(0, "All Subject", "All Category"));
            } else if (!subjectCategory.equals("0") && inputSearch.equals("")) { //filter; no search
                pos = Integer.parseInt(subjectCategory) - 1;
                Subject sub = subjectVector.get(pos);
                subjectVector.set(pos, new Subject(0, "All", "All Category"));
                subjectVector.add(0, sub);
                registrationVector = daoRegistration.filterBySubjectCategory(userEmail, sub.getSubjectCategory());
            } else if (subjectCategory.equals("0") && !inputSearch.equals("")) { //search; no filter
                subjectVector.add(0, new Subject(0, "All Subject", "All Category"));
                registrationVector = daoRegistration.searchBySubjectName(userEmail, inputSearch);
            } else { //filter and search at the same time
                pos = Integer.parseInt(subjectCategory) - 1;
                Subject sub = subjectVector.get(pos);
                subjectVector.set(pos, new Subject(0, "All", "All Category"));
                subjectVector.add(0, sub);
                registrationVector = daoRegistration.searchNameFilter(userEmail, inputSearch, sub.getSubjectCategory());
            }

            request.setAttribute("value", inputSearch);
            request.setAttribute("select", subjectVector);
            request.setAttribute("data", registrationVector);
            page = "/MyRegistration.jsp";
            dispath(request, response, page);
        }
        // check service's value
        if (service.equals("cancel")) {
            int cancelId = Integer.parseInt(request.getParameter("cancelId"));
            int n = daoRegistration.removeRegistration(cancelId);
            service = "listAll";
            response.sendRedirect("RegistrationController");
        }
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet RegistrationController</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet RegistrationController at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
    }

    public void dispath(HttpServletRequest request, HttpServletResponse response, String url)
            throws ServletException, IOException {
        RequestDispatcher dispth = request.getRequestDispatcher(url);
        //run, show
        dispth.forward(request, response);
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

