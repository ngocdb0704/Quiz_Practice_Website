<%-- 
    Document   : UserRegisterThroughMail
    Created on : 11 thg 7, 2024, 23:21:54
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Register</title>
        <%@include file="/common/ImportBootstrap.jsp" %>
        <style>
            .modal-backdrop {
                background-color: rgba(255,255,255,0.3) !important;
            }
            .modal{
                background-color: rgba(255,255,255,0.6) !important;
            }
        </style>
    </head>
    <body>
        <!-- The Modal -->
        <div class="modal" id="registerModal">
            <div class="modal-dialog">
                <div class="modal-content">

                    <!-- Modal Header -->
                    <div class="modal-header text-bg-primary">
                        <h4 class="modal-title">User Register</h4>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>

                    <!-- Modal body -->
                    <div class="modal-body">
                        <form id="sendEmail" action="public/userRegister" method="post">
                            <div class="container row">
                                <h3>Register Form</h3>
                                <div class="mb-3">
                                    <label  class="form-label">Email</label>
                                    <!-- email regEx from w3School: /^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/ -->
                                    <input type="email" class="form-control email${param.subjectId}" 
                                           name="email" required>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Mobile</label>
                                    <input type="text" class="form-control mobile${param.subjectId}"  required
                                           pattern="^[0]{1}\d{9}$" name="mobile">
                                </div>
                                <div class="row mb-3">
                                    <div class="col-8">
                                        <label class="form-label">Full Name</label>
                                        <input type="text" class="form-control" name="fullName"
                                               pattern="^[a-zA-Z]?[a-zA-Z\s]{0,48}[a-zA-Z]$">
                                    </div>
                                    <div class="col-4">
                                        <label class="form-label">Gender</label>
                                        <select class="form-select" name="gender" required>
                                            <option value="Male" selected>Male</option>
                                            <option value="Female">Female</option>
                                            <option value="Prefer not to say">Prefer not to say</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="container row">
                                    <div class="mt-2 text text-secondary">
                                        Full name shouldn't be longer than 50 characters
                                        or contain any number, special characters.
                                        <br>Only Latin-script alphabet letters
                                    </div>
                                </div>
                                <div class="row mb-3 mt-2">
                                    <div class="mt-4 container text-end">
                                        <input type="hidden" name="service" value="userRegister"/>
                                        <div class="row">
                                            <div class="col">
                                            </div>
                                            <div class="col">
                                                <button type="submit" 
                                                        class="btn btn-primary">
                                                    Register</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>

                </div>
            </div>
        </div>

    </body>
</html>
