<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="public/css/notyf/notyf.min.css">
<script src="public/js/notyf/notyf.min.js"></script>

<c:if test="${not empty sessionScope.notyfSuccessMessage}">
    <script>
        window.notyf = window.notyf || new Notyf({position: {x: 'right', y: 'top'}, duration: 5000});

        if (window.notyf) {
            window.notyf.success(`${sessionScope.notyfSuccessMessage}`);
        }
    </script>
</c:if>
<c:if test="${not empty sessionScope.notyfErrorMessage}">
    <script>
        window.notyf = window.notyf || new Notyf({position: {x: 'right', y: 'top'}, duration: 5000});

        if (window.notyf) {
            window.notyf.error(`${sessionScope.notyfErrorMessage}`);
        }
    </script>
</c:if>

<jsp:scriptlet>
     if (session != null) {
        session.removeAttribute("notyfSuccessMessage");
        session.removeAttribute("notyfErrorMessage");
     } 
</jsp:scriptlet>