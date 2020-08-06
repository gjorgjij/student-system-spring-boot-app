package com.example.studentsystem.dao;

import com.example.studentsystem.dto.UserDto;
import com.example.studentsystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class UserDao implements Dao<UserDto> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<UserDto> getById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public Collection<UserDto> getAll() {
        return (Collection<UserDto>) userRepository.findAll();
    }

    @Override
    public UserDto save(UserDto userDto) {
        return userRepository.save(userDto);
    }

    @Override
    public void update(UserDto userDto) {
        userRepository.save(userDto);
    }

    @Override
    public void delete(UserDto userDto) {
        userRepository.delete(userDto);
    }
}
