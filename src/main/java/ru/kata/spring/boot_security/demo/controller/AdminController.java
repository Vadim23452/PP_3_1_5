package ru.kata.spring.boot_security.demo.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

  private final UserService userService;

  @Autowired
  public AdminController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping()
  public String showUsers(Model model) {
    model.addAttribute("users", userService.getAllUsers());
    return "usersPage";
  }

  @GetMapping("/")
  public String showUser(@RequestParam("id") Long id, Model model) {
    model.addAttribute("user", userService.showUserById(id));
    return "userPage";
  }

  @GetMapping("/edit")
  public String editUser(Model model, @RequestParam("id") Long id) {
    model.addAttribute("user", userService.showUserById(id));
    return "edit";
  }

  @PatchMapping()
  public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
      @RequestParam("id") Long id) {

    if (bindingResult.hasErrors()) {
      return "edit";
    }

    userService.updateUser(user);
    return "redirect:/admin";
  }

  @DeleteMapping()
  public String deleteUser(@RequestParam("id") Long id) {
    userService.deleteUser(id);
    return "redirect:/admin";
  }

  @GetMapping("/new")
  public String newUser(Model model) {
    model.addAttribute("user", new User());
    return "new";
  }

  @PostMapping
  public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      return "new";
    }

    userService.saveUser(user);
    return "redirect:/admin";
  }


}
