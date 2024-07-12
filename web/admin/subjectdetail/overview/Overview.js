const currentImg = document.currentScript.getAttribute("currentImg");
const fieldDefaultBorder = "var(--bs-border-width) solid #dee2e6";
const thumbnailDefaultBorder = "1px solid black";
const changeBorder = "2px solid #fcf762";
let previewImg = document.getElementById("subject-thumbnail");

let uploadName = document.getElementById("image-name");
let fileInput = document.getElementById('image-upload');
let uploadLabel = document.getElementById('upload-label');
let cateSelect = document.getElementById('subject-category');
let featuredCheck = document.getElementById('featured-flag');
let statusSelect = document.getElementById('subject-status');
let imgDiv = document.getElementById('img-div');

let trackChangeTitle = false;
let trackChangeCate = false;
let trackChangeFeatured = false;
let trackChangeStatus = false;
let trackChangeOwner = false;
let trackChangeThumbnail = false;
let trackChangeTagline = false;
let trackChangeBrief = false;
let trackChangeDescription = false;
let changeIndicator = document.getElementById("change-indicator");

function handleThumbnailErr() {
	previewImg.style.display = "none";
}

if (fileInput) fileInput.addEventListener('change', event => {
    if (event.target.files.length > 0) {
        previewImg.style.display = "block";
        let file = event.target.files[0];
        if (file.size > 0) {
            console.log(file.size);

            if (!(file.name.endsWith(".jpg") || file.name.endsWith(".jpeg") || file.name.endsWith(".webp") || file.name.endsWith(".png"))) {
                uploadLabel.innerHTML = "<strong style=\"color: red;\">Selected file was not in one of the supported image formats</strong>";
				previewImg.src = currentImg;
                uploadName.value = "";
                fileInput.value = null;
				trackChangeThumbnail = false;
				changeSaveButtonStatus();
                return;
            }
            if (file.size > 200000000) {
                uploadLabel.innerHTML = "<strong style=\"color: red;\">The image you just uploaded was too large! Please upload images under 200MB only.</strong>";
				previewImg.src = currentImg;
                uploadName.value = "";
                fileInput.value = null;
				trackChangeThumbnail = false;
				changeSaveButtonStatus();
                return;
            }

            uploadName.value = file.name.slice(file.name.lastIndexOf("\\") + 1);
            uploadLabel.innerHTML = "Selected file: " + file.name.slice(file.name.lastIndexOf("\\") + 1);
			trackChangeThumbnail = true;
            changeSaveButtonStatus();

            previewImg.src = URL.createObjectURL(
                    event.target.files[0],
                    );
        }
    }
});

/* Image URL submission and preview, will not be used for now
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

function changeSetter() {
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
*/

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
const briefDefault = "A short paragraph (<300 characters) that describes this subject.";
const briefWarningTxt = "Please don't exceed 300 characters for brief info!";

let changed = false;
window.addEventListener('beforeunload', function (e) {
    if (changed) {
        e.preventDefault();
        e.returnValue = '';
    }
});
function changeSaveButtonStatus() {
	changed =  trackChangeTitle || trackChangeCate || trackChangeFeatured || trackChangeStatus || trackChangeThumbnail || trackChangeOwner || trackChangeTagline || trackChangeBrief || trackChangeDescription;

	if (trackChangeTitle) title.style.border = changeBorder;
	else title.style.border = fieldDefaultBorder;

	title.style.border = (trackChangeTitle )? changeBorder: fieldDefaultBorder;
	cateSelect.style.border = (trackChangeCate )? changeBorder: fieldDefaultBorder;
	featuredCheck.style.border = (trackChangeFeatured )? changeBorder: fieldDefaultBorder;
	statusSelect.style.border = (trackChangeStatus )? changeBorder: fieldDefaultBorder;
	imgDiv.style.border = (trackChangeThumbnail )? changeBorder: thumbnailDefaultBorder;
	tagline.style.border = (trackChangeTagline )? changeBorder: fieldDefaultBorder;
	brief.style.border = (trackChangeBrief )? changeBorder: fieldDefaultBorder;
	desc.style.border = (trackChangeDescription )? changeBorder: fieldDefaultBorder;

	if (changed) changeIndicator.style.display = "inline-block";
	else changeIndicator.style.display = "none";
    if (validTitle && validBrief && validTagline 
		&& changed )
        submitButton.classList.remove("disabled");
    else
        submitButton.classList.add("disabled");
}

function validateTitle(val) {
	trackChangeTitle = true;
    if (val.length > 49) {
        validTitle = false;
        title.classList.add("is-invalid");
        titleWarning.style.display = "block";
    } else {
        validTitle = true;
        title.classList.remove("is-invalid");
        titleWarning.style.display = "none";
    }

    changeSaveButtonStatus();
}

