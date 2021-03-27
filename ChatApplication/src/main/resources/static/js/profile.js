const HOME_URL = "http://localhost:8080";
const CURRENT_USER = "currentuser";

const currentUser = localStorage.getItem(CURRENT_USER);
const addContactButton = document.getElementById("add-contact");
const newContactField = document.getElementById('contact-name');
const contactList = document.getElementById('contact-list');
const conversationSpace = document.getElementById('chat-area');
const logoutButton = document.getElementById('logout');
const sendMessageButton = document.getElementById('send-message-button');
const conversationTitle = document.getElementById('conversation-heading');
const textArea = document.getElementById('textarea');
const rightSpace = document.getElementById('right-space');
const firstLayer = document.getElementById('first-layer');
let otherUser;

addContactButton.addEventListener("click", function() {
    const newContact = newContactField.value;
    saveContact(newContact);
});

function markCurrentUserAsOnline() {
    fetch(`${HOME_URL}/login/${currentUser}`, {method: 'PUT'})
    .catch(err => {
        console.log(err);
    });
}

function getContactStatus(otherUser) {
    fetch(`${HOME_URL}/onlinestatus/${otherUser}`, {method: 'GET'})
    .then(response => response.json())
    .then(res => {
        const elem = document.evaluate( `//li[text()='${otherUser}'][1]`, document, null, XPathResult.ANY_TYPE, null ).iterateNext();
        if (res == true) {
            if (elem.style.color != 'green')
                elem.style.color = 'green';
        }
    });
}

function updateContactList() {
    const contacts = [];
    fetch(`${HOME_URL}/contacts/${currentUser}`)
    .then(response => response.json())
    .then(res => {
        let contactHTML = '';
        for (let i = 0; i < res.length; i++) {
            contactHTML += `<li class='single-contact list-group-item list-group-flush'>${res[i].name}</li>`;
            contacts.push(res[i].name);
        } 
        if (contactList.innerHTML != contactHTML && contacts.length > 0) {
            contactList.innerHTML = contactHTML;
            addClickListenerToContacts();
        }
    })
    .then(res => {
        contacts.forEach(contact => {
            getContactStatus(contact);
        });
    });
}

function saveContact(newContact) {
    if (newContact.length == 0) {
        alert('Contact cannot be empty.');
        return;
    }
    let requestBody = JSON.stringify({otherUser: newContact, currentUser: currentUser});
    fetch(`${HOME_URL}/contact`, {
        method:'PUT',
        headers: { 'Content-Type': 'application/json;charset=utf-8'},
        body: requestBody
    }).then(response => {
        updateContactList();
    })
}

function loadConversation(otherUser, currentUser) {
    promptToSelectConversation();
    if (conversationSpace.innerHTML.length == 0)
        conversationSpace.innerHTML = "Loading conversation..";
    fetch(`${HOME_URL}/conversation?otherUser=${otherUser}&currentUser=${currentUser}`)
    .then(res => res.json())
    .then(result => {
        let conversationHTML = '';
        result = result.conversationItemList;
        if (result == null) {
            conversationSpace.innerHTML = 'No conversation found.';
        } else {
            for (let i = 0; i < result.length; i++) {
                conversationHTML += `<b>${result[i].sender}</b>: ${result[i].message}<br/>`;
            }
            conversationSpace.innerHTML = conversationHTML;
            conversationSpace.scrollTo(0, conversationSpace.scrollHeight);
        }
    })
}

function sendMessage() {
    promptToSelectConversation();
    const typedMessage = textArea.value;
    if (!typedMessage || typedMessage.length == 0) {
        return;
    }
    fetch(`${HOME_URL}/message`, {
        method:'POST',
        headers: { 'Content-Type': 'application/json;charset=utf-8'},
        body: JSON.stringify({to: otherUser, from:currentUser, message: typedMessage})
    }).then(res => {
        textArea.value = '';
        textArea.focus();
        loadConversation(otherUser, currentUser);
    });
}

function addClickListenerToContacts() {
    setTimeout(() => {
        const elements = document.getElementsByClassName("single-contact");
        for (let i = 0; i < elements.length; i++) {
            elements[i].addEventListener('click', function() {
                otherUser = this.innerHTML;
                loadConversation(otherUser, currentUser);
                conversationTitle.innerHTML = otherUser;
                firstLayer.remove();
            });    
        }
    }, 500);
}

logoutButton.addEventListener('click', function() {
    fetch(`${HOME_URL}/logout/${currentUser}`, {method: 'DELETE'})
    .then(localStorage.removeItem(CURRENT_USER))
    .then(window.location.replace(HOME_URL))
});

function isConversationSelected() {
    return otherUser;
}

function promptToSelectConversation() {
    if (!isConversationSelected) {
        alert('Please select a contact to start a conversation.');
    }
}


window.onload = function() {
    markCurrentUserAsOnline();
    updateContactList();
    
    setInterval(function() {
        updateContactList();
    }, 2000);

    setInterval(function() {
        if (isConversationSelected()) {
            loadConversation(otherUser, currentUser);
        }
    }, 1000);


    sendMessageButton.addEventListener('click', sendMessage);
    textArea.addEventListener('keydown', event => {
        if (event.key === 'Enter') {
            sendMessage();
        }
    });
}