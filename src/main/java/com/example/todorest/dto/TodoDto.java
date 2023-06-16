package com.example.todorest.dto;

import com.example.todorest.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoDto {
    private int id;
    private String title;
    private Status status;
    private CategoryDto categoryDto;
    private UserDto userDto;
}
