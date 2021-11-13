
function printSettingsContent() {
    settingsContent.innerHTML = generateSettingsContent();
}

function generateSettingsContent() {
    let element =   '<div class="settings_header">Profile:</div>' +
                    '<input type="text" class="tab_input_rounded" id="firstname-input" placeholder="Firstname: ' + user.firstname + '">' +
                    '<input type="text" class="tab_input_rounded" id="lastname-input" placeholder="Lastname: ' + user.lastname + '">' +
                    '<input type="text" class="tab_input_rounded" id="email-input" placeholder="Email: ' + user.email + '">' +
                    '<div class="settings_header">Password:</div>' +
                    '<input type="text" class="tab_input_rounded" id="password-input" placeholder="Password">' +
                    '<input type="text" class="tab_input_rounded" id="repeat-password-input" placeholder="Repeat password">' +
                    '<button onclick="editUserProfile()" class="send_rounded" type="submit">Save</button>';
    return element;
}

function editUserProfile() {
    var firstnameInput = document.querySelector('#firstname-input');
    var lastnameInput = document.querySelector('#lastname-input');
    var emailInput = document.querySelector('#email-input');
    var passwordInput = document.querySelector('#password-input');
    var repeatPasswordInput = document.querySelector('#repeat-password-input');

    if (passwordInput.value === repeatPasswordInput.value) {
        var representation = {
            firstname: null,
            lastname: null,
            email: null,
            password: null
        }

        if (firstnameInput.value !== '') {
            representation.firstname = firstnameInput.value;
        }
        if(lastnameInput.value !== '') {
            representation.lastname = lastnameInput.value;
        }
        if(emailInput.value !== '') {
            representation.email = emailInput.value;
        }
        if (passwordInput.value !== '') {
            representation.password = passwordInput.value;
        }

        fetch('users/edit', {
                method: 'POST', // *GET, POST, PUT, DELETE, etc.
                headers: {
                    [csrfHeader] : csrfToken,
                    'charset': 'UTF-8',
                    'Content-Type': 'application/json'
                },
                redirect: 'manual', // manual, *follow, error
                body: JSON.stringify(representation)
            })
            .then(response => response.json())
            .then(data => {
                user = data;
                printSettingsContent();
            }).catch(err => console.log(err));
   }
}