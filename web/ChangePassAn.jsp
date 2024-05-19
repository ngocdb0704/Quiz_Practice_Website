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
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/bootstrap-utilities.min.css" rel="stylesheet">
        <style>
            .notification {
                background-color: #4CAF50;
                color: white;
                text-align: center;
                padding: 16px;
                position: fixed;
                top: 0;
                left: 50%;
                transform: translateX(-50%);
                z-index: 9999;
                opacity: 0;
                visibility: hidden;
                transition: opacity 0.3s ease-in-out, visibility 0.3s ease-in-out;
            }

            .show {
                opacity: 1;
                visibility: visible;
            }
        </style>
        <script>
            // Function to show the notification
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
        </script>
    </head>
    <body>
        <div id="notification" class="notification"></div>
        <form method="post" action="loginviewofAn">
            <h3>Change Password</h3>
            <p> Username: <input type="text" name="username"/></p>
            <p> Previous Password: <input type="password" name="prePass"/></p>
            <p> New Password: <input type="password" name="newPass"/> </p>
            <p> Confirm Password: <input type="password" name="confirmPass"/></p>
            <p> <input type="submit" name="submit" value="Change Password"/>
                <input type="hidden" name = "service" value = "changepass"/>
            </p>
        </form>
    </body>
</html>
