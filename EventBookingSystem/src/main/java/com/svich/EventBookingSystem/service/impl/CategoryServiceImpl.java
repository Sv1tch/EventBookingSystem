package com.svich.EventBookingSystem.service.impl;

import com.svich.EventBookingSystem.exception.category.CategoryAlreadyExistsException;
import com.svich.EventBookingSystem.exception.category.CategoryNotFoundException;
import com.svich.EventBookingSystem.dto.category.response.CategoryResponse;
import com.svich.EventBookingSystem.dto.category.request.CreateCategoryRequest;
import com.svich.EventBookingSystem.dto.category.request.UpdateCategoryRequest;
import com.svich.EventBookingSystem.entity.category.Category;
import com.svich.EventBookingSystem.mapper.category.CategoryMapper;
import com.svich.EventBookingSystem.repository.category.CategoryRepository;
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
    public CategoryResponse create(CreateCategoryRequest request){
        if(categoryRepository.existsByName(request.getName())){
            throw new CategoryAlreadyExistsException("Category with name " + request.getName() + " already exists");
        }

        Category category = categoryMapper.toEntity(request);

        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toResponse(savedCategory);
    }

    @Override
    public List<CategoryResponse> findAll(){
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map(categoryMapper::toResponse).toList();
    }

    @Override
    public CategoryResponse findById(Long categoryId){
        return categoryMapper.toResponse(findCategoryById(categoryId));
    }

    @Override
    public CategoryResponse updateById(Long categoryId, UpdateCategoryRequest request){
        Category category = findCategoryById(categoryId);

        if(!category.getName().equals(request.getName()) && categoryRepository.existsByName(request.getName())){
            throw new CategoryAlreadyExistsException("Category with id %d already exists".formatted(categoryId));
        }

        categoryMapper.updateEntity(request, category);

        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toResponse(savedCategory);
    }

    @Override
    public void deleteById(Long categoryId){
        Category category = findCategoryById(categoryId);

        categoryRepository.delete(category);
    }

    private Category findCategoryById(Long categoryId){
        return categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException("Category with id %d not found".formatted(categoryId)));
    }
}
