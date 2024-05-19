/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function sendRedirect(subjectCat, subject) {
    window.location.href = "RegistrationController?search=" + subject + "&subjectCategory=" + subjectCat.value;
}
function myFunction(subject) {
    if (confirm("Confirm cancellation?") === true) {
        window.location.href = "RegistrationController?cancelId=" + subject + "&service=cancel";
    } else {
    }
}
