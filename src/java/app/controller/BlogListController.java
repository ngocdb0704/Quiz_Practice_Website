package app.controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@WebServlet(name="BlogListController", urlPatterns={"/blogs/list"})
public class BlogListController extends HttpServlet {
    public class Blog {
        private String title;
        private String content;

        public Blog(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        ArrayList<Blog> blogs = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            blogs.add(new Blog("ijawdjioajdo", "aowdjaowid"));
        }
        
        request.setAttribute("blogs", blogs);
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
