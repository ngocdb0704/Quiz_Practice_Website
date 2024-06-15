<%-- 
    Document   : Unauthorized
    Created on : May 20, 2024, 11:29:23 PM
    Author     : QuanNM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="/common/ImportBootstrap.jsp" %>
        <link rel="stylesheet" href="common/ExtendBody.css"/>
        <title>Unauthorized</title>
    </head>
    <body>
        <%@include file="/common/header.jsp" %>
        <main class="container">
            <h1>The content that you want to access requires more authorization.</h1>
            <p>Please make sure that you have logged in to your correct account or contact administrator to verify that your account is authorized.</p>
            <a href=".">Back to home screen</a>
        </main>
        <%@include file="/common/footer.jsp" %>
    </body>
</html>
