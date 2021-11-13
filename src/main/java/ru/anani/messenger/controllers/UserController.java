package ru.anani.messenger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.anani.messenger.dto.UserDTO;
import ru.anani.messenger.entities.User;
import ru.anani.messenger.entities.enums.UserStatus;
import ru.anani.messenger.services.DTOService;
import ru.anani.messenger.services.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public UserDTO getAuthorizedUser(Principal principal) {
        User user = userService.findByEmail(principal.getName());
        if (user.getStatus().equals(UserStatus.OFFLINE)) {
            user.setStatus(UserStatus.ONLINE);
            user = userService.save(user);
        }
        return DTOService.toUserDTO(user);
    }

    @GetMapping("/search")
    public List<UserDTO> searchUsers(@RequestParam("value") String searchValue, Principal principal) {
        User authorizedUser = userService.findByEmail(principal.getName());
        List<UserDTO> userDTOs = new ArrayList<>();
        List<User> users = userService.searchUsersBy(authorizedUser, searchValue);
        List<User> hideUsers = userService.getAllCompanionIsBlockedUser(authorizedUser);
        users.removeAll(hideUsers);
        users.forEach(user -> userDTOs.add(DTOService.toUserDTO(user)));
        return userDTOs;
    }

    @PostMapping("/edit")
    public UserDTO editUserProfile(@RequestBody UserDTO representation, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        return DTOService.toUserDTO(userService.update(representation, user));
    }
}
