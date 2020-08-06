package com.example.studentsystem.dto;


import javax.persistence.*;

@Entity
@Table(name = "user")
public class UserDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String surname;

    @Column(name = "role_add")
    private boolean roleAdd;

    @Column(name = "role_remove")
    private boolean roleRemove;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean hasRoleAdd() {
        return roleAdd;
    }

    public void setRoleAdd(boolean role_add) {
        this.roleAdd = role_add;
    }

    public boolean hasRoleRemove() {
        return roleRemove;
    }

    public void setRoleRemove(boolean role_remove) {
        this.roleRemove = role_remove;
    }
}