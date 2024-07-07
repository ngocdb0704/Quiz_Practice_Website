<%-- 
    Document   : SubjectListForBusiness
    Created on : 18 thg 6, 2024, 17:28:00
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Subject List</title>
        <%@include file="/common/ImportBootstrap.jsp" %>
        <link rel="stylesheet" href="common/ExtendBody.css"/>
        <link rel="stylesheet" href="css/SubjectsList.css"/>
        <script src="public/js/SubjectsList.js"></script>
    </head>
    <body onload="scrollAtLoad()">
        <c:set var="goTo" value="${requestScope.goTo}"/>
        <input type="hidden" value="${goTo}" id="goToPos">
        <c:set var="numPerCarousel" value="${requestScope.numPerCarousel}"/>
        <c:set var="listFeaturedSubject" value="${requestScope.dataFeaturedSubject}"/>
        <c:set var="allSubjectsList" value="${requestScope.dataAllSubject}"/>
        <c:set var="plans" value="${requestScope.plans}"/>
        <c:set var="numOfAllSubjects" value="${requestScope.numOfAllSubjects}"/>
        <c:set var="checkNewSubject" value="${requestScope.listOfIdNew}"/>
        <c:set var="checkSaleSubject" value="${requestScope.listOfIdSale}"/>
        <c:set var="checkFeatSubject" value="${requestScope.listOfIdFeat}"/>
        <c:set var="cat" value="${requestScope.list}"/>
        <c:set var="check" value="${requestScope.check}"/>
        <c:set var="levels" value="${requestScope.levels}"/>
        <c:set var="checkLevel" value="${requestScope.checkLevel}"/>
        <c:set var="org" value="${requestScope.org}"/>
        <c:set var="checkOrg" value="${requestScope.checkOrg}"/>
        <c:set var="key" value="${requestScope.key}"/>
        <c:set var="order" value="${requestScope.order}"/>
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
            <div>
                <h1>
                    Subjects List
                </h1>
                <nav class="nav nav-pills" style="background-color: #e3f2fd;">
                    <a class="nav-link" href="public/SubjectsList?service=individual">For Individual</a>
                    <a class="nav-link active" href="public/SubjectsList?service=business">For Organization</a>
                </nav>

            </div>
            <br>
            <!-- Team Plan Section -->
            <section>
                <h1>Team Plan</h1>
                <div class="containerAtHead container">
                    <img class="img-thumbnail"
                         src="https://roost.nbcuni.com/bin/viewasset.html/content/dam/Peacock/Campaign/landingpages/library/theoffice/season8/episodes/office_s8_e7.jpg/_jcr_content/renditions/original?downsize=1200:*&output-quality=70" alt="Team" style="width:100%; height: 600px">
                    <div class="text-blockForTeam">
                        <h1 class="link-success">The upskilling solution <br> for small teams</h1>
                        <p>From communication skills to generative AI and a whole lot more,
                            <br> our top-rated courses will help you keep pace and then some.
                            <br> (Up to 125 members)
                            <br> Only ${Integer.valueOf(plans.get(0).getListPrice())*1000*25} VND per user for 4 months
                            <br> Only ${Integer.valueOf(plans.get(0).getSalePrice())*1000*25} VND/user/4 months for nonprofit teams
                        </p>
                        <div>
                            <a>
                                <button class="btn btn-dark ">
                                    Contact Our Sales
                                </button>
                            </a>
                        </div>
                    </div>
                    <div class="container text-center statTeam">
                        <div class="row">
                            <div class="col">
                                <h1>25%</h1>
                                <p>
                                    More productivity
                                </p>
                            </div>
                            <div class="col">
                                <h1>38%</h1>
                                <p>
                                    Higher retention
                                </p>
                            </div>
                            <div class="col">
                                <h1>24%</h1>
                                <p>
                                    Lower training costs
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <br>
            <!-- Companies section -->
            <section>
                <div class="row">
                    <div class="col-7">
                        <img src="https://images.ctfassets.net/2pudprfttvy6/2eC1z7LVc0mMog2PnVxS5u/71d96c6d0c8dad9f8cae5b0881385e6e/BC-2700_B2B_Homepage_Partner_Logo_Refresh_April_2024.png" 
                             alt="alt"
                             width="650"
                             height="400"/>
                    </div>
                    <div class="col-4 mt-5">
                        <h1>
                            Leaders at 3,800+
                            <br>companies develop
                            <br>their talent with 
                            <br>Quiz_Practice
                        </h1>
                    </div>
                </div>
            </section>
            <br>
            <!-- Enterprise Plan Section -->
            <section>
                <h1>Enterprise Plan</h1>
                <div class="containerAtHead container">
                    <img class="img-thumbnail"
                         src="https://i.imgur.com/mNsB5sq.png" alt="Enterprise" style="width:100%; height: 600px">
                    <div class="text-blockForEnterprise">
                        <h1 class="link-success">Upskill your <br> entire workforce</h1>
                        <p>
                            + Keep your people engaged <br> and help them grow
                            <br> + Boost learning 
                            <br> + Reduce training costs
                            <br> + Improve employee retention and mobility
                            <br> Only ${Integer.valueOf(plans.get(1).getListPrice())*1000*25} VND per user for 4 months
                            <br> Only ${Integer.valueOf(plans.get(1).getSalePrice())*1000*25} VND/user/4 months for universities
                            <br> ; governments or nonprofit agency
                        </p>
                        <div>
                            <a>
                                <button class="btn btn-dark ">
                                    Contact Our Sales
                                </button>
                            </a>
                        </div>
                    </div>
                    <div class="text-blockForTradition">
                        <h1 class="link-danger">Traditional Training Method</h1>
                        <h4>
                            - Slow progress <br> - Expensive training costs
                        </h4>
                    </div>
                    <div class="container text-center statEnterprise">
                        <div class="row">
                            <div class="col">
                                <h1>24%</h1>
                                <p>
                                    Reduction in training costs
                                </p>
                            </div>
                            <div class="col">
                                <h1>38%</h1>
                                <p>
                                    Higher retention rates
                                </p>
                            </div>
                            <div class="col">
                                <h1>25%</h1>
                                <p>
                                    More employee productivity
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <br>
            <!-- Advantages Section -->
            <section class="card">
                <h1>Achieve your goals from the start</h1>
                <div class="row">
                    <div class="col-4 container">
                        <div class="row mt-5">
                            <h4>Build more confident and productive teams</h4>
                            <p>
                                Get unlimited access to top rated, in-demand courses.
                            </p>
                        </div>
                        <div class="row mt-5">
                            <h4>Innovate by staying ahead of new technologies</h4>
                            <p>
                                Access first-to-market content taught by real-world practitioners.
                            </p>
                        </div>
                    </div>
                    <div class="col-4 container">
                        <img src="https://www.rbc.com/en/future-launch/wp-content/uploads/sites/9/2023/02/shutterstock_1095549857.jpg?w=1024"
                             alt="alt" height="500" width="500"
                             class="img-thumbnail"/>
                    </div>
                    <div class="col-4 container">
                        <div class="row mt-5">
                            <h4>Save costs by investing in internal talent</h4>
                            <p>
                                Foster employee development through learning on-demand and on the go.
                            </p>
                        </div>
                        <div class="row mt-5">
                            <h4>Support your employees through ongoing change</h4>
                            <p>
                                Track learner adoption and engagement to achieve measurable results.
                            </p>
                        </div>
                    </div>
                </div>
            </section>
            <br>
            <section>
                <c:set var="posToGo" value="${2640}"/>
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
                                        <input type="hidden" value="business" name="service">
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
                                    <div class="accordion-item">
                                        <h2 class="accordion-header">
                                            <button class="accordion-button collapsed" 
                                                    type="button" data-bs-toggle="collapse" 
                                                    data-bs-target="#flush-collapseThree" 
                                                    aria-expanded="false" 
                                                    aria-controls="flush-collapseThree"
                                                    onclick="scrollToTopCategory()">
                                                Providers
                                            </button>
                                        </h2>
                                        <div id="flush-collapseThree" 
                                             class="accordion-collapse collapse" 
                                             data-bs-parent="#accordionFlushSiderFilter">
                                            <div class="accordion-body">
                                                <ul class="list-group list-group-flush">
                                                    <c:forEach begin="0" end="${org.size()-1}" var="index">
                                                        <li class="list-group-item">
                                                            <input class="form-check-input" type="checkbox" name="org"
                                                                   value="${org.get(index).getOrgId()}"
                                                                   ${checkOrg[index]?"checked":""}
                                                                   onclick="this.form.submit()"
                                                                   > <span class="badge text-bg-light">
                                                                <!-- get all node -->
                                                                <!-- set name to node -->
                                                                ${org.get(index).getOrgName()}
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
                                                    data-bs-target="#flush-collapseFour" 
                                                    aria-expanded="false" 
                                                    aria-controls="flush-collapseFour">
                                                Featured Subjects
                                            </button>
                                        </h2>
                                        <div id="flush-collapseFour" 
                                             class="accordion-collapse collapse show" 
                                             data-bs-parent="#featuredAccordion">
                                            <div class="accordion-body">
                                                <ul>
                                                    <c:forEach begin="0" end="${listFeaturedSubject.size()-1}" var="iFeat">
                                                        <li class="w3tooltip" style="width: 350px">
                                                            <div class="my-2 ps-1 border border-1 rounded-1">
                                                                <div>
                                                                    ${listFeaturedSubject.get(iFeat).getSubjectName()} 
                                                                    <div class="w3tooltiptext card" style="width: 18rem;">
                                                                        <img src="${listFeaturedSubject.get(iFeat).getThumbnail()}" 
                                                                             class="card-img-top img-thumbnail" alt="...">
                                                                        <div class="card-body">
                                                                            <h5 class="card-title">
                                                                                ${listFeaturedSubject.get(iFeat).getSubjectName()} 
                                                                            </h5>
                                                                            <p class="card-text">
                                                                                ${listFeaturedSubject.get(iFeat).getTagLine()}
                                                                                <br>From: <span class="text-danger">${listFeaturedSubject.get(iFeat).getProvider()}</span> 
                                                                                <br>
                                                                                <span class="star">
                                                                                    &#9733; &#9733; &#9733; &#9733; &#9734;
                                                                                </span>
                                                                                <span>(99k subject reviews)</span>
                                                                                <br> ${listFeaturedSubject.get(iFeat).getLevel()}
                                                                                <br>
                                                                                <a>
                                                                                    <button class="btn btn-dark ">
                                                                                        Contact Our Sales
                                                                                    </button>
                                                                                </a>
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
                        <c:if test="${allSubjectsList.size()>=1}">
                            <nav class="row">
                                <ul class="pagination col-8">
                                    <li class="page-item">
                                        <a class="page-link ${page==1?"disabled":""}" 
                                           href="public/SubjectsList?${filter}page=${page-1}&goToPos=${posToGo}&orderList=${order}&service=business">Previous</a>
                                    </li>
                                    <!-- get all pages -->
                                    <c:if test="${numOfAllSubjects > 6}">
                                        <c:forEach begin="${1}" end="${2}" var="i">
                                            <li class="page-item">
                                                <a class="page-link ${i==page?"active":""}" 
                                                   href="public/SubjectsList?${filter}page=${i}&goToPos=${posToGo}&orderList=${order}&service=business">${i}</a>
                                            </li>
                                        </c:forEach>
                                        <li class="page-item">
                                            <a class="page-link disabled">...</a>
                                        </li>
                                        <c:if test="${page > 2 && page < numOfAllSubjects-1}">
                                            <li class="page-item">
                                                <a class="page-link active" 
                                                   href="public/SubjectsList?${filter}page=${page}&goToPos=${posToGo}&orderList=${order}&service=business">${page}</a>
                                            </li>
                                            <li class="page-item">
                                                <a class="page-link disabled">...</a>
                                            </li>
                                        </c:if>
                                        <c:forEach begin="${numOfAllSubjects-1}" end="${numOfAllSubjects}" var="i">
                                            <li class="page-item">
                                                <a class="page-link ${i==page?"active":""}" 
                                                   href="public/SubjectsList?${filter}page=${i}&goToPos=${posToGo}&orderList=${order}&service=business">${i}</a>
                                            </li>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${numOfAllSubjects <= 6}">
                                        <c:forEach begin="${1}" end="${numOfAllSubjects}" var="i">
                                            <li class="page-item">
                                                <a class="page-link ${i==page?"active":""}" 
                                                   href="public/SubjectsList?${filter}page=${i}&goToPos=${posToGo}&orderList=${order}&service=business">${i}</a>
                                            </li>
                                        </c:forEach>
                                    </c:if>        
                                    <li class="page-item">
                                        <a class="page-link ${page==numOfAllSubjects?"disabled":""}" 
                                           href="public/SubjectsList?${filter}page=${page+1}&goToPos=${posToGo}&orderList=${order}&service=business">Next</a>
                                    </li>
                                </ul>
                                <div class="col-4">
                                    <h4>Sort by Updated Date: </h4>
                                    <ul class="pagination">
                                        <li class="page-item">
                                            <a class=" page-link ${order==1?"active":""}" 
                                               href="public/SubjectsList?${filter}page=${page}&goToPos=${posToGo}&orderList=1&service=business">
                                                Latest
                                            </a>

                                        </li>
                                        <li class="page-item">
                                            <a class=" page-link ${order==0?"active":""}" 
                                               href="public/SubjectsList?${filter}page=${page}&goToPos=${posToGo}&orderList=0&service=business">
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
                                           href="public/SubjectsList?goToPos=2640&service=business">All Subjects</a>
                                    </span>
                                </h3>
                            </div>
                        </c:if>
                        <div class="row">
                            <!-- get all registrations that meet previous conditions: input key, filter -->
                            <c:forEach items="${allSubjectsList}" var="p">
                                <!-- Change cards' appearance -->
                                <div class="card col-4">
                                    <img src="public/thumbnails/${p.getThumbnail()}" 
                                         class="card-img-top" alt="..."
                                         style="height: 12rem;">
                                    <div class="card-body">
                                        <h5 class="card-title">${p.getSubjectName()}</h5>
                                        <p class="card-text">
                                            ${p.getTagLine()}
                                            <br>From: <span class="text-danger">${p.getProvider()}</span> 
                                            <br>
                                            <span class="star">
                                                &#9733; &#9733; &#9733; &#9733; &#9734;
                                            </span>
                                            <span>(99k subject reviews)</span>
                                            <br> ${p.getLevel()}
                                        </p>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <c:if test="${allSubjectsList.size()>=1}">
                            <nav>
                                <ul class="pagination col-8">
                                    <li class="page-item">
                                        <a class="page-link ${page==1?"disabled":""}" 
                                           href="public/SubjectsList?${filter}page=${page-1}&goToPos=${posToGo}&orderList=${order}&service=business">Previous</a>
                                    </li>
                                    <!-- get all pages -->
                                    <c:if test="${numOfAllSubjects > 6}">
                                        <c:forEach begin="${1}" end="${2}" var="i">
                                            <li class="page-item">
                                                <a class="page-link ${i==page?"active":""}" 
                                                   href="public/SubjectsList?${filter}page=${i}&goToPos=${posToGo}&orderList=${order}&service=business">${i}</a>
                                            </li>
                                        </c:forEach>
                                        <li class="page-item">
                                            <a class="page-link disabled">...</a>
                                        </li>
                                        <c:if test="${page > 2 && page < numOfAllSubjects-1}">
                                            <li class="page-item">
                                                <a class="page-link active" 
                                                   href="public/SubjectsList?${filter}page=${page}&goToPos=${posToGo}&orderList=${order}&service=business">${page}</a>
                                            </li>
                                            <li class="page-item">
                                                <a class="page-link disabled">...</a>
                                            </li>
                                        </c:if>
                                        <c:forEach begin="${numOfAllSubjects-1}" end="${numOfAllSubjects}" var="i">
                                            <li class="page-item">
                                                <a class="page-link ${i==page?"active":""}" 
                                                   href="public/SubjectsList?${filter}page=${i}&goToPos=${posToGo}&orderList=${order}&service=business">${i}</a>
                                            </li>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${numOfAllSubjects <= 6}">
                                        <c:forEach begin="${1}" end="${numOfAllSubjects}" var="i">
                                            <li class="page-item">
                                                <a class="page-link ${i==page?"active":""}" 
                                                   href="public/SubjectsList?${filter}page=${i}&goToPos=${posToGo}&orderList=${order}&service=business">${i}</a>
                                            </li>
                                        </c:forEach>
                                    </c:if>        
                                    <li class="page-item">
                                        <a class="page-link ${page==numOfAllSubjects?"disabled":""}" 
                                           href="public/SubjectsList?${filter}page=${page+1}&goToPos=${posToGo}&orderList=${order}&service=business">Next</a>
                                    </li>
                                </ul>
                            </nav>
                        </c:if>
                        <div class="container text-center">
                            <div class="row">
                                <div class="col">
                                </div>
                                <div class="col">
                                    <a>
                                        <button class="btn btn-dark ">
                                            Contact Our Sales
                                            For More Detailed
                                            List Of Subjects
                                        </button>
                                    </a>
                                </div>
                                <div class="col">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </main>
    </body>
</html>
