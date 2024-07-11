
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quiz Expired</title>

        <%@include file="/common/ImportBootstrap.jsp" %>
    </head>
    <body style="height: 100vh">
        <main class="h-100 d-flex align-items-center justify-content-center">
            <div class="card w-50">
               <div class="card-body text-center">
                   <h1 class="card-title">Quiz Expired</h1>
                   <p class="card-text">You cannot attempt this quiz anymore</p>
                   <a href="user/simulation" class="btn btn-primary">Go back</a>
                </div>
            </div>
        </main>
    </body>
</html>
