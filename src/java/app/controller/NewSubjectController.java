/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package app.controller;

import app.dal.DAOSubject;
import app.entity.SubjectCategory;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author quatn
 */
@WebServlet(name = "NewSubjectController")
public class NewSubjectController extends HttpServlet {

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

        HttpSession session = request.getSession();

        String service = request.getParameter("service");
        
        if(service == null || service.equals("view")) {
            DAOSubject daoSubject = new DAOSubject();
            List<SubjectCategory> categoryList = (List<SubjectCategory>) request.getAttribute("subjectCategoryList");
            if (request.getAttribute("dataNewSubject") == null) {
                categoryList = daoSubject.getAllSubjectCategories();
                request.setAttribute("subjectCategoryList", categoryList);
            }
        }
        else if (service.equals("add")) {
            try (PrintWriter out = response.getWriter()){
                out.print(request.getParameter("subjectTitle") + ",");
                out.print(request.getParameter("subjectCategory") + ",");
                out.print(request.getParameter("featured") + ",");
                out.print(request.getParameter("subjectStatus") + ",");
                out.print(request.getParameter("expertEmail") + ",");
                out.print(request.getParameter("thumbnailUrl") + ",");
                out.print(request.getParameter("subjectDescription"));
            }
        }
            
        request.getRequestDispatcher("NewSubject.jsp").forward(request, response);
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
