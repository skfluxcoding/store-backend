package com.coding.flux.sk.core.service.impl;

import com.coding.flux.sk.common.exception.AlreadyExistsException;
import com.coding.flux.sk.common.exception.NotFoundException;
import com.coding.flux.sk.core.dto.CategoryRequest;
import com.coding.flux.sk.core.dto.CategoryResponse;
import com.coding.flux.sk.core.entity.Category;
import com.coding.flux.sk.core.repository.CategoryJpaRepository;
import com.coding.flux.sk.core.service.CategoryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryJpaRepository categoryJpaRepository;

    public CategoryServiceImpl(CategoryJpaRepository categoryJpaRepository) {
        this.categoryJpaRepository = categoryJpaRepository;
    }

    @Override
    public List<CategoryResponse> findAll() {
        return categoryJpaRepository.findAllByEnabledIsTrueOrderByIdCategoryDesc()
                .stream()
                .map(item -> new CategoryResponse(
                        item.getIdCategory(),
                        item.getName(),
                        item.getDescription()))
                .toList();
    }

    @Override
    public CategoryResponse create(CategoryRequest dto) {
        if (categoryJpaRepository.existsByEnabledIsTrueAndNameIgnoreCase(dto.name())) {
            throw new AlreadyExistsException("Name " + dto.name() + " already exists");
        }
        var category = Category.builder()
                .name(dto.name())
                .description(dto.description())
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .build();
        var saved = categoryJpaRepository.save(category);
        return new CategoryResponse(
                saved.getIdCategory(),
                saved.getName(),
                saved.getDescription()
        );
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest dto) {
        var category = categoryJpaRepository.findByEnabledIsTrueAndIdCategory(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));
        if (categoryJpaRepository.existsByEnabledIsTrueAndNameIgnoreCaseAndIdCategoryNot(dto.name(), id)) {
            throw new AlreadyExistsException("Name " + dto.name() + " already exists");
        }
        category.setName(dto.name());
        category.setDescription(dto.description());
        category.setUpdatedAt(LocalDateTime.now());
        var saved = categoryJpaRepository.save(category);
        return new CategoryResponse(
                saved.getIdCategory(),
                saved.getName(),
                saved.getDescription()
        );
    }

    @Override
    public CategoryResponse findById(Long id) {
        var category = categoryJpaRepository.findByEnabledIsTrueAndIdCategory(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));
        return new CategoryResponse(
                category.getIdCategory(),
                category.getName(),
                category.getDescription()
        );
    }

    @Override
    public void deleteById(Long id) {
        var category = categoryJpaRepository.findByEnabledIsTrueAndIdCategory(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));
        category.setEnabled(false);
        category.setUpdatedAt(LocalDateTime.now());
        categoryJpaRepository.delete(category);
    }
}
