package com.example.todorest.service;

import com.example.todorest.dto.CreateTodoRequestDto;
import com.example.todorest.dto.CreateTodoResponseDto;
import com.example.todorest.dto.TodoDto;
import com.example.todorest.dto.UpdateTodoRequestDto;
import com.example.todorest.entity.Status;
import com.example.todorest.entity.Todo;
import com.example.todorest.entity.User;
import com.example.todorest.sequrity.CurrentUser;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface TodoService {
    ResponseEntity<List<TodoDto>> findTodos(CurrentUser currentUser);
    ResponseEntity<List<TodoDto>> findTodosByStatus(CurrentUser currentUser, Status status);
    ResponseEntity<Todo> findById(int id);

    ResponseEntity<CreateTodoResponseDto> addTodo(CreateTodoRequestDto todo, CurrentUser user) throws IOException;

    ResponseEntity<?> deleteById(int id);

    public ResponseEntity<UpdateTodoRequestDto> update(UpdateTodoRequestDto todo, User user);

}
