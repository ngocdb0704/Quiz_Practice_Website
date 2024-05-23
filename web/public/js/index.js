/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function showNotification(message) {
    var notification = document.getElementById("notification");
    notification.innerHTML = message;
    notification.classList.add("show");
    setTimeout(function () {
        notification.classList.remove("show");
    }, 3000);
}

window.onload = function () {
    var successMessage = '<%= session.getAttribute("successMessage") %>';
    if (successMessage && successMessage !== null) {
        showNotification(successMessage);
        session.removeAttribute("successMessage");
    }
};
