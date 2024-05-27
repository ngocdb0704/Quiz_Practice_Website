package app.controller;

import app.dal.DAOBlog;
import app.dal.DAOBlog.QueryResult;
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
        
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }

        try (DAOBlog daoBlog = new DAOBlog()) {
            QueryResult queryResult = daoBlog.getBlogListingsPaginated(page, pageSize);

            int pagesCount = (int)((double)(queryResult.getTotal()) / pageSize);
            request.setAttribute("pagesCount", pagesCount);
            request.setAttribute("blogs", queryResult.getResults());
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
