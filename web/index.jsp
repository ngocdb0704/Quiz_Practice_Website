

<!DOCTYPE html>
<html>
    <head>
        <title>Home</title>
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
                var successMessage = '<%= session.getAttribute("successMessage") %>';
                 if (successMessage && successMessage !== null) {
                    showNotification(successMessage);
                    session.removeAttribute("successMessage");
                }
            };
        </script>
    </head>
    <body>
        <h1>Welcome to the Home Page</h1>

        <div id="notification" class="notification"></div>

        <a href="LoginInterface.jsp">Login</a>
        <a href="home">Home</a>
        <form method="post" action="loginviewofAn">
            <input type="submit" name="LogOut" value="Log Out"/>
            <input type="hidden" name="service" value="logout"/>
        </form>
        <a href="ChangePassAn.jsp">Change Password</a>
    </body>
</html>
