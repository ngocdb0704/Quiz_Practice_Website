
package app.controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CounterController extends HttpServlet {
    private int count = 0;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.setAttribute("count", count);
        request.getRequestDispatcher("CounterView.jsp").forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        count++;
        request.setAttribute("count", count);
        request.getRequestDispatcher("CounterView.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "CounterController Servlet";
    }
}
