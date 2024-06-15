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
            <c:set var="check" value="${requestScope.check}"/>
            <c:set var="key" value="${requestScope.key}"/>
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
            <br>
            <section>
                <c:set var="posToGo" value="${1850}"/>
                <h1>
                    Subjects List
                </h1>
                <div class="row">
                    <aside class="col-3 sbar">
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
                                        <input type="hidden" value="1850" name="goToPos">
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
                                <div class="accordion accordion-flush" id="accordionFlushSiderFilter">
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
                                             class="accordion-collapse collapse" 
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
                                </div>
                            </form>
                        </div>
                    </aside>
                    <div class="col-9 mt-3">
                        <c:set var="page" value="${requestScope.page}"/>
                        <c:set var="filter" value="${requestScope.sendFilter}"/>
                        <c:if test="${allSubjectsList.size()>1}">
                            <nav>
                                <ul class="pagination">
                                    <li class="page-item">
                                        <a class="page-link ${page==1?"disabled":""}" 
                                           href="public/SubjectsList?${filter}page=${page-1}&goToPos=${posToGo}">Previous</a>
                                    </li>
                                    <!-- get all pages -->
                                    <c:if test="${numOfAllSubjects > 6}">
                                        <c:forEach begin="${1}" end="${2}" var="i">
                                            <li class="page-item">
                                                <a class="page-link ${i==page?"active":""}" 
                                                   href="public/SubjectsList?${filter}page=${i}&goToPos=${posToGo}">${i}</a>
                                            </li>
                                        </c:forEach>
                                        <li class="page-item">
                                            <a class="page-link disabled">...</a>
                                        </li>
                                        <c:if test="${page > 2 && page < numOfAllSubjects-1}">
                                            <li class="page-item">
                                                <a class="page-link active" 
                                                   href="public/SubjectsList?${filter}page=${page}&goToPos=${posToGo}">${page}</a>
                                            </li>
                                            <li class="page-item">
                                                <a class="page-link disabled">...</a>
                                            </li>
                                        </c:if>
                                        <c:forEach begin="${numOfAllSubjects-1}" end="${numOfAllSubjects}" var="i">
                                            <li class="page-item">
                                                <a class="page-link ${i==page?"active":""}" 
                                                   href="public/SubjectsList?${filter}page=${i}&goToPos=${posToGo}">${i}</a>
                                            </li>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${numOfAllSubjects <= 6}">
                                        <c:forEach begin="${1}" end="${numOfAllSubjects}" var="i">
                                            <li class="page-item">
                                                <a class="page-link ${i==page?"active":""}" 
                                                   href="public/SubjectsList?${filter}page=${i}&goToPos=${posToGo}">${i}</a>
                                            </li>
                                        </c:forEach>
                                    </c:if>        
                                    <li class="page-item">
                                        <a class="page-link ${page==numOfAllSubjects?"disabled":""}" 
                                           href="public/SubjectsList?${filter}page=${page+1}&goToPos=${posToGo}">Next</a>
                                    </li>    
                                </ul>
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
                                           href="public/SubjectsList?goToPos=1850">All Subjects</a>
                                    </span>
                                </h3>
                            </div>
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
                                                    <h5 class="card-title">
                                                        <a id="subjectLink" href="index.jsp">
                                                            ${p.getSubjectName()}
                                                        </a>
                                                    </h5>
                                                    <ul class="list-group list-group-flush">
                                                        <li class="list-group-item">
                                                            <div class="row">
                                                                <div class="col-10">
                                                                    <span>
                                                                        ${p.getTagLine()}
                                                                    </span>
                                                                    <span>
                                                                        <c:if test="${checkNewSubject.contains(p.getSubjectName())}">
                                                                            <span class="badge text-bg-success">
                                                                                New
                                                                            </span>
                                                                        </c:if>
                                                                        <c:if test="${checkSaleSubject.contains(p.getSubjectName())}">
                                                                            <span class="badge text-bg-warning">
                                                                                Big Sale
                                                                            </span>
                                                                        </c:if>
                                                                        <c:if test="${checkFeatSubject.contains(p.getSubjectName())}">
                                                                            <span class="badge text-bg-primary">
                                                                                Featured
                                                                            </span>
                                                                        </c:if>
                                                                    </span>
                                                                </div>
                                                            </div>
                                                        </li>
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
                                                <h6>
                                                    <span class="fw-light badge rounded-pill text-bg-light" 
                                                          style=" text-decoration-line: line-through;"> 
                                                        ${Integer.valueOf(listNewSubject.get(indexCarNewItem).getPackageListPrice()*1000)} vnd
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
                                                <button type="button" class="btn btn-info" 
                                                        data-bs-toggle="modal" 
                                                        data-bs-target=".modalRegister${p.getSubjectId()}">
                                                    Register
                                                </button>
                                                <!-- Modal Register -->
                                                <div class="modal fade modalRegister${p.getSubjectId()} "
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
                                                                    <jsp:param name="registId" value="${p.getSubjectId()}"/>
                                                                </jsp:include>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                        <c:if test="${allSubjectsList.size()>1}">
                            <nav>
                                <ul class="pagination">
                                    <li class="page-item">
                                        <a class="page-link ${page==1?"disabled":""}" 
                                           href="public/SubjectsList?${filter}page=${page-1}&goToPos=${posToGo}">Previous</a>
                                    </li>
                                    <!-- get all pages -->
                                    <c:if test="${numOfAllSubjects > 6}">
                                        <c:forEach begin="${1}" end="${2}" var="i">
                                            <li class="page-item">
                                                <a class="page-link ${i==page?"active":""}" 
                                                   href="public/SubjectsList?${filter}page=${i}&goToPos=${posToGo}">${i}</a>
                                            </li>
                                        </c:forEach>
                                        <li class="page-item">
                                            <a class="page-link disabled">...</a>
                                        </li>
                                        <c:if test="${page > 2 && page < numOfAllSubjects-1}">
                                            <li class="page-item">
                                                <a class="page-link active" 
                                                   href="public/SubjectsList?${filter}page=${page}&goToPos=${posToGo}">${page}</a>
                                            </li>
                                            <li class="page-item">
                                                <a class="page-link disabled">...</a>
                                            </li>
                                        </c:if>
                                        <c:forEach begin="${numOfAllSubjects-1}" end="${numOfAllSubjects}" var="i">
                                            <li class="page-item">
                                                <a class="page-link ${i==page?"active":""}" 
                                                   href="public/SubjectsList?${filter}page=${i}&goToPos=${posToGo}">${i}</a>
                                            </li>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${numOfAllSubjects <= 6}">
                                        <c:forEach begin="${1}" end="${numOfAllSubjects}" var="i">
                                            <li class="page-item">
                                                <a class="page-link ${i==page?"active":""}" 
                                                   href="public/SubjectsList?${filter}page=${i}&goToPos=${posToGo}">${i}</a>
                                            </li>
                                        </c:forEach>
                                    </c:if>        
                                    <li class="page-item">
                                        <a class="page-link ${page==numOfAllSubjects?"disabled":""}" 
                                           href="public/SubjectsList?${filter}page=${page+1}&goToPos=${posToGo}">Next</a>
                                    </li>    
                                </ul>
                            </nav>
                        </c:if>
                    </div>
                </div>
            </section>
        </main>
        <%@include file="/common/footer.jsp" %>
    </body>
</html>
