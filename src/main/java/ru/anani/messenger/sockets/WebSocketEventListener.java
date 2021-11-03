package ru.anani.messenger.sockets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import ru.anani.messenger.dto.NotificationDTO;
import ru.anani.messenger.entities.User;
import ru.anani.messenger.entities.enums.NotificationType;
import ru.anani.messenger.entities.enums.UserStatus;
import ru.anani.messenger.services.UserService;

import java.util.Date;

@Component
public class WebSocketEventListener {

    private final UserService service;

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public WebSocketEventListener(UserService service) {
        this.service = service;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        Long userId = (Long) headerAccessor.getSessionAttributes().get("userId");

        if(userId != null) {
            User user = disconnectUser(userId);
            logger.info("User Disconnected : " + user.getEmail());
            messagingTemplate.convertAndSend("/conversation/notification", createLeaveNotification(user));
        }
    }

    private NotificationDTO createLeaveNotification(User user) {
        return NotificationDTO.builder()
                .type(NotificationType.LEFT)
                .userId(user.getId())
                .createdBy(new Date().getTime())
                .build();
    }

    private User disconnectUser(long id) {
        User user = service.findById(id);
        user.setStatus(UserStatus.OFFLINE);
        return service.save(user);
    }
}