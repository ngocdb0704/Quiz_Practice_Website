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
        <div class="container d-flex justify-content-center align-items-center" style="height: 100vh;">
            <form method="get" class="card w-50 p-3" action="VerificationUserRegister">
                <input type="hidden" name="action" value="register">

                <div class="text-center">
                    <h2>User Registration</h2>
                    <p class="mb-3">Please enter your email and password to register</p>
                </div>

                <div class="mb-3">
                    <label class="form-label">Email</label>
                    <input type="email" class="form-control" name="email" placeholder="Enter your email" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Password</label>
                    <input type="password" class="form-control" name="password" placeholder="Enter your password" required>
                </div>

                <div class="mb-3">
                    <div class="mb-3">
                        <label class="form-label">Verification Code</label>
                        <div class="d-flex">
                            <input type="text" class="form-control me-2" name="verificationCode" placeholder="Enter verification code" required>
                            <button type="button" class="btn btn-primary" onclick="sendVerificationCode()">Send Code</button>
                        </div>
                    </div>
                </div>
                <script>
                    function sendVerificationCode() {
                        // Get the email value from the form
                        var email = document.querySelector('input[name="email"]').value;

                        // Send the AJAX request to the server
                        fetch('VerificationUserRegister?action=register&service=sendCode&email=' + encodeURIComponent(email), {
                            method: 'GET'
                        })
                                .then(response => response.text())
                                .then(data => {
                                    // Handle the response from the server
                                    console.log(data);
                                    // You can add additional logic here, such as displaying a success message
                                })
                                .catch(error => {
                                    // Handle any errors that occur during the AJAX request
                                    console.error(error);
                                });
                    }
                </script>
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
