const HOME_URL = "http://localhost:8080";
const CURRENT_USER = "currentuser";


const usernameField = document.getElementById("username");
const submitButton = document.getElementById("submit");
let currentUser = undefined;

submitButton.addEventListener("click", function() {
    localStorage.setItem(CURRENT_USER, usernameField.value);
    navigateToChatArea(usernameField.value);
});

function navigateToChatArea(currentUser) {
    localStorage.setItem(CURRENT_USER, currentUser);

    fetch(`${HOME_URL}/user/${currentUser}`, {method:"PUT"})
    .then(response => {
        window.location.replace(`${HOME_URL}/profile`);
    });
}

if (localStorage.getItem(CURRENT_USER)) {
    navigateToChatArea(localStorage.getItem(CURRENT_USER));
}