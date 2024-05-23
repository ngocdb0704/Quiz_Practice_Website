<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset=utf-8>
        <title>Home</title>
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
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <%@include file="/common/ImportBootstrap.jsp" %>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="common/ExtendBody.css"/>
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
        </main>
        <%@include file="/common/footer.jsp" %>           
    </body>
</html>
