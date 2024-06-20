<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="table-responsive">
<table class="admin-table table table-striped table-bordered table-hover sortable-theme-bootstrap" data-sortable>
    <thead class="thead-dark">
        <tr>
            <th></th>
            <th>ID</th>
            <th>Subject</th>
            <th>Quiz Name</th>
            <th># Questions</th>
            <th># Attempts</th>
            <th>Level</th>
            <th>Duration (minutes)</th>
            <th>Pass Rate</th>
            <th>Type</th>
            <th>Status</th>
            <th></th>
        </tr>
    </thead>
    <tbody x-ref="tableBody">
        <c:forEach var="quiz" items="${result.getResults()}">
            <tr>
                <td style="text-align: center; vertical-align: middle">
                    <input
                        class="checkbox-big"
                        type="checkbox"
                        name="ids"
                        @change="toggle('${quiz.getQuizId()}', '${quiz.getQuizName()}', ${quiz.isValid()}, ${quiz.getNumberOfAttempts()})"
                        :checked="map['${quiz.getQuizId()}']"
                        data-id="${quiz.getQuizId()}"
                        data-title="${quiz.getQuizName()}"
                        data-valid="${quiz.isValid()}"
                        data-attempts="${quiz.getNumberOfAttempts()}"
                    >
                </td>
                <td>${quiz.getQuizId()}</td>
                <td>${quiz.getSubjectName()}</td>
                <td>${quiz.getQuizName()}</td>
                <td>
                    ${quiz.getQuestionCount()}
                    <c:if test="${quiz.getQuestionCount() == 0}">
                        <i class="bi bi-exclamation-triangle"></i>
                    </c:if>
                </td>
                <td>
                    ${quiz.getNumberOfAttempts()}
                </td>
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
                        ${quiz.isPublished() ? 'Published' : 'Archived'}
                    </span>
                </td>
                <td>
                    <a href="admin/quizdetails">
                        ${quiz.getNumberOfAttempts() > 0 ? 'View' : 'Edit'}
                    </a>
                </td>
            </tr>
        </c:forEach>

        <c:if test="${result.getResults().isEmpty()}">
            <tr>
                <td colspan="12" class="text-center">No results</td>
            </tr>
        </c:if>
    </tbody>
</table>
</div>