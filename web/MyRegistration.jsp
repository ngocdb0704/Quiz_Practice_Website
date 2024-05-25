<%-- 
    Document   : My Registration
    Created on : 12 thg 5, 2024, 16:59:06
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector"%>
<%@page import="app.entity.Registration"%>
<%@page import="app.entity.Subject"%>
<%@page import="app.utils.FormatData"%>
<%@page import="app.dal.DAOPackage"%>
<%@page import="app.entity.Package"%>



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
            <%
                DAOPackage daoPackage = new DAOPackage();
                FormatData dataFormatter = new FormatData();
                String registDate, validF, validT;
                String placeHolder ="";
                String disableButton = "";
                int i=1;
                String value= (String) request.getAttribute("value");
                if(value == null) value = "";
                if(value.equals("")) placeHolder = "Search Subject";
            %>
            <div class="row">
                <aside class="col-3">
                    <form class="siderbar"  action="RegistrationController" method="post">
                        <div class="mb-3 mt-5">
                            <div class="row card-body container justify-content-center" 
                                 id="inputContainer">
                                <input class="col-8" type="text" 
                                       placeholder="<%=placeHolder%>" 
                                       value="<%=value%>" name="search">
                                <button class="col-3" type="submit" 
                                        name="submit" value="submit">
                                    <i class="bi bi-search"></i>
                                </button>
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="subjectCategory">Filter Subject:</label>
                            <%
                                Vector<Subject> vecSub = (Vector<Subject>) request.getAttribute("select"); 
                            %>
                            <select class="col-10 form-select" name="subjectCategory" 
                                    id="subjectCategory" 
                                    onchange="sendRedirect(this, '<%=value%>')">
                                <%
                                        //show all elements in vector
                                        for(Subject sub:vecSub){
                                %>
                                <option value="<%=sub.getSubjectId()%>">
                                    <%=sub.getSubjectCategory()%>
                                </option>
                                <%}%>
                            </select>
                        </div>
                    </form>
                </aside>
                <div class="col-9">
                    <h1>List of Registration</h1>
                    <%
                        Vector<Registration> registrationVector = (Vector<Registration>) request.getAttribute("data");
                        for(Registration regist:registrationVector){
                        registDate = dataFormatter.dateFormat(regist.getRegistrationTime());
                        validF = dataFormatter.dateFormat(regist.getValidFrom());
                        validT= dataFormatter.dateFormat(regist.getValidTo());
                        if(regist.getStatus().equals("Submitted")) disableButton = "";
                        else disableButton = "disabled";
                    %>
                    <!-- Change cards' appearance -->
                    <div class="card mb-3">
                        <div class="row g-0">
                            <div class="col-md-4">
                                <img src="<%=regist.getSubjectImg()%>"
                                     class="img-fluid rounded-start" 
                                     width="300" height="300">
                            </div>
                            <div class="col-md-6">
                                <div class="card-body">
                                    <h5 class="card-title"><%=regist.getSubjectName()%></h5>
                                    <h6>Status: <%=regist.getStatus()%></h6>
                                    <ul class="list-group list-group-flush">
                                        <li class="list-group-item">
                                            <span>Registered: <%=regist.getPackageName()%></span>
                                            <span>on <%=registDate%></span>
                                        </li>
                                        <li class="list-group-item">
                                            <span>Valid from <%=validF%></span>
                                            <span class="ml-2">to <%=validT%></span>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div class="col-md-2 mt-md-5">
                                <h5><%=regist.getTotalCost()%>$</h5>
                                <!-- Button trigger modal -->
                                <!-- Remove onclick, if status is not Submitted then disabled -->
                                <button type="button" class="btn btn-warning"
                                        data-bs-toggle="modal" 
                                        data-bs-target=".modal<%=regist.getRegistrationId()%>"
                                        <%=disableButton%>>
                                    Edit
                                </button>
                                <!-- Modal -->
                                <div class="modal fade modal<%=regist.getRegistrationId()%>" tabindex="-1" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="exampleModalLabel">Subject Register</h5>
                                            </div>
                                            <form action="RegistrationController" method="post">
                                                <div class="modal-body">
                                                    <input type="hidden" name="service" value="update">
                                                    <input type="hidden" name="registId" value="<%=regist.getRegistrationId()%>">
                                                    <label for="SubjectTitle">Subject Title:</label><br>
                                                    <input class="form-text" type="text" id="SubjectTitle" 
                                                           name="sname" 
                                                           value="<%=regist.getSubjectName()%>" readonly><br>
                                                    <label for="packageList">Choose a package: </label> <br>
                                                    <%
                                                        Vector<Package> subjectPackage = daoPackage.getSubjectPackage(regist.getSubjectName());
                                                        Package selectedPackage = daoPackage.getByPackageNameSubjectName(regist.getPackageName(),regist.getSubjectName());
                                                    %>
                                                    <select class="form-select" name="packName"
                                                            id="packageList">
                                                        <option value="<%=selectedPackage.getPackageId()%>">
                                                            <%=selectedPackage.getPackageName()%> for only <%=selectedPackage.getSalePrice()%>$
                                                        </option>
                                                        <%
                                                                //show all elements in vector
                                                                for(Package pack:subjectPackage){
                                                                    if(pack.getPackageId() != selectedPackage.getPackageId()){
                                                        %>
                                                        <option value="<%=pack.getPackageName()%>">
                                                            <%=pack.getPackageName()%> for only <%=pack.getSalePrice()%>$
                                                        </option>
                                                        <%
                                                            }
                                                                }%>
                                                    </select> <br>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                    <input type="submit" class="btn btn-primary" value="Save Changes">
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div> 
                                <!-- Remove onclick, if status is not Submitted then disabled -->
                                <button class="btn btn-danger" 
                                        onclick="cancellation('<%=regist.getStatus()%>', '<%=regist.getRegistrationId()%>')"
                                        <%=disableButton%>>
                                    Cancel
                                </button>
                            </div>
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
