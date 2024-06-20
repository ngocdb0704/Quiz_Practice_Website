
package app.controller;

import app.dal.DAOBlog;
import app.dal.DAOBlogCategory;
import app.dal.QueryResult;
import app.entity.BlogCategory;
import app.entity.BlogInformation;
import app.entity.MarkdownDocument;
import app.utils.Parsers;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet(name="BlogDetailController", urlPatterns={"/blogs/detail"})
public class BlogDetailController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        DAOBlog daoBlog = new DAOBlog();
        DAOBlogCategory daoCategory = new DAOBlogCategory();

        String idRaw = request.getParameter("id");
        if (idRaw != null) {
            try {
                int id = Integer.parseInt(idRaw);
                BlogInformation information = daoBlog.getBlogInformationById(id);
                MarkdownDocument document = daoBlog.getPostTextById(id);

                QueryResult relatedPosts =
                        daoBlog.getByCategory(information.getCategoryId(), 3);

                request.setAttribute("related", relatedPosts.getResults());
                request.setAttribute("information", information);
                request.setAttribute("document", document);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }

            List<BlogCategory> categories = daoCategory.getAllCategories();
            request.setAttribute("categories", categories);

            QueryResult latestPosts = daoBlog.getRecentBlogs(4);
            request.setAttribute("recents", latestPosts.getResults());
        }
        
        request.getRequestDispatcher("BlogDetail.jsp").forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.getRequestDispatcher("BlogDetail.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "BlogDetailController Servlet";
    }
}
