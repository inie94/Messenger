package ru.anani.messenger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.anani.messenger.dto.MessageDTO;
import ru.anani.messenger.entities.Dialog;
import ru.anani.messenger.entities.Message;
import ru.anani.messenger.entities.User;
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
    private final DialogService dialogService;

    @Autowired
    public MessagesService(MessagesRepository repository, UserService userService, DialogService dialogService) {
        this.repository = repository;
        this.userService = userService;
        this.dialogService = dialogService;
    }

    public Message save(Message message) {
        return repository.save(message);
    }

    public MessageDTO save(MessageDTO message) {
        return DTOService.toMessageDTO(repository.save(toMessage(message)));
    }
//
    public Message getLastMessageByDialog(Dialog dialog) {
        try {
            return repository.getLastMessageByDialog(dialog).get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public List<Message> getLastMessages(Dialog dialog) {
        return repository.findFirst20ByDialogOrderByCreatedByDesc(dialog).stream()
                .sorted(Comparator.comparingLong(Message::getCreatedBy))
                .collect(Collectors.toList());
    }

    public Message toMessage(MessageDTO dto) {
        User sender = userService.findById(dto.getSenderId());
        Dialog dialog = dialogService.getDialogById(dto.getDialogId());
        return new Message(dto.getId(), dialog, sender, dto.getContent(), dto.getCreatedBy(), dto.getStatus());
    }

    public Long getCountOfNewMessagesInDialogWhereSenderNotUser(Dialog dialog, User user) {
        return repository.getCountOfNewMessagesInDialogWhereSenderNotUser(dialog, user);
    }
//
//    public void updateMessagesToReadByUserId(Long id) {
//        User user = userService.findById(id);
//        List<Message> messages = repository.findAllMessagesIsNotReadByUser(user);
//        messages.forEach(message -> {
//            message.setStatus(MessageStatus.READ);
//            repository.save(message);
//        });
//    }
}
