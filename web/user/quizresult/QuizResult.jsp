
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quiz Result</title>

        <%@include file="/common/ImportBootstrap.jsp" %>
        <link href="common/common.css" rel="stylesheet">
        <script src="common/common.js"></script>
    </head>
    <body class="d-flex flex-column" style="height: 100vh;">
        <%@include file="/common/header.jsp" %>

        <main class="mt-3 d-flex justify-content-center align-items-center flex-grow-1">
            <div class="card w-50 shadow">
                <div class="card-header">
                    <h5 class="text-center">Results</h5>
                </div>
                <div class="card-body d-flex flex-column align-items-center">
                    <c:if test="${pass}">
                        <img src="public/images/duo-happy.png">
                        <h2>Yay, you passed!</h2>
                    </c:if>
                    <c:if test="${not pass}">
                        <img src="public/images/duo-sad.png" width="225">
                        <h2>Oops, better luck next time!</h2>
                        <p>You need at least ${min} / ${total} correct questions to pass this exam.</p>
                    </c:if>

                    <p>
                        You got
                        <span class="p-1 text-white bg-primary rounded fw-bold">${attempt.getCorrectCount()} / ${total}</span>
                        questions correct.
                    </p>
                </div>
                <div class="card-footer d-flex justify-content-center">
                    <a href="user/simulation" class="btn btn-primary">Back to Exams</a>
                </div>
            </div>
        </main>

        <%@include file="/common/footer.jsp" %>
    </body>
</html>
