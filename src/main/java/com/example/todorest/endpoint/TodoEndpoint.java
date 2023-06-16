package com.example.todorest.endpoint;

import com.example.todorest.dto.CreateTodoRequestDto;
import com.example.todorest.dto.CreateTodoResponseDto;
import com.example.todorest.dto.TodoDto;
import com.example.todorest.dto.UpdateTodoRequestDto;
import com.example.todorest.entity.Status;
import com.example.todorest.entity.Todo;
import com.example.todorest.entity.User;
import com.example.todorest.sequrity.CurrentUser;
import com.example.todorest.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoEndpoint {


    private final TodoService todoService;


    @PostMapping()
    public ResponseEntity<CreateTodoResponseDto> create(@RequestBody CreateTodoRequestDto todo,@AuthenticationPrincipal CurrentUser user) throws IOException {
        System.out.println(user);
        return ResponseEntity.ok(todoService.addTodo(todo,user).getBody());
    }

    @GetMapping()
    public ResponseEntity<List<TodoDto>> getAll(@AuthenticationPrincipal CurrentUser user) {
        return  todoService.findTodos(user);

    }
    @GetMapping("/byStatus/{status}")
    public ResponseEntity<List<TodoDto>> getAllByStatus(@AuthenticationPrincipal CurrentUser user, @RequestParam Status status) {
        return  todoService.findTodosByStatus(user,status);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getById(@PathVariable("id") int id) {
        return todoService.findById(id);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
        return todoService.deleteById(id);

    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateTodoRequestDto> update(@PathVariable("id") int id, @RequestBody UpdateTodoRequestDto todo,@AuthenticationPrincipal User user){

        return ResponseEntity.ok(todoService.update(todo,user).getBody());

    }

}
