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
                        <img src="public/images/blogimg.jpg" height="400" width="100%" class="rounded" alt="Main Image">
                    </div>

                    <div class="mb-3 card">
                        <div class="card-body">
                            <h5 class="card-title fw-bold">Table of Contents</h5>
                        </div>
                    </div>

                    <div>
                        <h2>Heading 1</h2>
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua...</p>

                        <h2>Heading 2</h2>
                        <p>Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat...</p>
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
                    </div>

                </div>
            </div>
        </main>
        <%@include file="/common/footer.jsp" %>
    </body>
</html>
