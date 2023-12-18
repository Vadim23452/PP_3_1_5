package ru.kata.spring.boot_security.demo.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

  @PersistenceContext
  private EntityManager em;

  @Autowired
  private UserRepository userRepository;


  @Autowired
  private PasswordEncoder passwordEncoder;


  @Transactional(readOnly = true)
  public List<User> getAllUsers() {
    return em.createQuery("SELECT u FROM User u", User.class).getResultList();
  }

  @Transactional
  public void saveUser(User user) {
    user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    em.persist(user);
  }

  @Transactional(readOnly = true)
  public User showUserById(Long id) {
    return em.find(User.class, id);
  }

  @Transactional
  public void deleteUser(Long id) {
    em.remove(em.find(User.class, id));
  }

  @Transactional
  public void updateUser(Long id, User updatedUser) {
    User userToUpdate = showUserById(id);
    userToUpdate.setName(updatedUser.getName());
    userToUpdate.setLastName(updatedUser.getLastName());
    userToUpdate.setAge(updatedUser.getAge());
    userToUpdate.setEmail(updatedUser.getEmail());
    userToUpdate.setAddress(updatedUser.getAddress());
    userToUpdate.setUsername(updatedUser.getUsername());
    userToUpdate.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
    em.merge(userToUpdate);
  }

  public User findByUserName(String name) {
    return (User) em.createNativeQuery(" Select * from users u where u.username =:username ", User.class)
        .setParameter("username", name).getSingleResult();
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("User '%s not found" + username);
    }
    return new org.springframework.security.core.userdetails.User(user.getUsername(),
        user.getPassword(), mapRolesAuthorities(user.getRoles()));
  }

  private Collection<? extends GrantedAuthority> mapRolesAuthorities(Set<Role> roles) {
    return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(
        Collectors.toList());
  }


}
