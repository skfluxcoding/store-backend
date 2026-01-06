package com.coding.flux.sk.core.mapper;

import com.coding.flux.sk.core.dto.*;
import com.coding.flux.sk.core.entity.Category;

import java.time.LocalDateTime;

public class CategoryMapper {

    private CategoryMapper() {
    }

    public static CategoryFindAll toFindAll(Category category) {
        return new CategoryFindAll(
                category.getIdCategory(),
                category.getName(),
                category.getDescription()
        );
    }

    public static Category toCreate(CategoryCreate dto) {
        return Category.builder()
                .name(dto.name())
                .description(dto.description())
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static CategoryCreated toCreated(Category category) {
        return new CategoryCreated(
                category.getIdCategory(),
                category.getName(),
                category.getDescription()
        );
    }

    public static Category toUpdate(Category category, CategoryUpdate dto) {
        category.setName(dto.name());
        category.setDescription(dto.description());
        category.setUpdatedAt(LocalDateTime.now());
        return category;
    }

    public static CategoryUpdated toUpdated(Category category) {
        return new CategoryUpdated(
                category.getIdCategory(),
                category.getName(),
                category.getDescription()
        );
    }

    public static CategoryFindById toFindById(Category category) {
        return new CategoryFindById(
                category.getIdCategory(),
                category.getName(),
                category.getDescription()
        );
    }
}
