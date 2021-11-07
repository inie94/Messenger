package ru.anani.messenger.dto;

import lombok.*;
import ru.anani.messenger.entities.enums.MessageStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private Long id;
    private String content;
    private Long senderId;
    private Long dialogId;
    private Long createdBy;
    private MessageStatus status;
}
