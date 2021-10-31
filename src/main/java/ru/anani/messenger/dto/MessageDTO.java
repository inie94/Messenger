package ru.anani.messenger.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private Long id;
//    private MessageType type;
//    private TopicDTO topic;
    private String content;
    private UserDTO sender;
    private Long createdBy;
}
