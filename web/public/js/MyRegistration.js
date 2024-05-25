/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function sendRedirect(subjectStatus, subject) {
    window.location.href = "RegistrationController?search=" + subject + "&subjectCategory=" + subjectStatus;
}
function cancellation(subjectStatus, subject) {
    if (confirm("Confirm cancellation?") === true) {
        window.location.href = "RegistrationController?cancelId=" + subject + "&service=cancel";
    }
}
function updatePrice(sale, subjectClass) {
    document.getElementById(subjectClass).value = sale;
}
