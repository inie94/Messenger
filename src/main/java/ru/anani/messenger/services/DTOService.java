package ru.anani.messenger.services;

import ru.anani.messenger.dto.DialogDTO;
import ru.anani.messenger.dto.MessageDTO;
import ru.anani.messenger.dto.UserDTO;
import ru.anani.messenger.entities.Dialog;
import ru.anani.messenger.entities.Message;
import ru.anani.messenger.entities.User;

public class DTOService {

    public static UserDTO toUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .gender(user.getGender())
                .email(user.getEmail())
                .dateOfBirth(user.getDateOfBirth())
                .status(user.getStatus())
                .build();
    }

    public static DialogDTO toDialogDTO(Dialog dialog, User user, Message lastMessage, Long newMessagesCount) {
        return DialogDTO.builder()
                .id(dialog.getId())
                .user(toUserDTO(user))
                .lastMessage(toMessageDTO(lastMessage))
                .newMessagesCount(newMessagesCount)
                .build();
    }

    public static MessageDTO toMessageDTO(Message message) {
        if (message == null) {
            return null;
        }
        return MessageDTO.builder()
                .id(message.getId())
                .senderId(message.getSender().getId())
                .dialogId(message.getDialog().getId())
                .content(message.getContent())
                .createdBy(message.getCreatedBy())
                .status(message.getStatus())
                .build();
    }


//    public static RelationshipDTO toRelationshipDTOWithoutUser(Relationship relationship) {
//        return RelationshipDTO.builder()
//                .id(relationship.getId())
//                .status(relationship.getStatus())
//                .topic(toTopicDTO(relationship.getTopic()))
//                .updatedBy(relationship.getUpdatedBy())
//                .build();
//    }
//
//    public static RelationshipDTO toRelationshipDTO(Relationship relationship) {
//        return RelationshipDTO.builder()
//                .id(relationship.getId())
//                .status(relationship.getStatus())
//                .topic(toTopicDTO(relationship.getTopic()))
//                .user(toUserDTOWithoutRelationships(relationship.getUser()))
//                .updatedBy(relationship.getUpdatedBy())
//                .build();
//    }
//
//    public static TopicDTO toTopicDTO(Topic topic) {
//        Set<RelationshipDTO> relationships = new HashSet<>();
//
//        if(topic.getRelationships() != null)
//            topic.getRelationships().forEach(relationship -> relationships.add(toRelationshipDTOWithoutTopic(relationship)));
//
//        return TopicDTO.builder()
//                .id(topic.getId())
//                .creator(topic.getCreator())
//                .name(topic.getName())
//                .mode(topic.getMode())
//                .relationships(relationships)
//                .updatedBy(topic.getUpdatedBy())
//                .build();
//    }
//
//    private static RelationshipDTO toRelationshipDTOWithoutTopic(Relationship relationship) {
//        return RelationshipDTO.builder()
//                .id(relationship.getId())
//                .status(relationship.getStatus())
//                .user(toUserDTOWithoutRelationships(relationship.getUser()))
//                .updatedBy(relationship.getUpdatedBy())
//                .build();
//    }
//
//    public static UserDTO toUserDTOWithoutRelationships(User user) {
//
//        return UserDTO.builder()
//                .id(user.getId())
//                .firstname(user.getFirstname())
//                .lastname(user.getLastname())
//                .gender(user.getGender())
//                .email(user.getEmail())
//                .dateOfBirth(user.getDateOfBirth())
//                .status(user.getStatus())
//                .build();
//    }
//

}
