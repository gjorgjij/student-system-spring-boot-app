package com.example.studentsystem.services;

import com.example.studentsystem.dto.StudentDto;
import com.example.studentsystem.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserDto> getById(int id);

    UserDto getByUsername(String username);

    List<UserDto> getAll();

    Integer save(UserDto userDto) throws Exception;
}
