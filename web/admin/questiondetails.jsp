<%-- 
    Document   : questiondetails
    Created on : Jun 23, 2024, 12:11:12 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Question Details</title>
        <%@include file="/common/ImportBootstrap.jsp" %>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/questionlist.css" />
        <link rel="stylesheet" href="admin/common/admin-common.css">
        <script src="admin/common/admin-common.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/public/js/questionlist.js"></script>
    </head>
    <body>
        <div class="admin-layout">
            <%@include file="/admin/common/admin-header.jsp" %>
            <%@include file="/admin/common/admin-sidebar.jsp" %>
            
        </div>
    </body>
</html>
