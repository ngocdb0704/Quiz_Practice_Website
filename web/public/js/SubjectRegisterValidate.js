/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function checkCaptcha (captcha){
    if(captcha){
        let cap = document.getElementById("cap");
        cap.value = 'approve';
        document.getElementById('sendEmail').submit();
    }else alert('Please check the captcha');
}
