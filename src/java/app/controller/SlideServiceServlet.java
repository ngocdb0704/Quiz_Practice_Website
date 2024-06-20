/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package app.controller;

/**
 *
 * @author OwO
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import app.dal.DAOSlide;
import app.dal.DAOUser;
import app.entity.Slide;
import app.entity.User;

@WebServlet("/slideService")
@MultipartConfig(maxFileSize = 16177215) // Max file size up to 16MB
public class SlideServiceServlet extends HttpServlet {

//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String service = request.getParameter("service");
//
//        switch (service) {
//            case "updateStatus":
//                updateSlideStatus(request, response);
//                break;
//            case "editSlide":
//                editSlide(request, response);
//                break;
//            case "detailSlide":
//                detailSlide(request, response);
//                break;
//            case "updateImage":
//                updateSlideImage(request, response);
//                break;
//            default:
//                response.sendRedirect("error.jsp");
//                break;
//        }
//    }
//
//    private void updateSlideStatus(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        int sliderId = Integer.parseInt(request.getParameter("sliderId"));
//        String action = request.getParameter("action");
//        DAOSlide daoSlide = new DAOSlide();
//        boolean success = false;
//
//        if ("hide".equals(action)) {
//            success = daoSlide.updateSlideStatus(sliderId, false);
//        } else if ("show".equals(action)) {
//            success = daoSlide.updateSlideStatus(sliderId, true);
//        }
//
//        daoSlide.close();
//
//        if (success) {
//            response.sendRedirect("home.jsp");
//        } else {
//            response.sendRedirect("error.jsp");
//        }
//    }
//
//    private void editSlide(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        int sliderId = Integer.parseInt(request.getParameter("sliderId"));
//        request.setAttribute("sliderId", sliderId);
//        request.getRequestDispatcher("edit_slide.jsp").forward(request, response);
//    }
//
//    private void detailSlide(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        int sliderId = Integer.parseInt(request.getParameter("sliderId"));
//
//        DAOSlide daoSlide = new DAOSlide();
//        Slide selectedSlide = daoSlide.getSlideById(sliderId); // Assuming getSlideById exists
//        daoSlide.close();
//
//        request.setAttribute("selectedSlide", selectedSlide);
//        request.getRequestDispatcher("detail_slide.jsp").forward(request, response);
//    }
//
//    private void updateSlideImage(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        int sliderId = Integer.parseInt(request.getParameter("sliderId"));
//        Part filePart = request.getPart("image");
//
//        InputStream inputStream = filePart.getInputStream();
//
//        DAOSlide daoSlide = new DAOSlide();
//        boolean success = daoSlide.updateSlideImage(sliderId, inputStream);
//        daoSlide.close();
//
//        if (success) {
//            response.sendRedirect("home.jsp");
//        } else {
//            response.sendRedirect("error.jsp");
//        }
//    }
}