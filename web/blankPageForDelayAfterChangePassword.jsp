<%-- 
    Document   : blankPageForDelayAfterChangePassword
    Created on : May 25, 2024, 10:24:58 PM
    Author     : OwO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Change Password</title>
        <link rel="stylesheet" href="public/css/blankPageForDelayAfterChangePassword.css"/>
    </head>
    <body>
        <div class="middle-page-div">
            <h2>You have successfully changed your password</h2>
            <h5 id="countdown">You will automatically be redirected to the login page in: 60</h5>
            <a href="index.jsp" id="redirectLink">Return</a>
        </div>

        <script>
            var countdownElement = document.getElementById("countdown");
            var redirectLink = document.getElementById("redirectLink");
            var countdown = 60;

            function redirectToLogin() {
                window.location.href = "index.jsp";
            }

            function updateCountdown() {
                countdown--;
                countdownElement.textContent = "You will automatically be redirected to the login page in: " + countdown;

                
                if (countdown === 0) {
                    clearInterval(countdownInterval);
                    countdownElement.style.display = "none";
                    redirectLink.style.display = "inline";
            <%session.invalidate();%>
                    redirectToLogin();
                }
            }

            var countdownInterval = setInterval(updateCountdown, 1000);
        </script>
    </body>
</html>
