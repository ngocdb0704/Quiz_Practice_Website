<%-- 
    Document   : My Registration
    Created on : 12 thg 5, 2024, 16:59:06
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector"%>
<%@page import="app.entity.Registration"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Registration</title>
        <%@include file="/common/ImportBootstrap.jsp" %>
        <script src="public/js/bootstrap/MyRegistration.js"></script>
        <link rel="stylesheet" href="public/css/bootstrap/MyRegistration.css"/>
    </head>
    <body>
        <%@include file="/common/header.jsp" %>
        <div class="row">
            <aside class="col-3 sbar">
                <div class="row mb-3">
                    <c:set var="cat" value="${requestScope.list}"/>
                    <c:set var="check" value="${requestScope.check}"/>
                    <form action="RegistrationController">
                        <c:forEach begin="0" end="${cat.size()-1}" var="i">
                            <ul class="list-group list-group-flush">
                                <c:if test="${cat.get(i).getCateParentId()==0}">
                                    <li class="list-group-item">
                                        <input type="checkbox" name="idTier1" 
                                               value="${cat.get(i).getCateId()}"
                                               ${check[i]?"checked":""}
                                               onclick="this.form.submit()"/>
                                        ${cat.get(i).getCateName()}
                                        <c:set var="parentTier1" value="${cat.get(i).getCateId()}"/>
                                        <c:forEach begin="0" end="${cat.size()-1}" var="ii">
                                            <ul class="list-group list-group-flush">
                                                <c:if test="${cat.get(ii).getCateParentId()==parentTier1}">
                                                    <li class="list-group-item">
                                                        <input type="checkbox" name="idTier2" 
                                                               value="${cat.get(ii).getCateId()}"
                                                               ${check[ii]?"checked":""}
                                                               onclick="this.form.submit()"/>
                                                        ${cat.get(ii).getCateName()}
                                                        <c:set var="parentTier2" value="${cat.get(ii).getCateId()}"/>
                                                        <c:forEach begin="0" end="${cat.size()-1}" var="iii">
                                                            <ul class="list-group list-group-flush">
                                                                <c:if test="${cat.get(iii).getCateParentId()==parentTier2}">
                                                                    <li class="list-group-item">
                                                                        <input type="checkbox" name="idTier3" 
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
                    </form>
                </div>
                <div class="row mb-3">
                    <a class="link-primary mt-3"
                       href="ContactUs.jsp" target="_blank"
                       rel="noopener noreferrer">Contact Us</a>
                </div>
            </aside>
            <div class="col-9 mt-3">
                <h1>List of Registrations</h1>
                <c:set var="page" value="${requestScope.page}"/>
                <c:set var="filter" value="${requestScope.sendFilter}"/>
                <nav>
                    <ul class="pagination">
                        <c:forEach begin="${1}" end="${requestScope.num}" var="i">
                            <li class="page-item">
                                <a class="page-link ${i==page?"active":""}" href="RegistrationController?${filter}page=${i}">${i}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </nav>
                <ul class="list-group">
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
                                                <span>ID:${p.registrationId}</span>  <span>Status: ${p.status}</span>
                                            </h6>
                                            <ul class="list-group list-group-flush">
                                                <li class="list-group-item">
                                                    <span>Registered: ${p.packageName}</span>
                                                    <span>on ${p.registrationTime}</span>
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
                                        <h5>${p.totalCost}$</h5>
                                        <!-- Button trigger modal -->
                                        <!-- Remove onclick, if status is not Submitted then disabled -->
                                        <!-- add button below -->
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
