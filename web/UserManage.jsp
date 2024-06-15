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
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<html>
    <head>
        <title>Customer Management</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"> 
        <link href="css/style.css" rel="stylesheet" type="text/css"/> 
    </head>
    <body>

        <div class="container-xl"> 
            <header>
                <h1>Customer Management</h1>
                <a href="admin_index.jsp" class="btn btn-primary">Home</a>
                <a href="addUser.jsp" class="btn btn-success">New User</a>
            </header>
            <section class="filters">
                <div>
                    <div class="dropdown">
                        <button class="dropbtn">Sort By</button>
                        <div class="dropdown-content">
                            <a href="CustomerController?service=sortbyID">Sort by ID</a>
                            <a href="CustomerController?service=sortbyName">Sort by FullName</a>
                            <a href="CustomerController?service=sortbyMail">Sort by Email</a>
                            <a href="CustomerController?service=sortbyPhone">Sort by Mobile</a>
                            <a href="CustomerController?service=sortbyGen">Sort by Gender</a>
                            <a href="CustomerController?service=sortbyRole">Sort by Role</a>
                            <a href="CustomerController?service=sortbyStatus">Sort by Status</a>
                        </div>
                    </div>
                    <div class="dropdown">
                        <button class="dropbtn">Filtered By</button>
                        <div class="dropdown-content">
                            <a href="CustomerController?service=activefilter">Status - Active</a>
                            <a href="CustomerController?service=notactivefilter">Status - NotActive</a>
                            <a href="CustomerController?service=malefilter">Gender - Male</a>
                            <a href="CustomerController?service=femalefilter">Gender - Female</a>
                            <a href="CustomerController?service=guestfilter">Role - Guest</a>
                            <a href="CustomerController?service=cusfilter">Role - Customer</a>
                            <a href="CustomerController?service=mktfilter">Role - Marketing</a>
                            <a href="CustomerController?service=salefilter">Role - Sale</a>
                            <a href="CustomerController?service=expertfilter">Role - Expert</a>
                            <a href="CustomerController?service=admfilter">Role - Admin</a>
                        </div>
                    </div>
            </section>

            <section class="search-bar">
                <form action="CustomerController?service=searchByname" method="post">
                    <div class="input-group input-group-sm">
                        <div class="input-group-prepend"> 
                            <span class="input-group-text"><i class="bi bi-search"></i></span>
                        </div>
                        <input name="input" type="text" class="form-control" placeholder="Search by fullname">
                        <button type="submit" class="btn btn-primary">Search</button>
                    </div>

                </form>
                <form action="CustomerController?service=searchByemail" method="post">
                    <div class="input-group input-group-sm">
                        <div class="input-group-prepend"> 
                            <span class="input-group-text"><i class="bi bi-search"></i></span>
                        </div>
                        <input name="input2" type="text" class="form-control" placeholder="Search by mail">
                        <button type="submit" class="btn btn-primary">Search</button>
                    </div>
                </form>
                <form action="CustomerController?service=searchByphone" method="post">
                    <div class="input-group input-group-sm">
                        <div class="input-group-prepend"> 
                            <span class="input-group-text"><i class="bi bi-search"></i></span>
                        </div>
                        <input name="input3" type="text" class="form-control" placeholder="Search by phone">
                        <button type="submit" class="btn btn-primary">Search</button>
                    </div>
                </form>
            </section>

            <main>
                <table class="table">
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
                        <th>View Details</th>
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
                        <td><%=(cus.isIsActive() == true ? "Active" : "Not Active")%></td>
                        <td><a href="CustomerController?service=update&id=<%=cus.getUserId()%>">Edit</a></td>
                        <td><a href="CustomerController?service=detailsList&id=<%=cus.getUserId()%>">View Details</a></td>
                    </tr>
                    <%}%>
                </table>
            </main>

        </div> 

    </body>
</html>

<%}%>
