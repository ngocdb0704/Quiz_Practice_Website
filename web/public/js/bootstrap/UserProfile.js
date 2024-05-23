/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

let uploadSubmission = document.getElementById("upload-submission");
let uploadName = document.getElementById("upload-name");

function noticeFileUpload(name) {
    uploadSubmission.style.display = "block";
    console.log(name);
    console.log(name.lastIndexOf("/"));
    
    uploadName.innerHTML = "Selected file: " + name.slice(name.lastIndexOf("\\") + 1);
}
