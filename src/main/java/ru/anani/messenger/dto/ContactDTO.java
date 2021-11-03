package ru.anani.messenger.dto;

import lombok.*;
import ru.anani.messenger.entities.Message;
import ru.anani.messenger.entities.User;
import ru.anani.messenger.entities.enums.RelationshipStatus;
import ru.anani.messenger.entities.enums.UserStatus;

import java.sql.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactDTO {
    private UserDTO user;
    private RelationshipStatus status;
    private MessageDTO lastMessage;
    private Long newMessagesCount;
}
