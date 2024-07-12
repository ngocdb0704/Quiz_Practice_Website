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
import app.entity.User;
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
public class UserProfileController extends HttpServlet {

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
        Integer uId = null;
        DAOUser dao = new DAOUser();

        //get userId attribute from session, get and set it if the attribute does't exist. 
        try {
            uId = (int)session.getAttribute("userId");
        } catch (Exception e) {
            if (session.getAttribute("userEmail") != null) {
                try {
                    User fetched = dao.getUserByEmail(session.getAttribute("userEmail").toString());
                    uId = fetched.getUserId();
                    session.setAttribute("userId", uId);
                    System.out.println("Id: " + uId);
                } catch (Exception e1) {
                    System.out.println(e1);
                }
            }
        }

        String service = request.getParameter("service");

        //Default/view service
        if (service == null || service.length() < 1 || service.equals("view")) {
            request.getRequestDispatcher("UserProfile.jsp").forward(request, response);
            return;
        }

        //All of the serives that require users to be logged in (session has a valid userId) will be behind this point, so this check for userId and redirect user to Unauthorized.jsp or let user continue.
        if (uId == null && !service.equals("showPic")) {
            request.getRequestDispatcher("Unauthorized.jsp").forward(request, response);
            return;
        } else {
            //Update service
            if (service.equals("update")) {
                try {
                    Part filePart = request.getPart("upload");
                    //TODO: add file limit via notice popup
                    //System.out.println(filePart.getInputStream().available());
                    
                    if (filePart.getSize() > 0) {
                        InputStream ins = filePart.getInputStream();
                        byte[] uploaded = new byte[ins.available()];
                        ins.read(uploaded);
                        dao.updateProfileImage(uId, uploaded);
                    }
                } catch (Exception e) {
                    System.out.println("ProfileUpdate1: " + e);
                }

                try {
                    String fullName = request.getParameter("fullName");
                    int genderId = Integer.parseInt(request.getParameter("gender"));
                    String mobile = request.getParameter("mobile");

                    //TODO: Add parameter conditions
                    //String currentPage = request.getRequestURI().substring(request.getContextPath().length());
                    dao.updateUserProfile(uId, fullName, genderId, mobile);
                    String redirectTo = request.getParameter("redirect");
                    System.out.println("Redirect to: " + redirectTo);
                    response.sendRedirect(redirectTo);
                    //request.getRequestDispatcher(redirectTo).forward(request, response);
                } catch (Exception e) {
                    //TODO: Redirect to an error page
                    System.out.println(e);
                }

            }

            //Update profile picture service
            if (service.equals("updateProfilePicture")) {
                Part filePart = request.getPart("upload");
                //TODO: add file limit via notice popup
                //System.out.println(filePart.getInputStream().available());

                System.out.println(filePart.getSize());
                
                InputStream ins = filePart.getInputStream();
                byte[] uploaded = new byte[ins.available()];
                ins.read(uploaded);
                dao.updateProfileImage(uId, uploaded);

                /* Debug: display the image that was just uploaded instead of going back to UserProfile
                try (OutputStream o = response.getOutputStream()) {
                    o.write(uploaded);
                    o.flush();
                    o.close();
                }
                 */
                String redirectTo = request.getParameter("redirect");
                System.out.println("Redirect to: " + redirectTo);
                response.sendRedirect(redirectTo);
                //request.getRequestDispatcher(redirectTo).forward(request, response);
            }

            //Fetch and return user's profile picture through http
            if (service.equals("showPic")) {
                byte[] fetched;

                try {
                    //If an UID is provided as a paremeter, fetch profile image of the user with that UID instead
                    fetched = dao.getProfileImage(Integer.parseInt(request.getParameter("uId")));
                } catch (Exception e) {
                    fetched = dao.getProfileImage(uId);
                }

                //Check if user has a profile picture
                if (fetched == null) {
                    request.getRequestDispatcher("public/images/anonymous-user.webp").forward(request, response);

                } else {
                    //Return fetched image via an OutputStream
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
