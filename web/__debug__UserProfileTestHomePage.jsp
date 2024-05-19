<%-- 
    Document   : __debug__UserProfileTestHomePage.jsp
    Created on : May 17, 2024, 4:40:10 PM
    Author     : quatn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html style="height: 100%; display: flex;">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="public/css/bootstrap/bootstrap.css">
        <title>UserProfile</title>
    </head>
    <body style="flex-grow: 1; background-image: linear-gradient(buttonface, #ccccff)">
        <%
        if (request.getParameter("setUID") != null && request.getParameter("setUID").length() > 0)
            session.setAttribute("uId", Integer.parseInt(request.getParameter("setUID")));
        %>
        <h1>Test page!</h1>
        <form method="post" action="__debug__UserProfileTestHomePage.jsp">
        Set user id: <input type="number" name="setUID" />
        <input type="submit" value="Set" />
        </form>
        <p>Current user id: <%=session.getAttribute("uId")%></p>
        <a href="UserProfile" target="tempPopup">View profile</a>
        <div class="container-sm border" style="height: 500px; padding: 0; position: fixed; top: 50%; left: 50%; transform: translate(-50%,-50%)">
            <iframe name="tempPopup" allowtransparency = "true" style="width: 100%; height: 100%">Profile</iframe>
        </div>
    </body>
</html>
