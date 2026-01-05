package com.coding.flux.sk.core.repository;


import com.coding.flux.sk.core.entity.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryMongoRepository extends MongoRepository<Category, String> {

    List<Category> findAllByEnabledTrueOrderByCreatedAtDesc();

    boolean existsByEnabledTrueAndNameIgnoreCase(String name);

    Optional<Category> findByEnabledTrueAndIdCategory(String id);

    boolean existsByEnabledTrueAndNameIgnoreCaseAndIdCategoryNot(String name, String id);
}