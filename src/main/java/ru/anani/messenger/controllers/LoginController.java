package ru.anani.messenger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.anani.messenger.security.UserRepresentation;
import ru.anani.messenger.services.UserService;

import java.security.Principal;

@Controller
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String index(Model model,
                        Principal principal){
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/registration")
    public String registerForm(Model model) {
        model.addAttribute("user", new UserRepresentation());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerNewUser(@ModelAttribute("user") UserRepresentation userRepresentation,
                                  BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "registration";
        if(!userRepresentation.getPassword().equals(userRepresentation.getRepeatPassword())) {
            bindingResult.rejectValue("password", "Пароли не совпадают");
            return "registration";
        }

        userService.create(userRepresentation);
        return "redirect:/login";
    }
}
