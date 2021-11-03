'use strict';

var contactsSearchInput = document.querySelector('#contacts-tab').querySelector('.search');

function clearSearchInput() {
    contactsSearchInput.value = '';
    contactsSearchInput.blur();
    viewContactList();
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
                    element += generateUserCard(item);
                });
                contactsContent.innerHTML = element;
            });
    } else {
        clearSearchInput();
    }
};

