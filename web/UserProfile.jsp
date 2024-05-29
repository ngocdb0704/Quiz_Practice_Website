<%-- 
    Document   : UserProfile
    Created on : May 12, 2024, 5:57:58 PM
    Author     : quatn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="app.entity.User, app.dal.DAOUser, app.dal.DAOGender, app.dal.DAORole, java.util.concurrent.ConcurrentHashMap" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- TODO: Format this away from scriptlet spam bc this is pretty unreadable -->
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
        String service = "";
        User fetched = null;
        DAOUser dao = new DAOUser();
        
        //get current page url to redirect to the same page after updating profile
        String currentPage = request.getContextPath();
        try {
            currentPage = request.getAttribute("jakarta.servlet.forward.request_uri").toString();
        }
        catch (Exception e1) {}
        
        //get userId attribute from session, get and set it if the attribute does't exist. 
        try {
            uId = Integer.parseInt(request.getParameter("userId"));
            fetched = dao.getUserById(uId);
        } catch (Exception e) {
            if (session.getAttribute("userEmail") != null) {
                try {
                    fetched = dao.getUserByEmail(session.getAttribute("userEmail").toString());
                    uId = fetched.getUserId();
                    session.setAttribute("userId", uId);
                }
                catch (Exception e1) {System.out.println(e1);}
            }
        }

        
        %>
        <main>
            <%
        
            if(fetched == null) {
            %>
            <h1>Please login to view you profile</h1>
            <%
            } else {
                final int genderId = fetched.getGenderId();
                ConcurrentHashMap genderMap = new DAOGender().getMap();
                
                String role = "";
                try {  //redundancy
                    role = new DAORole().getMap().get(fetched.getRoleId());
                } catch (Exception e){}


            %>
            <div class="card mb-3"> 
                <div class="row g-0" style="width: 100%; margin: 0;">
                    <div class="col-lg-4">
                        <form method="post" action="UserProfile" enctype="multipart/form-data">
                            <div id="img-div" style="border: 1px solid black">
                                <img style="width: 240px" src="UserProfile?service=showPic" alt="Profile picture" />
                                <input id="upload" type="file" name="upload" onchange="noticeFileUpload(this.value)"/>
                                <label id="upload-label" for="upload">Select image</label>
                            </div>
                            <input type="hidden" name="service" value="updateProfilePicture">
                            <input type="hidden" name="redirect" value="<%=currentPage%>">
                            <div id="upload-submission">
                                <p id="upload-name">Selected file: none</p>
                                <input type="submit" value="Save as new profile picture" />
                            </div>
                        </form>
                    </div>

                    <div class="col-lg-8 container-lg">
                        <div class="form-group mb-3"> Email: <input class="form-control" style="background-color: #cecece; border: 1px solid black" type="text" name="email" value="<%=(fetched != null)? fetched.getEmail(): ""%>" readonly/> </div>
                        <form action="UserProfile" method="POST">
                            <div class="form-group mb-3"> Full name: <input class="form-control" type="text" name="fullName" value="<%=(fetched != null)? fetched.getFullName(): ""%>" /> </div>
                            <div class="form-group mb-3"> Gender: 
                                <select class="form-control" name="gender">
                                    <%= genderMap.reduce(0, (key, val) -> "<option value=\"" + key + "\" "
                                            + (((int)key == genderId)? "selected": "")
                                            + "  >" + val + "</option>"
                                            , (option, option1) -> option + "\n" + option1).toString()%>
                                </select>

                            </div>
                            <div class="form-group mb-3"> Mobile: <input class="form-control" type="text" name="mobile" value="<%=(fetched != null)? fetched.getMobile(): ""%>" /> </div>
                            <br>
                            <div class="form-group mb-3"> <input class="form-control" type="submit" value="Save"> </div>
                            <input type="hidden" name="service" value="update">
                            <input type="hidden" name="redirect" value="<%=currentPage%>">
                        </form>
                    </div>
                </div>

                <%if (role.equals("Admin")) {%>
                <p style="position: absolute; top: -55px; color: red">Admin</p>
                <%}%>
            </div>
        </main>
        <%
        }
        %>
        <script src="public/js/UserProfile.js"></script>
    </body>
</html>
