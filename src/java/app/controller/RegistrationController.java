/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package app.controller;

import app.dal.DAOPackage;
import app.dal.DAORegistration;
import app.dal.DAOSubject;
import app.entity.Registration;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Vector;
import app.entity.Package;
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
<<<<<<< HEAD
        DAOUser daoUser = new DAOUser();
=======
        DAOPackage daoPack = new DAOPackage();
>>>>>>> origin/ngocBranch
        HttpSession session = request.getSession();
        String userEmailString = "";
        String service = request.getParameter("service");
        Vector<Registration> registrationVector = null;
        Vector<String> statusVector = daoRegistration.statusFilter();
        String page;
        int userId;
        User loggedInUser = null;
        String submit = request.getParameter("submit");
        int permitToListAll = 0;
        String inputSearch = request.getParameter("search");
        if (inputSearch == null) {
            inputSearch = "";
        }
        int pos;
<<<<<<< HEAD
        String subjectCategory = request.getParameter("subjectCategory");
        if (session.getAttribute("username") != null) {
            userEmailString = session.getAttribute("username").toString();
=======
        String filterStatus = request.getParameter("subjectStatus");
        //check session's attribute for email
        if (session.getAttribute("userEmail") != null) {
            userEmail = session.getAttribute("userEmail").toString();
>>>>>>> origin/ngocBranch
        } else {
            page = "/index.jsp";
            dispath(request, response, page);
        }
        try {
            loggedInUser = daoUser.getByEmail(userEmailString);
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        userId = loggedInUser.getUserId();
        if (service == null) {
            service = "listAll";
        }
        if (service.equals("listAll")) {
<<<<<<< HEAD
            if (subjectCategory == null) {
                subjectCategory = "0";
            }
            if (subjectCategory.equals("0") && inputSearch.equals("")) {
                registrationVector = daoRegistration.getAll(userId);
                subjectVector.add(0, new Subject(0, "All Subject", "All Category"));
            } else if (!subjectCategory.equals("0") && inputSearch.equals("")) {
                pos = Integer.parseInt(subjectCategory) - 1;
                Subject sub = subjectVector.get(pos);
                subjectVector.set(pos, new Subject(0, "All", "All Category"));
                subjectVector.add(0, sub);
                registrationVector = daoRegistration.filterBySubjectCategory(userId, sub.getSubjectCategory());
            } else if (subjectCategory.equals("0") && !inputSearch.equals("")) {
                subjectVector.add(0, new Subject(0, "All Subject", "All Category"));
                registrationVector = daoRegistration.searchBySubjectName(userId, inputSearch);
            } else {
                pos = Integer.parseInt(subjectCategory) - 1;
                Subject sub = subjectVector.get(pos);
                subjectVector.set(pos, new Subject(0, "All", "All Category"));
                subjectVector.add(0, sub);
                registrationVector = daoRegistration.searchNameFilter(userId, inputSearch, sub.getSubjectCategory());
=======
            //check subject's category 
            if (filterStatus == null) {
                filterStatus = "0All Status";
            }
            //no filter; no search
            if (filterStatus.contains("All Status") && inputSearch.equals("")) {
                registrationVector = daoRegistration.getAll(userEmail);
                statusVector.add(0, "All Status");
            } else if (!filterStatus.contains("All Status") && inputSearch.equals("")) { //filter; no search
                pos = Integer.parseInt(filterStatus.substring(0, 1)) - 1;
                String posStatus = statusVector.get(pos);
                statusVector.set(pos, "All Status");
                statusVector.add(0, posStatus);
                registrationVector = daoRegistration.filterBySubjectStatus(userEmail, posStatus);
            } else if (filterStatus.contains("All Status") && !inputSearch.equals("")) { //search; no filter
                statusVector.add(0, "All Status");
                registrationVector = daoRegistration.searchBySubjectName(userEmail, inputSearch);
            } else { //filter and search at the same time
                pos = Integer.parseInt(filterStatus.substring(0, 1)) - 1;
                String posStatus = statusVector.get(pos);
                statusVector.set(pos, "All Status");
                statusVector.add(0, posStatus);
                registrationVector = daoRegistration.searchNameFilter(userEmail, inputSearch, posStatus);
>>>>>>> origin/ngocBranch
            }

            request.setAttribute("value", inputSearch);
            request.setAttribute("prevStatus", filterStatus);
            request.setAttribute("select", statusVector);
            request.setAttribute("data", registrationVector);
            page = "/MyRegistration.jsp";
            dispath(request, response, page);
        }
        if (service.equals("cancel")) {
            int cancelId = Integer.parseInt(request.getParameter("cancelId"));
            int n = daoRegistration.removeRegistration(cancelId);
            service = "listAll";
            response.sendRedirect("RegistrationController");
        }
        if (service.equals("update")) {
            int registId = Integer.parseInt(request.getParameter("registId"));
            Registration newRegist = daoRegistration.getByRegistId(userEmail, registId);
            String subjectName = request.getParameter("sname");
            String packName = request.getParameter("packName");
            Package newPack = daoPack.getByPackageNameSubjectName(packName, subjectName);
            int n = daoRegistration.updateRegistration(newPack, registId);
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
