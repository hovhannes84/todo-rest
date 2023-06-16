package com.example.todorest.mapper;

import com.example.todorest.dto.CreateTodoRequestDto;
import com.example.todorest.dto.CreateTodoResponseDto;
import com.example.todorest.dto.TodoDto;
import com.example.todorest.dto.UpdateTodoRequestDto;
import com.example.todorest.entity.Todo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoMapper {
    Todo map(CreateTodoRequestDto dto);
    CreateTodoResponseDto map(Todo entity);
    TodoDto mapToDto(Todo entity);
    UpdateTodoRequestDto updateDto(Todo entity);

}
