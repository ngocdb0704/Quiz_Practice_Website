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
                <div class="carousel-item active">
                    <img class="d-block w-100" src="https://i.pinimg.com/originals/30/3f/4c/303f4c853caa0b51acb5dd94ca18f93f.webp" alt="First slide">
                </div>
                <div class="carousel-item">
                    <img class="d-block w-100" src="./public/images/anonymous-user.webp" alt="Second slide">
                </div>
                <div class="carousel-item">
                    <img class="d-block w-100" src="https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fi.pinimg.com%2Foriginals%2F30%2F3f%2F4c%2F303f4c853caa0b51acb5dd94ca18f93f.webp&f=1&nofb=1&ipt=ab1763aa8bd57b08728acb8f7848620e7a4df46cf106fae61ffecebed4f6706b&ipo=images" alt="Third slide">
                </div>
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
        
        <div class="featured-subject">
            <h2>Featured subjects</h2>
            <div class="d-flex flex-row flex-nowrap overflow-auto">

                <div class="card">
                    <img class="card-img-top" src="./public/images/anonymous-user.webp" alt="Card image cap">
                    <div class="card-body">
                        <h5 class="card-title">Subject title</h5>
                        <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                        <a href="#" class="btn btn-primary">Go to subject</a>
                    </div>
                </div>

                <div class="card">
                    <img class="card-img-top" src="./public/images/anonymous-user.webp" alt="Card image cap">
                    <div class="card-body">
                        <h5 class="card-title">Subject title</h5>
                        <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                        <a href="#" class="btn btn-primary">Go to subject</a>
                    </div>
                </div>

                <div class="card">
                    <img class="card-img-top" src="./public/images/anonymous-user.webp" alt="Card image cap">
                    <div class="card-body">
                        <h5 class="card-title">Subject title</h5>
                        <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                        <a href="#" class="btn btn-primary">Go to subject</a>
                    </div>
                </div>

                <div class="card">
                    <img class="card-img-top" src="./public/images/anonymous-user.webp" alt="Card image cap">
                    <div class="card-body">
                        <h5 class="card-title">Subject title</h5>
                        <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                        <a href="#" class="btn btn-primary">Go to subject</a>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="post-list">
            Sort by <button class="btn btn-outline-primary active">Hot <i class="bi bi-fire"></i></button> <button class="btn btn-outline-primary">New <i class="bi bi-bar-chart-line"></i></button>
            
            <div class="card">
                <div class="card-body">
                    <img class="profilePic" src="./public/images/anonymous-user.webp">
                    <h5 class="card-title">Post title</h5>
                    <i>Date</i>
                </div>
                <p class="card-text container">Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                <img class="card-img-bottom" src="./public/images/anonymous-user.webp" alt="Card image cap">
            </div>
            
            <div class="card">
                <div class="card-body">
                    <img class="profilePic" src="./public/images/anonymous-user.webp">
                    <h5 class="card-title">Post title</h5>
                    <i>Date</i>
                </div>
                <p class="card-text container">Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                <img class="card-img-bottom" src="./public/images/anonymous-user.webp" alt="Card image cap">
            </div>
        </div>
        
    </main>
    <%@include file="/common/footer.jsp" %>
</body>
</html>
