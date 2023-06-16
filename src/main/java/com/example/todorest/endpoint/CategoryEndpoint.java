package com.example.todorest.endpoint;

import com.example.todorest.dto.CategoryDto;
import com.example.todorest.dto.CreateCategoryRequestDto;
import com.example.todorest.dto.CreateCategoryResponseDto;
import com.example.todorest.dto.UpdateCategoryRequestDto;
import com.example.todorest.entity.Category;
import com.example.todorest.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryEndpoint {

    private final CategoryService categoryService;


    @PostMapping()
    public ResponseEntity<CreateCategoryResponseDto> create(@RequestBody CreateCategoryRequestDto requestDto) throws IOException {
        return ResponseEntity.ok(categoryService.addCategory(requestDto).getBody());
    }

    @GetMapping()
    public ResponseEntity<List<CategoryDto>> getAll() {
        return categoryService.findCategories();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable("id") int id) {
        return categoryService.findById(id);

    }
    @PutMapping("/{id}")
    public ResponseEntity<UpdateCategoryRequestDto> update(@PathVariable("id") int id, @RequestBody Category category){
        return ResponseEntity.ok(categoryService.update(id,category).getBody());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
        return categoryService.deleteById(id);

    }


}
