<%-- 
    Document   : quizdetails
    Created on : Jul 7, 2024, 11:11:15 AM
    Author     : hoapmhe173343
--%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quiz details</title>
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
                            <form class="mt-3">
                                <div class="mb-3">
                                    <label for="name" class="form-label">Name</label>
                                    <input type="text" class="form-control" id="name" placeholder="Exam 1">
                                </div>
                                <div class="mb-3">
                                    <label for="subject" class="form-label">Subject</label>
                                    <select class="form-select" id="subject">
                                        <option>Subject name</option>
                                        <!-- Add more options as needed -->
                                    </select>
                                </div>
                                <div class="mb-3 row">
                                    <div class="col">
                                        <label for="examLevel" class="form-label">Exam Level</label>
                                        <select class="form-select" id="examLevel">
                                            <option>Medium</option>
                                            <!-- Add more options as needed -->
                                        </select>
                                    </div>
                                    <div class="col">
                                        <label for="duration" class="form-label">Duration (minutes)</label>
                                        <input type="number" class="form-control" id="duration" value="50">
                                    </div>
                                </div>
                                <div class="mb-3 row">
                                    <div class="col">
                                        <label for="passRate" class="form-label">Pass Rate (%)</label>
                                        <input type="number" class="form-control" id="passRate" value="50">
                                    </div>
                                    <div class="col">
                                        <label for="quizType" class="form-label">Quiz Type</label>
                                        <select class="form-select" id="quizType">
                                            <option>Simulation</option>
                                            <!-- Add more options as needed -->
                                        </select>
                                    </div>
                                </div>
                                <div class="mb-3">
                                    <label for="description" class="form-label">Description</label>
                                    <textarea class="form-control" id="description" rows="3">Text box</textarea>
                                </div>
                                <button type="submit" class="btn btn-primary">Submit</button>
                                <button type="button" class="btn btn-secondary">Back</button>
                            </form>
                        </div>
                        <div class="tab-pane fade" id="setting" role="tabpanel" aria-labelledby="setting-tab">
                            <form class="mt-3">
                                <div class="mb-3">
                                    <label for="totalQuestions" class="form-label">Number of question</label>
                                    <input type="number" class="form-control" id="totalQuestions" value="50">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Question type</label>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="questionType" id="byTopic" value="Theo topic" checked>
                                        <label class="form-check-label" for="byTopic">Theo topic</label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="questionType" id="byGroup" value="Theo group">
                                        <label class="form-check-label" for="byGroup">Theo group</label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="questionType" id="byDomain" value="Theo domain">
                                        <label class="form-check-label" for="byDomain">Theo domain</label>
                                    </div>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Choose the number of questions in groups</label>
                                    <div class="input-group mb-2">
                                        <select class="form-select">
                                            <option>Group1</option>
                                            <!-- Add more options as needed -->
                                        </select>
                                        <button class="btn btn-secondary">number</button>
                                        <button class="btn btn-danger">Delete</button>
                                    </div>
                                    <div class="input-group mb-2">
                                        <select class="form-select">
                                            <option>Group2</option>
                                            <!-- Add more options as needed -->
                                        </select>
                                        <button class="btn btn-secondary">number</button>
                                        <button class="btn btn-danger">Delete</button>
                                    </div>
                                    <div class="input-group mb-2">
                                        <select class="form-select">
                                            <option>Group3</option>
                                            <!-- Add more options as needed -->
                                        </select>
                                        <button class="btn btn-secondary">number</button>
                                        <button class="btn btn-danger">Delete</button>
                                    </div>
                                    <button class="btn btn-primary">Add</button>
                                </div>
                                <button type="submit" class="btn btn-primary">Save</button>
                                <button type="button" class="btn btn-secondary">Back</button>
                            </form>
                        </div>
                    </div>
                </div>
            </main>
        </div>

    </body>
</html>

