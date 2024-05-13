/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.Customer;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Vector;
import model.DAOCustomer;

/**
 *
 * @author Administrator
 */
public class CustomerController extends HttpServlet {

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
        DAOCustomer dao = new DAOCustomer();
        try (PrintWriter out = response.getWriter()) {
            String service = request.getParameter("service");
            if (service == null) {
                service = ("listAll");
            }
            if (service.equals("listAll")) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet CustomerController</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<a href=\"insertCustomer.html\">insert Customer</a>");
                out.println("<div>Filtered by</div>");
                out.println("<form method=\"get\">\n"
                        + "    <table>\n"
                        + "        <tr>\n"
                        + "            <td><input type=\"text\" name=\"input\" placeholder=\"Search\"></td>\n"
                        + "            <td><input type=\"submit\" value=\"Search\" name=\"submit\"></td>\n"
                        + "            <td><input type=\"reset\"  value=\"Clear\"></td>\n"
                        + "        </tr>\n"
                        + "    </table>\n"
                        + "</form>");
                out.println("<table border = 1>\n"
                        + "        <tr>\n"
                        + "            <th>UserID</th>\n"
                        + "            <th>Email</th>\n"
                        + "            <th>Password</th>\n"
                        + "            <th>Role</th>\n"
                        + "            <th>FullName</th>\n"
                        + "            <th>Gender</th>\n"
                        + "            <th>Mobile</th>\n"
                        + "            <th>View</th>\n"
                        + "            <th>Edit</th>\n"
                        + "        </tr>");
                String submit = request.getParameter("submit");
                Vector<Customer> vector;
                if (submit == null) { //list All
                    vector = dao.getAll("select * from [User]");
                } else {
                    String input = request.getParameter("input");
                    if (input.matches("^([A-Z]?[a-z]+[ ]?)+$")) {
                        vector = dao.searchbyName(input);
                    } else vector = null;
                    if(input.matches("^(\\\\+\\\\d{1,3}[- ]?)?\\\\d{10}$")){
                        vector = dao.searchbyMobile(input);
                    } else vector = null;
                    if(input.matches("(?:[a-z0-9!#$%&'+/=?^_`{|}~-]+"
                            + "(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)"
                            + "*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")){
                        vector = dao.searchbyEmail(input);
                    } else vector = null;
                }
                for (Customer cus : vector) {
                    out.print("<tr>\n"
                            + "            <td>" + cus.getUserID() + "</td>\n"
                            + "            <td>" + cus.getCustomerEmail() + "</td>\n"
                            + "            <td>" + cus.getPassword() + "</td>\n"
                            + "            <td>" + cus.getRole() + "</td>\n"
                            + "            <td>" + cus.getFullName() + "</td>\n"
                            + "            <td>" + cus.getGender() + "</td>\n"
                            + "            <td>" + cus.getMobile() + "</td>\n"
                            + "            <td><a>View</a></td>\n"
                            + "            <td><a>Edit</a></td>\n"
                            + "        </tr>");
                }
                out.println("</table>");
                out.println("</body>");
                out.println("</html>");
            }
            if (service.equals("View")) {

                String id = request.getParameter("UserID");
                Vector<Customer> vector = dao.getAll("select * from [User]"
                        + " where UserID=" + Integer.parseInt(id));
            }
            if (service.equals("addUser")) {
                //getdata
                int UserID = Integer.parseInt(request.getParameter("UserID"));
                String email = request.getParameter("Email");
                String password = request.getParameter("Password");
                String role = request.getParameter("Role");
                String fname = request.getParameter("FullName");
                String gender = request.getParameter("Gender");
                String mobile = request.getParameter("Mobile");
                Customer cus = new Customer(UserID, email, password, role, fname, gender, mobile);
                dao.addCustomers(cus);
                response.sendRedirect("CustomerControllerURL?service=listAll");
            }
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
