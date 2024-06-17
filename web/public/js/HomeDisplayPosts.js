/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

let postContainer = document.getElementById("posts");
//Loading gif by loading.io
let loading = '<center class="loading"><img src="./public/images/Ellipsis@1x-2.2s-200px-200px.gif" alt="loading..."/></center>';
//tumbleweed.png by flaticon
let endOfFeed = `
            <center class="endOfFeed">
                <img style="width: 256px" src="./public/images/tumbleweed.png" alt="loading..."/>
                <h3>Looks like we're all out of posts...</h3>
                <h4><a href="#sliders" onclick="resetFeed()">Refresh your feed</a> to see new stuffs!</h4>
            </center>
`
let apending = false;
let apendingIntervention = false;

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

let firstLoad = true;
function appendHotposts(ammount, resetOffset) {
    if (postContainer) {
        fetch("QuizPractice/Home?service=hotposts" + (resetOffset? "&resetOffset=true":"") + "&ammount=" + ammount)
                .then((res) => res.text())
                .then((text) => {
                    if (text) {
                        firstLoad = false;
                        let blogList = JSON.parse(text);
                        blogList.forEach((post, index) => {
                            postContainer.innerHTML += postElement(post.BlogId, post.UserId, post.FullName, post.UpdatedTime, post.BlogTitle, post.CardContent);
                        });
                    }
                    else {
                        if (firstLoad) resetFeed();
                        else {
                            postContainer.innerHTML += endOfFeed;
                            apendingIntervention = true;
                        }
                    }
                });
    }
}

function resetFeed() {
    postContainer.innerHTML = "";
    apendingIntervention = false;
    appendHotposts(5, true);
}

$(window).scroll(async function () {
    if ($(window).scrollTop() + $(window).height() > $(document).height() - 100) {
        if (!apending && !apendingIntervention) {
            apending = true;
            //console.log("huh1");
            postContainer.innerHTML += loading;
            setTimeout(() => {
                //console.log("huh");
                postContainer.querySelector(".loading").remove();
                appendHotposts(5, false);
                setTimeout(() => {
                    apending = false;
                    //console.log("huh2");
                }, 1000)
            }, 500)

        }
    }
});
        

