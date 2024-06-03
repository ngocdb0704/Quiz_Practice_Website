/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

let uploadSubmission = document.getElementById("upload-submission");
let uploadName = document.getElementById("upload-name");
let fileInput = document.getElementById('upload');
let imgDisplay = document.getElementById('img-display');

fileInput.addEventListener('change', event => {
  if (event.target.files.length > 0) {
    imgDisplay.src = URL.createObjectURL(
      event.target.files[0],
    );

    imgDisplay.style.display = 'block';
  }
});

function noticeFileUpload(name) {
    uploadSubmission.style.display = "block";
    //console.log(name);
    //console.log(name.lastIndexOf("/"));

    if (name.endsWith(".jpg") || name.endsWith(".png")) {
        uploadName.innerHTML = "Selected file: " + name.slice(name.lastIndexOf("\\") + 1);
        uploadName.innerHTML += "<br><p style=\"color: blue;\">(Save profile to save new profile picture)</p>";
        changeSaveButtonStatus();

    } else {
        uploadName.innerHTML = "<strong style=\"color: red;\">Please select a .png or .jpg file!<br>(Your profile picture will nout be changed)</strong>";
        fileInput.value = null;
    }
}

let body = document.querySelector("body");

function displayPopUp(url) {
    if (body) body.innerHTML += "<iframe style=\"position: fixed; width: 100%; height: 100%; top: 0;\" name=\"popup\" class=\"popup-iframe\" allowtransparency = \"true\" src=\"" + url + "\">Profile</iframe>";
}

function closePopUp() {
    let popupIframe = parent.document.querySelector(".popup-iframe");
    if (body && popupIframe) popupIframe.remove();
}

let saveButton = document.getElementById("saveButton");
let buttonAllowed = "form-control btn btn-primary container", buttonBlocked = "form-control btn btn-outline-secondary disabled container";
let fullNameInput = document.getElementById("fullNameInput"), mobileInput = document.getElementById("mobileInput");
let fullNameWarning = document.getElementById("fullNameWarning"), mobileWarning = document.getElementById("mobileWarning");
let allowSaveName = true;
let allowSaveMobile = true;

function changeSaveButtonStatus() {
    if (allowSaveName && allowSaveMobile) saveButton.classList = buttonAllowed;
    else saveButton.classList = buttonBlocked;
}

let checkName = /^.*[^a-z].*$|^$/i;
function validateName(val) {    
    let blocked = false;
    val.split(" ").forEach((it, ind) => {
        if (it.match(checkName)) {
            allowSaveName = false;
            blocked = true;
            fullNameInput.classList.add("is-invalid");
            if (fullNameInput.value.length < 1) fullNameWarning.innerHTML = "Please enter your full name";
            else fullNameWarning.innerHTML = "Please don't put numbers and special characters in, and make sure to separate each word with a white space character";
        }
            
        });
    if (!blocked) {
        allowSaveName = true;
        fullNameInput.classList.remove("is-invalid");
        fullNameWarning.innerHTML = "";
    }
    
    changeSaveButtonStatus();
}

let checkMobile = /^0[9,8][0-9]{8,9}$/i;
function validateMobile(val) {
    if (val.match(checkMobile))  {
        allowSaveMobile = true;
        mobileInput.classList.remove("is-invalid");
        mobileWarning.innerHTML = "";
    } else {
        allowSaveMobile = false;
        mobileInput.classList.add("is-invalid");
        if (fullNameInput.value.length < 1) mobileWarning.innerHTML = "Please enter your mobile number";
        else mobileWarning.innerHTML = "Please input 9 to 10 numbers as your mobile number";
    }
    
    changeSaveButtonStatus();
}

function formReset() {
    allowSaveName = true;
    fullNameInput.classList.remove("is-invalid");
    allowSaveMobile = true;
    mobileInput.classList.remove("is-invalid");
    fullNameWarning.innerHTML = "";
    mobileWarning.innerHTML = "";
    imgDisplay.src = "UserProfile?service=showPic";
    fileInput.value = null;
    uploadSubmission.style.display = "none";
    saveButton.classList = buttonBlocked;
}