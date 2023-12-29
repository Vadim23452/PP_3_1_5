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

        roleService.saveRole(roleAdmin);
        roleService.saveRole(roleUser);

        User admin = new User("Игорь", "Игорев", 20, "admin", "admin", adminRole);
        User user = new User("Гриша", "Гришин", 21, "user", "user", userRole);

        userService.addUser(admin);
        userService.addUser(user);
    }
}