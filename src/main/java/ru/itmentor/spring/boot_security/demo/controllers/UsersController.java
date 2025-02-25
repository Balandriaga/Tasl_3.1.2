package ru.itmentor.spring.boot_security.demo.controllers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/user")
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService){
        this.userService=userService;

    }

    @GetMapping("/{id}")
    public String userProfile(@PathVariable("id") long id, Model model){
        log.info("Запрос на доступ к странице пользователя с  id={}",id);
        model.addAttribute("user", userService.findById(id));
        return "/user/show";
    }
}


