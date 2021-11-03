package ru.anani.messenger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.anani.messenger.dto.MessageDTO;
import ru.anani.messenger.entities.Message;
import ru.anani.messenger.entities.User;
import ru.anani.messenger.entities.enums.MessageStatus;
import ru.anani.messenger.repositories.MessagesRepository;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
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

    public Message save(Message message) {
        return repository.save(message);
    }

    public MessageDTO save(MessageDTO message) {
        return DTOService.toMessageDTO(repository.save(toMessage(message)));
    }

    public Message getLastMessageByUserAndCompanion(User user, User companion) {
        try {
            return repository.getLastByUserAndCompanion(user, companion).get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public List<Message> getLastMessages(User user, User companion) {
        return repository.findFirst20BySenderAndRecipientOrSenderAndRecipientOrderByCreatedByDesc(user, companion, companion, user).stream()
                .sorted(Comparator.comparingLong(Message::getCreatedBy))
                .collect(Collectors.toList());
    }

    public Message toMessage(MessageDTO dto) {
        User sender = userService.findById(dto.getSenderId());
        User recipient = userService.findById(dto.getRecipientId());
        return new Message(dto.getId(), sender, recipient, dto.getContent(), dto.getCreatedBy(), dto.getStatus());
    }

    public Long getNewMessagesCount(User recipient, User sender) {
        return repository.getCountNewMessageCount(recipient, sender);
    }

    public void updateMessagesToReadByUserId(Long id) {
        User user = userService.findById(id);
        List<Message> messages = repository.findAllMessagesIsNotReadByUser(user);
        messages.forEach(message -> {
            message.setStatus(MessageStatus.READ);
            repository.save(message);
        });
    }
}
