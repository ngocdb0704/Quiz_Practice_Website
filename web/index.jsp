<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset=utf-8>
  <title>Home</title>
  <%@include file="/common/ImportBootstrap.jsp" %>
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
    function showNotification(message) {
        var notification = document.getElementById("notification");
        notification.innerHTML = message;
        notification.classList.add("show");
        setTimeout(function () {
            notification.classList.remove("show");
        }, 3000); 
    }
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
    <button class="btn btn-primary">This is a bootstrap button</button>
    <i class="bi bi-star-fill"></i>
    <i class="bi bi-square-fill"></i>
    <h1>Welcome to the Home Page</h1>

        <div id="notification" class="notification"></div>

        <a href="LoginInterface.jsp">Login</a>
        <a href="home">Home</a>
        <form method="post" action="loginviewofAn">
            <input type="submit" name="LogOut" value="Log Out"/>
            <input type="hidden" name="service" value="logout"/>
        </form>
        <a href="ChangePassAn.jsp">Change Password</a>
        <a href="RegistrationController">My Registration</a>
</body>
</html>