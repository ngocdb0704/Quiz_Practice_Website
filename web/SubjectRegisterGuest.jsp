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
        <title>Subject Register</title>
    </head>
    <body>
        <c:set var="packageList" value="${requestScope.map}"/>
        <form action="user/MyRegistrations" method="post">
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
                        <input type="hidden" name="service" value="${param.service}"/>
                    </div>
                </div>
                <div class="card col-6">
                    <h3>Register Form</h3>
                    <div class="mb-3">
                        <label for="inputEmail" class="form-label">Email address</label>
                        <input type="email" class="form-control" id="inputEmail" 
                               aria-describedby="emailHelp" required>
                        <div id="emailHelp" class="form-text">We'll never share your email with anyone else.</div>
                    </div>
                    <div class="mb-3">
                        <label for="inputMobile" class="form-label">Phone number</label>
                        <input type="text" class="form-control"  required
                               id="inputMobile" aria-describedby="mobileHelp"
                               pattern="^[0]{1}\d{9}$">
                        <div id="mobileHelp" class="form-text">We'll never share your phone number with anyone else.</div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-8">
                            <label for="inputFullName" class="form-label">Full Name</label>
                            <input type="text" class="form-control" 
                                   id="inputFullName" required aria-describedby="nameHelp"
                                   pattern="^[\\p{L} .'-]+$">
                            <div id="nameHelp" class="form-text">
                                Only alphabetical characters; no diacritics or special characters.
                            </div>
                        </div>
                        <div class="col-4">
                            <label for="inputGender" class="form-label">Gender</label>
                            <select class="form-select" id="inputGender" required>
                                <option selected></option>
                                <option value="1">Male</option>
                                <option value="2">Female</option>
                                <option value="3">Prefer not to say</option>
                            </select>
                        </div>
                    </div>
                    <div class="mb-3 container text-end">
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
        </form>
    </body>
</html>
