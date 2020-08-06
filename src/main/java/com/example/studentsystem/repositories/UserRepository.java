package com.example.studentsystem.repositories;

import com.example.studentsystem.dao.UserDao;
import com.example.studentsystem.dto.UserDto;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserDto, Integer> {

    UserDto findByUsername(String username);
}
