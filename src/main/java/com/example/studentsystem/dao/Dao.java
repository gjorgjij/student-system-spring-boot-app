package com.example.studentsystem.dao;

import com.example.studentsystem.dto.CourseDto;

import java.util.Collection;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> getById(int id);
    Collection<T> getAll();
//    CourseDto getByName(String name);

    T save(T t);
    void update(T t);
    void delete(T t);
}
