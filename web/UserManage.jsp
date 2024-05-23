<%-- 
    Document   : CustomerManage
    Created on : Mar 7, 2024, 9:50:55 AM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector,app.entity.Customer,app.model.DAOCustomer"%>
<%
    Vector<Customer> vector
                    =(Vector<Customer>)request.getAttribute("data");
if(vector == null ){%>
<div></div>
<%} else { %>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!------ Include the above in your HEAD tag ---------->
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<style>
    table {
    margin: 0 auto;  /* Centers the table horizontally */
    width: 80%;      /* Adjust the width as needed */
}
.dropdown {
    position: relative;
    display: inline-block; /* or block if you want full width */
}

.dropbtn {
    background-color: #f1f1f1; /* Match your current background */
    color: blue; /* Match your link color */
    padding: 10px;
    font-size: 16px;
    border: none;
    cursor: pointer;
}

.dropdown-content {
    display: none;
    position: absolute;
    background-color: #f1f1f1;
    min-width: 160px;
    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
    z-index: 1;
}

.dropdown-content a {
    color: black;
    padding: 12px 16px;
    text-decoration: none;
    display: block;
}

/* Show the dropdown menu on hover */
.dropdown:hover .dropdown-content {
    display: block;
}
</style>
<div>
<a href="CustomerControllerURL?service=listAll">Home</a>
<div>
<form method="get">
    <a href="insertCustomer.html">New User</a>
</form>
    </div>
</div>



<div>
    <div class="dropdown">
    <button class="dropbtn">Sort By</button>
    <div class="dropdown-content">
        <a href="CustomerController?service=sortbyID">Sort by ID</a>
        <a href="CustomerController?service=sortbyName">Sort by FullName</a>
        <a href="CustomerController?service=sortbyMail">Sort by Email</a>
        <a href="CustomerController?service=sortbyPhone">Sort by Mobile</a>
    </div>
</div>
    <div class="dropdown">
        <button class="dropbtn">Filtered By</button>
        <div class="dropdown-content">
          <a href="CustomerControllerURL?service=activefilter">Filtered by Status - Active</a>
            <a href="CustomerControllerURL?service=notactivefilter">Filtered by Status - NotActive</a>
            <a href="CustomerControllerURL?service=malefilter">Filtered by Gender - Male</a>
           <a href="CustomerControllerURL?service=userfilter">Filtered by Role - User</a>
    </div>
        </div>

    <form action="CustomerControllerURL?service=searchByname" method="post">
        <div class="input-group input-group-sm">
            <input  name="input" type="text" class="form-control" aria-label="Small" aria-describedby="inputGroup-sizing-sm" placeholder="Search by fullname">
            <button type="submit" class="btn btn-secondary btn-number">
                Search
            </button>
        </div>
    </form>
    <form action="CustomerControllerURL?service=searchByemail" method="post">
        <div class="input-group input-group-sm">
            <input  name="input2" type="text" class="form-control" aria-label="Small" aria-describedby="inputGroup-sizing-sm" placeholder="Search by mail">
            <button type="submit" class="btn btn-secondary btn-number">
                Search
            </button>
        </div>
    </form>
    <form action="CustomerControllerURL?service=searchByphone" method="post">
        <div class="input-group input-group-sm">
            <input  name="input3" type="text" class="form-control" aria-label="Small" aria-describedby="inputGroup-sizing-sm" placeholder="Search by phone">
            <button type="submit" class="btn btn-secondary btn-number">
                Search
            </button>
        </div>
    </form>
</div>
<div>
    <table border="1" class="table">
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
            <th>Edit</th>
            <th>View</th>
        </tr>

        <% 
        for(Customer cus:vector){
        %>
        <tr>
            <td><%=cus.getUserId()%></td>
            <td><%=cus.getEmail()%></td>
            <td><%=cus.getPassword()%></td>
            <td><%=cus.getRole()%></td>
            <td><%=cus.getFullName()%></td>
            <td><%=cus.getGender()%></td>
            <td><%=cus.getMobile()%></td>
            <td><%=cus.isIsActive()%></td>
            <td><a href="CustomerControllerURL?service=update&id=<%=cus.getUserId()%>">Edit</a></td>
            <td><a href="CustomerControllerURL?service=view&id=<%=cus.getUserId()%>">View</a></td>
        </tr>
        <%}%>
    </table>
</div>
<%}%>
