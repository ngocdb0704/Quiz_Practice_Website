/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package app.controller;

import app.dal.DAOSubject;
import app.dal.DAOUser;
import app.entity.User;
import app.entity.Subject;
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
        
        DAOSubject daoSubject = new DAOSubject();
        
        if(service == null || service.equals("view")) {
            
            List<SubjectCategory> categoryList = (List<SubjectCategory>) request.getAttribute("subjectCategoryList");
            if (request.getAttribute("dataNewSubject") == null) {
                categoryList = daoSubject.getAllSubjectCategories();
                request.setAttribute("subjectCategoryList", categoryList);
            }
        }
        else if (service.equals("add")) {
            try (PrintWriter out = response.getWriter()){
                DAOUser daoUser = new DAOUser();
                
                String subjectTitle = request.getParameter("subjectTitle");
                int subjectCategory = Integer.parseInt(request.getParameter("subjectCategory"));
                int featured = (request.getParameter("featured") == null)? 0 : 1;
                int subjectStatus = Integer.parseInt(request.getParameter("subjectStatus"));
                String expertEmail = request.getParameter("expertEmail");
                User owner = daoUser.getUserByEmail(expertEmail);
                String thumbnailUrl = request.getParameter("thumbnailUrl");
                String subjectTagline = request.getParameter("subjectTagline");
                String subjectBrief = request.getParameter("subjectBrief");
                String subjectDescription = request.getParameter("subjectDescription");
                
                if (owner != null && owner.getRoleId() == 4) {
                    out.print(daoSubject.addSubject(new Subject(0, subjectTitle, subjectTagline, subjectBrief, subjectDescription, thumbnailUrl, subjectCategory), owner.getUserId(), subjectStatus, featured));
                }
                else {
                    out.print("User was not an Expert");
                }
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
