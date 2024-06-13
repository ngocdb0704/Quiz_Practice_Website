/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package app.controller;

import app.dal.DAOCustomer;
import app.entity.Customer;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;
import app.dal.DAOCustomer;

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
                Vector<Customer> vector = dao.getAll("select u.UserId , u.Email, u.Password,r.RoleName as Role,u.FullName,g.GenderName as Gender,u.Mobile,u.IsActive from [User] u\n"
                        + "join [Gender] g on u.GenderId = g.GenderId \n"
                        + "join [Role] r on r.RoleId = u.RoleId");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }

            if (service.equals("detailsList")) {
                int id = Integer.parseInt(request.getParameter("id"));
                Vector<Customer> vector = dao.getAll("select u.UserId , u.Email, u.Password,r.RoleName as Role,u.FullName,g.GenderName as Gender,u.Mobile,u.IsActive from [User] u\n"
                        + "join [Gender] g on u.GenderId = g.GenderId \n"
                        + "join [Role] r on r.RoleId = u.RoleId where UserId ="+id);
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserDetails");
                request.setAttribute("titleTable", "User Details");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserDetails.jsp");
                //run
                dispath.forward(request, response);
            }

            if (service.equals("sortbyID")) {
                Vector<Customer> vector = dao.getAll("select u.UserId , u.Email, u.Password,r.RoleName as Role,u.FullName,g.GenderName as Gender,u.Mobile,u.IsActive from [User] u\n"
                        + "join [Gender] g on u.GenderId = g.GenderId \n"
                        + "join [Role] r on r.RoleId = u.RoleId order by u.UserId");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User sorted by ID");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }
            if (service.equals("sortbyName")) {
                Vector<Customer> vector = dao.getAll("select u.UserId , u.Email, u.Password,r.RoleName as Role,u.FullName,g.GenderName as Gender,u.Mobile,u.IsActive from [User] u\n"
                        + "join [Gender] g on u.GenderId = g.GenderId \n"
                        + "join [Role] r on r.RoleId = u.RoleId order by u.FullName");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User sorted by Name");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }
            if (service.equals("sortbyMail")) {
                Vector<Customer> vector = dao.getAll("select u.UserId , u.Email, u.Password,r.RoleName as Role,u.FullName,g.GenderName as Gender,u.Mobile,u.IsActive from [User] u\n"
                        + "join [Gender] g on u.GenderId = g.GenderId \n"
                        + "join [Role] r on r.RoleId = u.RoleId order by u.Email");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User sorted by Email");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }
            if (service.equals("sortbyPhone")) {
                Vector<Customer> vector = dao.getAll("select u.UserId , u.Email, u.Password,r.RoleName as Role,u.FullName,g.GenderName as Gender,u.Mobile,u.IsActive from [User] u\n"
                        + "join [Gender] g on u.GenderId = g.GenderId \n"
                        + "join [Role] r on r.RoleId = u.RoleId order by u.Mobile");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User sorted by MobilePhone");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }
            if (service.equals("sortbyRole")) {
                Vector<Customer> vector = dao.getAll("select u.UserId , u.Email, u.Password,r.RoleName as Role,u.FullName,g.GenderName as Gender,u.Mobile,u.IsActive from [User] u\n"
                        + "join [Gender] g on u.GenderId = g.GenderId \n"
                        + "join [Role] r on r.RoleId = u.RoleId order by u.GenderId");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User sorted by Role");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }
                        if (service.equals("sortbyGen")) {
                Vector<Customer> vector = dao.getAll("select u.UserId , u.Email, u.Password,r.RoleName as Role,u.FullName,g.GenderName as Gender,u.Mobile,u.IsActive from [User] u\n"
                        + "join [Gender] g on u.GenderId = g.GenderId \n"
                        + "join [Role] r on r.RoleId = u.RoleId order by g.GenderId");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User sorted by Gender");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }
                        if (service.equals("sortbyStatus")) {
                Vector<Customer> vector = dao.getAll("select u.UserId , u.Email, u.Password,r.RoleName as Role,u.FullName,g.GenderName as Gender,u.Mobile,u.IsActive from [User] u\n"
                        + "join [Gender] g on u.GenderId = g.GenderId \n"
                        + "join [Role] r on r.RoleId = u.RoleId order by u.isActive");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User sorted by Status");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }

            if (service.equals("searchByname")) {
                String input = request.getParameter("input");
                Vector<Customer> vector = dao.getAll("select * from [User] where FullName like '%" + input + "%'");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }
            if (service.equals("searchByemail")) {
                String input2 = request.getParameter("input2");
                Vector<Customer> vector = dao.getAll("select * from [User] where Email like '%" + input2 + "%'");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }
            if (service.equals("searchByphone")) {
                String input3 = request.getParameter("input3");
                Vector<Customer> vector = dao.getAll("select * from [User] where Mobile like '%" + input3 + "%'");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }
            

            if (service.equals("addUser")) {
                //getdata
                int id = Integer.parseInt(request.getParameter("UserId"));
                String em = request.getParameter("Email");
                String pass = request.getParameter("Password");
                String rid = request.getParameter("RoleId");
                String fname = request.getParameter("FullName");
                String genid = request.getParameter("GenderId");
                String mb = request.getParameter("Mobile");
                boolean isactive
                        = Boolean.parseBoolean(request.getParameter("isActive"));
                Customer cus = new Customer(id, em, pass, rid, fname, genid, mb, isactive);
                dao.addCustomers(cus);
                response.sendRedirect("CustomerController?service=listAll");
            }
            if (service.equals("activefilter")) {
                Vector<Customer> vector = dao.getAll("SELECT u.UserId, u.Email, u.Password, r.RoleName as [Role], u.FullName, g.GenderName as Gender, u.Mobile, u.IsActive \n"
                        + "FROM [User] u \n"
                        + "JOIN [Role] r ON u.RoleId = r.RoleId\n"
                        + "JOIN [Gender] g on u.GenderId = g.GenderId\n"
                        + "WHERE IsActive = '1';");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User that active");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }

            if (service.equals("notactivefilter")) {
                Vector<Customer> vector = dao.getAll("SELECT u.UserId, u.Email, u.Password, r.RoleName as [Role], u.FullName, g.GenderName as Gender, u.Mobile, u.IsActive \n"
                        + "FROM [User] u \n"
                        + "JOIN [Role] r ON u.RoleId = r.RoleId\n"
                        + "JOIN [Gender] g on u.GenderId = g.GenderId\n"
                        + "WHERE IsActive = '0';");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User that not active");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }
            if (service.equals("malefilter")) {
                Vector<Customer> vector = dao.getAll("SELECT u.UserId, u.Email, u.Password, r.RoleName, u.FullName, g.GenderName as Gender, u.Mobile, u.IsActive \n"
                        + "FROM [User] u \n"
                        + "JOIN [Role] r ON u.RoleId = r.RoleId\n"
                        + "JOIN Gender g ON u.GenderId = g.GenderId\n"
                        + "WHERE u.GenderId = '1';");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User that is Male");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }
            if (service.equals("femalefilter")) {
                Vector<Customer> vector = dao.getAll("SELECT u.UserId, u.Email, u.Password, u.RoleId, u.FullName, g.GenderName as Gender, u.Mobile, u.IsActive \n"
                        + "FROM [User] u \n"
                        + "JOIN Gender g ON u.GenderId = g.GenderId\n"
                        + "WHERE u.GenderId = '2';");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User that is Female");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }
            if (service.equals("guestfilter")) {
                Vector<Customer> vector = dao.getAll("SELECT u.UserId, u.Email, u.Password, r.RoleName as [Role], u.FullName, g.GenderName as Gender, u.Mobile, u.IsActive \n"
                        + "FROM [User] u \n"
                        + "JOIN [Role] r ON u.RoleId = r.RoleId\n"
                        + "JOIN [Gender] g on u.GenderId = g.GenderId\n"
                        + "WHERE r.RoleId = '2';");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User that is Guest");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }

            if (service.equals("cusfilter")) {
                Vector<Customer> vector = dao.getAll("SELECT u.UserId, u.Email, u.Password, r.RoleName as [Role], u.FullName, g.GenderName as Gender, u.Mobile, u.IsActive \n"
                        + "FROM [User] u \n"
                        + "JOIN [Role] r ON u.RoleId = r.RoleId\n"
                        + "JOIN [Gender] g on u.GenderId = g.GenderId\n"
                        + "WHERE r.RoleId = '1';");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User that is Customer");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }
            if (service.equals("mktfilter")) {
                Vector<Customer> vector = dao.getAll("SELECT u.UserId, u.Email, u.Password, r.RoleName as [Role], u.FullName, g.GenderName as Gender, u.Mobile, u.IsActive \n"
                        + "FROM [User] u \n"
                        + "JOIN [Role] r ON u.RoleId = r.RoleId\n"
                        + "JOIN [Gender] g on u.GenderId = g.GenderId\n"
                        + "WHERE r.RoleId = '3';");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User that is Customer");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }
            if (service.equals("salefilter")) {
                Vector<Customer> vector = dao.getAll("SELECT u.UserId, u.Email, u.Password, r.RoleName as [Role], u.FullName, g.GenderName as Gender, u.Mobile, u.IsActive \n"
                        + "FROM [User] u \n"
                        + "JOIN [Role] r ON u.RoleId = r.RoleId\n"
                        + "JOIN [Gender] g on u.GenderId = g.GenderId\n"
                        + "WHERE r.RoleId = '4';");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User that is Customer");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }

            if (service.equals("expertfilter")) {
                Vector<Customer> vector = dao.getAll("SELECT u.UserId, u.Email, u.Password, r.RoleName as [Role], u.FullName, g.GenderName as Gender, u.Mobile, u.IsActive \n"
                        + "FROM [User] u \n"
                        + "JOIN [Role] r ON u.RoleId = r.RoleId\n"
                        + "JOIN [Gender] g on u.GenderId = g.GenderId\n"
                        + "WHERE r.RoleId = '5';");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User that is Customer");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }
            if (service.equals("admfilter")) {
                Vector<Customer> vector = dao.getAll("SELECT u.UserId, u.Email, u.Password, r.RoleName as [Role], u.FullName, g.GenderName as Gender, u.Mobile, u.IsActive \n"
                        + "FROM [User] u \n"
                        + "JOIN [Role] r ON u.RoleId = r.RoleId\n"
                        + "JOIN [Gender] g on u.GenderId = g.GenderId\n"
                        + "WHERE r.RoleId = '6';");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User that is Customer");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }
            if (service.equals("update")) {
                //check submit
                String submit = request.getParameter("submit");
                if (submit == null) { 
                    int id = Integer.parseInt(request.getParameter("id"));
                    Vector<Customer> vector
                            = dao.getAll("select * from [User] where UserId =" + id);
                    request.setAttribute("vector", vector);
                    RequestDispatcher dispath
                            = request.getRequestDispatcher("EditUser.jsp");
                    //run
                    dispath.forward(request, response);
                } else { // update data
                    int id = Integer.parseInt(request.getParameter("UserId"));
                    String mail = request.getParameter("Email");
                    String pass = request.getParameter("Password");
                    String roleid = request.getParameter("RoleId");
                    String fname = request.getParameter("FullName");
                    String genid = request.getParameter("GenderId");
                    String mb = request.getParameter("Mobile");
                    boolean isActive = request.getParameter("isActive").equals("1");
                    Customer cus = new Customer(id, mail, pass, roleid, fname, genid, mb, isActive);
                    dao.updateUser(cus);
                    response.sendRedirect("CustomerController?service=listAll");
                }
            }
            if (service.equals("view")) {
                int id = Integer.parseInt(request.getParameter("id"));
                //get product with id
                Vector<Customer> vector
                        = dao.getAll("select u.UserId , u.Email, u.Password,r.RoleName as Role,u.FullName,g.GenderName as Gender,u.Mobile,u.IsActive from [User] u\n"
                        + "join [Gender] g on u.GenderId = g.GenderId \n"
                        + "join [Role] r on r.RoleId = u.RoleId where UserId=" + id);
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "User");

                RequestDispatcher dispath
                        = request.getRequestDispatcher("viewUser.jsp");
                //run
                dispath.forward(request, response);
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