package com.coding.flux.sk.core.service;

import com.coding.flux.sk.core.dto.CategoryRequest;
import com.coding.flux.sk.core.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {

    public List<CategoryResponse> findAll();

    public CategoryResponse create(CategoryRequest dto);

    public CategoryResponse update(Long id, CategoryRequest dto);

    public CategoryResponse findById(Long id);

    public void deleteById(Long id);

}
