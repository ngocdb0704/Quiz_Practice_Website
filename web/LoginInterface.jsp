<%-- 
    Document   : LoginInterface
    Created on : May 13, 2024, 12:24:32 AM
    Author     : OwO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/bootstrap-utilities.min.css" rel="stylesheet">
    </head>
    <body>
        <form>
            <h3>Login</h3>
            <p> Username: <input type="text" name="username">
            <p> Password: <input type="text" name="password">
            <p> <input type="submit" name="submit" value="Login">
                <input type="hidden" name = "service" value = "login">
            </p>
            <a href="">Register</a>
            <a href="">Forgot password?</a>
        </form>
    </body>
</html>
