/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


//check payment after modal's closure
async function checkPaid(price, content, id, responseCaptcha) {
    //if not captcha then alert then return to list
    if (responseCaptcha) {
        try {
            //this response always fetch the first 2 rows
            const response = await fetch(
                    "https://script.google.com/macros/s/AKfycbza8YMZDLO6pST-pdbjKTaXr_-mbhoX6eLGXwRa4YLqORqBZqBHXzmvNz4KH7kloN059g/exec"
                    );
            const sheet = await response.json();
            let isPaid = 0; //flag
            //get all payment
            //this will be a fatal error if the number of transactions in a small amount of time is huge
            let code, pay, acc;
            for (let payment of sheet.data) {
                //if price and content match, leave the loop
                if (payment["Giá trị"] === price && payment["Mô tả"].includes(content)) {
                    code = payment["Mô tả"];
                    acc = payment["Số tài khoản"];
                    isPaid = 1;
                    break;
                }
            }
            //check flag
            if (isPaid === 1) {
                alert("Subject's Successfully Registered!");
                window.location.href = "user/MyRegistrations?paidId=" + id
                        + "&service=paid&code=" + code
                        + "&acc=" + acc;
            } else {
                alert("Payment Not Found!");
            }
        } catch {
            console.error("Error");
        }
    } else {
        alert("Check the captcha please!");
    }

}