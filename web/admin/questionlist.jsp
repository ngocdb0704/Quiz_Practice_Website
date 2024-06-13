<%-- 
    Document   : questionlist
    Created on : Jun 11, 2024, 7:21:24 AM
    Author     : hoapmhe173343
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Question List</title>
        <%@include file="/common/ImportBootstrap.jsp" %>
        <link rel="stylesheet" href="common/admin-common.css">
        <script src="common/admin-common.js"></script>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/questionlist.css" />
    </head>
    <body>
        <div class="admin-layout">
            <%@include file="/common/admin-header.jsp" %>
            <%@include file="/common/admin-sidebar.jsp" %>
            <main class="admin-main">


                <%--pagging--%>
                <div class="pagination-area mt-50">
                    <div class="page-number">
                        <ul>
                            <c:forEach begin="1" end="${totalPage}" var="pageNumber">
                                <li class="${pageNumber == currentPage ? 'active' : ''}">
                                    <a href="questionlist?page=${pageNumber}">${pageNumber}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </main>
        </div>
    </body>
</html>
