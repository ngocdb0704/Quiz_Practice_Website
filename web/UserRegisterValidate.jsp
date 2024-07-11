<%-- 
    Document   : UserRegisterValidate
    Created on : 11 thg 7, 2024, 14:35:50
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Register</title>
        <%@include file="/common/ImportBootstrap.jsp" %>
        <link rel="stylesheet" href="common/ExtendBody.css"/>
        <!-- Script google reCaptcha -->
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
        <script src="public/js/UserRegister.js"></script>
    </head>
    <body>
        <%@include file="/common/header.jsp" %>
        <main>
            <div class="container">
                <h1>User Register</h1>
                <c:if test="${requestScope.isExist}">
                    <div class="row">
                        <div class="col">

                        </div>
                        <div class="col">
                            <div>
                                <img id="errorImage" 
                                     src="public/images/error01.png"
                                     alt="..."
                                     style="width: 30rem;">
                                <div>
                                    <h5 class="text-danger text-center">Registration Request Fail</h5>
                                    <p class="text-center">${requestScope.message}</p>
                                    <div class="container">
                                        <div class="row">
                                            <div class="col">

                                            </div>
                                            <div class="col">
                                                <a href="home">
                                                    <button type="button" class="btn btn-primary">Back to Home page</button>
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
                </c:if>
                <c:if test="${!requestScope.isExist}">
                    <c:if test="${requestScope.captcha == null}">
                        <div class="row">
                            <div class="col">

                            </div>
                            <div class="col">
                                <div>
                                    <img id="successImage" 
                                         src="public/images/thankImage.png" 
                                         alt="..."
                                         style="width: 30rem;">
                                    <div>
                                        <h5 class="text-success text-center">Registration Requested Successfully</h5>
                                        <p class="text-center">${requestScope.message}</p>
                                        <div class="container">
                                            <div class="row">
                                                <div class="col">

                                                </div>
                                                <div class="col">
                                                    <a href="home">
                                                        <button type="button" class="btn btn-primary">Back to Home page</button>
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
                    </c:if>
                    <c:if test="${requestScope.captcha != null}">
                        <div class="row">
                            <div class="col">

                            </div>
                            <div class="col">
                                <div>
                                    <img id="successImage" 
                                         src="public/images/robot.png" 
                                         alt="..."
                                         style="width: 30rem; height: 50vh;">
                                    <div>
                                        <h5 class="text-primary text-center">Are you a robot?</h5>
                                        <p class="text-center">${requestScope.message}</p>
                                        <div class="container">
                                            <div class="row">
                                                <form id="sendUser" action="public/userRegister" method="post">
                                                    <input type="hidden" name="service" value="userRegister"/>
                                                    <input type="hidden" name="email" value="${requestScope.email}"/>
                                                    <input type="hidden" name="mobile" value="${requestScope.mobile}"/>
                                                    <input type="hidden" name="gender" value="${requestScope.gender}"/>
                                                    <input type="hidden" name="fullName" value="${requestScope.fullName}"/>
                                                    <input type="hidden" id="cap" name="gservice" value=""/>
                                                </form>
                                                <div class="col">
                                                    <div class="g-recaptcha" 
                                                         data-sitekey="6LemYewpAAAAAI4V2BR_nIibN_L8sK23JPuU8MBo">
                                                    </div>
                                                </div>
                                                <div class="col">
                                                    <button id="submitbutton" type="button" 
                                                            onclick="checkCaptcha(grecaptcha.getResponse())" 
                                                            class="btn btn-primary">
                                                        Register
                                                    </button>
                                                </div>
                                                <div id="dummyDiv" class="alert alert-danger"
                                                     style="visibility: hidden;">
                                                    Please check the captcha!
                                                </div>
                                                <div id="loading" class="alert alert-primary" style="display: none;">
                                                    <span class="spinner-border spinner-border-sm" aria-hidden="true"></span>
                                                    <span role="status">Loading...Please wait</span>
                                                </div>
                                                <div id="capAlert" class="alert alert-danger"
                                                     style="display: none;" role="alert">
                                                    Please check the captcha!
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col">

                            </div>
                        </div>
                    </c:if>
                </c:if>
            </div>
        </main>
        <%@include file="/common/footer.jsp" %> 
    </body>
</html>
