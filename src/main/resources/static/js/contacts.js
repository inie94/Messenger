'use strict';

var contacts = [];
var blacklist = [];

function getContacts() {
    fetch('contacts')
        .then(response => response.json())
        .then(data => {
            data.forEach(contact => {
                contacts[contact.id] = contact;
//                stompClient.subscribe(('/conversation/user/id:' + contact.user.id), onMessageReceived);
//                loadMessages(contact.user.id);
            });
            viewContactList();
//            viewUserConversations();
        });
}

function getBlacklist() {
    fetch('blacklist')
        .then(response => response.json())
        .then(data => {
            data.forEach(companion => {
                blacklist[companion.id] = companion;
//                stompClient.subscribe(('/conversation/user/id:' + contact.user.id), onMessageReceived);
//                loadMessages(contact.user.id);
            });
            viewContactList();
//            viewUserConversations();
        });
}

function generateUserCard(user) {
    var element = (user.status == 'ONLINE') ? '<div class="card _active" id="user-' + user.id + '">' : '<div class="card" id="user-' + user.id + '">';
    element +=
        '<div class="card_body">' +
            '<div class="card_icon_container">' +
                '<div class="card_icon">' +
                    '<div class="status_oval"></div>' +
                '</div>' +
            '</div>' +
            '<div class="card_info">' +
                '<div class="card_header">' +
                    '<div class="card_title"><div class="title_text">' + user.firstname + ' ' + user.lastname + '</div></div>' +
                '</div>' +
                '<div class="card_content">' +
                    '<div class="card_text_info">' + user.email + '</div>' +
                '</div>' +
            '</div>' +
        '</div>'+
        '<div class="card_movement">' +
            '<div class="message_box">' +
                '<input type="text" class="message_input" placeholder="Enter new message">' +
                '<button onclick="sendMessageToUser(' + user.id + ')" class="send" type="submit">Send</button>' +
            '</div>';
    if(!blacklist[user.id]) {
        if(!contacts[user.id]) {
            element += '<div onclick="addToContacts(' + user.id + ')" class="add_button" id="add-btn">Add contact</div>';
        } else {
            element += '<div onclick="deleteContact(' + user.id + ')" class="delete_button" id="del-btn">Delete contact</div>';
        }
        element += '<div onclick="blockContact(' + user.id + ')" class="delete_button" id="block-btn">Block contact</div>';
    } else {
        element += '<div onclick="unblockContact(' + user.id + ')" class="add_button" id="unblock-btn">Unblock contact</div>';
    }

    element += '</div></div>';
    return element;
}

function viewContactList() {
    var element = '';
    element += '<p>Contacts:</p>';
    contacts.forEach(contact => {
        element += generateUserCard(contact);
    });
    element += '<p>Blacklist:</p>';
    blacklist.forEach(companion => {
        element += generateUserCard(companion);
    });
    contactsContent.innerHTML = element;
}

function addToContacts(id) {
    fetch('contacts', {
            method: 'PUT', // *GET, POST, PUT, DELETE, etc.
            headers: {
                [csrfHeader] : csrfToken,
                'charset': 'UTF-8',
                'Content-Type': 'application/json'
            },
            redirect: 'manual', // manual, *follow, error
            body: id
        })
        .then(response => response.json())
        .then(data => {
            contacts[data.id] = data;
            clearSearchInput();
            viewContactList();
            if(currentDialogId) {
                viewDialogUserInfo(data);
            }
        }).catch(err => console.log(err));
}

function deleteContact(id) {
    fetch('contacts', {
            method: 'DELETE', // *GET, POST, PUT, DELETE, etc.
            headers: {
                [csrfHeader] : csrfToken,
                'charset': 'UTF-8',
                'Content-Type': 'application/json'
            },
            redirect: 'manual', // manual, *follow, error
            body: id
        }).then(response => {
            var contact = contacts[id];
            contacts.splice(id, 1);
            viewContactList();
            if(currentDialogId) {
                viewDialogUserInfo(contact);
            }
        }).catch(err => console.log(err));
}

function blockContact(id) {
    fetch('blacklist', {
            method: 'PUT', // *GET, POST, PUT, DELETE, etc.
            headers: {
                [csrfHeader] : csrfToken,
                'charset': 'UTF-8',
                'Content-Type': 'application/json'
            },
            redirect: 'manual', // manual, *follow, error
            body: id
        })
        .then(response => response.json())
        .then(data => {
            contacts.splice(data.id, 1);
            blacklist[data.id] = data;
            viewContactList();
            if (extendsDialogIntoDialogsArrayByContactId(dialogs, data.id)) {
                var dialogId = getDialogIdIntoDialogsArrayByUserId(dialogs, data.id);
                blockedDialogs[dialogId] = dialogs[dialogId];
                dialogs.splice(dialogId, 1);
            }
        }).catch(err => console.log(err));
}

function unblockContact(id) {
    fetch('blacklist', {
            method: 'DELETE', // *GET, POST, PUT, DELETE, etc.
            headers: {
                [csrfHeader] : csrfToken,
                'charset': 'UTF-8',
                'Content-Type': 'application/json'
            },
            redirect: 'manual', // manual, *follow, error
            body: id
        })
        .then(response => {
            blacklist.splice(id, 1);
            if (extendsDialogIntoDialogsArrayByContactId(blockedDialogs, id)) {
                var dialogId = getDialogIdIntoDialogsArrayByUserId(blockedDialogs, id);
                hiddenDialogs[dialogId] = blockedDialogs[dialogId];
                blockedDialogs.splice(dialogId, 1);
            }
            viewContactList();
        }).catch(err => console.log(err));
}