<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${not empty information ? information.getBlogTitle() : "Not found" }</title>
                <!-- Common import -->
        <%@include file="/common/ImportBootstrap.jsp" %>
        <link href="common/common.css" rel="stylesheet">
        <link href="blogs/BlogDetail.css" rel="stylesheet">
        <script src="common/common.js"></script>
    </head>
    <body class="body-layout">
        <%@include file="/common/header.jsp" %>
        <jsp:useBean id="formatter" class="app.utils.FormatData" />
        
        <main class="container mt-3">
            <div class="row">
                <div class="col-md-8">
                    <h1>${information.getBlogTitle()}</h1>
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <p class="m-0 text-muted">
                                Written by: ${information.getAuthorFullName()}
                            </p>
                            <p class="text-muted">
                                Updated Date: ${formatter.dateFormat(information.getUpdatedTime())}
                            </p>
                        </div>
                        <p class="badge bg-primary">${information.getCategoryName()}</p>
                    </div>

                    <div class="mb-3">
                        <img src="public/images/blogimg.jpg" class="cover-image rounded-3" height="400" width="100%" class="rounded" alt="Main Image">
                    </div>

                    <div class="mb-3 card w-50">
                        <div class="card-body">
                            <h5 class="card-title fw-bold">Table of Contents</h5>
                            <ul>
                                <li><a href="blogs/detail?id=${information.getBlogId()}#heading1">1. Understanding the Exam Format</a></li>
                                <li><a href="blogs/detail?id=${information.getBlogId()}#heading2">2. Effective Study Techniques</a></li>
                                <li><a href="blogs/detail?id=${information.getBlogId()}#heading3">3. Time Management</a></li>
                                <li><a href="blogs/detail?id=${information.getBlogId()}#heading4">4. Practice with Past Papers</a></li>
                                <li><a href="blogs/detail?id=${information.getBlogId()}#heading5">5. On the Day of the Exam</a></li>
                            </ul>
                        </div> 
                    </div>

                    <div>
                        <h2 id="heading1">Understanding the Exam Format</h2>
                        <p>Knowing the structure of the exam you are preparing for is crucial. Make sure to:</p>
                        <ul>
                            <li>Review the syllabus thoroughly.</li>
                            <li>Understand the types of questions that will be asked (e.g., multiple-choice, essay, true/false).</li>
                            <li>Get familiar with the marking scheme.</li>
                        </ul>

                        <h2 id="heading2">Effective Study Techniques</h2>
                        <p>Effective study techniques can significantly improve your retention and understanding of the material:</p>
                        <ul>
                            <li>Use active learning methods such as summarizing information in your own words.</li>
                            <li>Take regular breaks to avoid burnout.</li>
                            <li>Use mnemonic devices to remember key information.</li>
                        </ul>

                        <h2 id="heading3">Time Management</h2>
                        <p>Managing your time efficiently during your study sessions and the exam is essential:</p>
                        <ul>
                            <li>Create a study schedule and stick to it.</li>
                            <li>Prioritize topics based on their importance and difficulty.</li>
                            <li>Allocate specific time slots for revision and practice tests.</li>
                        </ul>

                        <h2 id="heading4">Practice with Past Papers</h2>
                        <p>Practicing with past exam papers can help you get used to the format and timing of the exam:</p>
                        <ul>
                            <li>Simulate exam conditions by timing yourself while practicing.</li>
                            <li>Review your answers and understand your mistakes.</li>
                            <li>Identify patterns in questions and topics that frequently appear.</li>
                        </ul>

                        <h2 id="heading5">On the Day of the Exam</h2>
                        <p>What you do on the day of the exam can also impact your performance:</p>
                        <ul>
                            <li>Get a good night's sleep before the exam day.</li>
                            <li>Have a healthy breakfast to fuel your brain.</li>
                            <li>Arrive at the exam venue early to avoid any last-minute stress.</li>
                        </ul>
                    </div>

                    <div class="mt-5">
                        <h5>Related Posts by same Category</h5>
                    </div>
                </div>

                <div class="col-md-4">
                    <form method="GET" action="blogs/list">
                        <div class="mb-3">
                            <label for="searchBox" class="form-label fs-5 fw-bold">Search</label>
                            <input type="text" placeholder="Search posts..." class="form-control" name="q" id="searchBox">
                        </div>
                    </form>

                    <div class="mb-3">
                        <h5 class="fw-bold">Advertisement</h5>
                        <div class="card text-white">
                            <img src="public/images/ad.jpg" class="card-img" alt="Banner Ad">
                            <div class="card-img-overlay ad-overlay">
                                <h4 class="card-title fw-bold">Boost Your Quiz Skills Today!</h4>
                                <p>
                                    Ace Your Exams with Fun and Interactive Practice Sessions
                                </p>
                                <ul>
                                    <li>Engaging quizzes</li>
                                    <li>Instant feedback</li>
                                    <li>Cheap</li>
                                </ul>
                                <a href="." class="btn btn-primary">Checkout our Courses!</a>
                            </div>
                        </div>
                    </div>

                    <div class="mb-3">
                        <h5 class="fw-bold">Categories</h5>
                        <ul class="list-group">
                            <c:forEach var="cat" items="${categories}">
                            <a
                                href="blogs/list?categoryId=${cat.getCategoryId()}"
                                class="list-group-item ${information.getCategoryId() eq cat.getCategoryId() ? "active" : ""}">
                                ${cat.getCategoryName()}
                            </a>
                            </c:forEach>
                        </ul>
                    </div>

                    <div class="mb-3">
                        <h5 class="fw-bold">Latest Posts</h5>
                        <c:forEach items="${recents}" var="post">
                            <a href="blogs/detail?id=${post.getBlogId()}">
                                <div class="card d-flex flex-row align-items-center mb-3">
                                    <img height="128" src="public/images/blogimg.jpg" class="p-2 rounded-4" alt="${post.getPostBrief()}">
                                    <div class="card-body">
                                        <h5 class="card-title">${post.getBlogTitle()}</h5>
                                        <p class="card-text"><small class="text-muted">${formatter.dateFormat(post.getUpdatedTime())}</small></p>
                                    </div>
                                </div>
                            </a>
                        </c:forEach>
                    </div>

                </div>
            </div>
        </main>
        <%@include file="/common/footer.jsp" %>
    </body>
</html>
