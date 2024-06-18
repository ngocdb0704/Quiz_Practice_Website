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
    <body onload="parse()">
        <%@include file="/common/header.jsp" %>


        <main class="row">
            <div class="col-lg-2 col-md-3 col-sm-12 mx-lg-3 mx-md-0 bg-light border rounded-3">
                <h4 class="pt-3">Search for subject:</h4>

                <div id="subject-search" class="input-group">
                    <input type="text" class="form-control">
                    <span class="input-group-text">
                        <i class="bi bi-search"></i>
                    </span>
                </div>

                <div class="card pt-3 mt-5 w-100">
                    <div class="card-header py-0 bg-white">
                        <h4 class="">Category:</h4>
                    </div>
                    <ul class="pt-1 pb-3 list-group list-group-flush">
                        <ul>
                            <li>A</li>
                            <ul>
                                <li>B</li>
                                <ul>
                                    <li>C</li>
                                </ul>
                            </ul>
                        </ul>
                    </ul>
                </div>

                <div class="card pt-3 mt-5 w-100">
                    <div class="card-header py-0 bg-white">
                        <h4 class="">Tags:</h4>
                    </div>
                    <ul class="pt-1 pb-3 list-group list-group-flush">
                        <ul>
                            <li>A</li>
                            <li>B</li>
                            <li>C</li>
                        </ul>
                    </ul>
                </div>

            </div>

            <div class="col py-3">
                <!--a href="#"><- Back</a
                
                -->
                <div id="subject-brief" style="" class="container rounded-3 p-5">
                    <div style="height: 250px"></div>

                    <img id='subject-thumbnail' src="${subjectDetails.getThumbnail()}" alt="alt"/>

                    <div id="info-div bg-white">
                        <h1>${subjectDetails.getSubjectName()}</h1>
                        <h2>${subjectDetails.getTagLine()}</h2>
                        <h2>${subjectDetails.getBriefInfo()}</h2>

                        <div class="container-fluid">
                            <c:if test="${not empty lowestPackage}">
                                <button class="btn btn-primary">Register</button>
                                <c:if test="${lowestPackage.getListPrice() != lowestPackage.getSalePrice()}">
                                    <span class="ms-3"><del>${lowestPackage.getListPrice() * 1000} vnd</del></span>
                                </c:if>

                                <span class="ms-3">${lowestPackage.getSalePrice() * 1000} vnd</span>

                                <c:if test="${lowestPackage.getListPrice() > lowestPackage.getSalePrice()}">
                                    <span class="badge rounded-pill text-bg-danger">-${(1 - (lowestPackage.getSalePrice() / lowestPackage.getListPrice())) * 100 }%</span>
                                </c:if>
                            </c:if>
                            <c:if test="${empty lowestPackage}">
                                <button style="cursor: default" class="btn btn-secondary btn-disabled">Register 
                                    <div class="w3tooltip"><i class="bi bi-question-circle"></i>
                                        <span class="w3tooltiptext">This subject does not currently have any packages available, please come back later.</span>
                                    </div>
                                </button>
                            </c:if>
                        </div>
                    </div>
                </div>    

                <div id="subject-description" class="container mt-5">
                    ${subjectDetails.getSubjectDescription()}
                </div>

            </div>
        </main>

        <%@include file="/common/footer.jsp" %>
    </body>
</html>
