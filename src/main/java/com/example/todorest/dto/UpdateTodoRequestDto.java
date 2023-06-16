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
public class UpdateTodoRequestDto {

    private int id;
    private String title;
    private Status status;
    private int categoryId;
    private int userId;
}
