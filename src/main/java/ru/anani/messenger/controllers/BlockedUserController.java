package ru.anani.messenger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import ru.anani.messenger.dto.UserDTO;
import ru.anani.messenger.dto.UserNotificationDTO;
import ru.anani.messenger.entities.BlockedUser;
import ru.anani.messenger.entities.Dialog;
import ru.anani.messenger.entities.User;
import ru.anani.messenger.entities.enums.UserNotificationType;
import ru.anani.messenger.services.ContactsService;
import ru.anani.messenger.services.DTOService;
import ru.anani.messenger.services.DialogService;
import ru.anani.messenger.services.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("blacklist")
public class BlockedUserController {

    private final UserService userService;
    private final ContactsService contactsService;
    private final DialogService dialogService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public BlockedUserController(UserService userService, ContactsService contactsService, DialogService dialogService, SimpMessagingTemplate messagingTemplate) {
        this.userService = userService;
        this.contactsService = contactsService;
        this.dialogService = dialogService;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping
    public List<UserDTO> getAllUserBlockedCompanions(Principal principal) {
        User user = userService.findByEmail(principal.getName());
        List<BlockedUser> blockedUsers = contactsService.getBlockedContactsByUser(user);
        blockedUsers.forEach(System.out::println);
        List<UserDTO> users = new ArrayList<>();
        blockedUsers.forEach(contact -> users.add(DTOService.toUserDTO(contact.getCompanion())));
        return users;
    }

    @PutMapping
    public UserDTO blockCompanionToUser(@RequestBody Long companionId, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        User companion = userService.findById(companionId);
        Dialog dialog = dialogService.getDialogByUsers(user, companion);
        if (dialog != null) {
            dialogService.blockDialog(dialog);
            messagingTemplate.convertAndSend("/conversation/user/id:" + companion.getId(),
                    UserNotificationDTO.builder()
                            .targetId(user.getId())
                            .type(UserNotificationType.USER_IS_BLOCKED)
                            .build());
        }
        return DTOService.toUserDTO(contactsService.addToBlockList(user, companion).getCompanion());
    }

    @DeleteMapping
    public void unblockCompanionIntoUserBlacklist(@RequestBody Long companionId, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        User companion = userService.findById(companionId);
        Dialog dialog = dialogService.getDialogByUsers(user, companion);
        if (dialog != null) {
            dialogService.unblockDialog(dialog);
            messagingTemplate.convertAndSend("/conversation/user/id:" + companion.getId(),
                    UserNotificationDTO.builder()
                            .targetId(dialog.getId())
                            .type(UserNotificationType.USER_IS_UNBLOCKED)
                            .build());
        }
        contactsService.unblockCompanionByUser(user, companion);
    }
}
