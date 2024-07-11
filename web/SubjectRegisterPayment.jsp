<%-- 
    Document   : SubjectRegisterPayment
    Created on : 5 thg 7, 2024, 20:07:07
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Subject Register Payment</title>
        <%@include file="/common/ImportBootstrap.jsp" %>
        <link rel="stylesheet" href="common/ExtendBody.css"/>
        <!-- Script google reCaptcha -->
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
        <script src="public/js/SubjectRegisterPayment.js"></script>
    </head>
    <body onload="hideAllAlert()">
        <%@include file="/common/header.jsp" %>
        <main class="container">
            <h1>Subject Register</h1>
            <div class="container">
                <div class="row">
                    <div class="col container">
                        <div class="card mb-5" style="width: 35rem; background-color: #ccccff; opacity: 1.0;">
                            <h2 class="text-center">Registration's detail</h2>
                            <div class="container">
                                <div class="card">
                                    <h4 class="text-danger text-center">User's information:</h4>
                                    <div class="ml-4 container">
                                        <p class="text-secondary">Email:</p>
                                        <h5>${requestScope.email}</h5>
                                        <p class="text-secondary">Mobile:</p>
                                        <h5>${requestScope.mobile}</h5>
                                        <p class="text-secondary">Full Name:</p>
                                        <h5>${requestScope.fullName}</h5>
                                        <p class="text-secondary">Gender:</p>
                                        <h5 class="mb-4">${requestScope.gender}</h5>
                                    </div>
                                </div>
                                <div class="card mt-3 mb-4">
                                    <h4 class="text-primary text-center">Subject's information:</h4>
                                    <div class="ml-4 container">
                                        <p class="text-secondary">Title:</p>
                                        <h5>${requestScope.title}</h5>
                                        <p class="text-secondary">Package:</p>
                                        <h5>${requestScope.packageName}</h5>
                                        <p class="text-secondary">Price:</p>
                                        <h5 class="mb-4">${requestScope.price} VND</h5>
                                    </div>
                                </div>
                                <div class="mt-3 mb-4">
                                    <div class="row">
                                        <div class="col-8">
                                            <div class="g-recaptcha" 
                                                 data-sitekey="6LemYewpAAAAAI4V2BR_nIibN_L8sK23JPuU8MBo">
                                            </div>
                                        </div>
                                        <div class="col-4">
                                            <form id="registerForm" action="public/registerSubjectGuest" method="post">
                                                <input type="hidden" name="email" value="${requestScope.email}"/>
                                                <input type="hidden" name="mobile" value="${requestScope.mobile}"/>
                                                <input type="hidden" name="fullName" value="${requestScope.fullName}"/>
                                                <input type="hidden" name="gender" value="${requestScope.gender}"/>
                                                <input type="hidden" name="selectedPackage" value="${requestScope.packageId}"/>
                                                <input type="hidden" id="payAcc" name="payAcc" value=""/>
                                                <input type="hidden" id="payCon" name="payCon" value=""/>
                                                <input type="hidden" name="service" value="paid"/>
                                            </form>
                                            <button id="submitbutton" type="button" class="btn btn-primary"
                                                    onclick="checkPaidRegister(${requestScope.price},
                                                                    'USER${requestScope.mobile}COURSE${requestScope.packageId}',
                                                                    grecaptcha.getResponse())">Check Payment</button>
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
                                        <div id="notFound" class="alert alert-warning" 
                                             style="display: none;" role="alert">
                                            Payment Not Found!
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>    
                    </div>
                    <div class="col container">
                        <div class="mb-5" style="width: 35rem; background-color: #ccccff; opacity: 1.0;">
                            <h2 class="text-center">Please scan the QR below to pay</h2>
                            <!--style = 1 add background to QR
                                       = 0 no background
                                logo = 1 add bank's logo
                                      = 0 no bank's logo
                                isMask = 1 hide part of account
                                       = 0 show full account
                                bg = 69 background code (there're a lot of backgrounds, ngoc chose 69)
                            -->
                            <img src="https://vietqr.co/api/generate/${requestScope.bankCode}/${requestScope.owner}/VIETQR.CO/${requestScope.price}/USER${requestScope.mobile}COURSE${requestScope.packageId}?style=1&logo=1&isMask=1&bg=22" 
                                 class="img-thumbnail qrimg" 
                                 alt="qrcode">
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <%@include file="/common/footer.jsp" %>
    </body>
</html>
