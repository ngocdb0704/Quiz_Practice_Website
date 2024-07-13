<%-- 
    Document   : My Registration
    Created on : 12 thg 5, 2024, 16:59:06
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector"%>
<%@page import="app.entity.Registration"%>
<%@page import="java.util.Random"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Registration</title>
        <%@include file="/common/ImportBootstrap.jsp" %>
        <link rel="stylesheet" href="public/css/bootstrap/MyRegistration.css"/>
        <link rel="stylesheet" href="common/ExtendBody.css"/>
        <script src="public/js/MyRegistration.js"></script>
        <!-- Script google reCaptcha -->
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
    </head>
    <body>
        <%@include file="/common/header.jsp" %>
        <%@include file="/common/sidebar.jsp" %>
        <main class="container">
            <div class="row">
                <aside class="col-3 sbar">
                    <div class="row mb-3">
                        <c:set var="map" value="${requestScope.map}"/>
                        <c:set var="cat" value="${requestScope.list}"/>
                        <c:set var="check" value="${requestScope.check}"/>
                        <c:set var="key" value="${requestScope.key}"/>
                        <c:set var="status" value="${requestScope.listOfStatus}"/>
                        <c:set var="checkStatus" value="${requestScope.checkStatus}"/>
                        <c:set var="userId" value="${requestScope.userId}"/>
                        <c:set var="bankCode" value="${requestScope.bankCode}"/>
                        <c:set var="owner" value="${requestScope.ownerAccount}"/>
                        <c:set var="noti" value="${requestScope.noti}"/>
                        <c:set var="snoti" value="${requestScope.successNoti}"/>
                        <c:set var="matchActive" value="Pending Approval Active"/>
                        <c:set var="enoti" value="${requestScope.warningNoti}"/>
                        <form action="user/MyRegistrations">
                            <div class="mb-3">
                                <div class="row card-body container justify-content-center">
                                    <label for="searchKey">Subject Search Box</label>
                                    <input class="col-8" 
                                           id="searchKey" 
                                           type="text" value="${key}" 
                                           name="key" 
                                           placeholder="Search Subject by Title">
                                    <button class="col-3" onclick="this.form.submit()">
                                        <i class="bi bi-search"></i>
                                    </button>
                                </div>
                            </div>
                            <div>
                                <h4>
                                    Filter Registrations By:
                                </h4>
                            </div>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item">
                                    <h4 class="text-primary">Status</h4>
                                    <!-- status category tree -->
                                    <ul class="list-group list-group-flush">
                                        <c:forEach begin="0" end="${status.size()-1}" var="index">
                                            <li class="list-group-item">
                                                <input class="form-check-input" type="checkbox" name="idStatus"
                                                       value="${status.get(index)}"
                                                       ${checkStatus[index]?"checked":""}
                                                       onclick="this.form.submit()"
                                                       > <span class="badge text-bg-light">
                                                    <!-- get all node -->
                                                    <!-- set name to node -->
                                                    <c:choose>
                                                        <c:when test="${status.get(index)==1}">
                                                            Submitted
                                                        </c:when>
                                                        <c:when test="${status.get(index)==2}">
                                                            Pending Approval
                                                        </c:when>
                                                        <c:when test="${status.get(index)==3}">
                                                            Active
                                                        </c:when>
                                                        <c:when test="${status.get(index)==4}">
                                                            Withdrawn
                                                        </c:when>
                                                        <c:when test="${status.get(index)==5}">
                                                            Inactive
                                                        </c:when>
                                                    </c:choose>
                                                </span>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </li>
                            </ul>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item">
                                    <h4 class="text-primary">Subject Categories</h4>
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
                        </form>
                    </div>
                    <div class="row mb-3">
                        <a class="link-primary mt-3"
                           href="ContactUs.jsp" target="_blank"
                           rel="noopener noreferrer">Contact Us</a>
                    </div>
                </aside>
                <div class="col-9 mt-3">
                    <c:if test="${noti !=null}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <strong>Notification</strong> ${noti}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>
                    <c:if test="${snoti != null}">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            <strong>Notification</strong> ${snoti}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>
                    <c:if test="${enoti != null}">
                        <div class="alert alert-warning alert-dismissible fade show" role="alert">
                            <strong>Notification</strong> ${enoti}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>
                    <h1>
                        My Registrations
                    </h1>
                    <c:set var="page" value="${requestScope.page}"/>
                    <c:set var="filter" value="${requestScope.sendFilter}"/>
                    <nav>
                        <ul class="pagination">
                            <!-- get all pages -->
                            <c:forEach begin="${1}" end="${requestScope.num}" var="i">
                                <li class="page-item">
                                    <a class="page-link ${i==page?"active":""}" 
                                       href="user/MyRegistrations?${filter}page=${i}">${i}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </nav>
                    <c:if test="${requestScope.data.size()<1}">
                        <h3>Empty</h3>
                    </c:if>
                    <ul class="list-group">
                        <!-- get all registrations that meet previous conditions: input key, filter -->
                        <c:forEach items="${requestScope.data}" var="p">
                            <!-- Change cards' appearance -->
                            <li class="list-group-item list-group-item-info">
                                <div class="card mb-3">
                                    <div class="row g-0">
                                        <div class="col-md-4">
                                            <img src="public/thumbnails/${p.getSubjectImg()}"
                                                 class="img-thumbnail rounded-start" 
                                                 width="300" height="180">
                                        </div>
                                        <div class="col-md-6">
                                            <div class="card-body">
                                                <h5 class="card-title">Subject: ${p.getSubjectName()}</h5>
                                                <ul class="list-group list-group-flush">
                                                    <li class="list-group-item">
                                                        <div class="row">
                                                            <div class="col-6">
                                                                ID: ${p.getRegistrationId()}
                                                            </div>
                                                            <div class="col-6 ml-3">
                                                                <span>Status: </span> 
                                                                <span class=" <c:choose>
                                                                          <c:when test="${p.getStatus()=='Active'}">
                                                                              badge text-bg-success
                                                                          </c:when>
                                                                          <c:when test="${p.getStatus()=='Pending Approval'}">
                                                                              badge text-bg-warning
                                                                          </c:when>
                                                                          <c:when test="${p.getStatus()=='Withdrawn'}">
                                                                              badge text-bg-secondary
                                                                          </c:when>
                                                                          <c:when test="${p.getStatus()=='Inactive'}">
                                                                              badge text-bg-dark
                                                                          </c:when>
                                                                          <c:when test="${p.getStatus()=='Submitted'}">
                                                                              badge text-bg-primary
                                                                          </c:when>
                                                                      </c:choose>">
                                                                    ${p.getStatus()}
                                                                </span>
                                                            </div>
                                                        </div>        
                                                    </li>
                                                    <li class="list-group-item">
                                                        <div class="row">
                                                            <div class="col-6">
                                                                Package: ${p.getPackageName()}
                                                            </div>
                                                            <div class="col-6 ml-3">
                                                                Registration time: ${p.getRegistrationTime()==null? "N/A":p.getRegistrationTime()}
                                                            </div>
                                                        </div>
                                                    </li>
                                                    <li class="list-group-item">
                                                        <div class="row">
                                                            <div class="col-6">
                                                                Valid from: ${p.getValidFrom()==null? "N/A":p.getValidFrom()}
                                                            </div>
                                                            <div class="col-6 ml-3">
                                                                Valid to: ${p.getValidTo()==null? "N/A":p.getValidTo()}
                                                            </div>
                                                        </div>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                        <div class="col-md-2 mt-md-3">
                                            <h5>Total Cost:</h5>
                                            <h5>${Integer.valueOf(p.getTotalCost()*1000)} VND</h5>
                                            <diV class="row">
                                                <!-- Button buy trigger modal -->
                                                <div class="col-xl-4">
                                                    <button class="btn btn-success" 
                                                            ${!matchActive.contains(p.getStatus())?"":"disabled"}
                                                            data-bs-toggle="modal" 
                                                            data-bs-target=".modalBuy${p.getRegistrationId()}"
                                                            >
                                                        Buy
                                                    </button>
                                                </div>
                                                <div class="col-xl-6">
                                                    <button type="button" class="btn btn-danger" 
                                                            data-bs-toggle="modal" 
                                                            ${!matchActive.contains(p.getStatus())?"":"disabled"}
                                                            data-bs-target=".modalCancel${p.getRegistrationId()}">
                                                        Cancel
                                                    </button>
                                                </div>
                                            </diV>
                                            <div class="row mt-3">
                                                <div class="col-xl-4">
                                                    <!-- Button trigger modal -->
                                                    <!-- currently do nothing -->
                                                    <button type="button" class="btn btn-warning" 
                                                            data-bs-toggle="modal" 
                                                            ${!matchActive.contains(p.getStatus())?"":"disabled"}
                                                            data-bs-target=".modalEdit${p.getRegistrationId()}">
                                                        Edit
                                                    </button>
                                                </div>
                                                <div class="col-xl-6">
                                                    <!-- Button trigger report modal -->
                                                    <!-- currently do nothing -->
                                                    <button type="button" class="btn btn-primary" 
                                                            data-bs-toggle="modal" 
                                                            data-bs-target=".modalReport${p.getRegistrationId()}">
                                                        Report
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                    <nav>
                        <ul class="pagination">
                            <!-- get all pages -->
                            <c:forEach begin="${1}" end="${requestScope.num}" var="i">
                                <li class="page-item">
                                    <a class="page-link ${i==page?"active":""}" href="user/MyRegistrations?${filter}page=${i}">${i}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </nav>
                </div>
            </div>
        </main>
        <%@include file="/common/footer.jsp" %>
        <!--Modals-->
        <c:forEach items="${requestScope.data}" var="p">
            <!-- Modal Buy -->
            <!-- Check transaction when the modal is closed -->
            <div class="modal modalBuy${p.getRegistrationId()}"
                 tabindex="-1"
                 role="dialog" 
                 data-bs-backdrop="static"
                 data-bs-keyboard="false">
                <div class="modal-dialog modal-dialog-centered" 
                     role="document">
                    <div class="modal-content">
                        <div class="modal-header text-bg-success">
                            <h4>Payment For Registration</h4>
                            <button 
                                type="button" 
                                class="btn-close"
                                data-bs-dismiss="modal" 
                                aria-label="Close">
                            </button>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <h5>Please scan the QR below to pay</h5>
                                <!--style = 1 add background to QR
                                           = 0 no background
                                    logo = 1 add bank's logo
                                          = 0 no bank's logo
                                    isMask = 1 hide part of account
                                           = 0 show full account
                                    bg = 69 background code (there're a lot of backgrounds, ngoc chose 69)
                                -->
                                <img src="https://vietqr.co/api/generate/${bankCode}/${owner}/VIETQR.CO/${Integer.valueOf(p.getTotalCost()*1000)}/USER${userId}COURSE${p.getRegistrationId()}?style=1&logo=1&isMask=1&bg=22" 
                                     class="img-thumbnail qrimg" 
                                     alt="qrcode">
                            </div>
                        </div>
                        <div class="modal-footer row">
                            <div class="row">
                                <h5>Remember to check the captcha below</h5>
                            </div>
                            <div class="row">
                                <div class="col-8">
                                    <!-- add recaptcha to optimize pay method-->
                                    <div class="g-recaptcha" 
                                         data-sitekey="6LemYewpAAAAAI4V2BR_nIibN_L8sK23JPuU8MBo"
                                         >
                                    </div>
                                </div>
                                <!-- payment call checkPaid, check if the captcha is valid-->
                                <div class="col-4 payButton">
                                    <button type="button" 
                                            class="btn btn-primary pay${p.getRegistrationId()}" 
                                            onclick="checkPaid(${Integer.valueOf(p.getTotalCost()*1000)},
                                                            'USER${userId}COURSE${p.getRegistrationId()}',
                                            ${p.getRegistrationId()}, grecaptcha.getResponse())">
                                        Check Payment
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--Cancel Modal-->
            <div class="modal modalCancel${p.getRegistrationId()} "
                 tabindex="-1"
                 role="dialog"
                 data-bs-backdrop="static"
                 data-bs-keyboard="false">
                <div class="modal-dialog modal-dialog-centered" 
                     role="document">
                    <div class="modal-content">
                        <div class="modal-header text-bg-danger">
                            <h4>Registration Cancel</h4>
                        </div>
                        <div class="modal-body">
                            <form action="user/MyRegistrations">
                                <div class="mb-3 container row">
                                    <div class="card">
                                        <img src="public/thumbnails/${p.getSubjectImg()}" class="card-img-top" height="200" width="100">
                                        <div class="card-body">
                                            <h5 class="card-title">${p.getSubjectName()}</h5>
                                            <p class="card-text">
                                                <span>
                                                    Registration's Id: ${p.getRegistrationId()}
                                                </span><br>
                                                <span>
                                                    Package: ${p.getPackageName()}
                                                </span><br>
                                                <span>
                                                    Total Cost: ${Integer.valueOf(p.getTotalCost()*1000)} VND
                                                </span><br>
                                                <input type="hidden" name="cancelId" value="${p.getRegistrationId()}">
                                                <input type="hidden" name="service" value="cancel">
                                            </p>
                                        </div>
                                    </div>
                                </div>
                                <div>
                                    <h5>Do you really want to cancel this registration?</h5>
                                    <div class="d-flex flex-row-reverse">
                                        <div class="p-2">
                                            <button type="submit" class="btn btn-danger">Confirm cancellation</button>
                                        </div>
                                        <div class="p-2">
                                            <button type="button" 
                                                    class="btn btn-secondary ml-3"
                                                    data-bs-dismiss="modal" 
                                                    aria-label="Close">
                                                Decline
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Modal Edit -->
            <div class="modal modalEdit${p.getRegistrationId()} "
                 tabindex="-1"
                 role="dialog" >
                <div class="modal-dialog modal-dialog-centered" 
                     role="document">
                    <div class="modal-content">
                        <div class="modal-header text-bg-warning">
                            <h4>Subject Register</h4>
                            <button type="button" 
                                    class="btn-close" 
                                    data-bs-dismiss="modal" 
                                    aria-label="Close">
                            </button>
                        </div>
                        <div class="modal-body">
                            <!-- It's Subject Register Popup - for edit registration -->
                            <c:import url="SubjectRegisterUser.jsp">
                                <c:param name="service" value="editRegist"/>
                                <c:param name="subjectId" value="${p.getSubjectId()}"/>
                                <c:param name="thumbnail" value="${p.getSubjectImg()}"/>
                                <c:param name="subjectName" value="${p.getSubjectName()}"/>
                                <c:param name="registId" value="${p.getRegistrationId()}"/>
                                <c:param name="registPack" value="${p.getPackageName()}"/>
                            </c:import>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Modal Report -->
            <div class="modal modalReport${p.getRegistrationId()} "
                 tabindex="-1"
                 role="dialog" >
                <div class="modal-dialog modal-dialog-centered" 
                     role="document">
                    <div class="modal-content">
                        <div class="modal-header text-bg-primary">
                            <h4>Registration Report</h4>
                            <button type="button" 
                                    class="btn-close" 
                                    data-bs-dismiss="modal" 
                                    aria-label="Close">
                            </button>
                        </div>
                        <div class="modal-body">
                            <h4>We are working on Registration Report pop-up. Thank you for your visit!</h4>
                            <img src="https://static.vecteezy.com/system/resources/previews/003/857/417/original/people-working-in-the-office-free-vector.jpg"
                                 alt="alt"
                                 height="430"
                                 width="450"/>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </body>
</html>
