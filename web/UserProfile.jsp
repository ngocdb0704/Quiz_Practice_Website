<%-- 
    Document   : UserProfile
    Created on : May 12, 2024, 5:57:58 PM
    Author     : quatn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="app.entity.Users" %>>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Profile</title>
        <link rel="stylesheet" href="css/bootstrap.css">
    </head>
    <body>
        <%
            Users fetched = (Users)request.getAttribute("UserObj");
        %>
        <main class="container-sm border">
            <div class="row">
                <div class="col-md-6 col-sm-12">
                    <img style="min-width: 120px" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4
                 //8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==" alt="Red dot" />
                </div>
                
                <div class="col-md-6 col-sm-12">
                    <div class="row"> Email: <input type="text" name="email" value="<%=fetched.getEmail()%>" readonly/> </div>
                    <form action="UserProfile" method="POST">
                    <div class="row"> Full name: <input type="text" name="fullName" value="<%=fetched.getFullName()%>" /> </div>
                    <div class="row"> Gender: <input type="text" name="gender" value="<%=fetched.getGender()%>" /> </div>
                    <div class="row"> Mobile: <input type="text" name="mobile" value="<%=fetched.getMobile()%>" /> </div>
                    <div class="row"> <input type="submit" value="Save"> </div>
                    <input type="hidden" name="service" value="update">
                    <input type="hidden" name="uId" value="<%=fetched.getId()%>">
                    </form>
                </div>
                
             </div>
        </main>
    </body>
</html>
