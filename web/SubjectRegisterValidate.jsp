<%-- 
    Document   : SubjectRegisterValidate
    Created on : 7 thg 7, 2024, 23:21:54
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Subject Register</title>
        <%@include file="/common/ImportBootstrap.jsp" %>
        <link rel="stylesheet" href="common/ExtendBody.css"/>
    </head>
    <body>
        <%@include file="/common/header.jsp" %>
        <main>
            <div class="container">
                <h1>Subject Register</h1>
                <c:if test="${requestScope.isExist}">
                    <div class="row">
                        <div class="col">

                        </div>
                        <div class="col">
                            <div>
                                <img id="errorImage" 
                                     src="public/images/error01.png"
                                     alt="..."
                                     style="width: 30rem;">
                                <div>
                                    <h5 class="text-danger text-center">Registration Request Fail</h5>
                                    <p class="text-center">${requestScope.message}</p>
                                    <div class="container">
                                        <div class="row">
                                            <div class="col">

                                            </div>
                                            <div class="col">
                                                <a href="public/SubjectsList">
                                                    <button type="button" class="btn btn-primary">Back to Subjects List</button>
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col">

                        </div>
                    </div>
                </c:if>
                <c:if test="${!requestScope.isExist}">
                    <div class="row">
                        <div class="col">

                        </div>
                        <div class="col">
                            <div>
                                <img id="successImage" 
                                     src="public/images/thankImage.png" 
                                     alt="..."
                                     style="width: 30rem;">
                                <div>
                                    <h5 class="text-success text-center">Registration Requested Successfully</h5>
                                    <p class="text-center">${requestScope.message}</p>
                                    <div class="container">
                                        <div class="row">
                                            <div class="col">

                                            </div>
                                            <div class="col">
                                                <a href="public/SubjectsList">
                                                    <button type="button" class="btn btn-primary">Back to Subjects List</button>
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col">

                        </div>
                    </div>
                </c:if>
            </div>
        </main>
        <%@include file="/common/footer.jsp" %> 
    </body>
</html>
