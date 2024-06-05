<%-- 
    Document   : importquestion
    Created on : Jun 5, 2024
    Author     : hoapmhe173343
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/bootstrap/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/importquestion.css"/>
</head>
<body>
    <div class="container">
        <button class="btn btn-primary" type="button" data-toggle="collapse" 
                data-target="#importExcel">Import file excel</button>
        <div class="collapse" id="importExcel">
            <div class="card card-body">
                <button class="btn btn-success">Download the import question template</button>
                <p>Select the file excel containing question to add to the question bank.</p>
                <div class="inputBox ">
                    <form action="#" method="post" enctype="multipart/form-data">
                        <div class="row">
                            <input class="col-md-8" type="file" name="file" accept=".xlsx" required/>
                            <button class="col-md-4" type="submit" class="btn btn-primary">Upload</button>
                        </div>
                    </form>
                    <div class="message">${message}</div>
                </div>
            </div>
        </div>

        <form action="#" method="POST">
            <div class="form-group">
                <label for="SubjectID"><b>Subject Name</b></label>
                <select name="SubjectID" class="form-control" required>
                    <c:forEach var="sub" items="${subject}">
                        <option value="${sub.getSubjectID()}">${sub.getName()}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="QuestionText"><b>Question Content</b></label>
                <input type="text" class="form-control" placeholder="Enter Question Text" name="QuestionText" required>
            </div>

            <div class="row">
                <div class="form-group col-md-6">
                    <label for="OptionA"><b>Answer A</b></label>
                    <input type="text" class="form-control" placeholder="Option A" name="OptionA" required>
                </div>
                <div class="form-group col-md-6">
                    <label for="OptionC"><b>Answer C</b></label>
                    <input type="text" class="form-control" placeholder="Enter Answer C" name="OptionC" required>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-md-6">
                    <label for="OptionB"><b>Answer B</b></label>
                    <input type="text" class="form-control" placeholder="Enter Answer B" name="OptionB" required>
                </div>
                <div class="form-group col-md-6">
                    <label for="OptionD"><b>Answer D</b></label>
                    <input type="text" class="form-control" placeholder="Enter Answer D" name="OptionD" required>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-md-6">
                    <label for="CorrectAnswer"><b>Correct Answer</b></label>
                    <input type="text" class="form-control" placeholder="Enter Correct Answer" name="CorrectAnswer" required>
                </div>
                <div class="form-group col-md-6">
                    <label for="QuestionLevel"><b>Question Level</b></label>
                    <select name="QuestionLevel" class="form-control" required>
                        <option value="1">Easy</option>
                        <option value="2">Medium</option>
                        <option value="3">Hard</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="Explanation"><b>Explanation</b></label>
                <input type="text" class="form-control" placeholder="Enter Explanation" name="Explanation">
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-secondary">Add question</button>
            </div>
        </form>
    </div>
    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <!-- Popper.js -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <!-- Bootstrap JS -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
