package com.svich.EventBookingSystem.service.impl;

import com.svich.EventBookingSystem.exception.CategoryNotFoundException;
import com.svich.EventBookingSystem.dto.CategoryResponse;
import com.svich.EventBookingSystem.dto.CreateCategoryRequest;
import com.svich.EventBookingSystem.dto.UpdateCategoryRequest;
import com.svich.EventBookingSystem.entity.Category;
import com.svich.EventBookingSystem.mapper.CategoryMapper;
import com.svich.EventBookingSystem.repository.CategoryRepository;
import com.svich.EventBookingSystem.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createCategory(CreateCategoryRequest request){
        Category category = categoryMapper.toEntity(request);

        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toResponse(savedCategory);
    }

    @Override
    public List<CategoryResponse> getAllCategories(){
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map(categoryMapper::toResponse).toList();
    }

    @Override
    public CategoryResponse getCategoryById(Long categoryId){
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        return categoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse updateCategoryById(Long categoryId, UpdateCategoryRequest request){
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        categoryMapper.updateEntity(request, category);

        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toResponse(savedCategory);
    }

    @Override
    public void deleteCategoryById(Long categoryId){
        if(!categoryRepository.existsById(categoryId)){
            throw new CategoryNotFoundException("Category not found");
        }

        categoryRepository.deleteById(categoryId);
    }
}
