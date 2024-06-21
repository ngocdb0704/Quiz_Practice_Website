<%-- 
    Document   : DimensionTable
    Created on : Jun 20, 2024, 11:24:02 PM
    Author     : OwO
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="table-responsive mt-4">
    <table class="admin-table table table-striped table-bordered table-hover sortable-theme-bootstrap" data-sortable>
        <thead class="thead-dark">
            <tr>
                <th>ID</th>
                <th>Type</th>
                <th>Dimension</th>
                <th>Description</th>
                <th width="200">Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="dimension" items="${dimensions}">
                <tr>
                    <td>${dimension.dimensionId}</td>
                    <td>${dimension.dimensionType}</td>
                    <td>${dimension.dimensionName}</td>
                    <td>${dimension.description}</td>
                    <td>
                        <a href="DimensionServlet?action=edit&dimensionId=${dimension.dimensionId}&subjectId=${param.subjectId}" class="btn btn-sm btn-primary">Edit</a>
                        <a href="javascript:void(0);" onclick="confirmDelete(${dimension.dimensionId}, ${param.subjectId})" class="btn btn-sm btn-danger btn-primary">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
        <script>
            function confirmDelete(dimensionId, subjectId) {
                if (confirm("Are you sure you want to delete this dimension?")) {
                    window.location.href = "DimensionServlet?action=delete&dimensionId=" + dimensionId + "&subjectId=" + subjectId;
                }
            }
        </script>
    </table>
</div>
