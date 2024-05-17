<%-- 
    Document   : UserProfile
    Created on : May 12, 2024, 5:57:58 PM
    Author     : quatn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="app.entity.Users, app.dal.DAOUsers" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Profile</title>
        <link rel="stylesheet" href="css/bootstrap.css">
    </head>
    <body>
        <%
        String uIdString = null;
        Users fetched = null;
        
        if (session.getAttribute("uId") != null) {
            uIdString = session.getAttribute("uId").toString();
            System.out.println(uIdString);
        }
        
        if (uIdString == null || uIdString.length() < 1) {
            out.print("Provide a uId attribute to display a user");
        }
        else {
            Integer uId =  Integer.parseInt(uIdString);
            DAOUsers dao = new DAOUsers();
            fetched = dao.getUserById(uId);
            System.out.println(fetched);
        }
        %>
        <main class="container-sm border">
            <div class="row">
                <div class="col-md-6 col-sm-12">
                    <img style="min-width: 120px" src="redDot.txt" alt="Red dot" />
                    <a href="UserProfile?service=showPic">AA</a>
                    <form method="post" action="UserProfile" enctype="multipart/form-data">
                        Choose a file: <input type="file" name="test" />
                        <input type="hidden" name="service" value="update">
                        <input type="submit" value="Upload" />
                    </form>
                </div>
                
                <div class="col-md-6 col-sm-12">
                    <div class="row"> Email: <input type="text" name="email" value="<%=(fetched != null)? fetched.getEmail(): ""%>" readonly/> </div>
                    <form action="UserProfile" method="POST">
                    <div class="row"> Full name: <input type="text" name="fullName" value="<%=(fetched != null)? fetched.getFullName(): ""%>" /> </div>
                    <div class="row"> Gender: <input type="text" name="gender" value="<%=(fetched != null)? fetched.getGender(): ""%>" /> </div>
                    <div class="row"> Mobile: <input type="text" name="mobile" value="<%=(fetched != null)? fetched.getMobile(): ""%>" /> </div>
                    <div class="row"> <input type="submit" value="Save"> </div>
                    <input type="hidden" name="service" value="update">
                    </form>
                </div>
                
             </div>
        </main>
    </body>
</html>
