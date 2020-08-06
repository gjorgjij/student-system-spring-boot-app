package com.example.studentsystem.servicesImpl;

import com.example.studentsystem.dao.Dao;
import com.example.studentsystem.dto.UserDto;
import com.example.studentsystem.repositories.UserRepository;
import com.example.studentsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    public Dao<UserDto> userDao;

    @Autowired
    public UserRepository userRepository;

    @Override
    public Optional<UserDto> getById(int id) {
        return userDao.getById(id);
    }

    @Override
    public UserDto getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<UserDto> getAll() {
        return null;
    }

    @Override
    public Integer save(UserDto userDto) throws Exception {
        return userDao.save(userDto).getId();
    }
}
