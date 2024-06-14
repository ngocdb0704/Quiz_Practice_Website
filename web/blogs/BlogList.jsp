<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="myTags" tagdir="/WEB-INF/tags" %>

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
        <link href="blogs/BlogItem.css" rel="stylesheet">
    </head>
    <body class="body-layout">
        <%@include file="/common/header.jsp" %>
        
        <main class="container my-2">
            <div class="text-center my-3">
                <h1 class="">Blogs</h1>
                <c:if test="${not empty param.q}">
                    <h5 class="text-mute">
                        Searching sub-string <b>"${param.q}"</b> in blog titles.
                    </h5>
                </c:if>
            </div>
            <myTags:Paginator
                className="mt-3 d-flex justify-content-end"
                total="${pagesCount}"
                size="1"
                current="${param.page}"
                url="blogs/list"
            />
            <div class="d-flex gap-3 blog-main-section">
                <aside>
                    <form class="blog-sidebar" method="GET" action="blogs/list">
                        <div class="mb-3">
                            <label for="searchBox" class="form-label">Search</label>
                            <input type="text" placeholder="Search posts..." class="form-control" name="q" id="searchBox" value="${param.q}">
                        </div>
                        <div class="mb-3">
                            <label for="categories" class="form-label">Categories</label>
                            <select class="form-select" id="categories" name="categoryId">
                                <option value="-1">All</option>
                                <c:forEach var="cat" items="${categories}">
                                    <option
                                        value="${cat.getCategoryId()}"
                                        ${param.categoryId eq cat.getCategoryId() ? "selected" : ""}
                                    >${cat.getCategoryName()}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="dateRange" class="form-label">Date range</label>
                            <select class="form-select" id="dateRange" name="dateRange">
                                <option value="any" ${param.dateRange eq 'any' ? "selected" : ""}>Any</option>
                                <option
                                    value="specified"
                                    ${param.dateRange eq 'specified' ? "selected" : ""}
                                >Specified</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <input type="date" id="startDate" value="${param.startDate}" name="startDate" />
                            <input type="date" id="endDate" value="${param.endDate}" name="endDate" />
                        </div>
                        <button class="btn btn-primary w-100" type="submit">Search</button>
                    </form>
                </aside>
                <section class="flex-grow-1">

                    <div class="blog-grid">
                        <c:forEach items="${blogs}" var="blog">
                            <myTags:BlogItem highlight="${param.q}" blog="${blog}" />
                        </c:forEach>
                    </div>
                    
                    <c:if test="${empty blogs or blogs.size() eq 0}">
                        <div class="d-flex justify-content-center align-items-center h-100">
                            <div class="text-center">
                                <i class="bi bi-emoji-neutral-fill fs-1 text-danger"></i>
                                <h1 class="text-body-secondary fw-bold">No posts found</h1>
                                <a href="blogs/list" class="btn btn-primary">Reset</a>
                            </div>
                        </div>
                    </c:if>

                    <myTags:Paginator
                        className="mt-3 d-flex justify-content-end"
                        total="${pagesCount}"
                        size="1"
                        current="${param.page}"
                        url="blogs/list"
                    />
                </section>
            </div>
        </main>
        <%@include file="/common/footer.jsp" %>
        <script src="blogs/BlogList.js"></script>
    </body>
</html>
