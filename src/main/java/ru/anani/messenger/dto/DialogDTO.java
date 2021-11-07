package ru.anani.messenger.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DialogDTO {
    private Long id;
    private UserDTO user;
    private MessageDTO lastMessage;
    private Long newMessagesCount;
}
