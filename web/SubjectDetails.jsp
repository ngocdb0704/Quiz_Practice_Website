<%-- 
    Document   : SubjectDetails
    Created on : Jun 10, 2024, 5:07:33 PM
    Author     : QuanNM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Subject details</title>
        <%@include file="/common/ImportBootstrap.jsp" %>
        <link rel="stylesheet" href="common/ExtendBody.css"/>
        <link rel="stylesheet" href="./public/css/SubjectDetails.css"/>
        <script src="./public/js/SubjectDetails.js"></script>
    </head>
    <body>
        <%@include file="/common/header.jsp" %>


        <main class="row">
            <div class="col-lg-2 col-md-3 col-sm-12 mx-lg-3 mx-md-0 bg-light border rounded-3">
                <h1>Sider</h1>
            </div>

            <div class="col py-3">
                <!--a href="#"><- Back</a-->
                <div id="subject-brief" style="" class="container rounded-3 p-5">
                    <div style="height: 250px"></div>

                    <img id='subject-thumbnail' src="${subjectDetails.getThumbnail()}" alt="alt"/>

                    <div id="info-div bg-white">
                        <h1>${subjectDetails.getSubjectName()}</h1>
                        <h2>${subjectDetails.getTagLine()}</h2>
                        <h2>${subjectDetails.getBriefInfo()}</h2>

                        <div class="row">
                            <div class="col-lg-3 col-md-6">
                                <div class="input-group">
                                    <button class="btn btn-primary">Register</button>
                                    <select class="form-select" id="inputGroupSelect04" aria-label="Example select with button addon">
                                        <option selected>Choose...</option>
                                        <option value="1">One</option>
                                        <option value="2">Two</option>
                                        <option value="3">Three</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>    

                <div class="container mt-5">
                    ${subjectDetails.getSubjectDescription()}
                </div>
            </div>
        </main>

        <%@include file="/common/footer.jsp" %>
    </body>
</html>
