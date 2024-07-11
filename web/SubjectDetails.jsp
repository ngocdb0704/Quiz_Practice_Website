<%-- 
    Document   : SubjectDetails
    Created on : Jun 10, 2024, 5:07:33 PM
    Author     : QuanNM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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


        <main class="row mw-100">
            <div class="col-lg-2 col-md-3 col-sm-12 mx-lg-3 mx-md-0 bg-light border rounded-3">
                <h4 class="pt-3">Search for subject:</h4>

                <form action="public/SubjectsList#searchKey" id="subject-search" class="input-group">
                    <input type="text" name="key" class="form-control">
                    <span class="input-group-text" onclick="document.getElementById('subject-search').submit();">
                        <i class="bi bi-search"></i>
                    </span>
                </form>

                <div class="card pt-3 mt-5 w-100">
                    <div class="card-header py-0 bg-white">
                        <h4 class="">Category</h4>
                    </div>
                    <ul id="subject-category" class="pt-1 pb-3 list-group list-group-flush">
                        <%int tier = 1;%>
                        <c:forEach var="category" items="${subjectDetailsCategoryLine}">
                            <ul>
                                <li><a href="./public/SubjectsList?idTier<%=tier++%>=${category.getCateId()}#searchKey">${category.getCateName()}</a></li>
                                </c:forEach>
                                <c:forEach var="category" items="${subjectDetailsCategoryLine}">
                            </ul>
                        </c:forEach>
                    </ul>
                </div>

                <div id="subject-tags" class="card pt-3 mt-5 w-100">
                    <div class="card-header py-0 bg-white">
                        <h4 class="">Tags</h4>
                    </div>
                    <ul class="pt-1 pb-3 list-group list-group-flush">
                        <ul class="ps-2"><!--ul is here to force all tags into a single line-->
                            <c:if test="${SubjectTagNew}">
                                <span><a class="badge text-bg-success" href="./public/SubjectsList#carouselNewSubject">New</a></span>
                            </c:if>
                            <c:if test="${SubjectTagBigSale}">
                                <span><a class="badge text-bg-warning" href="./public/SubjectsList#carouselSaleSubject">Big Sale</a></span>
                            </c:if>
                            <c:if test="${SubjectTagFeatured}">
                                <span><a class="badge text-bg-primary" href="./public/SubjectsList#carouselFeaturedSubject">Featured</a></span>
                            </c:if>
                        </ul>  
                        <c:if test="${!SubjectTagNew && !SubjectTagFeatured && !SubjectTagBigSale}"><p class="ps-2">This subject has no tags</p></c:if>
                        </ul>
                    </div>

                    <h4 class="mt-5">Featured subjects</h4>
                <c:forEach var="subject" items="${dataFeaturedSubject}" begin="0" end="5">
                    <a class="featured-subject" href="SubjectDetails?subjectId=${subject.getSubjectId()}">
                        <div class="my-2 ps-1 border border-1 rounded-1">
                            <p class="mb-1">${subject.getSubjectName()}</p>
                            <small>${subject.getTagLine()}</small>
                        </div>
                    </a>
                </c:forEach>

                <a class="d-inline-block link-primary mt-5" href="ContactUs.jsp" target="_blank" rel="noopener noreferrer">Contact Us</a>

            </div>

            <div class="col py-3">
                <!--a href="#"><- Back</a
                
                -->
                <div id="subject-brief" style="" class="container rounded-3 p-5">
                    <div style="height: 250px"></div>

                    <img id='subject-thumbnail' src="public/thumbnails/${subjectDetails.getThumbnail()}" alt="alt"/>

                    <div id="info-div bg-white">
                        <h1>${subjectDetails.getSubjectName()}</h1>
                        <h3>${subjectDetails.getTagLine()}</h3>
                        <p>${subjectDetails.getBriefInfo()}</p>

                        <div class="container-fluid">
                            <c:if test="${not empty lowestPackage}">
                                <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target=".modalRegister">Register</button>
                                <c:if test="${lowestPackage.getListPrice() != lowestPackage.getSalePrice()}">
                                    <span class="price-tag ms-3"><del>${lowestPackage.getListPrice() * 1000} vnd</del></span>
                                </c:if>

                                <span class="price-tag ms-3">${lowestPackage.getSalePrice() * 1000} vnd</span>

                                <c:if test="${lowestPackage.getListPrice() > lowestPackage.getSalePrice()}">
                                    <span class="badge rounded-pill text-bg-danger">-<fmt:formatNumber value="${(1 - (lowestPackage.getSalePrice() / lowestPackage.getListPrice())) * 100 }" minFractionDigits="0" maxFractionDigits="0"/>%</span>
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

<!-- Modal Register -->
            <div class="modal fade modalRegister"
                 tabindex="-1">
                <div class="modal-dialog modal-dialog-centered ${sessionScope.userEmail == null ? "modal-lg": ""}" 
                     role="document">
                    <div class="modal-content">
                        <div class="modal-header text-bg-primary">
                            <h4>Subject Register</h4>
                            <button type="button" 
                                    class="btn-close" 
                                    data-bs-dismiss="modal" 
                                    aria-label="Close">
                            </button>
                        </div>
                        <div class="modal-body">
                            <c:if test="${sessionScope.userEmail != null}">
                                <c:import url="SubjectRegisterUser.jsp">
                                    <c:param name="service" value="register"/>
                                    <c:param name="subjectId" value="${subjectDetails.getSubjectId()}"/>
                                    <c:param name="thumbnail" value="${subjectDetails.getThumbnail()}"/>
                                    <c:param name="subjectName" value="${subjectDetails.getSubjectName()}"/>
                                    <c:param name="tagLine" value="${subjectDetails.getTagLine()}"/>
                                </c:import>
                            </c:if>
                            <c:if test="${sessionScope.userEmail == null}">
                                <c:import url="SubjectRegisterGuest.jsp">
                                    <c:param name="service" value="freshRegister"/>
                                    <c:param name="subjectId" value="${subjectDetails.getSubjectId()}"/>
                                    <c:param name="thumbnail" value="${subjectDetails.getThumbnail()}"/>
                                    <c:param name="subjectName" value="${subjectDetails.getSubjectName()}"/>
                                    <c:param name="tagLine" value="${subjectDetails.getTagLine()}"/>
                                </c:import>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
