package ru.anani.messenger.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private Long id;
    private String content;
    private Long sender;
    private Long recipient;
    private Long createdBy;
}
