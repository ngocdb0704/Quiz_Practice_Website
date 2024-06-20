<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quizzes List</title>
        <%@include file="/common/ImportBootstrap.jsp" %>
        <link rel="stylesheet" href="admin/common/admin-common.css">
        <script src="admin/common/admin-common.js"></script>
    </head>
    <body>
        <div class="admin-layout">
            <%@include file="/admin/common/admin-header.jsp" %>
            <%@include file="/admin/common/admin-sidebar.jsp" %>
            <main class="admin-main">
                <div class="container mt-4">
                    <h1>Edit Subject</h1>
                    <h2 class="bg-warning text-white">${mess}</h2>
                    <form action="admin/subjectDimension" method="post">
                        <input type="hidden" name="subjectId" value="${subject.subjectId}" />

                        <div class="form-group">
                            <label for="subjectTitle">Title:</label>
                            <input type="text" class="form-control" id="subjectTitle" name="subjectTitle" value="${subject.subjectTitle}" required>
                        </div>

                        <div class="form-group">
                            <label for="isFeaturedSubject">Featured Subject:</label>
                            <select class="form-control" id="isFeaturedSubject" name="isFeaturedSubject">
                                <option value="true" ${subject.isFeaturedSubject ? 'selected' : ''}>Yes</option>
                                <option value="false" ${!subject.isFeaturedSubject ? 'selected' : ''}>No</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="subjectBriefInfo">Brief Info:</label>
                            <textarea class="form-control" id="subjectBriefInfo" name="subjectBriefInfo">${subject.subjectBriefInfo}</textarea>
                        </div>

                        <div class="form-group">
                            <label for="subjectDescription">Description:</label>
                            <textarea class="form-control" id="subjectDescription" name="subjectDescription">${subject.subjectDescription}</textarea>
                        </div>

                        <div class="form-group">
                            <label for="subjectThumbnail">Thumbnail URL:</label>
                            <input type="text" class="form-control" id="subjectThumbnail" name="subjectThumbnail" value="${subject.subjectThumbnail}">
                        </div>

                        <button type="submit" class="btn btn-primary">Update</button>
                    </form>
                </div>
            </main>
        </div>
    </body>
</html>


