package com.example.todorest.service.impl;

import com.example.todorest.dto.*;
import com.example.todorest.entity.Category;
import com.example.todorest.entity.Status;
import com.example.todorest.entity.Todo;
import com.example.todorest.entity.User;
import com.example.todorest.mapper.CategoryMapper;
import com.example.todorest.mapper.TodoMapper;
import com.example.todorest.mapper.UserMapper;
import com.example.todorest.repository.CategoryRepository;
import com.example.todorest.repository.TodoRepository;
import com.example.todorest.repository.UserRepository;
import com.example.todorest.sequrity.CurrentUser;
import com.example.todorest.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;
    private final UserMapper userMapper;
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;


    @Override
    public ResponseEntity<CreateTodoResponseDto> addTodo(CreateTodoRequestDto requestDto, CurrentUser user) throws IOException {
        Optional<Category> category = categoryRepository.findById(requestDto.getCategoryId());
        if(category.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Todo saved = todoMapper.map(requestDto);
        saved.setCategory(category.get());
        int userId = user.getUser().getId();
        Optional<User> byId = userRepository.findById(userId);
        if (byId.isPresent()){
            saved.setUser(byId.get());
        }
        saved.setStatus(Status.valueOf("NOT_STARTED"));
        todoRepository.save(saved);
        return ResponseEntity.ok(todoMapper.map(saved));
    }
    @Override
    public ResponseEntity<List<TodoDto>> findTodos(CurrentUser currentUser) {
        List<Todo> all = todoRepository.findAllByUser_Id(currentUser.getUser().getId());
        if (all.size() == 0) {

            return ResponseEntity.notFound().build();
        }
        List<TodoDto> todoDtos = new ArrayList<>();
        for (Todo todo : all) {
            Optional<Category> category = categoryRepository.findById(todo.getCategory().getId());
            Optional<User> user = userRepository.findById(todo.getUser().getId());
            TodoDto todoDto = todoMapper.mapToDto(todo);
            todoDto.setStatus(todo.getStatus());
            todoDto.setCategoryDto(categoryMapper.mapToDto(category.get()));
            todoDto.setUserDto(userMapper.mapToDto(user.get()));
            todoDtos.add(todoDto);

        }
        return ResponseEntity.ok(todoDtos);
    }
    @Override
    public ResponseEntity<List<TodoDto>> findTodosByStatus(CurrentUser currentUser,Status status) {

        List<Todo> all = todoRepository.findAllByUser_Id(currentUser.getUser().getId());
        if (all.size() == 0) {

            return ResponseEntity.notFound().build();
        }
        List<Todo> allByStatus = new ArrayList<>();
        for (Todo todo : all) {
            if (todo.getStatus() == status){
                allByStatus.add(todo);
            }
        }

        List<TodoDto> todoDtos = new ArrayList<>();
        for (Todo todo : allByStatus) {
            Optional<Category> category = categoryRepository.findById(todo.getCategory().getId());
            Optional<User> user = userRepository.findById(todo.getUser().getId());
            TodoDto todoDto = todoMapper.mapToDto(todo);
            todoDto.setStatus(todo.getStatus());
            todoDto.setCategoryDto(categoryMapper.mapToDto(category.get()));
            todoDto.setUserDto(userMapper.mapToDto(user.get()));
            todoDtos.add(todoDto);

        }
        return ResponseEntity.ok(todoDtos);
    }


    @Override
    public ResponseEntity<Todo> findById(int id) {
        Optional<Todo> byId = todoRepository.findById(id);
        if (byId.isPresent()) {
            return ResponseEntity.ok(byId.get());
        }
        return ResponseEntity
                .notFound()
                .build();
    }
    @Override
    public ResponseEntity<?> deleteById(int id) {
        if (todoRepository.existsById(id)) {

            todoRepository.deleteById(id);
            return ResponseEntity
                    .noContent()
                    .build();
        }
        return ResponseEntity
                .notFound()
                .build();
    }
    public ResponseEntity<UpdateTodoRequestDto> update(UpdateTodoRequestDto todo, User user) {
        Optional<Todo> byId = todoRepository.findById(todo.getId());
        if (byId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<Todo> byTitle = todoRepository.findByTitle(todo.getTitle());
        if (byTitle.isPresent() && byTitle.get().getId() != todo.getId()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Todo todoFromDB = byId.get();
        if (todo.getTitle() != null && !todo.getTitle().isEmpty()) {
            todoFromDB.setTitle(todo.getTitle());
        }
        if (todo.getStatus() != null ){
            todoFromDB.setStatus(todo.getStatus());
        }
        Optional<Category> category = categoryRepository.findById(todo.getCategoryId());
        if (category.isPresent()){
            todoFromDB.setCategory(category.get());
        }

        todoFromDB.setUser(user);
        todoRepository.save(todoFromDB);

        return ResponseEntity.ok(todoMapper.updateDto(todoFromDB));
    }

}
