package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.kata.spring.boot_security.demo.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final SuccessUserHandler successUserHandler;
  private final UserService userService;
  private final PasswordEncoderConfig passwordEncoderConfig;

  public WebSecurityConfig(SuccessUserHandler successUserHandler,
                           UserService userService,
                           PasswordEncoderConfig passwordEncoderConfig) {
    this.successUserHandler = successUserHandler;
    this.userService = userService;
    this.passwordEncoderConfig = passwordEncoderConfig;
    }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .httpBasic()
        .and()
        .csrf()
        .disable()
        .authorizeRequests()
        .antMatchers("/login").permitAll()
        .antMatchers("/api/admin/**").hasRole("ADMIN")
        .antMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
        .anyRequest().authenticated()
        .and()
        .formLogin().loginPage("/login")
        .successHandler(successUserHandler)
        .permitAll()
        .and()
        .logout()
        .permitAll();
  }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
      DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
      authenticationProvider.setPasswordEncoder(passwordEncoderConfig.passwordEncoder());
      authenticationProvider.setUserDetailsService(userService);
      return authenticationProvider;
    }
}