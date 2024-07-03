/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package app.controller;

import app.dal.DAOSubject;
import app.entity.Subject;
import app.utils.Config;
import app.utils.GmailService;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author admin
 */
@WebServlet(name = "SubjectRegisterController", urlPatterns = {"/public/registerSubjectGuest"})
public class SubjectRegisterController extends HttpServlet {

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
        Config cfg = new Config(getServletContext());
        DAOSubject daoSubject = new DAOSubject();
        String service = request.getParameter("service");
        if (service.equals("freshRegister")) {
            int packageId = Integer.valueOf(request.getParameter("selectedPackage"));
            String email = request.getParameter("email");
            String mobile = request.getParameter("mobile");
            String fullName = request.getParameter("fullName");
            String gender = request.getParameter("gender");
            Subject registeredSubject = daoSubject.getSubjectByPackageId(packageId);
            GmailService gmailService = new GmailService(getServletContext());
            String emailBody = ""
                    + "<div class=\"container row\">\n"
                    + "                <div class=\"card col-6\">\n"
                    + "                    <img src=\"public/thumbnails/" + registeredSubject.getThumbnail() + "\" \n"
                    + "                         class=\"card-img-top\" alt=\"...\">\n"
                    + "                    <div class=\"card-body\">\n"
                    + "                        <h5 class=\"card-title\">\n"
                    + "                            " + registeredSubject.getSubjectName() + "\n"
                    + "                        </h5>\n"
                    + "                        <h6>\n"
                    + "                            " + registeredSubject.getTagLine() + "\n"
                    + "                        </h6>\n"
                    + "                        <h5>Package: " + registeredSubject.getLowestPackageName() + "</h5>\n"
                    + "                        <h5>Price: " + (int) registeredSubject.getPackageSalePrice() * 1000 + "VND</h5>\n"
                    + "                        <br>\n"
                    + "                    </div>\n"
                    + "                </div>\n"
                    + "                <div class=\"card col-6\">\n"
                    + "                    <h3>Register Report</h3>\n"
                    + "                    <div class=\"mb-3\">\n"
                    + "                        <label for=\"inputEmail\" class=\"form-label\">Email address</label>\n"
                    + "                        <input type=\"email\" class=\"form-control\" id=\"inputEmail\" \n"
                    + "                               name=\"email\" readonly value=" + email + ">\n"
                    + "                    </div>\n"
                    + "                    <div class=\"mb-3\">\n"
                    + "                        <label for=\"inputMobile\" class=\"form-label\">Phone number</label>\n"
                    + "                        <input type=\"text\" class=\"form-control\"  readonly\n"
                    + "                               id=\"inputMobile\" value=" + mobile + "\n"
                    + "                               name=\"mobile\">\n"
                    + "                    </div>\n"
                    + "                    <div class=\"row mb-3\">\n"
                    + "                        <div class=\"col-8\">\n"
                    + "                            <label for=\"inputFullName\" class=\"form-label\">Full Name</label>\n"
                    + "                            <input type=\"text\" class=\"form-control\" name=\"fullName\"\n"
                    + "                                   id=\"inputFullName\" readonly\n"
                    + "                                   value=" + fullName + ">\n"
                    + "                        </div>\n"
                    + "                        <div class=\"col-4\">\n"
                    + "                            <label for=\"inputGender\" class=\"form-label\">Gender</label>\n"
                    + "                            <input type=\"text\" class=\"form-control\" name=\"fullName\"\n"
                    + "                                   id=\"inputGender\" readonly\n"
                    + "                                   value=" + gender + ">\n"
                    + "                            <div class=\"mt-4 container text-end\">\n"
                    + "                                <input type=\"hidden\" name=\"service\" value=\"registPaid\"/>\n"
                    + "                            </div>\n"
                    + "                        </div>\n"
                    + "                    </div>\n"
                    + "                </div>\n"
                    + "            </div>";
            try {
                gmailService.sendMailWithContent("Reset Password", emailBody, new String[]{email});
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.sendRedirect("SubjectsList");
        }
    }

    private String generateRegisterUrl(HttpServletRequest req, String token) {
        return String.format(
                "%s://%s:%s%s/public/registerSubjectGuest?token=" + token,
                req.getScheme(),
                req.getServerName(),
                req.getServerPort(),
                req.getContextPath()
        );
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
