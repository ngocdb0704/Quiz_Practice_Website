<%@tag description="custom paginator" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="message"%>
<%@attribute name="total" type="java.lang.Integer" required="true"%>
<%@attribute name="size" type="java.lang.Integer" required="true"%>
<%@attribute name="current" type="java.lang.Integer" required="true"%>
<%@attribute name="url" type="java.lang.String" required="true"%>
<%@attribute name="className" type="java.lang.String" required="false" %>

<jsp:useBean id="paginatorUrlUtils" class="app.utils.URLUtils" />

<c:if test="${empty current}">
    <c:set var="current" value="1" />
</c:if>

<nav class="${className}">
    <c:set var="paramsMap" value="${paginatorUrlUtils.cloneParamsMap(param)}" />
    <ul class="pagination">
        <li class="page-item ${current eq 1 ? 'disabled' : ''}">
            <c:set var="previous" value="${current - 1 < 1 ? 1 : current - 1}" />
            <c:set target="${paramsMap}" property="page" value="${previous.toString()}" />
            <a
                class="page-link"
                href="${url}${paginatorUrlUtils.getQueryParamsString(paramsMap)}"
            >Previous</a>
        </li>

        <c:set var="begin" value="${current - size >= 1 ? current - size : 1}" />
        <c:set var="end" value="${current + size >= total ? total : current + size}" />

        <c:if test="${begin > 1}">
            <li class="page-item ${empty current or current == 1}">
                <c:set target="${paramsMap}" property="page" value="1" />
                <a
                    class="page-link"
                    href="${url}${paginatorUrlUtils.getQueryParamsString(paramsMap)}"
                >1</a>
            </li>
            <c:if test="${begin > 2}">
                <li class="page-item"><a class="page-link">...</a></li>
            </c:if>
        </c:if>

        <c:forEach begin="${begin}" end="${end}" var="page">
            <li class="page-item ${(empty current and page == 1) or (current eq page) ? "active" : ""}">
                <c:set target="${paramsMap}" property="page" value="${page.toString()}" />
                <a
                    class="page-link"
                    href="${url}${paginatorUrlUtils.getQueryParamsString(paramsMap)}"
                >${page}</a>
            </li>
        </c:forEach>

        <c:if test="${end < total}">
            <c:if test="${end < total - 1}">
                <li class="page-item"><a class="page-link">...</a></li>
            </c:if>
            <li class="page-item ${current eq total ? "active" : ""}">
                <c:set target="${paramsMap}" property="page" value="${total.toString()}" />
                <a
                    class="page-link"
                    href="${url}${paginatorUrlUtils.getQueryParamsString(paramsMap)}"
                >${total}</a>
            </li>
        </c:if>

        <li class="page-item ${current eq total ? 'disabled' : ''}">
            <c:set var="next" value="${current + 1 > total ? total : current + 1}" />
            <c:set target="${paramsMap}" property="page" value="${next.toString()}" />
            <a
                class="page-link"
                href="${url}${paginatorUrlUtils.getQueryParamsString(paramsMap)}"
            >Next</a>
        </li>
    </ul>
</nav>