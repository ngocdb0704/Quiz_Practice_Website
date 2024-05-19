<%-- 
    Document   : viewUser
    Created on : May 19, 2024, 11:30:36 AM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector,app.entity.Customer,app.model.DAOCustomer"%>
<!DOCTYPE html>
<%
    Vector<Customer> vector
                    =(Vector<Customer>)request.getAttribute("data");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <a href="CustomerControllerURL?service=listAll">Home</a>
        <table border="1" >
        <% String titleTable=(String)request.getAttribute("titleTable");%>
        <caption><%=titleTable%></caption>
        <tr>
            <th>UserID</th>
            <th>Email</th>
            <th>Password</th>
            <th>Role</th>
            <th>FullName</th>
            <th>Gender</th>
            <th>Mobile</th>
            <th>Active Status</th>
        </tr>

        <% 
        for(Customer cus:vector){
        %>
        <tr>
            <td><%=cus.getUserID()%></td>
            <td><%=cus.getEmail()%></td>
            <td><%=cus.getPassword()%></td>
            <td><%=cus.getRole()%></td>
            <td><%=cus.getFullName()%></td>
            <td><%=cus.getGender()%></td>
            <td><%=cus.getMobile()%></td>
            <td><%=cus.isIsActive()%></td>
        </tr>
        <%}%>
    </table>
    </body>
</html>
