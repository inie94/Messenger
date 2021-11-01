'use strict';

var user;
var stompClient = null;

function onError(error) {
    connectingElement.innerHTML = 'Could not connect to WebSocket server. Please refresh this page to try again!';
//    connectingElement.style.color = 'red';
}

function onNotificationReceived(payload) {
    var notification = JSON.parse(payload.body);
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
}

function onConnected() {
    // Subscribe to the Notification Topic
    stompClient.subscribe('/conversation/notification', onNotificationReceived);
    // Tell your username to the server
    stompClient.send("/app/notification",
        {},
        JSON.stringify({
            type: 'JOIN',
            sender: user
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
            sender: user,
            recipient: contacts[0],
            content: 'Hi!'
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
//    }
}

function sendMessageToUser(id) {
    var text = document.querySelector('#user-' + id).querySelector('.message_input').value;
    var chatMessage = {
        sender: user,
        recipient: contacts.filter(contact => contact.user.id === id)[0].user,
        content: text
    };
    stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
}

initialization();