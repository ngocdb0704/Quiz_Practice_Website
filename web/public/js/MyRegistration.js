/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function cancellation(subject) {
    if (confirm("Confirm cancellation registration id " + subject + " ?") === true) {
        window.location.href = "RegistrationController?cancelId=" + subject + "&service=cancel";
    }
}
//check payment after modal's closure
async function checkPaid(price, content, id) {
    try {
        const response = await fetch("https://script.googleusercontent.com/a/macros/fpt.edu.vn/echo?user_content_key=ytHU4eukGHOj6v2WP4yBdDXK2qOb0Wg0nCr6NgvL5uE_I-Mc0BGcL2dNVMPcqfHwK5tUudKd8cvcTaKa97ge8rlTpOFlaAz4m5_BxDlH2jW0nuo2oDemN9CCS2h10ox_nRPgeZU6HP_D4Q7OAC5yTWrBneWIKbHuSCbn0FEC_3IDbaa_Vmbk5LP19otVKusVvTg-c5gFx-x2hjk3u_fMnHmb0q_VaQokfCov3YcitKdz-1P86jgzQ8a5rOTO1K6G295Y-tqJFAE&lib=Mt8HFMgtInaRk-hnkZZhYAA5bVwgXBXoo");
        const sheet = await response.json();
        let isPaid = 0; //flag
        //get all payment
        //this will be a fatal error if the number of transactions in a small amount of time is huge
        for (let payment of sheet.data) {
            //if price and content match, leave the loop
            if(payment["Giá trị"] === price && payment["Mô tả"].includes(content)) {
                isPaid = 1;
                break;
            }
        }
        //check flag
        if (isPaid === 1) {
            alert("Subject's Successfully Registered!");
            window.location.href = "RegistrationController?paidId=" + id + "&service=paid";
        } else {
            alert("Payment Not Found!");
        }
    } catch {
        console.error("Error");
    }
}