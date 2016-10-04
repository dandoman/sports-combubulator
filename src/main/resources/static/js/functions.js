$(document).ready(function(){
    $("p").click(function(){
        $("p").hide();
    });
});

function goToComingSoon() {
    window.location.href = "/coming-soon.html";
}

function goToNFL() {
    window.location.href = "/nfl";
}

function goToURL(url) {
    window.location.href = url;
}