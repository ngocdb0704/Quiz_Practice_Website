<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="app.dal.DAOUser" %>
<%@ page import="app.dal.DAOSlide" %>
<%@ page import="app.entity.User" %>
<%@ page import="java.util.List" %>
<%@ page import="app.entity.Slide" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset=utf-8>
        <title>Home</title>
        <%@include file="/common/ImportBootstrap.jsp" %>
        <link rel="stylesheet" href="common/ExtendBody.css"/>
        <link rel="stylesheet" href="public/css/HomePage.css"/>
        <style>
            .notification {
                background-color: #4CAF50;
                color: white;
                text-align: center;
                padding: 16px;
                position: fixed;
                top: 0;
                left: 50%;
                transform: translateX(-50%);
                z-index: 9999;
                opacity: 0;
                visibility: hidden;
                transition: opacity 0.3s ease-in-out, visibility 0.3s ease-in-out;
            }
            .show {
                opacity: 1;
                visibility: visible;
            }
        </style>
        <script>
            function showNotification(message) {
                var notification = document.getElementById("notification");
                notification.innerHTML = message;
                notification.classList.add("show");
                setTimeout(function () {
                    notification.classList.remove("show");
                }, 3000);
            }
            window.onload = function () {
                var successMessage = '<%= session.getAttribute("successMessage") %>';
                if (successMessage && successMessage !== "null" && successMessage.length > 0) {
                    showNotification(successMessage);
            <% session.removeAttribute("successMessage"); %>
                }
            };
        </script>
    </head>
    <body>
        <%@include file="/common/header.jsp" %>
        <%@include file="/common/sidebar.jsp" %>

        <main class="container">
            <div id="sliders" class="slider carousel slide mh-75" data-bs-ride="carousel">
                <div class="carousel-inner">
                    <% int count = 1; %>
                    <c:forEach var="slider" items="${homeSliders}">
                        <div class="carousel-item <%=(count == 1)? "active" : ""%>">
                            <a href="${slider.getBacklink()}"><img id="slide_<%=count++%>" class="d-block w-100" src="${slider.getImageRef()}" alt="${slider.getTitle()}" ></a>
                        </div>
                    </c:forEach>

                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#sliders" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#sliders" data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>




            <c:if test="${not empty featuredSubjects}">
                <div class="featured-subject">
                    <h2>Featured subjects</h2>

                    <div class="d-flex flex-row flex-nowrap overflow-auto">
                        <c:forEach var="subject" items="${featuredSubjects}">
                            <div class="card">
                                <img class="card-img-top" src="${subject.getThumbnail()}" alt="Card image cap">
                                <div class="card-body">
                                    <h5 class="card-title">${subject.getSubjectName()}</h5>
                                    <p class="card-text">${subject.getTagLine()}></p>
                                    <a href="#" class="btn btn-primary">Explore</a>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:if>

            <div id="posts" class="post-list">
            </div>

            <script src="./public/js/HomeDisplayPosts.js"></script>

        </main>
        <%@include file="/common/footer.jsp" %>
    </body>
</html>
