/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

let previewImg = document.getElementById("subject-thumbnail");
let urlSubmission = document.getElementById("thumbnail-url");

//<a href="https://www.flaticon.com/free-icons/broken" title="broken icons">Broken icons created by Rahul Kaklotar - Flaticon</a> Yea anyways
//add onerror="this.src='public/images/image-break.png';" to img for more functionalites in the future
function setPreviewImg() {
    previewImg.src = urlSubmission.value;
}
