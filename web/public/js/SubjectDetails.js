/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
const courseraNav =`
                    <div style='position: sticky; top: 0;' class="my-2 py-3 bg-light rounded-2 container-fluid">
                        <nav class="coursera-nav">
                            <span id='nav-about' onclick="navDriect('#about')" class="c-active px-4 py-2">About</span>
                            <span id='nav-outcome' onclick="navDriect('#outcome')" class="px-4 py-2">Outcome</span>
                            <span id='nav-lessons' onclick="navDriect('#lessons')" class="px-4 py-2">Lessons</span>
                            <span id='nav-duration' onclick="navDriect('#duration')" class="px-4 py-2">Duration</span>
                        </nav>
                    </div>
`;

//<a> href to #element-id doesn't work for some reason
function navDriect(tag) {
    if (window.location.href.includes('#')) window.location.href = window.location.href.slice(0, window.location.href.indexOf('#')) + tag;
    else window.location.href += tag;
}

function parse() {
    const description = document.getElementById("subject-description");

    if (description !== null) {
        //console.log(description.innerHTML.trim().replace(/(\r\n|\n|\r)/gm, ""));
        if (description.innerHTML.trim().replace(/(\r\n|\n|\r)/gm, "").match(/^##Coursera style description.*##About.*##Outcome.*##Lessons.*##Duration.*##End$/gm))
            courseraDesc(description);
    }
}

function courseraDesc(description) {
    let des = description.innerHTML;
    let about = des.slice(des.indexOf("##About") + 7, des.indexOf("##Outcome"));
    let outcome = des.slice(des.indexOf("##Outcome") + 9, des.indexOf("##Lessons"));
    let lesson = des.slice(des.indexOf("##Lessons") + 9, des.indexOf("##Duration"));
    let duration = des.slice(des.indexOf("##Duration") + 10, des.indexOf("##End"));

    //console.log([about, outcome, lesson, duration]);

    description.innerHTML = courseraNav;
    description.innerHTML +=
            "<h1 id='about'>About:</h1>" + "<p>" + about + "</p>" +
            "<h1 id='outcome'>Outcome:</h1>" + "<p>" + outcome + "</p>" +
            "<h1 id='lessons'>Lessons:</h1>" + "<p>" + lesson + "</p>" +
            "<h1 id='duration'>Duration:</h1>" + "<p>" + duration + "</p>";
    
    const navAbout = document.getElementById('nav-about');
    const navOutcome = document.getElementById('nav-outcome');
    const navLessons = document.getElementById('nav-lessons');
    const navDuration = document.getElementById('nav-duration');

    $(window).scroll(function () {
        var aboutT = $('#about').offset().top,
                aboutH = $('#about').outerHeight(),
                outcomeT = $('#outcome').offset().top,
                outcomeH = $('#outcome').outerHeight(),
                lessonsT = $('#lessons').offset().top,
                lessonsH = $('#lessons').outerHeight(),
                durationT = $('#duration').offset().top,
                durationH = $('#duration').outerHeight(),
                wH = $(window).height(),
                wS = $(this).scrollTop();
        if (wS > (durationT + durationH - wH  + 150)) {
            //console.log('#duration on the view!');
            navAbout.classList.remove('c-active');
            navOutcome.classList.remove('c-active');
            navLessons.classList.remove('c-active');
            navDuration.classList.add('c-active');
        } else if (wS > (lessonsT + lessonsH - wH  + 500)) {
            //console.log('#lessons on the view!');
            navAbout.classList.remove('c-active');
            navOutcome.classList.remove('c-active');
            navLessons.classList.add('c-active');
            navDuration.classList.remove('c-active');
        } else if (wS > (outcomeT + outcomeH - wH + 500)) {
            //console.log('#outcome on the view!');
            navAbout.classList.remove('c-active');
            navOutcome.classList.add('c-active');
            navLessons.classList.remove('c-active');
            navDuration.classList.remove('c-active');
        } else {
            //console.log('#about on the view!');
            navAbout.classList.add('c-active');
            navOutcome.classList.remove('c-active');
            navLessons.classList.remove('c-active');
            navDuration.classList.remove('c-active');
        }
    });
}
