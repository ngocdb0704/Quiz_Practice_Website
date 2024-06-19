<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="table-responsive mt-4">
<table class="admin-table table table-striped table-bordered table-hover sortable-theme-bootstrap" data-sortable>
    <thead class="thead-dark">
        <tr>
            <th>ID</th>
            <th>Package</th>
            <th>Duration</th>
            <th>List Price</th>
            <th>Sale Price</th>
            <th>Status</th>
            <th width="150">Action</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="pkg" items="${packages}">
            <tr>
                <td>${pkg.getPackageId()}</td>
                <td>${pkg.getPackageName()}</td>
                <td>${pkg.getDuration()}</td>
                <td>${pkg.getListPriceVND()} VND</td>
                <td>${pkg.getSalePriceVND()} VND</td>
                <td>${pkg.isActive() ? 'Active' : 'Inactive'}</td>
                <td width="190" class="d-flex gap-2">
                    <c:choose>
                        <c:when test="${pkg.isActive()}">
                            <button
                                data-bs-target="#deactivateModal"
                                data-bs-toggle="modal"
                                @click="selected = { id: ${pkg.getPackageId()}, name: '${pkg.getPackageName()}' }"
                                class="btn btn-danger flex-grow-1"
                            >Deactivate</button>
                        </c:when>
                        <c:otherwise>
                            <button
                                data-bs-target="#activateModal"
                                data-bs-toggle="modal"
                                @click="selected = { id: ${pkg.getPackageId()}, name: '${pkg.getPackageName()}' }"
                                class="btn btn-primary flex-grow-1"
                            >Activate</button>
                        </c:otherwise>
                    </c:choose>
                    <a href="admin/subjectdetail/pricepackage?action=edit&id=${pkg.getPackageId()}&subjectId=${param.subjectId}" class="btn btn-outline-dark">Edit</button>
                </td>
            </tr>
        </c:forEach>

        <c:if test="${packages.isEmpty()}">
            <tr>
                <td colspan="12" class="text-center">No results</td>
            </tr>
        </c:if>
    </tbody>
</table>
</div>