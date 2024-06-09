<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${not empty information ? information.getBlogTitle() : "Not found" }</title>
                <!-- Common import -->
        <%@include file="/common/ImportBootstrap.jsp" %>
        <link href="common/common.css" rel="stylesheet">
        <link href="blogs/BlogDetail.css" rel="stylesheet">
        <script src="common/common.js"></script>
    </head>
    <body class="body-layout">
        <%@include file="/common/header.jsp" %>
        <jsp:useBean id="formatter" class="app.utils.FormatData" />
        
        <main class="container mt-3">
            <nav style="--bs-breadcrumb-divider: url(&#34;data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='8' height='8'%3E%3Cpath d='M2.5 0L1 1.5 3.5 4 1 6.5 2.5 8l4-4-4-4z' fill='%236c757d'/%3E%3C/svg%3E&#34;);" aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="blogs/list">Blogs</a></li>
                    <li class="breadcrumb-item" aria-current="page">
                        <a href="blogs/list?categoryId=${information.getCategoryId()}">
                            ${information.getCategoryName()}
                        </a>
                    </li>
                    <li class="breadcrumb-item active" aria-current="page">${information.getBlogTitle()}</li>
                </ol>
            </nav>
            <div class="row">
                <div class="col-md-8">
                    <h1>${information.getBlogTitle()}</h1>
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <p class="m-0 text-muted">
                                Written by: ${information.getAuthorFullName()}
                            </p>
                            <p class="text-muted">
                                Updated Date: ${formatter.dateFormat(information.getUpdatedTime())}
                            </p>
                        </div>
                        <p class="badge bg-primary">${information.getCategoryName()}</p>
                    </div>

                    <div class="mb-3">
                        <img src="public/images/blogimg.jpg" class="cover-image rounded-3" height="400" width="100%" class="rounded" alt="Main Image">
                    </div>

                    <div class="mb-3 card w-50">
                        <div class="card-body">
                            <h5 class="card-title fw-bold">Table of Contents</h5>
                            <c:set var="currentUrl" value="blogs/detail?id=${param.id}" />
                            ${document.getHtmlTableOfContents(currentUrl)}
                        </div> 
                    </div>

                    <div>
                        ${document.toHtml()}
                    </div>

                    <div class="mt-5">
                        <h5>Related Posts by same Category</h5>
                    </div>
                </div>

                <div class="col-md-4">
                    <form method="GET" action="blogs/list">
                        <div class="mb-3">
                            <label for="searchBox" class="form-label fs-5 fw-bold">Search</label>
                            <input type="text" placeholder="Search posts..." class="form-control" name="q" id="searchBox">
                        </div>
                    </form>

                    <div class="mb-3">
                        <h5 class="fw-bold">Advertisement</h5>
                        <div class="card text-white">
                            <img src="public/images/ad.jpg" class="card-img" alt="Banner Ad">
                            <div class="card-img-overlay ad-overlay">
                                <h4 class="card-title fw-bold">Boost Your Quiz Skills Today!</h4>
                                <p>
                                    Ace Your Exams with Fun and Interactive Practice Sessions
                                </p>
                                <ul>
                                    <li>Engaging quizzes</li>
                                    <li>Instant feedback</li>
                                    <li>Cheap</li>
                                </ul>
                                <a href="." class="btn btn-primary">Checkout our Courses!</a>
                            </div>
                        </div>
                    </div>

                    <div class="mb-3">
                        <h5 class="fw-bold">Categories</h5>
                        <ul class="list-group">
                            <c:forEach var="cat" items="${categories}">
                            <a
                                href="blogs/list?categoryId=${cat.getCategoryId()}"
                                class="list-group-item ${information.getCategoryId() eq cat.getCategoryId() ? "active" : ""}">
                                ${cat.getCategoryName()}
                            </a>
                            </c:forEach>
                        </ul>
                    </div>

                    <div class="mb-3">
                        <h5 class="fw-bold">Latest Posts</h5>
                        <c:forEach items="${recents}" var="post">
                            <a href="blogs/detail?id=${post.getBlogId()}">
                                <div class="card d-flex flex-row align-items-center mb-3">
                                    <img height="128" src="public/images/blogimg.jpg" class="p-2 rounded-4" alt="${post.getPostBrief()}">
                                    <div class="card-body">
                                        <h5 class="card-title">${post.getBlogTitle()}</h5>
                                        <p class="card-text"><small class="text-muted">${formatter.dateFormat(post.getUpdatedTime())}</small></p>
                                    </div>
                                </div>
                            </a>
                        </c:forEach>
                        <div class="text-center">
                            <a href="blogs/list" class="btn btn-outline-primary">View all</a>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <%@include file="/common/footer.jsp" %>
    </body>
</html>
