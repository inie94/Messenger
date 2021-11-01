package ru.anani.messenger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.anani.messenger.dto.ContactDTO;
import ru.anani.messenger.dto.UserDTO;
import ru.anani.messenger.entities.User;
import ru.anani.messenger.entities.enums.UserStatus;
import ru.anani.messenger.services.DTOService;
import ru.anani.messenger.services.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public UserDTO getAuthorizedUser(Principal principal) {
        User user = userService.findByEmail(principal.getName());
        if (user.getStatus().equals(UserStatus.OFFLINE)) {
            user.setStatus(UserStatus.ONLINE);
            user = userService.save(user);
        }
        return DTOService.toUserDTO(user);
    }

//    @PostMapping("/user/edit-profile")
//    public UserDTO editUser(Principal principal,
//                         @RequestBody UserDTO representation) {
//
//        System.out.println(representation);
//
//        if (representation.getEmail() != null && service.findByEmail(representation.getEmail()) != null) {
//            /**
//             *  В БД есть пользователь с таким Email.
//             *  необходимо вернуть ответ в котором указывается что Email неправильный
//             */
//        }
//
//        /**
//         *  Записать измененные значения и сохранить в БД.
//         *  Необходимо переписать метод update в классе UserService
//         *  и изменить форму регистрации
//         */
//
//        User user = service.update(representation, service.findByEmail(principal.getName()));
//        return DTOService.toUserDTO(user);
//    }
//
//
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
