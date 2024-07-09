<%-- 
    Document   : SubjectRegisterPaid
    Created on : 6 thg 7, 2024, 02:06:06
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Subject Register</title>
        <%@include file="/common/ImportBootstrap.jsp" %>
        <link rel="stylesheet" href="common/ExtendBody.css"/>
    </head>
    <body>
        <%@include file="/common/header.jsp" %>
        <main class="container">
            <div class="container">
                <h1>Subject Registered Successfully</h1>
                <div class="row">
                    <div class="col">
                        <div class="card">
                            <img src="public/images/thankImage.png" class="card-img-top" alt="...">
                            <div class="card-body">
                                <h5 class="card-title text text-success"> Thank you for your registration!</h5>
                                <p class="card-text">
                                    We have sent you an email about your new password!.
                                    <br><span class="text text-danger">Do not share this email with anyone!</span>
                                    <br>Your can log in Quiz_Practice using email address <span class="text text-primary">${requestScope.email}</span>
                                    <br>and the password in this email.
                            </div>
                        </div>
                    </div>
                    <div class="col">
                        <div class="card">
                            <img src="public/thumbnails/${requestScope.thumbnail}" class="card-img-top" alt="...">
                            <div class="card-body">
                                <h5 class="card-title">Registration Summary</h5>
                                <p class="card-text">
                                    Registration Id: ${requestScope.registId}
                                    <br> Package: ${requestScope.packageName}
                                    <br> Registration Time: ${requestScope.registTime}
                                    <br> Valid From: ${requestScope.validFrom}
                                    <br> Valid To: ${requestScope.validTo}
                                    <br> Price: ${requestScope.price}
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <%@include file="/common/footer.jsp" %>
    </body>
</html>
