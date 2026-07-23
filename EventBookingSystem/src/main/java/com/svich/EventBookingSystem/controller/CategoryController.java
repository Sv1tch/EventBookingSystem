package com.svich.EventBookingSystem.controller;

import com.svich.EventBookingSystem.dto.CategoryResponse;
import com.svich.EventBookingSystem.dto.CreateCategoryRequest;
import com.svich.EventBookingSystem.dto.UpdateCategoryRequest;
import com.svich.EventBookingSystem.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CreateCategoryRequest request){
        CategoryResponse categoryResponse = categoryService.createCategory(request);

        return new ResponseEntity<>(categoryResponse, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<CategoryResponse>> getAllCategories(){
        List<CategoryResponse> categories = categoryService.getAllCategories();

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long categoryId){
        CategoryResponse categoryResponse = categoryService.getCategoryById(categoryId);

        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategoryById(@PathVariable Long categoryId,@RequestBody UpdateCategoryRequest request){
        CategoryResponse categoryResponse = categoryService.updateCategoryById(categoryId, request);

        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@PathVariable Long categoryId){
        categoryService.deleteCategoryById(categoryId);
    }
}
