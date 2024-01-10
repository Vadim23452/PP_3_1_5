package ru.kata.spring.boot_security.demo.testUser;


import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;

@Component
public class DBUsers {
    private final UserService userService;
    private final RoleServiceImpl roleService;

    public DBUsers(UserService userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    private void dataBaseInit() {
        Set<Role> adminRole = new HashSet<>();
        Role roleUser = new Role("ROLE_USER");
        Role roleAdmin = new Role("ROLE_ADMIN");

        adminRole.add(roleAdmin);
        adminRole.add(roleUser);

        Set<Role> userRole = new HashSet<>();
        userRole.add(roleUser);

        roleService.save(roleAdmin);
        roleService.save(roleUser);

        User admin = new User("admin","adminov",22,"admin@mail.ru","admin",adminRole);
        User user = new User("user","userkov",20,"user@mail.ru","user",userRole);

        userService.addUser(admin);
        userService.addUser(user);
    }
}