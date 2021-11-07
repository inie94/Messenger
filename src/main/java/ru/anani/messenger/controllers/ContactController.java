package ru.anani.messenger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.anani.messenger.dto.UserDTO;
import ru.anani.messenger.entities.Contact;
import ru.anani.messenger.entities.BlockedUser;
import ru.anani.messenger.entities.User;
import ru.anani.messenger.services.ContactsService;
import ru.anani.messenger.services.DTOService;
import ru.anani.messenger.services.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("contacts")
public class ContactController {

    private final UserService userService;
    private final ContactsService contactsService;

    @Autowired
    public ContactController(UserService userService, ContactsService contactsService) {
        this.userService = userService;
        this.contactsService = contactsService;
    }

    @GetMapping
    public List<UserDTO> getAllUserContacts(Principal principal) {
        User user = userService.findByEmail(principal.getName());
        List<Contact> contacts = contactsService.getContactsByUser(user);
        List<UserDTO> users = new ArrayList<>();
        contacts.forEach(contact -> users.add(DTOService.toUserDTO(contact.getCompanion())));
        return users;
    }

    @PutMapping
    public UserDTO addCompanionToUserContacts(@RequestBody Long companionId, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        User companion = userService.findById(companionId);
        return DTOService.toUserDTO(contactsService.addToContactList(user, companion).getCompanion());
    }

    @DeleteMapping
    public void deletePersonIntoUserContactList(@RequestBody Long companionId, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        User companion = userService.findById(companionId);
        contactsService.deleteContactByUserAndCompanion(user, companion);
    }
}
