/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

let previewImg = document.getElementById("subject-thumbnail");
let urlSubmission = document.getElementById("thumbnail-url");
let imgSetterText = document.getElementById("imgsetter-text");
let setterId = 0;
let delayInterval = 700;
let loadingGif = new URL("public/images/Ellipsis@1x-2.2s-200px-200px.gif", document.baseURI).href;

function imgError() {
    imgSetterText.innerHTML = 'The link did not lead to an image!';
    previewImg.style.display = 'none';
}

function setPreviewImg() {
    imgSetterText.innerHTML = "Link to thumbnail image:";
    previewImg.style.display = 'block';
    previewImg.src = urlSubmission.value;
}

function changeSetter(){
    if (previewImg.style.display !== 'block') {
        previewImg.style.display = 'block';
    }
    if (previewImg.src != loadingGif) {
        previewImg.src = loadingGif;
    }
    clearTimeout(setterId);
    setterId = setTimeout(setPreviewImg, delayInterval);
}
