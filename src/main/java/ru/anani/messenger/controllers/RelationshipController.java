package ru.anani.messenger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.anani.messenger.dto.ContactDTO;
import ru.anani.messenger.entities.Message;
import ru.anani.messenger.entities.Relationship;
import ru.anani.messenger.entities.User;
import ru.anani.messenger.entities.enums.RelationshipStatus;
import ru.anani.messenger.services.DTOService;
import ru.anani.messenger.services.MessagesService;
import ru.anani.messenger.services.RelationshipsService;
import ru.anani.messenger.services.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RelationshipController {

    private final UserService userService;
    private final RelationshipsService relationshipsService;
    private final MessagesService messagesService;

    @Autowired
    public RelationshipController(UserService userService, RelationshipsService relationshipsService, MessagesService messagesService) {
        this.userService = userService;
        this.relationshipsService = relationshipsService;
        this.messagesService = messagesService;
    }

    @GetMapping("user/contacts")
    public List<ContactDTO> getContacts(Principal principal) {
        User user = userService.findByEmail(principal.getName());

        List<Relationship> relationships = relationshipsService.getRelationshipByUser(user);
        List<ContactDTO> contacts = new ArrayList<>();

        relationships.forEach(relationship -> {
            Message message = messagesService.getLastMessageByUserAndCompanion(user, relationship.getCompanion());
            contacts.add(DTOService.toContact(relationship, message));
        });

        return contacts;
    }

    @GetMapping("contact/id:{id}/add")
    public ContactDTO newRelationship(@PathVariable("id") Long id, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        User companion = userService.findById(id);

        Relationship userRelationship = new Relationship();
        Relationship companionRelationship = new Relationship();

        userRelationship.setUser(user);
        userRelationship.setCompanion(companion);
        userRelationship.setStatus(RelationshipStatus.ACTIVE);

        userRelationship = relationshipsService.save(userRelationship);

        companionRelationship.setUser(companion);
        companionRelationship.setCompanion(user);
        companionRelationship.setStatus(RelationshipStatus.ACTIVE);

        relationshipsService.save(companionRelationship);

        Message message = messagesService.getLastMessageByUserAndCompanion(user, companion);

        return DTOService.toContact(userRelationship, message);
    }
}
