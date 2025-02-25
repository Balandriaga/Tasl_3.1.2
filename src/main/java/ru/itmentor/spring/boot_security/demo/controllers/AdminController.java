package ru.itmentor.spring.boot_security.demo.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RolesService;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RolesService rolesService;

    @Autowired
    public AdminController(UserService userService, RolesService rolesService) {
        this.userService = userService;
        this.rolesService = rolesService;
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        log.info("Запрос на получение списка всех пользователей");
        model.addAttribute("users", userService.findAll());
        return "admin/users";
    }
    @GetMapping("/new")
    public  String createUserForm(@ModelAttribute("user")User user, Model model){
        log.info("Вызов формы для создания юзера");
        model.addAttribute("roles", rolesService.findAll());
        return "admin/form";
    }

    @PostMapping("/users")
    public String createUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.info("Ошибка при сохранении пользователя с данными: {}", user);
            return "admin/form";
        }
        log.info("Юзер успешно сохранен с id {} ", user.getId());
        userService.save(user);
        return "redirect:/admin/users";
    }
    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id")long id, Model model){
        log.info("Вызов формы удаления юзера");
        User user = userService.findById(id);
        model.addAttribute("user",user);
        model.addAttribute("roles",rolesService.findAll());
        return "admin/form";
    }
    @PatchMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid @ModelAttribute("user") User updatedUser, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.info("Ошибка при обновлении пользователя с id = {}", id);
            return "admin/form";
        }
        updatedUser.setId(id);
        userService.update(id, updatedUser);
        log.info("Юзер с id = {} обновлен", id);
        return "redirect:/admin/users";
    }
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.delete(id);
        log.info("Пользователь с id = {} удален", id);
        return "redirect:/admin/users";

    }
}


