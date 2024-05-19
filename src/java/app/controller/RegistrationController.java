/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package app.controller;

import app.entity.DAORegistration;
import app.entity.DAOSubject;
import app.entity.Registration;
import app.entity.Subject;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Vector;

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
        String service = request.getParameter("service");
        Vector<Registration> registrationVector = null;
        Vector<Subject> subjectVector = daoSubject.getFilterList();
        String page;
        String subjectCategory = request.getParameter("subjectCategory");
        if (service == null) {
            service = "listAll";
        }
        if (service.equals("listAll")) {
            if(subjectCategory == null){
                subjectCategory = "0";
            }
            if(subjectCategory.equals("0")){
                registrationVector = daoRegistration.getAll(1);
                subjectVector.add(0, new Subject(0, "All Subject", "All Category"));
            } else{
                int pos = Integer.parseInt(subjectCategory) -1;
                Subject sub = subjectVector.get(pos);
                subjectVector.set(pos, new Subject(0, "All", "All Category"));
                subjectVector.add(0, sub);
                registrationVector = daoRegistration.filterBySubjectCategory(1, sub.getSubjectCategory());
            }
            request.setAttribute("select", subjectVector);
            request.setAttribute("data", registrationVector);
            page = "/MyRegistration.jsp";
            dispath(request, response, page);
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
