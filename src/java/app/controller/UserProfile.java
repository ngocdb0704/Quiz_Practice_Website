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
import java.util.Vector;
import app.dal.DAOUser;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.MultipartConfig;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author quatn
 */

@MultipartConfig //This enables a native API that can read "multipart/form-data" file upload. Source: https://stackoverflow.com/questions/2422468/how-can-i-upload-files-to-a-server-using-jsp-servlet
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
        
        HttpSession session = request.getSession();
        String uIdString = null;
        
        if (session.getAttribute("uId") != null) {
            uIdString = session.getAttribute("uId").toString();
        }
        
        if (uIdString == null || uIdString.length() < 1) {
            try (PrintWriter out = response.getWriter()) {
                out.print("You need to be logged in to display user profile");
            }
        }
        else {
            DAOUser dao = new DAOUser();
            Integer uId =  Integer.parseInt(uIdString);
            String service = request.getParameter("service");

            if (service == null || service.length() < 1 || service.equals("view")) {
                request.getRequestDispatcher("UserProfile.jsp").forward(request, response);
                return;
            }
            
            if (service.equals("update")) {
                String fullName = request.getParameter("fullName");
                String gender = request.getParameter("gender");
                String mobile = request.getParameter("mobile");
                //TODO: Add parameter conditions
                dao.updateUser(uId, fullName, gender, mobile);
                request.getRequestDispatcher("UserProfile.jsp").forward(request, response);
            }
            
            if (service.equals("updateProfilePicture")) {
                Part filePart = request.getPart("upload");
                System.out.println(filePart.getInputStream().available()); //TODO: add file limit
                
                InputStream ins = filePart.getInputStream();
                byte[] uploaded = new byte[ins.available()];
                ins.read(uploaded);
                dao.updateImage(uId, uploaded);
                
                /* Debug: display the image that was just uploaded instead of going back to UserProfile
                try (OutputStream o = response.getOutputStream()) {
                    o.write(uploaded);
                    o.flush();
                    o.close();
                }
                */
                
                request.getRequestDispatcher("UserProfile.jsp").forward(request, response);
            }
            
            if (service.equals("showPic")) {
                byte[] fetched = dao.profileImage(uId);
                if (fetched == null) {
                    response.setContentType("image/gif");
                    ServletContext cntxt = this.getServletContext();
                    String fName = "image/anonymous-user.webp";
                    InputStream ins = cntxt.getResourceAsStream(fName);
                    
                    try (OutputStream o = response.getOutputStream()) {
                        byte[] fetchedDefault = new byte[ins.available()];
                        ins.read(fetchedDefault);
                        o.write(fetchedDefault);
                        o.flush();
                        o.close();
                    }
                    
                } else {
                    try (OutputStream o = response.getOutputStream()) {
                        response.setContentType("image/gif");
                        o.write(fetched);
                        o.flush();
                        o.close();
                    }
                }
            }
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
