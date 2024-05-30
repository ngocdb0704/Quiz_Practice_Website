<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reset Password</title>
        
        <!-- Common import -->
        <%@include file="/common/ImportBootstrap.jsp" %>
        <link href="common/common.css" rel="stylesheet">
        <script src="common/common.js"></script>
        
        <!-- Specific page import -->
        <link href="user/ResetPassword.css" href="stylesheet">
        <script src="user/ResetPassword.js"></script>
    </head>
    <body class="body-layout">
        <%@include file="/common/header.jsp" %>

        <main class="d-flex justify-content-center align-items-center p-2">
            <c:choose>
                <c:when test="${screen eq 'success'}">
                    <div class="card w-50 p-3">
                        <div class="text-center">
                            <h2>Success!</h2>
                            <p class="mb-3">You are being redirected back to the home page</p>
                        </div>
                    </div>
                </c:when>
                <c:when test="${screen eq 'change_pw'}">
                    <form method="POST" class="card w-50 p-3">
                        <input type="hidden" name="action" value="reset_password">
                        
                        <div class="text-center">
                            <h2>Welcome back, ${user.getFullName()}!</h2>
                            <p class="mb-3">Create a new password for your account</p>
                        </div>
                        <div
                            <label class="form-label">New Password</label>
                            <input type="password" class="form-control" name="newpw" placeholder="New password" required>
                        </div>
                        <div
                            <label class="form-label">Confirm new password</label>
                            <input type="password" class="form-control" name="confirmnewpw" placeholder="Confirm new password" required>
                        </div>
                        <c:if test="${error eq 'error_pw_not_same'}">
                            <div class="alert alert-primary mt-4" role="alert">
                                Please make sure two passwords are the same
                            </div>
                        </c:if>
                        <button type="submit" class="btn btn-primary mt-4">Continue</button>
                    </form>
                </c:when>
                <c:when test="${screen eq 'expired'}">
                    <div class="card w-50 p-3">
                        <div class="text-center">
                            <h2>Token has expired</h2>
                            <p class="mb-3">Please restart the process</p>
                            <a href="user/reset">
                                <button class="btn btn-primary mt-4">Restart</button>
                            </a>
                        </div>
                    </div>
                </c:when>
                <c:when test="${screen eq 'sent'}">
                    <div class="card w-50 p-3">
                        <div class="text-center">
                            <h2 class="mb-3">Check your inbox</h2>
                            <img src="public/images/email-svgrepo-com.svg" width="120">
                            <p class="my-3">
                                If your email exists in our database, a message with instruction
                                will be sent to it.
                            </p>
                            <p class="text-secondary">
                                <c:if test="${timeout > 60}">
                                This email expires after ${timeout / 60} minutes
                                </c:if>
                                <c:if test="${timeout < 60}">
                                This email expires after ${timeout} seconds
                                </c:if>
                            </p>
                            <a href="user/reset">
                                <button class="btn btn-outline btn-secondary">Go back</button>
                            </a>
                            <a href="">
                                <button class="btn btn-primary">Go home</button>
                            </a>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <form method="POST" class="card w-50 p-3">
                        <input type="hidden" name="action" value="send_email">

                        <div class="text-center">
                            <h2>Forgot your password?</h2>
                            <p class="mb-3">You can enter your email down below to send a reset password email</p>
                        </div>
                        <div
                            <label class="form-label">Email</label>
                            <input type="email" class="form-control" name="email" placeholder="Enter your email" required>
                        </div>
                        <button type="submit" class="btn btn-primary mt-4">Send Email</button>
                        <c:if test="${error eq 'error_invalid_token'}">
                            <div class="alert alert-primary mt-4" role="alert">
                                Invalid token
                            </div>
                        </c:if>
                    </form>
                </c:otherwise>
            </c:choose>
        </main>

        <%@include file="/common/footer.jsp" %>
    </body>
</html>
