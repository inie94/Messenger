package ru.anani.messenger.services;

import ru.anani.messenger.dto.ContactDTO;
import ru.anani.messenger.dto.UserDTO;
import ru.anani.messenger.entities.User;

public class DTOService {

    public static ContactDTO toContact(User user) {
        return ContactDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .gender(user.getGender())
                .dateOfBirth(user.getDateOfBirth())
                .status(user.getStatus())
                .build();
    }

    public static UserDTO toUserDTO(User user) {
//        Set<RelationshipDTO> relationships = new HashSet<>();

//        if(user.getRelationships() != null)
//            user.getRelationships().forEach(relationship -> relationships.add(toRelationshipDTOWithoutUser(relationship)));

        return UserDTO.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .gender(user.getGender())
                .email(user.getEmail())
                .dateOfBirth(user.getDateOfBirth())
//                .relationships(relationships)
                .build();
    }
//
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
//    public static MessageDTO toMessageDTO(Message message) {
//
//        return MessageDTO.builder()
//                .id(message.getId())
//                .type(message.getType())
//                .topic(toTopicDTO(message.getTopic()))
//                .sender(toUserDTOWithoutRelationships(message.getSender()))
//                .content(message.getContent())
//                .createdBy(message.getCreatedBy())
//                .build();
//    }
}
