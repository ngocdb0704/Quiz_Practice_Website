<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="app.utils.URLUtils" %>

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
                        <button class="btn btn-primary w-100" type="submit">Search</button>
                    </form>
                </aside>
                <section class="flex-grow-1">
                    <div class="blog-grid">
                        <c:forEach items="${blogs}" var="blog">
                            <div class="card blog-item">
                                <img src="public/images/blogimg.jpg" class="card-img-top blog-item-img" alt="...">
                                <div class="card-body d-flex flex-column">
                                    <h4 class="card-title">${blog.getBlogTitle()}</h4>
                                    <div>
                                        <span class="badge bg-primary">${blog.getCategoryName()}</span>
                                    </div>
                                    <p class="card-text text-body-secondary mt-3">${blog.getPostBrief()}</p>
                                </div>

                                <div class="card-footer d-flex align-items-center justify-content-between">
                                    <h6 class="card-subtitle text-body-secondary">
                                        <i class="bi bi-person-circle"></i>
                                        <b>${blog.getAuthorFullName()}</b>
                                    </h6>
                                    <a href="#" class="btn blog-read-more btn-primary align-self-end">Read More</a>
                                </div>
                            </div>
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

                    <nav class="d-flex justify-content-center mt-3">
                        <c:set var="paramsMap" value="${URLUtils.cloneParamsMap(param)}" />
                        <ul class="pagination">
                            <c:forEach begin="1" end="${pagesCount}" var="page">
                                <li class="page-item ${(empty param.page and page == 1) or (param.page eq page) ? "active" : ""}">
                                    <c:set target="${paramsMap}" property="page" value="${page.toString()}" />
                                    <a
                                        class="page-link"
                                        href="blogs/list${URLUtils.getQueryParamsString(paramsMap)}"
                                    >${page}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </nav>
                </section>
            </div>
        </main>
        <%@include file="/common/footer.jsp" %>
    </body>
</html>
