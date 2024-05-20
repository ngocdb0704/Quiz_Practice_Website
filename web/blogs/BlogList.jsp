<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Blogs</title>

        <!-- Common import -->
        <%@include file="/common/ImportBootstrap.jsp" %>
        <link href="common/common.css" rel="stylesheet">
        <script src="common/common.js"></script>

        <link href="blogs/BlogList.css" rel="stylesheet">
    </head>
    <body class="body-layout">
        <%@include file="/common/header.jsp" %>
        <main class="container my-2">
            <h1 class="text-center my-3">Blogs</h1>
            <div class="blog-grid">
                <c:forEach items="${blogs}" var="blog">
                    <div class="card blog-item">
                        <img src="https://source.unsplash.com/random/" class="card-img-top blog-item-img" alt="...">
                        <div class="card-body d-flex flex-column">
                            <h5 class="card-title">${blog.getTitle()}</h5>
                            <p class="card-text">${blog.getContent()}</p>
                            <a href="#" class="btn blog-read-more btn-primary align-self-end">Read More</a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </main>
        <%@include file="/common/footer.jsp" %>
    </body>
</html>
