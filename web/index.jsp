<!DOCTYPE html>
<html>
    <head>
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
    </head>
    <body>
        <h1>Welcome to the Home Page</h1>

        <div id="notification" class="notification"></div>

        <!-- Button trigger modal -->
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#loginModal">
            Login
        </button>

        <!-- Modal -->
        <div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="loginModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <jsp:include page="LoginInterface.jsp" />
                    </div>
                </div>
            </div>
        </div>

        
        <form method="post" action="loginviewofAn">
            <input type="submit" name="LogOut" value="Log Out"/>
            <input type="hidden" name="service" value="logout"/>
        </form>
        <form method="post" action="loginviewofAn">
            <input type="hidden" name="service" value="changepass"/>
            <input type="submit" value="Change Password"/>
        </form>
        
        
        
        
    </body>
</html>
