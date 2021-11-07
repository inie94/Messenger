package ru.anani.messenger.dto;

import lombok.*;
import ru.anani.messenger.entities.enums.UserNotificationType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserNotificationDTO {
    private Long targetId;
    private UserNotificationType type;
}
