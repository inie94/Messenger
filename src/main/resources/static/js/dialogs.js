'use strict';

var messages = [];
var dialogs = [];
var hiddenDialogs = [];
var blockedDialogs = [];
var currentDialogId;

function getDialogs() {
    fetch('dialogs')
        .then(response => response.json())
        .then(data => {
            data.forEach(dialog => {
                dialogs[dialog.id] = dialog;
                connectToDialog(dialog);
            });
            // check 'SENT' message and do its 'RECEIVED'
            viewUserConversations();
        });
}

function getHiddenDialogs() {
    fetch('dialogs/hidden')
        .then(response => response.json())
        .then(data => {
            data.forEach(dialog => {
                hiddenDialogs[dialog.id] = dialog;
                connectToDialog(dialog);
            });
        });
}

function getBlockedDialogs() {
    fetch('dialogs/blocked')
        .then(response => response.json())
        .then(data => {
            data.forEach(dialog => {
                blockedDialogs[dialog.id] = dialog;
            });
        });
}

function loadMessages(dialogId) {
    fetch('dialogs/id:' + dialogId + '/messages')
        .then(response => response.json())
        .then(data => {
            messages[dialogId] = data;
            readAllMessages(dialogId);
            configureContentTab(dialogs[dialogId]);
        });
}

function readAllMessages(dialogId) {
    fetch('dialogs/messages/read', {
            method: 'POST',
            headers: {
                [csrfHeader] : csrfToken,
                'charset': 'UTF-8',
                'Content-Type': 'application/json'
            },
            redirect: 'manual',
            body: dialogId
        })
        .then(response => {
            dialogs[dialogId].newMessagesCount = 0;
            viewUserConversations();
            viewDialogMessages(dialogId);
        }).catch(err => console.log(err));
}

function viewUserConversations() {
    var element = '';
    dialogs.forEach(dialog => {
        if(dialog.lastMessage) {
            element += generateConversationTab(dialog);
            allMessagesReceived(dialog.id);
        }
    });
    messagesContent.innerHTML = element;
}

function generateConversationTab(dialog) {
    var date = new Date(dialog.lastMessage.createdBy);
    var element = '';
    if (dialog.user.status == 'ONLINE') {
        element = '<div class="card _active" id="dialog-' + dialog.id + '">';
    } else {
        element = '<div class="card" id="dialog-' + dialog.id + '">';
    }
    element +=
     '<div class="card_body" onclick="openConversation(' + dialog.id + ')">' +
         '<div class="card_icon_container"><div class="card_icon"><div class="status_oval"></div></div></div>' +
         '<div class="card_info">' +
             '<div class="card_header">' +
                 '<div class="card_title"><div class="title_text">' + dialog.user.firstname + ' ' + dialog.user.lastname + '</div></div>' +
                 '<div class="card_last_updated_by"><div class="date_text">' + generateDataOutput(date) + '</div></div>' +
             '</div>' +
             '<div class="card_content">' +
                 '<div class="card_last_content">' +
                     dialog.lastMessage.content +
                 '</div>' +
                 '<div class="card_update_count">';
     if (dialog.newMessagesCount && dialog.newMessagesCount != 0) {
        element += '<div class="message_count _active">'+ dialog.newMessagesCount + '</div>';
     } else {
        element += '<div class="message_count">'+ dialog.newMessagesCount + '</div>';
     }
     element += '</div>' +
             '</div>' +
         '</div>' +
     '</div>' +
     '<div class="card_movement">' +
         '<div onclick="hideDialogById(' + dialog.id +')" class="delete_button">Delete dialog</div>'
     '</div>' +
    '</div>';
    return element;
}

function allMessagesReceived(dialogId) {
    fetch('dialogs/messages/receive', {
            method: 'POST', // *GET, POST, PUT, DELETE, etc.
            headers: {
                [csrfHeader] : csrfToken,
                'charset': 'UTF-8',
                'Content-Type': 'application/json'
            },
            redirect: 'manual', // manual, *follow, error
            body: dialogId
        })
//        .then(response => response.json())
//        .then(data => {
//            dialogs.splice(data.id, 1);
//            hiddenDialogs[data.id] = data;
//            viewUserConversations();
//        })
        .catch(err => console.log(err));
}

function connectToDialog(dialog) {
    subscriptions[dialog.id] = stompClient.subscribe(('/conversation/dialog/id:' + dialog.id), onMessageReceived);
}

function disconnectToDialog(dialog) {
    subscriptions[dialog.id].unsubscribe();
}
function viewDialogUserInfo(user) {
    document.querySelector('.info_content').innerHTML = generateInfoTab(user);
}

