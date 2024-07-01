<%-- 
    Document   : importquestion
    Created on : Jun 5, 2024
    Author     : hoapmhe173343
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/bootstrap/bootstrap.min.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/importquestion.css"/>
        <link rel="stylesheet" href="https://unpkg.com/nprogress@0.2.0/nprogress.css"/>
    </head>
    <body>
        <div>
            <div id="liveAlertPlaceholder" class="bg-warning"></div>
            <div class="card card-body">
                <p>Select the excel file containing questions to add to the question bank.</p>
                <form action="importquestion" method="post" class="d-flex" enctype="multipart/form-data">
                    <input id="fileInput" type="file" name="file" class="flex-grow-1" accept=".xlsx" required/>
                    <button class="btn btn-primary" type="submit" onclick="confirmUpload(event)">Upload</button>
                </form>
                <a href="${pageContext.request.contextPath}/public/template/question_template.xlsx" 
                   class="btn btn-info col-md-12 mt-3" download>Download the import question template</a>
            </div>
        </div>

        <script src="${pageContext.request.contextPath}/public/js/bootstrap/bootstrap.bundle.min.js"></script>
        <script src="https://unpkg.com/nprogress@0.2.0/nprogress.js"></script>

        <script>
            function btnUpload() {
                NProgress.start();
                
            }

            function confirmUpload(e) {
                const fileInput = document.getElementById('fileInput');
                if (fileInput.value) {
                    var result = confirm("Are you sure you want to upload this file?");
                    if (!result) {
                        e.preventDefault();//cancel
                    }else{
                        btnUpload();
                    }

                }
            }

            window.addEventListener('load', function () {
                if (window.location.href.includes('importquestion')) {
                    NProgress.done();
                }
            });

            const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
            const appendAlert = (message, type) => {
                const wrapper = document.createElement('div');
                wrapper.innerHTML = [
                    `<div class="alert alert-${type} alert-dismissible" role="alert">`,
                    `   <div>${notification}</div>`,
                    '   <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>',
                    '</div>'
                ].join('');

                alertPlaceholder.append(wrapper);
            };

            <c:if test="${notification != null}">
                        appendAlert('${notification}', 'success');
            </c:if>
        </script>
    </body>
</html>
