<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector,app.entity.Customer,java.sql.ResultSet"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit User</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <%
        Vector<Customer> vector = (Vector<Customer>) request.getAttribute("vector");
        if (vector != null && !vector.isEmpty()) {
            Customer cus = vector.get(0);
    %>
    <div class="container mt-4">
        <h2 class="mb-3">Edit User (ID: <%= cus.getUserId() %>)</h2>

        <form action="CustomerController" method="post">
            <input type="hidden" name="service" value="update">

            </div>
            <div class="form-group">
                <label for="UserId">UserId:</label>
                <input type="text" class="form-control" id="UserId" name="UserId" value="<%= cus.getUserId() %>" readonly>
            </div>

            <div class="form-group">
                <label for="Email">Email:</label>
                <input type="email" class="form-control" id="Email" name="Email" value="<%= cus.getEmail() %>" readonly>
            </div>
            <div class="form-group">
                <label for="Password">Password:</label>
                <input type="password" class="form-control" id="Password" name="Password" value="<%= cus.getPassword() %>" readonly>
            </div>
            <div class="form-group">
                <label for="FullName">Full Name:</label>
                <input type="text" class="form-control" id="FullName" name="FullName" value="<%= cus.getFullName() %>" readonly>
            </div>
            <div class="form-group">
                <label for="Mobile">Mobile:</label>
                <input type="tel" class="form-control" id="Mobile" name="Mobile" value="<%= cus.getMobile() %>" readonly>
            </div>

            <div class="form-group">
                <label for="RoleId">Role ID:</label>
                <select id="RoleId" name="RoleId" class="form-control" required>
                    <option value="1" <%= cus.getRoleId().equals("1") ? "selected" : "" %>>Guest</option>
                    <option value="2" <%= cus.getRoleId().equals("2") ? "selected" : "" %>>Customer</option>
                    <option value="3" <%= cus.getRoleId().equals("3") ? "selected" : "" %>>Marketing</option>
                    <option value="4" <%= cus.getRoleId().equals("4") ? "selected" : "" %>>Sale</option>
                    <option value="5" <%= cus.getRoleId().equals("5") ? "selected" : "" %>>Expert</option>
                    <option value="6" <%= cus.getRoleId().equals("6") ? "selected" : "" %>>Admin</option>
                </select>
            </div>

            <div class="form-group">
                <label for="GenderId">Gender ID:</label>
                <select id="GenderId" name="GenderId" class="form-control" readonly>
                    <option value="1" <%= cus.getGenderId().equals("1") ? "selected" : "" %>>Male</option>
                    <option value="2" <%= cus.getGenderId().equals("2") ? "selected" : "" %>>Female</option>
                </select>
            </div>
            <div class="form-group">
                <label>Is Active:</label>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="isActive" id="isActive" value="1" <%= cus.isIsActive() ? "checked" : "" %> required>
                    <label class="form-check-label" for="isActive">Yes</label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="isActive" id="isnotActive" value="0" <%= !cus.isIsActive() ? "checked" : "" %> required>
                    <label class="form-check-label" for="isnotActive">No</label>
                </div>
            </div>
                    <button type="submit" name="submit" class="btn btn-primary">Update User</button>
            <button type="reset" class="btn btn-secondary">Reset</button>
        </form>
    </div>
    <%
        } else {
            out.println("<p>Không tìm thấy người dùng.</p>");
        }
    %>
</body>
</html>