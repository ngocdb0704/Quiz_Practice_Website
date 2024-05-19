<%-- 
    Document   : My Registration
    Created on : 12 thg 5, 2024, 16:59:06
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector"%>
<%@page import="app.entity.Registration"%>
<%@page import="app.entity.Subject"%>



<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Registration</title>
         <%@include file="/common/ImportBootstrap.jsp" %>
        <link rel="stylesheet" href="public/css/bootstrap/bootstrap.min.css"/> 
        <link rel="stylesheet" href="public/css/bootstrap/bootstrap-utilities.min.css"/>
        <link rel="stylesheet" href="public/css/MyRegistration.css"/>
        <script src="public/js/MyRegistration.js"></script>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-9">
                    <table class="table table-bordered table-hover">
                        <thead class="table-info">
                            <tr>
                                <th scope="col">ID</th>
                                <th scope="col">Subject</th>
                                <th scope="col">Registration Time</th>
                                <th scope="col">Package</th>
                                <th scope="col">Total cost</th>
                                <th scope="col">Status</th>
                                <th scope="col">Valid From</th>
                                <th scope="col">Valid To</th>
                                <th scope="col"></th>
                                <th scope="col"></th>
                            </tr>
                        </thead>
                        <%
                            String placeHolder ="";
                            String value= (String) request.getAttribute("value");
                            if(value == null) value = "";
                            if(value.equals("")) placeHolder = "Search Subject";
                            Vector<Registration> registrationVector = (Vector<Registration>) request.getAttribute("data");
                            for(Registration regist:registrationVector){
            
                        %>
                        <tbody>
                            <tr>
                                <th scope="row"><%=regist.getRegistrationId()%></th>
                                <td><%=regist.getSubjectName()%></td>
                                <td><%=regist.getRegistrationTime()%></td>
                                <td><%=regist.getPackageName()%></td>
                                <td><%=regist.getTotalCost()%></td>
                                <td><%=regist.getStatus()%></td>
                                <td><%=regist.getValidFrom()%></td>
                                <td><%=regist.getValidTo()%></td>
                                <td><a href="RegistrationController?service=edit"><i class="bi bi-pencil"></i></a></td>
                                <td><button onclick="myFunction('<%=regist.getRegistrationId()%>')"><i class="bi bi-trash3"></i></button></td>
                            </tr>
                            <%}%>
                        </tbody>
                    </table>
                </div>
                <div class="container col-3 sider">
                    <form  action="RegistrationController" method="post">
                        <div class="row container justify-content-center" id="inputContainer">
                            <input class="col-8" type="text" placeholder="<%=placeHolder%>" value="<%=value%>" name="search">
                            <button class="col-3" type="submit" name="submit" value="submit">
                                <i class="bi bi-search"></i>
                            </button>
                        </div>
                        <div class="row container justify-content-center">
                            <label for="subjectCategory">Filter Subject:</label>
                            <%
                                Vector<Subject> vecSub = (Vector<Subject>) request.getAttribute("select"); 

                            %>
                            <select class="col-10" name="subjectCategory" id="subjectCategory" onchange="sendRedirect(this, '<%=value%>')">
                                <%
                                        for(Subject sub:vecSub){
                                %>
                                <option value="<%=sub.getSubjectId()%>"><%=sub.getSubjectCategory()%></option>
                                <%}%>
                            </select>
                        </div>
                    </form>
                    <a class="row justify-content-center" href="">Contact us</a>
                </div>
            </div>
        </div>
    </body>
</html>
