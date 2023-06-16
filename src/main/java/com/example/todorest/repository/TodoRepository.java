package com.example.todorest.repository;

import com.example.todorest.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo,Integer> {
    Optional<Todo> findByTitle(String title);
    List<Todo> findAllByUser_Id(int id);

}
