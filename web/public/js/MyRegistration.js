/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function cancellation (subject){
    if (confirm("Confirm cancellation registration id "+subject+" ?") === true) {
        window.location.href = "RegistrationController?cancelId=" + subject + "&service=cancel";
    }
}
