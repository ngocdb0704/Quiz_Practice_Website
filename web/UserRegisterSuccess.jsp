<%-- 
    Document   : UserRegisterSuccess
    Created on : 11 thg 7, 2024, 15:26:57
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Register</title>
        <%@include file="/common/ImportBootstrap.jsp" %>
        <link rel="stylesheet" href="common/ExtendBody.css"/>
    </head>
    <body>
        <%@include file="/common/header.jsp" %>
        <main class="container">
            <div class="container">
                <h1>User Registered Successfully</h1>
                <div class="row">
                    <div class="col">

                    </div>
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

                    </div>
                </div>
            </div>
        </main>
        <%@include file="/common/footer.jsp" %>
    </body>
</html>
