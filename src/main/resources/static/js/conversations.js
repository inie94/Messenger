'use strict';

var messages = [];


function loadMessages(id) {
    fetch('user/contact/id:' + id + '/messages')
            .then(response => response.json())
            .then(data => {
                messages[id] = data;
                console.log(messages);
            });
}

function viewUserConversations() {
    var element = '';
    contacts.forEach(contact => {
        element += generateConversationTab(contact);
    });
    messagesContent.innerHTML = element;
}

function generateConversationTab(contact) {
    var date = new Date(contact.lastMessage.createdBy);
    var element = (contact.status == 'ONLINE') ? '<div class="card _active" onclick="openConversation(' + contact.user.id + ')">' : '<div class="card" onclick="openConversation(' + contact.user.id + ')">';
    element +=
     '<div class="card_body">' +
         '<div class="card_icon_container"><div class="card_icon"><div class="status_oval"></div></div></div>' +
         '<div class="card_info">' +
             '<div class="card_header">' +
                 '<div class="card_title"><div class="title_text">' + contact.user.firstname + ' ' + contact.user.lastname + '</div></div>' +
                 '<div class="card_last_updated_by"><div class="date_text">' + generateDataOutput(date) + '</div></div>' +
             '</div>' +
             '<div class="card_content">' +
                 '<div class="card_last_content">' +
                     contact.lastMessage.content +
                 '</div>' +
                 '<div class="card_update_count">' +
                     '<div class="message_count">4</div>' +
                 '</div>' +
             '</div>' +
         '</div>' +
     '</div>' +
    '</div>';
    return element;
}

function getUserConversations() {

}


function openConversation(id) {
    loadMessages(id);
    content.classList.remove('_hide');
    content.classList.add('_active');
    control.classList.remove('_active');

}

function generateDataOutput(date) {
    var result = '';
    const day = date.getDay();
    if(day < 10) {
        result = '0' + day + ' ';
    } else {
        result = day + ' ';
    }

    const month = date.getMonth();
    if (month === 0) {
        result += 'Jan ';
    } else if (month === 1) {
        result += 'Feb ';
    } else if (month === 2) {
        result += 'Mar ';
    } else if (month === 3) {
        result += 'Apr ';
    } else if (month === 4) {
        result += 'May ';
    } else if (month === 5) {
        result += 'Jun ';
    } else if (month === 6) {
        result += 'Jul ';
    } else if (month === 7) {
        result += 'Aug ';
    } else if (month === 8) {
        result += 'Sep ';
    } else if (month === 9) {
        result += 'Oct ';
    } else if (month === 10) {
        result += 'Nov ';
    } else if (month === 11) {
        result += 'Dec ';
    }

    const hours = date.getHours();
    const minutes = date.getMinutes();

    if(hours < 10) {
        result += '0' + hours + ':';
    } else {
        result += hours + ':';
    }

    if(minutes < 10) {
        result += '0' + minutes;
    } else {
        result += minutes;
    }

    return result;
}