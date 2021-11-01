package ru.anani.messenger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.anani.messenger.entities.Message;
import ru.anani.messenger.entities.User;
import ru.anani.messenger.repositories.MessagesRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MessagesService {

    private final MessagesRepository repository;
    private final UserService userService;

    @Autowired
    public MessagesService(MessagesRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public void save(Message message) {
        repository.save(message);
    }

    public Message getLastMessageByUserAndCompanion(User user, User companion) {
        return repository.getLastByUserAndCompanion(user, companion).get();
    }
//
//    public void save(MessageDTO message) {
//        Topic topic = topicService.update(topicService.findById(message.getTopic().getId()));
//        message.setTopic(DTOService.toTopicDTO(topic));
//
//        repository.save(toMessage(message));
//    }
//
    public List<Message> getLastMessages(User user, User companion) {
        return repository.findFirst20BySenderAndRecipientOrSenderAndRecipientOrderByCreatedByDesc(user, companion, companion, user).stream()
                .sorted(Comparator.comparingLong(Message::getCreatedBy))
                .collect(Collectors.toList());
    }

//
//    public Message toMessage(MessageDTO dto) {
//        Topic topic = topicService.findById(dto.getTopic().getId());
//        User sender = userService.findById(dto.getSender().getId());
//        Set<User> received = new HashSet<>();
////        dto.getReceived().forEach(userDTO -> received.add(userService.findById(userDTO.getId())));
//        return new Message(dto.getId(), dto.getType(), topic, dto.getContent(), sender, dto.getCreatedBy());
//    }
//
//
////    public List<Message> findAllByTopicAndReceivedNotUser(Topic topic, User user){
////        return repository.findAllByTopicAndReceivedNotIn(topic, new HashSet<User>(Arrays.asList(user)));
////    }
//
//    public Set<Message> getAllMessagesByTopicAndSenderNot(Topic topic, User user){
//        return new HashSet<>(repository.findAllByTopicAndSenderNot(topic, user));
//    }
}
