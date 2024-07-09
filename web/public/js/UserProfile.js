/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

let uploadSubmission = document.getElementById("upload-submission");
let uploadName = document.getElementById("upload-name");
let fileInput = document.getElementById('upload');
let imgDisplay = document.getElementById('img-display');
let body = document.querySelector("body");
let saveButton = document.getElementById("saveButton");
let buttonAllowed = "form-control btn btn-primary container", buttonBlocked = "form-control btn btn-outline-secondary disabled container";
let fullNameInput = document.getElementById("fullNameInput"), mobileInput = document.getElementById("mobileInput");
let fullNameWarning = document.getElementById("fullNameWarning"), mobileWarning = document.getElementById("mobileWarning");
let allowSaveName = true;
let allowSaveMobile = true;

if (fileInput) fileInput.addEventListener('change', event => {
    if (event.target.files.length > 0) {
        uploadSubmission.style.display = "block";
        let file = event.target.files[0];
        if (file.size > 0) {
            console.log(file.size);

            if (!(file.name.endsWith(".jpg") || file.name.endsWith(".jpeg") || file.name.endsWith(".webp") || file.name.endsWith(".png"))) {
                uploadName.innerHTML = "<strong style=\"color: red;\">Please select an image file!<br>(Your profile picture will not be changed)</strong>";
                imgDisplay.src = "UserProfile?service=showPic";
                fileInput.value = null;
                return;
            }
            if (file.size > 10000000) {
                uploadName.innerHTML = "<strong style=\"color: red;\">The image you just uploaded was too large! Please upload images under 10MB only.</strong>";
                imgDisplay.src = "UserProfile?service=showPic";
                fileInput.value = null;
                return;
            }

            uploadName.innerHTML = "Selected file: " + file.name.slice(file.name.lastIndexOf("\\") + 1);
            uploadName.innerHTML += "<br><p style=\"color: blue;\">(Save profile to save new profile picture)</p>";
            changeSaveButtonStatus();

            imgDisplay.src = URL.createObjectURL(
                    event.target.files[0],
                    );
        }
    }
});

//Deprecated, to be removed soon
function noticeFileUpload(name) {
    
    //console.log(name);
    //console.log(name.lastIndexOf("/"));

    if (name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".webp") || name.endsWith(".png")) {
        uploadName.innerHTML = "Selected file: " + name.slice(name.lastIndexOf("\\") + 1);
        uploadName.innerHTML += "<br><p style=\"color: blue;\">(Save profile to save new profile picture)</p>";
        changeSaveButtonStatus();
        return;
    }

    uploadName.innerHTML = "<strong style=\"color: red;\">Please select an image file!<br>(Your profile picture will not be changed)</strong>";
    fileInput.value = null;
}

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