package ru.anani.messenger.controllers;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

//    private final MessagesService messagesService;
//    private final TopicService topicService;
//    private final UserService userService;
//    private final RelationshipsService relationshipsService;
//
//    @Autowired
//    public MessageController(MessagesService messagesService, TopicService topicService, UserService userService, RelationshipsService relationshipsService) {
//        this.messagesService = messagesService;
//        this.topicService = topicService;
//        this.userService = userService;
//        this.relationshipsService = relationshipsService;
//    }
//
//    @GetMapping("/user/topic/id:{id}/messages")
//    public List<MessageDTO> getAllTopicMessages(@PathVariable("id") long topicId) {
//        Topic topic = topicService.findById(topicId);
//        List<MessageDTO> messages = new ArrayList<>();
//        messagesService.getLastMessages(topic).forEach(message -> messages.add(DTOService.toMessageDTO(message)));
//
//        return messages;
//    }
//
//    @GetMapping("/user/topic/id:{id}/messages/received")
//    public RelationshipDTO allMessagesFromTopicReceivedByUser(@PathVariable("id") long id,
//                                                              Principal principal) {
//        User user = userService.findByEmail(principal.getName());
//
//        Relationship userRelationship =
//                user.getRelationships().stream()
//                        .filter(relationship -> relationship.getTopic().getId() == id)
//                        .findFirst()
//                        .get();
//
//        userRelationship.setUpdatedBy(new Date().getTime());
//
//        return DTOService.toRelationshipDTOWithoutUser(relationshipsService.save(userRelationship));
//    }
}
