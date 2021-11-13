"use strict"
var csrfToken = document.querySelector('#csrf').getAttribute('content');
var csrfHeader = document.querySelector('#csrf_header').getAttribute('content');

const info = document.querySelector('.info');
const conversation = document.querySelector('.conversation');
const control = document.querySelector('.control');
const content = document.querySelector('.content');
const headerMovement = document.querySelector('.header_movement');

// nav buttons
const createChatButton = document.querySelector('#create-chat-button');
const contactsButton = document.querySelector('#contacts-button');
const messagesButton = document.querySelector('#messages-button');
const notificationsButton = document.querySelector('#notifications-button');
const logoutButton = document.querySelector('#logout-button');
const settingsButton = document.querySelector('#settings-button');

// tabs 
const createChatTab = document.querySelector('#create-chat-tab');
const contactsTab = document.querySelector('#contacts-tab');
const messagesTab = document.querySelector('#messages-tab');
const notificationsTab = document.querySelector('#notifications-tab');
const settingsTab = document.querySelector('#settings-tab');

// tabs content
var createChatContent = document.querySelector('#create-chat-content');
var contactsContent = document.querySelector('#contacts-tab').querySelector('.control_content');
var messagesContent = document.querySelector('#messages-tab').querySelector('.control_content');
var notificationsContent = document.querySelector('#notifications-content');
var settingsContent = document.querySelector('#settings-content');

//content

const contentSendButton = document.querySelector('.conversation_footer').querySelector('.send');
var contentInput = document.querySelector('.conversation_footer').querySelector('.message_input');

const headerBtn = document.querySelector('.header_movement');
headerBtn.addEventListener('click', function(e) {
    conversation.classList.toggle('_active');
    info.classList.toggle('_active');
    headerMovement.style.display = 'none';
});

const closeInfoButton = document.querySelector('.close_button');
closeInfoButton.addEventListener('click', function(e) {
    conversation.classList.toggle('_active');
    info.classList.toggle('_active');
    headerMovement.style.display = 'block';
});

const backButton = document.querySelector('.back_button');
backButton.addEventListener('click', function(e) {
    content.classList.toggle('_active');
    control.classList.toggle('_active');
});

var conversationBody = document.querySelector('.conversation_body');
conversationBody.scrollTop = conversationBody.scrollHeight;

function resetActiveTab() {
    document.querySelector('.nav_button._active').classList.remove('_active');
    document.querySelectorAll('.search').forEach(input => {
        input.value = '';
    });
    document.querySelector('.tab._active').classList.remove('_active');
}

function openCreateChat(event) {
    resetActiveTab();
    event.currentTarget.classList.add('_active');
    createChatTab.classList.add('_active');
}

createChatButton.addEventListener('click', openCreateChat);

function openContacts(event) {
    resetActiveTab();
    event.currentTarget.classList.add('_active');
    contactsTab.classList.add('_active');
    viewContactList();
}

contactsButton.addEventListener('click', openContacts);

function openMessages(event) {
    resetActiveTab();
    event.currentTarget.classList.add('_active');
    messagesTab.classList.add('_active');
    viewUserConversations();
}

messagesButton.addEventListener('click', openMessages);

function openNotification(event) {
    resetActiveTab();
    event.currentTarget.classList.add('_active');
    notificationsTab.classList.add('_active');
}

notificationsButton.addEventListener('click', openNotification);

function logout(event) {
//    resetActiveTab();
//    event.currentTarget.classList.add('_active');
//    supportTab.classList.add('_active');
    fetch('/logout', {
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
        headers: {
            [csrfHeader] : csrfToken,
            'charset': 'UTF-8',
            'Content-Type': 'application/json',
        },
        redirect: 'follow', // manual, *follow, error
    })
    .then(response => {
            // HTTP 301 response
            // HOW CAN I FOLLOW THE HTTP REDIRECT RESPONSE?
            if (response.redirected) {
                window.location.href = response.url;
            }
        })
    .catch(function(err) {
        console.info(err + " url: " + url);
    });
}

logoutButton.addEventListener('click', logout);

function openSettings(event) {
    resetActiveTab();
    event.currentTarget.classList.add('_active');
    settingsTab.classList.add('_active');
}

settingsButton.addEventListener('click', openSettings);


