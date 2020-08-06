package com.example.studentsystem.servicesImpl;

import com.example.studentsystem.dao.Dao;
import com.example.studentsystem.dto.UserDto;
import com.example.studentsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;

@Component
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private Dao<UserDto> userDao;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = userService.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        //Todo Maybe add try/catch with response entity?
        if (user.hasRoleAdd()) {
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
        }
        if (user.hasRoleRemove()) {
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority("ROLE_REMOVER")));
        }

        return null;
    }

    public Integer save(UserDto user) {
        UserDto newUser = new UserDto();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setName(user.getName());
        newUser.setSurname(user.getSurname());
        newUser.setRoleAdd(user.hasRoleAdd());
        newUser.setRoleRemove(user.hasRoleRemove());
        try {
            return userService.save(newUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
