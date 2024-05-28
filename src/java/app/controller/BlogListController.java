package app.controller;

import app.dal.DAOBlog;
import app.dal.DAOBlog.QueryResult;
import app.dal.DAOBlogCategory;
import app.utils.Config;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name="BlogListController", urlPatterns={"/blogs/list"})
public class BlogListController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        Config cfg = new Config(getServletContext());
        int pageSize = cfg.getIntOrDefault("pagination.size", 5);

        int page = 1;
        int categoryId = -1;
        String query = request.getParameter("q");
        
        try {
            categoryId = Integer.parseInt(request.getParameter("categoryId"));
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        
        try (DAOBlog daoBlog = new DAOBlog()) {
            QueryResult queryResult = daoBlog.searchBlogListingsPaginated(query, categoryId, page, pageSize);

            request.setAttribute("pagesCount", queryResult.getTotalPages());
            request.setAttribute("blogs", queryResult.getResults());
        }
        
        try (DAOBlogCategory daoBlogCategory = new DAOBlogCategory()) {
            request.setAttribute("categories", daoBlogCategory.getAllCategories());
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
