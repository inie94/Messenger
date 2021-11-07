package ru.anani.messenger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import ru.anani.messenger.dto.DialogDTO;
import ru.anani.messenger.dto.UserNotificationDTO;
import ru.anani.messenger.entities.Dialog;
import ru.anani.messenger.entities.Message;
import ru.anani.messenger.entities.User;
import ru.anani.messenger.entities.enums.UserNotificationType;
import ru.anani.messenger.services.DTOService;
import ru.anani.messenger.services.DialogService;
import ru.anani.messenger.services.MessagesService;
import ru.anani.messenger.services.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("dialogs")
public class DialogController {

    private final DialogService dialogService;
    private final UserService userService;
    private final MessagesService messagesService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public DialogController(DialogService dialogService, UserService userService, MessagesService messagesService, SimpMessagingTemplate messagingTemplate) {
        this.dialogService = dialogService;
        this.userService = userService;
        this.messagesService = messagesService;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping
    public List<DialogDTO> getAllUserDialogs(Principal principal) {
        User user = userService.findByEmail(principal.getName());
        List<Dialog> dialogs = dialogService.getUserDialogs(user);
        List<DialogDTO> dialogDTOS = new ArrayList<>();
        dialogs.forEach(dialog -> {
            Message lastMessage = messagesService.getLastMessageByDialog(dialog);
            Long newMessagesCount = messagesService.getCountOfNewMessagesInDialogWhereSenderNotUser(dialog, user);
            dialogDTOS.add(DTOService.toDialogDTO(dialog, dialog.getAnotherCompanion(user), lastMessage, newMessagesCount));
        });
        return dialogDTOS;
    }

    @GetMapping("hidden")
    public List<DialogDTO> getAllUserHiddenDialogs(Principal principal) {
        User user = userService.findByEmail(principal.getName());
        List<Dialog> dialogs = dialogService.getUserHideDialogs(user);
        List<DialogDTO> dialogDTOS = new ArrayList<>();
        dialogs.forEach(dialog -> {
            Message lastMessage = messagesService.getLastMessageByDialog(dialog);
            Long newMessagesCount = messagesService.getCountOfNewMessagesInDialogWhereSenderNotUser(dialog, user);
            dialogDTOS.add(DTOService.toDialogDTO(dialog, dialog.getAnotherCompanion(user), lastMessage, newMessagesCount));
        });
        return dialogDTOS;
    }

    @GetMapping("id:{id}")
    public DialogDTO detDialogById(@PathVariable("id") Long id, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        Dialog dialog = dialogService.getDialogById(id);
        Message message = messagesService.getLastMessageByDialog(dialog);
        Long newMessagesCount = messagesService.getCountOfNewMessagesInDialogWhereSenderNotUser(dialog, user);
        return DTOService.toDialogDTO(dialog, dialog.getAnotherCompanion(user), message, newMessagesCount);
    }

    @GetMapping("blocked")
    public List<DialogDTO> getAllUserBlockedDialogs(Principal principal) {
        User user = userService.findByEmail(principal.getName());
        List<Dialog> dialogs = dialogService.getUserBlockedDialogs(user);
        List<DialogDTO> dialogDTOS = new ArrayList<>();
        dialogs.forEach(dialog -> {
            Message lastMessage = messagesService.getLastMessageByDialog(dialog);
            Long newMessagesCount = messagesService.getCountOfNewMessagesInDialogWhereSenderNotUser(dialog, user);
            dialogDTOS.add(DTOService.toDialogDTO(dialog, dialog.getAnotherCompanion(user), lastMessage, newMessagesCount));
        });
        return dialogDTOS;
    }

    @PutMapping
    public DialogDTO createDialogByCompanion(@RequestBody Long companionId, Principal principal) {
        User userA = userService.findByEmail(principal.getName());
        User userB = userService.findById(companionId);
        Dialog dialog = dialogService.create(userA, userB);


        messagingTemplate.convertAndSend("/conversation/user/id:" + userB.getId(),
                UserNotificationDTO.builder()
                        .targetId(dialog.getId())
                        .type(UserNotificationType.AWAIT_CONNECTION)
                        .build());

        return DTOService.toDialogDTO(dialog, userB, null, null);
    }

    @PostMapping("show")
    public DialogDTO showDialogToUserByCompanion(@RequestBody Long companionId, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        User companion = userService.findById(companionId);
        Dialog dialog = dialogService.getDialogByUsers(user, companion);
        dialogService.showToUser(dialog, user);
        Message lastMessage = messagesService.getLastMessageByDialog(dialog);
        Long newMessagesCount = messagesService.getCountOfNewMessagesInDialogWhereSenderNotUser(dialog, user);

        return DTOService.toDialogDTO(dialog, companion, lastMessage, newMessagesCount);
    }

    @PostMapping("hide")
    public DialogDTO hideDialogForUser(@RequestBody Long dialogId, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        Dialog dialog = dialogService.getDialogById(dialogId);
        dialog = dialogService.hideToUser(dialog, user);
        Message lastMessage = messagesService.getLastMessageByDialog(dialog);
        Long newMessagesCount = messagesService.getCountOfNewMessagesInDialogWhereSenderNotUser(dialog, user);
        return DTOService.toDialogDTO(dialog, dialog.getAnotherCompanion(user), lastMessage, newMessagesCount);
    }
}
