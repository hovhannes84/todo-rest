package com.example.todorest.service;

import com.example.todorest.dto.CategoryDto;
import com.example.todorest.dto.CreateCategoryRequestDto;
import com.example.todorest.dto.CreateCategoryResponseDto;
import com.example.todorest.dto.UpdateCategoryRequestDto;
import com.example.todorest.entity.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    ResponseEntity<CreateCategoryResponseDto> addCategory(CreateCategoryRequestDto category) throws IOException;
    ResponseEntity <List<CategoryDto>> findCategories();
    ResponseEntity<Category> findById(int id);

    ResponseEntity<?> deleteById(int id);

    public ResponseEntity<UpdateCategoryRequestDto> update(@PathVariable("id") int id, @RequestBody Category category);

}
