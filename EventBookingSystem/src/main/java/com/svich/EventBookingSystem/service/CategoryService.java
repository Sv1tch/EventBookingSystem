package com.svich.EventBookingSystem.service;

import com.svich.EventBookingSystem.dto.category.response.CategoryResponse;
import com.svich.EventBookingSystem.dto.category.request.CreateCategoryRequest;
import com.svich.EventBookingSystem.dto.category.request.UpdateCategoryRequest;

import java.util.List;

public interface CategoryService {
    CategoryResponse create(CreateCategoryRequest request);
    List<CategoryResponse> findAll();
    CategoryResponse findById(Long categoryId);
    CategoryResponse updateById(Long categoryId, UpdateCategoryRequest request);
    void deleteById(Long categoryId);
}
