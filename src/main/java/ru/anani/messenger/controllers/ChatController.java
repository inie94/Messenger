package ru.anani.messenger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.anani.messenger.dto.MessageDTO;
import ru.anani.messenger.dto.NotificationDTO;
import ru.anani.messenger.entities.enums.MessageStatus;
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
    public NotificationDTO sendNotification(@Payload NotificationDTO notification,
                                         SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("userId", notification.getUserId());
        return notification;
    }

    @MessageMapping("/message")
    public MessageDTO sendMessage(@Payload MessageDTO message) {
        message = service.save(message);
        messagingTemplate.convertAndSend("/conversation/dialog/id:" + message.getDialogId(), message);
        return message;
    }
}