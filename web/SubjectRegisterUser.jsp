<%-- 
    Document   : SubjectRegisterPopUp
    Created on : 3 thg 7, 2024, 01:38:24
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
        <div class="container">
            <form action="user/MyRegistrations" method="post">
                <div class="card">
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
                        <input type="hidden" name="registId" value="${param.registId}"/>
                        <div class="container text-end">
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
        </div>
    </body>
</html>
