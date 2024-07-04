<%-- 
    Document   : ContactUs
    Created on : 25 thg 5, 2024, 21:22:09
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Contact Us</title>
        <%@include file="/common/ImportBootstrap.jsp" %>
        <link rel="stylesheet" href="common/ExtendBody.css"/>
        <link rel="stylesheet" href="public/css/ContactUs.css"/>
    </head>
    <body>
        <%@include file="/common/header.jsp" %>
        <main>
            <div class="row">
                <div class="atHead">
                    <h2>Contact Us</h2>
                </div>
                <div class="row">
                    <div class="col-xxl-6">
                        <img class="mt-3" src="https://fthmb.tqn.com/h2aEhPyhwTKVL4f8G8dDTVxYXg8=/5184x3456/filters:fill(auto,1)/GettyImages-537337661-58f6487e5f9b581d59e881d4.jpg"
                             width="700" height="400" />
                    </div>
                    <div class="col-xxl-6">
                        <form action="">
                            <label class="form-label" for="email">Email</label><br>
                            <input type="text" class="form-text" id="email" placeholder="Your email..."><br>
                            <label class="form-label" for="name">Your Name:</label><br>
                            <input type="text" class="form-text" id="name" placeholder="Your name..."><br>
                            <label for="subject">Subject</label><br>
                            <textarea id="subject" placeholder="Write something..">
                            </textarea><br>
                            <input type="submit" value="submit">
                        </form>
                    </div>
                </div>
            </div>
        </main>
        <%@include file="/common/footer.jsp" %> 
    </body>
</html>
