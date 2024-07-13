<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="modal fade" id="scoreModal" tabindex="-1">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5">${answeredCount > 0 ? 'Score' : 'Exit'} Exam</h1>
      </div>
      <div class="modal-body">
        <c:if test="${answeredCount > 0 and answeredCount < all.size()}">
            <p class="fw-bold">${answeredCount} / ${all.size()} questions answered</p>
        </c:if>
        <p>
            By clicking on the [Score Exam] button below, you will complete your current exam and
            receive your score. You will not be able to change any answers after this point
        </p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Back</button>
        <form method="POST">
            <c:if test="${answeredCount > 0}">
                <button type="submit" name="action" value="score" class="btn btn-danger">Score Exam</button>
            </c:if>
            <c:if test="${answeredCount eq 0}">
                <button type="submit" name="action" value="delete" class="btn btn-danger">Exit Exam</button>
            </c:if>
        </form>
      </div>
    </div>
  </div>
</div>
