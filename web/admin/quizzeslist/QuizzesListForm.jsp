<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="app.entity.QuizType" %>

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
                    <option ${param.subjectIds eq '-1' ? 'selected' : ''} value="-1">
                        All
                    </option>
                    <c:forEach items="${subjects}" var="subject">
                        <option ${subject.getId().toString() eq param.subjectIds ? 'selected' : ''} value="${subject.getId()}">
                            ${subject.getSubjetName()}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group col-md-4">
                <label class="form-label" for="quizTypes">Quiz Type</label>
                <select id="quizTypes" name="quizTypes" class="form-control">
                    <option ${param.quizTypes eq '-1' ? 'selected' : ''} value="-1">All</option>
                    <c:forEach var="type" items="${QuizType.values()}">
                        <option value="${type.toInt()}" ${param.quizTypes eq type.toInt().toString() ? 'selected' : ''}>
                            ${type.toString()}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="mt-3 d-flex justify-content-end gap-2">
                <a href="admin/quizzeslist?published=${param.published}" class="btn btn-outline-danger">Reset</a>
                <button type="submit" class="btn btn-primary">Search</button>
            </div>
        </div>
    </div>
</form>