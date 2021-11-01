'use strict';

var contacts = [];

function getContacts() {
    fetch('user/contacts')
        .then(response => response.json())
        .then(data => {
            contacts = data;
            contacts.forEach(contact => {
                stompClient.subscribe(('/conversation/user/id:' + contact.user.id), onMessageReceived);
                loadMessages(contact.user.id);
            });
            viewContactList();
            viewUserConversations();
        });
}

function generateContactCard(contact) {
    var element = (contact.status == 'ONLINE') ? '<div class="card _active" id="user-' + contact.user.id + '">' : '<div class="card" id="user-' + contact.user.id + '">';
    element +=
        '<div class="card_body">' +
            '<div class="card_icon_container">' +
                '<div class="card_icon">' +
                    '<div class="status_oval"></div>' +
                '</div>' +
            '</div>' +
            '<div class="card_info">' +
                '<div class="card_header">' +
                    '<div class="card_title"><div class="title_text">' + contact.user.firstname + ' ' + contact.user.lastname + '</div></div>' +
                '</div>' +
                '<div class="card_content">' +
                    '<div class="card_text_info">' + contact.user.email + '</div>' +
                '</div>' +
            '</div>' +
        '</div>'+
        '<div class="card_movement">' +
            '<div class="message_box">' +
                '<input type="text" class="message_input" placeholder="Enter new message">' +
                '<button onclick="sendMessageToUser(' + contact.user.id + ')" class="send" type="submit">Send</button>' +
            '</div>';
    if(contacts.filter(item => item.id === contact.user.id).length === 0) {
        element += '<div onclick="addToContacts(' + contact.user.id + ')" class="add_button">Add contact</div>';
    }
    element += '<div onclick="blockContact(' + contact.user.id + ')" class="delete_button">Block contact</div>' +
        '</div>' +
    '</div>';
    return element;
}

function viewContactList() {
    var element = '';
    contacts.forEach(contact => {
        element += generateContactCard(contact);
    });
    contactsContent.innerHTML = element;
}

function addToContacts(id) {
    fetch('contact/id:' + id + '/add')
        .then(response => response.json())
        .then(data => {
            console.log(data);
            contacts.push(data);
            clearSearchInput();
        });
}

function blockContact(id) {
    console.log('block user: ' + id);
}