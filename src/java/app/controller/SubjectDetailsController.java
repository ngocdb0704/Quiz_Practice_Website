/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package app.controller;

import app.dal.DAOPackage;
import app.dal.DAOSubject;
import app.entity.Subject;
import app.entity.SubjectCategory;
import app.entity.Package;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author quatn
 */
public class SubjectDetailsController extends HttpServlet {

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

        try {
            DAOSubject daoSubject = new DAOSubject();
            int id = Integer.parseInt(request.getParameter("subjectId"));

            Subject displaySubject = daoSubject.getSubjectById(id);
            request.setAttribute("subjectDetails", displaySubject);
            HashMap<Integer, ArrayList<Package>> map = daoSubject.getSubjectPackagesMap();
            request.setAttribute("map", map);


            List<SubjectCategory> categoryLine = daoSubject.getSubjectCategoryLineById(displaySubject.getCategoryId());
            System.out.println(displaySubject);
            Collections.reverse(categoryLine);

            request.setAttribute("subjectDetailsCategoryLine", categoryLine);

            //Still not gonna clean this up cus I've got no time lol
            Vector<Subject> newSubjectList = (Vector<Subject>) request.getAttribute("dataNewSubject");
            if (request.getAttribute("dataNewSubject") == null) {
                newSubjectList = daoSubject.getNewSubject();
                request.setAttribute("dataNewSubject", newSubjectList);
            }
            if (newSubjectList.stream().map(prdct -> prdct.getSubjectId()).anyMatch(sId -> sId == id)) {
                request.setAttribute("SubjectTagNew", true);
            } else {
                request.setAttribute("SubjectTagNew", false);
            }
            
            Vector<Subject> saleSubjectList = (Vector<Subject>) request.getAttribute("dataSaleSubject");
            if (request.getAttribute("dataSaleSubject") == null) {
                saleSubjectList = daoSubject.getBigSaleSubject();
                request.setAttribute("dataSaleSubject", saleSubjectList);
            }
            if (saleSubjectList.stream().map(prdct -> prdct.getSubjectId()).anyMatch(sId -> sId == id)) {
                request.setAttribute("SubjectTagBigSale", true);
            } else {
                request.setAttribute("SubjectTagBigSale", false);
            }

            request.setAttribute("SubjectTagFeatured", displaySubject.getIsFeatured());

            DAOPackage daoPackage = new DAOPackage();
            Package lowestPackage = daoPackage.getLowestPackageBySubjectId(id);
            session.setAttribute("lowestPackage", lowestPackage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("SubjectDetails.jsp").forward(request, response);

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
