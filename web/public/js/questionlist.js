/**
 *
 * @author Hoapmhe173343
 *
 */
function resetForm() {
    document.getElementById("questionForm").reset();
}

function showAddOptionForm(){
    document.getElementById('addOptionForm').style.display = "block";
}

function hideAddOptionForm(){
    document.getElementById('addOptionForm').style.display = "none";
}

//function deleteOption(answerID, questionID) {
//    if (confirm("Are you sure you want to delete this option?")) {
//        $.ajax({
//            url: '${pageContext.request.contextPath}/admin/optionanswer',
//            type: 'POST',
//            data: { action: 'delete', answerID: answerID, questionID: questionID },
//            success: function(response) {
//                alert(response.message);
//                if (response.success) {
//                    location.reload();
//                }
//            },
//            error: function(xhr, status, error) {
//                alert("An error occurred while deleting the option: " + error);
//            }
//        });
//    }
//}


function deleteOption(answerID, questionID) {
    console.log("Delete Option called with answerID: " + answerID + " and questionID: " + questionID);
    if (confirm("Are you sure you want to delete this option?")) {
        $.ajax({
            url: 'admin/optionanswer',
            type: 'POST',
            data: { action: 'delete', answerID: answerID, questionID: questionID },
            success: function(response) {
                alert(response.message);
                if (response.success) {
                    location.reload();
                }
            },
            error: function(xhr, status, error) {
                alert("An error occurred while deleting the option: " + error);
            }
        });
    }
}
