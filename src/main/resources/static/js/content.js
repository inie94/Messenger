
var messagesContainer = document.querySelector('.messages');

function viewDialogMessages(dialogId) {
    if(messages[dialogId]) {
        var element = '';
        var date = getDateStart(new Date(messages[dialogId][0].createdBy));
        element = generateDateRow(date);
        messages[dialogId].forEach(message => {
            if (checkDate(date, new Date(message.createdBy))) {
                date = getDateStart(new Date(message.createdBy));
                element += generateDateRow(date);
            }
            element += generateMessage(message);
        });
        messagesContainer.innerHTML = element;
        var conversationBody = document.getElementById('conv-body');
        conversationBody.scrollTop = conversationBody.scrollHeight;
    }
}

function getDateStart(date) {
    var result = new Date(date.getFullYear(), date.getMonth(), date.getDate());
    return result;
}

function checkDate(previousDate, currentDate) {
    if (previousDate.getDate() != currentDate.getDate()) {
        return true;
    } else {
        return false;
    }
}

function generateDateRow(messageDate) {
    return '<div class="date_container">' + dateString(messageDate) + '</div>';
}

function dateString(date) {
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

        result += date.getFullYear();
        return result;
}

function printMessage(message) {
    var element = generateMessage(message);
    messagesContainer.innerHTML += element;
    var conversationBody = document.getElementById('conv-body');
    conversationBody.scrollTop = conversationBody.scrollHeight;
}

function generateMessage(message) {
    var element;
    if (message.senderId === user.id) {
        element = '<div class="message_container _user">';
    } else {
        element = '<div class="message_container _companion">';
    }
    element +=  '<div class="message">' +
                    '<div class="message_text">' + message.content + '</div>' +
                    '<div class="message_info">' + timeString(message.createdBy) + '</div>' +
                '</div>' +
            '</div>';
    return element;
}

//-------------------------------------------------------------------------

function timeString(timestamp) {
    var result = '';
    var date = new Date(timestamp);

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