<%-- 
    Document   : SubjectList
    Created on : 10 thg 6, 2024, 13:45:48
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Subjects List</title>
        <%@include file="/common/ImportBootstrap.jsp" %>
        <link rel="stylesheet" href="common/ExtendBody.css"/>
        <link rel="stylesheet" href="css/SubjectsList.css"/>
        <script src="public/js/SubjectsList.js"></script>
        <!-- jquery -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <!-- CSS -->
        <link rel="stylesheet" href="https://unpkg.com/flickity@2/dist/flickity.min.css">
        <!-- JavaScript -->
        <script src="https://unpkg.com/flickity@2/dist/flickity.pkgd.min.js"></script>
    </head>
    <body onload="scrollAtLoad()">
        <%@include file="/common/header.jsp" %>
        <%@include file="/common/sidebar.jsp" %>
        <div class="loader" id="load">
            <h3 class="text-center mt-5">Loading Subjects List</h3>
            <div class="loader-inner">
                <div class="loader-line-wrap">
                    <div class="loader-line"></div>
                </div>
                <div class="loader-line-wrap">
                    <div class="loader-line"></div>
                </div>
                <div class="loader-line-wrap">
                    <div class="loader-line"></div>
                </div>
                <div class="loader-line-wrap">
                    <div class="loader-line"></div>
                </div>
                <div class="loader-line-wrap">
                    <div class="loader-line"></div>
                </div>
            </div>
        </div>
        <main class="container">
            <c:set var="goTo" value="${requestScope.goTo}"/>
            <input type="hidden" value="${goTo}" id="goToPos">
            <c:set var="numPerCarousel" value="${requestScope.numPerCarousel}"/>
            <c:set var="listNewSubject" value="${requestScope.dataNewSubject}"/>
            <c:set var="listSaleSubject" value="${requestScope.dataSaleSubject}"/>
            <c:set var="listFeaturedSubject" value="${requestScope.dataFeaturedSubject}"/>
            <c:set var="allSubjectsList" value="${requestScope.dataAllSubject}"/>
            <c:set var="numOfAllSubjects" value="${requestScope.numOfAllSubjects}"/>
            <c:set var="numOfCarouselNew" value="${requestScope.numOfCarouselNew}"/>
            <c:set var="numOfCarouselSale" value="${requestScope.numOfCarouselSale}"/>
            <c:set var="numOfCarouselFeatured" value="${requestScope.numOfCarouselFeatured}"/>
            <c:set var="checkNewSubject" value="${requestScope.listOfIdNew}"/>
            <c:set var="checkSaleSubject" value="${requestScope.listOfIdSale}"/>
            <c:set var="checkFeatSubject" value="${requestScope.listOfIdFeat}"/>
            <c:set var="cat" value="${requestScope.list}"/>
            <c:set var="map" value="${requestScope.map}"/>
            <c:set var="check" value="${requestScope.check}"/>
            <c:set var="levels" value="${requestScope.levels}"/>
            <c:set var="checkLevel" value="${requestScope.checkLevel}"/>
            <c:set var="key" value="${requestScope.key}"/>
            <c:set var="order" value="${requestScope.order}"/>
            <c:set var="listRegistered" value="${requestScope.listOfIdRegist}"/>
            <c:set var="sponsor" value="${requestScope.sponsor}"/>
            <div>
                <h1>
                    Subjects List
                </h1>
                <nav class="nav nav-pills" style="background-color: #e3f2fd;">
                    <a class="nav-link active" href="public/SubjectsList?service=individual">For Individual</a>
                    <a class="nav-link" href="public/SubjectsList?service=business">For Organization</a>
                </nav>
            </div>
            <section>
                <h3>New Subjects</h3>
                <c:if test="${numOfCarouselNew == 0}">
                    <div>
                        <h3>There isn't any new subject at this time!</h3>
                    </div>
                </c:if>
                <c:if test="${numOfCarouselNew > 0}">
                    <div class="carousel" data-flickity='{ "groupCells": true }'>
                        <c:forEach begin="0" end="${numOfCarouselNew}" var="indexCarNewItem">
                            <div class="carousel-cell card">
                                <img src="public/thumbnails/${listNewSubject.get(indexCarNewItem).getThumbnail()}" 
                                     class="card-img-top img-thumbnail" 
                                     alt="..."
                                     style="width: 24rem; height: 14rem">
                                <div class="card-body">
                                    <h5 class="card-title">
                                        <a id="subjectLink" href="SubjectDetails?subjectId=${listNewSubject.get(indexCarNewItem).getSubjectId()}">
                                            ${listNewSubject.get(indexCarNewItem).getSubjectName()}
                                        </a>
                                    </h5>
                                    <c:if test="${listNewSubject.get(indexCarNewItem).getSubjectName().length()<=38}">
                                        <br>
                                    </c:if>
                                    <p>
                                        ${listNewSubject.get(indexCarNewItem).getTagLine()}
                                    <p>
                                    <p>
                                        <c:if test="${checkNewSubject.contains(listNewSubject.get(indexCarNewItem).getSubjectName())}">
                                            <button type="button" 
                                                    onclick="scrollToNew()"
                                                    class="badge text-bg-success">
                                                New
                                            </button>
                                        </c:if>
                                        <c:if test="${checkSaleSubject.contains(listNewSubject.get(indexCarNewItem).getSubjectName())}">
                                            <button type="button" 
                                                    onclick="scrollToBigSale()"
                                                    class="badge text-bg-warning">
                                                Big Sale
                                            </button>
                                        </c:if>
                                        <c:if test="${checkFeatSubject.contains(listNewSubject.get(indexCarNewItem).getSubjectName())}">
                                            <button type="button" 
                                                    onclick="scrollToFeatured()"
                                                    class="badge text-bg-primary">
                                                Featured
                                            </button>
                                        </c:if>
                                    </p>
                                    <p>
                                        <c:choose>
                                            <c:when test="${listRegistered.contains(listNewSubject.get(indexCarNewItem).getSubjectName())}">
                                                <a class="badge text-bg-dark" 
                                                   href="user/MyRegistrations?key=${listNewSubject.get(indexCarNewItem).getSubjectName()}"
                                                   style="text-decoration: none;">
                                                    In My Registrations
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <br>
                                            </c:otherwise>
                                        </c:choose>
                                    </p>
                                    <p>
                                        Package: ${listNewSubject.get(indexCarNewItem).getLowestPackageName()}
                                    <p>
                                    <p> 
                                        <span>
                                            Sale price:
                                        </span>
                                        <span class="badge rounded-pill text-bg-light">
                                            ${Integer.valueOf(listNewSubject.get(indexCarNewItem).getPackageSalePrice()*1000)} vnd
                                        </span>
                                        <span class="fw-light badge rounded-pill text-bg-light" 
                                              style=" text-decoration-line: line-through;"> 
                                            ${Integer.valueOf(listNewSubject.get(indexCarNewItem).getPackageListPrice()*1000)} vnd
                                        </span>
                                        <span class="badge rounded-pill text-bg-danger">
                                            - ${Integer.valueOf(
                                                (1-(listNewSubject.get(indexCarNewItem).getPackageSalePrice()/listNewSubject.get(indexCarNewItem).getPackageListPrice()))*100)}
                                            %
                                        </span>
                                    </p>
                                    <p>
                                        <c:choose>
                                            <c:when test="${sponsor.containsKey(listNewSubject.get(indexCarNewItem).getSubjectId())}">
                                                <button type="button" class="btn btn-dark">
                                                    Enrol Me
                                                </button>
                                            </c:when>
                                            <c:otherwise>
                                                <button type="button" class="btn btn-info" 
                                                        data-bs-toggle="modal" 
                                                        data-bs-target=".modalRegisterNew${listNewSubject.get(indexCarNewItem).getSubjectId()}"
                                                        ${listRegistered.contains(listNewSubject.get(indexCarNewItem).getSubjectName())?"disabled":""}>
                                                    Register
                                                </button>
                                            </c:otherwise>
                                        </c:choose>
                                        <br>
                                        <span>
                                            <c:choose>
                                                <c:when test="${sponsor.containsKey(listNewSubject.get(indexCarNewItem).getSubjectId())}">
                                                    @${sponsor.get(listNewSubject.get(indexCarNewItem).getSubjectId())} sponsored
                                                </c:when>
                                                <c:otherwise>
                                                    <br>
                                                </c:otherwise>
                                            </c:choose>
                                        </span>
                                    </p>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
            </section>
            <br>
            <section>
                <h3>Big Sale</h3>
                <c:if test="${numOfCarouselSale == 0}">
                    <div>
                        <h3>There isn't any sale off subject at this time!</h3>
                    </div>
                </c:if>
                <c:if test="${numOfCarouselSale > 0}">
                    <div class="carousel" data-flickity='{ "groupCells": true }'>
                        <c:forEach begin="0" end="${numOfCarouselSale}" var="indexCarSaleItem">
                            <div class="card">
                                <img src="public/thumbnails/${listSaleSubject.get(indexCarSaleItem).getThumbnail()}" 
                                     class="card-img-top img-thumbnail" 
                                     alt="..."
                                     style="width: 24rem; height: 14rem">
                                <div class="card-body">
                                    <h5 class="card-title">
                                        <a id="subjectLink" href="SubjectDetails?subjectId=${listSaleSubject.get(indexCarSaleItem).getSubjectId()}">
                                            ${listSaleSubject.get(indexCarSaleItem).getSubjectName()}
                                        </a>
                                    </h5>
                                    <c:if test="${listSaleSubject.get(indexCarSaleItem).getSubjectName().length()<=38}">
                                        <br>
                                    </c:if>
                                    <p>
                                        ${listSaleSubject.get(indexCarSaleItem).getTagLine()}
                                    <p>
                                    <p>
                                        <c:if test="${checkNewSubject.contains(listSaleSubject.get(indexCarSaleItem).getSubjectName())}">
                                            <button type="button" 
                                                    onclick="scrollToNew()"
                                                    class="badge text-bg-success">
                                                New
                                            </button>
                                        </c:if>
                                        <c:if test="${checkSaleSubject.contains(listSaleSubject.get(indexCarSaleItem).getSubjectName())}">
                                            <button type="button" 
                                                    onclick="scrollToBigSale()"
                                                    class="badge text-bg-warning">
                                                Big Sale
                                            </button>
                                        </c:if>
                                        <c:if test="${checkFeatSubject.contains(listSaleSubject.get(indexCarSaleItem).getSubjectName())}">
                                            <button type="button" 
                                                    onclick="scrollToFeatured()"
                                                    class="badge text-bg-primary">
                                                Featured
                                            </button>
                                        </c:if>
                                    </p>
                                    <p>
                                        <c:choose>
                                            <c:when test="${listRegistered.contains(listSaleSubject.get(indexCarSaleItem).getSubjectName())}">
                                                <a class="badge text-bg-dark" 
                                                   href="user/MyRegistrations?key=${listSaleSubject.get(indexCarSaleItem).getSubjectName()}"
                                                   style="text-decoration: none;">
                                                    In My Registrations
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <br>
                                            </c:otherwise>
                                        </c:choose>
                                    </p>
                                    <p>
                                        Package: ${listSaleSubject.get(indexCarSaleItem).getLowestPackageName()}
                                    <p>
                                    <p> 
                                        <span>
                                            Sale price:
                                        </span>
                                        <span class="badge rounded-pill text-bg-light">
                                            ${Integer.valueOf(listSaleSubject.get(indexCarSaleItem).getPackageSalePrice()*1000)} vnd
                                        </span>
                                        <span class="fw-light badge rounded-pill text-bg-light" 
                                              style=" text-decoration-line: line-through;"> 
                                            ${Integer.valueOf(listSaleSubject.get(indexCarSaleItem).getPackageListPrice()*1000)} vnd
                                        </span>
                                        <span class="badge rounded-pill text-bg-danger">
                                            - ${Integer.valueOf(
                                                (1-(listSaleSubject.get(indexCarSaleItem).getPackageSalePrice()/listSaleSubject.get(indexCarSaleItem).getPackageListPrice()))*100)}
                                            %
                                        </span>
                                    </p>
                                    <p>
                                        <c:choose>
                                            <c:when test="${sponsor.containsKey(listSaleSubject.get(indexCarSaleItem).getSubjectId())}">
                                                <button type="button" class="btn btn-dark">
                                                    Enrol Me
                                                </button>
                                            </c:when>
                                            <c:otherwise>
                                                <button type="button" class="btn btn-info" 
                                                        data-bs-toggle="modal" 
                                                        data-bs-target=".modalRegisterSale${listSaleSubject.get(indexCarSaleItem).getSubjectId()}"
                                                        ${listRegistered.contains(listSaleSubject.get(indexCarSaleItem).getSubjectName())?"disabled":""}>
                                                    Register
                                                </button>
                                            </c:otherwise>
                                        </c:choose>
                                        <br>
                                        <span>
                                            <c:choose>
                                                <c:when test="${sponsor.containsKey(listSaleSubject.get(indexCarSaleItem).getSubjectId())}">
                                                    @${sponsor.get(listSaleSubject.get(indexCarSaleItem).getSubjectId())} sponsored
                                                </c:when>
                                                <c:otherwise>
                                                    <br>
                                                </c:otherwise>
                                            </c:choose>
                                        </span>
                                    </p>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
            </section>
            <br>
            <section>
                <h3>Featured Subjects</h3>
                <c:if test="${numOfCarouselFeatured == 0}">
                    <div>
                        <h3>There isn't any featured subject at this time!</h3>
                    </div>
                </c:if>
                <c:if test="${numOfCarouselFeatured > 0}">
                    <div class="carousel" data-flickity='{ "groupCells": true }'>
                        <c:forEach begin="0" end="${numOfCarouselFeatured}" var="indexCarFeatItem">
                            <div class="card">
                                <img src="public/thumbnails/${listFeaturedSubject.get(indexCarFeatItem).getThumbnail()}" 
                                     class="card-img-top img-thumbnail" 
                                     alt="..."
                                     style="width: 24rem; height: 14rem">
                                <div class="card-body">
                                    <h5 class="card-title">
                                        <a id="subjectLink" href="SubjectDetails?subjectId=${listFeaturedSubject.get(indexCarFeatItem).getSubjectId()}">
                                            ${listFeaturedSubject.get(indexCarFeatItem).getSubjectName()}
                                        </a>
                                    </h5>
                                    <c:if test="${listFeaturedSubject.get(indexCarFeatItem).getSubjectName().length()<=38}">
                                        <br>
                                    </c:if>
                                    <p>
                                        ${listFeaturedSubject.get(indexCarFeatItem).getTagLine()}
                                    <p>
                                    <p>
                                        <c:if test="${checkNewSubject.contains(listFeaturedSubject.get(indexCarFeatItem).getSubjectName())}">
                                            <button type="button" 
                                                    onclick="scrollToNew()"
                                                    class="badge text-bg-success">
                                                New
                                            </button>
                                        </c:if>
                                        <c:if test="${checkSaleSubject.contains(listFeaturedSubject.get(indexCarFeatItem).getSubjectName())}">
                                            <button type="button" 
                                                    onclick="scrollToBigSale()"
                                                    class="badge text-bg-warning">
                                                Big Sale
                                            </button>
                                        </c:if>
                                        <c:if test="${checkFeatSubject.contains(listFeaturedSubject.get(indexCarFeatItem).getSubjectName())}">
                                            <button type="button" 
                                                    onclick="scrollToFeatured()"
                                                    class="badge text-bg-primary">
                                                Featured
                                            </button>
                                        </c:if>
                                    </p>
                                    <p>
                                        <c:choose>
                                            <c:when test="${listRegistered.contains(listFeaturedSubject.get(indexCarFeatItem).getSubjectName())}">
                                                <a class="badge text-bg-dark" 
                                                   href="user/MyRegistrations?key=${listFeaturedSubject.get(indexCarFeatItem).getSubjectName()}"
                                                   style="text-decoration: none;">
                                                    In My Registrations
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <br>
                                            </c:otherwise>
                                        </c:choose>
                                    </p>
                                    <p>
                                        Package: ${listFeaturedSubject.get(indexCarFeatItem).getLowestPackageName()}
                                    <p>
                                    <p> 
                                        <span>
                                            Sale price:
                                        </span>
                                        <span class="badge rounded-pill text-bg-light">
                                            ${Integer.valueOf(listFeaturedSubject.get(indexCarFeatItem).getPackageSalePrice()*1000)} vnd
                                        </span>
                                        <span class="fw-light badge rounded-pill text-bg-light" 
                                              style=" text-decoration-line: line-through;"> 
                                            ${Integer.valueOf(listFeaturedSubject.get(indexCarFeatItem).getPackageListPrice()*1000)} vnd
                                        </span>
                                        <span class="badge rounded-pill text-bg-danger">
                                            - ${Integer.valueOf(
                                                (1-(listFeaturedSubject.get(indexCarFeatItem).getPackageSalePrice()/listFeaturedSubject.get(indexCarFeatItem).getPackageListPrice()))*100)}
                                            %
                                        </span>
                                    </p>
                                    <p>
                                        <c:choose>
                                            <c:when test="${sponsor.containsKey(listFeaturedSubject.get(indexCarFeatItem).getSubjectId())}">
                                                <button type="button" class="btn btn-dark">
                                                    Enrol Me
                                                </button>
                                            </c:when>
                                            <c:otherwise>
                                                <button type="button" class="btn btn-info" 
                                                        data-bs-toggle="modal" 
                                                        data-bs-target=".modalRegisterCarou${listFeaturedSubject.get(indexCarFeatItem).getSubjectId()}"
                                                        ${listRegistered.contains(listFeaturedSubject.get(indexCarFeatItem).getSubjectName())?"disabled":""}>
                                                    Register
                                                </button>
                                            </c:otherwise>
                                        </c:choose>
                                        <br>
                                        <span>
                                            <c:choose>
                                                <c:when test="${sponsor.containsKey(listFeaturedSubject.get(indexCarFeatItem).getSubjectId())}">
                                                    @${sponsor.get(listFeaturedSubject.get(indexCarFeatItem).getSubjectId())} sponsored
                                                </c:when>
                                                <c:otherwise>
                                                    <br>
                                                </c:otherwise>
                                            </c:choose>
                                        </span>
                                    </p>
                                </div>
                            </div>
                        </c:forEach>
                    </c:if>
            </section>
            <br>
            <section>
                <c:set var="posToGo" value="${2130}"/>
                <h1>
                    Subjects List
                </h1>
                <div class="row">
                    <aside class="col-4 sbar">
                        <div class="row mb-3">
                            <form action="public/SubjectsList" method="post">
                                <div class="mb-3">
                                    <div class="row card-body container justify-content-center">
                                        <label for="searchKey">Subject Search Box</label>
                                        <input class="col-8" 
                                               id="searchKey" 
                                               type="text" value="${key}" 
                                               name="key" 
                                               placeholder="Search Subject by Title">
                                        <input type="hidden" value="${posToGo}" name="goToPos">
                                        <input type="hidden" value="individual" name="service">
                                        <button class="col-3" onclick="this.form.submit()">
                                            <i class="bi bi-search"></i>
                                        </button>
                                    </div>
                                </div>
                                <div>
                                    <h4>
                                        Filter Subjects By:
                                    </h4>
                                </div>
                                <div class="accordion " id="accordionFlushSiderFilter">
                                    <div class="accordion-item">
                                        <h2 class="accordion-header">
                                            <button class="accordion-button collapsed" 
                                                    type="button" data-bs-toggle="collapse" 
                                                    data-bs-target="#flush-collapseZero" 
                                                    aria-expanded="false" 
                                                    aria-controls="flush-collapseZero">
                                                Hide filters
                                            </button>
                                        </h2>
                                        <div id="flush-collapseZero" 
                                             class="accordion-collapse collapse" 
                                             data-bs-parent="#accordionFlushSiderFilter">
                                        </div>
                                    </div>
                                    <div class="accordion-item">
                                        <h2 class="accordion-header">
                                            <button class="accordion-button collapsed" 
                                                    type="button" 
                                                    data-bs-toggle="collapse" 
                                                    data-bs-target="#flush-collapseOne" 
                                                    aria-expanded="false" 
                                                    aria-controls="flush-collapseOne">
                                                Subject Categories
                                            </button>
                                        </h2>
                                        <div id="flush-collapseOne" 
                                             class="accordion-collapse collapse show" 
                                             data-bs-parent="#accordionFlushSiderFilter">
                                            <div class="accordion-body">
                                                <ul class="list-group list-group-flush">
                                                    <li class="list-group-item">
                                                        <!-- subject category tree -->
                                                        <!-- tree level 0 -->
                                                        <!-- get all node -->
                                                        <c:forEach begin="0" end="${cat.size()-1}" var="i">
                                                            <ul class="list-group list-group-flush">
                                                                <!-- tree level 1 or parent tier 1 -->
                                                                <c:if test="${cat.get(i).getCateParentId()==0}">
                                                                    <li class="list-group-item">
                                                                        <input class="form-check-input"
                                                                               type="checkbox" name="idTier1" 
                                                                               value="${cat.get(i).getCateId()}"
                                                                               ${check[i]?"checked":""}
                                                                               onclick="this.form.submit()"/>
                                                                        <span class="text-danger">${cat.get(i).getCateName()}</span>
                                                                        <c:set var="parentTier1" value="${cat.get(i).getCateId()}"/>
                                                                        <!-- get all node -->
                                                                        <c:forEach begin="0" end="${cat.size()-1}" var="ii">
                                                                            <ul class="list-group list-group-flush">
                                                                                <!-- tree level 2 or parent tier 2 -->
                                                                                <c:if test="${cat.get(ii).getCateParentId()==parentTier1}">
                                                                                    <li class="list-group-item">
                                                                                        <input class="form-check-input" 
                                                                                               type="checkbox" name="idTier2" 
                                                                                               value="${cat.get(ii).getCateId()}"
                                                                                               ${check[ii]?"checked":""}
                                                                                               onclick="this.form.submit()"/>
                                                                                        <span class="text-info-emphasis">${cat.get(ii).getCateName()}</span>
                                                                                        <c:set var="parentTier2" value="${cat.get(ii).getCateId()}"/>
                                                                                        <!-- get all node -->
                                                                                        <c:forEach begin="0" end="${cat.size()-1}" var="iii">
                                                                                            <ul class="list-group list-group-flush">
                                                                                                <!-- tree level 3 or parent tier 3 -->
                                                                                                <c:if test="${cat.get(iii).getCateParentId()==parentTier2}">
                                                                                                    <li class="list-group-item">
                                                                                                        <input class="form-check-input"
                                                                                                               type="checkbox" name="idTier3" 
                                                                                                               value="${cat.get(iii).getCateId()}"
                                                                                                               ${check[iii]?"checked":""}
                                                                                                               onclick="this.form.submit()"/>
                                                                                                        <span class="badge text-bg-light">${cat.get(iii).getCateName()}</span>
                                                                                                    </li>
                                                                                                </c:if>
                                                                                            </ul>
                                                                                        </c:forEach>
                                                                                    </li>
                                                                                </c:if>
                                                                            </ul>
                                                                        </c:forEach>
                                                                    </li>
                                                                </c:if>
                                                            </ul>
                                                        </c:forEach>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="accordion-item">
                                        <h2 class="accordion-header">
                                            <button class="accordion-button collapsed" 
                                                    type="button" data-bs-toggle="collapse" 
                                                    data-bs-target="#flush-collapseTwo" 
                                                    aria-expanded="false" 
                                                    aria-controls="flush-collapseTwo"
                                                    onclick="scrollToTopCategory()">
                                                Levels
                                            </button>
                                        </h2>
                                        <div id="flush-collapseTwo" 
                                             class="accordion-collapse collapse" 
                                             data-bs-parent="#accordionFlushSiderFilter">
                                            <div class="accordion-body">
                                                <ul class="list-group list-group-flush">
                                                    <c:forEach begin="0" end="${levels.size()-1}" var="index">
                                                        <li class="list-group-item">
                                                            <input class="form-check-input" type="checkbox" name="level"
                                                                   value="${levels.get(index).getCateId()}"
                                                                   ${checkLevel[index]?"checked":""}
                                                                   onclick="this.form.submit()"
                                                                   > <span class="badge text-bg-light">
                                                                <!-- get all node -->
                                                                <!-- set name to node -->
                                                                ${levels.get(index).getCateName()}
                                                            </span>
                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <h4 class="mt-3">
                                    Featured Subjects:
                                </h4>
                                <div class="accordion" id="featuredAccordion">
                                    <div class="accordion-item">
                                        <h2 class="accordion-header">
                                            <button class="accordion-button collapsed" 
                                                    type="button" data-bs-toggle="collapse" 
                                                    data-bs-target="#flush-collapseThree" 
                                                    aria-expanded="false" 
                                                    aria-controls="flush-collapseThree">
                                                Featured Subjects
                                            </button>
                                        </h2>
                                        <div id="flush-collapseThree" 
                                             class="accordion-collapse collapse show" 
                                             data-bs-parent="#featuredAccordion">
                                            <div class="accordion-body">
                                                <ul>
                                                    <c:forEach begin="0" end="${listFeaturedSubject.size()-1}" var="iFeat">
                                                        <li class="w3tooltip" style="width: 350px">
                                                            <div class="my-2 ps-1 border border-1 rounded-1">
                                                                <div>
                                                                    <span class="atLink">${listFeaturedSubject.get(iFeat).getSubjectName()}</span> 
                                                                    <div class="w3tooltiptext card" style="width: 18rem;">
                                                                        <img src="public/thumbnails/${listFeaturedSubject.get(iFeat).getThumbnail()}" 
                                                                             class="card-img-top img-thumbnail" alt="..."
                                                                             style="height: 10rem;">
                                                                        <div class="card-body">
                                                                            <h5 class="card-title">
                                                                                <a class="btn btn-link"
                                                                                   href="SubjectDetails?subjectId=${listFeaturedSubject.get(iFeat).getSubjectId()}">
                                                                                    ${listFeaturedSubject.get(iFeat).getSubjectName()} 
                                                                                </a>
                                                                            </h5>
                                                                            <p class="card-text">
                                                                                ${listFeaturedSubject.get(iFeat).getTagLine()}
                                                                                <br>
                                                                                Package: ${listFeaturedSubject.get(iFeat).getLowestPackageName()}
                                                                                <br>
                                                                            </p>
                                                                            <p>
                                                                                <c:if test="${checkNewSubject.contains(listFeaturedSubject.get(iFeat).getSubjectName())}">
                                                                                    <button type="button" 
                                                                                            onclick="scrollToNew()"
                                                                                            class="badge text-bg-success">
                                                                                        New
                                                                                    </button>
                                                                                </c:if>
                                                                                <c:if test="${checkSaleSubject.contains(listFeaturedSubject.get(iFeat).getSubjectName())}">
                                                                                    <button type="button" 
                                                                                            onclick="scrollToBigSale()"
                                                                                            class="badge text-bg-warning">
                                                                                        Big Sale
                                                                                    </button>
                                                                                </c:if>
                                                                                <c:if test="${checkFeatSubject.contains(listFeaturedSubject.get(iFeat).getSubjectName())}">
                                                                                    <button type="button" 
                                                                                            onclick="scrollToFeatured()"
                                                                                            class="badge text-bg-primary">
                                                                                        Featured
                                                                                    </button>
                                                                                </c:if>
                                                                            </p>
                                                                            <p>
                                                                                <c:if test="${listRegistered.contains(listFeaturedSubject.get(iFeat).getSubjectName())}">
                                                                                    <a class="badge text-bg-dark" 
                                                                                       href="user/MyRegistrations?key=${listFeaturedSubject.get(iFeat).getSubjectName()}"
                                                                                       style="text-decoration: none;">
                                                                                        In My Registrations
                                                                                    </a>
                                                                                </c:if>
                                                                            </p>
                                                                            <p> 
                                                                                <span>
                                                                                    Sale price:
                                                                                </span>
                                                                                <span class="badge rounded-pill text-bg-light">
                                                                                    ${Integer.valueOf(listFeaturedSubject.get(iFeat).getPackageSalePrice()*1000)} vnd
                                                                                </span>
                                                                                <span class="fw-light badge rounded-pill text-bg-light" 
                                                                                      style=" text-decoration-line: line-through;"> 
                                                                                    ${Integer.valueOf(listFeaturedSubject.get(iFeat).getPackageListPrice()*1000)} vnd
                                                                                </span>
                                                                                <span class="badge rounded-pill text-bg-danger">
                                                                                    - ${Integer.valueOf(
                                                                                        (1-(listFeaturedSubject.get(iFeat).getPackageSalePrice()/listFeaturedSubject.get(iFeat).getPackageListPrice()))*100)}
                                                                                    %
                                                                                </span>
                                                                            </p>
                                                                            <p>
                                                                                <c:choose>
                                                                                    <c:when test="${sponsor.containsKey(listFeaturedSubject.get(iFeat).getSubjectId())}">
                                                                                        <button type="button" class="btn btn-dark">
                                                                                            Enrol Me
                                                                                        </button>
                                                                                    </c:when>
                                                                                    <c:otherwise>
                                                                                        <button type="button" class="btn btn-info" 
                                                                                                data-bs-toggle="modal" 
                                                                                                data-bs-target=".modalRegisterFeat${listFeaturedSubject.get(iFeat).getSubjectId()}"
                                                                                                ${listRegistered.contains(listFeaturedSubject.get(iFeat).getSubjectName())?"disabled":""}>
                                                                                            Register
                                                                                        </button>
                                                                                    </c:otherwise>
                                                                                </c:choose>
                                                                                <br>
                                                                                <span>
                                                                                    <c:choose>
                                                                                        <c:when test="${sponsor.containsKey(listFeaturedSubject.get(iFeat).getSubjectId())}">
                                                                                            @${sponsor.get(listFeaturedSubject.get(iFeat).getSubjectId())} sponsored
                                                                                        </c:when>
                                                                                        <c:otherwise>
                                                                                            <br>
                                                                                        </c:otherwise>
                                                                                    </c:choose>
                                                                                </span>
                                                                            </p>
                                                                        </div>
                                                                    </div>
                                                                </div>  
                                                            </div>
                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="row mb-3">
                            <a class="link-primary mt-3"
                               href="ContactUs.jsp" target="_blank"
                               rel="noopener noreferrer">Contact Us</a>
                        </div>
                    </aside>
                    <div class="col-8 mt-3">
                        <c:set var="page" value="${requestScope.page}"/>
                        <c:set var="filter" value="${requestScope.sendFilter}"/>
                        <c:if test="${allSubjectsList.size()>1}">
                            <nav class="row">
                                <ul class="pagination col-8">
                                    <li class="page-item">
                                        <a class="page-link ${page==1?"disabled":""}" 
                                           href="public/SubjectsList?${filter}page=${page-1}&goToPos=${posToGo}&orderList=${order}&service=individual">Previous</a>
                                    </li>
                                    <!-- get all pages -->
                                    <c:if test="${numOfAllSubjects > 6}">
                                        <c:forEach begin="${1}" end="${2}" var="i">
                                            <li class="page-item">
                                                <a class="page-link ${i==page?"active":""}" 
                                                   href="public/SubjectsList?${filter}page=${i}&goToPos=${posToGo}&orderList=${order}&service=individual">${i}</a>
                                            </li>
                                        </c:forEach>
                                        <li class="page-item">
                                            <a class="page-link disabled">...</a>
                                        </li>
                                        <c:if test="${page > 2 && page < numOfAllSubjects-1}">
                                            <li class="page-item">
                                                <a class="page-link active" 
                                                   href="public/SubjectsList?${filter}page=${page}&goToPos=${posToGo}&orderList=${order}&service=individual">${page}</a>
                                            </li>
                                            <li class="page-item">
                                                <a class="page-link disabled">...</a>
                                            </li>
                                        </c:if>
                                        <c:forEach begin="${numOfAllSubjects-1}" end="${numOfAllSubjects}" var="i">
                                            <li class="page-item">
                                                <a class="page-link ${i==page?"active":""}" 
                                                   href="public/SubjectsList?${filter}page=${i}&goToPos=${posToGo}&orderList=${order}&service=individual">${i}</a>
                                            </li>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${numOfAllSubjects <= 6}">
                                        <c:forEach begin="${1}" end="${numOfAllSubjects}" var="i">
                                            <li class="page-item">
                                                <a class="page-link ${i==page?"active":""}" 
                                                   href="public/SubjectsList?${filter}page=${i}&goToPos=${posToGo}&orderList=${order}&service=individual">${i}</a>
                                            </li>
                                        </c:forEach>
                                    </c:if>        
                                    <li class="page-item">
                                        <a class="page-link ${page==numOfAllSubjects?"disabled":""}" 
                                           href="public/SubjectsList?${filter}page=${page+1}&goToPos=${posToGo}&orderList=${order}&service=individual">Next</a>
                                    </li>
                                </ul>
                                <div class="col-4">
                                    <h4>Sort by Updated Date: </h4>
                                    <ul class="pagination">
                                        <li class="page-item">
                                            <a class=" page-link ${order==1?"active":""}" 
                                               href="public/SubjectsList?${filter}page=${page}&goToPos=${posToGo}&orderList=1&service=individual">
                                                Latest
                                            </a>

                                        </li>
                                        <li class="page-item">
                                            <a class=" page-link ${order==0?"active":""}" 
                                               href="public/SubjectsList?${filter}page=${page}&goToPos=${posToGo}&orderList=0&service=individual">
                                                Oldest
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </nav>
                        </c:if>
                        <c:if test="${allSubjectsList.size()<1}">
                            <div>
                                <img src="https://www.kindpng.com/picc/m/71-714432_empty-list-vector-hd-png-download.png" 
                                     alt="alt"
                                     height="200"/>
                                <h3>
                                    It seems that we don't have what you are looking for
                                </h3>
                                <h3>
                                    We will update subjects list soon!
                                </h3>
                                <h3>
                                    <span>
                                        How about go back to full list ->
                                    </span>
                                    <span>
                                        <a class="btn btn-primary"
                                           href="public/SubjectsList?goToPos=2035&service=individual">All Subjects</a>
                                    </span>
                                </h3>
                            </div>
                        </c:if>
                        <ul class="list-group">
                            <!-- get all subjects that meet previous conditions: input key, filter -->
                            <c:forEach items="${allSubjectsList}" var="p">
                                <!-- Change cards' appearance -->
                                <li class="list-group-item list-group-item-info">
                                    <div class="card mb-3">
                                        <div class="row g-0">
                                            <div class="col-md-4">
                                                <img src="public/thumbnails/${p.getThumbnail()}"
                                                     class="img-thumbnail rounded-start" 
                                                     style="height: 14rem;">
                                            </div>
                                            <div class="col-md-5">
                                                <div class="card-body">
                                                    <h5 class="card-title">
                                                        <a id="subjectLink" href="SubjectDetails?subjectId=${p.getSubjectId()}">
                                                            ${p.getSubjectName()}
                                                        </a>
                                                    </h5>
                                                    <ul class="list-group list-group-flush">
                                                        <li class="list-group-item">
                                                            <div class="row">
                                                                <div class="col-10">
                                                                    <p>
                                                                        ${p.getTagLine()}
                                                                    </p>
                                                                    <p>
                                                                        <c:if test="${checkNewSubject.contains(p.getSubjectName())}">
                                                                            <button type="button" 
                                                                                    onclick="scrollToNew()"
                                                                                    class="badge text-bg-success">
                                                                                New
                                                                            </button>
                                                                        </c:if>
                                                                        <c:if test="${checkSaleSubject.contains(p.getSubjectName())}">
                                                                            <button type="button" 
                                                                                    onclick="scrollToBigSale()"
                                                                                    class="badge text-bg-warning">
                                                                                Big Sale
                                                                            </button>
                                                                        </c:if>
                                                                        <c:if test="${checkFeatSubject.contains(p.getSubjectName())}">
                                                                            <button type="button" 
                                                                                    onclick="scrollToFeatured()"
                                                                                    class="badge text-bg-primary">
                                                                                Featured
                                                                            </button>
                                                                        </c:if>
                                                                    </p>
                                                                    <p>
                                                                        <c:choose>
                                                                            <c:when test="${listRegistered.contains(p.getSubjectName())}">
                                                                                <a class="badge text-bg-dark" 
                                                                                   href="user/MyRegistrations?key=${p.getSubjectName()}"
                                                                                   style="text-decoration: none;">
                                                                                    In My Registrations
                                                                                </a>
                                                                            </c:when>
                                                                        </c:choose>
                                                                    </p>
                                                                    <p>
                                                                        Package: ${p.getLowestPackageName()}
                                                                    </p>
                                                                </div>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>
                                            <div class="col-md-2 mt-md-3">
                                                <h5>Sale Price:</h5>
                                                <h6>
                                                    <span class="fw-light badge rounded-pill text-bg-light" 
                                                          style=" text-decoration-line: line-through;"> 
                                                        ${Integer.valueOf(p.getPackageListPrice()*1000)} vnd
                                                    </span>
                                                    <span class="badge rounded-pill text-bg-danger">
                                                        - ${Integer.valueOf(
                                                            (1-(p.getPackageSalePrice()/p.getPackageListPrice()))*100)}
                                                        %
                                                    </span>
                                                </h6>
                                                <h5>
                                                    ${Integer.valueOf(p.getPackageSalePrice()*1000)} VND
                                                </h5>
                                                <diV class="row">
                                                    <div class="col-xl-9">
                                                        <!-- Button buy trigger modal -->
                                                        <c:choose>
                                                            <c:when test="${sponsor.containsKey(p.getSubjectId())}">
                                                                <button type="button" class="btn btn-dark">
                                                                    Enrol Me
                                                                </button>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <button type="button" class="btn btn-info" 
                                                                        data-bs-toggle="modal" 
                                                                        data-bs-target=".modalRegisterList${p.getSubjectId()}"
                                                                        ${listRegistered.contains(p.getSubjectName())?"disabled":""}>
                                                                    Register
                                                                </button>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                </diV>
                                                <div class="row">
                                                    <c:choose>
                                                        <c:when test="${sponsor.containsKey(p.getSubjectId())}">
                                                            @${sponsor.get(p.getSubjectId())} sponsored
                                                        </c:when>
                                                        <c:otherwise>
                                                            <br>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                        <c:if test="${allSubjectsList.size()>1}">
                            <nav>
                                <ul class="pagination col-8">
                                    <li class="page-item">
                                        <a class="page-link ${page==1?"disabled":""}" 
                                           href="public/SubjectsList?${filter}page=${page-1}&goToPos=${posToGo}&orderList=${order}&service=individual">Previous</a>
                                    </li>
                                    <!-- get all pages -->
                                    <c:if test="${numOfAllSubjects > 6}">
                                        <c:forEach begin="${1}" end="${2}" var="i">
                                            <li class="page-item">
                                                <a class="page-link ${i==page?"active":""}" 
                                                   href="public/SubjectsList?${filter}page=${i}&goToPos=${posToGo}&orderList=${order}&service=individual">${i}</a>
                                            </li>
                                        </c:forEach>
                                        <li class="page-item">
                                            <a class="page-link disabled">...</a>
                                        </li>
                                        <c:if test="${page > 2 && page < numOfAllSubjects-1}">
                                            <li class="page-item">
                                                <a class="page-link active" 
                                                   href="public/SubjectsList?${filter}page=${page}&goToPos=${posToGo}&orderList=${order}&service=individual">${page}</a>
                                            </li>
                                            <li class="page-item">
                                                <a class="page-link disabled">...</a>
                                            </li>
                                        </c:if>
                                        <c:forEach begin="${numOfAllSubjects-1}" end="${numOfAllSubjects}" var="i">
                                            <li class="page-item">
                                                <a class="page-link ${i==page?"active":""}" 
                                                   href="public/SubjectsList?${filter}page=${i}&goToPos=${posToGo}&orderList=${order}&service=individual">${i}</a>
                                            </li>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${numOfAllSubjects <= 6}">
                                        <c:forEach begin="${1}" end="${numOfAllSubjects}" var="i">
                                            <li class="page-item">
                                                <a class="page-link ${i==page?"active":""}" 
                                                   href="public/SubjectsList?${filter}page=${i}&goToPos=${posToGo}&orderList=${order}&service=individual">${i}</a>
                                            </li>
                                        </c:forEach>
                                    </c:if>        
                                    <li class="page-item">
                                        <a class="page-link ${page==numOfAllSubjects?"disabled":""}" 
                                           href="public/SubjectsList?${filter}page=${page+1}&goToPos=${posToGo}&orderList=${order}&service=individual">Next</a>
                                    </li>
                                </ul>
                            </nav>
                        </c:if>
                    </div>
                </div>
            </section>
        </main>
        <%@include file="/common/footer.jsp" %>
        <c:forEach begin="0" end="${numOfCarouselNew}" var="indexCarNewItem">
            <!-- Modal Register New Subjects -->
            <div class="modal modalRegisterNew${listNewSubject.get(indexCarNewItem).getSubjectId()}"
                 tabindex="-1">
                <div class="modal-dialog modal-dialog-centered ${sessionScope.userEmail == null ? "modal-lg": ""}" 
                     role="document">
                    <div class="modal-content">
                        <div class="modal-header text-bg-primary">
                            <h4>Subject Register</h4>
                            <button type="button" 
                                    class="btn-close" 
                                    data-bs-dismiss="modal" 
                                    aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <!-- It's Subject Register Popup baby - which costs me 13 working hrs -->
                            <!-- now it is 13.5 working hours because of an anoying bug -->
                            <!-- which is that the data isn't retrieved correctly  -->
                            <!-- This bad boy is currently applied for logged in  -->
                            <c:if test="${sessionScope.userEmail != null}">
                                <c:import url="SubjectRegisterUser.jsp">
                                    <c:param name="service" value="register"/>
                                    <c:param name="subjectId" value="${listNewSubject.get(indexCarNewItem).getSubjectId()}"/>
                                    <c:param name="thumbnail" value="${listNewSubject.get(indexCarNewItem).getThumbnail()}"/>
                                    <c:param name="subjectName" value="${listNewSubject.get(indexCarNewItem).getSubjectName()}"/>
                                    <c:param name="tagLine" value="${listNewSubject.get(indexCarNewItem).getTagLine()}"/>
                                </c:import>
                            </c:if>
                            <c:if test="${sessionScope.userEmail == null}">
                                <c:import url="SubjectRegisterGuest.jsp">
                                    <c:param name="service" value="freshRegister"/>
                                    <c:param name="subjectId" value="${listNewSubject.get(indexCarNewItem).getSubjectId()}"/>
                                    <c:param name="thumbnail" value="${listNewSubject.get(indexCarNewItem).getThumbnail()}"/>
                                    <c:param name="subjectName" value="${listNewSubject.get(indexCarNewItem).getSubjectName()}"/>
                                    <c:param name="tagLine" value="${listNewSubject.get(indexCarNewItem).getTagLine()}"/>
                                </c:import>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
        <c:forEach begin="0" end="${numOfCarouselSale}" var="indexCarSaleItem">
            <!-- Modal Register Big Sale Subjects -->
            <div class="modal modalRegisterSale${listSaleSubject.get(indexCarSaleItem).getSubjectId()}"
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
                            <!-- It's Subject Register Popup baby - which costs me 13 working hrs -->
                            <!-- now it is 13.5 working hours because of an anoying bug -->
                            <!-- which is that the data isn't retrieved correctly  -->
                            <!-- This bad boy is currently applied for logged in  -->
                            <c:if test="${sessionScope.userEmail != null}">
                                <c:import url="SubjectRegisterUser.jsp">
                                    <c:param name="service" value="register"/>
                                    <c:param name="subjectId" value="${listSaleSubject.get(indexCarSaleItem).getSubjectId()}"/>
                                    <c:param name="thumbnail" value="${listSaleSubject.get(indexCarSaleItem).getThumbnail()}"/>
                                    <c:param name="subjectName" value="${listSaleSubject.get(indexCarSaleItem).getSubjectName()}"/>
                                    <c:param name="tagLine" value="${listSaleSubject.get(indexCarSaleItem).getTagLine()}"/>
                                </c:import>
                            </c:if>
                            <c:if test="${sessionScope.userEmail == null}">
                                <c:import url="SubjectRegisterGuest.jsp">
                                    <c:param name="service" value="freshRegister"/>
                                    <c:param name="subjectId" value="${listSaleSubject.get(indexCarSaleItem).getSubjectId()}"/>
                                    <c:param name="thumbnail" value="${listSaleSubject.get(indexCarSaleItem).getThumbnail()}"/>
                                    <c:param name="subjectName" value="${listSaleSubject.get(indexCarSaleItem).getSubjectName()}"/>
                                    <c:param name="tagLine" value="${listSaleSubject.get(indexCarSaleItem).getTagLine()}"/>
                                </c:import>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
        <c:forEach begin="0" end="${numOfCarouselFeatured}" var="indexCarFeatItem">
            <!-- Modal Featured Register -->
            <div class="modal modalRegisterCarou${listFeaturedSubject.get(indexCarFeatItem).getSubjectId()} "
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
                            <!-- It's Subject Register Popup baby - which costs me 13 working hrs -->
                            <!-- now it is 13.5 working hours because of an anoying bug -->
                            <!-- which is that the data isn't retrieved correctly  -->
                            <!-- This bad boy is currently applied for logged in  -->
                            <c:if test="${sessionScope.userEmail != null}">
                                <c:import url="SubjectRegisterUser.jsp">
                                    <c:param name="service" value="register"/>
                                    <c:param name="subjectId" value="${listFeaturedSubject.get(indexCarFeatItem).getSubjectId()}"/>
                                    <c:param name="thumbnail" value="${listFeaturedSubject.get(indexCarFeatItem).getThumbnail()}"/>
                                    <c:param name="subjectName" value="${listFeaturedSubject.get(indexCarFeatItem).getSubjectName()}"/>
                                    <c:param name="tagLine" value="${listFeaturedSubject.get(indexCarFeatItem).getTagLine()}"/>
                                </c:import>
                            </c:if>
                            <c:if test="${sessionScope.userEmail == null}">
                                <c:import url="SubjectRegisterGuest.jsp">
                                    <c:param name="service" value="freshRegister"/>
                                    <c:param name="subjectId" value="${listFeaturedSubject.get(indexCarFeatItem).getSubjectId()}"/>
                                    <c:param name="thumbnail" value="${listFeaturedSubject.get(indexCarFeatItem).getThumbnail()}"/>
                                    <c:param name="subjectName" value="${listFeaturedSubject.get(indexCarFeatItem).getSubjectName()}"/>
                                    <c:param name="tagLine" value="${listFeaturedSubject.get(indexCarFeatItem).getTagLine()}"/>
                                </c:import>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
        <c:forEach begin="0" end="${listFeaturedSubject.size()-1}" var="iFeat">
            <!-- Modal Register Sider's Featured Subjects -->
            <div class="modal modalRegisterFeat${listFeaturedSubject.get(iFeat).getSubjectId()} "
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
                            <!-- It's Subject Register Popup baby - which costs me 13 working hrs -->
                            <!-- now it is 13.5 working hours because of an anoying bug -->
                            <!-- which is that the data isn't retrieved correctly  -->
                            <!-- This bad boy is currently applied for logged in  -->
                            <c:if test="${sessionScope.userEmail != null}">
                                <c:import url="SubjectRegisterUser.jsp">
                                    <c:param name="service" value="register"/>
                                    <c:param name="subjectId" value="${listFeaturedSubject.get(iFeat).getSubjectId()}"/>
                                    <c:param name="thumbnail" value="${listFeaturedSubject.get(iFeat).getThumbnail()}"/>
                                    <c:param name="subjectName" value="${listFeaturedSubject.get(iFeat).getSubjectName()}"/>
                                    <c:param name="tagLine" value="${listFeaturedSubject.get(iFeat).getTagLine()}"/>
                                </c:import>
                            </c:if>
                            <c:if test="${sessionScope.userEmail == null}">
                                <c:import url="SubjectRegisterGuest.jsp">
                                    <c:param name="service" value="freshRegister"/>
                                    <c:param name="subjectId" value="${listFeaturedSubject.get(iFeat).getSubjectId()}"/>
                                    <c:param name="thumbnail" value="${listFeaturedSubject.get(iFeat).getThumbnail()}"/>
                                    <c:param name="subjectName" value="${listFeaturedSubject.get(iFeat).getSubjectName()}"/>
                                    <c:param name="tagLine" value="${listFeaturedSubject.get(iFeat).getTagLine()}"/>
                                </c:import>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
        <c:forEach items="${allSubjectsList}" var="p">
            <!-- Modal Register Subjects in Subjects List -->
            <div class="modal modalRegisterList${p.getSubjectId()} "
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
                            <!-- It's Subject Register Popup baby - which costs me 13 working hrs -->
                            <!-- now it is 13.5 working hours because of an anoying bug -->
                            <!-- which is that the data isn't retrieved correctly  -->
                            <!-- This bad boy is currently applied for logged in  -->
                            <c:if test="${sessionScope.userEmail != null}">
                                <c:import url="SubjectRegisterUser.jsp">
                                    <c:param name="service" value="register"/>
                                    <c:param name="subjectId" value="${p.getSubjectId()}"/>
                                    <c:param name="thumbnail" value="${p.getThumbnail()}"/>
                                    <c:param name="subjectName" value="${p.getSubjectName()}"/>
                                    <c:param name="tagLine" value="${p.getTagLine()}"/>
                                </c:import>
                            </c:if>
                            <c:if test="${sessionScope.userEmail == null}">
                                <c:import url="SubjectRegisterGuest.jsp">
                                    <c:param name="service" value="freshRegister"/>
                                    <c:param name="subjectId" value="${p.getSubjectId()}"/>
                                    <c:param name="thumbnail" value="${p.getThumbnail()}"/>
                                    <c:param name="subjectName" value="${p.getSubjectName()}"/>
                                    <c:param name="tagLine" value="${p.getTagLine()}"/>
                                </c:import>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </body>
</html>
