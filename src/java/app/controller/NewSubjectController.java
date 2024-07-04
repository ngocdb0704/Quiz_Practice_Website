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
import app.utils.Config;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author quatn
 */
@WebServlet(name = "NewSubjectController")
@MultipartConfig //This enables a native API that can read "multipart/form-data" file upload. Source: https://stackoverflow.com/questions/2422468/how-can-i-upload-files-to-a-server-using-jsp-servlet
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
        ServletContext ctx = request.getServletContext();

        String service = request.getParameter("service");

        DAOSubject daoSubject = new DAOSubject();
        DAOUser daoUser = new DAOUser();

        if (service == null || service.equals("view")) {

            List<SubjectCategory> categoryList = (List<SubjectCategory>) request.getAttribute("subjectCategoryList");
            if (request.getAttribute("dataNewSubject") == null) {
                categoryList = daoSubject.getAllSubjectCategories();
                request.setAttribute("subjectCategoryList", categoryList);
            }

            ConcurrentHashMap<String, String> expertList = (ConcurrentHashMap) request.getAttribute("expertList");
            if (request.getAttribute("expertList") == null) {
                expertList = daoUser.ExpertsEmailNameMap();

                String jsonMap = (expertList.size() > 0) ? expertList
                        .reduce(0, (key, val) -> String.format("{\"email\": \"%s\", \"name\": \"%s\"}", key, val), (obj, obj1) -> obj + ", " + obj1).toString()
                        : "";
                System.out.println(jsonMap);
                request.setAttribute("expertList", "[" + jsonMap + "]");
            }
        } else if (service.equals("add")) {
            String subjectTitle = request.getParameter("subjectTitle");
            int subjectCategory = Integer.parseInt(request.getParameter("subjectCategory"));
            int featured = (request.getParameter("featured") == null) ? 0 : 1;
            int subjectStatus = Integer.parseInt(request.getParameter("subjectStatus"));
            String expertEmail = request.getParameter("expertEmail");
            User owner = daoUser.getUserByEmail(expertEmail);
            String subjectTagline = request.getParameter("subjectTagline");
            String subjectBrief = request.getParameter("subjectBrief");
            String subjectDescription = request.getParameter("subjectDescription");
            String uploadName = request.getParameter("uploadName");
            
            String thumbnailUrl = "";
            
            Part filePart = request.getPart("uploadData");
            if (filePart.getSize() > 0 && uploadName != null && (uploadName.endsWith(".png") || uploadName.endsWith(".jpg") || uploadName.endsWith(".jpeg") || uploadName.endsWith(".webm"))) {
                Config cfg = new Config(ctx);
                String thumbnailDir = cfg.getStringOrDefault("subjectThumbnail.dir", "/");
                thumbnailDir = thumbnailDir.replaceAll("\\s+", File.separator);
                String path = ctx.getRealPath(thumbnailDir);
                int subjectId = daoSubject.getLatestSubjectId();
                subjectId = (subjectId < 0)? 1 : subjectId; //If the table is empty then getLatestSubjectId() will return -1, in that case set subjectId to 1 (as the first element of the table
                
                String newFileName = path + File.separator + subjectId + "_" + uploadName;
                System.out.println("Save file as:" + newFileName);
                
                File file = new File(newFileName);
                InputStream ins = filePart.getInputStream();
                byte[] uploaded = new byte[ins.available()];
                ins.read(uploaded);
                try (FileOutputStream outputStream = new FileOutputStream(file)) {
                    outputStream.write(uploaded);
                    thumbnailUrl = subjectId + "_" + uploadName;
                }
            }

            if (owner != null && owner.getRoleId() == 4) {
                if (daoSubject.addSubject(new Subject(0, subjectTitle, subjectTagline, subjectBrief, subjectDescription, thumbnailUrl, subjectCategory), owner.getUserId(), subjectStatus, featured) == 1) //session.setAttribute("notification", "<p>Subject created succefully!</p>");
                {
                    session.setAttribute("notification", "Subject created succefully!");
                }
            } else {
                //out.print("User was not an Expert");
                //session.setAttribute("notification", "<p style='color: red'>Error: The user email submitted was not of an Expert</p>");
                session.setAttribute("notification", "Error: The user email submitted was not of an Expert");
                System.out.println("User was not an Expert");
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
