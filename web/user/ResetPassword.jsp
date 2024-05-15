<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reset Password</title>
        
        <!-- Common import -->
        <%@include file="/common/ImportBootstrap.jsp" %>
        <link href="../common/common.css" href="stylesheet">
        <script src="../common/common.js"></script>
        
        <!-- Specific page import -->
        <link href="ResetPassword.css" href="stylesheet">
        <script src="ResetPassword.js"></script>
    </head>
    <body>
        <main class="d-flex justify-content-center align-items-center p-2">
            <c:choose>
                <c:when test="${status eq 'change'}">
                    <form method="POST" class="card w-50 p-3">
                        <input type="hidden" name="action" value="reset_password">
                        
                        <div class="text-center">
                            <h2>Create New Password</h2>
                            <p class="mb-3">Create a new password for your account</p>
                        </div>
                        <div
                            <label class="form-label">New Password</label>
                            <input type="password" class="form-control" name="newpw" placeholder="New password">
                        </div>
                        <div
                            <label class="form-label">Confirm new password</label>
                            <input type="password" class="form-control" name="confirmnewpw" placeholder="Confirm new password">
                        </div>
                        <button type="submit" class="btn btn-primary mt-4">Continue</button>
                    </form>
                </c:when>
                <c:when test="${status eq 'sent'}">
                    <div class="card w-50 p-3">
                        <div class="text-center">
                            <h2>Email has been sent</h2>
                            <p class="mb-3">Please check your inbox for confirmation instructions</p>
                            <p>Generated token: ${token}</p>
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
                            <input type="email" class="form-control" name="email" placeholder="Enter your email">
                        </div>
                        <button type="submit" class="btn btn-primary mt-4">Send Email</button>
                        <c:if test="${not empty message}">
                            <div class="alert alert-primary mt-4" role="alert">
                                ${message}
                            </div>
                        </c:if>
                    </form>
                </c:otherwise>
            </c:choose>
        </main>
    </body>
</html>
