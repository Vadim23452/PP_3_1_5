package ru.kata.spring.boot_security.demo.service;

import java.util.List;
import java.util.Optional;
import ru.kata.spring.boot_security.demo.model.Role;

public interface RoleService {
  void saveRole(Role role);
  List<Role> getAllRoles();

}
