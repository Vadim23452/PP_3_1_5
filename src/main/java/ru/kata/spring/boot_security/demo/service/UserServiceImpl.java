package ru.kata.spring.boot_security.demo.service;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.configs.PasswordEncoderConfig;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoderConfig passwordEncoder;

  public UserServiceImpl(UserRepository userRepository, PasswordEncoderConfig passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  @Transactional
  public User getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepository.findByEmail(email);
  }

  @Override
  @Transactional(readOnly = true)
  public List<User> getUsersList() {
    return userRepository.findAll();
  }

  @Override
  @Transactional
  public User getById(Long id) {
    return userRepository.findById(id).get();
  }

  @Override
  @Transactional
  public void addUser(User user) {
    user.setPassword(passwordEncoder.passwordEncoder().encode(user.getPassword()));
    userRepository.save(user);
  }

  @Override
  @Transactional
  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }

  @Override
  @Transactional
  public void editUser(User user) {
    user.setPassword(passwordEncoder.passwordEncoder().encode(user.getPassword()));
    userRepository.save(user);
  }
}