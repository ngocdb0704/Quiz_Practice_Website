<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add Quiz</title>
    <%@ include file="/common/ImportBootstrap.jsp" %>
    <link rel="stylesheet" href="admin/common/admin-common.css">
    <style>
        .error-message {
            color: red;
            font-size: 0.875em;
        }
    </style>
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            function validateForm() {
                const lessonIds = document.getElementById('lessonId').value.split(',');
                const numberQuestions = document.getElementById('numberQuestion').value.split(',');

                if (lessonIds.length !== numberQuestions.length) {
                    alert('The number of Lesson IDs must match the number of questions.');
                    return false;
                }

                for (const id of lessonIds) {
                    if (isNaN(id.trim())) {
                        alert('Lesson IDs must be numbers.');
                        return false;
                    }
                }

                for (const count of numberQuestions) {
                    if (isNaN(count.trim())) {
                        alert('Number of questions must be numbers.');
                        return false;
                    }
                }

                return true;
            }

            document.getElementById('addRowBtn').addEventListener('click', function () {
                var table = document.getElementById('lessonTable').getElementsByTagName('tbody')[0];
                var newRow = table.insertRow();
                var cell1 = newRow.insertCell(0);
                var cell2 = newRow.insertCell(1);
                var cell3 = newRow.insertCell(2);

                cell1.innerHTML = `<select name="lessonId" style="width: 100%">
                    <c:forEach items="${lessonList}" var="c">
                        <option value="${c}">Lesson ${c}</option>
                    </c:forEach>
                </select>`;
                cell2.innerHTML = '<input type="number" name="numberQuestion" style="width: 100%">';
                cell3.innerHTML = '<button type="button" class="btn btn-danger deleteRowBtn">Delete</button>';

                // Add event listener to the new delete button
                cell3.getElementsByTagName('button')[0].addEventListener('click', function () {
                    table.deleteRow(newRow.rowIndex - 1);
                });
            });

            // Add event listeners to existing delete buttons
            document.querySelectorAll('.deleteRowBtn').forEach(function (btn) {
                btn.addEventListener('click', function () {
                    var row = btn.closest('tr');
                    row.parentNode.removeChild(row);
                });
            });
        });
    </script>
</head>
<body>
    <div class="admin-layout">
        <%@ include file="/admin/common/admin-header.jsp" %>
        <%@ include file="/admin/common/admin-sidebar.jsp" %>
        <main class="admin-main">
            <div class="container">
                <h2 class="my-4">
                    <i class="bi bi-plus-circle"></i>
                    Add New Quiz
                </h2>
                <form action="${pageContext.request.contextPath}/admin/addquiz" method="post" onsubmit="return validateForm()">
                    <div class="mb-3">
                        <label for="name" class="form-label">Name</label>
                        <input type="text" class="form-control" id="name" name="name" value="${quizName}" required>
                        <c:if test="${not empty errorName}">
                            <div class="error-message">${errorName}</div>
                        </c:if>
                    </div>
                    <div class="mb-3">
                        <label for="subject" class="form-label">Subject</label>
                        <select class="form-select" id="subject" name="subject" required>
                            <c:forEach var="entry" items="${subjectMap}">
                                <option value="${entry.key}">${entry.value}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="examLevel" class="form-label">Exam Level</label>
                        <select class="form-select" id="examLevel" name="examLevel">
                            <option value="0">Easy</option>
                            <option value="1">Medium</option>
                            <option value="2">Hard</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="duration" class="form-label">Duration (Minutes)</label>
                        <input type="number" class="form-control" id="duration" name="duration" value="${duration}" required>
                        <c:if test="${not empty errorDuration}">
                            <div class="error-message">${errorDuration}</div>
                        </c:if>
                    </div>
                    <div class="mb-3">
                        <label for="passRate" class="form-label">Pass Rate (%)</label>
                        <input type="number" class="form-control" id="passRate" name="passRate" value="${passRate}" required>
                        <c:if test="${not empty errorPassRate}">
                            <div class="error-message">${errorPassRate}</div>
                        </c:if>
                    </div>
                    <div class="mb-3">
                        <label for="quizType" class="form-label">Quiz Type</label>
                        <select class="form-select" id="quizType" name="quizType">
                            <option value="0" <c:if test="${quizTypeInt == 0}">selected</c:if>>Simulation</option>
                            <option value="1" <c:if test="${quizTypeInt == 1}">selected</c:if>>Lesson Quiz</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="description" class="form-label">Description</label>
                        <textarea class="form-control" id="description" name="description">${description}</textarea>
                    </div>
                    <div class="mb-3">
                        <label for="totalQuestions" class="form-label">Total Questions</label>
                        <input type="number" class="form-control" id="totalQuestions" name="totalQuestions" value="${totalQuestions}" required>
                        <c:if test="${not empty errorTotalQuestion}">
                            <div class="error-message">${errorTotalQuestion}</div>
                        </c:if>
                    </div>

                    <table id="lessonTable" class="table">
                        <thead>
                            <tr>
                                <th>Lesson</th>
                                <th>Number of Questions</th>
                                <th>Action</th>
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
                            <c:if test="${not empty errorLesson}">
                                <div class="error-message">${errorTotalQuestion}</div>
                            </c:if>
                        </tbody>
                    </table>
                    <button type="button" id="addRowBtn" class="btn btn-success">Add Lesson</button>

                    <div class="mt-2">
                        <button type="submit" class="btn btn-primary">Submit</button>
                        <a href="${pageContext.request.contextPath}/admin/quizzeslist" class="btn btn-secondary me-2">Back</a>
                    </div>
                </form>
            </div>
        </main>
    </div>
</body>
</html>
