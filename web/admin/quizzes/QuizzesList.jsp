<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="myTags" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quizzes List</title>
        <%@include file="/common/ImportBootstrap.jsp" %>
        <link rel="stylesheet" href="admin/common/admin-common.css">
        <link rel="stylesheet" href="public/css/sortable/sortable-theme-bootstrap.css">
        <script src="admin/common/admin-common.js"></script>
        <script src="public/js/sortable/sortable.min.js"></script>
    </head>
    <body>
        <div class="admin-layout">
            <%@include file="/admin/common/admin-header.jsp" %>
            <%@include file="/admin/common/admin-sidebar.jsp" %>
            <main class="admin-main">
                <div class="container">
                    <h2 class="my-4">
                        <i class="bi bi-clipboard-check-fill"></i> Quizzes List
                    </h2>
                    <ul class="nav nav-tabs">
                        <li class="nav-item">
                            <a
                                class="nav-link ${empty param.published or param.published eq '1' ? 'active' : ''}"
                                href="admin/quizzeslist"
                            >Published</a>
                        </li>
                        <li class="nav-item">
                            <a
                                class="nav-link ${param.published eq '0' ? 'active' : ''}"
                                href="admin/quizzeslist?published=0"
                            >Draft</a>
                        </li>
                    </ul>
                    <form class="card" action="admin/quizzeslist" method="get">
                        <input type="hidden" name="published" value="${empty param.published ? '1' : param.published}">
                        <div class="card-body">
                            <h5 class="card-title fw-bold">
                                <i class="bi bi-funnel-fill"></i>
                                Filter
                            </h5>
                            <div class="row">
                                <div class="form-group col-md-4">
                                    <label class="form-label" for="quizName">Quiz Name</label>
                                    <div class="input-group">
                                        <span class="input-group-text">
                                            <i class="bi bi-search"></i>
                                        </span>
                                        <input type="text" value="${param.quizName}" class="form-control" id="quizName" name="quizName" placeholder="Enter quiz name">
                                    </div>
                                </div>
                                <div class="form-group col-md-4">
                                    <label class="form-label" for="subjectIds">Subjects</label>
                                    <select id="subjectIds" name="subjectIds" class="form-control">
                                        <option selected>Choose...</option>
                                    </select>
                                </div>
                                <div class="form-group col-md-3">
                                    <label class="form-label" for="quizTypes">Quiz Type</label>
                                    <select id="quizTypes" name="quizTypes" class="form-control">
                                        <option selected>Choose...</option>
                                        <c:forEach var="type" items="${QuizInformation.QuizType.values()}">
                                            <option value="${type}">${type}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-auto d-flex flex-column justify-content-end">
                                    <button type="submit" class="btn btn-primary">Search</button>
                                </div>
                            </div>
                        </div>
                    </form>
                    <div class="my-4 card">
                        <div class="card-body">
                            <h5 class="card-title fw-bold">
                                <i class="bi bi-wrench"></i>
                                Actions
                            </h5>
                            <div class="d-flex mt-3 gap-2">
                                <button class="btn btn-primary">
                                    <i class="bi bi-check2-all"></i>
                                    Select All
                                </button>
                                <button class="btn btn-danger" disabled>
                                    <i class="bi bi-trash"></i>
                                    Delete Selected
                                </button>
                                <div class="flex-grow-1"></div>
                                <div class="flex-grow-1"></div>
                                <button class="btn btn-primary">
                                    <i class="bi bi-plus-circle"></i>
                                    Add New Quiz
                                </button>
                            </div>
                        </div>
                    </div>
                    <table class="admin-table table table-striped table-bordered table-hover sortable-theme-bootstrap" data-sortable>
                        <thead class="thead-dark">
                            <tr>
                                <th></th>
                                <th>ID</th>
                                <th>Quiz Name</th>
                                <th>Subject</th>
                                <th>Level</th>
                                <th>Duration (minutes)</th>
                                <th>Pass Rate</th>
                                <th>Type</th>
                                <th>Status</th>
                                <th>Updated Time</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="quiz" items="${result.getResults()}">
                                <tr>
                                    <td style="text-align: center; vertical-align: middle">
                                        <input class="checkbox-big" type="checkbox" name="ids">
                                    </td>
                                    <td>${quiz.getQuizId()}</td>
                                    <td>${quiz.getQuizName()}</td>
                                    <td>${quiz.getSubjectName()}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${quiz.getLevel() eq 'HARD'}">
                                                <c:set var="levelColor" value="danger" />
                                            </c:when>
                                            <c:when test="${quiz.getLevel() eq 'MEDIUM'}">
                                                <c:set var="levelColor" value="warning" />
                                            </c:when>
                                            <c:when test="${quiz.getLevel() eq 'EASY'}">
                                                <c:set var="levelColor" value="primary" />
                                            </c:when>
                                        </c:choose>
                                        <span class="badge w-100 text-bg-${levelColor}">
                                            ${quiz.getLevel().toString()}
                                        </span>
                                    </td>
                                    <td>${quiz.getDurationInMinutes()}</td>
                                    <td>${quiz.getPassRate()}%</td>
                                    <td>${quiz.getType().toString()}</td>
                                    <td>
                                        <span class="badge w-100 text-bg-${quiz.isPublished() ? 'primary' : 'secondary'}">
                                            ${quiz.isPublished() ? 'Published' : 'Draft'}
                                        </span>
                                    </td>
                                    <td>${quiz.getUpdatedTime()}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <myTags:Paginator
                        className="mt-3 d-flex justify-content-end"
                        current="${param.page}"
                        total="${result.getTotalPages()}"
                        size="1"
                        url="admin/quizzeslist"
                    />
                </div>
            </main>
        </div>
    </body>
</html>

