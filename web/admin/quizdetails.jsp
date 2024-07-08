<%-- 
    Document   : quizdetails
    Created on : Jul 7, 2024, 11:11:15 AM
    Author     : hoapmhe173343
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quiz Details</title>
        <%@include file="/common/ImportBootstrap.jsp" %>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/questionlist.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/admin/common/admin-common.css">
        <script src="${pageContext.request.contextPath}/admin/common/admin-common.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/public/js/questionlist.js"></script>
    </head>
    <body>
        <div class="admin-layout">
            <%@include file="/admin/common/admin-header.jsp" %>
            <%@include file="/admin/common/admin-sidebar.jsp" %>

            <main class="admin-main">
                <div class="container mt-5">
                    <h2>Quiz Details</h2>
                    <ul class="nav nav-tabs" id="myTab" role="tablist">
                        <li class="nav-item" role="presentation">
                            <button class="nav-link active" id="overview-tab" data-bs-toggle="tab" data-bs-target="#overview" type="button" role="tab" aria-controls="overview" aria-selected="true">Overview</button>
                        </li>
                        <li class="nav-item" role="presentation">
                            <button class="nav-link" id="setting-tab" data-bs-toggle="tab" data-bs-target="#setting" type="button" role="tab" aria-controls="setting" aria-selected="false">Setting</button>
                        </li>
                    </ul>
                    <div class="tab-content" id="myTabContent">
                        <div class="tab-pane fade show active" id="overview" role="tabpanel" aria-labelledby="overview-tab">
                            <form class="mt-3" method="post" action="${pageContext.request.contextPath}/admin/quizzeslist/details">
                                <div class="mb-3">
                                    <label for="name" class="form-label">Name</label>
                                    <input type="text" class="form-control" id="name" name="name" value="${quiz.quizName}" placeholder="Exam 1">
                                </div>
                                <div class="mb-3">
                                    <label for="subject" class="form-label">Subject</label>
                                    <select class="form-select" id="subject" name="subject">
                                        <c:forEach var="entry" items="${subjectMap.entrySet()}">
                                            <option value="${entry.key}" <c:if test="${quiz.subjectId == entry.key}">selected</c:if>>
                                                ${entry.value}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="mb-3 row">
                                    <div class="col">
                                        <label for="examLevel" class="form-label">Exam Level</label>
                                        <select class="form-select" id="examLevel" name="examLevel">
                                            <option value="0" <c:if test="${quiz.level.toInt() == 0}">selected</c:if>>Easy</option>
                                            <option value="1" <c:if test="${quiz.level.toInt() == 1}">selected</c:if>>Medium</option>
                                            <option value="2" <c:if test="${quiz.level.toInt() == 2}">selected</c:if>>Hard</option>
                                        </select>

                                        </div>
                                        <div class="col">
                                            <label for="duration" class="form-label">Duration (minutes)</label>
                                            <input type="number" class="form-control" id="duration" name="duration" value="${quiz.durationInMinutes}">
                                    </div>
                                </div>
                                <div class="mb-3 row">
                                    <div class="col">
                                        <label for="passRate" class="form-label">Pass Rate (%)</label>
                                        <input type="number" class="form-control" id="passRate" name="passRate" value="${quiz.passRate}">
                                    </div>
                                    <div class="col">
                                        <label for="quizType" class="form-label">Quiz Type</label>
                                        <select class="form-select" id="quizType" name="quizType">
                                            <option value="0" <c:if test="${quiz.type.toInt() == 0}">selected</c:if>>SIMULATION</option>
                                            <option value="1" <c:if test="${quiz.type.toInt() == 1}">selected</c:if>>LESSON_QUIZ</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="mb-3">
                                    <label for="description" class="form-label">Description</label>
                                    <textarea class="form-control" id="description" name="description" rows="3">${quiz.description}</textarea>
                                </div>
                                <button type="submit" class="btn btn-primary">Submit</button>
                                <button type="button" class="btn btn-secondary" onclick="history.back()">Back</button>
                            </form>
                        </div>
                        <div class="tab-pane fade" id="setting" role="tabpanel" aria-labelledby="setting-tab">
                            <form class="mt-3" method="post" action="${pageContext.request.contextPath}/admin/quizzeslist/details">
                                <div class="mb-3">
                                    <label for="totalQuestions" class="form-label">Number of question</label>
                                    <input type="number" class="form-control" id="totalQuestions" name="totalQuestions" value="${quiz.totalQuestion}">
                                </div>
                               
                                <div class="mb-3">
                                    <label class="form-label">Choose the number of questions in groups by lesson</label>
                                    <c:forEach var="group" items="${quiz.questionGroups}">
                                        <div class="input-group mb-2">
                                            <select class="form-select" name="questionGroups">
                                                <option>${group.name}</option>
                                                <!-- Add more options as needed -->
                                            </select>
                                            <input type="number" class="form-control" value="${group.number}">
                                            <button class="btn btn-danger" type="button">Delete</button>
                                        </div>
                                    </c:forEach>
                                    <button class="btn btn-primary" type="button">Add</button>
                                </div>
                                <button type="submit" class="btn btn-primary">Save</button>
                                <button type="button" class="btn btn-secondary" onclick="history.back()">Back</button>
                            </form>
                        </div>
                    </div>
                </div>
            </main>
        </div>

    </body>
</html>
