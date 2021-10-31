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
const supportButton = document.querySelector('#support-button');
const settingsButton = document.querySelector('#settings-button');

// tabs 
const createChatTab = document.querySelector('#create-chat-tab');
const contactsTab = document.querySelector('#contacts-tab');
const messagesTab = document.querySelector('#messages-tab');
const notificationsTab = document.querySelector('#notifications-tab');
const supportTab = document.querySelector('#support-tab');
const settingsTab = document.querySelector('#settings-tab');

// tabs content
var createChatContent = document.querySelector('#create-chat-content');
var contactsContent = document.querySelector('#contacts-tab').querySelector('.control_content');
var messagesContent = document.querySelector('#messages-content');
var notificationsContent = document.querySelector('#notifications-content');
var supportContent = document.querySelector('#support-content');
var settingsContent = document.querySelector('#settings-content');


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

const dialogs = document.querySelectorAll('.dialog');
dialogs.forEach(dialog => {
    dialog.addEventListener('click', function(e) {
        content.classList.toggle('_active');
        control.classList.toggle('_active');
    });
});

const conversationBody = document.querySelector('.conversation_body');
conversationBody.scrollTop = conversationBody.scrollHeight;

function resetActiveTab() {
    document.querySelector('.nav_button._active').classList.remove('_active');
    document.querySelectorAll('.search').forEach(input => {
        input.value = '';
    });
    document.querySelector('.tab._active').classList.remove('_active');
}

createChatButton.addEventListener('click', function(event) {
    resetActiveTab();
    event.currentTarget.classList.add('_active');
    createChatTab.classList.add('_active');
});

contactsButton.addEventListener('click', function(event) {
    resetActiveTab();
    event.currentTarget.classList.add('_active');
    contactsTab.classList.add('_active');
});

messagesButton.addEventListener('click', function(event) {
    resetActiveTab();
    event.currentTarget.classList.add('_active');
    messagesTab.classList.add('_active');
});

notificationsButton.addEventListener('click', function(event) {
    resetActiveTab();
    event.currentTarget.classList.add('_active');
    notificationsTab.classList.add('_active');
});

supportButton.addEventListener('click', function(event) {
    resetActiveTab();
    event.currentTarget.classList.add('_active');
    supportTab.classList.add('_active');
});

settingsButton.addEventListener('click', function(event) {
    resetActiveTab();
    event.currentTarget.classList.add('_active');
    settingsTab.classList.add('_active');
});