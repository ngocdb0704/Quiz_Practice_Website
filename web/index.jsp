<%@page import="java.util.List, java.util.ArrayList, app.entity.Slide, app.dal.DAOSlide, app.entity.Subject, app.dal.DAOSubject, app.dal.DAOBlog, app.entity.BlogInformation" %>

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
    <% DAOSlide daoSlide = new DAOSlide(); 
       DAOBlog daoBlog = new DAOBlog();
       DAOSubject daoSubject = new DAOSubject();
    %>
    
    
    <%@include file="/common/header.jsp" %>
    <%@include file="/common/sidebar.jsp" %>
    
    <main class="container">
        <div id="sliders" class="slider carousel slide mh-75" data-bs-ride="carousel">
            <div class="carousel-inner">
                <% int count = 1; 
                for (Slide sl : daoSlide.getSliderList()) { %>
                <div class="carousel-item <%=(count == 1)? "active" : ""%>">
                    <a href="<%=sl.getBacklink()%>"><img id="slide_<%=count++%>" class="d-block w-100" src="<%=sl.getImageRef()%>" alt="<%=sl.getTitle()%>" ></a>
                </div>
                <%}%>
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
        
        <% 
        List<Subject> featuredSubjects = daoSubject.getEnoughToDisplay(5);
        if (featuredSubjects.size() > 0) { %>
        <div class="featured-subject">
            <h2>Featured subjects</h2>
            
            <div class="d-flex flex-row flex-nowrap overflow-auto">
                <%for (Subject s: featuredSubjects) {%>
                <div class="card">
                    <img class="card-img-top" src="<%=s.getThumbnail()%>" alt="Card image cap">
                    <div class="card-body">
                        <h5 class="card-title"><%=s.getSubjectName()%></h5>
                        <p class="card-text"><%=s.getTagLine()%></p>
                        <a href="#" class="btn btn-primary">Explore</a>
                    </div>
                </div>
                <%}%>
            </div>
        </div>
        <%}%>
        
        <div class="post-list">
            Sort by <button class="btn btn-outline-primary disabled">Hot <i class="bi bi-fire"></i></button> <button class="btn btn-outline-primary active">New <i class="bi bi-bar-chart-line"></i></button>
            <%for (BlogInformation blog: daoBlog.getBlogListingsPaginated(1, 5)) {%>
            <div class="card">
                <div class="card-body">
                    <img class="profilePic" src="./public/images/anonymous-user.webp">
                    <h5 class="card-title">Post title</h5>
                    <i>Date</i>
                </div>
                <p class="card-text container">Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                <img class="card-img-bottom" src="./public/images/anonymous-user.webp" alt="Card image cap">
            </div>
            <%}%>
        </div>
        
    </main>
    <%@include file="/common/footer.jsp" %>
</body>
</html>
