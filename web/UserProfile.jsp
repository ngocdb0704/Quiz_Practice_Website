<%-- 
    Document   : UserProfile
    Created on : May 12, 2024, 5:57:58 PM
    Author     : quatn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="app.entity.User, app.dal.DAOUser" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Profile</title>
        <link rel="stylesheet" href="public/css/bootstrap/bootstrap.css">
        <link rel="stylesheet" href="public/css/UserProfile.css">
    </head>
    <body>
        <%
        Integer uId = null;
        String role = "", service = "";
        User fetched = null;
        DAOUser dao = new DAOUser();
        
        try {
            uId = Integer.parseInt(session.getAttribute("userId").toString());
            fetched = dao.getUserById(uId);
        } catch (Exception e) {}
        
        %>
        <main>
            <button style="position: absolute; top: -30px; right: -30px; color: red" onclick="closePopUp()">X</button>
        <%
        
        if(fetched == null) {
        %>
            <h1>Please login to view you profile</h1>
        <%
        } else {
        %>
            <div class="row" style="width: 100%; margin: 0;">
                <div class="col-lg-6 col-md-12">
                    <form method="post" action="UserProfile" enctype="multipart/form-data">
                        <div id="img-div">
                            <img style="width: 240px" src="UserProfile?service=showPic" alt="Profile picture" />
                            <input id="upload" type="file" name="upload" onchange="noticeFileUpload(this.value)"/>
                            <label id="upload-label" for="upload">Select image</label>
                        </div>
                        <input type="hidden" name="service" value="updateProfilePicture">
                        <div id="upload-submission">
                            <p id="upload-name">Selected file: none</p>
                            <input type="submit" value="Save as new profile picture" />
                        </div>
                    </form>
                </div>

                <div class="col-lg-6 col-md-12">
                    <div class="row"> Email: <input style="background-color: #cecece; border: 1px solid black" type="text" name="email" value="<%=(fetched != null)? fetched.getEmail(): ""%>" readonly/> </div>
                    <form action="UserProfile" method="POST">
                        <div class="row"> Full name: <input type="text" name="fullName" value="<%=(fetched != null)? fetched.getFullName(): ""%>" /> </div>
                        <div class="row"> Gender: <input type="text" name="gender" value="<%=(fetched != null)? fetched.getGender(): ""%>" /> </div>
                        <div class="row"> Mobile: <input type="text" name="mobile" value="<%=(fetched != null)? fetched.getMobile(): ""%>" /> </div>
                        <br>
                        <div class="row"> <input type="submit" value="Save"> </div>
                        <input type="hidden" name="service" value="update">
                    </form>
                </div>

            </div>
        <%
            role = fetched.getRole();
            if (role.equals("Admin")) {
        %>
            <h1>You're an admin</h1>
        <%
            }
        %>
        </main>
        <%
        }
        %>
        <script src="public/js/UserProfile.js"></script>
    </body>
</html>
