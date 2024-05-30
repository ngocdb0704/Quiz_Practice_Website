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

let body = document.querySelector("body");

function displayPopUp(url) {
    if (body) body.innerHTML += "<iframe style=\"position: fixed; width: 100%; height: 100%; top: 0;\" name=\"popup\" class=\"popup-iframe\" allowtransparency = \"true\" src=\"" + url + "\">Profile</iframe>";
}

function closePopUp() {
    let popupIframe = parent.document.querySelector(".popup-iframe");
    if (body && popupIframe) popupIframe.remove();
}

let saveButton;
let buttonAllowed = "btn btn-primary container", buttonBlocked = "btn btn-outline-secondary disabled container";
let allowSaveName = true;
let allowSaveMobile = true;

function changeSaveButtonStatus() {
    if (!saveButton) saveButton = document.getElementById("saveButton");
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
        }
            
        });
    if (!blocked) allowSaveName = true;
    
    changeSaveButtonStatus();
}

let checkMobile = /^0[9,8][0-9]{8,9}$/i;
function validateMobile(val) {
    if (val.match(checkMobile)) allowSaveMobile = true;
    else allowSaveMobile = false;
    
    changeSaveButtonStatus();
}