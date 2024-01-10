package ru.kata.spring.boot_security.demo.service;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

public interface UserService extends UserDetailsService {

  List<User> getUsersList();

  User getById(Long id);

  void addUser(User user);

  void deleteUser(Long id);

  void editUser(User user);

  User getUserByEmail(String email);
}