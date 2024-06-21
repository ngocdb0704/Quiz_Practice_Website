<%-- 
    Document   : DimensionAdd
    Created on : Jun 21, 2024, 1:54:08 AM
    Author     : OwO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add New Dimension</title>
        <%@include file="/common/ImportBootstrap.jsp" %>
        <link rel="stylesheet" href="admin/common/admin-common.css">
    </head>
    <body>
        <div class="admin-layout">
            <%@include file="/admin/common/admin-header.jsp" %>
            <%@include file="/admin/common/subject-sidebar.jsp" %>
            <main class="admin-main">
                <div class="container">
                    <h2 class="my-4">Add New Dimension</h2>
                    <form action="DimensionServlet" method="post">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" name="subjectId" value="${param.subjectId}">

                        <div class="form-group">
                            <label for="dimensionType">Type</label>
                            <input type="text" class="form-control" id="dimensionType" name="dimensionType" required>
                        </div>
                        <div class="form-group">
                            <label for="dimensionName">Name</label>
                            <input type="text" class="form-control" id="dimensionName" name="dimensionName" required>
                        </div>
                        <div class="form-group">
                            <label for="description">Description</label>
                            <input type="text" class="form-control" id="description" name="description" required>
                        </div>

                        <button type="submit" class="btn btn-primary">Add Dimension</button>

                    </form>
                </div>
            </main>
        </div>
        <%@include file="/admin/common/notyf.jsp" %>
    </body>
</html>