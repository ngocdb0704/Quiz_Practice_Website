/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

let postContainer = document.getElementById("posts");

function postElement(postId, userId, fullName, postTime, postTitle, CardContent) {
    return `
            <div id="post_${postId}" class="card">
                <div class="card-body">
                    <img class="profilePic" src="UserProfile?service=showPic&uId=${userId}">
                    <h5 class="card-title">${fullName}</h5>
                    <i>${postTime}</i>
                </div>
                <div class="container">
                    <h5>${postTitle}</h5>
                    <p class="card-text">${CardContent}</p>
                </div>
                <img class="card-img-bottom" src="./public/images/blogimg.jpg" alt="Card image cap">
            </div>
            `;
} 

function appendHotposts(ammount, resetOffset) {
    if (postContainer) {
        fetch("http://localhost:8080/QuizPractice/Home?service=hotposts" + (resetOffset? "&resetOffset=true":"") + "&ammount=" + ammount)
                .then((res) => res.text())
                .then((text) => {
                    if (text) {
                        let blogList = JSON.parse(text);
                        blogList.forEach((post, index) => {
                            postContainer.innerHTML += postElement(post.BlogId, post.UserId, post.FullName, post.UpdatedTime, post.BlogTitle, post.CardContent);
                        });
                    }
                });
    }
}

function resetFeed() {
    postContainer.innerHTML = "";
    appendHotposts(5, true);
}

appendHotposts(5, false);
