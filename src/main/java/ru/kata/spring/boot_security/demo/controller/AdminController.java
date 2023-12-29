package ru.kata.spring.boot_security.demo.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.*;
import ru.kata.spring.boot_security.demo.service.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

  private final UserService userService;
  private final RoleService roleService;

  public AdminController(UserService userService, RoleService roleService) {
    this.userService = userService;
    this.roleService = roleService;
  }

  @GetMapping()
  public String showUsers(Principal principal, Model model) {

    model.addAttribute("newUser", new User());
    model.addAttribute("allRoles", roleService.getAllRoles());
    model.addAttribute("user", userService.findUsersByEmail(principal.getName()));
    model.addAttribute("users", userService.showUsers());
    return "admin";
  }

  @PostMapping()
  public String createUser(@ModelAttribute("user") User user) {
    userService.addUser(user);
    return "redirect:/admin";
  }

  @PatchMapping(value = "/edit/{id}")
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
