<%@tag description="admin link" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="title" type="java.lang.String" required="true" %>
<%@attribute name="href" type="java.lang.String" required="true" %>
<%@attribute name="icon" type="java.lang.String" required="false" %>

<c:set var="match" value="${urlPattern.contains(href)}" />

<a href="${href}" class="btn admin-aside-link ${match ? 'active' : ''}" data-title="${title}">
    <i class="bi ${icon}"></i>
</a>