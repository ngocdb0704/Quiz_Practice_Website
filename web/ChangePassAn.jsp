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
        <%@include file="/common/ImportBootstrap.jsp" %>
    </head>
    <body>

        <div id="notification" class="notification"></div>

        <%
                String username = (String) session.getAttribute("userEmail");
                if (username == null || username.length() < 1) {
                session.setAttribute("successMessage", "Not authorized");
                response.sendRedirect("index.jsp");
            } else {
            
        %>


        <div class="modal fade" id="changePassModal" tabindex="-1" role="dialog" >
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
                        </button>
                    </div>
                    <div class="modal-body">
                        <form method="post" action="loginviewofAn" class="form-changepass">
                            <h3 classs="text-center">Change Password</h3>
                            <div class="form-group mb-3">
                                <label for="prePass">Previous Password: </label>
                                <input type="password" name="prePass" class="form-control"/>
                            </div>
                            <div class="form-group mb-3">
                                <label for="newPass">New Password: </label>
                                <input type="password" name="newPass" class="form-control"/>
                            </div>
                            <div class="form-group mb-3">
                                <label for="confirmPass">Confirm Password: </label>
                                <input type="password" name="confirmPass" class="form-control"/>
                            </div>
                            <div class="form-group text-center">
                                <input type="submit" name="submit" value="Change Password" class="btn btn-primary"/>
                                <input type="hidden" name = "service" value = "changepass"/>
                            </div>
                    </div>
                    </form>
                </div>
            </div>
        </div>
    </div>


    <%}%>
</body>
</html>
