<%-- 
    Document   : testModalChangePass
    Created on : May 25, 2024, 8:19:35 PM
    Author     : OwO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#changePassModal">
            Change Pass
        </button>
        <div class="modal fade" id="changePassModal" tabindex="-1" role="dialog" >
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
                        </button>
                    </div>
                    <div class="modal-body">
                        <jsp:include page="/ChangePassAn.jsp" />
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
