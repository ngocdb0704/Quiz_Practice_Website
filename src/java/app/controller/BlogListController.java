package app.controller;

import app.dal.DAOBlog;
import app.dal.DAOBlog.QueryResult;
import app.dal.DAOBlogCategory;
import app.utils.Config;
import app.utils.Parsers;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;

@WebServlet(name="BlogListController", urlPatterns={"/blogs/list"})
public class BlogListController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        Config cfg = new Config(getServletContext());
        int pageSize = cfg.getIntOrDefault("pagination.size", 5);

        int page = Parsers.parseIntOrDefault(request.getParameter("page"), 1);
        int categoryId = Parsers.parseIntOrDefault(request.getParameter("categoryId"), -1);
        String query = request.getParameter("q");
        
        LocalDate startDate = Parsers.parseDateOrDefault(
            request.getParameter("startDate"),
            "yyyy-MM-dd",
            null
        );
        
        LocalDate endDate = Parsers.parseDateOrDefault(
            request.getParameter("endDate"),
            "yyyy-MM-dd",
            null
        );
        
        DAOBlog daoBlog = new DAOBlog();
        QueryResult queryResult = daoBlog.searchBlogListingsPaginated(
                query,
                categoryId,
                startDate,
                endDate,
                page,
                pageSize
        );

        request.setAttribute("pagesCount", queryResult.getTotalPages());
        request.setAttribute("blogs", queryResult.getResults());
        
        DAOBlogCategory daoBlogCategory = new DAOBlogCategory();
        request.setAttribute("categories", daoBlogCategory.getAllCategories());
        
        daoBlog.close();
        daoBlogCategory.close();

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
