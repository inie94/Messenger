package ru.anani.messenger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.anani.messenger.dto.ContactDTO;
import ru.anani.messenger.dto.UserDTO;
import ru.anani.messenger.entities.User;
import ru.anani.messenger.services.DTOService;
import ru.anani.messenger.services.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserMovesController {

    private final UserService userService;

    @Autowired
    public UserMovesController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/search")
    public List<ContactDTO> searchUsers(@RequestParam("value") String searchValue, Principal principal) {

        User user = userService.findByEmail(principal.getName());

        List<ContactDTO> contacts = new ArrayList<>();
        List<User> users = userService.searchUsersBy(searchValue);

        users.remove(user);

        users.forEach(user1 -> contacts.add(DTOService.toContact(user1)));

        return contacts;
    }
//
//    @GetMapping("/user/id:{id}/get-topic")
//    public RelationshipDTO connectToUserTopic(@PathVariable("id") long id,
//                                              Principal principal) {
//        User user = userService.findByEmail(principal.getName());
//        User companion = userService.findById(id);
//
//        Relationship userRelationship;
//        Relationship companionRelationship;
//
//        List<Relationship> relationships = relationshipsService.getRelationshipByUserAndUserAndTopicStatus(user, companion, TopicMode.PRIVATE);
//        if (relationships != null && !relationships.isEmpty() && relationships.size() > 1) {
//            userRelationship = relationships.stream().filter(relationship -> relationship.getUser().equals(user)).findFirst().get();
//            companionRelationship = relationships.stream().filter(relationship -> relationship.getUser().equals(companion)).findFirst().get();
//            if(userRelationship.getStatus() != SubscribeStatus.SUBSCRIBE){
//                userRelationship.setStatus(SubscribeStatus.SUBSCRIBE);
//                userRelationship.setUpdatedBy(new Date().getTime());
//            }
//            if(companionRelationship.getStatus() == null) {
//                companionRelationship.setStatus(SubscribeStatus.UNSUBSCRIBE);
//                companionRelationship.setUpdatedBy(new Date().getTime());
//                relationshipsService.save(companionRelationship);
//            }
//
//            return DTOService.toRelationshipDTOWithoutUser(relationshipsService.save(userRelationship));
//        }
//
//        Topic topic = new Topic();
//        topic.setMode(TopicMode.PRIVATE);
//        topic = topicService.create(topic);
//
//        userRelationship = new Relationship();
//        userRelationship.setUser(user);
//        userRelationship.setTopic(topic);
//        userRelationship.setUpdatedBy(new Date().getTime());
//        userRelationship.setStatus(SubscribeStatus.SUBSCRIBE);
//
//        companionRelationship = new Relationship();
//        companionRelationship.setUser(companion);
//        companionRelationship.setTopic(topic);
//        companionRelationship.setUpdatedBy(new Date().getTime());
//        companionRelationship.setStatus(SubscribeStatus.UNSUBSCRIBE);
//
//        userRelationship = relationshipsService.save(userRelationship);
//        companionRelationship = relationshipsService.save(companionRelationship);
//
//        topic.setRelationships(new HashSet<>(Arrays.asList(userRelationship, companionRelationship)));
//
//        return DTOService.toRelationshipDTOWithoutUser(userRelationship);
//    }
//
//    @GetMapping("/user/topic:{id}")
//    public Topic getTopic(@PathVariable("id") long id) {
//        return topicService.findById(id);
//    }
//
//    @GetMapping("/user/topic:{id}/unsubscribe")
//    public RelationshipDTO unsubscribeAtTopic(@PathVariable("id") long id, Principal principal) {
//        User user = userService.findByEmail(principal.getName());
//        Topic topic = topicService.findById(id);
//        Relationship userRelationship = user.getRelationships().stream().filter(relationship -> relationship.getTopic().equals(topic)).findFirst().get();
//        userRelationship.setStatus(SubscribeStatus.UNSUBSCRIBE);
//        userRelationship.setUpdatedBy(new Date().getTime());
//        userRelationship = relationshipsService.save(userRelationship);
//        return DTOService.toRelationshipDTOWithoutUser(userRelationship);
//    }
//
//    @GetMapping("/user/id:{userId}/topic:{topicId}/subscribe")
//    public RelationshipDTO subscribeAtTopic(@PathVariable("topicId") long topicId,
//                                     @PathVariable("userId") long userId) {
//        User user = userService.findById(userId);
//
//        Relationship userRelationship = user.getRelationships().stream().filter(relationship -> relationship.getTopic().getId() == topicId).findFirst().get();
//        userRelationship.setStatus(SubscribeStatus.SUBSCRIBE);
//        userRelationship.setUpdatedBy(new Date().getTime());
//
//        return DTOService.toRelationshipDTOWithoutUser(relationshipsService.save(userRelationship));
//    }
}
