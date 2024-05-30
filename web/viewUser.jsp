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
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <a href="CustomerController?service=listAll ">Home</a>
        <table class="table" >
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
            <td><%=cus.getUserId()%></td>
            <td><%=cus.getEmail()%></td>
            <td><%=cus.getPassword()%></td>
            <td><%=cus.getRoleId()%></td>
            <td><%=cus.getFullName()%></td>
            <td><%=cus.getGenderId()%></td>
            <td><%=cus.getMobile()%></td>
            <td><%=cus.isIsActive()%></td>
        </tr>
        <%}%>
    </table>
    </body>
</html>
