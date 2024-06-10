<%-- 
    Document   : SubjectDetails
    Created on : Jun 10, 2024, 5:07:33 PM
    Author     : QuanNM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Subject details</title>
        <%@include file="/common/ImportBootstrap.jsp" %>
        <link rel="stylesheet" href="common/ExtendBody.css"/>
    </head>
    <body>
        <%@include file="/common/header.jsp" %>
        
        <!-- Sider here -->
        <main class="container">
            <a href="#"><- Back</a>
            <h1><c:out value="${subjectDetails.getSubjectName()}"/></h1>
            <h2><c:out value="${subjectDetails.getTagLine()}"/></h2>
            <h2><c:out value="${subjectDetails.getBriefInfo()}"/></h2>
            <button class="btn btn-primary">Register</button>
            <h2><c:out value="${subjectDetails.getSubjectDescription()}"/></h2>
        </main>
        <%@include file="/common/footer.jsp" %>
    </body>
</html>
