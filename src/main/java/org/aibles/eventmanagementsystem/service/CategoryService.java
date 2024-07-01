package org.aibles.eventmanagementsystem.service;

import org.aibles.eventmanagementsystem.dto.request.CategoryRequest;
import org.aibles.eventmanagementsystem.dto.response.CategoryResponse;
import org.aibles.eventmanagementsystem.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    Page<CategoryResponse> getAllCategories(Pageable pageable);

    List<CategoryResponse> searchCategories(String keyword);

    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse getCategoryById(String id);

    CategoryResponse updateCategory(String id, CategoryRequest request);

    void deleteCategory(String id);
}