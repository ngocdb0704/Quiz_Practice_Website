/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

let postContainer = document.getElementById("posts");

console.log(postContainer);
function postElement(postId, fullName, postTime, postTitle, postText) {
    return `
            <div class="card">
                <div class="card-body">
                    <img class="profilePic" src="UserProfile?service=showPic&uId=${postId}">
                    <h5 class="card-title">${fullName}</h5>
                    <i>${postTime}</i>
                </div>
                <div class="container">
                    <h5>${postTitle}</h5>
                    <p class="card-text">${postText}</p>
                </div>
                <img class="card-img-bottom" src="./public/images/blogimg.jpg" alt="Card image cap">
            </div>
            `;
} 

function appendHotposts(ammount) {
    if (postContainer) {
        fetch("http://localhost:8080/QuizPractice/Home?service=hotposts")
                .then((res) => res.text())
                .then((text) => {
                    let wordList = JSON.parse(text);
                    console.log(wordList);
                    wordList.forEach((post, index) => {
                        postContainer.innerHTML += postElement(post.BlogId, post.FullName, post.UpdatedTime, post.BlogTitle, post.PostText);
                    });
                });
    }
}

appendHotposts(5)
