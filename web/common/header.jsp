<nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Quiz Practice</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" href="index.jsp">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="">Courses</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="blogs/list">Blogs</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="RegistrationController">My Registration</a>
                </li>
            </ul>
            <div class="d-flex gap-2">
                <%
                    //Change made by QuanNM, change if you like lol I just need a place holder to see how my popup works.
                    if (session.getAttribute("userEmail") != null) {
                %>
                <p style="margin: auto;"><%=(String)session.getAttribute("userEmail")%></p>
                <img style="height: 50px; cursor: pointer" src="public/images/anonymous-user.webp" alt="View Profile" onclick="displayPopUp('UserProfile')"/>
                <script src="public/js/UserProfile.js"></script>
                <%
                    } else {
                %>
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
                <a href="user/signup">
                    <button class="btn btn-outline-success" type="submit">Sign Up</button>
                </a>
                <%
                    }
                %>
            </div>
        </div>
    </div>
</nav>