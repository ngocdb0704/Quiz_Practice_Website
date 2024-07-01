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
                <form id="questionForm" action="admin/savechange" method="post">
                    <input type="hidden" name="questionID" value="${question.questionID}">
                    <h1>Question Details</h1>
                    <div class="question-details">
                        <table class="table table-bordered">
                            <tr>
                                <th>Subject:</th>
                                <td>
                                    <select class="form-select" name="subjectId">
                                        <c:forEach var="entry" items="${subjectMap.entrySet()}">
                                            <option value="${entry.key}" <c:if test="${question.subjectID == entry.key}">selected</c:if>>${entry.value}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <th>Question Content:</th>
                                <td>
                                    <input name="questionName" value="${question.questionName}" style="width: 100%">
                                </td>
                            </tr>
                            <tr>
                                <th>Lesson:</th>
                                <td>
                                    <select name="lessonID" class="form-select">
                                        <c:forEach var="lesson" items="${lessonList}">
                                            <option value="${lesson}" <c:if test="${question.lessonID == lesson}">selected</c:if>>Lesson ${lesson}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <th>Level:</th>
                                <td>
                                    <select name="level" class="form-select" style="width: 100%">
                                        <c:forEach var="entry" items="${levelMap.entrySet()}">
                                            <option value="${entry.key}" <c:if test="${question.level == entry.key}">selected</c:if>>${entry.value}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <th>Explanation:</th>
                                <td>
                                    <textarea name="explanation" style="width: 100%">${question.explanation}</textarea>
                                </td>
                            </tr>
                            <tr>
                                <th>Status:</th>
                                <td>
                                    <select name="status" class="form-select">
                                        <c:forEach var="entry" items="${statusMap.entrySet()}">
                                            <option value="${entry.key}" <c:if test="${question.status == entry.key}">selected</c:if>>${entry.value}</option>
                                        </c:forEach>
                                    </select>
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
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="answer" items="${question.answers}" varStatus="status">
                                <tr>
                                    <td>${status.index + 1}</td>
                                    <td>
                                        <input type="hidden" name="answerID" value="${answer.answerID}">
                                        <input name="answerName" value="${answer.answerName}" style="width: 100%">
                                    </td>
                                    <td>
                                        <select name="isCorrect">
                                            <option value="1" <c:if test="${answer.isCorrect == 1}">selected</c:if>>True</option>
                                            <option value="0" <c:if test="${answer.isCorrect == 0}">selected</c:if>>False</option>
                                        </select>
                                    </td>
                                    <td>
                                        <button type="button" class="btn btn-danger" 
                                                onclick="deleteOption(${answer.answerID}, ${question.questionID})">
                                            <div class="bi bi-trash"> Delete</div>
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <!-- Flex container for buttons -->
                    <a href="${pageContext.request.contextPath}/admin/questionlist" class="btn btn-secondary me-2">Back to List</a>
                    <input type="submit" value="Save change" class="btn btn-info">
                    <button type="button" class="btn btn-warning ms-2" onclick="resetForm()">Reset</button>
                </form>
                <!-- Add Answer Option Button as Icon -->
                <button class="btn btn-primary mt-3" onclick="showAddOptionForm()">
                    <div class="bi bi-plus-square"> Add option</div>
                </button>

                <!-- Add Answer Option Form -->
                <div id="addOptionForm" style="display: none; margin-top: 20px;">
                    <h3>Add New Answer Option</h3>
                    <div>
                        <input type="hidden" name="action" value="add">
                        <div class="mb-3">
                            <label for="answerName" class="form-label">Answer Content:</label>
                            <input type="text" class="form-control" id="answerName" name="answerName" required>
                        </div>
                        <div class="mb-3">
                            <label for="isCorrect" class="form-label">Correct:</label>
                            <select class="form-control" id="isCorrect" name="isCorrect" required>
                                <option value="0">No</option>
                                <option value="1">Yes</option>
                            </select>
                        </div>
                        <button type="button" class="btn btn-success"
                                onclick="addOption(${question.questionID})">Add</button>
                        <button type="button" class="btn btn-secondary" onclick="hideAddOptionForm()">Cancel</button>
                    </div>
                </div>
            </main>
        </div>
    </body>
</html>
