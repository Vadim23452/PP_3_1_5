package ru.kata.spring.boot_security.demo.controller;


import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping()
  public String showUser(Principal principal, Model model) {
//    userService.findUsersByEmail(principal.getName());
    model.addAttribute("user", userService.findUsersByEmail(principal.getName()));
    return "user";
  }
}
