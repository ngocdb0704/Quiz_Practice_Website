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
        <script src="public/js/MyRegistration.js"></script>
        <!-- Script google reCaptcha -->
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
    </head>
    <body>
        <%@include file="/common/header.jsp" %>
        <div class="row">
            <aside class="col-3 sbar">
                <div class="row mb-3">
                    <c:set var="cat" value="${requestScope.list}"/>
                    <c:set var="check" value="${requestScope.check}"/>
                    <c:set var="key" value="${requestScope.key}"/>
                    <c:set var="status" value="${requestScope.listOfStatus}"/>
                    <c:set var="checkStatus" value="${requestScope.checkStatus}"/>
                    <c:set var="userId" value="${requestScope.userId}"/>
                    <c:set var="bankCode" value="mb"/>
                    <c:set var="owner" value="0886799110"/>
                    <form action="RegistrationController">
                        <div class="mb-3">
                            <div class="row card-body container justify-content-center">
                                <label for="searchKey">Search Subject By Name</label>
                                <input class="col-8" id="searchKey" type="text" value="${key}" name="key">
                                <button class="col-3" onclick="this.form.submit()">
                                    <i class="bi bi-search"></i>
                                </button>
                            </div>
                        </div>
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item">
                                Status
                                <!-- status category tree -->
                                <ul class="list-group list-group-flush">
                                    <c:forEach begin="0" end="${status.size()-1}" var="index">
                                        <li class="list-group-item">
                                            <input class="form-check-input" type="checkbox" name="idStatus"
                                                   value="${status.get(index)}"
                                                   ${checkStatus[index]?"checked":""}
                                                   onclick="this.form.submit()"
                                                   >
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
                                        </li>
                                    </c:forEach>
                                </ul>
                            </li>
                        </ul>
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item">
                                Subject Categories
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
                                                ${cat.get(i).getCateName()}
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
                                                                ${cat.get(ii).getCateName()}
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
                                                                                ${cat.get(iii).getCateName()}
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
                <c:set var="totalCost" value="${requestScope.total}"/>
                <h1><span>List of Registrations</span> </h1>
                <h3><span>Total Cost: ${totalCost}$</span></h3>
                <c:set var="page" value="${requestScope.page}"/>
                <c:set var="filter" value="${requestScope.sendFilter}"/>
                <nav>
                    <ul class="pagination">
                        <!-- get all pages -->
                        <c:forEach begin="${1}" end="${requestScope.num}" var="i">
                            <li class="page-item">
                                <a class="page-link ${i==page?"active":""}" href="RegistrationController?${filter}page=${i}">${i}</a>
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
                                        <img src="${p.subjectImg}"
                                             class="img-fluid rounded-start" 
                                             width="300" height="180">
                                    </div>
                                    <div class="col-md-6">
                                        <div class="card-body">
                                            <h5 class="card-title">${p.subjectName}</h5>
                                            <h6>
                                                <span>ID:${p.registrationId}</span>  
                                                <span class="">
                                                    Status: 
                                                    <!-- change status's appearance -->
                                                    <span class="
                                                          <c:choose>
                                                              <c:when test="${p.status=='Active'}">
                                                                  badge text-bg-success
                                                              </c:when>
                                                              <c:when test="${p.status=='Pending Approval'}">
                                                                  badge text-bg-warning
                                                              </c:when>
                                                              <c:when test="${p.status=='Withdrawn'}">
                                                                  badge text-bg-secondary
                                                              </c:when>
                                                              <c:when test="${p.status=='Inactive'}">
                                                                  badge text-bg-dark
                                                              </c:when>
                                                              <c:when test="${p.status=='Submitted'}">
                                                                  badge text-bg-primary
                                                              </c:when>
                                                          </c:choose>
                                                          ">
                                                        ${p.status}
                                                    </span>
                                                </span>
                                            </h6>
                                            <ul class="list-group list-group-flush">
                                                <li class="list-group-item">
                                                    <span>Registered: ${p.packageName}</span>
                                                    <span>on ${p.registrationTime==null? "N/A":p.registrationTime}</span>
                                                </li>
                                                <li class="list-group-item">
                                                    <span>Valid from 
                                                        ${p.validFrom==null? "N/A":p.validFrom}
                                                    </span>
                                                    <span class="ml-2">to ${p.validTo==null? "N/A":p.validTo}</span>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="col-md-2 mt-md-5">
                                        <h5>${Integer.valueOf(p.totalCost*1000)} VND</h5>
                                        <diV class="row">
                                            <!-- Button buy trigger modal -->
                                            <div class="col-4">
                                                <button class="btn btn-success" 
                                                        ${p.status.equals("Submitted")?"":"disabled"}
                                                        data-bs-toggle="modal" 
                                                        data-bs-target=".modalBuy${p.registrationId}"
                                                        >
                                                    Buy
                                                </button>
                                                <!-- Modal Buy -->
                                                <!-- Check transaction when the modal is closed -->
                                                <div class="modal fade modalBuy${p.registrationId}"
                                                     tabindex="-1"
                                                     role="dialog" 
                                                     data-bs-backdrop="static"
                                                     data-bs-keyboard="false">
                                                    <div class="modal-dialog modal-dialog-centered" 
                                                         role="document">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
                                                                </button>
                                                            </div>
                                                            <div class="modal-body">
                                                                <h4>Please Scan The QR Below UwU</h4>
                                                                <!--style = 1 add background to QR
                                                                           = 0 no background
                                                                    logo = 1 add bank's logo
                                                                          = 0 no bank's logo
                                                                    isMask = 1 hide part of account
                                                                           = 0 show full account
                                                                    bg = 69 background code (there're a lot of backgrounds, ngoc chose 69)
                                                                -->
                                                                <img src="https://vietqr.co/api/generate/${bankCode}/${owner}/VIETQR.CO/${Integer.valueOf(p.totalCost*1000)}/USER${userId}COURSE${p.registrationId}?style=1&logo=1&isMask=1&bg=22" 
                                                                     class="img-thumbnail qrimg" 
                                                                     alt="qrcode">
                                                            </div>
                                                            <div class="modal-footer row">
                                                                <!-- add recaptcha to optimize pay method-->
                                                                <div class="row">
                                                                    <div class="g-recaptcha" 
                                                                         data-sitekey="6LemYewpAAAAAI4V2BR_nIibN_L8sK23JPuU8MBo"
                                                                         >
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="col-9">

                                                                    </div>
                                                                    <!-- payment call checkPaid, check if the captcha is valid-->
                                                                    <div class="col-3 payButton">
                                                                        <button type="button" 
                                                                                class="btn btn-primary pay${p.registrationId}" 
                                                                                data-bs-dismiss="modal"
                                                                                onclick="checkPaid(${Integer.valueOf(p.totalCost*1000)},
                                                                                                'USER${userId}COURSE${p.registrationId}',
                                                                                ${p.registrationId}, grecaptcha.getResponse())">
                                                                            Done
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-6">
                                                <button class="btn btn-danger" 
                                                        ${p.status.equals("Submitted")?"":"disabled"}
                                                        onclick="cancellation(${p.registrationId})">
                                                    Cancel
                                                </button>
                                            </div>
                                        </diV>
                                        <div class="row mt-3">
                                            <div class="col-4">
                                                <!-- Button trigger modal -->
                                                <!-- currently do nothing -->
                                                <button type="button" class="btn btn-warning" 
                                                        data-bs-toggle="modal" 
                                                        ${p.status.equals("Submitted")?"":"disabled"}
                                                        data-bs-target=".modalEdit${p.registrationId}">
                                                    Edit
                                                </button>
                                                <!-- Modal Edit -->
                                                <div class="modal fade modalEdit${p.registrationId}"
                                                     tabindex="-1"
                                                     role="dialog" >
                                                    <div class="modal-dialog modal-dialog-centered" 
                                                         role="document">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <button type="button" 
                                                                        class="btn-close" 
                                                                        data-bs-dismiss="modal" 
                                                                        aria-label="Close">
                                                                </button>
                                                            </div>
                                                            <div class="modal-body">
                                                                <jsp:include page="/SubjectRegisterPopUp.jsp">
                                                                    <jsp:param name="registId" value="${p.registrationId}"/>
                                                                </jsp:include>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-6">
                                                <!-- add report button, currently do nothing -->
                                                <button class="btn btn-primary" 
                                                        ${p.status.equals("Submitted")?"":"disabled"}>
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
            </div>
        </div>
        <%@include file="/common/footer.jsp" %>
    </body>
</html>
