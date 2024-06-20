<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="href" type="java.lang.String" required="true" %>
<%@attribute name="key" type="java.lang.String" required="true" %>
<%@attribute name="value" type="java.lang.String" required="true" %>
<%@attribute name="className" type="java.lang.String" required="false" %>
<%@attribute name="target" type="java.lang.String" required="false" %>

<jsp:useBean id="qplUtils" class="app.utils.URLUtils" />
<c:set var="paramsMap" value="${qplUtils.cloneParamsMap(param)}" />
<c:set target="${paramsMap}" property="${key}" value="${value}" />
<c:set target="${paramsMap}" property="page" value="1" />

<a
    class="${className}"
    target="${target}"
    href="${href}${qplUtils.getQueryParamsString(paramsMap)}"
>
    <jsp:doBody />
</a>