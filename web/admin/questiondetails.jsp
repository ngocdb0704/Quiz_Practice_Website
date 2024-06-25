<%-- 
    Document   : questiondetails
    Created on : Jun 23, 2024, 12:11:12 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Question Details</title>
        <%@include file="/common/ImportBootstrap.jsp" %>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/questionlist.css" />
        <link rel="stylesheet" href="admin/common/admin-common.css">
        <script src="admin/common/admin-common.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/public/js/questionlist.js"></script>
    </head>
    <body>
        <div class="admin-layout">
            <%@include file="/admin/common/admin-header.jsp" %>
            <%@include file="/admin/common/admin-sidebar.jsp" %>
            <main class="admin-main">
                <h1>Question Details</h1>
                <div class="question-details">
                    <table class="table table-bordered">
                        <tr>
                            <th>Question Content:</th>
                            <td>${question.questionName}</td>
                        </tr>
                        <tr>
                            <th>Lesson:</th>
                            <td>Lesson ${question.lessonID}</td>
                        </tr>
                        <tr>
                            <th>Level:</th>
                            <td>${question.level}</td>
                        </tr>
                        <tr>
                            <th>Status:</th>
                            <td>
                                <c:if test="${question.status == 1}">Show</c:if>
                                <c:if test="${question.status == 2}">Hide</c:if>
                            </td>
                        </tr>
                    </table>
                </div>

                <h2>Answer Options</h2>
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>Option</th>
                            <th>Content</th>
                            <th>Correct</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="answer" items="${question.answers}" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${answer.answerName}</td>
                                <td>
                                    <c:if test="${answer.isCorrect == 1}">
                                        Yes
                                    </c:if>
                                    <c:if test="${answer.isCorrect == 0}">
                                        No
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <a href="${pageContext.request.contextPath}/admin/questionlist" class="btn btn-secondary">Back to List</a>
            </main>
        </div>
    </body>
</html>
