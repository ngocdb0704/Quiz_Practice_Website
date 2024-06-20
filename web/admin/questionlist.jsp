<%-- 
    Document   : questionlist
    Created on : Jun 11, 2024, 7:21:24 AM
    Author     : hoapmhe173343
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="myTags" tagdir="/WEB-INF/tags" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Question List</title>
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
                <h1>Question Management</h1>
                <!-- Filters Section -->
                <div class="filters">
                    <form method="get" action="admin/questionlist">
                        <label for="subject">Subject:</label>
                        <select name="subjectId">
                            <option value="0">All subjects</option>
                            <c:forEach var="entry" items="${subjectMap.entrySet()}">
                                <option value="${entry.key}">${entry.value}</option>
                            </c:forEach>
                        </select>

                        <label for="lesson">Lesson:</label>
                        <select id="lesson" name="lesson">
                            <!-- Add options dynamically or statically -->
                        </select>
                        
                        <label for="level">Level:</label>
                        <select name="level">
                            <option value="0">All Levels</option>
                            <c:forEach var="entry" items="${levelMap}">
                                <option value="${entry.key}">${entry.value}</option>
                            </c:forEach>
                        </select>

                        <label for="status">Status:</label>
                        <select name="status">
                            <option value="0">All status</option>
                            <c:forEach var="entry" items="${statusMap}">
                                <option value="${entry.key}">${entry.value}</option>
                            </c:forEach>
                        </select>
                        
                        <label for="searchContent"></label>
                        <input type="text" name="searchContent" placeholder="Search by Content"/>
                        <button type="submit">Filter</button>
                    </form>
                </div>

                <%-- import question button --%>
                <div class="d-flex justify-content-end mb-3">
                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#importquestionModal">
                        Import question
                    </button>
                </div>
                <div class="modal fade" id="importquestionModal" tabindex="-1" aria-hidden="true">
                    <div class="modal-dialog modal-xl">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title">Import question</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <iframe src="importquestion" title="Import question" width="100%" height="400px" style=";"></iframe>
                            </div>

                        </div>
                    </div>
                </div>
                <!-- Questions List Section -->
                <table>
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>ID</th>
                            <th>Question Content</th>
                            <th>Subject</th>
                            <th>Lesson</th>
                            <th>Level</th>
                            <th>Show/Hide</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="question" items="${listQuestion}" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${question.questionID}</td>
                                <td>${question.questionName}</td>
                                <td>${subjectMap[question.subjectID]}</td>
                                <td>${question.lessonID}</td>
                                <td>${levelMap[question.level]}</td>
                                <td> <input type="checkbox" 
                                            <c:if test="${question.status == 1}">checked</c:if>
                                            onchange="updateStatus(${question.questionID}, this.checked)"></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>



                <%--pagging--%>
                <myTags:Paginator
                    className="mt-3 d-flex justify-content-center"
                    total="${totalPage}"
                    size="1"
                    current="${currentPage}"
                    url="admin/questionlist"
                    />
            </main>
        </div>
    </body>
</html>
