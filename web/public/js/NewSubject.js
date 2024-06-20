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

//<a href="https://www.flaticon.com/free-icons/broken" title="broken icons">Broken icons created by Rahul Kaklotar - Flaticon</a> Yea anyways
function imgError() {
    imgSetterText.innerHTML = 'This link does not lead to an image!';
    //previewImg.src='public/images/image-break.png';
    previewImg.style.display = 'none';
}

function setPreviewImg() {
    //alert("Changed");
    imgSetterText.innerHTML = "Link to thumbnail image:";
    previewImg.style.display = 'block';
    previewImg.src = urlSubmission.value;
}

function changeSetter(){
  if (previewImg.style.display !== 'block') {
      previewImg.style.display = 'block';
  }
  if (previewImg.src !== loadingGif) {
      console.log(previewImg.src);
      previewImg.src = loadingGif;
      
  }
  clearTimeout(setterId); //clearing the previous timer using the id
  setterId = setTimeout(setPreviewImg, delayInterval);
}

let submitButton = document.getElementById("submitButton");

let title = document.getElementById("subject-title");
let titleWarning = document.getElementById("title-warning");
let validTitle = true;

let tagline = document.getElementById("subject-tagline");
let taglineWarning = document.getElementById("tagline-warning");
let validTagline = true;
const taglineDefault = "A short sentence (<50 characters) that describes this subject.";
const taglineWarningTxt = "Please don't exceed 50 characters for tagline!";

let brief = document.getElementById("subject-brief");
let briefWarning = document.getElementById("brief-warning");
let validBrief = true;
const briefDefault = "Write a short paragraph (<300 characters) that describes this subject.";
const briefWarningTxt = "Please don't exceed 300 characters for brief info!";

let fileInput = document.getElementById('upload');
let imgDisplay = document.getElementById('img-display');

function changeSaveButtonStatus() {
    if (validTitle && validBrief && validTagline) submitButton.classList.remove("disabled");
    else submitButton.classList.add("disabled");
}

function validateTitle(val) {
    if (val.length > 49) {
        validTitle = false;
        title.classList.add("is-invalid");
        titleWarning.style.display = "block";
    }
    else {
        validTitle = true;
        title.classList.remove("is-invalid");
        titleWarning.style.display = "none";
    }
    
    changeSaveButtonStatus();
}

function validateTagline(val) {
    if (val.length > 49) {
        validTagline = false;
        tagline.classList.add("is-invalid");
        taglineWarning.style.color = "red";
        taglineWarning.innerHTML = taglineWarningTxt;
    }
    else {
        validTagline = true;
        tagline.classList.remove("is-invalid");
        taglineWarning.style.color = "black";
        taglineWarning.innerHTML = taglineDefault;
    }
    
    changeSaveButtonStatus();
}

function validateBrief(val) {
    if (val.length > 299) {
        validBrief = false;
        brief.classList.add("is-invalid");
        briefWarning.style.color = "red";
        briefWarning.innerHTML = briefWarningTxt;
    }
    else {
        validBrief = true;
        brief.classList.remove("is-invalid");
        briefWarning.style.color = "black";
        briefWarning.innerHTML = briefDefault;
    }
    
    changeSaveButtonStatus();
}

function formReset() {
    validateTitle("");
    validateTagline("");
    validateBrief("")
}
