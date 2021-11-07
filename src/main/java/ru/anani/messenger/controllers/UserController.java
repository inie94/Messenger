package ru.anani.messenger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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


////
////    @GetMapping("/csrf")
////    public @ResponseBody String getCsrfToken(HttpServletRequest request) {
////        CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
////        return csrf.getToken();
////    }

    @GetMapping("/search")
    public List<UserDTO> searchUsers(@RequestParam("value") String searchValue, Principal principal) {
        User authorizedUser = userService.findByEmail(principal.getName());
        List<UserDTO> userDTOs = new ArrayList<>();
        List<User> users = userService.searchUsersBy(authorizedUser, searchValue);
        users.forEach(user -> userDTOs.add(DTOService.toUserDTO(user)));
        return userDTOs;
    }
}
