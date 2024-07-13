<%-- 
    Document   : SubjectRegisterGuest
    Created on : 3 thg 7, 2024, 16:53:17
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="public/js/SubjectRegisterGuest.js"></script>
        <title>Subject Register</title>
    </head>
    <body>
        <c:set var="packageList" value="${requestScope.map}"/>
        <form action="public/registerSubjectGuest" method="post">
            <div class="container row">
                <div class="card col-6">
                    <img src="public/thumbnails/${param.thumbnail}" 
                         class="card-img-top" alt="...">
                    <div class="card-body">
                        <h5 class="card-title">
                            ${param.subjectName}
                        </h5>
                        <h6>
                            ${param.tagLine}
                        </h6>
                        <h5>Select a package:</h5>
                        <select class="form-select" name="selectedPackage">
                            <c:forEach items="${packageList.entrySet()}" var="item">
                                <c:set var="key" value="${item.getKey()}"/>
                                <c:if test="${item.getKey() == param.subjectId}">
                                    <c:forEach begin="0" end="${item.getValue().size()-1}" var="atP">
                                        <option
                                            ${item.getValue().get(atP).getPackageName().equals(param.registPack)? "selected":""}
                                            value="${item.getValue().get(atP).getPackageId()}">
                                            ${item.getValue().get(atP).getPackageName()} - 
                                            <c:if test="${item.getValue().get(atP).getWorth() != 0}">
                                                save ${item.getValue().get(atP).getWorth()}% for only
                                            </c:if>
                                            ${Integer.valueOf(item.getValue().get(atP).getSalePrice()*1000)} VND
                                        </option>
                                    </c:forEach>
                                </c:if>
                            </c:forEach>
                        </select>
                        <br>
                    </div>
                </div>
                <div class="card col-6">
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
                            <div class="form-text">
                                Full name shouldn't be longer than 50 characters
                                or contain any number, special characters
                                <br> Only Latin-script alphabet letters
                            </div>
                        </div>
                        <div class="col-4">
                            <label class="form-label">Gender</label>
                            <select class="form-select" name="gender" required>
                                <option value="Male" selected>Male</option>
                                <option value="Female">Female</option>
                                <option value="Prefer not to say">Prefer not to say</option>
                            </select>
                            <div class="mt-4 container text-end">
                                <input type="hidden" name="service" value="${param.service}"/>
                                <div class="row">
                                    <div class="col">
                                    </div>
                                    <div class="col">
                                        <button type="submit"
                                                class="btn ${param.service.equals("editRegist")? "btn-warning":"btn-primary"}">
                                            ${param.service.equals("editRegist")? "Save change":"Register"}</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </body>
</html>
