'use strict';

var contactsSearchInput = document.querySelector('#contacts-tab').querySelector('.search');
var contacts = [];

function generateContactCard(contact) {
    var element = (contact.status == 'ONLINE') ? '<div class="card _active">' : '<div class="card">';
    element +=
        '<div class="card_body">' +
            '<div class="card_icon_container">' +
                '<div class="card_icon">' +
                    '<div class="status_oval"></div>' +
                '</div>' +
            '</div>' +
            '<div class="card_info">' +
                '<div class="card_header">' +
                    '<div class="card_title"><div class="title_text">' + contact.firstname + ' ' + contact.lastname + '</div></div>' +
                '</div>' +
                '<div class="card_content">' +
                    '<div class="card_last_content">' + contact.email + '</div>' +
                '</div>' +
            '</div>' +
        '</div>'+
        '<div class="card_movement">' +
            '<div onclick="openContactChat(' + contact.id + ')" class="add_button">Write message</div>' +
            '<div onclick="addToContacts(' + contact.id + ')" class="add_button">Add contact</div>' +
            '<div onclick="blockContact(' + contact.id + ')" class="delete_button">Block contact</div>' +
        '</div>' +
    '</div>';
    return element;
}
function viewUserContacts() {
    contactsContent.innerHTML = '';
}

function clearSearchInput() {
    contactsSearchInput.value = '';
    contactsSearchInput.blur();
    viewUserContacts();
}

contactsSearchInput.onkeydown = function(event) {
            if (event.keyCode == 27 || event.key == 27) {
                clearSearchInput();
            }
        };

contactsSearchInput.oninput = function(event) {
    var element = '';
    if(contactsSearchInput.value !== '' && contactsSearchInput.value !== ' ') {
        fetch('/search?value=' + contactsSearchInput.value)
            .then(response => response.json())
            .then(data => {
    //            element += searchHeader('Global search result: ');
                data.forEach(item => {
                    element += generateContactCard(item);
                });
                contactsContent.innerHTML = element;
            });
    } else {
        clearSearchInput();
    }
};

