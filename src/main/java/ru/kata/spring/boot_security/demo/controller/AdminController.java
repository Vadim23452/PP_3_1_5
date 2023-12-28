package ru.kata.spring.boot_security.demo.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
  private final UserService userService;
  private final RoleService roleService;
  @Autowired
  public AdminController(UserService userService, RoleService roleService) {
    this.userService = userService;
    this.roleService = roleService;
  }

  @GetMapping()
  public String userPage(Principal principal, Model model) {

    model.addAttribute("newUser", new User());
    Set<Role> roles = new HashSet<>(roleService.getAllRoles());
    model.addAttribute("allRoles", roles);
    User user = userService.findUsersByEmail(principal.getName());
    model.addAttribute("user", user);
    model.addAttribute("users", userService.getAllUsers());
    return "admin";
  }

  @PostMapping()
  public String createUser(@ModelAttribute("user") User user) {
    userService.add(user);
    return "redirect:/admin";
  }

  @PatchMapping(value = "/test2/{id}")
  public String updateUser(@ModelAttribute("user") User updatedUser, @PathVariable("id") Long id) {
    userService.updateUser(updatedUser, id);
    return "redirect:/admin";
  }

  @DeleteMapping("/{id}")
  public String deleteUser(@PathVariable("id") Long id) {
    userService.deleteUser(id);
    return "redirect:/admin";
  }
}
