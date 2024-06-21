<%-- 
    Document   : DimensionUpdate
    Created on : Jun 21, 2024, 2:47:38 AM
    Author     : OwO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit Dimension</title>
    <%@include file="/common/ImportBootstrap.jsp" %>
    <link rel="stylesheet" href="admin/common/admin-common.css">
</head>
<body>
    <div class="admin-layout">
        <%@include file="/admin/common/admin-header.jsp" %>
        <%@include file="/admin/common/admin-sidebar.jsp" %>
        <main class="admin-main">
            <div class="container">
                <h2 class="my-4">Edit Dimension</h2>
                <form action="DimensionServlet" method="post">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="dimensionId" value="${dimension.dimensionId}">
                    <input type="hidden" name="subjectId" value="${dimension.subjectId}">

                    <div class="form-group">
                        <label for="dimensionType">Dimension Type:</label>
                        <input type="text" class="form-control" id="dimensionType" name="dimensionType" value="${dimension.dimensionType}" required>
                    </div>
                    <div class="form-group">
                        <label for="dimensionName">Dimension Name:</label>
                        <input type="text" class="form-control" id="dimensionName" name="dimensionName" value="${dimension.dimensionName}" required>
                    </div>
                    <div class="form-group">
                        <label for="description">Description:</label>
                        <textarea class="form-control" id="description" name="description" required>${dimension.description}</textarea>
                    </div>
                    <button type="submit" class="btn btn-primary">Update Dimension</button>
                </form>
            </div>
        </main>
    </div>
    <%@include file="/admin/common/notyf.jsp" %>
</body>
</html>