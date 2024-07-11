<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="myTags" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quizzes List</title>
        <%@include file="/common/ImportBootstrap.jsp" %>
        <link rel="stylesheet" href="admin/common/admin-common.css">
        <link rel="stylesheet" href="public/css/sortable/sortable-theme-bootstrap.css">
        <script src="admin/common/admin-common.js"></script>
    </head>
    <body>
        <div class="admin-layout">
            <%@include file="/admin/common/admin-header.jsp" %>
            <%@include file="/admin/common/admin-sidebar.jsp" %>
            <main class="admin-main" x-data="state">
                <div class="container">
                    <h2 class="my-4">
                        <i class="bi bi-clipboard-check-fill"></i>
                        ${isSearching ? 'Searching Quizzes List' : 'Quizzes List' }
                    </h2>
                    
                    <c:if test="${not empty successMessage}">
                        <div class="alert alert-success" role="alert">
                            ${successMessage}
                        </div>
                    </c:if>
                    
                    <%@include file="/admin/quizzeslist/QuizzesListForm.jsp" %>

                    <div class="my-4 card">
                        <div class="card-body">
                            <h5 class="card-title fw-bold">
                                <i class="bi bi-wrench"></i>
                                Actions
                            </h5>
                            <form action="admin/addquiz" method="get">
                                <div class="d-flex justify-content-end mt-3 gap-2">
                                    <button type="submit" class="btn btn-primary">
                                        <i class="bi bi-plus-circle"></i>
                                        Add New Quiz
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>

                    <c:if test="${result.getTotalPages() > 0}">
                        <myTags:Paginator
                            className="mt-3 d-flex justify-content-end"
                            current="${param.page}"
                            total="${result.getTotalPages()}"
                            size="1"
                            url="admin/quizzeslist"
                        />
                    </c:if>

                    <%@include file="/admin/quizzeslist/QuizzesListTable.jsp" %>

                    <c:if test="${result.getTotalPages() > 0}">
                        <myTags:Paginator
                            className="mt-3 d-flex justify-content-end"
                            current="${param.page}"
                            total="${result.getTotalPages()}"
                            size="1"
                            url="admin/quizzeslist"
                        />
                    </c:if>
                </div>
            </main>
        </div>

        <script src="public/js/sortable/sortable.min.js"></script>
    </body>
</html>

