/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package app.controller;



import app.dal.DAOSubject;
import app.dto.SubjectDTO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UpdateSubjectController
 */

@WebServlet(name = "UpdateSubjectController", urlPatterns = {"/admin/subjectDimension"})
public class UpdateSubjectController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
        DAOSubject daoSubject = new DAOSubject();
        SubjectDTO subject = daoSubject.getSubjectByDTOId(subjectId);
        String mess = request.getParameter("mess");
        request.setAttribute("mess", mess);
        if (subject != null) {
            request.setAttribute("subject", subject);
            request.getRequestDispatcher("subject_detail.jsp").forward(request, response);
        } else {
            response.sendRedirect("error.jsp"); // Trang thông báo lỗi nếu không tìm thấy Subject
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int subjectId = Integer.parseInt(request.getParameter("subjectId"));
            String subjectTitle = request.getParameter("subjectTitle");
            boolean isFeaturedSubject = Boolean.parseBoolean(request.getParameter("isFeaturedSubject"));
            String subjectBriefInfo = request.getParameter("subjectBriefInfo");
            String subjectDescription = request.getParameter("subjectDescription");
            String subjectThumbnail = request.getParameter("subjectThumbnail");

            DAOSubject subjectDAO = new DAOSubject();
            SubjectDTO subject = subjectDAO.getSubjectByDTOId(subjectId);
            if (subject == null) {
                response.sendRedirect("/admin/subjectDimension?mess=not_found&subjectId=" + subjectId);
                return;
            }
            subject.setIsFeaturedSubject(isFeaturedSubject);
            subject.setSubjectBriefInfo(subjectBriefInfo);
            subject.setSubjectDescription(subjectDescription);
            subject.setSubjectThumbnail(subjectThumbnail);
            subject.setSubjectTitle(subjectTitle);
            
            boolean updateSuccess = subjectDAO.updateSubject(subject);

            if (updateSuccess) {
                response.sendRedirect("subjectDimension?subjectId=" + subjectId + "&mess=Successfully"); // Redirect to some confirmation page
            } else {
                request.setAttribute("mess", "Unable to update subject.");
                request.getRequestDispatcher("/admin/subjectDimension?mess=update_failed&subjectId=" + subjectId).forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("mess", "An error occurred while updating the subject.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for updating subjects";
    }
}
