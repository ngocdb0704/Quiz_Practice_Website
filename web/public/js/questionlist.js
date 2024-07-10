/**
 *
 * @author Hoapmhe173343
 *
 */
function resetForm() {
    document.getElementById("questionForm").reset();
}

function showAddOptionForm() {
    document.getElementById('addOptionForm').style.display = "block";
}

function hideAddOptionForm() {
    document.getElementById('addOptionForm').style.display = "none";
}

function deleteOption(answerID, questionID) {
    if (confirm("Are you sure you want to delete this option?")) {
        $.ajax({
            url: 'admin/optionanswer',
            type: 'POST',
            data: {action: 'delete', answerID: answerID, questionID: questionID},
            dataType: 'json', 
            success: function (response) {
                if (response.success) {
                    alert(response.message); 
                    location.reload(); 
                } else {
                    alert(response.message); 
                }
            },
            error: function (error) {
                alert("An error occurred while deleting the option: " + error); 
            }
        });
    }
}



function addOption(questionID) {
    var answerName = document.getElementById("answerName").value;
    var isCorrect = document.getElementById("isCorrect").value;
    if (confirm("Would you like to add this option?")) {
        $.ajax({
            url: 'admin/optionanswer',
            type: 'POST',
            data: {
                action: 'add',
                questionID: questionID,
                answerName: answerName,
                isCorrect: isCorrect},
            success: function (response) {
                alert(response.message);
                if (response.success) {
                    location.reload();
                }
            },
            error: function (error) {
                alert("An error occurred while adding the option: " + error);
            }
        });
    }
}

function saveChanges(event) {
    event.preventDefault();
    if (confirm("Do you want to save these changes?")) {
        $.ajax({
            url: 'admin/savechange',
            type: 'POST',
            data: $('#questionForm').serialize(),
            dataType: 'json',
            success: function (response) {
                if (response.status === 'success') {
                    alert('Save successful!');
                } else if (response.status === 'error') {
                    alert(response.message);
                } else {
                    alert('Save failed. Please try again.');
                }
            },
            error: function (error) {
                alert('An unexpected error occurred. Please try again.');
            }
        });
    }
}

function resetFilter() {
    document.querySelector('select[name="subjectId"]').selectedIndex = 0;
    document.querySelector('select[name="lesson"]').selectedIndex = 0;
    document.querySelector('select[name="level"]').selectedIndex = 0;
    document.querySelector('select[name="status"]').selectedIndex = 0;
    document.querySelector('input[name="searchContent"]').value = '';

    document.getElementById('filterForm').submit();
}

function deleteQuestion(questionId) {
    if (confirm("Are you sure you want to delete this question?")) {
        $.ajax({
            url: 'admin/deletequestion',
            type: 'POST',
            data: {
                questionID: questionId
            },
            success: function (response) {
                if (response.status === 'success') {
                    alert('Question deleted successfully.');
                    window.location.href = 'admin/questionlist'; // Redirect to the question list page
                } else if (response.status === 'error') {
                    alert(response.message);
                } else {
                    alert('Delete failed. Please try again.');
                }
            },
            error: function (error) {
                alert('An unexpected error occurred. Please try again.');
            }
        });
    }
}