function configureContentTab(dialog) {
    document.querySelector('#content_recipient_name').innerHTML = dialog.user.firstname + ' ' + dialog.user.lastname;
    document.querySelector('#content_recipient_status').innerHTML = dialog.user.status.toLowerCase();
    viewDialogUserInfo(dialog.user);
}

function addToContactsFromInfoTab(userId) {
    addToContacts(userId);
    if(extendsDialogIntoDialogsArrayByContactId(dialogs, userId)) {
        var contact = getUserFromDialogsByUserId(userId);
    }
}

function deleteFromContactsFromInfoTab(userId) {
    deleteContact(userId);
    if(extendsDialogIntoDialogsArrayByContactId(dialogs, userId)) {
        var contact = contacts[userId];
    }
}

function blockUserFromInfoTab(userId) {
    blockContact(userId);
    currentDialogId = null;
    content.classList.add('_hide');
    content.classList.remove('_active');
    control.classList.add('_active');
}

function generateInfoTab(user) {
    var element = '';
    element += '<div class="companion_info"><div class="companion_icon"><div class="status_oval _active"></div></div>'+
        '<div class="companion_name">' + user.firstname + ' ' + user.lastname + '</div>' +
        '<div class="companion_descriptions">' +
            '<div class="description_main">' + user.email + '</div>' +
            '<div class="description_other">' + user.gender + '</div>' +
            '<div class="description_other">' + generateDateOfBirth(new Date(user.dateOfBirth)) + '</div>' +
            '<div class="movements">';
    if(!blacklist[user.id]) {
        if(!contacts[user.id]) {
            element += '<div onclick="addToContactsFromInfoTab(' + user.id + ')" class="add_button" id="add-btn">Add contact</div>';
        } else {
            element += '<div onclick="deleteFromContactsFromInfoTab(' + user.id + ')" class="delete_button" id="del-btn">Delete contact</div>';
        }
        element += '<div onclick="blockUserFromInfoTab(' + user.id + ')" class="delete_button" id="block-btn">Block contact</div>';
    }
    element += '</div></div></div>';
    return element;
}

function openConversation(id) {
    currentDialogId = id;
    loadMessages(id);
    content.classList.remove('_hide');
    content.classList.add('_active');
    control.classList.remove('_active');
}

function hideDialogById(id) {
    fetch('dialogs/hide', {
            method: 'POST', // *GET, POST, PUT, DELETE, etc.
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
            dialogs.splice(data.id, 1);
            hiddenDialogs[data.id] = data;
            viewUserConversations();
        }).catch(err => console.log(err));
}

function createDialogWithUserAndSendMessage(id, text) {
    fetch('dialogs', {
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
        dialogs[data.id] = data;
        connectToDialog(data);
        sendMessageToUser(id);
        viewUserConversations();
    }).catch(err => console.log(err));
}

function showDialogForUser(dialogId) {
    fetch('dialogs/show', {
            method: 'POST', // *GET, POST, PUT, DELETE, etc.
            headers: {
                [csrfHeader] : csrfToken,
                'charset': 'UTF-8',
                'Content-Type': 'application/json'
            },
            redirect: 'manual', // manual, *follow, error
            body: dialogId
        })
        .then(response => response.json())
        .then(data => {
            hiddenDialogs.splice(data.id, 1);
            dialogs[data.id] = data;
            viewUserConversations();
        }).catch(err => console.log(err));
}

//function showDialogAndSendMessage(userId) {
//    fetch('dialogs/show', {
//        method: 'POST', // *GET, POST, PUT, DELETE, etc.
//        headers: {
//            [csrfHeader] : csrfToken,
//            'charset': 'UTF-8',
//            'Content-Type': 'application/json'
//        },
//        redirect: 'manual', // manual, *follow, error
//        body: getDialogIdIntoDialogsArrayByUserId(hiddenDialogs, userId)
//    })
//    .then(response => response.json())
//    .then(data => {
////        hiddenDialogs.splice(data.id, 1);
////        dialogs[data.id] = data;
////        connectToDialog(data);
////        sendMessageToUser(userId);
////        viewUserConversations();
//    }).catch(err => console.log(err));
//}

//------------------------------

function getUserFromDialogsByUserId(userId) {
    return dialogs.filter(dialog => dialog.user.id === userId)[0].user;
}

function extendsDialogIntoDialogsArrayByContactId(dialogsArray, userId) {
    return (dialogsArray.filter(dialog => dialog.user.id === userId)[0]) ? true : false;
}

function getDialogIdIntoDialogsArrayByUserId(dialogsArray, userId) {
    return dialogsArray.filter(dialog => dialog.user.id === userId)[0].id;
}

function generateDateOfBirth(date) {
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

    const year = date.getFullYear();

    result += year;

    return result;
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