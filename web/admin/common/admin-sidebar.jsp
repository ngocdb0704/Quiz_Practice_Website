<%@taglib prefix="myTags" tagdir="/WEB-INF/tags" %>

<aside class="admin-aside bg-dark">
    <h5 class="admin-aside-title" data-title="Course Content">
        <i class="bi bi-book"></i> 
    </h5>
    <div class="admin-aside-links-container">
        <myTags:AdminLink href="admin/questionlist" title="Question List" />
        <myTags:AdminLink href="admin/quizzeslist" title="Quizzes List" />
    </div>
    <h5 class="admin-aside-title" data-title="Users Management">
        <i class="bi bi-people"></i> 
    </h5>
    <div class="admin-aside-links-container">
        <a href="quiz" class="btn admin-aside-link" data-title="Users">
            <i class="bi bi-file-check-fill"></i>
        </a>
        <a href="quiz" class="btn admin-aside-link" data-title="Roles">
            <i class="bi bi-file-check-fill"></i>
        </a>
    </div>
</aside>
