package com.coding.flux.sk.core.repository;

import com.coding.flux.sk.core.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByEnabledIsTrueOrderByIdCategoryDesc();

    boolean existsByEnabledIsTrueAndNameIgnoreCase(String name);

    Optional<Category> findByEnabledIsTrueAndIdCategory(Long id);

    boolean existsByEnabledIsTrueAndNameIgnoreCaseAndIdCategoryNot(String name, Long id);

}
