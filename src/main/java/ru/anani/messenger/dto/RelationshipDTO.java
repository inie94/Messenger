package ru.anani.messenger.dto;

import lombok.*;
import ru.anani.messenger.entities.enums.RelationshipStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RelationshipDTO {
        private Long id;
//        private TopicDTO topic;
        private UserDTO user;
        private RelationshipStatus status;
        private Long updatedBy;
}
