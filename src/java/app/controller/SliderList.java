/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package app.controller;

import app.dal.DAOSlide;
import app.entity.Slide;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 *
 * @author OwO
 */
@WebServlet(name = "SliderList", urlPatterns = {"/sliderlist"})
public class SliderList extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        DAOSlide sdao = new DAOSlide();
        HttpSession session = request.getSession();
        int userId = 1;
//        Object o = session.getAttribute("account");
//        User user = (User) o;
//        userId = user.getUser_id();
        String action = request.getParameter("action");
        if (action == null) {
            String status = request.getParameter("status");
            String search = request.getParameter("search");
            String typeSearch = request.getParameter("type-search");
            String title = "";
            String backlink = "";
            if ("0".equals(typeSearch)) {
                title = search;
            }
            if ("1".equals(typeSearch)) {
                backlink = search;
            }
            if (status == null) {
                status = "";
            }
            int numberSlide = sdao.getNumberSlideWithCondition(status, title, backlink);
            int numberPage = (int) Math.ceil((double) numberSlide / 9);
            int index;
            String currentPage = request.getParameter("index");
            if (currentPage == null) {
                index = 1;
            } else {
                index = Integer.parseInt(currentPage);
            }
            ArrayList<Slide> slist = sdao.getAllSlideWithCondition(status, title, backlink, index);
//        response.getWriter().print(1);
            request.setAttribute("numberPage", numberPage);
            request.setAttribute("slist", slist);
            request.getRequestDispatcher("./admin/sliderlist.jsp").forward(request, response);
        }
        if ("edit".equals(action)) {
            String slidetitle = request.getParameter("slidetitle");
            String slideimage = request.getParameter("slideimage");
            String slidebacklink = request.getParameter("slidebacklink");
            String slidedescript = request.getParameter("slidedescript");
            String slidestatus = request.getParameter("slidestatus");
            String slideid = request.getParameter("slideid");
            sdao.updateSlide(slidetitle, slideimage, slidebacklink, userId, slidedescript, Integer.valueOf(slidestatus), Integer.valueOf(slideid));
            response.sendRedirect("sliderlist");
        }
        if ("switch".equals(action)) {
            String sid = request.getParameter("sid");
            String sstatus = request.getParameter("sstatus");
            sdao.switchStatus(Integer.valueOf(sid), Integer.valueOf(sstatus));
            response.sendRedirect("sliderlist");
        }
        if ("delete".equals(action)) {
            String sid = request.getParameter("sid");
            sdao.deleteSlide(Integer.valueOf(sid));
            response.sendRedirect("sliderlist");
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
