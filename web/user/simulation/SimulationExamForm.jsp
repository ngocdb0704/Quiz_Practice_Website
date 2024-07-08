<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form class="row my-3 align-items-end">
    <div class="col">
        <label class="form-label">Subject</label>
        <select name="subjectId" class="form-control">
            <option ${param.subjectId eq '-1' ? 'selected' : ''} value="-1">
                All
            </option>
            <c:forEach items="${subjects}" var="subject">
                <option ${subject.getSubjectId().toString() eq param.subjectId ? 'selected' : ''} value="${subject.getSubjectId()}">
                    ${subject.getSubjectName()}
                </option>
            </c:forEach>
        </select>
    </div>

    <div class="col">
        <label class="form-label">Exam Name</label>
        <input class="form-control" name="examName" placeholder="Exam Name" pattern="\S(.*\S)?" type="text" title="Search term must not contain trailing space" value="${param.examName}">
    </div>

    <div class="col-auto">
        <button class="btn btn-primary" type="submit">Search</button>
        <a href="user/simulation" class="btn btn-outline-danger">Reset</a>
    </div>
</form>