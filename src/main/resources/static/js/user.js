'use strict';

var user;
var stompClient = null;

var conversation = null;

function onError(error) {
    connectingElement.innerHTML = 'Could not connect to WebSocket server. Please refresh this page to try again!';
//    connectingElement.style.color = 'red';
}

function onNotificationReceived(payload) {
    var notification = JSON.parse(payload.body);
    if(notification.userId !== user.id && notification.type === 'JOIN') {
        contacts[notification.userId].user.status = 'ONLINE';
        document.querySelector('#user-' + notification.userId).classList.toggle('_active');
        var userDialog = document.querySelector('#user-dialog-' + notification.userId);
        if (userDialog) {
            userDialog.classList.toggle('_active');
        }
    } else if(notification.userId !== user.id && notification.type === 'LEFT') {
        contacts[notification.userId].user.status = 'OFFLINE';
        document.querySelector('#user-' + notification.userId).classList.toggle('_active');
        var userDialog = document.querySelector('#user-dialog-' + notification.userId);
        if (userDialog) {
            userDialog.classList.toggle('_active');
        }
    } else {

    }
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    if (message) {
        var id;
        if (message.senderId === user.id) {
            id = message.recipientId;
        } else {
            id = message.senderId;
            contacts[id].newMessagesCount++;
        }
        if (message.status === 'SENT') {
            messages[id].push(message);
            contacts[id].lastMessage = message;
            if (conversation && conversation.user.id == id) {
                // добавить сообщение на экран
                if (message.recipientId === user.id) {
                    sendResponseToUserAboutAllMessagesIsRead(id);
                }
            } else {
                if (message.recipientId === user.id) {
                    sendReceivedMessageToUser(id, message);
                }
            }
//            if (message.recipientId === user.id) {
//                sendReceivedMessageToUser(id, message);
//                if (conversation && conversation.user.id == id) {
//                    sendResponseToUserAboutAllMessagesIsRead(id);
//                }
//            }
        } else {
            var item = messages[id].filter(mess => mess.id === message.id)[0];
            var index = messages[id].indexOf(item);
            messages[id][index] = message;
            //
        }
        viewUserConversations();

        //print message
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

    stompClient.subscribe(('/conversation/user/id:' + user.id), onMessageReceived);

    getContacts();
}


function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
}

function initialization() {
    fetch('/user')
        .then(response => response.json())
        .then(data => {
            user = data;
            connect();
        });
}

function sendMessage(event) {
//    var messageContent = messageInput.value.trim();
//    if(messageContent && stompClient) {
        var chatMessage = {
            senderId: user.id,
            recipient: contacts[0],
            content: 'Hi!'
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
//    }
}

function sendResponseToUserAboutAllMessagesIsRead(id) {
    var chatMessage = {
        senderId: user.id,
        recipientId: id,
        status: 'READ'
    };
    stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
}

function sendReceivedMessageToUser(id, message) {
    message.status = 'RECEIVED';
    stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(message));
}

function sendMessageToUser(id) {
    var text = document.querySelector('#user-' + id).querySelector('.message_input').value;
    var chatMessage = {
        senderId: user.id,
        recipientId: id,
        content: text,
        status: 'SENT'
    };
    if (!contacts[id]) {
        addToContacts(id);
    }
    stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
}

initialization();