function validateTagline(val) {
	trackChangeTagline = true;
    if (val.length > 49) {
        validTagline = false;
        tagline.classList.add("is-invalid");
        taglineWarning.style.color = "red";
        taglineWarning.innerHTML = taglineWarningTxt;
    } else {
        validTagline = true;
        tagline.classList.remove("is-invalid");
        taglineWarning.style.color = "black";
        taglineWarning.innerHTML = taglineDefault;
    }

    changeSaveButtonStatus();
}

function validateBrief(val) {
	trackChangeBrief = true;
    if (val.length > 299) {
        validBrief = false;
        brief.classList.add("is-invalid");
        briefWarning.style.color = "red";
        briefWarning.innerHTML = briefWarningTxt;
    } else {
        validBrief = true;
        brief.classList.remove("is-invalid");
        briefWarning.style.color = "black";
        briefWarning.innerHTML = briefDefault;
    }

    changeSaveButtonStatus();
}

function handleFeaturedChange() {
	trackChangeFeatured = true;
	changeSaveButtonStatus();
}

function handleStatusChange() {
	trackChangeStatus = true;
	changeSaveButtonStatus();
}

function handleDescChange() {
	trackChangeDescription = true;
	changeSaveButtonStatus();
}

function handleCateChange() {
	trackChangeCate = true;
	changeSaveButtonStatus();
}

function formReset() {
    validateTitle("");
    validateTagline("");
    validateBrief("");
	clearEmail()

	trackChangeTitle = false;
	trackChangeCate = false;
	trackChangeFeatured = false;
	trackChangeStatus = false;
	trackChangeOwner = false;
	trackChangeThumbnail = false;
	trackChangeTagline = false;
	trackChangeBrief = false;
	trackChangeDescription = false;

	changeSaveButtonStatus();
}

let desc = document.getElementById("subject-description");
function setTemplate(templateNum) {
    switch (templateNum) {
        case '1':
            desc.value = "##Coursera style description\n" +
                    "Coursera-inspired description. It's divided into 4 sections and include a navbar.\n" +
                    "Note: This section will not be included in the output.\n" +
                    "##About\n" +
                    "Insert subject's general information here.\n" +
                    "##Outcome\n" +
                    "Insert information about the subject's outcome here.\n" +
                    "##Lessons\n" +
                    "Insert information about the subject's lessons here.\n" +
                    "##Duration\n" +
                    "Insert information about the subject's duration here.\n" +
                    "##End";
            break;
        default:
            break;
    }
}

const expertList = JSON.parse(document.currentScript.getAttribute("expertList"));
const currentOwner = document.currentScript.getAttribute("currentOwner");


let query = document.getElementById("query");
let result = document.getElementById("search-result");
let hiddenEmail = document.getElementById("hiddenEmail");
let chosenExpert = document.getElementById("chosen-expert");

function setEmail(name, email) {
    hiddenEmail.value = email;
    chosenExpert.innerHTML = "Owner will be reasigned as this user:" + resultElement2(name, email);
	trackChangeOwner = true;
    //validExpert = true;
    //submitButton.value = "Create";
    changeSaveButtonStatus();
}

function clearEmail() {
    hiddenEmail.value = "";
    chosenExpert.innerHTML = "";
	trackChangeOwner = true;
    //validExpert = false;
    //submitButton.value = "Please choose an expert ";
    changeSaveButtonStatus();
}

let resultElement = (name, email) => {
    return `
    <div class="experts border border-1 rounded m-1" onclick="setEmail('${name}', '${email}')" data-bs-toggle="modal" data-bs-target=".changeOnwerModal">
       <i class="bi bi-person-circle float-lg-start h-100"></i>
        <p class="result-display mb-1">${name}</p>
        <small>${email}</small>
    </div>

    `;
};


let resultElement2 = (name, email) => {
    return `
    <div class="experts border border-1 rounded m-1">
		<button style=\"background-color: red; margin: 3px;\" class=\"btn btn-close float-end\" onclick=\"clearEmail()\"></button>
       <i class="bi bi-person-circle float-lg-start h-100"></i>
        <p class="result-display mb-1">${name}</p>
        <small>${email}</small>
    </div>

    `;
};

function filterExpert() {  
    let queryTxt = query.value;
	if (queryTxt.length < 1) {
		result.innerHTML = "";
		return;
	}
    let filter = expertList.slice()
            .filter(obj => (obj.email != currentOwner) && (obj.name.includes(queryTxt) | obj.email.includes(queryTxt)))
            .map((obj, ) => resultElement(obj.name, obj.email));
    if (filter.length > 0) result.innerHTML = filter.reduce((acc, obj) => acc + obj);
    else result.innerHTML = "";
}
filterExpert();
