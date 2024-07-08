/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
function showAlert(alertClass) {
    let alertbox = document.getElementsByClassName(alertClass);
    alertbox[0].style.visibility = 'visible';
    setTimeout(function () {
        alertbox[0].style.visibility = 'hidden';
    }, 3000);
}