<%@taglib prefix="myTags" tagdir="/WEB-INF/tags" %>

<aside class="admin-aside bg-dark hide">
    <h5 class="admin-aside-title" data-title="Course Content">
        <i class="bi bi-layout-text-sidebar-reverse"></i> 
    </h5>
    <div class="admin-aside-links-container">
        
        <myTags:AdminLink icon="bi bi-book" href="admin/subjectlist" title="Subject List" />
        <myTags:AdminLink icon="bi bi-plus-square-fill" href="admin/newsubject" title="New Subject" />
        <myTags:AdminLink icon="bi-patch-question-fill" href="admin/questionlist" title="Question List" />
        <myTags:AdminLink icon="bi-clipboard-check-fill" href="admin/quizzeslist" title="Quizzes List" />
        <myTags:AdminLink icon="bi bi-arrow-through-heart-fill" href="admin/subjectDimension?subjectId=1" title="Edit Subject" />
        <myTags:AdminLink icon="bi bi-arrow-through-heart-fill" href="admin/sliderlist.jsp" title="Slider List" />
    </div>
    <h5 class="admin-aside-title" data-title="Users Management">
        <i class="bi bi-people"></i> 
    </h5>
    <div class="admin-aside-links-container">
        <myTags:AdminLink icon="bi-people-fill" href="#" title="Users" />
        <myTags:AdminLink icon="bi-shield-lock-fill" href="#" title="Roles" />
    </div>
</aside>
