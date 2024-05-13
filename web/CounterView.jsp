<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Counter View</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/bootstrap-utilities.min.css" rel="stylesheet">
    </head>
    <body>
        <main class="container p-2">
            <c:if test="${count > 10}">
                <div class="alert alert-primary" role="alert">
                   Congrats! Count is now greater than 10
                </div>
            </c:if>

            <h1>This is a simple counter</h1>
            <h2>${count}</h2>
            <form method="POST">
                <button type="submit" class="btn btn-primary">Increase</button>
            </form>
        </main>

        <script src="js/bootstrap.min.js"></script>
    </body>
</html>
