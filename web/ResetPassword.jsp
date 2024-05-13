<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Counter View</title>
        <link href="<%= getServletContext().getContextPath() %>/css/bootstrap.min.css" rel="stylesheet">
        <link href="<%= getServletContext().getContextPath() %>/css/bootstrap-utilities.min.css" rel="stylesheet">
    </head>
    <body>
        <main class="container p-2">
            <form method="POST" class="card p-3">
                <h2 class="text-center">Password Reset</h2>
                <div
                    <label class="form-label">Email</label>
                    <input type="email" class="form-control" placeholder="Enter your email">
                </div>
                <button type="submit" class="btn btn-primary mt-4">Send Email</button>
                <c:if test="${not empty message}">
                    <h2>${message}</h2>
                </c:if>
            </form>
        </main>

        <script src="<%= getServletContext().getContextPath() %>/js/bootstrap.min.js"></script>
    </body>
</html>
