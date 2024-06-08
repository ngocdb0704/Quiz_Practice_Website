<%-- 
    Document   : UserRegisterThroughMail
    Created on : May 30, 2024, 1:37:43 PM
    Author     : OwO
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>User Registration</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    </head>
    <body>
        <div class="d-flex justify-content-center align-items-center">
            <form method="get" action="VerificationUserRegister">
                <input type="hidden" name="action" value="register">

                <div class="text-center">
                    <p class="mb-3">Please enter your email and password to register</p>
                    Already have an account? <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#loginModal">
                        Login
                    </button>
                </div>
                <div style="display: flex">
                    <div class="mb-3">
                        <label class="form-label">Email:</label>
                        <input type="email" class="form-control" name="email" placeholder="Enter your email" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Password:</label>
                        <input type="password" class="form-control" name="password" placeholder="Enter your password" required>
                    </div>
                </div>
                <div style="display: flex">
                    <div class="mb-3">
                        <label class="form-label">Full Name:</label>
                        <input type="text" class="form-control" name="text" placeholder="Enter your full name">
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Gender:</label>
                        <input type="text" class="form-control" name="text" placeholder="Enter your gender">
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Mobile:</label>
                        <input type="text" class="form-control" name="text" placeholder="Enter your mobile">
                    </div>
                </div>

                <div class="mb-3">
                    <div class="mb-3">
                        <label class="form-label">Verification Code:</label>
                        <div class="d-flex">
                            <input type="text" class="form-control me-2" name="verificationCode" placeholder="Enter verification code" required>
                            <button type="button" class="btn btn-primary" onclick="sendVerificationCode()">Send Code</button>
                        </div>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary mt-4">Register</button>
                <c:if test="${error eq 'error_invalid_code'}">
                    <div class="alert alert-primary mt-4" role="alert">
                        Invalid verification code
                    </div>
                </c:if>
            </form>
        </div>
    </body>
</html>
