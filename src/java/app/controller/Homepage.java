/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package app.controller;

import java.util.List;
import java.util.ArrayList;
import app.entity.Slide;
import app.dal.DAOSlide;
import app.entity.Subject;
import app.dal.DAOSubject;
import app.dal.DAOBlog;
import app.dal.DAOBlogCategory;
import app.entity.Blog;
import app.dal.DAOUser;
import app.entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 *
 * @author quatn
 */
public class Homepage extends HttpServlet {

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

        if (service != null) {
            if (service.equals("hotposts")) {
                int offSet = 0;
                try {
                    offSet = Integer.parseInt(session.getAttribute("hotpostOffset").toString());
                } catch (Exception e) {
                }

                DAOBlog daoBlog = new DAOBlog();
                List<Blog> fetchPost = daoBlog.getEnoughToDisplay(5, offSet);

                ConcurrentHashMap<Integer, String> catMap = null;
                try {
                    catMap = (ConcurrentHashMap<Integer, String>) session.getAttribute("blogCategoryMap");
                } catch (Exception e) {
                    DAOBlogCategory daoBlogC = new DAOBlogCategory();
                    catMap = daoBlogC.getMap();
                    session.setAttribute("blogCategoryMap", catMap);
                }

                DAOUser daoUser = new DAOUser();
                final ConcurrentHashMap<Integer, String> fullNameMap = daoUser.idArrayToNameMap(
                        fetchPost.stream()
                                .map((post) -> post.getUserId())
                                .mapToInt(i -> i).toArray());

                if (!(fullNameMap == null || fullNameMap.isEmpty())) {
                    response.setContentType("application/json");
                    try (PrintWriter out = response.getWriter()) {
                        out.print(Arrays.stream(fetchPost.toArray())
                                .map(obj -> (Blog) obj)
                                .map(blog -> String.format("{\"BlogId\": %d, \"FullName\": \"%s\", \"BlogCategoryId\": %d, \"BlogTitle\": \"%s\", \"UpdatedTime\": \"%s\", \"PostText\": \"%s\"}",
                                 blog.getBlogId(),
                                 fullNameMap.get(blog.getUserId()),
                                 blog.getBlogCategoryId(),
                                 blog.getBlogTitle(),
                                 blog.getUpdatedTime(),
                                 blog.getPostText()
                        ))
                                .collect(Collectors.toList())
                        );
                    }
                }
                return;
            }
        }

        if (session.getAttribute("homeSliders") == null) {
            DAOSlide daoSlide = new DAOSlide(); 
            List<Slide> sliders = daoSlide.getSliderList();
            session.setAttribute("homeSliders", sliders);
        }

        if (session.getAttribute("featuredSubjects") == null) {
            DAOSubject daoSubject = new DAOSubject();
            List<Subject> featuredSubjects = daoSubject.getEnoughToDisplay(5);
            if (featuredSubjects.size() > 0) {
                session.setAttribute("featuredSubjects", featuredSubjects);
            }
        }

        request.getRequestDispatcher("home.jsp").forward(request, response);
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
