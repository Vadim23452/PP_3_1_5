package ru.kata.spring.boot_security.demo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.configs.PasswordEncoderConfig;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final PasswordEncoderConfig passwordEncoder;

  @Autowired
  public UserService(UserRepository userRepository, PasswordEncoderConfig passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }
  @Transactional
  public void add(User user) {
    user.setPassword(passwordEncoder.passwordEncoder().encode(user.getPassword()));
    userRepository.save(user);
  }

  @Transactional(readOnly = true)
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @Transactional(readOnly = true)
  public User showUserById(Long id) {
    return userRepository.getById(id);
  }

  @Transactional
  public void updateUser(User updatedUser) {

    updatedUser.setPassword(passwordEncoder.passwordEncoder().encode(updatedUser.getPassword()));
    userRepository.save(updatedUser);
  }

  @Transactional
  public void deleteUser(Long id) {

    userRepository.deleteById(id);
  }

  @Transactional
  public void updateUser(User updatedUser, Long id) {
    User existingUser = showUserById(id);
    existingUser.setFirstName(updatedUser.getFirstName());
    existingUser.setEmail(updatedUser.getEmail());
    existingUser.setLastname(updatedUser.getLastname());
    existingUser.setAge(updatedUser.getAge());
    existingUser.setRoles(updatedUser.getRoles());
    if (!updatedUser.getPassword().isEmpty()) {
      existingUser.setPassword(passwordEncoder.passwordEncoder().encode(updatedUser.getPassword()));
    }
    userRepository.save(existingUser);
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserDetails user = findUsersByEmail(email);
    if (user == null) {
      throw new UsernameNotFoundException(String.format("User '%s' not found", email));
    }

    return user;
  }

  public User findUsersByEmail(String email) {
    return userRepository.findByEmail(email);
  }
}
