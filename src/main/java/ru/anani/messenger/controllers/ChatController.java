package ru.anani.messenger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.anani.messenger.entities.Message;
import ru.anani.messenger.entities.Notification;
import ru.anani.messenger.services.MessagesService;

import java.util.Date;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessagesService service;

    @Autowired
    public ChatController(SimpMessagingTemplate messagingTemplate, MessagesService service) {
        this.messagingTemplate = messagingTemplate;
        this.service = service;
    }

    @MessageMapping("/notification")
    @SendTo("/conversation/notification")
    public Notification sendNotification(@Payload Notification notification,
                                         SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", notification.getSender());
        return notification;
    }

    @MessageMapping("/chat.sendMessage")
    public Message sendMessage(@Payload Message message) {
        message.setCreatedBy(new Date().getTime());
        service.save(message);
        messagingTemplate.convertAndSend("/conversation/user/id:" + message.getRecipient().getId(), message);
        return message;
    }



//    @MessageMapping("/chat.notification")
//    @SendTo("/topic/notification")
//    public MessageDTO sendNotification(@Payload MessageDTO message) {
////        messagingTemplate.convertAndSend("/topic/notification", message);
//        return message;
//    }

//    @MessageMapping("/chat.connect")
//    @SendTo("/topic/notification")
//    public MessageDTO connect(@Payload MessageDTO message,
//                           SimpMessageHeaderAccessor headerAccessor) {
//        headerAccessor.getSessionAttributes().put("username", message.getSender());
//        return message;
//    }

}