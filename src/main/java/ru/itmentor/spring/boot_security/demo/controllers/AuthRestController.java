package ru.itmentor.spring.boot_security.demo.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthRestController {
    @GetMapping("/login")
    public ResponseEntity<String> loginPage(){
        log.info("Запрос получения страницы входа");
        return ResponseEntity.ok("Страница входа недоступна через API");
    }
}
