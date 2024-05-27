package app.controller;

import app.dal.DAOBlog;
import app.entity.BlogInformation;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="BlogListController", urlPatterns={"/blogs/list"})
public class BlogListController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try (DAOBlog daoBlog = new DAOBlog()) {
            List<BlogInformation> blogs = daoBlog.getBlogListingsPaginated(1, 10);
            request.setAttribute("blogs", blogs);
        }

        request.getRequestDispatcher("BlogList.jsp").forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.getRequestDispatcher("BlogList.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "BlogListController Servlet";
    }
}
