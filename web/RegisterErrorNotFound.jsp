<%-- 
    Document   : RegisterErrorNotFound
    Created on : 5 thg 7, 2024, 16:37:17
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Error</title>
        <%@include file="/common/ImportBootstrap.jsp" %>
        <link rel="stylesheet" href="common/ExtendBody.css"/>
        <link rel="stylesheet" href="public/css/RegisterErrorNotFound.css"/>
    </head>
    <body>
        <%@include file="/common/header.jsp" %>
        <main>
            <div class="container">
                <h1>Subject Register</h1>
                <div class="row">
                    <div class="col">

                    </div>
                    <div class="col">
                        <div>
                            <img id="errorImage" src="public/images/error01.png" alt="...">
                            <div>
                                <h5 class="text-danger text-center">Registration Request Not Found</h5>
                                <p class="text-center">There must be something wrong with your request!</p>
                                <div class="container">
                                    <div class="row">
                                        <div class="col">

                                        </div>
                                        <div class="col">
                                            <a href="home">
                                                <button type="button" class="btn btn-primary">Back to Home</button>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col">

                    </div>
                </div>

            </div>
        </main>
        <%@include file="/common/footer.jsp" %> 
    </body>
</html>
