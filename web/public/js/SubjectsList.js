/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
function scrollAtLoad() {
    let goTo = document.getElementById('goToPos').value;
    window.scrollTo(0, goTo);
    setTimeout(function () {
        document.getElementById('load').style.display = 'none';
    }, 1000);
}

