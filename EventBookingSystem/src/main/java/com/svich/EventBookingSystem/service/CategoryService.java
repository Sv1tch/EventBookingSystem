package com.svich.EventBookingSystem.service;

import com.svich.EventBookingSystem.dto.CategoryResponse;
import com.svich.EventBookingSystem.dto.CreateCategoryRequest;
import com.svich.EventBookingSystem.dto.UpdateCategoryRequest;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CreateCategoryRequest request);
    List<CategoryResponse> getAllCategories();
    CategoryResponse getCategoryById(Long categoryId);
    CategoryResponse updateCategoryById(Long categoryId, UpdateCategoryRequest request);
    void deleteCategoryById(Long categoryId);
}
