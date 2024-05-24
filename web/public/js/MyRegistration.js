/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function sendRedirect(subjectCat, subject) {
    window.location.href = "RegistrationController?search=" + subject + "&subjectCategory=" + subjectCat.value;
}
function edit(subjectStatus) {
    if (subjectStatus === 'Submitted') {
            
    } else {
        alert("You can only edit registration whose status is Submitted!");
    }
}
function cancellation(subjectStatus, subject) {
    if (subjectStatus === 'Submitted') {
        if (confirm("Confirm cancellation?") === true) {
            window.location.href = "RegistrationController?cancelId=" + subject + "&service=cancel";
        } else {
        }
    } else {
        alert("You can only cancel registration whose status is Submitted!");
    }

}