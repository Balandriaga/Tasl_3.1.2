package ru.itmentor.spring.boot_security.demo.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RolesService;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/admin")
@Slf4j
public class AdminRestController {
    private final UserService userService;
    private final RolesService rolesService;

    @Autowired
    public AdminRestController(UserService userService, RolesService rolesService) {
        this.userService = userService;
        this.rolesService = rolesService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> showUsers() {
        log.info("Запрос на получение списка всех пользователей");
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/users/new")
    public ResponseEntity<Set<Role>> createUserForm() {
        log.info("Запрос на получение списка ролей для нового пользователя");
        return ResponseEntity.ok(rolesService.findAll());
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Ошибка при сохранении пользователя с данными: {}", user);
            return ResponseEntity.badRequest().body(user);
        }
        userService.save(user);
        log.info("Юзер успешно сохранен с id {} ", user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> editUser(@PathVariable("id") long id) {
        User user = userService.findById(id);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Пользователь с ID " + id + " не найден");
        }

        return ResponseEntity.ok(user);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @Valid @ModelAttribute("user") User updatedUser, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.info("Ошибка при обновлении пользователя с id = {}", id);
            return ResponseEntity.badRequest().body(updatedUser);
        }
        userService.update(id, updatedUser);
        log.info("Юзер с id = {} обновлен", id);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") long id) {
        userService.delete(id);
        log.info("Пользователь с id = {} удален", id);
        return ResponseEntity.noContent().build();
    }
}

