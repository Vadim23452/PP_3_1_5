package ru.kata.spring.boot_security.demo.controller;


import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {


  @Autowired
  private UserRepository userRepository;

  @GetMapping()
  public String userPage(Principal principal, Model model) {
    model.addAttribute("user", userRepository.findByUsername(principal.getName()));
    return "user";
  }
}
