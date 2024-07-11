<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="modal fade" id="reviewModal" tabindex="-1" aria-hidden="true">
  <div class="modal-dialog modal-lg modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5">Review Progress</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body modal-filter-buttons" x-data="getFilter()">
        <p>Review before scoring the exam</p>
        <div class="d-flex justify-content-between">
            <div>
                <input id="unanswered" class="btn-check" name="filterBy" value="unanswered" type="radio" x-model="filter">
                <label class="btn btn-outline-secondary" for="unanswered">
                    <i class="bi bi-app"></i>
                    Unanswered
                </label>
                <input id="marked" class="btn-check" name="filterBy" value="marked" type="radio" x-model="filter">
                <label class="btn btn-outline-secondary" for="marked">
                    <i class="bi bi-bookmark-fill"></i>
                    Marked
                </label>
                <input id="answered" class="btn-check" name="filterBy" value="answered" type="radio" x-model="filter">
                <label class="btn btn-outline-secondary" for="answered">
                    <i class="bi bi-check-square-fill"></i>
                    Answered
                </label>
                <input id="all" class="btn-check" name="filterBy" value="all" type="radio" checked x-model="filter">
                <label class="btn btn-outline-secondary" for="all">
                    All
                </label>
            </div>
            <div>
                <button class="btn btn-outline-danger" data-bs-toggle="modal" data-bs-target="#scoreModal">Score Exam Now</button>
            </div>
        </div>
        <div class="mt-3">
            <c:forEach var="ques" items="${all}" varStatus="loop">
                <c:set var="next" value="${loop.index + 1}" />
                <c:set target="${paramsMap}" property="q" value="${next.toString()}" />
                <a
                    class="fast-navigate-button" 
                    data-marked="${ques.isMarked()}"
                    data-current="${loop.index + 1 eq q}"
                    data-answered="${ques.isAnswered()}"
                    x-show="shouldBeVisible"
                    href="user/quizhandle${paginatorUrlUtils.getQueryParamsString(paramsMap)}"
                >
                    <c:if test="${ques.isMarked()}">
                        <i class="bi bi-bookmark-fill"></i>
                    </c:if>
                    ${next}
                </a>
            </c:forEach>
        </div>
      </div>
    </div>
  </div>
</div>

<script>
    function getFilter() {
        return {
            filter: 'all',
            shouldBeVisible() {
                if (this.filter === 'all') return true;
                const { marked, answered } = this.$el.dataset;

                if (this.filter === 'answered' && answered === 'true') {
                    return true;
                } else if (this.filter === 'unanswered' && answered === 'false') {
                    return true;
                } else if (this.filter === 'marked' && marked === 'true') {
                    return true;
                }

                return false;
            }
        }
    }
</script>