/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function sendRedirect(subjectCat, subject) {
    window.location.href = "RegistrationController?search="+subject+"&subjectCategory=" + subjectCat.value;
}
function myFunction() {
    confirm("Confirm!!!!");
}
