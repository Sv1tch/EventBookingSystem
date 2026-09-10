package com.svich.EventBookingSystem.service;

import com.svich.EventBookingSystem.dto.category.response.CategoryResponse;
import com.svich.EventBookingSystem.dto.category.request.CreateCategoryRequest;
import com.svich.EventBookingSystem.dto.category.request.UpdateCategoryRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CategoryService {
    CategoryResponse create(CreateCategoryRequest request);
    CategoryResponse findById(Long categoryId);
    CategoryResponse updateById(Long categoryId, UpdateCategoryRequest request);
    void deleteById(Long categoryId);

    Page<CategoryResponse> findCategories(
            String name,
            Pageable pageable
    );
}
