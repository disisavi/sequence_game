// window.onload = function () {
//     gapi.load('auth2', function () {
//         gapi.auth2.init({
//             client_id: '326140469666-jfos4rc28q1vo3c6d2mir28f8jfdlqce.apps.googleusercontent.com'
//         });
//     });
// };

function onSignIn(googleUser) {
    var profile = googleUser.getBasicProfile();
    console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
    console.log('Name: ' + profile.getName());
    console.log('Image URL: ' + profile.getImageUrl());
    console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
}