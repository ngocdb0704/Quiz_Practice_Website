<%@taglib prefix="myTags" tagdir="/WEB-INF/tags" %>

<aside class="admin-aside bg-dark hide">
    <h5 class="admin-aside-title" data-title="Subject Info">
        <i class="bi bi-book"></i> 
    </h5>
    <div class="admin-aside-links-container">
        <myTags:AdminLink icon="bi-info-circle" href="admin/subjectdetail/overview?subjectId=${param.subjectId}" title="Overview" />
        <myTags:AdminLink icon="bi-bounding-box-circles" href="DimensionServlet?subjectId=${param.subjectId}" title="Dimension" />
        <myTags:AdminLink icon="bi-tag-fill" href="admin/subjectdetail/pricepackage?subjectId=${param.subjectId}" title="Price Package" />
        <div class="mt-5"></div>
        <myTags:AdminLink icon="bi-arrow-left" href="admin/subjectlist" title="Back to List" />
    </div>
</aside>
