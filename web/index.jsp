<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset=utf-8>
  <title>Home</title>
  <%@include file="/common/ImportBootstrap.jsp" %>
  <link rel="stylesheet" href="common/ExtendBody.css"/>
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
         if (successMessage && successMessage !== null) {
            showNotification(successMessage);
            session.removeAttribute("successMessage");
        }
    };
</script>
</head>
<body>
    <%@include file="/common/header.jsp" %>
    <main class="container">
        <!--DEBUG BY QUANNM, REMOVE SOON-->
        Current user email: <%
        try {
            String userEmail = (String)session.getAttribute("userEmail");
            out.print(userEmail);
        } catch (Exception e) {}
        %>

        <div id="carouselExampleControls" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img class="d-block w-100" src="https://i.pinimg.com/originals/30/3f/4c/303f4c853caa0b51acb5dd94ca18f93f.webp" alt="First slide">
                </div>
                <div class="carousel-item">
                    <img class="d-block w-100" src="https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse3.mm.bing.net%2Fth%3Fid%3DOIP.sSI6M-b_eCig0dSmlyDSpQHaE7%26pid%3DApi&f=1&ipt=af8dd7c13c4a9d6a62cf285a0ce3f749bb2c16ad34394a54b184b17b3eaf423d&ipo=images" alt="Second slide">
                </div>
                <div class="carousel-item">
                    <img class="d-block w-100" src="https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fi.pinimg.com%2Foriginals%2F30%2F3f%2F4c%2F303f4c853caa0b51acb5dd94ca18f93f.webp&f=1&nofb=1&ipt=ab1763aa8bd57b08728acb8f7848620e7a4df46cf106fae61ffecebed4f6706b&ipo=images" alt="Third slide">
                </div>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
    </main>
    <%@include file="/common/footer.jsp" %>
</body>
</html>
