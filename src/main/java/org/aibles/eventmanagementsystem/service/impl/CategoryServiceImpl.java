package org.aibles.eventmanagementsystem.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.aibles.eventmanagementsystem.dto.request.CategoryRequest;
import org.aibles.eventmanagementsystem.dto.response.CategoryResponse;
import org.aibles.eventmanagementsystem.entity.Category;
import org.aibles.eventmanagementsystem.exception.exception.CategoryException;
import org.aibles.eventmanagementsystem.exception.exception.ResourceNotFoundException;
import org.aibles.eventmanagementsystem.repository.CategoryRepository;
import org.aibles.eventmanagementsystem.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Page<CategoryResponse> getAllCategories(Pageable pageable) {
        log.info("getAllCategories with pagination and sorting");
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories.map(this::convertToCategoryResponse);
    }

    @Override
    public List<CategoryResponse> searchCategories(String keyword) {
        log.info("Searching categories with keyword: {}", keyword);
        List<Category> categories = categoryRepository.findByNameContaining(keyword);
        return categories.stream()
                .map(this::convertToCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        log.info("Creating new category: {}", request);
        Category category = convertToCategory(request);
        category.setCreatedAt(LocalDateTime.now());
        category.setLastUpdatedAt(LocalDateTime.now());
        Category savedCategory = categoryRepository.save(category);
        return convertToCategoryResponse(savedCategory);
    }

    @Override
    public CategoryResponse getCategoryById(String id) {
        log.info("Getting category by id: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryException("Category not found with id: " + id));
        return convertToCategoryResponse(category);
    }

    @Override
    public CategoryResponse updateCategory(String id, CategoryRequest request) {
        log.info("Updating category with id: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryException("Category not found with id: " + id));
        category.setName(request.getName());
        category.setLastUpdatedAt(LocalDateTime.now());
        category.setLastUpdatedBy(request.getLastUpdatedBy());
        Category updatedCategory = categoryRepository.save(category);
        return convertToCategoryResponse(updatedCategory);
    }

    @Override
    public void deleteCategory(String id) {
        log.info("Deleting category with id: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryException("Category not found with id: " + id));
        categoryRepository.delete(category);
    }

    private Category convertToCategory(CategoryRequest request) {
        Category category = new Category();
        category.setId(request.getId());
        category.setName(request.getName());
        category.setCreatedAt(request.getCreatedAt());
        category.setCreatedBy(request.getCreatedBy());
        category.setLastUpdatedAt(request.getLastUpdatedAt());
        category.setLastUpdatedBy(request.getLastUpdatedBy());
        return category;
    }

    private CategoryResponse convertToCategoryResponse(Category category) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        categoryResponse.setCreatedAt(category.getCreatedAt());
        categoryResponse.setCreatedBy(category.getCreatedBy());
        categoryResponse.setLastUpdatedAt(category.getLastUpdatedAt());
        categoryResponse.setLastUpdatedBy(category.getLastUpdatedBy());
        return categoryResponse;
    }
}