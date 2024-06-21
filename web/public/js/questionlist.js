/**
 *
 * @author Hoapmhe173343
 *
 */

function updateStatus(questionID, isChecked) {
    $.ajax({
        url: 'admin/questionlist',
        type: 'POST',
        data: {
            questionID: questionID,
            status: isChecked ? 1 : 2
        },
        success: function (response) {
            alert(response.message);
        },
        error: function (xhr, status, error) {
            alert('Error updating status: ' + error);
        }
    });
}

