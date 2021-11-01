package ru.anani.messenger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.anani.messenger.dto.MessageDTO;
import ru.anani.messenger.entities.User;
import ru.anani.messenger.services.DTOService;
import ru.anani.messenger.services.MessagesService;
import ru.anani.messenger.services.RelationshipsService;
import ru.anani.messenger.services.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MessageController {

    private final MessagesService messagesService;
    private final UserService userService;
    private final RelationshipsService relationshipsService;

    @Autowired
    public MessageController(MessagesService messagesService, UserService userService, RelationshipsService relationshipsService) {
        this.messagesService = messagesService;
        this.userService = userService;
        this.relationshipsService = relationshipsService;
    }

    @GetMapping("user/contact/id:{id}/messages")
    public List<MessageDTO> getAllTopicMessages(@PathVariable("id") long companionId, Principal principal) {

        User user = userService.findByEmail(principal.getName());
        User companion = userService.findById(companionId);

        List<MessageDTO> messages = new ArrayList<>();
        messagesService.getLastMessages(user, companion).forEach(message -> messages.add(DTOService.toMessageDTO(message)));

        return messages;
    }

//    @GetMapping("/user/topic/id:{id}/messages")
//    public List<MessageDTO> getAllTopicMessages(@PathVariable("id") long topicId) {
//        Topic topic = topicService.findById(topicId);
//        List<MessageDTO> messages = new ArrayList<>();
//        messagesService.getLastMessages(topic).forEach(message -> messages.add(DTOService.toMessageDTO(message)));
//
//        return messages;
//    }
//
//    @GetMapping("/user/topic/id:{id}/messages/received")
//    public RelationshipDTO allMessagesFromTopicReceivedByUser(@PathVariable("id") long id,
//                                                              Principal principal) {
//        User user = userService.findByEmail(principal.getName());
//
//        Relationship userRelationship =
//                user.getRelationships().stream()
//                        .filter(relationship -> relationship.getTopic().getId() == id)
//                        .findFirst()
//                        .get();
//
//        userRelationship.setUpdatedBy(new Date().getTime());
//
//        return DTOService.toRelationshipDTOWithoutUser(relationshipsService.save(userRelationship));
//    }
}
