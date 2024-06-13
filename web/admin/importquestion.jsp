<%-- 
    Document   : importquestion
    Created on : Jun 5, 2024
    Author     : hoapmhe173343
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/bootstrap/bootstrap.min.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/importquestion.css"/>
    </head>
    <body>
        <div class="container">
            <div class="card card-body">
                <div class="text-center border p-2">Import question </div>
                <p>Select the file excel containing question to add to the question bank.</p>
                <form action="importquestion" method="post" class="d-flex" enctype="multipart/form-data">
                    <input type="file" name="file" class="flex-grow-1" accept=".xlsx" required/>
                    <button class="btn btn-primary" type="submit">Upload</button>
                </form>
                <a href="${pageContext.request.contextPath}/public/template/question_template.xlsx" 
                   class="btn btn-info col-md-12 mt-3" download>Download the import question template</a>
                   <c:if test="${notification != null}">
                    <div class="message">${notification}</div>
                    
                </c:if>
            </div>
        </div>

    </body>
</html>
