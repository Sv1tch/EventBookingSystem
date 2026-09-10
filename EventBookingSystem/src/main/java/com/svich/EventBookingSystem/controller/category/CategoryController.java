package com.svich.EventBookingSystem.controller.category;

import com.svich.EventBookingSystem.dto.category.response.CategoryResponse;
import com.svich.EventBookingSystem.dto.category.request.CreateCategoryRequest;
import com.svich.EventBookingSystem.dto.category.request.UpdateCategoryRequest;
import com.svich.EventBookingSystem.pagination.PageableFactory;
import com.svich.EventBookingSystem.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final PageableFactory factory;

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
            "name",
            "id"
    );

    @PostMapping("")
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CreateCategoryRequest request){
        CategoryResponse categoryResponse = categoryService.create(request);

        return new ResponseEntity<>(categoryResponse, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<Page<CategoryResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false) String name
    ){

        Pageable pageable = factory.create(page, size, sortBy, direction, ALLOWED_SORT_FIELDS);

        Page<CategoryResponse> categories = categoryService.findCategories(name, pageable);

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable Long categoryId){
        CategoryResponse categoryResponse = categoryService.findById(categoryId);

        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> updateById(@PathVariable Long categoryId, @Valid @RequestBody UpdateCategoryRequest request){
        CategoryResponse categoryResponse = categoryService.updateById(categoryId, request);

        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long categoryId){
        categoryService.deleteById(categoryId);
    }
}
