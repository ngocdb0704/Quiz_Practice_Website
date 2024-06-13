<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="Blog Item" pageEncoding="UTF-8"%>
<jsp:useBean id="formatter" class="app.utils.FormatData" />

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="blog" type="app.entity.BlogInformation"%>
<%@attribute name="highlight" %>

<div class="card blog-item">
    <img src="public/images/blogimg.jpg" class="card-img-top blog-item-img" alt="...">
    <div class="card-body d-flex flex-column">
        <a href="blogs/detail?id=${blog.getBlogId()}" class="invisible-link">
            <c:if test="${empty highlight}">
                <h4 class="card-title">
                    ${blog.getBlogTitle()}
                </h4>
            </c:if>
            <c:if test="${not empty highlight}">
                <c:set
                    var="split"
                    value="${formatter.splitBySubstring(blog.getBlogTitle(), highlight)}" />
                <h4 class="card-title">
                    ${split[0]}<span class="bg-warning">${split[1]}</span>${split[2]}
                </h4>
            </c:if>
        </a>
            
        <div>
            <span class="badge bg-primary">${blog.getCategoryName()}</span>
        </div>
        <p class="card-text text-body-secondary mt-3">${blog.getPostBrief()}</p>
    </div>
        
    <div class="card-footer d-flex align-items-center justify-content-between">
        <h6 class="card-subtitle text-body-secondary">
            <i class="bi bi-person-circle"></i>
            <b>${blog.getAuthorFullName()}</b>
            | ${formatter.dateFormat(blog.getUpdatedTime())}
        </h6>
        <a href="blogs/detail?id=${blog.getBlogId()}" class="btn blog-read-more btn-primary align-self-end">Read More</a>
    </div>
</div>