

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector,app.entity.Customer,app.model.DAOCustomer"%>
<!DOCTYPE html>
<%
    Vector<Customer> vector
                    =(Vector<Customer>)request.getAttribute("data");
if(vector == null ){%>
<div></div>
<%} else { %>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
<link href="css/style.css" rel="stylesheet" type="text/css"/>
    <body>
        <div>
            <a href="admin_index.jsp">Home</a>
    <table border="1" class="table">
        <% String titleTable=(String)request.getAttribute("titleTable");%>
        <caption><%=titleTable%></caption>
        <tr>
            <th>UserID</th>
            <th>Email</th>
            <th>Avatar</th>
            <th>Role</th>
            <th>FullName</th>
            <th>Gender</th>
            <th>Mobile</th>
            <th>Active Status</th>
            <th>Edit</th>
            <th>View</th>
        </tr>

        <% 
        for(Customer cus:vector){
        %>
        <tr>
            <td><%=cus.getUserId()%></td>
            <td><%=cus.getEmail()%></td>
            <td><img src="UserProfile?service=showPic&uId =<%=cus.getUserId()%>" width="100" height="100"></td>
            <td><%=cus.getRoleId()%></td>
            <td><%=cus.getFullName()%></td>
            <td><%=cus.getGenderId()%></td>
            <td><%=cus.getMobile()%></td>
            <td><%=cus.isIsActive()%></td>
            <td><a href="CustomerController?service=update&id=<%=cus.getUserId()%>">Edit</a></td>
            <td><a href="CustomerController?service=view&id=<%=cus.getUserId()%>">View</a></td>
        </tr>
        <%}%>
    </table>
</div>
    </body>
    <%}%>
