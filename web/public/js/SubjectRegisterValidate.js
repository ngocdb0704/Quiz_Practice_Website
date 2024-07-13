/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function checkCaptcha(captcha) {
    let alertCap = document.getElementById("capAlert");
    let cap = document.getElementById("cap");
    let alertbox = document.getElementById("loading");
    let submitbutton = document.getElementById("submitbutton");
    let dummy = document.getElementById("dummyDiv");
    if (captcha) {
        submitbutton.disabled = true;
        alertbox.style.display = 'block';
        alertCap.style.display = 'none';
        dummy.style.display = 'none';
        cap.value = 'approve';
        document.getElementById('sendMail').submit();
    } else {
        dummy.style.display = 'none';
        alertCap.style.display = 'block';
    }
}
