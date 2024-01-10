package ru.kata.spring.boot_security.demo.model;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;


@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "type")
  private String roleType;

  public Role() {
  }

  public Role(String roleType) {
    this.roleType = roleType;
  }

  public Role(Integer id) {
    this.id = id;
  }

  public Role(Integer id, String roleType) {
    this.id = id;
    this.roleType = roleType;
  }


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getRoleType() {
    return roleType;
  }

  public void setRoleType(String roleType) {
    this.roleType = roleType;
  }


  @Override
  public String getAuthority() {
    return getRoleType();
  }

  @Override
  public String toString() {
    return roleType.substring(5);
  }

}
