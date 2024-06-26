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
                    <form action="admin/answeraction" method="post">
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
                                <td>
                                    <c:if test="${question.level == 1}">Easy</c:if>
                                    <c:if test="${question.level == 2}">Medium</c:if>
                                    <c:if test="${question.level == 3}">Hard</c:if>
                                    </td>
                                </tr>
                                <tr>
                                    <th>Status:</th>
                                    <td>
                                        <input type="checkbox" 
                                        <c:if test="${question.status == 1}">checked</c:if>
                                        onchange="updateStatus(${question.questionID}, this.checked)"> Show/Hide
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>

                <h2>Answer Options</h2>
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>Option</th>
                            <th>Content</th>
                            <th>Correct</th>
                            <th>Action</th>
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
                                <td>
                                    <button type="button" class="btn btn-primary"
                                            onclick="editProductModal(this)">
                                        Edit
                                    </button>
                                    <button type="button" class="btn btn-danger" 
                                            onclick="deleteProductModal(${p.id})">
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <!-- Flex container for buttons -->
                <div class="d-flex justify-content-between align-items-center mt-3">
                    <div class="d-flex">
                        <a href="${pageContext.request.contextPath}/admin/questionlist" class="btn btn-secondary me-2">Back to List</a>
                        <p class="btn btn-info">Save change</p>
                    </div>
                    <!-- Add Answer Option Button as Icon -->
                    <button class="btn btn-primary" onclick="showAddOptionForm()">
                        <i class="bi bi-plus-square"> Add option</i>
                    </button>
                </div>

                <!-- Add Answer Option Form -->
                <div id="addOptionForm" style="display: none; margin-top: 20px;">
                    <h3>Add New Answer Option</h3>
                    <form action="admin/answeraction" method="post">
                        <input type="hidden" name="questionID" value="${question.questionID}">
                        <div class="mb-3">
                            <label for="answerName" class="form-label">Answer Content:</label>
                            <input type="text" class="form-control" id="answerName" name="answerName" required>
                        </div>
                        <div class="mb-3">
                            <label for="isCorrect" class="form-label">Correct:</label>
                            <select class="form-control" id="isCorrect" name="isCorrect" required>
                                <option value="1">Yes</option>
                                <option value="0">No</option>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-success">Add</button>
                        <button type="button" class="btn btn-secondary" onclick="hideAddOptionForm()">Cancel</button>
                    </form>
                </div>
            </main>
        </div>
    </body>
</html>
