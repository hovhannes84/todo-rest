package com.example.todorest.service.impl;

import com.example.todorest.dto.CategoryDto;
import com.example.todorest.dto.CreateCategoryRequestDto;
import com.example.todorest.dto.CreateCategoryResponseDto;
import com.example.todorest.dto.UpdateCategoryRequestDto;
import com.example.todorest.entity.Category;
import com.example.todorest.mapper.CategoryMapper;
import com.example.todorest.repository.CategoryRepository;
import com.example.todorest.service.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    @Override
    public ResponseEntity<CreateCategoryResponseDto> addCategory(CreateCategoryRequestDto requestDto) throws IOException {
        Optional<Category> byEmail = categoryRepository.findByName(requestDto.getName());
        if (byEmail.isEmpty()) {
            Category category = categoryMapper.map(requestDto);
            categoryRepository.save(category);

            return ResponseEntity.ok(categoryMapper.map(category));
        }
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .build();
    }


    @Override
    public ResponseEntity <List<CategoryDto>> findCategories() {
        List<Category> all = categoryRepository.findAll();
        if (all.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : all) {
            categoryDtos.add(categoryMapper.mapToDto(category));
        }
        return ResponseEntity.ok(categoryDtos);
    }
    @Override
    public ResponseEntity<Category> findById(int id) {
        Optional<Category> byId = categoryRepository.findById(id);
        if (byId.isPresent()) {
            return ResponseEntity.ok(byId.get());
        }
        return ResponseEntity
                .notFound()
                .build();
    }

    @Override
    public ResponseEntity<?> deleteById(int id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return ResponseEntity
                    .noContent()
                    .build();
        }
        return ResponseEntity
                .notFound()
                .build();
    }


    public ResponseEntity<UpdateCategoryRequestDto> update(int id, Category category) {
        Optional<Category> byId = categoryRepository.findById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<Category> byEmail = categoryRepository.findByName(category.getName());
        if (byEmail.isPresent() && byEmail.get().getId() != id) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Category categoryFromDB = byId.get();
        if (category.getName() != null && !category.getName().isEmpty()) {
            categoryFromDB.setName(category.getName());

            categoryRepository.save(categoryFromDB);

        }
        return ResponseEntity.ok(categoryMapper.updateDto(categoryFromDB));
    }


}
