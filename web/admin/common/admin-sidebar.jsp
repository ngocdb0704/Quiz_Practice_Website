<%@taglib prefix="myTags" tagdir="/WEB-INF/tags" %>

<aside class="admin-aside bg-dark hide">
    <h5 class="admin-aside-title" data-title="Course Content">
        <i class="bi bi-book"></i> 
    </h5>
    <div class="admin-aside-links-container">
        <myTags:AdminLink icon="bi-patch-question-fill" href="admin/questionlist" title="Question List" />
        <myTags:AdminLink icon="bi-clipboard-check-fill" href="admin/quizzeslist" title="Quizzes List" />
    </div>
    <h5 class="admin-aside-title" data-title="Users Management">
        <i class="bi bi-people"></i> 
    </h5>
    <div class="admin-aside-links-container">
        <myTags:AdminLink icon="bi-people-fill" href="#" title="Users" />
        <myTags:AdminLink icon="bi-shield-lock-fill" href="#" title="Roles" />
    </div>
</aside>
