package ru.itmentor.spring.boot_security.demo.service;

import ru.itmentor.spring.boot_security.demo.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    User findById(long id);
    void save(User user);
    void update(long id, User updatedUser);
    void delete(long id);
}
