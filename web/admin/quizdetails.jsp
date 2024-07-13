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
        <script>
            $(document).ready(function () {
                // Function to add a new row
                $("#addRowBtn").click(function () {
                    var lessonOptions = '';
            <c:forEach items="${lessonList}" var="c">
                    lessonOptions += '<option value="${c}">Lesson ${c}</option>';
            </c:forEach>

                    var newRow = `<tr>
                                    <td>
                                        <select name="lessonId" style="width: 100%">
            ${lessonOptions}
                                        </select>
                                    </td>
                                    <td><input type="number" name="numberQuestion" style="width: 100%"></td>
                                    <td><button type="button" class="btn btn-danger deleteRowBtn">Delete</button></td>
                                  </tr>`;
                    $("#questionsTable tbody").append(newRow);
                });

                // Function to delete a row
                $(document).on('click', '.deleteRowBtn', function () {
                    $(this).closest('tr').remove();
                });
            });
            
            function deleteQuiz() {
            var quizId = ${quiz.quizId}; // Ensure quiz ID is available in the JSP
            if (confirm("Are you sure you want to delete this quiz?")) {
                $.ajax({
                    url: "${pageContext.request.contextPath}/admin/deletequiz",
                    type: "POST",
                    data: { quizId: quizId },
                    success: function(response) {
                        if (response.status === 'success') {
                            alert("Quiz deleted successfully.");
                            window.location.href = "${pageContext.request.contextPath}/admin/quizzeslist";
                        } else {
                            alert("Error deleting quiz: " + response.message);
                        }
                    },
                    error: function(xhr, status, error) {
                        alert("An error occurred: " + error);
                    }
                });
            }
        }
        </script>
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
                                <a href="${pageContext.request.contextPath}/admin/quizzeslist" class="btn btn-secondary">Back</a>
                                <button type="button" class="btn btn-danger" onclick="deleteQuiz()">Delete Quiz</button>
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
                                    <table id="questionsTable">
                                        <thead>
                                            <tr>
                                                <th>Name of lesson</th>
                                                <th>Number question</th>
                                                <th></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${listGroupQuestion}" var="l">
                                                <tr>
                                                    <td>
                                                        <select name="lessonId" style="width: 100%">
                                                            <c:forEach items="${lessonList}" var="c">
                                                                <option value="${c}" <c:if test="${c == l.lessonId}">selected</c:if>>Lesson ${c}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td><input type="number" name="numberQuestion" value="${l.questionCount}" style="width: 100%"></td>
                                                    <td><button type="button" class="btn btn-danger deleteRowBtn">Delete</button></td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <button id="addRowBtn" class="btn btn-primary" type="button">Add</button>
                                <div class="mt-2">
                                    <button type="submit" class="btn btn-primary">Save</button>
                                    <a href="${pageContext.request.contextPath}/admin/quizzeslist" class="btn btn-secondary">Back</a>
                                    <button type="button" class="btn btn-danger" onclick="deleteQuiz()">Delete Quiz</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </body>
</html>
