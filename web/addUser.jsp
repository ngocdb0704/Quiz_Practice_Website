<%-- 
    Document   : addUser
    Created on : May 30, 2024, 3:45:08 PM
    Author     : Administrator
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector,app.entity.Customer,app.model.DAOCustomer"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
     <title>Add User</title>
 <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
    <% 
        Customer cus = (Customer) request.getAttribute("customer"); // Get customer from request
        if (cus == null) {
            cus = new Customer(); // If no existing customer, create a new one for defaults
        }
    %>
    <form action="CustomerController" method="post">
        <div class="form-group">
            <label for="UserID">User ID:</label>
            <input type="text" id="UserId" name="UserId" required>
        </div>

        <div class="form-group">
            <label for="Email">Email:</label>
            <input type="email" id="Email" name="Email" required>
        </div>

        <div class="form-group">
            <label for="Password">Password:</label>
            <input type="password" id="Password" name="Password" required>
        </div>
        
        </div>
        <div class="form-group">
            <label for="FullName">Full Name:</label>
            <input type="text" id="FullName" name="FullName" required>
        </div>

        <div class="form-group">
            <label for="GenderId">Gender ID:</label>
            <select id="GenderId" name="GenderId">
                <option value="1">Male</option>
                <option value="2">Female</option>
                <option value="3">Other</option>
            </select>
        </div>
        <div class="form-group">
            <label for="RoleId">Role ID:</label>
            <select id="RoleId" name="RoleId">
                <option value="1">Guest</option>
                <option value="2">Customer</option>
                <option value="3">Marketing</option>
                <option value="4">Sale</option>
                <option value="5">Expert</option>
                <option value="6">Admin</option>
            </select>
        </div>
       <div class="form-group">
            <label for="Mobile">Mobile:</label>
            <input type="tel" id="Mobile" name="Mobile" required>
        </div>


        <div class="form-group radio-group">
            <label>Is Active:</label>
            <div>
                <input type="radio" id="isActive" name="isActive" value="1" checked>
                <label for="isActive">Yes</label>
            </div>
            <div>
                <input type="radio" id="isnotActive" name="isActive" value="0">
                <label for="isnotActive">No</label>
            </div>
        </div>

        <div class="form-group">
            <button type="submit" name="submit" value="addUser">Add User</button>
            <button type="reset">Clear</button>
        </div>
         <input type="hidden" name="service" value="addUser">
    </form>
</body>
</html>
