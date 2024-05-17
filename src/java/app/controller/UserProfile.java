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
            System.out.println(uIdString);
        }
        
        if (uIdString == null || uIdString.length() < 1) {
            try (PrintWriter out = response.getWriter()) {
                out.print("Provide a uId parameter to display a user");
            }
        }
        else {
            /*
            fetched.getEmail();
            fetched.getFullName();
            fetched.getGender();
            fetched.getMobile();
            */
            DAOUsers dao = new DAOUsers();
            Integer uId =  Integer.parseInt(uIdString);
            String service = request.getParameter("service");
            System.out.println(service);
            if (service == null || service.length() < 1 || service.equals("view")) {
                request.getRequestDispatcher("UserProfile.jsp").forward(request, response);
                return;
            }
            
            if (service.equals("update")) {
                Part filePart = request.getPart("upload");
                System.out.println(filePart.getInputStream().available());
                
                
                OutputStream o = response.getOutputStream();
                InputStream is = filePart.getInputStream();
                byte[] uploaded = new byte[filePart.getInputStream().available()];
                dao.insertImage(uId, uploaded);

            }
            
            if (service.equals("showPic")) {
                byte[] fetched = dao.profileImage(uId);
                System.out.println("PRODUCTION LINE");
                if (fetched == null) {
                    System.out.println("LIVING TO WORK");
                    response.setContentType("base64");
                    try (PrintWriter out = response.getWriter()) {
                        out.print("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4"
                                + "//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==");
                    }
                } else {
                    System.out.println("WORKING TO LIVE");
                    System.out.println(fetched);
                    OutputStream o = response.getOutputStream();
                    response.setContentType("image/gif");
                    o.write(fetched);
                    o.flush();
                    o.close();
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
