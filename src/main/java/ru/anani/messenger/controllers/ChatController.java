package ru.anani.messenger.controllers;

import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

//    private final SimpMessagingTemplate messagingTemplate;
//    private final MessagesService service;
//
//    @Autowired
//    public ChatController(SimpMessagingTemplate messagingTemplate, MessagesService service) {
//        this.messagingTemplate = messagingTemplate;
//        this.service = service;
//    }
//
//    @MessageMapping("/chat.sendMessage")
//    public MessageDTO sendMessage(@Payload MessageDTO message) {
//        message.setCreatedBy(new Date().getTime());
//        service.save(message);
//        messagingTemplate.convertAndSend("/topic/id:" + message.getTopic().getId(), message);
//        return message;
//    }
//
//    @MessageMapping("/chat.notification")
//    @SendTo("/topic/notification")
//    public MessageDTO sendNotification(@Payload MessageDTO message) {
////        messagingTemplate.convertAndSend("/topic/notification", message);
//        return message;
//    }
//
//    @MessageMapping("/chat.connect")
//    @SendTo("/topic/notification")
//    public MessageDTO connect(@Payload MessageDTO message,
//                           SimpMessageHeaderAccessor headerAccessor) {
//        headerAccessor.getSessionAttributes().put("username", message.getSender());
//        return message;
//    }

}