<head>
    <jsp:include page="/ChangePassAn.jsp" />
</head>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-expand-lg bg-body-tertiary" style="z-index: 1">
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
                    <a class="nav-link" href="public/SubjectsList">Subjects List</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="blogs/list">Blogs</a>
                </li>
                <c:if test="${not empty sessionScope.userEmail}">
                    <li class="nav-item">
                        <a class="nav-link" href="user/MyRegistrations">My Registration</a>
                    </li>
                </c:if>
            </ul>
            <div class="d-flex gap-2">
                <div id="notification" class="notification"></div>

                <c:if test="${not empty sessionScope.userEmail}">
                    <div class="btn-group">
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#userProfileModal">
                            <i class="bi bi-person-circle"></i>
                            ${sessionScope.userEmail}
                        </button>
                    </div>
                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#changePassModal">
                        Change Pass
                    </button>
                </c:if>

                <c:if test="${empty sessionScope.userEmail}">
                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#loginModal">
                        Login
                    </button>
                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#registerModal">
                        Register
                    </button>
                </c:if>
            </div>
        </div>
    </div>
</nav>

<div class="modal fade" id="userProfileModal" tabindex="-1" role="dialog" >
    <div class="modal-dialog modal-lg modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
                </button>
            </div>
            <div class="modal-body">
                <jsp:include page="/UserProfile.jsp" />
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="loginModal" tabindex="-1" role="dialog" >
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
                </button>
            </div>
            <div class="modal-body">
                <jsp:include page="/LoginInterface.jsp" />
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="registerModal" tabindex="-1" role="dialog" >
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
                </button>
            </div>
            <div class="modal-body">
                <jsp:include page="/UserRegisterThroughMail.jsp" />
            </div>
        </div>
    </div>
</div>
