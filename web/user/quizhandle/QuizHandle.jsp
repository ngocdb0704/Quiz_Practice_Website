<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="myTags" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quiz Handle</title>

        <%@include file="/common/ImportBootstrap.jsp" %>
        <link href="common/common.css" rel="stylesheet">
        <link href="user/quizhandle/QuizHandle.css" rel="stylesheet">
        <script src="common/common.js"></script>
    </head>
    <body class="d-flex flex-column">
        <jsp:useBean id="paginatorUrlUtils" class="app.utils.URLUtils" />
        <c:set var="paramsMap" value="${paginatorUrlUtils.cloneParamsMap(param)}" />
        <c:if test="${empty param.q}">
            <c:set var="q" value="1" />
        </c:if>
        <c:if test="${not empty param.q}">
            <c:set var="q" value="${param.q}" />
        </c:if>
        <c:set var="total" value="${result.getTotalPages()}" />
        <c:set var="attemptQuestion" value="${result.getResults()[0]}" />

        <header
            x-data="getHeaderState()"
            class="container-fluid border-bottom py-3 d-flex align-items-center justify-content-end"
        >
            <h2 class="m-0"><i class="bi bi-geo-alt"></i> ${q} / ${total}</h2>
            <div class="mx-2"></div>
            <h2 class="m-0 bg-success text-white rounded-2 p-2">
                <i class="bi bi-hourglass-split"></i>
                <span x-text="timeLeft">0</span>
            </h2>
        </header>
        <main class="container-fluid mt-4 flex-grow-1">
            <div class="container-fluid rounded bg-dark py-2 text-white d-flex justify-content-between">
                <p class="m-0">${q})</p>
                <p class="m-0">Question ID ${attemptQuestion.getQuestion().getQuestionID()}</p>
            </div>
            
            <div class="container">
                <p class="card p-3 shadow text-justify card-text my-4">${attemptQuestion.getQuestion().getQuestionName()}</p>
                
                <form method="POST" class="d-flex flex-column gap-3">
                    <input type="hidden" name="action" value="answer" />
                    <input type="hidden" name="question" value="${attemptQuestion.getQuestion().getQuestionID()}" />
                <c:forEach var="ans" varStatus="loop" items="${attemptQuestion.getQuestion().getAnswers()}">
                    <c:set var="className" value="${ans.getAnswerID() == attemptQuestion.getSelectedAnswer() ? 'btn-secondary' : 'btn-outline-secondary'}" />
                    <button class="text-start btn ${className}" type="submit" name="choice" value="${ans.getAnswerID()}" ${ans.getAnswerID() == attemptQuestion.getSelectedAnswer() ? 'checked' : ''}>
                        <span class="fw-bold">${String.valueOf(Character.toChars(65 + loop.index))}. </span>
                        <span>${ans.getAnswerName()}</span>
                    </button>
                </c:forEach>
                </form>

            </div>
        </main>
        <div class="container-fluid mb-2 d-flex justify-content-end">
            <form method="POST">
                <input type="hidden" name="action" value="mark">
                <input type="hidden" name="question" value="${attemptQuestion.getQuestion().getQuestionID()}" />
                <c:if test="${not attemptQuestion.isMarked()}">
                    <button class="btn btn-outline-success" type="submit">
                        <i class="bi bi-bookmark-fill"></i>
                        Mark for Review
                    </button>
                </c:if>
                <c:if test="${attemptQuestion.isMarked()}">
                    <button class="btn btn-success" type="submit">
                        <i class="bi bi-bookmark-fill"></i>
                        Unmark
                    </button>
                </c:if>
            </form>
        </div>
        <footer class="container-fluid d-flex justify-content-between py-3 bg-success">
            <button class="btn btn-outline-light" data-bs-toggle="modal" data-bs-target="#reviewModal">Review Progress</button>
            
            <div class="action-buttons">
                <c:if test="${q > 1}">
                    <c:set var="previous" value="${q - 1 < 1 ? 1 : q - 1}" />
                    <c:set target="${paramsMap}" property="q" value="${previous.toString()}" />
                    <a class="btn btn-outline-light" href="user/quizhandle${paginatorUrlUtils.getQueryParamsString(paramsMap)}">Previous</a>
                </c:if>

                <c:if test="${q < total}">
                    <c:set var="next" value="${q + 1 > total ? total : q + 1}" />
                    <c:set target="${paramsMap}" property="q" value="${next.toString()}" />
                    <a class="btn btn-outline-light" href="user/quizhandle${paginatorUrlUtils.getQueryParamsString(paramsMap)}">Next</a>
                </c:if>

                <c:if test="${q eq total}">
                    <button class="btn btn-danger">Score Exam</button>
                </c:if>
            </div>
        </footer>

        <%@include file="/user/quizhandle/QuizReviewModal.jsp" %>
        
        <script>
            function getHeaderState() {
                return { 
                    endTimestamp: ${attempt.getEndEpoch()},
                    timeLeft: '...',
                    calculateTimeLeft() {
                        return Math.floor((this.endTimestamp - Date.now()) / 1000);
                    },
                    formatSeconds(seconds) {
                      const hours = Math.floor(seconds / 3600);
                      const minutes = Math.floor((seconds % 3600) / 60);
                      const remainingSeconds = seconds % 60;

                      const paddedHours = String(hours).padStart(2, '0');
                      const paddedMinutes = String(minutes).padStart(2, '0');
                      const paddedSeconds = String(remainingSeconds).padStart(2, '0');

                      return `\${paddedHours}:\${paddedMinutes}:\${paddedSeconds}`;
                    },
                    init() {
                        this.timeLeft = this.formatSeconds(this.calculateTimeLeft());
                        setInterval(() => {
                            this.timeLeft = this.formatSeconds(this.calculateTimeLeft());
                        }, 100);
                    }
                };
            }
        </script>

        <script src="public/js/alpine/core.min.js"></script>
    </body>
</html>
