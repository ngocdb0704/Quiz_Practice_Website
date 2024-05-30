<%-- 
    Document   : ChangePassAn
    Created on : May 15, 2024, 4:25:17 PM
    Author     : OwO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Change Password</title>
        <%@include file="/common/ImportBootstrap.jsp" %>
        <!--<link rel="stylesheet" href="public/css/ChangePassAn.css">-->
<!--        <script>
            function showNotification(message) {
                var notification = document.getElementById("notification");
                notification.innerHTML = message;
                notification.classList.add("show");
                setTimeout(function () {
                    notification.classList.remove("show");
                }, 3000); // Hide the notification after 3 seconds
            }

            // Check if there is a success message and show the notification
            window.onload = function () {
                var changePassMessage = '<%= session.getAttribute("changePassMessage") %>';
                 if (changePassMessage && changePassMessage !== null) {
                    showNotification(changePassMessage);
                    session.removeAttribute("changePassMessage");
                }
            };
        </script>-->
    </head>
    <body>

        <div id="notification" class="notification"></div>

        <%
                String username = (String) session.getAttribute("userEmail");
                if (username == null || username.length() < 1) {
                session.setAttribute("successMessage", "Not authorized");
                response.sendRedirect("index.jsp");
            } else {
            
        %>



        <div class="changepass-container">
            <div class="modal-body">
                <form method="post" action="loginviewofAn" class="form-changepass">
                    <h3 classs="text-center">Change Password</h3>
                    <div class="form-group mb-3">
                        <label for="prePass">Previous Password: </label>
                        <input type="password" name="prePass" class="form-control"/>
                    </div>
                    <div class="form-group mb-3">
                        <label for="newPass">New Password: </label>
                        <input type="password" name="newPass" class="form-control"/>
                    </div>
                    <div class="form-group mb-3">
                        <label for="confirmPass">Confirm Password: </label>
                        <input type="password" name="confirmPass" class="form-control"/>
                    </div>
                    <div class="form-group text-center">
                        <input type="submit" name="submit" value="Change Password" class="btn btn-primary"/>
                        <input type="hidden" name = "service" value = "changepass"/>
                    </div>
                </div>
            </form>
        </div>
        <%}%>
    </body>
</html>
