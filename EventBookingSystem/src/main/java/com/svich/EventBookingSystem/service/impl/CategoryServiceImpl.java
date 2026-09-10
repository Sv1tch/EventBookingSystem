package com.svich.EventBookingSystem.service.impl;

import com.svich.EventBookingSystem.exception.category.CategoryAlreadyExistsException;
import com.svich.EventBookingSystem.exception.category.CategoryNotFoundException;
import com.svich.EventBookingSystem.dto.category.response.CategoryResponse;
import com.svich.EventBookingSystem.dto.category.request.CreateCategoryRequest;
import com.svich.EventBookingSystem.dto.category.request.UpdateCategoryRequest;
import com.svich.EventBookingSystem.entity.category.Category;
import com.svich.EventBookingSystem.mapper.category.CategoryMapper;
import com.svich.EventBookingSystem.repository.category.CategoryRepository;
import com.svich.EventBookingSystem.repository.category.CategorySpecification;
import com.svich.EventBookingSystem.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse create(CreateCategoryRequest request){

        log.info("Creating category: name={}",
                request.getName()
        );

        if(categoryRepository.existsByName(request.getName())){
            throw new CategoryAlreadyExistsException("Category with name " + request.getName() + " already exists");
        }

        Category category = categoryMapper.toEntity(request);

        Category savedCategory = categoryRepository.save(category);

        log.info("Created category: categoryId={}, name={}",
                savedCategory.getId(),
                savedCategory.getName()
        );

        return categoryMapper.toResponse(savedCategory);
    }

    @Override
    public CategoryResponse findById(Long categoryId){
        return categoryMapper.toResponse(findCategoryById(categoryId));
    }

    @Override
    public CategoryResponse updateById(Long categoryId, UpdateCategoryRequest request){

        log.info("Updating category: categoryId={}, name={}",
                categoryId,
                request.getName()
        );

        Category category = findCategoryById(categoryId);

        if(!category.getName().equals(request.getName()) && categoryRepository.existsByName(request.getName())){
            throw new CategoryAlreadyExistsException("Category with id %d already exists".formatted(categoryId));
        }

        categoryMapper.updateEntity(request, category);

        Category savedCategory = categoryRepository.save(category);

        log.info("Updated category: categoryId={}, name={}",
                savedCategory.getId(),
                savedCategory.getName()
        );

        return categoryMapper.toResponse(savedCategory);
    }

    @Override
    public void deleteById(Long categoryId){
        Category category = findCategoryById(categoryId);

        log.info("Delete category: categoryId={}, name={}",
                category.getId(),
                category.getName()
        );

        categoryRepository.delete(category);

        log.info("Deleted category: categoryId={}, name={}",
                category.getId(),
                category.getName()
        );

    }

    private Category findCategoryById(Long categoryId){
        return categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException("Category with id %d not found".formatted(categoryId)));
    }

    // SEARCH FUTURES
    @Override
    public Page<CategoryResponse> findCategories(
            String name,
            Pageable pageable
    ){
        Specification<Category> spec = (root, query, criteriaBuilder) -> null;

        if(name != null && !name.isBlank()){
            spec = spec.and(CategorySpecification.hasName(name));
        }

        Page<Category> categories = categoryRepository.findAll(spec, pageable);

        return categories.map(categoryMapper::toResponse);
    }
}
