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
        <script src="public/js/MyRegistration.js"></script>
        <link rel="stylesheet" href="public/css/bootstrap/MyRegistration.css"/>
        <link rel="stylesheet" href="common/ExtendBody.css"/>
    </head>
    <body>
        <%@include file="/common/header.jsp" %>
        <main class="container">
            <h1>List of Registration</h1>
            <%
                String placeHolder ="";
                int i=1;
                String value= (String) request.getAttribute("value");
                if(value == null) value = "";
                if(value.equals("")) placeHolder = "Search Subject";
            %>
            <div class="row">
                <aside class="col-3">
                    <form class="siderbar"  action="RegistrationController" method="post">
                        <div class="mb-3">
                            <div class="row card-body container justify-content-center" id="inputContainer">
                                <input class="col-8" type="text" placeholder="<%=placeHolder%>" value="<%=value%>" name="search">
                                <button class="col-3" type="submit" name="submit" value="submit">
                                    <i class="bi bi-search"></i>
                                </button>
                            </div>
                        </div>
                        <div class="mb-3">
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
                </aside>
                <div class="col-9">
                    <%
                        Vector<Registration> registrationVector = (Vector<Registration>) request.getAttribute("data");
                        for(Registration regist:registrationVector){
                    %>
                    <div class="card col-3 regist">
                        <img src="<%=regist.getSubjectImg()%>" width="100" height="200"
                             class="card-img-top" alt="alt"/>
                        <div class="card-body">
                            <h5><%=regist.getSubjectName()%></h5>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item">Status: <%=regist.getStatus()%></li>
                                <li class="list-group-item">Valid From: <%=regist.getValidFrom()%></li>
                                <li class="list-group-item">Valid To: <%=regist.getValidTo()%></li>
                                <li class="list-group-item more <%=i%>">Registration Time: <%=regist.getRegistrationTime()%></li>
                                <li class="list-group-item more <%=i%>">Package: <%=regist.getPackageName()%></li>
                                <li class="list-group-item more <%=i%>">Total Cost: <%=regist.getTotalCost()%></li>
                                <li class="list-group-item">
                                    <table class="table">
                                        <tr>
                                            <th>
                                                <button onclick="showMore('<%=i%>', 'mySMB<%=i%>')" class="btn btn-primary mySMB<%=i%>">
                                                    More
                                                </button>
                                            </th>
                                            <th>
                                                <button class="btn btn-warning"
                                                        onclick="edit('<%=regist.getStatus()%>')">
                                                    <i class="bi bi-pencil"></i>
                                                </button>
                                            </th>
                                            <th></th>
                                            <th></th>
                                            <th>
                                                <button class="btn btn-danger" 
                                                        onclick="cancellation('<%=regist.getStatus()%>', '<%=regist.getRegistrationId()%>')">
                                                    <i class="bi bi-trash3"></i>
                                                </button>
                                            </th>
                                        </tr>
                                    </table>                                    
                                </li>
                            </ul>

                        </div>
                    </div>
                    <%
                        i++;
                        }
                    %>
                </div>
            </div>
        </main>
        <%@include file="/common/footer.jsp" %>
    </body>
</html>
