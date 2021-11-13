'use strict';

var user;
var stompClient = null;

var subscriptions = [];

var target = null;

function onError(error) {
    connectingElement.innerHTML = 'Could not connect to WebSocket server. Please refresh this page to try again!';
//    connectingElement.style.color = 'red';
}

function onNotificationReceived(payload) {
    var notification = JSON.parse(payload.body);
    if (notification.userId !== user.id) {
        if(notification.type === 'JOIN') {
            if(contacts[notification.userId]) {
                contacts[notification.userId].status = 'ONLINE';
                document.querySelector('#user-' + notification.userId).classList.toggle('_active');
            }
            if(extendsDialogIntoDialogsArrayByContactId(dialogs, notification.userId)) {
                var dialogId = getDialogIdIntoDialogsArrayByUserId(dialogs, notification.userId);
                var userDialog = document.querySelector('#dialog-' + dialogId);
                if (userDialog) {
                    userDialog.classList.add('_active');
                }
                dialogs[dialogId].user.status = "ONLINE";
            }
        } else if(notification.type === 'LEFT') {
            if(contacts[notification.userId]) {
                contacts[notification.userId].status = 'OFFLINE';
                document.querySelector('#user-' + notification.userId).classList.toggle('_active');
            }
            if(extendsDialogIntoDialogsArrayByContactId(dialogs, notification.userId)) {
                var dialogId = getDialogIdIntoDialogsArrayByUserId(dialogs, notification.userId);
                var userDialog = document.querySelector('#dialog-' + dialogId);
                if (userDialog) {
                    userDialog.classList.remove('_active');
                }
                dialogs[dialogId].user.status = "OFFLINE";
            }
        }
    }
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    if (message) {
        if (message.status === 'SENT') {
            var dialogId = message.dialogId;
            if(!messages[dialogId]){
                messages[dialogId] = [];
            }
            messages[dialogId].push(message);

            if (dialogs[dialogId]) {
                if(message.senderId !== user.id) {
                // исключить инкремент если диалог открыт
                    dialogs[dialogId].newMessagesCount++;
                }
                dialogs[dialogId].lastMessage = message;
                viewUserConversations();
            } else if (hiddenDialogs[dialogId]) {
                showDialogForUser(message.senderId);
            }
            if(message.senderId !== user.id) {
                sendReceivedMessageToUser(message);
            }
            if (message.dialogId === currentDialogId) {
                printMessage(message);
                // send read fetch
            }
        }
    }
}
function onUserNotificationReceived(payload) {
    var notification = JSON.parse(payload.body);
    if (notification) {
        if (notification.type === 'AWAIT_CONNECTION') {
            fetch('dialogs/id:' + notification.targetId)
            .then(response => response.json())
            .then(data => {
               dialogs[data.id] = data;
               connectToDialog(data);
            });
        } else if (notification.type === 'USER_IS_BLOCKED') {
            if (contacts[notification.targetId]) {
                contacts.splice(notification.targetId, 1);
                viewContactList();
            }
            if (extendsDialogIntoDialogsArrayByContactId(dialogs, notification.targetId)) {
                var id = getDialogIdIntoDialogsArrayByUserId(dialogs, notification.targetId);
                blockedDialogs[id] = dialogs[id];
                dialogs.splice(id, 1);
                disconnectToDialog(blockedDialogs[id]);
                viewUserConversations();
            }
            if (extendsDialogIntoDialogsArrayByContactId(hiddenDialogs, notification.targetId)) {
                var id = getDialogIdIntoDialogsArrayByUserId(hiddenDialogs, notification.targetId);
                blockedDialogs[id] = hiddenDialogs[id];
                hiddenDialogs.splice(id, 1);
                disconnectToDialog(blockedDialogs[id]);
                viewUserConversations();
            }
        } else if (notification.type === 'USER_IS_UNBLOCKED') {
            if (blockedDialogs[notification.targetId]) {
                var id = notification.targetId;
                hiddenDialogs[id] = blockedDialogs[id];
                connectToDialog(hiddenDialogs[id]);
                blockedDialogs.splice(id, 1);
                viewUserConversations();
            }
        }
    }
}

function onConnected() {
    // Subscribe to the Notification Topic
    stompClient.subscribe('/conversation/notification', onNotificationReceived);
    // Tell your username to the server
    stompClient.send("/app/notification",
        {},
        JSON.stringify({
            type: 'JOIN',
            userId: user.id,
            createdBy: Date.now()
        })
    )

    getContacts();
    getBlacklist()
    getDialogs();
    getHiddenDialogs();
    getBlockedDialogs();

    stompClient.subscribe(('/conversation/user/id:' + user.id), onUserNotificationReceived);
}


function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
}

function getUser() {
    fetch('/users')
        .then(response => response.json())
        .then(data => {
            user = data;
            connect();
            printSettingsContent();
        });
}

function initialization() {
    getUser();
}

function sendAwaitConversationNotificationToUser(id) {
//    stompClient.send("/app/notification",
//        {},
//        JSON.stringify({
//            type: 'AWAIT_CONVERSATION',
//            userId: user.id,
//            targetId: id,
//            createdBy: Date.now()
//        })
//    )
}

function sendResponseToUserAboutAllMessagesIsRead(id) {
//    var chatMessage = {
//        senderId: user.id,
//        recipientId: id,
//        status: 'READ'
//    };
//    stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
}

function sendReceivedMessageToUser(message) {
    message.status = 'RECEIVED';
    stompClient.send("/app/message", {}, JSON.stringify(message));
}

function sendMessageToUser(id) {
    var text = document.querySelector('#user-' + id).querySelector('.message_input').value;
    if (!extendsDialogIntoDialogsArrayByContactId(blockedDialogs, id)) {
        if (extendsDialogIntoDialogsArrayByContactId(dialogs, id)) {
            var chatMessage = {
                senderId: user.id,
                dialogId: getDialogIdIntoDialogsArrayByUserId(dialogs, id),
                content: text,
                status: 'SENT',
                createdBy: Date.now()
            };

            stompClient.send("/app/message", {}, JSON.stringify(chatMessage));
            document.querySelector('#user-' + id).querySelector('.message_input').value = '';
        } else if (extendsDialogIntoDialogsArrayByContactId(hiddenDialogs, id)) {
            var chatMessage = {
                senderId: user.id,
                dialogId: getDialogIdIntoDialogsArrayByUserId(hiddenDialogs, id),
                content: text,
                status: 'SENT',
                createdBy: Date.now()
            };

            stompClient.send("/app/message", {}, JSON.stringify(chatMessage));
            document.querySelector('#user-' + id).querySelector('.message_input').value = '';
        } else {
            createDialogWithUserAndSendMessage(id, text);
        }
    }
}

function sendMessageToCurrentDialog() {
    let text = contentInput.value;
    if (text && currentDialogId && text != '') {
        var chatMessage = {
            senderId: user.id,
            dialogId: currentDialogId,
            content: text,
            status: 'SENT',
            createdBy: Date.now()
        };

        stompClient.send("/app/message", {}, JSON.stringify(chatMessage));
        contentInput.value = '';
    }
}


contentSendButton.addEventListener('click', sendMessageToCurrentDialog);

initialization();