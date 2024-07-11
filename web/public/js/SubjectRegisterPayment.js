/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function hideAllAlert() {
    let alertCap = document.getElementById("capAlert");
    let alertbox = document.getElementById("loading");
    let dummy = document.getElementById("dummyDiv");
    let warn = document.getElementById("notFound");
    dummy.style.display = 'block';
    dummy.style.visibility = 'hidden';
    warn.style.display = 'none';
    alertbox.style.display = 'none';
    alertCap.style.display = 'none';
}

async function checkPaidRegister(price, content, responseCaptcha) {
    //if not captcha then alert then return to list
    let alertCap = document.getElementById("capAlert");
    let alertbox = document.getElementById("loading");
    let dummy = document.getElementById("dummyDiv");
    let warn = document.getElementById("notFound");
    let submitbutton = document.getElementById("submitbutton");
    if (responseCaptcha) {
        try {
            dummy.style.display = 'none';
            alertCap.style.display = 'none';
            alertbox.style.display = 'block';
            warn.style.display = 'none';
            //this response always fetch the first 2 rows
            const response = await fetch(
                    "https://script.google.com/macros/s/AKfycbza8YMZDLO6pST-pdbjKTaXr_-mbhoX6eLGXwRa4YLqORqBZqBHXzmvNz4KH7kloN059g/exec"
                    );
            const sheet = await response.json();
            let isPaid = 0; //flag
            //get all payment
            //this will be a fatal error if the number of transactions in a small amount of time is huge
            let code, acc;
            for (let payment of sheet.data) {
                //if price and content match, leave the loop
                //remove ! after testing
                if (payment["Giá trị"] === price && payment["Mô tả"].includes(content)) {
                    code = payment["Mô tả"];
                    acc = payment["Số tài khoản"];
                    isPaid = 1;
                    break;
                }
            }
            //check flag
            //change this to 1 after testing
            if (isPaid === 1) {
                let accountField = document.getElementById("payAcc");
                accountField.value = acc;
                let contentField = document.getElementById("payCon");
                contentField.value = code;
                document.getElementById("registerForm").submit();
                submitbutton.disabled = true;
                setTimeout(function () {
                    dummy.style.display = 'block';
                    dummy.style.visibility = 'hidden';
                    alertbox.style.display = 'none';
                }, 10000);
            } else {
                setTimeout(function () {
                    dummy.style.display = 'none';
                    warn.style.display = 'block';
                    alertCap.style.display = 'none';
                    alertbox.style.display = 'none';
                }, 3000);

            }
        } catch {
            console.error("Error");
        }
    } else {
        submitbutton.disabled = true;
        dummy.style.display = 'none';
        alertCap.style.display = 'none';
        alertbox.style.display = 'block';
        setTimeout(function () {
            submitbutton.disabled = false;
            dummy.style.display = 'none';
            alertCap.style.display = 'block';
            alertbox.style.display = 'none';
            warn.style.display = 'none';
        }, 3000);
    }

}