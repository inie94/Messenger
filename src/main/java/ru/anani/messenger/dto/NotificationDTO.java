package ru.anani.messenger.dto;

import lombok.*;
import ru.anani.messenger.entities.User;
import ru.anani.messenger.entities.enums.NotificationType;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {
    private Long id;
    private NotificationType type;
//    private Long senderId;
    private Long userId;
    private Long createdBy;
}
