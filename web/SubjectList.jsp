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
    </head>
    <body>
        <%@include file="/common/header.jsp" %>
        <%@include file="/common/sidebar.jsp" %>
        <main class="container">
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
            <h1>Subjects List</h1>
            <section>
                <h3>New Subjects</h3>
                <c:if test="${numOfCarouselNew == 0}">
                    <div>
                        <h3>There isn't any new subject at this time!</h3>
                    </div>
                </c:if>
                <c:if test="${numOfCarouselNew != 0}">
                    <div id="carouselNewSubject" 
                         class="carousel carousel-dark slide row" 
                         data-bs-ride="carousel"
                         style="border: solid 1px;
                         background-image: url(https://thumbs.dreamstime.com/z/beautiful-delicate-background-blossoming-light-pink-sakura-flowers-place-text-delicate-floral-design-realistic-145967566.jpg);">
                        <div class="col-2">
                            <button class="carousel-control-prev" 
                                    type="button" 
                                    data-bs-target="#carouselNewSubject" 
                                    data-bs-slide="prev">
                                <span class="carousel-control-prev-icon"
                                      aria-hidden="false"></span>
                                <span class="visually-hidden">Previous</span>
                            </button>
                        </div>
                        <div class="col-10 ml-5">
                            <div class="carousel-inner">
                                <c:forEach begin="0" end="${numOfCarouselNew-1}" var="indexCarNew">
                                    <div class="carousel-item ${indexCarNew==0?"active":""}">
                                        <div class="row">
                                            <c:set var="start" value="${indexCarNew*numPerCarousel}"/>
                                            <c:set var="fin" value="${indexCarNew*numPerCarousel + numPerCarousel -1}"/>
                                            <c:forEach begin="${start}" 
                                                       end="${fin}"
                                                       var="indexCarNewItem">
                                                <div class="card col-3">
                                                    <img src="${listNewSubject.get(indexCarNewItem).getThumbnail()}" 
                                                         class="card-img-top img-thumbnail" 
                                                         alt="..."
                                                         style="width: 18rem; height: 10rem">
                                                    <div class="card-body">
                                                        <h5 class="card-title">
                                                            ${listNewSubject.get(indexCarNewItem).getSubjectName()}
                                                        </h5>
                                                        <c:if test="${listNewSubject.get(indexCarNewItem).getSubjectName().length()<=22}">
                                                            <br>
                                                        </c:if>
                                                        <p>
                                                            ${listNewSubject.get(indexCarNewItem).getTagLine()}
                                                        <p>
                                                        <p>
                                                            <c:if test="${checkNewSubject.contains(listNewSubject.get(indexCarNewItem).getSubjectName())}">
                                                                <a style="text-decoration: none;" href="" class="badge text-bg-success">New</a>
                                                            </c:if>
                                                            <c:if test="${checkSaleSubject.contains(listNewSubject.get(indexCarNewItem).getSubjectName())}">
                                                                <a style="text-decoration: none;" href="" class="badge text-bg-warning">Big Sale</a>
                                                            </c:if>
                                                            <c:if test="${checkFeatSubject.contains(listNewSubject.get(indexCarNewItem).getSubjectName())}">
                                                                <a style="text-decoration: none;" href="" class="badge text-bg-primary">Featured</a>
                                                            </c:if>
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
                                                        <button type="button" class="btn btn-info" 
                                                                data-bs-toggle="modal" 
                                                                data-bs-target=".modalRegister${listNewSubject.get(indexCarNewItem).getSubjectId()}">
                                                            Register
                                                        </button>
                                                        <!-- Modal Register -->
                                                        <div class="modal fade modalRegister${listNewSubject.get(indexCarNewItem).getSubjectId()} "
                                                             tabindex="-1"
                                                             role="dialog" >
                                                            <div class="modal-dialog modal-dialog-centered" 
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
                                                                        <jsp:include page="/SubjectRegisterPopUp.jsp">
                                                                            <jsp:param name="registId" value="${listNewSubject.get(indexCarNewItem).getSubjectId()}"/>
                                                                        </jsp:include>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                        <div class="col-1">
                            <button class="carousel-control-next"
                                    type="button" 
                                    data-bs-target="#carouselNewSubject" 
                                    data-bs-slide="next">
                                <span class="carousel-control-next-icon" 
                                      aria-hidden="false"></span>
                                <span class="visually-hidden">Next</span>
                            </button>
                        </div>
                        <div class="mt-5">
                            <div class="carousel-indicators">
                                <c:forEach begin="0" end="${numOfCarouselNew-1}" var="indexCarNew">
                                    <button type="button" 
                                            data-bs-target="#carouselNewSubject" 
                                            data-bs-slide-to="${indexCarNew}" 
                                            class="${indexCarNew==0?"active":""}"
                                            aria-current="true" 
                                            aria-label="Slide ${indexCarNew+1}"></button>
                                </c:forEach>
                            </div>
                        </div>            
                    </div>
                </c:if>
            </section>
            <br>
            <section>
                <h3>Big Sale</h3>
                <c:if test="${numOfCarouselSale == 0}">
                    <div>
                        <h3>There isn't any new subject at this time!</h3>
                    </div>
                </c:if>
                <c:if test="${numOfCarouselSale != 0}">
                    <div id="carouselSaleSubject" 
                         class="carousel carousel-dark slide row" 
                         data-bs-ride="carousel"
                         style="border: solid 1px;
                         background-image: url(https://thumbs.dreamstime.com/z/northern-lights-aurora-borealis-shining-green-night-starry-sky-star-tracks-winter-lofoten-islands-norway-northern-116904948.jpg);">
                        <div class="col-2">
                            <button class="carousel-control-prev" 
                                    type="button" 
                                    data-bs-target="#carouselSaleSubject" 
                                    data-bs-slide="prev">
                                <span class="carousel-control-prev-icon"
                                      aria-hidden="false"></span>
                                <span class="visually-hidden">Previous</span>
                            </button>
                        </div>
                        <div class="col-10 ml-5">
                            <div class="carousel-inner">
                                <c:forEach begin="0" end="${numOfCarouselSale-1}" var="indexCarSale">
                                    <div class="carousel-item ${indexCarSale==0?"active":""}">
                                        <div class="row">
                                            <c:set var="start" value="${indexCarSale*numPerCarousel}"/>
                                            <c:set var="fin" value="${indexCarSale*numPerCarousel + numPerCarousel -1}"/>
                                            <c:forEach begin="${start}" 
                                                       end="${fin}"
                                                       var="indexCarSaleItem">
                                                <div class="card col-3">
                                                    <img src="${listSaleSubject.get(indexCarSaleItem).getThumbnail()}" 
                                                         class="card-img-top img-thumbnail" 
                                                         alt="..."
                                                         style="width: 18rem; height: 10rem">
                                                    <div class="card-body">
                                                        <h5 class="card-title">
                                                            ${listSaleSubject.get(indexCarSaleItem).getSubjectName()}
                                                        </h5>
                                                        <c:if test="${listSaleSubject.get(indexCarSaleItem).getSubjectName().length()<=22}">
                                                            <br>
                                                        </c:if>
                                                        <p>
                                                            ${listSaleSubject.get(indexCarSaleItem).getTagLine()}
                                                        <p>
                                                        <p>
                                                            <c:if test="${checkNewSubject.contains(listSaleSubject.get(indexCarSaleItem).getSubjectName())}">
                                                                <a style="text-decoration: none;" href="" class="badge text-bg-success">New</a>
                                                            </c:if>
                                                            <c:if test="${checkSaleSubject.contains(listSaleSubject.get(indexCarSaleItem).getSubjectName())}">
                                                                <a style="text-decoration: none;" href="" class="badge text-bg-warning">Big Sale</a>
                                                            </c:if>
                                                            <c:if test="${checkFeatSubject.contains(listSaleSubject.get(indexCarSaleItem).getSubjectName())}">
                                                                <a style="text-decoration: none;" href="" class="badge text-bg-primary">Featured</a>
                                                            </c:if>
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
                                                        <button type="button" class="btn btn-info" 
                                                                data-bs-toggle="modal" 
                                                                data-bs-target=".modalRegister${listSaleSubject.get(indexCarSaleItem).getSubjectId()}">
                                                            Register
                                                        </button>
                                                        <!-- Modal Register -->
                                                        <div class="modal fade modalRegister${listSaleSubject.get(indexCarSaleItem).getSubjectId()} "
                                                             tabindex="-1"
                                                             role="dialog" >
                                                            <div class="modal-dialog modal-dialog-centered" 
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
                                                                        <jsp:include page="/SubjectRegisterPopUp.jsp">
                                                                            <jsp:param name="registId" value="${listSaleSubject.get(indexCarSaleItem).getSubjectId()}"/>
                                                                        </jsp:include>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                        <div class="col-1">
                            <button class="carousel-control-next"
                                    type="button" 
                                    data-bs-target="#carouselSaleSubject" 
                                    data-bs-slide="next">
                                <span class="carousel-control-next-icon" 
                                      aria-hidden="false"></span>
                                <span class="visually-hidden">Next</span>
                            </button>
                        </div>
                        <div class="mt-5">
                            <div class="carousel-indicators">
                                <c:forEach begin="0" end="${numOfCarouselSale-1}" var="indexCarSale">
                                    <button type="button" 
                                            data-bs-target="#carouselSaleSubject" 
                                            data-bs-slide-to="${indexCarSale}" 
                                            class="${indexCarSale==0?"active":""}"
                                            aria-current="true" 
                                            aria-label="Slide ${indexCarSale+1}"></button>
                                </c:forEach>
                            </div>
                        </div>            
                    </div>
                </c:if>
            </section>
            <br>
            <section>
                <h3>Featured Subjects</h3>
                <c:if test="${numOfCarouselFeatured == 0}">
                    <div>
                        <h3>There isn't any new subject at this time!</h3>
                    </div>
                </c:if>
                <c:if test="${numOfCarouselFeatured != 0}">
                    <div id="carouselFeaturedSubject" 
                         class="carousel carousel-dark slide row" 
                         data-bs-ride="carousel"
                         style="border: solid 1px;
                         background-image: url(https://thumbs.dreamstime.com/z/crystal-lakes-chamonix-alps-mont-blanc-france-autumn-picturesque-fantastically-beautiful-first-frost-against-130410035.jpg);">
                        <div class="col-2">
                            <button class="carousel-control-prev" 
                                    type="button" 
                                    data-bs-target="#carouselFeaturedSubject" 
                                    data-bs-slide="prev">
                                <span class="carousel-control-prev-icon"
                                      aria-hidden="false"></span>
                                <span class="visually-hidden">Previous</span>
                            </button>
                        </div>
                        <div class="col-10 ml-5">
                            <div class="carousel-inner">
                                <c:forEach begin="0" end="${numOfCarouselFeatured-1}" var="indexCarFeat">
                                    <div class="carousel-item ${indexCarFeat==0?"active":""}">
                                        <div class="row">
                                            <c:set var="start" value="${indexCarFeat*numPerCarousel}"/>
                                            <c:set var="fin" value="${indexCarFeat*numPerCarousel + numPerCarousel -1}"/>
                                            <c:forEach begin="${start}" 
                                                       end="${fin}"
                                                       var="indexCarFeatItem">
                                                <div class="card col-3">
                                                    <img src="${listFeaturedSubject.get(indexCarFeatItem).getThumbnail()}" 
                                                         class="card-img-top img-thumbnail" 
                                                         alt="..."
                                                         style="width: 18rem; height: 10rem">
                                                    <div class="card-body">
                                                        <h5 class="card-title">
                                                            ${listFeaturedSubject.get(indexCarFeatItem).getSubjectName()}
                                                        </h5>
                                                        <c:if test="${listFeaturedSubject.get(indexCarFeatItem).getSubjectName().length()<=22}">
                                                            <br>
                                                        </c:if>
                                                        <p>
                                                            ${listFeaturedSubject.get(indexCarFeatItem).getTagLine()}
                                                        <p>
                                                        <p>
                                                            <c:if test="${checkNewSubject.contains(listFeaturedSubject.get(indexCarFeatItem).getSubjectName())}">
                                                                <a style="text-decoration: none;" href="" class="badge text-bg-success">New</a>
                                                            </c:if>
                                                            <c:if test="${checkSaleSubject.contains(listFeaturedSubject.get(indexCarFeatItem).getSubjectName())}">
                                                                <a style="text-decoration: none;" href="" class="badge text-bg-warning">Big Sale</a>
                                                            </c:if>
                                                            <c:if test="${checkFeatSubject.contains(listFeaturedSubject.get(indexCarFeatItem).getSubjectName())}">
                                                                <a style="text-decoration: none;" href="" class="badge text-bg-primary">Featured</a>
                                                            </c:if>
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
                                                        <button type="button" class="btn btn-info" 
                                                                data-bs-toggle="modal" 
                                                                data-bs-target=".modalRegister${listFeaturedSubject.get(indexCarFeatItem).getSubjectId()}">
                                                            Register
                                                        </button>
                                                        <!-- Modal Register -->
                                                        <div class="modal fade modalRegister${listFeaturedSubject.get(indexCarFeatItem).getSubjectId()} "
                                                             tabindex="-1"
                                                             role="dialog" >
                                                            <div class="modal-dialog modal-dialog-centered" 
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
                                                                        <jsp:include page="/SubjectRegisterPopUp.jsp">
                                                                            <jsp:param name="registId" value="${listFeaturedSubject.get(indexCarFeatItem).getSubjectId()}"/>
                                                                        </jsp:include>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                        <div class="col-1">
                            <button class="carousel-control-next"
                                    type="button" 
                                    data-bs-target="#carouselFeaturedSubject" 
                                    data-bs-slide="next">
                                <span class="carousel-control-next-icon" 
                                      aria-hidden="false"></span>
                                <span class="visually-hidden">Next</span>
                            </button>
                        </div>
                        <div class="mt-5">
                            <div class="carousel-indicators">
                                <c:forEach begin="0" end="${numOfCarouselFeatured-1}" var="indexCarFeat">
                                    <button type="button" 
                                            data-bs-target="#carouselFeaturedSubject" 
                                            data-bs-slide-to="${indexCarFeat}" 
                                            class="${indexCarFeat==0?"active":""}"
                                            aria-current="true" 
                                            aria-label="Slide ${indexCarFeat+1}"></button>
                                </c:forEach>
                            </div>
                        </div>            
                    </div>
                </c:if>
            </section>
            <section>
                <div class="row">
                    <aside class="col-3 sbar">                        
                    </aside>
                    <div class="col-9 mt-3">
                        <h1>
                            Subjects List
                        </h1>
                        <c:set var="page" value="${requestScope.page}"/>
                        <c:set var="filter" value="${requestScope.sendFilter}"/>
                        <nav>
                            <ul class="pagination">
                                <li class="page-item">
                                    <a class="page-link ${page==1?"disabled":""}" 
                                       href="public/SubjectsList?${filter}page=${page-1}">Previous</a>
                                </li>
                                <!-- get all pages -->
                                <c:forEach begin="${1}" end="${3}" var="i">
                                    <li class="page-item">
                                        <a class="page-link ${i==page?"active":""}" 
                                           href="public/SubjectsList?${filter}page=${i}">${i}</a>
                                    </li>
                                </c:forEach>
                                    <li class="page-item">
                                        <a class="page-link disabled">...</a>
                                    </li>
                                    <c:if test="${page > 3 && page < numOfAllSubjects-2}">
                                        <li class="page-item">
                                        <a class="page-link active" 
                                           href="public/SubjectsList?${filter}page=${page}">${page}</a>
                                    </li>
                                        <li class="page-item">
                                        <a class="page-link disabled">...</a>
                                    </li>
                                    </c:if>
                                <c:forEach begin="${numOfAllSubjects-2}" end="${numOfAllSubjects}" var="i">
                                    <li class="page-item">
                                        <a class="page-link ${i==page?"active":""}" 
                                           href="public/SubjectsList?${filter}page=${i}">${i}</a>
                                    </li>
                                </c:forEach>
                                <li class="page-item">
                                    <a class="page-link ${page==numOfAllSubjects?"disabled":""}" 
                                       href="public/SubjectsList?${filter}page=${page+1}">Next</a>
                                </li>    
                            </ul>
                        </nav>
                        <c:if test="${allSubjectsList.size()<1}">
                            <h3>Empty</h3>
                        </c:if>
                        <ul class="list-group">
                            <!-- get all registrations that meet previous conditions: input key, filter -->
                            <c:forEach items="${allSubjectsList}" var="p">
                                <!-- Change cards' appearance -->
                                <li class="list-group-item list-group-item-info">
                                    <div class="card mb-3">
                                        <div class="row g-0">
                                            <div class="col-md-4">
                                                <img src="${p.getThumbnail()}"
                                                     class="img-fluid rounded-start" 
                                                     width="300" height="180">
                                            </div>
                                            <div class="col-md-6">
                                                <div class="card-body">
                                                    <h5 class="card-title">Subject: ${p.getSubjectName()}</h5>
                                                    <ul class="list-group list-group-flush">
                                                        <li class="list-group-item">
                                                            <div class="row">
                                                                <div class="col-6">
                                                                    Package: ${p.getLowestPackageName()}
                                                                </div>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>
                                            <div class="col-md-2 mt-md-3">
                                                <h5>Sale Price:</h5>
                                                <h5>${Integer.valueOf(p.getPackageSalePrice()*1000)} VND</h5>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                        <nav>
                            <ul class="pagination">
                                <!-- get all pages -->
                                <c:forEach begin="${1}" end="${numOfAllSubjects}" var="i">
                                    <li class="page-item">
                                        <a class="page-link ${i==page?"active":""}" 
                                           href="public/SubjectsList?${filter}page=${i}">${i}</a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </nav>
                    </div>
                </div>
            </section>
        </main>
        <%@include file="/common/footer.jsp" %>
    </body>
</html>
