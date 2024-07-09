<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="myTags" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Simulation Exams</title>

        <%@include file="/common/ImportBootstrap.jsp" %>
        <link href="common/common.css" rel="stylesheet">
        <link href="user/simulation/SimulationExams.css" rel="stylesheet">
        <link rel="stylesheet" href="public/css/sortable/sortable-theme-bootstrap.css">
        <script src="common/common.js"></script>
    </head>
    <body class="d-flex flex-column">
        <%@include file="/common/header.jsp" %>

        <main class="container mt-3 flex-grow-1">
            <h1>Simulation Exams</h1>
            <p class="text-secondary">Here you can find all exams for subjects that are active in your registrations</p>

            <%@include file="/user/simulation/SimulationExamForm.jsp" %>
            <%@include file="/user/simulation/SimulationExamTable.jsp" %>

            <c:if test="${result.getTotalPages() > 0}">
                <myTags:Paginator
                    className="mt-3 d-flex justify-content-end"
                    current="${param.page}"
                    total="${result.getTotalPages()}"
                    size="1"
                    url="user/simulation"
                />
            </c:if>
        </main>

        <%@include file="/common/footer.jsp" %>
        <script src="public/js/sortable/sortable.min.js"></script>
    </body>
</html>
