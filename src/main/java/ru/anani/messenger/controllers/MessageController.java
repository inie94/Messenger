package ru.anani.messenger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.anani.messenger.dto.MessageDTO;
import ru.anani.messenger.entities.Dialog;
import ru.anani.messenger.entities.Message;
import ru.anani.messenger.entities.User;
import ru.anani.messenger.entities.enums.MessageStatus;
import ru.anani.messenger.services.DTOService;
import ru.anani.messenger.services.DialogService;
import ru.anani.messenger.services.MessagesService;
import ru.anani.messenger.services.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MessageController {

    private final MessagesService messagesService;
    private final UserService userService;
    private final DialogService dialogService;

    @Autowired
    public MessageController(MessagesService messagesService, UserService userService, DialogService dialogService) {
        this.messagesService = messagesService;
        this.userService = userService;
        this.dialogService = dialogService;
    }


    @GetMapping("dialogs/id:{id}/messages")
    public List<MessageDTO> getAllTopicMessages(@PathVariable("id") long dialogId, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        Dialog dialog = dialogService.getDialogById(dialogId);
        List<MessageDTO> messages = new ArrayList<>();
        List<Message> messageList = messagesService.getLastMessages(dialog);
        messageList.forEach(message -> {
            if (!message.getSender().getId().equals(user.getId())) {
                message.setStatus(MessageStatus.RECEIVED);
                messages.add(DTOService.toMessageDTO(messagesService.save(message)));
            } else {
                messages.add(DTOService.toMessageDTO(message));
            }
        });
        return messages;
    }

//    @GetMapping("/user/message/all-read")
//    public void messageReceived(Principal principal) {
//        User user = userService.findByEmail(principal.getName());
//        messagesService.updateMessagesToReadByUser(user);
//    }

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
